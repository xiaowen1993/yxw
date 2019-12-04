package com.yxw.vo.attribution;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class AttributionVo implements Serializable {

	private static final long serialVersionUID = 4769737043360347344L;

	/**
	 * id
	 */
	@Field
	private String id;
	
	/**
	 * 省份
	 */
	@Field
	private String province;
	
	/**
	 * 城市
	 */
	@Field
	private String city;
	
	/**
	 * 地区（province+city）
	 */
	@Field
	private String areaName;
	
	/**
	 * 统计日期
	 */
	@Field
	private String statsDate;
	
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
	
	/**
	 * 数量
	 */
	@Field
	private long count;

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

	public String getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
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

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
}
