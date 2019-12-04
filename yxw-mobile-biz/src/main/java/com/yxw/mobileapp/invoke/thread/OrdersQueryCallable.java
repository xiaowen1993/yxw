/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.invoke.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.ReceiveConstant;
import com.yxw.commons.dto.outside.OrdersQueryResult;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;
import com.yxw.mobileapp.biz.register.service.RegisterService;

/**
 * @Package: com.yxw.mobileapp.invoke.thread
 * @ClassName: OrdersQueryRunnable
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月17日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class OrdersQueryCallable implements Callable<List<OrdersQueryResult>> {
	private RegisterService registerService = SpringContextHolder.getBean(RegisterService.class);
	private ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);
	//	private InpatientService inpatientService = SpringContextHolder.getBean(InpatientService.class);

	/**
	 * 医院ID
	 */
	private String hospitalId;

	/**
	 * 分院code
	 */
	private String branchCode;

	/**
	 * 支付类型
	 */
	private String tradeMode;

	/**
	 * 开始时间
	 */
	private String startTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 表名
	 */
	private String hashTableName;

	/**
	 * 订单类型 0： 全部 1：挂号  2：门诊
	 */
	private String orderMode;

	public OrdersQueryCallable(String hospitalId, String branchCode, String tradeMode, String startTime, String endTime,
			String hashTableName, String orderMode) {
		super();
		this.hospitalId = hospitalId;
		this.branchCode = branchCode;
		this.tradeMode = tradeMode;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hashTableName = hashTableName;
		this.orderMode = orderMode;
	}

	@Override
	public List<OrdersQueryResult> call() throws Exception {
		List<OrdersQueryResult> result = new ArrayList<OrdersQueryResult>();
		if (tradeMode.equals(ReceiveConstant.TRADEMODE_ALL) || tradeMode.equals(ReceiveConstant.TRADEMODE_APP)) {
			tradeMode = "";
		}
		if (orderMode.equals(String.valueOf(BizConstant.BIZ_TYPE_REGISTER))) {
			List<RegisterRecord> list =
					registerService.findDownRegisterRecord(hospitalId, branchCode, tradeMode, startTime, endTime, hashTableName);
			result.addAll(listVoConvertForRegisterRecord(list));
		} else if (orderMode.equals(String.valueOf(BizConstant.BIZ_TYPE_CLINIC))) {
			List<ClinicRecord> list =
					clinicService.findDownPaidRecord(hospitalId, branchCode, tradeMode, startTime, endTime, hashTableName);
			result.addAll(listVoConvertForClinicRecord(list));
		} else if (orderMode.equals(String.valueOf(BizConstant.BIZ_TYPE_DEPOSIT))) {
			//			List<DepositRecord> list = inpatientService.findDownPaidRecord(hospitalId, branchCode, tradeMode, startTime, endTime, hashTableName);
			//			result.addAll(listVoConvert(list, orderMode));
		} else {

		}
		return result;
	}

	/**
	 * 挂号转换
	 * @param list
	 * @return
	 */
	private List<OrdersQueryResult> listVoConvertForRegisterRecord(List<RegisterRecord> list) {
		List<OrdersQueryResult> queryResults = new ArrayList<OrdersQueryResult>();
		if (list.size() > 0) {
			for (RegisterRecord record : list) {
				queryResults.add(voConvert(record.getTradeTime(), record.getTradeType(), record.getTradeMode(), record.getAgtOrdNum(),
						record.getAgtRefundOrdNum(), record.getHisOrdNo(), record.getRefundHisOrdNo(), record.getOrderNo(),
						record.getRefundOrderNo(), String.valueOf(record.getRealRegFee() + record.getRealTreatFee()),
						String.valueOf(record.getRefundTotalFee())));
			}
		}
		return queryResults;
	}

	/**
	 * 门诊转换
	 * @param list
	 * @return
	 */
	private List<OrdersQueryResult> listVoConvertForClinicRecord(List<ClinicRecord> list) {
		List<OrdersQueryResult> queryResults = new ArrayList<OrdersQueryResult>();
		for (ClinicRecord record : list) {
			queryResults.add(voConvert(record.getTradeTime(), record.getTradeType(), record.getTradeMode(), record.getAgtOrdNum(),
					record.getAgtRefundOrdNum(), record.getHisOrdNo(), record.getRefundHisOrdNo(), record.getOrderNo(),
					record.getRefundOrderNo(), String.valueOf(record.getPayFee()), String.valueOf(record.getRefundTotalFee())));
		}
		return queryResults;
	}

	/**
	 * 参数写入
	 * @param tradeTime
	 * @param tradeType
	 * @param tradeMode
	 * @param agtOrdNum
	 * @param agtRefundOrdNum
	 * @param hisOrdNo
	 * @param refundHisOrdNo
	 * @param orderNo
	 * @param refundOrderNo
	 * @param payFee
	 * @param refundTotalFee
	 * @return
	 */
	private OrdersQueryResult voConvert(String tradeTime, String tradeType, Integer tradeMode, String agtOrdNum, String agtRefundOrdNum,
			String hisOrdNo, String refundHisOrdNo, String orderNo, String refundOrderNo, String payFee, String refundTotalFee) {
		OrdersQueryResult payQr = new OrdersQueryResult();
		payQr.setTradeTime(tradeTime);
		payQr.setTradeType(tradeType);
		payQr.setOrderMode(String.valueOf(orderMode));
		payQr.setTradeMode(tradeMode);
		payQr.setAgtPayOrdNum(setParams(agtOrdNum));
		payQr.setHisPayOrdNum(setParams(hisOrdNo));
		payQr.setPsPayOrdNum(setParams(orderNo));
		if (tradeType.equals("1")) {
			payQr.setPayTotalFee(payFee);
		} else {
			payQr.setRefundTotalFee(refundTotalFee);
			payQr.setAgtRefundOrdNum(setParams(agtRefundOrdNum));
			payQr.setHisRefundOrdNum(setParams(refundHisOrdNo));
			payQr.setPsRefundOrdNum(setParams(refundOrderNo));
			payQr.setPayTotalFee("");
		}
		return payQr;
	}

	private String setParams(String params) {
		if (StringUtils.isBlank(params)) {
			return "";
		} else {
			return params;
		}
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHashTableName() {
		return hashTableName;
	}

	public void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	public String getOrderMode() {
		return orderMode;
	}

	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

}
