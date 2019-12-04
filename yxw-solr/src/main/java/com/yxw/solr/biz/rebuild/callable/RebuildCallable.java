package com.yxw.solr.biz.rebuild.callable;

import java.util.concurrent.Callable;

public abstract class RebuildCallable implements Callable<String> {

	private Integer platform;
	
	private String hospitalCode;
	
	private String branchCode;
	
	private String beginDate;
	
	public RebuildCallable (Integer platform, String hospitalCode, String branchCode, String statsDate) {
		super();
		this.platform = platform;
		this.hospitalCode = hospitalCode;
		this.branchCode = branchCode;
		this.beginDate = statsDate;
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getStatsDate() {
		return beginDate;
	}

	public void setStatsDate(String statsDate) {
		this.beginDate = statsDate;
	}
	
}
