package com.yxw.interfaces.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.framework.config.SystemConfig;
import com.yxw.interfaces.elasticsearch.ESClient;
import com.yxw.interfaces.service.YxwESService;

public class YxwESServiceImpl implements YxwESService {

	@Override
	public JSONObject search(String index, String type, Map<String, String> match) {

		String[] indexs;
		if (StringUtils.isBlank(index)) {
			String configIndex = SystemConfig.getStringValue("elasticsearch.index." + type);
			indexs = configIndex.split(",");
		} else {
			indexs = index.split(",");
		}

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		for (Map.Entry<String, String> entry : match.entrySet()) {
			boolQueryBuilder.must(QueryBuilders.matchQuery(entry.getKey(), entry.getValue()));
		}

		SearchResponse response = ESClient.getInstance().prepareSearch(indexs)
		//.setTypes(type)
				.setQuery(QueryBuilders.matchAllQuery()) //查询所有
				.setQuery(boolQueryBuilder).get();

		JSONObject responseJson = JSONObject.parseObject(response.toString());
		Integer totalHits = Integer.valueOf(JSONPath.eval(responseJson, "$.hits.total").toString());
		if (totalHits > 0) {
			return responseJson.getJSONObject("hits");
		}

		return null;
	}

	@Override
	public JSONObject getPlatformByOrderNo(String type, String... orderNo) {
		JSONObject json = new JSONObject();

		String configIndex = SystemConfig.getStringValue("elasticsearch.index." + type);
		String[] indexs = configIndex.split(",");

		SearchResponse response = ESClient.getInstance().prepareSearch(indexs)
		//.setTypes(type)
				.setPostFilter(QueryBuilders.termsQuery("order_no.keyword", orderNo)).execute().actionGet();

		JSONObject responseJson = JSONObject.parseObject(response.toString());
		Integer totalHits = Integer.valueOf(JSONPath.eval(responseJson, "$.hits.total").toString());

		json.put("total", totalHits);
		if (totalHits > 0) {
			JSONArray hits = (JSONArray) JSONPath.eval(responseJson, "$.hits.hits");

			JSONArray ja = new JSONArray();
			for (int i = 0; i < hits.size(); i++) {
				JSONObject jsonItem = hits.getJSONObject(i);

				JSONObject result = new JSONObject();
				result.put("order_no", JSONPath.eval(jsonItem, "$._source.order_no"));
				result.put("es_index", jsonItem.getString("_index"));
				ja.add(result);
			}
			json.put("data", ja);
		}

		return json;
	}

	@Override
	public JSONObject getEasyHealthUserByIDCardOrMobile(String type, String IDCard, String mobile) {
		JSONObject json = new JSONObject();

		String configIndex = SystemConfig.getStringValue("elasticsearch.index." + type);

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.should(QueryBuilders.termsQuery("card_no", IDCard));
		boolQueryBuilder.should(QueryBuilders.termsQuery("mobile", mobile));

		SearchResponse response = ESClient.getInstance().prepareSearch(configIndex).setQuery(QueryBuilders.matchAllQuery()) //查询所有
				.setQuery(boolQueryBuilder).get();

		JSONObject responseJson = JSONObject.parseObject(response.toString());
		Integer totalHits = Integer.valueOf(JSONPath.eval(responseJson, "$.hits.total").toString());
		if (totalHits > 0) {
			return responseJson.getJSONObject("hits");
		}

		return json;
	}
}
