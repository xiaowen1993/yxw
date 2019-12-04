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

import com.yxw.task.collect.CurDayvisitWarnCollector;

/**
 * @Package: com.yxw.platform.quartz.task
 * @ClassName: CurDayVisitTask
 * @Statement: <p>
 *             就诊当天的就诊消息推送
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-10-24
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class CurDayVisitWarnTask {
	public static Logger logger = LoggerFactory.getLogger(CurDayVisitWarnTask.class);
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public CurDayVisitWarnTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次，就诊当天的就诊消息推送处理开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		CurDayvisitWarnCollector collector = new CurDayvisitWarnCollector();
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次，就诊当天的就诊消息推送处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

}
