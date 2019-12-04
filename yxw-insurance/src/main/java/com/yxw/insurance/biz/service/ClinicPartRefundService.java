package com.yxw.insurance.biz.service;

public interface ClinicPartRefundService {

	/**
	 * 检测是否还能够进行退费操作
	 * @param refundFee
	 * @param orderNo
	 * @param hospitalCode
	 * @return
	 */
	public Integer getRefundedFee(String orderNo, String hospitalCode);
}
