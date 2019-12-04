package com.yxw.vo.deposit;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class DepositStats implements Serializable {

	private static final long serialVersionUID = -5340714594949994491L;

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
	
	@Field
	private int monthPayCount;
	
	@Field
	private int monthNoPayCount;
	
	@Field
	private int monthRefundCount;
	
	@Field
	private int monthPartRefundCount;
	
	@Field
	private int monthWxRefundCount;
	
	@Field
	private int monthAliRefundCount;
	
	@Field
	private int monthWxPayCount;
	
	@Field
	private int monthAliPayCount;
	
	@Field
	private int monthWxPartRefundCount;
	
	@Field
	private int monthAliPartRefundCount;
	
	@Field
	private int monthWxNoPayCount;
	
	@Field
	private int monthAliNoPayCount;
	
	@Field
	private long monthPayAmount;
	
	@Field
	private long monthWxPayAmount;
	
	@Field
	private long monthAliPayAmount;
	
	@Field
	private long monthRefundAmount;
	
	@Field
	private long monthWxRefundAmount;
	
	@Field
	private long monthAliRefundAmount;
	
	@Field
	private long monthPartRefundAmount;
	
	@Field
	private long monthWxPartRefundAmount;
	
	@Field
	private long monthAliPartRefundAmount;
	
	@Field
	private int cumulateTotalCount;
	
	@Field
	private int cumulateNoPayCount;
	
	@Field
	private int cumulateRefundCount;
	
	@Field
	private int cumulatePartRefundCount;
	
	@Field
	private int cumulateWxRefundCount;
	
	@Field
	private int cumulateAliRefundCount;
	
	@Field
	private int cumulateWxTotalCount;
	
	@Field
	private int cumulateAliTotalCount;
	
	@Field
	private int cumulateWxPartRefundCount;
	
	@Field
	private int cumulateAliPartRefundCount;
	
	@Field
	private int cumulateWxNoPayCount;
	
	@Field
	private int cumulateAliNoPayCount;
	
	@Field
	private long cumulateTotalAmount; 
	
	@Field
	private long cumulateWxTotalAmount;
	
	@Field
	private long cumulateAliTotalAmount;
	
	@Field
	private long cumulateRefundAmount;
	
	@Field
	private long cumulateWxRefundAmount;
	
	@Field
	private long cumulateAliRefundAmount;
	
	@Field
	private long cumulatePartRefundAmount;
	
	@Field
	private long cumulateWxPartRefundAmount;
	
	@Field
	private long cumulateAliPartRefundAmount;

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

	public int getMonthPayCount() {
		return monthPayCount;
	}

	public void setMonthPayCount(int monthPayCount) {
		this.monthPayCount = monthPayCount;
	}

	public int getMonthNoPayCount() {
		return monthNoPayCount;
	}

	public void setMonthNoPayCount(int monthNoPayCount) {
		this.monthNoPayCount = monthNoPayCount;
	}

	public int getMonthRefundCount() {
		return monthRefundCount;
	}

	public void setMonthRefundCount(int monthRefundCount) {
		this.monthRefundCount = monthRefundCount;
	}

	public int getMonthPartRefundCount() {
		return monthPartRefundCount;
	}

	public void setMonthPartRefundCount(int monthPartRefundCount) {
		this.monthPartRefundCount = monthPartRefundCount;
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

	public int getMonthWxPayCount() {
		return monthWxPayCount;
	}

	public void setMonthWxPayCount(int monthWxPayCount) {
		this.monthWxPayCount = monthWxPayCount;
	}

	public int getMonthAliPayCount() {
		return monthAliPayCount;
	}

	public void setMonthAliPayCount(int monthAliPayCount) {
		this.monthAliPayCount = monthAliPayCount;
	}

	public int getMonthWxPartRefundCount() {
		return monthWxPartRefundCount;
	}

	public void setMonthWxPartRefundCount(int monthWxPartRefundCount) {
		this.monthWxPartRefundCount = monthWxPartRefundCount;
	}

	public int getMonthAliPartRefundCount() {
		return monthAliPartRefundCount;
	}

	public void setMonthAliPartRefundCount(int monthAliPartRefundCount) {
		this.monthAliPartRefundCount = monthAliPartRefundCount;
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

	public long getMonthPayAmount() {
		return monthPayAmount;
	}

	public void setMonthPayAmount(long monthPayAmount) {
		this.monthPayAmount = monthPayAmount;
	}

	public long getMonthWxPayAmount() {
		return monthWxPayAmount;
	}

	public void setMonthWxPayAmount(long monthWxPayAmount) {
		this.monthWxPayAmount = monthWxPayAmount;
	}

	public long getMonthAliPayAmount() {
		return monthAliPayAmount;
	}

	public void setMonthAliPayAmount(long monthAliPayAmount) {
		this.monthAliPayAmount = monthAliPayAmount;
	}

	public long getMonthRefundAmount() {
		return monthRefundAmount;
	}

	public void setMonthRefundAmount(long monthRefundAmount) {
		this.monthRefundAmount = monthRefundAmount;
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

	public long getMonthPartRefundAmount() {
		return monthPartRefundAmount;
	}

	public void setMonthPartRefundAmount(long monthPartRefundAmount) {
		this.monthPartRefundAmount = monthPartRefundAmount;
	}

	public long getMonthWxPartRefundAmount() {
		return monthWxPartRefundAmount;
	}

	public void setMonthWxPartRefundAmount(long monthWxPartRefundAmount) {
		this.monthWxPartRefundAmount = monthWxPartRefundAmount;
	}

	public long getMonthAliPartRefundAmount() {
		return monthAliPartRefundAmount;
	}

	public void setMonthAliPartRefundAmount(long monthAliPartRefundAmount) {
		this.monthAliPartRefundAmount = monthAliPartRefundAmount;
	}

	public int getCumulateTotalCount() {
		return cumulateTotalCount;
	}

	public void setCumulateTotalCount(int cumulateTotalCount) {
		this.cumulateTotalCount = cumulateTotalCount;
	}

	public int getCumulateNoPayCount() {
		return cumulateNoPayCount;
	}

	public void setCumulateNoPayCount(int cumulateNoPayCount) {
		this.cumulateNoPayCount = cumulateNoPayCount;
	}

	public int getCumulateRefundCount() {
		return cumulateRefundCount;
	}

	public void setCumulateRefundCount(int cumulateRefundCount) {
		this.cumulateRefundCount = cumulateRefundCount;
	}

	public int getCumulatePartRefundCount() {
		return cumulatePartRefundCount;
	}

	public void setCumulatePartRefundCount(int cumulatePartRefundCount) {
		this.cumulatePartRefundCount = cumulatePartRefundCount;
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

	public int getCumulateWxPartRefundCount() {
		return cumulateWxPartRefundCount;
	}

	public void setCumulateWxPartRefundCount(int cumulateWxPartRefundCount) {
		this.cumulateWxPartRefundCount = cumulateWxPartRefundCount;
	}

	public int getCumulateAliPartRefundCount() {
		return cumulateAliPartRefundCount;
	}

	public void setCumulateAliPartRefundCount(int cumulateAliPartRefundCount) {
		this.cumulateAliPartRefundCount = cumulateAliPartRefundCount;
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

	public long getCumulateTotalAmount() {
		return cumulateTotalAmount;
	}

	public void setCumulateTotalAmount(long cumulateTotalAmount) {
		this.cumulateTotalAmount = cumulateTotalAmount;
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

	public long getCumulateRefundAmount() {
		return cumulateRefundAmount;
	}

	public void setCumulateRefundAmount(long cumulateRefundAmount) {
		this.cumulateRefundAmount = cumulateRefundAmount;
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

	public long getCumulatePartRefundAmount() {
		return cumulatePartRefundAmount;
	}

	public void setCumulatePartRefundAmount(long cumulatePartRefundAmount) {
		this.cumulatePartRefundAmount = cumulatePartRefundAmount;
	}

	public long getCumulateWxPartRefundAmount() {
		return cumulateWxPartRefundAmount;
	}

	public void setCumulateWxPartRefundAmount(long cumulateWxPartRefundAmount) {
		this.cumulateWxPartRefundAmount = cumulateWxPartRefundAmount;
	}

	public long getCumulateAliPartRefundAmount() {
		return cumulateAliPartRefundAmount;
	}

	public void setCumulateAliPartRefundAmount(long cumulateAliPartRefundAmount) {
		this.cumulateAliPartRefundAmount = cumulateAliPartRefundAmount;
	}
}
