package com.yxw.task.taskitem;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.task.collector.StatsClinicCollector;


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
		if (logger.isInfoEnabled()) {
			logger.info("第[" + count + "]次，门诊统计开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		StatsClinicCollector collector = new StatsClinicCollector();
		collector.startUp();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 [" + count + "]次，门诊统计开始处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}
}
