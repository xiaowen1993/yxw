/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.message;

public class MessageConstant {
	/**
	 * 已删除
	 * */
	public static final int DELETE = 0;//
	/**
	 * 未删除
	 * */
	public static final int NORMAL = 1;//
	/**
	 * 禁用
	 * */
	public static final int DISABLE = 0;//
	/**
	 * // 启用
	 * */
	public static final int ENABLE = 1;

	/**
	 * 回复的内容类型 文字1
	 * */
	public static final int TEXT = 1;
	/**
	 * 回复的内容类型 图片2
	 * */
	public static final int IMAGE = 2;
	/**
	 * 回复的内容类型 图文3
	 * */
	public static final int IMAGETEXT = 3;
	/**
	 * 回复类型（1被添加/关注后回复 2关键词自动回复）
	 * */
	public static final int REPLY_FOUCSED = 1;
	/**
	 * 回复类型（1被添加/关注后回复 2关键词自动回复）
	 * */
	public static final int REPLY_KEYWORD = 2;
	/**
	 * 第三方类型（1微信 2支付宝）
	 * */
	public static final int THIRD_WECHAT = 1;
	/**
	 * 第三方类型（1微信 2支付宝）
	 * */
	public static final int THIRD_ALIPAY = 2;
	/**
	 * 第三方类型（1微信 2支付宝）
	 * */
	public static final String THIRD_WECHAT_STR = "1";
	/**
	 * 第三方类型（1微信 2支付宝）
	 * */
	public static final String THIRD_ALIPAY_STR = "2";
	/**
	 * 规则类型 （1随机回复 2 回复全部）
	 * */
	public static final int RULE_RANDOM = 1;
	/**
	 * 规则类型 （1随机回复 2 回复全部）
	 * */
	public static final int RULE_ALL = 2;

	public static final String LOGINED_USER = "loginedUser";// session中的登录用户KEY
	/**
	 * 图文类型 单图文 1 多图文 2
	 * */
	public static final int METERIAL_TYPE_SINGLE = 1;
	/**
	 * 图文类型 单图文 1 多图文 2
	 * */
	public static final int METERIAL_TYPE_MULTI = 2;

}
