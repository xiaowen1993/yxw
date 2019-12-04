/**
 * 
 */
package com.yxw.mobileapp.biz.register.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.commons.vo.platform.register.SimpleRecord;
import com.yxw.framework.common.ServerNoGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.utils.QueryHashTableCallable;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.register.dao.RegisterRecordDao;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.biz.register.thread.QueryRegsiterRecordCallable;
import com.yxw.mobileapp.biz.register.thread.SaveAndUpdateCacheRunnable;
import com.yxw.payrefund.utils.TradeCommonHoder;
import com.yxw.utils.DateUtils;

/**
 * @Package: com.yxw.mobileapp.biz.register.service.impl
 * @ClassName: RegisterConfirmServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository(value = "registerService")
public class RegisterServiceImpl implements RegisterService {
	private RegisterRecordDao registerDao = SpringContextHolder.getBean(RegisterRecordDao.class);
	//private RegisterOrderDao orderDao = SpringContextHolder.getBean(RegisterOrderDao.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	//private BaseDatasManager baseDataManager = SpringContextHolder.getBean(BaseDatasManager.class);

	private Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

	@Override
	public List<RegisterRecord> queryByMedicalCard(MedicalCard card) {
		// TODO Auto-generated method stub
		List<RegisterRecord> records = registerDao.findByMedicalCard(card);
		if (records == null) {
			records = new ArrayList<RegisterRecord>();
		}
		return records;
	}

	/**
	 * 更新挂号记录状态
	 * @param orderNo
	 * @param regStatus
	 * @param isException
	 */
	@Override
	public void updateRecordStatusByOrderNo(String orderNo, Integer regStatus, Boolean isException) {
		// TODO Auto-generated method stub
		RegisterRecord record = findRecordByOrderNo(orderNo);
		record.setRegStatus(regStatus);
		record.setUpdateTime( ( new Date() ).getTime());
		if (isException) {
			record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
			record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL_EXCEPTION);
		}

		//避免缓存存储比数据库慢  先保存缓存
		Thread cacheThread =
				new Thread(new SaveAndUpdateCacheRunnable(record, CacheConstant.CACHE_OP_UPDATE), "CacheRunnable.saveRegisterRecord");
		cacheThread.start();
		registerDao.update(record);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.register.service.RegisterService#findRecordForThreeMonth()
	 */
	@Override
	public List<RegisterRecord> findOverTimeRecords(String hospitalId, String branchHospitalId, String openId, String cardNo,
			List<Integer> regStatuses, Long overTime) {
		// TODO Auto-generated method stub
		return registerDao.findOverTimeRecords(hospitalId, branchHospitalId, openId, cardNo, regStatuses, overTime);
	}

	@Override
	public void updateRegisterRecordSatuts(Integer regStatus, List<String> recordIds, String hashTableName) {
		// TODO Auto-generated method stub
		registerDao.updateRegisterRecordSatuts(regStatus, recordIds, hashTableName);
	}

	public void updateRecordPayTimeOutStatus(List<String> recordIds, String hashTableName) {
		registerDao.updateRecordPayTimeOutStatus(recordIds, hashTableName);
	}

	@Override
	public List<String> queryNotPayMentByIds(List<String> recordIds, String hashTableName) {
		// TODO Auto-generated method stub
		return registerDao.queryNotPayMentByIds(recordIds, hashTableName);
	}

	public List<SimpleRecord> findNotPayRecord() {
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<List<SimpleRecord>>> taskList = new ArrayList<FutureTask<List<SimpleRecord>>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String tableName = SimpleHashTableNameGenerator.REGISTER_RECORD_TABLE_NAME + "_" + i;
			QueryRegsiterRecordCallable collectCall = new QueryRegsiterRecordCallable(tableName, OrderConstant.STATE_NO_PAYMENT);
			// 创建每条指令的采集任务对象
			FutureTask<List<SimpleRecord>> collectTask = new FutureTask<List<SimpleRecord>>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		List<SimpleRecord> records = new ArrayList<SimpleRecord>();
		try {
			for (FutureTask<List<SimpleRecord>> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<SimpleRecord> taskFRes = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty(taskFRes)) {
					records.addAll(taskFRes);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return records;
	}

	/**
	 * 查询已预约但未支付的的挂号记录
	 * @return
	 */
	public List<SimpleRecord> findNotPayHadRecord() {
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<List<SimpleRecord>>> taskList = new ArrayList<FutureTask<List<SimpleRecord>>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String tableName = SimpleHashTableNameGenerator.REGISTER_RECORD_TABLE_NAME + "_" + i;
			QueryRegsiterRecordCallable collectCall =
					new QueryRegsiterRecordCallable(tableName, RegisterConstant.STATE_NORMAL_HAD, OrderConstant.STATE_NO_PAYMENT);
			// 创建每条指令的采集任务对象
			FutureTask<List<SimpleRecord>> collectTask = new FutureTask<List<SimpleRecord>>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		List<SimpleRecord> records = new ArrayList<SimpleRecord>();
		try {
			for (FutureTask<List<SimpleRecord>> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<SimpleRecord> res = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty(res)) {
					records.addAll(records);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return records;
	}

	@Override
	public List<SimpleRecord> findHavingRecord() {
		// TODO Auto-generated method stub
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<List<SimpleRecord>>> taskList = new ArrayList<FutureTask<List<SimpleRecord>>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String tableName = SimpleHashTableNameGenerator.REGISTER_RECORD_TABLE_NAME + "_" + i;
			QueryRegsiterRecordCallable collectCall =
					new QueryRegsiterRecordCallable(tableName, RegisterConstant.STATE_NORMAL_HAVING, OrderConstant.STATE_NO_PAYMENT);
			// 创建每条指令的采集任务对象
			FutureTask<List<SimpleRecord>> collectTask = new FutureTask<List<SimpleRecord>>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		List<SimpleRecord> records = new ArrayList<SimpleRecord>();
		try {
			for (FutureTask<List<SimpleRecord>> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<SimpleRecord> res = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty(res)) {
					records.addAll(res);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return records;

	}

	@Override
	public void updateExceptionRecord(ExceptionRecord record) {
		// TODO Auto-generated method stub
		registerDao.updateExceptionRecord(record);
	}

	/* (non-Javadoc)
	 * @see com.yxw.mobileapp.biz.register.service.RegisterConfirmService#generateOrder(com.yxw.platform.register.entity.RegisterRecord)
	 */
	@Override
	public Map<String, Object> saveRegisterInfo(RegisterRecord record) {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = new HashMap<String, Object>();
		//挂号记录转化为订单信息
		// 实际需要支付的金额
		Integer tradeAmout = record.getRealRegFee() + record.getRealTreatFee();
		resMap.put(BizConstant.URL_PARAM_TRADE_AMOUT, tradeAmout);
		// 零元支付
		if (tradeAmout == 0 && record.getOnlinePaymentType().intValue() != BizConstant.PAYMENT_TYPE_NOT_NEED) {
			record.setPayStatus(OrderConstant.STATE_PAYMENT);
			record.setRegStatus(RegisterConstant.STATE_NORMAL_HAD);
		} else {
			record.setPayStatus(OrderConstant.STATE_NO_PAYMENT);
		}

		record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
		String recordTitle =
				BizConstant.BIZ_TYPE_REGISTER_NAME.concat(":").concat(record.getDeptName()).concat("-").concat(record.getDoctorName());
		record.setRecordTitle(recordTitle);

		//避免缓存存储比数据库慢  先保存缓存
		Thread cacheThread =
				new Thread(new SaveAndUpdateCacheRunnable(record, CacheConstant.CACHE_OP_ADD), "CacheRunnable.saveRegisterRecord");
		cacheThread.start();

		try {
			saveRecordInfo(record);
		} catch (Exception e) {
			logger.error("insert record is error. ", e);
			record.setRegStatus(RegisterConstant.STATE_EXCEPTION_HAVING);
			record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
			// exceptionCache.setExceptionRegisterToCache(record.convertExceptionObj());
			List<Object> params = new ArrayList<Object>();
			params.add(record.convertExceptionObj());
			serveComm.set(CacheType.REGISTER_EXCEPTION_CACHE, "setExceptionRegisterToCache", params);
		}

		Object pay = buildPayInfo(record);

		if (record.getRegStatus().intValue() != RegisterConstant.STATE_NORMAL_HAD) {
			resMap.put(BizConstant.TRADE_PAY_KEY, pay);
		} else {
			if (record.getOnlinePaymentType().intValue() == BizConstant.PAYMENT_TYPE_SUPPORT_TEMPORARILY_NOT && record.getIsPay() != null
					&& record.getIsPay().intValue() == BizConstant.PAYMENT_TYPE_SUPPORT_TEMPORARILY_NOT_IS_PAY_YES) {
				resMap.put(BizConstant.TRADE_PAY_KEY, pay);
				resMap.put("currentIsPay", record.getIsPay());

			}
			/*			if (pay != null) {
							//暂不支付   支付时间是为支付操作的最后截至时间  不作为支付超时来判定
							pay.setAgtTimeout(null);
						}*/
			resMap.put("currentRegStatus", record.getRegStatus());
			resMap.put("currentRegOrderNo", record.getOrderNo());
			resMap.put("currentOpenId", record.getOpenId());

			CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
			commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_LOCK_RES_SUCCESS);
		}

		return resMap;
	}

	/**
	 * 构建支付信息
	 * @param record
	 * @return
	 */
	public Object buildPayInfo(RegisterRecord record) {
		Object pay = null;
		String tradeModeCode = ModeTypeUtil.getTradeModeCode(record.getTradeMode());

		if (StringUtils.equals(tradeModeCode, TradeConstant.TRADE_MODE_WECHAT)) {
			pay = TradeCommonHoder.buildWechatPayInfo(record);
		} else if (StringUtils.equals(tradeModeCode, TradeConstant.TRADE_MODE_ALIPAY)) {
			pay = TradeCommonHoder.buildAlipayInfo(record);
		} else if (StringUtils.equals(tradeModeCode, TradeConstant.TRADE_MODE_UNIONPAY)) {
			// TODO 新增
			pay = TradeCommonHoder.buildUnionpayInfo(record);
		}

		return pay;
	}

	/**
	 * 构建订单查询信息
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月14日 
	 * @param record
	 * @return
	 */
	public Object buildOrderQueryInfo(RegisterRecord record) {
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

	/**
	 * 构建支付所需的参数
	 * @param record
	 * @return
	 */
	/*
	public PayParamsVo buildPayParam(RegisterRecord record) {
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
		Unionpay unionpay = TradeCommonHoder.buildUnionpayInfo(record);
		BeanUtils.copyProperties(unionpay, payParams);
	}

	if (record.getOnlinePaymentType().intValue() != BizConstant.PAYMENT_TYPE_MUST) {
		payParams.setPayTimeoutTime(null);
	}
	return payParams;
	}*/

	public void saveRecordInfo(RegisterRecord record) {
		if (record != null) {
			if (record.getUpdateTime() == null) {
				record.setUpdateTime(record.getRegisterTime());
			}
			registerDao.add(record);
		}
	}

	/**
	 * 构建退费信息
	 * @param record
	 * @return
	 */
	public Object buildRefundInfo(RegisterRecord record) {
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

		String tradeModeCode = ModeTypeUtil.getTradeModeCode(record.getTradeMode());

		if (StringUtils.equals(tradeModeCode, TradeConstant.TRADE_MODE_WECHAT)) {
			refund = TradeCommonHoder.buildWechatPayRefundInfo(record);
		} else if (StringUtils.equals(tradeModeCode, TradeConstant.TRADE_MODE_ALIPAY)) {
			refund = TradeCommonHoder.buildAlipayRefundInfo(record);
		} else if (StringUtils.equals(tradeModeCode, TradeConstant.TRADE_MODE_UNIONPAY)) {
			// TODO 新增
			refund = TradeCommonHoder.buildUnionpayRefundInfo(record);
		}

		registerDao.updateRefundOrderNo(record.getOrderNo(), record.getRefundOrderNo(), record.getOpenId());

		//缓存中的记录设置退费订单号
		List<Object> params = new ArrayList<Object>();
		params.add(record.getOrderNo());
		params.add(record.getRefundOrderNo());
		serveComm.set(CacheType.REGISTER_INFO_CACHE, "updateRecordRefundOrderNo", params);

		return refund;
	}

	@Override
	public void delRecordAndOrder(String orderNo, String openId) {
		// TODO Auto-generated method stub
		//orderDao.deleteByOrderNo(orderNo);
		registerDao.deleteByOrderNo(orderNo, openId);
	}

	@Override
	public void updateRecordStatus(RegisterRecord record) {
		// TODO Auto-generated method stub
		if (record.getUpdateTime() == null) {
			record.setUpdateTime(new Date().getTime());
		}
		registerDao.updateRecordStatus(record);

	}

	@Override
	public RegisterRecord findRecordByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		logger.info("orderNo:{} ", new Object[] { orderNo });
		// RegsiterInfoCache registerCache = SpringContextHolder.getBean(RegsiterInfoCache.class);
		RegisterRecord record = null;
		try {
			// record = registerCache.getRecordFromCache(orderNo);
			List<Object> params = new ArrayList<Object>();
			params.add(orderNo);
			List<Object> results = serveComm.get(CacheType.REGISTER_RECORD_CACHE, "getRecordFromCache", params);

			if (CollectionUtils.isNotEmpty(results)) {
				record = (RegisterRecord) results.get(0);
			}

			if (record == null) {
				record = registerDao.findByOrderNo(orderNo);
				logger.info("json:{}", JSON.toJSONString(record));
				if (record != null) {
					params.clear();
					params.add(record);
					serveComm.set(CacheType.REGISTER_INFO_CACHE, "setRecordToCache", params);
					// registerCache.setRecordToCache(record);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}

	@Override
	public void updateStatusForPay(RegisterRecord record) {
		//public void updateStatusForPay(RegisterRecord record, RegisterOrder order) {
		// TODO Auto-generated method stub
		if (record != null) {
			if (record.getUpdateTime() == null) {
				record.setUpdateTime(new Date().getTime());
			}
			registerDao.updateRecordStatus(record);

			Thread cacheThread =
					new Thread(new SaveAndUpdateCacheRunnable(record, CacheConstant.CACHE_OP_UPDATE), "CacheRunnable.updateRegisterInfo");
			cacheThread.start();
		}

		logger.info("updateRecordAndOrder record RegStatus :{} , payStatus : {}",
				new Object[] { record.getRegStatus(), record.getPayStatus() });
	}

	/* (non-Javadoc)
	 * @see com.yxw.mobileapp.biz.register.service.RegisterService#updateRecordForAgtRefund(com.yxw.mobileapp.biz.register.entity.RegisterRecord)
	 */
	public void updateRecordForAgtRefund(RegisterRecord record) {
		if (record.getUpdateTime() == null) {
			record.setUpdateTime(new Date().getTime());
		}
		registerDao.updateRecordForAgtRefund(record);
	}

	/* (non-Javadoc)
	 * @see com.yxw.mobileapp.biz.register.service.RegisterService#updateRecordForHisRefund(com.yxw.mobileapp.biz.register.entity.RegisterRecord)
	 */
	@Override
	public void updateRecordForHisRefund(RegisterRecord record) {
		// TODO Auto-generated method stub
		if (record.getUpdateTime() == null) {
			record.setUpdateTime(new Date().getTime());
		}
		registerDao.updateRecordStatus(record);

		Thread cacheThread =
				new Thread(new SaveAndUpdateCacheRunnable(record, CacheConstant.CACHE_OP_UPDATE), "CacheRunnable.updateRegisterInfo");
		cacheThread.start();
		logger.info("updateRecordAndOrder record RegStatus :{} , payStatus : ",
				new Object[] { record.getRegStatus(), record.getPayStatus() });
	}

	@Override
	public void updateRecordForAgtPayment(RegisterRecord record) {
		// TODO Auto-generated method stub
		registerDao.updateRecordForAgtPayment(record);
		Thread cacheThread =
				new Thread(new SaveAndUpdateCacheRunnable(record, CacheConstant.CACHE_OP_UPDATE), "CacheRunnable.updateRegisterInfo");
		cacheThread.start();
	}

	@Override
	public List<RegisterRecord> findStopRegisterRecord(String condition, String hashTableName) {
		// TODO Auto-generated method stub
		return registerDao.findStopRegisterRecord(condition, hashTableName);
	}

	@Override
	public List<RegisterRecord> findStopRegisterRecordForPoll(String condition, String hashTableName) {
		return registerDao.findStopRegisterRecordForPoll(condition, hashTableName);
	}

	@Override
	public RegisterRecord findStopRegisterRecordByOrderNo(String orderNo, List<String> hashTableName) {
		RegisterRecord record = null;
		List<Object> params = new ArrayList<Object>();
		params.add(orderNo);
		List<Object> results = serveComm.get(CacheType.REGISTER_RECORD_CACHE, "getRecordFromCache", params);
		if (CollectionUtils.isNotEmpty(results)) {
			record = (RegisterRecord) results.get(0);
		}
		// RegisterRecord record = RegsiterInfoCache registerCache = SpringContextHolder.getBean(RegsiterInfoCache.class);
		//  = registerCache.getRecordFromCache(orderNo);
		try {
			logger.info("load record form cache : {}", record == null ? null : JSON.toJSONString(record));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (record == null) {
			record = registerDao.findStopRegisterRecordByOrderNo(orderNo, hashTableName);
			/**
			 * 如果数据库中也没有查到数据，不需要更新到缓存
			 * 周鉴斌 2015年8月21日 10:35:33 增加判断
			 */
			if (record != null) {
				// registerCache.setRecordToCache(record);
				params.clear();
				params.add(record);
				serveComm.set(CacheType.REGISTER_INFO_CACHE, "setRecordToCache", params);

			}

		}

		return record;
	}

	/**
	 * 是否符合取消挂号 次数限制
	 * @param record
	 * @param ruleRegister
	 * @return
	 */
	public Map<String, Object> checkValidCacleTime(RegisterRecord record, RuleRegister ruleRegister) {

		Map<String, Object> checkResMap = new HashMap<String, Object>();
		/* 是否提示取消挂号已到限制次数*/
		boolean isTip = false;
		/* 不符合限制的前端提示信息 */
		String tipMsg = null;

		List<Integer> regStatuses = new ArrayList<Integer>();
		regStatuses.add(RegisterConstant.STATE_NORMAL_USER_CANCEL);
		//regStatuses.add(RegisterConstant.STATE_NORMAL_PAY_TIMEOUT_CANCEL);

		GregorianCalendar inDay = new GregorianCalendar();
		inDay.set(Calendar.HOUR_OF_DAY, 0);
		inDay.set(Calendar.MINUTE, 0);
		inDay.set(Calendar.SECOND, 0);
		long overTimeInDay = inDay.getTimeInMillis();

		GregorianCalendar inMonth = new GregorianCalendar();
		inMonth.set(Calendar.DAY_OF_MONTH, 1);
		inMonth.set(Calendar.HOUR_OF_DAY, 0);
		inMonth.set(Calendar.MINUTE, 0);
		inMonth.set(Calendar.SECOND, 0);
		long overTimeInMonth = inMonth.getTimeInMillis();

		String hospitalId = record.getHospitalId();
		String branchId = record.getBranchHospitalId();
		String cardNo = record.getCardNo();
		String openId = record.getOpenId();
		List<RegisterRecord> recordsInDay = findOverTimeRecords(hospitalId, branchId, openId, cardNo, regStatuses, overTimeInDay);
		List<RegisterRecord> recordsInMonth = findOverTimeRecords(hospitalId, branchId, openId, cardNo, regStatuses, overTimeInMonth);

		if (!CollectionUtils.isEmpty(recordsInDay)) {
			Integer regCancelMaxnumInDay = ruleRegister.getRegCancelMaxnumInDay();
			if (regCancelMaxnumInDay != null) {
				if (recordsInDay.size() + 2 == regCancelMaxnumInDay.intValue()) {
					isTip = true;
					tipMsg = ruleRegister.getWillReachCancelMaxnumInDayTip();
					if (StringUtils.isBlank(tipMsg)) {
						tipMsg = RegisterConstant.WILL_REACH_CANCEL_MAXNUM_IN_DAY;
					}
				} else if (recordsInDay.size() + 2 > regCancelMaxnumInDay.intValue()) {
					isTip = true;
					tipMsg = ruleRegister.getHadReachCancelMaxnumInDayCancelTip();
					if (StringUtils.isBlank(tipMsg)) {
						tipMsg = RegisterConstant.HAD_REACH_CANCEL_MAXNUM_IN_DAY;
					}
				}
			}
		}
		if (!CollectionUtils.isEmpty(recordsInMonth) && !isTip) {
			Integer regCancelMaxnumInMonth = ruleRegister.getRegCancelMaxnumInMonth();
			if (regCancelMaxnumInMonth != null) {
				if (recordsInMonth.size() + 2 == regCancelMaxnumInMonth.intValue()) {
					isTip = true;
					tipMsg = ruleRegister.getWillReachCancelMaxnumInMonthTip();
					if (StringUtils.isBlank(tipMsg)) {
						tipMsg = RegisterConstant.WILL_REACH_CANCEL_MAXNUM_IN_MONTH;
					}
				} else if (recordsInMonth.size() + 2 > regCancelMaxnumInMonth.intValue()) {
					isTip = true;
					tipMsg = ruleRegister.getHadReachCancelMaxnumInMonthCancelTip();
					if (StringUtils.isBlank(tipMsg)) {
						tipMsg = RegisterConstant.HAD_REACH_CANCEL_MAXNUM_IN_MONTH;
					}
				}
			}
		}
		checkResMap.put(BizConstant.CHECK_IS_VALID_RES_KEY, isTip);
		checkResMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, tipMsg);
		return checkResMap;

	}

	/**
	 * 是否符合取消挂号 时间限制
	 * @return
	 */
	public Map<String, Object> checkValidCacleDateTime(RegisterRecord record, RuleRegister ruleRegister) {
		Map<String, Object> checkResMap = new HashMap<String, Object>();
		/*是否符合取消挂号规则/条件限制*/
		boolean isValid = true;
		/*不符合限制的前端提示信息 */
		String inValidTip = null;

		//就诊时间
		Date scheduleDate = record.getScheduleDate();
		//就诊开始时间
		Date scheduleStartTime = null;
		Date scheduleEndTime = null;

		try {
			scheduleStartTime =
					BizConstant.YYYYMMDDHHMM.parse(BizConstant.YYYYMMDD.format(scheduleDate) + " "
							+ BizConstant.HHMM.format(record.getBeginTime()));
			scheduleEndTime =
					BizConstant.YYYYMMDDHHMM.parse(BizConstant.YYYYMMDD.format(scheduleDate) + " "
							+ BizConstant.HHMM.format(record.getEndTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Integer regType = record.getRegType();
		if (regType == null) {
			regType = RegisterConstant.REG_TYPE_APPOINTMENT;
		}

		/**
		 *  取消当班挂号截止时间点
		 *	1、就诊当天几点
		 *	2、就诊时间段开始前几小时
		 *	3、就诊时间段开始前多少分钟
		 *	4、就诊时间段开始的时间
		 *	5、就诊时间段结束的时间
		 *	6、就诊时间段结束前多少分钟
		 *	7、不限制
		 */
		if (RegisterConstant.REG_TYPE_CUR == regType.intValue()) {
			Integer cancelOnDutyCloseType = ruleRegister.getCancelOnDutyCloseType();
			if (cancelOnDutyCloseType == null) {
				cancelOnDutyCloseType = RegisterConstant.DEF_CANCEL_ONDUTY_CLOSE_TYPE;
			}

			Integer cancelOnDutyEndTimes = ruleRegister.getCancelOnDutyEndTimes();

			Long overTime = null;
			Long nowTime = new Date().getTime();

			GregorianCalendar scheduleCalendar = new GregorianCalendar();
			switch (cancelOnDutyCloseType) {
			case 1:
				//1、就诊当天几点
				if (cancelOnDutyEndTimes == null) {
					cancelOnDutyEndTimes = 12;
				}
				scheduleCalendar.setTime(scheduleDate);
				scheduleCalendar.set(Calendar.HOUR_OF_DAY, cancelOnDutyEndTimes);
				overTime = scheduleCalendar.getTimeInMillis();
				break;
			case 2:
				//2、就诊时间段开始前几小时
				if (cancelOnDutyEndTimes == null) {
					cancelOnDutyEndTimes = 1;
				}
				scheduleCalendar.setTime(scheduleStartTime);
				overTime = scheduleCalendar.getTimeInMillis() - cancelOnDutyEndTimes * 60 * 60 * 1000;
				break;
			case 3:
				//3、就诊时间段开始前多少分钟
				if (cancelOnDutyEndTimes == null) {
					cancelOnDutyEndTimes = 10;
				}
				scheduleCalendar.setTime(scheduleStartTime);
				overTime = scheduleCalendar.getTimeInMillis() - cancelOnDutyEndTimes * 60 * 1000;
				break;
			case 4:
				//4、就诊时间段开始的时间
				scheduleCalendar.setTime(scheduleStartTime);
				overTime = scheduleCalendar.getTimeInMillis();
				break;
			case 5:
				//5、就诊时间段结束的时间
				scheduleCalendar.setTime(scheduleEndTime);
				overTime = scheduleCalendar.getTimeInMillis();
				break;
			case 6:
				//6、就诊时间段结束前多少分钟
				if (cancelOnDutyEndTimes == null) {
					cancelOnDutyEndTimes = 10;
				}
				scheduleCalendar.setTime(scheduleEndTime);
				overTime = scheduleCalendar.getTimeInMillis() - cancelOnDutyEndTimes * 60 * 1000;
				break;
			case 7:
				//不限制情况  当前时间+1 始终使overTime > nowTime
				overTime = nowTime + 1;
				break;
			}

			if (nowTime >= overTime) {
				isValid = false;
				inValidTip = ruleRegister.getCancelOnDutyTimeOutTip();
				if (StringUtils.isBlank(inValidTip)) {
					inValidTip = RegisterConstant.DEF_TIP_OVER_TIME_CANCEL_ONDUTY;
				}
			}
		} else {
			/**
			 * 1、就诊前一天几点
			 * 2、就诊当天几点
			 * 3、就诊时间段开始前几小时
			 * 4、就诊时间段开始前多少分钟
			 * 5、就诊时间段开始的时间
			 * 6、就诊时间段结束的时间
			 * 7、就诊时间结束前多少分钟
			 * 8、不限制
			 */
			Integer cancelBespeakCloseType = ruleRegister.getCancelBespeakCloseType();
			if (cancelBespeakCloseType == null) {
				cancelBespeakCloseType = RegisterConstant.DEF_CANCEL_BESPEAK_CLOSE_TYPE;
			}

			Integer cancelBespeakEndTime = ruleRegister.getCancelBespeakEndTime();

			Long overTime = null;
			Long nowTime = new Date().getTime();
			GregorianCalendar scheduleCalendar = new GregorianCalendar();
			switch (cancelBespeakCloseType) {
			case 1:
				//1、就诊前一天几点
				if (cancelBespeakEndTime == null) {
					cancelBespeakEndTime = 12;
				}
				scheduleCalendar.setTime(scheduleDate);
				scheduleCalendar.add(Calendar.DAY_OF_YEAR, -1);
				scheduleCalendar.set(Calendar.HOUR_OF_DAY, cancelBespeakEndTime);
				overTime = scheduleCalendar.getTimeInMillis();
				break;
			case 2:
				//2、就诊当天几点
				if (cancelBespeakEndTime == null) {
					cancelBespeakEndTime = 12;
				}
				scheduleCalendar.setTime(scheduleDate);
				scheduleCalendar.set(Calendar.HOUR_OF_DAY, cancelBespeakEndTime);
				overTime = scheduleCalendar.getTimeInMillis();
				break;
			case 3:
				//3、就诊时间段开始前几小时
				if (cancelBespeakEndTime == null) {
					cancelBespeakEndTime = 1;
				}
				scheduleCalendar.setTime(scheduleStartTime);
				overTime = scheduleCalendar.getTimeInMillis() - cancelBespeakEndTime * 60 * 60 * 1000;
				break;
			case 4:
				//4、就诊时间段开始前多少分钟
				if (cancelBespeakEndTime == null) {
					cancelBespeakEndTime = 30;
				}
				scheduleCalendar.setTime(scheduleStartTime);
				overTime = scheduleCalendar.getTimeInMillis() - cancelBespeakEndTime * 60 * 1000;
				break;
			case 5:
				//5、就诊时间段开始的时间
				scheduleCalendar.setTime(scheduleStartTime);
				overTime = scheduleCalendar.getTimeInMillis();
				break;
			case 6:
				//6、就诊时间段结束的时间
				scheduleCalendar.setTime(scheduleEndTime);
				overTime = scheduleCalendar.getTimeInMillis();
				break;
			case 7:
				//7、就诊时间结束前多少分钟
				if (cancelBespeakEndTime == null) {
					cancelBespeakEndTime = 30;
				}
				scheduleCalendar.setTime(scheduleEndTime);
				overTime = scheduleCalendar.getTimeInMillis() - cancelBespeakEndTime * 60 * 1000;
				break;
			case 8:
				overTime = nowTime + 1;
				break;
			}

			if (nowTime >= overTime) {
				isValid = false;
				inValidTip = ruleRegister.getCancelBespeakTimeOutTip();
				if (StringUtils.isBlank(inValidTip)) {
					inValidTip = RegisterConstant.DEF_TIP_OVER_TIME_CANCEL_APPOINTMENT;
				}
			}
		}
		checkResMap.put(BizConstant.CHECK_IS_VALID_RES_KEY, isValid);
		checkResMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, inValidTip);
		return checkResMap;
	}

	@Override
	public List<RegisterRecord> findDownRegisterRecord(String hospitalId, String branchCode, String tradeMode, String startTime,
			String endTime, String hashTableName) {
		return registerDao.findDownRegisterRecord(hospitalId, branchCode, tradeMode, startTime, endTime, hashTableName);
	}

	@Override
	public List<RegisterRecord> findDownRegisterRecordByScheduleDate(String hospitalId, String branchCode, String tradeMode,
			String regType, String startDate, String endDate, String hashTableName) {
		return registerDao.findDownRegisterRecordByScheduleDate(hospitalId, branchCode, tradeMode, regType, startDate, endDate,
				hashTableName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterRecord> findAllNeedHandleExceptionRecord() {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String hashTableName = SimpleHashTableNameGenerator.REGISTER_RECORD_TABLE_NAME + "_" + i;
			Object[] parameters = new Object[] { hashTableName };
			Class<?>[] parameterTypes = new Class[] { String.class };
			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(RegisterRecordDao.class, "findAllNeedHandleExceptionRecord", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		List<RegisterRecord> records = new ArrayList<RegisterRecord>();
		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				Object obj = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (obj != null) {
					records.addAll((List<RegisterRecord>) obj);
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
				logger.debug("findByIdFromHashTable cast time :{}Millis . res:",
						new Object[] { System.currentTimeMillis() - start, JSON.toJSONString(records) });
			} else {
				logger.debug("findByIdFromHashTable cast time :{}Millis . res:", new Object[] { System.currentTimeMillis() - start, 0 });
			}
		}

		return records;
	}

	@Override
	public void updateTradeMode(RegisterRecord record) {
		// TODO Auto-generated method stub
		registerDao.updateTradeMode(record);
	}

	@Override
	public List<RegisterRecord> findRecordsByOpenIdAndIdNo(String openId, String idNo) {
		String registerTableName = SimpleHashTableNameGenerator.getRegRecordHashTable(openId, true);
		String medicalcardTableName = SimpleHashTableNameGenerator.getMedicalCardHashTable(openId, true);
		return registerDao.findRecordsByOpenIdAndIdNo(openId, idNo, registerTableName, medicalcardTableName);
	}

	@Override
	public Map<String, List<RegisterRecord>> findAllTodayRecords() {
		Map<String, List<RegisterRecord>> resultMap = new HashMap<String, List<RegisterRecord>>();

		// 找到所有的第二天
		String scheduleDate = DateUtils.today();
		List<RegisterRecord> records = findAllVisitRecords(scheduleDate);

		for (RegisterRecord record : records) {
			String hospitalCode = record.getHospitalCode();

			List<RegisterRecord> hospitalRecords = null;
			if (!resultMap.containsKey(hospitalCode)) {
				hospitalRecords = new ArrayList<RegisterRecord>();
			} else {
				hospitalRecords = resultMap.get(hospitalCode);
			}

			hospitalRecords.add(record);

			resultMap.put(hospitalCode, hospitalRecords);

		}

		if (logger.isDebugEnabled()) {
			logger.debug("findAllCurDayRecords: ", JSON.toJSONString(resultMap));
		}

		return resultMap;
	}

	@Override
	public Map<String, List<RegisterRecord>> findAllTomorrowRecords() {
		Map<String, List<RegisterRecord>> resultMap = new HashMap<String, List<RegisterRecord>>();

		// 找到所有的第二天
		String scheduleDate = DateUtils.tomorrowDate();
		List<RegisterRecord> records = findAllVisitRecords(scheduleDate);

		for (RegisterRecord record : records) {
			String hospitalCode = record.getHospitalCode();

			List<RegisterRecord> hospitalRecords = null;
			if (!resultMap.containsKey(hospitalCode)) {
				hospitalRecords = new ArrayList<RegisterRecord>();
			} else {
				hospitalRecords = resultMap.get(hospitalCode);
			}

			hospitalRecords.add(record);

			resultMap.put(hospitalCode, hospitalRecords);

		}

		if (logger.isDebugEnabled()) {
			logger.debug("findAllCurDayRecords: ", JSON.toJSONString(resultMap));
		}

		return resultMap;
	}

	@SuppressWarnings("unchecked")
	private List<RegisterRecord> findAllVisitRecords(String scheduleDate) {
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String hashTableName = SimpleHashTableNameGenerator.REGISTER_RECORD_TABLE_NAME + "_" + i;
			Object[] parameters = new Object[] { scheduleDate, hashTableName };
			Class<?>[] parameterTypes = new Class[] { String.class, String.class };
			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(RegisterRecordDao.class, "findRecordsForVisitWarn", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		List<RegisterRecord> records = new ArrayList<RegisterRecord>();
		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				Object obj = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (obj != null) {
					records.addAll((List<RegisterRecord>) obj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return records;
	}

	@Override
	public List<RegisterRecord> findRecordsByOpenIdAndAppCode(String openId, String appCode, String hospitalCode, long beginTime,
			long endTime) {
		String hashTableName = SimpleHashTableNameGenerator.getRegRecordHashTable(openId, true);
		return registerDao.findRecordsByOpenIdAndAppCode(openId, appCode, hospitalCode, beginTime, endTime, hashTableName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterRecord> findRecordsByIdTypeAndIdNo(Integer idType, String idNo) {

		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String registerTableName = SimpleHashTableNameGenerator.REGISTER_RECORD_TABLE_NAME + "_" + i;
			String medicalcardTableName = SimpleHashTableNameGenerator.MEDICAL_CARD_TABLE_NAME + "_" + i;
			Object[] parameters = new Object[] { idType, idNo, registerTableName, medicalcardTableName };
			Class<?>[] parameterTypes = new Class[] { Integer.class, String.class, String.class, String.class };
			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(RegisterRecordDao.class, "findRecordsByIdTypeAndIdNo", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		List<RegisterRecord> records = new ArrayList<RegisterRecord>();
		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				Object obj = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (obj != null) {
					records.addAll((List<RegisterRecord>) obj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return records;
	}

	@Override
	public RegisterRecord findRecordFromDBByOrderNoOpenId(String orderNo) {
		List<Object> params = new ArrayList<Object>();

		RegisterRecord record = null;
		try {
			record = registerDao.findByOrderNo(orderNo);
			if (record != null) {
				params.add(record);
				serveComm.set(CacheType.REGISTER_INFO_CACHE, "setRecordToCache", params);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}

	@Override
	public List<RegisterRecord> findListByProcedure(Map map) {
		return registerDao.findListByProcedure(map);
	}

	@Override
	public List<RegisterRecord> findStopListByProcedure(Map map) {
		return registerDao.findStopListByProcedure(map);
	}

}
