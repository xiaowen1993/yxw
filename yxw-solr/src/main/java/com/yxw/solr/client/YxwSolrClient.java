package com.yxw.solr.client;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class YxwSolrClient extends HttpSolrClient {

	private static final long serialVersionUID = 4093457342110920761L;
	
	// 读取超时
	private int readTimeout;
	
	// 链接超时
	private int connectTimeout;
	
	// 是否允许压缩
	private boolean allowCompression;
	
	// 最大连接数
	private int maxTotalConnections;
	
	public YxwSolrClient(String baseURL) {
		super(baseURL);
		this.setDefaultMaxConnectionsPerHost(100);
	}
	
	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		super.setSoTimeout(readTimeout);
		this.readTimeout = readTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		super.setConnectionTimeout(connectTimeout);
		this.connectTimeout = connectTimeout;
	}

	public boolean isAllowCompression() {
		return allowCompression;
	}

	public void setAllowCompression(boolean allowCompression) {
		super.setAllowCompression(allowCompression);
		this.allowCompression = allowCompression;
	}

	public int getMaxTotalConnections() {
		return maxTotalConnections;
	}

	public void setMaxTotalConnections(int maxTotalConnections) {
		super.setMaxTotalConnections(maxTotalConnections);
		this.maxTotalConnections = maxTotalConnections;
	}

}
