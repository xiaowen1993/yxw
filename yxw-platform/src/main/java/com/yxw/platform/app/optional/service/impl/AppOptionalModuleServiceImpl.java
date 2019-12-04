package com.yxw.platform.app.optional.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.app.optional.AppOptionalModule;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.app.optional.dao.AppOptionalModuleDao;
import com.yxw.platform.app.optional.service.AppOptionalModuleService;
import com.yxw.platform.app.optional.service.AppOptionalService;

@Service(value = "appOptionalModuleService")
public class AppOptionalModuleServiceImpl extends BaseServiceImpl<AppOptionalModule, Integer> implements AppOptionalModuleService {

	@Autowired
	private AppOptionalModuleDao appOptionalModuleDao;

	@Autowired
	private AppOptionalService appOptionalService;

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@Override
	protected BaseDao<AppOptionalModule, Integer> getDao() {
		return appOptionalModuleDao;
	}

	@Override
	public List<AppOptionalModule> getCacheAppOptionalModules() {
		List<AppOptionalModule> resultList = null;
		List<Object> params = new ArrayList<Object>();
		List<Object> results = serveComm.get(CacheType.APP_OPTIONAL_CACHE, "getAppOptionalModules", params);
		if (CollectionUtils.isNotEmpty(results)) {
			resultList = new ArrayList<AppOptionalModule>(results.size());
			String sourceJson = JSON.toJSONString(results);
			resultList.addAll(JSON.parseArray(sourceJson, AppOptionalModule.class));
		}

		return resultList != null ? resultList : new ArrayList<AppOptionalModule>();
	}

	/**
	 * 获取模块列表，包含模块下功能列表数据
	 * 
	 * @return List<AppOptionalModule>
	 */
	@Override
	public List<AppOptionalModule> getCacheAppOptionalModulesHasAppOptionals() {
		List<AppOptionalModule> cacheAppOptionalModules = getCacheAppOptionalModules();

		for (AppOptionalModule appOptionalModule : cacheAppOptionalModules) {
			appOptionalModule.setAppOptionals(appOptionalService.getCacheAppOptionalsByModuleCode(appOptionalModule.getCode()));
		}

		return cacheAppOptionalModules;
	}

}
