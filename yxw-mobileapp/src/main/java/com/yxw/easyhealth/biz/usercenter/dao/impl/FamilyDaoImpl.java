package com.yxw.easyhealth.biz.usercenter.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.easyhealth.biz.usercenter.dao.FamilyDao;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

@Repository
public class FamilyDaoImpl extends BaseDaoImpl<Family, String> implements FamilyDao {

	private Logger logger = LoggerFactory.getLogger(FamilyDaoImpl.class);
	private final static String SQLNAME_FIND_FAMILIES_BY_OPEN_ID = "findFamiliesByOpenId";
	private final static String SQLNAME_FIND_FAMILIES_BY_OPEN_ID_AND_ID_NO = "findFamiliesByOpenIdAndIdNo";
	private final static String SQLNAME_FIND_FAMILIES_BY_OPEN_ID_AND_NAME_AND_GUARD_ID_NO = "findFamiliesByOpenIDAndNameAndGuardIdNo";
	private final static String SQLNAME_FIND_SELF_INFO = "findSelfInfo";
	private final static String SQLNAME_FIND_FAMILY_INFO = "findFamilyInfo";
	private final static String SQLNAME_FIND_ALL_FAMILIES = "findAllFamilies";

	@Override
	public List<Family> findFamiliesByOpenId(String openId, String hashTableName, int state) {
		List<Family> list = null;
		try {
			Assert.notNull(openId, "openId不能为空");
			Assert.notNull(hashTableName, "hashTableName不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("hashTableName", hashTableName);
			params.put("state", state);
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_FAMILIES_BY_OPEN_ID), params);
		} catch (Exception e) {
			logger.error(String.format("通过openId获取绑定的家人出错！语句：%s", getSqlName(SQLNAME_FIND_FAMILIES_BY_OPEN_ID)), e);
			throw new SystemException(String.format("通过openId获取绑定的家人出错！语句：%s", getSqlName(SQLNAME_FIND_FAMILIES_BY_OPEN_ID)), e);
		}

		return list;
	}

	@Override
	public List<Family> findFamiliesByOpenIdAndIdNo(String openId, String hashTableName, int idType, String idNo, int state) {
		List<Family> list = null;
		try {
			Assert.notNull(openId, "openId不能为空");
			Assert.notNull(hashTableName, "hashTableName不能为空");
			Assert.notNull(idType, "idType不能为空");
			Assert.notNull(idNo, "idNo不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("hashTableName", hashTableName);
			params.put("idType", idType);
			params.put("idNo", idNo);
			params.put("state", state);
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_FAMILIES_BY_OPEN_ID_AND_ID_NO), params);
		} catch (Exception e) {
			logger.error(String.format("获取绑定的大人出错！语句：%s", getSqlName(SQLNAME_FIND_FAMILIES_BY_OPEN_ID_AND_ID_NO)), e);
			throw new SystemException(String.format("获取绑定的大人出错！语句：%s", getSqlName(SQLNAME_FIND_FAMILIES_BY_OPEN_ID_AND_ID_NO)), e);
		}

		return list;
	}

	@Override
	public List<Family> findFamiliesByOpenIDAndNameAndGuardIdNo(String openId, String hashTableName, String name, int guardIdType, String guardIdNo,
			int state) {
		List<Family> list = null;
		try {
			Assert.notNull(openId, "openId不能为空");
			Assert.notNull(hashTableName, "hashTableName不能为空");
			Assert.notNull(name, "name不能为空");
			Assert.notNull(guardIdType, "guardIdType不能为空");
			Assert.notNull(guardIdNo, "guardIdNo不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("hashTableName", hashTableName);
			params.put("name", name);
			params.put("guardIdType", guardIdType);
			params.put("guardIdNo", guardIdNo);
			params.put("state", state);
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_FAMILIES_BY_OPEN_ID_AND_NAME_AND_GUARD_ID_NO), params);
		} catch (Exception e) {
			logger.error(String.format("获取绑定的小孩信息出错！语句：%s", getSqlName(SQLNAME_FIND_FAMILIES_BY_OPEN_ID_AND_NAME_AND_GUARD_ID_NO)), e);
			throw new SystemException(String.format("获取绑定的小孩出错！语句：%s", getSqlName(SQLNAME_FIND_FAMILIES_BY_OPEN_ID_AND_NAME_AND_GUARD_ID_NO)), e);
		}

		return list;
	}

	@Override
	public Family findSelfInfo(String openId, String hashTableName) {
		Family family = null;
		try {
			Assert.notNull(openId, "openId不能为空");
			Assert.notNull(hashTableName, "hashTableName不能为空");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("hashTableName", hashTableName);
			family = sqlSession.selectOne(getSqlName(SQLNAME_FIND_SELF_INFO), params);
		} catch (Exception e) {
			logger.error(String.format("获取本人的信息出错！语句：%s", getSqlName(SQLNAME_FIND_SELF_INFO)), e);
			throw new SystemException(String.format("获取本人的信息出错！语句：%s", getSqlName(SQLNAME_FIND_SELF_INFO)), e);
		}

		return family;
	}

	@Override
	public Family findFamilyInfo(String familyId, String openId, String hashTableName) {
		Family family = null;
		try {
			Assert.notNull(familyId, "familyId不能为空");
			Assert.notNull(openId, "openId不能为空");
			Assert.notNull(hashTableName, "hashTableName不能为空");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("hashTableName", hashTableName);
			params.put("familyId", familyId);
			family = sqlSession.selectOne(getSqlName(SQLNAME_FIND_FAMILY_INFO), params);
		} catch (Exception e) {
			logger.error(String.format("获取家人的信息出错！语句：%s", getSqlName(SQLNAME_FIND_FAMILY_INFO)), e);
			throw new SystemException(String.format("获取家人的信息出错！语句：%s", getSqlName(SQLNAME_FIND_FAMILY_INFO)), e);
		}

		return family;
	}

	@Override
	public List<Family> findAllFamilies(String openId, String hashTableName, int state) {
		List<Family> list = null;
		try {
			Assert.notNull(openId, "openId不能为空");
			Assert.notNull(hashTableName, "hashTableName不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("hashTableName", hashTableName);
			params.put("state", state);
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_FAMILIES), params);
		} catch (Exception e) {
			logger.error(String.format("获取所有的家人信息（含本人）出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_FAMILIES)), e);
			throw new SystemException(String.format("获取所有的家人信息（含本人）出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_FAMILIES)), e);
		}

		return list;
	}

}
