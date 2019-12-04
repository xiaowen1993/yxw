package com.yxw.outside.callable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.yxw.client.YxwSolrClient;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * 普通查询类
 * @author Administrator
 *
 */
public class QueryInfosCallable implements Callable<Map<String, Object>> {
	
	private String coreName;
	
	private String key;
	
	private SolrQuery solrQuery;
	
	private Class<?> clazz;
	
	public QueryInfosCallable(String coreName, String key, SolrQuery solrQuery, Class<?> clazz) {
		this.coreName = coreName;
		this.key = key;
		this.solrQuery = solrQuery;
		this.clazz = clazz;
	}

	@Override
	public Map<String, Object> call() throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		
		// 查solr获取相应的信息
		YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);
		QueryResponse response = solrClient.query(coreName, solrQuery);
		
		resultMap.put(key, response.getBeans(clazz));
		
		return resultMap;
	}

	public String getCoreName() {
		return coreName;
	}

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public SolrQuery getSolrQuery() {
		return solrQuery;
	}

	public void setSolrQuery(SolrQuery solrQuery) {
		this.solrQuery = solrQuery;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

}
