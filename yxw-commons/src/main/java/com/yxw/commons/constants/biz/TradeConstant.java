/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年7月19日
 * @version 1.0
 */
package com.yxw.commons.constants.biz;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年7月19日  
 */
public class TradeConstant {

	/**
	 * 交易方式-val
	 */
	public static final int TRADE_MODE_WECHAT_VAL = 1;//微信支付
	public static final int TRADE_MODE_ALIPAY_VAL = 2;//支付宝支付
	//	public static final int TRADE_MODE_EASYHEALTH_WEIXIN_VAL = 3;//微信支付（健康易app）
	//	public static final int TRADE_MODE_EASYHEALTH_ALIPAY_VAL = 4;//支付宝支付（健康易app）
	//	public static final int TRADE_MODE_EASYHEALTH_UNIONPAY_VAL = 5;//银联支付（健康易app）
	public static final int TRADE_MODE_UNIONPAY_VAL = 6;//银联服务窗
	//	public static final int TRADE_MODE_EASYHEALTH_HEALTH_CARD_VAL = 7;//健康卡支付（健康易app）
	//	public static final int TRADE_MODE_EASYHEALTH_INSURANCE_HEALTH_CARD_VAL = 8;//商保支付（内嵌银联钱包app）
	public static final int TRADE_MODE_WECHATNATIVE_VAL = 9;//自助机（微信扫码付）
	public static final int TRADE_MODE_ALIPAYNATIVE_VAL = 10;//自助机（支付宝扫码付）
	public static final int TRADE_MODE_WECHATMICRO_VAL = 11;//自助机（微信条码付）
	public static final int TRADE_MODE_ALIPAYMICRO_VAL = 12;//自助机（支付宝条码付）
	public static final int TRADE_MODE_BANKCARD_VAL = 13;//自助机（银行卡支付）
	public static final int TRADE_MODE_CREDITCARD_VAL = 14;//自助机（信用卡支付）
	public static final int TRADE_MODE_UNIONPAYNATIVE_VAL = 15;//自助机（银联钱包扫码付）
	public static final int TRADE_MODE_UNIONPAYMICRO_VAL = 16;//自助机（银联钱包条码付）
	public static final int TRADE_MODE_CASH_VAL = 17;// 自助机（现金）

	public static final int TRADE_MODE_APP_VAL = 20;
	public static final int TRADE_MODE_APP_WECHAT_VAL = 21;//微信支付（内嵌微信app）
	public static final int TRADE_MODE_APP_ALIPAY_VAL = 22;//支付宝支付（内嵌支付宝app）
	public static final int TRADE_MODE_APP_UNIONPAY_VAL = 23;//银联支付（内嵌银联钱包app）
	public static final int TRADE_MODE_ALIPAY_MOBILE_WEB_VAL = 24;//支付宝手机网页支付
	public static final int TRADE_MODE_DIAGNOSIS_CARD_VAL = 25;//诊疗卡支付

	/**
	 * 交易方式-code
	 */
	public static final String TRADE_MODE_WECHAT = "wechat";
	public static final String TRADE_MODE_ALIPAY = "alipay";
	public static final String TRADE_MODE_UNIONPAY = "unionpay";
	public static final String TRADE_MODE_INSURANCE = "insurance";
	public static final String TRADE_MODE_HEALTHCARD = "healthcard";
	public static final String TRADE_MODE_WECHATNATIVE = "wechatnative";
	public static final String TRADE_MODE_ALIPAYNATIVE = "alipaynative";
	public static final String TRADE_MODE_WECHATMICRO = "wechatmicro";
	public static final String TRADE_MODE_ALIPAYMICRO = "alipaymicro";
	public static final String TRADE_MODE_BANKCARD = "bankcard";
	public static final String TRADE_MODE_CREDITCARD = "creditcard";
	public static final String TRADE_MODE_UNIONPAYNATIVE = "unionpaynative";
	public static final String TRADE_MODE_UNIONPAYMICRO = "unionpaymicro";
	public static final String TRADE_MODE_ALIPAYMOBILEWEB = "alipaymobileweb";

	public static final String TRADE_STATE_SUCCESS = "success";//支付成功
	public static final String TRADE_STATE_REFUND = "refund";//退款
	public static final String TRADE_STATE_NOTPAY = "notpay";//未支付
	public static final String TRADE_STATE_CLOSED = "closed";//已关闭
	public static final String TRADE_STATE_EXCEPTION = "exception";// 未知状态|异常状态， 需堕入轮询

	public static final String REFUND_STATE_SUCCESS = "success";//退款成功
	public static final String REFUND_STATE_FAIL = "fail";//退款失败
}
