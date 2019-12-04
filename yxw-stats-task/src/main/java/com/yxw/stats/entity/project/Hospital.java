package com.yxw.stats.entity.project;

import java.io.Serializable;
import java.util.Date;

public class Hospital implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8146601330938824517L;

	private Long id;

	private Date version;

	private String name;

	private String simplename;

	private String accesskey;

	private String accessname;

	private String appid;

	private String platformtype;

	private String hospitaltel;

	private String hospitaladdress;

	private Integer amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getSimplename() {
		return simplename;
	}

	public void setSimplename(String simplename) {
		this.simplename = simplename == null ? null : simplename.trim();
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey == null ? null : accesskey.trim();
	}

	public String getAccessname() {
		return accessname;
	}

	public void setAccessname(String accessname) {
		this.accessname = accessname == null ? null : accessname.trim();
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid == null ? null : appid.trim();
	}

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype == null ? null : platformtype.trim();
	}

	public String getHospitaltel() {
		return hospitaltel;
	}

	public void setHospitaltel(String hospitaltel) {
		this.hospitaltel = hospitaltel == null ? null : hospitaltel.trim();
	}

	public String getHospitaladdress() {
		return hospitaladdress;
	}

	public void setHospitaladdress(String hospitaladdress) {
		this.hospitaladdress = hospitaladdress == null ? null : hospitaladdress.trim();
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}