package com.yxw.biz.medicalcard.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.biz.common.StatsConstant;
import com.yxw.biz.medicalcard.callable.CardInfosCallable;
import com.yxw.biz.medicalcard.service.CardInfoService;
import com.yxw.client.YxwSolrClient;
import com.yxw.client.YxwUpdateClient;
import com.yxw.constants.Cores;
import com.yxw.constants.PoolConstant;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.utils.DateUtils;
import com.yxw.vo.medicalcard.AgeGroupStats;
import com.yxw.vo.medicalcard.CardInfoStatsRequest;
import com.yxw.vo.medicalcard.GenderStats;

@Service(value = "cardInfoService")
public class CardInfoServiceImpl implements CardInfoService {

	private static Logger logger = LoggerFactory.getLogger(CardInfoService.class);

	private YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);

	@Override
	public String statsInfos(CardInfoStatsRequest request) {
		String beginDate = StatsConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();

		// tempDate = "2017-01-01";

		// 需要生成索引的数据
		Map<String, Object> statsGenderMap = new LinkedHashMap<>();
		Map<String, Object> statsAgeGroupMap = new LinkedHashMap<>();

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("statsInfos-card"));
		List<FutureTask<Map<String, Object>>> taskList = new ArrayList<FutureTask<Map<String, Object>>>();

		/**
		 * 按月分来进行
		 */

		while (tempDate.compareTo(endDate) < 0) {
			String queryBeginDate = tempDate;
			String queryEndDate = DateUtils.getDayForDate(DateUtils.StringToDate(DateUtils.getFirstDayNextMonth(tempDate)), -1);

			CardInfosCallable callable = new CardInfosCallable(request, queryBeginDate, queryEndDate);
			FutureTask<Map<String, Object>> task = new FutureTask<Map<String, Object>>(callable);
			taskList.add(task);
			executorService.submit(task);

			tempDate = DateUtils.getFirstDayNextMonth(tempDate);
		}

		try {
			for (FutureTask<Map<String, Object>> task : taskList) {
				// 后续可能需要改成1天
				Map<String, Object> resultMap = task.get(30, TimeUnit.MINUTES);
				if (resultMap.size() > 0) {
					// logger.info(JSON.toJSONString(resultMap));
					GenderStats genderStats = (GenderStats) resultMap.get("gender");
					AgeGroupStats ageGroupStats = (AgeGroupStats) resultMap.get("ageGroup");
					statsGenderMap.put(genderStats.getThisMonth(), genderStats);
					statsAgeGroupMap.put(ageGroupStats.getThisMonth(), ageGroupStats);
				}
			}
		} catch (Exception e) {
			logger.error("医院： {}. 年龄段&&性别信息统计错误. errorMsg: {}. cause: {}.",
					new Object[] { request.getHospitalName(), e.getMessage(), e.getCause() });
		}

		executorService.shutdown();

		saveStatsInfos(request, statsGenderMap.values(), Cores.CORE_STATS_GENDER);
		saveStatsInfos(request, statsAgeGroupMap.values(), Cores.CORE_STATS_AGEGROUP);

		return String.format("hospitalCode: [%s], all gender&&ageGroup stats end...", new Object[] { request.getHospitalCode() });
	}

	private void saveStatsInfos(CardInfoStatsRequest request, Collection<Object> collections, String coreName) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:" + request.getHospitalCode() + " AND ");
		sb.append("areaCode:" + request.getAreaCode().replace("/", "\\/"));

		// 不能删除全部，只删除需要的部分 -- 数据本区域本医院的部分删除
		if (StringUtils.isNotBlank(request.getBeginDate())) {
			sb.append(" AND statsDate:[" + request.getBeginDate() + " TO *]");
		}

		YxwUpdateClient.addBeans(coreName, collections, true, sb.toString());
	}

	@Override
	public String statsAreaAgeGroupInfos(CardInfoStatsRequest request) {
		String beginDate = StatsConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();

		// area infos to stats
		Map<String, Object> statsAgeGroupMap = new LinkedHashMap<>();

		Map<String, Map<String, Double>> dataMap = getAreaAgeGroupStatsByAreaCode(request.getAreaCode(), "");

		while (tempDate.compareTo(endDate) < 0) {
			String tDate = tempDate.substring(0, 7);
			if (dataMap.containsKey(tDate)) {
				AgeGroupStats stats = new AgeGroupStats();
				BeanUtils.copyProperties(request, stats);
				stats.setId(PKGenerator.generateId());
				// 这个platform的问题，得以后考虑。 标准平台1健康益2其他3吧？？
				stats.setPlatform(request.getPlatform());
				stats.setHospitalCode("-");
				stats.setHospitalName("-");
				stats.setThisMonth(tempDate.substring(0, 7));

				stats.setWxAgeGroup0(dataMap.get(tDate).get("wxAgeGroup0").intValue());
				stats.setWxAgeGroup1(dataMap.get(tDate).get("wxAgeGroup1").intValue());
				stats.setWxAgeGroup2(dataMap.get(tDate).get("wxAgeGroup2").intValue());
				stats.setWxAgeGroup3(dataMap.get(tDate).get("wxAgeGroup3").intValue());
				stats.setWxAgeGroup4(dataMap.get(tDate).get("wxAgeGroup4").intValue());
				stats.setWxAgeGroup5(dataMap.get(tDate).get("wxAgeGroup5").intValue());
				stats.setWxAgeGroup6(dataMap.get(tDate).get("wxAgeGroup6").intValue());
				
				stats.setAliAgeGroup0(dataMap.get(tDate).get("aliAgeGroup0").intValue());
				stats.setAliAgeGroup1(dataMap.get(tDate).get("aliAgeGroup1").intValue());
				stats.setAliAgeGroup2(dataMap.get(tDate).get("aliAgeGroup2").intValue());
				stats.setAliAgeGroup3(dataMap.get(tDate).get("aliAgeGroup3").intValue());
				stats.setAliAgeGroup4(dataMap.get(tDate).get("aliAgeGroup4").intValue());
				stats.setAliAgeGroup5(dataMap.get(tDate).get("aliAgeGroup5").intValue());
				stats.setAliAgeGroup6(dataMap.get(tDate).get("aliAgeGroup6").intValue());

				statsAgeGroupMap.put(tDate, stats);
			}

			// 下月1号
			tempDate = DateUtils.getFirstDayNextMonth(tempDate);
		}

		// logger.info(JSON.toJSONString(statsAgeGroupMap));
		saveAreaStatsInfos(request, statsAgeGroupMap.values(), Cores.CORE_STATS_AGEGROUP);
		dataMap.clear();
		dataMap = null;

		return String.format("AreaName: [%s], all area AgeGroup stats end...", new Object[] { request.getAreaName() });
	}

	private Map<String, Map<String, Double>> getAreaAgeGroupStatsByAreaCode(String areaCode, String statsMonth) {
		Map<String, Map<String, Double>> resultMap = new LinkedHashMap<>();
		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("areaCode:" + areaCode.replace("/", "\\/") + " AND ");
		// 日期
		if (StringUtils.isNotBlank(statsMonth)) {
			sbQuery.append("thisMonth:" + statsMonth + " AND ");
		}

		// !hospitalCode:\\-1
		sbQuery.append("-hospitalCode:\\-");

		solrQuery.setQuery(sbQuery.toString());
		solrQuery.setRows(0);

		solrQuery.setGetFieldStatistics(true);
		solrQuery.addGetFieldStatistics("wxAgeGroup0");
		solrQuery.addGetFieldStatistics("wxAgeGroup1");
		solrQuery.addGetFieldStatistics("wxAgeGroup2");
		solrQuery.addGetFieldStatistics("wxAgeGroup3");
		solrQuery.addGetFieldStatistics("wxAgeGroup4");
		solrQuery.addGetFieldStatistics("wxAgeGroup5");
		solrQuery.addGetFieldStatistics("wxAgeGroup6");
		solrQuery.addGetFieldStatistics("aliAgeGroup0");
		solrQuery.addGetFieldStatistics("aliAgeGroup1");
		solrQuery.addGetFieldStatistics("aliAgeGroup2");
		solrQuery.addGetFieldStatistics("aliAgeGroup3");
		solrQuery.addGetFieldStatistics("aliAgeGroup4");
		solrQuery.addGetFieldStatistics("aliAgeGroup5");
		solrQuery.addGetFieldStatistics("aliAgeGroup6");
		solrQuery.addStatsFieldFacets(null, "thisMonth");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_AGEGROUP, solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response
					.getResponse().asMap(100);
			Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> statsFields = responseMap.get("stats")
					.get("stats_fields");
			for (Entry<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> entry1 : statsFields.entrySet()) {
				String statsField = entry1.getKey();
				Map<String, Map<String, Object>> facetField = entry1.getValue().get("facets").get("thisMonth");

				for (Entry<String, Map<String, Object>> entry2 : facetField.entrySet()) {
					String date = entry2.getKey();
					Double value = (Double) entry2.getValue().get("sum");

					if (resultMap.get(date) != null) {
						resultMap.get(date).put(statsField, value);
					} else {
						Map<String, Double> statsMap = new HashMap<>();
						statsMap.put(statsField, value);
						resultMap.put(date, statsMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取区域绑卡统计数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	private Map<String, Map<String, Double>> getAreaGenderStatsByAreaCode(String areaCode, String statsMonth) {
		Map<String, Map<String, Double>> resultMap = new LinkedHashMap<>();
		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("areaCode:" + areaCode.replace("/", "\\/") + " AND ");
		// 日期
		if (StringUtils.isNotBlank(statsMonth)) {
			sbQuery.append("thisMonth:" + statsMonth + " AND ");
		}

		// !hospitalCode:\\-1
		sbQuery.append("-hospitalCode:\\-");

		solrQuery.setQuery(sbQuery.toString());
		solrQuery.setRows(0);

		solrQuery.setGetFieldStatistics(true);
		solrQuery.addGetFieldStatistics("thisMonthWxMan");
		solrQuery.addGetFieldStatistics("thisMonthWxWoman");
		solrQuery.addGetFieldStatistics("thisMonthAliMan");
		solrQuery.addGetFieldStatistics("thisMonthAliWoman");
		solrQuery.addStatsFieldFacets(null, "thisMonth");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_GENDER, solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response
					.getResponse().asMap(100);
			Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> statsFields = responseMap.get("stats")
					.get("stats_fields");
			for (Entry<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> entry1 : statsFields.entrySet()) {
				String statsField = entry1.getKey();
				Map<String, Map<String, Object>> facetField = entry1.getValue().get("facets").get("thisMonth");

				for (Entry<String, Map<String, Object>> entry2 : facetField.entrySet()) {
					String date = entry2.getKey();
					Double value = (Double) entry2.getValue().get("sum");

					if (resultMap.get(date) != null) {
						resultMap.get(date).put(statsField, value);
					} else {
						Map<String, Double> statsMap = new HashMap<>();
						statsMap.put(statsField, value);
						resultMap.put(date, statsMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取区域绑卡统计数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	private void saveAreaStatsInfos(CardInfoStatsRequest request, Collection<Object> collections, String coreName) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:\\- AND areaCode:" + request.getAreaCode().replace("/", "\\/"));

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getStatsMonth())) {
			sb.append(" AND thisMonth:[" + request.getStatsMonth() + " TO *]");
		}

		YxwUpdateClient.addBeans(coreName, collections, true, sb.toString());
	}

	@Override
	public String statsAreaGenderInfos(CardInfoStatsRequest request) {
		String beginDate = StatsConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();

		// area infos to stats
		Map<String, Object> statsGenderMap = new LinkedHashMap<>();

		Map<String, Map<String, Double>> dataMap = getAreaGenderStatsByAreaCode(request.getAreaCode(), "");

		while (tempDate.compareTo(endDate) < 0) {
			String tDate = tempDate.substring(0, 7);
			if (dataMap.containsKey(tDate)) {
				GenderStats stats = new GenderStats();
				BeanUtils.copyProperties(request, stats);
				stats.setId(PKGenerator.generateId());
				// 这个platform的问题，得以后考虑。 标准平台1健康益2其他3吧？？
				stats.setPlatform(request.getPlatform());
				stats.setHospitalCode("-");
				stats.setHospitalName("-");
				stats.setThisMonth(tempDate.substring(0, 7));

				stats.setThisMonthWxMan(dataMap.get(tDate).get("thisMonthWxMan").intValue());
				stats.setThisMonthWxWoman(dataMap.get(tDate).get("thisMonthWxWoman").intValue());
				stats.setThisMonthAliMan(dataMap.get(tDate).get("thisMonthAliMan").intValue());
				stats.setThisMonthAliWoman(dataMap.get(tDate).get("thisMonthAliWoman").intValue());

				statsGenderMap.put(tDate, stats);
			}

			// 下月1号
			tempDate = DateUtils.getFirstDayNextMonth(tempDate);
		}

		// logger.info(JSON.toJSONString(statsGenderMap));
		saveAreaStatsInfos(request, statsGenderMap.values(), Cores.CORE_STATS_GENDER);
		dataMap.clear();
		dataMap = null;

		return String.format("AreaName: [%s], all area Gender stats end...", new Object[] { request.getAreaName() });
	}

	@Override
	public String statsAreaInfos(CardInfoStatsRequest request) {
		statsAreaAgeGroupInfos(request);
		statsAreaGenderInfos(request);
		return String.format("AreaName: [%s], all area Gender&&AgeGroup stats end...", new Object[] { request.getAreaName() });
	}

}
