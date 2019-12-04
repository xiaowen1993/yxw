/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年8月14日
 * @version 1.0
 */
package com.yxw.mobileapp.invoke.payment.register;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.entity.platform.payrefund.PayAsynResponse;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.register.RegRecord;
import com.yxw.interfaces.vo.register.appointment.PayReg;
import com.yxw.interfaces.vo.register.onduty.PayCurReg;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;
import com.yxw.mobileapp.invoke.thread.RegRefundRunnable;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年8月14日  
 */
public class RegisterPaymentInvoker {

	private static final String DEF_FAILURE_MSG = "不符合医院限定,无法预约支付,预约已取消或者预约已作废.";
	private static Logger logger = LoggerFactory.getLogger(RegisterPaymentInvoker.class);
	private static RegisterBizManager registerBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
	private static RegisterService registerSvc = SpringContextHolder.getBean(RegisterService.class);
	private static CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);

	private static ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * 挂号回调具体业务处理
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月20日 
	 * @param payAsynResponse
	 */
	@SuppressWarnings("unchecked")
	public static void regPayment(Object payAsynResponse) {

		/*		if (payAsynResponse instanceof WechatPayAsynResponse) {

				} else if (payAsynResponse instanceof AlipayAsynResponse) {

				} else if (payAsynResponse instanceof UnionpayAsynResponse) {
					// TODO 新增
					
				}*/

		PayAsynResponse payAsynRsp = (PayAsynResponse) payAsynResponse;

		List<Object> cacheParams = new ArrayList<Object>();

		RegisterRecord record = registerSvc.findRecordByOrderNo(payAsynRsp.getOrderNo());//原订单号始终返回过来

		if (StringUtils.equals(payAsynRsp.getTradeState(), TradeConstant.TRADE_STATE_SUCCESS)) {//支付成功

			boolean hasLock = false;
			cacheParams.add(payAsynRsp.getOrderNo());
			List<Object> regLock = serveComm.get(CacheType.RE_INVOKE_LOCK_CACHE, "getRegLock", cacheParams);//回调加锁
			if (!CollectionUtils.isEmpty(regLock)) {
				hasLock = (Boolean) regLock.get(0);
			}

			//重复回调  不处理
			if ( ( record.getIsHadCallBack() != null && record.getIsHadCallBack().intValue() == BizConstant.IS_HAD_CALLBACK_YES )
					|| !hasLock) {
				logger.info("【挂号支付回调】 第3方交易平台回调重复调用 不处理.orderNo:{}", new Object[] { payAsynRsp.getOrderNo() });
				return;
			}

			try {
				if (StringUtils.isNotBlank(payAsynRsp.getTradeTime())) {
					record.setPayTime(BizConstant.YYYYMMDDHHMMSS.parse(payAsynRsp.getTradeTime()).getTime());
				} else {
					logger.error("【挂号支付回调】regPayMent 支付回调:payMent success.orderNo:{},tradeTime is null.use systime.");
					record.setPayTime(System.currentTimeMillis());
				}
			} catch (ParseException e) {

				logger.error("【挂号支付回调】regPayMent 支付回调:payMent success.orderNo:{},tradeTime:{},Parse tradeTime is error.use systime.");
				record.setPayTime(System.currentTimeMillis());
			}

			/**第3方支付成功 记录第3方交易平台返回的订单号 ***/
			record.setAgtOrdNum(payAsynRsp.getAgtOrderNo());
			//加入支付成功日志信息
			StringBuffer infoSb = new StringBuffer();
			infoSb.append("【挂号支付回调】第3方支付成功,第3方订单号-").append(payAsynRsp.getAgtOrderNo()).append(",支付时间-").append(payAsynRsp.getTradeTime());
			record.addLogInfo(infoSb.toString());
			record.setPayStatus(OrderConstant.STATE_PAYMENT);
			record.setIsHadCallBack(BizConstant.IS_HAD_CALLBACK_YES);
			registerSvc.updateStatusForPay(record);
		}

		if (StringUtils.equals(payAsynRsp.getTradeState(), TradeConstant.TRADE_STATE_EXCEPTION)) {
			/**支付失败的情况 不记录到数据库  只写入缓存 用于把支付失败的原因展示给用户**/
			record.setFailureMsg(payAsynRsp.getResultMsg());
			record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
			logger.info("【挂号支付回调】第3方交易平台支付失败的情况 不记录到数据库 ,只写入缓存.orderNo:{}", new Object[] { payAsynRsp.getOrderNo() });
			cacheParams.clear();
			cacheParams.add(record);
			serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateCacheForPayFail", cacheParams);
		} else if (StringUtils.equals(payAsynRsp.getTradeState(), TradeConstant.TRADE_STATE_REFUND)
				|| StringUtils.equals(payAsynRsp.getTradeState(), TradeConstant.TRADE_STATE_NOTPAY)
				|| StringUtils.equals(payAsynRsp.getTradeState(), TradeConstant.TRADE_STATE_CLOSED)) {
			//不处理
		} else {

			boolean isStartRefundThread = false;
			// 判断是否支付超时了，若是支付超时则进入退费
			Long overtime = record.getPayTimeoutTime();
			long nowtime = System.currentTimeMillis();
			Integer onlinePaymentType = record.getOnlinePaymentType();
			if ( ( record.getRegStatus() != null && record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_PAY_TIMEOUT_CANCEL )
					|| ( onlinePaymentType == BizConstant.PAYMENT_TYPE_MUST && overtime != null && overtime <= nowtime )) {
				logger.info("【挂号支付回调】该订单已经超过支付时间，已经进入超时解锁状态，将发起退费并取消号源 {}", record.getOrderNo());
				record.addLogInfo("【挂号支付回调】该订单已经超过支付时间，已经进入超时解锁状态，将发起退费并取消号源 ");
				//若已经支付超时取消,第3方平台退费(不退号)
				Thread refundThread = new Thread(new RegRefundRunnable(record, true, RegisterConstant.STATE_NORMAL_PAY_TIMEOUT_CANCEL));
				refundThread.start();
			} else {
				Map<String, Object> interfaceResMap = null;
				if (record.getRegType() != null && record.getRegType().intValue() == RegisterConstant.REG_TYPE_CUR) {
					interfaceResMap = registerBizManager.registerCurPayReg(record);
				} else {
					interfaceResMap = registerBizManager.registerPayReg(record);
				}
				logger.info("【挂号支付回调】调用医院支付接口:orderNo：{},registerPayReg 接口返回结果res：{}", record.getOrderNo(),
						JSON.toJSONString(interfaceResMap));

				//支付后与his确认是否出现异常
				boolean isException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
				if (isException) {
					//查询his 是否已支付
					interfaceResMap = registerBizManager.queryRegisterRecords(record);
					isException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
					logger.info("【挂号支付回调】第3方交易平台支付成功后HIS确认异常.订单号:{},调用his订单查询接口返回订单数据:{}",
							new Object[] { record.getOrderNo(), JSON.toJSONString(interfaceResMap) });
					Boolean isPutExceptionCache = false;
					if (isException) {
						isPutExceptionCache = true;
					} else {
						String resCode = interfaceResMap.get(BizConstant.INTERFACE_MAP_CODE_KEY).toString();
						if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(resCode)) {
							List<RegRecord> records = (List<RegRecord>) interfaceResMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
							if (!CollectionUtils.isEmpty(records)) {
								RegRecord hisRecord = records.get(0);
								if (RegisterConstant.HIS_STATUS_HAD_PAYMENT.equalsIgnoreCase(hisRecord.getStatus())
										|| RegisterConstant.HIS_STATUS_HAD_TAKENO.equalsIgnoreCase(hisRecord.getStatus())
										|| RegisterConstant.HIS_STATUS_HAD_VISITED.equalsIgnoreCase(hisRecord.getStatus())) {
									dealWithHisHadPaymentRegOrder(record, hisRecord, interfaceResMap);
								} else {
									//其它状态 退费操作
									isStartRefundThread = true;
									record.addLogInfo("【挂号支付回调】调用医院订单查询接口查询到的订单状态为,hisStatus:".concat(hisRecord.getStatus()).concat(
											",需要退费,isStartRefundThread = true"));
									logger.info("【挂号支付回调】第3方交易平台支付成功后HIS支付确认接口失败,调用his订单查询接口.订单号:{},hisRecord订单状态:{}",
											new Object[] { record.getOrderNo(), hisRecord.getStatus() });
									/** 挂号失败 消息推送 **/
									commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_APPOINT_PAY_FAIL);
								}
							}
						} else if (BizConstant.INTERFACE_RES_SUCCESS_NO_DATA_CODE.equalsIgnoreCase(resCode)) {
							// 进入轮询【第三方支付成功his支付异常】
							isStartRefundThread = false;
							record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY_HIS_COMMIT);
							record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
							record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
							record.setHandleCount(0);
							record.addLogInfo("【挂号支付回调】调用医院订单查询接口查询不到订单,进入异常轮询【第三方支付成功his支付异常】,");
							registerSvc.updateStatusForPay(record);

							RegisterRecord recordT = registerSvc.findRecordByOrderNo(payAsynRsp.getOrderNo());
							logger.info("【挂号支付回调】{},regstatus={}", record.getOrderNo(), recordT.getRegStatus());
							logger.info("【挂号支付回调】第3方交易平台支付成功后HIS支付确认失败.订单号:{},调用【his订单查询接口】未查询到订单数据", record.getOrderNo());
							/** 挂号异常消息推送 **/
							commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_APPOINT_PAY_EXP);
						} else {
							isPutExceptionCache = true;
							record.addLogInfo("【挂号支付回调】调用医院订单查询接口查询返回其他结果代码,放入异常队列,isPutExceptionCache = true");
							logger.info("【挂号支付回调】orderNo:{},调用医院订单查询接口查询返回其他结果代码,放入异常队列,isPutExceptionCache = true", record.getOrderNo());
						}
					}
					if (isPutExceptionCache) {
						isStartRefundThread = false;
						logger.info("【挂号支付回调】record-OrderNo:{},支付回调->调用his接口支付异常处理.", record.getOrderNo());
						//支付成功后his确认异常处理
						dealWithPayRegException(record);
					}
				} else {
					boolean isSuccess = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
					if (isSuccess) {
						logger.info("【挂号支付回调】record-OrderNo:{},支付回调->调用his接口支付成功.", record.getOrderNo());
						//支付成功后  his确认成功后续处理
						dealWithPayRegSuccess(record, interfaceResMap);
					} else {
						logger.info("【挂号支付回调】record-OrderNo:{},支付回调->调用his接口支付失败.", record.getOrderNo());
						//支付失败后  his确认失败后续处理
						dealWithPayRegFailure(record, interfaceResMap);
						//进入退费子线程
						isStartRefundThread = true;
					}
				}
				if (isStartRefundThread) {
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_FAILURE);
					record.setPayStatus(OrderConstant.STATE_REFUNDING);
					registerSvc.updateStatusForPay(record);
					logger.info("【挂号支付回调】orderNo:{},开启子线程退费", record.getOrderNo());
					Thread refundThread = new Thread(new RegRefundRunnable(record, true, null));
					refundThread.start();
				}
			}
		}

		// 释放锁
		cacheParams.clear();
		cacheParams.add(payAsynRsp.getOrderNo());
		serveComm.delete(CacheType.RE_INVOKE_LOCK_CACHE, "delRegLock", cacheParams);
	}

	/**
	 * 处理已经支付确认过的his订单
	 * @param record
	 * @param hisRecord
	 * @param interfaceResMap
	 */
	private static void dealWithHisHadPaymentRegOrder(RegisterRecord record, RegRecord hisRecord, Map<String, Object> interfaceResMap) {
		record.setPayStatus(OrderConstant.STATE_PAYMENT);
		record.setRegStatus(RegisterConstant.STATE_NORMAL_HAD);
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
		record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);

		//根据his订单添加信息到平台挂号记录中
		addHisOrderInfo(record, hisRecord);

		logger.info("orderNo:{},第3方支付成功后调用医院支付确认成功interfaceResMap:{} ",
				new Object[] { record.getOrderNo(), JSON.toJSONString(interfaceResMap) });
		record.addLogInfo("第3方支付成功后调用医院支付确认成功,状态变更为已预约[STATE_NORMAL_HAD=1]");
		registerSvc.updateStatusForPay(record);

		/** 预约成功(已缴费) 消息推送  **/
		if (record.getRegType() != null && record.getRegType().intValue() == RegisterConstant.REG_TYPE_CUR) {
			commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_ON_DUTY_PAY_SUCCESS);
		} else {
			commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_APPOINT_PAY_SUCCESS);
		}
	}

	/**
	 * 根据his订单添加信息到平台挂号记录中
	 * @param record
	 * @param hisRecord
	 */
	private static void addHisOrderInfo(RegisterRecord record, RegRecord hisRecord) {
		if (StringUtils.isNotEmpty(hisRecord.getHisOrdNum())) {
			record.setHisOrdNo(hisRecord.getHisOrdNum());
		}

		if (StringUtils.isNotEmpty(hisRecord.getSerialNum())) {
			record.setSerialNum(hisRecord.getSerialNum());
		}

		if (StringUtils.isNotEmpty(hisRecord.getVisitLocation())) {
			record.setVisitLocation(hisRecord.getVisitLocation());
		}

		if (StringUtils.isNotEmpty(hisRecord.getReceiptNum())) {
			record.setReceiptNum(hisRecord.getReceiptNum());
		}
		if (StringUtils.isNotEmpty(hisRecord.getBarCode())) {
			record.setBarcode(hisRecord.getBarCode());
		}
		if (StringUtils.isNotEmpty(hisRecord.getVisitDesc())) {
			record.setVisitDesc(hisRecord.getVisitDesc());
		}
	}

	/**
	 * 支付成功后  his确认失败后续处理
	 * @param record
	 */
	private static void dealWithPayRegFailure(RegisterRecord record, Map<String, Object> interfaceResMap) {
		record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY_HIS_COMMIT);
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
		record.setPayStatus(OrderConstant.STATE_REFUNDING);
		record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
		Object msgObj = interfaceResMap.get(BizConstant.INTERFACE_MAP_MSG_KEY);
		if (msgObj == null) {
			record.addLogInfo(DEF_FAILURE_MSG);
			record.setFailureMsg(DEF_FAILURE_MSG);
			msgObj = DEF_FAILURE_MSG;
		} else {
			record.addLogInfo(msgObj.toString());
			record.setFailureMsg(msgObj.toString());
		}
		record.addLogInfo("第3方支付成功后调用医院支付确认接口失败,进入退费流程;");
		registerSvc.updateStatusForPay(record);
		logger.info("支付成功后  his确认失败.orderNo:{}.failure info:{}", new Object[] { record.getOrderNo(), msgObj.toString() });

		/** 挂号失败 消息推送 **/
		//commonMsgPushSvc.pushMsg(record, CommonMsgPushService.ACTION_TYPE_APPOINT_PAY_FAIL);
	}

	/**
	 * 支付成功后  his确认成功后续处理
	 * @param record
	 */
	private static void dealWithPayRegSuccess(RegisterRecord record, Map<String, Object> interfaceResMap) {
		//某些医院 可能此时才添加  就诊序号  就诊地址  his订单号
		addRecordInfo(record, interfaceResMap.get(BizConstant.INTERFACE_MAP_DATA_KEY));

		record.setPayStatus(OrderConstant.STATE_PAYMENT);
		record.setRegStatus(RegisterConstant.STATE_NORMAL_HAD);
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
		record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
		record.addLogInfo("第3方支付成功后调用医院支付确认成功,状态变更为已预约[STATE_NORMAL_HAD=1]");
		logger.info("支付成功后his确认成功.orderNo:{}.开始更新数据库和缓存状态为PayStatus:{},RegStatus:{}", record.getOrderNo(), record.getPayStatus(),
				record.getRegStatus());
		registerSvc.updateStatusForPay(record);

		/** 预约成功(已缴费) 消息推送  **/
		if (record.getRegType() != null && record.getRegType().intValue() == RegisterConstant.REG_TYPE_CUR) {
			commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_ON_DUTY_PAY_SUCCESS);
		} else {
			commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_APPOINT_PAY_SUCCESS);
		}
	}

	/**
	 * 支付成功后his确认异常处理
	 * @param record
	 */
	private static void dealWithPayRegException(RegisterRecord record) {
		//his确认异常处理
		record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY_HIS_COMMIT);
		record.setPayStatus(OrderConstant.STATE_REFUNDING);
		record.setHandleCount(0);
		record.addLogInfo("第3方支付成功后调用医院支付确认接口失败,状态变更为医院支付确认异常[STATE_EXCEPTION_PAY_HIS_COMMIT=7],进入轮询流程;");
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
		registerSvc.updateStatusForPay(record);
		/** 挂号异常消息推送 **/
		commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_APPOINT_PAY_EXP);
	}

	private static void addRecordInfo(RegisterRecord record, Object payRegObj) {
		if (payRegObj != null) {
			if (record.getRegType() != null && record.getRegType().intValue() == RegisterConstant.REG_TYPE_CUR) {
				PayCurReg payReg = (PayCurReg) payRegObj;
				if (StringUtils.isNotBlank(payReg.getSerialNum())) {
					record.setSerialNum(payReg.getSerialNum());
				}
				if (StringUtils.isNotBlank(payReg.getHisOrdNum())) {
					record.setHisOrdNo(payReg.getHisOrdNum());
				}
				if (StringUtils.isNotBlank(payReg.getVisitLocation())) {
					record.setVisitLocation(payReg.getVisitLocation());
				}

				if (StringUtils.isNotBlank(payReg.getReceiptNum())) {
					record.setReceiptNum(payReg.getReceiptNum());
				}

				if (StringUtils.isNotBlank(payReg.getBarCode())) {
					record.setBarcode(payReg.getBarCode());
				}
			} else {
				PayReg payReg = (PayReg) payRegObj;
				if (StringUtils.isNotBlank(payReg.getSerialNum())) {
					record.setSerialNum(payReg.getSerialNum());
				}
				if (StringUtils.isNotBlank(payReg.getHisOrdNum())) {
					record.setHisOrdNo(payReg.getHisOrdNum());
				}
				if (StringUtils.isNotBlank(payReg.getVisitLocation())) {
					record.setVisitLocation(payReg.getVisitLocation());
				}

				if (StringUtils.isNotBlank(payReg.getReceiptNum())) {
					record.setReceiptNum(payReg.getReceiptNum());
				}

				if (StringUtils.isNotBlank(payReg.getBarCode())) {
					record.setBarcode(payReg.getBarCode());
				}
			}
		}
	}
}
