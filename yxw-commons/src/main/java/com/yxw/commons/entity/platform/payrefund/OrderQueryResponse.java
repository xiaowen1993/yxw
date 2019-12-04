package com.yxw.commons.entity.platform.payrefund;

import java.io.Serializable;

public class OrderQueryResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2048033258264177456L;

	protected String resultCode;

	protected String resultMsg;

	/**
	 * 当前交易状态
	 */
	protected String tradeState;

	protected String openId;

	protected String totalFee;

	protected String agtOrderNo;

	protected String orderNo;
	
	/**
	 * 支付完成时间|交易付款时间
	 * 统一格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * 原始字段：
	 * wechat:   time_end（yyyyMMddHHmmss）
	 * alipay:   send_pay_date（yyyy-MM-dd HH:mm:ss）
	 * unionpay: txnTime（yyyyMMddHHmmss）
	 */
	protected String tradeTime;

	/**
	 * 第三方退费订单号
	 */
	protected String agtRefundOrderNo;

	/**
	 * YXW 退费订单号
	 */
	protected String refundOrderNo;
	
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getAgtOrderNo() {
		return agtOrderNo;
	}

	public void setAgtOrderNo(String agtOrderNo) {
		this.agtOrderNo = agtOrderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getAgtRefundOrderNo() {
		return agtRefundOrderNo;
	}

	public void setAgtRefundOrderNo(String agtRefundOrderNo) {
		this.agtRefundOrderNo = agtRefundOrderNo;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public OrderQueryResponse() {
		super();
	}

	public OrderQueryResponse(String resultCode, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

}
