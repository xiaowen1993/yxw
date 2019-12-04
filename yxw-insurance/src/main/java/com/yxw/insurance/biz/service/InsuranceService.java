package com.yxw.insurance.biz.service;

import java.util.List;

import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.MedicalSettlementReuslt;
import com.yxw.commons.dto.outside.PaidMZDetailReuslt;
import com.yxw.commons.dto.outside.PrescriptionDetailReuslt;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.insurance.biz.dto.inside.ApplyClaimParams;
import com.yxw.insurance.biz.dto.inside.ApplyMedicalSettlementParams;
import com.yxw.insurance.biz.dto.inside.ApplyPrescriptionParams;

public interface InsuranceService {

	/**
	 * 获取门诊缴费记录详情
	 * 
	 * @return
	 */
	public PaidMZDetailReuslt getMZPayFeeDetail(PaidMZDetailCommParams params);

	/**
	 * 获取处方记录详情
	 * 
	 * @return
	 */
	public List<PrescriptionDetailReuslt> getCFRecordDetail(PaidMZDetailCommParams params);

	/**
	 * 国寿-就医登记
	 * 
	 * @param params
	 * @return
	 */
	public String applyClaim(ApplyClaimParams params);

	/**
	 * 国寿-上传处方处方明细
	 * 
	 * @param params
	 * @return
	 */
	public boolean applyPrescription(List<ApplyPrescriptionParams> params);

	/**
	 * 获取就医结算数据
	 * 
	 * @param params
	 * @return
	 */
	public List<MedicalSettlementReuslt> getMedicalSettlement(PaidMZDetailCommParams params);

	/**
	 * 国寿-就医结算
	 * 
	 * @param params
	 * @return
	 */
	public boolean applyMedicalSettlement(List<ApplyMedicalSettlementParams> params);

	/**
	 * 查询缴费列表
	 * @param parmas
	 * @return
	 */
	public List<ClinicRecord> getMZPayFeeList(PaidMZDetailCommParams params);

	/**
	 * 更新理赔状态
	 * @param params
	 * @return
	 */
	public boolean updateClaimStatus(PaidMZDetailReuslt params);
}
