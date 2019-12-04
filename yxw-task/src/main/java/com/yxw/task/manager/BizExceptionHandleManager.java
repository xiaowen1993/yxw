/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-5-24</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.task.manager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.entity.platform.payrefund.AlipayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.AlipayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.AlipayRefund;
import com.yxw.commons.entity.platform.payrefund.AlipayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.OrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.RefundResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefund;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefundResponse;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;
import com.yxw.interfaces.vo.register.RegRecord;
import com.yxw.interfaces.vo.register.appointment.RefundRegRequest;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;
import com.yxw.payrefund.service.QueryService;
import com.yxw.payrefund.service.RefundService;
import com.yxw.payrefund.utils.TradeCommonHoder;
import com.yxw.task.taskitem.HandleRegisterExceptionTask;

/**
 * @Package: com.yxw.platform.register.service
 * @ClassName: RegisterExceptionManager
 * @Statement: <p>业务异常处理管理</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-24
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class BizExceptionHandleManager {
	private RegisterService recordService = SpringContextHolder.getBean(RegisterService.class);
	private RegisterBizManager bizManager = SpringContextHolder.getBean(RegisterBizManager.class);
	private BaseDatasManager baseDateManager = SpringContextHolder.getBean(BaseDatasManager.class);
	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	private static final String IS_VALID_KEY = "isValid";
	private static final String RECORD_KEY = "record";
	private static final String HIS_RECORD_KEY = "hisRecord";

	/**
	 * 处理挂号异常
	 * @param record
	 * return     
	 */
	public ExceptionRecord handleRegisterException(ExceptionRecord record) throws SystemException {
		HandleRegisterExceptionTask.logger.info("订单号:{}, regStatus:{}, payStatus:{} ,had exec times:{}, exception  handle start.........",
				new Object[] { record.getOrderNo(), record.getRegStatus(), record.getPayStatus(), record.getExecCount() });
		int state = record.getRegStatus();
		if (record.getRegType() == null) {
			record.setRegType(RegisterConstant.REG_TYPE_APPOINTMENT);
		}
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		record.setHandleCount(record.getHandleCount() + 1);
		switch (state) {
		// 预约异常(his锁号异常)
		case RegisterConstant.STATE_EXCEPTION_HAVING:
			record = handleLockException(record);
			break;
		// 第3方支付异常
		case RegisterConstant.STATE_EXCEPTION_PAY:
			record = handlePayException(record);
			break;
		// 第3方支付成功后 his确认异常
		case RegisterConstant.STATE_EXCEPTION_PAY_HIS_COMMIT:
			record = handlePayHisCommitException(record);
			break;
		// 未支付状态取消挂号异常
		case RegisterConstant.STATE_EXCEPTION_CANCEL_EXCEPTION:
			record = handleCancelNotPayMentException(record);
			break;
		// 已支付状态下取消挂号-> his预约退费确认异常
		case RegisterConstant.STATE_EXCEPTION_REFUND_HIS_CONFIRM:
			record = handleRefundHisConfirmException(record);
			break;
		//已支付状态下取消挂号->第3方退费异常 
		case RegisterConstant.STATE_EXCEPTION_REFUND:
			record = handleRefundException(record);
			break;
		//已支付状态下取消挂号->第3方退费成功后提交his确认异常
		case RegisterConstant.STATE_EXCEPTION_REFUND_HIS_COMMIT:
			record = handleRefundHisCommitException(record);
			break;
		// (停诊)已支付状态下取消挂号->第3方退费成功后提交his确认异常
		case RegisterConstant.STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT:
			record = handleStopRegisterRefundHisCommitException(record);
			break;
		default:
			record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
			break;
		}
		//判断下次是否满足异常处理条件
		isCheckHandleValid(record);
		recordService.updateExceptionRecord(record);

		List<Object> params = new ArrayList<Object>();
		params.add(record);
		serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateExceptionRecordsStatusToCache", params);

		HandleRegisterExceptionTask.logger.info("订单号:{}, handleRegisterException end ", record.getOrderNo());
		return record;
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月28日 
	 * @param record
	 * @return 
	 */

	/**
	 * (停诊)已支付状态下取消挂号->第3方退费成功后提交his确认异常
	 * 
	 * @param record
	 * @return
	 * @throws SystemException
	 */
	private ExceptionRecord handleStopRegisterRefundHisCommitException(ExceptionRecord record) throws SystemException {

		Map<String, Object> interfaceResMap = new HashMap<String, Object>();
		String hospitalCode = record.getHospitalCode();
		String branchCode = record.getBranchHospitalCode();
		RefundRegRequest refundRequet = record.covertRefundRegRequestVo();
		Integer regType = record.getRegType();
		if (regType == null) {
			regType = RegisterConstant.REG_TYPE_APPOINTMENT;
		}
		if (regType.intValue() == RegisterConstant.REG_TYPE_CUR) {
			interfaceResMap = bizManager.refundCurReg(hospitalCode, branchCode, refundRequet);
		} else {
			interfaceResMap = bizManager.refundReg(hospitalCode, branchCode, refundRequet);
		}
		Boolean isExcetion = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
		if (isExcetion) {
			record.setRegStatus(RegisterConstant.STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT);
			record.setPayStatus(OrderConstant.STATE_REFUND);
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
			record.addLogInfo("调用HIS预约退费异常,状态不变[STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT=19];");
			record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
		} else {
			String resCode = interfaceResMap.get(BizConstant.INTERFACE_MAP_CODE_KEY).toString();
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(resCode)) {
				record.setRegStatus(RegisterConstant.STATE_NORMAL_STOP_CURE_CANCEL);
				record.setPayStatus(OrderConstant.STATE_REFUND);
				record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
				record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
				record.addLogInfo("HandleMsg:调用HIS退费成功,停诊成功,状态变更为[STATE_NORMAL_STOP_CURE_CANCEL=4];");
				record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);

				List<Object> params = new ArrayList<Object>();
				params.add(record.getOrderNo());
				serveComm.delete(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "delStopRegOrdersByOrderNo", params);

			} else if (BizConstant.INTERFACE_RES_SUCCESS_HIS_INVALID.equalsIgnoreCase(resCode)) {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_NEED_HOSPITAL_HANDLE);
				record.setPayStatus(OrderConstant.STATE_REFUND);
				record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
				record.addLogInfo("调用HIS预约退费因医院限制,退费失败,状态变更为[STATE_EXCEPTION_NEED_HOSPITAL_HANDLE=15];");
				record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
			} else {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT);
				record.setPayStatus(OrderConstant.STATE_REFUND);
				record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
				record.addLogInfo("调用HIS预约退费失败,原因未知,状态不变[STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT=19];");
				record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
			}
		}
		return record;
	}

	private Map<String, Object> isCheckHandleValid(ExceptionRecord record) {
		Boolean isValid = true;
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (record.getHandleCount() >= 3) {
			record.setRegStatus(RegisterConstant.STATE_EXCEPTION_NEED_PERSON_HANDLE);
			record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
			record.setRegStatus(RegisterConstant.STATE_EXCEPTION_NEED_PERSON_HANDLE);
			String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
			StringBuilder sbLog = new StringBuilder();
			sbLog.append(handleLog);
			sbLog.append("HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + ",");
			sbLog.append("HandleMsg:经系统轮询,处理最大次数超过3次,移除异常队列!状态更新为人工处理(STATE_EXCEPTION_NEED_PERSON_HANDLE);");
			record.setHandleLog(sbLog.toString());

			recordService.updateExceptionRecord(record);
			isValid = false;
		}
		resMap.put(IS_VALID_KEY, isValid);
		resMap.put(RECORD_KEY, record);
		return resMap;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> handleQueryHisRecord(ExceptionRecord record) {// 查询his是否生成订单
		List<RegRecord> records = null;
		Map<String, Object> interfaceResMap = bizManager.queryRegisterRecords(record.convertRecord());
		Boolean isHappenException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
		String resultCode = interfaceResMap.get(BizConstant.INTERFACE_MAP_CODE_KEY).toString();

		RegRecord hisRecord = null;
		if (isHappenException) {
			HandleRegisterExceptionTask.logger.info("exception type:{} ,订单号:{} , 查询订单记录异常",
					new Object[] { record.getRegStatus(), record.getOrderNo() });
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
			record.addLogInfo("调用HIS订单查询接口网络异常,异常状态不变[" + record.getRegStatus() + "];");
		} else {
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(resultCode)) {
				records = (List<RegRecord>) interfaceResMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
				if (!CollectionUtils.isEmpty(records)) {
					hisRecord = records.get(0);
					HandleRegisterExceptionTask.logger.info("exception type:{} ,订单号:{} , his订单信息:{}", new Object[] { record.getRegStatus(),
							record.getOrderNo(),
							JSON.toJSONString(hisRecord) });
				} else {
					// 订单不存在
					HandleRegisterExceptionTask.logger.info("exception type:{} ,订单号:{} , his订单不存在  移除异常缓存队列",
							new Object[] { record.getRegStatus(), record.getOrderNo() });
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
					record.addLogInfo("调用HIS订单查询接口,查询his是否生成订单时,未生成订单");
				}
			} else if (BizConstant.INTERFACE_RES_SUCCESS_NO_DATA_CODE.equalsIgnoreCase(resultCode)) {
				HandleRegisterExceptionTask.logger.info("exception type:{} ,订单号:{} , his订单不存在  移除异常缓存队列",
						new Object[] { record.getRegStatus(), record.getOrderNo() });
				record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
				record.addLogInfo("调用HIS订单查询接口,查询his是否生成订单时,未生成订单");
				// 订单不存在 移除异常缓存队列
			} else {
				HandleRegisterExceptionTask.logger
						.info("exception type:{} ,订单号:{} , 查询his订单异常, msg:{}", new Object[] { record.getRegStatus(),
								record.getOrderNo(),
								interfaceResMap.get(BizConstant.INTERFACE_MAP_MSG_KEY) });
				record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
				record.addLogInfo("调用HIS订单查询接口查询失败,状态不变[" + record.getRegStatus() + "];");
			}
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RECORD_KEY, record);
		resMap.put(HIS_RECORD_KEY, hisRecord);
		resMap.put("isHappenException", isHappenException);
		resMap.put("resultCode", resultCode);
		return resMap;
	}

	private ExceptionRecord handleCancelRecord(ExceptionRecord record, RegRecord hisRecord) {
		Map<String, Object> interfaceResMap = null;
		if (RegisterConstant.HIS_STATUS_NO_PAYMENT.equalsIgnoreCase(hisRecord.getStatus())) {
			//已产生订单  需要解锁
			if (RegisterConstant.REG_TYPE_CUR == record.getRegType().intValue()) {
				interfaceResMap =
						bizManager.cancelCurRegister(record.getHospitalCode(), record.getBranchHospitalCode(), record.getPlatformMode()
								.toString(), record.getHisOrdNo(), record.getOrderNo().toString());// TODO 是否兼容医院不修改接口
			} else {
				interfaceResMap =
						bizManager.cancelRegister(record.getHospitalCode(), record.getBranchHospitalCode(), record.getPlatformMode()
								.toString(), record.getHisOrdNo(), record.getOrderNo().toString());// TODO 是否兼容医院不修改接口
			}

			boolean isHappenException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
			String resCode = interfaceResMap.get(BizConstant.INTERFACE_MAP_CODE_KEY).toString();

			if (isHappenException) {
				HandleRegisterExceptionTask.logger.info("handleCancelRecord ->订单号:{} 异常,msg：{}", new Object[] { record.getOrderNo(),
						interfaceResMap.get(BizConstant.INTERFACE_MAP_MSG_KEY) });
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_HAVING);
				record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
				String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
				StringBuilder sbLog = new StringBuilder();
				sbLog.append("HandleCount:" + record.getHandleCount());
				sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
				sbLog.append(",HandleMsg:调用HIS取消挂号接口网络异常,状态[STATE_EXCEPTION_HAVING=5]预约异常(his锁号异常);");
				record.setHandleLog(handleLog + sbLog.toString());
			} else {
				if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(resCode)) {
					HandleRegisterExceptionTask.logger.info("handleCancelRecord ->订单号:{} 执行成功.", record.getOrderNo());
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL);
					record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);

					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:调用HIS取消挂号接口成功,状态变更为[STATE_EXCEPTION_CANCEL=13]取消挂号;");
					record.setHandleLog(handleLog + sbLog.toString());
					record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
					recordService.updateExceptionRecord(record);
				} else if (BizConstant.INTERFACE_RES_SUCCESS_INVALID_DATA_CODE.equalsIgnoreCase(resCode)) {
					HandleRegisterExceptionTask.logger.info("handleCancelRecord ->订单号:{} 执行失败,msg.", new Object[] { record.getOrderNo(),
							interfaceResMap.get(BizConstant.INTERFACE_MAP_MSG_KEY) });
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_NEED_HOSPITAL_HANDLE);
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:调用HIS取消挂号成功,但因医院限制,无法解锁,状态变更为[STATE_EXCEPTION_NEED_HOSPITAL_HANDLE=2]");
					record.setHandleLog(handleLog + sbLog.toString());
					record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
					recordService.updateExceptionRecord(record);
				} else {
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_HAVING);
					record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:调用HIS取消挂号失败,状态未变[STATE_EXCEPTION_HAVING=2]");
					record.setHandleLog(handleLog + sbLog.toString());
					record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
				}
			}
		} else {
			if (RegisterConstant.HIS_STATUS_PERSON_CANCEL.equalsIgnoreCase(hisRecord.getStatus())) {
				record.setRegStatus(RegisterConstant.STATE_NORMAL_USER_CANCEL);
			} else if (RegisterConstant.HIS_STATUS_PAYMENT_TIME_OUT.equalsIgnoreCase(hisRecord.getStatus())) {
				record.setRegStatus(RegisterConstant.STATE_NORMAL_PAY_TIMEOUT_CANCEL);
			} else if (RegisterConstant.HIS_STATUS_HAD_REFUND.equalsIgnoreCase(hisRecord.getStatus())) {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL);
				record.setPayStatus(OrderConstant.STATE_REFUND);
			} else {
				record.setRegStatus(RegisterConstant.STATE_NORMAL_HAD);
			}
			record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
			String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
			StringBuilder sbLog = new StringBuilder();
			sbLog.append("HandleCount:" + record.getHandleCount());
			sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
			sbLog.append(",HandleMsg:经系统轮询处理,异常状态[STATE_EXCEPTION_HAVING,预约异常(his锁号异常)],通过his接口同步为." + hisRecord.getStatus() + ";");
			record.setHandleLog(handleLog + sbLog.toString());
			recordService.updateExceptionRecord(record);
		}
		return record;
	}

	/**
	 * 处理锁号时 产生的异常
	 * 处理逻辑  是否生成订单,生成,取消
	 * @param record
	 * @param isNeedQuery  是否需要查询his订单
	 * @return  
	 * @throws SystemException
	 */
	private ExceptionRecord handleLockException(ExceptionRecord record) throws SystemException {
		//检查是否处理次数满3次
		Map<String, Object> resMap = isCheckHandleValid(record);
		Boolean isValid = (Boolean) resMap.get(IS_VALID_KEY);
		if (!isValid) {
			return record;
		} else {
			record.setHandleCount(record.getHandleCount() + 1);
			record = (ExceptionRecord) resMap.get(RECORD_KEY);
			resMap = handleQueryHisRecord(record);
			record = (ExceptionRecord) resMap.get(RECORD_KEY);

			RegRecord hisRecord = (RegRecord) resMap.get(HIS_RECORD_KEY);
			if (hisRecord != null) {
				record = handleCancelRecord(record, hisRecord);
			}
			return record;
		}

	}

	/**
	 * 处理支付时 产生的异常
	 */
	private ExceptionRecord handlePayException(ExceptionRecord record) throws SystemException {
		Map<String, Object> resMap = queryPayRecordStatus(record);
		boolean isException = (Boolean) resMap.get("isException");

		if (!isException) {
			OrderQueryResponse orderInfoResponse = (OrderQueryResponse) resMap.get("orderResponse");
			if (StringUtils.equals(orderInfoResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
				// 未支付 转入取消号源
				//				if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == OrderConstant.STATE_NO_PAYMENT) {
				if (StringUtils.equals(orderInfoResponse.getTradeState(), TradeConstant.TRADE_STATE_NOTPAY)) {

					HandleRegisterExceptionTask.logger.info(
							"ExceptionRecord ->订单号:{},regstauts:{},paySatuts:{}  query trade WechatPay. pay's tradeState:{} . 进入解锁流程",
							new Object[] { record.getOrderNo(),
									record.getRegStatus(),
									record.getPayStatus(),
									orderInfoResponse.getTradeState() });
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL_EXCEPTION);
					record.setPayStatus(OrderConstant.STATE_NO_PAYMENT);
					record.addLogInfo("第三方支付异常处理：查询第三方平台发现订单未支付成功，调用his接口取消挂号");
					// 转入取消号源处理
					return cancelNotPayMentRecord(record);
				}
				/**
				 * 已支付 先调用his订单查询接口判断his的订单是否已经支付 
				 * 1.若his订单已支付则不做退费操作,订单标志为预约成功已支付
				 * 		regStatus=STATE_NORMAL_HAD[1: 已预约] payStatus=STATE_PAYMENT[2:已支付]
				 * 2.若his订单未支付 则进行退费
				 */
				//			else if (String.valueOf(OrderConstant.STATE_PAYMENT).equals(orderState)) {
				//				if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == OrderConstant.STATE_PAYMENT) {
				if (StringUtils.equals(orderInfoResponse.getTradeState(), TradeConstant.TRADE_STATE_SUCCESS)) {

					// 调用his订单查询接口
					Map<String, Object> hisRecordMap = handleQueryHisRecord(record);
					RegRecord hisRecord = (RegRecord) hisRecordMap.get(HIS_RECORD_KEY);
					Boolean isHappenException = (Boolean) hisRecordMap.get("isHappenException");
					String resultCode = (String) hisRecordMap.get("resultCode");

					if (!isHappenException) {

						/******************************************	未查询到数据	*******************************************/

						if (BizConstant.INTERFACE_RES_SUCCESS_NO_DATA_CODE.equalsIgnoreCase(resultCode)) {

							/****************************** 有的医院未支付是查不到订单的 针对这种情况需要退费   ****************************************/
							RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(record.getHospitalCode());
							Integer canQueryUnpaidRecord = ruleRegister.getCanQueryUnpaidRecord();

							if (canQueryUnpaidRecord == BizConstant.QUERY_UNPAID_RECORD_NO) {
								HandleRegisterExceptionTask.logger.info(
										"handlePayException ->订单号:{} ,查询his接口未查询到数据，进入退费流程。【有的医院未支付是查不到订单的 针对这种情况需要退费 】",
										record.getOrderNo());
								if (record.getIsMedicarePayMents() != null
										&& record.getIsMedicarePayMents() == BizConstant.BASEDON_MEDICAL_INSURANCE_YES) {
									//								return agtRefundMedicare(record);
								} else {
									return agtRefund(record);
								}
							}
							/****************************** 有的医院未支付可以查到订单，这种未查到的情况可能是his那边还在处理，订单还未生成   ****************************************/
							else {
								boolean autoRefund = ifOverRefundDeadline(record);
								if (autoRefund) {
									// 第3方退费
									record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
									record.setPayStatus(OrderConstant.STATE_REFUNDING);
									// 进入退费流程
									HandleRegisterExceptionTask.logger.info(
											"handlePayException ->订单号:{} ,根据规则配置的退费延时，已经达到退费时间点仍然无法确认订单的状态，接口无异常，进入退费流程。",
											record.getOrderNo());
									if (record.getIsMedicarePayMents() != null
											&& record.getIsMedicarePayMents() == BizConstant.BASEDON_MEDICAL_INSURANCE_YES) {
										//									return agtRefundMedicare(record);
									} else {
										return agtRefund(record);
									}
									//commonMsgPushSvc.pushMsg(record, CommonMsgPushService.ACTION_TYPE_APPOINT_PAY_FAIL);
								} else {
									HandleRegisterExceptionTask.logger
											.info("ExceptionRecord ->订单号:{},regstauts:{},paySatuts:{},查询第三方平台的订单状态显示-->该订单为已支付，在调用his订单查询接口查询该订单在his平台的状态时出现异常，返回的hisRecord为空 ",
													new Object[] { record.getOrderNo(), record.getRegStatus(), record.getPayStatus() });
									record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
									record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY);
									record.addLogInfo("调用his订单查询接口,未查询到his订单的确定状态,状态未变[STATE_EXCEPTION_PAY=6]");
									record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
								}
							}
						}
						/******************************************	返回集合中无订单	*******************************************/
						else {
							if (hisRecord == null) {
								boolean autoRefund = ifOverRefundDeadline(record);
								if (autoRefund) {
									// 第3方退费
									record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
									record.setPayStatus(OrderConstant.STATE_REFUNDING);
									// 进入退费流程
									HandleRegisterExceptionTask.logger.info(
											"handlePayException ->订单号:{} ,根据规则配置的退费延时，已经达到退费时间点仍然无法确认订单的状态，接口无异常，进入退费流程。",
											record.getOrderNo());
									if (record.getIsMedicarePayMents() != null
											&& record.getIsMedicarePayMents() == BizConstant.BASEDON_MEDICAL_INSURANCE_YES) {
										//									return agtRefundMedicare(record);
									} else {
										return agtRefund(record);
									}
									//commonMsgPushSvc.pushMsg(record, CommonMsgPushService.ACTION_TYPE_APPOINT_PAY_FAIL);
								} else {
									HandleRegisterExceptionTask.logger
											.info("ExceptionRecord ->订单号:{},regstauts:{},paySatuts:{},查询第三方平台的订单状态显示-->该订单为已支付，在调用his订单查询接口查询该订单在his平台的状态时出现异常，返回的hisRecord为空 ",
													new Object[] { record.getOrderNo(), record.getRegStatus(), record.getPayStatus() });
									record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
									record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY);
									record.addLogInfo("调用his订单查询接口,未查询到his订单的确定状态,状态未变[STATE_EXCEPTION_PAY=6]");
									record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
								}
							} else {
								String hisPayStatus = hisRecord.getStatus();
								// his状态为已支付时则更新订单状态
								if (hisPayStatus != null
										&& ( hisPayStatus.equals(RegisterConstant.HIS_STATUS_HAD_PAYMENT)
												|| hisPayStatus.equals(RegisterConstant.HIS_STATUS_HAD_TAKENO) || hisPayStatus
													.equals(RegisterConstant.HIS_STATUS_HAD_VISITED) )) {
									HandleRegisterExceptionTask.logger.info(
											"ExceptionRecord ->订单号:{},查询his状态为已支付时则认为该订单在his平台及第三方支付平台都支付成功，更新数据库订单为已预约/已支付",
											new Object[] { record.getOrderNo() });
									record.setRegStatus(RegisterConstant.STATE_NORMAL_HAD);
									record.setPayStatus(OrderConstant.STATE_PAYMENT);
									record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
									record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
									record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
									record.addLogInfo("调用his订单查询接口,查询到his订单状态为已支付,更新数据库订单为已预约/已支付");

								} else if (hisPayStatus != null && hisPayStatus.equals(RegisterConstant.HIS_STATUS_NO_PAYMENT)) {
									// his状态为未支付，转入第3方退费处理
									HandleRegisterExceptionTask.logger
											.info("ExceptionRecord ->订单号:{},regstauts:{},paySatuts:{}  query trade PayWechat. pay's tradeState:{} . 第三方平台状态为已支付，his状态为未支付， 进退费流程.状态变更为退费中[STATE_EXCEPTION_REFUND]",
													new Object[] { record.getOrderNo(),
															record.getRegStatus(),
															record.getPayStatus(),
															orderInfoResponse.getTradeState() });
									record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
									record.setPayStatus(OrderConstant.STATE_REFUNDING);
									record.setAgtOrdNum(orderInfoResponse.getAgtOrderNo());
									try {
										if (StringUtils.isNotBlank(orderInfoResponse.getTradeTime())) {
											record.setPayTime(BizConstant.YYYYMMDDHHMMSS.parse(orderInfoResponse.getTradeTime()).getTime());
										} else {
											HandleRegisterExceptionTask.logger
													.error("payMent success.orderNo:{},tradeTime is null.use systime.");
											record.setPayTime(System.currentTimeMillis());
										}
									} catch (ParseException e) {
										HandleRegisterExceptionTask.logger
												.error("payMent success.orderNo:{},tradeTime:{},Parse tradeTime is error.use systime.");
										record.setPayTime(System.currentTimeMillis());
									}

									if (record.getIsMedicarePayMents() != null
											&& record.getIsMedicarePayMents() == BizConstant.BASEDON_MEDICAL_INSURANCE_YES) {
										//									return agtRefundMedicare(record);
									} else {
										return agtRefund(record);
									}
								}
							}
						}
					} else {

						HandleRegisterExceptionTask.logger
								.info("ExceptionRecord ->订单号:{},regstauts:{},paySatuts:{},查询第三方平台的订单状态显示-->该订单为已支付，在调用his订单查询接口查询该订单在his平台的状态时出现异常 isHappenException=true",
										new Object[] { record.getOrderNo(), record.getRegStatus(), record.getPayStatus() });
						record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
						record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY);
						record.addLogInfo("调用his订单查询接口,未查询到his订单的确定状态,状态未变[STATE_EXCEPTION_PAY=6]");
						record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);

					}
					//			} else if (String.valueOf(OrderConstant.STATE_PAYMENTING).equals(orderState)) {
					//				} else if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == OrderConstant.STATE_PAYMENTING) {
				} else {
					HandleRegisterExceptionTask.logger
							.info("ExceptionRecord ->订单号:{},regstauts:{},paySatuts:{}  query trade PayWechat. pay's tradeState:{} . 状态不变,仍未第3方支付异常,等待下一次执行",
									new Object[] { record.getOrderNo(),
											record.getRegStatus(),
											record.getPayStatus(),
											orderInfoResponse.getTradeState() });
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY);
					record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
					record.addLogInfo("调用第3方交易平台的查询接口,未查询到确定状态,状态未变[STATE_EXCEPTION_PAY=6]");
					record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
				}
			} else if (StringUtils.equals(orderInfoResponse.getResultCode(), BizConstant.METHOD_INVOKE_FAILURE)) {
				HandleRegisterExceptionTask.logger.info(
						"ExceptionRecord ->订单号:{},regstauts:{},paySatuts:{}  query trade PayWechat is Exception. 状态不变,仍未第3方支付异常,等待下一次执行",
						new Object[] { record.getOrderNo(), record.getRegStatus(), record.getPayStatus() });
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY);
				record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
				record.addLogInfo("调用第3方交易平台的查询接口,查询网络异常,状态未变[STATE_EXCEPTION_PAY=6]");
				record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
			}
		} else {
			HandleRegisterExceptionTask.logger.info(
					"ExceptionRecord ->订单号:{},regstauts:{},paySatuts:{}  query trade PayWechat is Exception. 状态不变,仍未第3方支付异常,等待下一次执行",
					new Object[] { record.getOrderNo(), record.getRegStatus(), record.getPayStatus() });
			record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY);
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
			record.addLogInfo("调用第3方交易平台的查询接口,查询网络异常,状态未变[STATE_EXCEPTION_PAY=6]");
			record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
		}
		HandleRegisterExceptionTask.logger.info("exception[STATE_EXCEPTION_PAY] Handle res : {}", JSON.toJSONString(record));
		return record;
	}

	private boolean ifOverRefundDeadline(ExceptionRecord record) {
		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(record.getHospitalCode());
		boolean autoRefund = false;
		// 根据医院配置获取是否允许自动退费
		boolean allowAutoRefund = ruleRegister.getRefundDelayAfterException() != null && ruleRegister.getRefundDelayAfterException() > 0;
		if (allowAutoRefund) {
			Long occurTime = record.getUpdateTime();
			for (int i = 0; i < record.getHandleCount() - 1; i++) {
				occurTime -= ExceptionRecord.getHandleDelays()[i];
			}
			// 退费延时时长（毫秒）
			Long refundDelay = ruleRegister.getRefundDelayAfterException() * 60 * 1000L;
			// 异常已处理时长（毫秒）
			Long handledTime = System.currentTimeMillis() - occurTime;
			autoRefund = handledTime >= refundDelay;
		}
		HandleRegisterExceptionTask.logger.info(
				"{},his订单查询接口处于异常，根据规则配置判断是否需要退费[RefundDelayAfterException={}],是否允许自动退费allowAutoRefund={},是否将退费autoRefund={}",
				record.getOrderNo(), ruleRegister.getRefundDelayAfterException(), allowAutoRefund, autoRefund);

		return autoRefund;
	}

	private Map<String, Object> queryPayRecordStatus(ExceptionRecord record) {

		boolean isException = false;

		Map<String, Object> resMap = new HashMap<String, Object>();

		Object orderQuery = recordService.buildOrderQueryInfo(record.convertRecord());
		QueryService orderQueryService = TradeCommonHoder.getInvokeOrderQueryService();
		Object orderResponse = null;

		if (orderQuery instanceof WechatPayOrderQuery) {

			try {
				orderResponse = orderQueryService.wechatPayOrderQuery((WechatPayOrderQuery) orderQuery);
			} catch (Exception e) {
				orderResponse = null;
				HandleRegisterExceptionTask.logger.error("第3方(Wechat)退费异常->订单号:{},query WechatPayOrderQuery is null.msg:{}",
						new Object[] { record.getOrderNo(), e.getMessage() });
				isException = true;
			}

		} else if (orderQuery instanceof AlipayOrderQuery) {

			try {
				orderResponse = orderQueryService.alipayOrderQuery((AlipayOrderQuery) orderQuery);
			} catch (Exception e) {
				orderResponse = null;
				HandleRegisterExceptionTask.logger.error("第3方(AliPay)退费异常->订单号:{},query AlipayOrderQuery is null.msg:{}",
						new Object[] { record.getOrderNo(), e.getMessage() });
				isException = true;
			}

		} else if (orderQuery instanceof UnionpayOrderQuery) {
			// TODO 新增
			try {
				orderResponse = orderQueryService.unionpayOrderQuery((UnionpayOrderQuery) orderQuery);
			} catch (Exception e) {
				orderResponse = null;
				HandleRegisterExceptionTask.logger.error("第3方(UnionPay)退费异常->订单号:{},query AlipayOrderQuery is null.msg:{}",
						new Object[] { record.getOrderNo(), e.getMessage() });
				isException = true;
			}
		}

		resMap.put("isException", isException);

		if (orderResponse != null) {
			resMap.put("orderResponse", orderResponse);
		}

		return resMap;
	}

	/**
	 * 处理第3方支付成功后 his确认异常
	 * @param record
	 * @return
	 * @throws SystemException
	 */
	private ExceptionRecord handlePayHisCommitException(ExceptionRecord record) throws SystemException {
		HandleRegisterExceptionTask.logger.info(" handlePayHisCommitException QueryHisRecord.订单号:{}", record.getOrderNo());
		//检查是否处理次数满3次
		Map<String, Object> resMap = isCheckHandleValid(record);
		Boolean isValid = (Boolean) resMap.get(IS_VALID_KEY);
		record = (ExceptionRecord) resMap.get(RECORD_KEY);
		if (!isValid) {
			return record;
		} else {
			record.setHandleCount(record.getHandleCount() + 1);
			resMap = handleQueryHisRecord(record);
			record = (ExceptionRecord) resMap.get(RECORD_KEY);
			RegRecord hisRecord = (RegRecord) resMap.get(HIS_RECORD_KEY);
			// Integer regType = record.getRegType();
			if (hisRecord != null) {
				HandleRegisterExceptionTask.logger.info("handlePayHisCommitException ->订单号:{} , his信息:{}",
						new Object[] { record.getOrderNo(), JSON.toJSONString(hisRecord) });
				if (RegisterConstant.HIS_STATUS_NO_PAYMENT.equalsIgnoreCase(hisRecord.getStatus())) {
					//第3方退费
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
					record.setPayStatus(OrderConstant.STATE_REFUNDING);
					recordService.updateExceptionRecord(record);
					//进入退费流程
					HandleRegisterExceptionTask.logger.info("handlePayHisCommitException ->订单号:{} , his状态未支付,进入退费流程", record.getOrderNo());
					return agtRefund(record);

				} else if (RegisterConstant.HIS_STATUS_HAD_PAYMENT.equalsIgnoreCase(hisRecord.getStatus())) {
					record.setRegStatus(RegisterConstant.STATE_NORMAL_HAD);
					record.setPayStatus(OrderConstant.STATE_PAYMENT);
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:调用HIS查询接口,HIS状态为已支付,挂号记录状态变为预约成功,已支付;");
					record.setHandleLog(handleLog + sbLog.toString());
					record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
					HandleRegisterExceptionTask.logger.info("handlePayHisCommitException ->订单号:{} , his状态已支付,移除缓存队列", record.getOrderNo());
					recordService.updateExceptionRecord(record);
				} else {
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_FAILURE);
					record.setPayStatus(OrderConstant.STATE_PAYMENTING);
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					String hisStatus = null;
					sbLog.append(",HandleMsg:调用HIS查询接口,HIS返回状态为[" + hisStatus + "].该挂号业务处理结束;");
					record.setHandleLog(handleLog + sbLog.toString());
					record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
					HandleRegisterExceptionTask.logger
							.info("handlePayHisCommitException ->订单号:{} , 查询his状态异常,等待下一次处理", record.getOrderNo());
					recordService.updateExceptionRecord(record);
				}
			} else {
				Boolean isHappenException = (Boolean) resMap.get("isHappenException");
				if (!isHappenException) {
					//第3方退费
					HandleRegisterExceptionTask.logger.info("handlePayHisCommitException ->订单号:{} , his无订单,进入退费流程", record.getOrderNo());
					return agtRefund(record);
				}
			}

		}
		return record;
	}

	/**
	 * 处理未支付状态取消挂号异常
	 * @param record
	 * @return
	 * @throws SystemException
	 */
	private ExceptionRecord handleCancelNotPayMentException(ExceptionRecord record) throws SystemException {
		Map<String, Object> resMap = isCheckHandleValid(record);
		Boolean isValid = (Boolean) resMap.get(IS_VALID_KEY);
		record = (ExceptionRecord) resMap.get(RECORD_KEY);
		if (!isValid) {
			return record;
		} else {
			record.setHandleCount(record.getHandleCount() + 1);
			record = cancelNotPayMentRecord(record);
		}
		return record;
	}

	/**
	 * 已支付状态下取消挂号-> his预约退费确认异常
	 * 此异常不用处理  不操作数据的变更  出现异常  允许用户可重复提交
	 * @param record
	 * @return
	 * @throws SystemException
	 */
	private ExceptionRecord handleRefundHisConfirmException(ExceptionRecord record) throws SystemException {
		record.setHandleCount(record.getHandleCount() + 1);
		return record;
	}

	/**
	 * 已支付状态下取消挂号->第3方退费异常
	 * @param record
	 * @return
	 * @throws SystemException
	 */
	private ExceptionRecord handleRefundException(ExceptionRecord record) throws SystemException {
		//退费查询 是否已成功退费
		try {
			HandleRegisterExceptionTask.logger.info("handleRefundException start->订单号:{}", record.getOrderNo());
			Map<String, Object> resMap = isCheckHandleValid(record);
			Boolean isValid = (Boolean) resMap.get(IS_VALID_KEY);
			if (!isValid) {
				return record;
			}
			record.setHandleCount(record.getHandleCount() + 1);

			boolean isException = false;

			RegisterRecord registerRecord = new RegisterRecord();
			BeanUtils.copyProperties(record.convertRecord(), registerRecord);

			Object orderQuery = recordService.buildOrderQueryInfo(registerRecord);
			QueryService orderQueryService = TradeCommonHoder.getInvokeOrderQueryService();
			Object orderResponse = null;

			if (orderQuery instanceof WechatPayOrderQuery) {

				try {
					orderResponse = orderQueryService.wechatPayOrderQuery((WechatPayOrderQuery) orderQuery);
				} catch (Exception e) {
					orderResponse = null;
					HandleRegisterExceptionTask.logger.error("第3方(Wechat)退费异常->订单号:{},query wechatPayOrderQuery is null.msg:{}",
							new Object[] { record.getOrderNo(), e.getMessage() });
					isException = true;
				}

			} else if (orderQuery instanceof AlipayOrderQuery) {

				try {
					orderResponse = orderQueryService.alipayOrderQuery((AlipayOrderQuery) orderQuery);
				} catch (Exception e) {
					orderResponse = null;
					HandleRegisterExceptionTask.logger.error("第3方(AliPay)退费异常->订单号:{},query alipayOrderQuery is null.msg:{}",
							new Object[] { record.getOrderNo(), e.getMessage() });
					isException = true;
				}

			} else if (orderQuery instanceof UnionpayOrderQuery) {
				// TODO 新增
				try {
					orderResponse = orderQueryService.unionpayOrderQuery((UnionpayOrderQuery) orderQuery);
				} catch (Exception e) {
					orderResponse = null;
					HandleRegisterExceptionTask.logger.error("第3方(Unionpay)退费异常->订单号:{},query unionpayOrderQuery is null.msg:{}",
							new Object[] { record.getOrderNo(), e.getMessage() });
					isException = true;
				}
			}

			if (orderResponse == null) {
				HandleRegisterExceptionTask.logger.info("handleRefundException start->订单号:{} , 查询第3方交易平台,未找到对应的退费记录.转入直接退费操作.",
						record.getOrderNo());
			} else {
				HandleRegisterExceptionTask.logger.info("handleRefundException start->订单号:{} , 查询第3方交易平台,对应的退费记录为:{}",
						new Object[] { record.getOrderNo(), JSON.toJSONString(orderResponse) });
			}

			if (isException) {
				HandleRegisterExceptionTask.logger.info("第3方退费异常 ->订单号:{},查询第3方平台订单状态网络异常,状态不变.HandleCount:{}", record.getHandleCount());
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
				record.setPayStatus(OrderConstant.STATE_REFUNDING);
				record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
				String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
				StringBuilder sbLog = new StringBuilder();
				sbLog.append("HandleCount:" + record.getHandleCount());
				sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
				sbLog.append(",HandleMsg:调用第3方交易平台的查询接口,查询网络异常,状态未变[STATE_EXCEPTION_REFUND=10]");
				record.setHandleLog(handleLog + sbLog.toString());
				record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
				recordService.updateExceptionRecord(record);
			} else {
				if (orderResponse != null) {
					OrderQueryResponse orderInfoResponse = (OrderQueryResponse) orderResponse;
					if (StringUtils.equals(orderInfoResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
						if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == OrderConstant.STATE_REFUND) {
							HandleRegisterExceptionTask.logger.info("第3方退费异常->订单号:{},已退费,转入解锁号源流程.");
							record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL_EXCEPTION);//设置his取消号源异常，下一步执行取消号源
							record.setPayStatus(OrderConstant.STATE_REFUND);
							record.setRefundTime(System.currentTimeMillis());

							if (orderResponse instanceof WechatPayOrderQueryResponse) {
								record.setAgtRefundOrdNum( ( (WechatPayRefundResponse) orderResponse ).getAgtRefundOrderNo());
							} else if (orderResponse instanceof AlipayOrderQueryResponse) {
								record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
							} else if (orderResponse instanceof UnionpayOrderQueryResponse) {
								record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
							}

							recordService.updateExceptionRecord(record);
							//转入取消号源处理
							return cancelNotPayMentRecord(record);
						} else if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == OrderConstant.STATE_NO_REFUND) {
							HandleRegisterExceptionTask.logger.info("第3方退费异常 ->订单号:{},第3方交易平台未退费,转入退费流程.", record.getOrderNo());
							record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL_EXCEPTION);//设置his取消号源异常，下一步执行取消号源
							record.setPayStatus(OrderConstant.STATE_REFUNDING);
							recordService.updateExceptionRecord(record);
							//转入第3方退费
							return agtRefund(record);
						} else if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == OrderConstant.STATE_REFUNDING) {
							HandleRegisterExceptionTask.logger.info("第3方退费异常 ->订单号:{},第3方交易平台退费中,状态不变.HandleCount:{}",
									new Object[] { record.getOrderNo(), record.getHandleCount() });
							record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
							record.setPayStatus(OrderConstant.STATE_REFUNDING);
							record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
							String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
							StringBuilder sbLog = new StringBuilder();
							sbLog.append("HandleCount:" + record.getHandleCount());
							sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
							sbLog.append(",HandleMsg:调用第3方交易平台的查询接口,未查询到确定状态,状态未变[STATE_EXCEPTION_REFUND=10]");
							record.setHandleLog(handleLog + sbLog.toString());
							record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
							recordService.updateExceptionRecord(record);
						} else if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == 0) {
							//未查询到第3方的退费记录  认为上一次引发的退费操作未请求到第3方交易平台  故直接退费
							return agtRefund(record);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HandleRegisterExceptionTask.logger.info("exception[STATE_EXCEPTION_REFUND] Handle res : {}", JSON.toJSONString(record));
		return record;
	}

	/**
	 * 已支付状态下取消挂号->第3方退费成功后提交his确认异常
	 * @param record
	 * @return
	 * @throws SystemException
	 */
	private ExceptionRecord handleRefundHisCommitException(ExceptionRecord record) throws SystemException {
		Map<String, Object> resMap = isCheckHandleValid(record);
		Boolean isValid = (Boolean) resMap.get(IS_VALID_KEY);
		record = (ExceptionRecord) resMap.get(RECORD_KEY);
		if (!isValid) {
			return record;
		} else {
			record.setHandleCount(record.getHandleCount() + 1);
			Map<String, Object> interfaceResMap = new HashMap<String, Object>();
			String hospitalCode = record.getHospitalCode();
			String branchCode = record.getBranchHospitalCode();

			RefundRegRequest refundRequet = record.covertRefundRegRequestVo();
			Integer regType = record.getRegType();
			if (regType == null) {
				regType = RegisterConstant.REG_TYPE_APPOINTMENT;
			}
			if (regType.intValue() == RegisterConstant.REG_TYPE_CUR) {
				interfaceResMap = bizManager.refundCurReg(hospitalCode, branchCode, refundRequet);
			} else {
				interfaceResMap = bizManager.refundReg(hospitalCode, branchCode, refundRequet);
			}

			Boolean isExcetion = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);

			if (isExcetion) {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND_HIS_COMMIT);
				record.setPayStatus(OrderConstant.STATE_REFUND);
				record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
				String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
				StringBuilder sbLog = new StringBuilder();
				sbLog.append("HandleCount:" + record.getHandleCount());
				sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
				sbLog.append(",HandleMsg:调用HIS预约退费异常,状态不变[STATE_EXCEPTION_REFUND_HIS_COMMIT=11];");
				record.setHandleLog(handleLog + sbLog.toString());
				record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
			} else {
				String resCode = interfaceResMap.get(BizConstant.INTERFACE_MAP_CODE_KEY).toString();
				if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(resCode)) {
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL);
					record.setPayStatus(OrderConstant.STATE_REFUND);
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:调用HIS预约退费成功,状态变更为[STATE_EXCEPTION_CANCEL=13];");
					record.setHandleLog(handleLog + sbLog.toString());
					record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);

					recordService.updateExceptionRecord(record);
				} else if (BizConstant.INTERFACE_RES_SUCCESS_HIS_INVALID.equalsIgnoreCase(resCode)) {
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_NEED_HOSPITAL_HANDLE);
					record.setPayStatus(OrderConstant.STATE_REFUND);
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:调用HIS预约退费因医院限制,退费失败,状态变更为[STATE_EXCEPTION_NEED_HOSPITAL_HANDLE=15];");
					record.setHandleLog(handleLog + sbLog.toString());
					record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);

					recordService.updateExceptionRecord(record);
				} else {
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND_HIS_COMMIT);
					record.setPayStatus(OrderConstant.STATE_REFUND);
					record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:调用HIS预约退费失败,原因未知,状态不变[STATE_EXCEPTION_REFUND_HIS_COMMIT=11];");
					record.setHandleLog(handleLog + sbLog.toString());
					record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
				}
			}
		}
		return record;
	}

	private ExceptionRecord cancelNotPayMentRecord(ExceptionRecord record) {
		Integer regType = record.getRegType();

		String hospitalCode = record.getHospitalCode();
		String branchCode = record.getBranchHospitalCode();
		String cancleMode = String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode()));
		String hisOrdNum = record.getHisOrdNo();
		String psOrdNum = record.getOrderNo();
		Map<String, Object> interfaceResMap = new HashMap<String, Object>();
		if (regType.intValue() == RegisterConstant.REG_TYPE_APPOINTMENT) {
			//当班取消
			interfaceResMap = bizManager.cancelCurRegister(hospitalCode, branchCode, cancleMode, hisOrdNum, psOrdNum);
		} else {
			//预约取消
			interfaceResMap = bizManager.cancelRegister(hospitalCode, branchCode, cancleMode, hisOrdNum, psOrdNum);
		}

		Boolean isException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
		if (isException) {
			record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL_EXCEPTION);
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
			String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
			StringBuilder sbLog = new StringBuilder();
			sbLog.append("HandleCount:" + record.getHandleCount());
			sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
			sbLog.append(",HandleMsg:调用HIS取消挂号网络异常,状态变为[STATE_EXCEPTION_CANCEL_NOT_PAYMENT=8];");
			record.setHandleLog(handleLog + sbLog.toString());
			record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
		} else {
			String resCode = interfaceResMap.get(BizConstant.INTERFACE_MAP_CODE_KEY).toString();
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(resCode)) {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL);
				record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
				String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
				StringBuilder sbLog = new StringBuilder();
				sbLog.append("HandleCount:" + record.getHandleCount());
				sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
				sbLog.append(",HandleMsg:调用HIS取消挂号接口,取消成功[STATE_EXCEPTION_CANCEL=13];");
				record.setHandleLog(handleLog + sbLog.toString());
				record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);

				recordService.updateExceptionRecord(record);
			} else if (BizConstant.INTERFACE_RES_SUCCESS_INVALID_DATA_CODE.equalsIgnoreCase(resCode)) {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_NEED_PERSON_HANDLE);
				record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
				String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
				StringBuilder sbLog = new StringBuilder();
				sbLog.append("HandleCount:" + record.getHandleCount());
				sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
				sbLog.append(",HandleMsg:调用HIS取消挂号接口,因不符合医院限制取消失败[STATE_EXCEPTION_NEED_PERSON_HANDLE=14];");
				record.setHandleLog(handleLog + sbLog.toString());
				record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);

				recordService.updateExceptionRecord(record);
			} else {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL_EXCEPTION);
				record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
				String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
				StringBuilder sbLog = new StringBuilder();
				sbLog.append("HandleCount:" + record.getHandleCount());
				sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
				sbLog.append(",HandleMsg:调用HIS取消挂号接口,取消失败[STATE_EXCEPTION_CANCEL_NOT_PAYMENT=8];");
				record.setHandleLog(handleLog + sbLog.toString());
				record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
			}
		}
		return record;
	}

	/**
	 * 第3方退费
	 * @param record
	 * @return
	 */
	private ExceptionRecord agtRefund(ExceptionRecord record) {

		Object refund = recordService.buildRefundInfo(record.convertRecord());
		RefundService refundService = TradeCommonHoder.getInvokeRefundService();
		Object refundResponse = null;

		if (refund instanceof WechatPayRefund) {
			try {
				refundResponse = refundService.wechatPayRefund((WechatPayRefund) refund);
			} catch (Exception e) {
				refundResponse = null;
				HandleRegisterExceptionTask.logger.error("第3方(WechatPayRefund)退费异常->订单号:{},query WechatPayRefund is null.msg:{}",
						new Object[] { record.getOrderNo(), e.getMessage() });
			}

		} else if (refund instanceof AlipayRefund) {
			try {
				refundResponse = refundService.alipayRefund((AlipayRefund) refund);
			} catch (Exception e) {
				refundResponse = null;
				HandleRegisterExceptionTask.logger.error("第3方(AlipayRefund)退费异常->订单号:{},query AlipayRefund is null.msg:{}",
						new Object[] { record.getOrderNo(), e.getMessage() });
			}

		} else if (refund instanceof UnionpayRefund) {
			// TODO 新增
			try {
				refundResponse = refundService.unionpayRefund((UnionpayRefund) refund);
			} catch (Exception e) {
				refundResponse = null;
				HandleRegisterExceptionTask.logger.error("第3方(UnionpayRefund)退费异常->订单号:{},query UnionpayRefund is null.msg:{}",
						new Object[] { record.getOrderNo(), e.getMessage() });
			}
		}

		HandleRegisterExceptionTask.logger.info("订单号:{},退费结果:{}", new Object[] { record.getOrderNo(), JSON.toJSONString(refundResponse) });

		if (refundResponse != null) {
			RefundResponse refundRsp = (RefundResponse) refundResponse;
			if (StringUtils.equals(refundRsp.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
				if (StringUtils.equals(refundRsp.getRefundState(), TradeConstant.REFUND_STATE_SUCCESS)) {// 已经退费

					HandleRegisterExceptionTask.logger.info("第3方(Wechat)退费异常处理成功->订单号:{},已退费,转入解锁号源流程.", record.getOrderNo());
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL);//设置his取消号源异常，下一步执行取消号源
					record.setPayStatus(OrderConstant.STATE_REFUND);
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
					record.setRefundTime(System.currentTimeMillis());

					if (refundRsp instanceof WechatPayRefundResponse) {
						record.setAgtRefundOrdNum( ( (WechatPayRefundResponse) refundRsp ).getAgtRefundOrderNo());
					} else if (refundRsp instanceof AlipayRefundResponse) {
						record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
					} else if (refundRsp instanceof UnionpayRefundResponse) {
						record.setAgtRefundOrdNum( ( (UnionpayRefundResponse) refundRsp ).getAgtRefundOrderNo());
					}

					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:第3方交易平台(Wechat)退费成功,状态变为[STATE_EXCEPTION_CANCEL=8]");

					recordService.updateExceptionRecord(record);
					//转入取消号源异常处理
					return cancelNotPayMentRecord(record);
				}
			}
		}

		//转入第3方退费异常处理
		HandleRegisterExceptionTask.logger.info("第3方({})退费 ->订单号:{},第3方交易平台失败/异常,转入退费异常处理流程.", new Object[] { record.getPlatformMode(),
				record.getOrderNo() });// TODO 是否兼容医院不修改接口
		record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
		record.setPayStatus(OrderConstant.STATE_REFUNDING);
		record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
		String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
		StringBuilder sbLog = new StringBuilder();
		sbLog.append("HandleCount:" + record.getHandleCount());
		sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
		sbLog.append(",HandleMsg:第3方交易平台退费失败,状态变为[STATE_EXCEPTION_REFUND=10]");
		record.setHandleLog(handleLog + sbLog.toString());
		record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
		recordService.updateExceptionRecord(record);
		return record;
	}
}
