/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.constant;

import com.yxw.framework.config.SystemConfig;

/**
 * @Package: com.yxw.platform.sdk.app.constant
 * @ClassName: EasyHealthConstant
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class EasyHealthConstant {
	/**
	 * php消息推送接口地址
	 */
	public static final String URL = SystemConfig.getStringValue("url");
	public static final String MODULE = SystemConfig.getStringValue("module");
	public static final String CONTROLLER = SystemConfig.getStringValue("controller");
	public static final String ACTION = SystemConfig.getStringValue("action");
	/**
	 * php接口授权码
	 */
	public static final String SECRET = SystemConfig.getStringValue("secret");
	/**
	 * 百度应用的apiKey
	 */
	public static final String API_KEY = SystemConfig.getStringValue("apiKey");
	/**
	 * 百度应用的secretKey
	 */
	public static final String SECRET_KEY = SystemConfig.getStringValue("secretKey");
	/**
	 * 苹果推送证书名
	 */
	public static final String CERTIFICATE = SystemConfig.getStringValue("certificate");
	/**
	 * 苹果
	 */
	public static final String APPLE = "1";
	/**
	 * 安卓
	 */
	public static final String ANDROID = "2";
	/**
	 * 打开方式,1：跳转url地址
	 */
	public static final String OPEN_TYPE_ = "1";

	/**
	 * 保存在session中的登录信息键值常量
	 * */
	public final static String SESSION_USER_ENTITY = "sessionUser";

	/**
	 * 保存在cookies中的登录信息键值常量
	 * */
	public final static String COOKIES_USER_ENTITY = "cookiesUser";

	public final static String SESSION_FAMILY_SIZE = "sessionFamilySize";

	public final static String SESSION_IS_FAMILY_INFO = "sessionIsFamilyInfo";

	public final static String SESSION_IS_FAMILY_INFO_NO_VAL = "0";//没有就诊人信息
	public final static String SESSION_IS_FAMILY_INFO_YES_VAL = "1";//有就诊人信息

	/**
	 * 保存在cookies中的登录信息键值常量
	 * */
	public final static int COOKIES_USER_MAX_AGE = 30;
	/**
	 * 健康易平台code
	 * */
	public final static String EASY_HEALTH = "easyHealth";

	public final static String SESSION_APP_CODE = "appCode";
	public final static String SESSION_APP_COLOR = "appColor";
	public final static String SESSION_AREA_CODE = "areaCode";
	public final static String APP_CODE_APP = "app";
	public final static String APP_CODE_WECHAT = "wechat";
	public final static String APP_CODE_ALIPAY = "alipay";
	public final static String APP_CODE_UNIONPAY = "innerUnionPay";
	public final static String APP_CODE_INNERCHINALIFE = "innerChinaLife";
	public final static String UNIONPAY_APPID = SystemConfig.getStringValue("unionPayAppid");
	public final static String UNIONPAY_SECRET = SystemConfig.getStringValue("unionPaySecret");
	public final static String UNION_3DES_KEY = SystemConfig.getStringValue("union3DESKey");
	public final static String UNIONPAY_SCOPE_UPAPI_BASE = "upapi_base";
	public final static String UNIONPAY_SCOPE_UPAPI_MOBILE = "upapi_mobile";

	public final static String ES_INTERFACE_ID = "yxwESService";

	public final static String YXW_RSA_PRIVATE_KEY = SystemConfig.getStringValue("yxwRSAPrivateKey");
	public final static String YXW_RSA_PUBLIC_KEY = SystemConfig.getStringValue("yxwRSAPublicKey");
	public final static String INNERCHINALIFE_RSA_PUBLIC_KEY = SystemConfig.getStringValue("innerChinaLifeRSAPublicKey");

}
