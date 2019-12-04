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

import com.yxw.task.collect.RegisterSourceCollector;

/**
 * @Package: com.yxw.platform.quartz.task
 * @ClassName: UnlockRegisterSourceTask
 * @Statement: <p>获取医院号源任务</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-18
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterSourceTask {
	public static Logger logger = LoggerFactory.getLogger(RegisterSourceTask.class);
	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次医院号源采集开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		RegisterSourceCollector collector = new RegisterSourceCollector();
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次医院号源采集结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

}
