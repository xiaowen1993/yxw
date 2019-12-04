package com.common;

import com.yxw.framework.config.SystemConfig;

/**
 * @author homer.yang
 */
public class AlipayConstant {

	/** 支付宝 OpenAuth 网关 */
	public static final String ALIPAY_OPENAUTH_GATEWAY = "https://openauth.alipay.com";

	/** 支付宝 OPEN API 网关 */
	public static final String ALIPAY_OPENAPI_GATEWAY = "https://openapi.alipay.com/gateway.do";

	/** 支付宝网关 */
	public static final String ALIPAY_MAPI_GATEWAY = "https://mapi.alipay.com/gateway.do";
	public static final String ALIPAY_WAPPAY_GATEWAY = "http://wappaygw.alipay.com/service/rest.htm";
	
	/**
	 * 支付宝支付异步回调地址
	 */
	public static final String ALIPAY_NOTIFY_URL = SystemConfig.getStringValue("alipay_notify_url", 
													CommonConstant.URL_PREFIX.concat(SystemConfig.getStringValue("alipay_notify_path")));
	
	/**
	 * 支付宝退费回调地址
	 */
	public static final String ALIPAY_REFUND_NOTIFY_URL = SystemConfig.getStringValue("alipay_refund_notify_url", 
															CommonConstant.URL_PREFIX.concat(SystemConfig.getStringValue("alipay_refund_notify_path")));
	
	public static final String ALIPAY_ORDER_TIMEOUT = SystemConfig.getStringValue("alipay_order_timeout", "300");
	
	public static final Boolean ALIPAY_TEST = SystemConfig.getBooleanValue("alipay_test", false);
	public static final String ALIPAY_TEST_MONEY = SystemConfig.getStringValue("alipay_test_money", "1");
	
	public static final String ALIPAY_OPEN_PLATFORM_PUBLIC_KEY = SystemConfig.getStringValue("aliapy_open_platform_public_key");
	public static final String ALIPAY_OPEN_PLATFORM_PUBLIC_KEY_RSA2 = SystemConfig.getStringValue("aliapy_open_platform_public_key_rsa2");
	public static final String ALIAPY_PARTNERS_PUBLIC_KEY = SystemConfig.getStringValue("aliapy_partners_public_key");
	
	//销售产品码，商家和支付宝签约的产品码。该产品请填写固定值：QUICK_WAP_WAY
	public static final String ALIPAY_PRODUCT_CODE = "QUICK_WAP_WAY";
	
	public static String FORMAT_JSON = "json";

	/**
	 * 支付字符编码格式 目前支持 utf-8
	 */
	public static String INPUT_CHARSET = "UTF-8";
	
	public static String CHARSET = "UTF-8";

	// 签名方式，选择项：0001(RSA)、MD5
	public static String SIGN_TYPE_RSA_0001 = "0001";
	// 无线的产品中，签名方式为rsa时，sign_type需赋值为0001而不是RSA

	// 签名方式
	public static String SIGN_TYPE_RSA = "RSA";
	// 签名方式
	public static String SIGN_TYPE_RSA2 = "RSA2";

	// 退款签名方式 不需修改
	public static String REFUND_SIGN_TYPE = "MD5";

	// 支付类型 ，无需修改
	public static String PAYMENT_TYPE = "1";

	/**
	 * 支付宝消息类型:事件类消息
	 */
	public static final String ALIPAY_MSG_TYPE_EVENT = "event";

	/**
	 * 支付宝事件类型:激活
	 */
	public static final String ALIPAY_EVENT_TYPE_VERIFYGW = "verifygw";
	/**
	 * 支付宝事件类型:关注
	 */
	public static final String ALIPAY_EVENT_TYPE_FOLLOW = "follow";
	
}
