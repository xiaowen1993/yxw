/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年6月27日
 * @version 1.0
 */
package com.yxw.commons.utils.biz;

import org.apache.commons.lang.StringUtils;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.TradeConstant;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年6月27日  
 */
public class ModeTypeUtil {

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年6月27日  
	 */
	public static int getPlatformModeType(String appCode) {

		int platformMode = BizConstant.MODE_TYPE_OTHER_VAL;

		if (StringUtils.equals(appCode, BizConstant.MODE_TYPE_WECHAT)) {// 微信服务窗
			platformMode = BizConstant.MODE_TYPE_WECHAT_VAL;
		} else if (StringUtils.equals(appCode, BizConstant.MODE_TYPE_ALIPAY)) {// 支付宝服务窗
			platformMode = BizConstant.MODE_TYPE_ALIPAY_VAL;
		} else if (StringUtils.equals(appCode, BizConstant.MODE_TYPE_APP)) {// 自己的app平台
			platformMode = BizConstant.MODE_TYPE_APP_VAL;
		} else if (StringUtils.equals(appCode, BizConstant.MODE_TYPE_AUTOSERVICE)) {// 自助机
			platformMode = BizConstant.MODE_TYPE_AUTOSERVICE_VAL;
		} else if (StringUtils.equals(appCode, BizConstant.MODE_TYPE_INNER_UNIONPAY)) {// 嵌在银联钱包
			platformMode = BizConstant.MODE_TYPE_INNER_UNIONPAY_VAL;
		} else if (StringUtils.equals(appCode, BizConstant.MODE_TYPE_INNER_CHINALIFE)) {// 嵌在国寿app
			platformMode = BizConstant.MODE_TYPE_INNER_CHINALIFE_VAL;
		}

		return platformMode;
	}

	public static String getPlatformCode(int platformType) {
		String platformCode = "";

		switch (platformType) {
		case BizConstant.MODE_TYPE_WECHAT_VAL:
			platformCode = BizConstant.MODE_TYPE_WECHAT;
			break;
		case BizConstant.MODE_TYPE_ALIPAY_VAL:
			platformCode = BizConstant.MODE_TYPE_ALIPAY;
			break;
		case BizConstant.MODE_TYPE_AUTOSERVICE_VAL:
			platformCode = BizConstant.MODE_TYPE_AUTOSERVICE;
			break;
		case BizConstant.MODE_TYPE_APP_VAL:
			platformCode = BizConstant.MODE_TYPE_APP;
			break;
		case BizConstant.MODE_TYPE_INNER_WECHAT_VAL:
			platformCode = BizConstant.MODE_TYPE_INNER_WECHAT;
			break;
		case BizConstant.MODE_TYPE_INNER_ALIPAY_VAL:
			platformCode = BizConstant.MODE_TYPE_INNER_ALIPAY;
			break;
		case BizConstant.MODE_TYPE_INNER_UNIONPAY_VAL:
			platformCode = BizConstant.MODE_TYPE_INNER_UNIONPAY;
			break;
		case BizConstant.MODE_TYPE_INNER_CHINALIFE_VAL:
			platformCode = BizConstant.MODE_TYPE_INNER_CHINALIFE;
			break;
		default:
			break;
		}

		return platformCode;
	}

	/*
	 * public static String getMsgModeType(String appCode) {
	 * 
	 * String msgMode = BizConstant.MESSAGE_MODE_APP_VAL;
	 * 
	 * if (StringUtils.equals(appCode, BizConstant.MODE_TYPE_WECHAT)) {//微信服务窗 msgMode = BizConstant.MESSAGE_MODE_WECHAT_VAL; } else if
	 * (StringUtils.equals(appCode, BizConstant.MODE_TYPE_ALIPAY)) {//支付宝服务窗 msgMode = BizConstant.MESSAGE_MODE_ALIPAY_VAL; } else if
	 * (StringUtils.equals(appCode, BizConstant.MODE_TYPE_APP)) {//自己的app平台 msgMode = BizConstant.MESSAGE_MODE_APP_VAL; } else if
	 * (StringUtils.equals(appCode, BizConstant.MODE_TYPE_INNER_UNIONPAY)) {//嵌在银联钱包 msgMode = BizConstant.MESSAGE_MODE_INNER_UNIONPAY_VAL;
	 * }
	 * 
	 * return msgMode; }
	 */
	/** 
	 * 描述: TODO
	 * 通过tradeMode值返回对应的支付code值
	 * @author Caiwq
	 * @date 2017年6月27日  
	 */

	public static String getTradeModeCode(Integer tradeMode) {
		String code = TradeConstant.TRADE_MODE_WECHAT;

		switch (tradeMode) {
		case 1:
		case 3:
		case 21:
			code = TradeConstant.TRADE_MODE_WECHAT;
			break;
		case 2:
		case 4:
		case 22:
		case 24:
			code = TradeConstant.TRADE_MODE_ALIPAY;
			break;
		case 5:
		case 6:
		case 23:
			code = TradeConstant.TRADE_MODE_UNIONPAY;
			break;
		case 7:
			code = TradeConstant.TRADE_MODE_HEALTHCARD;
			break;
		case 8:
			code = TradeConstant.TRADE_MODE_INSURANCE;
			break;
		case 9:
			code = TradeConstant.TRADE_MODE_WECHATNATIVE;
			break;
		case 10:
			code = TradeConstant.TRADE_MODE_ALIPAYNATIVE;
			break;
		case 11:
			code = TradeConstant.TRADE_MODE_WECHATMICRO;
			break;
		case 12:
			code = TradeConstant.TRADE_MODE_ALIPAYMICRO;
			break;
		default:
			break;
		}

		return code;
	}

}
