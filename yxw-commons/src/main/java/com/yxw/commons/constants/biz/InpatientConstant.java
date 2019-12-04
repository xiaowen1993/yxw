package com.yxw.commons.constants.biz;

public class InpatientConstant {
	/**
	 * 渠道来源： 微信
	 */
	public static final String MODE_TYPE_WEIXIN = "wechat";

	/**
	 * 渠道来源： 支付宝
	 */
	public static final String MODE_TYPE_ALIPAY = "alipay";

	/**
	 * 渠道来源： 未知
	 */
	public static final String MODE_TYPE_UNKNOWN = "未知";

	/**
	 * 渠道来源：微信
	 */
	public static final int MODE_TYPE_WEIXIN_VAL = 1;

	/**
	 * 渠道来源： 支付宝
	 */
	public static final int MODE_TYPE_ALIPAY_VAL = 2;
	
	/**
	 * 渠道来源：未知
	 */
	public static final int MODE_TYPE_UNKNOWN_VAL = 0;
	
	/**
	 * 有效的押金补缴订单
	 */
	public static final int RECORD_IS_VALID_TRUE = 1;
	
	/**
	 * 无效的押金补缴订单
	 */
	public static final int RECORD_IS_VALID_FALSE = 0;
	
	/**
	 * 默认的押金补缴记录Title
	 */
	public static final String DEFAULT_DEPOSIT_TITLE = "住院押金补缴";
	
	/**
	 * 业务状态： 待缴费
	 */
	public static final int STATE_NO_PAY = 0;
	
	/**
	 * 业务状态： 已缴费
	 */
	public static final int STATE_PAY_SUCCESS = 1;
	
	/**
	 * 业务状态：第三方缴费失败(要查询第三方接口，看有没有缴费)
	 */
	public static final int STATE_EXCEPTION_PAY = 2;
	
	/**
	 * 业务状态： 第三方缴费成功，写入His异常
	 */
	public static final int STATE_EXCEPTION_HIS = 3;
	
	/**
	 * 业务状态： 缴费关闭(第三方缴费成功，写入His明确失败，退费给用户成功，则缴费关闭)
	 */
	public static final int STATE_PAY_CLOSED = 4;
	
	/**
	 * 业务状态: 退费异常(第三方缴费成功，写入His明确失败，退费给用户失败/异常，这个需要走人工流程)
	 */
	public static final int STATE_EXCEPTION_REFUND = 5;
	
	/**
	 * 业务状态： 第三方缴费成功，写入His失败(中间状态)
	 */
	public static final int STATE_FAIL_HIS = 6;
	
	/**
	 * 业务状态：业务异常  需要人工处理
	 */
	public static final int STATE_EXCEPTION_NEED_PERSON_HANDLE = 7;

	/**
	 * 业务状态：业务异常  需要到医院窗口处理
	 */
	public static final int STATE_EXCEPTION_NEED_HOSPITAL_HANDLE = 8;
	
	/**
	 * 业务状态：窗口退费成功
	 */
	public static final int STATE_PERSON_HANDLE_SUCCESS = 20;
	
	/**
	 * 业务状态：窗口退费异常
	 */
	public static final int STATE_PERSON_HANDLE_EXCEPTION = 21;
	
	/**
	 * 业务状态：部分退费（His发起了，不管成功还是失败，都是部分退费）
	 */
	public static final int STATE_PART_REFUND = 30;
	
	/**
	 * 部分退费状态：成功
	 */
	public static final int STATE_PART_REFUND_SUCCESS = 31;
	
	/**
	 * 部分退费状态：失败
	 */
	public static final int STATE_PART_REFUND_FAIL = 32;
	
	/**
	 * 部分退费状态：异常
	 */
	public static final int STATE_PART_REFUND_EXCEPTION = 3;
	
	/**
	 * His押金补缴订单状态：未支付
	 */
	public static final String HIS_STATUS_NEVER_PAY = "0";
	
	/**
	 * His押金补缴订单状态：已支付
	 */
	public static final String HIS_STATUS_HAD_PAY = "1";
	
	/**
	 * His押金补缴订单状态：已退费
	 */
	public static final String HIS_STATUS_HAD_REFUND = "2";
	
	/**
	 * 默认的退费订单Title
	 */
	public static final String CLNIC_PART_REFUND_TITLE = "押金部分退费";
}
