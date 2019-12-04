package com.yxw.platform.app.optional.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.app.optional.AppOptional;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.app.optional.dao.AppOptionalDao;

@Repository
public class AppOptionalDaoImpl extends BaseDaoImpl<AppOptional, Integer> implements AppOptionalDao {
	private Logger logger = LoggerFactory.getLogger(AppOptionalDaoImpl.class);
	
	private final static String SQLNAME_FIND_BY_MODULE_ID = "findByModuleId";

	@Override
	public List<AppOptional> findByModuleId(String moduleId) {
		try {
			Assert.notNull(moduleId);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_MODULE_ID), moduleId);
		} catch (Exception e) {
			logger.error(String.format("根据模块ID查询APP功能出错！语句：%s", getSqlName(SQLNAME_FIND_BY_MODULE_ID)), e);
			throw new SystemException(String.format("根据模块ID查询APP功能出错！语句：%s", getSqlName(SQLNAME_FIND_BY_MODULE_ID)), e);
		}
	}


	

}
