package com.yxw.outside.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.biz.common.service.HospitalInfoService;
import com.yxw.constants.Cores;
import com.yxw.constants.OutsideResultConstant;
import com.yxw.constants.PoolConstant;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.outside.callable.AgeGroupDatasCallable;
import com.yxw.outside.callable.CardDatasCallable;
import com.yxw.outside.callable.CardResumeCallable;
import com.yxw.outside.callable.ClinicDatasCallable;
import com.yxw.outside.callable.ClinicResumeCallable;
import com.yxw.outside.callable.DepositDatasCallable;
import com.yxw.outside.callable.DepositResumeCallable;
import com.yxw.outside.callable.GenderDatasCallable;
import com.yxw.outside.callable.HospitalResumeCallable;
import com.yxw.outside.callable.QueryInfosCallable;
import com.yxw.outside.callable.RegDatasCallable;
import com.yxw.outside.callable.RegResumeCallable;
import com.yxw.outside.callable.SubscribeDatasCallable;
import com.yxw.outside.callable.SubscribeResumeCallable;
import com.yxw.outside.service.YxwStatsService;
import com.yxw.outside.vo.AreaVo;
import com.yxw.outside.vo.YxwResponse;
import com.yxw.outside.vo.YxwResult;
import com.yxw.utils.DateUtils;
import com.yxw.vo.StatsHospitalInfosVo;
import com.yxw.vo.attribution.AttributionStats;

public class YxwStatsServiceImpl implements YxwStatsService {

	private Logger logger = LoggerFactory.getLogger(YxwStatsService.class);

	@Override
	public String getAllResume() {
		YxwResponse yxwResponse = new YxwResponse();
		Map<String, Object> resultMap = new HashMap<>();
		// 分线程获取下列信息
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("getAllResume"));
		List<FutureTask<Map<String, Object>>> taskList = new ArrayList<FutureTask<Map<String, Object>>>();

		// 1、医院数目 | 区域医院数据
		Callable<Map<String, Object>> areaCalllabel = new HospitalResumeCallable();
		FutureTask<Map<String, Object>> areaTask = new FutureTask<Map<String, Object>>(areaCalllabel);
		taskList.add(areaTask);
		executorService.submit(areaTask);

		// 2、微信关注数|支付宝关注数
		Callable<Map<String, Object>> subscribeCalllabel = new SubscribeResumeCallable();
		FutureTask<Map<String, Object>> subscribeTask = new FutureTask<Map<String, Object>>(subscribeCalllabel);
		taskList.add(subscribeTask);
		executorService.submit(subscribeTask);

		// 3、总绑卡数|微信支付宝绑卡情况
		Callable<Map<String, Object>> cardCalllabel = new CardResumeCallable();
		FutureTask<Map<String, Object>> cardTask = new FutureTask<Map<String, Object>>(cardCalllabel);
		taskList.add(cardTask);
		executorService.submit(cardTask);

		// 4、挂号概要
		Callable<Map<String, Object>> regCalllabel = new RegResumeCallable();
		FutureTask<Map<String, Object>> regTask = new FutureTask<Map<String, Object>>(regCalllabel);
		taskList.add(regTask);
		executorService.submit(regTask);

		// 5、门诊概要
		Callable<Map<String, Object>> clinicCalllabel = new ClinicResumeCallable();
		FutureTask<Map<String, Object>> clinicTask = new FutureTask<Map<String, Object>>(clinicCalllabel);
		taskList.add(clinicTask);
		executorService.submit(clinicTask);

		// 6、押金概要
		Callable<Map<String, Object>> depositCalllabel = new DepositResumeCallable();
		FutureTask<Map<String, Object>> depositTask = new FutureTask<Map<String, Object>>(depositCalllabel);
		taskList.add(depositTask);
		executorService.submit(depositTask);

		try {
			for (FutureTask<Map<String, Object>> task : taskList) {
				// 后续可能需要改成1天
				Map<String, Object> result = task.get(30, TimeUnit.SECONDS);
				if (!CollectionUtils.isEmpty(result)) {
					resultMap.putAll(result);
				}
			}

			YxwResult yxwResult = new YxwResult();
			yxwResult.setItems(resultMap);
			yxwResult.setSize(resultMap.size());
			yxwResponse.setResult(yxwResult);
		} catch (Exception e) {
			logger.error("获取摘要信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			yxwResponse.setResultCode(OutsideResultConstant.RESULT_CODE_ERROR);
		}
		
		executorService.shutdown();

		return JSON.toJSONString(yxwResponse);
	}

	@Override
	public String getAreaHospitalInfos() {
		YxwResponse yxwResponse = new YxwResponse();
		HospitalInfoService service = SpringContextHolder.getBean(HospitalInfoService.class);
		Map<String, List<StatsHospitalInfosVo>> map = service.getAllHospitalInfos();

		Map<String, AreaVo> areaVos = new HashMap<>();

		for (Entry<String, List<StatsHospitalInfosVo>> areaEntry : map.entrySet()) {
			for (StatsHospitalInfosVo vo : areaEntry.getValue()) {
				String areaName = vo.getAreaName();
				Integer state = vo.getState();
				AreaVo areaVo = null;
				if (areaVos.containsKey(areaName)) {
					areaVo = areaVos.get(areaName);
					if (state == 0) {
						areaVo.getSignItems().add(vo);
					} else {
						areaVo.getOnlineItems().add(vo);
					}

					areaVo.setSize(areaVo.getSize() + 1);
				} else {
					areaVo = new AreaVo();
					areaVo.setAreaName(areaName);
					if (state == 0) {
						areaVo.getSignItems().add(vo);
					} else {
						areaVo.getOnlineItems().add(vo);
					}
					areaVo.setSize(1);
				}

				areaVos.put(areaName, areaVo);
			}
		}

		YxwResult yxwResult = new YxwResult();
		yxwResult.setItems(areaVos.values());
		yxwResult.setSize(areaVos.size());
		yxwResponse.setResult(yxwResult);

		return JSON.toJSONString(yxwResponse);
	}

	@Override
	public String getCardDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth) {
		YxwResponse yxwResponse = new YxwResponse();
		Map<String, Object> resultMap = new HashMap<>();

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("getCardDatas"));
		List<FutureTask<Map<String, Object>>> taskList = new ArrayList<FutureTask<Map<String, Object>>>();

		// 3、总绑卡数|微信支付宝绑卡情况
		Callable<Map<String, Object>> cardCalllabel = new CardDatasCallable(areaCode, hospitalCode, beginMonth.substring(0, 7),
				endMonth.substring(0, 7));
		FutureTask<Map<String, Object>> cardTask = new FutureTask<Map<String, Object>>(cardCalllabel);
		taskList.add(cardTask);
		executorService.submit(cardTask);

		try {
			for (FutureTask<Map<String, Object>> task : taskList) {
				// 后续可能需要改成1天
				Map<String, Object> result = task.get(30, TimeUnit.SECONDS);
				if (!CollectionUtils.isEmpty(result)) {
					resultMap.putAll(result);
				}
			}

			YxwResult yxwResult = new YxwResult();
			yxwResult.setItems(resultMap);
			yxwResult.setSize(resultMap.size());
			yxwResponse.setResult(yxwResult);
		} catch (Exception e) {
			logger.error("获取绑卡信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			yxwResponse.setResultCode(OutsideResultConstant.RESULT_CODE_ERROR);
		} 
		
		executorService.shutdown();
		
		return JSON.toJSONString(yxwResponse);
	}

	@Override
	public String getSubscribeDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth) {
		YxwResponse yxwResponse = new YxwResponse();
		Map<String, Object> resultMap = new HashMap<>();

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("getSubscribeDatas"));
		List<FutureTask<Map<String, Object>>> taskList = new ArrayList<FutureTask<Map<String, Object>>>();

		// 3、总绑卡数|微信支付宝绑卡情况
		Callable<Map<String, Object>> subscirbeCalllabel = new SubscribeDatasCallable(areaCode, hospitalCode, beginMonth.substring(0, 7),
				endMonth.substring(0, 7));
		FutureTask<Map<String, Object>> subscribeTask = new FutureTask<Map<String, Object>>(subscirbeCalllabel);
		taskList.add(subscribeTask);
		executorService.submit(subscribeTask);

		try {
			for (FutureTask<Map<String, Object>> task : taskList) {
				// 后续可能需要改成1天
				Map<String, Object> result = task.get(30, TimeUnit.SECONDS);
				if (!CollectionUtils.isEmpty(result)) {
					resultMap.putAll(result);
				}
			}

			YxwResult yxwResult = new YxwResult();
			yxwResult.setItems(resultMap);
			yxwResult.setSize(resultMap.size());
			yxwResponse.setResult(yxwResult);
		} catch (Exception e) {
			logger.error("获取关注统计信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			yxwResponse.setResultCode(OutsideResultConstant.RESULT_CODE_ERROR);
		} 
		
		executorService.shutdown();

		return JSON.toJSONString(yxwResponse);
	}

	@Override
	public String getOrderDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth) {
		YxwResponse yxwResponse = new YxwResponse();
		Map<String, Object> resultMap = new HashMap<>();

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("getOrderDatas"));
		List<FutureTask<Map<String, Object>>> taskList = new ArrayList<FutureTask<Map<String, Object>>>();

		// 挂号
		Callable<Map<String, Object>> regCallable = new RegDatasCallable(areaCode, hospitalCode, beginMonth.substring(0, 7),
				endMonth.substring(0, 7));
		FutureTask<Map<String, Object>> regTask = new FutureTask<Map<String, Object>>(regCallable);
		taskList.add(regTask);
		executorService.submit(regTask);
		// 门诊
		Callable<Map<String, Object>> clinicCallable = new ClinicDatasCallable(areaCode, hospitalCode, beginMonth.substring(0, 7),
				endMonth.substring(0, 7));
		FutureTask<Map<String, Object>> clinicTask = new FutureTask<Map<String, Object>>(clinicCallable);
		taskList.add(clinicTask);
		executorService.submit(clinicTask);
		// 押金
		Callable<Map<String, Object>> depositCallable = new DepositDatasCallable(areaCode, hospitalCode, beginMonth.substring(0, 7),
				endMonth.substring(0, 7));
		FutureTask<Map<String, Object>> depositTask = new FutureTask<Map<String, Object>>(depositCallable);
		taskList.add(depositTask);
		executorService.submit(depositTask);

		try {
			for (FutureTask<Map<String, Object>> task : taskList) {
				// 后续可能需要改成1天
				Map<String, Object> result = task.get(30, TimeUnit.SECONDS);
				if (!CollectionUtils.isEmpty(result)) {
					resultMap.putAll(result);
				}
			}

			YxwResult yxwResult = new YxwResult();
			yxwResult.setItems(resultMap);
			yxwResult.setSize(resultMap.size());
			yxwResponse.setResult(yxwResult);
		} catch (Exception e) {
			logger.error("获取订单统计信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			yxwResponse.setResultCode(OutsideResultConstant.RESULT_CODE_ERROR);
		} 
		
		executorService.shutdown();

		return JSON.toJSONString(yxwResponse);
	}

	@Override
	public String getAttributionDatas() {
		YxwResponse yxwResponse = new YxwResponse();
		Map<String, Object> resultMap = new HashMap<>();

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("getAttributionDatas"));
		List<FutureTask<Map<String, Object>>> taskList = new ArrayList<FutureTask<Map<String, Object>>>();

		SolrQuery solrQuery = new SolrQuery();
		String thisMonth = DateUtils.getFirstDayOfLastMonth(DateUtils.today()).substring(0, 7);
		solrQuery.setQuery("thisMonth:".concat(thisMonth));
		solrQuery.setRows(Integer.MAX_VALUE);
		solrQuery.addSort("cumulateCount", ORDER.desc);
		solrQuery.addSort("areaName", ORDER.asc);
		Callable<Map<String, Object>> attributionCallable = new QueryInfosCallable(Cores.CORE_STATS_ATTRIBUTION, "attribution", solrQuery, AttributionStats.class);
		FutureTask<Map<String, Object>> subscribeTask = new FutureTask<Map<String, Object>>(attributionCallable);
		taskList.add(subscribeTask);
		executorService.submit(subscribeTask);

		try {
			for (FutureTask<Map<String, Object>> task : taskList) {
				// 后续可能需要改成1天
				Map<String, Object> result = task.get(30, TimeUnit.SECONDS);
				if (!CollectionUtils.isEmpty(result)) {
					resultMap.putAll(result);
				}
			}

			YxwResult yxwResult = new YxwResult();
			yxwResult.setItems(resultMap);
			yxwResult.setSize(resultMap.size());
			yxwResponse.setResult(yxwResult);
		} catch (Exception e) {
			logger.error("获取归属地统计信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			yxwResponse.setResultCode(OutsideResultConstant.RESULT_CODE_ERROR);
		} 
		
		executorService.shutdown();

		return JSON.toJSONString(yxwResponse);
	}

	@Override
	public String getGenderDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth) {
		YxwResponse yxwResponse = new YxwResponse();
		Map<String, Object> resultMap = new HashMap<>();

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("getGenderDatas"));
		List<FutureTask<Map<String, Object>>> taskList = new ArrayList<FutureTask<Map<String, Object>>>();

		// 3、总绑卡数|微信支付宝绑卡情况
		Callable<Map<String, Object>> genderCalllabel = new GenderDatasCallable(areaCode, hospitalCode, beginMonth.substring(0, 7),
				endMonth.substring(0, 7));
		FutureTask<Map<String, Object>> genderTask = new FutureTask<Map<String, Object>>(genderCalllabel);
		taskList.add(genderTask);
		executorService.submit(genderTask);

		try {
			for (FutureTask<Map<String, Object>> task : taskList) {
				// 后续可能需要改成1天
				Map<String, Object> result = task.get(30, TimeUnit.SECONDS);
				if (!CollectionUtils.isEmpty(result)) {
					resultMap.putAll(result);
				}
			}

			YxwResult yxwResult = new YxwResult();
			yxwResult.setItems(resultMap);
			yxwResult.setSize(resultMap.size());
			yxwResponse.setResult(yxwResult);
		} catch (Exception e) {
			logger.error("获取性别统计信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			yxwResponse.setResultCode(OutsideResultConstant.RESULT_CODE_ERROR);
		} 
		
		executorService.shutdown();

		return JSON.toJSONString(yxwResponse);
	}

	@Override
	public String getAgeGroupDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth) {
		YxwResponse yxwResponse = new YxwResponse();
		Map<String, Object> resultMap = new HashMap<>();

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("getAgeGroupDatas"));
		List<FutureTask<Map<String, Object>>> taskList = new ArrayList<FutureTask<Map<String, Object>>>();

		// 3、总绑卡数|微信支付宝绑卡情况
		Callable<Map<String, Object>> ageGroupCalllabel = new AgeGroupDatasCallable(areaCode, hospitalCode, beginMonth.substring(0, 7),
				endMonth.substring(0, 7));
		FutureTask<Map<String, Object>>ageGroupTask = new FutureTask<Map<String, Object>>(ageGroupCalllabel);
		taskList.add(ageGroupTask);
		executorService.submit(ageGroupTask);

		try {
			for (FutureTask<Map<String, Object>> task : taskList) {
				// 后续可能需要改成1天
				Map<String, Object> result = task.get(30, TimeUnit.SECONDS);
				if (!CollectionUtils.isEmpty(result)) {
					resultMap.putAll(result);
				}
			}

			YxwResult yxwResult = new YxwResult();
			yxwResult.setItems(resultMap);
			yxwResult.setSize(resultMap.size());
			yxwResponse.setResult(yxwResult);
		} catch (Exception e) {
			logger.error("获取年龄段统计信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			yxwResponse.setResultCode(OutsideResultConstant.RESULT_CODE_ERROR);
		} 
		
		executorService.shutdown();

		return JSON.toJSONString(yxwResponse);
	}

}
