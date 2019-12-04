package com.yxw.stats.entity.stats;

import java.io.Serializable;

public class YxDataSysMedicalCardStatistical implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2684967649887238958L;

	private String id;

	private String date;

	private String hospitalId;

	private String branchId;

	private Integer count;

	private Integer platform;

	private Integer wechatCount;

	private Integer alipayCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date == null ? null : date.trim();
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId == null ? null : hospitalId.trim();
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId == null ? null : branchId.trim();
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getWechatCount() {
		return wechatCount;
	}

	public void setWechatCount(Integer wechatCount) {
		this.wechatCount = wechatCount;
	}

	public Integer getAlipayCount() {
		return alipayCount;
	}

	public void setAlipayCount(Integer alipayCount) {
		this.alipayCount = alipayCount;
	}
}