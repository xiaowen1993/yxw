/**
 * Copyright© 2014-2016 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2016年11月10日
 * @version 1.0
 */
package com.yxw.vo.deposit;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2016年11月10日  
 */
public class DepositVo implements Serializable {

	private static final long serialVersionUID = -6004026808129037699L;

	@Field
	private String id;

	@Field
	private String statsDate;

	@Field
	private String hospitalId;

	@Field
	private String branchId;

	@Field
	private Integer payment = 0;

	@Field
	private Integer noPayment = 0;

	@Field
	private Integer refund = 0;

	@Field
	private Long depositPayFee = 0l;

	@Field
	private Long depositRefundFee = 0l;

	@Field
	private Long partRefund = 0l;

	@Field
	private Integer bizMode = 0;

	@Field
	private Integer paymentWechat = 0;

	@Field
	private Integer noPaymentWechat = 0;

	@Field
	private Integer refundWechat = 0;

	@Field
	private Long depositPayFeeWechat = 0l;

	@Field
	private Long depositRefundFeeWechat = 0l;

	@Field
	private Long partRefundWechat = 0l;

	@Field
	private Integer paymentAlipay = 0;

	@Field
	private Integer noPaymentAlipay = 0;

	@Field
	private Integer refundAlipay = 0;

	@Field
	private Long depositPayFeeAlipay = 0l;

	@Field
	private Long depositRefundFeeAlipay = 0l;

	@Field
	private Long partRefundAlipay = 0l;

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

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the statsDate
	 */
	public String getStatsDate() {
		return statsDate;
	}

	/**
	 * @param statsDate the statsDate to set
	 */
	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
	}

	/**
	 * @return the hospitalId
	 */
	public String getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId the hospitalId to set
	 */
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the payment
	 */
	public Integer getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	/**
	 * @return the noPayment
	 */
	public Integer getNoPayment() {
		return noPayment;
	}

	/**
	 * @param noPayment the noPayment to set
	 */
	public void setNoPayment(Integer noPayment) {
		this.noPayment = noPayment;
	}

	/**
	 * @return the refund
	 */
	public Integer getRefund() {
		return refund;
	}

	/**
	 * @param refund the refund to set
	 */
	public void setRefund(Integer refund) {
		this.refund = refund;
	}

	/**
	 * @return the depositPayFee
	 */
	public Long getDepositPayFee() {
		return depositPayFee;
	}

	/**
	 * @param depositPayFee the depositPayFee to set
	 */
	public void setDepositPayFee(Long depositPayFee) {
		this.depositPayFee = depositPayFee;
	}

	/**
	 * @return the depositRefundFee
	 */
	public Long getDepositRefundFee() {
		return depositRefundFee;
	}

	/**
	 * @param depositRefundFee the depositRefundFee to set
	 */
	public void setDepositRefundFee(Long depositRefundFee) {
		this.depositRefundFee = depositRefundFee;
	}

	/**
	 * @return the partRefund
	 */
	public Long getPartRefund() {
		return partRefund;
	}

	/**
	 * @param partRefund the partRefund to set
	 */
	public void setPartRefund(Long partRefund) {
		this.partRefund = partRefund;
	}

	/**
	 * @return the bizMode
	 */
	public Integer getBizMode() {
		return bizMode;
	}

	/**
	 * @param bizMode the bizMode to set
	 */
	public void setBizMode(Integer bizMode) {
		this.bizMode = bizMode;
	}

	/**
	 * @return the paymentWechat
	 */
	public Integer getPaymentWechat() {
		return paymentWechat;
	}

	/**
	 * @param paymentWechat the paymentWechat to set
	 */
	public void setPaymentWechat(Integer paymentWechat) {
		this.paymentWechat = paymentWechat;
	}

	/**
	 * @return the noPaymentWechat
	 */
	public Integer getNoPaymentWechat() {
		return noPaymentWechat;
	}

	/**
	 * @param noPaymentWechat the noPaymentWechat to set
	 */
	public void setNoPaymentWechat(Integer noPaymentWechat) {
		this.noPaymentWechat = noPaymentWechat;
	}

	/**
	 * @return the refundWechat
	 */
	public Integer getRefundWechat() {
		return refundWechat;
	}

	/**
	 * @param refundWechat the refundWechat to set
	 */
	public void setRefundWechat(Integer refundWechat) {
		this.refundWechat = refundWechat;
	}

	/**
	 * @return the depositPayFeeWechat
	 */
	public Long getDepositPayFeeWechat() {
		return depositPayFeeWechat;
	}

	/**
	 * @param depositPayFeeWechat the depositPayFeeWechat to set
	 */
	public void setDepositPayFeeWechat(Long depositPayFeeWechat) {
		this.depositPayFeeWechat = depositPayFeeWechat;
	}

	/**
	 * @return the depositRefundFeeWechat
	 */
	public Long getDepositRefundFeeWechat() {
		return depositRefundFeeWechat;
	}

	/**
	 * @param depositRefundFeeWechat the depositRefundFeeWechat to set
	 */
	public void setDepositRefundFeeWechat(Long depositRefundFeeWechat) {
		this.depositRefundFeeWechat = depositRefundFeeWechat;
	}

	/**
	 * @return the partRefundWechat
	 */
	public Long getPartRefundWechat() {
		return partRefundWechat;
	}

	/**
	 * @param partRefundWechat the partRefundWechat to set
	 */
	public void setPartRefundWechat(Long partRefundWechat) {
		this.partRefundWechat = partRefundWechat;
	}

	/**
	 * @return the paymentAlipay
	 */
	public Integer getPaymentAlipay() {
		return paymentAlipay;
	}

	/**
	 * @param paymentAlipay the paymentAlipay to set
	 */
	public void setPaymentAlipay(Integer paymentAlipay) {
		this.paymentAlipay = paymentAlipay;
	}

	/**
	 * @return the noPaymentAlipay
	 */
	public Integer getNoPaymentAlipay() {
		return noPaymentAlipay;
	}

	/**
	 * @param noPaymentAlipay the noPaymentAlipay to set
	 */
	public void setNoPaymentAlipay(Integer noPaymentAlipay) {
		this.noPaymentAlipay = noPaymentAlipay;
	}

	/**
	 * @return the refundAlipay
	 */
	public Integer getRefundAlipay() {
		return refundAlipay;
	}

	/**
	 * @param refundAlipay the refundAlipay to set
	 */
	public void setRefundAlipay(Integer refundAlipay) {
		this.refundAlipay = refundAlipay;
	}

	/**
	 * @return the depositPayFeeAlipay
	 */
	public Long getDepositPayFeeAlipay() {
		return depositPayFeeAlipay;
	}

	/**
	 * @param depositPayFeeAlipay the depositPayFeeAlipay to set
	 */
	public void setDepositPayFeeAlipay(Long depositPayFeeAlipay) {
		this.depositPayFeeAlipay = depositPayFeeAlipay;
	}

	/**
	 * @return the depositRefundFeeAlipay
	 */
	public Long getDepositRefundFeeAlipay() {
		return depositRefundFeeAlipay;
	}

	/**
	 * @param depositRefundFeeAlipay the depositRefundFeeAlipay to set
	 */
	public void setDepositRefundFeeAlipay(Long depositRefundFeeAlipay) {
		this.depositRefundFeeAlipay = depositRefundFeeAlipay;
	}

	/**
	 * @return the partRefundAlipay
	 */
	public Long getPartRefundAlipay() {
		return partRefundAlipay;
	}

	/**
	 * @param partRefundAlipay the partRefundAlipay to set
	 */
	public void setPartRefundAlipay(Long partRefundAlipay) {
		this.partRefundAlipay = partRefundAlipay;
	}

	/**
	 * @return the hospitalCode
	 */
	public String getHospitalCode() {
		return hospitalCode;
	}

	/**
	 * @param hospitalCode the hospitalCode to set
	 */
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the branchcode
	 */
	public String getBranchcode() {
		return branchcode;
	}

	/**
	 * @param branchcode the branchcode to set
	 */
	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	/**
	 * @return the platformType
	 */
	public Integer getPlatformType() {
		return platformType;
	}

	/**
	 * @param platformType the platformType to set
	 */
	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	public void combineEntity(DepositVo vo) {
		if (vo != null) {
			setPayment(payment + vo.getPayment());
			setNoPayment(noPayment + vo.getNoPayment());
			setRefund(refund + vo.getRefund());
			setDepositPayFee(depositPayFee + vo.getDepositPayFee());
			setDepositRefundFee(depositRefundFee + vo.getDepositRefundFee());
			setPartRefund(partRefund + vo.getPartRefund());
			setPaymentWechat(paymentWechat + vo.getPaymentWechat());
			setNoPaymentWechat(noPaymentWechat + vo.getNoPaymentWechat());
			setRefundWechat(refundWechat + vo.getRefundWechat());
			setDepositPayFeeWechat(depositPayFeeWechat + vo.getDepositPayFeeWechat());
			setDepositRefundFeeWechat(depositRefundFeeWechat + vo.getDepositRefundFeeWechat());
			setPartRefundWechat(partRefundWechat + vo.getPartRefundWechat());
			setPaymentAlipay(paymentAlipay + vo.getPaymentAlipay());
			setNoPaymentAlipay(noPaymentAlipay + vo.getNoPaymentAlipay());
			setRefundAlipay(refundAlipay + vo.getRefundAlipay());
			setDepositPayFeeAlipay(depositPayFeeAlipay + vo.getDepositPayFeeAlipay());
			setDepositRefundFeeAlipay(depositRefundFeeAlipay + vo.getDepositRefundFeeAlipay());
			setPartRefundAlipay(partRefundAlipay + vo.getPartRefundAlipay());
		}
	}
}
