package com.yxw.commons.constants.biz;

public class FamilyConstants {
	public static final String FAMILY_KEY = "family";
	public static final String MAX_FAMILY_NUM_KEY = "app_family_numbers";
	/**
	 *  用户类型 1、成人，2、小孩
	 */
	public static final int USER_TYPE_ADULT = 1;
	public static final int USER_TYPE_CHILD = 2;
	
	/**
	 * 关系类型
	 */
	public static final int FAMILY_OWNERSHIP_SELF = 1;			// 本人
	public static final int FAMILY_OWNERSHIP_OTHERS = 2;		// 他人
	public static final int FAMILY_OWNERSHIP_CHILD = 3;			// 子女
	public static final int FAMILY_OWNERSHIP_PARENT = 4;		// 父母
	public static final int FAMILY_OWNERSHIP_SIBLING = 5;		// 兄弟姐妹
	public static final int FAMILY_OWNERSHIP_PARTNER = 6;		// 伴侣
	
	/**
	 * 是否启用
	 */
	public static final int FAMILY_STATE_ENABLE = 1;
	public static final int FAMILY_STATE_DISABLE = 0;
	public static final int FAMILY_STATE_ALL = -1;		// 不区分是否启用
}
