package com.yxw.mobileapp.biz.insure.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.PaidMZDetailReuslt;
import com.yxw.commons.dto.outside.PrescriptionDetailReuslt;
import com.yxw.commons.dto.outside.MedicalSettlementReuslt;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.insure.dao.InsureDataDao;
import com.yxw.mobileapp.biz.insure.dao.PrescriptionDataDao;
import com.yxw.mobileapp.biz.insure.dao.MedicalSettlementDataDao;
import com.yxw.mobileapp.biz.insure.service.InsureDataService;

@Service(value="insureDataService")
public class InsureDataServiceImpl implements InsureDataService {


	private InsureDataDao insureDataDao = SpringContextHolder.getBean(InsureDataDao.class);
	
	private MedicalSettlementDataDao medicalSettlementDataDao = SpringContextHolder.getBean(MedicalSettlementDataDao.class);
	
	private PrescriptionDataDao prescriptionDataDao = SpringContextHolder.getBean(PrescriptionDataDao.class);
	
	/**
	 * 就医登记
	 */
	@Override
	public PaidMZDetailReuslt getPaidMZDetail(PaidMZDetailCommParams params) {
		return insureDataDao.getPaidMZDetail(params);
	}
	
	/**
	 * 处方明细
	 */
	 
	@Override
	public List<PrescriptionDetailReuslt> getPrescriptionDetail(PaidMZDetailCommParams params) {
		return prescriptionDataDao.getPrescriptionDetail(params);
	}
	
	/**
	 * 就医结算
	 */
	@Override
	public List<MedicalSettlementReuslt> getMedicalSettlement(PaidMZDetailCommParams params) {
		return medicalSettlementDataDao.getMedicalSettlement(params);
	}

}
