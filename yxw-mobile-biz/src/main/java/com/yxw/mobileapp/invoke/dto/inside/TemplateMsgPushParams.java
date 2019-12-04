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
 * 模板消息推送接口参数定义
 * @Package: com.yxw.mobileapp.invoke.dto.inside
 * @ClassName: TemplateMsgPushParams
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年9月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class TemplateMsgPushParams implements Serializable {

	private static final long serialVersionUID = 7231412951479664277L;
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
	 * 模板编码
	 */
	private String templateCode;
	/**
	 * 详情链接url
	 */
	private String url;
	/**
	 * 字体颜色
	 */
	private String topColor;
	/**
	 * 消息内容,json格式
	 */
	private String msgContent;

	public TemplateMsgPushParams() {
		super();
	}

	/**
	 * @param appId
	 * @param platformType
	 * @param msgType
	 * @param userType
	 * @param toUser
	 * @param tempLateCode
	 * @param url
	 * @param topColor
	 * @param msgContent
	 */
	public TemplateMsgPushParams(String appId, String platformType, String userType, String toUser, String templateCode, String url,
			String topColor, String msgContent) {
		super();
		this.appId = appId;
		this.platformType = platformType;
		this.userType = userType;
		this.toUser = toUser;
		this.templateCode = templateCode;
		this.url = url;
		this.topColor = topColor;
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
	 * @return the tempLateCode
	 */
	public String getTemplateCode() {
		return templateCode;
	}

	/**
	 * @param tempLateCode the tempLateCode to set
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the topColor
	 */
	public String getTopColor() {
		return topColor;
	}

	/**
	 * @param topColor the topColor to set
	 */
	public void setTopColor(String topColor) {
		this.topColor = topColor;
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
