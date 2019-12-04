package com.yxw.biz.medicalcard.service.impl;

import org.junit.Test;

import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.task.collector.StatsCardCollector;

public class MedicalcardServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void test() {
		StatsCardCollector collector = new StatsCardCollector();
		collector.startUp();
	}

}
