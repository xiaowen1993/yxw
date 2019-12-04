package com.yxw.vo.register;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class RegStats implements Serializable {

	private static final long serialVersionUID = 6691041609471797983L;

	@Field
	private String id;

	@Field
	private Integer platform;

	@Field
	private String hospitalCode = "-";

	@Field
	private String hospitalName = "-";

	@Field
	private String areaCode;

	@Field
	private String areaName;

	@Field
	private String thisMonth;

	@Field
	private String lastMonth;

	/**
	 * 每月总额 (不算未支付)
	 */
	@Field
	private long monthTotalAmount;

	/**
	 * 每月总量(不算未支付)
	 */
	@Field
	private int monthTotalCount;

	/**
	 * 累计总额(不算未支付)
	 */
	@Field
	private long cumulateTotalAmount;

	/**
	 * 累计总量(不算未支付)
	 */
	@Field
	private int cumulateTotalCount;

	/**
	 * 每月微信支付总额
	 */
	@Field
	private long monthWxTotalAmount;

	/**
	 * 每月支付宝支付总额
	 */
	@Field
	private long monthAliTotalAmount;

	/**
	 * 每月微信支付数目
	 */
	@Field
	private int monthWxTotalCount;

	/**
	 * 每月支付宝支付数目
	 */
	@Field
	private int monthAliTotalCount;

	/**
	 * 累积微信支付总额
	 */
	@Field
	private long cumulateWxTotalAmount;

	/**
	 * 累积支付宝支付总额
	 */
	@Field
	private long cumulateAliTotalAmount;

	/**
	 * 累积微信支付总量
	 */
	@Field
	private int cumulateWxTotalCount;

	/**
	 * 累计支付宝支付总量
	 */
	@Field
	private int cumulateAliTotalCount;

	/**
	 * 每月当班未支付笔数
	 */
	@Field
	private int monthDutyNoPayCount;

	/**
	 * 每月预约未支付笔数
	 */
	@Field
	private int monthAppointmentNoPayCount;

	/**
	 * 每月未支付笔数
	 */
	@Field
	private int monthNoPayCount;

	/**
	 * 累积当班未支付笔数
	 */
	@Field
	private int cumulateDutyNoPayCount;

	/**
	 * 累积预约未支付笔数
	 */
	@Field
	private int cumulateAppointmentNoPayCount;

	/**
	 * 累计未支付笔数
	 */
	@Field
	private int cumulateNoPayCount;

	/**
	 * 每月当班微信支付笔数
	 */
	@Field
	private int monthDutyWxPayCount;

	/**
	 * 每月当班支付宝支付笔数
	 */
	@Field
	private int monthDutyAliPayCount;

	/**
	 * 每月当班支付总数
	 */
	@Field
	private int monthDutyPayCount;

	/**
	 * 每月预约微信支付总数
	 */
	@Field
	private int monthAppointmentWxPayCount;

	/**
	 * 每月预约支付宝支付总数
	 */
	@Field
	private int monthAppointmentAliPayCount;

	/**
	 * 每月预约支付总数
	 */
	@Field
	private int monthAppointmentPayCount;

	/**
	 * 累计当班微信支付总数
	 */
	@Field
	private int cumulateDutyWxPayCount;

	/**
	 * 累计当班支付宝支付总数
	 */
	@Field
	private int cumulateDutyAliPayCount;

	/**
	 * 累计当班支付总数
	 */
	@Field
	private int cumulateDutyPayCount;

	/**
	 * 累计预约微信支付总数
	 */
	@Field
	private int cumulateAppointmentWxPayCount;

	/**
	 * 累计预约支付宝支付总数
	 */
	@Field
	private int cumulateAppointmentAliPayCount;

	/**
	 * 累计预约支付总数
	 */
	@Field
	private int cumulateAppointmentPayCount;

	/**
	 * 每月当班微信退费总数
	 */
	@Field
	private int monthDutyWxRefundCount;

	/**
	 * 每月当班支付宝退费总数
	 */
	@Field
	private int monthDutyAliRefundCount;

	/**
	 * 每月预约微信退费总数
	 */
	@Field
	private int monthAppointmentWxRefundCount;

	/**
	 * 每月预约支付宝退费总数
	 */
	@Field
	private int monthAppointmentAliRefundCount;

	/**
	 * 每月当班退费总数
	 */
	@Field
	private int monthDutyRefundCount;

	/**
	 * 每月预约退费总数
	 */
	@Field
	private int monthAppointmentRefundCount;

	/**
	 * 每月微信退费总数
	 */
	@Field
	private int monthWxRefundCount;

	/**
	 * 每月支付宝退费总数
	 */
	@Field
	private int monthAliRefundCount;

	/**
	 * 每月微信退费总额
	 */
	@Field
	private long monthWxRefundAmount;

	/**
	 * 每月支付宝退费总额
	 */
	@Field
	private long monthAliRefundAmount;

	/**
	 * 每月退费总数
	 */
	@Field
	private int monthRefundCount;

	/**
	 * 每月退费总额
	 */
	@Field
	private long monthRefundAmount;
	
	@Field
	private int cumulateDutyWxRefundCount;
	
	@Field
	private int cumulateDutyAliRefundCount;
	
	@Field
	private int cumulateAppointmentWxRefundCount;
	
	@Field
	private int cumulateAppointmentAliRefundCount;
	
	@Field
	private int cumulateDutyRefundCount;
	
	@Field
	private int cumulateAppointmentRefundCount;
	
	@Field
	private int cumulateWxRefundCount;
	
	@Field
	private int cumulateAliRefundCount;
	
	@Field
	private long cumulateWxRefundAmount;
	
	@Field
	private long cumulateAliRefundAmount;
	
	@Field
	private long cumulateRefundAmount;
	
	@Field
	private int cumulateRefundCount;
	
	@Field
	private int monthWxDutyNoPayCount;
	
	@Field
	private int monthAliDutyNoPayCount;
	
	@Field
	private int monthWxAppointmentNoPayCount;
	
	@Field
	private int monthAliAppointmentNoPayCount;
	
	@Field
	private int monthWxNoPayCount;
	
	@Field
	private int monthAliNoPayCount;
	
	@Field
	private int cumulateWxDutyNoPayCount;
	
	@Field
	private int cumulateAliDutyNoPayCount;
	
	@Field
	private int cumulateWxAppointmentNoPayCount;
	
	@Field
	private int cumulateAliAppointmentNoPayCount;
	
	@Field
	private int cumulateWxNoPayCount;
	
	@Field
	private int cumulateAliNoPayCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
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

	public String getThisMonth() {
		return thisMonth;
	}

	public void setThisMonth(String thisMonth) {
		this.thisMonth = thisMonth;
	}

	public String getLastMonth() {
		return lastMonth;
	}

	public void setLastMonth(String lastMonth) {
		this.lastMonth = lastMonth;
	}

	public long getMonthTotalAmount() {
		return monthTotalAmount;
	}

	public void setMonthTotalAmount(long monthTotalAmount) {
		this.monthTotalAmount = monthTotalAmount;
	}

	public int getMonthTotalCount() {
		return monthTotalCount;
	}

	public void setMonthTotalCount(int monthTotalCount) {
		this.monthTotalCount = monthTotalCount;
	}

	public long getCumulateTotalAmount() {
		return cumulateTotalAmount;
	}

	public void setCumulateTotalAmount(long cumulateTotalAmount) {
		this.cumulateTotalAmount = cumulateTotalAmount;
	}

	public int getCumulateTotalCount() {
		return cumulateTotalCount;
	}

	public void setCumulateTotalCount(int cumulateTotalCount) {
		this.cumulateTotalCount = cumulateTotalCount;
	}

	public long getMonthWxTotalAmount() {
		return monthWxTotalAmount;
	}

	public void setMonthWxTotalAmount(long monthWxTotalAmount) {
		this.monthWxTotalAmount = monthWxTotalAmount;
	}

	public long getMonthAliTotalAmount() {
		return monthAliTotalAmount;
	}

	public void setMonthAliTotalAmount(long monthAliTotalAmount) {
		this.monthAliTotalAmount = monthAliTotalAmount;
	}

	public int getMonthWxTotalCount() {
		return monthWxTotalCount;
	}

	public void setMonthWxTotalCount(int monthWxTotalCount) {
		this.monthWxTotalCount = monthWxTotalCount;
	}

	public int getMonthAliTotalCount() {
		return monthAliTotalCount;
	}

	public void setMonthAliTotalCount(int monthAliTotalCount) {
		this.monthAliTotalCount = monthAliTotalCount;
	}

	public long getCumulateWxTotalAmount() {
		return cumulateWxTotalAmount;
	}

	public void setCumulateWxTotalAmount(long cumulateWxTotalAmount) {
		this.cumulateWxTotalAmount = cumulateWxTotalAmount;
	}

	public long getCumulateAliTotalAmount() {
		return cumulateAliTotalAmount;
	}

	public void setCumulateAliTotalAmount(long cumulateAliTotalAmount) {
		this.cumulateAliTotalAmount = cumulateAliTotalAmount;
	}

	public int getCumulateWxTotalCount() {
		return cumulateWxTotalCount;
	}

	public void setCumulateWxTotalCount(int cumulateWxTotalCount) {
		this.cumulateWxTotalCount = cumulateWxTotalCount;
	}

	public int getCumulateAliTotalCount() {
		return cumulateAliTotalCount;
	}

	public void setCumulateAliTotalCount(int cumulateAliTotalCount) {
		this.cumulateAliTotalCount = cumulateAliTotalCount;
	}

	public int getMonthDutyNoPayCount() {
		return monthDutyNoPayCount;
	}

	public void setMonthDutyNoPayCount(int monthDutyNoPayCount) {
		this.monthDutyNoPayCount = monthDutyNoPayCount;
	}

	public int getMonthAppointmentNoPayCount() {
		return monthAppointmentNoPayCount;
	}

	public void setMonthAppointmentNoPayCount(int monthAppointmentNoPayCount) {
		this.monthAppointmentNoPayCount = monthAppointmentNoPayCount;
	}

	public int getMonthNoPayCount() {
		return monthNoPayCount;
	}

	public void setMonthNoPayCount(int monthNoPayCount) {
		this.monthNoPayCount = monthNoPayCount;
	}

	public int getCumulateDutyNoPayCount() {
		return cumulateDutyNoPayCount;
	}

	public void setCumulateDutyNoPayCount(int cumulateDutyNoPayCount) {
		this.cumulateDutyNoPayCount = cumulateDutyNoPayCount;
	}

	public int getCumulateAppointmentNoPayCount() {
		return cumulateAppointmentNoPayCount;
	}

	public void setCumulateAppointmentNoPayCount(int cumulateAppointmentNoPayCount) {
		this.cumulateAppointmentNoPayCount = cumulateAppointmentNoPayCount;
	}

	public int getCumulateNoPayCount() {
		return cumulateNoPayCount;
	}

	public void setCumulateNoPayCount(int cumulateNoPayCount) {
		this.cumulateNoPayCount = cumulateNoPayCount;
	}

	public int getMonthDutyWxPayCount() {
		return monthDutyWxPayCount;
	}

	public void setMonthDutyWxPayCount(int monthDutyWxPayCount) {
		this.monthDutyWxPayCount = monthDutyWxPayCount;
	}

	public int getMonthDutyAliPayCount() {
		return monthDutyAliPayCount;
	}

	public void setMonthDutyAliPayCount(int monthDutyAliPayCount) {
		this.monthDutyAliPayCount = monthDutyAliPayCount;
	}

	public int getMonthDutyPayCount() {
		return monthDutyPayCount;
	}

	public void setMonthDutyPayCount(int monthDutyPayCount) {
		this.monthDutyPayCount = monthDutyPayCount;
	}

	public int getMonthAppointmentWxPayCount() {
		return monthAppointmentWxPayCount;
	}

	public void setMonthAppointmentWxPayCount(int monthAppointmentWxPayCount) {
		this.monthAppointmentWxPayCount = monthAppointmentWxPayCount;
	}

	public int getMonthAppointmentAliPayCount() {
		return monthAppointmentAliPayCount;
	}

	public void setMonthAppointmentAliPayCount(int monthAppointmentAliPayCount) {
		this.monthAppointmentAliPayCount = monthAppointmentAliPayCount;
	}

	public int getMonthAppointmentPayCount() {
		return monthAppointmentPayCount;
	}

	public void setMonthAppointmentPayCount(int monthAppointmentPayCount) {
		this.monthAppointmentPayCount = monthAppointmentPayCount;
	}

	public int getCumulateDutyWxPayCount() {
		return cumulateDutyWxPayCount;
	}

	public void setCumulateDutyWxPayCount(int cumulateDutyWxPayCount) {
		this.cumulateDutyWxPayCount = cumulateDutyWxPayCount;
	}

	public int getCumulateDutyAliPayCount() {
		return cumulateDutyAliPayCount;
	}

	public void setCumulateDutyAliPayCount(int cumulateDutyAliPayCount) {
		this.cumulateDutyAliPayCount = cumulateDutyAliPayCount;
	}

	public int getCumulateDutyPayCount() {
		return cumulateDutyPayCount;
	}

	public void setCumulateDutyPayCount(int cumulateDutyPayCount) {
		this.cumulateDutyPayCount = cumulateDutyPayCount;
	}

	public int getCumulateAppointmentWxPayCount() {
		return cumulateAppointmentWxPayCount;
	}

	public void setCumulateAppointmentWxPayCount(int cumulateAppointmentWxPayCount) {
		this.cumulateAppointmentWxPayCount = cumulateAppointmentWxPayCount;
	}

	public int getCumulateAppointmentAliPayCount() {
		return cumulateAppointmentAliPayCount;
	}

	public void setCumulateAppointmentAliPayCount(int cumulateAppointmentAliPayCount) {
		this.cumulateAppointmentAliPayCount = cumulateAppointmentAliPayCount;
	}

	public int getCumulateAppointmentPayCount() {
		return cumulateAppointmentPayCount;
	}

	public void setCumulateAppointmentPayCount(int cumulateAppointmentPayCount) {
		this.cumulateAppointmentPayCount = cumulateAppointmentPayCount;
	}

	public int getMonthDutyWxRefundCount() {
		return monthDutyWxRefundCount;
	}

	public void setMonthDutyWxRefundCount(int monthDutyWxRefundCount) {
		this.monthDutyWxRefundCount = monthDutyWxRefundCount;
	}

	public int getMonthDutyAliRefundCount() {
		return monthDutyAliRefundCount;
	}

	public void setMonthDutyAliRefundCount(int monthDutyAliRefundCount) {
		this.monthDutyAliRefundCount = monthDutyAliRefundCount;
	}

	public int getMonthAppointmentWxRefundCount() {
		return monthAppointmentWxRefundCount;
	}

	public void setMonthAppointmentWxRefundCount(int monthAppointmentWxRefundCount) {
		this.monthAppointmentWxRefundCount = monthAppointmentWxRefundCount;
	}

	public int getMonthAppointmentAliRefundCount() {
		return monthAppointmentAliRefundCount;
	}

	public void setMonthAppointmentAliRefundCount(int monthAppointmentAliRefundCount) {
		this.monthAppointmentAliRefundCount = monthAppointmentAliRefundCount;
	}

	public int getMonthDutyRefundCount() {
		return monthDutyRefundCount;
	}

	public void setMonthDutyRefundCount(int monthDutyRefundCount) {
		this.monthDutyRefundCount = monthDutyRefundCount;
	}

	public int getMonthAppointmentRefundCount() {
		return monthAppointmentRefundCount;
	}

	public void setMonthAppointmentRefundCount(int monthAppointmentRefundCount) {
		this.monthAppointmentRefundCount = monthAppointmentRefundCount;
	}

	public int getMonthWxRefundCount() {
		return monthWxRefundCount;
	}

	public void setMonthWxRefundCount(int monthWxRefundCount) {
		this.monthWxRefundCount = monthWxRefundCount;
	}

	public int getMonthAliRefundCount() {
		return monthAliRefundCount;
	}

	public void setMonthAliRefundCount(int monthAliRefundCount) {
		this.monthAliRefundCount = monthAliRefundCount;
	}

	public long getMonthWxRefundAmount() {
		return monthWxRefundAmount;
	}

	public void setMonthWxRefundAmount(long monthWxRefundAmount) {
		this.monthWxRefundAmount = monthWxRefundAmount;
	}

	public long getMonthAliRefundAmount() {
		return monthAliRefundAmount;
	}

	public void setMonthAliRefundAmount(long monthAliRefundAmount) {
		this.monthAliRefundAmount = monthAliRefundAmount;
	}

	public int getMonthRefundCount() {
		return monthRefundCount;
	}

	public void setMonthRefundCount(int monthRefundCount) {
		this.monthRefundCount = monthRefundCount;
	}

	public long getMonthRefundAmount() {
		return monthRefundAmount;
	}

	public void setMonthRefundAmount(long monthRefundAmount) {
		this.monthRefundAmount = monthRefundAmount;
	}
	
	public int getCumulateDutyWxRefundCount() {
		return cumulateDutyWxRefundCount;
	}

	public void setCumulateDutyWxRefundCount(int cumulateDutyWxRefundCount) {
		this.cumulateDutyWxRefundCount = cumulateDutyWxRefundCount;
	}

	public int getCumulateDutyAliRefundCount() {
		return cumulateDutyAliRefundCount;
	}

	public void setCumulateDutyAliRefundCount(int cumulateDutyAliRefundCount) {
		this.cumulateDutyAliRefundCount = cumulateDutyAliRefundCount;
	}

	public int getCumulateAppointmentWxRefundCount() {
		return cumulateAppointmentWxRefundCount;
	}

	public void setCumulateAppointmentWxRefundCount(int cumulateAppointmentWxRefundCount) {
		this.cumulateAppointmentWxRefundCount = cumulateAppointmentWxRefundCount;
	}

	public int getCumulateAppointmentAliRefundCount() {
		return cumulateAppointmentAliRefundCount;
	}

	public void setCumulateAppointmentAliRefundCount(int cumulateAppointmentAliRefundCount) {
		this.cumulateAppointmentAliRefundCount = cumulateAppointmentAliRefundCount;
	}

	public int getCumulateDutyRefundCount() {
		return cumulateDutyRefundCount;
	}

	public void setCumulateDutyRefundCount(int cumulateDutyRefundCount) {
		this.cumulateDutyRefundCount = cumulateDutyRefundCount;
	}

	public int getCumulateAppointmentRefundCount() {
		return cumulateAppointmentRefundCount;
	}

	public void setCumulateAppointmentRefundCount(int cumulateAppointmentRefundCount) {
		this.cumulateAppointmentRefundCount = cumulateAppointmentRefundCount;
	}

	public int getCumulateWxRefundCount() {
		return cumulateWxRefundCount;
	}

	public void setCumulateWxRefundCount(int cumulateWxRefundCount) {
		this.cumulateWxRefundCount = cumulateWxRefundCount;
	}

	public int getCumulateAliRefundCount() {
		return cumulateAliRefundCount;
	}

	public void setCumulateAliRefundCount(int cumulateAliRefundCount) {
		this.cumulateAliRefundCount = cumulateAliRefundCount;
	}

	public long getCumulateWxRefundAmount() {
		return cumulateWxRefundAmount;
	}

	public void setCumulateWxRefundAmount(long cumulateWxRefundAmount) {
		this.cumulateWxRefundAmount = cumulateWxRefundAmount;
	}

	public long getCumulateAliRefundAmount() {
		return cumulateAliRefundAmount;
	}

	public void setCumulateAliRefundAmount(long cumulateAliRefundAmount) {
		this.cumulateAliRefundAmount = cumulateAliRefundAmount;
	}

	public long getCumulateRefundAmount() {
		return cumulateRefundAmount;
	}

	public void setCumulateRefundAmount(long cumulateRefundAmount) {
		this.cumulateRefundAmount = cumulateRefundAmount;
	}

	public int getCumulateRefundCount() {
		return cumulateRefundCount;
	}

	public void setCumulateRefundCount(int cumulateRefundCount) {
		this.cumulateRefundCount = cumulateRefundCount;
	}
	
	public int getMonthWxDutyNoPayCount() {
		return monthWxDutyNoPayCount;
	}

	public void setMonthWxDutyNoPayCount(int monthWxDutyNoPayCount) {
		this.monthWxDutyNoPayCount = monthWxDutyNoPayCount;
	}

	public int getMonthAliDutyNoPayCount() {
		return monthAliDutyNoPayCount;
	}

	public void setMonthAliDutyNoPayCount(int monthAliDutyNoPayCount) {
		this.monthAliDutyNoPayCount = monthAliDutyNoPayCount;
	}

	public int getMonthWxAppointmentNoPayCount() {
		return monthWxAppointmentNoPayCount;
	}

	public void setMonthWxAppointmentNoPayCount(int monthWxAppointmentNoPayCount) {
		this.monthWxAppointmentNoPayCount = monthWxAppointmentNoPayCount;
	}

	public int getMonthAliAppointmentNoPayCount() {
		return monthAliAppointmentNoPayCount;
	}

	public void setMonthAliAppointmentNoPayCount(int monthAliAppointmentNoPayCount) {
		this.monthAliAppointmentNoPayCount = monthAliAppointmentNoPayCount;
	}

	public int getMonthWxNoPayCount() {
		return monthWxNoPayCount;
	}

	public void setMonthWxNoPayCount(int monthWxNoPayCount) {
		this.monthWxNoPayCount = monthWxNoPayCount;
	}

	public int getMonthAliNoPayCount() {
		return monthAliNoPayCount;
	}

	public void setMonthAliNoPayCount(int monthAliNoPayCount) {
		this.monthAliNoPayCount = monthAliNoPayCount;
	}

	public int getCumulateWxDutyNoPayCount() {
		return cumulateWxDutyNoPayCount;
	}

	public void setCumulateWxDutyNoPayCount(int cumulateWxDutyNoPayCount) {
		this.cumulateWxDutyNoPayCount = cumulateWxDutyNoPayCount;
	}

	public int getCumulateAliDutyNoPayCount() {
		return cumulateAliDutyNoPayCount;
	}

	public void setCumulateAliDutyNoPayCount(int cumulateAliDutyNoPayCount) {
		this.cumulateAliDutyNoPayCount = cumulateAliDutyNoPayCount;
	}

	public int getCumulateWxAppointmentNoPayCount() {
		return cumulateWxAppointmentNoPayCount;
	}

	public void setCumulateWxAppointmentNoPayCount(int cumulateWxAppointmentNoPayCount) {
		this.cumulateWxAppointmentNoPayCount = cumulateWxAppointmentNoPayCount;
	}

	public int getCumulateAliAppointmentNoPayCount() {
		return cumulateAliAppointmentNoPayCount;
	}

	public void setCumulateAliAppointmentNoPayCount(int cumulateAliAppointmentNoPayCount) {
		this.cumulateAliAppointmentNoPayCount = cumulateAliAppointmentNoPayCount;
	}

	public int getCumulateWxNoPayCount() {
		return cumulateWxNoPayCount;
	}

	public void setCumulateWxNoPayCount(int cumulateWxNoPayCount) {
		this.cumulateWxNoPayCount = cumulateWxNoPayCount;
	}

	public int getCumulateAliNoPayCount() {
		return cumulateAliNoPayCount;
	}

	public void setCumulateAliNoPayCount(int cumulateAliNoPayCount) {
		this.cumulateAliNoPayCount = cumulateAliNoPayCount;
	}

}
