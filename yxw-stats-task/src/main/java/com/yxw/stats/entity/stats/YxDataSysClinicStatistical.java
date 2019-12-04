package com.yxw.stats.entity.stats;

import java.io.Serializable;

public class YxDataSysClinicStatistical implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6043583584946936140L;

	private String id;

	private String date;

	private String hospitalId;

	private String branchId;

	private Integer payment;

	private Integer noPayment;

	private Integer refund;

	private Double clinicPayFee;

	private Double clinicRefundFee;

	private Double partRefund;

	private Integer bizMode;

	private Integer paymentWechat;

	private Integer noPaymentWechat;

	private Integer refundWechat;

	private Double clinicPayFeeWechat;

	private Double clinicRefundFeeWechat;

	private Double partRefundWechat;

	private Integer paymentAlipay;

	private Integer noPaymentAlipay;

	private Integer refundAlipay;

	private Double clinicPayFeeAlipay;

	private Double clinicRefundFeeAlipay;

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

	public Double getClinicPayFee() {
		return clinicPayFee;
	}

	public void setClinicPayFee(Double clinicPayFee) {
		this.clinicPayFee = clinicPayFee;
	}

	public Double getClinicRefundFee() {
		return clinicRefundFee;
	}

	public void setClinicRefundFee(Double clinicRefundFee) {
		this.clinicRefundFee = clinicRefundFee;
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

	public Double getClinicPayFeeWechat() {
		return clinicPayFeeWechat;
	}

	public void setClinicPayFeeWechat(Double clinicPayFeeWechat) {
		this.clinicPayFeeWechat = clinicPayFeeWechat;
	}

	public Double getClinicRefundFeeWechat() {
		return clinicRefundFeeWechat;
	}

	public void setClinicRefundFeeWechat(Double clinicRefundFeeWechat) {
		this.clinicRefundFeeWechat = clinicRefundFeeWechat;
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

	public Double getClinicPayFeeAlipay() {
		return clinicPayFeeAlipay;
	}

	public void setClinicPayFeeAlipay(Double clinicPayFeeAlipay) {
		this.clinicPayFeeAlipay = clinicPayFeeAlipay;
	}

	public Double getClinicRefundFeeAlipay() {
		return clinicRefundFeeAlipay;
	}

	public void setClinicRefundFeeAlipay(Double clinicRefundFeeAlipay) {
		this.clinicRefundFeeAlipay = clinicRefundFeeAlipay;
	}

	public Double getPartRefundAlipay() {
		return partRefundAlipay;
	}

	public void setPartRefundAlipay(Double partRefundAlipay) {
		this.partRefundAlipay = partRefundAlipay;
	}
}