/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年3月16日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.app.sdk;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.framework.common.http.HttpClientUtil;
import com.yxw.framework.config.SystemConfig;

/**
 * @author homer.yang
 * @version 1.0
 * @date 2015年3月16日
 */
public class SDKPublicArgs {
	public static Logger logger = LoggerFactory.getLogger(SDKPublicArgs.class);
	public static HttpClientUtil httpClient = HttpClientUtil.getInstance();

	/**
	 * 存储 UnioinPay FrontToken & BackendToken
	 */
	public static Map<String, JSONObject> unionPayFrontTokenMap = new HashMap<String, JSONObject>();
	public static Map<String, JSONObject> unionPayBackendTokenMap = new HashMap<String, JSONObject>();

	public static final String PLATFORM_UNIONPAY = "UnionPay";

	/** openId 在 Cookie 里面的有效天数 */
	public static final String OPENID_COOKIE_MAX_AGE = SystemConfig.getStringValue("OPENID_COOKIE_MAX_AGE");

	/** 银联钱包网关 */
	//public static final String UNIONPAY_GATEWAY = "https://open.95516.com";
	public static final String UNIONPAY_GATEWAY = SystemConfig.getStringValue("UNIONPAY_GATEWAY");
	/** 银联钱包 OpenAuth 网关 */
	public static final String UNIONPAY_OPENAUTH_GATEWAY = SystemConfig.getStringValue("UNIONPAY_OPENAUTH_GATEWAY");

}
