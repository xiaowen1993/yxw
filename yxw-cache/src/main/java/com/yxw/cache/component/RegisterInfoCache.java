/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */
package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package com.yxw.platform.cache
 * @ClassName OrderCache
 * @Statement 避免 RegisterRecordCache与RegisterServiceImpl的重复调用而引起的Spring报错
 *             故把 订单缓存的存与取写在此类中供RegisterServiceImpl调用  而不写在RegisterRecordCache中
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-14
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterInfoCache {
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	public List<RegisterRecord> getRecordFromCache(String orderNo) {
		List<RegisterRecord> result = null;
		RegisterRecord record = null;
		String cacheKey = CacheConstant.CACHE_REG_RECORD_INFO_PREFIX.concat(orderNo);
		String json = redisSvc.get(cacheKey);
		if (StringUtils.isNotBlank(json) && !json.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			record = JSON.parseObject(json, RegisterRecord.class);
			result = new ArrayList<RegisterRecord>(1);
			result.add(record);
		}
		return result == null ? new ArrayList<RegisterRecord>(0) : result;
	}

	public int setRecordToCache(RegisterRecord record) {
		String json = JSON.toJSONString(record);
		String cacheKey = CacheConstant.CACHE_REG_RECORD_INFO_PREFIX.concat(record.getOrderNo());
		//设置 7天自动过期
		redisSvc.setex(cacheKey, CacheConstant.SEVEN_DAY_SECONDS, json);
		return 1;
	}

	public int updateRecordRefundOrderNo(String orderNo, String refundOrderNo) {
		List<RegisterRecord> records = getRecordFromCache(orderNo);
		if (CollectionUtils.isNotEmpty(records)) {
			RegisterRecord record = records.get(0);
			if (record != null) {
				record.setRefundOrderNo(refundOrderNo);
				return setRecordToCache(record);
			}
		}

		return 0;
	}
}
