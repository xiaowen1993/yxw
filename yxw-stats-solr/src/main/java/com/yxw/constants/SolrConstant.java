package com.yxw.constants;

import java.util.HashMap;
import java.util.Map;

public class SolrConstant {
	/**
	 * 默认查询的数据数目 （按文档：20）
	 */
	public final static int DEFAULT_RECORD_COUNT = 20;
	
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
