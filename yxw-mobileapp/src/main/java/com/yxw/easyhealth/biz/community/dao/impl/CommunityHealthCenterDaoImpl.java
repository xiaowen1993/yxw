package com.yxw.easyhealth.biz.community.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.mobile.biz.community.CommunityHealthCenter;
import com.yxw.easyhealth.biz.community.dao.CommunityHealthCenterDao;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

@Repository
public class CommunityHealthCenterDaoImpl extends BaseDaoImpl<CommunityHealthCenter, String> implements CommunityHealthCenterDao {

	private static Logger logger = LoggerFactory.getLogger(CommunityHealthCenterDaoImpl.class);

	private final static String SQLNAME_FIND_BY_ID = "findById";
	private final static String SQLNAME_FIND_ALL = "findAll";

	private final static String SQLNAME_FIND_GROUP_BY_ADMINSTRATIVE_REGION = "findGroupByAdministrativeRegion";
	private final static String SQLNAME_FIND_BY_ADMINSTRATIVE_REGION = "findByAdministrativeRegion";

	@Override
	public CommunityHealthCenter findCommunityHealthCenterById(Map<String, Object> params) {
		try {
			Assert.notNull(params.get("id"), "id 不能为空");
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ID), params);
		} catch (Exception e) {
			logger.error(String.format("根据id查询社区中心信息出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ID)), e);
			throw new SystemException(String.format("根据id查询社区中心信息出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ID)), e);
		}
	}

	@Override
	public List<CommunityHealthCenter> findCommunityHealthCenterAll() {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL));
		} catch (Exception e) {
			logger.error(String.format("查询社区中心所有信息出错！语句：%s", getSqlName(SQLNAME_FIND_ALL)), e);
			throw new SystemException(String.format("查询社区中心所有信息出错！语句：%s", getSqlName(SQLNAME_FIND_ALL)), e);
		}
	}

	@Override
	public List<String> findGroupByAdministrativeRegion() {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_GROUP_BY_ADMINSTRATIVE_REGION));
		} catch (Exception e) {
			logger.error(String.format("根据社康中心信息分组得到各区 区名 查询出错！语句：%s", getSqlName(SQLNAME_FIND_GROUP_BY_ADMINSTRATIVE_REGION)), e);
			throw new SystemException(String.format("根据社康中心信息分组得到各区 区名 查询出错！语句：%s", getSqlName(SQLNAME_FIND_GROUP_BY_ADMINSTRATIVE_REGION)), e);
		}
	}

	@Override
	public List<CommunityHealthCenter> findByAdministrativeRegion(Map<String, Object> params) {
		try {
			//			Assert.notNull(params.get("administrativeRegion"),"地区名 AdministrativeRegion 不能为空");
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_ADMINSTRATIVE_REGION), params);
		} catch (Exception e) {
			logger.error(String.format("根据地区名字查询社康中心信息！语句：%s", getSqlName(SQLNAME_FIND_BY_ADMINSTRATIVE_REGION)), e);
			throw new SystemException(String.format("根据地区名字查询社康中心信息！语句：%s", getSqlName(SQLNAME_FIND_BY_ADMINSTRATIVE_REGION)), e);
		}
	}
}
