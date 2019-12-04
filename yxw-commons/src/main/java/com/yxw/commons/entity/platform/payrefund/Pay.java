package com.yxw.commons.entity.platform.payrefund;

import java.io.Serializable;
import java.text.ParseException;

public class Pay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5106622392137082253L;

	/**
	 * 医院代码/药店（一切跟支付配置信息关联的唯一code）
	 */
	protected String code;

	/**
	 * 支付方式-val
	 */
	protected String tradeMode;

	/**
	 * 订单编号
	 */
	protected String orderNo;

	/**
	 * 支付金额
	 */
	protected String totalFee;

	/**
	 * 支付描述信息
	 */
	protected String body;

	/**
	 * 支付成功后页面
	 */
	protected String paySuccessPageUrl;

	/**
	 * 支付信息显示页面
	 */
	protected String infoUrl;

	/**
	 * 支付超时时间（第三方的超时时间）
	 * 单位（秒）
	 */
	protected String agtTimeout;

	/**
	 * 支付超时时间(倒计时时间间隔)
	 * 单位（秒）
	 */
	protected String timeout;

	/**
	 * 显示视图的类型：iframe/jsonp
	 */
	protected String viewType = "iframe";

	/**
	 * infoUrl页面的高度
	 */
	protected Integer frameHeight;

	/**
	 * 支付成功后的回调地址 （restful方式必传）
	 */
	protected String notifyUrl;

	/**
	 * 自定义原样返回数据
	 */
	protected String customAttach;

	public Pay() {
		super();
	}

	public Pay(String code, String tradeMode, String orderNo, String totalFee, String body, String paySuccessPageUrl, String infoUrl,
			String agtTimeout, String timeout, String viewType, Integer frameHeight, String notifyUrl, String customAttach) {
		super();
		this.code = code;
		this.tradeMode = tradeMode;
		this.orderNo = orderNo;
		this.totalFee = totalFee;
		this.body = body;
		this.paySuccessPageUrl = paySuccessPageUrl;
		this.infoUrl = infoUrl;
		this.agtTimeout = agtTimeout;
		this.timeout = timeout;
		this.viewType = viewType;
		this.frameHeight = frameHeight;
		this.notifyUrl = notifyUrl;
		this.customAttach = customAttach;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPaySuccessPageUrl() {
		return paySuccessPageUrl;
	}

	public void setPaySuccessPageUrl(String paySuccessPageUrl) {
		this.paySuccessPageUrl = paySuccessPageUrl;
	}

	public String getInfoUrl() {
		return infoUrl;
	}

	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

	public String getAgtTimeout() {
		return agtTimeout;
	}

	public void setAgtTimeout(String agtTimeout) {
		this.agtTimeout = agtTimeout;
	}

	public String getTimeout() throws ParseException {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public Integer getFrameHeight() {
		return frameHeight;
	}

	public void setFrameHeight(Integer frameHeight) {
		this.frameHeight = frameHeight;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getCustomAttach() {
		return customAttach;
	}

	public void setCustomAttach(String customAttach) {
		this.customAttach = customAttach;
	}

}
