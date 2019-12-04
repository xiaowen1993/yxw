package com.yxw.commons.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yxw.commons.entity.platform.hospital.Optional;

/**
 * 医院-平台-功能列表
 * 功能概要：
 * @author Administrator
 * @date 2017年5月31日
 */
public class PlatformOptionalVo implements Serializable {

	private static final long serialVersionUID = 9109859612637659337L;
	
	private String hospitalId;
	
	private String hospitalCode;
	
	private String hospitalName;
	
	private String platformSettingsId;
	
	private String platformCode;
	
	private String platformId;
	
	private String platformName;
	
	private String platformSettingsOptionalId;
	
	private List<Optional> optionals = new ArrayList<>();
	
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

	public String getPlatformSettingsId() {
		return platformSettingsId;
	}

	public void setPlatformSettingsId(String platformSettingsId) {
		this.platformSettingsId = platformSettingsId;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public List<Optional> getOptionals() {
		return optionals;
	}

	public void setOptionals(List<Optional> optionals) {
		this.optionals = optionals;
	}

	public String getPlatformSettingsOptionalId() {
		return platformSettingsOptionalId;
	}

	public void setPlatformSettingsOptionalId(String platformSettingsOptionalId) {
		this.platformSettingsOptionalId = platformSettingsOptionalId;
	}
}
