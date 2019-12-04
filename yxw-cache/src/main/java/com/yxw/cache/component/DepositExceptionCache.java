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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.mobile.biz.inpatient.DepositRecord;
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
public class DepositExceptionCache {
	public static Logger logger = LoggerFactory.getLogger(DepositExceptionCache.class);

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	/**
	 * 初始化异常订单队列
	 */
	//	public void initCache() {
	//		CacheConstant.logger.info("init depositExceptionRecord Cache start......................");
	//		InpatientService service = SpringContextHolder.getBean(InpatientService.class);
	//		List<DepositRecord> depositExceptionRecords = service.findAllNeedHandleExceptionRecords();
	//		Map<String, List<String>> groupJsonMap = new HashMap<String, List<String>>();
	//		List<String> groupJsonRecords = null;
	//		String cacheKey = null;
	//		if (!CollectionUtils.isEmpty(depositExceptionRecords)) {
	//			for (DepositRecord record : depositExceptionRecords) {
	//				boolean isNeedPut = false;
	//				cacheKey = getCacheKey(record.getHospitalCode());
	//				groupJsonRecords = groupJsonMap.get(cacheKey);
	//				if (groupJsonRecords == null) {
	//					groupJsonRecords = new ArrayList<String>();
	//					isNeedPut = true;
	//				}
	//				groupJsonRecords.add(JSON.toJSONString(record));
	//				if (isNeedPut) {
	//					groupJsonMap.put(cacheKey, groupJsonRecords);
	//				}
	//			}
	//
	//			//删除缓存中的异常队列, 确保不会出现重复
	//			if (!groupJsonMap.isEmpty()) {
	//				redisSvc.del(groupJsonMap.keySet().toArray(new String[groupJsonMap.keySet().size()]));
	//				redisSvc.pipelineLDatas(groupJsonMap);
	//			}
	//		}
	//
	//		CacheConstant.logger.info("init depositExceptionRecord Cache end......................");
	//	}

	/**
	 * 批量保存住院押金异常记录到缓存
	 * @param records
	 * @return
	 */
	public int setExceptionRecordsToCache(List<DepositRecord> records) {
		boolean successed = false;

		CacheConstant.logger.info("init depositExceptionRecord Cache start......................");
		Map<String, List<String>> groupJsonMap = new HashMap<String, List<String>>();
		List<String> groupJsonRecords = null;
		String cacheKey = null;
		if (!CollectionUtils.isEmpty(records)) {
			for (DepositRecord record : records) {
				boolean isNeedPut = false;
				cacheKey = getCacheKey(record.getHospitalCode());
				groupJsonRecords = groupJsonMap.get(cacheKey);
				if (groupJsonRecords == null) {
					groupJsonRecords = new ArrayList<String>();
					isNeedPut = true;
				}
				groupJsonRecords.add(JSON.toJSONString(record));
				if (isNeedPut) {
					groupJsonMap.put(cacheKey, groupJsonRecords);
				}
			}

			//删除缓存中的异常队列, 确保不会出现重复
			if (!groupJsonMap.isEmpty()) {
				redisSvc.del(groupJsonMap.keySet().toArray(new String[groupJsonMap.keySet().size()]));
				successed = redisSvc.pipelineLDatas(groupJsonMap);
			}
		}

		CacheConstant.logger.info("init depositExceptionRecord Cache end......................");

		return successed ? groupJsonMap.size() : 0;
	}

	/**
	 * 异常挂号记录存入 缓存的异常队列
	 * @param Record
	 * @return
	 */
	public int setExceptionRecordToCache(DepositRecord record) {
		logger.info("redis set the record to last of the list...");
		//存储的key
		String cacheKey = getCacheKey(record.getHospitalCode());
		String val = JSON.toJSONString(record);
		return redisSvc.rpush(cacheKey, val).intValue();
	}

	/**
	 * 异常挂号记录存入 缓存的异常队列
	 * @param Record
	 * @return
	 */
	//	public void setExceptionRecordToCache(DepositRecord record, Jedis jedis) {
	//		logger.info("redis set the record to last of the list...");
	//		//存储的key
	//		String cacheKey = getCacheKey(record.getHospitalCode());
	//		String val = JSON.toJSONString(record);
	//		jedis.rpush(cacheKey, val);
	//	}

	/**
	 * 从异常队列获取index 0(链表的头部) 的挂号记录
	 * @param Record
	 * @return
	 */
	public List<DepositRecord> getExceptionRecordFromCache(String hospitalCode) {
		List<DepositRecord> result = null;
		DepositRecord record = null;
		logger.info("redis get the first record from the list...");
		String cacheKey = getCacheKey(hospitalCode);
		String val = redisSvc.lindex(cacheKey, 0);
		//判断是否未空 或者是否存在
		if (StringUtils.isNotEmpty(val) && !CacheConstant.CACHE_NULL_STRING.equalsIgnoreCase(val)) {
			record = JSON.parseObject(val, DepositRecord.class);
			result = new ArrayList<DepositRecord>(1);
			result.add(record);
		}
		return result == null ? new ArrayList<DepositRecord>(0) : result;
	}

	/**
	 * 将队列的头部元素移出异常挂号队列  
	 * @param hospitalCode
	 */
	//	public void removeExceptionRecordFromCache(String hospitalCode) {
	//		logger.info("redis remove the first record from the list...");
	//		String cacheKey = getCacheKey(hospitalCode);
	//		redisSvc.lpop(cacheKey);
	//	}

	/**
	 * 将队列的头部元素移出异常挂号队列  
	 * @param hospitalCode
	 * @return
	 */
	public int removeExceptionRecordFromCache(DepositRecord record) {
		logger.info("redis remove the first record from the list...");
		String cacheKey = getCacheKey(record.getHospitalCode());

		String jsonVal = redisSvc.lpop(cacheKey);
		//判断是否未空 或者是否存在
		if (StringUtils.isNotEmpty(jsonVal) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonVal)) {
			//			record = JSON.parseObject(jsonVal, DepositRecord.class);
			return 1;
		}
		return 0;
	}

	public static String getCacheKey(String hospitalCode) {
		return CacheConstant.CACHE_DEPOSIT_EXCEPTION + hospitalCode;
	}

}
