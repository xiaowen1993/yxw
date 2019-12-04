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
package com.yxw.stats.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Package: com.yxw.mobileapp.invoke
 * @ClassName: ReceiveConstant
 * @Statement: <p>
 *             内部参数
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ReceiveConstant {

	/**
	 * 保存停诊订单的map集合，如果已经存在，则不处理新增加的订单
	 */
	public static ConcurrentHashMap<String, Object> stopRegOrders = new ConcurrentHashMap<String, Object>();

	/**
	 * 停诊订单超时删除时长 5分钟
	 */
	public static final long STOP_REG_MAP_OUT_TIME = 5 * 60 * 1000;

	/**
	 * 标识查询所有支付第三方
	 */
	public static final String TRADEMODE_ALL = "0";

	/**
	 * 标识查询所有订单(判断是否查询所有挂号订单，即不区分查询预约和挂号订单)
	 */
	public static final String REGTYPE_ALL = "0";

	/**
	 * 分表数量
	 */
	public static final int TABLE_SIZE = 10;

	/**
	 * 查询所有标识
	 */
	public static final int QUERY_ALL_TYPE = 0;

	/**
	 * 平台参数集合
	 */
	public static Map<Object, Object> tradeModeParams = new HashMap<Object, Object>();

	static {
		tradeModeParams.put(String.valueOf(BizConstant.MODE_TYPE_WEIXIN_VAL), BizConstant.MODE_TYPE_WEIXIN);
		tradeModeParams.put(String.valueOf(BizConstant.MODE_TYPE_ALIPAY_VAL), BizConstant.MODE_TYPE_ALIPAY);
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
	 * 对账订单查询接口
	 */
	public static final String METHOD_BILL_QUERY = "billQuery";

	/**
	 * 对账订单查询接口 东莞
	 */
	public static final String METHOD_GET_DAILY_BILL = "getDailyBill";

	/**
	 * 第三方订单查询接口
	 */
	public static final String METHOD_TRADE_ORDER_QUERY = "tradeOrderQuery";

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
	 * 客服消息推送接口名称
	 */
	public static final String METHOD_HOSPITAL_QUERY = "hospitalQuery";

	/**
	 * 客服消息推送接口名称
	 */
	public static final String METHOD_MEDICAL_CARD_QUERY = "medicalCardQuery";
	/**
	 * 客服消息推送接口名称
	 */
	public static final String METHOD_UNBIND_MEDICAL_CARD_STA = "unBindMedicalCardSta";

	/**
	 * 匹配模板消息推送接口名称
	 */
	public static final String MATCHER_MSG_SEND = "matcherMsgSend";
	/**
	 * 门诊缴费扫码支付接口名称
	 */
	public static final String METHOD_QR_PAY = "qrPay";
	public static final String METHOD_QR_PAY_OLD = "qrPay2";

	/**
	 * 扫码支付方式二（跳转页面支付）页面的url
	 */
	public final static String QR_PAY_FORWARD_URL = "qr_pay_forward_url";

	/**
	 * 条码交易支付接口
	 */
	public final static String BAR_CODE_PAY = "barCodePay";

	/**
	 * 条码交易查询接口
	 */
	public final static String BAR_CODE_ORDER_QUERY = "barCodeOrderQuery";

	/**
	 * 条码交易撤销接口
	 */
	public final static String BAR_CODE_CANCEL = "barCodeCancel";

	/**
	 * 条码交易退费接口
	 */
	public final static String BAR_CODE_REFUND = "barCodeRefund";

	/**
	 * 接口方法
	 */
	public static Map<Object, Object> methodCodeParams = new HashMap<Object, Object>();

	static {
		methodCodeParams.put(METHOD_STOP_REG, METHOD_STOP_REG);
		methodCodeParams.put(METHOD_REFUND_GENERAL, METHOD_REFUND_GENERAL);
		methodCodeParams.put(METHOD_ORDERS_QUERY, METHOD_ORDERS_QUERY);
		methodCodeParams.put(METHOD_REG_ORDERS_QUERY, METHOD_REG_ORDERS_QUERY);
		methodCodeParams.put(METHOD_TEMPLATE_MSG_PUSH, METHOD_TEMPLATE_MSG_PUSH);
		methodCodeParams.put(METHOD_CUSTOMER_MSG_PUSH, METHOD_CUSTOMER_MSG_PUSH);
		methodCodeParams.put(METHOD_QR_PAY, METHOD_QR_PAY);
		methodCodeParams.put(METHOD_QR_PAY_OLD, METHOD_QR_PAY_OLD);
		methodCodeParams.put(METHOD_HOSPITAL_QUERY, METHOD_HOSPITAL_QUERY);
		methodCodeParams.put(METHOD_MEDICAL_CARD_QUERY, METHOD_MEDICAL_CARD_QUERY);
		methodCodeParams.put(METHOD_UNBIND_MEDICAL_CARD_STA, METHOD_UNBIND_MEDICAL_CARD_STA);
		methodCodeParams.put(MATCHER_MSG_SEND, MATCHER_MSG_SEND);
		methodCodeParams.put(BAR_CODE_PAY, BAR_CODE_PAY);
		methodCodeParams.put(BAR_CODE_ORDER_QUERY, BAR_CODE_ORDER_QUERY);
		methodCodeParams.put(BAR_CODE_CANCEL, BAR_CODE_CANCEL);
		methodCodeParams.put(BAR_CODE_REFUND, BAR_CODE_REFUND);
		methodCodeParams.put(METHOD_BILL_QUERY, METHOD_BILL_QUERY);
		methodCodeParams.put(METHOD_TRADE_ORDER_QUERY, METHOD_TRADE_ORDER_QUERY);
		methodCodeParams.put(METHOD_GET_DAILY_BILL, METHOD_GET_DAILY_BILL);
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
