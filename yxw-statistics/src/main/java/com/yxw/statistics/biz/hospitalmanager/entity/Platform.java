package com.yxw.statistics.biz.hospitalmanager.entity;

import com.yxw.base.entity.BaseEntity;

public class Platform extends BaseEntity {

	private static final long serialVersionUID = -3018551809720351301L;

	private String name;
	
	private String code;
	
	private String shortName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
