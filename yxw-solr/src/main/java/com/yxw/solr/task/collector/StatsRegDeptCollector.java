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
import com.yxw.solr.task.callable.StatsRegDeptCallable;
import com.yxw.utils.DateUtils;


public class StatsRegDeptCollector implements Collector {
	private Logger logger = LoggerFactory.getLogger(StatsRegDeptCollector.class);

	public void startUp() {
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("statsRegDept"));
		List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();
		HospitalInfoService hospitalInfoService = SpringContextHolder.getBean(HospitalInfoService.class);

		String statsDate = "";
		statsDate = DateUtils.getDayForDate(new Date(), -1);    // 开关此处即可

		/**
		 * 科室统计，不按分院进行统计，只对医院，科室，但是，科室可以挂靠分院，不进行总院的一个计算了。（理论上来说，每个科室，ID都是不同的，无关分院）
		 * 不进行分院统计了。
		 */
		
		Map<String, Map<String, List<String>>> map = hospitalInfoService.getInfos();

		/** ------------------------------------- 指定平台的统计 -------------------------------------------- **/
		for (Entry<String, Map<String, List<String>>> hospitalMap : map.entrySet()) {
			String hospitalCode = hospitalMap.getKey();

			for (Entry<String, List<String>> platformMap : hospitalMap.getValue().entrySet()) {
				String platform = platformMap.getKey();

				// 不进行分院的统计 统一叫做
				StatsRegDeptCallable callable = new StatsRegDeptCallable(Integer.valueOf(platform), hospitalCode, "-1", statsDate);
				FutureTask<String> task = new FutureTask<String>(callable);
				taskList.add(task);
				executorService.submit(task);
			}
		}
		
		
		/** ------------------------------------- 全关平台的统计 -------------------------------------------- **/
		try {
			for (FutureTask<String> task : taskList) {
				String result = task.get(Integer.MAX_VALUE, TimeUnit.DAYS);
				if (StringUtils.isNotBlank(result)) {
					logger.info(result);
				}
			}
			
			if (taskList.size() > 0) {
				taskList.clear();
				for (Entry<String, Map<String, List<String>>> hospitalMap : map.entrySet()) {
					String hospitalCode = hospitalMap.getKey();
					// 统计所有分院
					StatsRegDeptCallable callable = new StatsRegDeptCallable(-1, hospitalCode, "-1", statsDate);
					FutureTask<String> task = new FutureTask<String>(callable);
					taskList.add(task);
					executorService.submit(task);
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
			logger.error("挂号科室轮训统计异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		executorService.shutdown();
	}

}
