package com.yxw.biz.subscribe.service.impl;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yxw.biz.subscribe.service.SubscribeService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.task.collector.Collector;
import com.yxw.task.collector.StatsSubscribeCollector;
import com.yxw.vo.subscribe.SubscribeStatsRequest;

public class SubscribeServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void test() {
		Collector collector = new StatsSubscribeCollector();
		collector.startUp();
	}
	
	@Test
	public void getStatsInfos() {
		SubscribeService service = SpringContextHolder.getBean(SubscribeService.class);
		SubscribeStatsRequest request = new SubscribeStatsRequest();
		// 根据areaCode - hospitalCode - 日期区间来拿。 如果没有区间，则返回最后一个
		request.setHospitalCode("");
		request.setAreaCode("/440000/440100");
		request.setBeginDate("2016-07");
		request.setEndDate("2016-11");
		
		// areaCode: xxx AND hospitalCode: XXX AND thisMonth:[XXX TO XXX]
		System.err.println(JSON.toJSONString(service.getStatsInfos(request)));
	}

}
