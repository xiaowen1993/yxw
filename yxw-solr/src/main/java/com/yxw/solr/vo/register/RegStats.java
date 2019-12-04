package com.yxw.solr.vo.register;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class RegStats implements Serializable {

	private static final long serialVersionUID = 6310406338515229858L;

	/**
	 * id
	 */
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
	 * 已支付订单总数
	 */
	@Field
	private Integer paidQuantity;
	
	/**
	 * 已支付订单总金额
	 */
	@Field
	private Integer paidAmount;
	
	/**
	 * 已退费订单总数
	 */
	@Field
	private Integer refundQuantity;
	
	/**s
	 * 已退费订单总额
	 */
	@Field
	private Integer refundAmount;
	
	/**
	 * 预约已支付总数
	 */
	@Field
	private Integer appointmentPaidQuantity;
	
	/**
	 * 预约已支付总额
	 */
	@Field
	private Integer appointmentPaidAmount;
	
	/**
	 * 当班已支付总数
	 */
	@Field
	private Integer dutyPaidQuantity;
	
	/**
	 * 当班已支付总金额
	 */
	@Field
	private Integer dutyPaidAmount;
	
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
		return platform == null ? -1 : platform;
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

	public Integer getPaidQuantity() {
		return paidQuantity;
	}

	public void setPaidQuantity(Integer paidQuantity) {
		this.paidQuantity = paidQuantity;
	}

	public Integer getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Integer paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Integer getRefundQuantity() {
		return refundQuantity == null ? 0 : refundQuantity;
	}

	public void setRefundQuantity(Integer refundQuantity) {
		this.refundQuantity = refundQuantity;
	}

	public Integer getRefundAmount() {
		return refundAmount == null ? 0 : refundAmount;
	}

	public void setRefundAmount(Integer refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Integer getAppointmentPaidQuantity() {
		return appointmentPaidQuantity == null ? 0 : appointmentPaidQuantity;
	}

	public void setAppointmentPaidQuantity(Integer appointmentPaidQuantity) {
		this.appointmentPaidQuantity = appointmentPaidQuantity;
	}

	public Integer getAppointmentPaidAmount() {
		return appointmentPaidAmount == null ? 0 : appointmentPaidAmount;
	}

	public void setAppointmentPaidAmount(Integer appointmentPaidAmount) {
		this.appointmentPaidAmount = appointmentPaidAmount;
	}

	public Integer getDutyPaidQuantity() {
		return dutyPaidQuantity == null ? 0 : dutyPaidQuantity;
	}

	public void setDutyPaidQuantity(Integer dutyPaidQuantity) {
		this.dutyPaidQuantity = dutyPaidQuantity;
	}

	public Integer getDutyPaidAmount() {
		return dutyPaidAmount == null ? 0 : dutyPaidAmount;
	}

	public void setDutyPaidAmount(Integer dutyPaidAmount) {
		this.dutyPaidAmount = dutyPaidAmount;
	}
	
}
