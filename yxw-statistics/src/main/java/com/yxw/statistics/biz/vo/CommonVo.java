package com.yxw.statistics.biz.vo;

import java.io.Serializable;

import com.yxw.statistics.biz.constants.CommonConstant;

public class CommonVo implements Serializable {

	private static final long serialVersionUID = -6910551093011127043L;

	private Integer bizType;
	
	private String bizTitle;
	
	private String hospitalName;
	
	private String hospitalCode;
	
	public Integer getBizType() {
		return bizType == null ? -1 : bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBizTitle() {
		if (CommonConstant.bizTypes.containsKey(this.getBizType())) {
			bizTitle = CommonConstant.bizTypes.get(this.getBizType());
		} else {
			bizTitle = "未定义！";
		}
		
		return bizTitle;
	}

	public void setBizTitle(String bizTitle) {
		this.bizTitle = bizTitle;
	}
}
