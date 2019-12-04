package com.yxw.stats.task.project.taskitem;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.stats.task.project.collect.MedicalCardStatisticalCollector;

/**
 * 
 * 
 * CREATE TABLE `SYS_MEDICAL_CARD_STATISTICAL` (
	  `ID` varchar(32) NOT NULL,
	  `DATE` varchar(10) DEFAULT NULL COMMENT '日期',
	  `HOSPITAL_ID` varchar(32) NOT NULL COMMENT '医院ID',
	  `BRANCH_ID` varchar(32) DEFAULT NULL COMMENT '分院ID',
	  `COUNT` int(11) DEFAULT NULL COMMENT '绑卡数',
	  `PLATFORM` int(5) DEFAULT NULL COMMENT '绑卡渠道',
	  PRIMARY KEY (`ID`)
	) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC
	
	
	select count(1) as counts, DATE_FORMAT(createTime,'%Y-%m-%d') as createTime 
	from tb_medical_card 
	where hospitalId = {n:hospitalId} and createTime BETWEEN '2014-01-01' and {今天} GROUP BY DATE_FORMAT(createTime,'%Y-%m-%d') DESC
	
 *
 */
public class MedicalCardStatisticalTask {

	public static Logger logger = LoggerFactory.getLogger(MedicalCardStatisticalTask.class);
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public MedicalCardStatisticalTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次绑卡统计数据（按日）处理开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		MedicalCardStatisticalCollector collector = new MedicalCardStatisticalCollector();
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次绑卡统计数据（按日）处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

}
