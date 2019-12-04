/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-24</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo;

import java.io.Serializable;

/**
 * @Package: com.yxw.mobileapp.biz.vo
 * @ClassName: PayParamsVo
 * @Statement: <p>交易参数对象</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-24
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class TradeParamsVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2898132342212605459L;

	private String cacheKey;
	private String cacheField;

	private String hospitalCode;
	private String branchHospitalCode;

	/**
	 * 交易方式   1：微信公众号    2：支付宝服务窗
	 */
	private String tradeMode;
	private String orderNo;
	private String openId;
	/**
	 * 交易成功后 的流水号
	 */
	private String tradeNo;

	/**
	 * 交易金额(分)
	 */
	private Integer tradeAmout;

	private Integer regType;

	private Boolean isException;

	private String failMsg;

	public TradeParamsVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TradeParamsVo(String cacheKey, String cacheField, String orderNo, String openId) {
		super();
		this.cacheKey = cacheKey;
		this.cacheField = cacheField;
		this.orderNo = orderNo;
		this.openId = openId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	public String getCacheField() {
		return cacheField;
	}

	public void setCacheField(String cacheField) {
		this.cacheField = cacheField;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public Integer getTradeAmout() {
		return tradeAmout;
	}

	public void setTradeAmout(Integer tradeAmout) {
		this.tradeAmout = tradeAmout;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public Boolean getIsException() {
		return isException;
	}

	public void setIsException(Boolean isException) {
		this.isException = isException;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}
}
