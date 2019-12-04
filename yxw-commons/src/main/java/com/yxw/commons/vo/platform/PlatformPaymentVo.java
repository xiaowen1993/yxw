package com.yxw.commons.vo.platform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yxw.commons.entity.platform.hospital.PayMode;

public class PlatformPaymentVo implements Serializable {

	private static final long serialVersionUID = -1294335955929092597L;

	private String platformId;
	
	private String platformCode;
	
	private String platformName;
	
	private Integer targetId;
	
	private Integer state;
	
	private String platformSettingsId;
	
	private String appId;
	
	private List<PayMode> payModes = new ArrayList<>();

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
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

	public List<PayMode> getPayModes() {
		return payModes;
	}

	public void setPayModes(List<PayMode> payModes) {
		this.payModes = payModes;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getPlatformSettingsId() {
		return platformSettingsId;
	}

	public void setPlatformSettingsId(String platformSettingsId) {
		this.platformSettingsId = platformSettingsId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
}
