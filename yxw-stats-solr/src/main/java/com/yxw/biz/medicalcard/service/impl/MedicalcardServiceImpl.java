package com.yxw.biz.medicalcard.service.impl;

import java.text.NumberFormat;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.biz.common.StatsConstant;
import com.yxw.biz.medicalcard.service.MedicalcardService;
import com.yxw.client.YxwSolrClient;
import com.yxw.client.YxwUpdateClient;
import com.yxw.constants.Cores;
import com.yxw.constants.PlatformConstant;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.outside.vo.YxwResponse;
import com.yxw.utils.DateUtils;
import com.yxw.vo.medicalcard.CardStats;
import com.yxw.vo.medicalcard.CardStatsRequest;
import com.yxw.vo.medicalcard.CardVo;

@Service(value = "medicalcardService")
public class MedicalcardServiceImpl implements MedicalcardService {

	private Logger logger = LoggerFactory.getLogger(MedicalcardService.class);

	private YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);

	@Override
	public YxwResponse getStatsInfos(CardStatsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String statsInfos(CardStatsRequest request) {
		String beginDate = StatsConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();
		// endDate = "2016-10-19";

		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		Map<String, CardVo> cardVos = getCardInfosByHospital(request.getHospitalCode(), "", request.getPlatform());
		// 全部生成则直接new，如果是天天轮训则需要先去solr查一遍
		Map<String, Object> statsMap = new LinkedHashMap<>();
		Long wxCumulateCount = 0L;
		Long aliCumulateCount = 0L;

		while (tempDate.compareTo(endDate) < 0) {
			CardVo vo = cardVos.get(tempDate);

			if (vo == null) {
				logger.warn("医院[{}]在[{}]没有数据。", new Object[] { request.getHospitalName().trim(), tempDate.trim() });
			}

			// 累计当月
			String tmpDate = tempDate.substring(0, 7);
			CardStats stats = null;
			if (statsMap.containsKey(tmpDate)) {
				stats = (CardStats) statsMap.get(tmpDate);
			} else {
				stats = new CardStats();
				BeanUtils.copyProperties(request, stats);
				stats.setPlatform(request.getPlatform());
				stats.setId(PKGenerator.generateId());
			}

			Long thisMonthWxIncCount = vo == null ? 0 : vo.getWechatCount();
			Long thisMonthAliIncCount = vo == null ? 0 : vo.getAlipayCount();

			// 计算累计
			wxCumulateCount += thisMonthWxIncCount;
			aliCumulateCount += thisMonthAliIncCount;

			String lastMonth = DateUtils.getFirstDayOfMonth(tempDate, -1);

			// 计算数据--这里只计算微信的，所有支付宝的全写0.
			stats.setThisMonth(tempDate.substring(0, 7));
			stats.setLastMonth(lastMonth.substring(0, 7));
			// 这个字段计算累积。需要从之前的数据中获取
			stats.setWxCumulateCountTillThisMonth(wxCumulateCount);
			stats.setAliCumulateCountTillThisMonth(aliCumulateCount);
			stats.setThisMonthWxIncCount(stats.getThisMonthWxIncCount() + thisMonthWxIncCount);
			stats.setThisMonthAliIncCount(stats.getThisMonthAliIncCount() + thisMonthAliIncCount);

			statsMap.put(tmpDate, stats);
			tempDate = DateUtils.getDayForDate(DateUtils.StringToDate(tempDate), 1);
		}

		// logger.info(JSON.toJSONString(statsMap));
		saveStatsInfos(request, statsMap.values());

		return String.format("hospitalCode: [%s], all card stats end...", new Object[] { request.getHospitalCode() });
	}

	/**
	 * 获取医院绑卡信息
	 * 
	 * @param hospitalCode
	 * @return
	 */
	private Map<String, CardVo> getCardInfosByHospital(String hospitalCode, String sDate, int platformType) {
		Map<String, CardVo> map = new HashMap<>();
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
				coreName = Cores.CORE_STD_CARD;
			} else if (platformType == PlatformConstant.PLATFORM_TYPE_HIS) {
				coreName = Cores.CORE_HIS_CARD;
			}
			QueryResponse response = solrClient.query(coreName, solrQuery);
			List<CardVo> list = response.getBeans(CardVo.class);
			for (CardVo vo : list) {
				String statsDate = vo.getStatsDate();
				if (!map.containsKey(statsDate)) {
					map.put(statsDate, vo);
				} else {
					CardVo tempVo = map.get(statsDate);
					tempVo.combineEntity(vo);
					map.put(statsDate, tempVo);
				}
			}

			// 如果他妈比是广中药，狗日的跨两个平台，需要狗日对待
			if (hospitalCode.equals("gxykdxdyfsyy") || hospitalCode.equals("gzsdyrmyy")) {
				QueryResponse tempResponse = solrClient.query(Cores.CORE_STD_CARD, solrQuery);

				// 考虑到有分院的情况，一个日期下同一医院可能有多条信息，需要分别对项进行添加
				List<CardVo> tempList = tempResponse.getBeans(CardVo.class);
				for (CardVo vo : tempList) {
					String statsDate = vo.getStatsDate();
					if (!map.containsKey(statsDate)) {
						map.put(statsDate, vo);
					} else {
						CardVo tempVo = map.get(statsDate);
						tempVo.combineEntity(vo);
						map.put(statsDate, tempVo);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取所有医院绑卡信息异常。 errorMsg: {}. cause by: {}.", new Object[] { e.getMessage(), e.getCause() });
			logger.error("异常-----》 hospitalCode: {}. sDate: {}. platformType: {}.", new Object[]{hospitalCode, sDate, platformType});
		}

		return map;
	}

	private void saveStatsInfos(CardStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:" + request.getHospitalCode() + " AND ");
		sb.append("areaCode:" + request.getAreaCode().replace("/", "\\/"));

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getBeginDate())) {
			sb.append(" AND statsDate:[" + request.getBeginDate() + " TO *]");
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_CARD, collection, true, sb.toString());
	}

	@Override
	@Deprecated
	public String statsInfoForMonth(CardStatsRequest request) {
		// 每月的统计
		// 这个方法也就是没有进行过上月的统计。直接执行上月的统计即可
		// 该方法有一定缺陷，lastMonthStats需要有，不能为空也就是不能是第一次的统计
		String statsDate = request.getBeginDate();
		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		// 获取上上月的统计信息
		Map<String, CardStats> dataMap = getStatsInfoByStatsMonth(request);
		// 获取现在需要的信息
		Map<String, CardVo> voMap = getCardInfosByHospital(request.getHospitalCode(), request.getBeginDate(), request.getPlatform());
		String lastMonth = DateUtils.getFirstDayOfMonth(request.getBeginDate(), -2).substring(0, 7);
		CardStats lastMonthStats = dataMap.get(lastMonth);
		CardStats stats = new CardStats();
		BeanUtils.copyProperties(request, stats);
		stats.setPlatform(lastMonthStats.getPlatform());
		stats.setId(PKGenerator.generateId());
		stats.setThisMonth(DateUtils.getFirstDayOfLastMonth(statsDate).substring(0, 7));
		stats.setLastMonth(lastMonth);

		// 累计设置
		long wxCount = 0;
		long aliCount = 0;

		String beginDate = DateUtils.getFirstDayOfLastMonth(statsDate);
		String endDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(DateUtils.getFirstDayOfThisMonth(statsDate)), -1);
		String tempDate = beginDate;

		while (tempDate.compareTo(endDate) <= 0) {
			// 一个月的统计
			if (voMap.containsKey(tempDate)) {
				wxCount += voMap.get(tempDate) == null ? 0 : voMap.get(tempDate).getWechatCount();
				aliCount += voMap.get(tempDate) == null ? 0 : voMap.get(tempDate).getAlipayCount();
			} else {
				logger.warn("该医院[{}]这一天[{}]没有关注数据", request.getHospitalCode(), tempDate);
			}

			tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
		}

		stats.setThisMonthWxIncCount(wxCount);
		stats.setThisMonthAliIncCount(aliCount);
		stats.setWxCumulateCountTillThisMonth(lastMonthStats.getWxCumulateCountTillThisMonth() + wxCount);
		stats.setAliCumulateCountTillThisMonth(lastMonthStats.getAliCumulateCountTillThisMonth() + aliCount);

		saveMonthlyStatsInfo(request, stats);

		return String.format("hospitalCode: [%s], statsDate: [%s]. cards stats end...",
				new Object[] { request.getHospitalCode(), request.getBeginDate() });
	}

	private void saveMonthlyStatsInfo(CardStatsRequest request, CardStats stats) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:" + request.getHospitalCode() + " AND ");
		sb.append("thisMonth:" + DateUtils.getFirstDayOfLastMonth(request.getBeginDate()).substring(0, 7) + " AND ");
		sb.append("areaCode:" + request.getAreaCode().replace("/", "\\/"));

		YxwUpdateClient.addBean(Cores.CORE_STATS_CARD, stats, true, sb.toString());
	}

	private Map<String, CardStats> getStatsInfoByStatsMonth(CardStatsRequest request) {
		Map<String, CardStats> resultMap = new HashMap<>();

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
			QueryResponse response = solrClient.query(Cores.CORE_STATS_CARD, solrQuery);
			List<CardStats> resultList = response.getBeans(CardStats.class);
			if (CollectionUtils.isNotEmpty(resultList)) {
				for (CardStats stats : resultList) {
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
	public String statsAreaInfos(CardStatsRequest request) {
		String beginDate = StatsConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();
		// endDate = "2016-10-19";

		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		Map<String, Map<String, Double>> dataMap = getAreaStatsByAreaCode(request.getAreaCode(), "");

		Map<String, Object> statsMap = new LinkedHashMap<>();

		while (tempDate.compareTo(endDate) < 0) {
			String tDate = tempDate.substring(0, 7);
			if (dataMap.containsKey(tDate)) {
				CardStats stats = new CardStats();
				BeanUtils.copyProperties(request, stats);
				stats.setId(PKGenerator.generateId());
				// 这个platform的问题，得以后考虑。 标准平台1健康益2其他3吧？？
				stats.setPlatform(request.getPlatform());
				stats.setHospitalCode("-");
				stats.setHospitalName("-");

				long thisMonthWxIncCount = dataMap.get(tDate).get("thisMonthWxIncCount").intValue();
				long wxCumulateCountTillThisMonth = dataMap.get(tDate).get("wxCumulateCountTillThisMonth").intValue();
				long thisMonthAliIncCount = dataMap.get(tDate).get("thisMonthAliIncCount").intValue();
				long aliCumulateCountTillThisMonth = dataMap.get(tDate).get("aliCumulateCountTillThisMonth").intValue();

				// 计算数据--这里只计算微信的，所有支付宝的全写0.
				stats.setThisMonth(tempDate.substring(0, 7));
				stats.setLastMonth(DateUtils.getFirstDayOfMonth(tempDate, -1).substring(0, 7));

				stats.setThisMonthWxIncCount(thisMonthWxIncCount);
				stats.setWxCumulateCountTillThisMonth(wxCumulateCountTillThisMonth);
				stats.setThisMonthAliIncCount(thisMonthAliIncCount);
				stats.setAliCumulateCountTillThisMonth(aliCumulateCountTillThisMonth);

				statsMap.put(tempDate, stats);
			}

			// 下月1号
			tempDate = DateUtils.getFirstDayNextMonth(tempDate);
		}

		// logger.info(JSON.toJSONString(statsMap));
		saveAreaStatsInfos(request, statsMap.values());

		return String.format("AreaName: [%s], all medicalcard stats end...", new Object[] { request.getAreaName() });
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
		solrQuery.addGetFieldStatistics("wxCumulateCountTillThisMonth");
		solrQuery.addGetFieldStatistics("thisMonthAliIncCount");
		solrQuery.addGetFieldStatistics("aliCumulateCountTillThisMonth");
		solrQuery.addStatsFieldFacets(null, "thisMonth");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_CARD, solrQuery);
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

	private void saveAreaStatsInfos(CardStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:\\- AND areaCode:" + request.getAreaCode().replace("/", "\\/"));

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getStatsMonth())) {
			sb.append(" AND thisMonth:[" + request.getStatsMonth() + " TO *]");
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_CARD, collection, true, sb.toString());
	}

	@Override
	@Deprecated
	public String statsAreaInfoForMonth(CardStatsRequest request) {
		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		String lastMonth = DateUtils.getFirstDayOfMonth(request.getBeginDate(), -1).substring(0, 7);
		Map<String, Map<String, Double>> dataMap = getAreaStatsByAreaCode(request.getAreaCode(), lastMonth);

		CardStats stats = new CardStats();
		BeanUtils.copyProperties(request, stats);
		stats.setId(PKGenerator.generateId());
		// 这个platform的问题，得以后考虑。 标准平台1健康益2其他3吧？？
		stats.setPlatform(request.getPlatform());
		stats.setHospitalCode("-");
		stats.setHospitalName("-");
		stats.setThisMonth(lastMonth);
		stats.setLastMonth(DateUtils.getFirstDayOfMonth(request.getBeginDate(), -2).substring(0, 7));

		long thisMonthWxIncCount = dataMap.get(lastMonth).get("thisMonthWxIncCount").intValue();
		long wxCumulateCountTillThisMonth = dataMap.get(lastMonth).get("wxCumulateCountTillThisMonth").intValue();
		long thisMonthAliIncCount = dataMap.get(lastMonth).get("thisMonthAliIncCount").intValue();
		long aliCumulateCountTillThisMonth = dataMap.get(lastMonth).get("aliCumulateCountTillThisMonth").intValue();

		// 计算数据
		stats.setWxCumulateCountTillThisMonth(wxCumulateCountTillThisMonth);
		stats.setThisMonthWxIncCount(thisMonthWxIncCount);
		stats.setThisMonthAliIncCount(thisMonthAliIncCount);
		stats.setAliCumulateCountTillThisMonth(aliCumulateCountTillThisMonth);

		Map<String, Object> map = new HashMap<>();
		map.put(lastMonth, stats);
		saveAreaStatsInfos(request, map.values());

		return String.format("area subscribe stats end...");
	}

}
