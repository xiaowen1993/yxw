package com.yxw.commons.entity.platform.payrefund;

import java.io.Serializable;

public class Refund implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1787003794660738083L;

	/**
	 * 医院代码/药店（一切跟支付配置信息关联的唯一code）
	 */
	protected String code;

	/**
	 * 退费方式-val
	 */
	protected String tradeMode;

	/**
	 * 退费订单编号
	 */
	protected String refundOrderNo;

	/**
	 * 第三方支付订单号
	 */
	protected String agtOrderNo;

	/**
	 * yxw订单号
	 */
	protected String orderNo;

	/**
	 * 第三方订单金额
	 */
	protected String totalFee;
	/**
	 * 退费金额
	 */
	protected String refundFee;

	/**
	 * 退费描述信息
	 */
	protected String refundDesc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the tradeMode
	 */
	public String getTradeMode() {
		return tradeMode;
	}

	/**
	 * @param tradeMode the tradeMode to set
	 */
	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	/**
	 * @return the refundOrderNo
	 */
	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	/**
	 * @param refundOrderNo the refundOrderNo to set
	 */
	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	/**
	 * @return the agtOrderNo
	 */
	public String getAgtOrderNo() {
		return agtOrderNo;
	}

	/**
	 * @param agtOrderNo the agtOrderNo to set
	 */
	public void setAgtOrderNo(String agtOrderNo) {
		this.agtOrderNo = agtOrderNo;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the totalFee
	 */
	public String getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee the totalFee to set
	 */
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * @return the refundFee
	 */
	public String getRefundFee() {
		return refundFee;
	}

	/**
	 * @param refundFee the refundFee to set
	 */
	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	/**
	 * @return the refundDesc
	 */
	public String getRefundDesc() {
		return refundDesc;
	}

	/**
	 * @param refundDesc the refundDesc to set
	 */
	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

}
