package com.yxw.insurance.biz.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.insurance.biz.dao.ClinicRecordDao;

@Repository
public class ClinicRecordDaoImpl extends BaseDaoImpl<ClinicRecord, String> implements ClinicRecordDao {

	private Logger logger = LoggerFactory.getLogger(ClinicRecordDaoImpl.class);

	private final static String SQLNAME_FIND_BY_ORDERNO = "findByOrderNo";

	@Override
	public ClinicRecord findByOrderNo(ClinicRecord record) {
		ClinicRecord clinicRecord = null;
		try {
			Assert.notNull(record.getOrderNo());
			Integer orderNoHashVal = Math.abs(record.getOrderNo().hashCode());
			Integer splitKeyVal = Integer.valueOf(OrderNoGenerator.getHashVal(record.getOrderNo()));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orderNo", record.getOrderNo());
			paramMap.put("orderNoHashVal", orderNoHashVal);
			if (logger.isDebugEnabled()) {
				logger.debug("hashVal:" + splitKeyVal + "  hashTableName :"
						+ SimpleHashTableNameGenerator.getRegRecordHashTable(splitKeyVal, false));
			}
			paramMap.put("hashTableName", SimpleHashTableNameGenerator.getClinicRecordHashTable(splitKeyVal, false));
			clinicRecord = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ORDERNO), paramMap);

			return clinicRecord;
		} catch (Exception e) {
			logger.error(String.format("通过orderNo和openId查询门诊记录出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ORDERNO)), e);
			throw new SystemException(String.format("通过orderNo和openId查询门诊记录出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ORDERNO)), e);
		}
	}

}
