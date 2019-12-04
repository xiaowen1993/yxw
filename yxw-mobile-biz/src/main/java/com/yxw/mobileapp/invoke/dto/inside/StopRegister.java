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
 * @Package: com.yxw.mobileapp.invoke.dto
 * @ClassName: stopRegistration
 * @Statement: <p>窗口停诊输入参数 新版本格式参数</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class StopRegister implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2282553559013021670L;

	/**
	 * arg0(appid):医院微信公众号的appid
	 */
	private String appId;

	/**
	 * 	arg9(orderList):医享网平台的微信公众服务号预约订单号列表，一个或多个，订单号间用英文逗号隔开
	 */
	private String orderList;

	public StopRegister() {
		super();
	}

	public StopRegister(String appId, String orderList) {
		super();
		this.appId = appId;
		this.orderList = orderList;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOrderList() {
		return orderList;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}

}
