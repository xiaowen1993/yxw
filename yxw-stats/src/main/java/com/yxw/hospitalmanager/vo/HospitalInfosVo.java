package com.yxw.hospitalmanager.vo;

import java.io.Serializable;

import com.yxw.hospitalmanager.entity.Hospital;

public class HospitalInfosVo implements Serializable {

	private static final long serialVersionUID = 4591057264583256023L;

	private String id;

	private String hospitalId;

	private String hospitalName;

	private String hospitalCode;

	private String areaCode;

	private String areaName;

	private String platformName;

	private String platformCode;

	private String tradeName;

	private String tradeMode;

	private String appId;
	
	private Integer state;

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Hospital convertToHospital() {
		Hospital hospital = new Hospital();
		hospital.setId(this.getHospitalId());
		hospital.setName(this.getHospitalName());
		hospital.setCode(this.getHospitalCode());
		hospital.setAreaCode(this.getAreaCode());
		hospital.setAreaName(this.getAreaName());
		hospital.setState(this.getState());

		return hospital;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
