package com.yxw.biz.attribution.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.CommonParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.biz.attribution.service.AttributionService;
import com.yxw.biz.common.StatsConstant;
import com.yxw.client.YxwSolrClient;
import com.yxw.client.YxwUpdateClient;
import com.yxw.constants.Cores;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.utils.DateUtils;
import com.yxw.vo.attribution.AttributionStats;
import com.yxw.vo.attribution.AttributionStatsRequest;
import com.yxw.vo.attribution.AttributionVo;
import com.yxw.vo.attribution.CityVo;

@Service(value = "attributionService")
public class AttributionServiceImpl implements AttributionService {

	private Logger logger = LoggerFactory.getLogger(AttributionService.class);

	private YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);

	@Override
	public List<AttributionStats> getStatsInfos(AttributionStatsRequest request) {
		List<AttributionStats> resultList = new ArrayList<>();
		// 查询条件
		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("*:*");
		// 省
		if (StringUtils.isNotBlank(request.getProvince())) {
			sbQuery.append(" AND province:" + request.getProvince().replace("/", "\\/"));
		}
		// 市
		if (StringUtils.isNotBlank(request.getCity())) {
			sbQuery.append(" AND city:" + request.getCity());
		}
		// 区域
		if (StringUtils.isNotBlank(request.getAreaName())) {
			sbQuery.append(" AND areaName:" + request.getAreaName());
		}
		// 日期 作为查询的时候，beginDate 和 endDate，格式都是YYYY-MM
		if (StringUtils.isNotBlank(request.getBeginDate())) {
			if (StringUtils.isNotBlank(request.getEndDate())) {
				sbQuery.append(" AND thisMonth:[" + request.getBeginDate() + " TO " + request.getEndDate() + "]");
			} else {
				sbQuery.append(" AND thisMonth:[" + request.getBeginDate() + " TO *]");
			}
		} else {
			if (StringUtils.isNotBlank(request.getEndDate())) {
				sbQuery.append(" AND thisMonth:[* TO " + request.getEndDate() + "]");
			}
		}

		solrQuery.set(CommonParams.Q, sbQuery.toString());
		// 分页查询
		solrQuery.set(CommonParams.START, request.getPageSize() * (request.getPageIndex() - 1));
		solrQuery.set(CommonParams.ROWS, request.getPageSize());
		// 倒序
		solrQuery.set(CommonParams.SORT, "thisMonth desc");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_ATTRIBUTION, solrQuery);
			resultList = response.getBeans(AttributionStats.class);
		} catch (Exception e) {
			logger.error("查询关注统计数据异常。errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultList;
	}

	@Override
	public String statsInfos(AttributionStatsRequest request) {
		String beginDate = StatsConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();
		// endDate = "2016-10-19";

		Map<String, AttributionVo> attributions = getAttributionMapByHospital(request.getAreaName(), "");
		// logger.info(JSON.toJSONString(subscribes));
		// 全部生成则直接new，如果是天天轮训则需要先去solr查一遍
		Map<String, Object> statsMap = new LinkedHashMap<>();
		Long cumulateCount = 0L;

		while (tempDate.compareTo(endDate) < 0) {
			AttributionVo vo = attributions.get(tempDate);

			if (vo == null) {
				// logger.warn("区域[{}]在[{}]没有数据。", new Object[] { request.getAreaName().trim(), tempDate.trim() });
			}

			// 累计当月
			String tmpDate = tempDate.substring(0, 7);
			AttributionStats stats = null;
			if (statsMap.containsKey(tmpDate)) {
				stats = (AttributionStats) statsMap.get(tmpDate);
			} else {
				stats = new AttributionStats();
				BeanUtils.copyProperties(request, stats);
				stats.setId(PKGenerator.generateId());
				stats.setThisMonth(tempDate.substring(0, 7));
				stats.setAreaName(request.getAreaName());
				stats.setCity(request.getCity());
				stats.setProvince(request.getProvince());
			}

			Long thisMonthIncCount = 0L;

			if (vo != null) {
				thisMonthIncCount = vo.getCount();
			}

			cumulateCount += thisMonthIncCount;
			stats.setThisMonthIncCount(stats.getThisMonthIncCount() + thisMonthIncCount);
			stats.setCumulateCount(cumulateCount);
			stats.setLatitude(request.getLatitude());
			stats.setLongitude(request.getLongitude());

			statsMap.put(tmpDate, stats);

			tempDate = DateUtils.getDayForDate(DateUtils.StringToDate(tempDate), 1);
		}

		saveStatsInfos(request, statsMap.values());

		return String.format("areaName: [%s], all attribution stats end...", new Object[] { request.getAreaName() });
	}

	private Map<String, AttributionVo> getAttributionMapByHospital(String areaName, String sDate) {
		Map<String, AttributionVo> map = new HashMap<>();
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("areaName:" + areaName);

		if (StringUtils.isNotBlank(sDate)) {
			// 查询sDate的上月信息。 是一个区间
			String beginDate = DateUtils.getFirstDayOfLastMonth(sDate);
			String endDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(DateUtils.getFirstDayOfThisMonth(sDate)), -1);
			solrQuery.setQuery(solrQuery.getQuery() + " AND statsDate:[" + beginDate + " TO " + endDate + "]");
		}

		solrQuery.setRows(Integer.MAX_VALUE);

		try {
			QueryResponse response = solrClient.query(Cores.CORE_ATTRIBUTION, solrQuery);
			List<AttributionVo> list = response.getBeans(AttributionVo.class);
			for (AttributionVo vo : list) {
				map.put(vo.getStatsDate(), vo);
			}
		} catch (Exception e) {
			logger.error("获取所有医院关注信息异常。 errorMsg: {}. cause by: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return map;
	}

	private void saveStatsInfos(AttributionStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("areaName:" + request.getAreaName().replace("/", "\\/"));

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getBeginDate())) {
			sb.append(" AND statsDate:[" + request.getBeginDate() + " TO *]");
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_ATTRIBUTION, collection, true, sb.toString());
	}

	@Override
	public String statsInfoForMonth(AttributionStatsRequest request) {
		// 每月的统计
		// 这个方法也就是没有进行过上月的统计。直接执行上月的统计即可
		// 该方法有一定缺陷，lastMonthStats需要有，不能为空也就是不能是第一次的统计
		String statsDate = request.getBeginDate();

		// 获取上上月的统计信息
		Map<String, AttributionStats> dataMap = getStatsInfoByStatsMonth(request);
		// 获取现在需要的信息
		Map<String, AttributionVo> voMap = getAttributionMapByHospital(request.getAreaName(), request.getBeginDate());
		String lastMonth = DateUtils.getFirstDayOfMonth(request.getBeginDate(), -2).substring(0, 7);
		AttributionStats lastMonthStats = dataMap.get(lastMonth);
		AttributionStats stats = new AttributionStats();
		BeanUtils.copyProperties(request, stats);
		stats.setId(PKGenerator.generateId());
		stats.setThisMonth(DateUtils.getFirstDayOfLastMonth(statsDate).substring(0, 7));

		String beginDate = DateUtils.getFirstDayOfLastMonth(statsDate);
		String endDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(DateUtils.getFirstDayOfThisMonth(statsDate)), -1);
		String tempDate = beginDate;

		long monthIncCount = 0;

		while (tempDate.compareTo(endDate) <= 0) {
			// 一个月的统计
			if (voMap.containsKey(tempDate)) {
				monthIncCount += voMap.get(tempDate).getCount();
			} else {
				// logger.warn("该区域[{}]这一天[{}]没有关注数据", request.getAreaName(), tempDate);
			}

			tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
		}
		
		stats.setThisMonthIncCount(monthIncCount);
		if (lastMonthStats == null) {
			stats.setCumulateCount(monthIncCount);
		} else {
			stats.setCumulateCount(lastMonthStats.getCumulateCount() + monthIncCount);
		}
		

		saveMonthlyStatsInfo(request, stats);

		return String.format("areaName: [%s], statsDate: [%s]. attribution stats end...",
				new Object[] { request.getAreaName(), request.getBeginDate() });
	}
	
	private void saveMonthlyStatsInfo(AttributionStatsRequest request, AttributionStats stats) {
		StringBuffer sb = new StringBuffer();
		sb.append("thisMonth:" + DateUtils.getFirstDayOfLastMonth(request.getBeginDate()).substring(0, 7) + " AND ");
		sb.append("areaName:" + request.getAreaName());

		YxwUpdateClient.addBean(Cores.CORE_STATS_ATTRIBUTION, stats, true, sb.toString());
	}

	private Map<String, AttributionStats> getStatsInfoByStatsMonth(AttributionStatsRequest request) {
		Map<String, AttributionStats> resultMap = new HashMap<>();

		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		// thisMonth:YYYY-MM
		String lastMonth = DateUtils.getFirstDayOfMonth(request.getBeginDate(), -2).substring(0, 7);
		sbQuery.append("thisMonth:" + lastMonth);
		// areaCode:XXXX
		sbQuery.append(" AND areaName:" + request.getAreaName());
		solrQuery.setQuery(sbQuery.toString());

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_ATTRIBUTION, solrQuery);
			List<AttributionStats> resultList = response.getBeans(AttributionStats.class);
			if (CollectionUtils.isNotEmpty(resultList)) {
				for (AttributionStats stats : resultList) {
					resultMap.put(stats.getThisMonth(), stats);
				}
			}

		} catch (Exception e) {
			logger.error("查询单月某区域数据异常.thisMonth:[{}],  areaName:[{}].", new Object[] { request.getStatsMonth(), request.getAreaName() });
		}

		return resultMap;
	}

	@Override
	public List<CityVo> getCities() {
		List<CityVo> results = new ArrayList<>();
		try {
			// q=*:*&rows=655333&wt=json&indent=true&fl=areaName,province,city&group=true&group.field=areaName&group.facet=true
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("longitude:* AND latitude:*");
			solrQuery.setRows(Integer.MAX_VALUE);
			solrQuery.setFields("province,city,longitude,latitude");

			// 分组条件
			solrQuery.set("group", true);
			solrQuery.set("group.field", "areaName");
			solrQuery.set("group.ngroups", true); // 会在结果返回数据的行数

			QueryResponse response = solrClient.query(Cores.CORE_ATTRIBUTION, solrQuery);
			GroupResponse groupResponse = response.getGroupResponse();
			if (CollectionUtils.isNotEmpty(groupResponse.getValues())) {
				List<Group> groups = groupResponse.getValues().get(0).getValues();
				if (CollectionUtils.isNotEmpty(groups)) {
					for (Group group : groups) {
						String groupValue = group.getGroupValue(); // 返回的是AreaName
						SolrDocument doc = group.getResult().get(0);
						CityVo cityVo = new CityVo();
						cityVo.setAreaName(groupValue);
						cityVo.setProvince((String) doc.getFieldValue("province"));
						cityVo.setCity((String) doc.getFieldValue("city"));
						cityVo.setLongitude((String) doc.getFieldValue("longitude"));
						cityVo.setLatitude((String) doc.getFieldValue("latitude"));
						results.add(cityVo);
					}
				} else {
					logger.error("返回的groups为空。");
				}
			} else {
				logger.error("groupResponse为空。");
			}
		} catch (Exception e) {
			logger.error("获取城市列表异常. errorMsg: {}. cause: {}.", e.getMessage(), e.getCause());
		}

		return results;
	}

}
