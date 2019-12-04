package com.yxw.insurance.biz.service;

import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;

public interface ClinicService {

	/**
	 * 通过订单号找到缴费订单
	 * @param orderNo
	 * @return
	 */
	public ClinicRecord findRecordByOrderNo(String orderNo);

}
