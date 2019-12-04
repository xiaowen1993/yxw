package com.yxw.commons.entity.platform.payrefund;

public class UnionpayRefundResponse extends RefundResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7786437865871058001L;

	/**
	 * queryId
	 * 查询流水号
	 * 退货交易的交易流水号 供查询用
	 * 
	 * 可以理解为第三方退费流水号
	 */
	private String agtRefundOrderNo;

	/**
	 * origQryId
	 * 原交易查询流水号
	 * 原始消费交易的queryId
	 * 
	 * 可以理解为第三方订单号
	 */
	private String agtOrderNo;

	public String getAgtRefundOrderNo() {
		return agtRefundOrderNo;
	}

	public void setAgtRefundOrderNo(String agtRefundOrderNo) {
		this.agtRefundOrderNo = agtRefundOrderNo;
	}

	public String getAgtOrderNo() {
		return agtOrderNo;
	}

	public void setAgtOrderNo(String agtOrderNo) {
		this.agtOrderNo = agtOrderNo;
	}

	public UnionpayRefundResponse() {
		super();
	}

	public UnionpayRefundResponse(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}

}
