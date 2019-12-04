package com.yxw.solr.vo.medicalcard;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class CardStats implements Serializable {
	private static final long serialVersionUID = 5639085041886496485L;

	/**
	 * 新增总数
	 */
	@Field
	private int newBindQuantity;
	
	/**
	 * 解绑总数
	 */
	@Field
	private int unBindQuantity;
	
	/**
	 * 净增总数
	 */
	@Field
	private int netIncreasedQuantity;
	
	/**
	 * 总绑卡数
	 */
	@Field
	private int totalQuantity;
	
	/**
	 * 累计绑卡数
	 */
	@Field
	private int cumulativeQuantity;
	
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
	 * 统计日期
	 */
	@Field
	private String statsDate;
	
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

	public String getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
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

	public void merge(CardStats stats){
		this.setNewBindQuantity(this.newBindQuantity + stats.getNewBindQuantity());
		this.setCumulativeQuantity(this.cumulativeQuantity + stats.getCumulativeQuantity());
		this.setNetIncreasedQuantity(this.netIncreasedQuantity + stats.getNetIncreasedQuantity());
		this.setTotalQuantity(this.totalQuantity + stats.getTotalQuantity());
		this.setUnBindQuantity(this.unBindQuantity + stats.getUnBindQuantity());
	}

	public int getNewBindQuantity() {
		return newBindQuantity;
	}

	public void setNewBindQuantity(int newBindQuantity) {
		this.newBindQuantity = newBindQuantity;
	}

	public int getUnBindQuantity() {
		return unBindQuantity;
	}

	public void setUnBindQuantity(int unBindQuantity) {
		this.unBindQuantity = unBindQuantity;
	}

	public int getNetIncreasedQuantity() {
		this.netIncreasedQuantity = this.newBindQuantity - this.unBindQuantity;
		return netIncreasedQuantity;
	}

	public void setNetIncreasedQuantity(int netIncreasedQuantity) {
		this.netIncreasedQuantity = netIncreasedQuantity;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public int getCumulativeQuantity() {
		return cumulativeQuantity;
	}

	public void setCumulativeQuantity(int cumulativeQuantity) {
		this.cumulativeQuantity = cumulativeQuantity;
	}
}
