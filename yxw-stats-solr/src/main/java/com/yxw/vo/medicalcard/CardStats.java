package com.yxw.vo.medicalcard;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class CardStats implements Serializable {

	private static final long serialVersionUID = -3108528599804003374L;

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
	 * 上月的年月 格式：YYYY-MM
	 */
	@Field
	private String lastMonth;

	/**
	 * 本月微信新增数
	 */
	@Field
	private long thisMonthWxIncCount;

	/**
	 * 微信至该月前累计的关注数量
	 */
	@Field
	private long wxCumulateCountTillThisMonth;

	/**
	 * 本月支付宝新增数
	 */
	@Field
	private long thisMonthAliIncCount;

	/**
	 * 微信至该月前累计的绑卡数量
	 */
	@Field
	private long aliCumulateCountTillThisMonth;

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

	public String getLastMonth() {
		return lastMonth;
	}

	public void setLastMonth(String lastMonth) {
		this.lastMonth = lastMonth;
	}

	public long getThisMonthWxIncCount() {
		return thisMonthWxIncCount;
	}

	public void setThisMonthWxIncCount(long thisMonthWxIncCount) {
		this.thisMonthWxIncCount = thisMonthWxIncCount;
	}

	public long getWxCumulateCountTillThisMonth() {
		return wxCumulateCountTillThisMonth;
	}

	public void setWxCumulateCountTillThisMonth(long wxCumulateCountTillThisMonth) {
		this.wxCumulateCountTillThisMonth = wxCumulateCountTillThisMonth;
	}

	public long getThisMonthAliIncCount() {
		return thisMonthAliIncCount;
	}

	public void setThisMonthAliIncCount(long thisMonthAliIncCount) {
		this.thisMonthAliIncCount = thisMonthAliIncCount;
	}

	public long getAliCumulateCountTillThisMonth() {
		return aliCumulateCountTillThisMonth;
	}

	public void setAliCumulateCountTillThisMonth(long aliCumulateCountTillThisMonth) {
		this.aliCumulateCountTillThisMonth = aliCumulateCountTillThisMonth;
	}
}
