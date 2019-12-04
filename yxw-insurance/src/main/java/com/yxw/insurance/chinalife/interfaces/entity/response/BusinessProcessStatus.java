package com.yxw.insurance.chinalife.interfaces.entity.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class BusinessProcessStatus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5859997103801181733L;

	/**
	 * 1 成功;  0 失败
	 */
	@JSONField(name = "BusinessStatus")
	private String businessStatus = "";
	@JSONField(name = "BusinessName")
	private String businessName = "";
	@JSONField(name = "BusinessMessage")
	private String businessMessage = "";

	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessMessage() {
		return businessMessage;
	}

	public void setBusinessMessage(String businessMessage) {
		this.businessMessage = businessMessage;
	}

}
