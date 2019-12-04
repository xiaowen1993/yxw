package com.yxw.vo.register;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class RegVo implements Serializable {

	private static final long serialVersionUID = -2882778468878403524L;

	@Field
	private String id;

	@Field
	private String statsDate;

	@Field
	private String hospitalId;

	@Field
	private String branchId;

	@Field
	private Integer reservationPayment = 0;

	@Field
	private Integer reservationNoPayment = 0;

	@Field
	private Integer reservationRefund = 0;

	@Field
	private Integer dutyPayment = 0;

	@Field
	private Integer dutyNoPayment = 0;

	@Field
	private Integer dutyRefund = 0;

	@Field
	private Long regPayFee = 0l;

	@Field
	private Long regRefundFee = 0l;

	@Field
	private Integer bizMode = 0;

	@Field
	private Integer reservationPaymentWechat = 0;

	@Field
	private Integer reservationNoPaymentWechat = 0;

	@Field
	private Integer reservationRefundWechat = 0;

	@Field
	private Integer dutyPaymentWechat = 0;

	@Field
	private Integer dutyNoPaymentWechat = 0;

	@Field
	private Integer dutyRefundWechat = 0;

	@Field
	private Long regPayFeeWechat = 0l;

	@Field
	private Long regRefundFeeWechat = 0l;

	@Field
	private Integer reservationPaymentAlipay = 0;

	@Field
	private Integer reservationNoPaymentAlipay = 0;

	@Field
	private Integer reservationRefundAlipay = 0;

	@Field
	private Integer dutyPaymentAlipay = 0;

	@Field
	private Integer dutyNoPaymentAlipay = 0;

	@Field
	private Integer dutyRefundAlipay = 0;

	@Field
	private Long regPayFeeAlipay = 0l;

	@Field
	private Long regRefundFeeAlipay = 0l;

	@Field
	private String hospitalCode;

	@Field
	private String areaCode;

	@Field
	private String areaName;

	@Field
	private String branchcode;

	@Field
	private Integer platformType = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
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

	public Long getRegPayFee() {
		return regPayFee;
	}

	public void setRegPayFee(Long regPayFee) {
		this.regPayFee = regPayFee;
	}

	public Long getRegRefundFee() {
		return regRefundFee;
	}

	public void setRegRefundFee(Long regRefundFee) {
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

	public Long getRegPayFeeWechat() {
		return regPayFeeWechat;
	}

	public void setRegPayFeeWechat(Long regPayFeeWechat) {
		this.regPayFeeWechat = regPayFeeWechat;
	}

	public Long getRegRefundFeeWechat() {
		return regRefundFeeWechat;
	}

	public void setRegRefundFeeWechat(Long regRefundFeeWechat) {
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

	public Long getRegPayFeeAlipay() {
		return regPayFeeAlipay;
	}

	public void setRegPayFeeAlipay(Long regPayFeeAlipay) {
		this.regPayFeeAlipay = regPayFeeAlipay;
	}

	public Long getRegRefundFeeAlipay() {
		return regRefundFeeAlipay;
	}

	public void setRegRefundFeeAlipay(Long regRefundFeeAlipay) {
		this.regRefundFeeAlipay = regRefundFeeAlipay;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	public void combineEntity(RegVo vo) {
		if (vo != null) {
			setDutyNoPayment(dutyNoPayment + vo.getDutyNoPayment());
			setDutyNoPaymentAlipay(dutyNoPaymentAlipay + vo.getDutyNoPaymentAlipay());
			setDutyNoPaymentWechat(dutyNoPaymentWechat + vo.getDutyNoPaymentWechat());
			setDutyPayment(dutyPayment + vo.getDutyPayment());
			setDutyPaymentAlipay(dutyPaymentAlipay + vo.getDutyPaymentAlipay());
			setDutyPaymentWechat(dutyPaymentWechat + vo.getDutyPaymentWechat());
			setDutyRefund(dutyRefund + vo.getDutyRefund());
			setDutyRefundAlipay(dutyRefundAlipay + vo.getDutyRefundAlipay());
			setDutyRefundWechat(dutyRefundWechat + vo.getDutyRefundWechat());
			setRegPayFee(regPayFee + vo.getRegPayFee());
			setRegPayFeeAlipay(regPayFeeAlipay + vo.getRegPayFeeAlipay());
			setRegPayFeeWechat(regPayFeeWechat + vo.getRegPayFeeWechat());
			setRegRefundFee(regRefundFee + vo.getRegRefundFee());
			setRegRefundFeeAlipay(regRefundFeeAlipay + vo.getRegRefundFeeAlipay());
			setRegRefundFeeWechat(regRefundFeeWechat + vo.getRegRefundFeeWechat());
			setReservationNoPayment(reservationNoPayment + vo.getReservationNoPayment());
			setReservationNoPaymentAlipay(reservationNoPaymentAlipay + vo.getReservationNoPaymentAlipay());
			setReservationNoPaymentWechat(reservationNoPaymentWechat + vo.getReservationNoPaymentWechat());
			setReservationPayment(reservationPayment + vo.getReservationPayment());
			setReservationPaymentAlipay(reservationPaymentAlipay + vo.getReservationPaymentAlipay());
			setReservationPaymentWechat(reservationPaymentWechat + vo.getReservationPaymentWechat());
			setReservationRefund(reservationRefundAlipay + vo.getReservationRefundAlipay());
			setReservationRefundWechat(reservationRefundWechat + vo.getReservationRefundWechat());
		}
	}

}
