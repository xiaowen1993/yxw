package com.yxw.platform.sdk.alipay.entity;

/***
 * 跳转URL菜单按钮
 * */
public class ViewButtonAlipay extends ButtonAlipay {

	// 当actionType为link时，该参数为详细链接；当actionType为out时，该参数为用户自定义参数；当actionType为tel时，该参数为电话号码。该参数最长255个字符，不允许冒号等特殊字符
	private String actionParam;

	// actionType 菜单类型，out:事件型菜单；link:链接型菜单；tel:点击拨打电话
	private String actionType;

	public String getActionParam() {
		return actionParam;
	}

	public void setActionParam(String actionParam) {
		this.actionParam = actionParam;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

}
