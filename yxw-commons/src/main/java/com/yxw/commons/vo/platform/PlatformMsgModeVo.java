package com.yxw.commons.vo.platform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yxw.commons.entity.platform.hospital.MsgMode;

public class PlatformMsgModeVo implements Serializable {

	private static final long serialVersionUID = -1294335955929092597L;

	private String platformId;
	
	private String platformCode;
	
	private String platformName;
	
	private Integer state;
	
	private Integer targetId;
	
	private String platformSettingsId;
	
	private String appId;
	
	private List<MsgMode> msgModes = new ArrayList<>();

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

	public List<MsgMode> getMsgModes() {
		return msgModes;
	}

	public void setMsgModes(List<MsgMode> msgModes) {
		this.msgModes = msgModes;
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
