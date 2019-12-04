package com.yxw.solr.vo;

import java.io.Serializable;

public class BaseRequest implements Serializable {
	
	private static final long serialVersionUID = -1867953283393080973L;

	/**
	 * 医院代码
	 */
	protected String hospitalCode;
	
	/**
	 * 分院代码 -- -1 表示全部
	 */
	protected String branchCode;
	
	/**
	 * 平台 -- -1 表示全部，这个时候需要查询多个core
	 */
	protected Integer platform;
	
	/**
	 * 分页大小
	 */
	protected Integer pageSize;
	
	/**
	 * 分页序号
	 */
	protected Integer pageIndex;
	
	/**
	 * 查询的字段
	 */
	protected String fields;
	
	protected String beginDate;
	
	protected String endDate;

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

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBranchCode() {
		if (branchCode == null || branchCode.length() == 0) {
			branchCode = "-1";
		}
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Integer getPlatform() {
		return platform == null ? -1 : platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getPageSize() {
		if (pageSize == null) {
			pageSize = 20;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		if (pageIndex == null) {
			pageIndex = 1;
		}
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public String getPlatformValue() {
		String sPlatform = "\\-1";
		
		if (this.getPlatform() != -1) {
			sPlatform = platform.toString();
		}
		
		return sPlatform;
	}
	
	public String getBranchCodeValue() {
		String sBranchCode = "\\-1";
		
		if (!this.getBranchCode().equals("-1")) {
			sBranchCode = this.getBranchCode();
		}
		
		return sBranchCode;
	}
	
}
