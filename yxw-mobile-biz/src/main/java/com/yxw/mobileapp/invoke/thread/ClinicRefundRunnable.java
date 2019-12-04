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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.platform.payrefund.AlipayRefund;
import com.yxw.commons.entity.platform.payrefund.AlipayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefund;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefundResponse;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.clinicpay.PayFee;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.datas.manager.ClinicBizManager;
import com.yxw.payrefund.service.RefundService;
import com.yxw.payrefund.utils.TradeCommonHoder;

/**
 * 门诊缴费退费
 * @Package: com.yxw.mobileapp.invoke.thread
 * @ClassName: ClinicRefundRunnable
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-13
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ClinicRefundRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(ClinicRefundRunnable.class);
	private ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);
	private ClinicBizManager clinicBizManager = SpringContextHolder.getBean(ClinicBizManager.class);
	private CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
	private ClinicRecord record;

	public ClinicRefundRunnable(ClinicRecord record) {
		super();
		this.record = record;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		Map<String, Object> hisMap = queryHisClinicRecord(record);
		Boolean isException = (Boolean) hisMap.get("isException");
		PayFee hisClinicRecord = (PayFee) hisMap.get("hisClinicRecord");
		if (!isException) {
			if (hisClinicRecord != null) {

				if (ClinicConstant.HIS_STATUS_HAD_PAY.equals(hisClinicRecord.getPayStatus())) {
					record.setClinicStatus(ClinicConstant.STATE_PAY_SUCCESS);
					record.setPayStatus(OrderConstant.STATE_PAYMENT);
					String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
					StringBuilder sbLog = new StringBuilder();
					sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					sbLog.append(",HandleMsg:门诊支付回调，his缴费异常，进入退费子线程，查询his门诊订单状态为已缴费，不做退费操作");
					record.setHandleLog(handleLog + sbLog.toString());
					logger.info("订单号：{},门诊支付回调，his缴费异常，进入退费子线程，查询his门诊订单状态为已缴费，不做退费操作", record.getOrderNo());
					clinicService.updateOrderPayInfo(record);

					commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_CLINIC_PAY_SUCCESS);

				} else {

					logger.info("进入订单[{}]的退费子流程....", record.getOrderNo());
					try {

						Object refund = clinicService.buildRefundInfo(record);
						RefundService refundService = TradeCommonHoder.getInvokeRefundService();

						if (refund instanceof WechatPayRefund) {

							WechatPayRefundResponse wechatPayRefundResponse = refundService.wechatPayRefund((WechatPayRefund) refund);

							if (StringUtils.equals(wechatPayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
								// 退费成功
								record.setAgtRefundOrdNum(wechatPayRefundResponse.getAgtRefundOrderNo());
								record.setClinicStatus(ClinicConstant.STATE_PAY_CLOSED);
								record.setPayStatus(OrderConstant.STATE_REFUND);
								record.setRefundTime(System.currentTimeMillis());
								String oldLog = record.getHandleLog() == null ? "" : record.getHandleLog();
								record.setHandleLog(oldLog + " " + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + " 第3方退费成功;");
								clinicService.updateOrderPayInfo(record);

								commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_CLINIC_REFUND_SUCCESS);
							} else {
								// 退费失败
								handleHisException(record);
							}

						} else if (refund instanceof AlipayRefund) {

							AlipayRefundResponse alipayRefundResponse = refundService.alipayRefund((AlipayRefund) refund);

							if (StringUtils.equals(alipayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
								// 退费成功
								record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
								record.setClinicStatus(ClinicConstant.STATE_PAY_CLOSED);
								record.setPayStatus(OrderConstant.STATE_REFUND);
								record.setRefundTime(System.currentTimeMillis());
								String oldLog = record.getHandleLog() == null ? "" : record.getHandleLog();
								record.setHandleLog(oldLog + " " + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + " 第3方退费成功;");
								clinicService.updateOrderPayInfo(record);
							} else {
								// 退费失败
								handleHisException(record);
							}
						} else if (refund instanceof UnionpayRefund) {
							// TODO 新增
							UnionpayRefundResponse unionpayRefundResponse = refundService.unionpayRefund((UnionpayRefund) refund);

							if (StringUtils.equals(unionpayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
								// 退费成功
								record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
								record.setClinicStatus(ClinicConstant.STATE_PAY_CLOSED);
								record.setPayStatus(OrderConstant.STATE_REFUND);
								record.setRefundTime(System.currentTimeMillis());
								String oldLog = record.getHandleLog() == null ? "" : record.getHandleLog();
								record.setHandleLog(oldLog + " " + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + " 第3方退费成功;");
								clinicService.updateOrderPayInfo(record);
							} else {
								// 退费失败
								handleHisException(record);
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
						logger.info("退费线程执行失败, 订单号orderNo:{}, 错误信息message:{}, 引起原因:{}", new Object[] { record.getOrderNo(),
								e.getMessage(),
								e.getCause() });
						// 异常处理
						handleHisException(record);
					}

				}
			} else {

				logger.info("进入订单[{}]的退费子流程....", record.getOrderNo());
				try {

					Object refund = clinicService.buildRefundInfo(record);
					RefundService refundService = TradeCommonHoder.getInvokeRefundService();

					if (refund instanceof WechatPayRefund) {

						WechatPayRefundResponse wechatPayRefundResponse = refundService.wechatPayRefund((WechatPayRefund) refund);

						if (StringUtils.equals(wechatPayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
							// 退费成功
							record.setAgtRefundOrdNum(wechatPayRefundResponse.getAgtRefundOrderNo());
							record.setClinicStatus(ClinicConstant.STATE_PAY_CLOSED);
							record.setPayStatus(OrderConstant.STATE_REFUND);
							record.setRefundTime(System.currentTimeMillis());
							String oldLog = record.getHandleLog() == null ? "" : record.getHandleLog();
							record.setHandleLog(oldLog + " " + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + " 第3方退费成功;");
							clinicService.updateOrderPayInfo(record);

							commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_CLINIC_REFUND_SUCCESS);
						} else {
							// 退费失败
							handleHisException(record);
						}

					} else if (refund instanceof AlipayRefund) {

						AlipayRefundResponse alipayRefundResponse = refundService.alipayRefund((AlipayRefund) refund);

						if (StringUtils.equals(alipayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
							// 退费成功
							record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
							record.setClinicStatus(ClinicConstant.STATE_PAY_CLOSED);
							record.setPayStatus(OrderConstant.STATE_REFUND);
							record.setRefundTime(System.currentTimeMillis());
							String oldLog = record.getHandleLog() == null ? "" : record.getHandleLog();
							record.setHandleLog(oldLog + " " + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + " 第3方退费成功;");
							clinicService.updateOrderPayInfo(record);
						} else {
							// 退费失败
							handleHisException(record);
						}
					} else if (refund instanceof UnionpayRefund) {
						// TODO 新增
						UnionpayRefundResponse unionpayRefundResponse = refundService.unionpayRefund((UnionpayRefund) refund);

						if (StringUtils.equals(unionpayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
							// 退费成功
							record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
							record.setClinicStatus(ClinicConstant.STATE_PAY_CLOSED);
							record.setPayStatus(OrderConstant.STATE_REFUND);
							record.setRefundTime(System.currentTimeMillis());
							String oldLog = record.getHandleLog() == null ? "" : record.getHandleLog();
							record.setHandleLog(oldLog + " " + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + " 第3方退费成功;");
							clinicService.updateOrderPayInfo(record);
						} else {
							// 退费失败
							handleHisException(record);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					logger.info("退费线程执行失败, 订单号orderNo:{}, 错误信息message:{}, 引起原因:{}",
							new Object[] { record.getOrderNo(), e.getMessage(), e.getCause() });
					// 异常处理
					handleHisException(record);
				}
			}

		} else {
			record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_NEED_PERSON_HANDLE);
			record.setPayStatus(OrderConstant.STATE_REFUNDING);
			String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
			StringBuilder sbLog = new StringBuilder();
			sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
			sbLog.append(",HandleMsg:门诊支付回调，his缴费异常，进入退费子线程，查询his门诊订单出现异常，子线程退费中断...转为人工处理");
			record.setHandleLog(handleLog + sbLog.toString());
			logger.info("订单号：{},门诊支付回调，his缴费异常，进入退费子线程，查询his门诊订单出现异常，子线程退费中断...转为人工处理", record.getOrderNo());
			clinicService.updateOrderPayInfo(record);
		}

	}

	private Map<String, Object> queryHisClinicRecord(ClinicRecord record) {
		Map<String, Object> map = new HashMap<String, Object>();
		PayFee hisClinicRecord = null;
		Map<String, String> params = generateGetOrderStatusParams(record);
		Map<String, Object> interfaceResMap = clinicBizManager.getPayOrderStatus(params);
		Boolean isException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
		if (!isException) {
			boolean isSuccess = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
			if (isSuccess) {
				List<PayFee> records = (List<PayFee>) interfaceResMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
				if (!CollectionUtils.isEmpty(records)) {
					hisClinicRecord = records.get(0);
				}
			}
		}
		map.put("hisClinicRecord", hisClinicRecord);
		map.put("isException", isException);
		return map;
	}

	private Map<String, String> generateGetOrderStatusParams(ClinicRecord record) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("hospitalCode", record.getHospitalCode());
		map.put("branchHospitalCode", record.getBranchHospitalCode());
		map.put("patCardType", record.getCardType().toString());
		map.put("patCardNo", record.getCardNo().toString());
		// 接口：0所有，1微信，2支付宝。 	掌上医院：1微信，2支付宝
		map.put("payMode", record.getTradeMode().toString());
		map.put("beginDate", "");
		map.put("endDate", "");
		map.put("psOrdNum", record.getOrderNo());
		return map;
	}

	/**
	 * 出现异常处理
	 * @param record
	 */
	public void handleHisException(ClinicRecord record) {
		logger.info("处理退费异常：[{}]", record.getOrderNo());

		record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_REFUND);
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
		record.setHandleCount(1);
		StringBuilder sbLog = new StringBuilder();
		sbLog.append("HandleCount:1");
		sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
		sbLog.append(",HandleMsg:调用第3方交易平台退费接口退费异常,状态变更为第3方交易平台退费异常[STATE_EXCEPTION_REFUND=5];");
		record.setHandleLog(record.getHandleLog() + sbLog.toString());
		// 更新状态信息
		clinicService.updateOrderPayInfo(record);
	}

}
