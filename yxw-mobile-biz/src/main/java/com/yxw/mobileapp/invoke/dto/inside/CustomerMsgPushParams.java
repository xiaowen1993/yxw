/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.invoke.dto.inside;

import java.io.Serializable;

/**
 * 客服消息推送接口参数定义
 * @Package: com.yxw.mobileapp.invoke.dto.inside
 * @ClassName: CustomerMsgPushParams
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月12日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class CustomerMsgPushParams implements Serializable {

	private static final long serialVersionUID = -2023064015461380945L;

	/**
	 * 微信公众号ID/支付宝服务窗ID
	 */
	private String appId;
	/**
	 * 平台类型,wechat:微信公众号,alipay:支付宝服务窗
	 */
	private String platformType;
	/**
	 * 接收人账号类别,1:诊疗卡号码,2: 微信公众号openId/支付宝服务窗openId,3:挂号订单号,4:门诊订单号,5:押金补缴订单号
	 */
	private String userType;
	/**
	 * 诊疗卡号码,微信公众号openId,挂号订单号,门诊订单号,押金补缴订单号
	 */
	private String toUser;
	/**
	 * 消息类型,1:text,目前默认为1
	 */
	private String msgType;
	/**
	 * 消息内容,json格式
	 */
	private String msgContent;

	public CustomerMsgPushParams() {
		super();
	}

	/**
	 * @param appId
	 * @param platformType
	 * @param userType
	 * @param toUser
	 * @param msgTitle
	 * @param msgType
	 * @param msgContent
	 */
	public CustomerMsgPushParams(String appId, String platformType, String userType, String toUser, String msgType, String msgContent) {
		super();
		this.appId = appId;
		this.platformType = platformType;
		this.userType = userType;
		this.toUser = toUser;
		this.msgType = msgType;
		this.msgContent = msgContent;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the platformType
	 */
	public String getPlatformType() {
		return platformType;
	}

	/**
	 * @param platformType the platformType to set
	 */
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the toUser
	 */
	public String getToUser() {
		return toUser;
	}

	/**
	 * @param toUser the toUser to set
	 */
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	/**
	 * @return the msgType
	 */
	public String getMsgType() {
		return msgType;
	}

	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	/**
	 * @return the msgContent
	 */
	public String getMsgContent() {
		return msgContent;
	}

	/**
	 * @param msgContent the msgContent to set
	 */
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

}
