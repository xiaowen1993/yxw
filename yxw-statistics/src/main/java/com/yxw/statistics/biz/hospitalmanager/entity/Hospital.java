package com.yxw.statistics.biz.hospitalmanager.entity;

import com.yxw.base.entity.BaseEntity;

public class Hospital extends BaseEntity {

	private static final long serialVersionUID = -6539571135501654859L;

	private String name;

	private String code;

	private String areaCode;

	private String areaName;
	
	private Integer state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
