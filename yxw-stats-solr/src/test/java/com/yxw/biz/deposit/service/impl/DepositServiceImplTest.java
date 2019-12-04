package com.yxw.biz.deposit.service.impl;

import org.junit.Test;

import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.task.collector.Collector;
import com.yxw.task.collector.StatsDepositCollector;

public class DepositServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void test() {
		Collector collector = new StatsDepositCollector();
		collector.startUp();
	}

}
