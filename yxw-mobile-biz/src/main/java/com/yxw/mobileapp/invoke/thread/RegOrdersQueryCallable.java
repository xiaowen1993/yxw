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
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
//import com.yxw.mobileapp.biz.inpatient.entity.DepositRecord;
//import com.yxw.mobileapp.biz.inpatient.service.InpatientService;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.invoke.dto.outside.RegOrdersQueryResult;

/**
 * @Package: com.yxw.mobileapp.invoke.thread
 * @ClassName: OrdersQueryRunnable
 * @Statement: <p>挂号订单查询 根据</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月17日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegOrdersQueryCallable implements Callable<List<RegOrdersQueryResult>> {
	private RegisterService registerService = SpringContextHolder.getBean(RegisterService.class);

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
	private String startDate;

	/**
	 * 结束时间
	 */
	private String endDate;

	/**
	 * 表名
	 */
	private String hashTableName;

	/**
	 * 订单类型 0： 全部 1：预约  2：当班
	 */
	private String regType;

	public RegOrdersQueryCallable(String hospitalId, String branchCode, String tradeMode, String startDate, String endDate,
			String hashTableName, String regType) {
		super();
		this.hospitalId = hospitalId;
		this.branchCode = branchCode;
		this.tradeMode = tradeMode;
		this.startDate = startDate;
		this.endDate = endDate;
		this.hashTableName = hashTableName;
		this.regType = regType;
	}

	@Override
	public List<RegOrdersQueryResult> call() throws Exception {
		List<RegOrdersQueryResult> result = new ArrayList<RegOrdersQueryResult>();
		if (tradeMode.equals(ReceiveConstant.TRADEMODE_ALL) || tradeMode.equals(ReceiveConstant.TRADEMODE_APP)) {
			tradeMode = "";
		}
		if (regType.equals(ReceiveConstant.REGTYPE_ALL)) {
			regType = "";
		}
		List<RegisterRecord> records =
				registerService.findDownRegisterRecordByScheduleDate(hospitalId, branchCode, tradeMode, regType, startDate, endDate,
						hashTableName);
		result.addAll(listVoConvertForRegisterRecord(records));
		return result;
	}

	/**
	 * 挂号转换
	 * @param list
	 * @return
	 */
	private List<RegOrdersQueryResult> listVoConvertForRegisterRecord(List<RegisterRecord> list) {
		List<RegOrdersQueryResult> queryResults = new ArrayList<RegOrdersQueryResult>();
		if (list.size() > 0) {
			for (RegisterRecord record : list) {
				queryResults.add(voConvert(BizConstant.YYYYMMDD.format(record.getScheduleDate()), record.getTradeTime(),
						record.getTradeType(), String.valueOf(record.getTradeMode()), String.valueOf(record.getRegType()),
						record.getAgtOrdNum(), record.getAgtRefundOrdNum(), record.getHisOrdNo(), record.getRefundHisOrdNo(),
						record.getOrderNo(), record.getRefundOrderNo(), String.valueOf(record.getRealRegFee() + record.getRealTreatFee()),
						String.valueOf(record.getRefundTotalFee())));
			}
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
	private RegOrdersQueryResult voConvert(String bookDate, String tradeTime, String tradeType, String tradeMode, String regType,
			String agtOrdNum, String agtRefundOrdNum, String hisOrdNo, String refundHisOrdNo, String orderNo, String refundOrderNo,
			String payFee, String refundTotalFee) {
		RegOrdersQueryResult payQr = new RegOrdersQueryResult();
		payQr.setBookDate(bookDate);
		payQr.setTradeTime(tradeTime);
		payQr.setTradeType(tradeType);
		payQr.setTradeMode(tradeMode);
		payQr.setRegType(regType);
		payQr.setAgtPayOrdNum(setParams(agtOrdNum));
		payQr.setHisPayOrdNum(setParams(hisOrdNo));
		payQr.setPsPayOrdNum(setParams(orderNo));
		if (tradeType.equals("1")) {
			payQr.setPayTotalFee(payFee);
			payQr.setRefundTotalFee("");
		} else {
			payQr.setAgtRefundOrdNum(setParams(agtRefundOrdNum));
			payQr.setHisRefundOrdNum(setParams(refundHisOrdNo));
			payQr.setPsRefundOrdNum(setParams(refundOrderNo));
			payQr.setRefundTotalFee(refundTotalFee);
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getHashTableName() {
		return hashTableName;
	}

	public void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
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
