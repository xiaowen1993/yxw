package com.yxw.solr.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BizConstant {
	/**
	 * 总订单
	 */
	public final static int BIZ_TYPE_ORDER = 0;
	public final static String BIZ_TYPE_ORDER_TITLE = "订单";
	
	
	/**
	 * 挂号
	 */
	public final static int BIZ_TYPE_REGISTER = 1;
	public final static String BIZ_TYPE_REGISTER_TITLE = "挂号订单";
	
	/**
	 * 门诊
	 */
	public final static int BIZ_TYPE_CLINIC = 2;
	public final static String BIZ_TYPE_CLINIC_TITLE = "门诊订单";
	public final static int BIZ_TYPE_CLINIC_PART_REFUND = -2;
	public final static String BIZ_TYPE_CLINIC_PART_REFUND_TITLE = "门诊订单";
	
	/**
	 * 押金补交
	 */
	public final static int BIZ_TYPE_DEPOSIT = 3;
	public final static String BIZ_TYPE_DEPOSIT_TITLE = "押金补缴订单";
	public final static int BIZ_TYPE_DEPOSIT_PART_REFUND = -3;
	public final static String BIZ_TYPE_DEPOSIT_PART_REFUND_TITLE = "押金补缴订单";
	
	public static Map<Integer, Map<Integer, String>> bizOrderMap = new ConcurrentHashMap<>();
	
	static {
		bizOrderMap.put(BIZ_TYPE_REGISTER, PlatformConstant.platformRegisterMap);
		bizOrderMap.put(BIZ_TYPE_CLINIC, PlatformConstant.platformClinicMap);
		bizOrderMap.put(BIZ_TYPE_CLINIC_PART_REFUND, PlatformConstant.platformClinicPartRefundMap);
		bizOrderMap.put(BIZ_TYPE_DEPOSIT, PlatformConstant.platformDepositMap);
		bizOrderMap.put(BIZ_TYPE_DEPOSIT_PART_REFUND, PlatformConstant.platformDepositPartRefundMap);
	}
	
	/**
	 * 就诊卡
	 */
	public final static int BIZ_TYPE_CARD = 10;
	public final static String BIZ_TYPE_CARD_TITLE = "绑卡数据";
	
	/**
	 * 挂号科室
	 */
	public final static int BIZ_TYPE_DEPT = 11;
	public final static String BIZ_TYPE_DEPT_TITLE = "挂号科室";
}
