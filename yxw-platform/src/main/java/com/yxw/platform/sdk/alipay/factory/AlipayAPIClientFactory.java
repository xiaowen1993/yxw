/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.yxw.platform.sdk.alipay.factory;

import java.util.HashMap;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.yxw.platform.sdk.alipay.constant.AlipayServiceEnvConstants;

/**
 * API调用客户端工厂
 * 
 * @author taixu.zqq
 * @version $Id: AlipayAPIClientFactory.java, v 0.1 2014年7月23日 下午5:07:45
 *          taixu.zqq Exp $
 */
public class AlipayAPIClientFactory {

	/** API调用客户端 */
	private static AlipayClient alipayClient;

	/**
	 * 获得API调用客户端
	 * 
	 * @return
	 */

	public static AlipayClient getAlipayClient(String appId, String private_key, String type, String charset) {

		if (clients.containsKey(appId)) {
			return clients.get(appId);
		}
		alipayClient = new DefaultAlipayClient(AlipayServiceEnvConstants.ALIPAY_GATEWAY, appId, private_key, type, charset);
		clients.put(appId, alipayClient);
		return alipayClient;
	}

	private static HashMap<String, AlipayClient> clients = new HashMap<String, AlipayClient>();

}
