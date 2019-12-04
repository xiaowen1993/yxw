/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.constants.biz;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.yxw.platform.hospital
 * @ClassName: HospitalConstant
 * @Statement: <p>
 *             医院常量定义
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-24
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RuleConstant {
	/**
	 * 分院使用相同的规则
	 */
	public static final String USE_SAME_RULES = "1";

	/**
	 * 分院使用不同的规则
	 */
	public static final String USE_DIFFERENT_RULES = "0";

	/**
	 * 编辑规则
	 */
	public static final String RULE_TYPE_EDIT = "ruleEdit";

	/**
	 * 个人中心规则
	 */
	public static final String RULE_TYPE_USERCENTER = "ruleUserCenter";

	/**
	 * 在线建档
	 */
	public static final String RULE_TYPE_ONLINEFILING = "ruleOnlineFiling";

	/**
	 * 绑卡
	 */
	public static final String RULE_TYPE_TIEDCARD = "ruleTiedCard";

	/**
	 * 挂号
	 */
	public static final String RULE_TYPE_REGISTER = "ruleRegister";

	/**
	 * 支付方式
	 */
	public static final String RULE_TYPE_PAYMENT = "rulePayment";

	/**
	 * 门诊缴费
	 */
	public static final String RULE_TYPE_CLINIC = "ruleClinic";

	/**
	 * 查询
	 */
	public static final String RULE_TYPE_QUERY = "ruleQuery";

	/**
	 * 配送代煎
	 */
	public static final String RULE_TYPE_FRIEDEXPRESS = "ruleFriedExpress";

	public static final String RULE_TYPE_IN_HOSPITAL = "ruleInHospital";

	/**
	 * 推送
	 */
	public static final String RULE_TYPE_PUSH = "rulePush";

	/**
	 * his基础数据
	 */
	public static final String RULE_TYPE_HIS_DATA = "ruleHisData";

	/**
	 * 有分院
	 */
	public static final int IS_HAS_BRANCH_YES = 1;

	/**
	 * 无分院
	 */
	public static final int IS_HAS_BRANCH_NO = 0;

	/**
	 * 是否兼容医院不修改接口 : 不兼容（默认）
	 */
	public static final int IS_COMPATIBLE_OTHER_PLATFORM_NO = 0;

	/**
	 * 是否兼容医院不修改接口 : 兼容
	 */
	public static final int IS_COMPATIBLE_OTHER_PLATFORM_YES = 1;

	/**
	 * 有2级科室
	 */
	public static final int IS_HAS_TWO_DEPT_YES = 1;

	/**
	 * 没有2级科室
	 */
	public static final int IS_HAS_TWO_DEPT_NO = 0;

	/**
	 * 
	 */
	public static final int IS_APPOINT_PAY_SUC_YES = 1;
	/**
	 * 
	 */
	public static final int IS_APPOINT_PAY_SUC_NO = 0;

	/**
	 * 0：医院his主动推送
	 */
	public static final int IS_ACCEPT_STOP_INFO_TYPE_YES = 0;

	/**
	 * 1：标准平台轮询查询
	 */
	public static final int IS_ACCEPT_STOP_INFO_TYPE_NO = 1;

	/** 挂号规则-医院是否有此接口-有 */
	public static final int IS_HAD_INTERFACE_YES = 1;
	/** 挂号规则-医院是否有此接口-无 */
	public static final int IS_HAD_INTERFACE_NO = 0;

	/** 挂号规则-当班与预约挂号的科室数据是否相同-相同 */
	public static final int IS_SAME_DEPT_DATA_YES = 1;
	/** 挂号规则-当班与预约挂号的科室数据是否相同-不相同 */
	public static final int IS_SAME_DEPT_DATA_NO = 0;

	/** 挂号规则-当班与预约挂号的医生数据是否相同-相同 */
	public static final int IS_SAME_DOCTOR_DATA_YES = 1;
	/** 挂号规则-当班与预约挂号的医生数据是否相同-不相同 */
	public static final int IS_SAME_DOCTOR_DATA_NO = 0;

	/*** 挂号规则-是否选择挂号人群类型-是 ****/
	public static final int IS_VIEW_POPULATION_TYPE_YES = 1;
	/** 挂号规则-是否选择挂号人群类型-否 */
	public static final int IS_VIEW_POPULATION_TYPE_NO = 0;

	/*** 挂号规则-是否选择预约类型-是 ***/
	public static final int IS_VIEW_APPOINTMENT_TYPE_YES = 1;
	/** 挂号规则-是否选择预约类型-否 */
	public static final int IS_VIEW_APPOINTMENT_TYPE_NO = 0;

	/*** 人群类型定义 , 预约类型 定义 ****/
	public static final Map<String, String> populationTypeMap = new HashMap<String, String>();
	public static final Map<String, String> appointmentTypeMap = new HashMap<String, String>();
	static {
		populationTypeMap.put("1", "本地");
		populationTypeMap.put("2", "外地");
		appointmentTypeMap.put("1", "一般");
		appointmentTypeMap.put("2", "出院后复查");
		appointmentTypeMap.put("3", "社区转诊");
		appointmentTypeMap.put("4", "术后复查");
		appointmentTypeMap.put("5", "产前检查");
		appointmentTypeMap.put("6", "慢病预约");
	}

	/**
	 * 1：为本人挂号 2：为子女挂号 3：为他人挂号
	 */
	public static final String DEFAULT_REG_USE_TYPE = "1";

	/**
	 * 不开放-0
	 */
	public static final int IS_OPEN_NO = 0;
	/**
	 * 开放-1
	 */
	public static final int IS_OPEN_YES = 1;

}
