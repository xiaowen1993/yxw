package com.yxw.insurance.chinalife.interfaces.service;

import com.yxw.insurance.chinalife.interfaces.vo.request.HospitalFeeDetailInfoRequest;
import com.yxw.insurance.chinalife.interfaces.vo.request.HospitalRegistrationRequest;
import com.yxw.insurance.chinalife.interfaces.vo.request.HospitalSettlementRequest;
import com.yxw.insurance.chinalife.interfaces.vo.response.InsuranceResponse;

/**
 * 保险接口
 * 
 * @author YangXuewen
 *
 */
public interface InsuranceService {
	
	/**
	 * 就医登记
	 * @param hospitalRegistration
	 * @return
	 */
	public InsuranceResponse hospitalRegistration(HospitalRegistrationRequest hospitalRegistrationRequest);
	
	/**
	 * 就医处方明细上传
	 * @param hospitalFeeDetailInfo
	 * @return
	 */
	public InsuranceResponse hospitalFeeDetailInfo(HospitalFeeDetailInfoRequest hospitalFeeDetailInfoRequest);
	
	/**
	 * 就医结算
	 * @param hospitalSettlement
	 * @return
	 */
	public InsuranceResponse hospitalSettlement(HospitalSettlementRequest hospitalSettlementRequest);

}
