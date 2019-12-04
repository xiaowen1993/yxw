package com.yxw.statistics.biz.hospitalmanager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.statistics.biz.hospitalmanager.dao.HospitalDao;
import com.yxw.statistics.biz.hospitalmanager.entity.Hospital;
import com.yxw.statistics.biz.vo.HospitalInfosVo;

@Repository
public class HospitalDaoImpl extends BaseDaoImpl<Hospital, String> implements HospitalDao {
	private static Logger logger = LoggerFactory.getLogger(HospitalDao.class);

	private final static String SQLNAME_FIND_BY_NAME = "findByName";
	
	private final static String SQLNAME_FIND_ALL_HOSPITAL_INFOS = "findAllHospitalInfos";
	
	private final static String SQLNAME_FIND_HOSPITALS_BY_NAME = "findHospitalsByName";
	
	private final static String SQLNAME_FIND_HOSPITALS_BY_CODE = "findHospitalsByCode";
	
	@Override
	public List<Hospital> findByName(String name) {
		try {
			Assert.notNull(name, "通过名字过滤医院, 过滤字段不能为空");
			Map<String, String> queryMap = new HashMap<>();
			queryMap.put("name", name);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_NAME), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过名字过滤医院出错！语句：%s", getSqlName(SQLNAME_FIND_BY_NAME)), e);
			throw new SystemException(String.format("通过名字过滤医院出错！语句：%s", getSqlName(SQLNAME_FIND_BY_NAME)), e);
		}
	}

	@Override
	public List<HospitalInfosVo> findAllHospitalInfos(String name) {
		try {
			Map<String, String> queryMap = new HashMap<>();
			queryMap.put("name", name);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_HOSPITAL_INFOS), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过名字过滤医院、平台信息出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_HOSPITAL_INFOS)), e);
			throw new SystemException(String.format("通过名字过滤医院、平台信息出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_HOSPITAL_INFOS)), e);
		}
	}

	@Override
	public List<Hospital> findHospitalsByName(String name) {
		try {
			Map<String, String> queryMap = new HashMap<>();
			queryMap.put("name", name);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_HOSPITALS_BY_NAME), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过名称找医院出错出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITALS_BY_NAME)), e);
			throw new SystemException(String.format("通过名称找医院出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITALS_BY_NAME)), e);
		}
	}
	
	@Override
	public List<Hospital> findHospitalsByCode(String code) {
		try {
			Map<String, String> queryMap = new HashMap<>();
			queryMap.put("code", code);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_HOSPITALS_BY_CODE), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过代码找医院出错出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITALS_BY_CODE)), e);
			throw new SystemException(String.format("通过代码找医院出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITALS_BY_CODE)), e);
		}
	}
}
