package com.yxw.biz.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.biz.common.dao.HospitalInfoDao;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.vo.AreaHospitalInfosVo;
import com.yxw.vo.HospitalInfosVo;
import com.yxw.vo.StatsHospitalInfosVo;

@Repository
public class HospitalInfoDaoImpl extends BaseDaoImpl<HospitalInfosVo, String> implements HospitalInfoDao {
	
	private static Logger logger = LoggerFactory.getLogger(HospitalInfoDao.class);
	
	private final static String SQLNAME_FIND_ALL_BY_APP_ID = "findAllByAppId";
	
	private final static String SQLNAME_FIND_ALL_INFOS = "findAllInfos";

	private final static String SQL_NAME_GET_HOSPITAL_INFOS = "getHospitalInfos";
	
	private final static String SQL_NAME_GET_ALL_HOSPITAL_INFOS = "getAllHospitalInfos";
	
	private final static String SQL_NAME_GET_AREA_HOSPITAL_INFOS = "getAreaHospitalInfos";

	@Override
	public List<HospitalInfosVo> findAllByAppId(String appId) {
		try {
			Assert.notNull(appId, "appId 不能为空");
			Map<String, String> queryMap = new HashMap<>();
			queryMap.put("appId", appId);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_BY_APP_ID), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过appId过滤医院出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_BY_APP_ID)), e);
			throw new SystemException(String.format("通过appId过滤医院出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_BY_APP_ID)), e);
		}
	}

	@Override
	public List<StatsHospitalInfosVo> findAllInfos() {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_INFOS));
		} catch (Exception e) {
			logger.error(String.format("获取所有医院出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_INFOS)), e);
			throw new SystemException(String.format("获取所有医院出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_INFOS)), e);
		}
	}

	@Override
	public List<StatsHospitalInfosVo> getHospitalInfos() {
		try {
			return sqlSession.selectList(getSqlName(SQL_NAME_GET_HOSPITAL_INFOS));
		} catch (Exception e) {
			logger.error(String.format("获取标准平台医院出错！语句：%s", getSqlName(SQL_NAME_GET_HOSPITAL_INFOS)), e);
			throw new SystemException(String.format("获取标准平台医院出错！语句：%s", getSqlName(SQL_NAME_GET_HOSPITAL_INFOS)), e);
		}
	}

	@Override
	public List<AreaHospitalInfosVo> getAreaHospitalInfos() {
		try {
			return sqlSession.selectList(getSqlName(SQL_NAME_GET_AREA_HOSPITAL_INFOS));
		} catch (Exception e) {
			logger.error(String.format("获取区域医院出错！语句：%s", getSqlName(SQL_NAME_GET_AREA_HOSPITAL_INFOS)), e);
			throw new SystemException(String.format("获取区域医院出错！语句：%s", getSqlName(SQL_NAME_GET_AREA_HOSPITAL_INFOS)), e);
		}
	}

	@Override
	public List<StatsHospitalInfosVo> getAllHospitalInfos() {
		try {
			return sqlSession.selectList(getSqlName(SQL_NAME_GET_ALL_HOSPITAL_INFOS));
		} catch (Exception e) {
			logger.error(String.format("获取所有医院出错！语句：%s", getSqlName(SQL_NAME_GET_ALL_HOSPITAL_INFOS)), e);
			throw new SystemException(String.format("获取所有医院出错！语句：%s", getSqlName(SQL_NAME_GET_ALL_HOSPITAL_INFOS)), e);
		}
	}

}
