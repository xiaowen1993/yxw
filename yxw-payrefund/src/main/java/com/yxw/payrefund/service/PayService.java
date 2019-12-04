package com.yxw.payrefund.service;

import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.entity.platform.payrefund.Alipay;
import com.yxw.commons.entity.platform.payrefund.Unionpay;
import com.yxw.commons.entity.platform.payrefund.WechatPay;

public interface PayService {
	
	/**
	 * 扫码支付(微信)
	 * @param pay 支付实体类
	 */
	public JSONObject wechatNativeService(WechatPay pay) throws Exception;
	
	
	/**
	 * 扫码支付（支付宝）
	 * @param pay 支付实体类
	 */
	public JSONObject alipayNativeService(Alipay pay) throws Exception;
	
	
	/**
	 * 扫码支付（银联）
	 * @param pay 支付实体类
	 */
	public JSONObject unionpayNativeService(Unionpay pay) throws Exception;

}
