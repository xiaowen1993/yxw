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
package com.yxw.platform.sdk;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.yxw.framework.common.http.HttpClientUtil;
import com.yxw.framework.config.SystemConfig;
import com.yxw.platform.sdk.vo.VoForSDK;

/**
 * @author homer.yang
 * @version 1.0
 * @date 2015年3月16日
 */
public class SDKPublicArgs {
	public static Logger logger = LoggerFactory.getLogger(SDKPublicArgs.class);
	public static HttpClientUtil httpClient = HttpClientUtil.getInstance();

	/**
	 * 存储 Wechat Access token
	 */
	public static Map<String, JSONObject> wechatAccessTokenMap = new HashMap<String, JSONObject>();

	/**
	 * 存储 UnioinPay FrontToken & BackendToken
	 */
	public static Map<String, JSONObject> unionPayFrontTokenMap = new HashMap<String, JSONObject>();
	public static Map<String, JSONObject> unionPayBackendTokenMap = new HashMap<String, JSONObject>();

	/**
	 * 存储 Wechat JS ticket
	 */
	public static Map<String, JSONObject> wechatJSTicketMap = new HashMap<String, JSONObject>();

	/** 支付宝API调用客户端 */
	public static Map<String, AlipayClient> alipayClientMap = new HashMap<String, AlipayClient>();

	public static final String PLATFORM_WECHAT = "wechat";
	public static final String PLATFORM_ALIPAY = "alipay";
	public static final String PLATFORM_EASY_HEALTH = "easyHealth";
	public static final String PLATFORM_UNIONPAY = "UnionPay";

	/** openId 在 Cookie 里面的有效天数 */
	public static final String OPENID_COOKIE_MAX_AGE = SystemConfig.getStringValue("OPENID_COOKIE_MAX_AGE");

	/** 微信 OpenAuth 网关 */
	public static final String WECHAT_OPENAUTH_GATEWAY = SystemConfig.getStringValue("WECHAT_OPENAUTH_GATEWAY");

	/** 微信网关 */
	public static final String WECHAT_GATEWAY = SystemConfig.getStringValue("WECHAT_GATEWAY");

	/** 签名编码-视支付宝服务窗要求 */
	public static final String SIGN_CHARSET = "GBK";

	/** 字符编码-传递给支付宝的数据编码 */
	public static final String CHARSET = "GBK";

	/** 签名类型-视支付宝服务窗要求 */
	public static final String SIGN_TYPE = "RSA";

	/** 支付宝 OpenAuth 网关 */
	public static final String ALIPAY_OPENAUTH_GATEWAY = SystemConfig.getStringValue("ALIPAY_OPENAUTH_GATEWAY");

	/** 支付宝网关 */
	public static final String ALIPAY_GATEWAY = SystemConfig.getStringValue("ALIPAY_GATEWAY");

	/** 授权访问令牌的授权类型 */
	public static final String GRANT_TYPE = "authorization_code";

	/** 银联钱包网关 */
	public static final String UNIONPAY_GATEWAY = SystemConfig.getStringValue("UNIONPAY_GATEWAY");
	/** 银联钱包 OpenAuth 网关 */
	public static final String UNIONPAY_OPENAUTH_GATEWAY = SystemConfig.getStringValue("UNIONPAY_OPENAUTH_GATEWAY");

	// 临时存hospital信息的map，用于测试
	public static Map<String, VoForSDK> tempVoForSDKs = new HashMap<String, VoForSDK>();
	static {

		// 支付宝：医享网络
		VoForSDK h1 = new VoForSDK();
		h1.setId(1l);
		h1.setAppId("2014110300015485");
		h1.setPrivateKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKK0PXoLKnBkgtOl0kvyc9X2tUUdh/lRZr9RE1frjr2ZtAulZ+Moz9VJZFew1UZIzeK0478obY/DjHmD3GMfqJoTguVqJ2MEg+mJ8hJKWelvKLgfFBNliAw+/9O6Jah9Q3mRzCD8pABDEHY7BM54W7aLcuGpIIOa/qShO8dbXn+FAgMBAAECgYA8+nQ380taiDEIBZPFZv7G6AmT97doV3u8pDQttVjv8lUqMDm5RyhtdW4n91xXVR3ko4rfr9UwFkflmufUNp9HU9bHIVQS+HWLsPv9GypdTSNNp+nDn4JExUtAakJxZmGhCu/WjHIUzCoBCn6viernVC2L37NL1N4zrR73lSCk2QJBAPb/UOmtSx+PnA/mimqnFMMP3SX6cQmnynz9+63JlLjXD8rowRD2Z03U41Qfy+RED3yANZXCrE1V6vghYVmASYsCQQCoomZpeNxAKuUJZp+VaWi4WQeMW1KCK3aljaKLMZ57yb5Bsu+P3odyBk1AvYIPvdajAJiiikRdIDmi58dqfN0vAkEAjFX8LwjbCg+aaB5gvsA3t6ynxhBJcWb4UZQtD0zdRzhKLMuaBn05rKssjnuSaRuSgPaHe5OkOjx6yIiOuz98iQJAXIDpSMYhm5lsFiITPDScWzOLLnUR55HL/biaB1zqoODj2so7G2JoTiYiznamF9h9GuFC2TablbINq80U2NcxxQJBAMhw06Ha/U7qTjtAmr2qAuWSWvHU4ANu2h0RxYlKTpmWgO0f47jCOQhdC3T/RK7f38c7q8uPyi35eZ7S1e/PznY=");
		h1.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCitD16CypwZILTpdJL8nPV9rVFHYf5UWa/URNX6469mbQLpWfjKM/VSWRXsNVGSM3itOO/KG2Pw4x5g9xjH6iaE4LlaidjBIPpifISSlnpbyi4HxQTZYgMPv/TuiWofUN5kcwg/KQAQxB2OwTOeFu2i3LhqSCDmv6koTvHW15/hQIDAQAB");
		h1.setPlatformCode("alipay");
		tempVoForSDKs.put(h1.getAppId(), h1);

		// 支付宝：医享发展
		VoForSDK h2 = new VoForSDK();
		h2.setId(2l);
		h2.setAppId("2014122400021484");
		h2.setPrivateKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKK0PXoLKnBkgtOl0kvyc9X2tUUdh/lRZr9RE1frjr2ZtAulZ+Moz9VJZFew1UZIzeK0478obY/DjHmD3GMfqJoTguVqJ2MEg+mJ8hJKWelvKLgfFBNliAw+/9O6Jah9Q3mRzCD8pABDEHY7BM54W7aLcuGpIIOa/qShO8dbXn+FAgMBAAECgYA8+nQ380taiDEIBZPFZv7G6AmT97doV3u8pDQttVjv8lUqMDm5RyhtdW4n91xXVR3ko4rfr9UwFkflmufUNp9HU9bHIVQS+HWLsPv9GypdTSNNp+nDn4JExUtAakJxZmGhCu/WjHIUzCoBCn6viernVC2L37NL1N4zrR73lSCk2QJBAPb/UOmtSx+PnA/mimqnFMMP3SX6cQmnynz9+63JlLjXD8rowRD2Z03U41Qfy+RED3yANZXCrE1V6vghYVmASYsCQQCoomZpeNxAKuUJZp+VaWi4WQeMW1KCK3aljaKLMZ57yb5Bsu+P3odyBk1AvYIPvdajAJiiikRdIDmi58dqfN0vAkEAjFX8LwjbCg+aaB5gvsA3t6ynxhBJcWb4UZQtD0zdRzhKLMuaBn05rKssjnuSaRuSgPaHe5OkOjx6yIiOuz98iQJAXIDpSMYhm5lsFiITPDScWzOLLnUR55HL/biaB1zqoODj2so7G2JoTiYiznamF9h9GuFC2TablbINq80U2NcxxQJBAMhw06Ha/U7qTjtAmr2qAuWSWvHU4ANu2h0RxYlKTpmWgO0f47jCOQhdC3T/RK7f38c7q8uPyi35eZ7S1e/PznY=");
		h2.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCitD16CypwZILTpdJL8nPV9rVFHYf5UWa/URNX6469mbQLpWfjKM/VSWRXsNVGSM3itOO/KG2Pw4x5g9xjH6iaE4LlaidjBIPpifISSlnpbyi4HxQTZYgMPv/TuiWofUN5kcwg/KQAQxB2OwTOeFu2i3LhqSCDmv6koTvHW15/hQIDAQAB");
		h2.setPlatformCode("alipay");
		tempVoForSDKs.put(h2.getAppId(), h2);

		// 支付宝：医享网络科技
		VoForSDK h6 = new VoForSDK();
		h6.setId(6l);
		h6.setAppId("2014122400021487");
		h6.setPrivateKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKK0PXoLKnBkgtOl0kvyc9X2tUUdh/lRZr9RE1frjr2ZtAulZ+Moz9VJZFew1UZIzeK0478obY/DjHmD3GMfqJoTguVqJ2MEg+mJ8hJKWelvKLgfFBNliAw+/9O6Jah9Q3mRzCD8pABDEHY7BM54W7aLcuGpIIOa/qShO8dbXn+FAgMBAAECgYA8+nQ380taiDEIBZPFZv7G6AmT97doV3u8pDQttVjv8lUqMDm5RyhtdW4n91xXVR3ko4rfr9UwFkflmufUNp9HU9bHIVQS+HWLsPv9GypdTSNNp+nDn4JExUtAakJxZmGhCu/WjHIUzCoBCn6viernVC2L37NL1N4zrR73lSCk2QJBAPb/UOmtSx+PnA/mimqnFMMP3SX6cQmnynz9+63JlLjXD8rowRD2Z03U41Qfy+RED3yANZXCrE1V6vghYVmASYsCQQCoomZpeNxAKuUJZp+VaWi4WQeMW1KCK3aljaKLMZ57yb5Bsu+P3odyBk1AvYIPvdajAJiiikRdIDmi58dqfN0vAkEAjFX8LwjbCg+aaB5gvsA3t6ynxhBJcWb4UZQtD0zdRzhKLMuaBn05rKssjnuSaRuSgPaHe5OkOjx6yIiOuz98iQJAXIDpSMYhm5lsFiITPDScWzOLLnUR55HL/biaB1zqoODj2so7G2JoTiYiznamF9h9GuFC2TablbINq80U2NcxxQJBAMhw06Ha/U7qTjtAmr2qAuWSWvHU4ANu2h0RxYlKTpmWgO0f47jCOQhdC3T/RK7f38c7q8uPyi35eZ7S1e/PznY=");
		h6.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCitD16CypwZILTpdJL8nPV9rVFHYf5UWa/URNX6469mbQLpWfjKM/VSWRXsNVGSM3itOO/KG2Pw4x5g9xjH6iaE4LlaidjBIPpifISSlnpbyi4HxQTZYgMPv/TuiWofUN5kcwg/KQAQxB2OwTOeFu2i3LhqSCDmv6koTvHW15/hQIDAQAB");
		h6.setPlatformCode("alipay");
		tempVoForSDKs.put(h6.getAppId(), h6);

		// 微信：医享网络
		VoForSDK h3 = new VoForSDK();
		h3.setId(3l);
		h3.setAppId("wxf71dabf8da40d87e");
		h3.setPrivateKey("eadc261df5c8c4dd446337c21919f396");
		h3.setPlatformCode("wechat");
		tempVoForSDKs.put(h3.getAppId(), h3);

		// 微信：医享发展
		VoForSDK h4 = new VoForSDK();
		h4.setId(4l);
		h4.setAppId("wxa3ba664bdb288a4a8");
		h4.setPrivateKey("7d4c44b258c57ff8dd7c3c3c396d6a1a");
		h4.setPlatformCode("wechat");
		tempVoForSDKs.put(h4.getAppId(), h4);

		// 微信：广州医享网络
		VoForSDK h5 = new VoForSDK();
		h5.setId(5l);
		h5.setAppId("wxfc6703a4f4a2fe36");
		h5.setPrivateKey("9913cf680d3519d9be8132e60f5e324b");
		h5.setPlatformCode("wechat");
		tempVoForSDKs.put(h5.getAppId(), h5);

		// 微信：广州医享网络
		VoForSDK h9 = new VoForSDK();
		h9.setId(9l);
		h9.setAppId("wx0218e9166b996302");
		h9.setPrivateKey("06f1937bea8c485b414ac7e78e6b263b");
		h9.setPlatformCode("wechat");
		tempVoForSDKs.put(h9.getAppId(), h9);

		VoForSDK h11 = new VoForSDK();
		h11.setId(11l);
		h11.setAppId("wxa3ba664bb288a4a8");
		h11.setPrivateKey("7d4c44b258c57ff8dd7c3c3c396d6a1a");
		h11.setPlatformCode("wechat");
		tempVoForSDKs.put(h11.getAppId(), h11);
	}

}
