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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.commons.vo.platform.register.SimpleRecord;
import com.yxw.framework.cache.redis.RedisLock;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.platform.cache
 * @ClassName: RegisterRecordCache
 * @Statement: <p>
 *             挂号记录缓存
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-14
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterRecordCache {
	private Logger logger = LoggerFactory.getLogger(RegisterRecordCache.class);
	//	private RegisterService recordSvc = SpringContextHolder.getBean(RegisterService.class);

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	/**************************** 预约中挂号记录的hash结构缓存处理 start ****************************/
	//	public void initCache() {
	//		//预约中
	//		List<SimpleRecord> records = recordSvc.findNotPayRecord();
	//		if (!CollectionUtils.isEmpty(records)) {
	//			Map<String, Map<String, String>> allDatas = new HashMap<String, Map<String, String>>();
	//			Map<String, String> hospitalData = null;
	//			for (SimpleRecord record : records) {
	//				if (record.getPayTimeoutTime() == null) {
	//					continue;
	//				}
	//
	//				String cacheKey = null;
	//				if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAVING) {
	//					cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_HAVING_HASH, record.getHospitalCode());
	//				} else if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAD) {
	//					cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_NOT_PAY_HAD_HASH, record.getHospitalCode());
	//				} else {
	//					continue;
	//				}
	//				hospitalData = allDatas.get(cacheKey);
	//				boolean isNeedPut = false;
	//				if (hospitalData == null) {
	//					hospitalData = new HashMap<String, String>();
	//					isNeedPut = true;
	//				}
	//
	//				String fieldName = getHashFieldName(record);
	//				hospitalData.put(fieldName, JSON.toJSONString(record));
	//				if (isNeedPut) {
	//					allDatas.put(cacheKey, hospitalData);
	//				}
	//			}
	//
	//			if (!CollectionUtils.isEmpty(allDatas)) {
	//				redisSvc.pipelineDatas(allDatas);
	//			}
	//		}
	//	}

	/**
	 * (所有医院)批量保存挂号记录到缓存
	 * @param records
	 * @return
	 */
	public int setNotPayRegsToCache(List<SimpleRecord> records) {
		//预约中
		if (!CollectionUtils.isEmpty(records)) {
			Map<String, Map<String, String>> allDatas = new HashMap<String, Map<String, String>>();
			Map<String, String> hospitalData = null;
			for (SimpleRecord record : records) {
				if (record.getPayTimeoutTime() == null) {
					continue;
				}

				String cacheKey = null;
				if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAVING) {
					cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_HAVING_HASH, record.getHospitalCode());
				} else if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAD) {
					cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_NOT_PAY_HAD_HASH, record.getHospitalCode());
				} else {
					continue;
				}
				hospitalData = allDatas.get(cacheKey);
				boolean isNeedPut = false;
				if (hospitalData == null) {
					hospitalData = new HashMap<String, String>();
					isNeedPut = true;
				}

				String fieldName = getHashFieldName(record);
				hospitalData.put(fieldName, JSON.toJSONString(record));
				if (isNeedPut) {
					allDatas.put(cacheKey, hospitalData);
				}
			}

			if (!CollectionUtils.isEmpty(allDatas)) {
				redisSvc.pipelineDatas(allDatas);
				return allDatas.size();
			}
		}

		return 0;
	}

	/**
	 * (同一医院)未支付的挂号记录写入缓存  使用redis的hash数据结构
	 * @param records
	 */
	public int setNotPayRegsToCache(SimpleRecord... records) {
		if (records.length == 0 && records[0] == null) {
			return 0;
		}

		//存储的key
		String cacheKey = null;
		SimpleRecord simpleRecord = records[0];
		if (simpleRecord.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAVING) {
			cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_HAVING_HASH, simpleRecord.getHospitalCode());
		} else if (simpleRecord.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAD) {
			cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_NOT_PAY_HAD_HASH, simpleRecord.getHospitalCode());
		}
		Map<String, String> dataMap = new HashMap<String, String>();
		for (SimpleRecord record : records) {
			//hash结构的field
			String fieldName = getHashFieldName(record);
			dataMap.put(fieldName, JSON.toJSONString(record));
		}

		if (!dataMap.isEmpty()) {
			redisSvc.hmset(cacheKey, dataMap);

			return dataMap.size();
		}

		return 0;
	}

	/**
	 * 移除指定支付超时的挂号记录(预约中状态)  <br>
	 * @param record
	 */
	public int removeNotPayFormCache(SimpleRecord record) {
		//存储的key
		String cacheKey = null;
		if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAVING) {
			cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_HAVING_HASH, record.getHospitalCode());
		} else if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAD) {
			cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_NOT_PAY_HAD_HASH, record.getHospitalCode());
		}
		//hash结构的field
		String fieldName = getHashFieldName(record);
		redisSvc.hdel(cacheKey, fieldName);

		return 1;
	}

	/**
	 * 处理支付超时的挂号记录
	 * @param hospitalCode
	 * @return
	 */
	public List<SimpleRecord> handleTimeOutRegister(String hospitalCode) {
		// TODO Auto-generated method stub
		List<SimpleRecord> records = new ArrayList<SimpleRecord>();

		try {
			Set<String> fieldNameSet = new HashSet<String>();

			//预约中(未支付)
			String havingCacheKey = CacheConstant.CACHE_REGISTER_HAVING_HASH.concat(hospitalCode);
			Set<String> havingSet = redisSvc.hkeys(havingCacheKey);
			if (!CollectionUtils.isEmpty(havingSet)) {
				logger.info("havingCacheKey:{},hospitalCode:{} , havingSet.size():{}", havingCacheKey, hospitalCode, havingSet.size());
				fieldNameSet.addAll(havingSet);
			}

			//已预约(未支付)
			String hadNotpayCacheKey = CacheConstant.CACHE_REGISTER_NOT_PAY_HAD_HASH.concat(hospitalCode);
			Set<String> notPayHadSet = redisSvc.hkeys(hadNotpayCacheKey);
			if (!CollectionUtils.isEmpty(notPayHadSet)) {
				logger.info("hadNotpayCacheKey:{},hospitalCode:{} , notPayHadSet.size():{}", hadNotpayCacheKey, hospitalCode,
						notPayHadSet.size());
				fieldNameSet.addAll(notPayHadSet);
			}

			if (!CollectionUtils.isEmpty(fieldNameSet)) {
				long nowTime = System.currentTimeMillis();
				List<String> needRemoves = new ArrayList<String>();
				for (String fieldName : fieldNameSet) {
					long overTime = Long.valueOf(fieldName.split(CacheConstant.CACHE_KEY_SPLIT_CHAR)[0]);
					if (overTime <= nowTime) {
						needRemoves.add(fieldName);
					}
				}
				if (needRemoves.size() > 0) {
					String[] fieldNames = new String[needRemoves.size()];
					List<String> datas = new ArrayList<String>();
					if (havingSet.size() > 0) {
						List<String> havingDatas = redisSvc.hmget(havingCacheKey, needRemoves.toArray(fieldNames));
						if (!CollectionUtils.isEmpty(havingDatas)) {
							logger.info("havingCacheKey:{},hospitalCode:{},needRemoves:{}", havingCacheKey, hospitalCode,
									needRemoves.size());
							redisSvc.hdel(havingCacheKey, needRemoves.toArray(fieldNames));
							datas.addAll(havingDatas);
						} else {
							logger.info("havingCacheKey:{},hospitalCode:{} , needRemoves:{}", havingCacheKey, hospitalCode, 0);
						}
					}
					if (notPayHadSet.size() > 0) {
						List<String> notPayHadDatas = redisSvc.hmget(hadNotpayCacheKey, needRemoves.toArray(fieldNames));
						if (!CollectionUtils.isEmpty(notPayHadDatas)) {
							logger.info("hadNotpayCacheKey:{},hospitalCode:{} , needRemoves:{}", hadNotpayCacheKey, hospitalCode,
									needRemoves.size());
							redisSvc.hdel(hadNotpayCacheKey, needRemoves.toArray(fieldNames));
							datas.addAll(notPayHadDatas);
						} else {
							logger.info("hadNotpayCacheKey:{},hospitalCode:{} , needRemoves:{}", hadNotpayCacheKey, hospitalCode, 0);
						}
					}

					if (!CollectionUtils.isEmpty(datas)) {
						for (String json : datas) {
							if (StringUtils.isNotBlank(json) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(json)) {
								records.add(JSON.parseObject(json, SimpleRecord.class));
							}
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return records;
	}

	/**************************** 预约中挂号记录的hash结构缓存处理  end ****************************/

	/**************************** 待解锁号源挂号记录的缓存队列 操作 start ****************************/
	/**
	 * 将不同医院需要解锁的挂号记录写入缓存  cacheKey：register.need.unlock:hospitalCode
	 * @param records
	 */
	public int setNeedUnLockRegToCache(List<SimpleRecord> records) {
		if (!CollectionUtils.isEmpty(records)) {
			Map<String, List<String>> allDatas = new HashMap<String, List<String>>();
			for (SimpleRecord record : records) {
				String cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_NEED_UNLOCK, record.getHospitalCode());
				boolean isNeedPut = false;
				List<String> hospitalData = allDatas.get(cacheKey);
				if (hospitalData == null) {
					hospitalData = new ArrayList<String>();
					isNeedPut = true;
				}
				hospitalData.add(JSON.toJSONString(record));

				if (isNeedPut) {
					allDatas.put(cacheKey, hospitalData);
				}
			}
			redisSvc.pipelineLDatas(allDatas);

			return allDatas.size();
		}

		return 0;
	}

	/**
	 * 需要解锁的挂号记录写入缓存  使用redis的链表结构 按照先进先出的原则存储 插入到链表的尾部
	 * @param record
	 */
	public int setNeedUnLockRegToCache(SimpleRecord record) {
		//存储的key
		String cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_NEED_UNLOCK, record.getHospitalCode());
		String val = JSON.toJSONString(record);
		return redisSvc.rpush(cacheKey, val).intValue();
	}

	/**
	 * 获取医院需要解锁的号源  按照先进先出的原则获取 头部元素 
	 */
	public List<SimpleRecord> getNeedUnLockRegFormCache(String hospitalCode) {
		List<SimpleRecord> result = null;
		String cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_NEED_UNLOCK, hospitalCode);
		SimpleRecord record = null;
		String val = redisSvc.lpop(cacheKey);
		//判断是否未空 或者是否存在
		if (StringUtils.isNotEmpty(val) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(val)) {
			record = JSON.parseObject(val, SimpleRecord.class);
			result = new ArrayList<SimpleRecord>(1);
			result.add(record);
		}
		return result == null ? new ArrayList<SimpleRecord>(0) : result;
	}

	/**
	 * 获取医院所有需要解锁的号源
	 */
	public List<SimpleRecord> getNeedAllUnLockRegFormCache(String hospitalCode) {
		List<SimpleRecord> records = new ArrayList<SimpleRecord>();
		String cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_NEED_UNLOCK, hospitalCode);
		SimpleRecord record = null;
		List<String> jsonVals = redisSvc.lrange(cacheKey, 0, -1);
		for (String jsonVal : jsonVals) {
			//判断是否未空 或者是否存在
			if (StringUtils.isNotEmpty(jsonVal) && !CacheConstant.CACHE_NULL_STRING.equalsIgnoreCase(jsonVal)) {
				record = JSON.parseObject(jsonVal, SimpleRecord.class);
				records.add(record);
			}
		}
		return records;
	}

	/**
	 * 获取医院需要解锁的号源 按照先进先出的原则获取 头部元素
	 */
	public int getNeedUnLockRegNumFormCache(String hospitalCode) {
		String cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_NEED_UNLOCK, hospitalCode);
		return redisSvc.llen(cacheKey).intValue();
	}

	/**
	 * 将待解锁挂号记录移除待解锁队列
	 * @param hospitalCode
	 * @return
	 */
	public int removeNeedUnLockRegFormCache(String hospitalCode) {
		String cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_NEED_UNLOCK, hospitalCode);

		redisSvc.lpop(cacheKey);

		return 1;
	}

	/**************************** 待解锁号源挂号记录的缓存队列 操作 end ****************************/

	/**
	 * 处理医院的异常挂号
	 * @param hospitalCode
	 */
	@SuppressWarnings("unused")
	public void handlerException(String hospitalCode) {
		String cacheKey = getCacheKey(CacheConstant.CACHE_REGISTER_NEED_UNLOCK, hospitalCode);
		RedisLock redisLock = new RedisLock(redisSvc.getRedisPool());
		boolean isLock = false;
	}

	/**
	 * 根据RegisterRecord 生成cacheKey   挂号的状态 的标志:医院的code
	 * @param type  CACHE_REGISTER_HAVING_HASH/CACHE_REGISTER_NEED_UNLOCK
	 * @param record
	 * @return
	 */
	public static String getCacheKey(String type, String hospitalCode) {
		String cacheKey = type;
		if (StringUtils.isNotBlank(hospitalCode)) {
			cacheKey = cacheKey.concat(hospitalCode);
		}
		return cacheKey;
	}

	/**
	 * 根据RegisterRecord 生成redis hash结构中存储的Fieldname
	 * @param record
	 * @return
	 */
	private String getHashFieldName(SimpleRecord record) {
		String fieldName = record.getPayTimeoutTime().toString().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(record.getOpenId());
		return fieldName;
	}

	public int updateCacheForPayFail(RegisterRecord record) {
		String cacheKey = CacheConstant.CACHE_REG_RECORD_INFO_PREFIX.concat(record.getOrderNo());
		Jedis jedis = redisSvc.getRedisClient();
		boolean broken = false;
		if (jedis != null) {
			try {
				String json = jedis.get(cacheKey);
				if (StringUtils.isNotEmpty(json) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(json)) {
					RegisterRecord cacheRecord = JSON.parseObject(json, RegisterRecord.class);
					cacheRecord.setIsHadCallBack(record.getIsHadCallBack());
					cacheRecord.setFailureMsg(record.getFailureMsg());
					cacheRecord.setUpdateTime(new Date().getTime());
					jedis.set(cacheKey, JSON.toJSONString(cacheRecord));
				}
			} catch (Exception e) {
				broken = true;
			} finally {
				redisSvc.returnResource(jedis, broken);
			}
		}

		return broken ? -1 : 1;
	}

	/****************************  RegisterRecord  cache  start ********************************/
	/**
	 * 更新挂号记录的完整信息到缓存
	 * @param record
	 */
	public int updateRecordToCache(RegisterRecord record) {
		String json = JSON.toJSONString(record);
		String cacheKey = CacheConstant.CACHE_REG_RECORD_INFO_PREFIX.concat(record.getOrderNo());
		//设置 7天自动过期
		redisSvc.setex(cacheKey, CacheConstant.ONE_DAY_SECONDS, json);

		return 1;
	}

	/**
	 * 根据订单号在缓存中得到挂号记录
	 * @param orderNo
	 * @return
	 */
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

	/**
	 * 根据订单号在缓存中得到挂号记录
	 * @param orderNo
	 * @return
	 */
	public List<RegisterRecord> getRecordFromCache(String orderNo, Jedis jedis) {
		List<RegisterRecord> result = null;
		RegisterRecord record = null;
		String cacheKey = CacheConstant.CACHE_REG_RECORD_INFO_PREFIX.concat(orderNo);
		String json = jedis.get(cacheKey);
		if (StringUtils.isNotBlank(json) && !json.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			record = JSON.parseObject(json, RegisterRecord.class);
			result = new ArrayList<RegisterRecord>(1);
			result.add(record);
		}
		return result == null ? new ArrayList<RegisterRecord>(0) : result;
	}

	/**
	 * 更新异常挂号记录的状态
	 * @param record
	 */
	public int updateExceptionRecordsStatusToCache(ExceptionRecord exceptionRecord) {
		logger.info("updateExceptionRecordsStatusToCache start --- > 订单号:{} , record info:{}", new Object[] { exceptionRecord.getOrderNo(),
				JSON.toJSONString(exceptionRecord) });
		String cahceKey = CacheConstant.CACHE_REG_RECORD_INFO_PREFIX.concat(exceptionRecord.getOrderNo());
		boolean broken = false;
		Jedis jedis = redisSvc.getRedisClient();
		if (jedis != null) {
			try {
				String jsonRecord = jedis.get(cahceKey);
				if (StringUtils.isNotBlank(jsonRecord) && !jsonRecord.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
					RegisterRecord reocrd = JSON.parseObject(jsonRecord, RegisterRecord.class);
					if (exceptionRecord.getRegStatus() != null) {
						reocrd.setRegStatus(exceptionRecord.getRegStatus());
					}
					if (exceptionRecord.getPayStatus() != null) {
						reocrd.setPayStatus(exceptionRecord.getPayStatus());
					}
					if (exceptionRecord.getIsException() != null) {
						reocrd.setIsException(exceptionRecord.getIsException());
					}
					if (StringUtils.isNotBlank(exceptionRecord.getHandleLog())) {
						reocrd.setHandleLog(exceptionRecord.getHandleLog());
					}
					if (exceptionRecord.getHandleCount() != null) {
						reocrd.setHandleCount(exceptionRecord.getHandleCount());
					}
					if (exceptionRecord.getIsHandleSuccess() != null) {
						reocrd.setIsHandleSuccess(exceptionRecord.getIsHandleSuccess());
					}
					if (StringUtils.isNotBlank(exceptionRecord.getAgtRefundOrdNum())) {
						reocrd.setAgtRefundOrdNum(exceptionRecord.getAgtRefundOrdNum());
					}
					if (StringUtils.isNotBlank(exceptionRecord.getAgtOrdNum())) {
						reocrd.setAgtOrdNum(exceptionRecord.getAgtOrdNum());
					}

					jedis.setex(cahceKey, CacheConstant.ONE_DAY_SECONDS, JSON.toJSONString(reocrd));
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				broken = true;

			} finally {
				redisSvc.returnResource(jedis, broken);
			}

			logger.info("updateExceptionRecordsStatusToCache end --- > 订单号:{} , record info:{}",
					new Object[] { exceptionRecord.getOrderNo(), JSON.toJSONString(exceptionRecord) });
		}

		return broken ? -1 : 1;
	}

	/**
	 * 更新挂号记录的状态
	 * @param record
	 */
	public int updateRecordsStatusToCache(List<SimpleRecord> records, Integer Status) {
		Set<String> keySet = new HashSet<String>();
		for (SimpleRecord record : records) {
			keySet.add(CacheConstant.CACHE_REG_RECORD_INFO_PREFIX.concat(record.getOrderNo()));
		}
		String[] cacheKeys = new String[keySet.size()];
		boolean broken = false;
		Jedis jedis = redisSvc.getRedisClient();
		if (jedis != null) {
			try {
				List<String> recordJsons = jedis.mget(keySet.toArray(cacheKeys));
				if (!CollectionUtils.isEmpty(recordJsons)) {
					List<String> keyValList = new ArrayList<String>();
					for (String json : recordJsons) {
						RegisterRecord record = JSON.parseObject(json, RegisterRecord.class);
						//只更新未支付的挂号记录为超时取消  避免出现超时轮询中正好支付成功
						if (record.getPayStatus() == OrderConstant.STATE_NO_PAYMENT) {
							record.setRegStatus(Status);
							String cacheKey = CacheConstant.CACHE_REG_RECORD_INFO_PREFIX.concat(record.getOrderNo());
							keyValList.add(cacheKey);
							keyValList.add(JSON.toJSONString(record));
						}
					}
					if (!CollectionUtils.isEmpty(keyValList)) {
						String[] keysvalues = new String[keyValList.size()];
						jedis.mset(keyValList.toArray(keysvalues));

						return keyValList.size();
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				broken = true;

			} finally {
				redisSvc.returnResource(jedis, broken);
			}
		}

		return broken ? -1 : 0;
	}

	/**
	 * 更新挂号记录交易信息  状态、第3方支付、退款订单号到缓存
	 * @param record
	 */
	public int updateRecordTradeInfoToCache(RegisterRecord record) {
		boolean broken = false;
		Jedis jedis = redisSvc.getRedisClient();
		if (jedis != null) {
			try {
				String cacheKey = CacheConstant.CACHE_REG_RECORD_INFO_PREFIX.concat(record.getOrderNo());
				String json = jedis.get(cacheKey);
				if (StringUtils.isNotBlank(json) && !json.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
					RegisterRecord cacheRecord = JSON.parseObject(json, RegisterRecord.class);
					logger.info("updateRecordToCache record. RegStatus:{},payStatus:{}",
							new Object[] { record.getRegStatus(), record.getPayStatus() });
					cacheRecord.setRegStatus(record.getRegStatus());
					cacheRecord.setPayStatus(record.getPayStatus());
					cacheRecord.setUpdateTime(record.getUpdateTime());

					if (record.getIsHadCallBack() != null) {
						cacheRecord.setIsHadCallBack(record.getIsHadCallBack());
					}

					if (StringUtils.isNotBlank(record.getRefundOrderNo())) {
						cacheRecord.setRefundOrderNo(record.getRefundOrderNo());
					}

					if (StringUtils.isNotBlank(record.getAgtOrdNum())) {
						cacheRecord.setAgtOrdNum(record.getAgtOrdNum());
					}

					if (StringUtils.isNotBlank(record.getAgtRefundOrdNum())) {
						cacheRecord.setAgtRefundOrdNum(record.getAgtRefundOrdNum());
					}

					if (StringUtils.isNotBlank(record.getFailureMsg())) {
						cacheRecord.setFailureMsg(record.getFailureMsg());
					}
					jedis.set(cacheKey, JSON.toJSONString(cacheRecord));

					return 1;
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				broken = true;

			} finally {
				redisSvc.returnResource(jedis, broken);
			}
		}

		return broken ? -1 : 0;
	}

	public int updateRecordStatus(RegisterRecord record) {
		int updateCount = 0;

		String cacheKey = CacheConstant.CACHE_REG_RECORD_INFO_PREFIX.concat(record.getOrderNo());
		String json = redisSvc.get(cacheKey);
		if (StringUtils.isNotBlank(json) && !json.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			RegisterRecord cacheRecord = JSON.parseObject(json, RegisterRecord.class);
			boolean isNeedUpdateCache = true;

			//避免第3方回调已经成功  但是用户网速慢请求服务器查询慢导致失败
			if (record.getPayStatus() != null && record.getRegStatus().intValue() == OrderConstant.STATE_PAYMENTING) {
				if (cacheRecord.getPayStatus().intValue() == OrderConstant.STATE_PAYMENT) {
					isNeedUpdateCache = false;
				}
			}

			if (isNeedUpdateCache) {
				if (record.getUpdateTime() != null) {
					cacheRecord.setUpdateTime(record.getUpdateTime());
				}

				if (record.getRegStatus() != null) {
					cacheRecord.setRegStatus(record.getRegStatus());
				}

				if (record.getPayStatus() != null) {
					cacheRecord.setPayStatus(record.getPayStatus());
				}
				if (StringUtils.isNotEmpty(record.getAgtOrdNum())) {
					cacheRecord.setAgtOrdNum(record.getAgtOrdNum());
				}
				if (StringUtils.isNotEmpty(record.getRefundOrderNo())) {
					cacheRecord.setRefundOrderNo(record.getRefundOrderNo());
				}
				if (StringUtils.isNotEmpty(record.getAgtRefundOrdNum())) {
					cacheRecord.setAgtRefundOrdNum(record.getAgtRefundOrdNum());
				}
				if (StringUtils.isNotEmpty(record.getRefundHisOrdNo())) {
					cacheRecord.setRefundHisOrdNo(record.getRefundHisOrdNo());
				}
				if (record.getIsHadCallBack() != null) {
					cacheRecord.setIsHadCallBack(record.getIsHadCallBack());
				}
				if (record.getIsException() != null) {
					cacheRecord.setIsException(record.getIsException());
				}
				if (StringUtils.isNotBlank(record.getHandleLog())) {
					cacheRecord.setHandleLog(record.getHandleLog());
				}
				if (StringUtils.isNotBlank(record.getHisOrdNo())) {
					cacheRecord.setHisOrdNo(record.getHisOrdNo());
				}
				if (StringUtils.isNotBlank(record.getSerialNum())) {
					cacheRecord.setSerialNum(record.getSerialNum());
				}
				if (StringUtils.isNotBlank(record.getVisitLocation())) {
					cacheRecord.setVisitLocation(record.getVisitLocation());
				}

				if (StringUtils.isNotBlank(record.getFailureMsg())) {
					cacheRecord.setFailureMsg(record.getFailureMsg());
				}

				logger.info("cacheRecord :" + JSON.toJSONString(cacheRecord));
				redisSvc.setex(cacheKey, CacheConstant.ONE_DAY_SECONDS, JSON.toJSONString(cacheRecord));
			}
		}

		return updateCount;
	}
	/****************************  RegisterRecord  cache  end ********************************/
	//public void updateRecord
}
