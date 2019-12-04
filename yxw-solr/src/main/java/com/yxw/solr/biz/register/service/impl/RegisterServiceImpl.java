package com.yxw.solr.biz.register.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

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

import com.alibaba.fastjson.JSON;
import com.yxw.commons.dto.outside.OrdersQueryResult;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.solr.biz.common.callable.BizInfosQueryCallable;
import com.yxw.solr.biz.outside.callable.OutsideOrderCallable;
import com.yxw.solr.biz.outside.comparator.OrdersQueryResultComparator;
import com.yxw.solr.biz.register.service.RegisterService;
import com.yxw.solr.client.YxwSolrClient;
import com.yxw.solr.client.YxwUpdateClient;
import com.yxw.solr.constants.BizConstant;
import com.yxw.solr.constants.Cores;
import com.yxw.solr.constants.PlatformConstant;
import com.yxw.solr.constants.PoolConstant;
import com.yxw.solr.constants.ResultConstant;
import com.yxw.solr.constants.SolrConstant;
import com.yxw.solr.utils.UTCDateUtils;
import com.yxw.solr.vo.CoreVo;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.YxwResult;
import com.yxw.solr.vo.register.RegInfoRequest;
import com.yxw.solr.vo.register.RegStats;
import com.yxw.solr.vo.register.RegStatsRequest;
import com.yxw.utils.DateUtils;

@Service(value = "registerService")
public class RegisterServiceImpl implements RegisterService {

	private Logger logger = LoggerFactory.getLogger(RegisterService.class);

	private YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);

	@Override
	public YxwResponse findOrders(RegInfoRequest request) {
		YxwResponse response = new YxwResponse();
		YxwResult yxwResult = new YxwResult();
		response.setResult(yxwResult);

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("findRegisterOrders"));
		List<FutureTask<Map<String, Object>>> tasks = new ArrayList<FutureTask<Map<String, Object>>>();

		try {
			Map<String, Object> queryParamMap = getQueryParams(request);
			String core = (String) queryParamMap.get("core");
			SolrQuery solrQuery = (SolrQuery) queryParamMap.get("solrQuery");
			if (solrQuery != null) {
				if (StringUtils.isBlank(core)) {
					// 没指定core则查全部
					for (String coreName : PlatformConstant.registerList) {
						Callable<Map<String, Object>> callable = new BizInfosQueryCallable(coreName, solrQuery);
						FutureTask<Map<String, Object>> task = new FutureTask<Map<String, Object>>(callable);
						tasks.add(task);
						executorService.submit(task);
					}
				} else {
					Callable<Map<String, Object>> callable = new BizInfosQueryCallable(core, solrQuery);
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

			} else {
				String errorMsg = "invalid params to generate SolrQuery params. params: " + JSON.toJSONString(request);
				logger.warn(errorMsg);
				response.setResultCode(ResultConstant.RESULT_CODE_ERROR);
				response.setResultMessage(errorMsg);
			}
		} catch (Exception e) {
			logger.error("findOrders error. params: {}. errorMsg: {}. cause: {}.",
					new Object[] { JSON.toJSONString(request), e.getMessage(), e.getCause() });

			response.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			response.setResultMessage(e.getMessage());
		} finally {
			executorService.shutdown();
		}

		return response;
	}

	private Map<String, Object> getQueryParams(RegInfoRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SolrQuery solrQuery = new SolrQuery();

		/*---------------------------- 查询条件 ----------------------------*/
		StringBuffer sbQuery = new StringBuffer();
		// 医院
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());
		// 挂号渠道
		if (request.getPlatform() != -1) {
			sbQuery.append(SolrConstant.AND).append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		}

		// 挂号类型
		Integer regType = request.getRegType();
		if (regType != null && regType.intValue() != -1) {
			sbQuery.append(SolrConstant.AND).append("regType").append(SolrConstant.COLON).append(regType);
		}
		// 挂号状态
		Integer bizStatus = request.getBizStatus();
		if (bizStatus != null && bizStatus.intValue() != -1) {
			sbQuery.append(SolrConstant.AND).append("bizStatus").append(SolrConstant.COLON).append(bizStatus);
		}
		// 挂号院区
		String branchCode = request.getBranchCode();
		if (StringUtils.isNotBlank(branchCode) && !branchCode.equals("-1")) {
			sbQuery.append(SolrConstant.AND).append("branchCode").append(SolrConstant.COLON).append(branchCode);
		}
		// 挂号科室
		String deptName = request.getDeptName();
		if (StringUtils.isNotBlank(deptName)) {
			sbQuery.append(SolrConstant.AND).append("deptName").append(SolrConstant.COLON).append(deptName);
		}
		// 挂号医生
		String doctorName = request.getDoctorName();
		if (StringUtils.isNotBlank(doctorName)) {
			sbQuery.append(SolrConstant.AND).append("doctorName").append(SolrConstant.COLON).append(doctorName);
		}
		// 患者姓名
		String patientName = request.getPatientName();
		if (StringUtils.isNotBlank(patientName)) {
			sbQuery.append(SolrConstant.AND).append("patientName").append(SolrConstant.COLON).append(patientName);
		}
		// 患者卡号
		String cardNo = request.getCardNo();
		if (StringUtils.isNotBlank(cardNo)) {
			sbQuery.append(SolrConstant.AND).append("cardNo").append(SolrConstant.COLON).append(cardNo);
		}
		// 患者手机
		String patientMobile = request.getPatientMobile();
		if (StringUtils.isNotBlank(patientMobile)) {
			sbQuery.append(SolrConstant.AND).append("patientMobile").append(SolrConstant.COLON).append(patientMobile);
		}

		solrQuery.setQuery(sbQuery.toString());
		/*---------------------------- end of 查询条件 ----------------------------*/

		List<String> filterQuerys = new ArrayList<String>();
		/*---------------------------- 挂号时间过滤(createDate) ----------------------------*/
		String regBeginDate = request.getRegBeginTime();
		String regEndDate = request.getRegEndTime();
		if (StringUtils.isNotBlank(regBeginDate)) {
			StringBuffer sbFilter = new StringBuffer("createDate");
			sbFilter.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(regBeginDate).append(SolrConstant.TO);
			sbFilter.append(StringUtils.isNotBlank(regEndDate) ? regEndDate : SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
			filterQuerys.add(sbFilter.toString());
		} else {
			if (StringUtils.isNotBlank(regEndDate)) {
				StringBuffer sbFilter = new StringBuffer("createDate");
				sbFilter.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(SolrConstant.ALL_VALUE).append(SolrConstant.TO);
				sbFilter.append(regEndDate).append(SolrConstant.INTERVAL_END);
				filterQuerys.add(sbFilter.toString());
			}
		}
		/*---------------------------- end of 挂号时间过滤 ----------------------------*/

		/*---------------------------- 就诊时间过滤(scheduleDate) ----------------------------*/
		String schBeginDate = request.getVisitBeginTime();
		String schEndDate = request.getVisitEndTime();
		if (StringUtils.isNotBlank(schBeginDate)) {
			StringBuffer sbFilter = new StringBuffer("scheduleDate");
			sbFilter.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(schBeginDate).append(SolrConstant.TO);
			sbFilter.append(StringUtils.isNotBlank(schEndDate) ? schEndDate : SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
			filterQuerys.add(sbFilter.toString());
		} else {
			if (StringUtils.isNotBlank(schEndDate)) {
				StringBuffer sbFilter = new StringBuffer("scheduleDate");
				sbFilter.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(SolrConstant.ALL_VALUE).append(SolrConstant.TO);
				sbFilter.append(schEndDate).append(SolrConstant.INTERVAL_END);
				filterQuerys.add(sbFilter.toString());
			}
		}
		/*---------------------------- end of 就诊时间过滤----------------------------*/

		if (filterQuerys.size() > 0) {
			String[] filterAry = new String[filterQuerys.size()];
			solrQuery.addFilterQuery((String[]) filterQuerys.toArray(filterAry));
		}

		// 分页获取数据
		solrQuery.set(CommonParams.START, request.getPageSize() * (request.getPageIndex() - 1));
		solrQuery.set(CommonParams.ROWS, request.getPageSize());

		solrQuery.setSort("registerTime", ORDER.desc);

		// coreName 由platform确定，与业务关联紧密，后续补全
		resultMap.put("solrQuery", solrQuery);

		return resultMap;
	}

	@Override
	public String statsInfosWithinPlatform(RegStatsRequest request) {
		String startDate = getStartDate(request);
		String endDate = DateUtils.getDayForDate(new Date(), -1);

		Map<String, Object> statsMap = getStatsWithinPlatform(request, startDate, endDate);

		// 写入
		if (statsMap != null && statsMap.size() > 0) {
			saveStatsInfos(request, statsMap.values());
			logger.info(String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. 索引新增并优化完成...",
					new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() }));
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
	private String getStartDate(RegStatsRequest request) {
		String startDate = DateUtils.today();

		SolrQuery solrQuery = new SolrQuery();

		try {
			StringBuffer sbQuery = new StringBuffer();
			// 医院
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());

			if (request.getPlatform() != -1) {
				solrQuery.setQuery(sbQuery.toString());
				solrQuery.addField("payDate");
				solrQuery.setRows(1);
				solrQuery.addSort("payDate", ORDER.asc);

				QueryResponse queryResponse = solrClient.query(request.getCoreName(), solrQuery);
				SolrDocumentList documentList = queryResponse.getResults();
				if (CollectionUtils.isNotEmpty(documentList)) {
					startDate = (String) documentList.get(0).getFieldValue("payDate");
				}
			} else {
				solrQuery.setQuery(sbQuery.toString());
				solrQuery.addField("statsDate");
				solrQuery.setRows(1);
				solrQuery.addSort("statsDate", ORDER.asc);

				QueryResponse queryResponse = solrClient.query(Cores.CORE_STATS_REGISTER, solrQuery);
				SolrDocumentList documentList = queryResponse.getResults();
				if (CollectionUtils.isNotEmpty(documentList)) {
					startDate = (String) documentList.get(0).getFieldValue("statsDate");
				}
			}

			if (StringUtils.isBlank(startDate)) {
				startDate = DateUtils.today();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return startDate;
	}

	/**
	 * 从开始日期进行数据保存
	 * @param request
	 * @param collection
	 */
	private void saveStatsInfos(RegStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		YxwUpdateClient.addBeans(Cores.CORE_STATS_REGISTER, collection, true, sb.toString());
	}
	
	/**
	 * 从指定开始日期，进行数据保存（也就是需要删除这天之后的数据再进行插入）
	 * @param request
	 * @param collection
	 */
	private void saveRebuildStatsInfos(RegStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		
		// 范围条件
		sb.append(SolrConstant.AND).append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START)
		  .append(request.getBeginDate()).append(SolrConstant.TO).append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
		
		YxwUpdateClient.addBeans(Cores.CORE_STATS_REGISTER, collection, true, sb.toString());
	}

	/**
	 * 保存一天数据（先删除后新增）
	 * @param stats
	 */
	private void saveStatsInfo(RegStats stats) {
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
		YxwUpdateClient.addBean(Cores.CORE_STATS_REGISTER, stats, true, sb.toString());
	}

	/**
	 * 当班支付统计 -- payDate统计
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Map<String, Object>> getDutyStats(RegStatsRequest request) {
		Map<String, Map<String, Object>> resultMap = new HashMap<String, Map<String, Object>>();
		SolrQuery solrQuery = new SolrQuery();

		try {
			// 查询条件
			StringBuffer sbQuery = new StringBuffer();
			// 医院
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());
			// 分院
			if (!request.getBranchCode().equals("-1")) {
				sbQuery.append(SolrConstant.AND).append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue());
			}
			// 平台 挂号的，使用regMode作为platform写入
			if (request.getPlatform() != -1) {
				sbQuery.append(SolrConstant.AND).append("platform").append(SolrConstant.COLON).append(request.getPlatform());
			}
			// 支付状态 payStatus:2 已支付 -- @see OrderConstant
			// sbQuery.append(SolrConstant.AND).append("payStatus").append(SolrConstant.COLON).append("2");
			// 挂号类型 1预约，2当班 -- @see RegisterConstant
			sbQuery.append(SolrConstant.AND).append("regType").append(SolrConstant.COLON).append("2");

			solrQuery.setQuery(sbQuery.toString());

			// 开始时间
			String beginDate = request.getBeginDate();
			if (StringUtils.isBlank(beginDate)) {
				beginDate = "0";		// 这里限制最小值，不建议用*
			}
			
			// 结束时间
			String endDate = request.getEndDate();
			if (StringUtils.isBlank(endDate)) {
				endDate = SolrConstant.ALL_VALUE;                                        
			}
			
			solrQuery.addFilterQuery("payDate".concat(SolrConstant.COLON).concat(SolrConstant.INTERVAL_START).concat(beginDate)
					.concat(SolrConstant.TO).concat(endDate).concat(SolrConstant.INTERVAL_END));

			// 过滤条件 -- payTime > 0
/*			StringBuffer sbFilter = new StringBuffer();
			sbFilter.append("payTime").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(0).append(SolrConstant.TO)
					.append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
			solrQuery.addFilterQuery(sbFilter.toString());*/

			// 统计信息
			solrQuery.setGetFieldStatistics(true);
			solrQuery.addGetFieldStatistics("totalFee");
			solrQuery.addStatsFieldFacets("totalFee", "payDate");

			// 查询信息
			solrQuery.setRows(0);

			QueryResponse response = solrClient.query(request.getCoreName(), solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response.getResponse()
					.asMap(100);
			resultMap = responseMap.get("stats").get("stats_fields").get("totalFee").get("facets").get("payDate");
		} catch (Exception e) {
			logger.error("getDutyStats error. params: {}. errorMsg: {}. cause by: {}.",
					new Object[] { JSON.toJSONString(request), e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	/**
	 * 预约支付统计 -- payDate统计
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Map<String, Object>> getAppointmentStats(RegStatsRequest request) {
		Map<String, Map<String, Object>> resultMap = new HashMap<String, Map<String, Object>>();
		SolrQuery solrQuery = new SolrQuery();

		try {
			// 查询条件
			StringBuffer sbQuery = new StringBuffer();
			// 医院
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());
			// 分院
			if (!request.getBranchCode().equals("-1")) {
				sbQuery.append(SolrConstant.AND).append("branchCode").append(SolrConstant.COLON).append(request.getBranchCode());
			}
			// 平台 挂号的，使用regMode作为platform写入
			if (request.getPlatform() != -1) {
				sbQuery.append(SolrConstant.AND).append("platform").append(SolrConstant.COLON).append(request.getPlatform());
			}
			// 支付状态 payStatus:2 已支付 -- @see OrderConstant
			// sbQuery.append(SolrConstant.AND).append("payStatus").append(SolrConstant.COLON).append("2");
			// 挂号类型 1预约，2当班 -- @see RegisterConstant
			sbQuery.append(SolrConstant.AND).append("regType").append(SolrConstant.COLON).append("1");

			solrQuery.setQuery(sbQuery.toString());

			// 过滤条件
			

			// 过滤条件 -- payTime > 0
			StringBuffer sbFilter = new StringBuffer();
			sbFilter.append("payTime").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(0).append(SolrConstant.TO)
					.append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
			solrQuery.addFilterQuery(sbFilter.toString());

			// 统计信息
			solrQuery.setGetFieldStatistics(true);
			solrQuery.addGetFieldStatistics("totalFee");
			solrQuery.addStatsFieldFacets("totalFee", "payDate");

			// 查询信息
			solrQuery.setRows(0);

			QueryResponse response = solrClient.query(request.getCoreName(), solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response.getResponse()
					.asMap(100);
			resultMap = responseMap.get("stats").get("stats_fields").get("totalFee").get("facets").get("payDate");
		} catch (Exception e) {
			logger.error("getDutyStats error. params: {}. errorMsg: {}. cause by: {}.",
					new Object[] { JSON.toJSONString(request), e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	/**
	 * 已退费统计 -- 用refundDate统计
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Map<String, Object>> getRefundStats(RegStatsRequest request) {
		Map<String, Map<String, Object>> resultMap = new HashMap<String, Map<String, Object>>();
		SolrQuery solrQuery = new SolrQuery();

		try {
			// 查询条件
			StringBuffer sbQuery = new StringBuffer();
			// 医院
			sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());
			// 分院
			if (!request.getBranchCode().equals("-1")) {
				sbQuery.append(SolrConstant.AND).append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue());
			}
			// 平台 挂号的，使用regMode作为platform写入
			if (request.getPlatform() != -1) {
				sbQuery.append(SolrConstant.AND).append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
			}
			// 支付状态 payStatus:3 已退费 -- @see OrderConstant
			sbQuery.append(SolrConstant.AND).append("payStatus").append(SolrConstant.COLON).append("3");

			solrQuery.setQuery(sbQuery.toString());

			// 过滤条件
			if (StringUtils.isNotBlank(request.getBeginDate())) {
				if (StringUtils.isNotBlank(request.getEndDate())) {
					solrQuery.setFilterQueries("refundDate".concat(SolrConstant.COLON).concat(SolrConstant.INTERVAL_START)
							.concat(request.getBeginDate()).concat(SolrConstant.TO).concat(request.getEndDate()).concat(SolrConstant.INTERVAL_END));
				} else {
					solrQuery.setFilterQueries("refundDate".concat(SolrConstant.COLON).concat(SolrConstant.INTERVAL_START)
							.concat(request.getBeginDate()).concat(SolrConstant.TO).concat(SolrConstant.ALL_VALUE).concat(SolrConstant.INTERVAL_END));
				}
			} else {
				if (StringUtils.isNotBlank(request.getEndDate())) {
					solrQuery.setFilterQueries("refundDate".concat(SolrConstant.COLON).concat(SolrConstant.INTERVAL_START).concat(SolrConstant.ALL_VALUE)
							.concat(SolrConstant.TO).concat(request.getEndDate()).concat(SolrConstant.INTERVAL_END));
				}
			}

			// 统计信息
			solrQuery.setGetFieldStatistics(true);
			solrQuery.addGetFieldStatistics("totalFee");
			solrQuery.addStatsFieldFacets("totalFee", "refundDate");

			// 查询信息
			solrQuery.setRows(0);

			QueryResponse response = solrClient.query(request.getCoreName(), solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response.getResponse()
					.asMap(100);
			resultMap = responseMap.get("stats").get("stats_fields").get("totalFee").get("facets").get("refundDate");
		} catch (Exception e) {
			logger.error("getRefundStats error. params: {}. errorMsg: {}. cause by: {}.",
					new Object[] { JSON.toJSONString(request), e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	@Override
	public String statsInfoForDayWithinPlatform(RegStatsRequest request) {
		String tempDate = request.getStatsDate();

		RegStatsRequest yesterdayRequest = new RegStatsRequest();
		BeanUtils.copyProperties(request, yesterdayRequest);
		yesterdayRequest.setBeginDate(tempDate);
		yesterdayRequest.setEndDate(tempDate);

		Map<String, Map<String, Object>> dutysMap = getDutyStats(yesterdayRequest);
		Map<String, Map<String, Object>> appointmentsMap = getAppointmentStats(yesterdayRequest);
		Map<String, Map<String, Object>> renfundsMap = getRefundStats(yesterdayRequest);

		RegStats regStats = new RegStats();
		// 基本信息
		regStats.setId(PKGenerator.generateId());
		regStats.setHospitalCode(request.getHospitalCode());
		regStats.setBranchCode(request.getBranchCode());
		regStats.setPlatform(request.getPlatform());
		regStats.setStatsDate(tempDate);
		Date date = new Date();
		regStats.setUpdateTime_utc(UTCDateUtils.parseDate(date));
		regStats.setUpdateTime(DateUtils.getDateStr(date));

		// 统计信息
		Map<String, Object> dutyMap = dutysMap.get(tempDate);
		regStats.setDutyPaidQuantity(dutyMap != null ? Integer.valueOf(dutyMap.get("count").toString()) : 0);
		regStats.setDutyPaidAmount(dutyMap != null ? Double.valueOf(dutyMap.get("sum").toString()).intValue() : 0);

		Map<String, Object> appointmentMap = appointmentsMap.get(tempDate);
		regStats.setAppointmentPaidQuantity(appointmentMap != null ? Integer.valueOf(appointmentMap.get("count").toString()) : 0);
		regStats.setAppointmentPaidAmount(appointmentMap != null ? Double.valueOf(appointmentMap.get("sum").toString()).intValue() : 0);

		Map<String, Object> renfundMap = renfundsMap.get(tempDate);
		regStats.setRefundQuantity(renfundMap != null ? Integer.valueOf(renfundMap.get("count").toString()) : 0);
		regStats.setRefundAmount(renfundMap != null ? Double.valueOf(renfundMap.get("sum").toString()).intValue() : 0);

		regStats.setPaidQuantity(regStats.getAppointmentPaidQuantity() + regStats.getDutyPaidQuantity());
		regStats.setPaidAmount(regStats.getAppointmentPaidAmount() + regStats.getDutyPaidAmount());

		// 写入
		saveStatsInfo(regStats);
		logger.info(String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. 索引新增并优化完成...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() }));

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	@Override
	public YxwResponse getStatsInfos(RegStatsRequest request) {
		YxwResponse response = new YxwResponse();
		YxwResult yxwResult = new YxwResult();
		response.setResult(yxwResult);

		String coreName = Cores.CORE_STATS_REGISTER;

		SolrQuery solrQuery = new SolrQuery();

		String queryString = SolrConstant.QUERY_ALL;
		// 医院
		queryString = queryString.concat(SolrConstant.AND).concat("hospitalCode").concat(SolrConstant.COLON).concat(request.getHospitalCode());
		// 分院
		queryString = queryString.concat(SolrConstant.AND).concat("branchCode").concat(SolrConstant.COLON).concat(request.getBranchCodeValue());
		// 平台
		queryString = queryString.concat(SolrConstant.AND).concat("platform").concat(SolrConstant.COLON).concat(request.getPlatformValue());

		solrQuery.setQuery(queryString);

		// 分页查询
		solrQuery.set(CommonParams.START, request.getPageSize() * (request.getPageIndex() - 1));
		solrQuery.set(CommonParams.ROWS, request.getPageSize());

		// 设置排序， 此处默认降序
		solrQuery.setSort("statsDate", ORDER.desc);

		// 时间范围
		StringBuffer sbQuery = new StringBuffer();
		if (StringUtils.isNotBlank(request.getBeginDate())) {
			if (StringUtils.isNotBlank(request.getEndDate())) {
				// 有开始时间、结束时间
				sbQuery.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(request.getBeginDate())
						.append(SolrConstant.TO).append(request.getEndDate()).append(SolrConstant.INTERVAL_END);
			} else {
				// 只有开始时间
				sbQuery.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(request.getBeginDate())
						.append(SolrConstant.TO).append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
			}

			solrQuery.setFilterQueries(sbQuery.toString());
		} else {
			if (StringUtils.isNotBlank(request.getEndDate())) {
				// 只指定了结束时间
				sbQuery.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(SolrConstant.ALL_VALUE)
						.append(SolrConstant.TO).append(request.getEndDate()).append(SolrConstant.INTERVAL_END);
				solrQuery.setFilterQueries(sbQuery.toString());
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

		} catch (Exception e) {
			logger.error("获取绑卡统计数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			response.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			response.setResultMessage(e.getMessage());
		}

		return response;
	}

	@Override
	public String statsInfosWithoutPlatform(RegStatsRequest request) {
		String startDate = getStartDate(request);
		// 统计到昨天之前，昨天的数据，用轮训做
		String endDate = DateUtils.getDayForDate(new Date(), -1);
		
		Map<String, Object> statsMap = getStatsWithoutPlatform(request, startDate, endDate);

		if (!org.springframework.util.CollectionUtils.isEmpty(statsMap)) {
			// 写入
			saveStatsInfos(request, statsMap.values());
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	@Override
	public String statsInfoForDayWithoutPlatform(RegStatsRequest request) {
		String statsDate = request.getStatsDate();
		// 统计到昨天之前，昨天的数据，用轮训做
		String currentDate = DateUtils.today();
		String tempDate = statsDate;

		if (tempDate.compareTo(currentDate) < 0) {
			Map<String, Map<String, Integer>> dataMap = getAllStats(request.getHospitalCode(), request.getBranchCodeValue(), statsDate, null);
			Date curDate = new Date();
			RegStats stats = new RegStats();
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
			stats.setAppointmentPaidAmount(0);
			stats.setAppointmentPaidQuantity(0);
			stats.setDutyPaidAmount(0);
			stats.setDutyPaidQuantity(0);

			if (dataMap.get(tempDate) != null) {
				// paidQuantity
				if (dataMap.get(tempDate).get("paidQuantity") != null) {
					stats.setPaidQuantity(dataMap.get(tempDate).get("paidQuantity"));
				}

				// paidAmount
				if (dataMap.get(tempDate).get("paidAmount") != null) {
					stats.setPaidAmount(dataMap.get(tempDate).get("paidAmount"));
				}

				// refundQuantity
				if (dataMap.get(tempDate).get("refundQuantity") != null) {
					stats.setRefundQuantity(dataMap.get(tempDate).get("refundQuantity"));
				}

				// refundAmount
				if (dataMap.get(tempDate).get("refundAmount") != null) {
					stats.setRefundAmount(dataMap.get(tempDate).get("refundAmount"));
				}

				// appointmentPaidQuantity
				if (dataMap.get(tempDate).get("appointmentPaidQuantity") != null) {
					stats.setAppointmentPaidQuantity(dataMap.get(tempDate).get("appointmentPaidQuantity"));
				}

				// appointmentPaidAmount
				if (dataMap.get(tempDate).get("appointmentPaidAmount") != null) {
					stats.setAppointmentPaidAmount(dataMap.get(tempDate).get("appointmentPaidAmount"));
				}

				// dutyPaidQuantity
				if (dataMap.get(tempDate).get("dutyPaidQuantity") != null) {
					stats.setDutyPaidQuantity(dataMap.get(tempDate).get("dutyPaidQuantity"));
				}

				// dutyPaidAmount
				if (dataMap.get(tempDate).get("dutyPaidAmount") != null) {
					stats.setDutyPaidAmount(dataMap.get(tempDate).get("dutyPaidAmount"));
				}
			}

			// 保存
			saveStatsInfo(stats);
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	private Map<String, Map<String, Integer>> getAllStats(String hospitalCode, String branchCode, String statsDate, String startDate) {
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
		
		// 开始日期
		if (StringUtils.isNotBlank(startDate)) {
			StringBuffer sbFilter = new StringBuffer();
			sbFilter.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START)
					.append(startDate).append(SolrConstant.TO).append(SolrConstant.ALL_VALUE)
					.append(SolrConstant.INTERVAL_END);
			solrQuery.setFilterQueries(sbFilter.toString());
		}

		solrQuery.setGetFieldStatistics(true);
		solrQuery.addGetFieldStatistics("paidQuantity");
		solrQuery.addGetFieldStatistics("paidAmount");
		solrQuery.addGetFieldStatistics("refundQuantity");
		solrQuery.addGetFieldStatistics("refundAmount");
		solrQuery.addGetFieldStatistics("appointmentPaidQuantity");
		solrQuery.addGetFieldStatistics("appointmentPaidAmount");
		solrQuery.addGetFieldStatistics("dutyPaidQuantity");
		solrQuery.addGetFieldStatistics("dutyPaidAmount");
		solrQuery.addStatsFieldFacets(null, "statsDate");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_REGISTER, solrQuery);
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
			logger.error("获取全平台门诊统计数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> rebuildWithinPlatform(RegStatsRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("request", request);

		String startDate = getStartDate(request);
		String statsDate = request.getBeginDate();
		String endDate = DateUtils.getDayForDate(new Date(), -1);

		if (statsDate.compareTo(startDate) >= 0 && statsDate.compareTo(endDate) <= 0) {
			Map<String, Object> statsMap = getStatsWithinPlatform(request, startDate, endDate);
	
			// 写入
			if (statsMap != null && statsMap.size() > 0) {
				saveRebuildStatsInfos(request, statsMap.values());
				logger.info(String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. 索引新增并优化完成...",
						new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() }));
			}
			
			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_SUCCESS);
		} else {
			String resultMsg = String.format("统计时间不在区间内！统计时间: [%s]. 实际开始时间: [%s]. 最大结束时间: [%s].", new Object[]{statsDate, startDate, endDate});
			logger.error(resultMsg);
			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_ERROR);
		}
		
		return resultMap;
	}
	
	private Map<String, Object> getStatsWithinPlatform(RegStatsRequest request, String startDate, String endDate) {
		Map<String, Object> resultMap = new HashMap<>();
		
		// 给request加一个查询范围，加快查询速度
		request.setBeginDate(startDate);
		request.setEndDate(endDate);
		
		// 获取当班支付
		Map<String, Map<String, Object>> dutyMap = getDutyStats(request);
		// 获取预约支付
		Map<String, Map<String, Object>> appointmentMap = getAppointmentStats(request);
		// 获取退费
		Map<String, Map<String, Object>> refundMap = getRefundStats(request);
		
		String tempDate = startDate;
		
		while (tempDate.compareTo(endDate) <= 0) {
			RegStats regStats = new RegStats();
			// 基本信息
			regStats.setId(PKGenerator.generateId());
			regStats.setHospitalCode(request.getHospitalCode());
			regStats.setBranchCode(request.getBranchCode());
			regStats.setPlatform(request.getPlatform());
			regStats.setStatsDate(tempDate);
			Date date = new Date();
			regStats.setUpdateTime_utc(UTCDateUtils.parseDate(date));
			regStats.setUpdateTime(DateUtils.getDateStr(date));
			// 统计信息
			Map<String, Object> dutys = dutyMap.get(tempDate);
			regStats.setDutyPaidQuantity(dutys != null ? Integer.valueOf(dutys.get("count").toString()) : 0);
			regStats.setDutyPaidAmount(dutys != null ? Double.valueOf(dutys.get("sum").toString()).intValue() : 0);

			Map<String, Object> appointments = appointmentMap.get(tempDate);
			regStats.setAppointmentPaidQuantity(appointments != null ? Integer.valueOf(appointments.get("count").toString()) : 0);
			regStats.setAppointmentPaidAmount(appointments != null ? Double.valueOf(appointments.get("sum").toString()).intValue() : 0);

			Map<String, Object> refunds = refundMap.get(tempDate);
			regStats.setRefundQuantity(refunds != null ? Integer.valueOf(refunds.get("count").toString()) : 0);
			regStats.setRefundAmount(refunds != null ? Double.valueOf(refunds.get("sum").toString()).intValue() : 0);

			regStats.setPaidQuantity(regStats.getAppointmentPaidQuantity() + regStats.getDutyPaidQuantity());
			regStats.setPaidAmount(regStats.getAppointmentPaidAmount() + regStats.getDutyPaidAmount());
			
			resultMap.put(tempDate, regStats);

			// 加一天
			tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
		}
		
		return resultMap;
	}

	@Override
	public Map<String, Object> rebuildWithoutPlatform(RegStatsRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("request", request);

		String statsDate = request.getBeginDate();
		String startDate = getStartDate(request);
		String endDate = DateUtils.getDayForDate(new Date(), -1);
		
		if (statsDate.compareTo(startDate) >= 0 && statsDate.compareTo(endDate) <= 0) {
			Map<String, Object> statsMap = getStatsWithoutPlatform(request, statsDate, endDate);
	
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
	
	private Map<String, Object> getStatsWithoutPlatform(RegStatsRequest request, String beginDate, String endDate) {
		Map<String, Object> statsMap = new HashMap<>();
		Map<String, Map<String, Integer>> dataMap = getAllStats(request.getHospitalCode(), request.getBranchCodeValue(), null, beginDate);
		String tempDate = beginDate;
		
		while (tempDate.compareTo(endDate) <= 0) {
			Date curDate = new Date();
			RegStats stats = new RegStats();
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
			stats.setAppointmentPaidAmount(0);
			stats.setAppointmentPaidQuantity(0);
			stats.setDutyPaidAmount(0);
			stats.setDutyPaidQuantity(0);

			if (dataMap.get(tempDate) != null) {
				// paidQuantity
				if (dataMap.get(tempDate).get("paidQuantity") != null) {
					stats.setPaidQuantity(dataMap.get(tempDate).get("paidQuantity"));
				}

				// paidAmount
				if (dataMap.get(tempDate).get("paidAmount") != null) {
					stats.setPaidAmount(dataMap.get(tempDate).get("paidAmount"));
				}

				// refundQuantity
				if (dataMap.get(tempDate).get("refundQuantity") != null) {
					stats.setRefundQuantity(dataMap.get(tempDate).get("refundQuantity"));
				}

				// refundAmount
				if (dataMap.get(tempDate).get("refundAmount") != null) {
					stats.setRefundAmount(dataMap.get(tempDate).get("refundAmount"));
				}

				// appointmentPaidQuantity
				if (dataMap.get(tempDate).get("appointmentPaidQuantity") != null) {
					stats.setAppointmentPaidQuantity(dataMap.get(tempDate).get("appointmentPaidQuantity"));
				}

				// appointmentPaidAmount
				if (dataMap.get(tempDate).get("appointmentPaidAmount") != null) {
					stats.setAppointmentPaidAmount(dataMap.get(tempDate).get("appointmentPaidAmount"));
				}

				// dutyPaidQuantity
				if (dataMap.get(tempDate).get("dutyPaidQuantity") != null) {
					stats.setDutyPaidQuantity(dataMap.get(tempDate).get("dutyPaidQuantity"));
				}

				// dutyPaidAmount
				if (dataMap.get(tempDate).get("dutyPaidAmount") != null) {
					stats.setDutyPaidAmount(dataMap.get(tempDate).get("dutyPaidAmount"));
				}
			}

			statsMap.put(tempDate, stats);

			// 加一天
			tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
		}
		
		return statsMap;
	}

	@Override
	public YxwResult orderQuery(List<RegInfoRequest> regInfoRequests) {
		YxwResult yxwResult = new YxwResult();

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("orderQuery"));
		List<FutureTask<List<OrdersQueryResult>>> tasks = new ArrayList<FutureTask<List<OrdersQueryResult>>>();

		try {
			for (RegInfoRequest request: regInfoRequests) {
				Set<CoreVo> coreVos = getCores(request.getPlatform());
				Iterator<CoreVo> iterator = coreVos.iterator();
				while (iterator.hasNext()) {
					CoreVo coreVo = iterator.next();
					if (StringUtils.isNoneBlank(coreVo.getCoreName())) {
						// 此处返回的应该是多个查询条件 clinic&&deposit 分支付和全额退费两种查询。
						String coreName = coreVo.getCoreName();
						Integer orderMode = Math.abs(coreVo.getBizType());
						// 支付
						SolrQuery payQuery = getOutsidePayParams(request, coreVo.getPlatform());
						Callable<List<OrdersQueryResult>> payCallable = new OutsideOrderCallable(payQuery, coreName, 1, orderMode);
						FutureTask<List<OrdersQueryResult>> payTask = new FutureTask<List<OrdersQueryResult>>(payCallable);
						tasks.add(payTask);
						executorService.submit(payTask);
						
						// 退费
						SolrQuery refundQuery = getOutsideRefundParams(request, coreVo.getPlatform());
						Callable<List<OrdersQueryResult>> refundCallable = new OutsideOrderCallable(refundQuery, coreName, 2, orderMode);
						FutureTask<List<OrdersQueryResult>> refundTask = new FutureTask<List<OrdersQueryResult>>(refundCallable);
						tasks.add(refundTask);
						executorService.submit(refundTask);
					}
				}
			}
			
			if (tasks.size() > 0) {
				List<OrdersQueryResult> beans = new ArrayList<>();
				int size = 0;
				for (FutureTask<List<OrdersQueryResult>> futureTask : tasks) {
					List<OrdersQueryResult> infoResponse = futureTask.get(Integer.MAX_VALUE, TimeUnit.DAYS);
					if (infoResponse != null) {
						size += infoResponse.size();
						beans.addAll(infoResponse);
					}
				}

				Collections.sort(beans, new OrdersQueryResultComparator());

				yxwResult.setSize(size);
				yxwResult.setItems(beans);

				// results = JSON.toJSONString(solrDocumentList);
			}
			
		} catch (Exception e) {
			logger.error("orderQuery error.errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		} finally {
			executorService.shutdown();
		}

		return yxwResult;
	}
	
	private SolrQuery getOutsidePayParams(RegInfoRequest request, Integer platform) {
		// 支付
		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());
		sbQuery.append(SolrConstant.AND).append("platform").append(SolrConstant.COLON).append(platform);
		sbQuery.append(SolrConstant.AND).append("payStatus").append(SolrConstant.COLON).append(2);				
		if (!request.getBranchCode().equals("-1")) {
			sbQuery.append(SolrConstant.AND).append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue());			
		}
		solrQuery.setQuery(sbQuery.toString());
		// payTime:[begin TO end]
		sbQuery = new StringBuffer("payTime");
		sbQuery.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(request.getBeginTimestamp()).append(SolrConstant.TO);
		sbQuery.append(request.getEndTimestamp()).append(SolrConstant.INTERVAL_END);
		solrQuery.addFilterQuery(sbQuery.toString());
		
		solrQuery.set(CommonParams.ROWS, Integer.MAX_VALUE);
		solrQuery.setSort("payTime", ORDER.desc);

		return solrQuery;
	}
	
	private SolrQuery getOutsideRefundParams(RegInfoRequest request, Integer platform) {
		// 全额退费
		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());
		sbQuery.append(SolrConstant.AND).append("platform").append(SolrConstant.COLON).append(platform);
		sbQuery.append(SolrConstant.AND).append("payStatus").append(SolrConstant.COLON).append(3);			// 支付状态3：已退费
		if (!request.getBranchCode().equals("-1")) {
			sbQuery.append(SolrConstant.AND).append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue());			
		}
		solrQuery.setQuery(sbQuery.toString());
		// refundTime:[begin TO end]
		sbQuery = new StringBuffer("refundTime");
		sbQuery.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(request.getBeginTimestamp()).append(SolrConstant.TO);
		sbQuery.append(request.getEndTimestamp()).append(SolrConstant.INTERVAL_END);
		solrQuery.addFilterQuery(sbQuery.toString());
		
		solrQuery.set(CommonParams.ROWS, Integer.MAX_VALUE);
		solrQuery.setSort("refundTime", ORDER.desc);
		
		return solrQuery;
	}
	
	private Set<CoreVo> getCores(Integer platform) {
		Set<CoreVo> cores = new HashSet<>();
		int bizType = 1;
		if (platform != -1) {
			CoreVo vo = new CoreVo();
			vo.setPlatform(platform);
			vo.setBizType(bizType);
			cores.add(vo);
		} else {
			Map<Integer, String> platformMap = BizConstant.bizOrderMap.get(bizType);
			for (Entry<Integer, String> platformEntry : platformMap.entrySet()) {
				CoreVo vo = new CoreVo();
				vo.setPlatform(platformEntry.getKey());
				vo.setBizType(bizType);
				cores.add(vo);
			}
		}

		return cores;
	}

}
