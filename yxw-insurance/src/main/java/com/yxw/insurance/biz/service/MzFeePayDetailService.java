package com.yxw.insurance.biz.service;

import java.util.List;

import com.yxw.insurance.biz.entity.MzFeePayDetail;

public interface MzFeePayDetailService {
	/**
	 * 根据门诊缴费编号查询缴费明细
	 * @param mzFeeId
	 * @return
	 */
	public List<MzFeePayDetail> findMzFeePayDetail(String mzFeeId);
}
