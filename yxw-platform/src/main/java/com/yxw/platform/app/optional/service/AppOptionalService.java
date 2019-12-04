package com.yxw.platform.app.optional.service;

import java.util.List;

import com.yxw.commons.entity.platform.app.optional.AppOptional;
import com.yxw.framework.mvc.service.BaseService;

public interface AppOptionalService extends BaseService<AppOptional, Integer> {
	public List<AppOptional> findByModuleId(String moduleId);

	public List<AppOptional> getCacheAppOptionalsByModuleCode(String moduleCode);

	public void setCacheAppOptionalsByModuleCode(List<AppOptional> appOptionals, String moduleCode);

}
