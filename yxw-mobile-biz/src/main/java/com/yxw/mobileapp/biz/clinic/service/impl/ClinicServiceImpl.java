package com.yxw.mobileapp.biz.clinic.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.constants.pool.PoolConstant;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.ServerNoGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.utils.QueryHashTableCallable;
import com.yxw.mobileapp.biz.clinic.dao.ClinicRecordDao;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;
import com.yxw.mobileapp.biz.clinic.thread.SaveAndUpdateClinicCacheRunnable;
import com.yxw.mobileapp.biz.clinic.vo.ClinicQueryVo;
import com.yxw.mobileapp.datas.manager.ClinicBizManager;
import com.yxw.payrefund.utils.TradeCommonHoder;

@Service(value = "clinicService")
public class ClinicServiceImpl implements ClinicService {

	private Logger logger = LoggerFactory.getLogger(ClinicServiceImpl.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	private ClinicRecordDao clinicRecordDao = SpringContextHolder.getBean(ClinicRecordDao.class);

	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);

	@Override
	public ClinicRecord findRecordByOrderNo(String orderNo) {
		ClinicRecord record = null;

		List<Object> params = new ArrayList<Object>();
		params.add(orderNo);
		List<Object> results = serveComm.get(CacheType.CLINIC_RECORD_CACHE, "getRecordByOrderNo", params);

		try {
			if (CollectionUtils.isNotEmpty(results)) {
				record = (ClinicRecord) results.get(0);

				if (StringUtils.isBlank(record.getAppId())) {
					HospIdAndAppSecretVo hospIdAndAppSecretVo =
							baseDatasManager.getHospitalEasyHealthAppInfo(record.getHospitalId(), record.getAppCode());
					record.setAppId(hospIdAndAppSecretVo.getAppId());
				}
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("find record from cache error, make transfer error. orderNo={}, errorMsg={}, " + "cause by: {}.",
						new Object[] { orderNo, e.getMessage(), e.getCause() });
			}
		}

		// 缓存拿不到则从数据库拿
		if (record == null) {
			record = new ClinicRecord();
			record.setOrderNo(orderNo);
			record.setOrderNoHashVal(Math.abs(orderNo.hashCode()));

			record = clinicRecordDao.findByOrderNo(record);

			// 设置appId, appCode
			HospIdAndAppSecretVo hospIdAndAppSecretVo =
					baseDatasManager.getHospitalEasyHealthAppInfo(record.getHospitalId(), record.getAppCode());
			record.setAppId(hospIdAndAppSecretVo.getAppId());

			if (record != null) {
				logger.info("start a thread to save the record into cache where the record was not found from the cache first but database did...");
				Thread cacheThread =
						new Thread(new SaveAndUpdateClinicCacheRunnable(record, CacheConstant.CACHE_OP_ADD),
								"CacheRunnable.saveClinicRecord");
				cacheThread.start();
			}
		}

		return record;
	}

	@Override
	public ClinicRecord findRecordByOrderNoHash(String orderNoHash) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveRecord(ClinicRecord record) {
		clinicRecordDao.add(record);
	}

	/*	@Override
		public PayWechat buildWechatPayInfo(ClinicRecord record) {
			PayWechat pay = TradeCommonHoder.buildWechatPayInfo(record);
			addClinicPayInfo(pay, record);

			return pay;
		}

		@Override
		public PayAli buildAliPayInfo(ClinicRecord record) {
			PayAli pay = TradeCommonHoder.buildAliPayInfo(record);
			addClinicPayInfo(pay, record);

			return pay;
		}*/

	@Override
	public ClinicRecord getClinicRecord(ClinicRecord record) {
		return findRecordByOrderNo(record.getOrderNo());
	}

	@Override
	public void updateExceptionRecord(ClinicRecord record) {
		record.setUpdateTime(System.currentTimeMillis());
		clinicRecordDao.updateRecordStatusByOrderNoAndOpenId(record);
	}

	// 如果存入数据库异常，那么就不应该生成支付信息。
	/*Pay pay = null;
	if (record.getTradeMode().intValue() == BizConstant.TRADE_MODE_WEIXIN_VAL
			|| record.getTradeMode().intValue() == BizConstant.TRADE_MODE_APP_WEIXIN_VAL) {
		pay = buildWechatPayInfo(record);
	} else if (record.getTradeMode().intValue() == BizConstant.TRADE_MODE_ALIPAY_VAL
			|| record.getTradeMode().intValue() == BizConstant.TRADE_MODE_APP_ALIPAY_VAL) {
		pay = buildAliPayInfo(record);
	} else if (record.getTradeMode().intValue() == BizConstant.TRADE_MODE_UNIONPAY_VAL
			|| record.getTradeMode().intValue() == BizConstant.TRADE_MODE_APP_UNIONPAY_VAL) {

	}
	*/
	// 门诊缴费都是0 需要在线支付.
	//	pay.setOnlinePaymentControl(0);
	// 剩余时间0，与接口约束了，传入0则不显示剩余时间
	// pay.setPayTimeoutTime(0L);

	@Override
	public void saveRecordInfo(ClinicRecord record) {
		// 订单信息
		record.setPayStatus(OrderConstant.STATE_NO_PAYMENT); // 未支付
		record.setIsValid(ClinicConstant.RECORD_IS_VALID_TRUE); // 有效的订单
		record.setRecordTitle(ClinicConstant.DEFAULT_CLINIC_TITLE); // 暂时固定
		record.setClinicStatus(ClinicConstant.STATE_NO_PAY); // 待缴费

		// 开线程把订单信息存入缓存
		Thread cacheThread =
				new Thread(new SaveAndUpdateClinicCacheRunnable(record, CacheConstant.CACHE_OP_ADD), "CacheRunnable.saveClinicRecord");
		cacheThread.start();

		// 将订单信息存到数据库中
		saveRecord(record);

	}

	@Override
	public Object buildPayInfo(ClinicRecord record) {
		Object pay = null;
		String payModeCode = ModeTypeUtil.getTradeModeCode(record.getTradeMode());

		if (StringUtils.equals(payModeCode, TradeConstant.TRADE_MODE_WECHAT)) {
			pay = TradeCommonHoder.buildWechatPayInfo(record);
		} else if (StringUtils.equals(payModeCode, TradeConstant.TRADE_MODE_ALIPAY)) {
			pay = TradeCommonHoder.buildAlipayInfo(record);
		} else if (StringUtils.equals(payModeCode, TradeConstant.TRADE_MODE_UNIONPAY)) {
			// TODO 新增
			pay = TradeCommonHoder.buildUnionpayInfo(record);
		}

		return pay;
	}

	/*	@Override
		public PayParamsVo buildPayParam(ClinicRecord record) {
			String payModeCode = ModeTypeUtil.getTradeModeCode(record.getTradeMode());

			PayParamsVo payParams = new PayParamsVo();
			if (StringUtils.equals(payModeCode, BizConstant.TRADE_MODE_WECHAT)) {
				WechatPay payWechat = TradeCommonHoder.buildWechatPayInfo(record);
				BeanUtils.copyProperties(payWechat, payParams);
			} else if (StringUtils.equals(payModeCode, BizConstant.TRADE_MODE_ALIPAY)) {
				Alipay payAli = TradeCommonHoder.buildAlipayInfo(record);
				BeanUtils.copyProperties(payAli, payParams);
			} else if (StringUtils.equals(payModeCode, BizConstant.TRADE_MODE_UNIONPAY)) {
				// TODO 新增
			}

			return payParams;
		}*/

	@Override
	public void updateOrderPayInfo(ClinicRecord record) {
		// 开线程把订单信息存入缓存
		Thread cacheThread =
				new Thread(new SaveAndUpdateClinicCacheRunnable(record, CacheConstant.CACHE_OP_UPDATE), "CacheRunnable.updateClinicRecord");
		cacheThread.start();
		clinicRecordDao.updateRecordStatusByOrderNoAndOpenId(record);
	}

	@Override
	public Object buildRefundInfo(ClinicRecord record) {
		Object refund = null;
		try {
			String refundOrderNo =
					OrderNoGenerator.genOrderNo(String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode())),
							String.valueOf(record.getTradeMode()), OrderConstant.ORDER_TYPE_REFUND, BizConstant.BIZ_TYPE_REGISTER,
							ServerNoGenerator.getServerNoByIp(), record.getOpenId());
			record.setRefundOrderNo(refundOrderNo);
		} catch (Exception e) {
			throw new SystemException(e.getCause());
		}

		String payModeCode = ModeTypeUtil.getTradeModeCode(record.getTradeMode());

		if (StringUtils.equals(payModeCode, TradeConstant.TRADE_MODE_WECHAT)) {
			refund = TradeCommonHoder.buildWechatPayRefundInfo(record);
		} else if (StringUtils.equals(payModeCode, TradeConstant.TRADE_MODE_ALIPAY)) {
			refund = TradeCommonHoder.buildAlipayRefundInfo(record);
		} else if (StringUtils.equals(payModeCode, TradeConstant.TRADE_MODE_UNIONPAY)) {
			// TODO 新增
			refund = TradeCommonHoder.buildUnionpayRefundInfo(record);
		}

		clinicRecordDao.updateRecordStatusByOrderNoAndOpenId(record);

		return refund;
	}

	/**
	 * 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月14日 
	 * @param record
	 * @return
	 */
	public Object buildOrderQueryInfo(ClinicRecord record) {
		Object orderQuery = null;

		String payModeCode = ModeTypeUtil.getTradeModeCode(record.getTradeMode());

		if (StringUtils.equals(payModeCode, TradeConstant.TRADE_MODE_WECHAT)) {
			orderQuery = TradeCommonHoder.buildWechatPayOrderQueryInfo(record);
		} else if (StringUtils.equals(payModeCode, TradeConstant.TRADE_MODE_ALIPAY)) {
			orderQuery = TradeCommonHoder.buildAlipayOrderQueryInfo(record);
		} else if (StringUtils.equals(payModeCode, TradeConstant.TRADE_MODE_UNIONPAY)) {
			// TODO 新增
			orderQuery = TradeCommonHoder.buildUnionpayOrderQueryInfo(record);
		}

		return orderQuery;
	}

	/*
		@Override
		public WechatPayRefund buildRefundWechatInfo(ClinicRecord record, String invokeType) {
			try {
				String refundOrderNo =
						OrderNoGenerator.genOrderNo(String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode())),
								String.valueOf(record.getTradeMode()), BizConstant.ORDER_TYPE_REFUND, BizConstant.BIZ_TYPE_CLINIC,
								ServerNoGenerator.getServerNoByIp());
				record.setRefundOrderNo(refundOrderNo);
			} catch (Exception e) {
				throw new SystemException(e.getCause());
			}

			WechatPayRefund refund = TradeCommonHoder.buildWechatRefundInfo(record);

			// 门诊缴费只有系统的退费，用户无法主动发起退费。
			if (BizConstant.INVOKE_TYPE_SERVICE_REQ.equals(invokeType)) {
				clinicRecordDao.updateRecordStatusByOrderNoAndOpenId(record);
				return refund;
			} else {
				// 不进行处理
				logger.info("门诊缴费只有系统的退费，用户无法主动发起退费。[{}]", record.getOrderNo());
				record.setHandleLog("[" + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + "]门诊缴费只有系统的退费，用户无法主动发起退费。");
				clinicRecordDao.updateRecordStatusByOrderNoAndOpenId(record);
				return null;
			}

		}

		@Override
		public AlipayRefund buildRefundAliInfo(ClinicRecord record, String invokeType) {
			// 退费的单号必须在外部指定

			try {
				String refundOrderNo =
						OrderNoGenerator.genOrderNo(String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode())),
								String.valueOf(record.getTradeMode()), BizConstant.ORDER_TYPE_REFUND, BizConstant.BIZ_TYPE_CLINIC,
								ServerNoGenerator.getServerNoByIp());
				record.setRefundOrderNo(refundOrderNo);
			} catch (Exception e) {
				throw new SystemException(e.getCause());
			}

			//		String refundOrderNo = OrderNoGenerator.genOrderNo(record.getOrderType(), BizConstant.ORDER_TYPE_REFUND_ONLINE, BizConstant.BIZ_TYPE_CLINIC);
			//		record.setRefundOrderNo(refundOrderNo);
			AlipayRefund refund = TradeCommonHoder.buildAliRefundInfo(record);

			// 门诊缴费只有系统的退费，用户无法主动发起退费。
			if (BizConstant.INVOKE_TYPE_SERVICE_REQ.equals(invokeType)) {
				clinicRecordDao.updateRecordStatusByOrderNoAndOpenId(record);
				return refund;
			} else {
				// 不进行处理
				logger.info("门诊缴费只有系统的退费，用户无法主动发起退费。[{}]", record.getOrderNo());
				record.setHandleLog("[" + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + "]门诊缴费只有系统的退费，用户无法主动发起退费。");
				clinicRecordDao.updateRecordStatusByOrderNoAndOpenId(record);
				return null;
			}
		}*/

	@Override
	public List<ClinicRecord> getPaidList(ClinicRecord record, Integer queryMonths) {
		Long endTime = System.currentTimeMillis();

		// 默认为近3个月内
		if (queryMonths == null || queryMonths == 0) {
			queryMonths = 3;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, queryMonths * -1);
		Long beginTime = calendar.getTimeInMillis();

		record.setQueryBeginTime(beginTime);
		record.setQueryEndTime(endTime);
		return clinicRecordDao.findPaidClinicRecord(record);
	}

	@Override
	public ClinicRecord findPaidRecordByOrderNo(String orderNo, List<String> hashTableName) {
		return clinicRecordDao.findPaidRecordByOrderNo(orderNo, hashTableName);
	}

	@Override
	public List<ClinicRecord> findDownPaidRecord(String hospitalId, String branchCode, String tradeMode, String startTime, String endTime,
			String hashTableName) {
		return clinicRecordDao.findDownPaidRecord(hospitalId, branchCode, tradeMode, startTime, endTime, hashTableName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClinicRecord> findAllNeedHandleExceptionRecords() {
		long start = System.currentTimeMillis();
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String hashTableName = SimpleHashTableNameGenerator.CLINIC_RECORD_TABLE_NAME + "_" + i;
			Object[] parameters = new Object[] { hashTableName };
			Class<?>[] parameterTypes = new Class[] { String.class };
			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(ClinicRecordDao.class, "findAllNeedHandleExceptionRecords", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		List<ClinicRecord> records = new ArrayList<ClinicRecord>();
		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				Object obj = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (obj != null) {
					records.addAll((List<ClinicRecord>) obj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		if (logger.isDebugEnabled()) {
			if (!CollectionUtils.isEmpty(records)) {
				logger.debug("findAllNeedHandleExceptionRecords cast time :{}Millis . res:", new Object[] { System.currentTimeMillis()
						- start,
						JSON.toJSONString(records) });
			} else {
				logger.debug("findAllNeedHandleExceptionRecords cast time :{}Millis . res:", new Object[] { System.currentTimeMillis()
						- start,
						0 });
			}
		}

		return records;
	}

	@Override
	public ClinicRecord findPaidRecordByOrderNo(String orderNo, String hospitalCode) {
		long start = System.currentTimeMillis();
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String hashTableName = SimpleHashTableNameGenerator.CLINIC_RECORD_TABLE_NAME + "_" + i;
			Object[] parameters = new Object[] { orderNo, hospitalCode, hashTableName };
			Class<?>[] parameterTypes = new Class[] { String.class, String.class, String.class };
			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(ClinicRecordDao.class, "findByOrderNoAndHospitalCode", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		ClinicRecord record = null;
		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				Object obj = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (obj != null) {
					record = (ClinicRecord) obj;
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		if (logger.isDebugEnabled()) {
			if (record != null) {
				logger.debug("findByOrderNoAndHospitalCode cast time :{}Millis . res:", new Object[] { System.currentTimeMillis() - start,
						JSON.toJSONString(record) });
			} else {
				logger.debug("findByOrderNoAndHospitalCode cast time :{}Millis . res:", new Object[] { System.currentTimeMillis() - start,
						0 });
			}
		}

		return record;
	}

	@Override
	public List<ClinicRecord>
			findPartRefundRecord(String hospitalId, String branchCode, String tradeMode, String startTime, String endTime) {
		return clinicRecordDao.findPartRefundRecord(hospitalId, branchCode, tradeMode, startTime, endTime);
	}

	@Override
	public List<ClinicRecord> findPaidRecords(ClinicQueryVo vo) {
		return clinicRecordDao.findPaidRecords(vo);
	}

	@Override
	public List<ClinicRecord> findPaidRecordByIdNoAndOpenId(String openId, String idNo) {
		String clinicTableName = SimpleHashTableNameGenerator.getClinicRecordHashTable(openId, true);
		String medicalcardTableName = SimpleHashTableNameGenerator.getMedicalCardHashTable(openId, true);
		return clinicRecordDao.findPaidRecordByOpenIdAndIdNo(openId, idNo, clinicTableName, medicalcardTableName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClinicRecord> findPaidRecordByIdTypeAndIdNo(String idNo, Integer idType) {
		long start = System.currentTimeMillis();
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String clinicTableName = SimpleHashTableNameGenerator.CLINIC_RECORD_TABLE_NAME + "_" + i;
			String medicalcardTableName = SimpleHashTableNameGenerator.MEDICAL_CARD_TABLE_NAME + "_" + i;
			Object[] parameters = new Object[] { idNo, idType, clinicTableName, medicalcardTableName };
			Class<?>[] parameterTypes = new Class[] { String.class, Integer.class, String.class, String.class };
			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(ClinicRecordDao.class, "findPaidRecordByIdNoAndIdType", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		List<ClinicRecord> records = new ArrayList<ClinicRecord>();
		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				Object obj = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (obj != null) {
					records.addAll((List<ClinicRecord>) obj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		if (logger.isDebugEnabled()) {
			if (!CollectionUtils.isEmpty(records)) {
				logger.debug("findPaidRecordByIdNoAndIdType cast time :{}Millis . res:", new Object[] { System.currentTimeMillis() - start,
						JSON.toJSONString(records) });
			} else {
				logger.debug("findPaidRecordByIdNoAndIdType cast time :{}Millis . res:", new Object[] { System.currentTimeMillis() - start,
						0 });
			}
		}

		return records;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getPayList(List<MedicalCard> medicalCards) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ExecutorService collectExec = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("getPayList"));
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		for (int i = 0; i < medicalCards.size(); i++) {
			try {
				MedicalCard medicalCard = medicalCards.get(i);
				Object[] parameters = new Object[] { medicalCard };
				Class<?>[] parameterTypes = new Class[] { MedicalCard.class };

				QueryHashTableCallable collectCall =
						new QueryHashTableCallable(ClinicBizManager.class, "getMZFeeListEx", parameters, parameterTypes);
				// 创建每条指令的采集任务对象
				FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
				// 添加到list,方便后面取得结果
				taskList.add(collectTask);
				// 提交给线程池 exec.submit(task);
				collectExec.submit(collectTask);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				Map<String, Object> payList = (Map<String, Object>) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty((List<Object>) payList.get(BizConstant.COMMON_ENTITY_LIST_KEY))) {
					list.add(payList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return list;
	}

	@Override
	public ClinicRecord findSavedUnpaidClinicRecord(ClinicRecord record) {
		return clinicRecordDao.findSavedUnpaidClinicRecord(record);
	}

	@Override
	public void coverUnpaidRecord(ClinicRecord newOrder, ClinicRecord originalOrder) {
		try {
			// 清除缓存数据 
			List<Object> params = new ArrayList<>();
			params.add(originalOrder);
			serveComm.delete(CacheType.CLINIC_RECORD_CACHE, "deleteRecord", params);

			// 统一操作
			newOrder.setPayStatus(OrderConstant.STATE_NO_PAYMENT); // 未支付
			newOrder.setIsValid(ClinicConstant.RECORD_IS_VALID_TRUE); // 有效的订单
			newOrder.setRecordTitle(ClinicConstant.DEFAULT_CLINIC_TITLE); // 暂时固定
			newOrder.setClinicStatus(ClinicConstant.STATE_NO_PAY); // 待缴费

			// 数据库数据只做更新操作。
			// 开线程把订单信息存入缓存
			Thread cacheThread =
					new Thread(new SaveAndUpdateClinicCacheRunnable(newOrder, CacheConstant.CACHE_OP_ADD), "CacheRunnable.saveClinicRecord");
			cacheThread.start();

			// 将订单信息存到数据库中
			clinicRecordDao.updateCoverOrder(newOrder);
		} catch (Exception e) {
			logger.error("delete clinic record error. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}
	}

	@Override
	public List<ClinicRecord> findListByProcedure(Map map) {
		return clinicRecordDao.findListByProcedure(map);
	}

}
