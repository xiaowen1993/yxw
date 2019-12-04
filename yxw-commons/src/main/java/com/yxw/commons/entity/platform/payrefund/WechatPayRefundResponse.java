package com.yxw.commons.entity.platform.payrefund;

public class WechatPayRefundResponse extends RefundResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1509214657010911845L;

	/**
	 * 第三方退费订单号
	 */
	private String agtRefundOrderNo;

	public String getAgtRefundOrderNo() {
		return agtRefundOrderNo;
	}

	public void setAgtRefundOrderNo(String agtRefundOrderNo) {
		this.agtRefundOrderNo = agtRefundOrderNo;
	}

	public WechatPayRefundResponse() {
		super();
	}

	public WechatPayRefundResponse(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}

}
