package com.yxw.biz.attribution.service.impl;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yxw.biz.attribution.service.AttributionService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.task.collector.Collector;
import com.yxw.task.collector.StatsAttributionCollector;

public class AttributionServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void test() {
		Collector collector = new StatsAttributionCollector();
		collector.startUp();
	}
	
	@Test
	public void testGetCities() {
		AttributionService attributionService = SpringContextHolder.getBean(AttributionService.class);
		System.out.println(JSON.toJSONString(attributionService.getCities()));
	}
	

}
