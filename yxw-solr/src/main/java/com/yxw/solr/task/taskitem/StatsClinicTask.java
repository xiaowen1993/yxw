package com.yxw.solr.task.taskitem;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.solr.task.collector.Collector;
import com.yxw.solr.task.collector.StatsClinicCollector;


public class StatsClinicTask implements BaseTask {
	private Logger logger = LoggerFactory.getLogger(StatsClinicTask.class);
	
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public StatsClinicTask() {
		super();
	}

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		logger.info("第 [" + count + "]次门诊统计开始....................");
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		Collector collector = new StatsClinicCollector();
		collector.startUp();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		logger.info("第 [" + count + "]次门诊统计开始处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
	}
}
