package com.yxw.solr.outside.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.dto.outside.OrdersQueryResult;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.solr.outside.service.YxwOutsideService;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.YxwResult;
import com.yxw.solr.vo.outside.OrderParams;

public class YxwOutsideServiceImplTest extends Junit4SpringContextHolder {
	
	private Logger logger = LoggerFactory.getLogger(YxwOutsideServiceImplTest.class);

	@Test
	public void testQueryOrders() {
		YxwOutsideService service = new YxwOutsideServiceImpl();
		
//		Map<String, String> requestMap = new HashMap<>();
//		requestMap.put("appId", "wx7759862b6d224820");
//		requestMap.put("orderMode", "1");
//		requestMap.put("tradeMode", "2");
//		// requestMap.put("branchCode", "");
//		requestMap.put("startTime", "2016-07-01 00:00:00");
//		requestMap.put("endTime", "2016-07-06 00:00:00");
//		requestMap.put("isQiaoQiaoShang", "1");
		
		OrderParams orderParams = new OrderParams();
		orderParams.setAppId("wx7759862b6d224820");
		orderParams.setBranchCode("");
		orderParams.setEndTime("2016-07-06 00:00:00");
		orderParams.setIsQiaoQiaoShang(1);
		orderParams.setStartTime("2016-07-01 00:00:00");
		orderParams.setTradeMode(2);
		orderParams.setOrderMode(1);
		
		// 押金补缴，用东莞
//		Map<String, String> requestMap = new HashMap<>();
//		requestMap.put("appId", "2015081900222319");
//		requestMap.put("orderMode", "3");
//		requestMap.put("tradeMode", "0");
//		// requestMap.put("branchCode", "");
//		requestMap.put("startTime", "2016-06-01 00:00:00");
//		requestMap.put("endTime", "2016-06-20 00:00:00");
//		requestMap.put("isQiaoQiaoShang", "1");
		
		YxwResponse response = service.queryOrders(orderParams);
		logger.info(JSON.toJSONString(response));
		YxwResult result = response.getResult();
		if (result != null) {
			Object resultObj = result.getItems();
			String source = JSON.toJSONString(resultObj);
			List<OrdersQueryResult> list = JSON.parseArray(source, OrdersQueryResult.class);
			// --
			System.out.println(list.get(0).getAgtPayOrdNum());
		}
	}

	@Test
	public void testQueryRegOrders() {
		YxwOutsideService service = new YxwOutsideServiceImpl();
		
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("appId", "wx7759862b6d224820");
		requestMap.put("regType", "0");
		requestMap.put("tradeMode", "0");
		// requestMap.put("branchCode", "");
		requestMap.put("startDate", "2016-07-01 00:00:00");
		requestMap.put("endDate", "2016-07-06 00:00:00");
		requestMap.put("isQiaoQiaoShang", "1");
		
//		YxwResponse response = service.queryRegOrders(requestMap);
//		logger.info(JSON.toJSONString(response));
	}

}
