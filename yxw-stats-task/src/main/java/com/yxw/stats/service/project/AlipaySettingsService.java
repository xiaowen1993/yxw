package com.yxw.stats.service.project;

import com.yxw.stats.entity.project.AlipaySettings;

public interface AlipaySettingsService {

	public AlipaySettings findAlipaySettingsByAppId(String appId);
}