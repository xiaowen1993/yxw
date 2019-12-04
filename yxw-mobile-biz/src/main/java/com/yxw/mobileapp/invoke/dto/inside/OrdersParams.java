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
package com.yxw.mobileapp.invoke.dto.inside;

import java.io.Serializable;

/**
 * @Package: com.yxw.mobileapp.invoke.dto.inside
 * @ClassName: OrdersParams
 * @Statement: <p>订单查询输入参数</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月15日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class OrdersParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6394476583108696487L;

	/**
	 * 微信/支付宝appid
	 */
	private String appId;

	/**
	 * 订单类型 0： 全部 1：挂号  2：门诊
	 */
	private String orderMode;

	/**
	 * 支付方式 0: 全部 1：微信 2：支付宝
	 */
	private String tradeMode;

	/**
	 * 对单标识  1：对单 2：不对单 默认为不对单
	 */
	private String isCheckOrder;

	/**
	 * 分院code
	 */
	private String branchCode;

	/**
	 * 开始时间
	 * 订单查询开始时间，优先级下雨公众服务平台订单号。格式：YYYY-MM-DD HH24:MI:SS
	 */
	private String startTime;

	/**
	 * 结束时间
	 * 订单查询结束时间，优先级下雨公众服务平台订单号。格式：YYYY-MM-DD HH24:MI:SS
	 */
	private String endTime;

	/**
	 * 查询范围    0  默认（不包括异常单） 1 所有（包括异常单）
	 */
	private String scope;

	public OrdersParams() {
		super();
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOrderMode() {
		return orderMode;
	}

	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getIsCheckOrder() {
		return isCheckOrder;
	}

	public void setIsCheckOrder(String isCheckOrder) {
		this.isCheckOrder = isCheckOrder;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
