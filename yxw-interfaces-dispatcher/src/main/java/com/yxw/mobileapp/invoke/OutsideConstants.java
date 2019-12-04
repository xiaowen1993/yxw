package com.yxw.mobileapp.invoke;

public class OutsideConstants {

	/**
	 * 对外接口ID后缀
	 */
	public static final String SERVICEID_SUFFIX = "OutsideInvokeService";

	/**
	 * 对外接口ID前缀 标准平台
	 */
	//public static final String SERVICEID_PREFIX_STANDARD = "standard";
	public static final String SERVICEID_PREFIX_STANDARD = "sp";
	public static final String ES_INDEX_PREFIX_STANDARD = "47.yx129";

	/**
	 * 对外接口ID前缀 新平台
	 */
	public static final String SERVICEID_PREFIX_PT = "pt";
	public static final String ES_INDEX_PREFIX_PT = "209.new_yx129_platform";

	/**
	 * 原渠道退费 - 判断条件:根据 订单号(psOrdNum),得到平台类型
	 */
	public static final String METHOD_CODE_REFUND_GENERAL = "refundGeneral";

	/**
	 * 模板消息推送
	 * 判断条件:
	 * 用户类别(userType) 
	 * 	1:诊疗卡
		2:微信公众号/支付宝服务窗 用户openId
		3:挂号订单号
		4:门诊订单号
		5:押金补缴订单号(暂不支持)
	 * 和
	 * 接收人(toUser) 诊疗卡号码,openId,挂号订单号,门诊订单号,押金补缴订单号
	 */
	public static final String METHOD_CODE_TEMPLATE_MSG_PUSH = "templateMsgPush";

	/**
	 * 客服消息推送 - 判断条件和模板消息一致
	 */
	public static final String METHOD_CODE_CUSTOMER_MSG_PUSH = "customerMsgPush";

	/**
	 * 医生停诊 - 判断条件:根据订单号列表(orderList),得到每个订单号的平台类型
	 */
	public static final String METHOD_CODE_STOP_REG = "stopReg";

	/**
	 * 订单查询
	 */
	public static final String METHOD_CODE_ORDERS_QUERY = "ordersQuery";

	/**
	 * 挂号订单查询
	 */
	public static final String METHOD_CODE_REG_ORDERS_QUERY = "regOrdersQuery";

	/**
	 * 门诊缴费支付二维码
	 */
	public static final String METHOD_CODE_QR_PAY = "qrPay";

}
