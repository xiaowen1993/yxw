package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

public class PayInfoCache {

	private Logger logger = LoggerFactory.getLogger(PayInfoCache.class);

	/**
	 * 查询该医院下所有
	 * @param hospitalCode
	 * @return
	 */
	public List<HospitalCodeAndAppVo> getAllPayInfos(String hospitalCode) {
		List<HospitalCodeAndAppVo> results = null;
		HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
		try {
			List<String> hospitalIds = hospitalCache.getHospitalIdByCode(hospitalCode);
			if (!CollectionUtils.isEmpty(hospitalIds)) {
				String hospitalId = hospitalIds.get(0);
				List<PlatformSettings> platforms = hospitalCache.findHospitalPlatform(hospitalId);
				if (!CollectionUtils.isEmpty(platforms)) {
					results = new ArrayList<>();
					for (PlatformSettings platformSettings : platforms) {
						String appId = platformSettings.getAppId();
						String appCode = platformSettings.getCode();
						List<HospitalCodeAndAppVo> appVos = hospitalCache.queryHospitalCodeByApp(appCode, appId);
						results.addAll(appVos);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getAllPayInfos error. hospitalCode: {}. errorMessage: {}. cause by: {}.",
					new Object[] { hospitalCode, e.getMessage(), e.getCause() });
		}
		return CollectionUtils.isEmpty(results) ? new ArrayList<HospitalCodeAndAppVo>(0) : results;
	}

	public List<HospitalCodeAndAppVo> getPayInfo(String hospitalCode, String tradeMode) {
		List<HospitalCodeAndAppVo> results = new ArrayList<HospitalCodeAndAppVo>();
		List<HospitalCodeAndAppVo> vos = getAllPayInfos(hospitalCode);
		if (!CollectionUtils.isEmpty(vos)) {
			for (HospitalCodeAndAppVo tempVo : vos) {
				if (tempVo.getTradeMode().equals(tradeMode)) {
					results.add(tempVo);
					break;
				}
			}
		}
		return results;
	}

}
