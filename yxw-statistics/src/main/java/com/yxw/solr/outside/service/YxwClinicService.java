package com.yxw.solr.outside.service;

import com.yxw.solr.vo.clinic.ClinicInfoRequest;
import com.yxw.solr.vo.clinic.ClinicStatsRequest;

public interface YxwClinicService {
	
	/**
	 * 获取统计信息
	 * @param request
	 * @return
	 */
	public String getStatsInfos(ClinicStatsRequest request);

	/**
	 * 提供统计某一天数据的接口 -- 指定的天及之后的数据都需要改动。
	 * @param request
	 * @return
	 */
	public String statsDailyInfo(ClinicStatsRequest request);
	
	/**
	 * 订单信息查询 -- 暂时不提供实现
	 * @param request
	 * @return
	 */
	public String findOrders(ClinicInfoRequest request);

}
