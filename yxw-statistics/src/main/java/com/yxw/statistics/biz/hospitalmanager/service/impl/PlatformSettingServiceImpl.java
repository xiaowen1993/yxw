package com.yxw.statistics.biz.hospitalmanager.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.statistics.biz.hospitalmanager.dao.PlatformSettingDao;
import com.yxw.statistics.biz.hospitalmanager.entity.PlatformSetting;
import com.yxw.statistics.biz.hospitalmanager.service.PlatformSettingService;
import com.yxw.statistics.biz.vo.HospitalInfosVo;

@Service(value="platformSettingService")
public class PlatformSettingServiceImpl extends BaseServiceImpl<PlatformSetting, String> implements PlatformSettingService {

	private PlatformSettingDao platformSettingDao = SpringContextHolder.getBean(PlatformSettingDao.class);
	
	@Override
	public List<PlatformSetting> findAllByHospitalId(String hospitalId) {
		return platformSettingDao.findByHospitalId(hospitalId);
	}

	@Override
	public List<HospitalInfosVo> findAllSettings(String hospitalId) {
		return platformSettingDao.findAllSettings(hospitalId);
	}

	@Override
	public void deleteAllByHospitalId(String hospitalId) {
		platformSettingDao.deleteByHospitalId(hospitalId);
	}

	@Override
	protected BaseDao<PlatformSetting, String> getDao() {
		return platformSettingDao;
	}

	@Override
	public boolean checkExistsByHospitalIdAndAppId(String hospitalId, String appId) {
		return !CollectionUtils.isEmpty(platformSettingDao.findByHospitalIdAndAppId(hospitalId, appId));
	}

	@Override
	public boolean checkExistsByHospitalIdAndAppIdAndPlatformId(String hospitalId, String appId, String platformId) {
		return !CollectionUtils.isEmpty(platformSettingDao.findByHospitalIdAndAppIdAndPlatformId(hospitalId, appId, platformId));
	}

	@Override
	public boolean checkExistsByHospitalIdAndPlatformIdAndTradeId(String hospitalId, String platformId, String tradeId) {
		return !CollectionUtils.isEmpty(platformSettingDao.findByHospitalIdAndPlatformIdAndTradeId(hospitalId, platformId, tradeId));
	}

}
