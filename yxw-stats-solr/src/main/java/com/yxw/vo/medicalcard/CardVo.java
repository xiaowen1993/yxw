package com.yxw.vo.medicalcard;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class CardVo implements Serializable {

	private static final long serialVersionUID = 966523278968210387L;

	@Field
	private String id;
	
	@Field
	private String hospitalId;
	
	@Field
	private String hospitalCode;
	
	@Field
	private String hospitalName;
	
	@Field
	private String branchId;
	
	@Field
	private String branchName;
	
	@Field
	private String branchCode;
	
	@Field
	private String areaCode;
	
	@Field
	private String areaName;
	
	@Field
	private Long count = 0L;
	
	@Field
	private Long wechatCount = 0L;
	
	@Field
	private Long alipayCount = 0L;
	
	@Field
	private Integer platformType;
	
	@Field
	private String statsDate;

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

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getWechatCount() {
		return wechatCount;
	}

	public void setWechatCount(Long wechatCount) {
		this.wechatCount = wechatCount;
	}

	public Long getAlipayCount() {
		return alipayCount;
	}

	public void setAlipayCount(Long alipayCount) {
		this.alipayCount = alipayCount;
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
	
	public void combineEntity(CardVo vo) {
		setCount(count + vo.getCount());
		setWechatCount(wechatCount + vo.getWechatCount());
		setAlipayCount(alipayCount + vo.getAlipayCount());
	}
}
