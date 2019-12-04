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
import com.yxw.solr.task.callable.StatsClinicCallable;
import com.yxw.utils.DateUtils;

public class StatsClinicCollector implements Collector {
	private Logger logger = LoggerFactory.getLogger(StatsClinicCollector.class);

	public void startUp() {
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("statsClinic"));
		List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();
		HospitalInfoService hospitalInfoService = SpringContextHolder.getBean(HospitalInfoService.class);

		String statsDate = "";
		statsDate = DateUtils.getDayForDate(new Date(), -1);
		//statsDate = "2016-05-26";

		try {
			Map<String, Map<String, List<String>>> map = hospitalInfoService.getInfos();

			/** ------------------------------------- 指定平台的统计 -------------------------------------------- **/
			for (Entry<String, Map<String, List<String>>> hospitalMap : map.entrySet()) {
				String hospitalCode = hospitalMap.getKey();

				for (Entry<String, List<String>> platformMap : hospitalMap.getValue().entrySet()) {
					String platform = platformMap.getKey();
					List<String> branchCodes = platformMap.getValue();

					for (String branchCode : branchCodes) {
						StatsClinicCallable callable = new StatsClinicCallable(Integer.valueOf(platform), hospitalCode, branchCode, statsDate);
						FutureTask<String> task = new FutureTask<String>(callable);
						taskList.add(task);
						executorService.submit(task);
					}

					// 统计所有分院
					StatsClinicCallable callable = new StatsClinicCallable(Integer.valueOf(platform), hospitalCode, "-1", statsDate);
					FutureTask<String> task = new FutureTask<String>(callable);
					taskList.add(task);
					executorService.submit(task);
				}
			}

			for (FutureTask<String> task : taskList) {
				String result = task.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (StringUtils.isNotBlank(result)) {
					logger.info(result);
				}
			}

			/** ------------------------------------- 全关平台的统计 -------------------------------------------- **/
			if (taskList.size() > 0) {
				for (Entry<String, Map<String, List<String>>> hospitalMap : map.entrySet()) {
					String hospitalCode = hospitalMap.getKey();

					for (Entry<String, List<String>> platformMap : hospitalMap.getValue().entrySet()) {
						List<String> branchCodes = platformMap.getValue();

						for (String branchCode : branchCodes) {
							StatsClinicCallable callable = new StatsClinicCallable(-1, hospitalCode, branchCode, statsDate);
							FutureTask<String> task = new FutureTask<String>(callable);
							taskList.add(task);
							executorService.submit(task);
						}

						// 统计所有分院
						StatsClinicCallable callable = new StatsClinicCallable(-1, hospitalCode, "-1", statsDate);
						FutureTask<String> task = new FutureTask<String>(callable);
						taskList.add(task);
						executorService.submit(task);

						break;
					}
				}

				for (FutureTask<String> task : taskList) {
					String result = task.get(Long.MAX_VALUE, TimeUnit.DAYS);
					if (StringUtils.isNotBlank(result)) {
						logger.info(result);
					}
				}
			}

		} catch (Exception e) {
			logger.error("门诊轮训统计异常.. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		executorService.shutdown();
	}

}
