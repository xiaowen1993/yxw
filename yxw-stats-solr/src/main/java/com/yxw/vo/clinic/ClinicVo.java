package com.yxw.vo.clinic;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class ClinicVo implements Serializable {

	private static final long serialVersionUID = -2882778468878403524L;

	@Field
	private String id;

	@Field
	private String hospitalId;

	@Field
	private String branchId;

	@Field
	private String statsDate;

	@Field
	private Integer payment = 0;

	@Field
	private Integer noPayment = 0;

	@Field
	private Integer refund = 0;

	@Field
	private Long clinicPayFee = 0L;

	@Field
	private Long clinicRefundFee = 0L;

	@Field
	private Long partRrefund = 0L;

	@Field
	private Integer bizMode = 0;

	@Field
	private Integer paymentWechat = 0;

	@Field
	private Integer noPaymentWechat = 0;

	@Field
	private Integer refundWechat = 0;

	@Field
	private Long clinicPayFeeWechat = 0L;

	@Field
	private Long clinicRefundFeeWechat = 0L;

	@Field
	private Long partRefundWechat = 0L;

	@Field
	private Integer paymentAlipay = 0;

	@Field
	private Integer noPaymentAlipay = 0;

	@Field
	private Integer refundAlipay = 0;

	@Field
	private Long clinicPayFeeAlipay = 0L;

	@Field
	private Long clinicRefundFeeAlipay = 0L;

	@Field
	private Long partRefundAlipay = 0L;

	@Field
	private String hospitalCode;

	@Field
	private String areaCode;

	@Field
	private String areaName;

	@Field
	private String branchcode;

	@Field
	private Integer platformType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Long getClinicPayFee() {
		return clinicPayFee;
	}

	public void setClinicPayFee(Long clinicPayFee) {
		this.clinicPayFee = clinicPayFee;
	}

	public Long getClinicRefundFee() {
		return clinicRefundFee;
	}

	public void setClinicRefundFee(Long clinicRefundFee) {
		this.clinicRefundFee = clinicRefundFee;
	}

	public Long getPartRrefund() {
		return partRrefund;
	}

	public void setPartRrefund(Long partRrefund) {
		this.partRrefund = partRrefund;
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

	public Long getClinicPayFeeWechat() {
		return clinicPayFeeWechat;
	}

	public void setClinicPayFeeWechat(Long clinicPayFeeWechat) {
		this.clinicPayFeeWechat = clinicPayFeeWechat;
	}

	public Long getClinicRefundFeeWechat() {
		return clinicRefundFeeWechat;
	}

	public void setClinicRefundFeeWechat(Long clinicRefundFeeWechat) {
		this.clinicRefundFeeWechat = clinicRefundFeeWechat;
	}

	public Long getPartRefundWechat() {
		return partRefundWechat;
	}

	public void setPartRefundWechat(Long partRefundWechat) {
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

	public Long getClinicPayFeeAlipay() {
		return clinicPayFeeAlipay;
	}

	public void setClinicPayFeeAlipay(Long clinicPayFeeAlipay) {
		this.clinicPayFeeAlipay = clinicPayFeeAlipay;
	}

	public Long getClinicRefundFeeAlipay() {
		return clinicRefundFeeAlipay;
	}

	public void setClinicRefundFeeAlipay(Long clinicRefundFeeAlipay) {
		this.clinicRefundFeeAlipay = clinicRefundFeeAlipay;
	}

	public Long getPartRefundAlipay() {
		return partRefundAlipay;
	}

	public void setPartRefundAlipay(Long partRefundAlipay) {
		this.partRefundAlipay = partRefundAlipay;
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

	public String getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
	}

	public void combineEntity(ClinicVo vo) {
		if (vo != null) {
			setPayment(payment + vo.getPayment());
			setNoPayment(noPayment + vo.getNoPayment());
			setRefund(refund + vo.getRefund());
			setClinicPayFee(clinicPayFee + vo.getClinicPayFee());
			setClinicRefundFee(clinicRefundFee + vo.getClinicRefundFee());
			setPartRrefund(partRrefund + vo.getPartRrefund());
			setPaymentWechat(paymentWechat + vo.getPaymentWechat());
			setNoPaymentWechat(noPaymentWechat + vo.getNoPaymentWechat());
			setRefundWechat(refundWechat + vo.getRefundWechat());
			setClinicPayFeeWechat(clinicPayFeeWechat + vo.getClinicPayFeeWechat());
			setClinicRefundFeeWechat(clinicRefundFeeWechat + vo.getClinicRefundFeeWechat());
			setPartRefundWechat(partRefundWechat + vo.getPartRefundWechat());
			setPaymentAlipay(paymentAlipay + vo.getPaymentAlipay());
			setNoPaymentAlipay(noPaymentAlipay + vo.getNoPaymentAlipay());
			setRefundAlipay(refundAlipay + vo.getRefundAlipay());
			setClinicPayFeeAlipay(clinicPayFeeAlipay + vo.getClinicPayFeeAlipay());
			setClinicRefundFeeAlipay(clinicRefundFeeAlipay + vo.getClinicRefundFeeAlipay());
			setPartRefundAlipay(partRefundAlipay + vo.getPartRefundAlipay());
		}
	}
}
