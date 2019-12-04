package com.yxw.solr.vo.subscribe;

import java.io.Serializable;

public class SubscribeVo implements Serializable {

	private static final long serialVersionUID = -3343116765614014778L;

	private String id;
	
	private String hospitalId;
	
	private String hospitalCode;
	
	private String hospitalName;
	
	private String statsDate;
	
	private String appId;
	
	private Integer platform;
	
	private Double cancelCount;
	
	private Double cumulateCount;
	
	private Double increaseCount;
	
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

	public Double getCancelCount() {
		return cancelCount;
	}

	public void setCancelCount(Double cancelCount) {
		this.cancelCount = cancelCount;
	}

	public Double getCumulateCount() {
		return cumulateCount;
	}

	public void setCumulateCount(Double cumulateCount) {
		this.cumulateCount = cumulateCount;
	}

	public Double getIncreaseCount() {
		return increaseCount;
	}

	public void setIncreaseCount(Double increaseCount) {
		this.increaseCount = increaseCount;
	}
	
}
