/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-15</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.constants.biz;

/**
 * @Package: com.yxw.platform.order
 * @ClassName: OrderConstant
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class OrderConstant {
	/**
	 * 订单状态  未支付   1<br>
	 */
	public static final int STATE_NO_PAYMENT = 1;//TradeConstant.TRADE_STATE_NOTPAY

	/**
	 * 订单状态  已支付   2<br>
	 */
	public static final int STATE_PAYMENT = 2;//TradeConstant.TRADE_STATE_SUCCESS

	/**
	 * 订单状态  已退费   3<br>
	 */
	public static final int STATE_REFUND = 3;//TradeConstant.TRADE_STATE_REFUND

	/**
	 * 订单支付中   4<br>
	 */
	public static final int STATE_PAYMENTING = 4;

	/**
	 * 订单退费中   5<br>
	 */
	public static final int STATE_REFUNDING = 5;

	/**
	 * 订单已经关闭   6<br>
	 */
	public static final int STATE_CLOSE = 6;//TradeConstant.TRADE_STATE_CLOSED

	/**
	 * 订单状态 未退费   7<br>
	 */
	public static final int STATE_NO_REFUND = 7;

	/************************************************************/

	/**
	 * 订单类型-支付
	 */
	public static final int ORDER_TYPE_PAYMENT = 1;

	/**
	 * 订单类型-退费
	 */
	public static final int ORDER_TYPE_REFUND = 2;

	/**
	 * 订单类型-部分退费
	 */
	public static final int ORDER_TYPE_REFUND_PART = 3;
}
