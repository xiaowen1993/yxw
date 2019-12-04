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
public class RegOrdersParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6394476583108696487L;

	/**
	 * 微信/支付宝appid
	 */
	private String appId;

	/**
	 * 挂号类型 0：所有 1：预约 2：当天
	 */
	private String regType;

	/**
	 * 支付方式 0: 全部 1：微信 2：支付宝
	 */
	private String tradeMode;

	/**
	 * 分院code
	 */
	private String branchCode;

	/**
	 * 开始时间
	 * 订单查询开始时间，优先级下雨公众服务平台订单号。格式：YYYY-MM-DD 
	 */
	private String startDate;

	/**
	 * 结束时间
	 * 订单查询结束时间，优先级下雨公众服务平台订单号。格式：YYYY-MM-DD 
	 */
	private String endDate;

	public RegOrdersParams() {
		super();
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
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
