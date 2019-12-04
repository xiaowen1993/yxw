/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */
package com.yxw.task.taskitem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.task.collect.StopRegisterCollector;

/**
 * 
 * @Package: com.yxw.platform.quartz.task
 * @ClassName: StopRegisterTask
 * @Statement: <p>停诊处理任务</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class StopRegisterTask {
	public static Logger logger = LoggerFactory.getLogger(StopRegisterTask.class);
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
			logger.info("停诊处理任务.第 " + count + " 次停诊处理开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		StopRegisterCollector collector = new StopRegisterCollector(loopTime);
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("停诊处理任务.第 " + count + " 次停诊处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

	public Long getLoopTime() {
		return loopTime;
	}

	public void setLoopTime(Long loopTime) {
		this.loopTime = loopTime;
	}
}
