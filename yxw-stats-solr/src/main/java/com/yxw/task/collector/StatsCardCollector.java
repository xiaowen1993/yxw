package com.yxw.task.collector;

import java.util.ArrayList;
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

import com.yxw.biz.common.service.HospitalInfoService;
import com.yxw.constants.PoolConstant;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.task.callable.StatsCardCallable;
import com.yxw.vo.StatsHospitalInfosVo;

public class StatsCardCollector implements Collector {

	private Logger logger = LoggerFactory.getLogger(StatsCardCollector.class);

	public void startUp() {
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("statsCard"));
		List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();

		HospitalInfoService hospitalInfoService = SpringContextHolder.getBean(HospitalInfoService.class);
		Map<String, List<StatsHospitalInfosVo>> map = hospitalInfoService.getHospitalInfosGroupByAreaCode();
		// logger.error(JSON.toJSONString(map));

		// 不指定日期，则统计从开始时间到昨天为止。（实际是到前天为止，昨天统计前天的数据）
		// 指定日期，则只统计昨天的数据。
		String statsDate = "";
		// statsDate = DateUtils.today(); // 开关此处代码即可。

		/**
		 * ------------------------------------- 所有区域单个医院统计
		 * --------------------------------------------
		 **/
		for (Entry<String, List<StatsHospitalInfosVo>> areaMap : map.entrySet()) {
			// String areaCode = areaMap.getKey();
			for (StatsHospitalInfosVo infosVo : areaMap.getValue()) {
				StatsCardCallable callable = new StatsCardCallable(infosVo, false, statsDate);
				FutureTask<String> task = new FutureTask<String>(callable);
				taskList.add(task);
				executorService.submit(task);
			}
		}

		try {
			for (FutureTask<String> task : taskList) {
				// 后续可能需要改成1天
				String object = task.get(30, TimeUnit.MINUTES);
				if (StringUtils.isNoneBlank(object)) {
					logger.info(object);
				}
			}

			/**
			 * ------------------------------------- 按区域统计 --------------------------------------------
			 **/
			if (taskList.size() > 0) {
				taskList.clear();
				for (Entry<String, List<StatsHospitalInfosVo>> areaMap : map.entrySet()) {
					logger.info("开始进行区域统计=====[{}]", areaMap.getValue().get(0).getAreaName());
					StatsCardCallable callable = new StatsCardCallable(areaMap.getValue().get(0), true, statsDate);
					FutureTask<String> task = new FutureTask<String>(callable);
					taskList.add(task);
					executorService.submit(task);
				}

				for (FutureTask<String> task : taskList) {
					// 后续可能需要改成1天
					String object = task.get(30, TimeUnit.MINUTES);
					if (StringUtils.isNoneBlank(object)) {
						logger.info(object);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("绑卡信息统计错误. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		} finally {
			executorService.shutdown();
		}
	}

}
