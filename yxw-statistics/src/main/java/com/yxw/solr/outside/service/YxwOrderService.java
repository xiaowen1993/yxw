package com.yxw.solr.outside.service;

import com.yxw.solr.vo.order.OrderInfoRequest;
import com.yxw.solr.vo.order.OrderStatsRequest;

public interface YxwOrderService {
	/**
	 * 统计信息查询
	 * 1、订单统计查询
	 * 2、整体统计-详细数据查询
	 * 3、整体统计-关键指标详解
	 * 4、订单统计 -- 累计订单查询（单查当天的一天数据）
	 * @param request
	 * @return
	 */
	public String getStatsInfos(OrderStatsRequest request);

	/**
	 * 关键指标统计 -- 把一段时间内的金额数据相加
	 * @param request
	 * @return
	 */
	public String getPKIInfos(OrderStatsRequest request);
	
	/**
	 * 订单信息查询
	 * @param request
	 * @return
	 */
	public String findOrders(OrderInfoRequest request);
	
	/**
	 * 订单信息查询
	 * @param request
	 * @return
	 */
	public String searchOrders(OrderInfoRequest request);
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public String statsDailyInfo(OrderStatsRequest request);

}
