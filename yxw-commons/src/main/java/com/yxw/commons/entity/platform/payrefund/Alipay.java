package com.yxw.commons.entity.platform.payrefund;

import java.io.Serializable;

public class Alipay extends Pay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5223925719299502328L;

	/**
	 * 中断返回地址
	 */
	private String merchantUrl;

	public Alipay() {
		super();
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

}
