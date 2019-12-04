package com.yxw.stats.task.project.taskitem;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.stats.task.project.collect.DataMergeCollector;

public class DataMergeTask {

	public static Logger logger = LoggerFactory.getLogger(DataMergeTask.class);
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public DataMergeTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + "次数据合并处理开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		DataMergeCollector collector = new DataMergeCollector();
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次数据合并处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

}
