/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.yxw.platform.sdk.alipay.executor;

import net.sf.json.JSONObject;

import com.yxw.platform.sdk.alipay.utils.AlipayMsgBuildUtil;

/**
 * 取消关注服务窗执行器
 * 
 * @author baoxing.gbx
 * @version $Id: InAlipayUnFollowExecutor.java, v 0.1 Jul 24, 2014 4:29:29 PM
 *          baoxing.gbx Exp $
 */
public class InAlipayUnFollowExecutor implements ActionExecutor {

	/** 业务参数 */
	private JSONObject bizContent;

	public InAlipayUnFollowExecutor(JSONObject bizContent) {
		this.bizContent = bizContent;
	}

	public InAlipayUnFollowExecutor() {
		super();
	}

	public String execute() {
		// 取得发起请求的支付宝账号id
		final String fromUserId = bizContent.getString("FromUserId");
		final String appId = bizContent.getString("AppId");
		return AlipayMsgBuildUtil.buildBaseAckMsg(fromUserId, appId);
	}
}
