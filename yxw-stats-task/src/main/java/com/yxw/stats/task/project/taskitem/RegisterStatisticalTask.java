package com.yxw.stats.task.project.taskitem;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.stats.task.project.collect.DepositStatisticalCollector;

/**
 * 
 * 
 * CREATE TABLE `SYS_REG_STATISTICAL` (
	  `ID` varchar(32) NOT NULL,
	  `DATE` varchar(10) DEFAULT NULL COMMENT '日期',
	  `HOSPITAL_ID` varchar(32) NOT NULL COMMENT '医院ID',
	  `BRANCH_ID` varchar(32) DEFAULT NULL COMMENT '分院ID',
	  `RESERVATION_PAYMENT` int(11) DEFAULT NULL COMMENT '预约挂号已支付数',
	  `RESERVATION_NO_PAYMENT` int(11) DEFAULT NULL COMMENT '预约挂号未支付数',
	  `RESERVATION_REFUND` int(11) DEFAULT NULL COMMENT '预约挂号已退费数',
	  `DUTY_PAYMENT` int(11) DEFAULT NULL COMMENT '当班挂号已支付数',
	  `DUTY_NO_PAYMENT` int(11) DEFAULT NULL COMMENT '当班挂号未支付数',
	  `DUTY_REFUND` int(11) DEFAULT NULL COMMENT '当班挂号已退费数',
	  `REG_PAY_FEE` double(20,0) DEFAULT NULL,
	  `REG_REFUND_FEE` double(20,0) DEFAULT NULL,
	  `BIZ_MODE` int(11) DEFAULT NULL,
	  PRIMARY KEY (`ID`)
	) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC
	
	
	select 
	sum(CASE when payType in(2,6,7,8,10,11,18,19,20) and registerType = 1 then counts else 0 END) as DUTY_PAYMENT, 
	sum(CASE when payType in(-1,-2,0,1,3,5,9,17) and registerType = 1 then counts else 0 END) as DUTY_NO_PAYMENT, 
	sum(CASE when paytype in(4,12,13,14,15,16,21,98) and registerType = 1 then counts else 0 END) DIV 2 as DUTY_REFUND, 
	sum(CASE when payType in(2,6,7,8,10,11,18,19,20) and registerType = 2 then counts else 0 END) as RESERVATION_PAYMENT, 
	sum(CASE when payType in(-1,-2,0,1,3,5,9,17) and registerType = 2 then counts else 0 END) as RESERVATION_NO_PAYMENT, 
	sum(CASE when paytype in(4,12,13,14,15,16,21,98) and registerType = 2 then counts else 0 END) DIV 2 as RESERVATION_REFUND, 
	
	sum(CASE when payType in(2,6,7,8,10,11,18,19,20) then totalFees else 0 END) as REG_PAY_FEE, 
	sum(CASE when paytype in(4,12,13,14,15,16,21,98) then totalFees else 0 END) DIV 2 as REG_REFUND_FEE, 
	
	createTime
					
	from (
					
	
		select 
		count(1) as counts, 
		sum(totalFee) as totalFees, 
		DATE_FORMAT(registerTime,'%Y-%m-%d') as createTime, 
		payType, 
		tb_register_log.type as registerType 
		from tb_register_log 
		LEFT JOIN tb_order 
		on tb_register_log.refOrderNo = tb_order.orderNo 
		where tb_register_log.hospitalId = 24 
		GROUP BY DATE_FORMAT(registerTime,'%Y-%m-%d'), payType, tb_register_log.type
					
	
	) a  GROUP BY createTime desc
	
 *
 */
public class RegisterStatisticalTask {

	public static Logger logger = LoggerFactory.getLogger(RegisterStatisticalTask.class);
	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public RegisterStatisticalTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void startUp() {
		// TODO Auto-generated method stub
		long count = idGen.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次挂号统计数据（按日）处理开始....................");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		// TODO Auto-generated method stub
		DepositStatisticalCollector collector = new DepositStatisticalCollector();
		collector.start();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次挂号统计数据（按日）处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

}
