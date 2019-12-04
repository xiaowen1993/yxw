package com.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.acp.sdk.SDKConfig;
import com.yxw.framework.config.SystemConfig;

public class UnionPayConstant {
	
	/**
	 * 存储 UnioinPay FrontToken & BackendToken
	 */
	public static Map<String, JSONObject> unionPayFrontTokenMap = Collections.synchronizedMap(new HashMap<String, JSONObject>());
	public static Map<String, JSONObject> unionPayBackendTokenMap = Collections.synchronizedMap(new HashMap<String, JSONObject>());

	public static final String GRANT_TYPE = "authorization_code";
	
	/** 银联钱包网关 */
	public static final String UNIONPAY_GATEWAY = "https://open.95516.com";
		
	//默认配置的是UTF-8
	public static String encoding = "UTF-8";
	
	//全渠道固定值
	public static String version = SDKConfig.getConfig().getVersion();
	
	/**
	 * 银联支付回调地址
	 */
	public static final String UNIONPAY_NOTIFY_URL = SystemConfig.getStringValue("unionpay_notify_url", 
														CommonConstant.URL_PREFIX.concat(SystemConfig.getStringValue("unionpay_notify_path")));
	
	/**
	 * 银联退费回调地址
	 */
	public static final String UNIONPAY_REFUND_NOTIFY_URL = SystemConfig.getStringValue("unionpay_refund_notify_url", 
															CommonConstant.URL_PREFIX.concat(SystemConfig.getStringValue("unionpay_refund_notify_path")));
	
	public static final String UNIONPAY_ORDER_TIMEOUT = SystemConfig.getStringValue("unionpay_order_timeout", "300");
	
	public static final String UNIONPAY_APPID = SystemConfig.getStringValue("unionpay_appid");
	public static final String UNIONPAY_SECRET = SystemConfig.getStringValue("unionpay_secret");
	
	//测试商户号码
	public static final String UNIONPAY_TEST_MERID = SystemConfig.getStringValue("unionpay_test_merid");
	
	public static final Boolean UNIONPAY_TEST = SystemConfig.getBooleanValue("unionpay_test", false);
	public static final String UNIONPAY_TEST_MONEY = SystemConfig.getStringValue("unionpay_test_money", "1");
	
}