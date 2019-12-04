/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.yxw.platform.sdk.alipay.executor;

import net.sf.json.JSONObject;

import com.yxw.platform.sdk.alipay.exception.MyException;
import com.yxw.platform.sdk.alipay.utils.AlipayMsgBuildUtil;

/**
 * 默认执行器(该执行器仅发送ack响应)
 * 
 * @author baoxing.gbx
 * @version $Id: InAlipayDefaultExecutor.java, v 0.1 Jul 30, 2014 10:22:11 AM
 *          baoxing.gbx Exp $
 */
public class InAlipayDefaultExecutor implements ActionExecutor {

	/** 业务参数 */
	private JSONObject bizContent;

	public InAlipayDefaultExecutor(JSONObject bizContent) {
		this.bizContent = bizContent;
	}

	public InAlipayDefaultExecutor() {
		super();
	}

	/**
	 * 
	 * @see com.alipay.executor.ActionExecutor#execute()
	 */
	public String execute() throws MyException {

		// 取得发起请求的支付宝账号id
		final String fromUserId = bizContent.getString("FromUserId");
		final String appId = bizContent.getString("AppId");
		return AlipayMsgBuildUtil.buildBaseAckMsg(fromUserId, appId);
	}
}
