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

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.task.collect.RegisterUnlockCollector;

/**
 * @Package: com.yxw.platform.quartz.task
 * @ClassName: UnlockRegisterSourceTask
 * @Statement: <p>号源解锁任务</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-18
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterUnlockTask {
	private static Logger logger = LoggerFactory.getLogger(RegisterUnlockTask.class);
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("号源解锁任务.第 " + count + " 次采集开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		RegisterUnlockCollector collector = new RegisterUnlockCollector();
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isDebugEnabled()) {
			logger.info("号源解锁任务.第 " + count + " 次采集结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

}
