package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.app.optional.AppOptional;
import com.yxw.commons.entity.platform.app.optional.AppOptionalModule;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * APP功能 缓存
 * 
 * @author homer.yang
 */
public class AppOptionalCache {
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	private String FIELD_APP_OPTIONAL_MODULES = "modules";

	public AppOptionalCache() {
		super();
	}

	/**
	 * 获取APP功能模块
	 * 
	 * @return List<AppOptionalModule>
	 */
	public List<AppOptionalModule> getAppOptionalModules() {
		List<AppOptionalModule> appOptionalModules = new ArrayList<AppOptionalModule>();

		String cacheKey = getCacheKey();

		String jsonList = redisSvc.hget(cacheKey, FIELD_APP_OPTIONAL_MODULES);
		if (StringUtils.isNotEmpty(jsonList) && !jsonList.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			appOptionalModules = JSON.parseArray(jsonList, AppOptionalModule.class);
		}
		return appOptionalModules;
	}

	public int setAppOptionalModules(List<AppOptionalModule> appOptionalModules) {
		if (!CollectionUtils.isEmpty(appOptionalModules)) {
			String cacheKey = getCacheKey();

			String jsonString = JSON.toJSONString(appOptionalModules);
			redisSvc.hdel(cacheKey, FIELD_APP_OPTIONAL_MODULES);
			redisSvc.hset(cacheKey, FIELD_APP_OPTIONAL_MODULES, jsonString);

			return appOptionalModules.size();
		}

		return 0;
	}

	/**
	 * 获取APP功能
	 * 
	 * @return List<AppOptional>
	 */
	public List<AppOptional> getAppOptionalsByModuleCode(String moduleCode) {
		List<AppOptional> appOptionals = new ArrayList<AppOptional>();

		String cacheKey = getCacheKey();

		String jsonList = redisSvc.hget(cacheKey, moduleCode);
		if (StringUtils.isNotEmpty(jsonList) && !jsonList.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			appOptionals = JSON.parseArray(jsonList, AppOptional.class);
		}
		return appOptionals;
	}

	public List<Map<String, List<AppOptional>>> getOptionsByModules(List<String> modules) {
		List<Map<String, List<AppOptional>>> list = new ArrayList<Map<String, List<AppOptional>>>();
		Map<String, List<AppOptional>> resultMap = new HashMap<String, List<AppOptional>>();
		list.add(resultMap);

		for (String module : modules) {
			resultMap.put(module, getAppOptionalsByModuleCode(module));
		}

		return list;
	}

	/**
	 * 根据板块code 缓存 APP功能
	 * 
	 * @param appOptionals List<AppOptional>
	 * @param moduleCode 板块code
	 * @return 缓存的数据条数
	 */
	public int setAppOptionalsByModuleCode(List<AppOptional> appOptionals, String moduleCode) {
		if (!CollectionUtils.isEmpty(appOptionals)) {
			String cacheKey = getCacheKey();

			String jsonString = JSON.toJSONString(appOptionals);
			redisSvc.hdel(cacheKey, moduleCode);
			redisSvc.hset(cacheKey, moduleCode, jsonString);

			return appOptionals.size();
		}

		return 0;
	}

	private String getCacheKey() {
		return CacheConstant.CACHE_APP_OPTIONAL;
	}

}
