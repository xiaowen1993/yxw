package com.yxw.solr.vo.outside;

import java.io.Serializable;

public class RegOrderParams implements Serializable {

	private static final long serialVersionUID = 8863681867867040870L;

	/**
	 * 微信/支付宝/健康易 ... appid
	 * 
	 * ** 必要
	 */
	private String appId;
	
	/**
	 * 0：所有
	 * 1：预约
	 * 2：当天
	 * 
	 * ** 必要
	 */
	private Integer regType;
	
	/**
	 * 0： 所有
	 * 1：微信
	 * 2：支付宝
	 * 3：健康易
	 * 
	 * ** 必要
	 * 
	 * ** 备注: 这里的tradeMode需要进行转换，按照isQiaoQiaoShang字段来进行。
	 * ** 	        如果=0 则不转换，=1，则进行转换同时查询多个平台
	 */
	private Integer tradeMode;
	
	/**
	 * 分院代码
	 * 
	 * ** 非必要
	 */
	private String branchCode;
	
	/**
	 * 开始时间	格式：yyyy-MM-dd
	 * 
	 * ** 必要
	 */
	private String startDate;
	
	/**
	 * 结束时间	格式：yyyy-MM-dd
	 * 
	 * ** 必要
	 */
	private String endDate;
	
	/**
	 * 0: 不悄悄上，就是帮主要光明正大"上"
	 * 1: 悄悄上，鸡贼
	 */
	private Integer isQiaoQiaoShang;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(Integer tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Integer getIsQiaoQiaoShang() {
		return isQiaoQiaoShang;
	}

	public void setIsQiaoQiaoShang(Integer isQiaoQiaoShang) {
		this.isQiaoQiaoShang = isQiaoQiaoShang;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
