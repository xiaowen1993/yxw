package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.app.color.AppColor;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

public class AppColorCache {

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);
	
	public List<AppColor> findAll() {
		List<AppColor> results = null;
		
		String cacheKey = getCacheKey();
		results = redisSvc.getList(cacheKey, AppColor.class);
		
		return results == null ? new ArrayList<AppColor>(0) : results;
	}
	
	public List<AppColor> findByAppCode(String appCode) {
		List<AppColor> result = null;
		
		String cacheKey = getCacheKey();
		List<AppColor> results = redisSvc.getList(cacheKey, AppColor.class);
		for (AppColor color : results) {
			if (color.getAppCode().equals(appCode)) {
				result = new ArrayList<>(1);
				result.add(color);
				break;
			}
		}
		
		return result == null ? new ArrayList<AppColor>(0) : result;
	}

	public int AddOrUpdate(List<AppColor> values) {
		String cacheKey = getCacheKey();
		List<AppColor> results = redisSvc.getList(cacheKey, AppColor.class);
		
		if (!CollectionUtils.isEmpty(results)) {
			Map<String, AppColor> map = Maps.uniqueIndex(results, new Function<AppColor, String>() {
				@Override
				public String apply(AppColor input) {
					return input.getId();
				}
			});
			
			for (AppColor color : values) {
				String key = color.getId();
				if (map.containsKey(key)) {
					// 做update操作
					results.remove(map.get(key));
				} else {
					// 做插入操作
				}
				
				results.add(color);
			}
			
			redisSvc.set(cacheKey, JSON.toJSONString(results));
		} else {
			redisSvc.set(cacheKey, JSON.toJSONString(values));
		}
		
		return values.size();
	}
	
	public int saveAppColors(List<AppColor> values) {
		if (!CollectionUtils.isEmpty(values)) {
			String cacheKey = getCacheKey();
			
			String jsonString = JSON.toJSONString(values);
			redisSvc.del(cacheKey);
			redisSvc.set(cacheKey, jsonString);

			return values.size();
		}

		return 0;
	}
	
	public int delete(List<String> ids) {
		if (!CollectionUtils.isEmpty(ids)) {
			String cacheKey = getCacheKey();
			
			List<AppColor> cacheList = redisSvc.getList(cacheKey, AppColor.class);
			List<AppColor> newList = new ArrayList<>();
			if (!CollectionUtils.isEmpty(cacheList)) {
				for (AppColor color : cacheList) {
					if (ids.indexOf(color.getId()) == -1) {
						newList.add(color);
					}
				}
				
				redisSvc.set(cacheKey, JSON.toJSONString(newList));
			} else {
				return 0;
			}
		}
		
		return ids.size();
	}
	
	public int addAppColors(List<AppColor> values) {
		if (!CollectionUtils.isEmpty(values)) {
			String cacheKey = getCacheKey();
			
			String jsonString = JSON.toJSONString(values);
			List<AppColor> list = JSON.parseArray(jsonString, AppColor.class);
			list.addAll(values);
			redisSvc.set(cacheKey, JSON.toJSONString(list));

			return values.size();
		}

		return 0;
	}
	
	/**
	 * app color cache key
	 */
	private String getCacheKey() {
		return CacheConstant.CACHE_APP_COLOR_HASH;
	}
}
