package com.yxw.commons.dto.inside;

import java.io.Serializable;

/**
 * 获取门诊缴费数据公共入参，提供给商保就医登记、缴费明细，结算信息
 * @author Administrator
 *
 */
public class PaidMZDetailCommParams implements Serializable {

	private static final long serialVersionUID = -4773522411073276632L;

	private String appId;

	private String appCode;

	private String openId;

	/**
	 * 医院代码
	 */
	private String hospitalCode;

	/**
	 * 缴费单号
	 */
	private String orderNo;

	/**
	 * 缴费项唯一标示
	 */
	private String mzFeeId;

	/**
	 * 诊疗卡号
	 */
	private String cardNo;

	/**
	 * 表名
	 */
	private String hashTableName;

	/**
	 * 表名2
	 */
	private String hashTableName2;

	/**
	 * 是否已理赔，1：已理赔  0：未理赔
	 */
	private Integer isClaim;

	public Integer getIsClaim() {
		return isClaim;
	}

	public void setIsClaim(Integer isClaim) {
		this.isClaim = isClaim;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getHashTableName() {
		return hashTableName;
	}

	public void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	public String getHashTableName2() {
		return hashTableName2;
	}

	public void setHashTableName2(String hashTableName2) {
		this.hashTableName2 = hashTableName2;
	}

}
