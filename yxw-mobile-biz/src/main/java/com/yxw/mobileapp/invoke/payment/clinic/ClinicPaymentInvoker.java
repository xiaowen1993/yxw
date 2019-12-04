/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年8月14日
 * @version 1.0
 */
package com.yxw.mobileapp.invoke.payment.clinic;

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
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.mobile.biz.vote.VoteInfo;
import com.yxw.commons.entity.platform.payrefund.PayAsynResponse;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.clinicpay.AckPayOrder;
import com.yxw.interfaces.vo.clinicpay.PayFee;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;
import com.yxw.mobileapp.biz.clinic.thread.SaveAndUpdateClinicCacheRunnable;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.vote.service.VoteService;
import com.yxw.mobileapp.datas.manager.ClinicBizManager;
import com.yxw.mobileapp.invoke.thread.ClinicRefundRunnable;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年8月14日  
 */
public class ClinicPaymentInvoker {

	private static final String DEF_CLINIC_FAILURE_MSG = "不符合医院限定，无法完成改门诊订单的支付。";
	private static Logger logger = LoggerFactory.getLogger(ClinicPaymentInvoker.class);
	private static CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
	private static VoteService voteService = SpringContextHolder.getBean(VoteService.class);

	// 门诊回调
	private static ClinicBizManager clinicBizManager = SpringContextHolder.getBean(ClinicBizManager.class);
	private static ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);

	//回调加锁
	private static ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/** 
	 * 门诊回调具体业务处理
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月20日 
	 * @param payAsynResponse 
	 */
	public static void clinicPayment(Object payAsynResponse) {

		if (logger.isDebugEnabled()) {
			logger.debug("clinicPayment invoke payAsynResponse: {}", new Object[] { JSON.toJSONString(payAsynResponse) });
		}

		PayAsynResponse payAsynRsp = (PayAsynResponse) payAsynResponse;

		ClinicRecord record = clinicService.findRecordByOrderNo(payAsynRsp.getOrderNo());

		List<Object> cacheParams = new ArrayList<Object>();

		boolean hasLock = false;
		cacheParams.add(payAsynRsp.getOrderNo());
		List<Object> regLock = serveComm.get(CacheType.RE_INVOKE_LOCK_CACHE, "getRegLock", cacheParams);//回调加锁
		if (!CollectionUtils.isEmpty(regLock)) {
			hasLock = (Boolean) regLock.get(0);
		}

		//重复回调  不处理
		if ( ( record.getIsHadCallBack() != null && record.getIsHadCallBack().intValue() == BizConstant.IS_HAD_CALLBACK_YES ) || !hasLock) {
			logger.info("pay invoke duplicate ...orderNo: [{}], return with do nothing.", payAsynRsp.getOrderNo());
			return;
		}

		record.setIsHadCallBack(BizConstant.IS_HAD_CALLBACK_YES);
		if (StringUtils.equals(payAsynRsp.getTradeState(), TradeConstant.TRADE_STATE_EXCEPTION)) {//异常
			// 支付失败，不需要调用接口，只要把数据写入缓存即可
			// 交易失败，则关闭
			record.setPayStatus(OrderConstant.STATE_CLOSE);
			// 记录错误信息
			record.setFailureMsg(payAsynRsp.getResultMsg());
			// 没有发生异常
			record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
			// 是否处理成功：处理失败了
			record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
			String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
			StringBuilder sbLog = new StringBuilder();
			sbLog.append("HandleCount:" + record.getHandleCount());
			sbLog.append(",HandleDate:".concat(BizConstant.YYYYMMDDHHMMSS.format(new Date())));
			sbLog.append(",HandleMsg:第三方支付失败，订单状态还是未支付。");
			record.setHandleLog(handleLog.concat(sbLog.toString()));
			// 数据写入缓存
			Thread cacheThread =
					new Thread(new SaveAndUpdateClinicCacheRunnable(record, CacheConstant.CACHE_OP_UPDATE),
							"CacheRunnable.updateClinicRecord");
			cacheThread.start();
			// 数据更新到数据库中
			clinicService.updateOrderPayInfo(record);
		} else {
			// 是否开启退费线程
			boolean isStartRefundThread = false;
			long currentTime = System.currentTimeMillis();
			// 记录第三方支付流水号
			record.setAgtOrdNum(payAsynRsp.getAgtOrderNo());
			// 设定完成支付的时间
			record.setPayTime(currentTime);
			// 更新时间
			record.setUpdateTime(currentTime);
			String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
			StringBuilder sbLog = new StringBuilder();
			sbLog.append(",HandleDate:".concat(BizConstant.YYYYMMDDHHMMSS.format(new Date())));
			sbLog.append(",HandleMsg:第三方支付成功。");
			record.setHandleLog(handleLog.concat(sbLog.toString()));

			// 写入His
			Map<String, String> params = generatePayClinicOrderParams(record);
			Map<String, Object> resultMap = clinicBizManager.payOrder(params);

			logger.info("order[{}], 调用his门诊缴费接口返回结果: {}", new Object[] { record.getOrderNo(), JSON.toJSONString(resultMap) });

			//支付后与his确认是否出现异常
			boolean isException = (Boolean) resultMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
			if (isException) {
				record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_HIS);
				record.setPayStatus(OrderConstant.STATE_PAYMENT);
				handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
				sbLog = new StringBuilder();
				sbLog.append("HandleDate:".concat(BizConstant.YYYYMMDDHHMMSS.format(new Date())).concat(","));
				sbLog.append("HandleMsg:第3方支付成功后调用医院支付确认接口异常,状态变更为医院支付确认异常[STATE_PAYMENT=2],进入轮询流程;");
				record.setHandleLog(handleLog.concat(sbLog.toString()));
				record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
				record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
				clinicService.updateOrderPayInfo(record);
				logger.info("pay clinic order exception. the order[{}] will be put into the clinic order exception queue.",
						record.getOrderNo());

				/*
				 * 发送异常消息
				 */
				commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_CLINIC_PAY_EXP);

			} else {
				boolean isSuccess = (Boolean) resultMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
				if (isSuccess) {
					// 添加订单号、流水号等
					record = addClinicInfo(record, resultMap.get(BizConstant.INTERFACE_MAP_DATA_KEY));

					record.setPayStatus(OrderConstant.STATE_PAYMENT);
					record.setClinicStatus(ClinicConstant.STATE_PAY_SUCCESS);
					record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
					handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					sbLog = new StringBuilder();
					sbLog.append(",HandleDate:".concat(BizConstant.YYYYMMDDHHMMSS.format(new Date())));
					sbLog.append(",HandleMsg: 缴费成功，无异常。");
					record.setHandleLog(handleLog.concat(sbLog.toString()));

					clinicService.updateOrderPayInfo(record);

					/** 门诊缴费成功 消息推送   **/
					// clinicRecord 里面的消息写好。
					logger.info("订单{}支付成功...不需要查询状态...", record.getOrderNo());
					commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_CLINIC_PAY_SUCCESS);

					try {
						VoteInfo voteInfo =
								voteService.generateVoteInfo(record.getAppId(), record.getAppCode(),
										String.valueOf(BizConstant.BIZ_TYPE_CLINIC), record.getOrderNo(), record.getCardNo(),
										record.getOpenId(), record.getDeptName(), "",
										record.getDeptName().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(record.getRecordTitle()),
										record.getPatientName());
						voteInfo.setRecord(record);
						commonMsgPushSvc.pushMsg(voteInfo, MessageConstant.ACTION_TYPE_CLINIC_SUCCESS_COMMENT);
					} catch (Exception e) {
						logger.error("门诊缴费成功，推送就诊评价消息异常. sent clinic comment error. openId: {}, orderNo: {}, errorMsg: {}, cause: {},",
								new Object[] { record.getOpenId(), record.getOrderNo(), e.getMessage(), e.getCause() });
					}
				} else {
					record.setClinicStatus(ClinicConstant.STATE_FAIL_HIS); // 门诊订单状态，支付失败
					record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO); // 无异常
					record.setPayStatus(OrderConstant.STATE_REFUNDING); // 支付状态，要退费
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS); // 处理成功
					handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					sbLog = new StringBuilder();
					sbLog.append(",HandleDate:".concat(BizConstant.YYYYMMDDHHMMSS.format(new Date())));
					sbLog.append(",HandleMsg:缴费明确返回失败，进入退费流程");
					record.setHandleLog(handleLog.concat(sbLog.toString()));

					Object msgObj = resultMap.get(BizConstant.INTERFACE_MAP_MSG_KEY);
					if (msgObj == null) {
						record.setHisMessage(DEF_CLINIC_FAILURE_MSG);
						record.setHandleLog(record.getHandleLog().concat("[").concat(BizConstant.YYYYMMDDHHMMSS.format(new Date()))
								.concat("],").concat(DEF_CLINIC_FAILURE_MSG));
						record.setFailureMsg(DEF_CLINIC_FAILURE_MSG);
					} else {
						record.setHisMessage(msgObj.toString());
						record.setHandleLog(record.getHandleLog().concat("[").concat(BizConstant.YYYYMMDDHHMMSS.format(new Date()))
								.concat("],").concat(msgObj.toString()));
						record.setFailureMsg(msgObj.toString());
						//logger.info("failure info : {}", msgObj.toString());
					}
					clinicService.updateOrderPayInfo(record);
					logger.info("订单{}支付失败...不需要查询状态.需要开启退费子线程...", record.getOrderNo());
					/** 门诊缴费失败 消息推送 **/
					commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_CLINIC_PAY_FAIL);
					//进入退费子线程
					isStartRefundThread = true;
				}
			}

			if (isStartRefundThread) {
				logger.info("门诊支付成功后与医院确认异常 : 转入退费流程 {}", record.getOrderNo());
				Thread refundThread = new Thread(new ClinicRefundRunnable(record));
				refundThread.start();
			}
		}

		// 释放锁
		cacheParams.clear();
		cacheParams.add(payAsynRsp.getOrderNo());
		serveComm.delete(CacheType.RE_INVOKE_LOCK_CACHE, "delRegLock", cacheParams);

	}

	private static Map<String, String> generatePayClinicOrderParams(ClinicRecord record) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			map.put("hospitalCode", record.getHospitalCode());
			map.put("branchHospitalCode", record.getBranchHospitalCode());
			map.put("patCardType", record.getCardType().toString());
			map.put("patCardNo", record.getCardNo().toString());
			map.put("mzFeeIdList", record.getMzFeeId());
			map.put("payAmout", record.getPayFee());
			map.put("totalAmout", record.getTotalFee());
			map.put("psOrdNum", record.getOrderNo());
			map.put("agtOrdNum", record.getAgtOrdNum());
			map.put("agtCode", ""); // 没有收单机构代码
			map.put("payTime", BizConstant.YYYYMMDDHHMMSS.format(new Date(record.getPayTime())));
			map.put("payMode", record.getTradeMode().toString());
		} catch (Exception e) {
			logger.error("构建门诊缴费支付参数缺失. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}
		return map;
	}

	private static ClinicRecord addClinicInfo(ClinicRecord record, Object payObj) {
		if (payObj != null) {
			if (payObj instanceof AckPayOrder) {
				AckPayOrder payOrder = (AckPayOrder) payObj;
				if (StringUtils.isNotBlank(payOrder.getBarCode())) {
					record.setBarcode(payOrder.getBarCode());
				}
				if (StringUtils.isNotBlank(payOrder.getHisMessage())) {
					record.setHisMessage(payOrder.getHisMessage());
				}
				if (StringUtils.isNoneBlank(payOrder.getHisOrdNum())) {
					record.setHisOrdNo(payOrder.getHisOrdNum());
				}
				if (StringUtils.isNoneBlank(payOrder.getReceiptNum())) {
					record.setReceiptNum(payOrder.getReceiptNum());
				}
			} else if (payObj instanceof PayFee) {
				PayFee payOrder = (PayFee) payObj;
				if (StringUtils.isNotBlank(payOrder.getBarCode())) {
					record.setBarcode(payOrder.getBarCode());
				}
				if (StringUtils.isNotBlank(payOrder.getHisMessage())) {
					record.setHisMessage(payOrder.getHisMessage());
				}
				if (StringUtils.isNoneBlank(payOrder.getHisOrdNum())) {
					record.setHisOrdNo(payOrder.getHisOrdNum());
				}
				if (StringUtils.isNoneBlank(payOrder.getReceiptNum())) {
					record.setReceiptNum(payOrder.getReceiptNum());
				}
			}
		}

		return record;
	}

}
