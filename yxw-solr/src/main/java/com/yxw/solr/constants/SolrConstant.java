package com.yxw.solr.constants;

import java.util.HashMap;
import java.util.Map;

public class SolrConstant {
	/**
	 * 默认查询的数据数目 （按文档：20）
	 */
	public final static int DEFAULT_RECORD_COUNT = 20;
	
	/**
	 * 查询所有数据
	 */
	public final static String QUERY_ALL = "*:*";
	
	/**
	 * and操作
	 */
	public final static String AND = " AND ";
	
	/**
	 * or操作
	 */
	public final static String OR = " OR ";
	
	/**
	 * 区间
	 */
	public final static String TO = " TO ";
	
	/**
	 * 匹配所有
	 */
	public final static String ALL_VALUE = "*";
	
	/**
	 * 非
	 */
	public final static String NOT = "-";
	
	/**
	 * 匹配一个
	 */
	public final static String ONE_VALUE = "?";
	
	/**
	 * 冒号
	 */
	public final static String COLON = ":";
	
	/**
	 * 无数据的时候返回
	 */
	public final static String NO_DATA = "[]";
	
	/**
	 * 区间开始
	 */
	public final static String INTERVAL_START = "[";
	
	/**
	 * 区间结束
	 */
	public final static String INTERVAL_END = "]";
	
	/**
	 * facet排序: 按分组字段值升序
	 */
	public final static String SORT_INDEX = "index";
	
	/**
	 * facet排序：按分组命中率升序
	 */
	public final static String SORT_COUNT = "count";
	
	/**
	 * 平台类型
	 */
	public final static Map<String, Integer> platforms = new HashMap<String, Integer>();
	
	static {
		// 初始化平台类型
		platforms.put("全部", 0);
		platforms.put("微信", 1);
		platforms.put("支付宝", 2);
		platforms.put("健康易", 3);
		platforms.put("统一平台", 4);
		
	}
}
