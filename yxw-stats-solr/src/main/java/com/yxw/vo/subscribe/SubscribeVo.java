package com.yxw.vo.subscribe;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class SubscribeVo implements Serializable {

	private static final long serialVersionUID = -3343116765614014778L;

	@Field
	private String id;
	
	@Field
	private String hospitalId;
	
	@Field
	private String hospitalCode;
	
	@Field
	private String hospitalName;
	
	@Field
	private String statsDate;
	
	@Field
	private String appId;
	
	@Field
	private Integer platform;
	
	@Field
	private Long cancelCount;
	
	@Field
	private Long cumulateCount;
	
	@Field
	private Long increaseCount;
	
	@Field
	private Integer platformType;
	
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

	public String getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Long getCancelCount() {
		return cancelCount;
	}

	public void setCancelCount(Long cancelCount) {
		this.cancelCount = cancelCount;
	}

	public Long getCumulateCount() {
		return cumulateCount;
	}

	public void setCumulateCount(Long cumulateCount) {
		this.cumulateCount = cumulateCount;
	}

	public Long getIncreaseCount() {
		return increaseCount;
	}

	public void setIncreaseCount(Long increaseCount) {
		this.increaseCount = increaseCount;
	}

	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}
	
}
