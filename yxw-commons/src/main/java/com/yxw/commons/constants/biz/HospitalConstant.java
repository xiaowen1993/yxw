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
package com.yxw.commons.constants.biz;

/**
 * @Package: com.yxw.platform.hospital
 * @ClassName: HospitalConstant
 * @Statement: <p>
 *             医院常量定义
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-24
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HospitalConstant {
	/**
	 * 医院的状态 禁用
	 */
	public static final int HOSPITAL_INVALID_STATUS = 0;

	/**
	 * 医院的状态 启用
	 */
	public static final int HOSPITAL_VALID_STATUS = 1;

	/**
	 * 医院规则的状态 未发布
	 */
	public static final int HOSPITAL_RULE_NOT_PUBLISH = 0;

	/**
	 * 医院规则的状态 已发布
	 */
	public static final int HOSPITAL_RULE_PUBLISH = 1;

	/**
	 * 菜单类型 0：功能
	 * */
	public static final int MENU_TYPE_FUNCTION = 0;
	/**
	 * 菜单类型 1：图文
	 * */
	public static final int MENU_TYPE_IMAGETEXT = 1;
	/**
	 * 菜单类型 2：链接地址
	 * */
	public static final int MENU_TYPE_URL = 2;
}
