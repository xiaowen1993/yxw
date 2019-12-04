package com.yxw.solr.outside.service.impl;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.order.service.OrderService;
import com.yxw.solr.constants.ResultConstant;
import com.yxw.solr.outside.service.YxwOrderService;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.order.OrderInfoRequest;
import com.yxw.solr.vo.order.OrderStatsRequest;

public class YxwOrderServiceImpl implements YxwOrderService {

	private OrderService orderService = SpringContextHolder.getBean(OrderService.class);

	@Override
	public String getStatsInfos(OrderStatsRequest request) {
		// 检测
		YxwResponse responseVo = orderService.getStatsInfos(request);
		return JSON.toJSONString(responseVo);
	}

	@Override
	public String getPKIInfos(OrderStatsRequest request) {
		// 检测 医院、日期区间
		YxwResponse vo = new YxwResponse();
		if (StringUtils.isBlank(request.getHospitalCode())) {
			vo.setResultCode(ResultConstant.RESULT_CODE_FAIL);
			vo.setResultMessage(ResultConstant.NEED_HOSPITALCODE);
		} else if (StringUtils.isBlank(request.getBeginDate()) || StringUtils.isBlank(request.getEndDate())) {
			vo.setResultCode(ResultConstant.RESULT_CODE_FAIL);
			vo.setResultMessage(ResultConstant.NEED_RANGE_DATE);
		} else {
			vo = orderService.getKPIStatsInfo(request);
		}
		return JSON.toJSONString(vo);
	}

	@Override
	public String findOrders(OrderInfoRequest request) {
		// 检测
		YxwResponse vo = new YxwResponse();
		if (StringUtils.isBlank(request.getHospitalCode())) {
			vo.setResultCode(ResultConstant.RESULT_CODE_FAIL);
			vo.setResultMessage(ResultConstant.NEED_HOSPITALCODE);
		} else {
			vo = orderService.findOrders(request);
		}

		return JSON.toJSONString(vo);
	}

	@Override
	public String statsDailyInfo(OrderStatsRequest request) {
		// 检测
		return orderService.statsInfoForDayWithinPlatform(request);
	}

	@Override
	public String searchOrders(OrderInfoRequest request) {
		// 检测
		YxwResponse vo = new YxwResponse();
		if (request.getPlatform() == -1) {
			vo.setResultCode(ResultConstant.RESULT_CODE_FAIL);
			vo.setResultMessage(ResultConstant.NEED_PLATFORM);
		} else if (StringUtils.isBlank(request.getHospitalCode())) {
			vo.setResultCode(ResultConstant.RESULT_CODE_FAIL);
			vo.setResultMessage(ResultConstant.NEED_HOSPITALCODE);
		} else if (StringUtils.isBlank(request.getOrderNo())) {
			vo.setResultCode(ResultConstant.RESULT_CODE_FAIL);
			vo.setResultMessage(ResultConstant.NEED_ORDER_NO);
		} else {
			vo = orderService.searchOrders(request);
		}

		return JSON.toJSONString(vo);
	}

}
