/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.outside.entity.in;

/**
 * @Package: com.yxw.mobileapp.biz.outside.entity.in
 * @ClassName: OrderQueryParams
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年9月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class OrdersParams {

	/**
	 * 微信/支付宝appid
	 */
	private String appid;

	/**
	 * 	getFromAppid
	 */
	private String saction;

	/**
	 * 1 微信  
	 * 2 支付宝
	 */
	private String agencyType;

	/**
	 * {DD2EE862-C2B9-414D-9961-B019D87FCD94}
	 */
	private String secretCode;

	/**
	 * 开始时间， 格式 YYYY-MM-DD HH24:MI:SS
	 */
	private String beginDate;

	/**
	 * 结束时间，格式	YYYY-MM-DD HH24:MI:SS 只能查询到前一天的订单
	 */
	private String endDate;

	/**
	 * 默认20
	 */
	private String pageSize;

	/**
	 * 默认0，从0开始
	 */
	private String currySize;

	/**
	 * 默认倒序，
	 * 1 正序
	 * 2 倒序
	 */
	private String isAsc;

	/**
	 * 1挂号订单 2门诊订单 3住院押金订单 101挂号退费订单 不传这个参数默认查所有
	 */
	private String orderType;

	/**
	 * Json csv
	 */
	private String dataType;

	/**
	 * 0待支付，1未完成，2已经支付，3缴费超时，4已取消，10待退费，11退费失败12已退费98部分退费不传这参数，默认查所有
	 */
	private String payType;

	public OrdersParams() {
		super();
	}

	public OrdersParams(String appid, String saction, String agencyType, String secretCode, String beginDate, String endDate, String pageSize,
			String currySize, String isAsc, String orderType, String dataType, String payType) {
		super();
		this.appid = appid;
		this.saction = saction;
		this.agencyType = agencyType;
		this.secretCode = secretCode;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.pageSize = pageSize;
		this.currySize = currySize;
		this.isAsc = isAsc;
		this.orderType = orderType;
		this.dataType = dataType;
		this.payType = payType;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSaction() {
		return saction;
	}

	public void setSaction(String saction) {
		this.saction = saction;
	}

	public String getAgencyType() {
		return agencyType;
	}

	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}

	public String getSecretCode() {
		return secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getCurrySize() {
		return currySize;
	}

	public void setCurrySize(String currySize) {
		this.currySize = currySize;
	}

	public String getIsAsc() {
		return isAsc;
	}

	public void setIsAsc(String isAsc) {
		this.isAsc = isAsc;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

}
