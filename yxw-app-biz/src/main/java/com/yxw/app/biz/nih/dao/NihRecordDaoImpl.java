package com.yxw.app.biz.nih.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.mobile.biz.nih.NihRecord;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

@Repository
public class NihRecordDaoImpl extends BaseDaoImpl<NihRecord, String> implements NihRecordDao {
	
	private static Logger logger = LoggerFactory.getLogger(NihRecordDao.class);
	
	private final static String SQLNAME_FIND_BY_USERID_AND_APPCODE = "findByUserIdAndAppCode";
	private final static String SQLNAME_FIND_BY_APPCODE = "findByAppCode";
	private final static String SQLNAME_FIND_BY_USERID = "findByUserId";

	@Override
	public List<NihRecord> findByUserIdAndAppCode(String userId, String appCode, String tableName) {
		List<NihRecord> records = null;
		try {
			Assert.notNull(tableName, "hashTableName is null.");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("hashTableName", tableName);
			param.put("userId", userId);
			param.put("appCode", appCode);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_USERID_AND_APPCODE), param);
		} catch (Exception e) {
			logger.error(String.format("查询用户单个系统的挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_USERID_AND_APPCODE)), e);
			throw new SystemException(String.format("查询用户单个系统的挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_USERID_AND_APPCODE)), e);
		}
		if (records == null) {
			records = new ArrayList<NihRecord>();
		}
		return records;
	}

	@Override
	public List<NihRecord> findByAppCode(String appCode, String tableName) {
		List<NihRecord> records = null;
		try {
			Assert.notNull(tableName, "hashTableName is null.");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("hashTableName", tableName);
			param.put("appCode", appCode);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_APPCODE), param);
		} catch (Exception e) {
			logger.error(String.format("查询某系统用户的挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_APPCODE)), e);
			throw new SystemException(String.format("查询某系统的挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_APPCODE)), e);
		}
		if (records == null) {
			records = new ArrayList<NihRecord>();
		}
		return records;
	}

	@Override
	public List<NihRecord> findByUserId(String userId, String tableName) {
		List<NihRecord> records = null;
		try {
			Assert.notNull(tableName, "hashTableName is null.");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("hashTableName", tableName);
			param.put("userId", userId);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_USERID), param);
		} catch (Exception e) {
			logger.error(String.format("查询用户所有系统下的挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_USERID)), e);
			throw new SystemException(String.format("查询用户所有系统下的挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_USERID)), e);
		}
		if (records == null) {
			records = new ArrayList<NihRecord>();
		}
		return records;
	}

}
