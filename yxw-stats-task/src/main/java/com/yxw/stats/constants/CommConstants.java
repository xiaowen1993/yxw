package com.yxw.stats.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

public class CommConstants {

	public static final List<String> PLATFORMTYPES = Lists.newArrayList("wechat", "alipay");
	public static final String WECHAT_PLATFORM = "wechat";
	public static final String ALIPAY_PLATFORM = "alipay";

	public static final String INIT_DATE = "2014-12-31";

	public static String[] CLINIC_STATISTICAL_COLUMNS =
			{ "payment", "noPayment", "refund", "clinicPayFee", "clinicRefundFee", "partRefund" };
	public static String[] DEPOSIT_STATISTICAL_COLUMNS = { "payment",
			"noPayment",
			"refund",
			"depositPayFee",
			"depositRefundFee",
			"partRefund" };
	public static String[] REG_STATISTICAL_COLUMNS = { "reservationPayment",
			"reservationNoPayment",
			"reservationRefund",
			"dutyPayment",
			"dutyNoPayment",
			"dutyRefund",
			"regPayFee",
			"regRefundFee" };

	public static String[] USER_STATS_COLUMNS = { "newuser", "canceluser", "cumulateuser" };

	public static String ACCESSTOKEN_GATEWAY = "http://hw21.yx129.net/interface/s_yxw/GetAccessToken";
	public static String WECHAT_API_GATEWAY = "https://api.weixin.qq.com";
	public static String ALIPAY_API_GATEWAY = "https://openapi.alipay.com/gateway.do";
	public static int WECHAT_RANGE = 7;
	public static int ALIPAY_RANGE = 30;

	public static Map<String, JSONObject> WECHAT_ACCESSTOKEN_MAP = Collections.synchronizedMap(new HashMap<String, JSONObject>());
}
