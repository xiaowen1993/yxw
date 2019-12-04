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
 * 支付状态
 * @Package: com.yxw.interfaces.constants
 * @ClassName: PaymentStatusType
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月30日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PaymentStatusType {
	/**
	 * 1:未支付（预约成功，可以进行预约取消，预约支付）
	 */
	public static final String NOT_PAID = "1";
	/**
	 * 2:已支付（预约成功，可以进行预约退费）
	 */
	public static final String PAID = "2";
	/**
	 * 3:已取消（用户取消）
	 */
	public static final String CANCELLED = "3";
	/**
	 * 4:缴费超时（超时未支付）
	 */
	public static final String PAYMENT_OVERTIME = "4";
	/**
	 * 5:已取号
	 */
	public static final String ALREADY_TAKE_NUMBER = "5";
	/**
	 * 6:已就诊
	 */
	public static final String HAS_BEEN_TREATED = "6";
	/**
	 * 7:已过期
	 */
	public static final String EXPIRED = "7";
	/**
	 * 8:已退费
	 */
	public static final String HAVE_A_REFUND = "8";
}
