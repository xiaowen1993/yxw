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
package com.yxw.interfaces.constants;

/**
 * 交易结果代码
 * @Package: com.yxw.interfaces.constants
 * @ClassName: ResultCodeType
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年7月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ResultCodeType {
	/**
	 * 接口转换程序异常
	 */
	public static final String[] INTERFACE_CONVERSION_PROGRAM_ERROR = { "-2", "接口转换程序异常" };
	/**
	 * 请求医院接口异常
	 */
	public static final String[] REQUEST_HIS_INTERFACE_ERROR = { "-1", "请求医院接口异常" };
	/**
	 * 成功
	 */
	public static final String[] SUCCESS = { "0", "成功" };
	/**
	 * 成功,但未查询到数据
	 */
	public static final String[] SUCCESS_NO_DATA = { "1", "未查询到数据" };
	/**
	 * 成功,但不符合医院限定
	 */
	public static final String[] SUCCESS_NOT_HIS_LIMIT = { "2", "不符合医院限定" };
	/**
	 * 成功,但没有返回任何信息
	 */
	public static final String[] SUCCESS_NOT_ANY_INFO = { "3", "医院没有返回任何信息" };
}
