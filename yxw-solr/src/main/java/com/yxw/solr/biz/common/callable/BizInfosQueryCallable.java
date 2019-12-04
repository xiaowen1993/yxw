package com.yxw.solr.biz.common.callable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.client.YxwSolrClient;

public class BizInfosQueryCallable implements Callable<Map<String, Object>> {

	private Logger logger = LoggerFactory.getLogger(BizInfosQueryCallable.class);

	private String coreName;

	private SolrQuery solrQuery;
	
	public BizInfosQueryCallable(String coreName, SolrQuery solrQuery) {
		this.coreName = coreName;
		this.solrQuery = solrQuery;
	}

	@Override
	public Map<String, Object> call() throws Exception {
		YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);
		Map<String, Object> resultMap = new HashMap<>();

		try {
			QueryResponse response = solrClient.query(coreName, solrQuery);
			SolrDocumentList solrDocumentList = response.getResults();
			resultMap.put("size", solrDocumentList.getNumFound());
			resultMap.put("beans", solrDocumentList);
			
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

}
