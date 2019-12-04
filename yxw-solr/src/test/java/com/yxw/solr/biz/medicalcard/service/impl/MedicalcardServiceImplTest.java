package com.yxw.solr.biz.medicalcard.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.GroupParams;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.solr.biz.medicalcard.service.MedicalcardService;
import com.yxw.solr.client.YxwSolrClient;
import com.yxw.solr.constants.Cores;
import com.yxw.solr.constants.PoolConstant;
import com.yxw.solr.constants.SolrConstant;
import com.yxw.solr.task.callable.StatsCardsCallable;
import com.yxw.solr.task.collector.StatsCardsCollector;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.medicalcard.CardInfoRequest;
import com.yxw.solr.vo.medicalcard.CardStatsRequest;
import com.yxw.utils.DateUtils;

public class MedicalcardServiceImplTest extends Junit4SpringContextHolder {

	private Logger logger = LoggerFactory.getLogger(MedicalcardServiceImplTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindMedicalcards() {
		MedicalcardService service = new MedicalcardServiceImpl();
		CardInfoRequest request = new CardInfoRequest();
		request.setPatientName("王松尧");
		request.setState(1);
		request.setPageIndex(1);
		request.setPageSize(10);
		request.setPlatform(3);
		YxwResponse vo = service.findCards(request);
		System.out.println(JSON.toJSONString(vo));
	}

	@Test
	public void testGetStatInfos() {
		CardStatsRequest request = new CardStatsRequest();
		request.setHospitalCode("dgsrmyy");
		request.setBranchCode("0007");
		request.setPlatform(2);
		request.setBeginDate("2016-04-01");
		request.setEndDate("2016-05-02");
		request.setPageIndex(1);
		request.setPageSize(20);
		// 统计默认倒序 懒得提供了

		MedicalcardService medicalcardService = SpringContextHolder.getBean(MedicalcardService.class);
//		ResponseVo vo = medicalcardService.getStatInfos(request);
//		System.out.println(JSON.toJSONString(vo));
		
		medicalcardService.statsInfosWithoutPlatform(request);
	}

	@Test
	public void testGetStats() {
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("statsCard"));
		List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();

		try {
			Map<String, Map<String, List<String>>> map = getHospitalInfos();

			for (Entry<String, Map<String, List<String>>> platformMap : map.entrySet()) {
				String platform = platformMap.getKey();

				for (Entry<String, List<String>> hospitalMap : platformMap.getValue().entrySet()) {
					String hospitalCode = hospitalMap.getKey();
					List<String> branchCodes = hospitalMap.getValue();

					for (String branchCode : branchCodes) {
						// 只统计昨天
						String statsDate = DateUtils.getDayForDate(new Date(), -1);
						// 统计昨天之前所有。
						// String statsDate = "";
						StatsCardsCallable callable = new StatsCardsCallable(Integer.valueOf(platform), hospitalCode, branchCode,
								statsDate);
						FutureTask<String> task = new FutureTask<String>(callable);
						taskList.add(task);
						executorService.submit(task);
					}
				}
			}

			// List<String> results = new ArrayList<String>();
			for (FutureTask<String> task : taskList) {
				task.get(Long.MAX_VALUE, TimeUnit.DAYS);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}

	private Map<String, Map<String, List<String>>> getHospitalInfos() {
		Map<String, Map<String, List<String>>> resultMap = new HashMap<String, Map<String, List<String>>>();

		Map<String, List<String>> easyHealthMap = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("HZ010");
		list1.add("-1");
		easyHealthMap.put("bjdxszyycs", list1);

		List<String> list2 = new ArrayList<String>();
		list2.add("0001");
		list2.add("-1");
		easyHealthMap.put("szetyycs", list2);

		List<String> list3 = new ArrayList<String>();
		list3.add("H0320");
		list3.add("-1");
		easyHealthMap.put("szsdermyycsh", list3);

		List<String> list4 = new ArrayList<String>();
		list4.add("45575559X");
		list4.add("45575559X1");
		list4.add("-1");
		easyHealthMap.put("szsdsrmyy", list4);

		List<String> list5 = new ArrayList<String>();
		list5.add("0001");
		list5.add("-1");
		easyHealthMap.put("szsfybjycs", list5);

		List<String> list6 = new ArrayList<String>();
		list6.add("H0320");
		list6.add("-1");
		easyHealthMap.put("szsykyy", list6);

		List<String> list7 = new ArrayList<String>();
		list7.add("0001");
		list7.add("-1");
		easyHealthMap.put("szszyy", list7);

		// 健康易为3
		resultMap.put("3", easyHealthMap);

		return resultMap;
	}

	private List<String> getHospitalCodes(YxwSolrClient solrClient) throws SolrServerException, IOException {
		List<String> hospitalCodes = new ArrayList<String>();
		SolrQuery query = new SolrQuery();
		query.setQuery(SolrConstant.QUERY_ALL);
		query.setFields("hospitalCode");
		query.setParam(GroupParams.GROUP, "true");
		query.setParam(GroupParams.GROUP_FIELD, "hospitalCode");
		query.setParam(GroupParams.GROUP_FORMAT, "simple");
		QueryResponse response = solrClient.query(Cores.CORE_STATS_CARD, query);

		if (!response.getGroupResponse().getValues().isEmpty()) {
			GroupCommand groupCommand = response.getGroupResponse().getValues().get(0);
			if (!groupCommand.getValues().isEmpty()) {
				Group group = groupCommand.getValues().get(0);
				for (SolrDocument document : group.getResult()) {
					hospitalCodes.add((String) document.getFieldValue("hospitalCode"));
				}
			}
		}

		return hospitalCodes;
	}

	private List<String> getBranchCodes(YxwSolrClient solrClient, String hospitalCode) throws SolrServerException, IOException {
		List<String> branchCodes = new ArrayList<String>();

		SolrQuery query = new SolrQuery();
		query.setQuery(SolrConstant.QUERY_ALL.concat(SolrConstant.AND).concat("hospitalCode").concat(SolrConstant.COLON).concat(hospitalCode));
		query.setFields("branchCode");
		query.setParam(GroupParams.GROUP, "true");
		query.setParam(GroupParams.GROUP_FIELD, "branchCode");
		query.setParam(GroupParams.GROUP_FORMAT, "simple");
		QueryResponse response = solrClient.query(Cores.CORE_STATS_CARD, query);

		if (!response.getGroupResponse().getValues().isEmpty()) {
			GroupCommand groupCommand = response.getGroupResponse().getValues().get(0);
			if (!groupCommand.getValues().isEmpty()) {
				Group group = groupCommand.getValues().get(0);
				for (SolrDocument document : group.getResult()) {
					branchCodes.add((String) document.getFieldValue("branchCode"));
				}
			}
		}

		// 默认全部分院
		branchCodes.add("-1");

		return branchCodes;
	}
	
	@Test
	public void testStatsCards() {
		StatsCardsCollector collector = new StatsCardsCollector();
		collector.startUp();
	}

}
