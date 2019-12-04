package com.yxw.solr.task.collector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.solr.biz.common.service.HospitalInfoService;
import com.yxw.solr.constants.PoolConstant;
import com.yxw.solr.task.callable.StatsCardsCallable;
import com.yxw.utils.DateUtils;

public class StatsCardsCollector implements Collector {
	
	private Logger logger = LoggerFactory.getLogger(StatsCardsCollector.class);

	public void startUp() {
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("statsCard"));
		List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();

		HospitalInfoService hospitalInfoService = SpringContextHolder.getBean(HospitalInfoService.class);
		Map<String, Map<String, List<String>>> map = hospitalInfoService.getInfos();
		
		// 不指定日期，则统计从开始时间到昨天为止。（实际是到前天为止，昨天统计前天的数据）
		// 指定日期，则只统计昨天的数据。
		String statsDate = "";
		statsDate = DateUtils.getDayForDate(new Date(), -1);		// 开关此处代码即可。

		/** ------------------------------------- 指定平台的统计 -------------------------------------------- **/
		for (Entry<String, Map<String, List<String>>> hospitalMap : map.entrySet()) {
			String hospitalCode = hospitalMap.getKey();

			for (Entry<String, List<String>> platformMap : hospitalMap.getValue().entrySet()) {
				String platform = platformMap.getKey();
				List<String> branchCodes = platformMap.getValue();

				for (String branchCode : branchCodes) {
					StatsCardsCallable callable = new StatsCardsCallable(Integer.valueOf(platform), hospitalCode, branchCode, statsDate);
					FutureTask<String> task = new FutureTask<String>(callable);
					taskList.add(task);
					executorService.submit(task);
					
				}
				
				// 统计所有分院
				StatsCardsCallable callable = new StatsCardsCallable(Integer.valueOf(platform), hospitalCode, "-1", statsDate);
				FutureTask<String> task = new FutureTask<String>(callable);
				taskList.add(task);
				executorService.submit(task);
				
			}
		}
			
		try {
			for (FutureTask<String> task : taskList) {
				// 后续可能需要改成1天
				String object = task.get(30, TimeUnit.SECONDS);
				if (StringUtils.isNoneBlank(object)) {
					logger.info(object);
				} 
			}
			
			/** ------------------------------------- 全关平台的统计 -------------------------------------------- **/
			if (taskList.size() > 0) {
				taskList.clear();
				for (Entry<String, Map<String, List<String>>> hospitalMap : map.entrySet()) {
					String hospitalCode = hospitalMap.getKey();
					for (Entry<String, List<String>> platformMap : hospitalMap.getValue().entrySet()) {
						List<String> branchCodes = platformMap.getValue();

						for (String branchCode : branchCodes) {
							StatsCardsCallable callable = new StatsCardsCallable(-1, hospitalCode, branchCode, statsDate);
							FutureTask<String> task = new FutureTask<String>(callable);
							taskList.add(task);
							executorService.submit(task);
							
						}
						
						// 统计所有分院
						StatsCardsCallable callable = new StatsCardsCallable(-1, hospitalCode, "-1", statsDate);
						FutureTask<String> task = new FutureTask<String>(callable);
						taskList.add(task);
						executorService.submit(task);
						
						// 每家医院 按平台来 只要做一次即可
						break;
					}
				}
				
				for (FutureTask<String> task : taskList) {
					// 后续可能需要改成1天
					String object = task.get(30, TimeUnit.SECONDS);
					if (StringUtils.isNoneBlank(object)) {
						logger.info(object);
					} 
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("统计就诊卡错误. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		} finally {
			executorService.shutdown();
		}
	}

}
