package com.yxw.stats.entity.stats;

import java.io.Serializable;

public class YxDataSysRegStatistical implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5445091099648274134L;

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

	private Integer reservationPaymentWechat;

	private Integer reservationNoPaymentWechat;

	private Integer reservationRefundWechat;

	private Integer dutyPaymentWechat;

	private Integer dutyNoPaymentWechat;

	private Integer dutyRefundWechat;

	private Double regPayFeeWechat;

	private Double regRefundFeeWechat;

	private Integer reservationPaymentAlipay;

	private Integer reservationNoPaymentAlipay;

	private Integer reservationRefundAlipay;

	private Integer dutyPaymentAlipay;

	private Integer dutyNoPaymentAlipay;

	private Integer dutyRefundAlipay;

	private Double regPayFeeAlipay;

	private Double regRefundFeeAlipay;

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

	public Integer getReservationPaymentWechat() {
		return reservationPaymentWechat;
	}

	public void setReservationPaymentWechat(Integer reservationPaymentWechat) {
		this.reservationPaymentWechat = reservationPaymentWechat;
	}

	public Integer getReservationNoPaymentWechat() {
		return reservationNoPaymentWechat;
	}

	public void setReservationNoPaymentWechat(Integer reservationNoPaymentWechat) {
		this.reservationNoPaymentWechat = reservationNoPaymentWechat;
	}

	public Integer getReservationRefundWechat() {
		return reservationRefundWechat;
	}

	public void setReservationRefundWechat(Integer reservationRefundWechat) {
		this.reservationRefundWechat = reservationRefundWechat;
	}

	public Integer getDutyPaymentWechat() {
		return dutyPaymentWechat;
	}

	public void setDutyPaymentWechat(Integer dutyPaymentWechat) {
		this.dutyPaymentWechat = dutyPaymentWechat;
	}

	public Integer getDutyNoPaymentWechat() {
		return dutyNoPaymentWechat;
	}

	public void setDutyNoPaymentWechat(Integer dutyNoPaymentWechat) {
		this.dutyNoPaymentWechat = dutyNoPaymentWechat;
	}

	public Integer getDutyRefundWechat() {
		return dutyRefundWechat;
	}

	public void setDutyRefundWechat(Integer dutyRefundWechat) {
		this.dutyRefundWechat = dutyRefundWechat;
	}

	public Double getRegPayFeeWechat() {
		return regPayFeeWechat;
	}

	public void setRegPayFeeWechat(Double regPayFeeWechat) {
		this.regPayFeeWechat = regPayFeeWechat;
	}

	public Double getRegRefundFeeWechat() {
		return regRefundFeeWechat;
	}

	public void setRegRefundFeeWechat(Double regRefundFeeWechat) {
		this.regRefundFeeWechat = regRefundFeeWechat;
	}

	public Integer getReservationPaymentAlipay() {
		return reservationPaymentAlipay;
	}

	public void setReservationPaymentAlipay(Integer reservationPaymentAlipay) {
		this.reservationPaymentAlipay = reservationPaymentAlipay;
	}

	public Integer getReservationNoPaymentAlipay() {
		return reservationNoPaymentAlipay;
	}

	public void setReservationNoPaymentAlipay(Integer reservationNoPaymentAlipay) {
		this.reservationNoPaymentAlipay = reservationNoPaymentAlipay;
	}

	public Integer getReservationRefundAlipay() {
		return reservationRefundAlipay;
	}

	public void setReservationRefundAlipay(Integer reservationRefundAlipay) {
		this.reservationRefundAlipay = reservationRefundAlipay;
	}

	public Integer getDutyPaymentAlipay() {
		return dutyPaymentAlipay;
	}

	public void setDutyPaymentAlipay(Integer dutyPaymentAlipay) {
		this.dutyPaymentAlipay = dutyPaymentAlipay;
	}

	public Integer getDutyNoPaymentAlipay() {
		return dutyNoPaymentAlipay;
	}

	public void setDutyNoPaymentAlipay(Integer dutyNoPaymentAlipay) {
		this.dutyNoPaymentAlipay = dutyNoPaymentAlipay;
	}

	public Integer getDutyRefundAlipay() {
		return dutyRefundAlipay;
	}

	public void setDutyRefundAlipay(Integer dutyRefundAlipay) {
		this.dutyRefundAlipay = dutyRefundAlipay;
	}

	public Double getRegPayFeeAlipay() {
		return regPayFeeAlipay;
	}

	public void setRegPayFeeAlipay(Double regPayFeeAlipay) {
		this.regPayFeeAlipay = regPayFeeAlipay;
	}

	public Double getRegRefundFeeAlipay() {
		return regRefundFeeAlipay;
	}

	public void setRegRefundFeeAlipay(Double regRefundFeeAlipay) {
		this.regRefundFeeAlipay = regRefundFeeAlipay;
	}
}