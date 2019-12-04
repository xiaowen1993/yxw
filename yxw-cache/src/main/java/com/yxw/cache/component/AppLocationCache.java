package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.app.location.AppLocation;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * APP可选城市缓存
 * 
 * @author homer.yang
 */
public class AppLocationCache {
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	public AppLocationCache() {
		super();
	}

	public List<AppLocation> get() {
		List<AppLocation> areas = new ArrayList<AppLocation>();

		String cacheKey = getCacheKey();

		Map<String, String> map = redisSvc.hgetAll(cacheKey);
		if (map != null) {
			for (String key : map.keySet()) {
				areas.addAll(JSON.parseArray(map.get(key), AppLocation.class));
			}
		}
		
		return areas;
	}

	public int set(Map<String, List<AppLocation>> map) {
		if (!CollectionUtils.isEmpty(map)) {
			String cacheKey = getCacheKey();
			//转换为json格式
			Map<String, String> jsonMap = new ConcurrentHashMap<String, String>();
			for (String mapKeys : map.keySet()) {
				jsonMap.put(mapKeys, JSON.toJSONString(map.get(mapKeys)));
			}
			if (!jsonMap.isEmpty()) {
				// redisSvc.hdel(cacheKey, map.keySet().toArray(new String[map.keySet().size()]));
				redisSvc.del(cacheKey);
				redisSvc.hmset(cacheKey, jsonMap);

				return jsonMap.size();
			}
		}

		return 0;

	}

	public Map<String, List<AppLocation>> getAllAppLocation() {
		Map<String, List<AppLocation>> map = new HashMap<String, List<AppLocation>>();
		/*String cacheKey = getCacheKey();
		redisSvc.hmget(cacheKey, fields)*/
		return map;
	}

	private String getCacheKey() {
		return CacheConstant.CACHE_AREA_SELECTED;
	}

}
