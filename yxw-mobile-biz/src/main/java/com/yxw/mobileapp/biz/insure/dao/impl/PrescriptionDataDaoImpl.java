package com.yxw.mobileapp.biz.insure.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.PrescriptionDetailReuslt;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.mobileapp.biz.insure.dao.PrescriptionDataDao;

@Repository
public class PrescriptionDataDaoImpl extends BaseDaoImpl<PrescriptionDetailReuslt, String> implements PrescriptionDataDao {

	private Logger logger = LoggerFactory.getLogger(PrescriptionDataDaoImpl.class);
	private final String SQLNAME_FIND_PAYDETAIL = "getPrescriptionDetail";

	@Override
	public List<PrescriptionDetailReuslt> getPrescriptionDetail(PaidMZDetailCommParams params) {
		List<PrescriptionDetailReuslt> result = null;
		try {
			params.setHashTableName(SimpleHashTableNameGenerator.getClinicRecordHashTable(params.getOpenId(), true));
			params.setHashTableName2(SimpleHashTableNameGenerator.getMedicalCardHashTable(params.getOpenId(), true));
			result = sqlSession.selectList(getSqlName(SQLNAME_FIND_PAYDETAIL), params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("查询处方明细出错!语句：%s", getSqlName(SQLNAME_FIND_PAYDETAIL)), e);
			throw new SystemException(String.format("查询处方明细出错!语句：%s", getSqlName(SQLNAME_FIND_PAYDETAIL)), e);
		}
		return result;
	}

}
