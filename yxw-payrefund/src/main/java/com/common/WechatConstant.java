package com.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yxw.framework.config.SystemConfig;

/**
 * @author homer.yang
 */
public class WechatConstant {

	public static Map<String, JSONObject> wechatAccessTokenMap = Collections.synchronizedMap(new HashMap<String, JSONObject>());
	public static Map<String, JSONObject> wechatJSTicketMap = Collections.synchronizedMap(new HashMap<String, JSONObject>());

	public static Map<String, JSONObject> wechatComponentAccessTokenMap = Collections.synchronizedMap(new HashMap<String, JSONObject>());
	public static Map<String, String> wechatComponentVerifyTicketMap = Collections.synchronizedMap(new HashMap<String, String>());

	// 微信API网关
	public static final String WECHAT_API_GATEWAY = "https://api.weixin.qq.com";
	// 微信 OPENAUTH 网关
	public static final String WECHAT_OPEN_AUTH_GATEWAY = "https://open.weixin.qq.com";

	// 微信商户API网关
	public static final String WECHAT_API_MCH_GATEWAY = "https://api.mch.weixin.qq.com";

	public static final String WECHAT_MP_GATEWAY = "https://mp.weixin.qq.com";

	//微信统一获取 AccessToken 接口地址
	public static final String WECHAT_GET_ACCESS_TOKEN_URL = SystemConfig.getStringValue("wechat_get_access_token_url");

	/**
	 * 微信支付异步回调地址
	 */
	public static final String WECHAT_NOTIFY_URL = SystemConfig.getStringValue("wechat_notify_url",
			CommonConstant.URL_PREFIX.concat(SystemConfig.getStringValue("wechat_notify_path")));

	/**
	 * 微信退费回调地址
	 */
	public static final String WECHAT_REFUND_NOTIFY_URL = SystemConfig.getStringValue("wechat_refund_notify_url",
			CommonConstant.URL_PREFIX.concat(SystemConfig.getStringValue("wechat_refund_notify_path")));

	//订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。
	//注意：最短失效时间间隔必须大于5分钟
	public static final String WECHAT_ORDER_TIMEOUT = SystemConfig.getStringValue("wechat_order_timeout", "300");

	public static final Boolean WECHAT_TEST = SystemConfig.getBooleanValue("wechat_test", false);
	public static final String WECHAT_TEST_MONEY = SystemConfig.getStringValue("wechat_test_money", "1");

	/**
	 * 微信OAuth2授权scope - snsapi_base
	 */
	public static final String WECHAT_OAUTH2_SCOPE_SNSAPI_BASE = "snsapi_base";

	/**
	 * 微信OAuth2授权scope - snsapi_userinfo
	 */
	public static final String WECHAT_OAUTH2_SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";

	/**
	 * 微信OAuth2授权grant_type - authorization_code
	 */
	public static final String WECHAT_OAUTH2_GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";

	/**
	 * 微信第三方授权OAuth2授权，重定向后会带上state参数，开发者可以填写任意参数值，最多128字节
	 */
	public static final String WECHAT_COMPONENT_OAUTH2_STATE = "componentOAuth2";

	/**
	 * subscribe(订阅)
	 */
	public static final String WECHAT_EVENT_SUBSCRIBE = "subscribe";

	/**
	 * unsubscribe(取消订阅)
	 */
	public static final String WECHAT_EVENT_UNSUBSCRIBE = "unsubscribe";

	/**
	 * ShakearoundUserShake(摇一摇事件通知)
	 */
	public static final String WECHAT_EVENT_SHAKEAROUNDUSERSHAKE = "ShakearoundUserShake";

	/**
	 * VIEW(点击菜单跳转链接时的事件推送)
	 */
	public static final String WECHAT_EVENT_VIEW = "VIEW";

	/**
	 * CLICK(点击菜单拉取消息时的事件推送)
	 */
	public static final String WECHAT_EVENT_CLICK = "CLICK";

	/**
	 * TEMPLATESENDJOBFINISH(模板消息发送OK后结果推送)
	 */
	public static final String WECHAT_EVENT_TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";

	/**
	 * event(事件消息)
	 */
	public static final String WECHAT_MSGTYPE_EVENT = "event";

	/**
	 * event(文本消息)
	 */
	public static final String WECHAT_MSGTYPE_TEXT = "text";

	public static final String WECHAT_TRADE_TYPE_JSAPI = "JSAPI";
	public static final String WECHAT_TRADE_TYPE_NATIVE = "NATIVE";
	public static final String WECHAT_TRADE_TYPE_APP = "APP";

	public static final String WECHAT_TRADE_TYPE_MICROPAY = "MICROPAY";

	public static final String WECHAT_COMPONENT_TOKEN = SystemConfig.getStringValue("wechat_component_token");
	public static final String WECHAT_COMPONENT_APPID = SystemConfig.getStringValue("wechat_component_appid");
	public static final String WECHAT_COMPONENT_APPSECRET = SystemConfig.getStringValue("wechat_component_appsecret");
	public static final String WECHAT_COMPONENT_ENCODING_AES_KEY = SystemConfig.getStringValue("wechat_component_encoding_aes_key");

}
