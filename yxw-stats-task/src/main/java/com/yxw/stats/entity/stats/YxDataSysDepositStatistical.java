package com.yxw.stats.entity.stats;

import java.io.Serializable;

public class YxDataSysDepositStatistical implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4456748749440143339L;

	private String id;

	private String date;

	private String hospitalId;

	private String branchId;

	private Integer payment;

	private Integer noPayment;

	private Integer refund;

	private Double depositPayFee;

	private Double depositRefundFee;

	private Double partRefund;

	private Integer bizMode;

	private Integer paymentWechat;

	private Integer noPaymentWechat;

	private Integer refundWechat;

	private Double depositPayFeeWechat;

	private Double depositRefundFeeWechat;

	private Double partRefundWechat;

	private Integer paymentAlipay;

	private Integer noPaymentAlipay;

	private Integer refundAlipay;

	private Double depositPayFeeAlipay;

	private Double depositRefundFeeAlipay;

	private Double partRefundAlipay;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date == null ? null : date.trim();
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId == null ? null : hospitalId.trim();
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId == null ? null : branchId.trim();
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public Integer getNoPayment() {
		return noPayment;
	}

	public void setNoPayment(Integer noPayment) {
		this.noPayment = noPayment;
	}

	public Integer getRefund() {
		return refund;
	}

	public void setRefund(Integer refund) {
		this.refund = refund;
	}

	public Double getDepositPayFee() {
		return depositPayFee;
	}

	public void setDepositPayFee(Double depositPayFee) {
		this.depositPayFee = depositPayFee;
	}

	public Double getDepositRefundFee() {
		return depositRefundFee;
	}

	public void setDepositRefundFee(Double depositRefundFee) {
		this.depositRefundFee = depositRefundFee;
	}

	public Double getPartRefund() {
		return partRefund;
	}

	public void setPartRefund(Double partRefund) {
		this.partRefund = partRefund;
	}

	public Integer getBizMode() {
		return bizMode;
	}

	public void setBizMode(Integer bizMode) {
		this.bizMode = bizMode;
	}

	public Integer getPaymentWechat() {
		return paymentWechat;
	}

	public void setPaymentWechat(Integer paymentWechat) {
		this.paymentWechat = paymentWechat;
	}

	public Integer getNoPaymentWechat() {
		return noPaymentWechat;
	}

	public void setNoPaymentWechat(Integer noPaymentWechat) {
		this.noPaymentWechat = noPaymentWechat;
	}

	public Integer getRefundWechat() {
		return refundWechat;
	}

	public void setRefundWechat(Integer refundWechat) {
		this.refundWechat = refundWechat;
	}

	public Double getDepositPayFeeWechat() {
		return depositPayFeeWechat;
	}

	public void setDepositPayFeeWechat(Double depositPayFeeWechat) {
		this.depositPayFeeWechat = depositPayFeeWechat;
	}

	public Double getDepositRefundFeeWechat() {
		return depositRefundFeeWechat;
	}

	public void setDepositRefundFeeWechat(Double depositRefundFeeWechat) {
		this.depositRefundFeeWechat = depositRefundFeeWechat;
	}

	public Double getPartRefundWechat() {
		return partRefundWechat;
	}

	public void setPartRefundWechat(Double partRefundWechat) {
		this.partRefundWechat = partRefundWechat;
	}

	public Integer getPaymentAlipay() {
		return paymentAlipay;
	}

	public void setPaymentAlipay(Integer paymentAlipay) {
		this.paymentAlipay = paymentAlipay;
	}

	public Integer getNoPaymentAlipay() {
		return noPaymentAlipay;
	}

	public void setNoPaymentAlipay(Integer noPaymentAlipay) {
		this.noPaymentAlipay = noPaymentAlipay;
	}

	public Integer getRefundAlipay() {
		return refundAlipay;
	}

	public void setRefundAlipay(Integer refundAlipay) {
		this.refundAlipay = refundAlipay;
	}

	public Double getDepositPayFeeAlipay() {
		return depositPayFeeAlipay;
	}

	public void setDepositPayFeeAlipay(Double depositPayFeeAlipay) {
		this.depositPayFeeAlipay = depositPayFeeAlipay;
	}

	public Double getDepositRefundFeeAlipay() {
		return depositRefundFeeAlipay;
	}

	public void setDepositRefundFeeAlipay(Double depositRefundFeeAlipay) {
		this.depositRefundFeeAlipay = depositRefundFeeAlipay;
	}

	public Double getPartRefundAlipay() {
		return partRefundAlipay;
	}

	public void setPartRefundAlipay(Double partRefundAlipay) {
		this.partRefundAlipay = partRefundAlipay;
	}
}