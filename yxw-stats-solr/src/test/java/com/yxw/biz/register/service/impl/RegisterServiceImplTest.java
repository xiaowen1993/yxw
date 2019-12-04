package com.yxw.biz.register.service.impl;

import org.junit.Test;

import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.task.collector.Collector;
import com.yxw.task.collector.StatsRegCollector;

public class RegisterServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void test() {
		Collector collector = new StatsRegCollector();
		collector.startUp();
	}

}
