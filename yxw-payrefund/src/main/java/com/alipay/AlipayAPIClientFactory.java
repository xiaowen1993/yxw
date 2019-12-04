/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.common.AlipayConstant;

/**
 * API调用客户端工厂
 * 
 * @author taixu.zqq
 */
public class AlipayAPIClientFactory {
	/** API调用客户端 */
	private static Map<String, AlipayClient> alipayClientMap = Collections.synchronizedMap(new HashMap<String, AlipayClient>());

	public static AlipayClient getAlipayClient(String appId, String privateKey) {
		AlipayClient alipayClient = alipayClientMap.get(appId);
		if (null == alipayClient) {
			//alipayClient = new DefaultAlipayClient(AlipayConstant.ALIPAY_OPENAPI_GATEWAY, appId, privateKey, AlipayConstant.FORMAT_JSON, AlipayConstant.CHARSET);
			alipayClient = new DefaultAlipayClient(AlipayConstant.ALIPAY_OPENAPI_GATEWAY, appId, privateKey, 
					AlipayConstant.FORMAT_JSON, AlipayConstant.CHARSET, AlipayConstant.ALIPAY_OPEN_PLATFORM_PUBLIC_KEY, AlipayConstant.SIGN_TYPE_RSA);
			alipayClientMap.put(appId.concat(privateKey), alipayClient);
		}
		return alipayClient;
	}
	
	
	public static AlipayClient getAlipayClientRSA2(String appId, String privateKey) {
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConstant.ALIPAY_OPENAPI_GATEWAY, appId, privateKey, 
					AlipayConstant.FORMAT_JSON, AlipayConstant.CHARSET, AlipayConstant.ALIPAY_OPEN_PLATFORM_PUBLIC_KEY_RSA2, AlipayConstant.SIGN_TYPE_RSA2);
		return alipayClient;
	}

}
