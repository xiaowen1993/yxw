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

import com.yxw.task.collect.HospitalDeptInfoCollector;

/**
 * @Package: com.yxw.platform.quartz.task
 * @ClassName: HospitalBaseInfoTask
 * @Statement: <p>
 *             定时采集医院基本信息 科室/医生信息
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HospitalDeptInfoTask {
	private static Logger logger = LoggerFactory.getLogger(HospitalDeptInfoTask.class);
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public HospitalDeptInfoTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次医院科室信息采集开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		HospitalDeptInfoCollector collector = new HospitalDeptInfoCollector();
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次医院科室信息采集结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

}
