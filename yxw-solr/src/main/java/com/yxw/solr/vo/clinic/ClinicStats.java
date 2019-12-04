package com.yxw.solr.vo.clinic;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class ClinicStats implements Serializable {

	private static final long serialVersionUID = -3289693563088051511L;

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
	 * 有效订单总数
	 */
	@Field
	private Integer totalQuantity;
	
	/**
	 * 已支付订单总数
	 */
	@Field
	private Integer paidQuantity;
	
	/**
	 * 已支付总金额
	 */
	@Field
	private Integer paidAmount;
	
	/**
	 * 已退费订单数 （包括全额退费、部分退费 -- 一次部分退费作为一次订单数）
	 */
	@Field
	private Integer refundQuantity;
	
	/**
	 * 已退费总金额
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

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Integer getPaidQuantity() {
		return paidQuantity;
	}

	public void setPaidQuantity(Integer pQuantity) {
		this.paidQuantity = pQuantity;
	}

	public Integer getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Integer pAmount) {
		this.paidAmount = pAmount;
	}

	public Integer getRefundQuantity() {
		return refundQuantity;
	}

	public void setRefundQuantity(Integer refundQuantity) {
		this.refundQuantity = refundQuantity;
	}

	public Integer getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Integer refundAmount) {
		this.refundAmount = refundAmount;
	}
}
