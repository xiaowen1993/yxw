package com.yxw.insurance.biz.dao;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.insurance.biz.entity.MzFeeData;

public interface MzFeeDataDao extends BaseDao<MzFeeData, String> {
	
	
	/**
	 * 查询门诊信息
	 * @param openId
	 * @param mzFeeId 代缴id
	 * @return
	 */
	 public MzFeeData findMzFeeData(String openId,String mzFeeId);
}
