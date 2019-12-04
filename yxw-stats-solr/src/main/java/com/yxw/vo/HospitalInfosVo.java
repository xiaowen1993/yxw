package com.yxw.vo;

import com.yxw.base.entity.BaseEntity;

public class HospitalInfosVo extends BaseEntity {

	private static final long serialVersionUID = 4591057264583256023L;
	
	/**
	 * 关系ID
	 */
	private String id;
	
	/**
	 * 医院ID
	 */
	private String hospitalId;

	/**
	 * 医院名称
	 */
	private String hospitalName;
	
	/**
	 * 医院代码
	 */
	private String hospitalCode;
	
	/**
	 * 平台名称
	 */
	private String platformName;
	
	/**
	 * 平台代码
	 */
	private String platformCode;
	
	/**
	 * 支付名
	 */
	private String tradeName;
	
	/**
	 * 支付方式
	 */
	private String tradeMode;
	
	/**
	 * appId
	 */
	private String appId;
	
	/**
	 * 类别，默认是0，无分类
	 * 1、微信
	 * 2、支付宝
	 * 代码中，目前对isQiaoQiao上的，只在微信、支付宝中走。以后如果有医保的话，此处请添加
	 */
	private Integer category;
	
	private String areaCode;
	
	private String areaName;
	
	private Integer state;

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
