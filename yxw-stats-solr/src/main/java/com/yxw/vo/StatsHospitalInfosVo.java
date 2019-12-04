package com.yxw.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.yxw.constants.PlatformConstant;

public class StatsHospitalInfosVo implements Serializable {

	private static final long serialVersionUID = 1845915774024606432L;

	private String hospitalName;
	
	private String hospitalCode;
	
	private String branchName;
	
	private String branchCode;
	
	private String platformCode;
	
	private String platformName;
	
	private String tradeName;
	
	private String tradeMode;
	
	private String areaCode;
	
	private String areaName;
	
	private String appId;
	
	private Integer state;

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

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
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

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public Integer getPlatformType() {
		int platformType = -1;
		
		if (StringUtils.isNotBlank(this.getPlatformCode())) {
			if (platformCode.equals(PlatformConstant.PLATFORM_STANDARD_WECHAT_CODE) ||
					platformCode.equals(PlatformConstant.PLATFORM_STANDARD_ALIPAY_CODE)) {
				platformType = PlatformConstant.PLATFORM_TYPE_STD;
			} else if (platformCode.equals(PlatformConstant.PLATFORM_HIS_WECHAT_CODE) ||
					platformCode.equals(PlatformConstant.PLATFORM_HIS_ALIPAY_CODE)) {
				platformType = PlatformConstant.PLATFORM_TYPE_HIS;
			}
		}
		
		return platformType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
