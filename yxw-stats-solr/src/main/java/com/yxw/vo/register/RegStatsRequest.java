package com.yxw.vo.register;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class RegStatsRequest implements Serializable {

	private static final long serialVersionUID = -3571675124149352866L;

	/**
	 * 医院代码
	 */
	private String hospitalCode;
	
	private String hospitalId;
	
	private String hospitalName;
	
	private String areaName;
	
	/**
	 * 区域代码
	 */
	private String areaCode;
	
	/**
	 * 统计年月 -- 自行做统计的时候录入
	 * 格式：YYYY-MM （需要自行截取）
	 */
	private String statsMonth;
	
	private String beginDate;
	
	private String endDate;
	
	/**
	 * 0 随意
	 * 1标准平台
	 * 2医院项目
	 */
	private Integer platform;
	
	/**
	 * 分页大小
	 */
	private Integer pageSize;
	
	/**
	 * 页码
	 */
	private Integer pageIndex;
	
	/**
	 * 查询的字段直接用逗号隔开
	 */
	private String fields;
	
	public String getHospitalCode() {
		return hospitalCode == null ? "-" : hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getStatsMonth() {
		if (StringUtils.isNotBlank(statsMonth) && statsMonth.length() >= 7) {
			statsMonth = statsMonth.substring(0, 7);
		}
		return statsMonth;
	}

	public void setStatsMonth(String statsMonth) {
		this.statsMonth = statsMonth;
	}

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

	public String getHospitalId() {
		return hospitalId == null ? "-" : hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName == null ? "-" : hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getPlatform() {
		return platform;
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

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

}
