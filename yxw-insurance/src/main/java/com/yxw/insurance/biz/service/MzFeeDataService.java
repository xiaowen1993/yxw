package com.yxw.insurance.biz.service;

import com.yxw.insurance.biz.entity.MzFeeData;

public interface MzFeeDataService {
	/**
	 * 查询门诊信息
	 * @param openId
	 * @param mzFeeId 代缴id
	 * @return
	 */
	 public MzFeeData findMzFeeData(String openId,String mzFeeId);
}
