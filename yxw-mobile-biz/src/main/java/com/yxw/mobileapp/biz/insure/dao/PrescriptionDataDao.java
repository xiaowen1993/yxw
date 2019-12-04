package com.yxw.mobileapp.biz.insure.dao;

import java.util.List;

import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.PrescriptionDetailReuslt;
import com.yxw.framework.mvc.dao.BaseDao;

public interface PrescriptionDataDao extends BaseDao<PrescriptionDetailReuslt, String> {

	/**
	 * 查询缴费明细
	 * @param params
	 * @return
	 */
	public List<PrescriptionDetailReuslt> getPrescriptionDetail(PaidMZDetailCommParams params);
}
