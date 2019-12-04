package com.yxw.stats.dao.project;

import com.yxw.stats.entity.project.AlipaySettings;

public interface AlipaySettingsDao {

	public AlipaySettings findAlipaySettingsByAppId(String appId);
}