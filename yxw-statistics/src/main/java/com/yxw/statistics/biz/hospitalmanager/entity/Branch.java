package com.yxw.statistics.biz.hospitalmanager.entity;

import com.yxw.base.entity.BaseEntity;

public class Branch extends BaseEntity {

	private static final long serialVersionUID = 5459955551187042573L;
	
	private String name;
	
	private String code;
	
	private String hospitalId;

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

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

}
