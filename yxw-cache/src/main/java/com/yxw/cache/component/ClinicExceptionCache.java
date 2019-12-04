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
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
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
public class ClinicExceptionCache {
	public static Logger logger = LoggerFactory.getLogger(ClinicExceptionCache.class);

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	/**
	 * 批量门诊订单记录存入 缓存的异常队列
	 * @param Record
	 * @return
	 */
	public int setExceptionClinicsToCache(Map<String, Double> resourceMap) {

		if (!CollectionUtils.isEmpty(resourceMap)) {
			String cacheKey = getCacheKey();
			return redisSvc.zadd(cacheKey, resourceMap).intValue();
		}

		return -1;
	}

	/**
	 * 门诊订单记录存入 缓存的异常队列
	 * @param Record
	 * @return
	 */
	public int setExceptionClinicToCache(ClinicRecord record) {
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		String cacheKey = getCacheKey();
		String val = JSON.toJSONString(record);
		return redisSvc.zadd(cacheKey, record.getNextFireTime(), val).intValue();
	}

	/**
	 * 从异常有序集合中获取指定时间毫秒数之前的门诊订单
	 *
	 * @param timeMillis
	 * @return
	 */
	public List<ClinicRecord> getClinicExceptionFromCache(Long timeMillis) {
		List<ClinicRecord> records = Collections.EMPTY_LIST;
		ClinicRecord record = null;

		String cacheKey = getCacheKey();
		Set<String> vals = redisSvc.zrangeByScore(cacheKey, "-inf", timeMillis.toString());
		// 判断是否为空 或者是否存在
		if (!CollectionUtils.isEmpty(vals)) {
			records = new ArrayList<ClinicRecord>(vals.size());
			for (String val : vals) {
				if (!CacheConstant.CACHE_NULL_STRING.equalsIgnoreCase(val)) {
					record = JSON.parseObject(val, ClinicRecord.class);
					records.add(record);
				}
			}
		}

		return records;
	}

	/**
	 * 将指定时间毫秒数之前的门诊订单移出异常有序集合
	 *
	 * @param timeMillis
	 * @return
	 */
	public int removeClinicExceptionFromCache(Long timeMillis) {
		String cacheKey = getCacheKey();
		return redisSvc.zremrangeByScore(cacheKey, "-inf", timeMillis.toString()).intValue();
	}

	public static String getCacheKey() {
		return CacheConstant.CACHE_CLINIC_EXCEPTION_SORTEDSET;
	}

}
