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
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.platform.cache.component
 * @ClassName: RegisterExceptionCache
 * @Statement: <p>挂号异常缓存</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-24
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterExceptionCache {
	public static Logger logger = LoggerFactory.getLogger(RegisterExceptionCache.class);

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	public int setExceptionRegisterToCache(ExceptionRecord record) {
		//存储的key
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		String cacheKey = getCacheKey();
		String val = JSON.toJSONString(record);
		return redisSvc.zadd(cacheKey, record.getNextFireTime(), val).intValue();
	}

	/**
	 * 从异常有序集合中获取指定时间毫秒数之前的挂号记录
	 * 
	 * @param hospitalCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExceptionRecord> getExceptionRecordsFromCache(Long timeMillis) {
		String cacheKey = getCacheKey();
		Set<String> vals = redisSvc.zrangeByScore(cacheKey, "-inf", timeMillis.toString());
		List<ExceptionRecord> records = Collections.EMPTY_LIST;

		ExceptionRecord record = null;
		//判断是否未空 或者是否存在
		if (!CollectionUtils.isEmpty(vals)) {
			records = new ArrayList<ExceptionRecord>(vals.size());
			for (String val : vals) {
				if (!CacheConstant.CACHE_NULL_STRING.equalsIgnoreCase(val)) {
					record = JSON.parseObject(val, ExceptionRecord.class);
					records.add(record);
				}
			}
		}
		return records;
	}

	/**
	 * 将第三方退费异常挂号记录存入 缓存的异常队列
	 * @param Record
	 * @return
	 */
	public int setAgtRefundExceptionToCache(ExceptionRecord record) {
		logger.info("将第三方退费异常挂号记录存入 缓存的异常队列：{}", record.getOrderNo());
		//存储的key
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		String cacheKey = getAgtRefundCacheKey(record.getHospitalCode());
		String val = JSON.toJSONString(record);
		logger.info("orderNo:{},cacheKey:{}", record.getOrderNo(), cacheKey);
		return redisSvc.rpush(cacheKey, val).intValue();
	}

	/**
	 * 从第三方退费异常队列获取index 0(链表的头部) 的挂号记录
	 * @param Record
	 * @return
	 */
	public List<ExceptionRecord> getAgtRefundExceptionFromCache(String hospitalCode) {
		String cacheKey = getAgtRefundCacheKey(hospitalCode);
		String val = redisSvc.lpop(cacheKey);
		@SuppressWarnings("unchecked")
		List<ExceptionRecord> records = Collections.EMPTY_LIST;

		ExceptionRecord record = null;
		//判断是否未空 或者是否存在
		if (StringUtils.isNotEmpty(val) && !CacheConstant.CACHE_NULL_STRING.equalsIgnoreCase(val)) {
			records = new ArrayList<ExceptionRecord>(1);
			record = JSON.parseObject(val, ExceptionRecord.class);

			records.add(record);
		}

		return records;
	}

	/**
	 * 将指定时间毫秒数之前的元素移出异常有序集合
	 * 
	 * @param timeMillis
	 * @param hospitalCode
	 * @param exceptionRecords
	 * @return
	 */
	public int removeExceptionRecordsFromCache(Long timeMillis) {
		String cacheKey = getCacheKey();
		return redisSvc.zremrangeByScore(cacheKey, "-inf", timeMillis.toString()).intValue();
	}

	public static String getCacheKey() {
		return CacheConstant.CACHE_REGISTER_EXCEPTION_SORTEDSET;
	}

	public static String getAgtRefundCacheKey(String hospitalCode) {
		return CacheConstant.CACHE_AGT_REFUND_EXCEPTION.concat(hospitalCode);
	}

}
