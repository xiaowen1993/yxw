package com.yxw.solr.vo.register;

import com.yxw.solr.constants.PlatformConstant;
import com.yxw.solr.vo.BaseRequest;

public class RegDeptStatsRequest extends BaseRequest {

	private static final long serialVersionUID = 7326501581990685252L;

	/**
	 * 科室名称
	 */
	private String deptName;
	
	/**
	 * 科室代码
	 */
	private String deptCode;
	
	/**
	 * 统计日期
	 */
	private String statsDate;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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
