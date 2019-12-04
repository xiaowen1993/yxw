package com.yxw.insurance.biz.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.insurance.biz.dao.MzFeeDataDao;
import com.yxw.insurance.biz.entity.MzFeeData;

@Repository
public class MzFeeDataDaoImpl extends BaseDaoImpl<MzFeeData, String> implements MzFeeDataDao {
	private Logger logger = LoggerFactory.getLogger(MzFeeDataDaoImpl.class);

	private final static String SQLNAME_SELECT_DETAIL = "findMzFee";

	@Override
	public MzFeeData findMzFeeData(String openId, String mzFeeId) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("openId", openId);
			map.put("mzFeeId", mzFeeId);
			map.put("hashTableName", SimpleHashTableNameGenerator.getClinicRecordHashTable(openId, true));
			MzFeeData mzFeeData = sqlSession.selectOne(getSqlName(SQLNAME_SELECT_DETAIL), map);
			if (mzFeeData != null) {
				return mzFeeData;
			}
		} catch (Exception e) {
			logger.error(String.format("查询代缴费出错!语句：%s", getSqlName(SQLNAME_SELECT_DETAIL)), e);
			throw new SystemException(String.format("查询代缴费出错!语句：%s", getSqlName(SQLNAME_SELECT_DETAIL)), e);
		}
		return null;
	}

}
