package com.yxw.statistics.biz.stats.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.outside.service.YxwOrderService;
import com.yxw.solr.vo.order.OrderStatsRequest;
import com.yxw.statistics.biz.stats.service.OrderStatsService;
import com.yxw.statistics.biz.vo.StatsVo;

@Service(value="orderStatsService")
public class OrderStatsServiceImpl implements OrderStatsService {
	
	private Logger logger = LoggerFactory.getLogger(OrderStatsService.class);
	
	private YxwOrderService yxwService = SpringContextHolder.getBean(YxwOrderService.class);

	@Override
	public String getKPIInfos(StatsVo statsVo) {
		String result = null;
		OrderStatsRequest request = new OrderStatsRequest();
		BeanUtils.copyProperties(statsVo, request);
		try {
			result = yxwService.getPKIInfos(request);
		} catch (Exception e) {
			logger.error("getKPIInfos error. errorMsg: {}. cause: {}.", new Object[]{e.getMessage(), e.getCause()});
		}
		
		return result;
	}

	@Override
	public String getStatsInfo(StatsVo statsVo) {
		String result = null;
		OrderStatsRequest request = new OrderStatsRequest();
		BeanUtils.copyProperties(statsVo, request);
		try {
			result = yxwService.getStatsInfos(request);
		} catch (Exception e) {
			logger.error("getStatsInfo error. errorMsg: {}. cause: {}.", new Object[]{e.getMessage(), e.getCause()});
		}
		
		return result;
	}

}
