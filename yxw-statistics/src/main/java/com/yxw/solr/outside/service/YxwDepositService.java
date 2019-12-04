package com.yxw.solr.outside.service;

import java.util.Map;

import com.yxw.solr.vo.deposit.DepositStatsRequest;

public interface YxwDepositService {
	/**
	 * 查询订单 -- 通过给定的条件，查询订单数据，map里面必须有platform.!
	 * @param request
	 * @return
	 */
	public String findOrders(Map<String, String> map);
	
	/**
	 * 获取统计信息
	 * @param request
	 * @return
	 */
	public String getStatsInfos(DepositStatsRequest request);
	
	/**
	 * 统计单日的押金补交信息
	 * @param request
	 * @return
	 */
	public String statsDailyInfo(DepositStatsRequest request);
}
