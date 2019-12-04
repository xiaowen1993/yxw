package com.yxw.solr.biz.order.service.impl;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.solr.biz.order.service.OrderService;
import com.yxw.solr.task.collector.StatsOrdersCollector;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.order.OrderInfoRequest;
import com.yxw.solr.vo.order.OrderStatsRequest;

public class OrderServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void testFindOrder() {
		OrderService orderService = SpringContextHolder.getBean(OrderService.class);
		OrderInfoRequest request = new OrderInfoRequest();
		request.setPatientName("张小兰");
		request.setPlatform(1);
//		 request.setOrderNo("Y42015120915414028211");
//		request.setPatientMobile("13410156425");
//		request.setEndTime("2016-05-11");
		YxwResponse vo = orderService.findOrders(request);
		System.out.println(JSON.toJSONString(vo));
	}
	
	@Test
	public void testGetStatsInfos() {
		OrderService orderService = SpringContextHolder.getBean(OrderService.class);
		OrderStatsRequest request = new OrderStatsRequest();
		request.setHospitalCode("dgsrmyy");
		request.setPlatform(1);
		YxwResponse vo = orderService.getStatsInfos(request);
		System.out.println(JSON.toJSON(vo));
	}
	
	@Test
	public void testGetKPIStats() {
		OrderService orderService = SpringContextHolder.getBean(OrderService.class);
		OrderStatsRequest request = new OrderStatsRequest();
		request.setHospitalCode("dgsrmyy");
		request.setBeginDate("2016-05-22");
		request.setEndDate("2016-05-24");
		YxwResponse vo = orderService.getKPIStatsInfo(request);
		System.out.println(JSON.toJSON(vo));
	}
	
	@Test
	public void doStats() {
		StatsOrdersCollector collector = new StatsOrdersCollector();
		collector.startUp();
	}

}
