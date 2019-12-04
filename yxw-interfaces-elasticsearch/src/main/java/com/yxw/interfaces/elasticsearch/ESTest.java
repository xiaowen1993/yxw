package com.yxw.interfaces.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

public class ESTest {

	public static void test() {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchQuery("order_no.keyword", "Y242420170922115259110904400013"));

		//		SearchResponse response = ESClient.getInstance()
		//				.prepareSearch("209.new_yx129_platform.biz_register_record", "47.yx129.biz_register_record")
		//				.setTypes("biz_register_record")
		//				.setQuery(QueryBuilders.matchAllQuery()) //查询所有
		//		        .setQuery(boolQueryBuilder)
		//		        .get();

		SearchResponse response =
				ESClient.getInstance()
						.prepareSearch("209.new_yx129_platform.biz_register_record", "47.yx129.biz_register_record")
						.setTypes("biz_register_record")
						//.setPostFilter(QueryBuilders.matchQuery("order_no.keyword", "Y242420170922115259110904400013"))
						.setPostFilter(
								QueryBuilders.termsQuery("order_no.keyword", "Y242420170922115259110904400013", "2015091419291019506"))
						.execute().actionGet();

		JSONObject responseJson = JSONObject.parseObject(response.toString());
		System.out.println(JSONPath.eval(responseJson, "$.hits.total"));
		System.out.println(JSONObject.toJSONString(responseJson, true));
	}

	public static void getEasyHealthUserByIDCardOrMobile() {
		long start = System.currentTimeMillis();
		String configIndex = "246.new_yx129_platform.eh_user";

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.should(QueryBuilders.termsQuery("card_no", "150430199110270022"));
		boolQueryBuilder.should(QueryBuilders.termsQuery("mobile", "15702127761"));

		SearchResponse response = ESClient.getInstance().prepareSearch(configIndex).setQuery(QueryBuilders.matchAllQuery()) //查询所有
				.setQuery(boolQueryBuilder).get();

		JSONObject responseJson = JSONObject.parseObject(response.toString());
		Integer totalHits = Integer.valueOf(JSONPath.eval(responseJson, "$.hits.total").toString());
		if (totalHits > 0) {
			System.out.println(responseJson.getJSONObject("hits"));
		}
		System.out.println(start - ( System.currentTimeMillis() ));
	}

	public static void main(String[] args) {
		//		test();
		getEasyHealthUserByIDCardOrMobile();
	}

}
