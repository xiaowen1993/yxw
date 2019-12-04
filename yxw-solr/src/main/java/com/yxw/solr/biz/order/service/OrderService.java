package com.yxw.solr.biz.order.service;

import java.util.List;
import java.util.Map;

import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.YxwResult;
import com.yxw.solr.vo.order.OrderInfoRequest;
import com.yxw.solr.vo.order.OrderStatsRequest;

public interface OrderService {
	/**
	 * 查询挂号订单
	 * @param request
	 * @return
	 */
	public YxwResponse findOrders(OrderInfoRequest request);
	
	/**
	 * 查询挂号订单
	 * @param request
	 * @return
	 */
	public YxwResponse searchOrders(OrderInfoRequest request);
	
	/**
	 * 统计之前一段时间的整体信息  -- 需在其它业务的无关分院，无关平台统计完成后，再进行统计
	 * @param requst
	 * @return
	 */
	public String statsInfosWithinPlatform(OrderStatsRequest request);
	
	public Map<String, Object> rebuildWithinPlatform(OrderStatsRequest request);
	
	/**
	 * 统计之前某一天的整体信息   -- 需在其它业务的无关分院，无关平台统计完成后，再进行统计
	 * ------- 无关平台，无关分院
	 * @param requst
	 * @return
	 */
	public String statsInfoForDayWithinPlatform(OrderStatsRequest request);
	
	/**
	 * 统计之前一段时间的整体信息  -- 需在其它业务的无关分院，无关平台统计完成后，再进行统计
	 * @param requst
	 * @return
	 */
	public String statsInfosWithoutPlatform(OrderStatsRequest request);
	
	public Map<String, Object> rebuildWithoutPlatform(OrderStatsRequest request);
	
	/**
	 * 统计之前某一天的整体信息   -- 需在其它业务的无关分院，无关平台统计完成后，再进行统计
	 * ------- 无关平台，无关分院
	 * @param requst
	 * @return
	 */
	public String statsInfoForDayWithoutPlatform(OrderStatsRequest request);
	
	/**
	 * 获取KPI信息 --- 一段时间内的数据合计
	 * @param request
	 * @return
	 */
	public YxwResponse getKPIStatsInfo(OrderStatsRequest request);
	
	/**
	 * 获取统计信息  / 获取某日的累计信息也是一样的
	 * @param request
	 * @return
	 */
	public YxwResponse getStatsInfos(OrderStatsRequest request);
	
	/**
	 * 订单查询接口 -- 供His调用
	 * 
	 * @param request
	 * @return
	 */
	public YxwResult orderQuery(List<OrderInfoRequest> orderInfoRequests);
}
