package com.yxw.client;

import java.util.Collection;

import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.config.SystemConfig;

public abstract class YxwUpdateClient {

	// private static Map<String, ConcurrentUpdateSolrClient> clients = new ConcurrentHashMap<>();

	private static String SERVER_URL = "";

	private static int CONNECT_TIMEOUT = 60000;

	private static int SO_TIMEOUT = 60000;

	private static int QUEUE_SIZE = 10;

	private static int THREAD_COUNT = 10;

	private static Logger logger = LoggerFactory.getLogger(YxwUpdateClient.class);

	static {
		SERVER_URL = SystemConfig.getStringValue("solr.server.url");
		CONNECT_TIMEOUT = SystemConfig.getIntegerValue("solr.server.connectTimeout");
		SO_TIMEOUT = SystemConfig.getIntegerValue("solr.server.readTimeout");
		QUEUE_SIZE = SystemConfig.getIntegerValue("solr.server.queueSize");
		THREAD_COUNT = SystemConfig.getIntegerValue("solr.server.threadCount");
	}

	/**
	 * 新增单个对象
	 * @param coreName
	 * @param bean
	 * @throws Exception
	 */
	public static void addBean(String coreName, Object bean, Boolean clear, String sQuery) {
		/*
		 * ConcurrentUpdateSolrClient solrClient = clients.get(coreName); if (solrClient == null) { String solrServerUrl = SERVER_URL + "/"
		 * + coreName; solrClient = new ConcurrentUpdateSolrClient(solrServerUrl, 10, 10); clients.put(coreName, solrClient); }
		 */

		ConcurrentUpdateSolrClient solrClient = null;

		try {
			String solrServerUrl = SERVER_URL + "/" + coreName;
			solrClient = new ConcurrentUpdateSolrClient(solrServerUrl, QUEUE_SIZE, THREAD_COUNT);
			solrClient.setSoTimeout(SO_TIMEOUT);
			solrClient.setConnectionTimeout(CONNECT_TIMEOUT);
			if (clear) {
				logger.info("删除条件： " + sQuery);
				solrClient.deleteByQuery(sQuery);
				solrClient.commit();
			}

			solrClient.addBean(bean);
			solrClient.commit();
			solrClient.optimize();
			solrClient.commit();
		} catch (Exception e) {
			logger.error("add bean error. coreName: {}. erroMsg: {}. cause: {}.", new Object[] { coreName, e.getMessage(), e.getCause() });
		}
	}

	/**
	 * 添加多个对象
	 * @param coreName
	 * @param beans
	 * @param clear -- 是否需要重建索引
	 * @throws Exception
	 */
	public static void addBeans(String coreName, Collection<Object> beans, Boolean clear, String sQuery) {
		addBeans(coreName, beans, clear, sQuery, null);
	}

	/**
	 * 添加多个对象
	 * @param coreName
	 * @param beans
	 * @param clear -- 是否需要重建索引
	 * @throws Exception
	 */
	public static void addBeans(String coreName, Collection<Object> beans, Boolean clear, String sQuery, String sFilter) {
		/*
		 * ConcurrentUpdateSolrClient solrClient = getSolrClient(coreName); if (solrClient == null) { String solrServerUrl = SERVER_URL +
		 * "/" + coreName; solrClient = new ConcurrentUpdateSolrClient(solrServerUrl, 10, 10); clients.put(coreName, solrClient); }
		 */

		ConcurrentUpdateSolrClient solrClient = null;

		try {
			if (beans != null && beans.size() > 0) {
				String solrServerUrl = SERVER_URL + "/" + coreName;
				solrClient = new ConcurrentUpdateSolrClient(solrServerUrl, QUEUE_SIZE, THREAD_COUNT);
				solrClient.setSoTimeout(SO_TIMEOUT);
				solrClient.setConnectionTimeout(CONNECT_TIMEOUT);
				if (clear) {
					logger.info("删除条件： " + sQuery);
					solrClient.deleteByQuery(sQuery);
					solrClient.commit();
				}
	
				// solrClient.addBeans(beans, 10000);
				solrClient.addBeans(beans);
				solrClient.optimize();
				solrClient.commit();
			} else {
				logger.error("没有可以存入的数据. coreName: {}, sQuery: {}. sFilter: {}.", new Object[]{coreName, sQuery, sFilter});
			}
		} catch (Exception e) {
			logger.error("add beans error. coreName: {}. erroMsg: {}. cause: {}.", new Object[] { coreName, e.getMessage(), e.getCause() });
		}
	}

	/*
	 * private static ConcurrentUpdateSolrClient getSolrClient(String coreName) { ConcurrentUpdateSolrClient solrClient =
	 * clients.get(coreName); if (solrClient == null) { String solrServerUrl = SERVER_URL + "/" + coreName; // String solrServerUrl =
	 * "http://localhost:9999/solr/statsCard"; solrClient = new ConcurrentUpdateSolrClient(solrServerUrl, QUEUE_SIZE, THREAD_COUNT);
	 * solrClient.setSoTimeout(SO_TIMEOUT); solrClient.setConnectionTimeout(CONNECT_TIMEOUT); clients.put(coreName, solrClient); }
	 * 
	 * return solrClient; }
	 */
}
