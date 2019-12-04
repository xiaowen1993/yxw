package com.yxw.platform.sdk.alipay.entity;

/**
 * 复杂按钮（父按钮）
 * 
 */
public class ComplexButtonAlipay extends ButtonAlipay {
	private ButtonAlipay[] subButton;

	public ButtonAlipay[] getSubButton() {
		return subButton;
	}

	public void setSubButton(ButtonAlipay[] subButton) {
		this.subButton = subButton;
	}

}