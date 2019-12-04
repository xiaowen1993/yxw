package com.yxw.solr.constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PlatformConstant {
	
	public final static String PLATFORM_ALL_CODE = "-1";
	public final static int PLATFORM_ALL_VAL = -1;

	/**
	 * 标准平台-微信
	 */
	public final static String PLATFORM_STANDARD_WECHAT_CODE = "1";
	public final static int PLATFORM_STANDARD_WECHAT_VAL = 1;
	public final static String PLATFORM_STANDARD_WECHAT_SHORT_NAME = "wechat";
	
	/**
	 * 标准平台-支付宝
	 */
	public final static String PLATFORM_STANDARD_ALIPAY_CODE = "2";
	public final static int PLATFORM_STANDARD_ALIPAY_VAL = 2;
	public final static String PLATFORM_STANDARD_ALIPAY_SHORT_NAME = "alipay";
	
	/**
	 * 健康易
	 */
	public final static String PLATFORM_EH_CODE = "3";
	public final static int PLATFORM_EH_VAL = 3;
	public final static String PLATFORM_EH_SHORT_NAME = "eh";
	
	/**
	 * 新平台
	 */
	public final static String PLATFORM_APP_CODE = "4";
	public final static int PLATFORM_APP_VAL = 4;
	public final static String PLATFORM_APP_SHORT_NAME = "app";
	
	/**
	 * 新平台 -- 微信
	 */
	public final static String PLATFORM_APP_WECHAT_CODE = "5";
	public final static int PLATFORM_APP_WECHAT_VAL = 5;
	public final static String PLATFORM_APP_WECHAT_SHORT_NAME = "appWechat";
	
	/**
	 * 新平台 -- 支付宝
	 */
	public final static String PLATFORM_APP_ALIPAY_CODE = "6";
	public final static int PLATFORM_APP_ALIPAY_VAL = 6;
	public final static String PLATFORM_APP_ALIPAY_SHORT_NAME = "appAlipay";
	
	/**
	 * 医院项目-微信
	 */
	public final static String PLATFORM_HIS_WECHAT_CODE = "7";
	public final static int PLATFORM_HIS_WECHAT_VAL = 7;
	public final static String PLATFORM_HIS_WECHAT_SHORT_NAME = "hisWechat";
	
	/**
	 * 医院项目-支付宝
	 */
	public final static String PLATFORM_HIS_ALIPAY_CODE = "8";
	public final static int PLATFORM_HIS_ALIPAY_VAL = 8;
	public final static String PLATFORM_HIS_ALIPAY_SHORT_NAME = "hisAlipay";
	
	public static Map<Integer, String> platformCardMap = new ConcurrentHashMap<Integer, String>();
	public static Map<Integer, String> platformClinicMap = new ConcurrentHashMap<Integer, String>();
	public static Map<Integer, String> platformClinicPartRefundMap = new ConcurrentHashMap<Integer, String>();
	public static Map<Integer, String> platformRegisterMap = new ConcurrentHashMap<Integer, String>();
	public static Map<Integer, String> platformDepositMap = new ConcurrentHashMap<Integer, String>();
	public static Map<Integer, String> platformDepositPartRefundMap = new ConcurrentHashMap<Integer, String>();
	
	public static List<String> cardList = new ArrayList<>();
	public static List<String> clinicList = new ArrayList<>();
	public static List<String> depositList = new ArrayList<>();
	public static List<String> registerList = new ArrayList<>();
	
	static {
		/**
		 * 标准平台微信和支付宝都是放在同一个core中，另外医院项目微信、支付宝暂时没有加进来，预计也会放在一个core
		 */
		
		// 绑卡信息
		platformCardMap.put(PLATFORM_EH_VAL, Cores.CORE_EH_CARD);
		platformCardMap.put(PLATFORM_STANDARD_WECHAT_VAL, Cores.CORE_STD_CARD);
		platformCardMap.put(PLATFORM_STANDARD_ALIPAY_VAL, Cores.CORE_STD_CARD);
		
		cardList.add(Cores.CORE_EH_CARD);
		cardList.add(Cores.CORE_STD_CARD);
		
		// 门诊缴费信息
		platformClinicMap.put(PLATFORM_EH_VAL, Cores.CORE_EH_CLINIC);
		platformClinicMap.put(PLATFORM_STANDARD_WECHAT_VAL, Cores.CORE_STD_CLINIC);
		platformClinicMap.put(PLATFORM_STANDARD_ALIPAY_VAL, Cores.CORE_STD_CLINIC);
		
		// 门诊部分退费
		platformClinicPartRefundMap.put(PLATFORM_EH_VAL, Cores.CORE_EH_CLINIC_PART_REFUND);
		platformClinicPartRefundMap.put(PLATFORM_STANDARD_WECHAT_VAL, Cores.CORE_STD_CLINIC_PART_REFUND);
		platformClinicPartRefundMap.put(PLATFORM_STANDARD_ALIPAY_VAL, Cores.CORE_STD_CLINIC_PART_REFUND);
		
		// 挂号信息
		platformRegisterMap.put(PLATFORM_EH_VAL, Cores.CORE_EH_REGISTER);
		platformRegisterMap.put(PLATFORM_STANDARD_WECHAT_VAL, Cores.CORE_STD_REGISTER);
		platformRegisterMap.put(PLATFORM_STANDARD_ALIPAY_VAL, Cores.CORE_STD_REGISTER);
		
		registerList.add(Cores.CORE_EH_REGISTER);
		registerList.add(Cores.CORE_STD_REGISTER);
		
		// 押金补交信息
		platformDepositMap.put(PLATFORM_STANDARD_ALIPAY_VAL, Cores.CORE_STD_DEPOSIT);
		platformDepositMap.put(PLATFORM_STANDARD_WECHAT_VAL, Cores.CORE_STD_DEPOSIT);
		
		// 押金部分退费
		platformDepositPartRefundMap.put(PLATFORM_STANDARD_ALIPAY_VAL, Cores.CORE_STD_DEPOSIT_PART_REFUND);
		platformDepositPartRefundMap.put(PLATFORM_STANDARD_WECHAT_VAL, Cores.CORE_STD_DEPOSIT_PART_REFUND);
	}
}
