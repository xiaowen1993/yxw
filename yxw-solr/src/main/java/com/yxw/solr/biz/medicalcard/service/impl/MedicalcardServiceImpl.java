package com.yxw.solr.biz.medicalcard.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.GroupParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.solr.biz.common.callable.BizInfosQueryCallable;
import com.yxw.solr.biz.medicalcard.service.MedicalcardService;
import com.yxw.solr.client.YxwSolrClient;
import com.yxw.solr.client.YxwUpdateClient;
import com.yxw.solr.constants.Cores;
import com.yxw.solr.constants.PlatformConstant;
import com.yxw.solr.constants.PoolConstant;
import com.yxw.solr.constants.ResultConstant;
import com.yxw.solr.constants.SolrConstant;
import com.yxw.solr.utils.UTCDateUtils;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.YxwResult;
import com.yxw.solr.vo.medicalcard.CardInfoRequest;
import com.yxw.solr.vo.medicalcard.CardStats;
import com.yxw.solr.vo.medicalcard.CardStatsRequest;
import com.yxw.utils.DateUtils;

@Service(value = "medicalcardService")
public class MedicalcardServiceImpl implements MedicalcardService {

	private Logger logger = LoggerFactory.getLogger(MedicalcardService.class);

	private YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);

	@Override
	public YxwResponse findCards(CardInfoRequest request) {
		YxwResponse responseVo = new YxwResponse();
		YxwResult yxwResult = new YxwResult();

		// 创建查询器
		SolrQuery solrQuery = getQueryParams(request);
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("findMedicalcard"));
		List<FutureTask<Map<String, Object>>> tasks = new ArrayList<FutureTask<Map<String, Object>>>();

		try {
			if (solrQuery != null) {
				for (String coreName : PlatformConstant.cardList) {
					Callable<Map<String, Object>> callable = new BizInfosQueryCallable(coreName, solrQuery);
					FutureTask<Map<String, Object>> task = new FutureTask<Map<String, Object>>(callable);
					tasks.add(task);
					executorService.submit(task);
				}

				if (tasks.size() > 0) {
					List<Object> beans = new ArrayList<>();
					int size = 0;
					for (FutureTask<Map<String, Object>> futureTask : tasks) {
						Map<String, Object> resultMap = futureTask.get(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
						size += ((Long) resultMap.get("size")).intValue();
						beans.addAll((SolrDocumentList) resultMap.get("beans"));
					}

					yxwResult.setSize(size);
					yxwResult.setItems(beans);
				}

				responseVo.setResult(yxwResult);
				return responseVo;
			} else {
				String errorMsg = "invalid params to generate SolrQuery params. params: " + JSON.toJSONString(request);
				logger.warn(errorMsg);

				responseVo.setResultCode(ResultConstant.RESULT_CODE_ERROR);
				responseVo.setResultMessage(errorMsg);
				return responseVo;
			}
		} catch (Exception e) {
			logger.error("findMedicalcards error. erroQuery: {}. errorMsg: {}. cause: {}.",
					new Object[] { solrQuery.toString(), e.getMessage(), e.getCause() });

			responseVo.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			responseVo.setResultMessage(e.getMessage());
			return responseVo;
		} finally {
			executorService.shutdown();
		}
	}

	private SolrQuery getQueryParams(CardInfoRequest request) {
		SolrQuery solrQuery = new SolrQuery();
		StringBuffer queryString = new StringBuffer();
		// 医院
		queryString.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());

		// 平台
		if (request.getPlatform() != -1) {
			queryString.append(SolrConstant.AND).append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		}
		
		// 姓名
		if (StringUtils.isNotBlank(request.getPatientName())) {
			queryString.append(SolrConstant.AND).append("name").append(SolrConstant.COLON).append(request.getPatientName());
		}

		// 卡号
		if (StringUtils.isNotBlank(request.getCardNo())) {
			queryString.append(SolrConstant.AND).append("cardNo").append(SolrConstant.COLON).append(request.getCardNo());
		}

		// 手机号码
		if (StringUtils.isNotBlank(request.getMobileNo())) {
			queryString.append(SolrConstant.AND).append("mobile").append(SolrConstant.COLON).append(request.getMobileNo());
		}

		// 证件号码
		if (StringUtils.isNotBlank(request.getIdNo())) {
			queryString.append(SolrConstant.AND).append("idNo").append(SolrConstant.COLON).append(request.getIdNo());
		}

		// 状态
		if (request.getState() != -1) {
			queryString.append(SolrConstant.AND).append("state").append(SolrConstant.COLON).append(request.getState());
		}

		solrQuery.setQuery(queryString.toString());
		// 按姓名、卡号升序
		solrQuery.setSort("patientName", ORDER.asc);
		solrQuery.setSort("cardNo", ORDER.asc);

		// 分页查询
		solrQuery.set(CommonParams.START, request.getPageSize() * (request.getPageIndex() - 1));
		solrQuery.set(CommonParams.ROWS, request.getPageSize());

		return solrQuery;
	}

	@Override
	public YxwResponse getStatInfos(CardStatsRequest request) {
		YxwResponse responseVo = new YxwResponse();
		YxwResult yxwResult = new YxwResult();

		String coreName = Cores.CORE_STATS_CARD;

		SolrQuery solrQuery = new SolrQuery();

		StringBuffer sbQuery = new StringBuffer();
		// 医院
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		// 分院
		sbQuery.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		// 平台
		sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());

		solrQuery.setQuery(sbQuery.toString());

		// 分页查询
		solrQuery.set(CommonParams.START, request.getPageSize() * (request.getPageIndex() - 1));
		solrQuery.set(CommonParams.ROWS, request.getPageSize());

		// 设置排序， 此处默认降序
		solrQuery.setSort("statsDate", ORDER.desc);

		// 时间范围
		StringBuffer sbFilter = new StringBuffer();
		if (StringUtils.isNotBlank(request.getBeginDate())) {
			if (StringUtils.isNotBlank(request.getEndDate())) {
				// 有开始时间、结束时间
				sbFilter.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(request.getBeginDate())
						.append(SolrConstant.TO).append(request.getEndDate()).append(SolrConstant.INTERVAL_END);
			} else {
				// 只有开始时间
				sbFilter.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(request.getBeginDate())
						.append(SolrConstant.TO).append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
			}

			solrQuery.setFilterQueries(sbFilter.toString());
		} else {
			if (StringUtils.isNotBlank(request.getEndDate())) {
				// 只指定了结束时间
				sbFilter.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(SolrConstant.ALL_VALUE)
						.append(SolrConstant.TO).append(request.getEndDate()).append(SolrConstant.INTERVAL_END);
				solrQuery.setFilterQueries(sbFilter.toString());
			}
		}

		try {
			QueryResponse queryResponse = solrClient.query(coreName, solrQuery);
			SolrDocumentList docs = queryResponse.getResults();
			if (CollectionUtils.isNotEmpty(docs)) {
				List<Object> beans = new ArrayList<>();
				beans.addAll(docs);
				yxwResult.setItems(beans);
				yxwResult.setSize(((Long) docs.getNumFound()).intValue());
			}

			responseVo.setResult(yxwResult);
			return responseVo;
		} catch (Exception e) {
			logger.error("获取绑卡统计异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });

			responseVo.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			responseVo.setResultMessage(e.getMessage());
			return responseVo;
		}

	}

	private Map<String, Integer> getDailyNewBindMap(CardStatsRequest request) {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();

		try {
			SolrQuery query = new SolrQuery();
			StringBuffer sbQuery = new StringBuffer(); 

			// 绑卡渠道
			if (request.getPlatform() != -1) {
				sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);
			}
			// 分院
			if (!request.getBranchCode().equals("-1")) {
				sbQuery.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
			}

			if (StringUtils.isNotBlank(request.getStatsDate())) {
				sbQuery.append("createDate").append(SolrConstant.COLON).append(request.getStatsDate()).append(SolrConstant.AND);
			}

			// 医院
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());

			query.setQuery(sbQuery.toString());
			query.setFields("id");
			query.setSort("createTime_utc", ORDER.asc);
			query.setRows(Integer.MAX_VALUE);

			query.set(GroupParams.GROUP, "true");
			query.set(GroupParams.GROUP_FIELD, "createDate");

			QueryResponse response = solrClient.query(request.getCoreName(), query);
			GroupResponse groupResponse = response.getGroupResponse();
			if (groupResponse != null) {
				List<GroupCommand> groupList = groupResponse.getValues();
				for (GroupCommand groupCommand : groupList) {
					for (Group group : groupCommand.getValues()) {
						String groupValue = group.getGroupValue();
						resultMap.put(groupValue, (int) group.getResult().getNumFound());
					}
				}
			}

		} catch (Exception e) {
			logger.error("获取每日新增绑卡数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	private Map<String, Integer> getDailyUnbindMap(CardStatsRequest request) {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();

		try {
			SolrQuery query = new SolrQuery();
			StringBuffer sbQuery = new StringBuffer();

			// 医院
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
			// 绑卡渠道
			if (request.getPlatform() != -1) {
				sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);
			}
			// 分院
			if (!request.getBranchCode().equals("-1")) {
				sbQuery.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
			}

			if (StringUtils.isNotBlank(request.getStatsDate())) {
				sbQuery.append("updateDate").append(SolrConstant.COLON).append(request.getStatsDate()).append(SolrConstant.AND);
			}

			// state = 0
			sbQuery.append("state").append(SolrConstant.COLON).append("0");

			query.setQuery(sbQuery.toString());
			query.setFields("id");
			query.setSort("updateTime_utc", ORDER.asc);
			query.setRows(2000);

			query.set(GroupParams.GROUP, "true");
			query.set(GroupParams.GROUP_FIELD, "updateDate");

			QueryResponse response = solrClient.query(request.getCoreName(), query);
			GroupResponse groupResponse = response.getGroupResponse();
			if (groupResponse != null) {
				List<GroupCommand> groupList = groupResponse.getValues();
				for (GroupCommand groupCommand : groupList) {
					for (Group group : groupCommand.getValues()) {
						String groupValue = group.getGroupValue();
						resultMap.put(groupValue, (int) group.getResult().getNumFound());
					}
				}
			}

		} catch (Exception e) {
			logger.error("获取每日解绑数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	private String getBizStartDate(String hospitalCode, String coreName) {
		String startDate = DateUtils.today();

		SolrQuery solrQuery = new SolrQuery();

		try {
			String queryString = SolrConstant.QUERY_ALL;
			// 医院
			queryString = queryString.concat(SolrConstant.AND).concat("hospitalCode").concat(SolrConstant.COLON).concat(hospitalCode);

			solrQuery.setQuery(queryString);
			solrQuery.addField("createDate");
			solrQuery.setRows(1);
			solrQuery.addSort("createTime", ORDER.asc);

			QueryResponse queryResponse = solrClient.query(coreName, solrQuery);
			SolrDocumentList documentList = queryResponse.getResults();
			if (CollectionUtils.isNotEmpty(documentList)) {
				startDate = (String) documentList.get(0).getFieldValue("createDate");
			}

			if (StringUtils.isBlank(startDate)) {
				startDate = DateUtils.today();
			}
		} catch (Exception e) {
			logger.error("获取绑卡开始时间异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return startDate;
	}

	private String getStatsStartDate(String hospitalCode) {
		String startDate = DateUtils.today();

		SolrQuery solrQuery = new SolrQuery();

		try {
			String queryString = SolrConstant.QUERY_ALL;
			// 医院
			queryString = queryString.concat(SolrConstant.AND).concat("hospitalCode").concat(SolrConstant.COLON).concat(hospitalCode);

			solrQuery.setQuery(queryString);
			solrQuery.addField("statsDate");
			solrQuery.setRows(1);
			solrQuery.addSort("statsDate", ORDER.asc);

			QueryResponse queryResponse = solrClient.query(Cores.CORE_STATS_CARD, solrQuery);
			SolrDocumentList documentList = queryResponse.getResults();
			if (CollectionUtils.isNotEmpty(documentList)) {
				startDate = (String) documentList.get(0).getFieldValue("statsDate");
			}

			if (StringUtils.isBlank(startDate)) {
				startDate = DateUtils.today();
			}
		} catch (Exception e) {
			logger.error("获取绑卡开始时间异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return startDate;
	}

	private void saveStatsInfos(CardStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		YxwUpdateClient.addBeans(Cores.CORE_STATS_CARD, collection, true, sb.toString());
	}

	private void saveStatsInfo(CardStats stats) {
		String branchCode = stats.getBranchCode();
		if (branchCode.equals("-1")) {
			branchCode = "\\-1";
		}

		String platform = stats.getPlatform().toString();
		if (platform.equals("-1")) {
			platform = "\\-1";
		}

		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(stats.getHospitalCode()).append(SolrConstant.AND);
		sb.append("branchCode").append(SolrConstant.COLON).append(branchCode).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(platform).append(SolrConstant.AND);
		sb.append("statsDate").append(SolrConstant.COLON).append(stats.getStatsDate());
		YxwUpdateClient.addBean(Cores.CORE_STATS_CARD, stats, true, sb.toString());
	}

	@Override
	public String statsInfosWithinPlatform(CardStatsRequest request) {
		if (!request.getPlatform().equals("-1")) {
			Map<String, Object> statsMap = new HashMap<String, Object>();

			String startDate = getBizStartDate(request.getHospitalCode(), request.getCoreName());
			String tempDate = startDate;
			// 统计到昨天之前，昨天的数据，用轮训做
			String preDay = DateUtils.getYesterday(DateUtils.today());

			/**
			 * 此处查询的时候，应该同时查询多个core，合并结果
			 */
			Map<String, Integer> dailyNewBindMap = getDailyNewBindMap(request);
			Map<String, Integer> dailyUnBindMap = getDailyUnbindMap(request);

			while (tempDate.compareTo(preDay) <= 0) {
				int newBindQuantity = dailyNewBindMap.get(tempDate) == null ? 0 : dailyNewBindMap.get(tempDate);
				int unBindQuantity = dailyUnBindMap.get(tempDate) == null ? 0 : dailyUnBindMap.get(tempDate);

				Date curDate = new Date();
				CardStats stats = new CardStats();
				stats.setHospitalCode(request.getHospitalCode());
				stats.setBranchCode(request.getBranchCode());
				stats.setPlatform(request.getPlatform());
				stats.setNewBindQuantity(newBindQuantity);
				stats.setUnBindQuantity(unBindQuantity);
				stats.setId(PKGenerator.generateId());
				stats.setStatsDate(tempDate);
				stats.setUpdateTime(DateUtils.dateToString(curDate));
				stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));

				// 不排除出现负数
				stats.setNetIncreasedQuantity(newBindQuantity - unBindQuantity);

				if (tempDate.equals(startDate)) {
					stats.setCumulativeQuantity(stats.getNetIncreasedQuantity());
					stats.setTotalQuantity(newBindQuantity);
				} else {
					// 获取前一天的Key
					Date date = DateUtils.StringToDateYMD(tempDate);
					String dateKey = DateUtils.getDayForDate(date, -1);
					CardStats preDayStats = (CardStats) statsMap.get(dateKey);
					stats.setCumulativeQuantity(preDayStats.getCumulativeQuantity() + stats.getNetIncreasedQuantity());
					stats.setTotalQuantity(preDayStats.getTotalQuantity() + stats.getNewBindQuantity());
				}

				// logger.info(JSON.toJSONString(stats));
				statsMap.put(tempDate, stats);

				// 加一天
				tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
			}

			// 写入
			if (statsMap != null && statsMap.size() > 0) {
				saveStatsInfos(request, statsMap.values());
				logger.info(String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. 索引新增并优化完成...",
						new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() }));
			}
		} else {
			// 对于获取所有平台，指定分院的来说，只要把数据查询出来，相加即可
			// String startDate = getStatsStartDate(request.getHospitalCode());
			// String tempDate = startDate;
			// // 统计到昨天之前，昨天的数据，用轮训做
			// String currentDate = DateUtils.getDayForDate(new Date(), -1);
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	@Override
	public String statsInfoForDayWithinPlatform(CardStatsRequest request) {
		String tempDate = request.getStatsDate();

		String currentDate = DateUtils.today();

		if (tempDate.compareTo(currentDate) < 0) {
			/**
			 * 此处查询的时候，应该同时查询多个core，合并结果
			 */
			Map<String, Integer> dailyNewBindMap = getDailyNewBindMap(request);
			Map<String, Integer> dailyUnBindMap = getDailyUnbindMap(request);

			int newBindQuantity = dailyNewBindMap.get(tempDate) == null ? 0 : dailyNewBindMap.get(tempDate);
			int unBindQuantity = dailyUnBindMap.get(tempDate) == null ? 0 : dailyUnBindMap.get(tempDate);

			CardStats stats = new CardStats();
			stats.setHospitalCode(request.getHospitalCode());
			stats.setBranchCode(request.getBranchCode());
			stats.setPlatform(request.getPlatform());
			stats.setNewBindQuantity(newBindQuantity);
			stats.setUnBindQuantity(unBindQuantity);
			stats.setId(PKGenerator.generateId());
			stats.setStatsDate(tempDate);
			Date curDate = new Date();
			stats.setUpdateTime(DateUtils.dateToString(curDate));
			stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));
			// 不排除出现负数
			stats.setNetIncreasedQuantity(newBindQuantity - unBindQuantity);

			String yesterday = DateUtils.getDayForDate(DateUtils.StringToDate(request.getStatsDate()), -1);

			CardStatsRequest yesterdayRequest = new CardStatsRequest();
			BeanUtils.copyProperties(request, yesterdayRequest);
			yesterdayRequest.setStatsDate(yesterday);
			CardStats yesterdayStats = getDailyStats(yesterdayRequest);

			stats.setCumulativeQuantity(yesterdayStats.getCumulativeQuantity() + stats.getNetIncreasedQuantity());
			stats.setTotalQuantity(yesterdayStats.getTotalQuantity() + stats.getNewBindQuantity());

			saveStatsInfo(stats);
			logger.info(String.format("hospitalCode: [%s], branchCosde: [%s], platform: [%s]. 索引新增并优化完成...",
					new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() }));
		} else {
			// 中途某一天，则需要更新后面的数据
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	@Override
	public CardStats getDailyStats(CardStatsRequest request) {
		CardStats stats = null;

		SolrQuery solrQuery = new SolrQuery();
		String coreName = Cores.CORE_STATS_CARD;

		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
			sbQuery.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
			sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);
			sbQuery.append("statsDate").append(SolrConstant.COLON).append(request.getStatsDate());

			solrQuery.setQuery(sbQuery.toString());
			QueryResponse response = solrClient.query(coreName, solrQuery);
			SolrDocumentList docs = response.getResults();
			if (CollectionUtils.isNotEmpty(docs)) {
				SolrDocument doc = docs.get(0);
				String source = JSON.toJSONString(doc);
				stats = JSON.parseObject(source, CardStats.class);
			}
		} catch (Exception e) {
			logger.error("获取某日统计数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return stats == null ? new CardStats() : stats;
	}

	private Map<String, Map<String, Integer>> getAllStats(String hospitalCode, String branchCode, String statsDate) {
		Map<String, Map<String, Integer>> resultMap = new HashMap<>();

		SolrQuery solrQuery = new SolrQuery();

		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(hospitalCode).append(SolrConstant.AND);
		sbQuery.append(SolrConstant.NOT).append("platform").append(SolrConstant.COLON).append("\\-1").append(SolrConstant.AND);

		// 日期
		if (StringUtils.isNotBlank(statsDate)) {
			sbQuery.append("statsDate").append(SolrConstant.COLON).append(statsDate).append(SolrConstant.AND);
		}

		sbQuery.append("branchCode").append(SolrConstant.COLON).append(branchCode);

		solrQuery.setQuery(sbQuery.toString());
		solrQuery.setRows(0);

		solrQuery.setGetFieldStatistics(true);
		solrQuery.addGetFieldStatistics("newBindQuantity");
		solrQuery.addGetFieldStatistics("unBindQuantity");
		solrQuery.addGetFieldStatistics("netIncreasedQuantity");
		solrQuery.addGetFieldStatistics("totalQuantity");
		solrQuery.addGetFieldStatistics("cumulativeQuantity");
		solrQuery.addStatsFieldFacets(null, "statsDate");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_CARD, solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response.getResponse()
					.asMap(100);
			Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> statsFields = responseMap.get("stats").get("stats_fields");
			for (Entry<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> entry1 : statsFields.entrySet()) {
				String statsField = entry1.getKey();
				Map<String, Map<String, Object>> facetField = entry1.getValue().get("facets").get("statsDate");

				for (Entry<String, Map<String, Object>> entry2 : facetField.entrySet()) {
					String date = entry2.getKey();
					Integer value = ((Double) entry2.getValue().get("sum")).intValue();

					if (resultMap.get(date) != null) {
						resultMap.get(date).put(statsField, value);
					} else {
						Map<String, Integer> statsMap = new HashMap<>();
						statsMap.put(statsField, value);
						resultMap.put(date, statsMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取医院绑卡统计异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	@Override
	public String statsInfoForDayWithoutPlatform(CardStatsRequest request) {
		String statsDate = request.getStatsDate();
		Map<String, Map<String, Integer>> dataMap = getAllStats(request.getHospitalCode(), request.getBranchCodeValue(), statsDate);

		// 统计到昨天之前，昨天的数据，用轮训做
		String currentDate = DateUtils.today();

		if (statsDate.compareTo(currentDate) < 0) {

			Date curDate = new Date();
			CardStats stats = new CardStats();
			stats.setHospitalCode(request.getHospitalCode());
			stats.setBranchCode(request.getBranchCode());
			stats.setPlatform(-1);
			stats.setId(PKGenerator.generateId());
			stats.setStatsDate(statsDate);
			stats.setUpdateTime(DateUtils.dateToString(curDate));
			stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));
			stats.setNewBindQuantity(0);
			stats.setUnBindQuantity(0);
			stats.setNetIncreasedQuantity(0);
			stats.setCumulativeQuantity(0);
			stats.setTotalQuantity(0);

			// newBindQuantity
			if (dataMap.get(statsDate).get("newBindQuantity") != null) {
				stats.setNewBindQuantity(dataMap.get(statsDate).get("newBindQuantity"));
			}

			// unBindQuantity
			if (dataMap.get(statsDate).get("unBindQuantity") != null) {
				stats.setUnBindQuantity(dataMap.get(statsDate).get("unBindQuantity"));
			}

			// netIncreasedQuantity
			if (dataMap.get(statsDate).get("netIncreasedQuantity") != null) {
				stats.setNetIncreasedQuantity(dataMap.get(statsDate).get("netIncreasedQuantity"));
			}

			// cumulativeQuantity
			if (dataMap.get(statsDate).get("cumulativeQuantity") != null) {
				stats.setCumulativeQuantity(dataMap.get(statsDate).get("cumulativeQuantity"));
			}

			// totalQuantity
			if (dataMap.get(statsDate).get("totalQuantity") != null) {
				stats.setTotalQuantity(dataMap.get(statsDate).get("totalQuantity"));
			}

			// 保存
			saveStatsInfo(stats);
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	@Override
	public String statsInfosWithoutPlatform(CardStatsRequest request) {
		Map<String, Object> statsMap = new HashMap<>();

		String statsDate = "";
		Map<String, Map<String, Integer>> dataMap = getAllStats(request.getHospitalCode(), request.getBranchCodeValue(), statsDate);

		String startDate = getStatsStartDate(request.getHospitalCode());
		String tempDate = startDate;
		// 统计到昨天之前，昨天的数据，用轮训做
		String preDay = DateUtils.getYesterday(DateUtils.today());

		while (tempDate.compareTo(preDay) <= 0) {

			Date curDate = new Date();
			CardStats stats = new CardStats();
			stats.setHospitalCode(request.getHospitalCode());
			stats.setBranchCode(request.getBranchCode());
			stats.setPlatform(-1);
			stats.setId(PKGenerator.generateId());
			stats.setStatsDate(tempDate);
			stats.setUpdateTime(DateUtils.dateToString(curDate));
			stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));
			stats.setNewBindQuantity(0);
			stats.setUnBindQuantity(0);
			stats.setNetIncreasedQuantity(0);
			stats.setCumulativeQuantity(0);
			stats.setTotalQuantity(0);

			// newBindQuantity
			if (dataMap.get(tempDate).get("newBindQuantity") != null) {
				stats.setNewBindQuantity(dataMap.get(tempDate).get("newBindQuantity"));
			}

			// unBindQuantity
			if (dataMap.get(tempDate).get("unBindQuantity") != null) {
				stats.setUnBindQuantity(dataMap.get(tempDate).get("unBindQuantity"));
			}

			// netIncreasedQuantity
			if (dataMap.get(tempDate).get("netIncreasedQuantity") != null) {
				stats.setNetIncreasedQuantity(dataMap.get(tempDate).get("netIncreasedQuantity"));
			}

			// cumulativeQuantity
			if (dataMap.get(tempDate).get("cumulativeQuantity") != null) {
				stats.setCumulativeQuantity(dataMap.get(tempDate).get("cumulativeQuantity"));
			}

			// totalQuantity
			if (dataMap.get(tempDate).get("totalQuantity") != null) {
				stats.setTotalQuantity(dataMap.get(tempDate).get("totalQuantity"));
			}

			statsMap.put(tempDate, stats);

			// 加一天
			tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
		}

		if (!org.springframework.util.CollectionUtils.isEmpty(statsMap)) {
			// 写入
			saveStatsInfos(request, statsMap.values());
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	@Override
	public YxwResponse searchCards(CardInfoRequest request) {
		YxwResponse responseVo = new YxwResponse();
		YxwResult yxwResult = new YxwResult();

		// 创建查询器
		SolrQuery solrQuery = new SolrQuery();
		StringBuffer queryString = new StringBuffer();
		// 医院
		queryString.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());

		// 平台
		if (request.getPlatform() != -1) {
			queryString.append(SolrConstant.AND).append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		}

		// 卡号
		if (StringUtils.isNotBlank(request.getCardNo())) {
			queryString.append(SolrConstant.AND).append("cardNo").append(SolrConstant.COLON).append(request.getCardNo());
		}

		// 状态
		if (request.getState() != -1) {
			queryString.append(SolrConstant.AND).append("state").append(SolrConstant.COLON).append(request.getState());
		}

		solrQuery.setQuery(queryString.toString());
		// 按姓名、卡号升序
		solrQuery.setSort("createTime", ORDER.asc);

		// 分页查询
		solrQuery.set(CommonParams.ROWS, Integer.MAX_VALUE);

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("searchCards"));
		List<FutureTask<Map<String, Object>>> tasks = new ArrayList<FutureTask<Map<String, Object>>>();

		try {
			for (String coreName : PlatformConstant.cardList) {
				Callable<Map<String, Object>> callable = new BizInfosQueryCallable(coreName, solrQuery);
				FutureTask<Map<String, Object>> task = new FutureTask<Map<String, Object>>(callable);
				tasks.add(task);
				executorService.submit(task);
			}

			if (tasks.size() > 0) {
				List<Object> beans = new ArrayList<>();
				int size = 0;
				for (FutureTask<Map<String, Object>> futureTask : tasks) {
					Map<String, Object> resultMap = futureTask.get(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
					size += ((Long) resultMap.get("size")).intValue();
					beans.addAll((SolrDocumentList) resultMap.get("beans"));
				}

				yxwResult.setSize(size);
				yxwResult.setItems(beans);
			}

			responseVo.setResult(yxwResult);
			return responseVo;
		} catch (Exception e) {
			logger.error("findMedicalcards error. erroQuery: {}. errorMsg: {}. cause: {}.",
					new Object[] { solrQuery.toString(), e.getMessage(), e.getCause() });

			responseVo.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			responseVo.setResultMessage(e.getMessage());
			return responseVo;
		} finally {
			executorService.shutdown();
		}
	}

	@Override
	public Map<String, Object> rebuildWithinPlatform(CardStatsRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("request", request);
		
		String startDate = getBizStartDate(request.getHospitalCode(), request.getCoreName());
		String statsDate = request.getBeginDate();
		String tempDate = statsDate;
		// 统计到昨天之前，昨天的数据，用轮训做
		String endDate = DateUtils.getYesterday(DateUtils.today());
		
		if (statsDate.compareTo(startDate) >= 0 && statsDate.compareTo(endDate) <= 0) {
			Map<String, Object> statsMap = new HashMap<String, Object>();
			// 获取之前日期的统计数据
			CardStatsRequest historyRequest = new CardStatsRequest();
			BeanUtils.copyProperties(request, historyRequest);
			historyRequest.setBeginDate(DateUtils.getYesterday(statsDate));
			historyRequest.setEndDate(statsDate);
			Map<String, CardStats> historyMap = getStatsForDays(historyRequest);
			//logger.info(JSON.toJSONString(historyMap));
			
			Map<String, Integer> dailyNewBindMap = getDailyNewBindMap(request);
			Map<String, Integer> dailyUnBindMap = getDailyUnbindMap(request);
	
			while (tempDate.compareTo(endDate) <= 0) {
				int newBindQuantity = dailyNewBindMap.get(tempDate) == null ? 0 : dailyNewBindMap.get(tempDate);
				int unBindQuantity = dailyUnBindMap.get(tempDate) == null ? 0 : dailyUnBindMap.get(tempDate);
	
				Date curDate = new Date();
				CardStats stats = new CardStats();
				stats.setHospitalCode(request.getHospitalCode());
				stats.setBranchCode(request.getBranchCode());
				stats.setPlatform(request.getPlatform());
				stats.setNewBindQuantity(newBindQuantity);
				stats.setUnBindQuantity(unBindQuantity);
				stats.setId(PKGenerator.generateId());
				stats.setStatsDate(tempDate);
				stats.setUpdateTime(DateUtils.dateToString(curDate));
				stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));
	
				// 不排除出现负数
				stats.setNetIncreasedQuantity(newBindQuantity - unBindQuantity);
	
				if (tempDate.equals(startDate)) {
					stats.setCumulativeQuantity(stats.getNetIncreasedQuantity());
					stats.setTotalQuantity(newBindQuantity);
				} else {
					Date date = DateUtils.StringToDateYMD(tempDate);
					String dateKey = DateUtils.getDayForDate(date, -1);
					
					// 第一天的计算
					if (tempDate.equals(statsDate)) {
						CardStats preDayStats = historyMap.get(dateKey);
						stats.setCumulativeQuantity(preDayStats.getCumulativeQuantity() + stats.getNetIncreasedQuantity());
						stats.setTotalQuantity(preDayStats.getTotalQuantity() + stats.getNewBindQuantity());
					} else {
						CardStats preDayStats = (CardStats) statsMap.get(dateKey);
						stats.setCumulativeQuantity(preDayStats.getCumulativeQuantity() + stats.getNetIncreasedQuantity());
						stats.setTotalQuantity(preDayStats.getTotalQuantity() + stats.getNewBindQuantity());
					}
					
				}
	
				// logger.info(JSON.toJSONString(stats));
				statsMap.put(tempDate, stats);
	
				// 加一天
				tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
			}
	
			// 写入
			if (statsMap != null && statsMap.size() > 0) {
				saveRebuildStatsInfos(request, statsMap.values());
			}
			
			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_SUCCESS);
		} else {
			String resultMsg = String.format("统计时间不在区间内！统计时间: [%s]. 实际开始时间: [%s]. 最大结束时间: [%s].", new Object[]{statsDate, startDate, endDate});
			logger.error(resultMsg);
			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_ERROR);
		}
		
		return resultMap;
	}

	@Override
	public Map<String, Object> rebuildWithoutPlatform(CardStatsRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("request", request);

		String statsDate = request.getBeginDate();
		String startDate = getStatsStartDate(request.getHospitalCode());
		String tempDate = statsDate;
		String endDate = DateUtils.getYesterday(DateUtils.today());
		
		if (statsDate.compareTo(startDate) >= 0 && statsDate.compareTo(endDate) <= 0) {
			Map<String, Object> statsMap = new HashMap<>();
			Map<String, Map<String, Integer>> dataMap = getAllStats(request.getHospitalCode(), request.getBranchCodeValue(), "");
			logger.info(JSON.toJSONString(dataMap));
			
			while (tempDate.compareTo(endDate) <= 0) {
				Date curDate = new Date();
				CardStats stats = new CardStats();
				stats.setHospitalCode(request.getHospitalCode());
				stats.setBranchCode(request.getBranchCode());
				stats.setPlatform(-1);
				stats.setId(PKGenerator.generateId());
				stats.setStatsDate(tempDate);
				stats.setUpdateTime(DateUtils.dateToString(curDate));
				stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));
				stats.setNewBindQuantity(0);
				stats.setUnBindQuantity(0);
				stats.setNetIncreasedQuantity(0);
				stats.setCumulativeQuantity(0);
				stats.setTotalQuantity(0);
	
				// newBindQuantity
				if (dataMap.get(tempDate).get("newBindQuantity") != null) {
					stats.setNewBindQuantity(dataMap.get(tempDate).get("newBindQuantity"));
				}
	
				// unBindQuantity
				if (dataMap.get(tempDate).get("unBindQuantity") != null) {
					stats.setUnBindQuantity(dataMap.get(tempDate).get("unBindQuantity"));
				}
	
				// netIncreasedQuantity
				if (dataMap.get(tempDate).get("netIncreasedQuantity") != null) {
					stats.setNetIncreasedQuantity(dataMap.get(tempDate).get("netIncreasedQuantity"));
				}
	
				// cumulativeQuantity
				if (dataMap.get(tempDate).get("cumulativeQuantity") != null) {
					stats.setCumulativeQuantity(dataMap.get(tempDate).get("cumulativeQuantity"));
				}
	
				// totalQuantity
				if (dataMap.get(tempDate).get("totalQuantity") != null) {
					stats.setTotalQuantity(dataMap.get(tempDate).get("totalQuantity"));
				}
	
				statsMap.put(tempDate, stats);
	
				// 加一天
				tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
			}
	
			if (!org.springframework.util.CollectionUtils.isEmpty(statsMap)) {
				// 写入
				saveRebuildStatsInfos(request, statsMap.values());
			}
			
			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_SUCCESS);
		} else {
			String resultMsg = String.format("统计时间不在区间内！统计时间: [%s]. 实际开始时间: [%s]. 最大结束时间: [%s].", new Object[]{statsDate, startDate, endDate});
			logger.error(resultMsg);
			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_ERROR);
		}
		
		return resultMap;
	}

	@Override
	public Map<String, CardStats> getStatsForDays(CardStatsRequest request) {
		Map<String, CardStats> resultMap = new HashMap<>();
		
		SolrQuery solrQuery = new SolrQuery();
		String coreName = Cores.CORE_STATS_CARD;
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
			sbQuery.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
			sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
			// sbQuery.append("statsDate").append(SolrConstant.COLON).append(request.getStatsDate());

			solrQuery.setQuery(sbQuery.toString());
			
			//*-------------------------- 如果没有指定开始时间或者结束时间，那么请使用* 替代相应的那啥
			String beginDate = request.getBeginDate();
			String endDate = request.getEndDate();
			if (StringUtils.isBlank(beginDate)) {
				beginDate = SolrConstant.ALL_VALUE;
			}
			if (StringUtils.isBlank(endDate)) {
				endDate = SolrConstant.ALL_VALUE;
			}
			// 查询范围
			StringBuffer sbFilter = new StringBuffer();
			sbFilter.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(beginDate)
				    .append(SolrConstant.TO).append(endDate).append(SolrConstant.INTERVAL_END);
			solrQuery.setFilterQueries(sbFilter.toString());
			
			solrQuery.setRows(Integer.MAX_VALUE);
			
			QueryResponse response = solrClient.query(coreName, solrQuery);
			SolrDocumentList docs = response.getResults();
			if (CollectionUtils.isNotEmpty(docs)) {
				String source = JSON.toJSONString(docs);
				List<CardStats> list = JSON.parseArray(source, CardStats.class);
				for (CardStats stats: list) {
					resultMap.put(stats.getStatsDate(), stats);
				}
			}
		} catch (Exception e) {
			logger.error("获取某日统计数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}
		
		return resultMap;
	}
	
	// 删除指定从某时间开始的数据
	private void saveRebuildStatsInfos(CardStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		
		// 删除从beginDate开始的数据
		sb.append(SolrConstant.AND).append("statsDate").append(SolrConstant.COLON)
		  .append(SolrConstant.INTERVAL_START).append(request.getBeginDate()).append(SolrConstant.TO)
		  .append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
		
		
		YxwUpdateClient.addBeans(Cores.CORE_STATS_CARD, collection, true, sb.toString());
	}

}
