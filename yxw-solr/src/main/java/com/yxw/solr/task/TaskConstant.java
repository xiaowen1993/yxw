package com.yxw.solr.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yxw.solr.constants.Cores;

public class TaskConstant {
	/**
	 * 绑卡: 业务core-统计core
	 */
	public static final Map<String, String> cardStatsMap = new ConcurrentHashMap<String, String>();
	
	/**
	 * 挂号: 业务core-统计core
	 */
	public static final Map<String, String> registerStatsMap = new ConcurrentHashMap<String, String>();
	
	/**
	 * 门诊: 业务core-统计core
	 */
	public static final Map<String, String> clinicStatsMap = new ConcurrentHashMap<String, String>();
	
	/**
	 * 住院: 业务core-统计core
	 */
	public static final Map<String, String> depositStatsMap = new ConcurrentHashMap<String, String>();
	
	static {
		// 绑卡数据初始化
		cardStatsMap.put(Cores.CORE_EH_CARD, Cores.CORE_STATS_CARD);
		
		// 挂号数据初始化
		
		// 门诊数据初始化
		
		// 住院数据初始化
	}
}
