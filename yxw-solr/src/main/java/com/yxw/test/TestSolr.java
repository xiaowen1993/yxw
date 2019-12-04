package com.yxw.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.GroupParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.PKGenerator;
import com.yxw.solr.constants.SolrConstant;
import com.yxw.solr.vo.CoreVo;
import com.yxw.utils.DateUtils;

public class TestSolr {
	public static void main(String[] args) {
		CoreVo coreVo = new CoreVo();
		coreVo.setBizType(1);
		coreVo.setPlatform(1);
		
		System.out.println(JSON.toJSONString(coreVo));
		
		Map<String, Object> map = null;
		((CoreVo) map.get("aaa")).getBizType();
		
		
//		String str = "{\"coreName\":\"stdRegister\",\"bizType\":7,\"platform\":1}";
//		CoreVo vo = JSON.parseObject(str, CoreVo.class);
//		System.out.println(vo.getBizType());
	}

	public static void getStatsInfo() {
		String url = "http://localhost:9999/solr" + "/" + "statsCard";
		HttpSolrClient client;
		try {
			// client = new LBHttpSolrClient(url);
			client = new HttpSolrClient(url);

			System.out.println("----------------------------------------------------------------------------");
			SolrQuery query = new SolrQuery();
			String queryString = SolrConstant.QUERY_ALL;
			queryString = queryString.concat(SolrConstant.AND).concat("platform").concat(SolrConstant.COLON).concat("3");
			queryString = queryString.concat(SolrConstant.AND).concat("statsDate").concat(SolrConstant.COLON).concat("2016-05-15");

			query.setQuery(queryString);
			QueryResponse response = client.query(query);

			System.out.println(JSON.toJSONString(response.getResults().get(0), true));
			//
			// String queryString = SolrConstant.QUERY_ALL;
			// queryString =
			// queryString.concat(SolrConstant.AND).concat("platform").concat(SolrConstant.COLON).concat("3");
			// queryString =
			// queryString.concat(SolrConstant.AND).concat("statsDate").concat(SolrConstant.COLON).concat("2016-05-16");
			//
			// client.deleteByQuery(queryString);
			// client.commit();

			// Thread.sleep(1800000L);
			// System.out.println("----------------------------------------------------------------------------");
			// response = client.query(query);
			// System.out.println(JSON.toJSONString(response.getResults().get(0),
			// true));

			client.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void groupBy() {
		/*
		 * String url = "http://localhost:9999/solr" + "/" + "ehCard";
		 * 
		 * // 普通使用 HttpSolrClient client; try { // client = new
		 * LBHttpSolrClient(url); client = new HttpSolrClient(url);
		 * 
		 * SolrQuery query = new SolrQuery();
		 * query.setQuery(SolrConstant.QUERY_ALL); query.setFields("id");
		 * query.set(GroupParams.GROUP, "true");
		 * query.set(GroupParams.GROUP_FIELD, "hospitalCode"); //
		 * query.setParam(GroupParams.GROUP_FORMAT, "simple"); QueryResponse
		 * response = client.query(query); GroupResponse groupResponse =
		 * response.getGroupResponse(); if (groupResponse != null) {
		 * List<GroupCommand> groupList = groupResponse.getValues();
		 * System.out.println(JSON.toJSONString(groupList)); for (GroupCommand
		 * groupCommand : groupList) { for (Group group :
		 * groupCommand.getValues()) { String groupValue =
		 * group.getGroupValue(); System.out.println((groupValue + ":" + (int)
		 * group.getResult().getNumFound())); } } } //
		 * System.out.println(JSON.toJSONString(response.getGroupResponse().
		 * getValues())); //
		 * System.out.println(JSON.toJSONString(getHospitalCodes(response.
		 * getGroupResponse())));
		 * 
		 * client.close(); } catch (Exception e1) { // TODO Auto-generated catch
		 * block e1.printStackTrace(); }
		 */

		String url = "http://localhost:9999/solr" + "/" + "ehRegister";

		// 普通使用
		HttpSolrClient client;
		try {
			// client = new LBHttpSolrClient(url);
			client = new HttpSolrClient(url);
			SolrQuery query = new SolrQuery();

			query.setFields("payDate,deptName,deptCode");
			// query.setQuery("payDate:2015-11-26 AND platform:3 AND
			// branchCode:45575559X AND hospitalCode:szsdsrmyy AND
			// payStatus:2");
			query.setQuery("platform:3 AND branchCode:45575559X AND hospitalCode:szsdsrmyy AND payStatus:2");
			query.setFields(new String[] { "deptCode", "deptName" });
			query.set(GroupParams.GROUP, "true");
			query.set(GroupParams.GROUP_FIELD, "deptCode");
			query.addSort("payDate", ORDER.asc);
			query.set(GroupParams.GROUP_FACET, true);
			QueryResponse response = client.query(query);
			GroupResponse groupResponse = response.getGroupResponse();
			if (groupResponse != null) {
				List<GroupCommand> groupList = groupResponse.getValues();
				System.out.println(JSON.toJSONString(groupList));
				for (GroupCommand groupCommand : groupList) {
					for (Group group : groupCommand.getValues()) {
						String groupValue = group.getGroupValue();
						System.out.println((groupValue + ":" + group.getResult().get(0).getFieldValue("deptName")));
					}
				}
			}
			// System.out.println(JSON.toJSONString(response.getGroupResponse().getValues()));
			// System.out.println(JSON.toJSONString(getHospitalCodes(response.getGroupResponse())));

			client.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static List<String> getHospitalCodes(GroupResponse groupResponse) {
		List<String> hospitalCodes = new ArrayList<String>();

		if (!groupResponse.getValues().isEmpty()) {
			GroupCommand groupCommand = groupResponse.getValues().get(0);
			if (!groupCommand.getValues().isEmpty()) {
				Group group = groupCommand.getValues().get(0);
				for (SolrDocument document : group.getResult()) {
					hospitalCodes.add((String) document.getFieldValue("hospitalCode"));
				}
			}
		}

		return hospitalCodes;
	}

	public void findCards() {
		/*
		 * MedicalcardService service = new MedicalcardServiceImpl(); String
		 * name = "陈广煜"; String cardNo = ""; String mobile = ""; String idNo =
		 * ""; int state = 1; int pageSize = 10; int pageIndex = 1;
		 * service.findMedicalcards(name, cardNo, mobile, idNo, state, pageSize,
		 * pageIndex, Cores.CORE_MEDICAL_CARD);
		 */
	}

	public static void addDoc() {
		String url = "http://localhost:9999/solr" + "/" + "statsDept";
		// LBHttpSolrClient 负载使用
		// LBHttpSolrClient client;

		// 普通使用
		HttpSolrClient client;
		try {
			// client = new LBHttpSolrClient(url);
			client = new HttpSolrClient(url);

			// SolrInputDocument doc = new SolrInputDocument();
			// doc.addField("id", "1");
			// doc.addField("statsDate", "2011-01-01");
			// doc.addField("newBindQuantity", "5");
			// doc.addField("unBindQuantity", "5");
			// doc.addField("netIncreasedQuantity", "5");
			// doc.addField("totalQuantity", "5");
			// doc.addField("cumulativeQuantity", "5");
			// doc.addField("updateTime_utc", "2011-01-01T01:01:01Z");
			// doc.addField("updateTime", "2011-01-01");

			// client.deleteById("1");
			client.deleteByQuery("statsDate:2016-05-24");
			client.commit();
			// UpdateResponse response = client.add(doc);
			// System.out.println(response.toString());
			// client.optimize();
			// client.commit();

			client.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void commonQuery() {
		String url = "http://localhost:9999/solr";
		// LBHttpSolrClient 负载使用
		// LBHttpSolrClient client;

		// 普通使用
		HttpSolrClient client;
		try {
			// client = new LBHttpSolrClient(url);
			client = new HttpSolrClient(url);

			SolrQuery query = new SolrQuery();
			// 普通查询
			query.setQuery("payStatus:2");
			query.setFilterQueries("payTime_utc:[2016-04-01T00:00:01Z TO 2016-04-03T00:00:01Z]");
			query.setFields("payTime_date,payFee,totalFee,medicareFee");
			System.out.println("----" + query.getCopy());
			SolrDocumentList list;
			QueryResponse response = client.query("clinicRecord", query);
			list = response.getResults();
			System.out.println(response.getResults());
			System.out.println(JSON.toJSONString(list, true));

			client.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void facetQuery() {
		String url = "http://localhost:9999/solr/medicalcard";
		// LBHttpSolrClient 负载使用
		// LBHttpSolrClient client;

		// 普通使用
		HttpSolrClient client;
		try {
			// client = new LBHttpSolrClient(url);
			client = new HttpSolrClient(url);
			SolrQuery query = new SolrQuery();

			// 组合
			query.setQuery("state:0");
			query.setFilterQueries("updateTime_utc:[2016-04-01T00:00:01Z TO 2016-04-30T23:59:59Z]");
			query.setFields("createTime_date");
			query.setFacet(true);
			query.setFacetSort("index");
			// query.addFacetField("updateTime_date");
			query.setRows(0);
			query.addFacetPivotField("updateTime_date");
			query.addFacetQuery("updateTime_utc:[2016-04-01T00:00:01Z TO 2016-04-30T23:59:59Z]");
			SolrDocumentList list;
			QueryResponse response = client.query(query);
			list = response.getResults();
			// System.out.println(response.getResults());
			//
			// System.out.println(JSON.toJSONString(list, true));
			// System.out.println(JSON.toJSONString(response.getFacetRanges(),
			// true));
			// System.out.println(JSON.toJSONString(response.getFacetQuery()));
			System.out.println(JSON.toJSONString(response.getFacetPivot().asMap(10).get("updateTime_date")));

			client.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void statsQuery() {
		// String url = "http://localhost:9999/solr/ehRegister";
		String url = "http://localhost:9999/solr/ehRegister";
		// 普通使用
		HttpSolrClient client;
		try {
			// client = new LBHttpSolrClient(url);
			client = new HttpSolrClient(url);
			SolrQuery query = new SolrQuery();

			// 聚合
			// query.setQuery("payStatus:2 AND regType:1");
			query.setQuery("payStatus:2 AND regType:2");
			query.setFilterQueries("payDate:[2016-03-01 TO 2016-03-05]");
			query.setRows(0);

			// 金额汇总
			query.setGetFieldStatistics(true);
			query.addGetFieldStatistics("totalFee");
			query.addStatsFieldFacets("totalFee", "createDate");
			query.addOrUpdateSort("payDate", ORDER.asc);

			/**
			 * @See FieldStatsInfo Object min; Object max; Object sum; Long
			 *      count; Long countDistinct; Collection
			 *      <Object> distinctValues; Long missing; Object mean = null;
			 *      Double sumOfSquares = null; Double stddev = null; Long
			 *      cardinality = null;
			 */

			QueryResponse response = client.query(query);
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> map = response.getResponse().asMap(100);

			System.out.println(map.get("stats").get("stats_fields").get("totalFee").get("facets").get("createDate"));

			client.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
