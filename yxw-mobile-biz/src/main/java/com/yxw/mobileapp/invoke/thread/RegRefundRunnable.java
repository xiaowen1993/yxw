/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-4</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.invoke.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.entity.platform.payrefund.AlipayRefund;
import com.yxw.commons.entity.platform.payrefund.AlipayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefund;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefundResponse;
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;
import com.yxw.interfaces.vo.register.RegRecord;
import com.yxw.interfaces.vo.register.appointment.RefundRegRequest;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;
import com.yxw.payrefund.service.RefundService;
import com.yxw.payrefund.utils.TradeCommonHoder;

/**
 * @Package: com.yxw.mobileapp.invoke.thread
 * @ClassName: RegRefundRunnable
 * @Statement: <p>退费子线程</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-4
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegRefundRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(RegRefundRunnable.class);
	private RegisterBizManager registerBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
	private RegisterService registerSvc = SpringContextHolder.getBean(RegisterService.class);
	private CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	private RegisterRecord record;

	/**
	 * 是否需要释放号源
	 */
	private Boolean isNeedCancleReg;

	private Integer regStatus;

	public RegRefundRunnable(RegisterRecord record) {
		super();
		this.record = record;
	}

	public RegRefundRunnable(RegisterRecord record, Boolean isNeedCancleReg, Integer regStatus) {
		super();
		this.record = record;
		this.isNeedCancleReg = isNeedCancleReg;
		this.regStatus = regStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		// TODO Auto-generated method stub
		if (isNeedCancleReg == null) {
			isNeedCancleReg = true;
		}
		boolean ifRefund = false;
		try {

			RuleEdit ruleEdit = ruleConfigManager.getRuleEditByHospitalId(record.getHospitalId());
			int waitingSecond = ruleEdit.getRefundWaitingSeconds();
			logger.info("waitingSecond:{}", waitingSecond);
			if (waitingSecond == 0) {
				waitingSecond = 180;
			}
			Thread.sleep(waitingSecond * 1000);
			Map<String, Object> interfaceResMap = registerBizManager.queryRegisterRecords(record);
			Boolean isException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
			logger.info("【regRefundRunable退费子线程】订单号:{},interfaceResMap:{}", record.getOrderNo(), JSON.toJSONString(interfaceResMap));
			if (isException) {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY_HIS_COMMIT);
				record.setPayStatus(OrderConstant.STATE_REFUNDING);
				record.setHandleCount(0);
				record.addLogInfo("第3方支付成功后调用医院支付确认接口失败,【regRefundRunable退费子线程】再次查询订单状态出现异常，状态变更为医院支付确认异常[STATE_EXCEPTION_PAY_HIS_COMMIT=7],进入轮询流程;");
				record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
				registerSvc.updateStatusForPay(record);
				commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_APPOINT_PAY_EXP);
			} else {
				String resCode = interfaceResMap.get(BizConstant.INTERFACE_MAP_CODE_KEY).toString();
				if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(resCode)) {
					List<RegRecord> records = (List<RegRecord>) interfaceResMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
					if (!CollectionUtils.isEmpty(records)) {
						RegRecord hisRecord = records.get(0);
						if (RegisterConstant.HIS_STATUS_HAD_PAYMENT.equalsIgnoreCase(hisRecord.getStatus())
								|| RegisterConstant.HIS_STATUS_HAD_TAKENO.equalsIgnoreCase(hisRecord.getStatus())
								|| RegisterConstant.HIS_STATUS_HAD_VISITED.equalsIgnoreCase(hisRecord.getStatus())) {

							record.setPayStatus(OrderConstant.STATE_PAYMENT);
							record.setRegStatus(RegisterConstant.STATE_NORMAL_HAD);
							record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
							record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);

							//根据his订单添加信息到平台挂号记录中
							addHisOrderInfo(record, hisRecord);
							record.addLogInfo("【regRefundRunable退费子线程】第3方支付成功后调用医院支付确认成功,状态变更为已预约[STATE_NORMAL_HAD=1]");
							registerSvc.updateStatusForPay(record);
							/** 预约成功(已缴费) 消息推送  **/
							if (record.getRegType() != null && record.getRegType().intValue() == RegisterConstant.REG_TYPE_CUR) {
								commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_ON_DUTY_PAY_SUCCESS);
							} else {
								commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_APPOINT_PAY_SUCCESS);
							}

						} else {
							ifRefund = true;
							/** 挂号失败 消息推送 **/
							commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_APPOINT_PAY_FAIL);
						}
					} else {
						record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY_HIS_COMMIT);
						record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
						record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
						record.setHandleCount(0);
						record.addLogInfo("【回调】-【regRefundRunable退费子线程】调用医院订单查询接口查询不到订单,进入异常轮询【第三方支付成功his支付异常】,");
						registerSvc.updateExceptionRecord(record.convertExceptionObj());

						List<Object> params = new ArrayList<Object>();
						params.add(record.convertExceptionObj());
						serveComm.set(CacheType.REGISTER_EXCEPTION_CACHE, "setExceptionRegisterToCache", params);

						logger.info("【回调】-【regRefundRunable退费子线程】第3方交易平台支付成功后HIS支付确认失败.订单号:{},调用【his订单查询接口】未查询到订单数据", record.getOrderNo());
					}
				} else if (BizConstant.INTERFACE_RES_SUCCESS_NO_DATA_CODE.equalsIgnoreCase(resCode)) {
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY_HIS_COMMIT);
					record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
					record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
					record.setHandleCount(0);
					record.addLogInfo("【回调】-【regRefundRunable退费子线程】调用医院订单查询接口查询不到订单,进入异常轮询【第三方支付成功his支付异常】,");
					registerSvc.updateExceptionRecord(record.convertExceptionObj());

					List<Object> params = new ArrayList<Object>();
					params.add(record.convertExceptionObj());
					serveComm.set(CacheType.REGISTER_EXCEPTION_CACHE, "setExceptionRegisterToCache", params);

					logger.info("【回调】-【regRefundRunable退费子线程】第3方交易平台支付成功后HIS支付确认失败.订单号:{},调用【his订单查询接口】未查询到订单数据", record.getOrderNo());
				}
			}

			if (ifRefund) {

				//第3方交易平台退费调用
				/*				Integer regMode = record.getRegMode();
								Refund refund = null;
								RefundService refundService = null;
								logger.info("refund is start.orderNo:{},regMode:{}", record.getOrderNo(), regMode.intValue());

								if (regMode.intValue() == BizConstant.MODE_TYPE_WEIXIN_VAL) {
									RefundWechat refundWechat = registerSvc.buildWechatRefundInfo(record, BizConstant.INVOKE_TYPE_SERVICE_REQ);
									if (logger.isDebugEnabled()) {
										logger.debug("refundWechat info:{}", JSON.toJSONString(refundWechat));
									}
									refundService = TradeCommonHoder.getInvokeRefundService(refundWechat);
									refund = refundService.wechatRefundService(refundWechat);
								} else {
									RefundAli refundAli = registerSvc.buildAliRefundInfo(record, BizConstant.INVOKE_TYPE_SERVICE_REQ);
									if (logger.isDebugEnabled()) {
										logger.debug("refundAli info:{}", JSON.toJSONString(refundAli));
									}
									refundService = TradeCommonHoder.getInvokeRefundService(refundAli);
									refund = refundService.aliRefundService(refundAli);
								}*/

				Object refund = registerSvc.buildRefundInfo(record);
				RefundService refundService = TradeCommonHoder.getInvokeRefundService();

				if (logger.isDebugEnabled()) {
					logger.debug("refund info : {} ", JSON.toJSONString(refund));
				}

				if (refund instanceof WechatPayRefund) {
					WechatPayRefundResponse wechatPayRefundResponse = refundService.wechatPayRefund((WechatPayRefund) refund);

					if (StringUtils.equals(wechatPayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
						record.setAgtRefundOrdNum(record.getAgtOrdNum());
						record.setPayStatus(OrderConstant.STATE_REFUND);
						record.setRefundTime(System.currentTimeMillis());
						String oldLog = record.getHandleLog() == null ? "" : record.getHandleLog();
						record.setHandleLog(oldLog + " " + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + " 第3方交易平台退费成功;");
						registerSvc.updateRecordForAgtRefund(record);
						List<Object> params = new ArrayList<Object>();
						params.add(record);
						serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordTradeInfoToCache", params);

						logger.info("invoke trade refund success.orderNo:{}.exec cancleRegister.", record.getOrderNo());
						//取消挂号 释放号源
						if (isNeedCancleReg) {
							RegRecord hisRecord = handleQueryHisRecord(record.convertExceptionObj());
							if (hisRecord == null) {
								cancleRegister();
							} else {
								String status = hisRecord.getStatus();
								logger.info("regRefundRunable.his record status = {}", status);
								if (status != null && status.equals(RegisterConstant.HIS_STATUS_NO_PAYMENT)) {
									cancleRegister();
								} else if (status != null
										&& ( status.equals(RegisterConstant.HIS_STATUS_HAD_PAYMENT)
												|| status.equals(RegisterConstant.HIS_STATUS_HAD_TAKENO) || status
													.equals(RegisterConstant.HIS_STATUS_HAD_VISITED) )) {
									handleRefundHisCommitException(record.convertExceptionObj());
								}
							}
						}
					} else {
						logger.warn("invoke trade refund failure.orderNo:{}.exec handleHisException.", record.getOrderNo());
						handleHisException(record);
					}

				} else if (refund instanceof AlipayRefund) {

					AlipayRefundResponse alipayRefundResponse = refundService.alipayRefund((AlipayRefund) refund);

					if (StringUtils.equals(alipayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
						record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
						record.setPayStatus(OrderConstant.STATE_REFUND);
						record.setRefundTime(System.currentTimeMillis());
						String oldLog = record.getHandleLog() == null ? "" : record.getHandleLog();
						record.setHandleLog(oldLog + " " + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + " 第3方交易平台退费成功;");
						registerSvc.updateRecordForAgtRefund(record);
						List<Object> params = new ArrayList<Object>();
						params.add(record);
						serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordTradeInfoToCache", params);

						logger.info("invoke trade refund success.orderNo:{}.exec cancleRegister.", record.getOrderNo());
						//取消挂号 释放号源
						if (isNeedCancleReg) {
							RegRecord hisRecord = handleQueryHisRecord(record.convertExceptionObj());
							if (hisRecord == null) {
								cancleRegister();
							} else {
								String status = hisRecord.getStatus();
								logger.info("regRefundRunable.his record status = {}", status);
								if (status != null && status.equals(RegisterConstant.HIS_STATUS_NO_PAYMENT)) {
									cancleRegister();
								} else if (status != null
										&& ( status.equals(RegisterConstant.HIS_STATUS_HAD_PAYMENT)
												|| status.equals(RegisterConstant.HIS_STATUS_HAD_TAKENO) || status
													.equals(RegisterConstant.HIS_STATUS_HAD_VISITED) )) {
									handleRefundHisCommitException(record.convertExceptionObj());
								}
							}
						}
					} else {
						logger.warn("invoke trade refund failure.orderNo:{}.exec handleHisException.", record.getOrderNo());
						handleHisException(record);
					}

				} else if (refund instanceof UnionpayRefund) {
					// TODO 新增
					UnionpayRefundResponse unionpayRefundResponse = refundService.unionpayRefund((UnionpayRefund) refund);

					if (StringUtils.equals(unionpayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
						record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
						record.setPayStatus(OrderConstant.STATE_REFUND);
						record.setRefundTime(System.currentTimeMillis());
						String oldLog = record.getHandleLog() == null ? "" : record.getHandleLog();
						record.setHandleLog(oldLog + " " + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + " 第3方交易平台退费成功;");
						registerSvc.updateRecordForAgtRefund(record);
						List<Object> params = new ArrayList<Object>();
						params.add(record);
						serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordTradeInfoToCache", params);

						logger.info("invoke trade refund success.orderNo:{}.exec cancleRegister.", record.getOrderNo());
						//取消挂号 释放号源
						if (isNeedCancleReg) {
							RegRecord hisRecord = handleQueryHisRecord(record.convertExceptionObj());
							if (hisRecord == null) {
								cancleRegister();
							} else {
								String status = hisRecord.getStatus();
								logger.info("regRefundRunable.his record status = {}", status);
								if (status != null && status.equals(RegisterConstant.HIS_STATUS_NO_PAYMENT)) {
									cancleRegister();
								} else if (status != null
										&& ( status.equals(RegisterConstant.HIS_STATUS_HAD_PAYMENT)
												|| status.equals(RegisterConstant.HIS_STATUS_HAD_TAKENO) || status
													.equals(RegisterConstant.HIS_STATUS_HAD_VISITED) )) {
									handleRefundHisCommitException(record.convertExceptionObj());
								}
							}
						}
					} else {
						logger.warn("invoke trade refund failure.orderNo:{}.exec handleHisException.", record.getOrderNo());
						handleHisException(record);
					}

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("RegRefundRunnable run error.exec handleHisException.record orderNo:{},errorMsg:{},caseBy:{}",
					new Object[] { record.getOrderNo(), e.getMessage(), e.getCause() });
			//更新数据库/缓存挂号记录 ,写入异常缓存
			handleHisException(record);
		}

	}

	private RegRecord handleQueryHisRecord(ExceptionRecord record) {

		// 查询his是否生成订单
		List<RegRecord> records = null;
		Map<String, Object> interfaceResMap = registerBizManager.queryRegisterRecords(record.convertRecord());
		Boolean isHappenException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
		RegRecord hisRecord = null;
		if (!isHappenException) {
			String resCode = interfaceResMap.get(BizConstant.INTERFACE_MAP_CODE_KEY).toString();
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(resCode)) {
				records = (List<RegRecord>) interfaceResMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
				if (!CollectionUtils.isEmpty(records)) {
					hisRecord = records.get(0);
				}
			}
		}
		return hisRecord;
	}

	public void cancleRegister() {
		Integer regType = record.getRegType();
		if (regType == null) {
			regType = RegisterConstant.REG_TYPE_APPOINTMENT;
		}

		Map<String, Object> interfaceResMap = new HashMap<String, Object>();
		if (regType.intValue() == RegisterConstant.REG_TYPE_CUR) {
			interfaceResMap = registerBizManager.cancelCurRegister(record);
		} else {
			interfaceResMap = registerBizManager.cancelRegister(record);
		}

		Boolean isException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);

		if (isException) {
			logger.info("exec cancleRegister is exception.status change [STATE_EXCEPTION_FAILURE=10].orderNo:{}", record.getOrderNo());
			handleCancleException(record);
		} else {
			String resCode = (String) interfaceResMap.get(BizConstant.INTERFACE_MAP_CODE_KEY);
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(resCode)) {
				if (regStatus != null) {
					record.setRegStatus(regStatus);
				} else {
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_FAILURE);
				}
				record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
				record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
				record.addLogInfo("【支付回调】---> 取消挂号(解锁)成功");
				registerSvc.updateExceptionRecord(record.convertExceptionObj());
				//				recordCache.updateRecordToCache(record);
				List<Object> params = new ArrayList<Object>();
				params.add(record);
				serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordToCache", params);

				logger.info("exec cancleRegister is success.status change [STATE_EXCEPTION_FAILURE=12],orderNo:{}", record.getOrderNo());
			} else {
				logger.info("exec cancleRegister is failure.status change [STATE_EXCEPTION_FAILURE=12],orderNo:{} , reason:{}",
						record.getOrderNo(), JSON.toJSONString(interfaceResMap));
				handleCancleException(record);
			}
		}

	}

	/**
	 * 已支付状态下取消挂号->第3方退费成功后提交his确认异常
	 * 
	 * @param record
	 * @return
	 * @throws SystemException
	 */
	private void handleRefundHisCommitException(ExceptionRecord record) throws SystemException {

		Map<String, Object> interfaceResMap = new HashMap<String, Object>();
		String hospitalCode = record.getHospitalCode();
		String branchCode = record.getBranchHospitalCode();

		RefundRegRequest refundRequet = record.covertRefundRegRequestVo();
		Integer regType = record.getRegType();
		if (regType == null) {
			regType = RegisterConstant.REG_TYPE_APPOINTMENT;
		}
		if (regType.intValue() == RegisterConstant.REG_TYPE_CUR) {
			interfaceResMap = registerBizManager.refundCurReg(hospitalCode, branchCode, refundRequet);
		} else {
			interfaceResMap = registerBizManager.refundReg(hospitalCode, branchCode, refundRequet);
		}

		Boolean isExcetion = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);

		if (isExcetion) {
			record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND_HIS_COMMIT);
			record.setPayStatus(OrderConstant.STATE_REFUND);
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
			record.addLogInfo("支付回调->开启子线程退费->第三方退费成功后调用HIS预约退费异常,状态不变[STATE_EXCEPTION_REFUND_HIS_COMMIT=11]");
			record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
			record.setHandleCount(0);
		} else {
			String resCode = interfaceResMap.get(BizConstant.INTERFACE_MAP_CODE_KEY).toString();
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(resCode)) {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL);
				record.setPayStatus(OrderConstant.STATE_REFUND);
				record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
				record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
				record.addLogInfo("支付回调->开启子线程退费->第三方退费成功后->调用HIS预约退费成功,状态变更为[STATE_EXCEPTION_CANCEL=13]");

			} else if (BizConstant.INTERFACE_RES_SUCCESS_HIS_INVALID.equalsIgnoreCase(resCode)) {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_NEED_HOSPITAL_HANDLE);
				record.setPayStatus(OrderConstant.STATE_REFUND);
				record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
				record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
				record.addLogInfo("支付回调->开启子线程退费->第三方退费成功后->调用HIS预约退费因医院限制,退费失败,状态变更为[STATE_EXCEPTION_NEED_HOSPITAL_HANDLE=15]");
			} else {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND_HIS_COMMIT);
				record.setPayStatus(OrderConstant.STATE_REFUND);
				record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
				record.addLogInfo("支付回调->开启子线程退费->第三方退费成功后->调用HIS预约退费失败,原因未知,状态不变[STATE_EXCEPTION_REFUND_HIS_COMMIT=11]");
				record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
				record.setHandleCount(0);
			}
		}
		registerSvc.updateExceptionRecord(record);
		if (record.getIsHandleSuccess() == BizConstant.HANDLED_FAILURE) {
			//			exceptionCache.setExceptionRegisterToCache(record);
			List<Object> params = new ArrayList<Object>();
			params.add(record);
			serveComm.set(CacheType.REGISTER_EXCEPTION_CACHE, "setExceptionRegisterToCache", params);
		}
	}

	/**
	 * 第3方退费出现异常处理
	 * @param record
	 */
	public void handleHisException(RegisterRecord record) {
		record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
		record.setHandleCount(0);
		record.addLogInfo("调用第3方交易平台退费接口退费异常,状态变更为第3方交易平台退费异常[STATE_EXCEPTION_REFUND=10];");
		registerSvc.updateExceptionRecord(record.convertExceptionObj());
		//		recordCache.updateRecordTradeInfoToCache(record);
		List<Object> params = new ArrayList<Object>();
		params.add(record);
		serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordTradeInfoToCache", params);

		//		exceptionCahce.setAgtRefundExceptionToCache(record.convertExceptionObj());
		params.add(record.convertExceptionObj());
		serveComm.set(CacheType.REGISTER_EXCEPTION_CACHE, "setAgtRefundExceptionToCache", params);
	}

	/**
	 * his解锁异常出现异常处理
	 * @param record
	 */
	public void handleCancleException(RegisterRecord record) {
		record.setRegStatus(RegisterConstant.STATE_EXCEPTION_FAILURE);
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
		record.setHandleCount(1);
		record.addLogInfo("调用HIS取消号源(解锁)失败,状态变更为挂号失败[STATE_EXCEPTION_FAILURE=12];");
		registerSvc.updateExceptionRecord(record.convertExceptionObj());
		//exceptionCahce.setExceptionRegisterToCache(record.convertExceptionObj());
	}

	/**
	 * 根据his订单添加信息到平台挂号记录中
	 * @param record
	 * @param hisRecord
	 */
	private void addHisOrderInfo(RegisterRecord record, RegRecord hisRecord) {
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

	public RegisterRecord getRecord() {
		return record;
	}

	public void setRecord(RegisterRecord record) {
		this.record = record;
	}

	public Boolean getIsNeedCancleReg() {
		return isNeedCancleReg;
	}

	public void setIsNeedCancleReg(Boolean isNeedCancleReg) {
		this.isNeedCancleReg = isNeedCancleReg;
	}

}
