/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.constants.biz;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.yxw.commons.hash.SimpleHashTableNameGenerator;

/**
 * @Package: com.yxw.mobileapp.invoke
 * @ClassName: ReceiveConstant
 * @Statement: <p>内部参数</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ReceiveConstant {

	/**
	 * 标识查询所有支付第三方
	 */
	public static final String TRADEMODE_ALL = "0";

	/**
	 * 标识查询所有订单(判断是否查询所有挂号订单，即不区分查询预约和挂号订单)
	 */
	public static final String REGTYPE_ALL = "0";

	/**
	 * 标识查询新平台
	 */
	public static final String TRADEMODE_APP = "3";

	/**
	 * 分表数量
	 */
	public static final int TABLE_SIZE = 10;

	/**
	 * 查询所有标识
	 */
	public static final int QUERY_ALL_TYPE = 0;

	/**
	 * his原渠道退费传过来的tradeMode=9，固定值，his对应医享网平台是9
	 */
	public static final String HIS_TRADE_MODE = "9";

	/**
	 * 交易参数类型
	 */
	public static Map<Object, Object> tradeTypeParams = new HashMap<Object, Object>();

	static {
		tradeTypeParams.put(String.valueOf(BizConstant.BIZ_TYPE_REGISTER), SimpleHashTableNameGenerator.REGISTER_RECORD_TABLE_NAME);
		tradeTypeParams.put(String.valueOf(BizConstant.BIZ_TYPE_CLINIC), SimpleHashTableNameGenerator.CLINIC_RECORD_TABLE_NAME);
		tradeTypeParams.put(String.valueOf(BizConstant.BIZ_TYPE_DEPOSIT), SimpleHashTableNameGenerator.DEPOSIT_RECORD_TABLE_NAME);
	}

	/**
	 * 停诊接口名称
	 */
	public static final String METHOD_STOP_REG = "stopReg";

	/**
	 * 退费接口名称
	 */
	public static final String METHOD_REFUND_GENERAL = "refundGeneral";

	/**
	 * 订单查询接口名称
	 */
	public static final String METHOD_ORDERS_QUERY = "ordersQuery";

	/**
	 * 挂号订单查询接口
	 */
	public static final String METHOD_REG_ORDERS_QUERY = "regOrdersQuery";

	/**
	 * 模板消息推送接口名称
	 */
	public static final String METHOD_TEMPLATE_MSG_PUSH = "templateMsgPush";

	/**
	 * 客服消息推送接口名称
	 */
	public static final String METHOD_CUSTOMER_MSG_PUSH = "customerMsgPush";

	/**
	 * 对账订单查询接口
	 */
	public static final String METHOD_BILL_QUERY = "billQuery";

	/**
	 * 第三方订单查询接口
	 */
	public static final String METHOD_TRADE_ORDER_QUERY = "tradeOrderQuery";

	
	
	
	/**
	 * 接口方法
	 */
	// public static Map<Object, Object> methodCodeParams = new HashMap<Object, Object>();
	public static Set<Object> methodCodeParams = new HashSet<Object>();

	static {
		//		methodCodeParams.put(METHOD_STOP_REG, METHOD_STOP_REG);
		//		methodCodeParams.put(METHOD_REFUND_GENERAL, METHOD_REFUND_GENERAL);
		//		methodCodeParams.put(METHOD_ORDERS_QUERY, METHOD_ORDERS_QUERY);
		//		methodCodeParams.put(METHOD_TEMPLATE_MSG_PUSH, METHOD_TEMPLATE_MSG_PUSH);
		//		methodCodeParams.put(METHOD_CUSTOMER_MSG_PUSH, METHOD_CUSTOMER_MSG_PUSH);
		//		methodCodeParams.put(METHOD_REG_ORDERS_QUERY, METHOD_REG_ORDERS_QUERY);
		methodCodeParams.add(METHOD_STOP_REG);
		methodCodeParams.add(METHOD_REFUND_GENERAL);
		methodCodeParams.add(METHOD_ORDERS_QUERY);
		methodCodeParams.add(METHOD_TEMPLATE_MSG_PUSH);
		methodCodeParams.add(METHOD_CUSTOMER_MSG_PUSH);
		methodCodeParams.add(METHOD_REG_ORDERS_QUERY);
		methodCodeParams.add(METHOD_BILL_QUERY);
		methodCodeParams.add(METHOD_TRADE_ORDER_QUERY);
//		methodCodeParams.add(PAY_DETAIL_QUERY);
//		methodCodeParams.add(PRESCRIPTION_DETAIL_QUERY);
//		methodCodeParams.add(MEDICAL_SETTLEMENT_QUERY);
	}

	/**
	 * 全部退费
	 */
	public static final int ALL_REFUND = 1;

	/**
	 * 部分退费
	 */
	public static final int PARTIAL_REFUND = 2;

	/**
	 * 推送消息
	 */
	public static final int PUSH_MESSAGE_TYPE = 1;

	/**
	 * 不推送消息
	 */
	public static final int NO_PUSH_MESSAGE_TYPE = 2;

}
