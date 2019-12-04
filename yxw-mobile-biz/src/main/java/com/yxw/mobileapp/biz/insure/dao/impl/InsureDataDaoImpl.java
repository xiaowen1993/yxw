package com.yxw.mobileapp.biz.insure.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.PaidMZDetailReuslt;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.mobileapp.biz.insure.dao.InsureDataDao;

@Repository
public class InsureDataDaoImpl extends BaseDaoImpl<PaidMZDetailReuslt, String> implements InsureDataDao {

	private Logger logger = LoggerFactory.getLogger(InsureDataDaoImpl.class);
	private final String SQLNAME_FIND_PAYDETAIL = "getPaidMZDetail";

	@Override
	public PaidMZDetailReuslt getPaidMZDetail(PaidMZDetailCommParams params) {
		PaidMZDetailReuslt result = null;
		try {
			params.setHashTableName(SimpleHashTableNameGenerator.getClinicRecordHashTable(params.getOpenId(), true));
			params.setHashTableName2(SimpleHashTableNameGenerator.getMedicalCardHashTable(params.getOpenId(), true));
			result = sqlSession.selectOne(getSqlName(SQLNAME_FIND_PAYDETAIL), params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("查询缴费明细出错!语句：%s", getSqlName(SQLNAME_FIND_PAYDETAIL)), e);
			throw new SystemException(String.format("查询缴费明细出错!语句：%s", getSqlName(SQLNAME_FIND_PAYDETAIL)), e);
		}
		return result;
	}
}
