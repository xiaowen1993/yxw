package com.yxw.vo.subscribe;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class SubscribeStats implements Serializable {

	private static final long serialVersionUID = -7827707479052534805L;

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
	private long thisMonthWxIncCount;
	
	/**
	 * 上月微信新增数
	 */
	@Field
	private long lastMonthWxIncCount;
	
	/**
	 * 本月微信取消关注数量
	 */
	@Field
	private long thisMonthWxCancelCount;
	
	/**
	 *上月微信取消关注数量
	 */
	@Field
	private long lastMonthWxCancelCount;
	
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
	 * 上月支付宝新增数
	 */
	@Field
	private long lastMonthAliIncCount;
	
	/**
	 * 本月微信取消关注数量
	 */
	@Field
	private long thisMonthAliCancelCount;
	
	/**
	 *上月微信取消关注数量
	 */
	@Field
	private long lastMonthAliCancelCount;
	
	/**
	 * 微信至该月钱累计的关注数量
	 */
	@Field
	private long aliCumulateCountTillThisMonth;

	/**
	 * 微信增长数 （较上月）
	 */
	@Field
	private long wxIncCount;
	
	@Field
	private long wxCancelCount;
	
	@Field
	private String wxCancelRate = "-";
	
	/**
	 * 支付宝增长数 （较上月）
	 */
	@Field
	private long aliIncCount;
	
	@Field
	private long aliCanelCount;
	
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

	public long getThisMonthWxIncCount() {
		return thisMonthWxIncCount;
	}

	public void setThisMonthWxIncCount(long thisMonthWxIncCount) {
		this.thisMonthWxIncCount = thisMonthWxIncCount;
	}

	public long getLastMonthWxIncCount() {
		return lastMonthWxIncCount;
	}

	public void setLastMonthWxIncCount(long lastMonthWxIncCount) {
		this.lastMonthWxIncCount = lastMonthWxIncCount;
	}

	public long getThisMonthAliIncCount() {
		return thisMonthAliIncCount;
	}

	public void setThisMonthAliIncCount(long thisMonthAliIncCount) {
		this.thisMonthAliIncCount = thisMonthAliIncCount;
	}

	public long getLastMonthAliIncCount() {
		return lastMonthAliIncCount;
	}

	public void setLastMonthAliIncCount(long lastMonthAliIncCount) {
		this.lastMonthAliIncCount = lastMonthAliIncCount;
	}

	public long getWxIncCount() {
		return wxIncCount;
	}

	public void setWxIncCount(long wxIncCount) {
		this.wxIncCount = wxIncCount;
	}

	public long getAliIncCount() {
		return aliIncCount;
	}

	public void setAliIncCount(long aliIncCount) {
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
	
	public long getThisMonthWxCancelCount() {
		return thisMonthWxCancelCount;
	}

	public void setThisMonthWxCancelCount(long thisMonthWxCancelCount) {
		this.thisMonthWxCancelCount = thisMonthWxCancelCount;
	}

	public long getLastMonthWxCancelCount() {
		return lastMonthWxCancelCount;
	}

	public void setLastMonthWxCancelCount(long lastMonthWxCancelCount) {
		this.lastMonthWxCancelCount = lastMonthWxCancelCount;
	}

	public long getWxCumulateCountTillThisMonth() {
		return wxCumulateCountTillThisMonth;
	}

	public void setWxCumulateCountTillThisMonth(long wxCumulateCountTillThisMonth) {
		this.wxCumulateCountTillThisMonth = wxCumulateCountTillThisMonth;
	}

	public long getThisMonthAliCancelCount() {
		return thisMonthAliCancelCount;
	}

	public void setThisMonthAliCancelCount(long thisMonthAliCancelCount) {
		this.thisMonthAliCancelCount = thisMonthAliCancelCount;
	}

	public long getLastMonthAliCancelCount() {
		return lastMonthAliCancelCount;
	}

	public void setLastMonthAliCancelCount(long lastMonthAliCancelCount) {
		this.lastMonthAliCancelCount = lastMonthAliCancelCount;
	}

	public long getAliCumulateCountTillThisMonth() {
		return aliCumulateCountTillThisMonth;
	}

	public void setAliCumulateCountTillThisMonth(long aliCumulateCountTillThisMonth) {
		this.aliCumulateCountTillThisMonth = aliCumulateCountTillThisMonth;
	}

	public String getAliCancelRate() {
		return aliCancelRate;
	}

	public void setAliCancelRate(String aliCancelRate) {
		this.aliCancelRate = aliCancelRate;
	}

	public long getAliCanelCount() {
		return aliCanelCount;
	}

	public void setAliCanelCount(long aliCanelCount) {
		this.aliCanelCount = aliCanelCount;
	}

	public String getWxCancelRate() {
		return wxCancelRate;
	}

	public void setWxCancelRate(String wxCancelRate) {
		this.wxCancelRate = wxCancelRate;
	}

	public long getWxCancelCount() {
		return wxCancelCount;
	}

	public void setWxCancelCount(long wxCancelCount) {
		this.wxCancelCount = wxCancelCount;
	}
}
