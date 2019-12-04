package com.yxw.solr.vo.subscribe;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class AreaSubscribeStats implements Serializable {

	private static final long serialVersionUID = 4965842656185668904L;

	/**
	 * id
	 */
	@Field
	private String id;
	
	@Field
	private Integer platform;
	
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
	private Long thisMonthWxIncCount;
	
	/**
	 * 上月微信新增数
	 */
	@Field
	private Long lastMonthWxIncCount;
	
	/**
	 * 本月支付宝新增数
	 */
	@Field
	private Long thisMonthAliIncCount;
	
	/**
	 * 上月支付宝新增数
	 */
	@Field
	private Long lastMonthAliIncCount;
	
	/**
	 * 微信增长数 （较上月）
	 */
	@Field
	private Long wxIncCount;
	
	/**
	 * 支付宝增长数 （较上月）
	 */
	@Field
	private Long aliIncCount;
	
	/**
	 * 微信增长率 （较上月）
	 */
	@Field
	private Long wxIncRate;
	
	/**
	 * 支付宝增长率 （较上月）
	 */
	@Field
	private Long aliIncRate;

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

	public Long getThisMonthWxIncCount() {
		return thisMonthWxIncCount;
	}

	public void setThisMonthWxIncCount(Long thisMonthWxIncCount) {
		this.thisMonthWxIncCount = thisMonthWxIncCount;
	}

	public Long getLastMonthWxIncCount() {
		return lastMonthWxIncCount;
	}

	public void setLastMonthWxIncCount(Long lastMonthWxIncCount) {
		this.lastMonthWxIncCount = lastMonthWxIncCount;
	}

	public Long getThisMonthAliIncCount() {
		return thisMonthAliIncCount;
	}

	public void setThisMonthAliIncCount(Long thisMonthAliIncCount) {
		this.thisMonthAliIncCount = thisMonthAliIncCount;
	}

	public Long getLastMonthAliIncCount() {
		return lastMonthAliIncCount;
	}

	public void setLastMonthAliIncCount(Long lastMonthAliIncCount) {
		this.lastMonthAliIncCount = lastMonthAliIncCount;
	}

	public Long getWxIncCount() {
		return wxIncCount;
	}

	public void setWxIncCount(Long wxIncCount) {
		this.wxIncCount = wxIncCount;
	}

	public Long getAliIncCount() {
		return aliIncCount;
	}

	public void setAliIncCount(Long aliIncCount) {
		this.aliIncCount = aliIncCount;
	}

	public Long getWxIncRate() {
		return wxIncRate;
	}

	public void setWxIncRate(Long wxIncRate) {
		this.wxIncRate = wxIncRate;
	}

	public Long getAliIncRate() {
		return aliIncRate;
	}

	public void setAliIncRate(Long aliIncRate) {
		this.aliIncRate = aliIncRate;
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
}
