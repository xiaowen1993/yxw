package com.yxw.commons.entity.platform.app.color;

import com.yxw.base.entity.BaseEntity;

public class AppColor extends BaseEntity {

	private static final long serialVersionUID = 8114263231882165544L;

	// 平台名称
	private String appName;

	// appCode, 银联：unionpay
	private String appCode;

	// 3/6位的颜色值，带#
	private String color;

	private String payInfoViewType = "iframe";

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPayInfoViewType() {
		return payInfoViewType;
	}

	public void setPayInfoViewType(String payInfoViewType) {
		this.payInfoViewType = payInfoViewType;
	}

}
