package com.yxw.commons.entity.platform.payrefund;

import java.io.Serializable;

public class OrderQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8807141394462886642L;

	/**
	 * 医院代码/药店（一切跟支付配置信息关联的唯一code）
	 */
	protected String code;

	/**
	 * 支付方式-val
	 */
	protected String tradeMode;

	// agtOrderNo | orderNo 二选一

	/**
	 * 第三方订单号
	 */
	protected String agtOrderNo;

	/**
	 * YXW 订单号
	 */
	protected String orderNo;

	/**
	 * 第三方退费订单号
	 */
	protected String agtRefundOrderNo;

	/**
	 * YXW 退费订单号
	 */
	protected String refundOrderNo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
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

}
