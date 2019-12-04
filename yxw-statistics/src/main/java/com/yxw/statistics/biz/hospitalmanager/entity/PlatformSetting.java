package com.yxw.statistics.biz.hospitalmanager.entity;

import com.yxw.base.entity.BaseEntity;

public class PlatformSetting extends BaseEntity {

	private static final long serialVersionUID = 942483479964655063L;
	
	private String hospitalId;
	
	private String platformId;
	
	private String tradeId;
	
	private String appId;

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

}
