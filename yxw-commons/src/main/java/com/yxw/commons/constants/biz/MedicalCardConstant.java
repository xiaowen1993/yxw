package com.yxw.commons.constants.biz;

/**
 * 
 * @Package: com.yxw.mobileapp.biz.medicalcard
 * @ClassName: MedicalCardConstant
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-6-3
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MedicalCardConstant {

	/**
	 * Platform类型 微信、支付宝、健康易
	 */
	/*	public static final int CARD_PLATFORM_WECHAT = 1;
		public static final int CARD_PLATFORM_ALIPAY = 2;
		public static final int CARD_PLATFORM_EASYHEALTH = 3;
		public static final int CARD_PLATFORM_APP = 4;
		public static final int CARD_PLATFORM_APP_WECHAT = 5;
		public static final int CARD_PLATFORM_APP_ALIPAY = 6;*/

	/**
	 * 绑卡方式
	 */
	public static final int BIND_TYPE_BIND = 1; // 正常绑卡
	public static final int BIND_TYPE_CREATE = 2; // 建档后自动绑卡
	public static final int BIND_TYPE_MIGRATION = 3; // 从项目中迁移过来的卡
	public static final int BIND_TYPE_AUTO = 4; // 自动绑卡（健康易自动绑卡）

	/**
	 * 绑卡类型(0全部，1本人，2他人)
	 */
	public static final String BIND_CARD_TYPE = "bindCardType";

	/**
	 * 查询卡类型：查询全部
	 */
	public static final int MEDICALCARD_ALL = 0;

	/**
	 * 查询卡类型：查询本人
	 */
	public static final int MEDICALCARD_SELF = 1;

	/**
	 * 查询卡类型：查询他人
	 */
	public static final int MEDICALCARD_OTHERS = 2;

	/**
	 * 绑卡：解绑状态
	 */
	public static final int MEDICALCARD_UNBIND = 0;

	/**
	 * 绑卡：绑定状态
	 */
	public static final int MEDICALCARD_BOUND = 1;

	/**
	 * 是否医保账户：非医保
	 */
	public static final int MEDICALCARD_NOT_MEDICARE = 0;

	/**
	 * 是否医保账户：医保
	 */
	public static final int MEDICALCARD_IS_MEDICARD = 1;

	/**
	 * 支付宝的用户信息
	 */
	public static final String ALIPAY_USER_INFO = "alipay_userinfo";

	/**
	 * 微信的用户信息
	 */
	public static final String WECHAT_USER_INFO = "wechat_userinfo";

	/**
	 * 绑卡成功提示语
	 */
	public static final String BIND_SUCCESS = "绑卡成功";

	/**
	 * 绑卡失败提示语
	 */
	public static final String BIND_FAIL = "绑卡失败";

	/**
	 * 就诊卡列表
	 */
	public static final String MEDICALCARD_LIST = "cardList";

	/**
	 * 最大绑卡 数
	 */
	public static final String MAX_CARD_COUNT = "maxCardCount";

	/**
	 * 是否能绑卡
	 */
	public static final String CAN_BIND_CARD = "canBindCard";

	/**
	 * 是否能绑卡：可以绑卡
	 */
	public static final int WHETHER_CAN_BIND_CARD_YES = 1;

	/**
	 * 是否能绑卡：不能绑卡
	 */
	public static final int WHETHER_CAN_BIND_CARD_NO = 0;

	/**
	 * 查询本人的卡键值
	 */
	public static final String SELF_CARDS = "selfCards";

	/**
	 * 查看非本人的卡键值
	 */
	public static final String OTHER_CARDS = "otherCards";

	/**
	 * 绑卡类型：本人
	 */
	public static final int OWNER_TYPE_SELF = 1;

	/**
	 * 绑卡类型：他人
	 */
	public static final int OWNER_TYPE_OTHER = 2;

	/**
	 * 绑卡类型：儿童
	 */
	public static final int OWNER_TYPE_CHILD = 3;

	/**
	 * 完善类型
	 */
	public static final String COMPLETE_TYPE = "completeType";

	/**
	 * 完善信息：就诊卡
	 */
	public static final int COMPLETE_TYPE_CARD_NO = 1;

	/**
	 * 完善信息：住院号
	 */
	public static final int COMPLETE_TYPE_ADMISSION_NO = 2;
}
