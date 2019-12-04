/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.yxw.platform.sdk.alipay.constant;

/**
 * 支付宝服务窗环境常量（demo中常量只是参考，需要修改成自己的常量值）
 * 
 * @author taixu.zqq
 * @version $Id: AlipayServiceConstants.java, v 0.1 2014年7月24日 下午4:33:49
 *          taixu.zqq Exp $
 */
public class AlipayServiceEnvConstants {

	/** 支付宝公钥-从支付宝服务窗获取 */
	public static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

	/** 签名编码-视支付宝服务窗要求 */
	public static final String SIGN_CHARSET = "GBK";

	/** 字符编码-传递给支付宝的数据编码 */
	public static final String CHARSET = "GBK";

	/** 签名类型-视支付宝服务窗要求 */
	public static final String SIGN_TYPE = "RSA";

	/** 支付宝网关 */
	public static final String ALIPAY_GATEWAY = "https://openapi.alipay.com/gateway.do";

	/** 授权访问令牌的授权类型 */
	public static final String GRANT_TYPE = "authorization_code";

}
