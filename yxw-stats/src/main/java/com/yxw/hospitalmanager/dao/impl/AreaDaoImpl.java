package com.yxw.hospitalmanager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.hospitalmanager.dao.AreaDao;
import com.yxw.hospitalmanager.entity.Area;

@Repository
public class AreaDaoImpl extends BaseDaoImpl<Area, String> implements AreaDao {

	private static Logger logger = LoggerFactory.getLogger(AreaDao.class);
	
	private final static String SQLNAME_FIND_ALL_BY_LEVEL = "findAllByLevel";
	
	private final static String SQLNAME_FIND_ALL_HYPER_LEVEL = "findAllHyperLevel";
	
	@Override
	public List<Area> findAllByLevel(Integer level) {
		try {
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put("level", level);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_BY_LEVEL), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过Level找Area出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_BY_LEVEL)), e);
			throw new SystemException(String.format("通过Level找Area出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_BY_LEVEL)), e);
		}
	}

	@Override
	public List<Area> findAllHiperLevel(Integer level) {
		try {
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put("level", level);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_HYPER_LEVEL), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过Level找Area出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_HYPER_LEVEL)), e);
			throw new SystemException(String.format("通过Level找Area出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_HYPER_LEVEL)), e);
		}
	}

}
