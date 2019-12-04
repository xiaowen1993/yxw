package com.yxw.mobileapp.biz.clinic.service;

import com.yxw.commons.entity.mobile.biz.clinic.ClinicPartRefundRecord;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;

public interface ClinicPartRefundService {
	/**
	 * 保存成功的部分退费订单
	 * @param record
	 * @param refundHisOrdNo
	 * @param refundFee
	 * @param agtRefundOrdNum
	 * @param curTime
	 * @return
	 */
	public ClinicPartRefundRecord saveSuccessPartRefundRecord(ClinicRecord record, String refundHisOrdNo, String agtRefundOrdNum, Long curTime);

	/**
	 * 保存失败的部分退费订单
	 * @param record
	 * @param refundHisOrdNo
	 * @param refundFee
	 * @param curTime
	 * @return
	 */
	public ClinicPartRefundRecord saveFailPartRefundRecord(ClinicRecord record, String refundHisOrdNo, Long curTime);

	/**
	 * 检测是否还能够进行退费操作
	 * @param refundFee
	 * @param orderNo
	 * @param hospitalCode
	 * @return
	 */
	public Integer getRefundedFee(String orderNo, String hospitalCode);

	public ClinicPartRefundRecord findByRefundOrderNo(String hospitalCode, String hisOrdNo, String orderNo);

	public void updateRecord(ClinicPartRefundRecord record);

}
