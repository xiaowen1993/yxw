package com.yxw.vo;

import java.io.Serializable;

public class AreaHospitalInfosVo implements Serializable {

	private static final long serialVersionUID = 7062916101422339553L;

	private Integer hospitalCount;
	
	private String areaCode;
	
	private String areaName;
	
	public Integer getHospitalCount() {
		return hospitalCount;
	}

	public void setHospitalCount(Integer hospitalCount) {
		this.hospitalCount = hospitalCount;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
