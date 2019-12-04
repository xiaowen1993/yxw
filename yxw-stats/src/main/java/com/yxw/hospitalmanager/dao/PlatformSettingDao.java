package com.yxw.hospitalmanager.dao;

import java.util.List;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.hospitalmanager.entity.PlatformSetting;
import com.yxw.hospitalmanager.vo.HospitalInfosVo;

public interface PlatformSettingDao extends BaseDao<PlatformSetting, String> {
	public List<HospitalInfosVo> findAllSettings(String hospitalId);
	
	public List<PlatformSetting> findByHospitalId(String hospitalId);
	
	public void deleteByHospitalId(String hospitalId);
	
	public List<HospitalInfosVo> findByHospitalIdAndAppId(String hospitalId, String appId);
	
	public List<HospitalInfosVo> findByHospitalIdAndAppIdAndPlatformId(String hospitalId, String appId, String platformId);
	
	public List<HospitalInfosVo> findByHospitalIdAndPlatformIdAndTradeId(String hospitalId, String platformId, String tradeId);
}
