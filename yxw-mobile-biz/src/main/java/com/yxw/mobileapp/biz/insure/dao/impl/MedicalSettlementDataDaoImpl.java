package com.yxw.mobileapp.biz.insure.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.MedicalSettlementReuslt;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.mobileapp.biz.insure.dao.MedicalSettlementDataDao;

@Repository
public class MedicalSettlementDataDaoImpl extends BaseDaoImpl<MedicalSettlementReuslt, String> implements MedicalSettlementDataDao {

	private Logger logger = LoggerFactory.getLogger(MedicalSettlementDataDaoImpl.class);
	private final String SQLNAME_GET_MEDICAL_SETTLEMENT = "getMedicalSettlement";

	@Override
	public List<MedicalSettlementReuslt> getMedicalSettlement(PaidMZDetailCommParams params) {
		List<MedicalSettlementReuslt> result = null;
		try {
			params.setHashTableName(SimpleHashTableNameGenerator.getClinicRecordHashTable(params.getOpenId(), true));
			params.setHashTableName2(SimpleHashTableNameGenerator.getMedicalCardHashTable(params.getOpenId(), true));
			result = sqlSession.selectList(getSqlName(SQLNAME_GET_MEDICAL_SETTLEMENT), params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("查询就医结算出错!语句：%s", getSqlName(SQLNAME_GET_MEDICAL_SETTLEMENT)), e);
			throw new SystemException(String.format("查询就医结算出错!语句：%s", getSqlName(SQLNAME_GET_MEDICAL_SETTLEMENT)), e);
		}
		return result;
	}

}
