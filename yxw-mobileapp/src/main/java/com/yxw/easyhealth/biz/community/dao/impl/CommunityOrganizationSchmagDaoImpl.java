package com.yxw.easyhealth.biz.community.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.mobile.biz.community.CommunityOrganizationSchmag;
import com.yxw.easyhealth.biz.community.dao.CommunityOrganizationSchmagDao;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

@Repository
public class CommunityOrganizationSchmagDaoImpl extends BaseDaoImpl<CommunityOrganizationSchmag, String> implements CommunityOrganizationSchmagDao {

	private static Logger logger = LoggerFactory.getLogger(CommunityOrganizationSchmagDaoImpl.class);

	private final static String SQLNAME_FIND_BY_ID = "findById";
	private final static String SQLNAME_FIND_ALL = "findAll";
	private final static String SQLNAME_ADD = "add";

	private final static String SQLNAME_UPDATE = "update";
	private final static String SQLNAME_DELETE = "delete";

	private final static String SQLNAME_FIND_ORGANIZAION_BY_CONDITION = "findOrganizaionByCondition";

	@Override
	public CommunityOrganizationSchmag findCommunityOrganizationSchmagById(Map<String, Object> params) {
		try {
			Assert.notNull(params.get("id"), "id 不能为空");
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ID), params);
		} catch (Exception e) {
			logger.error(String.format("根据id查询对应社康中心医生排班信息出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ID)), e);
			throw new SystemException(String.format("根据id查询对应社康中心医生排班信息出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ID)), e);
		}
	}

	@Override
	public List<CommunityOrganizationSchmag> findCommunityOrganizationSchmagAll() {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL));
		} catch (Exception e) {
			logger.error(String.format("查询对应社康中心医生排班所有信息出错！语句：%s", getSqlName(SQLNAME_FIND_ALL)), e);
			throw new SystemException(String.format("查询对应社康中心医生排班所有信息出错！语句：%s", getSqlName(SQLNAME_FIND_ALL)), e);
		}
	}

	@Override
	public List<CommunityOrganizationSchmag> findOrganizaionByCondition(Map<String, Object> params) {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ORGANIZAION_BY_CONDITION), params);
		} catch (Exception e) {
			logger.error(String.format("根据输入条件查询对应社康中心医生排班信息！语句：%s", getSqlName(SQLNAME_FIND_ORGANIZAION_BY_CONDITION)), e);
			throw new SystemException(String.format("根据输入条件查询对应社康中心医生排班信息！语句：%s", getSqlName(SQLNAME_FIND_ORGANIZAION_BY_CONDITION)), e);
		}
	}

	@Override
	public void addOrganizaion(CommunityOrganizationSchmag organizationSchmag) {
		try {
			sqlSession.selectList(getSqlName(SQLNAME_ADD), organizationSchmag);
		} catch (Exception e) {
			logger.error(String.format("添加医生排班信息！语句：%s", getSqlName(SQLNAME_ADD)), e);
			throw new SystemException(String.format("添加医生排班信息！语句：%s", getSqlName(SQLNAME_ADD)), e);
		}
	}

	@Override
	public void updateOrganizaion(CommunityOrganizationSchmag organizationSchmag) {
		try {
			sqlSession.selectList(getSqlName(SQLNAME_UPDATE), organizationSchmag);
		} catch (Exception e) {
			logger.error(String.format("修改医生排班信息！语句：%s", getSqlName(SQLNAME_UPDATE)), e);
			throw new SystemException(String.format("修改医生排班信息！语句：%s", getSqlName(SQLNAME_UPDATE)), e);
		}
	}

	@Override
	public void deleteOrganizaion(Map<String, Object> params) {
		try {
			Assert.notNull(params.get("id"), "id 不能为空");
			sqlSession.selectList(getSqlName(SQLNAME_DELETE), params);
		} catch (Exception e) {
			logger.error(String.format("删除医生排班信息！语句：%s", getSqlName(SQLNAME_DELETE)), e);
			throw new SystemException(String.format("删除医生排班信息！语句：%s", getSqlName(SQLNAME_DELETE)), e);
		}
	}
}
