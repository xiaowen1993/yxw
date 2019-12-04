package com.yxw.mobileapp.biz.insure.service;

import java.util.List;

import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.PaidMZDetailReuslt;
import com.yxw.commons.dto.outside.PrescriptionDetailReuslt;
import com.yxw.commons.dto.outside.MedicalSettlementReuslt;

public interface InsureDataService {
	/**
	 * 查询缴费明细
	 * @param params
	 * @return
	 */
	public PaidMZDetailReuslt getPaidMZDetail(PaidMZDetailCommParams params);
	
	
	/**
	 * 查询处方明细
	 * @param params
	 * @return
	 */
	public List<PrescriptionDetailReuslt> getPrescriptionDetail(PaidMZDetailCommParams params);
	
	/**
	 * 就医结算
	 * @param params
	 * @return
	 */
	public List<MedicalSettlementReuslt> getMedicalSettlement(PaidMZDetailCommParams params) ;
}
