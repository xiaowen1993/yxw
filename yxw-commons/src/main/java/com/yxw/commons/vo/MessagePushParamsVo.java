/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-13</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * @Package: com.yxw.mobileapp.common.vo
 * @ClassName: MessagePushParamsVo
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-13
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MessagePushParamsVo {

	private String hospitalId;

	/**
	 * 
	 */
	private String appId;

	/**
	 * 后台配置的模板/客服消息编码
	 */
	private String templateCode;

	/**
	 * BizConstant.MODE_TYPE_WEIXIN   
	 * BizConstant.MODE_TYPE_ALIPAY
	 */
	private String platformType;
	
	/**
	 * 某平台下的具体消息类型
	 */
	private String msgTarget;

	/**
	 * 
	 */
	private String openId;

	/**
	 * key 模板的关键字   值根据业务自己设置
	 */
	private Map<String, Serializable> paramMap;

	public MessagePushParamsVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessagePushParamsVo(String appId, String templateCode, String platformType, String openId, Map<String, Serializable> paramMap) {
		super();
		this.appId = appId;
		this.templateCode = templateCode;
		this.platformType = platformType;
		this.openId = openId;
		this.paramMap = paramMap;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Map<String, Serializable> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Serializable> paramMap) {
		this.paramMap = paramMap;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getMsgTarget() {
		return msgTarget;
	}

	public void setMsgTarget(String msgTarget) {
		this.msgTarget = msgTarget;
	}
}
