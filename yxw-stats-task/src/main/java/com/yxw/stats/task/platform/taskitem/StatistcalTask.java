package com.yxw.stats.task.platform.taskitem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.stats.task.platform.collect.StatisticalCollector;

public class StatistcalTask {
	public static Logger logger = LoggerFactory.getLogger(StatistcalTask.class);
	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	private Long loopTime;

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("统计处理任务.第 " + count + " 次统计处理开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		StatisticalCollector collector = new StatisticalCollector(loopTime);
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("统计处理任务.第 " + count + " 次统计诊处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

	public Long getLoopTime() {
		return loopTime;
	}

	public void setLoopTime(Long loopTime) {
		this.loopTime = loopTime;
	}
}
