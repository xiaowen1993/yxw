package com.yxw.solr.biz.register.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.solr.biz.register.service.RegDeptService;
import com.yxw.solr.constants.PoolConstant;
import com.yxw.solr.task.callable.StatsRegDeptCallable;
import com.yxw.solr.task.collector.StatsRegDeptCollector;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.register.RegDeptStatsRequest;

public class RegDeptServiceImplTest extends Junit4SpringContextHolder {
	
	private Logger logger = LoggerFactory.getLogger(RegDeptServiceImplTest.class);

	@Test
	public void testStatsInfos() {
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("statsCard"));
		List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();

		try {
			List<Map<String, Map<String, List<String>>>> platforms = getDepts();
			for (Map<String, Map<String, List<String>>> platformMap: platforms) {
				for (Entry<String, Map<String, List<String>>> entry : platformMap.entrySet()) {
					String platformCode = entry.getKey();
	
					for (Entry<String, List<String>> hospitalMap : entry.getValue().entrySet()) {
						String hospitalCode = hospitalMap.getKey();
						List<String> branchCodes = hospitalMap.getValue();
	
						for (String branchCode : branchCodes) {
							// 昨天一天统计
							// String statsDate = DateUtils.getDayForDate(new Date(), -1);
							// String statsDate = DateUtils.today();
							// 昨天之前的多日统计
							 String statsDate = "";
							StatsRegDeptCallable callable = new StatsRegDeptCallable(Integer.valueOf(platformCode), hospitalCode, branchCode, statsDate);
							FutureTask<String> task = new FutureTask<String>(callable);
							executorService.submit(callable);
							taskList.add(task);
						}
					}
				}
			}

			List<String> results = new ArrayList<String>();
			for (FutureTask<String> task : taskList) {
				String result = task.get(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
				if (StringUtils.isNotBlank(result)) {
					results.add(result);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}
	
	private List<Map<String, Map<String, List<String>>>> getDepts() {
		List<Map<String, Map<String, List<String>>>> platforms = new ArrayList<>();
		Map<String, Map<String, List<String>>> platformMap1 = new HashMap<>();
		
		Map<String, List<String>> hosp0 = new HashMap<String, List<String>>();
		List<String> branches0 = new ArrayList<String>();
		branches0.add("HZ010");
		hosp0.put("bjdxszyycs", branches0);
		platformMap1.put("3", hosp0);			// platform
		// platforms.put("-1", hosp0);
		platforms.add(platformMap1);
		
		// 深三
		Map<String, Map<String, List<String>>> platformMap2 = new HashMap<>();
		Map<String, List<String>> hosp1 = new HashMap<String, List<String>>();
		List<String> branches1 = new ArrayList<String>();
		
		branches1.add("45575559X");
		hosp1.put("szsdsrmyy", branches1);
		platformMap2.put("3", hosp1);
		// platforms.put("-1", hosp1);
		platforms.add(platformMap2);
		
		return platforms;
	}
	
	@Test
	public void testGetStatsInfos() {
		RegDeptService regDeptService = SpringContextHolder.getBean(RegDeptService.class);
		
		RegDeptStatsRequest request = new RegDeptStatsRequest();
		request.setHospitalCode("dgsrmyy");
		request.setBranchCode("0007");
		request.setPlatform(2);
		// request.setDeptName("妇产科");
		YxwResponse vo = regDeptService.getStatsInfos(request);
		
		logger.info(JSON.toJSONString(vo));
	}
	
	@Test
	public void testStatsInfoForDaysWithinPlatform() {
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("statsRegDept"));
		List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();

		try {
			List<Map<String, Map<String, List<String>>>> platforms = getDepts();
			for (Map<String, Map<String, List<String>>> platformMap: platforms) {
				for (Entry<String, Map<String, List<String>>> entry : platformMap.entrySet()) {
					String platformCode = entry.getKey();
	
					for (Entry<String, List<String>> hospitalMap : entry.getValue().entrySet()) {
						String hospitalCode = hospitalMap.getKey();
						List<String> branchCodes = hospitalMap.getValue();
	
						for (String branchCode : branchCodes) {
							// 昨天一天统计
							// String statsDate = DateUtils.getDayForDate(new Date(), -1);
							// String statsDate = DateUtils.today();
							// 昨天之前的多日统计
							String statsDate = "";
							StatsRegDeptCallable callable = new StatsRegDeptCallable(Integer.valueOf(platformCode), hospitalCode, branchCode, statsDate);
							FutureTask<String> task = new FutureTask<String>(callable);
							executorService.submit(callable);
							taskList.add(task);
						}
					}
				}
			}

			List<String> results = new ArrayList<String>();
			for (FutureTask<String> task : taskList) {
				String result = task.get(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
				if (StringUtils.isNotBlank(result)) {
					results.add(result);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}
	
	@Test
	public void doStats() {
		StatsRegDeptCollector collector = new StatsRegDeptCollector	();
		collector.startUp();
	}

}
