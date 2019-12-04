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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * 零重复调用锁
 */
public class ReInvokeLockCache {

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	private static Logger logger = LoggerFactory.getLogger(ReInvokeLockCache.class);

	private final int EXPIRE_TIME = 1800;

	public ReInvokeLockCache() {
		super();
	}

	/***********************************挂号支付回调*******************************************/

	/**
	 *  挂号支付回调加锁
	 *  false 已经上锁[上锁失败]  true 未上锁[上锁成功]
	 * */
	public List<Boolean> getRegLock(String orderNo) {
		List<Boolean> result = new ArrayList<Boolean>(1);

		String key = getRegCallbackLockKey(orderNo);
		long islock = redisSvc.setnx(key, orderNo);
		if (islock == 1) {
			redisSvc.expire(key, EXPIRE_TIME);
			logger.info("挂号支付回调加锁,orderNo:{}", orderNo);
			result.add(true);
		} else {
			result.add(false);
		}

		return result;
	}

	/**
	 *  挂号支付回调解锁
	 * */
	public int delRegLock(String orderNo) {
		redisSvc.del(getRegCallbackLockKey(orderNo));
		logger.info("挂号支付回调解锁,orderNo:{}", orderNo);

		return 0;
	}

	private String getRegCallbackLockKey(String orderNo) {
		return CacheConstant.REG_PAY_CALLBACK_LOCK_STR.concat(orderNo);
	}

	/***********************************门诊支付回调*******************************************/

	/**
	 *  门诊支付回调加锁
	 *  false 已经上锁[上锁失败]  true 未上锁[上锁成功]
	 * */
	public List<Boolean> getClinicLock(String orderNo) {
		List<Boolean> result = new ArrayList<Boolean>(1);

		String key = getClinicCallbackLockKey(orderNo);
		long islock = redisSvc.setnx(key, orderNo);
		if (islock == 1) {
			redisSvc.expire(key, EXPIRE_TIME);
			logger.info("门诊支付回调加锁,orderNo:{}", orderNo);
			result.add(true);
		} else {
			result.add(false);
		}

		return result;
	}

	/**
	 *  门诊支付回调解锁
	 * */
	public int delClinicLock(String orderNo) {
		redisSvc.del(getClinicCallbackLockKey(orderNo));
		logger.info("门诊支付回调解锁,orderNo:{}", orderNo);

		return 0;
	}

	private String getClinicCallbackLockKey(String orderNo) {
		return CacheConstant.CLINIC_PAY_CALLBACK_LOCK_STR.concat(orderNo);
	}

	/**************************************************************************************/

	/**********************************停诊重复调用********************************************/

	/**
	 * 停诊重复调用-加锁
	 * false 已经上锁[上锁失败]  true 未上锁[上锁成功]
	 * */
	public List<Boolean> getRepeatStopRegLock(String orderNo) {
		List<Boolean> result = new ArrayList<Boolean>(1);

		String key = getRepeatStopRegLockKey(orderNo);
		long islock = redisSvc.setnx(key, orderNo);
		if (islock == 1) {
			redisSvc.expire(key, EXPIRE_TIME);
			logger.info("停诊重复调用-加锁,orderNo:{}", orderNo);
			result.add(true);
		} else {
			result.add(false);
		}
		return result;
	}

	/**
	 *  停诊重复调用-解锁
	 * */
	public int delRepeatStopRegLock(String orderNo) {
		redisSvc.del(getRepeatStopRegLockKey(orderNo));
		logger.info("停诊重复调用-解锁,orderNo:{}", orderNo);

		return 0;
	}

	private String getRepeatStopRegLockKey(String orderNo) {
		return CacheConstant.REPEAT_STOP_REG_LOCK_STR.concat(orderNo);
	}

	/**************************************************************************************/
}
