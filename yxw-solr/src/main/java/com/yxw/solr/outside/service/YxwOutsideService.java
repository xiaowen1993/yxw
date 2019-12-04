package com.yxw.solr.outside.service;

import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.outside.OrderParams;
import com.yxw.solr.vo.outside.RegOrderParams;

public interface YxwOutsideService {
	/**
	 * 订单数据查询
	 * @param requestMap
	 * @return
	 */
	public YxwResponse queryOrders(OrderParams orderParams);
	
	/**
	 * 挂号数据查询
	 * @param requestMap
	 * @return
	 */
	public YxwResponse queryRegOrders(RegOrderParams regOrderParams);
}
