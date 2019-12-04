package com.yxw.outside.callable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.client.YxwSolrClient;
import com.yxw.constants.Cores;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

public class GenderDatasCallable implements Callable<Map<String, Object>> {
	
	private Logger logger = LoggerFactory.getLogger(GenderDatasCallable.class);
	
	private String areaCode;
	
	private String hospitalCode;
	
	private String beginMonth;
	
	private String endMonth;
	
	public GenderDatasCallable(String areaCode, String hospitalCode, String beginMonth, String endMonth) {
		this.areaCode = areaCode;
		this.hospitalCode = hospitalCode;
		this.beginMonth = beginMonth;
		this.endMonth = endMonth;
	}
	
	/**
	 * beginMonth-endMonth 都是对应thisMonth
	 * areaCode:-1  	按区域进行统计。返回区域、区域的微信绑卡数、支付宝的绑卡数
	 * araeCode:0 		按所有区域进行统计，返回所有区域内的各医院的微信绑卡数、支付宝绑卡数
	 * areaCode:XX  	按区域，返回该区域内所有医院信息
	 * hospitalCode:XX	返回特定医院信息
	 */
	
	@Override
	public Map<String, Object> call() throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		
		YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);
		SolrQuery solrQuery = new SolrQuery();
		
		StringBuffer sbQuery = new StringBuffer();
		if (areaCode.equals("-1")) {
			sbQuery.append("hospitalCode:\\- AND ");
			solrQuery.setSort("areaName", ORDER.asc);
		} else if (areaCode.equals("0")) {
			sbQuery.append("-hospitalCode:\\- AND ");
			solrQuery.setSort("areaName", ORDER.asc);
		} else {
			if (hospitalCode.equals("-1")) {
				sbQuery.append("-hospitalCode:\\- AND areaCode:" + areaCode.replace("/", "\\/") + " AND ");
				solrQuery.setSort("hospitalName", ORDER.asc);
			} else {
				sbQuery.append("hospitalCode:" + hospitalCode + " AND ");
				solrQuery.setSort("hospitalName", ORDER.asc);
			}
		}
		
		// 日期
		sbQuery.append("thisMonth:[" + beginMonth + " TO " + endMonth + "]");

		solrQuery.setQuery(sbQuery.toString());
		solrQuery.setRows(Integer.MAX_VALUE);
		solrQuery.addSort("thisMonth", ORDER.asc);
		solrQuery.setGetFieldStatistics(true);
		// wechat
		solrQuery.addGetFieldStatistics("thisMonthWxMan");
		solrQuery.addGetFieldStatistics("thisMonthWxWoman");
		
		// alipay
		solrQuery.addGetFieldStatistics("thisMonthAliMan");
		solrQuery.addGetFieldStatistics("thisMonthAliWoman");
		
		solrQuery.addStatsFieldFacets(null, "thisMonth");
		
		Map<String, Map<String, Object>> statsMap = new LinkedHashMap<>();
		
		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_GENDER, solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response
					.getResponse().asMap(100);
			Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> statsFields = responseMap.get("stats")
					.get("stats_fields");
			for (Entry<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> entry1 : statsFields.entrySet()) {
				String statsField = entry1.getKey();
				Map<String, Map<String, Object>> facetField = entry1.getValue().get("facets").get("thisMonth");

				for (Entry<String, Map<String, Object>> entry2 : facetField.entrySet()) {
					Double value = (Double) entry2.getValue().get("sum");
					// statsMap.put(entry2.getKey(), value.intValue());
					Map<String, Object> map = null;
					if (statsMap.containsKey(entry2.getKey())) {
						map = statsMap.get(entry2.getKey());
					} else {
						map = new HashMap<>();
					}
					
					map.put(statsField, (Long)Math.round(value) + "");
					statsMap.put(entry2.getKey(), map);
				}
			}
			resultMap.put("entity", statsMap);
		} catch (Exception e) {
			logger.error("获取性别统计数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}
		
		return resultMap;
	}
	
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBeginMonth() {
		return beginMonth;
	}

	public void setBeginMonth(String beginMonth) {
		this.beginMonth = beginMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

}
