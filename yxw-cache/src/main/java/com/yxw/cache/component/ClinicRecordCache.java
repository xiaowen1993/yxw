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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
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
public class ClinicRecordCache {
	private Logger logger = LoggerFactory.getLogger(ClinicRecordCache.class);

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	/**
	 * 写入缓存 设置缓存时间为7天
	 * @param record
	 * @return
	 */
	public int saveRecord(ClinicRecord record) {
		Jedis jedis = redisSvc.getRedisClient();
		boolean broken = false;
		if (jedis == null) {
			logger.info("there is no jedis clinet for the order. orderNo[" + record.getOrderNo() + "]");
			return 0;
		}

		try {
			if (record != null) {
				String json = JSON.toJSONString(record);
				String cacheKey = CacheConstant.CACHE_CLINIC_RECORD_INFO_PREFIX.concat(record.getOrderNo());
				jedis.setex(cacheKey, CacheConstant.SEVEN_DAY_SECONDS, json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save clinicRecord error. orderNo: {}, errorMessage: {}, cause by: {}.",
					new Object[] { record.getOrderNo(), e.getMessage(), e.getCause() });
			broken = true;
		} finally {
			// 发生异常，则将连接还回去
			redisSvc.returnResource(jedis, broken);
		}

		return broken ? -1 : 1;
	}

	public List<ClinicRecord> getRecordByOrderNo(String orderNo) {
		List<ClinicRecord> result = null;

		ClinicRecord record = null;
		String cacheKey = CacheConstant.CACHE_CLINIC_RECORD_INFO_PREFIX.concat(orderNo);
		String json = redisSvc.get(cacheKey);
		if (StringUtils.isNotBlank(json) && !json.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			record = JSON.parseObject(json, ClinicRecord.class);
			result = new ArrayList<ClinicRecord>(1);
			result.add(record);
		}

		return result == null ? new ArrayList<ClinicRecord>(0) : result;
	}

	/**
	 * 更新缓存
	 * @param record
	 */
	public int updateRecord(ClinicRecord record) {
		String cacheKey = CacheConstant.CACHE_CLINIC_RECORD_INFO_PREFIX.concat(record.getOrderNo());
		Jedis jedis = redisSvc.getRedisClient();
		boolean broken = false;

		if (jedis != null) {
			try {
				String json = jedis.get(cacheKey);
				// 如果是空的话，redis会给我们返回一个nil的字符串
				if (StringUtils.isNotBlank(json) && !json.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
					ClinicRecord cacheRecord = JSON.parseObject(json, ClinicRecord.class);

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
					// 医院返回的取药等信息
					if (record.getClinicStatus() != null) {
						cacheRecord.setClinicStatus(record.getClinicStatus());
					}
					// 条形码
					if (StringUtils.isNotBlank(record.getBarcode())) {
						cacheRecord.setBarcode(record.getBarcode());
					}
					// 医院返回的取药等信息
					if (StringUtils.isNotBlank(record.getHisMessage())) {
						cacheRecord.setHisMessage(record.getHisMessage());
					}
					// 收据号
					if (StringUtils.isNotBlank(record.getReceiptNum())) {
						cacheRecord.setReceiptNum(record.getReceiptNum());
					}
					// 支付金额
					if (StringUtils.isNotBlank(record.getPayFee())) {
						cacheRecord.setPayFee(record.getPayFee());
					}
					// 总金额
					if (StringUtils.isNotBlank(record.getTotalFee())) {
						cacheRecord.setTotalFee(record.getTotalFee());
					}
					// 社保结算门诊流水号
					if (StringUtils.isNotBlank(record.getsSClinicNo())) {
						cacheRecord.setsSClinicNo(record.getsSClinicNo());
					}
					// 社保结算单据号
					if (StringUtils.isNotBlank(record.getsSBillNumber())) {
						cacheRecord.setsSBillNumber(record.getsSBillNumber());
					}
					// 是否允许医保结算
					if (StringUtils.isNotBlank(record.getCanUseInsurance())) {
						cacheRecord.setCanUseInsurance(record.getCanUseInsurance());
					}
					// 处方类型
					if (StringUtils.isNotBlank(record.getRecipeType())) {
						cacheRecord.setRecipeType(record.getRecipeType());
					}
					// 处方ID号
					if (StringUtils.isNotBlank(record.getRecipeId())) {
						cacheRecord.setRecipeId(record.getRecipeId());
					}
					// 最大处方号 
					if (StringUtils.isNotBlank(record.getMzBillId())) {
						cacheRecord.setMzBillId(record.getMzBillId());
					}
					// 退款流水号
					if (StringUtils.isNotBlank(record.getCancelSerialNo())) {
						cacheRecord.setCancelSerialNo(record.getCancelSerialNo());
					}
					// 退款单据号
					if (StringUtils.isNotBlank(record.getCancelBillNo())) {
						cacheRecord.setCancelBillNo(record.getCancelBillNo());
					}
					// 门诊类别
					if (StringUtils.isNotBlank(record.getMzCategory())) {
						cacheRecord.setMzCategory(record.getMzCategory());
					}
					// 接诊科室代码
					if (StringUtils.isNotBlank(record.getDeptCode())) {
						cacheRecord.setDeptCode(record.getDeptCode());
					}
					// 接诊医生代码
					if (StringUtils.isNotBlank(record.getDoctorCode())) {
						cacheRecord.setDoctorCode(record.getDoctorCode());
					}
					// 自费金额
					if (StringUtils.isNotBlank(record.getPayAmout())) {
						cacheRecord.setPayAmout(record.getPayAmout());
					}
					// 个人账户结算金额
					if (StringUtils.isNotBlank(record.getAccountAmout())) {
						cacheRecord.setAccountAmout(record.getAccountAmout());
					}
					// 统筹基金结算金额
					if (StringUtils.isNotBlank(record.getMedicareAmount())) {
						cacheRecord.setMedicareAmount(record.getMedicareAmount());
					}
					// 总金额
					if (StringUtils.isNotBlank(record.getTotalAmout())) {
						cacheRecord.setTotalAmout(record.getTotalAmout());
					}
					// 记账合计
					if (StringUtils.isNotBlank(record.getInsuranceAmout())) {
						cacheRecord.setInsuranceAmout(record.getInsuranceAmout());
					}
					// 日志
					if (StringUtils.isNotBlank(record.getHandleLog())) {
						cacheRecord.setHandleLog(record.getHandleLog());
					}
					if (record.getHandleCount() != null) {
						cacheRecord.setHandleCount(record.getHandleCount());
					}
					if (record.getSsItems() != null) {
						cacheRecord.setSsItems(record.getSsItems());
					}
					if (record.getIsClaim() != null) {
						cacheRecord.setIsClaim(record.getIsClaim());
					}
					/**------------------------------------- end of 修改信息  ----------------------------------------**/
					// 保存
					jedis.setex(cacheKey, CacheConstant.SEVEN_DAY_SECONDS, JSON.toJSONString(cacheRecord));
				}

				// 如果是异常的挂号记录 写入异常缓存队列
				/**
				 * exception 是用来标注有没有发生过错误 handleSuccess 表示是否处理成功 异常队列里面，只要放那些没有处理成功的数据（必定有异常）
				 */
				// if (record.getIsException() != null && record.getIsException().intValue() ==
				// BizConstant.IS_HAPPEN_EXCEPTION_YES) {
				if (record.getIsHandleSuccess() != null && record.getIsHandleSuccess().intValue() == BizConstant.HANDLED_FAILURE) {
					logger.info("there is an exception order. orderNo[".concat(record.getOrderNo()).concat(
							"], here to put it to the exception clinic list"));
					cacheKey = getExcetionCacheKey();
					String val = JSON.toJSONString(record);
					redisSvc.zadd(cacheKey, record.getNextFireTime(), val);
				}
			} catch (Exception e) {
				logger.error("update clinicRecord error. orderNo: {}, errorMessage: {}, cause by: {}.", new Object[] { record.getOrderNo(),
						e.getMessage(),
						e.getCause() });
				broken = true;
			} finally {
				redisSvc.returnResource(jedis, broken);
			}
		} else {
			logger.info("there is no jedis clinet for the order. orderNo[" + record.getOrderNo() + "]");
		}

		return broken ? -1 : 1;
	}

	private String getExcetionCacheKey() {
		return CacheConstant.CACHE_CLINIC_EXCEPTION_SORTEDSET;
	}

	public int updateExceptionRecord(ClinicRecord record) {
		boolean broken = false;
		try {
			String cacheKey = CacheConstant.CACHE_CLINIC_RECORD_INFO_PREFIX.concat(record.getOrderNo());
			String json = redisSvc.get(cacheKey);
			// 如果是空的话，redis会给我们返回一个nil的字符串
			if (StringUtils.isNotBlank(json) && !json.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				ClinicRecord cacheRecord = JSON.parseObject(json, ClinicRecord.class);

				/** -------------------------------------- 修改信息 ------------------------------------ **/
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
				// 支付状态
				if (record.getPayStatus() != null) {
					cacheRecord.setPayStatus(record.getPayStatus());
				}
				// 业务状态
				if (record.getClinicStatus() != null) {
					cacheRecord.setClinicStatus(record.getClinicStatus());
				}
				// 条形码
				if (StringUtils.isNotBlank(record.getBarcode())) {
					cacheRecord.setBarcode(record.getBarcode());
				}
				// 医院返回的取药等信息
				if (StringUtils.isNotBlank(record.getHisMessage())) {
					cacheRecord.setHisMessage(record.getHisMessage());
				}
				// 收据号
				if (StringUtils.isNotBlank(record.getReceiptNum())) {
					cacheRecord.setReceiptNum(record.getReceiptNum());
				}
				// 社保结算门诊流水号
				if (StringUtils.isNotBlank(record.getsSClinicNo())) {
					cacheRecord.setsSClinicNo(record.getsSClinicNo());
				}
				// 社保结算单据号
				if (StringUtils.isNotBlank(record.getsSBillNumber())) {
					cacheRecord.setsSBillNumber(record.getsSBillNumber());
				}
				// 退款流水号
				if (StringUtils.isNotBlank(record.getCancelSerialNo())) {
					cacheRecord.setCancelSerialNo(record.getCancelSerialNo());
				}
				// 退款单据号
				if (StringUtils.isNotBlank(record.getCancelBillNo())) {
					cacheRecord.setCancelBillNo(record.getCancelBillNo());
				}
				// 日志
				if (StringUtils.isNotBlank(record.getHandleLog())) {
					cacheRecord.setHandleLog(record.getHandleLog());
				}
				if (record.getHandleCount() != null) {
					cacheRecord.setHandleCount(record.getHandleCount());
				}
				if (record.getSsItems() != null) {
					cacheRecord.setSsItems(record.getSsItems());
				}
				/** ------------------------------------- end of 修改信息 ---------------------------------------- **/
				// 保存
				redisSvc.setex(cacheKey, CacheConstant.SEVEN_DAY_SECONDS, JSON.toJSONString(cacheRecord));
			}
		} catch (Exception e) {
			logger.error("update clinicRecord error. orderNo: {}, errorMessage: {}, cause by: {}.",
					new Object[] { record.getOrderNo(), e.getMessage(), e.getCause() });
			broken = true;
		}

		return broken ? -1 : 1;
	}

	/**
	 * 删除缓存
	 * @param record
	 */
	public int deleteRecord(ClinicRecord record) {
		Jedis jedis = redisSvc.getRedisClient();
		boolean broken = false;

		if (jedis != null) {
			try {
				String cacheKey = CacheConstant.CACHE_CLINIC_RECORD_INFO_PREFIX.concat(record.getOrderNo());
				jedis.hdel(cacheKey);
			} catch (Exception e) {
				logger.error("delete clinicRecord error. orderNo: {}, errorMessage: {}, cause by: {}.", new Object[] { record.getOrderNo(),
						e.getMessage(),
						e.getCause() });
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
