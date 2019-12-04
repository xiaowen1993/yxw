package com.yxw.mobileapp.biz.register.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.mobileapp.biz.register.dao.StopRegisterRecordDao;
import com.yxw.mobileapp.biz.register.entity.StopRegisterRecord;

@Repository(value = "stopRegisterRecordDao")
public class StopRegisterRecordDaoImpl extends BaseDaoImpl<StopRegisterRecord, String> implements StopRegisterRecordDao {
	private static final Logger logger = LoggerFactory.getLogger(StopRegisterRecordDaoImpl.class);
	private final static String SQLNAME_FIND_BY_PARAMS = "findByParams";
	private final static String SQLNAME_DELETE_BY_LAUNCH_TIME = "deleteByLaunchTime";

	@Override
	public void deleteByLaunchTime(String min, String max) {
		Assert.notNull(min);
		Assert.notNull(max);
		Map<String, String> params = new HashMap<>(2);
		params.put("minLaunchTime", min);
		params.put("maxLaunchTime", max);
		try {
			sqlSession.delete(getSqlName(SQLNAME_DELETE_BY_LAUNCH_TIME), params);
		} catch (Exception e) {
			logger.error(String.format("删除停诊记录出错!语句：%s", getSqlName(SQLNAME_DELETE_BY_LAUNCH_TIME)), e);
			throw new SystemException(String.format("删除停诊记录出错!语句：%s", getSqlName(SQLNAME_DELETE_BY_LAUNCH_TIME)), e);
		} finally {
			params.clear();
		}
	}
}
