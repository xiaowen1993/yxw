/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年12月26日
 * @version 1.0
 */
package com.yxw.integration.elasticsearch;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.integration.common.PropertiesUtil;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年12月26日  
 */
public class ESClient {

	private static final Logger logger = LoggerFactory.getLogger(ElasticsearchController.class);

	private static TransportClient client = null;

	public static TransportClient getInstance() {
		try {
			if (client == null) {
				synchronized (ESClient.class) {
					if (client == null) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("cluster.name", "elasticsearch");
						Settings settings = Settings.builder().put(map).build();
						client = new PreBuiltTransportClient(settings);
						TransportAddress address =
								new InetSocketTransportAddress(InetAddress.getByName(PropertiesUtil.elasticsearchIp),
										PropertiesUtil.elasticsearchPort);

						client.addTransportAddress(address);
					}
				}
			}

		} catch (Exception e) {
			logger.error("init elssticsearch client failuer", e);
		}

		return client;
	}
}
