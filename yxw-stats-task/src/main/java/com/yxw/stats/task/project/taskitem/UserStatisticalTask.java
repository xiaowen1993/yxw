package com.yxw.stats.task.project.taskitem;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.stats.task.project.collect.UserStatisticalCollector;

/**
 * 
 * 
 * CREATE TABLE `tb_user_cumulate` (
	  `id` varchar(32) NOT NULL,
	  `appId` varchar(50) DEFAULT NULL,
	  `hospitalId` varchar(50) DEFAULT NULL,
	  `newUser` bigint(20) DEFAULT '0' COMMENT '新增的用户数量',
	  `cancelUser` bigint(20) DEFAULT '0' COMMENT '取消关注的用户数量，new_user减去cancel_user即为净增用户数量',
	  `cumulateUser` bigint(20) DEFAULT '0' COMMENT '总用户量',
	  `date` date DEFAULT NULL,
	  PRIMARY KEY (`id`),
	  UNIQUE KEY `IDX_appId_date` (`appId`,`date`) USING BTREE
	) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
	
 *
 */
public class UserStatisticalTask {

	public static Logger logger = LoggerFactory.getLogger(UserStatisticalTask.class);
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public UserStatisticalTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次用户关注数统计数据（按日）处理开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		UserStatisticalCollector collector = new UserStatisticalCollector();
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次用户关注数统计数据（按日）处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

}
