package com.yxw.stats.entity.project;

import java.io.Serializable;

public class SysRegStatistical implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7325977326705150127L;

	private String id;

	private String date;

	private String hospitalId;

	private String branchId;

	private Integer reservationPayment;

	private Integer reservationNoPayment;

	private Integer reservationRefund;

	private Integer dutyPayment;

	private Integer dutyNoPayment;

	private Integer dutyRefund;

	private Double regPayFee;

	private Double regRefundFee;

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

	public Integer getReservationPayment() {
		return reservationPayment;
	}

	public void setReservationPayment(Integer reservationPayment) {
		this.reservationPayment = reservationPayment;
	}

	public Integer getReservationNoPayment() {
		return reservationNoPayment;
	}

	public void setReservationNoPayment(Integer reservationNoPayment) {
		this.reservationNoPayment = reservationNoPayment;
	}

	public Integer getReservationRefund() {
		return reservationRefund;
	}

	public void setReservationRefund(Integer reservationRefund) {
		this.reservationRefund = reservationRefund;
	}

	public Integer getDutyPayment() {
		return dutyPayment;
	}

	public void setDutyPayment(Integer dutyPayment) {
		this.dutyPayment = dutyPayment;
	}

	public Integer getDutyNoPayment() {
		return dutyNoPayment;
	}

	public void setDutyNoPayment(Integer dutyNoPayment) {
		this.dutyNoPayment = dutyNoPayment;
	}

	public Integer getDutyRefund() {
		return dutyRefund;
	}

	public void setDutyRefund(Integer dutyRefund) {
		this.dutyRefund = dutyRefund;
	}

	public Double getRegPayFee() {
		return regPayFee;
	}

	public void setRegPayFee(Double regPayFee) {
		this.regPayFee = regPayFee;
	}

	public Double getRegRefundFee() {
		return regRefundFee;
	}

	public void setRegRefundFee(Double regRefundFee) {
		this.regRefundFee = regRefundFee;
	}

	public Integer getBizMode() {
		return bizMode;
	}

	public void setBizMode(Integer bizMode) {
		this.bizMode = bizMode;
	}
}