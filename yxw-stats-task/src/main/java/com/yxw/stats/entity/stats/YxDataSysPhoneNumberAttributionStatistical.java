package com.yxw.stats.entity.stats;

import java.io.Serializable;

public class YxDataSysPhoneNumberAttributionStatistical implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7132808297967054554L;

	private String id;

	private String date;

	private String province;

	private String city;

	private Integer count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date == null ? null : date.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}