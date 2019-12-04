package com.yxw.commons.entity.platform.payrefund;

public class AlipayRefundResponse extends RefundResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1708596206636120988L;

	/**
	 * 本次退款是否发生了资金变化
	 * Y|N
	 */
	private String fundChange;

	/**
	 * 买家在支付宝的用户ID（支付宝的openId）
	 */
	private String buyerUserId;

	/**
	 * 用户的登录ID（用户登录账号，有脱敏）
	 */
	private String buyerLogonId;

	public String getFundChange() {
		return fundChange;
	}

	public void setFundChange(String fundChange) {
		this.fundChange = fundChange;
	}

	public String getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public String getBuyerLogonId() {
		return buyerLogonId;
	}

	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}

	public AlipayRefundResponse() {
		super();
	}

	public AlipayRefundResponse(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}

}
