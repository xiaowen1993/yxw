package com.common;

import com.yxw.framework.config.SystemConfig;

public class CommonConstant {

	//	public static final String TRADE_MODE_WECHAT_NATIVE_AUTO_SERVICE_VAL = "9";  //自助机（微信扫码付）
	//	public static final String TRADE_MODE_ALIPAY_NATIVE_AUTO_SERVICE_VAL = "10"; //自助机（支付宝扫码付）
	//	public static final String TRADE_MODE_WECHAT_MICRO_AUTO_SERVICE_VAL = "11";  //自助机（微信条码付）
	//	public static final String TRADE_MODE_ALIPAY_MICRO_AUTO_SERVICE_VAL = "12";  //自助机（支付宝条码付）
	//	public static final String TRADE_MODE_UNIONPAY_WALLET_VAL = "23";            //银联钱包支付

	public static final String CERTS_PATH = SystemConfig.getStringValue("certs_path");
	public static final String SERVER_IP = SystemConfig.getStringValue("server_ip");
	public static final String URL_PREFIX = SystemConfig.getStringValue("url_prefix");
	public static final String PAY_VIEW_TYPE = SystemConfig.getStringValue("pay_view_type");

	public static final String TRADE_RESTFUL_URL_PREFIX = SystemConfig.getStringValue("trade_restful_url_prefix");
	public static final String TRADE_RESTFUL_PAYSETTING_PATH = SystemConfig.getStringValue("trade_restful_paysetting_path");

}
