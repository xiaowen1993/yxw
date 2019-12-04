package com.yxw.solr.biz.deposit.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.deposit.service.DepositService;
import com.yxw.solr.client.YxwSolrClient;
import com.yxw.solr.client.YxwUpdateClient;
import com.yxw.solr.constants.Cores;
import com.yxw.solr.constants.ResultConstant;
import com.yxw.solr.constants.SolrConstant;
import com.yxw.solr.utils.UTCDateUtils;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.YxwResult;
import com.yxw.solr.vo.clinic.ClinicStats;
import com.yxw.solr.vo.deposit.DepositStats;
import com.yxw.solr.vo.deposit.DepositStatsRequest;
import com.yxw.utils.DateUtils;

@Service(value = "depositService")
public class DepositServiceImpl implements DepositService {
	private YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);

	private Logger logger = LoggerFactory.getLogger(DepositService.class);

	@Override
	public YxwResponse getStatsInfos(DepositStatsRequest request) {
		YxwResponse responseVo = new YxwResponse();
		YxwResult yxwResult = new YxwResult();
		String coreName = Cores.CORE_STATS_DEPOSIT;

		SolrQuery solrQuery = new SolrQuery();

		StringBuffer sbQuery = new StringBuffer();
		// 医院
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		// 分院
		sbQuery.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		// 平台
		sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);
		// 时间
		if (StringUtils.isNotBlank(request.getStatsDate())) {
			sbQuery.append("statsDate").append(SolrConstant.COLON).append(request.getStatsDate()).append(SolrConstant.AND);
		}
		sbQuery.append(SolrConstant.QUERY_ALL);
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
				yxwResult.setSize(((Long) docs.getNumFound()).intValue());
				List<Object> beans = new ArrayList<>();
				beans.addAll(docs);
				yxwResult.setItems(beans);
			}
			
			responseVo.setResult(yxwResult);
			return responseVo;
		} catch (Exception e) {
			logger.error("获取押金统计信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			responseVo.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			responseVo.setResultMessage(e.getMessage());
			return responseVo;
		}
		
	}

	@Override
	public String statsInfosWithinPlatform(DepositStatsRequest request) {
		// 日期/统计
		Map<String, Object> statsMap = new HashMap<>();

		// 从订单第一天开始
		String startDate = getStartDate(request);
		// 到昨天结束
		String preDate = DateUtils.getYesterday(DateUtils.today());

		Map<String, Map<String, Object>> refundsMap = getRefundStatsInfos(request);
		Map<String, Map<String, Object>> paysMap = getPayStatsInfos(request);
		Map<String, Map<String, Object>> partRefundsMap = getPartRefundStatsInfos(request);

		String tempDate = startDate;

		while (tempDate.compareTo(preDate) <= 0) {
			DepositStats stats = new DepositStats();
			// 基本信息
			stats.setId(PKGenerator.generateId());
			stats.setHospitalCode(request.getHospitalCode());
			stats.setBranchCode(request.getBranchCode());
			stats.setPlatform(request.getPlatform());
			stats.setStatsDate(tempDate);
			Date date = new Date();
			stats.setUpdateTime_utc(UTCDateUtils.parseDate(date));
			stats.setUpdateTime(DateUtils.getDateStr(date));

			Integer paidQuantity = 0;
			Integer paidAmount = 0;
			Integer refundQuantity = 0;
			Integer refundAmount = 0;

			// 计算已支付
			if (!org.springframework.util.CollectionUtils.isEmpty(paysMap.get(tempDate))) {
				paidQuantity += ((Long) paysMap.get(tempDate).get("count")).intValue();
				paidAmount += ((Double) paysMap.get(tempDate).get("sum")).intValue();
			}

			// 计算全额退费
			if (!org.springframework.util.CollectionUtils.isEmpty(refundsMap.get(tempDate))) {
				refundQuantity += ((Long) refundsMap.get(tempDate).get("count")).intValue();
				refundAmount += ((Double) refundsMap.get(tempDate).get("sum")).intValue();
			}

			// 计算部分退费
			if (!org.springframework.util.CollectionUtils.isEmpty(partRefundsMap.get(tempDate))) {
				refundQuantity += ((Long) partRefundsMap.get(tempDate).get("count")).intValue();
				refundAmount += ((Double) partRefundsMap.get(tempDate).get("sum")).intValue();
			}

			stats.setTotalQuantity(paidQuantity + refundQuantity);
			stats.setPaidQuantity(paidQuantity);
			stats.setPaidAmount(paidAmount);
			stats.setRefundQuantity(refundQuantity);
			stats.setRefundAmount(refundAmount);

			statsMap.put(tempDate, stats);

			// 加一天
			tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
		}

		// 写入
		if (!org.springframework.util.CollectionUtils.isEmpty(statsMap)) {
			saveStatsInfos(request, statsMap.values());
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });

	}

	private void saveStatsInfos(DepositStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		YxwUpdateClient.addBeans(Cores.CORE_STATS_DEPOSIT, collection, true, sb.toString());
	}
	
	private void saveRebuildInfos(DepositStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		
		sb.append(SolrConstant.AND).append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START)
		  .append(request.getBeginDate()).append(SolrConstant.TO).append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
		
		YxwUpdateClient.addBeans(Cores.CORE_STATS_DEPOSIT, collection, true, sb.toString());
	}

	private void saveDailyInfos(DepositStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);
		sb.append("statsDate").append(SolrConstant.COLON).append(request.getStatsDate());
		YxwUpdateClient.addBeans(Cores.CORE_STATS_DEPOSIT, collection, true, sb.toString());
	}

	@Override
	public String statsInfoForDayWithinPlatform(DepositStatsRequest request) {
		// 日期/统计
		Map<String, Object> statsMap = new HashMap<>();

		String preDate = DateUtils.getYesterday(DateUtils.today());
		String tempDate = preDate;

		DepositStatsRequest yesterdayRequest = new DepositStatsRequest();
		BeanUtils.copyProperties(request, yesterdayRequest);
		if (StringUtils.isBlank(yesterdayRequest.getStatsDate())) {
			yesterdayRequest.setStatsDate(tempDate);
		} else {
			tempDate = yesterdayRequest.getStatsDate();
		}

		Map<String, Map<String, Object>> refundsMap = getRefundStatsInfos(request);
		Map<String, Map<String, Object>> paysMap = getPayStatsInfos(request);
		Map<String, Map<String, Object>> partRefundsMap = getPartRefundStatsInfos(request);

		DepositStats stats = new DepositStats();
		// 基本信息
		stats.setId(PKGenerator.generateId());
		stats.setHospitalCode(request.getHospitalCode());
		stats.setBranchCode(request.getBranchCode());
		stats.setPlatform(request.getPlatform());
		stats.setStatsDate(tempDate);
		Date date = new Date();
		stats.setUpdateTime_utc(UTCDateUtils.parseDate(date));
		stats.setUpdateTime(DateUtils.getDateStr(date));

		Integer paidQuantity = 0;
		Integer paidAmount = 0;
		Integer refundQuantity = 0;
		Integer refundAmount = 0;

		// 计算已支付
		if (!org.springframework.util.CollectionUtils.isEmpty(paysMap.get(tempDate))) {
			paidQuantity += ((Long) paysMap.get(tempDate).get("count")).intValue();
			paidAmount += ((Double) paysMap.get(tempDate).get("sum")).intValue();
		}

		// 计算全额退费
		if (!org.springframework.util.CollectionUtils.isEmpty(refundsMap.get(tempDate))) {
			refundQuantity += ((Long) refundsMap.get(tempDate).get("count")).intValue();
			refundAmount += ((Double) refundsMap.get(tempDate).get("sum")).intValue();
		}

		// 计算部分退费
		if (!org.springframework.util.CollectionUtils.isEmpty(partRefundsMap.get(tempDate))) {
			refundQuantity += ((Long) partRefundsMap.get(tempDate).get("count")).intValue();
			refundAmount += ((Double) partRefundsMap.get(tempDate).get("sum")).intValue();
		}

		stats.setTotalQuantity(paidQuantity + refundQuantity);
		stats.setPaidQuantity(paidQuantity);
		stats.setPaidAmount(paidAmount);
		stats.setRefundQuantity(refundQuantity);
		stats.setRefundAmount(refundAmount);

		statsMap.put(tempDate, stats);

		// 写入
		if (!org.springframework.util.CollectionUtils.isEmpty(statsMap)) {
			saveDailyInfos(request, statsMap.values());
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });

	}

	/**
	 * 按照支付时间，确定第一个订单的时间
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param platform
	 * @param coreName
	 * @return
	 */
	private String getStartDate(DepositStatsRequest request) {
		String startDate = DateUtils.today();

		SolrQuery solrQuery = new SolrQuery();

		try {
			
			StringBuffer sbQuery = new StringBuffer();
			// 医院
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());
			solrQuery.setQuery(sbQuery.toString());
			
			if (request.getPlatform() != -1) {
				solrQuery.addField("payDate");
				solrQuery.setRows(1);
				solrQuery.addSort("payDate", ORDER.asc);
	
				QueryResponse queryResponse = solrClient.query(request.getCoreName(), solrQuery);
				SolrDocumentList documentList = queryResponse.getResults();
				if (CollectionUtils.isNotEmpty(documentList)) {
					startDate = (String) documentList.get(0).getFieldValue("payDate");
				}
			} else {
				solrQuery.addField("statsDate");
				solrQuery.setRows(1);
				solrQuery.addSort("statsDate", ORDER.asc);
	
				QueryResponse queryResponse = solrClient.query(Cores.CORE_STATS_DEPOSIT, solrQuery);
				SolrDocumentList documentList = queryResponse.getResults();
				if (CollectionUtils.isNotEmpty(documentList)) {
					startDate = (String) documentList.get(0).getFieldValue("statsDate");
				}
			}

			if (StringUtils.isBlank(startDate)) {
				startDate = DateUtils.today();
			}
		} catch (Exception e) {
			logger.error("押金补交获取开始日期异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return startDate;
	}

	private Map<String, Map<String, Object>> getPartRefundStatsInfos(DepositStatsRequest request) {
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

		SolrQuery solrQuery = new SolrQuery();

		try {
			StringBuffer sbQuery = new StringBuffer();
			// 医院
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
			// 分院
			if (!request.getBranchCode().equals("-1")) {
				sbQuery.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
			}
			// 平台
			if (request.getPlatform() != -1) {
				sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);
			}
			// 日期
			if (StringUtils.isNotBlank(request.getStatsDate())) {
				sbQuery.append("refundDate").append(SolrConstant.COLON).append(request.getStatsDate()).append(SolrConstant.AND);
			}

			// 退费
			sbQuery.append("bizStatus").append(SolrConstant.COLON).append("31");
			solrQuery.setQuery(sbQuery.toString());

			// 统计
			solrQuery.setGetFieldStatistics(true);
			solrQuery.setGetFieldStatistics("refundFee");
			solrQuery.addStatsFieldFacets("refundFee", "refundDate");

			solrQuery.setRows(0);
			solrQuery.addSort("refundDate", ORDER.asc);

			QueryResponse response = solrClient.query(request.getPartRefundCoreName(), solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response.getResponse()
					.asMap(100);
			result = responseMap.get("stats").get("stats_fields").get("refundFee").get("facets").get("refundDate");
		} catch (Exception e) {
			logger.error("押金统计获取部分退费信息异常. errorMsg: {}, cause: {}.", new Object[]{e.getMessage(), e.getCause()});
		}

		return result;
	}

	private Map<String, Map<String, Object>> getPayStatsInfos(DepositStatsRequest request) {
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

		SolrQuery solrQuery = new SolrQuery();

		try {
			StringBuffer sbQuery = new StringBuffer();
			// 医院
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
			// 分院
			if (!request.getBranchCode().equals("-1")) {
				sbQuery.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
			}
			// 平台
			if (request.getPlatform() != -1) {
				sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);
			}
			// 日期
			if (StringUtils.isNotBlank(request.getStatsDate())) {
				sbQuery.append("payDate").append(SolrConstant.COLON).append(request.getStatsDate()).append(SolrConstant.AND);
			}

			// 支付，不是拿最终的状态，而是只要和第三方发生过支付就OK
			sbQuery.append("-payStatus").append(SolrConstant.COLON).append("1"); // 不是未支付
			solrQuery.setQuery(sbQuery.toString());

			// 过滤条件 -- payTime > 0
			StringBuffer sbFilter = new StringBuffer();
			sbFilter.append("payTime").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(0).append(SolrConstant.TO)
					.append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
			solrQuery.addFilterQuery(sbFilter.toString());

			// 统计
			solrQuery.setGetFieldStatistics(true);
			solrQuery.setGetFieldStatistics("payFee");
			solrQuery.addStatsFieldFacets("payFee", "payDate");

			solrQuery.setRows(0);
			solrQuery.addSort("refundDate", ORDER.asc);

			QueryResponse response = solrClient.query(request.getCoreName(), solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response.getResponse()
					.asMap(100);
			result = responseMap.get("stats").get("stats_fields").get("payFee").get("facets").get("payDate");
		} catch (Exception e) {
			logger.error("押金补交获取已缴费信息异常. errorMsg: {}. cause: {}.", new Object[]{e.getMessage(), e.getCause()});
		} 

		return result;
	}

	private Map<String, Map<String, Object>> getRefundStatsInfos(DepositStatsRequest request) {
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

		SolrQuery solrQuery = new SolrQuery();

		try {
			StringBuffer sbQuery = new StringBuffer();
			// 医院
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
			// 分院
			if (!request.getBranchCode().equals("-1")) {
				sbQuery.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
			}
			// 平台
			if (request.getPlatform() != -1) {
				sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);
			}
			// 日期
			if (StringUtils.isNotBlank(request.getStatsDate())) {
				sbQuery.append("refundDate").append(SolrConstant.COLON).append(request.getStatsDate()).append(SolrConstant.AND);
			}

			// 退费
			sbQuery.append("payStatus").append(SolrConstant.COLON).append("3");
			solrQuery.setQuery(sbQuery.toString());

			// 过滤条件 -- refundTime > 0
			StringBuffer sbFilter = new StringBuffer();
			sbFilter.append("refundTime").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(0).append(SolrConstant.TO)
					.append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
			solrQuery.addFilterQuery(sbFilter.toString());

			// 统计
			solrQuery.setGetFieldStatistics(true);
			solrQuery.setGetFieldStatistics("payFee");
			solrQuery.addStatsFieldFacets("payFee", "refundDate");

			solrQuery.setRows(0);
			solrQuery.addSort("refundDate", ORDER.asc);

			QueryResponse response = solrClient.query(request.getCoreName(), solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response.getResponse()
					.asMap(100);
			result = responseMap.get("stats").get("stats_fields").get("payFee").get("facets").get("refundDate");
		} catch (Exception e) {
			logger.error("押金统计获取已退费信息异常. errorMsg: {}, cause: {}.", new Object[]{e.getMessage(), e.getCause()});
		} 

		return result;
	}

	@Override
	public String statsInfosWithoutPlatform(DepositStatsRequest request) {
		Map<String, Object> statsMap = new HashMap<>();

		String statsDate = "";
		Map<String, Map<String, Integer>> dataMap = getAllStats(request.getHospitalCode(), request.getBranchCodeValue(), statsDate, null, null);

		String startDate = getStartDate(request);
		String tempDate = startDate;
		// 统计到昨天之前，昨天的数据，用轮训做
		String currentDate = DateUtils.getYesterday(DateUtils.today());

		while (tempDate.compareTo(currentDate) <= 0) {

			Date curDate = new Date();
			DepositStats stats = new DepositStats();
			stats.setHospitalCode(request.getHospitalCode());
			stats.setBranchCode(request.getBranchCode());
			stats.setPlatform(-1);
			stats.setId(PKGenerator.generateId());
			stats.setStatsDate(tempDate);
			stats.setUpdateTime(DateUtils.dateToString(curDate));
			stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));
			
			stats.setPaidAmount(0);
			stats.setPaidQuantity(0);
			stats.setRefundAmount(0);
			stats.setRefundQuantity(0);
			stats.setTotalQuantity(0);

			if (dataMap.get(tempDate) != null) {
				// totalQuantity
				if (dataMap.get(tempDate).get("totalQuantity") != null) {
					stats.setTotalQuantity(dataMap.get(tempDate).get("totalQuantity"));
				}
	
				// paidAmount
				if (dataMap.get(tempDate).get("paidAmount") != null) {
					stats.setPaidAmount(dataMap.get(tempDate).get("paidAmount"));
				}
	
				// paidQuantity
				if (dataMap.get(tempDate).get("paidQuantity") != null) {
					stats.setPaidQuantity(dataMap.get(tempDate).get("paidQuantity"));
				}
	
				// refundAmount
				if (dataMap.get(tempDate).get("refundAmount") != null) {
					stats.setRefundAmount(dataMap.get(tempDate).get("refundAmount"));
				}
	
				// refundQuantity
				if (dataMap.get(tempDate).get("refundQuantity") != null) {
					stats.setRefundQuantity(dataMap.get(tempDate).get("refundQuantity"));
				}
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
	public String statsInfoForDayWithoutPlatform(DepositStatsRequest request) {
		String statsDate = request.getStatsDate();
		// 统计到昨天之前，昨天的数据，用轮训做
		String currentDate = DateUtils.today();

		if (statsDate.compareTo(currentDate) < 0) {
			Map<String, Map<String, Integer>> dataMap = getAllStats(request.getHospitalCode(), request.getBranchCodeValue(), statsDate, null, null);
			Date curDate = new Date();
			ClinicStats stats = new ClinicStats();
			stats.setHospitalCode(request.getHospitalCode());
			stats.setBranchCode(request.getBranchCode());
			stats.setPlatform(-1);
			stats.setId(PKGenerator.generateId());
			stats.setStatsDate(statsDate);
			stats.setUpdateTime(DateUtils.dateToString(curDate));
			stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));
			
			stats.setPaidAmount(0);
			stats.setPaidQuantity(0);
			stats.setRefundAmount(0);
			stats.setRefundQuantity(0);
			stats.setTotalQuantity(0);

			// totalQuantity
			if (dataMap.get(statsDate).get("totalQuantity") != null) {
				stats.setTotalQuantity(dataMap.get(statsDate).get("totalQuantity"));
			}

			// paidAmount
			if (dataMap.get(statsDate).get("paidAmount") != null) {
				stats.setPaidAmount(dataMap.get(statsDate).get("paidAmount"));
			}

			// paidQuantity
			if (dataMap.get(statsDate).get("paidQuantity") != null) {
				stats.setPaidQuantity(dataMap.get(statsDate).get("paidQuantity"));
			}

			// refundAmount
			if (dataMap.get(statsDate).get("refundAmount") != null) {
				stats.setRefundAmount(dataMap.get(statsDate).get("refundAmount"));
			}

			// refundQuantity
			if (dataMap.get(statsDate).get("refundQuantity") != null) {
				stats.setRefundQuantity(dataMap.get(statsDate).get("refundQuantity"));
			}

			// 保存
			List<Object> beans = new ArrayList<>();
			beans.add(stats);
			saveDailyInfos(request, beans);
		}
		

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}
	
	private Map<String, Map<String, Integer>> getAllStats(String hospitalCode, String branchCode, String statsDate, String beginDate, String endDate) {
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
		
		// 日期过滤
		if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {
			StringBuffer sbFilter = new StringBuffer();
			sbFilter.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(beginDate)
					.append(SolrConstant.TO).append(endDate).append(SolrConstant.INTERVAL_END);
			solrQuery.addFilterQuery(sbFilter.toString());
		}

		solrQuery.setGetFieldStatistics(true);
		solrQuery.addGetFieldStatistics("totalQuantity");
		solrQuery.addGetFieldStatistics("paidQuantity");
		solrQuery.addGetFieldStatistics("paidAmount");
		solrQuery.addGetFieldStatistics("refundQuantity");
		solrQuery.addGetFieldStatistics("refundAmount");
		solrQuery.addStatsFieldFacets(null, "statsDate");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_DEPOSIT, solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response.getResponse()
					.asMap(100);
			Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> statsFields = responseMap.get("stats").get("stats_fields");
			for (Entry<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> entry1: statsFields.entrySet()) {
				String statsField = entry1.getKey();
				Map<String, Map<String, Object>> facetField = entry1.getValue().get("facets").get("statsDate");
				
				for (Entry<String, Map<String, Object>> entry2: facetField.entrySet()) {
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
			logger.error("获取全平台押金统计数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> rebuildWithinPlatform(DepositStatsRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("request", request);

		String statsDate = request.getBeginDate();
		String startDate = getStartDate(request);
		String endDate = DateUtils.getYesterday(DateUtils.today());
		String tempDate = statsDate;

		if (statsDate.compareTo(startDate) >= 0 && statsDate.compareTo(endDate) <= 0) {
			Map<String, Object> statsMap = new HashMap<>();
			Map<String, Map<String, Object>> refundsMap = getRefundStatsInfos(request);
			Map<String, Map<String, Object>> paysMap = getPayStatsInfos(request);
			Map<String, Map<String, Object>> partRefundsMap = getPartRefundStatsInfos(request);
			while (tempDate.compareTo(endDate) <= 0) {
				DepositStats stats = new DepositStats();
				// 基本信息
				stats.setId(PKGenerator.generateId());
				stats.setHospitalCode(request.getHospitalCode());
				stats.setBranchCode(request.getBranchCode());
				stats.setPlatform(request.getPlatform());
				stats.setStatsDate(tempDate);
				Date date = new Date();
				stats.setUpdateTime_utc(UTCDateUtils.parseDate(date));
				stats.setUpdateTime(DateUtils.getDateStr(date));
	
				Integer paidQuantity = 0;
				Integer paidAmount = 0;
				Integer refundQuantity = 0;
				Integer refundAmount = 0;
	
				// 计算已支付
				if (!org.springframework.util.CollectionUtils.isEmpty(paysMap.get(tempDate))) {
					paidQuantity += ((Long) paysMap.get(tempDate).get("count")).intValue();
					paidAmount += ((Double) paysMap.get(tempDate).get("sum")).intValue();
				}
	
				// 计算全额退费
				if (!org.springframework.util.CollectionUtils.isEmpty(refundsMap.get(tempDate))) {
					refundQuantity += ((Long) refundsMap.get(tempDate).get("count")).intValue();
					refundAmount += ((Double) refundsMap.get(tempDate).get("sum")).intValue();
				}
	
				// 计算部分退费
				if (!org.springframework.util.CollectionUtils.isEmpty(partRefundsMap.get(tempDate))) {
					refundQuantity += ((Long) partRefundsMap.get(tempDate).get("count")).intValue();
					refundAmount += ((Double) partRefundsMap.get(tempDate).get("sum")).intValue();
				}
	
				stats.setTotalQuantity(paidQuantity + refundQuantity);
				stats.setPaidQuantity(paidQuantity);
				stats.setPaidAmount(paidAmount);
				stats.setRefundQuantity(refundQuantity);
				stats.setRefundAmount(refundAmount);
	
				statsMap.put(tempDate, stats);
	
				// 加一天
				tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
			}
	
			// 写入
			if (!org.springframework.util.CollectionUtils.isEmpty(statsMap)) {
				// saveStatsInfos(request, statsMap.values());
				saveRebuildInfos(request, statsMap.values());
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
	public Map<String, Object> rebuildWithoutPlatform(DepositStatsRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		
		Map<String, Object> statsMap = new HashMap<>();

		String statsDate = request.getBeginDate();
		String startDate = getStartDate(request);
		String tempDate = statsDate;
		// 统计到昨天之前，昨天的数据，用轮训做
		String endDate = DateUtils.getYesterday(DateUtils.today());

		if (statsDate.compareTo(startDate) >= 0 && statsDate.compareTo(endDate) <= 0) {
			Map<String, Map<String, Integer>> dataMap = getAllStats(request.getHospitalCode(), request.getBranchCodeValue(), null, statsDate, SolrConstant.ALL_VALUE);

			while (tempDate.compareTo(endDate) <= 0) {
	
				Date curDate = new Date();
				DepositStats stats = new DepositStats();
				stats.setHospitalCode(request.getHospitalCode());
				stats.setBranchCode(request.getBranchCode());
				stats.setPlatform(-1);
				stats.setId(PKGenerator.generateId());
				stats.setStatsDate(tempDate);
				stats.setUpdateTime(DateUtils.dateToString(curDate));
				stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));
				
				stats.setPaidAmount(0);
				stats.setPaidQuantity(0);
				stats.setRefundAmount(0);
				stats.setRefundQuantity(0);
				stats.setTotalQuantity(0);
	
				if (dataMap.get(tempDate) != null) {
					// totalQuantity
					if (dataMap.get(tempDate).get("totalQuantity") != null) {
						stats.setTotalQuantity(dataMap.get(tempDate).get("totalQuantity"));
					}
		
					// paidAmount
					if (dataMap.get(tempDate).get("paidAmount") != null) {
						stats.setPaidAmount(dataMap.get(tempDate).get("paidAmount"));
					}
		
					// paidQuantity
					if (dataMap.get(tempDate).get("paidQuantity") != null) {
						stats.setPaidQuantity(dataMap.get(tempDate).get("paidQuantity"));
					}
		
					// refundAmount
					if (dataMap.get(tempDate).get("refundAmount") != null) {
						stats.setRefundAmount(dataMap.get(tempDate).get("refundAmount"));
					}
		
					// refundQuantity
					if (dataMap.get(tempDate).get("refundQuantity") != null) {
						stats.setRefundQuantity(dataMap.get(tempDate).get("refundQuantity"));
					}
				}
	
				statsMap.put(tempDate, stats);
	
				// 加一天
				tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
			}
		
			if (!org.springframework.util.CollectionUtils.isEmpty(statsMap)) {
				// 写入
				// saveStatsInfos(request, statsMap.values());
				saveRebuildInfos(request, statsMap.values());
			}
			
			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_SUCCESS);
		} else {
			String resultMsg = String.format("统计时间不在区间内！统计时间: [%s]. 实际开始时间: [%s]. 最大结束时间: [%s].", new Object[]{statsDate, startDate, endDate});
			logger.error(resultMsg);
			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_ERROR);
		}
		
		return resultMap;
	}
	
}
