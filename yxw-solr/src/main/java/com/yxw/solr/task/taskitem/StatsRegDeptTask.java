package com.yxw.solr.task.taskitem;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.solr.task.collector.Collector;
import com.yxw.solr.task.collector.StatsRegDeptCollector;


public class StatsRegDeptTask implements BaseTask {
	private Logger logger = LoggerFactory.getLogger(StatsRegDeptTask.class);
	
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public StatsRegDeptTask() {
		super();
	}

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("第 [" + count + "]次挂号科室统计开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		Collector collector = new StatsRegDeptCollector();
		collector.startUp();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 [" + count + "]次挂号科室统计开始处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}
}
