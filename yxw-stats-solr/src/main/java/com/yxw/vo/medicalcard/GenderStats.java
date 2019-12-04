package com.yxw.vo.medicalcard;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class GenderStats implements Serializable {

	private static final long serialVersionUID = 1560438623104196427L;

	/**
	 * id
	 */
	@Field
	private String id;

	@Field // 标准是1，医院项目是2
	private Integer platform;

	/**
	 * 医院代码
	 */
	@Field
	private String hospitalCode = "-";

	/**
	 * 医院名称
	 */
	@Field
	private String hospitalName = "-";

	/**
	 * 区域代码
	 */
	@Field
	private String areaCode;

	/**
	 * 区域名称
	 */
	@Field
	private String areaName;

	/**
	 * 本月的年月 格式：YYYY-MM
	 */
	@Field
	private String thisMonth;

	/**
	 * 本月微信新增数-男
	 */
	@Field
	private long thisMonthWxMan = 0;
	
	/**
	 * 本月微信新增数-女
	 */
	@Field
	private long thisMonthWxWoman = 0;

	/**
	 * 本月支付宝新增数 - 男
	 */
	@Field
	private long thisMonthAliMan = 0;

	/**
	 * 本月支付宝新增数 - 女
	 */
	@Field
	private long thisMonthAliWoman = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
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

	public String getThisMonth() {
		return thisMonth;
	}

	public void setThisMonth(String thisMonth) {
		this.thisMonth = thisMonth;
	}

	public long getThisMonthWxMan() {
		return thisMonthWxMan;
	}

	public void setThisMonthWxMan(long thisMonthWxMan) {
		this.thisMonthWxMan = thisMonthWxMan;
	}

	public long getThisMonthWxWoman() {
		return thisMonthWxWoman;
	}

	public void setThisMonthWxWoman(long thisMonthWxWoman) {
		this.thisMonthWxWoman = thisMonthWxWoman;
	}

	public long getThisMonthAliMan() {
		return thisMonthAliMan;
	}

	public void setThisMonthAliMan(long thisMonthAliMan) {
		this.thisMonthAliMan = thisMonthAliMan;
	}

	public long getThisMonthAliWoman() {
		return thisMonthAliWoman;
	}

	public void setThisMonthAliWoman(long thisMonthAliWoman) {
		this.thisMonthAliWoman = thisMonthAliWoman;
	}

}
