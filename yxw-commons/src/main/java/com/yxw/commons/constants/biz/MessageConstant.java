package com.yxw.commons.constants.biz;

public class MessageConstant {
	/**
	 * 是否需要推送消息 1
	 */
	public static final int IS_NEED_PUSH_MSG_YES = 1;

	/**
	 * 是否需要推送消息 0
	 */
	public static final int IS_NEED_PUSH_MSG_NO = 0;

	/*** 消息推送点 常量定义 ***/

	/**
	 * 绑卡成功
	 */
	public static final int ACTION_TYPE_BIND_CARD_SUCCESS = 1;//

	/**
	 * 建档成功
	 */
	public static final int ACTION_TYPE_CREATE_CARD_SUCCESS = 2;//

	/**
	 * 就诊前一天是否推送
	 */
	public static final int ACTION_TYPE_PREDAY_VISIT = 3;//

	/**
	 * 就诊当天是否推送
	 */
	public static final int ACTION_TYPE_CURDAY_VISIT = 4;//

	/**
	 * 锁号成功(暂不支付 确定挂号成功后)
	 */
	public static final int ACTION_TYPE_LOCK_RES_SUCCESS = 5;//

	/**
	 * 当班挂号成功
	 */
	public static final int ACTION_TYPE_ON_DUTY_PAY_SUCCESS = 6;//

	/**
	 * 预约挂号成功
	 */
	public static final int ACTION_TYPE_APPOINT_PAY_SUCCESS = 7;//

	/**
	 * 挂号支付失败
	 */
	public static final int ACTION_TYPE_APPOINT_PAY_FAIL = 8;// 挂号支付失败

	/**
	 * 挂号支付异常
	 */
	public static final int ACTION_TYPE_APPOINT_PAY_EXP = 9;//

	/**
	 * 取消当班挂号 未支付
	 */
	public static final int ACTION_TYPE_CANCEL_ON_DUTY = 10;//

	/**
	 * 取消预约挂号 未支付
	 */
	public static final int ACTION_TYPE_CANCEL_APPOINTMENT = 11;//

	/**
	 * 挂号退费成功
	 */
	public static final int ACTION_TYPE_REFUND_SUCCESS = 12;//

	/**
	 * 挂号退费失败
	 */
	public static final int ACTION_TYPE_REFUND_FAIL = 13;//

	/**
	 * 挂号退费异常
	 */
	public static final int ACTION_TYPE_REFUND_EXCEPTION = 14;//

	/**
	 * 医生停诊
	 */
	public static final int ACTION_TYPE_STOP_VISIT = 15;//

	/**
	 * 候诊排队
	 */
	public static final int ACTION_TYPE_WAIT_VISIT = 16;//

	/**
	 * 门诊缴费成功
	 */
	public static final int ACTION_TYPE_CLINIC_PAY_SUCCESS = 17;//

	/**
	 * 门诊缴费失败
	 */
	public static final int ACTION_TYPE_CLINIC_PAY_FAIL = 18;//

	/**
	 * 门诊缴费异常
	 */
	public static final int ACTION_TYPE_CLINIC_PAY_EXP = 19;//

	/**
	 * 押金补缴成功
	 */
	public static final int ACTION_TYPE_DEPOSIT_PAY_SUCCESS = 20;//

	/**
	 * 押金补缴失败
	 */
	public static final int ACTION_TYPE_DEPOSIT_PAY_FAIL = 21;//

	/**
	 * 押金补缴异常
	 */
	public static final int ACTION_TYPE_DEPOSIT_PAY_EXP = 22;//

	/**
	 * 报告出结果
	 */
	public static final int ACTION_TYPE_GENERATE_REPORT = 23;//

	/**
	 * 门诊退费成功
	 */
	public static final int ACTION_TYPE_CLINIC_REFUND_SUCCESS = 24;

	/**
	 * 门诊部分退费成功
	 */
	public static final int ACTION_TYPE_CLINIC_PART_REFUND_SUCCESS = 25;

	/**
	 * 门诊缴费后的服务评价
	 */
	public static final int ACTION_TYPE_CLINIC_SUCCESS_COMMENT = 26;

	/**
	 * 完善用户资料的消息
	 */
	public static final int ACTION_TYPE_PERFECT_USER_INFO = 27;

	/**
	 * 挂号退费成功(预约)
	 */
	public static final int ACTION_TYPE_REFUND_SUCCESS_APPOINT = 33;// 
}
