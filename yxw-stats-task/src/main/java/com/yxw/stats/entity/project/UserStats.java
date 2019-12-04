package com.yxw.stats.entity.project;

import java.io.Serializable;
import java.util.Date;

public class UserStats implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7799990198351695263L;

	private String id;

	private String appid;

	private String hospitalid;

	private Long newuser;

	private Long canceluser;

	private Long cumulateuser;

	private Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid == null ? null : appid.trim();
	}

	public String getHospitalid() {
		return hospitalid;
	}

	public void setHospitalid(String hospitalid) {
		this.hospitalid = hospitalid == null ? null : hospitalid.trim();
	}

	public Long getNewuser() {
		return newuser;
	}

	public void setNewuser(Long newuser) {
		this.newuser = newuser;
	}

	public Long getCanceluser() {
		return canceluser;
	}

	public void setCanceluser(Long canceluser) {
		this.canceluser = canceluser;
	}

	public Long getCumulateuser() {
		return cumulateuser;
	}

	public void setCumulateuser(Long cumulateuser) {
		this.cumulateuser = cumulateuser;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}