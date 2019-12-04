package com.yxw.vo.attribution;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class AttributionStats implements Serializable {

	private static final long serialVersionUID = 5324181573180277095L;

	/**
	 * id
	 */
	@Field
	private String id;
	
	/**
	 * 省
	 */
	@Field
	private String province;
	
	/**
	 * 市
	 */
	@Field
	private String city;
	
	/**
	 * 城市
	 */
	@Field
	private String areaName;
	
	/**
	 * 月份
	 */
	@Field
	private String thisMonth;
	
	/**
	 * 本月新增数量
	 */
	@Field
	private long thisMonthIncCount;
	
	/**
	 * 累计数量
	 */
	@Field
	private long cumulateCount;
	
	/**
	 * 经度
	 */
	@Field
	private String longitude = "0";
	
	/**
	 * 纬度
	 */
	@Field
	private String latitude = "0";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getThisMonth() {
		return thisMonth;
	}

	public void setThisMonth(String thisMonth) {
		this.thisMonth = thisMonth;
	}

	public long getThisMonthIncCount() {
		return thisMonthIncCount;
	}

	public void setThisMonthIncCount(long thisMonthIncCount) {
		this.thisMonthIncCount = thisMonthIncCount;
	}

	public long getCumulateCount() {
		return cumulateCount;
	}

	public void setCumulateCount(long cumulateCount) {
		this.cumulateCount = cumulateCount;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}
