package com.yxw.platform.hospital.service;

import java.util.List;

import com.yxw.commons.entity.platform.hospital.PlatformSettingsMenu;
import com.yxw.framework.mvc.service.BaseService;

public interface PlatformSettingsMenuService extends BaseService<PlatformSettingsMenu, String> {

	/**
	 * 根据接入平台配置ID查询平台配置
	 * @param platformSettingsIds
	 * @return
	 */
	public List<PlatformSettingsMenu> findByPlatformSettingsIds(String[] platformSettingsIds);

	/**
	 * 根据菜单ID集合查询平台配置
	 * @param platformSettingsIds
	 * @return
	 */
	public List<PlatformSettingsMenu> findByMenuIds(String[] menuIds);

}