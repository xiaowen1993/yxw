package com.yxw.task.taskitem;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.task.collect.RedisTestCollector;

public class RedisTestTask {
	private static Logger logger = LoggerFactory.getLogger(RedisTestTask.class);
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isDebugEnabled()) {
			logger.info("定时写入redis任务.第 " + count + " 次写入数据开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		RedisTestCollector collector = new RedisTestCollector();
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isDebugEnabled()) {
			logger.info("定时写入redis任务.第 " + count + " 次写入数据结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

}
