package com.yxw.solr.vo.register;

import com.yxw.solr.constants.PlatformConstant;
import com.yxw.solr.vo.BaseRequest;

public class RegStatsRequest extends BaseRequest {

	private static final long serialVersionUID = -4659856900628400754L;

	/**
	 * 统计开始日期
	 */
	private String beginDate;
	
	/**
	 * 统计结束日期
	 */
	private String endDate;
	
	/**
	 * 单独统计某一天的日期
	 */
	private String statsDate;

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
	}
	
	public String getCoreName() {
		return platform != -1 ? PlatformConstant.platformRegisterMap.get(platform) : "";
	}
	
}
