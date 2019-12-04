package com.yxw.outside.callable;

import java.util.HashMap;
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
import com.yxw.outside.constant.OutsideConstants;
import com.yxw.utils.DateUtils;

public class SubscribeResumeCallable implements Callable<Map<String, Object>> {
	
	private Logger logger = LoggerFactory.getLogger(SubscribeResumeCallable.class);
	
	@Override
	public Map<String, Object> call() throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		
		YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);
		SolrQuery solrQuery = new SolrQuery();
		
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("-hospitalCode:\\- AND ");
		// 日期
		String statsMonth = DateUtils.getFirstDayOfLastMonth(DateUtils.today()).substring(0, 7);
		sbQuery.append("thisMonth:" + statsMonth);

		solrQuery.setQuery(sbQuery.toString());
		solrQuery.setSort("hospitalName", ORDER.asc);
		solrQuery.setRows(0);

		solrQuery.setGetFieldStatistics(true);
		solrQuery.addGetFieldStatistics("wxCumulateCountTillThisMonth");
		solrQuery.addGetFieldStatistics("aliCumulateCountTillThisMonth");
		// solrQuery.addStatsFieldFacets(null, "thisMonth");
		solrQuery.addStatsFieldFacets(null, "hospitalName");
		
		Map<String, Map<String, Object>> statsMap = new HashMap<>();
		
		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_SUBSCRIBE, solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response
					.getResponse().asMap(100);
			Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> statsFields = responseMap.get("stats")
					.get("stats_fields");
			for (Entry<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> entry1 : statsFields.entrySet()) {
				String statsField = entry1.getKey();
				Map<String, Map<String, Object>> facetField = entry1.getValue().get("facets").get("hospitalName");

				for (Entry<String, Map<String, Object>> entry2 : facetField.entrySet()) {
//					String date = entry2.getKey();
//					if (date.equals(statsMonth)) {
//						Double value = (Double) entry2.getValue().get("sum");
//						statsMap.put(statsField, value.intValue());
//					}
					Double value = (Double) entry2.getValue().get("sum");
					// statsMap.put(entry2.getKey(), value.intValue());
					Map<String, Object> map = null;
					if (statsMap.containsKey(entry2.getKey())) {
						map = statsMap.get(entry2.getKey());
					} else {
						map = new HashMap<>();
					}
					
					map.put(statsField, value);
					statsMap.put(entry2.getKey(), map);
				}
			}
		} catch (Exception e) {
			logger.error("获取关注统计数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}
		
		resultMap.put(OutsideConstants.SUBSCRIBE_INFOS, statsMap);
		
		return resultMap;
	}

}
