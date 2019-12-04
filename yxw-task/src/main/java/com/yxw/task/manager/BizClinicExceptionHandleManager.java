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
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.mobile.biz.vote.VoteInfo;
import com.yxw.commons.entity.platform.payrefund.AlipayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.AlipayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.AlipayRefund;
import com.yxw.commons.entity.platform.payrefund.OrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.RefundResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefundResponse;
import com.yxw.commons.entity.platform.rule.RuleClinic;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;
import com.yxw.interfaces.vo.clinicpay.PayFee;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.vote.service.VoteService;
import com.yxw.mobileapp.datas.manager.ClinicBizManager;
import com.yxw.payrefund.service.QueryService;
import com.yxw.payrefund.service.RefundService;
import com.yxw.payrefund.utils.TradeCommonHoder;
import com.yxw.task.taskitem.HandleRegisterExceptionTask;

/**
 * @Package: com.yxw.platform.datas.manager
 * @ClassName: BizClinicExceptionHandleManager
 * @Statement: <p>
 *             门诊异常处理
 *             </p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-8-3
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class BizClinicExceptionHandleManager {
	private Logger logger = LoggerFactory.getLogger(BizClinicExceptionHandleManager.class);
	private ClinicBizManager clinicBizManager = SpringContextHolder.getBean(ClinicBizManager.class);
	private ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);
	private CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
	private VoteService voteService = SpringContextHolder.getBean(VoteService.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	private static final String IS_VALID_KEY = "isValid";
	private static final String RECORD_KEY = "record";

	/**
	 * 处理挂号异常
	 * 
	 * @param record
	 *            return
	 */
	public ClinicRecord handleClinicException(ClinicRecord record) throws SystemException {

		List<Object> params = new ArrayList<Object>();

		if (record.getHandleCount() == null) {
			record.setHandleCount(0);
		}
		record.setHandleCount(record.getHandleCount() + 1);

		int state = record.getClinicStatus();
		try {
			switch (state) {
			case ClinicConstant.STATE_EXCEPTION_HIS:
				// 写入His异常
				record = handleHisException(record);
				break;
			case ClinicConstant.STATE_EXCEPTION_REFUND:
				// 退费异常
				record = handleRefundException(record);
				break;
			case ClinicConstant.STATE_EXCEPTION_PAY:
				// 第三方缴费异常
				record = handlePayException(record);
				break;
			case ClinicConstant.STATE_PAY_EXCEPTION_NO_RECORD_WAIT:
				// His支付异常查询不到订单，等待his支付接口处理完5-10min再确认订单状态
				//				record = handlePayExceptionNoRecord(record);
				break;
			default:
				record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
				break;
			}

			//判断下次是否满足异常处理条件
			checkHandleValid(record);
			clinicService.updateExceptionRecord(record);

			params.add(record);
			serveComm.set(CacheType.CLINIC_RECORD_CACHE, "updateExceptionRecord", params);

		} catch (Exception e) {
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
			record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
			logger.error("订单号:{} , exec HandleClinicExceptionTask is execption. msg:{}",
					new Object[] { record.getOrderNo(), e.getMessage() });
		} finally {
			if (record.getIsHandleSuccess().intValue() == BizConstant.HANDLED_FAILURE) {
				params.clear();
				params.add(record);
				serveComm.set(CacheType.CLINIC_EXCEPTION_CACHE, "setExceptionClinicToCache", params);
			}
		}

		return record;
	}

	public ClinicRecord handleHisException(ClinicRecord record) throws SystemException {
		Map<String, Object> resMap = checkHandleValid(record);
		Boolean isValid = (Boolean) resMap.get(IS_VALID_KEY);
		record = (ClinicRecord) resMap.get(RECORD_KEY);
		if (!isValid) {
			return record;
		} else {
			record = handleHisRecord(record);
		}

		return record;
	}

	public ClinicRecord handleRefundException(ClinicRecord record) throws SystemException {
		Map<String, Object> resMap = checkHandleValid(record);
		Boolean isValid = (Boolean) resMap.get(IS_VALID_KEY);
		if (!isValid) {
			return record;
		}

		boolean isException = false; // 记录是否发生异常

		Object orderQuery = clinicService.buildOrderQueryInfo(record);
		QueryService orderQueryService = TradeCommonHoder.getInvokeOrderQueryService();
		Object orderResponse = null;

		if (orderQuery instanceof WechatPayOrderQuery) {
			// 查询微信状态
			try {
				orderResponse = orderQueryService.wechatPayOrderQuery((WechatPayOrderQuery) orderQuery);
			} catch (Exception e) {
				logger.info("query wechat refund order[{}] exception.cause: {}", record.getOrderNo(), e.getCause());
				isException = true;
			}
		} else if (orderQuery instanceof AlipayOrderQuery) {
			// 查询支付宝状态
			try {
				orderResponse = orderQueryService.alipayOrderQuery((AlipayOrderQuery) orderQuery);
			} catch (Exception e) {
				logger.info("query alipay refund order[{}] exception.cause: {}", record.getOrderNo(), e.getCause());
				isException = true;
			}
		} else if (orderQuery instanceof UnionpayOrderQuery) {
			// TODO 新增
			try {
				orderResponse = orderQueryService.unionpayOrderQuery((UnionpayOrderQuery) orderQuery);
			} catch (Exception e) {
				logger.info("query unionpay refund order[{}] exception.cause: {}", record.getOrderNo(), e.getCause());
				isException = true;
			}
		}

		if (orderResponse == null) {
			isException = true;
			logger.info("handleClinicRefundException start->订单号:{} , 查询第3方交易平台,未找到对应的退费记录.转入直接退费操作.", record.getOrderNo());
		} else {
			logger.info("handleClinicRefundException start->订单号:{} , 查询第3方交易平台,对应的退费记录为:{}",
					new Object[] { record.getOrderNo(), JSON.toJSONString(orderResponse) });
		}

		if (isException) {
			record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_REFUND);
			record.setHandleCount(record.getHandleCount().intValue() + 1);
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);

			String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
			StringBuilder sbLog = new StringBuilder();
			sbLog.append("HandleCount:" + record.getHandleCount());
			sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
			sbLog.append(",HandleMsg:调用第3方交易平台的查询接口,查询网络异常,状态未变[STATE_REFUNDING=5]");
			record.setHandleLog(handleLog + sbLog.toString());

			record.setIsValid(ClinicConstant.RECORD_IS_VALID_TRUE);
		} else {
			if (orderResponse != null) {
				OrderQueryResponse orderInfoResponse = (OrderQueryResponse) orderResponse;
				if (StringUtils.equals(orderInfoResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
					if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == OrderConstant.STATE_REFUND) {// 已经退费

						logger.info("查询订单状态，已退费，关闭交易...[{}]", record.getOrderNo());
						record.setClinicStatus(ClinicConstant.STATE_PAY_CLOSED);
						record.setPayStatus(OrderConstant.STATE_REFUND);
						record.setHandleCount(record.getHandleCount().intValue() + 1);
						record.setRefundTime(System.currentTimeMillis());
						// record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
						record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);

						String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
						StringBuilder sbLog = new StringBuilder();
						sbLog.append("HandleCount:" + record.getHandleCount());
						sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
						sbLog.append(",HandleMsg:退费成功,交易关闭,状态改成[STATE_REFUND=3]");
						record.setHandleLog(handleLog + sbLog.toString());

						if (orderResponse instanceof WechatPayOrderQueryResponse) {
							record.setAgtRefundOrdNum( ( (WechatPayRefundResponse) orderResponse ).getAgtRefundOrderNo());
						} else if (orderResponse instanceof AlipayOrderQueryResponse) {
							record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
						} else if (orderResponse instanceof UnionpayOrderQueryResponse) {
							record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
						}

						record.setRefundTime(System.currentTimeMillis()); // 在这里设置退费时间?

						// 更新数据库及缓存信息
						clinicService.updateOrderPayInfo(record);

					} else if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == OrderConstant.STATE_NO_REFUND) {
						// 未退费---------------------------
						logger.info("查询到该订单未退费，则执行退费...[{}]. refund start........", record.getOrderNo());
						record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_REFUND);
						record.setPayStatus(OrderConstant.STATE_REFUNDING);
						return agtRefund(record);
					} else if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == OrderConstant.STATE_REFUNDING) {
						// 退费中---------------------------
						record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_REFUND);
						// record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
						record.setHandleCount(record.getHandleCount().intValue() + 1);
						record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
						String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
						StringBuilder sbLog = new StringBuilder();
						sbLog.append("HandleCount:" + record.getHandleCount());
						sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
						sbLog.append(",HandleMsg:调用第3方交易平台的查询接口,订单状态为退费中,支付状态未变[STATE_REFUNDING=5]");
						record.setHandleLog(handleLog + sbLog.toString());
						record.setIsValid(ClinicConstant.RECORD_IS_VALID_TRUE);
					} else if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == 0) {
						// 未查询到第3方的退费记录 认为上一次引发的退费操作未请求到第3方交易平台 故直接退费
						logger.info("未查询到第3方的退费记录  认为上一次引发的退费操作未请求到第3方交易平台  故直接退费...[{}]", record.getOrderNo());
						return agtRefund(record);
					}
				}
			}
		}

		logger.info("exception[STATE_EXCEPTION_REFUND] Handle res : {}", JSON.toJSONString(record));
		return record;
	}

	public ClinicRecord handlePayException(ClinicRecord record) throws SystemException {
		Map<String, Object> resMap = checkHandleValid(record);
		Boolean isValid = (Boolean) resMap.get(IS_VALID_KEY);
		if (!isValid) {
			return record;
		}
		record.setHandleCount(record.getHandleCount() + 1);
		//查询是否已经支付
		Object orderQuery = clinicService.buildOrderQueryInfo(record);
		QueryService OrderQueryService = TradeCommonHoder.getInvokeOrderQueryService();
		Object orderResponse = null;

		if (orderQuery instanceof WechatPayOrderQuery) {

			try {
				orderResponse = OrderQueryService.wechatPayOrderQuery((WechatPayOrderQuery) orderQuery);
			} catch (Exception e) {
				orderResponse = null;
				logger.error("ClinicExceptionRecord -> 订单号:{},clinicStauts:{},paySatuts:{}  query trade PayAli. PayWechat is null",
						new Object[] { record.getOrderNo(), record.getClinicStatus(), record.getPayStatus() });
			}

		} else if (orderQuery instanceof AlipayOrderQuery) {

			try {
				orderResponse = OrderQueryService.alipayOrderQuery((AlipayOrderQuery) orderQuery);
			} catch (Exception e) {
				orderResponse = null;
				logger.error("ClinicExceptionRecord -> 订单号:{},clinicStauts:{},paySatuts:{}  query trade PayAli. PayAli is null",
						new Object[] { record.getOrderNo(), record.getClinicStatus(), record.getPayStatus() });
			}

		} else if (orderQuery instanceof UnionpayOrderQuery) {
			// TODO 新增
			try {

				orderResponse = OrderQueryService.unionpayOrderQuery((UnionpayOrderQuery) orderQuery);

			} catch (Exception e) {
				orderResponse = null;
				logger.error("ClinicExceptionRecord -> 订单号:{},clinicStauts:{},paySatuts:{}  query trade PayAli. PayAli is null",
						new Object[] { record.getOrderNo(), record.getClinicStatus(), record.getPayStatus() });
			}
		}

		if (orderResponse != null) {

			OrderQueryResponse orderInfoResponse = (OrderQueryResponse) orderResponse;
			if (StringUtils.equals(orderInfoResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {

				if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == OrderConstant.STATE_NO_PAYMENT) {
					// 未支付	（门诊这边是不需要做特别的处理，但是，存在如下情况：用户已经支付了，回调过慢，去查询接口查询的时候，查到未支付。这种订单目前如何处理？）
					logger.info(
							"ClinicExceptionRecord ->订单号:{},clinicStauts:{},paySatuts:{}  query trade PayWechat. pay's orderState:{} . 查询未支付。",
							new Object[] { record.getOrderNo(),
									record.getClinicStatus(),
									record.getPayStatus(),
									orderInfoResponse.getTradeState() });

					Date date = new Date();

					record.setAgtOrdNum(orderInfoResponse.getAgtOrderNo());// TODO 设置值为null 
					record.setPayTime(date.getTime());
					record.setClinicStatus(ClinicConstant.STATE_NO_PAY); // 门诊订单状态，支付失败
					record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO); // 无异常
					record.setPayStatus(OrderConstant.STATE_NO_PAYMENT); // 支付状态，要退费
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS); // 处理成功
					record.setHandleCount(record.getHandleCount().intValue() + 1);
					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(date));
					sbLog.append(",HandleMsg:调用第三方查询订单接口,该订单未支付[STATE_NO_PAY=0]。");
					record.setHandleLog(handleLog + sbLog.toString());

					clinicService.updateOrderPayInfo(record);
				} else if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == OrderConstant.STATE_PAYMENT) {
					// 已支付, 处理过慢了, 需要记录支付信息，然后进行退费操作。
					logger.info(
							"ClinicExceptionRecord ->订单号:{},clinicStauts:{},paySatuts:{}  query trade PayWechat. pay's orderState:{} . 查询到已支付。保存支付信息，进行退费操作。(无法进入回调操作业务)",
							new Object[] { record.getOrderNo(),
									record.getClinicStatus(),
									record.getPayStatus(),
									orderInfoResponse.getTradeState() });

					record.setAgtOrdNum(orderInfoResponse.getAgtOrderNo());
					record.setPayTime(System.currentTimeMillis());
					record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_PAY); // 门诊订单状态，支付失败
					//				record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO); // 无异常
					record.setPayStatus(OrderConstant.STATE_REFUNDING); // 支付状态，要退费
					//				record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS); // 处理成功
					record.setHandleCount(record.getHandleCount().intValue() + 1);
					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:调用第三方查询订单接口,该订单已支付,转入退费流程,计数器清零,状态为[STATE_REFUNDING=5]。");
					record.setHandleLog(handleLog + sbLog.toString());

					// 转入退费，计数器清0
					record.setHandleCount(0);
					// 转入退费处理
					record = handleRefundException(record);
					// 更新记录信息入库/缓存
					clinicService.updateOrderPayInfo(record);
				} else if (Integer.valueOf(orderInfoResponse.getTradeState()).intValue() == OrderConstant.STATE_PAYMENTING) {
					// 支付中
					logger.info(
							"ClinicExceptionRecord ->订单号:{},clinicStauts:{},paySatuts:{}  query trade PayWechat. pay's orderState:{} . 状态不变,支付中，仍未第3方支付异常,等待下一次执行",
							new Object[] { record.getOrderNo(),
									record.getClinicStatus(),
									record.getPayStatus(),
									orderInfoResponse.getTradeState() });
					record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_PAY);
					record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:调用第3方交易平台的查询接口,未查询到确定状态,状态未变[STATE_EXCEPTION_PAY=6]");
					record.setHandleLog(handleLog + sbLog.toString());
					record.setIsValid(ClinicConstant.RECORD_IS_VALID_TRUE);
				}

			}

		} else {
			// 查不到支付信息
			logger.info(
					"ClinicExceptionRecord ->订单号:{},clinicStauts:{},paySatuts:{}  query trade PayWechat is Exception. 状态不变,仍未第3方支付异常,等待下一次执行",
					new Object[] { record.getOrderNo(), record.getClinicStatus(), record.getPayStatus() });
			record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_PAY);
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
			String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
			StringBuilder sbLog = new StringBuilder();
			sbLog.append("HandleCount:" + record.getHandleCount());
			sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
			sbLog.append(",HandleMsg:调用第3方交易平台的查询接口,查询网络异常,状态未变[STATE_EXCEPTION_PAY=2]");
			record.setHandleLog(handleLog + sbLog.toString());
			record.setIsValid(ClinicConstant.RECORD_IS_VALID_TRUE);

		}
		logger.info("exception[STATE_EXCEPTION_PAY] Handle res : {}", JSON.toJSONString(record));
		return record;
	}

	/*private WechatPay buildWechatQueryParam(ClinicRecord record) {
		WechatPay pay = TradeCommonHoder.buildWechatPayInfo(record);
		pay.setOrderState(record.getPayStatus());
		return pay;
	}

	private Alipay buildAliQueryParam(ClinicRecord record) {
		Alipay pay = new Alipay();
		// HospitalCodeAndAppVo vo = baseDateManager.getAppInfoByHospitalCode(record.getHospitalCode(), record.getAppCode());\
		HospitalCodeAndAppVo vo =
				baseDateManager.queryAppInfoByAppIdAndAppCodeForApp(record.getAppId(), record.getAppCode(),
						ModeTypeUtil.getTradeModeCode(record.getTradeMode()));

		pay.setAppId(vo.getAppId());
		pay.setMchId(vo.getMchId());
		pay.setOrderNo(record.getOrderNo());
		//pay.setNonceStr(PKGenerator.generateId());
		pay.setKey(vo.getPaykey());
		return pay;
	}*/

	private Map<String, Object> checkHandleValid(ClinicRecord record) {
		Boolean isValid = true;
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (record.getHandleCount() >= 3) {
			// record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_NEED_PERSON_HANDLE);
			// record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
			String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
			StringBuilder sbLog = new StringBuilder();
			sbLog.append("HandleCount:" + ( record.getHandleCount().intValue() + 1 ));
			sbLog.append("HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + ",");

			if (record.getClinicStatus() == ClinicConstant.STATE_EXCEPTION_HIS) {
				// 只有his异常才需要进行窗口处理
				record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_NEED_HOSPITAL_HANDLE);
				sbLog.append("HandleMsg:经系统轮询,处理最大次数超过3次,移除异常队列!状态更新为医院窗口处理(STATE_EXCEPTION_NEED_HOSPITAL_HANDLE);");
			} else {
				sbLog.append("HandleMsg:经系统轮询,处理最大次数超过3次,移除异常队列!状态更新为人工处理(STATE_EXCEPTION_NEED_PERSON_HANDLE);");
			}
			record.setHandleLog(handleLog + sbLog.toString());
			isValid = false;

			// 更新记录信息入库/缓存
			clinicService.updateOrderPayInfo(record);
			commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_CLINIC_PAY_EXP);

			// 发送异常消息
		}
		resMap.put(IS_VALID_KEY, isValid);
		resMap.put(RECORD_KEY, record);
		return resMap;
	}

	@SuppressWarnings("unchecked")
	private ClinicRecord handleHisRecord(ClinicRecord record) {
		List<PayFee> records = null;

		/****************************** 查询订单状态   ************************************/
		Map<String, String> params = generateGetOrderStatusParams(record);
		Map<String, Object> interfaceResMap = clinicBizManager.getPayOrderStatus(params);
		/**********************************************************************************/

		Boolean isException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
		PayFee payFee = null;
		/*********************************** 发生异常  ********************************************/
		if (isException) {
			record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_HIS);
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
			String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
			StringBuilder sbLog = new StringBuilder();
			sbLog.append("HandleCount:" + record.getHandleCount());
			sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
			sbLog.append(",HandleMsg:调用HIS订单查询接口网络异常,状态不变[STATE_EXCEPTION_HIS=3];");
			record.setHandleLog(handleLog + sbLog.toString());
		}

		/**********************************  请求返回成功   ********************************************/
		else {
			boolean isSuccess = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
			if (isSuccess) {
				records = (List<PayFee>) interfaceResMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
				if (CollectionUtils.isEmpty(records)) {
					record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_HIS);
					record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:调用HIS订单查询接口数据错误,接口返回0,但是没有查到数据,状态不变[STATE_EXCEPTION_HIS=3];");
					record.setHandleLog(handleLog + sbLog.toString());
				} else {
					/***********************************HIS支付成功***********************************************/
					payFee = records.get(0);
					if (ClinicConstant.HIS_STATUS_HAD_PAY.equals(payFee.getPayStatus())) {
						// 已支付，移除异常队列，发送支付成功消息
						record.setClinicStatus(ClinicConstant.STATE_PAY_SUCCESS);
						record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
						record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
						String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
						StringBuilder sbLog = new StringBuilder();
						sbLog.append("HandleCount:" + record.getHandleCount());
						sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
						sbLog.append(",HandleMsg:调用HIS订单查询接口,订单已支付,状态更改为[STATE_PAY_SUCCESS=1]。");
						record.setHandleLog(handleLog + sbLog.toString());
						// 从门诊缴费记录中获取缴费信息
						record.setPayStatus(OrderConstant.STATE_PAYMENT);
						record.setHisMessage(payFee.getHisMessage());
						record.setHisOrdNo(payFee.getHisOrdNum());
						record.setReceiptNum(payFee.getReceiptNum());
						record.setBarcode(payFee.getBarCode());

						// 推送成功消息
						commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_CLINIC_PAY_SUCCESS);

						/*********************************** 推送就诊评价 ***********************************************/
						try {
							VoteInfo voteInfo =
									voteService
											.generateVoteInfo(record.getAppId(), record.getAppCode(),
													String.valueOf(BizConstant.BIZ_TYPE_CLINIC), record.getOrderNo(), record.getCardNo(),
													record.getOpenId(), record.getDeptName(), "", record.getDeptName()
															+ CacheConstant.CACHE_KEY_SPLIT_CHAR + record.getRecordTitle(),
													record.getPatientName());
							voteInfo.setRecord(record);
							commonMsgPushSvc.pushMsg(voteInfo, MessageConstant.ACTION_TYPE_CLINIC_SUCCESS_COMMENT);
						} catch (Exception e) {
							logger.error("门诊缴费成功，推送就诊评价消息异常. sent clinic comment error. openId: {}, orderNo: {}, errorMsg: {}, cause: {},",
									new Object[] { record.getOpenId(), record.getOrderNo(), e.getMessage(), e.getCause() });
						}
					} else if (ClinicConstant.HIS_STATUS_NEVER_PAY.equals(payFee.getPayStatus())) {
						/*************************************  HIS未支付   *******************************************/
						refundIfNecessary(record, isSuccess);
					} else {
						/*************************************  HIS已退费  *********************************************/
						// 已退费  --> 转人工处理流程， 移除队列。推异常消息
						record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_NEED_PERSON_HANDLE);
						record.setPayStatus(OrderConstant.STATE_REFUNDING);
						record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
						String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
						StringBuilder sbLog = new StringBuilder();
						sbLog.append("HandleCount:" + record.getHandleCount());
						sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
						sbLog.append(",HandleMsg:调用HIS订单查询接口,His订单状态为已退费,需要进行人工处理,状态更改为[STATE_EXCEPTION_NEED_PERSON_HANDLE=7]。");
						record.setHandleLog(handleLog + sbLog.toString());

						// 推送异常消息
						// commonMsgPushSvc.pushMsg(record, CommonMsgPushService.ACTION_TYPE_CLINIC_PAY_EXP);
						// 更新记录信息入库/缓存
					}
				}
			}
			/*************************************  请求返回失败  *********************************************/
			else {
				refundIfNecessary(record, isSuccess);
			}
		}

		return record;
	}

	/**
	 * 查询his订单返回未支付或者查询不到订单信息时，根据医院的缴费配置处理订单
	 * 若医院允许自动退费，则超过退费延时时间后进行退费处理
	 * 否则继续进行异常轮询
	 *
	 * @param record
	 * @param hisQuerySuccess
	 */
	public void refundIfNecessary(ClinicRecord record, boolean hisQuerySuccess) {
		RuleClinic ruleClinic = ruleConfigManager.getRuleClinicByHospitalCode(record.getHospitalCode());
		if (ruleClinic == null) {
			logger.error("订单{}: 缺少医院缴费配置信息，无法进行后续处理", record.getOrderNo());
			record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_HIS);
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
			String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
			StringBuilder sbLog = new StringBuilder();
			sbLog.append("HandleCount:" + record.getHandleCount());
			sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
			sbLog.append(",HandleMsg:调用HIS订单查询接口,");
			if (hisQuerySuccess) {
				sbLog.append("His订单状态为未缴费,");
			} else {
				sbLog.append("未查询到His订单,");
			}
			sbLog.append("但缺少医院缴费配置信息,状态不变[STATE_EXCEPTION_HIS=3];");
			record.setHandleLog(handleLog + sbLog.toString());
		} else {
			/*
			 * 是否自动退费
			 * 默认为不自动退费
			 */
			boolean autoRefund = false;
			// 根据医院配置获取是否允许自动退费
			boolean allowAutoRefund = ruleClinic.getRefundDelayAfterException() != null && ruleClinic.getRefundDelayAfterException() > 0;
			if (allowAutoRefund) {
				// 异常发生时间（估算值，因为是定时任务，存在定时执行的时间间隔误差）
				Long occurTime = record.getUpdateTime();
				for (int i = 0; i < record.getHandleCount() - 1; i++) {
					occurTime -= ClinicRecord.getHandleDelays()[i];
				}
				// 退费延时时长（毫秒）
				Long refundDelay = ruleClinic.getRefundDelayAfterException() * 60 * 1000L;
				// 异常已处理时长（毫秒）
				Long handledTime = System.currentTimeMillis() - occurTime;
				autoRefund = handledTime >= refundDelay;
			}
			if (autoRefund) {
				// 待缴费 --> 退费(相当于缴费失败)
				record.setClinicStatus(ClinicConstant.STATE_NO_PAY);
				record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
				String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
				StringBuilder sbLog = new StringBuilder();
				sbLog.append("HandleCount:" + record.getHandleCount());
				sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
				sbLog.append(",HandleMsg:调用HIS订单查询接口,");
				if (hisQuerySuccess) {
					sbLog.append("His订单状态为未缴费,");
				} else {
					sbLog.append("未查询到His订单,");
				}
				sbLog.append("已超过退费延时时间[").append(ruleClinic.getRefundDelayAfterException());
				sbLog.append("分钟],转入退费流程,计数器清零,状态更改为[STATE_NO_PAY=0]。");
				record.setHandleLog(handleLog + sbLog.toString());
				// 转入退费，计数器清0
				record.setHandleCount(0);
				/***
				 * 发送失败消息
				 */
				commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_CLINIC_PAY_FAIL);

				// 转入退费处理
				handleRefundException(record);
			} else {
				record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_HIS);
				record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
				String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
				StringBuilder sbLog = new StringBuilder();
				sbLog.append("HandleCount:" + record.getHandleCount());
				sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
				sbLog.append(",HandleMsg:调用HIS订单查询接口,");
				if (hisQuerySuccess) {
					sbLog.append("His订单状态为未缴费,");
				} else {
					sbLog.append("未查询到His订单,");
				}
				if (allowAutoRefund) {
					sbLog.append("未超过退费延时时间[").append(ruleClinic.getRefundDelayAfterException());
					sbLog.append("分钟],");
				} else {
					sbLog.append("医院不允许自动退费,");
				}
				sbLog.append("状态不变[STATE_EXCEPTION_HIS=3];");
				record.setHandleLog(handleLog + sbLog.toString());
			}
		}
	}

	private Map<String, String> generateGetOrderStatusParams(ClinicRecord record) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("hospitalCode", record.getHospitalCode());
		map.put("branchHospitalCode", record.getBranchHospitalCode());
		map.put("patCardType", record.getCardType().toString());
		map.put("patCardNo", record.getCardNo().toString());
		// 接口：0所有，1微信，2支付宝。 掌上医院：1微信，2支付宝
		map.put("payMode", record.getPlatformMode().toString());// TODO 是否兼容医院不修改接口
		map.put("beginDate", "");
		map.put("endDate", "");
		map.put("psOrdNum", record.getOrderNo());
		return map;
	}

	private ClinicRecord agtRefund(ClinicRecord record) {

		Object refund = clinicService.buildRefundInfo(record);
		RefundService refundService = TradeCommonHoder.getInvokeRefundService();

		HandleRegisterExceptionTask.logger.info("订单号:{},退款参数:{}", new Object[] { record.getOrderNo(), JSON.toJSONString(refund) });

		Object refundResponse = null;
		if (refund instanceof WechatPayRefund) {
			refundResponse = refundService.wechatPayRefund((WechatPayRefund) refund);
		} else if (refund instanceof AlipayRefund) {
			refundResponse = refundService.alipayRefund((AlipayRefund) refund);
		} else if (refund instanceof UnionpayRefund) {
			// TODO 新增
			refundResponse = refundService.unionpayRefund((UnionpayRefund) refund);
		}

		logger.info("订单号:{},退费结果:{}", new Object[] { record.getOrderNo(), JSON.toJSONString(refund) });

		if (refundResponse != null) {
			RefundResponse refundRsp = (RefundResponse) refundResponse;
			if (StringUtils.equals(refundRsp.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {

				if (StringUtils.equals(refundRsp.getRefundState(), TradeConstant.REFUND_STATE_SUCCESS)) {

					logger.info("第3方退费异常处理成功->订单号:{},已退费, 交易关闭.", record.getOrderNo());
					record.setClinicStatus(ClinicConstant.STATE_PAY_CLOSED);
					record.setPayStatus(OrderConstant.STATE_REFUND);
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
					record.setRefundTime(System.currentTimeMillis());

					if (refundResponse instanceof WechatPayOrderQueryResponse) {
						record.setAgtRefundOrdNum( ( (WechatPayRefundResponse) refundResponse ).getAgtRefundOrderNo());
					} else if (refundResponse instanceof AlipayOrderQueryResponse) {
						record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
					} else if (refundResponse instanceof UnionpayOrderQueryResponse) {
						record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
					}

					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append("HandleCount:" + record.getHandleCount());
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:第3方交易平台退费成功,交易关闭.状态变为[STATE_REFUND=3]");
					record.setHandleLog(handleLog + sbLog.toString());

					clinicService.updateOrderPayInfo(record);
					// 转入取消号源异常处理
					return record;
				}
			}
		}
		// 转入第3方退费异常处理
		HandleRegisterExceptionTask.logger.info("第3方({})退费 ->订单号:{},第3方交易平台失败/异常,转入退费异常处理流程.", new Object[] { record.getPlatformMode(),
				record.getOrderNo() });// TODO 是否兼容医院不修改接口
		record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_REFUND);
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
		record.setPayStatus(OrderConstant.STATE_REFUNDING);
		record.setHandleCount(record.getHandleCount().intValue() + 1);
		String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
		StringBuilder sbLog = new StringBuilder();
		sbLog.append("HandleCount:" + record.getHandleCount());
		sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
		sbLog.append(",HandleMsg:第3方交易平台退费失败,状态变为[STATE_REFUNDING=5]");
		record.setHandleLog(handleLog + sbLog.toString());
		record.setIsValid(ClinicConstant.RECORD_IS_VALID_TRUE);
		return record;
	}
}
