package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.area.Area;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * 城市区域缓存
 * 
 * @author homer.yang
 */
public class AreaCache {
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	public AreaCache() {
		super();
	}

	/**
	 * 获取城市缓存
	 * 
	 * @param level 等级
	 * @param parentId 上级ID 一级城市的上级ID是：100000
	 * @return List<Area>
	 */
	public List<Area> getAreas(String level, String parentId) {
		List<Area> areas = new ArrayList<Area>();
		
		String cacheKey = getCacheKey();
		String field = level.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(parentId);
		
		String jsonList = redisSvc.hget(cacheKey, field);
		if (StringUtils.isNotEmpty(jsonList) && !jsonList.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			areas = JSON.parseArray(jsonList, Area.class);
		}
		return areas;
	}

	/**
	 * 城市存入缓存
	 * 
	 * @param areas 城市数据
	 * @return 存入的数据条数
	 */
	public int setAreas(List<Area> areas) {
		if (!CollectionUtils.isEmpty(areas)) {
			String cacheKey = getCacheKey();
			String field = areas.get(0).getLevel() + CacheConstant.CACHE_KEY_SPLIT_CHAR.concat(areas.get(0).getParentId());
			
			String jsonString = JSON.toJSONString(areas);
			redisSvc.hdel(cacheKey, field);
			redisSvc.hset(cacheKey, field, jsonString);

			return areas.size();
		}

		return 0;
	}

	/**
	 * 城市cache key
	 */
	private String getCacheKey() {
		return CacheConstant.CACHE_AREA_HASH;
	}

}
