package com.yxw.solr.outside.service;

import com.yxw.solr.vo.register.RegDeptStatsRequest;

public interface YxwRegDeptService {
	/**
	 * 获取科室订单统计
	 * @param request
	 * @return
	 */
	public String getStatsInfos(RegDeptStatsRequest request);
	
	/**
	 * 进行某天的科室订单统计
	 * @param request
	 * @return
	 */
	public String statsDailyInfo(RegDeptStatsRequest request);
}
