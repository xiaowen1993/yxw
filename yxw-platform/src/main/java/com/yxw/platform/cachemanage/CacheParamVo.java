package com.yxw.platform.cachemanage;

import java.io.Serializable;

public class CacheParamVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1574472994893532627L;

	/**
	 * 医院id
	 */
	private String hospitalId;
	private String hospitalName;
	private String hospitalCode;
	private String branchCode;
	private String cacheType;
	private String regType;

	public CacheParamVo(String hospitalId, String hospitalName, String hospitalCode, String branchCode, String cacheType, String regType) {
		super();
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
		this.hospitalCode = hospitalCode;
		this.branchCode = branchCode;
		this.cacheType = cacheType;
		this.regType = regType;
	}

	public CacheParamVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
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

	public String getCacheType() {
		return cacheType;
	}

	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}
	
}
