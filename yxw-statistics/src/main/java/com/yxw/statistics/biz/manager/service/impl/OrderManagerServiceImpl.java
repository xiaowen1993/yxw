package com.yxw.statistics.biz.manager.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.outside.service.YxwOrderService;
import com.yxw.solr.vo.order.OrderInfoRequest;
import com.yxw.statistics.biz.manager.service.OrderManagerService;
import com.yxw.statistics.biz.vo.OrderManagerVo;

@Service("orderManagerService")
public class OrderManagerServiceImpl implements OrderManagerService {
	
	private YxwOrderService yxwOrderService = SpringContextHolder.getBean(YxwOrderService.class);

	@Override
	public String getOrders(OrderManagerVo vo) {
		OrderInfoRequest request = new OrderInfoRequest();
		BeanUtils.copyProperties(vo, request);
		return yxwOrderService.findOrders(request);
	}

}
