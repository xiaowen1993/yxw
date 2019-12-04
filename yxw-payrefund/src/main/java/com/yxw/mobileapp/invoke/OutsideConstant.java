/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-8-5</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.invoke;

/**
 * 
 * @Package: com.yxw.mobileapp.invoke
 * @ClassName: CopyOfOutsideConstant
 * @Statement: <p>对外接口返回状态</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月5日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class OutsideConstant {
	/**
	 * 请求接口异常
	 */
	public static final String OUTSIDE_REQUEST_ERROR = "-1";
	/**
	 * 成功状态
	 */
	public static final String OUTSIDE_SUCCESS = "1";

	/**
	 * 已经操作状态
	 */
	public static final String OUTSIDE_OPERATED = "2";

	/**
	 * 失败状态
	 */
	public static final String OUTSIDE_ERROR = "0";

	/**
	 * 参数不能为空
	 */
	public static final String PARAMS_CANNOT_BE_NULL = "3";

	/**
	 * 参数未检索到结果（既参数不存在）
	 */
	public static final String NOT_RETRIEVED_RESULTS = "4";

	/**
	 * 参数格式错误
	 */
	public static final String PARAMS_FORMAT_ERROR = "5";

	/**
	 * 参数转换失败
	 */
	public static final String PARAMETER_CONVERSION_FAILURE = "6";

}
