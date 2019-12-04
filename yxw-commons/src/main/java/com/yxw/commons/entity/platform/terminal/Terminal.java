package com.yxw.commons.entity.platform.terminal;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

@Entity(name = "terminal")
public class Terminal extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4537780539625998036L;

	private String deviceId;
	private String hospitalId;
	private String code; //终端机设备编码
	private String position; //终端机摆放位置
	private String appVersion; //终端机当前安装软件的版本号
	private int state; //1启用，0停用，默认0
	private String note; //备注

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
