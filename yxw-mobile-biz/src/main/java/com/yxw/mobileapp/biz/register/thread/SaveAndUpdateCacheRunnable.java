/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-15</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.register.thread;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.mobileapp.biz.register.thread
 * @ClassName: UnLockRegSourceThread
 * @Statement:
 * 			<p>
 *             挂号记录保存到缓存
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SaveAndUpdateCacheRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(SaveAndUpdateCacheRunnable.class);
	private RegisterRecord record;
	private String opType;
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	public SaveAndUpdateCacheRunnable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SaveAndUpdateCacheRunnable(RegisterRecord record, String opType) {
		super();
		this.record = record;
		this.opType = opType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("CacheRunnable.opType : {}", opType);
		if (CacheConstant.CACHE_OP_ADD.equalsIgnoreCase(opType) && record != null) {
			setRecordToCache(record);
		} else if (CacheConstant.CACHE_OP_UPDATE.equalsIgnoreCase(opType) && record != null) {
			updateRecordStatus(record);
		} else if (CacheConstant.CACHE_OP_DEL.equalsIgnoreCase(opType) && record != null) {
			delRecord(record);
		}
	}

	public void delRecord(RegisterRecord record) {
		// 删除普通的key-value 记录
		List<Object> params = new ArrayList<Object>();
		String cacheKey = CacheConstant.CACHE_REG_RECORD_INFO_PREFIX.concat(record.getOrderNo());
		params.add(cacheKey);
		serveComm.delete(CacheType.COMMON_CACHE, "delCache", params);

		// 删除hash结构中的记录
		if (record.getRegStatus() != null && RegisterConstant.STATE_NORMAL_HAVING == record.getRegStatus().intValue()) {
			cacheKey = CacheConstant.CACHE_REGISTER_HAVING_HASH.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(record.getHospitalCode());
			String fieldName = record.getPayTimeoutTime().toString().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(record.getOpenId());

			params.clear();
			params.add(cacheKey);
			params.add(fieldName);
			serveComm.delete(CacheType.COMMON_CACHE, "delHashCache", params);
		}

	}

	public void updateRecordStatus(RegisterRecord record) {
		// 更新状态信息
		List<Object> params = new ArrayList<Object>();
		params.add(record);
		serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordStatus", params);

		// 如果是异常的挂号记录 写入异常缓存队列
		if (record.getIsException() != null && record.getIsException().intValue() == BizConstant.IS_HAPPEN_EXCEPTION_YES) {
			logger.info("订单号:{} , regStatus[{}], payStatus:[{}],写入异常队列.",
					new Object[] { record.getOrderNo(), record.getRegStatus(), record.getPayStatus() });
			params.clear();
			params.add(record.convertExceptionObj());
			serveComm.set(CacheType.REGISTER_EXCEPTION_CACHE, "setExceptionRegisterToCache", params);
		}
	}

	/**
	 * 同时保存挂号记录和支付信息到缓存
	 * 
	 * @param record
	 * @param pay
	 * @return
	 */
	public Boolean setRecordToCache(RegisterRecord record) {
		List<Object> params = new ArrayList<Object>();
		String cacheKey = null;
		String json = null;
		try {
			// 添加key-value 记录
			cacheKey = CacheConstant.CACHE_REG_RECORD_INFO_PREFIX.concat(record.getOrderNo());
			json = JSON.toJSONString(record);
			params.add(cacheKey);
			params.add(CacheConstant.ONE_DAY_SECONDS);
			params.add(json);
			serveComm.set(CacheType.COMMON_CACHE, "setExpireObjToCache", params);

			// 预约中 状态 加入到未支付缓存队列
			if (record.getRegStatus() != null && ( OrderConstant.STATE_NO_PAYMENT == record.getPayStatus().intValue() )) {
				// 存储的key
				if (record.getPayTimeoutTime() != null || record.getPayTimeoutTime() != 0) {
					if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAVING) {
						cacheKey = CacheConstant.CACHE_REGISTER_HAVING_HASH.concat(record.getHospitalCode());
					} else if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAD) {
						cacheKey = CacheConstant.CACHE_REGISTER_NOT_PAY_HAD_HASH.concat(record.getHospitalCode());
					}
					String fieldName =
							record.getPayTimeoutTime().toString().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(record.getOpenId());

					params.clear();
					params.add(cacheKey);
					params.add(fieldName);
					params.add(JSON.toJSONString(record.convertSimpleObj()));
					serveComm.set(CacheType.COMMON_CACHE, "setHashCache", params);
				}
			}
		} catch (Exception e) {
			logger.error("setRecordToCache error. errorMsg: {}. cause by: {}.", new Object[] { e.getMessage(), e.getCause() });
			return false;
		}

		return true;
	}

	public RegisterRecord getRecord() {
		return record;
	}

	public void setRecord(RegisterRecord record) {
		this.record = record;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

}
