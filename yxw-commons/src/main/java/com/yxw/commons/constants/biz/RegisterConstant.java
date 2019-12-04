package com.yxw.commons.constants.biz;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.yxw.platform.register
 * @ClassName: RegisterConstant
 * @Statement: <p>
 *             挂号业务中定义的常量
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-19
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterConstant {

	public static final Map<Integer, String> REGISTER_STATUS_MAP = new HashMap<Integer, String>();

	/**
	 * 预约中
	 */
	public static final int STATE_NORMAL_HAVING = 0;

	/**
	 * 已预约
	 */
	public static final int STATE_NORMAL_HAD = 1;

	/**
	 * 已取消-用户取消
	 */
	public static final int STATE_NORMAL_USER_CANCEL = 2;

	/**
	 * 已取消-支付超过规定时长
	 */
	public static final int STATE_NORMAL_PAY_TIMEOUT_CANCEL = 3;

	/**
	 * 已取消-停诊取消
	 */
	public static final int STATE_NORMAL_STOP_CURE_CANCEL = 4;

	/**
	 * 预约异常(his锁号异常)
	 */
	public static final int STATE_EXCEPTION_HAVING = 5;

	/**
	 * 第3方支付异常
	 */
	public static final int STATE_EXCEPTION_PAY = 6;

	/**
	 * 第3方支付成功后 his确认异常
	 */
	public static final int STATE_EXCEPTION_PAY_HIS_COMMIT = 7;

	/**
	 * 取消挂号异常
	 */
	public static final int STATE_EXCEPTION_CANCEL_EXCEPTION = 8;

	/**
	 * 第3方退费前 调用his退费确认 出现异常
	 */
	public static final int STATE_EXCEPTION_REFUND_HIS_CONFIRM = 9;

	/**
	 * 第3方退费异常
	 */
	public static final int STATE_EXCEPTION_REFUND = 10;

	/**
	 * 第3方退费成功后提交his确认异常
	 */
	public static final int STATE_EXCEPTION_REFUND_HIS_COMMIT = 11;

	/**
	 * 挂号失败
	 */
	public static final int STATE_EXCEPTION_FAILURE = 12;

	/**
	 * 异常导致的挂号取消 后续轮询处理 处理成功的最终状态
	 */
	public static final int STATE_EXCEPTION_CANCEL = 13;

	/**
	 * 业务异常 需要人工处理
	 */
	public static final int STATE_EXCEPTION_NEED_PERSON_HANDLE = 14;

	/**
	 * 业务异常 需要到医院窗口处理
	 */
	public static final int STATE_EXCEPTION_NEED_HOSPITAL_HANDLE = 15;

	/**
	 * 停诊取消异常 未支付
	 */
	public static final int STATE_EXCEPTION_STOP_CURE_CANCEL = 16;

	/**
	 * hia预约退费确认异常 停诊
	 */
	public static final int STATE_EXCEPTION_STOP_REFUND_HIS_CONFIRM = 17;

	/**
	 * 第3方退费异常 停诊
	 */
	public static final int STATE_EXCEPTION_STOP_REFUND = 18;

	/**
	 * 第3方退费成功后提交his确认异常 停诊
	 */
	public static final int STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT = 19;

	/**
	 * 窗口退费成功
	 */
	public static final int STATE_WINDOW_SUCCESSFUL_REFUND = 20;

	/**
	 * 窗口退费异常
	 */
	public static final int STATE_WINDOW_EXCEPTION_REFUND = 21;

	/**
	 * 用户取消预约中
	 */
	public static final int STATE_NORMAL_USER_CANCELING = 22;

	/**
	 * 后台退费:成功
	 */
	public static final int STATE_PLATFORM_REFUND_SUCCESS = 23;

	/**
	 * 后台退费:失败
	 */
	public static final int STATE_PLATFORM_REFUND_FAIL = 24;
	/**
	 * 有效记录
	 */
	public static final int RECORD_IS_VALID_TRUE = 1;

	/**
	 * 无效记录
	 */
	public static final int RECORD_IS_VALID_FALSE = 0;

	/**
	 * 所有时段
	 */
	public static final String TIME_FLAG_ALL = "0";

	/**
	 * 上午
	 */
	public static final String TIME_FLAG_AM = "1";

	/**
	 * 下午
	 */
	public static final String TIME_FLAG_PM = "2";

	/**
	 * 晚上
	 */
	public static final String TIME_FLAG_NIT = "3";

	/**
	 * his系统挂号记录的状态 未支付（预约成功，可以进行预约取消，预约支付）
	 */
	public static final String HIS_STATUS_NO_PAYMENT = "1";

	/**
	 * his系统挂号记录的状态 已支付（预约成功，可以进行预约退费）
	 */
	public static final String HIS_STATUS_HAD_PAYMENT = "2";

	/**
	 * his系统挂号记录的状态 已取消(用户取消)
	 */
	public static final String HIS_STATUS_PERSON_CANCEL = "3";

	/**
	 * his系统挂号记录的状态 缴费超时(超时未支付)
	 */
	public static final String HIS_STATUS_PAYMENT_TIME_OUT = "4";

	/**
	 * his系统挂号记录的状态 已取号
	 */
	public static final String HIS_STATUS_HAD_TAKENO = "5";

	/**
	 * his系统挂号记录的状态 已就诊
	 */
	public static final String HIS_STATUS_HAD_VISITED = "6";

	/**
	 * his系统挂号记录的状态 已过期
	 */
	public static final String HIS_STATUS_HAD_EXPIRE = "7";

	/**
	 * his系统挂号记录的状态 已退费
	 */
	public static final String HIS_STATUS_HAD_REFUND = "8";

	public static final String CALENDAR_SHOW_DAYS = "showDays";

	/**
	 * 默认暂不支付模式 支付截止时间类型 ：就诊时间段开始的时间
	 */
	public static final int DEF_NOT_PAID_PAYOFF_TYPE = 5;

	/**
	 * 默认允许当前挂号
	 */
	public static final int DEF_IS_HAS_DUTY_REG = 1;

	/**
	 * 当班挂号未到开始时间的默认提示语
	 */
	public static final String DEF_TIP_DUTY_NOT_TO_MSG = "还未到当班挂号的开始时间.请稍候.";

	/**
	 * 当班挂号超过截至时间的默认提示语
	 */
	public static final String DEF_TIP_DUTY_OVER_TIME_MSG = "已过当班挂号的截至时间.请选择其它日期.";

	/**
	 * 所选日期没有号源信息 默认提示语
	 */
	public static final String DEF_TIP_NO_REG_SOURCE = "所选日期没有号源信息,请选择其他日期.";

	/**
	 * 超过预约明天号截止时间点的提示语
	 */
	public static final String DEF_TIP_APPOINTMENT_OVER_TIME_MSG = "已过预约明天号的截止时间.请选择其它日期.";

	/**
	 * 确认挂号信息（暂不支付）页面提示语
	 */
	public static final String DEF_TIP_PAUSE_PAYMENT_MSG = "请在就诊时间段开始前30分钟到人工窗口或使用手机支付挂号费取号,否则号源将会作废,并视为爽约.";

	/**
	 * 超过每天允许取消挂号次数提示语
	 */
	public static final String DEF_TIP_OVER_CANCEL_MAXNUM_IN_DAY = "您今天取消挂号次数过多,本日内将无法挂号.";

	/**
	 * 超过每月允许取消挂号次数提示语
	 */
	public static final String DEF_TIP_OVER_CANCEL_MAXNUM_IN_MONTH = "您本月取消挂号次数超过上限,本月内无法挂号.";

	/**
	 * 即将达到每天允许取消挂号次数提示语
	 */
	public static final String WILL_REACH_CANCEL_MAXNUM_IN_DAY = "您即将达到每日允许取消挂号次数,下次取消后将无法挂号.";

	/**
	 * 已经达到每天允许取消挂号次数提示语
	 */
	public static final String HAD_REACH_CANCEL_MAXNUM_IN_DAY = "您已达到每日允许取消挂号次数,本日内将无法挂号.";

	/**
	 * 即将达到每月允许取消挂号次数提示语
	 */
	public static final String WILL_REACH_CANCEL_MAXNUM_IN_MONTH = "您即将达到每月允许取消挂号次数,下次取消后将无法挂号.";

	/**
	 * 已经达到每月允许取消挂号次数提示语
	 */
	public static final String HAD_REACH_CANCEL_MAXNUM_IN_MONTH = "您已达到每月允许取消挂号次数,本月内将无法挂号.";

	/**
	 * 支付超时后手动取消提示
	 */
	public static final String DEF_TIP_CANCEL_OVER_TIME_PAY = "该挂号超过支付时间,已进入系统自动取消预约队列,无须手动取消";

	/**
	 * 可享受医院挂号优惠提示语
	 */
	public static final String DEF_TIP_REG_DIS_COUNT = "已享受医院挂号优惠";

	/**
	 * 是否有搜素医生功能
	 */
	public static final int DEF_IS_HAS_SEARCH_DOCTOR_YES = 1;

	/**
	 * 取消当班挂号截止默认类型 就诊时间段前几小时
	 */
	public static final int DEF_CANCEL_ONDUTY_CLOSE_TYPE = 1;

	/**
	 * 取消预约挂号截止时间点的计算类型 就诊前一天几点
	 */
	public static final int DEF_CANCEL_BESPEAK_CLOSE_TYPE = 1;

	/**
	 * 默认 超过取消当班挂号截止时间提示语
	 */
	public static final String DEF_TIP_OVER_TIME_CANCEL_ONDUTY = "超过取消当班挂号截止时间点,不可取消挂号";

	/**
	 * 默认 超过取消预约挂号截止时间提示语
	 */
	public static final String DEF_TIP_OVER_TIME_CANCEL_APPOINTMENT = "超过取消预约挂号截止时间点,不可取消挂号";

	/**
	 * 默认 当班挂号是否支持退费 支持
	 */
	public static final int SUPPORT_REFUND_ONDUTY_YES = 1;

	public static final int SUPPORT_REFUND_ONDUTY_NO = 0;

	/**
	 * 日历显示类型 7天
	 */
	public static final int SEVEN_DAYS = 1;

	/**
	 * 日历显示类型 15天
	 */
	public static final int FIFTEEN_DAYS = 2;

	/**
	 * 日历显示类型 30天
	 */
	public static final int THIRTY_DAYS = 3;

	/**
	 * 日历显示类型 自定义
	 */
	public static final int CUSTOM_DAYS = -1;

	/**
	 * 
	 */
	public static final String IS_NOT_DOCTOR_FLAG = "专科号源";

	/**
	 * 专家号的表示
	 */
	public static final String PROFICIENT_NUM = "1";

	/**
	 * 可切换的日期
	 */
	public static final String OPTIONAL_DATES = "optionalDates";

	/**
	 * 当前选择的日期下标
	 */
	public static final String SELECTED_INDEX = "selectedIndex";

	/**
	 * 上午截至时间(时)
	 */
	public static final int ANTE_MERIDIEM_TIME = 12;

	/**
	 * 下午截至时间(时)
	 */
	public static final int POST_MERIDIEM_TIME = 18;

	/**
	 * 绑定诊疗卡类型 ：就诊卡
	 */
	public static final String DEFAULT_CARD_TYPE = "1";

	/**
	 * 1：为本人挂号 2：为子女挂号 3：为他人挂号
	 */
	public static final String DEFAULT_REG_USE_TYPE = "1";

	/******* 挂号类型,1：预约,2：当天 ************/
	public static final int REG_TYPE_APPOINTMENT = 1;
	public static final String REG_TYPE_APPOINTMENT_CHINESE = "预约";

	public static final int REG_TYPE_CUR = 2;
	public static final String REG_TYPE_CUR_CHINESE = "当天";
	/******* 挂号类型,1：预约,2：当天 ************/

	/************** 异常轮询队列 更新数据库时 传递Map的key ***************/
	public static final String UPDATE_EXCEPTION_PARAM_REGSTATUS = "regStatus";
	public static final String UPDATE_EXCEPTION_PARAM_PAYSTATUS = "payStatus";
	public static final String UPDATE_EXCEPTION_PARAM_ISEXCEPTION = "isException";
	public static final String UPDATE_EXCEPTION_PARAM_ISHANDLESUCCESS = "isHandleSuccess";
	public static final String UPDATE_EXCEPTION_PARAM_HANDLECOUNT = "handleCount";
	public static final String UPDATE_EXCEPTION_PARAM_HANDLELOG = "handleLog";
	public static final String UPDATE_EXCEPTION_PARAM_ORDERNO = "orderNo";
	public static final String UPDATE_EXCEPTION_PARAM_ORDERNO_HASHVAL = "orderNoHashVal";

	/**** 是否被第3方回调过 ****/
	public static final int IS_HAD_CALLBACK_YES = 1;
	public static final int IS_HAD_CALLBACK_NO = 0;

	/**
	 * 挂号规则-预约挂号是否支持退费 -支持
	 */
	public static final int SUPPORT_REFUND_APPOINTMENT_YES = 1;

	/**
	 * 挂号规则-是否允许异常订单退费 支持
	 */
	public static final int IS_EXCEPTION_REFUND_ORDER_YES = 1;

	/**
	 * 挂号规则-是否允许异常订单退费 不支持
	 */
	public static final int IS_EXCEPTION_REFUND_ORDER = 0;

	/**
	 * 挂号规则-是否显示当班剩余号源数-是
	 */
	public static final int IS_VIEW_SOURCE_NUM_YES = 1;

	/**
	 * 是否按医生职称排序-是
	 */
	public static final int ORDERBY_TITLE_YES = 1;
	/**
	 * 是否按医生职称排序-否
	 */
	public static final int ORDERBY_TITLE_NO = 0;

	/** 挂号规则-是否查询患者类型-是 */
	public static final int IS_QUERY_PATIENT_TYPE_YES = 1;
	/** 挂号规则-是否查询患者类型-否 */
	public static final int IS_QUERY_PATIENT_TYPE_NO = 0;
}
