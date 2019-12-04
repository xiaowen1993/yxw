package com.yxw.platform.hospital.dao;

import com.yxw.commons.entity.platform.hospital.PlatformPaySettings;
import com.yxw.framework.mvc.dao.BaseDao;

public interface PlatformPaySettingsDao extends BaseDao<PlatformPaySettings, String> {

	/**
	 * 根据医院平台关联Id删除支付配置信息
	 * @param hospitalId
	 */
	public void deletePlatformPaySettingsByPlatFormId(String platformSettingsId, String paySettingsId);

	public PlatformPaySettings findByPlatformSettingsIdAndPaySettingsId(String platformSettingsId, String paySettingsId);
}