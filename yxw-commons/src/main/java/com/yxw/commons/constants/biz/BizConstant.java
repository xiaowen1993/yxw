package com.yxw.commons.constants.biz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @Package: com.yxw.mobileapp.biz
 * @ClassName: BizConstant
 * @Statement: <p>
 *             标准平台前端系统定义的公共常量
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-9
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class BizConstant {
	/**
	 * openId的KEY 从session的属性中直接获取
	 * */
	public final static String OPENID = "openId";

	/**
	 * 支付宝/微信的openId
	 * */
	public final static String USERID = "userId";
	/**
	 * appId的Key
	 */
	public final static String APPID = "appId";
	/**
	 * appCode的Key
	 */
	public final static String APPCODE = "appCode";
	/**
	 * areaCode的Key
	 */
	public final static String AREACODE = "areaCode";

	public final static String ORDERNO_KEY = "orderNo";

	/**
	 * 中文日期 yyyy-MM-dd E
	 */
	public static DateFormat YYYYMMDDE = new SimpleDateFormat("yyyy-MM-dd E", Locale.CHINESE);

	/**
	 * yyyy-MM-dd
	 */
	public static DateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static DateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static DateFormat YYYYMMDDHHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * HH:mm
	 */
	public static DateFormat HHMM = new SimpleDateFormat("HH:mm");

	/**
	 * 公共ajax请求是否成功key
	 */
	public static final String COMMON_AJAX_REQ_SUCCESS_KEY = "isSuccess";
	/**
	 * 公共传值实体列表对象的key
	 */
	public static final String COMMON_ENTITY_LIST_KEY = "entityList";

	/**
	 * 公共传值实体对象的key
	 */
	public static final String COMMON_ENTITY_KEY = "entity";

	/**
	 * 公共传值参数对象的key
	 */
	public static final String COMMON_PARAMS_KEY = "commonParams";

	/**
	 * 公共配置规则参数对象的key
	 */
	public static final String RULE_CONFIG_PARAMS_KEY = "ruleConfig";

	/**
	 * 公共记录对象的Key
	 */
	public static final String RECORD_PARAMS_KEY = "record";

	/**
	 * his订单类型，统一对应yxw一个平台，默认值：3
	 */
	public static final String HIS_ORDER_MODE_VAL = "3";

	/**
	 * 平台类型
	 */
	public static final String MODE_TYPE_WECHAT = "wechat";
	public static final String MODE_TYPE_ALIPAY = "alipay";
	public static final String MODE_TYPE_AUTOSERVICE = "autoService";
	public static final String MODE_TYPE_APP = "app";
	public static final String MODE_TYPE_INNER_WECHAT = "innerWechat";
	public static final String MODE_TYPE_INNER_ALIPAY = "innerAlipay";
	public static final String MODE_TYPE_INNER_UNIONPAY = "innerUnionPay";
	public static final String MODE_TYPE_INNER_CHINALIFE = "innerChinaLife";

	public static final String MODE_TYPE_OTHER = "未知";
	public static final int MODE_TYPE_OTHER_VAL = 0;
	public static final int MODE_TYPE_WECHAT_VAL = 1;
	public static final int MODE_TYPE_ALIPAY_VAL = 2;
	//	public static final int MODE_TYPE_EASYHEALTH_WEIXIN_VAL = 3;
	//	public static final int MODE_TYPE_EASYHEALTH_ALIPAY_VAL = 4;
	//	public static final int MODE_TYPE_EASYHEALTH_UNIONPAY_VAL = 5;
	public static final int MODE_TYPE_AUTOSERVICE_VAL = 6;

	public static final int MODE_TYPE_APP_VAL = 20;
	public static final int MODE_TYPE_INNER_WECHAT_VAL = 21;
	public static final int MODE_TYPE_INNER_ALIPAY_VAL = 22;
	public static final int MODE_TYPE_INNER_UNIONPAY_VAL = 23;
	public static final int MODE_TYPE_INNER_CHINALIFE_VAL = 24;

	/**
	 * 消息模板平台（不同于实际的平台情况）
	 */
	public static final int MSG_TYPE_APP_BASE_VAL = 1;
	public static final int MSG_TYPE_APP_VAL = 2;
	public static final int MSG_TYPE_APP_BASE_INNER_UNIONPAY_VAL = 3;

	public static final String MSG_YPE_APP_BASE = "appBase";
	public static final String MSG_TYPE_APP = "app";
	public static final String MSG_TYPE_APP_BASE_INNER_UNIONPAY = "appBaseInnerUnionpay";
	public static final String MSG_TYPE_APP_BASE_INNER_CHINALIFE = "appBaseInnerChinaLife";

	/**
	 * 业务类型 - 挂号
	 */
	public static final int BIZ_TYPE_REGISTER = 1;
	public static final String BIZ_TYPE_REGISTER_NAME = "挂号";

	/**
	 * 业务类型 - 门诊
	 */
	public static final int BIZ_TYPE_CLINIC = 2;
	public static final String BIZ_TYPE_CLINIC_NAME = "门诊";

	/**
	 * 业务类型 - 住院
	 */
	public static final int BIZ_TYPE_DEPOSIT = 3;
	public static final String BIZ_TYPE_DEPOSIT_NAME = "住院";

	/**
	 * 接口执行是否异常
	 */
	public static final String INTERFACE_EXEC_IS_EXCEPTION = "isException";

	/**
	 * 接口执行是否成功
	 */
	public static final String INTERFACE_EXEC_IS_SUCCESS = "isSuccess";

	/**
	 * 接口返回信息的 结果码key
	 */
	public static final String INTERFACE_MAP_CODE_KEY = "resCode";

	/**
	 * 接口返回信息的 数据key
	 */
	public static final String INTERFACE_MAP_DATA_KEY = "data";

	/**
	 * 接口返回 说明信息
	 */
	public static final String INTERFACE_MAP_MSG_KEY = "msgInfo";

	/**
	 * 接口请求成功代码
	 */
	public static final String INTERFACE_RES_SUCCESS_CODE = "0";

	/**
	 * 请求成功但没有查询到数据
	 */
	public static final String INTERFACE_RES_SUCCESS_NO_DATA_CODE = "1";

	/**
	 * 请求成功 但数据不合法执行失败
	 */
	public static final String INTERFACE_RES_SUCCESS_INVALID_DATA_CODE = "2";

	/**
	 * 不符合医院限定 不允许操作
	 */
	public static final String INTERFACE_RES_SUCCESS_HIS_INVALID = "2";

	/**
	 * 重复请求
	 */
	public static final String INTERFACE_RES_RE_OP_CODE = "1";

	/**
	 * 接口转换程序请求his异常代码
	 */
	public static final String INTERFACE_RES_EXCEPTION_CODE = "-1";

	/**
	 * 
	 */
	public static final String INTERFACE_RES_FAILURE_MSG = "网络异常,请保持您的网络通畅,稍后再试.";

	/**
	 * 调用方法执行结果是否成功
	 */
	public static final String METHOD_INVOKE_RES_ISSUCCESS_KEY = "isSuccess";

	public static final String METHOD_INVOKE_SUCCESS = "success";
	public static final String METHOD_INVOKE_FAILURE = "fail";
	public static final String METHOD_INVOKE_EXCEPTION = "exception";

	/**
	 * 是否重复提交/调用
	 */
	public static final String METHOD_INVOKE_RE_OPERATION = "isReInvoke";

	/**
	 * 调用方法执行后 返回的信息
	 */
	public static final String METHOD_INVOKE_RES_MSG_KEY = "msgInfo";

	/**
	 * 调用方法执行 返回的数据
	 */
	public static final String METHOD_INVOKE_RES_DATA_KEY = "data";

	/** * 公共页面跳转业务编码 */

	public static final String BIZ_CODE_PARAM_NAME = "bizCode";

	/**
	 * 消息详情地址需要的参数key 值形式为:?openId=xxxx&orderNo=xxxxxx
	 */
	public static final String MSG_PUSH_URL_PARAMS_KEY = "urlParams";

	/**
	 * 挂号
	 */
	public static final int BIZ_CODE_REGISTER = 1;

	/**
	 * 门诊
	 */
	public static final int BIZ_CODE_CLINIC = 2;

	/**
	 * 住院
	 */
	public static final int BIZ_CODE_HOSPITALIZATION = 3;

	/**
	 * 检查检验
	 */
	public static final int BIZ_CODE_INSPECT = 4;
	/**************** 公共页面跳转业务编码 ****************/

	/*************** 在线支付控制类型 ****************/
	/**
	 * 需要在线支付
	 */
	public static final int PAYMENT_TYPE_MUST = 1;

	/**
	 * 不用在线支付
	 */
	public static final int PAYMENT_TYPE_NOT_NEED = 2;

	/**
	 * 支持暂不支付
	 */
	public static final int PAYMENT_TYPE_SUPPORT_TEMPORARILY_NOT = 3;

	/**
	 * 支持暂不支付 选择支付
	 */
	public static final int PAYMENT_TYPE_SUPPORT_TEMPORARILY_NOT_IS_PAY_YES = 1;

	/*************** 在线支付控制类型 ****************/

	/**
	 * 默认显示病情描述
	 */
	public static final int DEFAULT_VIEW_DISEASEDESC = 1;

	public static final String TRADE_PAY_KEY = "pay";
	public static final String TRADE_REFUND_KEY = "refund";

	/**
	 * 
	 */
	public static final String TRADE_REMOTE_URL_PROPERTIES_KEY = "trading_platform_url";

	/**
	 * 
	 */
	public static final String TRADE_URL_KEY = "tradeUrl";

	/**
	 * 
	 */
	public static final String TRADE_NUM_PARAM_NAME = "tradeNo";

	/**
	 * 交易方式 1:微信 2：支付宝 3.银联
	 */
	public static final String TRADE_MODE = "tradeMode";

	/**
	 * 是否异常
	 */
	public static final String TRADE_IS_EXCEPTION = "isException";

	/**
	 * 失败msg
	 */
	public static final String TRADE_FAIL_MSG = "failMsg";

	/**
	 * 医院业务规则配置表信息
	 */
	public static final Map<String, String> ruleTableMap = new HashMap<String, String>();
	static {
		ruleTableMap.put("SYS_RULE_EDIT", "全局规则");
		ruleTableMap.put("SYS_RULE_ONLINE_FILING", "在线建档规则");
		ruleTableMap.put("SYS_RULE_PAYMENT", "缴费规则");
		ruleTableMap.put("SYS_RULE_QUERY", "查询规则");
		ruleTableMap.put("SYS_RULE_REGISTER", "挂号规则");
		ruleTableMap.put("SYS_RULE_TIED_CARD", "绑卡规则");
		ruleTableMap.put("SYS_RULE_PUSH_ALIPAY", "消息推送规则");
		ruleTableMap.put("SYS_RULE_USER_CONTENT", "个人中心规则");
		ruleTableMap.put("SYS_RULE_HIS_DATA", "his数据轮询规则");
	}

	/**
	 * 执行成功
	 */
	public static final String SUCCESS = "success";

	/**
	 * 执行失败
	 */
	public static final String FAIL = "fail";

	/**
	 * 执行失败，信息提示
	 */
	public static final String PROMTP = "prompt";

	/***************************** 异常相关变量定义 start *********************/
	/**
	 * 发生异常
	 */
	public static final int IS_HAPPEN_EXCEPTION_YES = 1;

	/**
	 * 未发生异常
	 */
	public static final int IS_HAPPEN_EXCEPTION_NO = 0;
	/**
	 * 异常处理/方法调用 已进行
	 */
	public static final int HANDLED_HAD_HANDLED = 1;

	/**
	 * 异常处理/方法调用 未进行
	 */
	public static final int HANDLED_NO_HANDLED = 0;

	/**
	 * 异常处理/方法调用 成功
	 */
	public static final int HANDLED_SUCCESS = 1;

	/**
	 * 异常处理/方法调用 失败
	 */
	public static final int HANDLED_FAILURE = 0;

	/**
	 * 异常处理/方法调用 超过最大次数(最大次数 3次)
	 */
	public static final int HANDLED_TIME_OVER_MAXNUM = 3;

	/***************************** 异常相关变量定义 start *********************/

	public static final String ALI_REFUND_SPLIT_CHAR = "^";

	public static final int SEX_MAN = 1;
	public static final int SEX_WOMEN = 2;

	/**
	 * 跳转链接的参数名
	 */
	public static final String URL_PARAM_FORWARD_NAME = "forward";
	/**
	 * PHP跳转参数名
	 */
	public static final String URL_PARAM_PHP_FORWARD_NAME = "redirect_uri";

	/**
	 * 系统前端请求
	 */
	public static final String INVOKE_TYPE_WEB_REQ = "webReq";
	/**
	 * 系统后台请求
	 */
	public static final String INVOKE_TYPE_SERVICE_REQ = "serviceReq";

	public static final String CHECK_IS_VALID_RES_KEY = "isValid";

	public static final String VIEW_SENSITIVE_CHAR = "*";

	/**
	 * 是否被第三方回调
	 */
	public static final int IS_HAD_CALLBACK_YES = 1;
	public static final int IS_HAD_CALLBACK_NO = 0;

	/**
	 * 挂号支付后的回调方法名
	 */
	public static final String PAY_NOTIFY_METHOD_NAME_REG = "regPayMent";

	/**
	 * 门诊支付后的回调方法名
	 */
	public static final String PAY_NOTIFY_METHOD_NAME_CLINIC = "clinicPayMent";

	/**
	 * 押金补缴支付后的回调方法名
	 */
	public static final String PAY_NOTIFY_METHOD_NAME_DEPOSIT = "depositPayMent";

	/**
	 * 白名单开启
	 */
	public static final int WHITE_LIST_IS_OPEN_YES = 1;

	/** 公共传递参数定义 **/
	public static final String URL_PARAM_CHAR_FIRST = "?";
	public static final String URL_PARAM_CHAR_ASSGIN = "=";
	public static final String URL_PARAM_CHAR_CONCAT = "&";
	public static final String URL_PARAM_ORDER_NO = "orderNo";
	public static final String URL_PARAM_APPID = "appId";
	public static final String URL_PARAM_APPCODE = "appCode";
	public static final String URL_PARAM_OPEN_ID = "openId";
	public static final String URL_PARAM_CARD_NO = "cardNo";
	public static final String URL_PARAM_HOSPITAL_CODE = "hospitalCode";
	public static final String URL_PARAM_BRANCH_HOSPITAL_CODE = "branchHospitalCode";
	public static final String URL_PARAM_TRADE_MODE = "tradeMode";
	public static final String URL_PARAM_TRADE_AMOUT = "tradeAmout";
	public static final String URL_PARAM_REG_TYPE = "regType";
	public static final String URL_PARAM_FOOT_LOGO_INFO = "footLogo";
	public static final String URL_PARAM_VOTE_TYPE = "voteType";
	public static final String URL_PARAM_WHITE_LIST_TIP = "whiteListTip";

	public static final String CANCEL_REG_IS_TIP = "isTip";

	public static final String CANCEL_REG_TIP_MSG = "tipMsg";

	/**
	 * 通用的在Session中存放URL跳转所需参数定义
	 */
	public static final String COMMON_SESSION_PARAMS = "commonSessionParams";

	/**
	 * 支付的请求url(后半截固定部分)
	 */
	//	public static final String TRADE_PATH_PREFIX = "/pay/info/";

	/**
	 * 是否已评价- 1:已评价 0:待评价
	 */
	public static final int VOTE_IS_HAD_YES = 1;
	public static final int VOTE_IS_HAD_NO = 0;

	/*** 评价列表显示类型 **********/
	public static final String VOTE_VIEW_LIST_NO_VOTE = "noVote";
	public static final String VOTE_VIEW_LIST_HAD_VOTE = "hadVote";
	public static final String VOTE_VIEW_LIST_ALL_VOTE = "allVote";

	/**
	 * 搜索的类型
	 */
	public static final String SEARCH_TYPE_HOSPITAL = "hospital";
	public static final String SEARCH_TYPE_DEPT = "dept";
	public static final String SEARCH_TYPE_DOCTOR = "doctor";

	/*** 是否显示 1显示 0不显示 **/
	public static final int IS_VIEW_YES = 1;
	public static final int IS_VIEW_NO = 0;

	/** 是否生效 ***************/
	public static final int IS_VALID_YES = 1;
	public static final int IS_VALID_NO = 0;

	/**
	 * 门诊缴费功能code
	 */
	public static final String OPTION_CLINIC_PAY = "clinicPay";

	/**
	 * 当班挂号功能code
	 */
	public static final String OPTION_DUTY_REGISTER = "dutyRegister";

	/**
	 * 预约挂号功能code
	 */
	public static final String OPTION_RESERVATION_REGISTER = "reservationRegister";

	/**
	 * 智能候诊功能code
	 */
	public static final String OPTION_SMART_WAIT = "smartWait";

	/**
	 * 报告查询功能code
	 */
	public static final String OPTION_REPORT_QUERY = "reportQuery";

	/**
	 * 在线建档功能code
	 */
	public static final String OPTION_ONLINE_FILING = "onlineFiling";
	/**
	 * 云医院功能code
	 */
	public static final String OPTION_CLOUD = "cloud";
	/**
	 * 就医指南功能code
	 */
	public static final String OPTION_GUIDE = "guide";
	/**
	 * 交通指南功能code
	 */
	public static final String OPTION_TRAFFIC = "traffic";

	/**
	 * 是否能走医保流程 1：是   0：否
	 */
	public static final int BASEDON_MEDICAL_INSURANCE_YES = 1;
	public static final int BASEDON_MEDICAL_INSURANCE_NO = 0;

	/**
	 * 未支付情况下能查询到医院订单
	 */
	public static final int QUERY_UNPAID_RECORD_YES = 1;
	/**
	 * 未支付情况下不能查询到医院订单
	 */
	public static final int QUERY_UNPAID_RECORD_NO = 2;
}
