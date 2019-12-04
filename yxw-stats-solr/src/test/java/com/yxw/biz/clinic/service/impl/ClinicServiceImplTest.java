package com.yxw.biz.clinic.service.impl;

import org.junit.Test;

import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.task.collector.Collector;
import com.yxw.task.collector.StatsClinicCollector;

public class ClinicServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void test() {
		Collector collector = new StatsClinicCollector();
		collector.startUp();
	}

}
