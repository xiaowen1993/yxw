package com.yxw.platform.app.optional.service;

import java.util.List;

import com.yxw.commons.entity.platform.app.optional.AppOptionalModule;
import com.yxw.framework.mvc.service.BaseService;

public interface AppOptionalModuleService extends BaseService<AppOptionalModule, Integer> {

	public List<AppOptionalModule> getCacheAppOptionalModules();

	/**
	 * 获取模块列表，包含模块下功能列表数据
	 * 
	 * @return List<AppOptionalModule>
	 */
	public List<AppOptionalModule> getCacheAppOptionalModulesHasAppOptionals();

}
