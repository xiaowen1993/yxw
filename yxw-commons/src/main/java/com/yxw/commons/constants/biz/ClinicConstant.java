package com.yxw.commons.constants.biz;

public class ClinicConstant {
	/**
	 * 缴费项唯一标识
	 */
	public static final String MZFEE_IDS = "mzFeeId";

	/**
	 * 医保类型
	 */
	public static final String MEDICARETYPE = "medicalInsuranceType";

	public static final String MEIDCARE_SELF_FINANCE = "自费";

	/**
	 * 有效的门诊订单
	 */
	public static final int RECORD_IS_VALID_TRUE = 1;

	/**
	 * 无效的门诊订单
	 */
	public static final int RECORD_IS_VALID_FALSE = 0;

	/**
	 * 默认的门诊缴费记录Title
	 */
	public static final String DEFAULT_CLINIC_TITLE = "门诊缴费";

	/**
	 * 支付医保：1支持
	 */
	public static final int SUPPORT_MEDICARE_TRUE = 1;

	/**
	 * 支持医保：0不支持
	 */
	public static final int SUPPORT_MEDICARE_FALSE = 0;

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
	 * 业务状态：门诊订单状态: 第三方缴费成功，写入His失败(中间状态)
	 */
	public static final int STATE_FAIL_HIS = 6;

	/**
	 * 业务状态：业务异常 需要人工处理
	 */
	public static final int STATE_EXCEPTION_NEED_PERSON_HANDLE = 7;

	/**
	 * 业务状态：业务异常 需要到医院窗口处理
	 */
	public static final int STATE_EXCEPTION_NEED_HOSPITAL_HANDLE = 8;

	/**
	 * 业务状态：业务异常     his支付异常后调用his接口查不到门诊单，有时是his处理过慢，需要轮询处理5-10分钟等待his返回真正结果
	 */
	public static final int STATE_PAY_EXCEPTION_NO_RECORD_WAIT = 9;

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
	public static final int STATE_PART_REFUND_EXCEPTION = 33;

	/**
	 * 后台退费:成功
	 */
	public static final int STATE_PLATFORM_REFUND_SUCCESS = 34;

	/**
	 * 后台退费:失败
	 */
	public static final int STATE_PLATFORM_REFUND_FAIL = 35;

	/**
	 * His门诊订单状态：未支付
	 */
	public static final String HIS_STATUS_NEVER_PAY = "0";

	/**
	 * His门诊订单状态：已支付
	 */
	public static final String HIS_STATUS_HAD_PAY = "1";

	/**
	 * His门诊订单状态：已退费
	 */
	public static final String HIS_STATUS_HAD_REFUND = "2";

	/**
	 * 部分退费的TItle
	 */
	public static final String CLNIC_PART_REFUND_TITLE = "门诊部分退费";

	/**
	 * 正常支付
	 */
	public static final int CLINIC_TYPE_COMMON_VAL = 0;

	/**
	 * 扫码支付 -- 直接到详情页面 （该平台暂不加）
	 */
	public static final int CLINIC_TYPE_QR_VAL = 1;

	/**
	 * 扫码支付 -- 不要求绑卡到缴费列表
	 */
	public static final String CLINIC_TYPE_ISSCANCODE = "isScanCode";
	public static final int CLINIC_TYPE_SCANCODE_VAL = 2;

	/**
	 * 外部消息连接：门诊待缴费消息
	 */
	public static final String CLINIC_URL_NEED_TO_PAY = "mobileApp/clinic/payIndexFromMsg";

}
