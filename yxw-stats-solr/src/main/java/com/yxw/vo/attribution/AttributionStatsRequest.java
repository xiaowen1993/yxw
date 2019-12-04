package com.yxw.vo.attribution;

import java.io.Serializable;

public class AttributionStatsRequest implements Serializable {

	private static final long serialVersionUID = 6086051112495643641L;

	private String province;
	
	private String city;
	
	private String areaName;
	
	private String latitude;
	
	private String longitude;
	
	private String statsMonth;
	
	private String beginDate;
	
	private String endDate;
	
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
	private String fileds;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getStatsMonth() {
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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getFileds() {
		return fileds;
	}

	public void setFileds(String fileds) {
		this.fileds = fileds;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
