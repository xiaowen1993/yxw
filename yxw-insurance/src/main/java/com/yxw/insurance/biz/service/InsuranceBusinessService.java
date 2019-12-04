package com.yxw.insurance.biz.service;

import com.yxw.commons.dto.outside.PaidMZDetailReuslt;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.insurance.biz.entity.ClaimOrder;

public interface InsuranceBusinessService {

	/**
	 * 申请理赔，生成订单
	 * @param params
	 * @return
	 */
	public ClaimOrder applyClaim(PaidMZDetailReuslt params);

	/**
	 * 上传就医处方明细
	 * @param order
	 */
	public boolean uploadPrescriptionDetail(ClaimOrder order);

	/**
	 * 结算交易
	 */
	public boolean settlementTransaction(ClaimOrder order);

	public RuleQuery getRuleQueryByHospitalCode(String hospitalCode);

	/**
	 * 获取医院的所有接入平台信息
	 * 
	 * @param hospitalId
	 * @return
	 */
	public HospIdAndAppSecretVo getHospitalEasyHealthAppInfo(String hospitalId, String appCode);
}
