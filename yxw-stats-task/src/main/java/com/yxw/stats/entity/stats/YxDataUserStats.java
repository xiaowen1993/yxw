package com.yxw.stats.entity.stats;

import java.io.Serializable;
import java.util.Date;

public class YxDataUserStats implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8122353997331226303L;

	private String id;

	private String appid;

	private String hospitalid;

	private Long newuser;

	private Long canceluser;

	private Long cumulateuser;

	private Long newuserWechat;

	private Long canceluserWechat;

	private Long cumulateuserWechat;

	private Long newuserAlipay;

	private Long canceluserAlipay;

	private Long cumulateuserAlipay;

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

	public Long getNewuserWechat() {
		return newuserWechat;
	}

	public void setNewuserWechat(Long newuserWechat) {
		this.newuserWechat = newuserWechat;
	}

	public Long getCanceluserWechat() {
		return canceluserWechat;
	}

	public void setCanceluserWechat(Long canceluserWechat) {
		this.canceluserWechat = canceluserWechat;
	}

	public Long getCumulateuserWechat() {
		return cumulateuserWechat;
	}

	public void setCumulateuserWechat(Long cumulateuserWechat) {
		this.cumulateuserWechat = cumulateuserWechat;
	}

	public Long getNewuserAlipay() {
		return newuserAlipay;
	}

	public void setNewuserAlipay(Long newuserAlipay) {
		this.newuserAlipay = newuserAlipay;
	}

	public Long getCanceluserAlipay() {
		return canceluserAlipay;
	}

	public void setCanceluserAlipay(Long canceluserAlipay) {
		this.canceluserAlipay = canceluserAlipay;
	}

	public Long getCumulateuserAlipay() {
		return cumulateuserAlipay;
	}

	public void setCumulateuserAlipay(Long cumulateuserAlipay) {
		this.cumulateuserAlipay = cumulateuserAlipay;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}