package com.yxw.easyhealth.biz.usercenter.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.mobile.biz.usercenter.People;
import com.yxw.easyhealth.biz.usercenter.dao.PeopleDao;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

@Repository
public class PeopleDaoImpl extends BaseDaoImpl<People, String> implements PeopleDao {

	private Logger logger = LoggerFactory.getLogger(PeopleDaoImpl.class);

	private final static String SQLNAME_FIND_BY_ID_TYPE_AND_ID_NO = "findByIdTypeAndIdNo";
	private final static String SQLNAME_FIND_BY_NAME_AND_GUARD_ID_TYPE_AND_GUARD_ID_NO = "findByNameAndGuardIdTypeAndGuardIdNo";

	@Override
	public People findByIdTypeAndIdNo(int idType, String idNo, String hashTableName) {
		People people = null;

		try {
			Assert.notNull(idType, "idType 不能为空");
			Assert.notNull(idNo, "idNo 不能为空");
			Assert.notNull(hashTableName, "hashTableName 不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("idType", idType);
			params.put("idNo", idNo);
			params.put("hashTableName", hashTableName);
			people = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ID_TYPE_AND_ID_NO), params);
		} catch (Exception e) {
			logger.error(String.format("根据证件号码获取人的信息出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ID_TYPE_AND_ID_NO)), e);
			throw new SystemException(String.format("根据证件号码获取人的信息出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ID_TYPE_AND_ID_NO)), e);
		}

		return people;
	}

	@Override
	public People findByNameAndGuardIdTypeAndGuardIdNo(String name, int idType, String guardIdNo, String hashTableName) {
		People people = null;

		try {
			Assert.notNull(name, "name 不能为空");
			Assert.notNull(idType, "idType 不能为空");
			Assert.notNull(guardIdNo, "guardIdNo 不能为空");
			Assert.notNull(hashTableName, "hashTableName 不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", name);
			params.put("idType", idType);
			params.put("guardIdNo", guardIdNo);
			params.put("hashTableName", hashTableName);
			people = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ID_TYPE_AND_ID_NO), params);
		} catch (Exception e) {
			logger.error(String.format("根据监护人证件号码获取人的信息出错！语句：%s", getSqlName(SQLNAME_FIND_BY_NAME_AND_GUARD_ID_TYPE_AND_GUARD_ID_NO)), e);
			throw new SystemException(String.format("根据监护人证件号码获取人的信息出错！语句：%s", getSqlName(SQLNAME_FIND_BY_NAME_AND_GUARD_ID_TYPE_AND_GUARD_ID_NO)), e);
		}

		return people;
	}

}
