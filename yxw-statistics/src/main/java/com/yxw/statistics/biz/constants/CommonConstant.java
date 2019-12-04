package com.yxw.statistics.biz.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommonConstant {
	
	public final static int PAGE_SIZE = 20;
	/**
	 * 统计
	 */
	public final static int TYPE_STATS_WHOLE = 1;
	public final static int TYPE_STATS_CARDS = 2;
	public final static int TYPE_STATS_ORDERS = 3;
	
	/**
	 * 地区级别
	 */
	public final static int LEVEL_PROVINCE = 1;
	public final static int LEVEL_CITY = 2;
	public final static int LEVEL_COUNTY = 3;
	
	/**
	 * 管理
	 */
	public final static int TYPE_MANAGER_ORDERS = 11;
	public final static int TYPE_MANAGER_CARDS = 12;
	public final static int TYPE_MANAGER_REGISTER = 13;
	
	public final static Map<Integer, String> bizTypes = new ConcurrentHashMap<Integer, String>();
	static {
		bizTypes.put(TYPE_STATS_WHOLE, "整体统计");
		bizTypes.put(TYPE_STATS_CARDS, "绑卡统计");
		bizTypes.put(TYPE_STATS_ORDERS, "订单统计");
		bizTypes.put(TYPE_MANAGER_ORDERS, "订单管理");
		bizTypes.put(TYPE_MANAGER_CARDS, "绑卡管理");
		bizTypes.put(TYPE_MANAGER_REGISTER, "挂号管理");
	}
	
	/**
	 * vo的Key
	 */
	public final static String COMMON_VO_KEY = "commonVo";
	
	/**
	 * 实体的Key
	 */
	public final static String COMMON_ENTITY_KEY = "commonEntity";
}
