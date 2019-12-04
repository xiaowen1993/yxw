package com.yxw.stats.task.platform.collect;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.stats.constants.BizConstant;
import com.yxw.stats.constants.OrderConstant;
import com.yxw.stats.constants.ReceiveConstant;
import com.yxw.stats.data.common.hash.SimpleHashTableNameGenerator;
import com.yxw.stats.entity.platform.ClinicOrderCount;
import com.yxw.stats.entity.platform.DepositOrderCount;
import com.yxw.stats.entity.platform.ExtensionCount;
import com.yxw.stats.entity.platform.HospIdAndAppSecret;
import com.yxw.stats.entity.platform.MedicalCardCount;
import com.yxw.stats.entity.platform.OrderCount;
import com.yxw.stats.entity.platform.UserSubscribe;
import com.yxw.stats.service.platform.ClinicOrderCountService;
import com.yxw.stats.service.platform.DepositOrderCountService;
import com.yxw.stats.service.platform.HospIdAndAppSecretService;
import com.yxw.stats.service.platform.MedicalCardCountService;
import com.yxw.stats.service.platform.OrderCountService;
import com.yxw.stats.service.platform.UserSubscribeService;
import com.yxw.stats.task.platform.callable.StatisticsClinicOrderCallable;
import com.yxw.stats.task.platform.callable.StatisticsDepositOrderCallable;
import com.yxw.stats.task.platform.callable.StatisticsExtensionCountCollectCall;
import com.yxw.stats.task.platform.callable.StatisticsMedicalCardCollectCall;
import com.yxw.stats.task.platform.callable.StatisticsRegOrderCollectCall;
import com.yxw.stats.task.platform.callable.StatisticsUserSubscribeCollectCall;
import com.yxw.utils.DateUtils;
import com.yxw.utils.StringUtils;

public class StatisticalCollector {
	public static Logger logger = LoggerFactory.getLogger(StatisticalCollector.class);

	public static final String PLATFORM_WECHAT = "wechat";

	public static final String PLATFORM_ALIPAY = "alipay";
	private Long loopTime;
	/**
	 * 每核Cpu负载的最大线程队列数
	 */
	private static final int POOL_SIZE = 2;

	/**
	 * 分表数量
	 */
	private static final int TABLE_SIZE = 10;

	public StatisticalCollector(Long loopTime) {
		super();
		this.loopTime = loopTime;
	}

	public void start() {
		/*
		 * // 根据采集的机器配置得出默认的线程数 int cpuNums = Runtime.getRuntime().availableProcessors(); int threadNum = cpuNums *
		 * POOL_SIZE;
		 */
		HashMap<String, Object> params = new HashMap<String, Object>();
		/**
		 * 拿当前的服务器时间的前一天，00:00:00-23:59:59
		 */
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// params.put("startDate", dateFormat.format(DateUtils.getDateBegin(DateUtils.yesterdayDate())));
		// params.put("endDate", dateFormat.format(DateUtils.getDateEnd(DateUtils.yesterdayDate())));
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String queryDate = date.format(DateUtils.yesterdayDate());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			params.put("startDate", dateFormat.parse(queryDate + " 00:00:00").getTime());
			params.put("endDate", dateFormat.parse(queryDate + " 23:59:59").getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(
				"统计开始时间:{} , 统计结束时间:{}. ",
				new Object[] { dateFormat.format(new Date(Long.parseLong(params.get("startDate").toString()))),
						dateFormat.format(new Date(Long.parseLong(params.get("endDate").toString()))) });
		List<OrderCount> regCounts = getRegisterRecord(params);
		logger.info("挂号组合统计结果:{}", JSON.toJSONString(regCounts));
		List<ClinicOrderCount> clinicCounts = getClinicRecord(params);
		logger.info("门诊组合统计结果:{}", JSON.toJSONString(clinicCounts));
		List<MedicalCardCount> medicalCardCounts = getMedicalCard(params);
		logger.info("绑卡组合统计结果:{}", JSON.toJSONString(medicalCardCounts));
		List<DepositOrderCount> depositCounts = getDepositRecord(params);
		logger.info("住院押金补缴统计结果:{}", JSON.toJSONString(depositCounts));
		/*List<UserSubscribe> userSubscribes = getUserSubscribes(queryDate);
		logger.info("用户关注数统计结果:{}", JSON.toJSONString(userSubscribes));
		List<ExtensionCount> extensionCounts = getExtensionCounts(params);
		logger.info("获取扫推广二维码订单统计结果:{}", JSON.toJSONString(extensionCounts));*/

		if (!CollectionUtils.isEmpty(regCounts)) {
			OrderCountService orderCountService = SpringContextHolder.getBean(OrderCountService.class);
			orderCountService.batchInsert(regCounts);
		}
		if (!CollectionUtils.isEmpty(clinicCounts)) {
			ClinicOrderCountService clinicOrderCountService = SpringContextHolder.getBean(ClinicOrderCountService.class);
			clinicOrderCountService.batchInsert(clinicCounts);
		}
		if (!CollectionUtils.isEmpty(medicalCardCounts)) {
			MedicalCardCountService medicalCardCountService = SpringContextHolder.getBean(MedicalCardCountService.class);
			medicalCardCountService.batchInsert(medicalCardCounts);
		}
		if (!CollectionUtils.isEmpty(depositCounts)) {
			DepositOrderCountService depositOrderCountService = SpringContextHolder.getBean(DepositOrderCountService.class);
			depositOrderCountService.batchInsert(depositCounts);
		}

		/*if (!CollectionUtils.isEmpty(userSubscribes)) {
			UserSubscribeService userSubscribeService = SpringContextHolder.getBean(UserSubscribeService.class);
			userSubscribeService.batchInsert(userSubscribes);
		}
		if (!CollectionUtils.isEmpty(extensionCounts)) {
			ExtensionCountService extensionService = SpringContextHolder.getBean(ExtensionCountService.class);
			extensionService.batchInsert(extensionCounts);
		}*/

	}

	/**
	 * 获取扫推广二维码订单统计
	 * 
	 * @param params
	 * @return
	 */
	private List<ExtensionCount> getExtensionCounts(HashMap<String, Object> params) {
		List<ExtensionCount> results = new ArrayList<ExtensionCount>();
		ExecutorService collectExec =
				Executors.newFixedThreadPool(BizConstant.EXECUTOR_COUNT, new SimpleThreadFactory("getExtensionCounts:doBiz"));
		List<FutureTask<List<ExtensionCount>>> taskList = new ArrayList<FutureTask<List<ExtensionCount>>>();
		for (int i = 0; i < ReceiveConstant.TABLE_SIZE; i++) {
			HashMap<String, Object> newMap = new HashMap<String, Object>();
			for (Object key : params.keySet()) {
				newMap.put(key.toString(), params.get(key).toString());
			}
			newMap.put("regHashTableName", SimpleHashTableNameGenerator.REGISTER_RECORD_TABLE_NAME + "_" + ( i + 1 ));
			newMap.put("clinicHashTableName", SimpleHashTableNameGenerator.CLINIC_RECORD_TABLE_NAME + "_" + ( i + 1 ));
			newMap.put("depositHashTableName", SimpleHashTableNameGenerator.DEPOSIT_RECORD_TABLE_NAME + "_" + ( i + 1 ));
			newMap.put("payStatus", OrderConstant.STATE_PAYMENT);
			newMap.put("bizStatus", BizConstant.STATE_NORMAL_HAD);
			StatisticsExtensionCountCollectCall queryRunnable = new StatisticsExtensionCountCollectCall(newMap);
			// 创建每条指令的采集任务对象
			FutureTask<List<ExtensionCount>> collectTask = new FutureTask<List<ExtensionCount>>(queryRunnable);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑

		for (FutureTask<List<ExtensionCount>> taskF : taskList) {
			// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
			List<ExtensionCount> collectData = null;
			try {
				collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (collectData != null && logger.isInfoEnabled()) {
				results.addAll(collectData);
				logger.info("date:{} RegisterRecordCount query success.", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
			}
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();
		logger.info("获取扫推广二维码订单统计结果:{}", JSON.toJSONString(results));
		if (!CollectionUtils.isEmpty(results)) {
			Map<String, ExtensionCount> map = new HashMap<String, ExtensionCount>();
			for (ExtensionCount extensionCount : results) {
				String key = extensionCount.getHospitalId() + "_" + extensionCount.getExtensionId();
				if (map.containsKey(key)) {
					ExtensionCount temp = map.get(key);
					temp.setNum(temp.getNum() + extensionCount.getNum());
				} else {
					map.put(key, extensionCount);
				}

			}
			results.clear();
			if (!map.isEmpty()) {
				for (String key : map.keySet()) {
					ExtensionCount extensionCount = map.get(key);
					results.add(extensionCount);
				}
			}
		}
		return results;
	}

	/**
	 * 获取用户关注数
	 * 
	 * @return
	 */
	private List<UserSubscribe> getUserSubscribes(String yesterday) {
		UserSubscribeService userSubscribeService = SpringContextHolder.getBean(UserSubscribeService.class);
		HospIdAndAppSecretService hospIdAndAppSecretService = SpringContextHolder.getBean(HospIdAndAppSecretService.class);
		ExecutorService collectExec =
				Executors.newFixedThreadPool(BizConstant.EXECUTOR_COUNT, new SimpleThreadFactory("getUserSubscribes:doBiz"));
		List<FutureTask<List<UserSubscribe>>> taskList = new ArrayList<FutureTask<List<UserSubscribe>>>();

		/**
		 * 获取所有医院微信公众号信息
		 */
		List<HospIdAndAppSecret> hospIdAndAppSecretVos = hospIdAndAppSecretService.findValidWechatAppInfo();
		logger.info("所有医院微信公众号信息:{}", JSONObject.toJSONString(hospIdAndAppSecretVos));
		List<UserSubscribe> userSubscribes = new ArrayList<UserSubscribe>();
		for (HospIdAndAppSecret hospIdAndAppSecretVo : hospIdAndAppSecretVos) {
			String appSecret = hospIdAndAppSecretVo.getAppSecret();
			String appId = hospIdAndAppSecretVo.getAppId();
			String hospitalId = hospIdAndAppSecretVo.getHospId();
			if (StringUtils.isNullOrBlank(hospitalId) || StringUtils.isNullOrBlank(appId) || StringUtils.isNullOrBlank(appSecret)
					|| appId.indexOf("_") > 0 || appSecret.indexOf("_") > 0 || appId.length() < 17 || appSecret.length() < 20) {
				logger.info("非法的微信公众号配置!请检查!,appSecret:{},appId:{},hospitalId:{}", appSecret, appId, hospitalId);
				continue;
			}
			if (hospIdAndAppSecretVo.getAppCode().equals(PLATFORM_WECHAT) && appSecret != null && appId != null) {

				if (userSubscribeService.verifyRefDate(yesterday, null, hospitalId, PLATFORM_WECHAT)) {
					// 微信模块
					UserSubscribe userSubscribe = userSubscribeService.getUserSubscribeLastOne(null, hospitalId, PLATFORM_WECHAT);
					String begin_Date = null;
					if (userSubscribe == null) {
						begin_Date = "2015-01-01";
					} else {
						Date begin = DateUtils.StringToDateYMD(userSubscribe.getRefDate());
						Calendar calendar = DateUtils.date2Calendar(begin);
						calendar.add(Calendar.DATE, 1);
						begin_Date = DateUtils.dateToStringYMD(calendar.getTime());
					}

					StatisticsUserSubscribeCollectCall queryRunnable =
							new StatisticsUserSubscribeCollectCall(begin_Date, hospitalId, PLATFORM_WECHAT, appId, appSecret);
					// 创建每条指令的采集任务对象
					FutureTask<List<UserSubscribe>> collectTask = new FutureTask<List<UserSubscribe>>(queryRunnable);
					// 添加到list,方便后面取得结果
					taskList.add(collectTask);
					// 提交给线程池 exec.submit(task);
					collectExec.submit(collectTask);

				}

			} else if (hospIdAndAppSecretVo.getAppCode().equals(PLATFORM_ALIPAY) && appSecret != null && appId != null) {
				System.out.println(appId);
			}

		}

		for (FutureTask<List<UserSubscribe>> taskF : taskList) {
			// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
			List<UserSubscribe> collectData = null;
			try {
				collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (collectData != null && logger.isInfoEnabled()) {
				userSubscribes.addAll(collectData);
				logger.info("date:{} getUserSubscribes query success.", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
			}
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();
		logger.info("获取用户关注数统计结果:{}", JSON.toJSONString(userSubscribes));

		return userSubscribes;
	}

	/**
	 * 获取挂号订单
	 * 
	 * @param params
	 * @return
	 * @throws TimeoutException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	private List<OrderCount> getRegisterRecord(HashMap<String, Object> params) {
		List<OrderCount> results = new ArrayList<OrderCount>();
		ExecutorService collectExec =
				Executors.newFixedThreadPool(BizConstant.EXECUTOR_COUNT, new SimpleThreadFactory("getRegisterRecord:doBiz"));
		List<FutureTask<List<OrderCount>>> taskList = new ArrayList<FutureTask<List<OrderCount>>>();
		for (int i = 0; i < ReceiveConstant.TABLE_SIZE; i++) {
			HashMap<String, Object> newMap = new HashMap<String, Object>();
			for (Object key : params.keySet()) {
				newMap.put(key.toString(), params.get(key).toString());
			}
			newMap.put("orderType", BizConstant.STATISTICS_REG_VALUE);
			newMap.put("hashTableName", SimpleHashTableNameGenerator.REGISTER_RECORD_TABLE_NAME + "_" + ( i + 1 ));
			StatisticsRegOrderCollectCall queryRunnable = new StatisticsRegOrderCollectCall(newMap);
			// 创建每条指令的采集任务对象
			FutureTask<List<OrderCount>> collectTask = new FutureTask<List<OrderCount>>(queryRunnable);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}
		// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑

		for (FutureTask<List<OrderCount>> taskF : taskList) {
			// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
			List<OrderCount> collectData = null;
			try {
				collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (collectData != null && logger.isInfoEnabled()) {
				results.addAll(collectData);
				logger.info("date:{} RegisterRecordCount query success.", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
			}
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();
		logger.info("挂号分表统计结果:{}", JSON.toJSONString(results));
		if (!CollectionUtils.isEmpty(results)) {
			Map<String, OrderCount> map = new HashMap<String, OrderCount>();
			for (OrderCount oc : results) {
				StringBuffer sb = new StringBuffer();
				String key =
						sb.append(oc.getHospitalId()).append("_").append(oc.getBranchId()).append("_").append(oc.getPayMode()).append("_")
								.append(oc.getMedicarePayments()).toString();
				boolean bl = map.containsKey(key);
				if (bl) {
					OrderCount ocRaw = map.get(key);
					// 预约挂号
					if (oc.getBizMode() == 1) {
						if (oc.getRegType().intValue() == 1) {
							/**
							 * 预约微信统计
							 */
							ocRaw.setReservationNoPaymentByWechat(ocRaw.getReservationNoPaymentByWechat() + oc.getReservationNoPayment());
							ocRaw.setReservationPaymentByWechat(ocRaw.getReservationPaymentByWechat() + oc.getReservationPayment());
							ocRaw.setReservationRefundByWechat(ocRaw.getReservationRefundByWechat() + oc.getReservationRefund());

						} else {
							// 当班
							/**
							 * 当班微信统计
							 */
							ocRaw.setDutyNoPaymentByWechat(ocRaw.getDutyNoPaymentByWechat() + oc.getReservationNoPayment());
							ocRaw.setDutyPaymentByWechat(ocRaw.getDutyPaymentByWechat() + oc.getReservationPayment());
							ocRaw.setDutyRefundByWechat(ocRaw.getDutyRefundByWechat() + oc.getReservationRefund());
						}

						ocRaw.setRegPayFeeByWechat(oc.getRegPayFee() + ocRaw.getRegPayFeeByWechat());
						ocRaw.setRegRefundFeeByWechat(oc.getRegRefundFee() + ocRaw.getRegRefundFeeByWechat());
					} else {

						if (oc.getRegType().intValue() == 1) {
							/**
							 * 预约支付宝统计
							 */
							ocRaw.setReservationNoPaymentByAlipay(ocRaw.getReservationNoPaymentByAlipay() + oc.getReservationNoPayment());
							ocRaw.setReservationPaymentByAlipay(ocRaw.getReservationPaymentByAlipay() + oc.getReservationPayment());
							ocRaw.setReservationRefundByAlipay(ocRaw.getReservationRefundByAlipay() + oc.getReservationRefund());
						} else {
							/**
							 * 当班支付宝统计
							 */
							ocRaw.setDutyNoPaymentByAlipay(ocRaw.getDutyNoPaymentByAlipay() + oc.getReservationNoPayment());
							ocRaw.setDutyPaymentByAlipay(ocRaw.getDutyPaymentByAlipay() + oc.getReservationPayment());
							ocRaw.setDutyRefundByAlipay(ocRaw.getDutyRefundByAlipay() + oc.getReservationRefund());
						}

						ocRaw.setRegPayFeeByAlipay(oc.getRegPayFee() + ocRaw.getRegPayFeeByAlipay());
						ocRaw.setRegRefundFeeByAlipay(oc.getRegRefundFee() + ocRaw.getRegRefundFeeByAlipay());
					}

					if (oc.getRegType().intValue() == 1) {
						ocRaw.setReservationNoPayment(ocRaw.getReservationNoPayment() + oc.getReservationNoPayment());
						ocRaw.setReservationPayment(ocRaw.getReservationPayment() + oc.getReservationPayment());
						ocRaw.setReservationRefund(ocRaw.getReservationRefund() + oc.getReservationRefund());
					} else {
						// 预约
						ocRaw.setDutyNoPayment(ocRaw.getDutyNoPayment() + oc.getReservationNoPayment());
						ocRaw.setDutyPayment(ocRaw.getDutyPayment() + oc.getReservationPayment());
						ocRaw.setDutyRefund(ocRaw.getDutyRefund() + oc.getReservationRefund());
					}
					ocRaw.setRegPayFee(oc.getRegPayFee() + ocRaw.getRegPayFee());
					ocRaw.setRegRefundFee(oc.getRegRefundFee() + ocRaw.getRegRefundFee());
					ocRaw.setRegType(null);
				} else {
					if (oc.getBizMode() == 1) {
						/**
						 * 预约微信统计
						 */
						if (oc.getRegType().intValue() == 1) {
							oc.setReservationNoPaymentByWechat(oc.getReservationNoPayment());
							oc.setReservationPaymentByWechat(oc.getReservationPayment());
							oc.setReservationRefundByWechat(oc.getReservationRefund());
						} else {
							/**
							 * 当班微信统计
							 */
							oc.setDutyNoPaymentByWechat(oc.getReservationNoPayment());
							oc.setDutyPaymentByWechat(oc.getReservationPayment());
							oc.setDutyRefundByWechat(oc.getReservationRefund());
						}

						oc.setRegPayFeeByWechat(oc.getRegPayFee());
						oc.setRegRefundFeeByWechat(oc.getRegRefundFee());
					} else {
						if (oc.getRegType().intValue() == 1) {
							/**
							 * 预约支付宝统计
							 */
							oc.setReservationNoPaymentByAlipay(oc.getReservationNoPayment());
							oc.setReservationPaymentByAlipay(oc.getReservationPayment());
							oc.setReservationRefundByAlipay(oc.getReservationRefund());
						} else {
							/**
							 * 当班支付宝统计
							 */
							oc.setDutyNoPaymentByAlipay(oc.getReservationNoPayment());
							oc.setDutyPaymentByAlipay(oc.getReservationPayment());
							oc.setDutyRefundByAlipay(oc.getReservationRefund());
						}

						oc.setRegPayFeeByAlipay(oc.getRegPayFee());
						oc.setRegRefundFeeByAlipay(oc.getRegRefundFee());
					}

					oc.setRegType(null);
					map.put(key, oc);
				}
			}
			results.clear();
			if (!map.isEmpty()) {
				for (String key : map.keySet()) {
					OrderCount oc = map.get(key);

					oc.setReservationCount(oc.getReservationNoPayment() + oc.getReservationPayment() + oc.getReservationRefund());
					oc.setDutyCount(oc.getDutyNoPayment() + oc.getDutyPayment() + oc.getDutyRefund());

					/**
					 * 预约微信统计
					 */
					oc.setReservationCountByWechat(oc.getReservationNoPaymentByWechat() + oc.getReservationPaymentByWechat()
							+ oc.getReservationRefundByWechat());
					/**
					 * 预约支付宝统计
					 */
					oc.setReservationCountByAlipay(oc.getReservationNoPaymentByAlipay() + oc.getReservationPaymentByAlipay()
							+ oc.getReservationRefundByAlipay());

					/**
					 * 当班微信统计
					 */
					oc.setDutyCountByWechat(oc.getDutyNoPaymentByWechat() + oc.getDutyPaymentByWechat() + oc.getDutyRefundByWechat());
					/**
					 * 当班支付宝统计
					 */
					oc.setDutyCountByAlipay(oc.getDutyNoPaymentByAlipay() + oc.getDutyPaymentByAlipay() + oc.getDutyRefundByAlipay());
					/**
					 * 微信统计
					 */
					oc.setCountByWechat(oc.getReservationCountByWechat() + oc.getDutyCountByAlipay());
					/**
					 * 支付宝统计
					 */
					oc.setCountByAlipay(oc.getReservationCountByWechat() + oc.getDutyCountByAlipay());
					oc.setCount(oc.getReservationCount() + oc.getDutyCount());

					results.add(oc);

				}

			}
		}
		return results;
	}

	/**
	 * 获取门诊订单
	 * 
	 * @param params
	 * @return
	 */
	private List<ClinicOrderCount> getClinicRecord(HashMap<String, Object> params) {
		List<ClinicOrderCount> results = new ArrayList<ClinicOrderCount>();
		ExecutorService collectExec =
				Executors.newFixedThreadPool(ReceiveConstant.TABLE_SIZE, new SimpleThreadFactory("getClinicRecord:doBiz"));
		List<FutureTask<List<ClinicOrderCount>>> taskList = new ArrayList<FutureTask<List<ClinicOrderCount>>>();
		for (int i = 0; i < ReceiveConstant.TABLE_SIZE; i++) {
			HashMap<String, Object> newMap = new HashMap<String, Object>();
			for (Object key : params.keySet()) {
				newMap.put(key.toString(), params.get(key).toString());
			}
			newMap.put("orderType", BizConstant.STATISTICS_CLINIC_VALUE);
			newMap.put("hashTableName", SimpleHashTableNameGenerator.CLINIC_RECORD_TABLE_NAME + "_" + ( i + 1 ));
			StatisticsClinicOrderCallable queryRunnable = new StatisticsClinicOrderCallable(newMap);
			// 创建每条指令的采集任务对象
			FutureTask<List<ClinicOrderCount>> collectTask = new FutureTask<List<ClinicOrderCount>>(queryRunnable);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}
		// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑

		for (FutureTask<List<ClinicOrderCount>> taskF : taskList) {
			// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
			List<ClinicOrderCount> collectData = null;
			try {
				collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (collectData != null && logger.isInfoEnabled()) {
				results.addAll(collectData);
				logger.info("date:{} ClinicRecordCount query success.", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
			}
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();
		logger.info("门诊分表统计结果:{}", JSON.toJSONString(results));
		try {

			if (!CollectionUtils.isEmpty(results)) {
				Map<String, ClinicOrderCount> map = new HashMap<String, ClinicOrderCount>();
				for (ClinicOrderCount oc : results) {
					StringBuffer sb = new StringBuffer();
					String key =
							sb.append(oc.getHospitalId()).append("_").append(oc.getBranchId()).append("_").append(oc.getPayMode())
									.append("_").append(oc.getMedicarePayments()).toString();
					boolean bl = map.containsKey(key);
					if (bl) {
						ClinicOrderCount ocRaw = map.get(key);
						if (oc.getBizMode() == 1) {
							ocRaw.setNoPaymentByWechat(ocRaw.getNoPaymentByWechat() + oc.getNoPayment());
							ocRaw.setPaymentByWechat(ocRaw.getPaymentByWechat() + oc.getPayment());
							ocRaw.setRefundByWechat(ocRaw.getRefundByWechat() + oc.getRefund());
							ocRaw.setClinicPayFeeByWechat(ocRaw.getClinicPayFeeByWechat() + oc.getClinicPayFee());
							ocRaw.setClinicRefundFeeByWechat(ocRaw.getClinicRefundFeeByWechat() + oc.getClinicRefundFee());
							ocRaw.setPartRefundByWechat(ocRaw.getPartRefundByWechat() + oc.getPartRefund());
						} else {
							ocRaw.setNoPaymentByAlipay(ocRaw.getNoPaymentByAlipay() + oc.getNoPayment());
							ocRaw.setPaymentByAlipay(ocRaw.getPaymentByAlipay() + oc.getPayment());
							ocRaw.setRefundByAlipay(ocRaw.getRefundByAlipay() + oc.getRefund());
							ocRaw.setClinicPayFeeByAlipay(ocRaw.getClinicPayFeeByAlipay() + oc.getClinicPayFee());
							ocRaw.setClinicRefundFeeByAlipay(ocRaw.getClinicRefundFeeByAlipay() + oc.getClinicRefundFee());
							ocRaw.setPartRefundByAlipay(ocRaw.getPartRefundByAlipay() + oc.getPartRefund());
						}

						ocRaw.setNoPayment(ocRaw.getNoPayment() + oc.getNoPayment());
						ocRaw.setPayment(ocRaw.getPayment() + oc.getPayment());
						ocRaw.setRefund(ocRaw.getRefund() + oc.getRefund());
						ocRaw.setClinicPayFee(ocRaw.getClinicPayFee() + oc.getClinicPayFee());
						ocRaw.setClinicRefundFee(ocRaw.getClinicRefundFee() + oc.getClinicRefundFee());
						ocRaw.setPartRefund(ocRaw.getPartRefund() + oc.getPartRefund());
					} else {
						if (oc.getBizMode() == 1) {
							oc.setNoPaymentByWechat(oc.getNoPayment());
							oc.setPaymentByWechat(oc.getPayment());
							oc.setRefundByWechat(oc.getRefund());
							oc.setClinicPayFeeByWechat(oc.getClinicPayFee());
							oc.setClinicRefundFeeByWechat(oc.getClinicRefundFee());
							oc.setPartRefundByWechat(oc.getPartRefund());
						} else {
							oc.setNoPaymentByAlipay(oc.getNoPayment());
							oc.setPaymentByAlipay(oc.getPayment());
							oc.setRefundByAlipay(oc.getRefund());
							oc.setClinicPayFeeByAlipay(oc.getClinicPayFee());
							oc.setClinicRefundFeeByAlipay(oc.getClinicRefundFee());
							oc.setPartRefundByAlipay(oc.getPartRefund());
						}
						map.put(key, oc);
					}
				}
				results.clear();
				if (!map.isEmpty()) {
					for (String key : map.keySet()) {
						ClinicOrderCount oc = map.get(key);
						oc.setCount(oc.getNoPayment() + oc.getPayment() + oc.getRefund());
						oc.setCountByWechat(oc.getNoPaymentByWechat() + oc.getPaymentByWechat() + oc.getRefundByWechat());
						oc.setCountByAlipay(oc.getNoPaymentByAlipay() + oc.getPaymentByAlipay() + oc.getRefundByAlipay());
						results.add(oc);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * 获取绑卡
	 * 
	 * @param params
	 * @return
	 */
	public List<MedicalCardCount> getMedicalCard(HashMap<String, Object> params) {
		List<MedicalCardCount> results = new ArrayList<MedicalCardCount>();
		// 设置线程池的数量
		ExecutorService collectExec =
				Executors.newFixedThreadPool(BizConstant.EXECUTOR_COUNT, new SimpleThreadFactory("medicalCardCount:doBiz"));
		List<FutureTask<List<MedicalCardCount>>> taskList = new ArrayList<FutureTask<List<MedicalCardCount>>>();
		for (int i = 0; i < ReceiveConstant.TABLE_SIZE; i++) {
			HashMap<String, Object> newMap = new HashMap<String, Object>();
			for (Object key : params.keySet()) {
				newMap.put(key.toString(), params.get(key).toString());
			}
			newMap.put("hashTableName", SimpleHashTableNameGenerator.MEDICAL_CARD_TABLE_NAME + "_" + ( i + 1 ));
			StatisticsMedicalCardCollectCall queryRunnable = new StatisticsMedicalCardCollectCall(newMap);
			// 创建每条指令的采集任务对象
			FutureTask<List<MedicalCardCount>> collectTask = new FutureTask<List<MedicalCardCount>>(queryRunnable);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}
		// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
		try {
			for (FutureTask<List<MedicalCardCount>> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<MedicalCardCount> collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (collectData != null && logger.isInfoEnabled()) {
					results.addAll(collectData);
					logger.info("date:{} medicalCardCount query success.", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();
		logger.info("绑卡分表统计结果:{}", JSON.toJSONString(results));
		if (!CollectionUtils.isEmpty(results)) {
			Map<String, MedicalCardCount> map = new HashMap<String, MedicalCardCount>();
			for (MedicalCardCount mcc : results) {
				String key = mcc.getHospitalId() + "_" + mcc.getBranchId();
				boolean bl = map.containsKey(key);
				if (bl) {

					MedicalCardCount mccRaw = map.get(key);
					if (mcc.getPlatform() == 1) {
						mccRaw.setWechatCount(mccRaw.getWechatCount() + mcc.getCount());
					} else {
						mccRaw.setAlipayCount(mccRaw.getAlipayCount() + mcc.getCount());
					}

					mccRaw.setCount(mccRaw.getCount() + mcc.getCount());

				} else {
					if (mcc.getPlatform() == 1) {
						mcc.setWechatCount(mcc.getCount());
					} else {
						mcc.setAlipayCount(mcc.getCount());
					}
					map.put(key, mcc);
				}
			}
			results.clear();
			if (!map.isEmpty()) {
				for (String key : map.keySet()) {
					results.add(map.get(key));
				}
			}
		}
		return results;
	}

	/**
	 * 获取住院押金补缴
	 * 
	 * @param params
	 * @return
	 */
	public List<DepositOrderCount> getDepositRecord(HashMap<String, Object> params) {
		List<DepositOrderCount> results = new ArrayList<DepositOrderCount>();
		ExecutorService collectExec =
				Executors.newFixedThreadPool(ReceiveConstant.TABLE_SIZE, new SimpleThreadFactory("getDepositRecord:doBiz"));
		List<FutureTask<List<DepositOrderCount>>> taskList = new ArrayList<FutureTask<List<DepositOrderCount>>>();
		for (int i = 0; i < ReceiveConstant.TABLE_SIZE; i++) {
			HashMap<String, Object> newMap = new HashMap<String, Object>();
			for (Object key : params.keySet()) {
				newMap.put(key.toString(), params.get(key).toString());
			}
			newMap.put("orderType", BizConstant.STATISTICS_DEPOSIT_VALUE);
			newMap.put("hashTableName", SimpleHashTableNameGenerator.DEPOSIT_RECORD_TABLE_NAME + "_" + ( i + 1 ));
			StatisticsDepositOrderCallable queryRunnable = new StatisticsDepositOrderCallable(newMap);
			// 创建每条指令的采集任务对象
			FutureTask<List<DepositOrderCount>> collectTask = new FutureTask<List<DepositOrderCount>>(queryRunnable);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}
		// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑

		for (FutureTask<List<DepositOrderCount>> taskF : taskList) {
			// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
			try {
				List<DepositOrderCount> collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (collectData != null && logger.isInfoEnabled()) {
					results.addAll(collectData);
					logger.info("date:{} DepositRecordCount query success.", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();
		logger.info("绑卡分表统计结果:{}", JSON.toJSONString(results));

		if (!CollectionUtils.isEmpty(results)) {
			Map<String, DepositOrderCount> map = new HashMap<String, DepositOrderCount>();
			for (DepositOrderCount oc : results) {
				String key = oc.getHospitalId() + "_" + oc.getBranchId() + "_" + oc.getPayMode();
				boolean bl = map.containsKey(key);
				if (bl) {
					DepositOrderCount ocRaw = map.get(key);
					if (oc.getBizMode() == 1) {
						ocRaw.setNoPaymentByWechat(ocRaw.getNoPaymentByWechat() + oc.getNoPayment());
						ocRaw.setPaymentByWechat(ocRaw.getPaymentByWechat() + oc.getPayment());
						ocRaw.setRefundByWechat(ocRaw.getRefundByWechat() + oc.getRefund());
						ocRaw.setDepositPayFeeByWechat(ocRaw.getDepositPayFeeByWechat() + oc.getDepositPayFee());
						ocRaw.setDepositRefundFeeByWechat(ocRaw.getDepositRefundFeeByWechat() + oc.getDepositRefundFee());
						ocRaw.setPartRefundByWechat(ocRaw.getPartRefundByWechat() + oc.getPartRefund());
					} else {
						ocRaw.setNoPaymentByAlipay(ocRaw.getNoPaymentByAlipay() + oc.getNoPayment());
						ocRaw.setPaymentByAlipay(ocRaw.getPaymentByAlipay() + oc.getPayment());
						ocRaw.setRefundByAlipay(ocRaw.getRefundByAlipay() + oc.getRefund());
						ocRaw.setDepositPayFeeByAlipay(ocRaw.getDepositPayFeeByAlipay() + oc.getDepositPayFee());
						ocRaw.setDepositRefundFeeByAlipay(ocRaw.getDepositRefundFeeByAlipay() + oc.getDepositRefundFee());
						ocRaw.setPartRefundByAlipay(ocRaw.getPartRefundByAlipay() + oc.getPartRefund());
					}

					ocRaw.setNoPayment(ocRaw.getNoPayment() + oc.getNoPayment());
					ocRaw.setPayment(ocRaw.getPayment() + oc.getPayment());
					ocRaw.setRefund(ocRaw.getRefund() + oc.getRefund());
					ocRaw.setDepositPayFee(ocRaw.getDepositPayFee() + oc.getDepositPayFee());
					ocRaw.setDepositRefundFee(ocRaw.getDepositRefundFee() + oc.getDepositRefundFee());
					ocRaw.setPartRefund(ocRaw.getPartRefund() + oc.getPartRefund());
				} else {

					if (oc.getBizMode() == 1) {
						oc.setNoPaymentByWechat(oc.getNoPayment());
						oc.setPaymentByWechat(oc.getPayment());
						oc.setRefundByWechat(oc.getRefund());
						oc.setDepositPayFeeByWechat(oc.getDepositPayFee());
						oc.setDepositRefundFeeByWechat(oc.getDepositRefundFee());
						oc.setPartRefundByWechat(oc.getPartRefund());
					} else {
						oc.setNoPaymentByAlipay(oc.getNoPayment());
						oc.setPaymentByAlipay(oc.getPayment());
						oc.setRefundByAlipay(oc.getRefund());
						oc.setDepositPayFeeByAlipay(oc.getDepositPayFee());
						oc.setDepositRefundFeeByAlipay(oc.getDepositRefundFee());
						oc.setPartRefundByAlipay(oc.getPartRefund());
					}
					map.put(key, oc);
				}
			}
			results.clear();
			if (!map.isEmpty()) {
				for (String key : map.keySet()) {
					DepositOrderCount oc = map.get(key);
					oc.setCount(oc.getNoPayment() + oc.getPayment() + oc.getRefund());
					oc.setCountByWechat(oc.getNoPaymentByWechat() + oc.getPaymentByWechat() + oc.getRefundByWechat());
					oc.setCountByAlipay(oc.getNoPaymentByAlipay() + oc.getPaymentByAlipay() + oc.getRefundByAlipay());
					results.add(oc);
				}
			}
		}

		return results;
	}

	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(DateUtils.getDateBegin(DateUtils.yesterdayDate())));
		System.out.println(dateFormat.format(DateUtils.getDateEnd(DateUtils.yesterdayDate())));
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(date.format(DateUtils.yesterdayDate()));
	}
}
