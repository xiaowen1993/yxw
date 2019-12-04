package com.yxw.commons.entity.platform.payrefund;

import java.io.Serializable;

public class WechatPay extends Pay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1787003794660738083L;

	/**
	 * 实际支付的openId
	 */
	private String openId;

	/**
	 * 被扫码支付时用到
	 * 
	 * 扫码支付授权码，设备读取用户微信中的条码或者二维码信息
		（注：用户刷卡条形码规则：18位纯数字，以10、11、12、13、14、15开头）
	 */
	private String authCode;

	/**
	 * 是否是第三方获取openId
	 */
	private Boolean componentOauth2;

	public WechatPay() {
		super();
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Boolean getComponentOauth2() {
		return componentOauth2;
	}

	public void setComponentOauth2(Boolean componentOauth2) {
		this.componentOauth2 = componentOauth2;
	}

}
