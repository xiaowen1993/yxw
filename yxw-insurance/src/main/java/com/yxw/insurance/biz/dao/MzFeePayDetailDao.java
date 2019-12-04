package com.yxw.insurance.biz.dao;

import java.util.List;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.insurance.biz.entity.MzFeePayDetail;

public interface MzFeePayDetailDao extends BaseDao<MzFeePayDetail, String> {

	/**
	 * 根据门诊缴费编号查询缴费明细
	 * @param mzFeeId
	 * @return
	 */
	public List<MzFeePayDetail> findMzFeePayDetail(String mzFeeId);
}
