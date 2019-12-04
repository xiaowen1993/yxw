package com.yxw.solr.biz.register.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.GroupParams;
import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.register.entity.Dept;
import com.yxw.solr.biz.register.service.RegDeptService;
import com.yxw.solr.client.YxwSolrClient;
import com.yxw.solr.client.YxwUpdateClient;
import com.yxw.solr.constants.Cores;
import com.yxw.solr.constants.ResultConstant;
import com.yxw.solr.constants.SolrConstant;
import com.yxw.solr.utils.UTCDateUtils;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.YxwResult;
import com.yxw.solr.vo.register.RegDeptStats;
import com.yxw.solr.vo.register.RegDeptStatsRequest;
import com.yxw.utils.DateUtils;

@Service(value = "regDeptService")
public class RegDeptServiceImpl implements RegDeptService {

	private YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);

	private Logger logger = LoggerFactory.getLogger(RegDeptService.class);

	@Override
	public YxwResponse getStatsInfos(RegDeptStatsRequest request) {
		YxwResponse yxwResponse = new YxwResponse();
		YxwResult yxwResult = new YxwResult();
		yxwResponse.setResult(yxwResult);
		
		if (StringUtils.isBlank(request.getStatsDate())) {
			request.setStatsDate(DateUtils.getDayForDate(new Date(), -1));
		}

		String coreName = Cores.CORE_STATS_DEPT;
		SolrQuery solrQuery = new SolrQuery();

		try {
			StringBuffer sbQuery = new StringBuffer();
			// 医院
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
			// 平台
			if (!request.getPlatformValue().equals("-1")) {
				sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);
			}
			// 科室名称
			if (StringUtils.isNotBlank(request.getDeptName())) {
				sbQuery.append("deptName").append(SolrConstant.COLON).append(request.getDeptName()).append(SolrConstant.AND);
			}
			// 科室代码
			if (StringUtils.isNotBlank(request.getDeptCode())) {
				sbQuery.append("deptCode").append(SolrConstant.COLON).append(request.getDeptCode()).append(SolrConstant.AND);
			}
			// 统计时间
			sbQuery.append("statsDate").append(SolrConstant.COLON).append(request.getStatsDate());

			solrQuery.setQuery(sbQuery.toString());

			// 排序
			solrQuery.setSort("deptName", ORDER.asc);

			// 分页
			solrQuery.set(CommonParams.START, request.getPageSize() * (request.getPageIndex() - 1));
			solrQuery.set(CommonParams.ROWS, request.getPageSize());

			QueryResponse queryResponse = solrClient.query(coreName, solrQuery);
			SolrDocumentList docs = queryResponse.getResults();
			List<Object> beans = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(docs)) {
				yxwResult.setSize(((Long) docs.getNumFound()).intValue());
				beans.addAll(docs);
			}
			yxwResult.setItems(beans);
			
			return yxwResponse;
		} catch (Exception e) {
			logger.error("获取挂号科室统计信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			
			yxwResponse.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			yxwResponse.setResultMessage(e.getMessage());
			return yxwResponse;
		}
		
	}

	@Override
	public String statsInfosWithinPlatform(RegDeptStatsRequest request) {
		// 科室/日期/统计
		Map<String, Map<String, RegDeptStats>> statsMap = new HashMap<String, Map<String, RegDeptStats>>();

		// 拿到这个分院下所有科室里面，第一个条数据[最早时间]
		// 后面的，判断下这一天有没有历史累计数，没有则不存。
		String startDate = getOrderStartDate(request, null);
		String tempDate = startDate;

		// 获取所有科室
		Map<String, Dept> depts = getAllDepts(request);
		// 有效科室/日期/数量
		Map<String, Map<String, Integer>> dailyMap = getDailyQuantity(request);
		
		// 统计到昨天之前，昨天的数据，用轮训做
		String endDate = DateUtils.getDayForDate(new Date(), -1);

		while (tempDate.compareTo(endDate) <= 0) {
			for (Entry<String, Map<String, Integer>> entry : dailyMap.entrySet()) {
				String deptCode = entry.getKey();
				Map<String, Integer> map = entry.getValue();

				RegDeptStats stats = new RegDeptStats();
				/**
				 * ------------------------------- 基本信息
				 */
				stats.setId(PKGenerator.generateId());
				stats.setDeptCode(deptCode);
				stats.setDeptName(depts.get(deptCode).getDeptName());
				stats.setHospitalCode(request.getHospitalCode());
				stats.setPlatform(request.getPlatform());
				stats.setStatsDate(tempDate);
				Date curDate = new Date();
				stats.setUpdateTime(DateUtils.formatDate(curDate));
				stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));

				/**
				 * ------------------------------- 统计数据
				 */
				String preDate = DateUtils.getYesterday(tempDate);

				// 昨日订单数
				int yesterdayQuantity = map.get(preDate) == null ? 0 : map.get(preDate);
				stats.setYesterdayQuantity(yesterdayQuantity);
				
				// 历史累计订单数
				if (preDate.equals(startDate) || statsMap.get(deptCode) == null || statsMap.get(deptCode).get(preDate) == null) {
					stats.setTotalQuantity(stats.getYesterdayQuantity());
				} else {
					stats.setTotalQuantity(statsMap.get(deptCode).get(preDate).getTotalQuantity() + stats.getYesterdayQuantity());
				}

				// 本月订单：判断日期是否是该月第一天，如果是，则为yesterdayQuantity,如果不是则为前一天数加上yesterdayQuantity
				String firstDayOfMonth = DateUtils.getFirstDayOfThisMonth(preDate);
				if (preDate.equals(firstDayOfMonth) || statsMap.get(deptCode) == null || statsMap.get(deptCode).get(preDate) == null) {
					stats.setThisMonthQuantity(stats.getYesterdayQuantity());
				} else {
					stats.setThisMonthQuantity(statsMap.get(deptCode).get(preDate).getThisMonthQuantity() + stats.getYesterdayQuantity());
				}

				// 本年订单
				String firstDayOfYear = DateUtils.getFirstDayOfThisYear(preDate);
				if (preDate.equals(firstDayOfYear) || statsMap.get(deptCode) == null || statsMap.get(deptCode).get(preDate) == null) {
					stats.setThisYearQuantity(stats.getYesterdayQuantity());
				} else {
					stats.setThisYearQuantity(statsMap.get(deptCode).get(preDate).getThisYearQuantity() + stats.getYesterdayQuantity());
				}

				// logger.info(tempDate);
				// 上周订单数量 ： 本周第一天的历史累计数量 - 上周第一天的历史累计数量 （每天统计的是前一天的数据）
				int thisWeekQuantity = 0; // 上星期最后一天的数据 英文不知道怎么写了
				int lastWeekQuantity = 0; // 上上星期最后一天的数据 同上哈。

				String firstDayOfThisWeek = DateUtils.getFirstDayOfThisWeek(preDate);
				String firstDayOfLastWeek = DateUtils.getFirstDayOfLastWeek(preDate);
				if (statsMap.get(deptCode) != null) {
					RegDeptStats thisWeek = statsMap.get(deptCode).get(firstDayOfThisWeek);
					if (thisWeek != null) {
						thisWeekQuantity = thisWeek.getTotalQuantity();
					}
					
					RegDeptStats lastWeek = statsMap.get(deptCode).get(firstDayOfLastWeek);
					if (lastWeek != null) {
						lastWeekQuantity = lastWeek.getTotalQuantity();
					}
				}
				
				stats.setLastWeekQuantity(thisWeekQuantity - lastWeekQuantity);
				
				// 上月累计订单 : 本月第一天历史累计数量 - 上月第一天的历史累计数量 （每天统计的是前一天的数据）
				int thisMonthQuantity = 0; // 上月最后一天的数据 英文不知道怎么写了
				int lastMonthQuantity = 0; // 上上月最后一天的数据 同上哈。
				String firstDayOfThisMonth = DateUtils.getFirstDayOfThisMonth(preDate);
				String firstDayOfLastMonth = DateUtils.getFirstDayOfLastMonth(preDate);
				if (statsMap.get(deptCode) != null) {
					RegDeptStats thisMonth = statsMap.get(deptCode).get(firstDayOfThisMonth); 
					if (thisMonth != null) {
						thisMonthQuantity = thisMonth.getTotalQuantity();
					}
					
					RegDeptStats lastMonth = statsMap.get(deptCode).get(firstDayOfLastMonth);
					if (lastMonth != null) {
						lastMonthQuantity = lastMonth.getTotalQuantity();
					}
				}

				stats.setLastMonthQuantity(thisMonthQuantity - lastMonthQuantity);
				
				Map<String, RegDeptStats> deptDailyMap = statsMap.get(deptCode);
				if (deptDailyMap == null) {
					deptDailyMap = new HashMap<String, RegDeptStats>();
					deptDailyMap.put(tempDate, stats);
				} else {
					deptDailyMap.put(tempDate, stats);
				}
				statsMap.put(deptCode, deptDailyMap);
			}

			// 加一天
			tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
		}

		// 写入
		if (statsMap != null && statsMap.size() > 0) {
			saveStatsInfos(request, statsMap);
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	private Map<String, Dept> getAllDepts(RegDeptStatsRequest request) {
		Map<String, Dept> depts = new HashMap<String, Dept>(50);
		SolrQuery solrQuery = new SolrQuery();

		try {
			String coreName = request.getCoreName();
			StringBuffer sbQuery = new StringBuffer();
			if (request.getPlatform() == -1) {
				coreName = Cores.CORE_STATS_DEPT;
			} 
			
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());
			solrQuery.setQuery(sbQuery.toString());

			// 字段
			solrQuery.setFields(new String[] { "deptName", "deptCode"});

			// group by
			solrQuery.set(GroupParams.GROUP, "true");
			solrQuery.set(GroupParams.GROUP_FIELD, "deptCode");
			solrQuery.setRows(Integer.MAX_VALUE);

			QueryResponse response = solrClient.query(coreName, solrQuery);
			GroupResponse groupResponse = response.getGroupResponse();
			if (groupResponse != null) {
				List<GroupCommand> groupList = groupResponse.getValues();
				for (GroupCommand groupCommand : groupList) {
					for (Group group : groupCommand.getValues()) {
						String groupValue = group.getGroupValue();
						Dept dept = new Dept();
						dept.setDeptCode(groupValue);
						dept.setDeptName((String) group.getResult().get(0).getFieldValue("deptName"));
						depts.put(groupValue, dept);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取所有科室信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return depts;
	}

	@Override
	public String statsInfoForDayWithinPlatform(RegDeptStatsRequest request) {
		// 科室/日期/统计
		Map<String, Map<String, RegDeptStats>> statsMap = new HashMap<String, Map<String, RegDeptStats>>();

		// 获取所有科室
		Map<String, Dept> depts = getAllDepts(request);

		// 拿到这个分院下所有科室里面，第一个条数据[最早时间]
		// 后面的，判断下这一天有没有历史累计数，没有则不存。
		String tempDate = request.getStatsDate();

		// 需要查询的数据
		String preDate = DateUtils.getYesterday(tempDate);
		String firstDayOfThisWeek = DateUtils.getFirstDayOfThisWeek(tempDate);
		String firstDayOfLastWeek = DateUtils.getFirstDayOfLastWeek(tempDate);
		String firstDayOfThisMonth = DateUtils.getFirstDayOfThisMonth(tempDate);
		String firstDayOfLastMonth = DateUtils.getFirstDayOfLastMonth(tempDate);

		// 有效科室/日期/数量
		RegDeptStatsRequest yesterdayRequest = new RegDeptStatsRequest();
		BeanUtils.copyProperties(request, yesterdayRequest);
		yesterdayRequest.setStatsDate(preDate);
		Map<String, Map<String, Integer>> dailyMap = getDailyQuantity(yesterdayRequest);

		// 科室/日期/统计数据
		Map<String, Map<String, RegDeptStats>> historicalDatasMap = getHistoricalDatas(request,
				new String[] { preDate, firstDayOfThisWeek, firstDayOfLastWeek, firstDayOfThisMonth, firstDayOfLastMonth });
		for (Entry<String, Map<String, Integer>> entry : dailyMap.entrySet()) {
			String deptCode = entry.getKey();
			Map<String, Integer> map = entry.getValue();

			RegDeptStats stats = new RegDeptStats();
			/**
			 * ------------------------------- 基本信息
			 */
			stats.setId(PKGenerator.generateId());
			stats.setDeptCode(deptCode);
			stats.setDeptName(depts.get(deptCode).getDeptName());
			stats.setHospitalCode(request.getHospitalCode());
			stats.setPlatform(request.getPlatform());
			stats.setStatsDate(tempDate);
			Date curDate = new Date();
			stats.setUpdateTime(DateUtils.formatDate(curDate));
			stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));

			/**
			 * ------------------------------- 统计数据
			 */
			// 昨日订单数
			stats.setYesterdayQuantity(map.containsKey(preDate) ? map.get(preDate) : 0);

			// 历史累计订单数 -- 拿到前一天统计的数据
			if (historicalDatasMap.get(deptCode) == null || historicalDatasMap.get(deptCode).get(preDate) == null) {
				stats.setTotalQuantity(stats.getYesterdayQuantity());
			} else {
				stats.setTotalQuantity(historicalDatasMap.get(deptCode).get(preDate).getTotalQuantity() + stats.getYesterdayQuantity());
			}

			// 本月订单：判断日期是否是该月第一天，如果是，则为yesterdayQuantity,如果不是则为前一天数加上yesterdayQuantity
			String firstDayOfMonth = DateUtils.getFirstDayOfThisMonth(preDate);
			if (preDate.equals(firstDayOfMonth) || historicalDatasMap.get(deptCode) == null
					|| historicalDatasMap.get(deptCode).get(preDate) == null) {
				stats.setThisMonthQuantity(stats.getYesterdayQuantity());
			} else {
				stats.setThisMonthQuantity(historicalDatasMap.get(deptCode).get(preDate).getThisMonthQuantity() + stats.getYesterdayQuantity());
			}

			// 本年订单
			String firstDayOfYear = DateUtils.getFirstDayOfThisYear(preDate);
			if (preDate.equals(firstDayOfYear) || historicalDatasMap.get(deptCode) == null || historicalDatasMap.get(deptCode).get(preDate) == null) {
				stats.setThisYearQuantity(stats.getYesterdayQuantity());
			} else {
				stats.setThisYearQuantity(historicalDatasMap.get(deptCode).get(preDate).getThisYearQuantity() + stats.getYesterdayQuantity());
			}

			// logger.info(tempDate);
			// 上周订单数量 ： 本周第一天的历史累计数量 - 上周第一天的历史累计数量 （每天统计的是前一天的数据）
			int thisWeekQuantity = 0; // 上星期最后一天的数据 英文不知道怎么写了
			int lastWeekQuantity = 0; // 上上星期最后一天的数据 同上哈。

			if (historicalDatasMap.get(deptCode) != null && historicalDatasMap.get(deptCode) != null
					&& historicalDatasMap.get(deptCode).get(firstDayOfThisWeek) != null) {
				thisWeekQuantity = historicalDatasMap.get(deptCode).get(firstDayOfThisWeek).getTotalQuantity();
			}

			if (historicalDatasMap.get(deptCode) != null && historicalDatasMap.get(deptCode) != null
					&& historicalDatasMap.get(deptCode).get(firstDayOfLastWeek) != null) {
				lastWeekQuantity = historicalDatasMap.get(deptCode).get(firstDayOfLastWeek).getTotalQuantity();
			}
			stats.setLastWeekQuantity(thisWeekQuantity - lastWeekQuantity);

			// 上月累计订单 : 本月第一天历史累计数量 - 上月第一天的历史累计数量 （每天统计的是前一天的数据）
			int thisMonthQuantity = 0; // 上月最后一天的数据 英文不知道怎么写了
			int lastMonthQuantity = 0; // 上上月最后一天的数据 同上哈。

			if (historicalDatasMap.get(deptCode) != null && historicalDatasMap.get(deptCode).get(firstDayOfThisMonth) != null) {
				thisMonthQuantity = historicalDatasMap.get(deptCode).get(firstDayOfThisMonth).getTotalQuantity();
			}

			if (historicalDatasMap.get(deptCode) != null && historicalDatasMap.get(deptCode).get(firstDayOfLastMonth) != null) {
				lastMonthQuantity = historicalDatasMap.get(deptCode).get(firstDayOfLastMonth).getTotalQuantity();
			}
			stats.setLastMonthQuantity(thisMonthQuantity - lastMonthQuantity);

			/**
			 * ------------------------------- end of 统计数据
			 */
			Map<String, RegDeptStats> deptDailyMap = statsMap.get(deptCode);
			if (deptDailyMap == null) {
				deptDailyMap = new HashMap<String, RegDeptStats>();
				deptDailyMap.put(tempDate, stats);
			} else {
				deptDailyMap.put(tempDate, stats);
			}
			statsMap.put(deptCode, deptDailyMap);
		}

		// 写入
		if (statsMap != null && statsMap.size() > 0) {
			saveDailyInfos(request, statsMap);
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	/**
	 * 获取指定日期的统计数据
	 * 
	 * @param request
	 * @param dates
	 * @return
	 */
	private Map<String, Map<String, RegDeptStats>> getHistoricalDatas(RegDeptStatsRequest request, String... dates) {
		Map<String, Map<String, RegDeptStats>> resultMap = new HashMap<String, Map<String, RegDeptStats>>();

		if (dates != null && dates.length != 0) {
			SolrQuery solrQuery = new SolrQuery();
			String coreName = Cores.CORE_STATS_DEPT;

			try {
				StringBuffer sbQuery = new StringBuffer();
				// 医院
				sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
				// 平台
				sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);

				sbQuery.append("(");
				for (int i = 0; i < dates.length; i++) {
					sbQuery.append("statsDate").append(SolrConstant.COLON).append(dates[i]).append(SolrConstant.OR);
				}

				solrQuery.setQuery(sbQuery.substring(0, sbQuery.length() - 3) + ")");
				solrQuery.setRows(Integer.MAX_VALUE);

				QueryResponse response = solrClient.query(coreName, solrQuery);
				SolrDocumentList documentList = response.getResults();
				if (CollectionUtils.isNotEmpty(documentList)) {
					for (SolrDocument doc : documentList) {
						String docSource = JSON.toJSONString(doc);
						RegDeptStats stats = JSON.parseObject(docSource, RegDeptStats.class);

						Map<String, RegDeptStats> statsMap = resultMap.get(stats.getDeptCode());
						if (statsMap == null) {
							statsMap = new HashMap<String, RegDeptStats>();
						}
						statsMap.put(stats.getStatsDate(), stats);
						resultMap.put(stats.getDeptCode(), statsMap);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return resultMap;
	}

	private Map<String, Map<String, Integer>> getDailyQuantity(RegDeptStatsRequest request) {
		Map<String, Map<String, Integer>> resultMap = new HashMap<String, Map<String, Integer>>();
		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		// 医院
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		// 平台
		if (!"-1".equals(request.getPlatformValue())) {
			sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);
		}
		
		// 日期 为空则找到所有的数据，不为空，则统计某一天的数据
		if (StringUtils.isNotBlank(request.getStatsDate())) {
			sbQuery.append("payDate").append(SolrConstant.COLON).append(request.getStatsDate()).append(SolrConstant.AND);
		}
		// 支付状态
		sbQuery.append("payStatus").append(SolrConstant.COLON).append("2");
		
		solrQuery.setQuery(sbQuery.toString());
		solrQuery.setRows(Integer.MAX_VALUE);
		
		// 开启多维度查询
		solrQuery.setFacet(true);
		solrQuery.addFacetPivotField("deptCode,payDate");

		try {
			QueryResponse response = solrClient.query(request.getCoreName(), solrQuery);
			
			/**
			 * 数据形如
			 *  "facet_counts":{
			    "facet_queries":{},
			    "facet_fields":{},
			    "facet_dates":{},
			    "facet_ranges":{},
			    "facet_intervals":{},
			    "facet_heatmaps":{},
			    "facet_pivot":{
			      "payDate,deptCode":[{
			          "field":"payDate",
			          "value":"2016-05-30",
			          "count":1254,
			          "pivot":[{
			              "field":"deptCode",
			              "value":"a134",
			              "count":434},
			            {
			              "field":"deptCode",
			              "value":"210a",
			              "count":139}]...
			 */
			
			NamedList<List<PivotField>> pivotDatas = response.getFacetPivot();
			if (pivotDatas != null && pivotDatas.size() > 0) {
				List<PivotField> pivotList = pivotDatas.get("deptCode,payDate");
				for (PivotField pivotField: pivotList) {
					String deptCode = (String) pivotField.getValue();
					List<PivotField> pivots = pivotField.getPivot();
					
					for (PivotField pivot: pivots) {
						String payDate = (String) pivot.getValue();
						int count = pivot.getCount();
						
						if (resultMap.containsKey(deptCode)) {
							resultMap.get(deptCode).put(payDate, count);
						} else {
							Map<String, Integer> deptDatasMap = new HashMap<>();
							deptDatasMap.put(payDate, count);
							resultMap.put(deptCode, deptDatasMap);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	private void saveStatsInfos(RegDeptStatsRequest request, Map<String, Map<String, RegDeptStats>> map) {
		List<Object> beans = new ArrayList<>();
		for (Entry<String, Map<String, RegDeptStats>> deptEntry : map.entrySet()) {
			beans.addAll(deptEntry.getValue().values());
		}

		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		
		YxwUpdateClient.addBeans(Cores.CORE_STATS_DEPT, beans, true, sb.toString());
	}
	
	private void saveRebuildInfos(RegDeptStatsRequest request, Map<String, Map<String, RegDeptStats>> map) {
		List<Object> beans = new ArrayList<>();
		for (Entry<String, Map<String, RegDeptStats>> deptEntry : map.entrySet()) {
			beans.addAll(deptEntry.getValue().values());
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND)
		  .append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		
		// 删除过滤条件
		sb.append(SolrConstant.AND).append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START)
		  .append(request.getBeginDate()).append(SolrConstant.TO).append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
		
		YxwUpdateClient.addBeans(Cores.CORE_STATS_DEPT, beans, true, sb.toString());
	}

	private void saveDailyInfos(RegDeptStatsRequest request, Map<String, Map<String, RegDeptStats>> map) {
		List<Object> beans = new ArrayList<>();
		for (Entry<String, Map<String, RegDeptStats>> deptEntry : map.entrySet()) {
			beans.addAll(deptEntry.getValue().values());
		}

		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);
		sb.append("statsDate").append(SolrConstant.COLON).append(request.getStatsDate());
		YxwUpdateClient.addBeans(Cores.CORE_STATS_DEPT, beans, true, sb.toString());
	}

	/**
	 * 所有订单开始时间
	 * 
	 * @return
	 */
	private String getOrderStartDate(RegDeptStatsRequest request, String coreName) {
		SolrQuery solrQuery = new SolrQuery();
		
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());
			if (StringUtils.isBlank(coreName)) {
				coreName = request.getCoreName();
				
				solrQuery.addField("payDate");
				solrQuery.addSort("payDate", ORDER.asc);
				solrQuery.setQuery(sbQuery.toString());
				solrQuery.setRows(1);
				QueryResponse response = solrClient.query(coreName, solrQuery);
				SolrDocumentList documentList = response.getResults();
				if (CollectionUtils.isNotEmpty(documentList)) {
					SolrDocument doc = documentList.get(0);
					return (String) doc.getFieldValue("payDate");
				}
			} else {
				solrQuery.addField("statsDate");
				solrQuery.addSort("statsDate", ORDER.asc);
				solrQuery.setQuery(sbQuery.toString());
				solrQuery.setRows(1);
				QueryResponse response = solrClient.query(coreName, solrQuery);
				SolrDocumentList documentList = response.getResults();
				if (CollectionUtils.isNotEmpty(documentList)) {
					SolrDocument doc = documentList.get(0);
					return (String) doc.getFieldValue("statsDate");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return DateUtils.today();
	}

	@Override
	public String statsInfosWithoutPlatform(RegDeptStatsRequest request) {
		Map<String, Map<String, RegDeptStats>> statsMap = new HashMap<>();

		Map<String, Dept> depts = getAllDepts(request);
		// 日期/科室/统计
		Map<String, Map<String, Map<String, Integer>>> dataMap = getAllStats(request, depts);

		String startDate = getOrderStartDate(request, Cores.CORE_STATS_DEPT);
		String tempDate = startDate;
		// 统计到昨天之前，昨天的数据，用轮训做
		String currentDate = DateUtils.getDayForDate(new Date(), -1);

		while (tempDate.compareTo(currentDate) <= 0) {
			for (Entry<String, Dept> dept: depts.entrySet()) {
				String deptCode = dept.getKey();
				Date curDate = new Date();
				RegDeptStats stats = new RegDeptStats();
				stats.setHospitalCode(request.getHospitalCode());
				stats.setPlatform(-1);
				stats.setId(PKGenerator.generateId());
				stats.setStatsDate(tempDate);
				stats.setUpdateTime(DateUtils.dateToString(curDate));
				stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));
				
				// 科室
				stats.setDeptCode(deptCode);
				stats.setDeptName(dept.getValue().getDeptName());
				
				stats.setYesterdayQuantity(0);
				stats.setLastWeekQuantity(0);
				stats.setLastMonthQuantity(0);
				stats.setThisMonthQuantity(0);
				stats.setThisYearQuantity(0);
				stats.setTotalQuantity(0);
	
				if (dataMap.get(tempDate) != null && dataMap.get(tempDate).get(deptCode) != null) {
					// yesterdayQuantity
					if (dataMap.get(tempDate).get(deptCode).get("yesterdayQuantity") != null) {
						stats.setYesterdayQuantity(dataMap.get(tempDate).get(deptCode).get("yesterdayQuantity"));
					}
		
					// lastWeekQuantity
					if (dataMap.get(tempDate).get(deptCode).get("lastWeekQuantity") != null) {
						stats.setLastWeekQuantity(dataMap.get(tempDate).get(deptCode).get("lastWeekQuantity"));
					}
		
					// thisMonthQuantity
					if (dataMap.get(tempDate).get(deptCode).get("thisMonthQuantity") != null) {
						stats.setThisMonthQuantity(dataMap.get(tempDate).get(deptCode).get("thisMonthQuantity"));
					} 
		
					// lastMonthQuantity
					if (dataMap.get(tempDate).get(deptCode).get("lastMonthQuantity") != null) {
						stats.setLastMonthQuantity(dataMap.get(tempDate).get(deptCode).get("lastMonthQuantity"));
					}
		
					// thisYearQuantity
					if (dataMap.get(tempDate).get(deptCode).get("thisYearQuantity") != null) {
						stats.setThisYearQuantity(dataMap.get(tempDate).get(deptCode).get("thisYearQuantity"));
					}
					
					// totalQuantity
					if (dataMap.get(tempDate).get(deptCode).get("totalQuantity") != null) {
						stats.setTotalQuantity(dataMap.get(tempDate).get(deptCode).get("totalQuantity"));
					}
				}
	
				// 保存
				Map<String, RegDeptStats> detailMap = statsMap.get(deptCode);
				if (detailMap == null) {
					detailMap = new HashMap<>();
					detailMap.put(tempDate, stats);
				} else {
					detailMap.put(tempDate, stats);
				}
				statsMap.put(deptCode, detailMap);
			}

			// 加一天
			tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
		}
		
		if (!org.springframework.util.CollectionUtils.isEmpty(statsMap)) {
			// 写入
			saveStatsInfos(request, statsMap);
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	@Override
	public String statsInfoForDayWithoutPlatform(RegDeptStatsRequest request) {
		String statsDate = request.getStatsDate();
		Map<String, Dept> depts = getAllDepts(request);
		// 日期/科室/统计
		Map<String, Map<String, Map<String, Integer>>> dataMap = getAllStats(request, depts);

		// 统计到昨天之前，昨天的数据，用轮训做
		String currentDate = DateUtils.getDayForDate(new Date(), -1);

		if (statsDate.compareTo(currentDate) <= 0) {
			Map<String, Map<String, RegDeptStats>> statsMap = new HashMap<>();
			for (Entry<String, Dept> dept: depts.entrySet()) {
				String deptCode = dept.getKey();
				
				Date curDate = new Date();
				RegDeptStats stats = new RegDeptStats();
				stats.setHospitalCode(request.getHospitalCode());
				stats.setPlatform(-1);
				stats.setId(PKGenerator.generateId());
				stats.setStatsDate(statsDate);
				stats.setUpdateTime(DateUtils.dateToString(curDate));
				stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));
				
				// 科室信息
				stats.setDeptCode(deptCode);
				stats.setDeptName(dept.getValue().getDeptName());
				
				stats.setYesterdayQuantity(0);
				stats.setLastWeekQuantity(0);
				stats.setLastMonthQuantity(0);
				stats.setThisMonthQuantity(0);
				stats.setThisYearQuantity(0);
				stats.setTotalQuantity(0);
	
				if (dataMap.get(statsDate) != null && dataMap.get(statsDate).get(deptCode) != null) { 
					// yesterdayQuantity
					if (dataMap.get(statsDate).get(deptCode).get("yesterdayQuantity") != null) {
						stats.setYesterdayQuantity(dataMap.get(statsDate).get(deptCode).get("yesterdayQuantity"));
					}
		
					// lastWeekQuantity
					if (dataMap.get(statsDate).get(deptCode).get("lastWeekQuantity") != null) {
						stats.setLastWeekQuantity(dataMap.get(statsDate).get(deptCode).get("lastWeekQuantity"));
					}
		
					// thisMonthQuantity
					if (dataMap.get(statsDate).get(deptCode).get("thisMonthQuantity") != null) {
						stats.setThisMonthQuantity(dataMap.get(statsDate).get(deptCode).get("thisMonthQuantity"));
					}
		
					// lastMonthQuantity
					if (dataMap.get(statsDate).get(deptCode).get("lastMonthQuantity") != null) {
						stats.setLastMonthQuantity(dataMap.get(statsDate).get(deptCode).get("lastMonthQuantity"));
					}
		
					// thisYearQuantity
					if (dataMap.get(statsDate).get(deptCode).get("thisYearQuantity") != null) {
						stats.setThisYearQuantity(dataMap.get(statsDate).get(deptCode).get("thisYearQuantity"));
					}
					
					// totalQuantity
					if (dataMap.get(statsDate).get(deptCode).get("totalQuantity") != null) {
						stats.setTotalQuantity(dataMap.get(statsDate).get(deptCode).get("totalQuantity"));
					}
				}
	
				// 保存
				Map<String, RegDeptStats> detailMap = statsMap.get(deptCode);
				if (detailMap == null) {
					detailMap = new HashMap<>();
					detailMap.put(statsDate, stats);
				} else {
					detailMap.put(statsDate, stats);
				}
				statsMap.put(deptCode, detailMap);
			}
			
			if (!org.springframework.util.CollectionUtils.isEmpty(statsMap)) {
				saveDailyInfos(request, statsMap);
			}
		}
		

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	// 只用在全平台统计上
	private Map<String, Map<String, Map<String, Integer>>> getAllStats(RegDeptStatsRequest request, Map<String, Dept> depts) {
		Map<String, Map<String, Map<String, Integer>>> resultMap = new HashMap<>();
		
		for (Entry<String, Dept> dept: depts.entrySet()) {
			SolrQuery solrQuery = new SolrQuery();
			String deptCode = dept.getKey();
	
			
			StringBuffer sbQuery = new StringBuffer();
			// 医院
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
			// 平台
			String platform = request.getPlatformValue();
			sbQuery.append(SolrConstant.NOT).append("platform").append(SolrConstant.COLON).append(platform).append(SolrConstant.AND);
			
			// 日期
			if (StringUtils.isNotBlank(request.getStatsDate())) {
				sbQuery.append("statsDate").append(SolrConstant.COLON).append(request.getStatsDate()).append(SolrConstant.AND);
			}
			
			sbQuery.append("deptCode").append(SolrConstant.COLON).append(deptCode);
	
			solrQuery.setQuery(sbQuery.toString());
			
			
			// 开始日期
			if (StringUtils.isNotBlank(request.getBeginDate()) && StringUtils.isNotBlank(request.getEndDate())) {
				StringBuffer sbFilter = new StringBuffer();
				sbFilter.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START)
						.append(request.getBeginDate()).append(SolrConstant.TO).append(request.getEndDate())
						.append(SolrConstant.INTERVAL_END);
				solrQuery.addFilterQuery(sbFilter.toString());
			}
			
			solrQuery.setRows(0);
	
			solrQuery.setGetFieldStatistics(true);
			solrQuery.addGetFieldStatistics("yesterdayQuantity");
			solrQuery.addGetFieldStatistics("lastWeekQuantity");
			solrQuery.addGetFieldStatistics("thisMonthQuantity");
			solrQuery.addGetFieldStatistics("lastMonthQuantity");
			solrQuery.addGetFieldStatistics("thisYearQuantity");
			solrQuery.addGetFieldStatistics("totalQuantity");
			solrQuery.addStatsFieldFacets(null, "statsDate");
	
			try {
				QueryResponse response = solrClient.query(Cores.CORE_STATS_DEPT, solrQuery);
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
							if (resultMap.get(date).get(deptCode) != null) {
								resultMap.get(date).get(deptCode).put(statsField, value);
							} else {
								Map<String, Integer> statsMap = new HashMap<>();
								statsMap.put(statsField, value);
								resultMap.get(date).put(deptCode, statsMap);
							}
						} else {
							Map<String, Integer> statsMap = new HashMap<>();
							statsMap.put(statsField, value);
							Map<String, Map<String, Integer>> deptsMap = new HashMap<>();
							deptsMap.put(deptCode, statsMap);
							resultMap.put(date, deptsMap);
						}
					}
				}
			} catch (Exception e) {
				logger.error("科室统计异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			}
		 }

		return resultMap;
	}

	@Override
	public Map<String, Object> rebuildWithinPlatform(RegDeptStatsRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("request", request);
		
		// 科室/日期/统计
		Map<String, Map<String, RegDeptStats>> statsMap = new HashMap<String, Map<String, RegDeptStats>>();

		// 拿到这个分院下所有科室里面，第一个条数据[最早时间]
		// 后面的，判断下这一天有没有历史累计数，没有则不存。
		String startDate = getOrderStartDate(request, Cores.CORE_STATS_DEPT);
		String endDate = DateUtils.getYesterday(DateUtils.today());
		String statsDate = request.getBeginDate();
		
		
		// 输入的统计时间，需要在开始时间和昨天之间
		if (statsDate.compareTo(startDate) >= 0 && statsDate.compareTo(endDate) <= 0) {
			String tempDate = statsDate;
			
			// 获取所有科室
			Map<String, Dept> depts = getAllDepts(request);
			// 获取日期数据统计  -- 科室/日期/数量
			Map<String, Map<String, Integer>> dailyMap = getDailyQuantity(request);	// 获取每天的统计数据数量
			// 科室/日期/数据
			Map<String, Map<String, RegDeptStats>> dataMap = getStatsDuringDays(request);	
			
			while (tempDate.compareTo(endDate) <= 0) {
				for (Entry<String, Map<String, Integer>> entry : dailyMap.entrySet()) {
					String deptCode = entry.getKey();
					Map<String, Integer> map = entry.getValue();
					Date curDate = new Date();
					RegDeptStats stats = new RegDeptStats();
					stats.setHospitalCode(request.getHospitalCode());
					stats.setPlatform(request.getPlatform());
					stats.setId(PKGenerator.generateId());
					stats.setStatsDate(tempDate);
					stats.setUpdateTime(DateUtils.dateToString(curDate));
					stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));
					
					// 科室
					stats.setDeptCode(deptCode);
					stats.setDeptName(depts.get(deptCode).getDeptName());
					
					stats.setYesterdayQuantity(0);
					stats.setLastWeekQuantity(0);
					stats.setLastMonthQuantity(0);
					stats.setThisMonthQuantity(0);
					stats.setThisYearQuantity(0);
					stats.setTotalQuantity(0);
		
					/**
					 * ------------------------------- 统计数据
					 */
					String preDate = DateUtils.getYesterday(tempDate);

					// 昨日订单数
					int yesterdayQuantity = map.get(preDate) == null ? 0 : map.get(preDate);
					stats.setYesterdayQuantity(yesterdayQuantity);

					// 历史累计订单数
					if (preDate.equals(startDate)) {
						stats.setTotalQuantity(stats.getYesterdayQuantity());
					} else {
						if (preDate.compareTo(statsDate) < 0) {
							stats.setTotalQuantity(dataMap.get(deptCode).get(preDate).getTotalQuantity() + stats.getYesterdayQuantity());
						} else {
							stats.setTotalQuantity(statsMap.get(deptCode).get(preDate).getTotalQuantity() + stats.getYesterdayQuantity());
						}
					}

					// 本月订单：判断日期是否是该月第一天，如果是，则为yesterdayQuantity,如果不是则为前一天数加上yesterdayQuantity
					String firstDayOfMonth = DateUtils.getFirstDayOfThisMonth(preDate);
					if (preDate.equals(firstDayOfMonth)) {
						stats.setThisMonthQuantity(stats.getYesterdayQuantity());
					} else {
						if (firstDayOfMonth.compareTo(statsDate) < 0) {
							if (dataMap.get(deptCode) != null && dataMap.get(deptCode).get(preDate) != null) {
								stats.setThisMonthQuantity(dataMap.get(deptCode).get(preDate).getThisMonthQuantity() + stats.getYesterdayQuantity());
							}
						} else {
							stats.setThisMonthQuantity(statsMap.get(deptCode).get(preDate).getThisMonthQuantity() + stats.getYesterdayQuantity());
						}
					}
					
					// 本年订单
					String firstDayOfYear = DateUtils.getFirstDayOfThisYear(preDate);
					if (preDate.equals(firstDayOfYear)) {
						stats.setThisYearQuantity(stats.getYesterdayQuantity());
					} else {
						if (firstDayOfYear.compareTo(statsDate) < 0) {
							if (dataMap.get(deptCode) != null && dataMap.get(deptCode).get(preDate) != null) {
								stats.setThisYearQuantity(dataMap.get(deptCode).get(preDate).getThisYearQuantity() + stats.getYesterdayQuantity());
							} else {
								stats.setThisYearQuantity(statsMap.get(deptCode).get(preDate).getThisYearQuantity() + stats.getYesterdayQuantity());
							}
						} else {
							stats.setThisYearQuantity(statsMap.get(deptCode).get(preDate).getThisYearQuantity() + stats.getYesterdayQuantity());
						}
					}

					// 上周订单数量 ： 本周第一天的历史累计数量 - 上周第一天的历史累计数量 （每天统计的是前一天的数据）
					int thisWeekQuantity = 0; // 上星期最后一天的数据 英文不知道怎么写了
					int lastWeekQuantity = 0; // 上上星期最后一天的数据 同上哈。

					String firstDayOfThisWeek = DateUtils.getFirstDayOfThisWeek(preDate);
					if (firstDayOfThisWeek.compareTo(statsDate) >= 0) {
						if (statsMap.get(deptCode) != null && statsMap.get(deptCode) != null && statsMap.get(deptCode).get(firstDayOfThisWeek) != null) {
							thisWeekQuantity = statsMap.get(deptCode).get(firstDayOfThisWeek).getTotalQuantity();
						}
					} else {
						if ( dataMap.get(deptCode) != null &&  dataMap.get(deptCode).get(firstDayOfThisWeek) != null) {
							thisWeekQuantity = dataMap.get(deptCode).get(firstDayOfThisWeek).getTotalQuantity();
						}
					}

					String firstDayOfLastWeek = DateUtils.getFirstDayOfLastWeek(preDate);
					if (firstDayOfLastWeek.compareTo(statsDate) >= 0) {
						if (statsMap.get(deptCode) != null && statsMap.get(deptCode) != null && statsMap.get(deptCode).get(firstDayOfLastWeek) != null) {
							lastWeekQuantity = statsMap.get(deptCode).get(firstDayOfLastWeek).getTotalQuantity();
						}
					} else {
						if (dataMap.get(deptCode) != null && dataMap.get(deptCode).get(firstDayOfLastWeek) != null) {
							lastWeekQuantity = dataMap.get(deptCode).get(firstDayOfLastWeek).getTotalQuantity();
						}
					}
					stats.setLastWeekQuantity(thisWeekQuantity - lastWeekQuantity);

					// 上月累计订单 : 本月第一天历史累计数量 - 上月第一天的历史累计数量 （每天统计的是前一天的数据）
					int thisMonthQuantity = 0; // 上月最后一天的数据 英文不知道怎么写了
					int lastMonthQuantity = 0; // 上上月最后一天的数据 同上哈。
					String firstDayOfThisMonth = DateUtils.getFirstDayOfThisMonth(preDate);
					String firstDayOfLastMonth = DateUtils.getFirstDayOfLastMonth(preDate);
					
					if (firstDayOfThisMonth.compareTo(statsDate) >= 0) {
						if (statsMap.get(deptCode) != null && statsMap.get(deptCode).get(firstDayOfThisMonth) != null) {
							thisMonthQuantity = statsMap.get(deptCode).get(firstDayOfThisMonth).getTotalQuantity();
						}
					} else {
						if (dataMap.get(deptCode) != null && dataMap.get(deptCode).get(firstDayOfThisMonth) != null) {
							thisMonthQuantity = dataMap.get(deptCode).get(firstDayOfThisMonth).getTotalQuantity();
						}
					}
					
					if (firstDayOfLastMonth.compareTo(statsDate) >= 0) {
						if (statsMap.get(deptCode) != null && statsMap.get(deptCode).get(firstDayOfLastMonth) != null) {
							lastMonthQuantity = statsMap.get(deptCode).get(firstDayOfLastMonth).getTotalQuantity();
						}
					} else {
						if (dataMap.get(firstDayOfLastMonth) != null && dataMap.get(firstDayOfLastMonth).get(deptCode) != null) {
							lastMonthQuantity = dataMap.get(deptCode).get(firstDayOfLastMonth).getTotalQuantity();
						}
					}
					
					stats.setLastMonthQuantity(thisMonthQuantity - lastMonthQuantity);
		
					// 保存
					Map<String, RegDeptStats> detailMap = statsMap.get(deptCode);
					if (detailMap == null) {
						detailMap = new HashMap<>();
						detailMap.put(tempDate, stats);
					} else {
						detailMap.put(tempDate, stats);
					}
					statsMap.put(deptCode, detailMap);
				}

				// 加一天
				tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
			}

			// 写入
			if (statsMap != null && statsMap.size() > 0) {
				saveRebuildInfos(request, statsMap);
			}
			
			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_SUCCESS);
		} else {
			String resultMsg = String.format("统计时间不在区间内！统计时间: [%s]. 实际开始时间: [%s]. 最大结束时间: [%s].", new Object[]{statsDate, startDate, endDate});
			logger.error(resultMsg);
			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_ERROR);
		}

		return resultMap;
	}
	
	/**
	 * 获取在某段时间内的数据  -- 例如，重建索引的时候，需要获取从一开始，到重建索引的前一天的数据
	 * @return
	 */
	private Map<String, Map<String, RegDeptStats>> getStatsDuringDays(RegDeptStatsRequest request) {
		Map<String, Map<String, RegDeptStats>> resultMap = new HashMap<>();
		
		String coreName = Cores.CORE_STATS_DEPT;
		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		// 医院
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		// 平台
		sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue()).append(SolrConstant.AND);
		// 过滤
		sbQuery.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(SolrConstant.ALL_VALUE)
				.append(SolrConstant.TO).append(request.getBeginDate()).append(SolrConstant.INTERVAL_END);
		solrQuery.setQuery(sbQuery.toString());
		solrQuery.setRows(Integer.MAX_VALUE);
		
		try {
			QueryResponse response = solrClient.query(coreName, solrQuery);
			SolrDocumentList docs = response.getResults();
			if (CollectionUtils.isNotEmpty(docs)) {
				for (SolrDocument doc : docs) {
					String docSource = JSON.toJSONString(doc);
					RegDeptStats stats = JSON.parseObject(docSource, RegDeptStats.class);

					Map<String, RegDeptStats> statsMap = resultMap.get(stats.getDeptCode());
					if (statsMap == null) {
						statsMap = new HashMap<String, RegDeptStats>();
					}
					statsMap.put(stats.getStatsDate(), stats);
					resultMap.put(stats.getDeptCode(), statsMap);
				}
			}
		} catch (Exception e) {
			logger.error("获取旧的科室统计信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}
		
		
		return resultMap;
	}

	@Override
	public Map<String, Object> rebuildWithoutPlatform(RegDeptStatsRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("request", request);
		
		String statsDate = request.getBeginDate();
		String startDate = getOrderStartDate(request, Cores.CORE_STATS_DEPT);
		String tempDate = startDate;
		String endDate = DateUtils.getDayForDate(new Date(), -1);
		if (statsDate.compareTo(startDate) >= 0 && statsDate.compareTo(endDate) <= 0) {
			Map<String, Map<String, RegDeptStats>> statsMap = new HashMap<>();
			Map<String, Dept> depts = getAllDepts(request);
			// 日期/科室/统计
			RegDeptStatsRequest tempRequest = new RegDeptStatsRequest();
			BeanUtils.copyProperties(request, tempRequest);
			tempRequest.setBeginDate(request.getBeginDate());
			tempRequest.setEndDate(SolrConstant.ALL_VALUE);
			Map<String, Map<String, Map<String, Integer>>> dataMap = getAllStats(tempRequest, depts);

			while (tempDate.compareTo(endDate) <= 0) {
				for (Entry<String, Dept> dept: depts.entrySet()) {
					String deptCode = dept.getKey();
					Date curDate = new Date();
					RegDeptStats stats = new RegDeptStats();
					stats.setHospitalCode(request.getHospitalCode());
					stats.setPlatform(-1);
					stats.setId(PKGenerator.generateId());
					stats.setStatsDate(tempDate);
					stats.setUpdateTime(DateUtils.dateToString(curDate));
					stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));
					
					// 科室
					stats.setDeptCode(deptCode);
					stats.setDeptName(dept.getValue().getDeptName());
					
					stats.setYesterdayQuantity(0);
					stats.setLastWeekQuantity(0);
					stats.setLastMonthQuantity(0);
					stats.setThisMonthQuantity(0);
					stats.setThisYearQuantity(0);
					stats.setTotalQuantity(0);
		
					// !!!!! 如果是旧数据，那么就查出来，如果是新的，在这天之后的，那么就只能从新的内存map中拿 
					
					if (dataMap.get(tempDate) != null && dataMap.get(tempDate).get(deptCode) != null) {
						// yesterdayQuantity
						if (dataMap.get(tempDate).get(deptCode).get("yesterdayQuantity") != null) {
							stats.setYesterdayQuantity(dataMap.get(tempDate).get(deptCode).get("yesterdayQuantity"));
						}
			
						// lastWeekQuantity
						if (dataMap.get(tempDate).get(deptCode).get("lastWeekQuantity") != null) {
							stats.setLastWeekQuantity(dataMap.get(tempDate).get(deptCode).get("lastWeekQuantity"));
						}
			
						// thisMonthQuantity
						if (dataMap.get(tempDate).get(deptCode).get("thisMonthQuantity") != null) {
							stats.setThisMonthQuantity(dataMap.get(tempDate).get(deptCode).get("thisMonthQuantity"));
						} 
			
						// lastMonthQuantity
						if (dataMap.get(tempDate).get(deptCode).get("lastMonthQuantity") != null) {
							stats.setLastMonthQuantity(dataMap.get(tempDate).get(deptCode).get("lastMonthQuantity"));
						}
			
						// thisYearQuantity
						if (dataMap.get(tempDate).get(deptCode).get("thisYearQuantity") != null) {
							stats.setThisYearQuantity(dataMap.get(tempDate).get(deptCode).get("thisYearQuantity"));
						}
						
						// totalQuantity
						if (dataMap.get(tempDate).get(deptCode).get("totalQuantity") != null) {
							stats.setTotalQuantity(dataMap.get(tempDate).get(deptCode).get("totalQuantity"));
						}
					}
		
					// 保存
					Map<String, RegDeptStats> detailMap = statsMap.get(deptCode);
					if (detailMap == null) {
						detailMap = new HashMap<>();
						detailMap.put(tempDate, stats);
					} else {
						detailMap.put(tempDate, stats);
					}
					statsMap.put(deptCode, detailMap);
				}
	
				// 加一天
				tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
			}
			
			if (!org.springframework.util.CollectionUtils.isEmpty(statsMap)) {
				// 写入
				saveRebuildInfos(request, statsMap);
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
