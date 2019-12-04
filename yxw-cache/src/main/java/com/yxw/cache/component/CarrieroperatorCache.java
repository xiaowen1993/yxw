package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.app.carrieroperator.Carrieroperator;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * 运营管理缓存
 * 
 * @author homer.yang
 */
public class CarrieroperatorCache {
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	public CarrieroperatorCache() {
		super();
	}

	public List<Carrieroperator> getByOperationPosition(String operationPosition) {
		List<Carrieroperator> carrieroperators = new ArrayList<Carrieroperator>();

		String cahceKey = getCacheKey();

		String jsonList = redisSvc.hget(cahceKey, operationPosition);
		if (StringUtils.isNotEmpty(jsonList) && !jsonList.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			carrieroperators = JSON.parseArray(jsonList, Carrieroperator.class);
		}
		return carrieroperators;
	}

	public int setByOperationPosition(List<Carrieroperator> carrieroperators) {
		if (!CollectionUtils.isEmpty(carrieroperators)) {
			String cahceKey = getCacheKey();
			String field = carrieroperators.get(0).getOperationPosition();

			String jsonString = JSON.toJSONString(carrieroperators);
			redisSvc.hdel(cahceKey, field);
			redisSvc.hset(cahceKey, field, jsonString);

			return carrieroperators.size();
		}

		return 0;
	}

	private String getCacheKey() {
		return CacheConstant.CACHE_APP_CARRIEROPERATOR;
	}

}
