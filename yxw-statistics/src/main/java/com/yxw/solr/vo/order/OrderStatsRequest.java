package com.yxw.solr.vo.order;

import com.yxw.solr.vo.BaseRequest;

public class OrderStatsRequest extends BaseRequest {

	private static final long serialVersionUID = -467573902879335463L;
	
	/**
	 * 单独统计某一天的日期
	 */
	private String statsDate;
	
	public String getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
	}
}
