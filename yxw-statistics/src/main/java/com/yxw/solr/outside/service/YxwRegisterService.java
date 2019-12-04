package com.yxw.solr.outside.service;

import com.yxw.solr.vo.register.RegInfoRequest;
import com.yxw.solr.vo.register.RegStatsRequest;

public interface YxwRegisterService {

	/**
	 * 获取统计信息
	 * @param request
	 * @return
	 */
	public String getStatsInfos(RegStatsRequest request);

	/**
	 * 提供统计某一天数据的接口 -- 指定的天及之后的数据都需要改动。
	 * @param request
	 * @return
	 */
	public String statsDailyInfo(RegStatsRequest request);
	
	/**
	 * 订单信息查询
	 * @param request
	 * @return
	 */
	public String findOrders(RegInfoRequest request);

}
