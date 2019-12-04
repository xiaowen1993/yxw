/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-24</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;

/**
 * @Package: com.yxw.cache.component
 * @ClassName: CommonCache
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-24
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class CommonCache {
	private Logger logger = LoggerFactory.getLogger(RegisterRecordCache.class);

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	public Boolean pipelineLDatas(Map<String, List<String>> datas) {

		Jedis jedis = redisSvc.getRedisClient();
		if (jedis == null) {
			return false;
		}
		boolean broken = false;
		try {
			jedis.pipelined();

			for (String dataKey : datas.keySet()) {
				List<String> data = datas.get(dataKey);
				String[] array = new String[data.size()];
				jedis.rpush(dataKey, data.toArray(array));
			}
			jedis.sync();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;

		} finally {
			redisSvc.returnResource(jedis, broken);
		}
		return true;
	}

	/**
	 * 批量把数据写入redis缓存服务器
	 * 
	 * @param datas
	 * @return
	 */
	public Boolean pipelineDatas(Map<String, Map<String, String>> datas) {
		Jedis jedis = redisSvc.getRedisClient();
		if (jedis == null) {
			return false;
		}
		boolean broken = false;
		try {
			jedis.pipelined();

			for (String dataKey : datas.keySet()) {
				jedis.hmset(dataKey, datas.get(dataKey));
			}

			jedis.sync();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;

		} finally {
			redisSvc.returnResource(jedis, broken);
		}
		return true;
	}

	public int delHashCache(String cacheKey, String cacheField) {
		return redisSvc.hdel(cacheKey, cacheField).intValue();
	}

	public int setHMCache(String cacheKey, Map<String, String> value) {
		try {
			redisSvc.hmset(cacheKey, value);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	public int setHashCache(String cacheKey, String cacheField, String obj) {
		if (StringUtils.isNotBlank(cacheKey) && StringUtils.isNotBlank(cacheField)) {
			return redisSvc.hset(cacheKey, cacheField, obj).intValue();
		} else {
			CacheConstant.logger.error("CommonCache.setHashCache is error. type:{}, cacheKey:{},cacheField:{}", new Object[] { obj
					.getClass().getName(), cacheKey, cacheField });

			return -1;
		}
	}

	public int delCache(String cacheKey) {
		return redisSvc.del(cacheKey) ? 1 : 0;
	}

	public int zaddCache(String cacheKey, Map<String, Double> value) {
		try {
			redisSvc.zadd(cacheKey, value);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * 保存信息到缓存 并设置过期时间
	 * @param key
	 * @param seconds  过期时间  单位秒
	 * @param json
	 */
	public int setExpireObjToCache(String key, Integer seconds, String json) {
		try {
			redisSvc.setex(key, seconds.intValue(), json);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * 从缓存中获取数据
	 * @param key
	 * @return
	 */
	public List<String> getValueFromCache(String key) {
		List<String> result = null;
		String value = redisSvc.get(key);
		if (value != null) {
			result = new ArrayList<String>(1);
			result.add(value);
		}

		return result != null ? result : new ArrayList<String>(0);
	}

	public <T> List<T> getHashCache(String cacheKey, String cacheField, Class<T> clazz) {
		List<T> result = null;
		T hashObj = null;
		try {
			String cacheVal = redisSvc.hget(cacheKey, cacheField);
			CacheConstant.logger.info("invoke CommonCache.getHashCache. type:{} , cacheVal:{}", new Object[] { clazz.getName(), cacheVal });
			if (StringUtils.isNotBlank(cacheVal) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(cacheVal)) {
				hashObj = JSON.parseObject(cacheVal, clazz);
				result = new ArrayList<T>();
				result.add(hashObj);
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			CacheConstant.logger.error("invoke CommonCache.getHashCache is error. type:{} , cacheKey:{}, cacheField {}",
					new Object[] { clazz.getName(), cacheKey, cacheField });
		}
		return result == null ? new ArrayList<T>(0) : result;
	}

	public int setValueFromCache(String cacheKey, String value) {
		try {

			redisSvc.set(cacheKey, value);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	public int setExpireFromCache(String cacheKey, Integer seconds) {
		try {
			redisSvc.expire(cacheKey, seconds.intValue());
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	public List<Long> getTtlFromCache(String cacheKey) {
		long ttl = redisSvc.ttl(cacheKey);
		List<Long> result = new ArrayList<Long>(1);
		result.add(ttl);

		return result;
	}
}
