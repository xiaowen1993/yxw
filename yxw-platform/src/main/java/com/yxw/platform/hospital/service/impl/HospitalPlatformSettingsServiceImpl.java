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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.hospital.HospitalPlatformSettings;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.HospitalPlatformSettingsDao;
import com.yxw.platform.hospital.service.HospitalPlatformSettingsService;

/**
 * 后台接入平台管理 Service 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Service(value = "hospitalPlatformSettingsService")
public class HospitalPlatformSettingsServiceImpl extends BaseServiceImpl<HospitalPlatformSettings, String> implements HospitalPlatformSettingsService {

	@Autowired
	private HospitalPlatformSettingsDao hospitalPlatformSettingsDao;

	@Override
	protected BaseDao<HospitalPlatformSettings, String> getDao() {
		return hospitalPlatformSettingsDao;
	}

	@Override
	public List<HospitalPlatformSettings> findByHospital(String hospitalId) {
		return hospitalPlatformSettingsDao.findByHospital(hospitalId);
	}

	@Override
	public <K, V> Map<K, HospitalPlatformSettings> findMapByHospital(String hospitalId, String mapKey) {
		return hospitalPlatformSettingsDao.findMapByHospital(hospitalId, mapKey);
	}

}
