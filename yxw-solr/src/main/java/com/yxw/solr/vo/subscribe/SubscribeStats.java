package com.yxw.solr.vo.subscribe;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class SubscribeStats implements Serializable {

	private static final long serialVersionUID = -7827707479052534805L;

	/**
	 * id
	 */
	@Field
	private String id;
	
	@Field
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
	 * 微信AppId
	 */
	@Field
	private String wxAppId = "-";
	
	/**
	 * 支付宝AppId
	 */
	@Field
	private String aliAppId = "-";
	
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
	 * 本月的年月 
	 * 格式：YYYY-MM
	 */
	@Field
	private String thisMonth;
	
	/**
	 * 上月的年月
	 * 格式：YYYY-MM
	 */
	@Field
	private String lastMonth;
	
	/**
	 * 本月微信新增数
	 */
	@Field
	private double thisMonthWxIncCount;
	
	/**
	 * 上月微信新增数
	 */
	@Field
	private double lastMonthWxIncCount;
	
	/**
	 * 本月微信取消关注数量
	 */
	@Field
	private double thisMonthWxCancelCount;
	
	/**
	 *上月微信取消关注数量
	 */
	@Field
	private double lastMonthWxCancelCount;
	
	/**
	 * 微信至该月钱累计的关注数量
	 */
	@Field
	private double wxCumulateCountTillThisMonth;
	
	/**
	 * 本月支付宝新增数
	 */
	@Field
	private double thisMonthAliIncCount;
	
	/**
	 * 上月支付宝新增数
	 */
	@Field
	private double lastMonthAliIncCount;
	
	/**
	 * 本月微信取消关注数量
	 */
	@Field
	private double thisMonthAliCancelCount;
	
	/**
	 *上月微信取消关注数量
	 */
	@Field
	private double lastMonthAliCancelCount;
	
	/**
	 * 微信至该月钱累计的关注数量
	 */
	@Field
	private double aliCumulateCountTillThisMonth;

	/**
	 * 微信增长数 （较上月）
	 */
	@Field
	private double wxIncCount;
	
	@Field
	private double wxCancelCount;
	
	@Field
	private String wxCancelRate = "-";
	
	/**
	 * 支付宝增长数 （较上月）
	 */
	@Field
	private double aliIncCount;
	
	@Field
	private double aliCanelCount;
	
	@Field
	private String aliCancelRate = "-";
	
	/**
	 * 微信增长率 （较上月）
	 */
	@Field
	private String wxIncRate = "-";
	
	/**
	 * 支付宝增长率 （较上月）
	 */
	@Field
	private String aliIncRate = "-";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public double getThisMonthWxIncCount() {
		return thisMonthWxIncCount;
	}

	public void setThisMonthWxIncCount(double thisMonthWxIncCount) {
		this.thisMonthWxIncCount = thisMonthWxIncCount;
	}

	public double getLastMonthWxIncCount() {
		return lastMonthWxIncCount;
	}

	public void setLastMonthWxIncCount(double lastMonthWxIncCount) {
		this.lastMonthWxIncCount = lastMonthWxIncCount;
	}

	public double getThisMonthAliIncCount() {
		return thisMonthAliIncCount;
	}

	public void setThisMonthAliIncCount(double thisMonthAliIncCount) {
		this.thisMonthAliIncCount = thisMonthAliIncCount;
	}

	public double getLastMonthAliIncCount() {
		return lastMonthAliIncCount;
	}

	public void setLastMonthAliIncCount(double lastMonthAliIncCount) {
		this.lastMonthAliIncCount = lastMonthAliIncCount;
	}

	public double getWxIncCount() {
		return wxIncCount;
	}

	public void setWxIncCount(double wxIncCount) {
		this.wxIncCount = wxIncCount;
	}

	public double getAliIncCount() {
		return aliIncCount;
	}

	public void setAliIncCount(double aliIncCount) {
		this.aliIncCount = aliIncCount;
	}

	public String getWxIncRate() {
		return wxIncRate;
	}

	public void setWxIncRate(String wxIncRate) {
		this.wxIncRate = wxIncRate;
	}

	public String getAliIncRate() {
		return aliIncRate;
	}

	public void setAliIncRate(String aliIncRate) {
		this.aliIncRate = aliIncRate;
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

	public String getWxAppId() {
		return wxAppId;
	}

	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}

	public String getAliAppId() {
		return aliAppId;
	}

	public void setAliAppId(String aliAppId) {
		this.aliAppId = aliAppId;
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

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	
	public double getThisMonthWxCancelCount() {
		return thisMonthWxCancelCount;
	}

	public void setThisMonthWxCancelCount(double thisMonthWxCancelCount) {
		this.thisMonthWxCancelCount = thisMonthWxCancelCount;
	}

	public double getLastMonthWxCancelCount() {
		return lastMonthWxCancelCount;
	}

	public void setLastMonthWxCancelCount(double lastMonthWxCancelCount) {
		this.lastMonthWxCancelCount = lastMonthWxCancelCount;
	}

	public double getWxCumulateCountTillThisMonth() {
		return wxCumulateCountTillThisMonth;
	}

	public void setWxCumulateCountTillThisMonth(double wxCumulateCountTillThisMonth) {
		this.wxCumulateCountTillThisMonth = wxCumulateCountTillThisMonth;
	}

	public double getThisMonthAliCancelCount() {
		return thisMonthAliCancelCount;
	}

	public void setThisMonthAliCancelCount(double thisMonthAliCancelCount) {
		this.thisMonthAliCancelCount = thisMonthAliCancelCount;
	}

	public double getLastMonthAliCancelCount() {
		return lastMonthAliCancelCount;
	}

	public void setLastMonthAliCancelCount(double lastMonthAliCancelCount) {
		this.lastMonthAliCancelCount = lastMonthAliCancelCount;
	}

	public double getAliCumulateCountTillThisMonth() {
		return aliCumulateCountTillThisMonth;
	}

	public void setAliCumulateCountTillThisMonth(double aliCumulateCountTillThisMonth) {
		this.aliCumulateCountTillThisMonth = aliCumulateCountTillThisMonth;
	}

	public String getAliCancelRate() {
		return aliCancelRate;
	}

	public void setAliCancelRate(String aliCancelRate) {
		this.aliCancelRate = aliCancelRate;
	}

	public double getAliCanelCount() {
		return aliCanelCount;
	}

	public void setAliCanelCount(double aliCanelCount) {
		this.aliCanelCount = aliCanelCount;
	}

	public String getWxCancelRate() {
		return wxCancelRate;
	}

	public void setWxCancelRate(String wxCancelRate) {
		this.wxCancelRate = wxCancelRate;
	}

	public double getWxCancelCount() {
		return wxCancelCount;
	}

	public void setWxCancelCount(double wxCancelCount) {
		this.wxCancelCount = wxCancelCount;
	}
}
