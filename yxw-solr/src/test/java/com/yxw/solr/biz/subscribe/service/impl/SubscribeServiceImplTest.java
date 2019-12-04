package com.yxw.solr.biz.subscribe.service.impl;

import org.junit.Test;

import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.solr.task.collector.Collector;
import com.yxw.solr.task.collector.StatsSubscribeCollector;

public class SubscribeServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void test() {
		Collector collector = new StatsSubscribeCollector();
		collector.startUp();
	}

}
