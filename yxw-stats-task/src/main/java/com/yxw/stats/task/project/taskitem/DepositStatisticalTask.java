package com.yxw.stats.task.project.taskitem;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.stats.task.project.collect.DepositStatisticalCollector;

/**
 * 
 * 
 * CREATE TABLE `SYS_DEPOSIT_STATISTICAL` (
	  `ID` varchar(32) NOT NULL,
	  `DATE` varchar(10) DEFAULT NULL COMMENT '时间',
	  `HOSPITAL_ID` varchar(32) NOT NULL COMMENT '医院id',
	  `BRANCH_ID` varchar(32) DEFAULT NULL COMMENT '分院编号',
	  `PAYMENT` int(11) DEFAULT NULL COMMENT '已支付数',
	  `NO_PAYMENT` int(11) DEFAULT NULL COMMENT '未支付数',
	  `REFUND` int(11) DEFAULT NULL COMMENT '已退费数',
	  `DEPOSIT_PAY_FEE` double(20,2) DEFAULT NULL COMMENT '住院押金补缴支付总金额',
	  `DEPOSIT_REFUND_FEE` double(20,2) DEFAULT NULL COMMENT '住院押金补缴退费总金额',
	  `PART_REFUND` double(20,2) DEFAULT NULL,
	  `BIZ_MODE` int(11) DEFAULT NULL,
	  PRIMARY KEY (`ID`)
	) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC
	
	
	select 
	sum(CASE when payType in(2,6,7,8,10,11,18,19,20) then counts else 0 END) as PAYMENT, 
	sum(CASE when payType in(-1,-2,0,1,3,5,9,17) then counts else 0 END) as NO_PAYMENT, 
	sum(CASE when paytype in(4,12,13,14,15,16,21,98) then counts else 0 END) DIV 2 as REFUND, 
	
	sum(CASE when payType in(2,6,7,8,10,11,18,19,20) then totalFees else 0 END) as DEPOSIT_PAY_FEE, 
	sum(CASE when payType = 98 then returnFees else 0 END) as PART_REFUND, 
	sum(CASE when paytype in(4,12,13,14,15,16,21) then totalFees else 0 END) DIV 2 as DEPOSIT_REFUND_FEE, 
	
	createTime 
					
	from (

		select 
		count(1) as counts, 
		sum(totalFee) as totalFees, 
		sum(returnFee) as returnFees, 
		DATE_FORMAT(createTime,'%Y-%m-%d') as createTime, 
		payType 

		from tb_order 
		where hospitalId = 15 and type = 3 
		GROUP BY DATE_FORMAT(createTime,'%Y-%m-%d'), payType

	) a  GROUP BY createTime desc
	
 *
 */
public class DepositStatisticalTask {

	public static Logger logger = LoggerFactory.getLogger(DepositStatisticalTask.class);
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public DepositStatisticalTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次住院押金统计数据（按日）处理开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		DepositStatisticalCollector collector = new DepositStatisticalCollector();
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次住院押金统计数据（按日）处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

}
