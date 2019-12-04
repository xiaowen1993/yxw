/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.stats.task.platform.callable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.entity.platform.OrderCount;
import com.yxw.stats.service.platform.OrderCountService;

public class StatisticsRegOrderCollectCall implements Callable<List<OrderCount>> {
	public static Logger logger = LoggerFactory.getLogger(StatisticsRegOrderCollectCall.class);
	private OrderCountService orderCountService = SpringContextHolder.getBean(OrderCountService.class);

	private Map<String, Object> params;

	public StatisticsRegOrderCollectCall(Map<String, Object> params) {
		super();
		this.params = params;
	}

	@Override
	public List<OrderCount> call() throws Exception {
		List<OrderCount> result = orderCountService.findRegOrderCountByDate(params);
		return result;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
