package com.yxw.biz.subscribe.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.CommonParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.biz.common.StatsConstant;
import com.yxw.biz.subscribe.service.SubscribeService;
import com.yxw.client.YxwSolrClient;
import com.yxw.client.YxwUpdateClient;
import com.yxw.constants.Cores;
import com.yxw.constants.PlatformConstant;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.utils.DateUtils;
import com.yxw.vo.subscribe.SubscribeStats;
import com.yxw.vo.subscribe.SubscribeStatsRequest;
import com.yxw.vo.subscribe.SubscribeVo;

/**
 * 单个月，每次操作需要去找有没有该医院|地区的数据，有则判断是否需要修改，不需要改动则不去查库否则查库，生成数据，写入
 * 多个月的，是为一次性写入，会先清空之前的数据。直接查库，生成数据，写入 statsDate:*-01 拿这个，按顺序去查。
 * 目前统计是按月统计，只需要拿每月1号的数据。 注意计算比率时，除数为空或者为空的问题。
 */
@Service(value = "subscribeService")
public class SubscribeServiceImpl implements SubscribeService {

	private Logger logger = LoggerFactory.getLogger(SubscribeService.class);

	private YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);

	@Override
	public List<SubscribeStats> getStatsInfos(SubscribeStatsRequest request) {
		List<SubscribeStats> resultList = new ArrayList<>();
		
		// 查询条件
		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("*:*");
		// 区域
		if (StringUtils.isNotBlank(request.getAreaCode())) {
			sbQuery.append(" AND areaCode:" + request.getAreaCode().replace("/", "\\/"));
		}
		// 医院
		if (StringUtils.isNotBlank(request.getHospitalCode())) {
			sbQuery.append(" AND hospitalCode:" + request.getHospitalCode());
		}
		// 日期  作为查询的时候，beginDate 和 endDate，格式都是YYYY-MM
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
			QueryResponse response = solrClient.query(Cores.CORE_STATS_SUBSCRIBE, solrQuery);
			resultList = response.getBeans(SubscribeStats.class);
		} catch (Exception e) {
			logger.error("查询关注统计数据异常。errorMsg: {}, cause: {}.", new Object[]{e.getMessage(), e.getCause()});
		}
		
		return resultList;
	}

	@Override
	public String statsInfos(SubscribeStatsRequest request) {
		String beginDate = StatsConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();
		// endDate = "2016-10-19";

		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		Map<String, SubscribeVo> subscribes = getSubscribeMapByHospital(request.getHospitalCode(), "", request.getPlatform());
		// logger.info(JSON.toJSONString(subscribes));
		// 全部生成则直接new，如果是天天轮训则需要先去solr查一遍
		Map<String, Object> statsMap = new LinkedHashMap<>();

		while (tempDate.compareTo(endDate) < 0) {
			SubscribeVo vo = subscribes.get(tempDate);
			
			if (vo == null) {
				logger.warn("医院[{}]在[{}]没有数据。", new Object[] { request.getHospitalName().trim(), tempDate.trim() });
			}
			
			// 累计当月
			String tmpDate = tempDate.substring(0, 7);
			SubscribeStats stats = null;
			if (statsMap.containsKey(tmpDate)) {
				stats = (SubscribeStats) statsMap.get(tmpDate);
			} else {
				stats = new SubscribeStats();
				BeanUtils.copyProperties(request, stats);
				stats.setPlatform(request.getPlatform());
				stats.setId(PKGenerator.generateId());

				/*
				 * if (vo.getPlatform().intValue() ==
				 * PlatformConstant.PLATFORM_STANDARD_WECHAT_VAL ||
				 * vo.getPlatform().intValue() ==
				 * PlatformConstant.PLATFORM_HIS_WECHAT_VAL) {
				 * stats.setWxAppId(vo.getAppId()); } else {
				 * stats.setAliAppId(vo.getAppId()); }
				 */
			}

			Long thisMonthWxIncCount = (vo == null ? 0 : vo.getIncreaseCount());
			Long lastMonthWxIncCount = 0L;
			Long thisMonthWxCancelCount = (vo == null ? 0 : vo.getCancelCount());
			Long lastMonthWxCancelCount = 0L;
			Long wxCumulateCountTillThisMonth = (vo == null ? 0 : vo.getCumulateCount());
			// ！！！支付宝的目前没有接口获取。就让一直是0
			// long thisMonthAliIncCount = 0L;
			// long lastMonthAliIncCount = 0L;

			String lastMonth = DateUtils.getFirstDayOfMonth(tempDate, -1);
			if (!tempDate.equals(beginDate)) {
				SubscribeStats lastMonthStats = (SubscribeStats) statsMap.get(lastMonth.substring(0, 7));
				if (lastMonthStats != null) {
					lastMonthWxIncCount = lastMonthStats.getThisMonthWxIncCount();
					lastMonthWxCancelCount = lastMonthStats.getThisMonthWxCancelCount();
					wxCumulateCountTillThisMonth = lastMonthStats.getWxCumulateCountTillThisMonth() + thisMonthWxIncCount + stats.getThisMonthWxIncCount();
				}
			}

			// 计算数据--这里只计算微信的，所有支付宝的全写0.
			stats.setThisMonth(tempDate.substring(0, 7));
			stats.setLastMonth(lastMonth.substring(0, 7));
			// 这个字段有点问题
			stats.setWxCumulateCountTillThisMonth(wxCumulateCountTillThisMonth);
			stats.setThisMonthWxIncCount(stats.getThisMonthWxIncCount() + thisMonthWxIncCount);
			stats.setThisMonthWxCancelCount(stats.getThisMonthWxCancelCount() + thisMonthWxCancelCount);

			stats.setLastMonthWxIncCount(lastMonthWxIncCount);
			stats.setLastMonthWxCancelCount(lastMonthWxCancelCount);
			stats.setWxIncCount(stats.getThisMonthWxIncCount() - lastMonthWxIncCount);
			stats.setWxCancelCount(stats.getThisMonthWxCancelCount() - lastMonthWxCancelCount);

			if (lastMonthWxIncCount == 0) {
				stats.setWxIncRate("-");
			} else {
				stats.setWxIncRate(format.format((stats.getWxIncCount() / (double) lastMonthWxIncCount)));
			}

			if (lastMonthWxCancelCount == 0) {
				stats.setWxCancelRate("-");
			} else {
				stats.setWxCancelRate(format.format(stats.getWxCancelCount() / (double) lastMonthWxCancelCount));
			}

			statsMap.put(tmpDate, stats);

			tempDate = DateUtils.getDayForDate(DateUtils.StringToDate(tempDate), 1);
		}

		// logger.info(JSON.toJSONString(statsMap));
		saveStatsInfos(request, statsMap.values());

		return String.format("hospitalCode: [%s], all subscribe stats end...", new Object[] { request.getHospitalCode() });
	}

	/**
	 * 获取医院关注信息
	 * 
	 * @param hospitalCode
	 * @return
	 */
	private Map<String, SubscribeVo> getSubscribeMapByHospital(String hospitalCode, String sDate, int platformType) {
		Map<String, SubscribeVo> map = new HashMap<>();
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("hospitalCode:" + hospitalCode);

		if (StringUtils.isNotBlank(sDate)) {
			// 查询sDate的上月信息。 是一个区间
			String beginDate = DateUtils.getFirstDayOfLastMonth(sDate);
			String endDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(DateUtils.getFirstDayOfThisMonth(sDate)), -1);
			solrQuery.setQuery(solrQuery.getQuery() + " AND statsDate:[" + beginDate + " TO " + endDate + "]");
		}

		solrQuery.setRows(Integer.MAX_VALUE);

		try {
			String coreName = "";
			if (platformType == PlatformConstant.PLATFORM_TYPE_STD) {
				coreName = Cores.CORE_STD_SUBSCRIBE;
			} else if (platformType == PlatformConstant.PLATFORM_TYPE_HIS) {
				coreName = Cores.CORE_HIS_SUBSCRIBE;
			}
			QueryResponse response = solrClient.query(coreName, solrQuery);
			List<SubscribeVo> list = response.getBeans(SubscribeVo.class);
			for (SubscribeVo vo : list) {
				map.put(vo.getStatsDate(), vo);
			}
		} catch (Exception e) {
			logger.error("获取所有医院关注信息异常。hospitalCode: {}. sDate: {}. platformType: {}", new Object[]{hospitalCode, sDate, platformType});
			logger.error("获取所有医院关注信息异常。 errorMsg: {}. cause by: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return map;
	}

	private Map<String, Map<String, Double>> getAreaStatsByAreaCode(String areaCode, String statsMonth) {
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
		solrQuery.addGetFieldStatistics("thisMonthWxIncCount");
		solrQuery.addGetFieldStatistics("lastMonthWxIncCount");
		solrQuery.addGetFieldStatistics("thisMonthWxCancelCount");
		solrQuery.addGetFieldStatistics("lastMonthWxCancelCount");
		solrQuery.addGetFieldStatistics("wxCumulateCountTillThisMonth");
		solrQuery.addGetFieldStatistics("thisMonthAliIncCount");
		solrQuery.addGetFieldStatistics("lastMonthAliIncCount");
		solrQuery.addGetFieldStatistics("thisMonthAliCancelCount");
		solrQuery.addGetFieldStatistics("lastMonthAliCancelCount");
		solrQuery.addGetFieldStatistics("aliCumulateCountTillThisMonth");
		solrQuery.addGetFieldStatistics("wxIncCount");
		solrQuery.addGetFieldStatistics("wxCancelCount");
		solrQuery.addGetFieldStatistics("aliIncCount");
		solrQuery.addGetFieldStatistics("aliCanelCount");
		solrQuery.addStatsFieldFacets(null, "thisMonth");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_SUBSCRIBE, solrQuery);
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
			logger.error("获取区域关注统计异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	private void saveStatsInfos(SubscribeStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:" + request.getHospitalCode() + " AND ");
		sb.append("areaCode:" + request.getAreaCode().replace("/", "\\/"));

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getBeginDate())) {
			sb.append(" AND statsDate:[" + request.getBeginDate() + " TO *]");
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_SUBSCRIBE, collection, true, sb.toString());
	}

	private void saveMonthlyStatsInfo(SubscribeStatsRequest request, SubscribeStats stats) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:" + request.getHospitalCode() + " AND ");
		sb.append("thisMonth:" + DateUtils.getFirstDayOfLastMonth(request.getBeginDate()).substring(0, 7) + " AND ");
		sb.append("areaCode:" + request.getAreaCode().replace("/", "\\/"));

		YxwUpdateClient.addBean(Cores.CORE_STATS_SUBSCRIBE, stats, true, sb.toString());
	}

	private void saveAreaStatsInfos(SubscribeStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:\\- AND areaCode:" + request.getAreaCode().replace("/", "\\/"));

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getStatsMonth())) {
			sb.append(" AND thisMonth:[" + request.getStatsMonth() + " TO *]");
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_SUBSCRIBE, collection, true, sb.toString());
	}

	@Override
	public String statsInfoForMonth(SubscribeStatsRequest request) {
		// 每月的统计
		// 这个方法也就是没有进行过上月的统计。直接执行上月的统计即可
		// 该方法有一定缺陷，lastMonthStats需要有，不能为空也就是不能是第一次的统计
		String statsDate = request.getBeginDate();
		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		// 获取上上月的统计信息
		Map<String, SubscribeStats> dataMap = getStatsInfoByStatsMonth(request);
		// 获取现在需要的信息
		Map<String, SubscribeVo> voMap = getSubscribeMapByHospital(request.getHospitalCode(), request.getBeginDate(),
				request.getPlatform());
		String lastMonth = DateUtils.getFirstDayOfMonth(request.getBeginDate(), -2).substring(0, 7);
		SubscribeStats lastMonthStats = dataMap.get(lastMonth);
		SubscribeStats stats = new SubscribeStats();
		BeanUtils.copyProperties(request, stats);
		stats.setPlatform(lastMonthStats.getPlatform());
		stats.setId(PKGenerator.generateId());
		stats.setThisMonth(DateUtils.getFirstDayOfLastMonth(statsDate).substring(0, 7));
		stats.setLastMonth(lastMonth);
		stats.setWxAppId(lastMonthStats.getWxAppId());
		stats.setAliAppId(lastMonthStats.getAliAppId());
		stats.setLastMonthWxIncCount(lastMonthStats.getThisMonthWxIncCount());
		stats.setLastMonthWxCancelCount(lastMonthStats.getThisMonthWxCancelCount());

		String beginDate = DateUtils.getFirstDayOfLastMonth(statsDate);
		String endDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(DateUtils.getFirstDayOfThisMonth(statsDate)), -1);
		String tempDate = beginDate;

		long wxIncCount = 0;
		long wxCancelCount = 0;
		long wxCumulateCount = 0;

		while (tempDate.compareTo(endDate) <= 0) {
			// 一个月的统计
			if (voMap.containsKey(tempDate)) {
				wxIncCount += voMap.get(tempDate).getIncreaseCount();
				wxCancelCount += voMap.get(tempDate).getCancelCount();
				wxCumulateCount = voMap.get(tempDate).getCumulateCount();
			} else {
				logger.warn("该医院[{}]这一天[{}]没有关注数据", request.getHospitalCode(), tempDate);
			}

			tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
		}

		stats.setThisMonthWxIncCount(wxIncCount);
		stats.setThisMonthWxCancelCount(wxCancelCount);
		stats.setWxCumulateCountTillThisMonth(wxCumulateCount);

		stats.setWxIncCount(stats.getThisMonthWxIncCount() - lastMonthStats.getThisMonthWxIncCount());
		stats.setWxCancelCount(stats.getThisMonthWxCancelCount() - lastMonthStats.getThisMonthWxCancelCount());

		if (stats.getLastMonthWxCancelCount() != 0) {
			stats.setWxCancelRate(format.format(stats.getWxIncCount() / (double) stats.getLastMonthWxIncCount()));
		} else {
			stats.setWxCancelRate("-");
		}

		if (stats.getLastMonthWxIncCount() != 0) {
			stats.setWxIncRate(format.format(stats.getWxCancelCount() / (double) stats.getLastMonthWxCancelCount()));
		} else {
			stats.setWxIncRate("-");
		}

		saveMonthlyStatsInfo(request, stats);

		return String.format("hospitalCode: [%s], statsDate: [%s]. subscribe stats end...",
				new Object[] { request.getHospitalCode(), request.getBeginDate() });
	}

	private Map<String, SubscribeStats> getStatsInfoByStatsMonth(SubscribeStatsRequest request) {
		Map<String, SubscribeStats> resultMap = new HashMap<>();

		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		// thisMonth:YYYY-MM
		String lastMonth = DateUtils.getFirstDayOfMonth(request.getBeginDate(), -2).substring(0, 7);
		sbQuery.append("thisMonth:" + lastMonth);
		// hospitalCode:XXXX
		sbQuery.append(" AND hospitalCode:" + request.getHospitalCode());
		// areaCode:XXXX
		sbQuery.append(" AND areaCode:" + request.getAreaCode().replace("/", "\\/"));
		solrQuery.setQuery(sbQuery.toString());

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_SUBSCRIBE, solrQuery);
			List<SubscribeStats> resultList = response.getBeans(SubscribeStats.class);
			if (CollectionUtils.isNotEmpty(resultList)) {
				for (SubscribeStats stats : resultList) {
					resultMap.put(stats.getThisMonth(), stats);
				}
			}

		} catch (Exception e) {
			logger.error("查询单月某医院数据异常.thisMonth:[{}], hospitalCode:[{}], areaCode:[{}].",
					new Object[] { request.getStatsMonth(), request.getHospitalCode(), request.getAreaCode() });
		}

		return resultMap;
	}

	@Override
	public String statsAreaInfos(SubscribeStatsRequest request) {
		String beginDate = StatsConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();
		// endDate = "2016-10-19";

		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		Map<String, Map<String, Double>> dataMap = getAreaStatsByAreaCode(request.getAreaCode(), "");
		// logger.info(JSON.toJSONString(dataMap));

		// 全部生成则直接new，如果是天天轮训则需要先去solr查一遍
		Map<String, Object> statsMap = new LinkedHashMap<>();

		while (tempDate.compareTo(endDate) < 0) {
			String tDate = tempDate.substring(0, 7);
			if (dataMap.containsKey(tDate)) {
				SubscribeStats stats = new SubscribeStats();
				BeanUtils.copyProperties(request, stats);
				stats.setId(PKGenerator.generateId());
				// 这个platform的问题，得以后考虑。 标准平台1健康益2其他3吧？？
				stats.setPlatform(-1);
				stats.setHospitalCode("-");
				stats.setHospitalName("-");
				stats.setWxAppId("-");
				stats.setAliAppId("-");

				long thisMonthWxIncCount = dataMap.get(tDate).get("thisMonthWxIncCount").intValue();
				long lastMonthWxIncCount = dataMap.get(tDate).get("lastMonthWxIncCount").intValue();
				long thisMonthWxCancelCount = dataMap.get(tDate).get("thisMonthWxCancelCount").intValue();
				long lastMonthWxCancelCount = dataMap.get(tDate).get("lastMonthWxCancelCount").intValue();
				long wxCumulateCountTillThisMonth = dataMap.get(tDate).get("wxCumulateCountTillThisMonth").intValue();
				// ！！！支付宝的目前没有接口获取。就让一直是0
				// long thisMonthAliIncCount = 0L;
				// long lastMonthAliIncCount = 0L;

				// 计算数据--这里只计算微信的，所有支付宝的全写0.
				stats.setThisMonth(tempDate.substring(0, 7));
				stats.setLastMonth(DateUtils.getFirstDayOfMonth(tempDate, -1).substring(0, 7));
				stats.setWxCumulateCountTillThisMonth(wxCumulateCountTillThisMonth);
				stats.setThisMonthWxIncCount(thisMonthWxIncCount);
				stats.setThisMonthWxCancelCount(thisMonthWxCancelCount);
				stats.setLastMonthWxIncCount(lastMonthWxIncCount);
				stats.setLastMonthWxCancelCount(lastMonthWxCancelCount);
				stats.setWxIncCount(thisMonthWxIncCount - lastMonthWxIncCount);
				stats.setWxCancelCount(thisMonthWxCancelCount - lastMonthWxCancelCount);

				if (lastMonthWxIncCount == 0) {
					stats.setWxIncRate("-");
				} else {
					stats.setWxIncRate(format.format(stats.getWxIncCount() / (double) lastMonthWxIncCount));
				}

				if (lastMonthWxCancelCount == 0) {
					stats.setWxCancelRate("-");
				} else {
					stats.setWxCancelRate(format.format(stats.getWxCancelCount() / (double) lastMonthWxCancelCount));
				}

				statsMap.put(tempDate, stats);
			}

			// 下月1号
			tempDate = DateUtils.getFirstDayNextMonth(tempDate);
		}

		// logger.info(JSON.toJSONString(statsMap));
		saveAreaStatsInfos(request, statsMap.values());

		return String.format("AreaName: [%s], all subscribe stats end...", new Object[] { request.getAreaName() });
	}

	@Override
	public String statsAreaInfoForMonth(SubscribeStatsRequest request) {
		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		String lastMonth = DateUtils.getFirstDayOfMonth(request.getBeginDate(), -1).substring(0, 7);
		Map<String, Map<String, Double>> dataMap = getAreaStatsByAreaCode(request.getAreaCode(), lastMonth);

		SubscribeStats stats = new SubscribeStats();
		BeanUtils.copyProperties(request, stats);
		stats.setId(PKGenerator.generateId());
		// 这个platform的问题，得以后考虑。 标准平台1健康益2其他3吧？？
		stats.setPlatform(-1);
		stats.setHospitalCode("-");
		stats.setHospitalName("-");
		stats.setWxAppId("-");
		stats.setAliAppId("-");

		long thisMonthWxIncCount = dataMap.get(lastMonth).get("thisMonthWxIncCount").intValue();
		long lastMonthWxIncCount = dataMap.get(lastMonth).get("lastMonthWxIncCount").intValue();
		long thisMonthWxCancelCount = dataMap.get(lastMonth).get("thisMonthWxCancelCount").intValue();
		long lastMonthWxCancelCount = dataMap.get(lastMonth).get("lastMonthWxCancelCount").intValue();
		long wxCumulateCountTillThisMonth = dataMap.get(lastMonth).get("wxCumulateCountTillThisMonth").intValue();
		// ！！！支付宝的目前没有接口获取。就让一直是0
		// long thisMonthAliIncCount = 0L;
		// long lastMonthAliIncCount = 0L;

		// 计算数据--这里只计算微信的，所有支付宝的全写0.
		stats.setThisMonth(lastMonth);
		stats.setLastMonth(DateUtils.getFirstDayOfMonth(request.getBeginDate(), -2).substring(0, 7));
		stats.setWxCumulateCountTillThisMonth(wxCumulateCountTillThisMonth);
		stats.setThisMonthWxIncCount(thisMonthWxIncCount);
		stats.setThisMonthWxCancelCount(thisMonthWxCancelCount);
		stats.setLastMonthWxIncCount(lastMonthWxIncCount);
		stats.setLastMonthWxCancelCount(lastMonthWxCancelCount);
		stats.setWxIncCount(thisMonthWxIncCount - lastMonthWxIncCount);
		stats.setWxCancelCount(thisMonthWxCancelCount - lastMonthWxCancelCount);

		if (lastMonthWxIncCount == 0) {
			stats.setWxIncRate("-");
		} else {
			stats.setWxIncRate(format.format(stats.getWxIncCount() / (double) lastMonthWxIncCount));
		}

		if (lastMonthWxCancelCount == 0) {
			stats.setWxCancelRate("-");
		} else {
			stats.setWxCancelRate(format.format(stats.getWxCancelCount() / (double) lastMonthWxCancelCount));
		}

		Map<String, Object> map = new HashMap<>();
		map.put(lastMonth, stats);
		saveAreaStatsInfos(request, map.values());

		return String.format("area subscribe stats end...");
	}

}
