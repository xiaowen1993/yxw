package com.yxw.interfaces.vo.hospitalized;

import com.yxw.interfaces.vo.Reserve;

public class Detail extends Reserve {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 通用名
	 */
	private String commonName;
	/**
	 * 商品名
	 */
	private String goodsName;
	/**
	 * 药品编码
	 */
	private String drugCode;
	/**
	 * 规格
	 */
	private String itemSpec;
	/**
	 * 总数量
	 */
	private String itemTotal;
	/**
	 * 单位
	 */
	private String itemUnit;
	/**
	 * 用法
	 */
	private String itemUsage;
	/**
	 * 用量
	 */
	private String singleDose;
	/**
	 * 频率
	 */
	private String itemFrequency;
	/**
	 * 嘱托
	 */
	private String noticeDesc;
	
	public Detail() {
		super();
	}

	public Detail(String commonName, String goodsName, String drugCode,
			String itemSpec, String itemTotal, String itemUnit,
			String itemUsage, String singleDose, String itemFrequency,
			String noticeDesc) {
		super();
		this.commonName = commonName;
		this.goodsName = goodsName;
		this.drugCode = drugCode;
		this.itemSpec = itemSpec;
		this.itemTotal = itemTotal;
		this.itemUnit = itemUnit;
		this.itemUsage = itemUsage;
		this.singleDose = singleDose;
		this.itemFrequency = itemFrequency;
		this.noticeDesc = noticeDesc;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(String itemTotal) {
		this.itemTotal = itemTotal;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemUsage() {
		return itemUsage;
	}

	public void setItemUsage(String itemUsage) {
		this.itemUsage = itemUsage;
	}

	public String getSingleDose() {
		return singleDose;
	}

	public void setSingleDose(String singleDose) {
		this.singleDose = singleDose;
	}

	public String getItemFrequency() {
		return itemFrequency;
	}

	public void setItemFrequency(String itemFrequency) {
		this.itemFrequency = itemFrequency;
	}

	public String getNoticeDesc() {
		return noticeDesc;
	}

	public void setNoticeDesc(String noticeDesc) {
		this.noticeDesc = noticeDesc;
	}
	
}
