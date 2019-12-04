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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.mobile.biz.inpatient.DepositRecord;
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
public class DepositRecordCache {
	private Logger logger = LoggerFactory.getLogger(DepositRecordCache.class);

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	/**
	 * 写入缓存 设置缓存时间为7天
	 * @param record
	 * @return
	 */
	public int saveRecord(DepositRecord record) {
		Jedis jedis = redisSvc.getRedisClient();
		boolean broken = false;
		if (jedis == null) {
			logger.info("there is no jedis clinet for the order. orderNo[" + record.getOrderNo() + "]");
			return -1;
		}

		try {
			if (record != null) {
				String json = JSON.toJSONString(record);
				String cacheKey = CacheConstant.CACHE_DEPOSIT_RECORD_INFO_PREFIX.concat(record.getOrderNo());
				//				jedis.setex(cacheKey, CacheConstant.SEVEN_DAY_SECONDS, json);
				// 测试存放20分钟
				jedis.setex(cacheKey, 12000, json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save depositRecord error. orderNo: {}, errorMessage: {}, cause by: {}.", new Object[] { record.getOrderNo(),
					e.getMessage(),
					e.getCause() });
			broken = true;
		} finally {
			// 发生异常，则将连接还回去
			redisSvc.returnResource(jedis, broken);
		}

		return broken ? -1 : 1;
	}

	/**
	 * 更新缓存
	 * @param record
	 */
	public int updateRecord(DepositRecord record) {
		String cacheKey = CacheConstant.CACHE_DEPOSIT_RECORD_INFO_PREFIX.concat(record.getOrderNo());
		Jedis jedis = redisSvc.getRedisClient();
		boolean broken = false;

		if (jedis != null) {
			try {
				String json = jedis.get(cacheKey);
				// 如果是空的话，redis会给我们返回一个nil的字符串
				if (StringUtils.isNotBlank(json) && !json.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
					DepositRecord cacheRecord = JSON.parseObject(json, DepositRecord.class);

					/**-------------------------------------- 修改信息  ------------------------------------**/
					// 更新时间
					if (record.getUpdateTime() != null) {
						cacheRecord.setUpdateTime(record.getUpdateTime());
					}
					// 支付时间
					if (record.getPayTime() != null) {
						cacheRecord.setPayTime(record.getPayTime());
					}
					// 退费时间
					if (record.getRefundTime() != null) {
						cacheRecord.setRefundTime(record.getRefundTime());
					}
					// 退费订单号
					if (StringUtils.isNotBlank(record.getRefundOrderNo())) {
						cacheRecord.setRefundOrderNo(record.getRefundOrderNo());
					}
					// 医院流水号
					if (StringUtils.isNotBlank(record.getHisOrdNo())) {
						cacheRecord.setHisOrdNo(record.getHisOrdNo());
					}
					// 医院退款流水号
					if (StringUtils.isNotBlank(record.getRefundHisOrdNo())) {
						cacheRecord.setRefundHisOrdNo(record.getRefundHisOrdNo());
					}
					// 交易机构收单流水号
					if (record.getAgtOrdNum() != null) {
						cacheRecord.setAgtOrdNum(record.getAgtOrdNum());
					}
					// 交易机构退费流水号
					if (StringUtils.isNotBlank(record.getAgtRefundOrdNum())) {
						cacheRecord.setAgtRefundOrdNum(record.getAgtRefundOrdNum());
					}
					// 费用说明
					if (StringUtils.isNotBlank(record.getFeeDesc())) {
						cacheRecord.setFeeDesc(record.getFeeDesc());
					}
					// 是否被回调
					if (record.getIsHadCallBack() != null) {
						cacheRecord.setIsHadCallBack(record.getIsHadCallBack());
					}
					// 操作失败信息
					if (StringUtils.isNotBlank(record.getFailureMsg())) {
						cacheRecord.setFailureMsg(record.getFailureMsg());
					}
					// 支付状态
					if (record.getPayStatus() != null) {
						cacheRecord.setPayStatus(record.getPayStatus());
					}
					// 押金业务状态
					if (record.getDepositStatus() != null) {
						cacheRecord.setDepositStatus(record.getDepositStatus());
					}
					// 收据号
					if (record.getReceiptNum() != null) {
						cacheRecord.setReceiptNum(record.getReceiptNum());
					}
					// 条形码
					if (record.getBarcode() != null) {
						cacheRecord.setBarcode(record.getBarcode());
					}
					// 当前余额
					if (record.getBalanceAfterPay() != null) {
						cacheRecord.setBalanceAfterPay(record.getBalanceAfterPay());
					}

					/**------------------------------------- end of 修改信息  ----------------------------------------**/
					// 保存
					//					jedis.setex(cacheKey, CacheConstant.SEVEN_DAY_SECONDS, JSON.toJSONString(cacheRecord));
					// 测试放20分钟。
					jedis.setex(cacheKey, 12000, JSON.toJSONString(cacheRecord));

				}

				//如果是异常的挂号记录 写入异常缓存队列
				/**
				 * exception 是用来标注有没有发生过错误
				 * handleSuccess 表示是否处理成功
				 * 异常队列里面，只要放那些没有处理成功的数据（必定有异常）
				 */
				// if (record.getIsException() != null && record.getIsException().intValue() == BizConstant.IS_HAPPEN_EXCEPTION_YES) {
				if (record.getIsHandleSuccess() != null && record.getIsHandleSuccess().intValue() == BizConstant.HANDLED_FAILURE) {
					logger.info("there is an exception order. orderNo[" + record.getOrderNo() + "], here to put it to the exception deposit list");
					cacheKey = getExcetionCacheKey(record.getHospitalCode());
					String val = JSON.toJSONString(record);
					jedis.rpush(cacheKey, val);
				}
			} catch (Exception e) {
				logger.error("update depositRecord error. orderNo: {}, errorMessage: {}, cause by: {}.",
						new Object[] { record.getOrderNo(), e.getMessage(), e.getCause() });
				broken = true;
			} finally {
				redisSvc.returnResource(jedis, broken);
			}
		} else {
			logger.info("there is no jedis clinet for the order. orderNo[" + record.getOrderNo() + "]");
		}
		
		return broken ? -1 : 1;
	}

	private String getExcetionCacheKey(String hospitalCode) {
		return CacheConstant.CACHE_DEPOSIT_EXCEPTION + hospitalCode;
	}

	/**
	 * 删除缓存
	 * @param record
	 */
	public int deleteRecord(DepositRecord record) {
		Jedis jedis = redisSvc.getRedisClient();
		boolean broken = false;

		if (jedis != null) {
			try {
				String cacheKey = CacheConstant.CACHE_DEPOSIT_RECORD_INFO_PREFIX.concat(record.getOrderNo());
				jedis.hdel(cacheKey);
			} catch (Exception e) {
				logger.error("delete depositRecord error. orderNo: {}, errorMessage: {}, cause by: {}.",
						new Object[] { record.getOrderNo(), e.getMessage(), e.getCause() });
				broken = true;
			} finally {
				redisSvc.returnResource(jedis, broken);
			}
		} else {
			logger.info("there is no jedis clinet for the order. orderNo[" + record.getOrderNo() + "]");
		}
		
		return broken ? -1 : 1;
	}
}
