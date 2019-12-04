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

import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.task.collect.HandleRegisterExceptionCollector;

/**
 * @Package: com.yxw.platform.quartz.task
 * @ClassName: HospitalBaseInfoTask
 * @Statement: <p>
 *             异常处理任务
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HandleRegisterExceptionTask extends Junit4SpringContextHolder {
	public static Logger logger = LoggerFactory.getLogger(HandleRegisterExceptionTask.class);
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public HandleRegisterExceptionTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次挂号异常处理开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		HandleRegisterExceptionCollector collector = new HandleRegisterExceptionCollector();
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次挂号异常处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

}
