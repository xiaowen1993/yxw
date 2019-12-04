/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年12月25日
 * @version 1.0
 */
package com.yxw.integration.elasticsearch;

import java.util.Map;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年12月25日
 */
public class ElasticsearchController {
	private static final Logger logger = LoggerFactory.getLogger(ElasticsearchController.class);

	public static void insertById(String index, String type, String id, Map<String, Object> dataMap) {
		IndexResponse indexResponse = null;
		try {
			indexResponse = ESClient.getInstance().prepareIndex(index, type, id).setSource(dataMap).get();

			//			logger.info("insert index=" + index + ", type=" + type + ", id=" + id);
		} catch (Exception e) {
			logger.error("elasticsearch insert error., index=" + index + ", type=" + type + ", data=" + new Gson().toJson(dataMap), e);

			if (indexResponse != null) {
				System.out.println("IndexResponse索引名称:" + indexResponse.getIndex() + "\n IndexResponse类型:" + indexResponse.getType()
						+ "\n IndexResponse文档ID:" + indexResponse.getId() + "\n当前实例IndexResponse状态:" + indexResponse.status());
			}
		}
	}

	public static void batchInsertById(String index, String type, Map<String, Map<String, Object>> idDataMap) {
		//		System.out.println("index info: (" + new Gson().toJson(idDataMap) + ")");

		BulkRequestBuilder bulkRequestBuilder = ESClient.getInstance().prepareBulk();

		idDataMap.forEach((id, dataMap) -> bulkRequestBuilder.add(ESClient.getInstance().prepareIndex(index, type, id).setSource(dataMap)));

		//		logger.info("bulk insert index=" + index + ", type=" + type + ", ids=" + idDataMap.keySet().toString());

		try {
			BulkResponse bulkResponse = bulkRequestBuilder.execute().get();
			if (bulkResponse.hasFailures()) {
				logger.error("elasticsearch batch insert error., index=" + index + ", type=" + type + ", data="
						+ new Gson().toJson(idDataMap) + ", cause:" + bulkResponse.buildFailureMessage());
			}
		} catch (Exception e) {
			logger.error("elasticsearch batch insert error., index=" + index + ", type=" + type + ", data=" + new Gson().toJson(idDataMap),
					e);
		}
	}

	public static void batchUpdateById(String index, String type, Map<String, Map<String, Object>> idDataMap) {
		//		System.out.println("index info: (" + new Gson().toJson(idDataMap) + ")");
		BulkRequestBuilder bulkRequestBuilder = ESClient.getInstance().prepareBulk();

		idDataMap.forEach((id, dataMap) -> bulkRequestBuilder.add(ESClient.getInstance().prepareUpdate(index, type, id).setDoc(dataMap)));

		//		logger.info("bulk update index=" + index + ", type=" + type + ", ids=" + idDataMap.keySet().toString());

		try {
			BulkResponse bulkResponse = bulkRequestBuilder.execute().get();
			if (bulkResponse.hasFailures()) {
				logger.error("elasticsearch batch update error., index=" + index + ", type=" + type + ", data="
						+ new Gson().toJson(idDataMap) + ", cause:" + bulkResponse.buildFailureMessage());
			}
		} catch (Exception e) {
			logger.error("elasticsearch batch update error., index=" + index + ", type=" + type + ", data=" + new Gson().toJson(idDataMap),
					e);
		}
	}

	public static void update(String index, String type, String id, Map<String, Object> dataMap) {
		UpdateResponse updateResponse = null;
		try {
			updateResponse = ESClient.getInstance().prepareUpdate(index, type, id).setDoc(dataMap).get();

			/*XContentBuilder fieldBuilder = XContentFactory.jsonBuilder().startObject();
			for (Map.Entry<String, Object> entry : dataMap.entrySet()) {

				fieldBuilder.field(entry.getKey(), entry.getValue());
			}
			fieldBuilder.endObject();
			UpdateRequest updateRequest = new UpdateRequest(index, type, id).doc(fieldBuilder);

			updateResponse = ESClient.getInstance().update(updateRequest).get();*/

			//			logger.info("update index=" + index + ", type=" + type + ", ids=" + id);
		} catch (Exception e) {
			logger.error("elasticsearch update error., index=" + index + ", type=" + type + ", id=" + id, e);

			if (updateResponse != null) {
				System.out.println("updateResponse索引名称:" + updateResponse.getIndex() + "\n updateResponse类型:" + updateResponse.getType()
						+ "\n updateResponse文档ID:" + updateResponse.getId() + "\n当前实例updateResponse状态:" + updateResponse.status());
			}
		}
	}

	public static void deleteById(String index, String type, String id) {
		DeleteResponse deleteResponse = null;
		try {
			deleteResponse = ESClient.getInstance().prepareDelete(index, type, id).get();

			//			logger.info("delete index=" + index + ", type=" + type + ", ids=" + id);

		} catch (Exception e) {
			logger.error("elasticsearch delete error., index=" + index + ", type=" + type + ", id=" + id, e);

			if (deleteResponse != null) {
				System.out.println("deleteResponse索引名称:" + deleteResponse.getIndex() + "\n deleteResponse类型:" + deleteResponse.getType()
						+ "\n deleteResponse文档ID:" + deleteResponse.getId() + "\n当前实例deleteResponse状态:" + deleteResponse.status());
			}
		}

	}

	public static void getById(String index, String type, String id) {
		GetResponse getResponse = ESClient.getInstance().prepareGet(index, type, id).get();
		//		System.out.println("索引库的数据:" + getResponse.getSourceAsString());
	}
}
