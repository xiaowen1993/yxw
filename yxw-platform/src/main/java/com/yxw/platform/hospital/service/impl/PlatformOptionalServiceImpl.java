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
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yxw.commons.entity.platform.hospital.PlatformOptional;
import com.yxw.commons.vo.PlatformOptionalVo;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.PlatformOptionalDao;
import com.yxw.platform.hospital.service.PlatformOptionalService;

/**
 * 后台医院已选功能管理 Service 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Service(value = "hospitalOptionalService")
public class PlatformOptionalServiceImpl extends BaseServiceImpl<PlatformOptional, String> implements PlatformOptionalService {

	@Autowired
	private PlatformOptionalDao hospitalOptionalDao;

	@Override
	protected BaseDao<PlatformOptional, String> getDao() {
		return hospitalOptionalDao;
	}

	@Override
	public List<PlatformOptional> findByHospital(String hospitalId) {
		return hospitalOptionalDao.findByHospital(hospitalId);
	}

	@Override
	public void deleteByPlatformSettingsId(String platformSettingsId) {
		hospitalOptionalDao.deleteByPlatformSettingsId(platformSettingsId);
	}

	@Override
	public void batchSaveHospitalOptional(Map<String, List<PlatformOptional>> map) {
	
		for (Entry<String, List<PlatformOptional>> entry: map.entrySet()) {
			String platformSettingsId = entry.getKey();
			if (!CollectionUtils.isEmpty(entry.getValue())) {
				deleteByPlatformSettingsId(platformSettingsId);
				for (PlatformOptional platformOptional: entry.getValue()) {
					hospitalOptionalDao.add(platformOptional);
				}
			}
		}

	}

	@Override
	public PlatformOptional findByPlatformSettingsId(String platformSettingsId) {
		return hospitalOptionalDao.findByPlatformSettingsId(platformSettingsId);
	}

	@Override
	public List<PlatformOptionalVo> findAllByHospitalId(String hospitalId) {
		return hospitalOptionalDao.findAllByHospitalId(hospitalId);
	}
}
