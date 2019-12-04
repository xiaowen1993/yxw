package com.yxw.vo.attribution;

import java.io.Serializable;

public class CityVo implements Serializable {

	private static final long serialVersionUID = -5966143061262934916L;

	private String province;
	
	private String city;
	
	private String areaName;
	
	private String longitude = "0";
	
	private String latitude = "0";

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
