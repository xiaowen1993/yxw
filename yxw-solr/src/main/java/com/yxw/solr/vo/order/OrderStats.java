package com.yxw.solr.vo.order;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class OrderStats implements Serializable {

	private static final long serialVersionUID = -7573316939749893619L;

	@Field
	private String id;
	
	/**
	 * 医院代码
	 */
	@Field
	private String hospitalCode;
	
	/**
	 * 分院代码
	 */
	@Field
	private String branchCode;
	
	/**
	 * 平台
	 */
	@Field
	private Integer platform;
	
	/**
	 * 更新时间
	 */
	@Field
	private String updateTime_utc;
	
	/**
	 * 更新时间
	 */
	@Field
	private String updateTime;
	
	/**
	 * 统计日期
	 */
	@Field
	private String statsDate;
	
	/**
	 * 总收入
	 */
	@Field
	private Integer netIncome;
	
	/**
	 * 挂号总缴费
	 */
	@Field
	private Integer regPaidAmount;
	
	/**
	 * 门诊总缴费
	 */
	@Field
	private Integer clinicPaidAmount;
	
	/**
	 * 住院总缴费
	 */
	@Field
	private Integer depositPaidAmount;
	
	/**
	 * 挂号总退费
	 */
	@Field
	private Integer regRefundAmount;
	
	/**
	 * 门诊总退费
	 */
	@Field
	private Integer clinicRefundAmount;
	
	/**
	 * 住院总退费
	 */
	@Field
	private Integer depositRefundAmount;
	
	/**
	 * 挂号支付总数
	 */
	@Field
	private Integer regPaidQuantity;
	
	/**
	 * 门诊支付订单总数
	 */
	@Field
	private Integer clinicPaidQuantity;
	
	/**
	 * 押金支付订单总数
	 */
	@Field
	private Integer depositPaidQuantity;
	
	/**
	 * 挂号退费订单总数
	 */
	@Field
	private Integer regRefundQuantity;

	/**
	 * 门诊退费订单总数
	 */
	@Field
	private Integer clinicRefundQuantity;
	
	/**
	 * 押金退费订单总数
	 */
	@Field
	private Integer depositRefundQuantity;
	
	/**
	 * 累计订单数
	 */
	@Field
	private Integer cumulativeQuantity;
	
	/**
	 * 累计支付总数
	 */
	@Field
	private Integer cumulativePaidQuantity;
	
	/**
	 * 累计退费总数
	 */
	@Field
	private Integer cumulativeRefundQuantity;
	
	/**
	 * 累计支付总额
	 */
	@Field
	private Integer cumulativePaidAmount;
	
	/**
	 * 累计退费总额
	 */
	@Field
	private Integer cumulativeRefundAmount;
	
	/**
	 * 订单总数
	 */
	@Field
	private Integer ordersQuantity;
	
	/**
	 * 支付总数
	 */
	@Field
	private Integer paidQuantity;
	
	/**
	 * 退费总数
	 */
	@Field
	private Integer refundQuantity;
	
	/**
	 * 支付总额
	 */
	@Field
	private Integer paidAmount;
	
	/**
	 * 退费总额
	 */
	@Field
	private Integer refundAmount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getUpdateTime_utc() {
		return updateTime_utc;
	}

	public void setUpdateTime_utc(String updateTime_utc) {
		this.updateTime_utc = updateTime_utc;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
	}

	public Integer getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(Integer netIncome) {
		this.netIncome = netIncome;
	}

	public Integer getRegPaidAmount() {
		return regPaidAmount;
	}

	public void setRegPaidAmount(Integer regPaidAmount) {
		this.regPaidAmount = regPaidAmount;
	}

	public Integer getClinicPaidAmount() {
		return clinicPaidAmount;
	}

	public void setClinicPaidAmount(Integer clinicPaidAmount) {
		this.clinicPaidAmount = clinicPaidAmount;
	}

	public Integer getDepositPaidAmount() {
		return depositPaidAmount;
	}

	public void setDepositPaidAmount(Integer depositPaidAmount) {
		this.depositPaidAmount = depositPaidAmount;
	}

	public Integer getRegRefundAmount() {
		return regRefundAmount;
	}

	public void setRegRefundAmount(Integer regRefundAmount) {
		this.regRefundAmount = regRefundAmount;
	}

	public Integer getClinicRefundAmount() {
		return clinicRefundAmount;
	}

	public void setClinicRefundAmount(Integer clinicRefundAmount) {
		this.clinicRefundAmount = clinicRefundAmount;
	}

	public Integer getDepositRefundAmount() {
		return depositRefundAmount;
	}

	public void setDepositRefundAmount(Integer depositRefundAmount) {
		this.depositRefundAmount = depositRefundAmount;
	}

	public Integer getRegPaidQuantity() {
		return regPaidQuantity;
	}

	public void setRegPaidQuantity(Integer regPaidQuantity) {
		this.regPaidQuantity = regPaidQuantity;
	}

	public Integer getClinicPaidQuantity() {
		return clinicPaidQuantity;
	}

	public void setClinicPaidQuantity(Integer clinicPaidQuantity) {
		this.clinicPaidQuantity = clinicPaidQuantity;
	}

	public Integer getDepositPaidQuantity() {
		return depositPaidQuantity;
	}

	public void setDepositPaidQuantity(Integer depositPaidQuantity) {
		this.depositPaidQuantity = depositPaidQuantity;
	}

	public Integer getRegRefundQuantity() {
		return regRefundQuantity;
	}

	public void setRegRefundQuantity(Integer regRefundQuantity) {
		this.regRefundQuantity = regRefundQuantity;
	}

	public Integer getClinicRefundQuantity() {
		return clinicRefundQuantity;
	}

	public void setClinicRefundQuantity(Integer clinicRefundQuantity) {
		this.clinicRefundQuantity = clinicRefundQuantity;
	}

	public Integer getDepositRefundQuantity() {
		return depositRefundQuantity;
	}

	public void setDepositRefundQuantity(Integer depositRefundQuantity) {
		this.depositRefundQuantity = depositRefundQuantity;
	}

	public Integer getCumulativeQuantity() {
		return cumulativeQuantity;
	}

	public void setCumulativeQuantity(Integer cumulativeQuantity) {
		this.cumulativeQuantity = cumulativeQuantity;
	}

	public Integer getCumulativePaidQuantity() {
		return cumulativePaidQuantity;
	}

	public void setCumulativePaidQuantity(Integer cumulativePaidQuantity) {
		this.cumulativePaidQuantity = cumulativePaidQuantity;
	}

	public Integer getCumulativeRefundQuantity() {
		return cumulativeRefundQuantity;
	}

	public void setCumulativeRefundQuantity(Integer cumulativeRefundQuantity) {
		this.cumulativeRefundQuantity = cumulativeRefundQuantity;
	}

	public Integer getCumulativePaidAmount() {
		return cumulativePaidAmount;
	}

	public void setCumulativePaidAmount(Integer cumulativePaidAmount) {
		this.cumulativePaidAmount = cumulativePaidAmount;
	}

	public Integer getCumulativeRefundAmount() {
		return cumulativeRefundAmount;
	}

	public void setCumulativeRefundAmount(Integer cumulativeRefundAmount) {
		this.cumulativeRefundAmount = cumulativeRefundAmount;
	}

	public Integer getOrdersQuantity() {
		return ordersQuantity;
	}

	public void setOrdersQuantity(Integer ordersQuantity) {
		this.ordersQuantity = ordersQuantity;
	}

	public Integer getPaidQuantity() {
		return paidQuantity;
	}

	public void setPaidQuantity(Integer paidQuantity) {
		this.paidQuantity = paidQuantity;
	}

	public Integer getRefundQuantity() {
		return refundQuantity;
	}

	public void setRefundQuantity(Integer refundQuantity) {
		this.refundQuantity = refundQuantity;
	}

	public Integer getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Integer paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Integer getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Integer refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	
}
