package com.yxw.stats.entity.project;

import java.io.Serializable;

public class SysClinicStatistical implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4608759605014560269L;

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
}