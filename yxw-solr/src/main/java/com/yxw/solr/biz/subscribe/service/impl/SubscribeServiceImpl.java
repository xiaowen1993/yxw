package com.yxw.solr.biz.subscribe.service.impl;

import java.io.IOException;
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
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.subscribe.SubscribeConstant;
import com.yxw.solr.biz.subscribe.service.SubscribeService;
import com.yxw.solr.client.YxwSolrClient;
import com.yxw.solr.client.YxwUpdateClient;
import com.yxw.solr.constants.Cores;
import com.yxw.solr.constants.PlatformConstant;
import com.yxw.solr.constants.SolrConstant;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.subscribe.SubscribeStats;
import com.yxw.solr.vo.subscribe.SubscribeStatsRequest;
import com.yxw.solr.vo.subscribe.SubscribeVo;
import com.yxw.utils.DateUtils;

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
	public YxwResponse getStatsInfos(SubscribeStatsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String statsInfos(SubscribeStatsRequest request) {
		String beginDate = SubscribeConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();

		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		Map<String, SubscribeVo> subscribes = getSubscribeMapByHospital(request.getHospitalCode(), "");

		// 全部生成则直接new，如果是天天轮训则需要先去solr查一遍
		Map<String, Object> statsMap = new LinkedHashMap<>();

		while (tempDate.compareTo(endDate) <= 0) {
			SubscribeVo vo = subscribes.get(tempDate);
			if (vo != null) {
				SubscribeStats stats = new SubscribeStats();
				BeanUtils.copyProperties(request, stats);
				stats.setId(PKGenerator.generateId());
				// 这个platform的问题，得以后考虑。
				stats.setPlatform(1);

				if (vo.getPlatform().intValue() == PlatformConstant.PLATFORM_STANDARD_WECHAT_VAL) {
					stats.setWxAppId(vo.getAppId());
				} else {
					stats.setAliAppId(vo.getAppId());
				}

				Double thisMonthWxIncCount = vo.getIncreaseCount();
				Double lastMonthWxIncCount = (double) 0;
				Double thisMonthWxCancelCount = vo.getCancelCount();
				Double lastMonthWxCancelCount = (double) 0;
				Double wxCumulateCountTillThisMonth = vo.getCumulateCount();
				// ！！！支付宝的目前没有接口获取。就让一直是0
				// long thisMonthAliIncCount = 0L;
				// long lastMonthAliIncCount = 0L;

				String lastMonth = DateUtils.getFirstDayOfMonth(tempDate, -1);
				if (!tempDate.equals(beginDate)) {
					SubscribeVo lastMonthVo = subscribes.get(lastMonth);
					if (lastMonthVo != null) {
						lastMonthWxIncCount = lastMonthVo.getIncreaseCount();
						lastMonthWxCancelCount = lastMonthVo.getCancelCount();
					}
				}

				// 计算数据--这里只计算微信的，所有支付宝的全写0.
				stats.setThisMonth(tempDate.substring(0, 7));
				stats.setLastMonth(lastMonth.substring(0, 7));
				stats.setWxCumulateCountTillThisMonth(wxCumulateCountTillThisMonth.intValue());
				stats.setThisMonthWxIncCount(thisMonthWxIncCount.intValue());
				stats.setThisMonthWxCancelCount(thisMonthWxCancelCount.intValue());
				stats.setLastMonthWxIncCount(lastMonthWxIncCount.intValue());
				stats.setLastMonthWxCancelCount(lastMonthWxCancelCount.intValue());
				stats.setWxIncCount(thisMonthWxIncCount.intValue() - lastMonthWxIncCount.intValue());
				stats.setWxCancelCount(thisMonthWxCancelCount.intValue() - lastMonthWxCancelCount.intValue());

				if (lastMonthWxIncCount == 0) {
					stats.setWxIncRate("-");
				} else {
					stats.setWxIncRate(format.format(((thisMonthWxIncCount - lastMonthWxIncCount) / lastMonthWxIncCount)));
				}

				if (lastMonthWxCancelCount == 0) {
					stats.setWxCancelRate("-");
				} else {
					stats.setWxCancelRate(format.format((thisMonthWxCancelCount - lastMonthWxCancelCount) / lastMonthWxCancelCount));
				}

				statsMap.put(tempDate, stats);
			} else {
				logger.warn("医院[{}]在[{}]没有数据。", new Object[] { request.getHospitalName().trim(), tempDate.trim() });
			}

			// 下月1号
			tempDate = DateUtils.getFirstDayNextMonth(tempDate);
		}

		// logger.error(JSON.toJSONString(statsMap));
		saveStatsInfos(request, statsMap.values());

		return String.format("hospitalCode: [%s], all subscribe stats end...", new Object[] { request.getHospitalCode() });
	}

	/**
	 * 获取医院关注信息
	 * 
	 * @param hospitalCode
	 * @return
	 */
	private Map<String, SubscribeVo> getSubscribeMapByHospital(String hospitalCode, String sDate) {
		/**
		 * q=statsDate%3A*-01+AND+hospitalCode%3Azsdxsyxjnyy sort=statsDate+ASC
		 * group=true&group.field=statsDate
		 */
		Map<String, SubscribeVo> map = new HashMap<>();
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("statsDate".concat(SolrConstant.COLON).concat("*-01").concat(SolrConstant.AND).concat("hospitalCode")
				.concat(SolrConstant.COLON).concat(hospitalCode));

		if (StringUtils.isNotBlank(sDate)) {
			solrQuery.setQuery(solrQuery.getQuery().concat(SolrConstant.AND).concat("statsDate").concat(SolrConstant.COLON).concat(sDate));
		}

		solrQuery.setRows(Integer.MAX_VALUE);
		solrQuery.set("group", true);
		solrQuery.set("group.field", "statsDate");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STD_SUBSCRIBE, solrQuery);
			GroupResponse groupResponse = response.getGroupResponse();
			List<GroupCommand> commands = groupResponse.getValues();
			if (CollectionUtils.isNotEmpty(commands)) {
				for (GroupCommand command : commands) {
					if (CollectionUtils.isNotEmpty(command.getValues())) {
						List<Group> groups = command.getValues();
						for (Group group : groups) {
							String statsDate = group.getGroupValue();
							if (CollectionUtils.isNotEmpty(group.getResult())) {
								SubscribeVo vo = new SubscribeVo();
								SolrDocument document = group.getResult().get(0);
								vo.setId((String) document.getFieldValue("id"));
								vo.setCancelCount((double) ((Long) document.getFieldValue("cancelCount")));
								vo.setCumulateCount((double) ((Long) document.getFieldValue("cumulateCount")));
								vo.setHospitalCode((String) document.getFieldValue("hospitalCode"));
								vo.setHospitalId((String) document.getFieldValue("hospitalId"));
								vo.setHospitalName((String) document.getFieldValue("hospitalName"));
								vo.setIncreaseCount((double) ((Long) document.getFieldValue("increaseCount")));
								vo.setPlatform((Integer) document.getFieldValue("platform"));
								vo.setStatsDate((String) document.getFieldValue("statsDate"));
								vo.setAppId((String) document.getFieldValue("appId"));
								map.put(statsDate, vo);
							} else {
								logger.warn("[no results]没有找到数据... hospitalCode: {}.", hospitalCode);
							}
						}
					} else {
						logger.warn("[no groups]没有找到数据... hospitalCode: {}.", hospitalCode);
					}
				}
			} else {
				logger.warn("[no group commands]没有找到的数据... hospitalCode: {}.", hospitalCode);
			}
		} catch (Exception e) {
			logger.error("获取所有医院关注信息异常。 errorMsg: {}. cause by: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return map;
	}

	private Map<String, Map<String, Double>> getAreaStatsByAreaCode(String areaCode, String statsMonth) {
		Map<String, Map<String, Double>> resultMap = new LinkedHashMap<>();

		/**
		 * q=areaCode%3A440000 stats=true&rows=0& stats.facet=thisMonth&
		 * stats.field=thisMonthWxIncCount ....
		 */

		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("areaCode").append(SolrConstant.COLON).append(areaCode).append(SolrConstant.AND);
		// 日期
		if (StringUtils.isNotBlank(statsMonth)) {
			sbQuery.append("thisMonth").append(SolrConstant.COLON).append(statsMonth).append(SolrConstant.AND);
		}

		// !hospitalCode:\\-1
		sbQuery.append(SolrConstant.NOT).append("hospitalCode").append(SolrConstant.COLON).append("\\-");

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
					double value = ((Double) entry2.getValue().get("sum"));

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
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("areaCode").append(SolrConstant.COLON).append(request.getAreaCode());

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getBeginDate())) {
			sb.append(SolrConstant.AND).append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START)
					.append(request.getBeginDate()).append(SolrConstant.TO).append(SolrConstant.ALL_VALUE)
					.append(SolrConstant.INTERVAL_END);
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_SUBSCRIBE, collection, true, sb.toString());
	}
	
	private void saveMonthlyStatsInfo(SubscribeStatsRequest request, Map<String, SubscribeStats> statsMap) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("thisMonth").append(SolrConstant.COLON).append(request.getStatsMonth()).append(SolrConstant.AND);
		sb.append("areaCode").append(SolrConstant.COLON).append(request.getAreaCode());
		
		Collection<SubscribeStats> collection = statsMap.values();
		YxwUpdateClient.addBean(Cores.CORE_STATS_SUBSCRIBE, collection, true, sb.toString());
	}

	private void saveAreaStatsInfos(SubscribeStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append("\\-");
		sb.append(SolrConstant.AND);
		sb.append("areaCode").append(SolrConstant.COLON).append(request.getAreaCode());

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getBeginDate())) {
			sb.append(SolrConstant.AND).append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START)
					.append(request.getBeginDate()).append(SolrConstant.TO).append(SolrConstant.ALL_VALUE)
					.append(SolrConstant.INTERVAL_END);
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_SUBSCRIBE, collection, true, sb.toString());
	}

	@Override
	public String statsInfoForMonth(SubscribeStatsRequest request) {
		// statsMonth 只会截取前7位。
		String beginDate = SubscribeConstant.DEFAULT_BEGIN_DATE;
		String statsDate = DateUtils.getFirstDayOfThisMonth(request.getStatsMonth().concat("-01"));
		// 获取源数据
		Map<String, SubscribeVo> dataMap = getSubscribeMapByHospital(request.getHospitalCode(), statsDate);
		SubscribeVo vo = dataMap.get(statsDate);
		// 这里需要查询指定月份以及指定月份之前的一个月份的数据
		Map<String, SubscribeStats> monthMap = getStatsInfoByStatsMonth(request);
		
		// 写入的map
		Map<String, SubscribeStats> statsMap = new HashMap<>();
		
		SubscribeStats stats = monthMap.get(request.getStatsMonth());
		
		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		if (vo != null) {
			// 信息整理
			if (stats == null) {
				stats = new SubscribeStats();
				stats.setId(PKGenerator.generateId());
				BeanUtils.copyProperties(request, stats);
				stats.setPlatform(1);

				if (vo.getPlatform().intValue() == PlatformConstant.PLATFORM_STANDARD_WECHAT_VAL) {
					stats.setWxAppId(vo.getAppId());
				} else {
					stats.setAliAppId(vo.getAppId());
				}
			}

			Double thisMonthWxIncCount = vo.getIncreaseCount();
			Double lastMonthWxIncCount = (double) 0;
			Double thisMonthWxCancelCount = vo.getCancelCount();
			Double lastMonthWxCancelCount = (double) 0;
			Double wxCumulateCountTillThisMonth = vo.getCumulateCount();
			// ！！！支付宝的目前没有接口获取。就让一直是0
			// long thisMonthAliIncCount = 0L;
			// long lastMonthAliIncCount = 0L;

			String lastMonth = DateUtils.getFirstDayOfMonth(statsDate, -1);
			if (!statsDate.equals(beginDate)) {
				SubscribeStats tempStats = monthMap.get(lastMonth);
				if (tempStats != null) {
					lastMonthWxIncCount = tempStats.getThisMonthWxIncCount();
					lastMonthWxCancelCount = tempStats.getThisMonthWxCancelCount();
				}
			}

			// 计算数据--这里只计算微信的，所有支付宝的全写0.
			stats.setThisMonth(request.getStatsMonth());
			stats.setLastMonth(lastMonth.substring(0, 7));
			stats.setWxCumulateCountTillThisMonth(wxCumulateCountTillThisMonth.intValue());
			stats.setThisMonthWxIncCount(thisMonthWxIncCount.intValue());
			stats.setThisMonthWxCancelCount(thisMonthWxCancelCount.intValue());
			stats.setLastMonthWxIncCount(lastMonthWxIncCount.intValue());
			stats.setLastMonthWxCancelCount(lastMonthWxCancelCount.intValue());
			stats.setWxIncCount(thisMonthWxIncCount.intValue() - lastMonthWxIncCount.intValue());
			stats.setWxCancelCount(thisMonthWxCancelCount.intValue() - lastMonthWxCancelCount.intValue());

			if (lastMonthWxIncCount == 0) {
				stats.setWxIncRate("-");
			} else {
				stats.setWxIncRate(format.format(((thisMonthWxIncCount - lastMonthWxIncCount) / lastMonthWxIncCount)));
			}

			if (lastMonthWxCancelCount == 0) {
				stats.setWxCancelRate("-");
			} else {
				stats.setWxCancelRate(format.format((thisMonthWxCancelCount - lastMonthWxCancelCount) / lastMonthWxCancelCount));
			}
		} else {
			// 没有任何可以统计的信息，则不进行保存了。
			logger.warn("该医院该月1号没有可录入的统计信息. hospitalCode:[{}], statsDate:[{}]", new Object[] { request.getHospitalCode(), statsDate });
		}
		
		// 写入
		statsMap.put(request.getStatsMonth(), stats);
		saveMonthlyStatsInfo(request, statsMap);

		return String.format("hospitalCode: [%s], one day subscribe stats end...",
				new Object[] { request.getHospitalCode()});
	}

	private Map<String, SubscribeStats> getStatsInfoByStatsMonth(SubscribeStatsRequest request) {
		Map<String, SubscribeStats> resultMap = new HashMap<>();

		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		// thisMonth:[YYYY-MM TO YYYY-MM]
		String lastMonth = DateUtils.getFirstDayOfMonth(request.getBeginDate(), -1).substring(0, 7);
		sbQuery.append("thisMonth").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(request.getStatsMonth())
				.append(SolrConstant.TO).append(lastMonth).append(SolrConstant.INTERVAL_END).append(SolrConstant.AND);
		// hospitalCode:XXXX
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		// areaCode:XXXX
		sbQuery.append("areaCode").append(SolrConstant.COLON).append(request.getAreaCode());
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
		String beginDate = SubscribeConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();

		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		Map<String, Map<String, Double>> dataMap = getAreaStatsByAreaCode(request.getAreaCode(), "");
		logger.error("----------------[{}]", request.getAreaCode());
		logger.error(JSON.toJSONString(dataMap));

		// 全部生成则直接new，如果是天天轮训则需要先去solr查一遍
		Map<String, Object> statsMap = new LinkedHashMap<>();

		while (tempDate.compareTo(endDate) <= 0) {
			String tDate = tempDate.substring(0, 7);
			SubscribeStats stats = new SubscribeStats();
			BeanUtils.copyProperties(request, stats);
			stats.setId(PKGenerator.generateId());
			// 这个platform的问题，得以后考虑。 标准平台1健康益2其他3吧？？
			stats.setPlatform(1);
			stats.setHospitalCode("-");
			stats.setHospitalName("-");
			stats.setWxAppId("-");
			stats.setAliAppId("-");

			Double thisMonthWxIncCount = dataMap.get(tDate).get("thisMonthWxIncCount");
			Double lastMonthWxIncCount = dataMap.get(tDate).get("lastMonthWxIncCount");
			Double thisMonthWxCancelCount = dataMap.get(tDate).get("thisMonthWxCancelCount");
			Double lastMonthWxCancelCount = dataMap.get(tDate).get("lastMonthWxCancelCount");
			Double wxCumulateCountTillThisMonth = dataMap.get(tDate).get("wxCumulateCountTillThisMonth");
			// ！！！支付宝的目前没有接口获取。就让一直是0
			// long thisMonthAliIncCount = 0L;
			// long lastMonthAliIncCount = 0L;

			// 计算数据--这里只计算微信的，所有支付宝的全写0.
			stats.setThisMonth(tempDate.substring(0, 7));
			stats.setLastMonth(DateUtils.getFirstDayOfMonth(tempDate, -1).substring(0, 7));
			stats.setWxCumulateCountTillThisMonth(wxCumulateCountTillThisMonth.intValue());
			stats.setThisMonthWxIncCount(thisMonthWxIncCount.intValue());
			stats.setThisMonthWxCancelCount(thisMonthWxCancelCount.intValue());
			stats.setLastMonthWxIncCount(lastMonthWxIncCount.intValue());
			stats.setLastMonthWxCancelCount(lastMonthWxCancelCount.intValue());
			stats.setWxIncCount(thisMonthWxIncCount - lastMonthWxIncCount.intValue());
			stats.setWxCancelCount(thisMonthWxCancelCount.intValue() - lastMonthWxCancelCount.intValue());

			if (lastMonthWxIncCount == 0) {
				stats.setWxIncRate("-");
			} else {
				stats.setWxIncRate(format.format(((thisMonthWxIncCount - lastMonthWxIncCount) / lastMonthWxIncCount)));
			}

			if (lastMonthWxCancelCount == 0) {
				stats.setWxCancelRate("-");
			} else {
				stats.setWxCancelRate(format.format((thisMonthWxCancelCount - lastMonthWxCancelCount) / lastMonthWxCancelCount));
			}

			statsMap.put(tempDate, stats);

			// 下月1号
			tempDate = DateUtils.getFirstDayNextMonth(tempDate);
		}

		// logger.error(JSON.toJSONString(statsMap));
		saveAreaStatsInfos(request, statsMap.values());

		return String.format("hospitalCode: [%s], all subscribe stats end...", new Object[] { request.getHospitalCode() });
	}

	@Override
	public String statsAreaInfoForMonth(SubscribeStatsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
