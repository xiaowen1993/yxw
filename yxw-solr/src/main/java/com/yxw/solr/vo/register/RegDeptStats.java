package com.yxw.solr.vo.register;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class RegDeptStats implements Serializable {

	private static final long serialVersionUID = 2155389955166419198L;

	@Field
	private String id;
	
	/**
	 * 医院代码
	 */
	@Field
	private String hospitalCode;
	
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
	 * 统计科室名称
	 */
	@Field
	private String deptName;
	
	/**
	 * 统计科室代码
	 */
	@Field
	private String deptCode;
	
	/**
	 * 昨天订单量
	 */
	@Field
	private Integer yesterdayQuantity;
	
	/**
	 * 上周订单量
	 */
	@Field
	private Integer lastWeekQuantity;
	
	/**
	 * 本月订单量
	 */
	@Field
	private Integer thisMonthQuantity;
	
	/**
	 * 上月订单量
	 */
	@Field
	private Integer lastMonthQuantity;
	
	/**
	 * 本年订单量
	 */
	@Field
	private Integer thisYearQuantity;
	
	/**
	 * 历史订单量
	 */
	@Field
	private Integer totalQuantity;

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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Integer getYesterdayQuantity() {
		return yesterdayQuantity;
	}

	public void setYesterdayQuantity(Integer yesterdayQuantity) {
		this.yesterdayQuantity = yesterdayQuantity;
	}

	public Integer getLastWeekQuantity() {
		return lastWeekQuantity;
	}

	public void setLastWeekQuantity(Integer lastWeekQuantity) {
		this.lastWeekQuantity = lastWeekQuantity;
	}

	public Integer getThisMonthQuantity() {
		return thisMonthQuantity;
	}

	public void setThisMonthQuantity(Integer thisMonthQuantity) {
		this.thisMonthQuantity = thisMonthQuantity;
	}

	public Integer getLastMonthQuantity() {
		return lastMonthQuantity;
	}

	public void setLastMonthQuantity(Integer lastMonthQuantity) {
		this.lastMonthQuantity = lastMonthQuantity;
	}

	public Integer getThisYearQuantity() {
		return thisYearQuantity;
	}

	public void setThisYearQuantity(Integer thisYearQuantity) {
		this.thisYearQuantity = thisYearQuantity;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
}
