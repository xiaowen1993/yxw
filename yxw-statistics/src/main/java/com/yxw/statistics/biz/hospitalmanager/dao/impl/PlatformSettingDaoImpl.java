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
import com.yxw.statistics.biz.hospitalmanager.dao.PlatformSettingDao;
import com.yxw.statistics.biz.hospitalmanager.entity.PlatformSetting;
import com.yxw.statistics.biz.vo.HospitalInfosVo;

@Repository
public class PlatformSettingDaoImpl extends BaseDaoImpl<PlatformSetting, String> implements PlatformSettingDao {

	private static Logger logger = LoggerFactory.getLogger(PlatformSettingDao.class);

	private final static String SQLNAME_FIND_BY_HOSPITAL_ID = "findByHospitalId";

	private final static String SQLNAME_FIND_ALL_SETTINGS = "findAllSettings";

	private final static String SQLNAME_DELETE_BY_HOSPITAL_ID = "deleteByHospitalId";

	private final static String SQLNAME_FIND_BY_HOSPITAL_ID_AND_APP_ID = "findByHospitalIdAndAppId";

	private final static String SQLNAME_FIND_BY_HOSPITAL_ID_AND_APP_ID_AND_PLATFORM_ID = "findByHospitalIdAndAppIdAndPlatformId";
	
	private final static String SQLNAME_FIND_BY_HOSPITAL_ID_AND_PLATFORM_ID_AND_TRADE_ID = "findByHospitalIdAndPlatformIdAndTradeId";

	@Override
	public List<PlatformSetting> findByHospitalId(String hospitalId) {
		try {
			Assert.notNull(hospitalId, "hospitalId不能为空");
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("通过医院ID找所有配置出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("通过医院ID找所有配置出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID)), e);
		}
	}

	@Override
	public void deleteByHospitalId(String hospitalId) {
		try {
			Assert.notNull(hospitalId, "hospitalId不能为空");
			sqlSession.delete(getSqlName(SQLNAME_DELETE_BY_HOSPITAL_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("通过医院ID删除配置出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("通过医院ID删除配置出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_HOSPITAL_ID)), e);
		}
	}

	@Override
	public List<HospitalInfosVo> findByHospitalIdAndAppId(String hospitalId, String appId) {
		try {
			Assert.notNull(hospitalId, "hospitalId不能为空");
			Assert.notNull(appId, "appId不能为空");
			Map<String, String> queryMap = new HashMap<>();
			queryMap.put("hospitalId", hospitalId);
			queryMap.put("appId", appId);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID_AND_APP_ID), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过医院ID、AppId找配置出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID_AND_APP_ID)), e);
			throw new SystemException(String.format("通过医院ID、AppId找配置出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID_AND_APP_ID)), e);
		}
	}

	@Override
	public List<HospitalInfosVo> findAllSettings(String hospitalId) {
		try {
			Assert.notNull(hospitalId, "hospitalId不能为空");
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_SETTINGS), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("通过医院ID找所有配置出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_SETTINGS)), e);
			throw new SystemException(String.format("通过医院ID找所有配置出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_SETTINGS)), e);
		}
	}

	@Override
	public List<HospitalInfosVo> findByHospitalIdAndAppIdAndPlatformId(String hospitalId, String appId, String platformId) {
		try {
			Assert.notNull(hospitalId, "hospitalId不能为空");
			Assert.notNull(appId, "appId不能为空");
			Assert.notNull(platformId, "platformId不能为空");
			Map<String, String> queryMap = new HashMap<>();
			queryMap.put("hospitalId", hospitalId);
			queryMap.put("appId", appId);
			queryMap.put("platformId", platformId);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID_AND_APP_ID_AND_PLATFORM_ID), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过医院ID、AppId、platformId找配置出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID_AND_APP_ID_AND_PLATFORM_ID)), e);
			throw new SystemException(
					String.format("通过医院ID、AppId、platformId找配置出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID_AND_APP_ID_AND_PLATFORM_ID)), e);
		}
	}

	@Override
	public List<HospitalInfosVo> findByHospitalIdAndPlatformIdAndTradeId(String hospitalId, String platformId, String tradeId) {
		try {
			Assert.notNull(hospitalId, "hospitalId不能为空");
			Assert.notNull(platformId, "platformId不能为空");
			Assert.notNull(tradeId, "tradeId不能为空");
			Map<String, String> queryMap = new HashMap<>();
			queryMap.put("hospitalId", hospitalId);
			queryMap.put("platformId", platformId);
			queryMap.put("tradeId", tradeId);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID_AND_PLATFORM_ID_AND_TRADE_ID), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过医院ID、platformId、tradeId找配置出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID_AND_PLATFORM_ID_AND_TRADE_ID)), e);
			throw new SystemException(
					String.format("通过医院ID、platformId、tradeId找配置出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID_AND_PLATFORM_ID_AND_TRADE_ID)), e);
		}
	}

}
