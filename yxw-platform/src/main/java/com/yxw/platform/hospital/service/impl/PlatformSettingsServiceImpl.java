/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月15日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.hospital.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.hospital.HospitalPlatformSettings;
import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.HospitalPlatformSettingsDao;
import com.yxw.platform.hospital.dao.PlatformSettingsDao;
import com.yxw.platform.hospital.service.PlatformSettingsService;

/**
 * 后台接入平台管理 Service 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Service(value = "platformSettingsService")
public class PlatformSettingsServiceImpl extends BaseServiceImpl<PlatformSettings, String> implements PlatformSettingsService {

	private Logger logger = LoggerFactory.getLogger(PlatformSettingsServiceImpl.class);

	@Autowired
	private PlatformSettingsDao platformSettingsDao;

	@Autowired
	private HospitalPlatformSettingsDao hospitalPlatformSettingsDao;

	@Override
	protected BaseDao<PlatformSettings, String> getDao() {
		return platformSettingsDao;
	}

	// cache缓存使用
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@Override
	public void batchSavePlatformSettings(List<PlatformSettings> platformSettings, String hospitalId) {

		if (platformSettings.size() > 0) {
			List<HospitalPlatformSettings> hospitalPlatformSettingsList = new ArrayList<HospitalPlatformSettings>();
			// 保存接入平台配置信息
			for (PlatformSettings platformSetting : platformSettings) {
				// logger.info(JSONObject.toJSONString(platformSetting));

				if (platformSetting.getId() != null) {
					logger.info("更新platformSetting： " + JSONObject.toJSONString(platformSetting));
					platformSettingsDao.update(platformSetting);
					hospitalPlatformSettingsList.add(new HospitalPlatformSettings(platformSetting, platformSetting.getHospital().getId()));
				} else {
					logger.info("新增platformSetting： " + JSONObject.toJSONString(platformSetting));
					platformSettingsDao.add(platformSetting);
					hospitalPlatformSettingsList.add(new HospitalPlatformSettings(platformSetting, platformSetting.getHospital().getId()));
				}
			}
			hospitalPlatformSettingsDao.deleteByHospitalId(hospitalId);
			if (hospitalPlatformSettingsList.size() > 0) {
				for (HospitalPlatformSettings hospitalPlatformSettings : hospitalPlatformSettingsList) {
					logger.info("新增hospitalPlatformSetting： " + JSONObject.toJSONString(hospitalPlatformSettings));
					hospitalPlatformSettingsDao.add(hospitalPlatformSettings);
				}
			}

			// 缓存医院与接入平台appId 的关系 (目前即我们的平台)
			/** hospital.app.platform  **/
			List<PlatformSettings> allPlatformSettings = findAllPlatformRelations();
			Map<String, String> platformSettingsMap = new HashMap<String, String>();
			if (!CollectionUtils.isEmpty(allPlatformSettings)) {
				for (PlatformSettings settings : allPlatformSettings) {
					if (StringUtils.isNotBlank(settings.getHpsId()) && StringUtils.isNotBlank(settings.getCode())) {
						String fieldName = genAppFieldNameByCode(settings.getCode(), settings.getHospital().getId());
						// 目前hospitalId唯一，appCode 也唯一
						platformSettingsMap.put(fieldName, JSON.toJSONString(settings));
					}
				}

				if (!CollectionUtils.isEmpty(platformSettingsMap)) {
					List<Object> parameters = new ArrayList<Object>();
					String cacheKey = getHospitalPlatformCacheKey();
					parameters.add(cacheKey);
					serveComm.delete(CacheType.COMMON_CACHE, "delCache", parameters);

					parameters.add(platformSettingsMap);
					serveComm.set(CacheType.COMMON_CACHE, "setHMCache", parameters);
				}
			}
		}
	}

	private String genAppFieldNameByCode(String appCode, String appId) {
		return appCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(appId);
	}

	private String getHospitalPlatformCacheKey() {
		return CacheConstant.CACHE_HOSPITAL_PLATFORM_KEY_PREFIX;
	}

	@Override
	public List<PlatformSettings> findByHospitalIdAndPlatformIds(String hospitalId, String[] platformIds) {
		return platformSettingsDao.findByHospitalIdAndPlatformIds(hospitalId, platformIds);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.service.PlatformSettingsService#findByHospitalIdAndPlatformId(java.lang.String, java.lang.String)
	 */
	@Override
	public PlatformSettings findByHospitalIdAndPlatformId(String hospitalId, String platformId) {
		return platformSettingsDao.findByHospitalIdAndPlatformId(hospitalId, platformId);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.service.PlatformSettingsService#findByHospitalIdAndPlatformId(java.lang.String, java.lang.String)
	 */
	@Override
	public PlatformSettings findByHospitalIdAndAppCode(String hospitalId, String appCode) {
		return platformSettingsDao.findByHospitalIdAndAppCode(hospitalId, appCode);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.service.PlatformSettingsService#findByAppId(java.lang.String)
	 */
	@Override
	public PlatformSettings findByAppId(String appId) {
		return platformSettingsDao.findByAppId(appId);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.service.PlatformSettingsService#findByPrivateKey(java.lang.String)
	 */
	@Override
	public PlatformSettings findByPrivateKey(String privateKey) {
		return platformSettingsDao.findByPrivateKey(privateKey);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.service.PlatformSettingsService#findByPublicKey(java.lang.String)
	 */
	@Override
	public PlatformSettings findByPublicKey(String publicKey) {
		return platformSettingsDao.findByPublicKey(publicKey);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.service.PlatformSettingsService#isUniqueAppIdForPlatformSettings(com.yxw.platform.hospital.entity.PlatformSettings)
	 */
	@Override
	public boolean isUniqueAppIdForPlatformSettings(PlatformSettings platformSettings) {
		String id = platformSettings.getId();
		PlatformSettings entity = this.findByAppId(platformSettings.getAppId());
		if (entity == null) {
			return true;
		} else {
			if (entity.getId().equals(id)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.service.PlatformSettingsService#isUniquePrivateKeyForPlatformSettings(com.yxw.platform.hospital.entity.PlatformSettings)
	 */
	@Override
	public boolean isUniquePrivateKeyForPlatformSettings(PlatformSettings platformSettings) {
		String id = platformSettings.getId();
		PlatformSettings entity = this.findByAppId(platformSettings.getPrivateKey());
		if (entity == null) {
			return true;
		} else {
			if (entity.getId().equals(id)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.service.PlatformSettingsService#isUniquePublicKeyForPlatformSettings(com.yxw.platform.hospital.entity.PlatformSettings)
	 */
	@Override
	public boolean isUniquePublicKeyForPlatformSettings(PlatformSettings platformSettings) {
		String id = platformSettings.getId();
		PlatformSettings entity = this.findByPublicKey(platformSettings.getPublicKey());
		if (entity == null) {
			return true;
		} else {
			if (entity.getId().equals(id)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年3月27日 
	 * @return 
	 */
	@Override
	public List<PlatformSettings> findAllPlatformRelations() {
		return platformSettingsDao.findAllPlatformRelations();
	}

}
