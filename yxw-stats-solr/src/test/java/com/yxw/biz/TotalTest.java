package com.yxw.biz;

import org.junit.Test;

import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.task.collector.Collector;
import com.yxw.task.collector.StatsAttributionCollector;
import com.yxw.task.collector.StatsCardCollector;
import com.yxw.task.collector.StatsClinicCollector;
import com.yxw.task.collector.StatsDepositCollector;
import com.yxw.task.collector.StatsRegCollector;
import com.yxw.task.collector.StatsSubscribeCollector;

public class TotalTest extends Junit4SpringContextHolder {
	@Test
	public void testTotalStats() {
		// 关注数
		Collector subscribeCollector = new StatsSubscribeCollector();
		subscribeCollector.startUp();
		
		// 绑卡
		Collector cardCollector = new StatsCardCollector();
		cardCollector.startUp();
		
		// 号码归属地
		Collector attributionCollector = new StatsAttributionCollector();
		attributionCollector.startUp();
		
		// 挂号
		Collector regCollector = new StatsRegCollector();
		regCollector.startUp();
		// 门诊
		Collector clinicCollector = new StatsClinicCollector();
		clinicCollector.startUp();
		// 押金
		Collector depositCollector = new StatsDepositCollector();
		depositCollector.startUp();
	}
}
