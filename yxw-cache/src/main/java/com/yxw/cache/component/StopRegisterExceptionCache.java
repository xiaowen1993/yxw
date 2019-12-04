/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-5-24</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.vo.cache.StopRegisterException;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.cache.component
 * @ClassName: StopRegisterExceptionCache
 * @Statement: <p>停诊异常缓存</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月21日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class StopRegisterExceptionCache {
	public static Logger logger = LoggerFactory.getLogger(StopRegisterExceptionCache.class);

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	/**
	 * 停诊异常记录存入 缓存的异常队列
	 * @param Record
	 * @return
	 */
	public int setExceptionStopRegisterToCache(String hospitalCode, StopRegisterException stopRegisterException) {
		//存储的key
		String fieldName =
				hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(stopRegisterException.getBranchCode())
						.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(stopRegisterException.getRegType());
		String val = JSON.toJSONString(stopRegisterException);
		return redisSvc.hset(getCacheKey(), fieldName, val).intValue();
	}

	/**
	 * 停诊异常记录存入 缓存的异常队列
	 * @param Record
	 * @return
	 */
	public int setExceptionStopRegisterToCache(String hospitalCode, StopRegisterException stopRegisterException, Jedis jedis) {
		//存储的key
		String fieldName =
				hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(stopRegisterException.getBranchCode())
						.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(stopRegisterException.getRegType());
		String val = JSON.toJSONString(stopRegisterException);
		return jedis.hset(getCacheKey(), fieldName, val).intValue();
	}

	/**
	 * 获取全部停诊异常队列
	 * 
	 * @category
	 * @param Record
	 * @return
	 */
	public List<Object> getStopExceptionRegisterFromCache() {
		List<Object> results = null;

		Map<String, String> StopRegOrderMaps = redisSvc.hgetAll(getCacheKey());
		if (!CollectionUtils.isEmpty(StopRegOrderMaps)) {
			results = new ArrayList<Object>(1);
			results.add(StopRegOrderMaps);
		}

		return results == null ? new ArrayList<Object>(0) : results;
	}

	/**
	 * 将队列元素移出异常挂号队列  
	 * @param hospitalCode
	 */
	public int removeExceptionStopRegisterFromCache(String hospitalCode, StopRegisterException stopRegisterException) {
		String cacheKey = getCacheKey();
		String fieldName =
				hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(stopRegisterException.getBranchCode())
						.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(stopRegisterException.getRegType());
		return redisSvc.hdel(cacheKey, fieldName).intValue();
	}

	/**
	 * 将队列元素移出异常挂号队列  
	 * @param hospitalCode
	 */
	public int removeExceptionStopRegisterFromCache(String hospitalCode, String branchCode, String regType) {
		String cacheKey = getCacheKey();
		String fieldName =
				hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(branchCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
						.concat(regType);
		return redisSvc.hdel(cacheKey, fieldName).intValue();
	}

	/**
	 * 删除停诊所有异常缓存
	 */
	public int removeExceptionStopRegisterAll() {
		Map<String, String> map = (Map<String, String>) getStopExceptionRegisterFromCache().get(0);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			return redisSvc.hdel(getCacheKey(), entry.getKey()).intValue();
		}

		return 0;
	}

	public static String getCacheKey() {
		return CacheConstant.CACHE_STOP_REGISTER_EXCEPTION;
	}

	/**
	 * 停诊阻止订单缓存key
	 * @return
	 */
	public static String getPreventCacheKey() {
		return CacheConstant.CACHE_STOP_REGISTER_PREVENT;
	}

	/**
	 * 添加停诊阻止订单缓存
	 * @param stopRegOrders
	 */
	public int addStopRegOrders(Map<String, String> stopRegOrders) {
		logger.info("添加停诊阻止订单缓存:{}", JSON.toJSONString(stopRegOrders));
		redisSvc.hmset(getPreventCacheKey(), stopRegOrders);

		return stopRegOrders.size();
	}

	/**
	 * 获取所有停诊阻止订单缓存
	 * 
	 * @category 存疑
	 * @return
	 */
	public List<Object> getStopRegOrders() {
		List<Object> results = null;

		Map<String, String> StopRegOrderMaps = redisSvc.hgetAll(getPreventCacheKey());
		if (!CollectionUtils.isEmpty(StopRegOrderMaps)) {
			results = new ArrayList<Object>(1);
			results.add(StopRegOrderMaps);
		}

		return results == null ? new ArrayList<Object>(0) : results;
	}

	/**
	 * 根据订单号获取停诊阻止订单缓存
	 * @param orderNo
	 * @return
	 */
	public String getStopRegOrdersByOrderNo(String orderNo) {
		return redisSvc.hget(getPreventCacheKey(), orderNo);
	}

	/**
	 * 判断当前订单是否已经存在停诊阻止订单缓存中
	 * @param orderNo
	 * @return
	 */
	//	public Boolean isFlagByOrderNo(String orderNo) {
	//		String value = getStopRegOrdersByOrderNo(orderNo);
	//		if (StringUtils.isNotBlank(value) && !value.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
	//			return false;
	//		} else {
	//			return true;
	//		}
	//	}

	/**
	 * 根据订单号删除停诊阻止订单缓存
	 * @param orderNo
	 */
	public int delStopRegOrdersByOrderNo(String orderNo) {
		logger.info("根据订单号删除停诊阻止订单缓存:{}", orderNo);
		return redisSvc.hdel(getPreventCacheKey(), orderNo).intValue();
	}

}
