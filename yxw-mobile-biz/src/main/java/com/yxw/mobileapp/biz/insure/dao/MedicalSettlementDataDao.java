package com.yxw.mobileapp.biz.insure.dao;

import java.util.List;

import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.MedicalSettlementReuslt;
import com.yxw.framework.mvc.dao.BaseDao;

public interface MedicalSettlementDataDao extends BaseDao<MedicalSettlementReuslt, String> {

	/**
	 * 查询缴费明细
	 * @param params
	 * @return
	 */
	public List<MedicalSettlementReuslt> getMedicalSettlement(PaidMZDetailCommParams params);
}
