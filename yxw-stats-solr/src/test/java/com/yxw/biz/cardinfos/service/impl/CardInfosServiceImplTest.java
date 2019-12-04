package com.yxw.biz.cardinfos.service.impl;

import org.junit.Test;

import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.task.collector.Collector;
import com.yxw.task.collector.StatsCardInfosCollector;

public class CardInfosServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void test() {
		Collector collector = new StatsCardInfosCollector();
		collector.startUp();
	}
	
}
