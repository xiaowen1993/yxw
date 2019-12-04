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
package com.yxw.commons.constants.cache;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Package: com.yxw.platform.cache
 * @ClassName: CacheConstant
 * @Statement: <p>
 *             缓存的常量定义
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-11
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class CacheConstant {
	public static Logger logger = LoggerFactory.getLogger("Cache");
	/**
	 * 日期格式化
	 */
	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 医院缓存key前缀
	 */
	public static final String CACHE_HOSPITAL_HASH_KEY_PREFIX = "hospital.hash";

	/**
	 * 分院的前缀
	 */
	public static final String CACHE_BRANCH_HOSPITAL_HASH_KEY_PREFIX = "branch.hospital.hash";

	/**
	 * 医院code与接口id的对应关系缓存前缀
	 */
	public static final String CACHE_CODE_INTERFACE_KEY_PREFIX = "hospital.code.interface";

	/**
	 * 医院Code与对接平台与的对应关系缓存前缀
	 */
	public static final String CACHE_APP_CODE_KEY_PREFIX = "hospital.app.interface";

	/**
	 * 医院与对应平台关系缓存前缀 appCode-hospitalId, 存在一个医院对应多个平台的问题。
	 */
	public static final String CACHE_HOSPITAL_PLATFORM_KEY_PREFIX = "hospital.app.platform";

	/**
	 * 我们的app可以登录的平台
	 */
	public static final String CACHE_PLATFORM_KEY_PREFIX = "app.platform";

	/**
	 * 平台与支付方式
	 */
	public static final String CACHE_PLATFORM_PAY_MODE_KEY_PREFIX = "app.platform.payMode";

	/**
	 * 平台与消息推送
	 */
	public static final String CACHE_PLATFORM_MSG_MODE_KEY_PREFIX = "app.platform.msgMode";

	/**
	 * 医院Code与对接平台token SECRET对应关系缓存前缀
	 */
	public static final String CACHE_APP_SECRET_KEY_PREFIX = "hospital.app.secret";

	/**
	 * 医院规则缓存前缀
	 */
	public static final String CACHE_HOSPITAL_RULES_KEY_PREFIX = "hospital.rules.hash";

	/**
	 * 科室医生缓存key前缀
	 */
	public static final String CAHCE_DOCTOR_KEY_PREFIX = "hospital.doctor:";

	/**
	 * 系统缓存管理 医院的科室信息缓存管理的key
	 */
	public static final String SYSTEM_MANAGER_CACHE_DEPT_HASH_PREFIX = "hospital.dept";

	/**
	 * 掌上医院一级科室列表缓存key前缀
	 */
	public static final String CACHE_LEVELONE_DEPT_HASH_PREFIX = "hospital.dept.levelOne.hash";

	/**
	 * 掌上医院医院二级科室列表缓存key前缀
	 */
	public static final String CACHE_LEVELTWO_DEPT_HASH_PREFIX = "hospital.dept.levelTwo.hash";

	/**
	 * 健康易 一级科室列表缓存key前缀
	 */
	public static final String CACHE_APP_LEVELONE_DEPT_HASH_PREFIX = "app.hospital.dept.levelOne.hash";

	/**
	 * 健康易 二级科室列表缓存key前缀
	 */
	public static final String CACHE_APP_LEVELTWO_DEPT_HASH_PREFIX = "app.hospital.dept.levelTwo.hash";

	/**
	 * 健康易,所有医院部门科室列表缓存key
	 */
	//	public static final String CACHE_EASYHEALTH_DEPT_NAME_HASH_PREFIX = "easyHealth.dept.name.hash";

	/**
	 * APP,所有医院部门科室列表缓存key
	 */
	public static final String CACHE_APP_DEPT_NAME_HASH_PREFIX = "app.dept.name.hash:";

	/**
	 * 根据医生姓名缓存key前缀
	 */
	public static final String CACHE_APP_DOCTOR_NAME_HASH_PREFIX = "app.doctor.name.hash:";

	public static final String CACHE_HOSPITAL_DOCTOR_NAME_HASH_PREFIX = "hospital.doctor.name.hash:";

	/**
	 * 医生hash缓存key前缀
	 */
	public static final String CACHE_DOCTOR_HASH_PREFIX = "hospital.doctor.hash:";

	/**
	 * 建康易预约(160)挂号医生hash缓存key前缀
	 */
	public static final String CACHE_EASYHEALTH_APPOINTMENT_DOCTOR_HASH_PREFIX = "easyHealth.appointment.doctor.hash";

	/**
	 * 挂号记录缓存key前缀
	 */
	public static final String CACHE_REG_RECORD_INFO_PREFIX = "reg:";

	/**
	 * 门诊记录缓存Key前缀
	 */
	public static final String CACHE_CLINIC_RECORD_INFO_PREFIX = "clinic:";

	/**
	 * 押金补缴记录缓存Key前缀
	 */
	public static final String CACHE_DEPOSIT_RECORD_INFO_PREFIX = "deposit:";

	/**
	 * 支付缓存key前缀
	 */
	public static final String CACHE_PAY_INFO_HASH_PREFIX = "pay:";

	/**
	 * 退费缓存key前缀
	 */
	public static final String CACHE_REFUND_INFO_HASH_PREFIX = "refund:";

	/**
	 * 医疗卡缓存key前缀
	 */
	public static final String CACHE_MEDICAL_CARD_HASH_PREFIX = "medicalCard:";

	/**
	 * 曾挂号医生缓存固定前缀
	 */
	public static final String CACHE_HAD_REG_DOCTOR = "hadRegDoctor:";

	/**
	 * 缓存key的分割符
	 */
	public static final String CACHE_KEY_SPLIT_CHAR = ":";

	/**
	 * key模糊匹配通配符
	 */
	public static final String CACHE_KEY_PATTERN_CHAR = "*";

	/**
	 * 缓存新增
	 */
	public static final String CACHE_OP_ADD = "cache_add";

	/**
	 * 缓存删除
	 */
	public static final String CACHE_OP_DEL = "cache_del";

	/**
	 * 缓存更新
	 */
	public static final String CACHE_OP_UPDATE = "cache_update";

	/**
	 * 标记为null的值
	 */
	public static final String CACHE_NULL_STRING = "null";

	/**
	 * 预约中
	 */
	public static final String CACHE_REGISTER_HAVING_HASH = "register.having.hash:";

	/**
	 * 已预约未支付
	 */
	public static final String CACHE_REGISTER_NOT_PAY_HAD_HASH = "register.notpay.had.hash:";

	/**
	 * 挂号记录: 解锁号源
	 */
	public static final String CACHE_REGISTER_NEED_UNLOCK = "register.need.unlock:";

	/**
	 * 挂号异常 有序集合缓存key 前缀
	 */
	public static final String CACHE_REGISTER_EXCEPTION_SORTEDSET = "register.exception.sortedset";

	/**
	 * 挂号异常 队列缓存key 前缀
	 */
	//	public static final String CACHE_REGISTER_EXCEPTION = "register.exception.list:";

	/**
	 * 第三方退费异常 队列缓存key 前缀
	 */
	public static final String CACHE_AGT_REFUND_EXCEPTION = "agt.refund.exception.list:";

	/**
	 * 门诊异常 队列缓存key 前缀
	 */
	//	public static final String CACHE_CLINIC_EXCEPTION = "clinic.exception.list:";
	public static final String CACHE_CLINIC_EXCEPTION_SORTEDSET = "clinic.exception.sortedset";

	/**
	 * 押金补缴异常队列缓存Key 前缀
	 */
	public static final String CACHE_DEPOSIT_EXCEPTION = "deposit.exception.list";

	/**
	 * 停诊异常 队列缓存key 前缀
	 */
	public static final String CACHE_STOP_REGISTER_EXCEPTION = "stop.register.exception.hash";

	/**
	 * 停诊阻止订单缓存
	 */
	public static final String CACHE_STOP_REGISTER_PREVENT = "stop.register.prevent.hash";

	/**
	 * 平台/支付/挂号来源/取消来源/退费渠道 类型:微信
	 */
	public static final String CACHE_PAY_TYPE_WEIXIN = "wechat";

	/**
	 * 平台/支付/挂号来源/取消来源/退费渠道 类型:支付宝
	 */
	public static final String CACHE_PAY_TYPE_ALIPAY = "alipay";

	/**
	 * 平台/支付/挂号来源/取消来源/退费渠道 类型:app
	 */
	public static final String CACHE_PAY_TYPE_APP = "app";

	/**
	 * redis 缓存key不存在返回的标志
	 */
	public static final String CACHE_KEY_NOT_EXIST = "nil";

	/**
	 * 科室为一级科室的标志 parentCode ： 0
	 */
	public static final String CACHE_PARENT_DEPT_CODE = "0";

	/**
	 * 预约号源信息
	 */
	public static final String CACHE_REGISTER_REG_SOURCE = "register.reg.source.hash:";

	/**
	 * 缓存号源 cacheKey
	 */
	public static final String CACHE_REGISTER_SOURCE = "register.source.hash:";

	/**
	 * 号源
	 */
	public static final String REG_SOURCE = "regSource";

	/**
	 * 医生预约分时号源信息
	 */
	public static final String CACHE_REGISTER_REG_TIME_SOURCE = "register.reg.time.source.hash:";

	public static final String SPLIT_CHAR = "_";

	public static final String UPDATE_OP_TYPE_ADD = "add";

	public static final String UPDATE_OP_TYPE_UPDATE = "update";

	public static final String UPDATE_OP_TYPE_DEL = "delete";

	/**
	 * 
	 */
	public static final String APPOINTMENT = "appointment";
	/**
	 * 模板消息:缓存hash结构的cachekey 前缀
	 */
	public static final String CACHE_MSG_TEMPLATE = "msg.template.hash";
	/**
	 * 客服消息:缓存hash结构的cachekey 前缀
	 */
	public static final String CACHE_MSG_CUSTOMER = "msg.customer.hash";
	/**
	 * 模板库:缓存hash结构的cachekey 前缀
	 */
	public static final String CACHE_MSG_LIBRARY = "msg.library.hash";

	/**
	 * 白名单:缓存hash结构的前缀
	 */
	public static final String CACHE_WHITE_LIST = "white.list.hash";

	/**
	 * 一天共计的秒数
	 */
	public static final Integer ONE_DAY_SECONDS = 86400;

	/**
	 * 权限系统:角色资源 hash结构的前缀
	 */
	public static final String CACHE_ROLE_RESOURCE = "role.resource.hash";

	/**
	 * 权限系统:用户角色 hash结构的前缀
	 */
	public static final String CACHE_USER_ROLE = "user.role.hash";

	/**
	 * 7天共计的秒数
	 */
	public static final int SEVEN_DAY_SECONDS = 604800;

	/**
	 * 默认的页面底部logo 信息缓存
	 */
	public static final String DEF_FOOT_LOGO_INFO = "医享网出品";

	/**
	 * 所有的缓存类型
	 */
	public static final Map<String, String> cacheTypeMap = new LinkedHashMap<String, String>();
	static {
		cacheTypeMap.put(CACHE_APP_CODE_KEY_PREFIX, "支付信息");
		//cacheTypeMap.put(CACHE_APP_CODE_KEY_PREFIX, "平台配置信息");
		//cacheTypeMap.put(CACHE_HOSPITAL_RULES_KEY_PREFIX, "医院规则配置");
		cacheTypeMap.put(SYSTEM_MANAGER_CACHE_DEPT_HASH_PREFIX, "科室信息");
		cacheTypeMap.put(CACHE_DOCTOR_HASH_PREFIX, "医生信息");
		cacheTypeMap.put(CACHE_REGISTER_REG_SOURCE, "号源信息");
		//cacheTypeMap.put(CACHE_MEDICAL_CARD_HASH_PREFIX, "就诊卡");
		//cacheTypeMap.put(CACHE_REGISTER_HAVING_HASH, "预约中订单");
		//cacheTypeMap.put(CACHE_REGISTER_NOT_PAY_HAD_HASH, "已预约未支付订单");

		//cacheTypeMap.put(CACHE_REGISTER_NEED_UNLOCK, "待解锁号源");
		//cacheTypeMap.put(CACHE_REGISTER_EXCEPTION, "挂号异常队列");

		//cacheTypeMap.put(CACHE_CLINIC_EXCEPTION, "门诊异常队列");
		//cacheTypeMap.put(CACHE_STOP_REGISTER_EXCEPTION, "停诊异常队列");

	}

	/**
	 * 短信验证码 hash结构的前缀
	 */
	public static final String CACHE_SMS_CODE = "sms.code.hash";
	/**
	 * 微信公众号token缓存前缀
	 */
	public static final String CACHE_HOSPITAL_WECHAT_TOKEN_KEY_PREFIX = "hospital.wechat.token.hash";
	public static final String CACHE_WECHAT_COMPONENT_VERIFY_TICKET_KEY_PREFIX = "wechat.component.verify.ticket.hash";

	/**
	 * 医院功能关系hash结果前缀
	 */
	public static final String CACHE_AREA_OPTION_KEY_PREFIX = "area.option.hash";

	public static final String CACHE_HOSPITAL_BASEINFO = "hospital.baseInfo";

	/**
	 * 城市缓存
	 */
	public static final String CACHE_AREA_HASH = "area.hash";

	/**
	 * 已选城市缓存
	 */
	public static final String CACHE_AREA_SELECTED = "area.selected.hash";

	/**
	 * 首页功能模块缓存
	 */
	public static final String CACHE_APP_OPTIONAL = "app.optional.hash";

	/**
	 * 运营数据缓存
	 */
	public static final String CACHE_APP_CARRIEROPERATOR = "app.carrieroperator.hash";

	/**
	 * Android APP版本缓存
	 */
	public static final String CACHE_ANDROID_APP_VERSION = "android.app.version.hash";

	/**
	 * IOS APP版本缓存
	 */
	public static final String CACHE_IOS_APP_VERSION = "ios.app.version.hash";

	/**
	 * APP颜色管理
	 */
	public static final String CACHE_APP_COLOR_HASH = "app.color.hash";

	/**
	 * 挂号支付回调redis加锁前缀 
	 */
	public static final String REG_PAY_CALLBACK_LOCK_STR = "reg.pay.callback.lock:";
	/**
	 * 门诊支付回调redis加锁前缀 
	 */
	public static final String CLINIC_PAY_CALLBACK_LOCK_STR = "clinic.pay.callback.lock:";
	/**
	 * 停诊重复调用
	 */
	public static final String REPEAT_STOP_REG_LOCK_STR = "repeat.stop.reg.lock:";
}
