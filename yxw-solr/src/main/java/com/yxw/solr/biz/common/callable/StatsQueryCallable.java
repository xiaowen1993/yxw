package com.yxw.solr.biz.common.callable;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.client.YxwSolrClient;

public class StatsQueryCallable implements Callable<Map<String, Integer>> {

	private Logger logger = LoggerFactory.getLogger(StatsQueryCallable.class);

	private String coreName;

	private SolrQuery solrQuery;
	
	private String facetField;
	
	public StatsQueryCallable(String coreName, SolrQuery solrQuery, String facetField) {
		this.coreName = coreName;
		this.solrQuery = solrQuery;
		this.facetField = facetField;
	}

	@Override
	public Map<String, Integer> call() throws Exception {
		YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);
		Map<String, Integer> resultMap = new HashMap<>();

		try {
			QueryResponse response = solrClient.query(coreName, solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response.getResponse()
					.asMap(100);
			Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> statsFields = responseMap.get("stats").get("stats_fields");
			for (Entry<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> entry1 : statsFields.entrySet()) {
				String statsField = entry1.getKey();
				Map<String, Map<String, Object>> facetMap = entry1.getValue().get("facets").get(facetField);

				for (Entry<String, Map<String, Object>> entry2 : facetMap.entrySet()) {
					// String date = entry2.getKey();
					Integer value = ((Double) entry2.getValue().get("sum")).intValue();

					resultMap.put(statsField, value);
				}
			}
			
		} catch (Exception e) {
			logger.error("findOrders error. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	public SolrQuery getSolrQuery() {
		return solrQuery;
	}

	public void setSolrQuery(SolrQuery solrQuery) {
		this.solrQuery = solrQuery;
	}

	public String getCoreName() {
		return coreName;
	}

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	public String getFacetField() {
		return facetField;
	}

	public void setFacetField(String facetField) {
		this.facetField = facetField;
	}

}
