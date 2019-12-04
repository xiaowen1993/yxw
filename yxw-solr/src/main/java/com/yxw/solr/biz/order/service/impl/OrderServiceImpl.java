package com.yxw.solr.biz.order.service.impl;

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
import org.apache.solr.common.SolrDocument;
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
import com.yxw.solr.biz.common.callable.OrdersQueryCallable;
import com.yxw.solr.biz.order.comparator.OrderInfoResponseComparator;
import com.yxw.solr.biz.order.service.OrderService;
import com.yxw.solr.biz.outside.callable.OutsideOrderCallable;
import com.yxw.solr.biz.outside.callable.OutsidePartRefundOrderCallable;
import com.yxw.solr.biz.outside.comparator.OrdersQueryResultComparator;
import com.yxw.solr.client.YxwSolrClient;
import com.yxw.solr.client.YxwUpdateClient;
import com.yxw.solr.constants.BizConstant;
import com.yxw.solr.constants.Cores;
import com.yxw.solr.constants.ErrorMessage;
import com.yxw.solr.constants.PoolConstant;
import com.yxw.solr.constants.ResultConstant;
import com.yxw.solr.constants.SolrConstant;
import com.yxw.solr.utils.UTCDateUtils;
import com.yxw.solr.vo.CoreVo;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.YxwResult;
import com.yxw.solr.vo.clinic.ClinicStats;
import com.yxw.solr.vo.deposit.DepositStats;
import com.yxw.solr.vo.order.OrderInfoRequest;
import com.yxw.solr.vo.order.OrderInfoResponse;
import com.yxw.solr.vo.order.OrderStats;
import com.yxw.solr.vo.order.OrderStatsRequest;
import com.yxw.solr.vo.register.RegStats;
import com.yxw.utils.DateUtils;

@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

	private Logger logger = LoggerFactory.getLogger(OrderService.class);

	private YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);

	@Override
	public YxwResponse findOrders(OrderInfoRequest request) {
		YxwResponse yxwResponse = new YxwResponse();
		YxwResult yxwResult = new YxwResult();
		String message = null;

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("findOrders"));
		List<FutureTask<OrderInfoResponse>> tasks = new ArrayList<FutureTask<OrderInfoResponse>>();

		try {
			Set<CoreVo> coreList = getCores(request.getPlatform(), request.getBizType());
			logger.info(JSON.toJSONString(coreList));

			if (CollectionUtils.isNotEmpty(coreList)) {
				for (CoreVo core : coreList) {
					if (StringUtils.isNoneBlank(core.getCoreName())) {
						SolrQuery solrQuery = getQueryParams(request, core.getPlatform());
						Callable<OrderInfoResponse> callable = new OrdersQueryCallable(core, solrQuery);
						FutureTask<OrderInfoResponse> task = new FutureTask<OrderInfoResponse>(callable);
						tasks.add(task);
						executorService.submit(task);
					}
				}

				if (tasks.size() > 0) {
					List<OrderInfoResponse> beans = new ArrayList<>();
					int size = 0;
					for (FutureTask<OrderInfoResponse> futureTask : tasks) {
						OrderInfoResponse infoResponse = futureTask.get(Integer.MAX_VALUE, TimeUnit.DAYS);
						if (infoResponse != null) {
							size += infoResponse.getSize();
							beans.add(infoResponse);
						}
					}

					Collections.sort(beans, new OrderInfoResponseComparator());

					yxwResult.setSize(size);
					yxwResult.setItems(beans);

					// results = JSON.toJSONString(solrDocumentList);
				}
			} else {
				message = "there is no cores. params: {}." + JSON.toJSONString(request);
				logger.warn(message);
			}
		} catch (Exception e) {
			logger.error("findOrders error. params: {}. errorMsg: {}. cause: {}.",
					new Object[] { JSON.toJSONString(request), e.getMessage(), e.getCause() });
		} finally {
			executorService.shutdown();
		}

		yxwResponse.setResult(yxwResult);
		yxwResponse.setResultMessage(message);
		return yxwResponse;
	}

	private SolrQuery getQueryParams(OrderInfoRequest request, Integer platform) {
		SolrQuery solrQuery = new SolrQuery();

		/*---------------------------- 查询条件 ----------------------------*/
		StringBuffer sbQuery = new StringBuffer();
		// 医院
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());
		// 订单渠道 --- 确认表需要, 这里不会出现-1的情况。
		sbQuery.append(SolrConstant.AND).append("platform").append(SolrConstant.COLON).append(platform);

		// 业务状态
		Integer bizStatus = request.getBizStatus();
		if (bizStatus != -1) {
			sbQuery.append(SolrConstant.AND).append("bizStatus").append(SolrConstant.COLON).append(bizStatus);
		}

		// 支付状态
		Integer payStatus = request.getPayStatus();
		if (payStatus != -1) {
			sbQuery.append(SolrConstant.AND).append("payStatus").append(SolrConstant.COLON).append(payStatus);
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
		// 平台订单号
		String orderNo = request.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sbQuery.append(SolrConstant.AND).append("orderNo").append(SolrConstant.COLON).append(orderNo);
		}
		// 患者手机
		String hisOrderNo = request.getHisOrderNo();
		if (StringUtils.isNotBlank(hisOrderNo)) {
			sbQuery.append(SolrConstant.AND).append("hisOrdNo").append(SolrConstant.COLON).append(hisOrderNo);
		}

		solrQuery.setQuery(sbQuery.toString());
		/*---------------------------- end of 查询条件 ----------------------------*/

		/*---------------------------- 订单生成时间过滤(createDate) ----------------------------*/
		String beginDate = request.getBeginTime();
		String endDate = request.getEndTime();
		if (StringUtils.isNotBlank(beginDate)) {
			StringBuffer sbFilter = new StringBuffer("createDate");
			sbFilter.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(beginDate).append(SolrConstant.TO);
			sbFilter.append(StringUtils.isNotBlank(endDate) ? endDate : SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
			solrQuery.addFilterQuery(sbFilter.toString());
		} else {
			if (StringUtils.isNotBlank(endDate)) {
				StringBuffer sbFilter = new StringBuffer("createDate");
				sbFilter.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(SolrConstant.ALL_VALUE).append(SolrConstant.TO);
				sbFilter.append(endDate).append(SolrConstant.INTERVAL_END);
				solrQuery.addFilterQuery(sbFilter.toString());
			}
		}
		/*---------------------------- end of 挂号时间过滤 ----------------------------*/

		// 分页获取数据
		solrQuery.set(CommonParams.START, request.getPageSize() * (request.getPageIndex() - 1));
		solrQuery.set(CommonParams.ROWS, request.getPageSize());

		solrQuery.setSort("createDate", ORDER.desc);

		// coreName 由platform确定，与业务关联紧密，后续补全

		return solrQuery;
	}

	private Set<CoreVo> getCores(Integer platform, Integer bizType) {
		logger.info("platform: {}, bizType: {}.", new Object[]{platform, bizType});
		Set<CoreVo> cores = new HashSet<>();
		if (bizType != -1) {
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
		} else {
			if (platform != -1) {
				for (Entry<Integer, Map<Integer, String>> bizEntry : BizConstant.bizOrderMap.entrySet()) {
					CoreVo vo = new CoreVo();
					vo.setPlatform(platform);
					vo.setBizType(bizEntry.getKey());
					cores.add(vo);
				}
			} else {
				for (Entry<Integer, Map<Integer, String>> bizEntry : BizConstant.bizOrderMap.entrySet()) {
					Integer type = bizEntry.getKey();
					for (Entry<Integer, String> platformEntry : bizEntry.getValue().entrySet()) {
						CoreVo vo = new CoreVo();
						vo.setPlatform(platformEntry.getKey());
						vo.setBizType(type);
						cores.add(vo);
					}
				}
			}
		}
		
		// 漏掉了部分退费的
		// 上面查询的是具体的门诊、挂号、住院但是并没有包括退费的。
		Set<CoreVo> extrasCores = new HashSet<>();
		for (CoreVo coreVo: cores) {
			if (coreVo != null) {
				int tempBizType = coreVo.getBizType();
				if (tempBizType == 2 || tempBizType == 3) {
					CoreVo partRefundVo = new CoreVo();
					partRefundVo.setPlatform(coreVo.getPlatform());
					partRefundVo.setBizType(tempBizType * -1);
					extrasCores.add(partRefundVo);
				}
			}
		}

		if (!extrasCores.isEmpty()) {
			cores.addAll(extrasCores);
		}

		return cores;
	}

	@Override
	public String statsInfosWithinPlatform(OrderStatsRequest request) {
		Map<String, Object> beans = new HashMap<String, Object>();
		// 获取开始时间
		String firstDateOfClinic = getStartDate(Cores.CORE_STATS_CLINIC);
		String firstDateOfRegister = getStartDate(Cores.CORE_STATS_REGISTER);
		String firstDateOfDeposit = getStartDate(Cores.CORE_STATS_DEPOSIT);
		String startDate = getMinString(new String[] { firstDateOfClinic, firstDateOfRegister, firstDateOfDeposit });

		// 获取统计数据
		SolrQuery solrQuery = getSolrQueryParams(request);
		Map<String, ClinicStats> clinicMap = getClinicStatsInfos(solrQuery);
		Map<String, RegStats> regMap = getRegStatsInfos(solrQuery);
		Map<String, DepositStats> depositMap = getDepositStatsInfos(solrQuery);

		String tempDate = startDate;
		String limitedDate = DateUtils.getDayForDate(new Date(), -1);

		while (tempDate.compareTo(limitedDate) <= 0) {
			OrderStats stats = new OrderStats();
			stats.setId(PKGenerator.generateId());
			stats.setHospitalCode(request.getHospitalCode());
			stats.setBranchCode(request.getBranchCode());
			stats.setPlatform(request.getPlatform());
			stats.setStatsDate(tempDate);
			Date date = new Date();
			stats.setUpdateTime_utc(UTCDateUtils.parseDate(date));
			stats.setUpdateTime(DateUtils.getDateStr(date));

			// Integer netIncome = 0;
			Integer regPaidAmount = 0;
			Integer regRefundAmount = 0;
			Integer clinicPaidAmount = 0;
			Integer clinicRefundAmount = 0;
			Integer depositPaidAmount = 0;
			Integer depositRefundAmount = 0;

			Integer regPaidQuantity = 0;
			Integer clinicPaidQuantity = 0;
			Integer depositPaidQuantity = 0;
			Integer regRefundQuantity = 0;
			Integer clinicRefundQuantity = 0;
			Integer depositRefundQuantity = 0;
			Integer paidQuantity = 0;
			Integer refundQuantity = 0;
			Integer paidAmount = 0;
			Integer refundAmount = 0;

			// 处理挂号
			if (regMap.get(tempDate) != null) {
				RegStats regStats = regMap.get(tempDate);
				regPaidAmount = regStats.getPaidAmount();
				regPaidQuantity = regStats.getPaidQuantity();
				regRefundAmount = regStats.getRefundAmount();
				regRefundQuantity = regStats.getRefundQuantity();

				// netIncome += regPaidAmount - regRefundAmount;
				paidQuantity += regPaidQuantity;
				refundQuantity += regRefundQuantity;
				paidAmount += regPaidAmount;
				refundAmount += regRefundAmount;
			}

			// 处理门诊
			if (clinicMap.get(tempDate) != null) {
				ClinicStats clinicStats = clinicMap.get(tempDate);
				clinicPaidAmount = clinicStats.getPaidAmount();
				clinicPaidQuantity = clinicStats.getPaidQuantity();
				clinicRefundAmount = clinicStats.getRefundAmount();
				clinicRefundQuantity = clinicStats.getRefundQuantity();

				// netIncome += clinicPaidAmount - clinicRefundAmount;
				paidQuantity += clinicPaidQuantity;
				refundQuantity += clinicRefundQuantity;
				paidAmount += clinicPaidAmount;
				refundAmount += clinicRefundAmount;
			}

			// 处理住院
			if (depositMap.get(tempDate) != null) {
				DepositStats depositStats = depositMap.get(tempDate);
				depositPaidAmount = depositStats.getPaidAmount();
				depositPaidQuantity = depositStats.getPaidQuantity();
				depositRefundAmount = depositStats.getRefundAmount();
				depositRefundQuantity = depositStats.getRefundQuantity();

				// netIncome += depositAmount - depositRefundAmount;
				paidQuantity += depositPaidQuantity;
				refundQuantity += depositRefundQuantity;
				paidAmount += depositPaidAmount;
				refundAmount += depositRefundAmount;
			}

			// 各类总额
			stats.setNetIncome(paidAmount - refundAmount);
			stats.setRegPaidAmount(regPaidAmount);
			stats.setRegRefundAmount(regRefundAmount);
			stats.setClinicPaidAmount(clinicPaidAmount);
			stats.setClinicRefundAmount(clinicRefundAmount);
			stats.setDepositPaidAmount(depositPaidAmount);
			stats.setDepositRefundAmount(depositRefundAmount);

			// 各类订单数量
			stats.setRegPaidQuantity(regPaidQuantity);
			stats.setRegRefundQuantity(regRefundQuantity);
			stats.setClinicPaidQuantity(clinicPaidQuantity);
			stats.setClinicRefundQuantity(clinicRefundQuantity);
			stats.setDepositPaidQuantity(depositPaidQuantity);
			stats.setDepositRefundQuantity(depositRefundQuantity);

			// 总额+数量
			stats.setOrdersQuantity(paidQuantity + refundQuantity);
			stats.setPaidQuantity(paidQuantity);
			stats.setRefundQuantity(refundQuantity);
			stats.setPaidAmount(paidAmount);
			stats.setRefundAmount(refundAmount);

			// 累计
			Integer cumulativeQuantity = stats.getOrdersQuantity();
			Integer cumulativePaidQuantity = paidQuantity;
			Integer cumulativeRefundQuantity = refundQuantity;
			Integer cumulativePaidAmount = paidAmount;
			Integer cumulativeRefundAmount = refundAmount;

			// 有没有之前那天的数据，没有就之后都不算了
			String preDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), -1);
			if (beans.get(preDate) != null) {
				OrderStats preStats = (OrderStats) beans.get(preDate);
				cumulativeQuantity += preStats.getCumulativeQuantity();
				cumulativePaidAmount += preStats.getCumulativePaidAmount();
				cumulativePaidQuantity += preStats.getCumulativePaidQuantity();
				cumulativeRefundAmount += preStats.getCumulativeRefundAmount();
				cumulativeRefundQuantity += preStats.getCumulativeRefundQuantity();
			}

			stats.setCumulativePaidAmount(cumulativePaidAmount);
			stats.setCumulativePaidQuantity(cumulativePaidQuantity);
			stats.setCumulativeQuantity(cumulativeQuantity);
			stats.setCumulativeRefundAmount(cumulativeRefundAmount);
			stats.setCumulativeRefundQuantity(cumulativeRefundQuantity);

			beans.put(tempDate, stats);

			tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
		}

		if (CollectionUtils.isNotEmpty(beans.values())) {
			saveStatsInfos(request, beans.values());
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	private void saveStatsInfos(OrderStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getBeginDate())) {
			sb.append(SolrConstant.AND).append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START)
					.append(request.getBeginDate()).append(SolrConstant.TO).append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_ORDER, collection, true, sb.toString());
	}

	private void saveRebuildInfos(OrderStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());

		// 不能删除全部，只删除需要的部分
		sb.append(SolrConstant.AND).append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(request.getBeginDate())
				.append(SolrConstant.TO).append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);

		YxwUpdateClient.addBeans(Cores.CORE_STATS_ORDER, collection, true, sb.toString());
	}

	private void saveDailyInfos(OrderStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sb.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		sb.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());

		if (StringUtils.isNotBlank(request.getStatsDate())) {
			sb.append(SolrConstant.AND).append("statsDate").append(SolrConstant.COLON).append(request.getStatsDate());
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_ORDER, collection, true, sb.toString());
	}

	/**
	 * 获取最大字符串
	 * 
	 * @param strs
	 * @return
	 */
	private String getMinString(String... strs) {
		String minStr = "";

		if (strs.length > 0) {
			minStr = strs[0];
		}

		for (int i = 1; i < strs.length; i++) {
			if (minStr.compareTo(strs[i]) > 0) {
				minStr = strs[i];
			}
		}

		return minStr;
	}

	@Override
	public String statsInfoForDayWithinPlatform(OrderStatsRequest request) {
		Map<String, Object> beans = new HashMap<String, Object>();

		// 获取统计数据
		SolrQuery solrQuery = getSolrQueryParams(request);
		Map<String, ClinicStats> clinicMap = getClinicStatsInfos(solrQuery);
		Map<String, RegStats> regMap = getRegStatsInfos(solrQuery);
		Map<String, DepositStats> depositMap = getDepositStatsInfos(solrQuery);

		String tempDate = request.getStatsDate();
		String limitedDate = DateUtils.today();

		if (tempDate.compareTo(limitedDate) < 0) {
			OrderStats stats = new OrderStats();
			stats.setId(PKGenerator.generateId());
			stats.setHospitalCode(request.getHospitalCode());
			stats.setBranchCode(request.getBranchCode());
			stats.setPlatform(request.getPlatform());
			stats.setStatsDate(tempDate);
			Date date = new Date();
			stats.setUpdateTime_utc(UTCDateUtils.parseDate(date));
			stats.setUpdateTime(DateUtils.getDateStr(date));

			// Integer netIncome = 0;
			Integer regPaidAmount = 0;
			Integer regRefundAmount = 0;
			Integer clinicPaidAmount = 0;
			Integer clinicRefundAmount = 0;
			Integer depositPaidAmount = 0;
			Integer depositRefundAmount = 0;

			Integer regPaidQuantity = 0;
			Integer clinicPaidQuantity = 0;
			Integer depositPaidQuantity = 0;
			Integer regRefundQuantity = 0;
			Integer clinicRefundQuantity = 0;
			Integer depositRefundQuantity = 0;
			Integer paidQuantity = 0;
			Integer refundQuantity = 0;
			Integer paidAmount = 0;
			Integer refundAmount = 0;

			// 处理挂号
			if (regMap.get(tempDate) != null) {
				RegStats regStats = regMap.get(tempDate);
				regPaidAmount = regStats.getPaidAmount();
				regPaidQuantity = regStats.getPaidQuantity();
				regRefundAmount = regStats.getRefundAmount();
				regRefundQuantity = regStats.getRefundQuantity();

				// netIncome += regPaidAmount - regRefundAmount;
				paidQuantity += regPaidQuantity;
				refundQuantity += regRefundQuantity;
				paidAmount += regPaidAmount;
				refundAmount += regRefundAmount;
			}

			// 处理门诊
			if (clinicMap.get(tempDate) != null) {
				ClinicStats clinicStats = clinicMap.get(tempDate);
				clinicPaidAmount = clinicStats.getPaidAmount();
				clinicPaidQuantity = clinicStats.getPaidQuantity();
				clinicRefundAmount = clinicStats.getRefundAmount();
				clinicRefundQuantity = clinicStats.getRefundQuantity();

				// netIncome += clinicPaidAmount - clinicRefundAmount;
				paidQuantity += clinicPaidQuantity;
				refundQuantity += clinicRefundQuantity;
				paidAmount += clinicPaidAmount;
				refundAmount += clinicRefundAmount;
			}

			// 处理住院
			if (depositMap.get(tempDate) != null) {
				DepositStats depositStats = depositMap.get(tempDate);
				depositPaidAmount = depositStats.getPaidAmount();
				depositPaidQuantity = depositStats.getPaidQuantity();
				depositRefundAmount = depositStats.getRefundAmount();
				depositRefundQuantity = depositStats.getRefundQuantity();

				// netIncome += depositAmount - depositRefundAmount;
				paidQuantity += depositPaidQuantity;
				refundQuantity += depositRefundQuantity;
				paidAmount += depositPaidAmount;
				refundAmount += depositRefundAmount;
			}

			// 各类总额
			stats.setNetIncome(paidAmount - refundAmount);
			stats.setRegPaidAmount(regPaidAmount);
			stats.setRegRefundAmount(regRefundAmount);
			stats.setClinicPaidAmount(clinicPaidAmount);
			stats.setClinicRefundAmount(clinicRefundAmount);
			stats.setDepositPaidAmount(depositPaidAmount);
			stats.setDepositRefundAmount(depositRefundAmount);

			// 各类订单数量
			stats.setRegPaidQuantity(regPaidQuantity);
			stats.setRegRefundQuantity(regRefundQuantity);
			stats.setClinicPaidQuantity(clinicPaidQuantity);
			stats.setClinicRefundQuantity(clinicRefundQuantity);
			stats.setDepositPaidQuantity(depositPaidQuantity);
			stats.setDepositRefundQuantity(depositRefundQuantity);

			// 总额+数量
			stats.setOrdersQuantity(paidQuantity + refundQuantity);
			stats.setPaidQuantity(paidQuantity);
			stats.setRefundQuantity(refundQuantity);
			stats.setPaidAmount(paidAmount);
			stats.setRefundAmount(refundAmount);

			// 累计
			Integer cumulativeQuantity = stats.getOrdersQuantity();
			Integer cumulativePaidQuantity = paidQuantity;
			Integer cumulativeRefundQuantity = refundQuantity;
			Integer cumulativePaidAmount = paidAmount;
			Integer cumulativeRefundAmount = refundAmount;

			// 有没有之前那天的数据，没有就之后都不算了
			String preDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), -1);
			OrderStatsRequest yesterdayRequest = new OrderStatsRequest();
			BeanUtils.copyProperties(request, yesterdayRequest);
			yesterdayRequest.setStatsDate(preDate);
			OrderStats preDayStats = getPreDayStats(yesterdayRequest);
			if (preDayStats != null) {
				cumulativeQuantity += preDayStats.getCumulativeQuantity();
				cumulativePaidAmount += preDayStats.getCumulativePaidAmount();
				cumulativePaidQuantity += preDayStats.getCumulativePaidQuantity();
				cumulativeRefundAmount += preDayStats.getCumulativeRefundAmount();
				cumulativeRefundQuantity += preDayStats.getCumulativeRefundQuantity();
			}

			stats.setCumulativePaidAmount(cumulativePaidAmount);
			stats.setCumulativePaidQuantity(cumulativePaidQuantity);
			stats.setCumulativeQuantity(cumulativeQuantity);
			stats.setCumulativeRefundAmount(cumulativeRefundAmount);
			stats.setCumulativeRefundQuantity(cumulativeRefundQuantity);

			beans.put(tempDate, stats);

			// 保存
			saveDailyInfos(request, beans.values());

			return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
					new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
		} else {
			return ErrorMessage.STATS_DATE_LIMITED + ": " + limitedDate;
		}

	}

	private OrderStats getPreDayStats(OrderStatsRequest request) {
		OrderStats stats = null;

		SolrQuery solrQuery = new SolrQuery();

		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		sbQuery.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		sbQuery.append("statsDate").append(SolrConstant.COLON).append(request.getStatsDate()).append(SolrConstant.AND);
		sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		solrQuery.setQuery(sbQuery.toString());

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_ORDER, solrQuery);
			SolrDocumentList docs = response.getResults();
			if (CollectionUtils.isNotEmpty(docs)) {
				String source = JSON.toJSONString(docs.get(0));
				stats = JSON.parseObject(source, OrderStats.class);
			}
		} catch (Exception e) {
			logger.error("获取前一天的统计信息异常。params: {}. errorMsg: {}. cause: {}.",
					new Object[] { JSON.toJSONString(request), e.getMessage(), e.getCause() });
		}

		return stats;
	}

	private String getStartDate(String statsCoreName) {
		String date = null;

		SolrQuery solrQuery = new SolrQuery();
		try {
			solrQuery.setQuery(SolrConstant.QUERY_ALL);
			solrQuery.setRows(1);
			solrQuery.setFields("statsDate");
			solrQuery.addSort("statsDate", ORDER.asc);

			QueryResponse queryResponse = solrClient.query(statsCoreName, solrQuery);
			SolrDocumentList documentList = queryResponse.getResults();
			if (CollectionUtils.isNotEmpty(documentList)) {
				date = (String) documentList.get(0).getFieldValue("statsDate");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return date == null ? DateUtils.today() : date;
	}

	private SolrQuery getSolrQueryParams(OrderStatsRequest request) {
		SolrQuery solrQuery = new SolrQuery();

		// 有时间间隔则拿时间间隔，没有则按分页来
		StringBuffer sbQuery = new StringBuffer();
		// 医院
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode()).append(SolrConstant.AND);
		// 分院 -- 此处不区分分院，所以-1。 后面也许需要通过request中拿。
		sbQuery.append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue()).append(SolrConstant.AND);
		// 如果有指定日期 （指定日期其实可以用范围来替代）
		if (StringUtils.isNotBlank(request.getStatsDate())) {
			sbQuery.append("statsDate").append(SolrConstant.COLON).append(request.getStatsDate()).append(SolrConstant.AND);
		}
		// 平台 -- 此处不区分平台，所以-1。 后面也许需要通过request中拿。
		sbQuery.append("platform").append(SolrConstant.COLON).append(request.getPlatformValue());
		solrQuery.setQuery(sbQuery.toString());
		solrQuery.setRows(1000);

		return solrQuery;
	}

	/**
	 * 获取门诊统计 日期/统计
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, ClinicStats> getClinicStatsInfos(SolrQuery solrQuery) {
		Map<String, ClinicStats> statsMap = new HashMap<String, ClinicStats>();

		String coreName = Cores.CORE_STATS_CLINIC;

		try {

			QueryResponse response = solrClient.query(coreName, solrQuery);

			SolrDocumentList docs = response.getResults();
			if (CollectionUtils.isNotEmpty(docs)) {
				for (SolrDocument doc : docs) {
					String str = JSON.toJSONString(doc);
					ClinicStats stats = JSON.parseObject(str, ClinicStats.class);
					statsMap.put(stats.getStatsDate(), stats);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return statsMap;
	}

	/**
	 * 获取挂号统计 日期/统计
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, RegStats> getRegStatsInfos(SolrQuery solrQuery) {
		Map<String, RegStats> statsMap = new HashMap<String, RegStats>();

		String coreName = Cores.CORE_STATS_REGISTER;

		try {

			QueryResponse response = solrClient.query(coreName, solrQuery);

			SolrDocumentList docs = response.getResults();
			if (CollectionUtils.isNotEmpty(docs)) {
				for (SolrDocument doc : docs) {
					String str = JSON.toJSONString(doc);
					RegStats stats = JSON.parseObject(str, RegStats.class);
					statsMap.put(stats.getStatsDate(), stats);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return statsMap;
	}

	/**
	 * 获取住院统计信息 日期/统计
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, DepositStats> getDepositStatsInfos(SolrQuery solrQuery) {
		Map<String, DepositStats> statsMap = new HashMap<String, DepositStats>();

		String coreName = Cores.CORE_STATS_DEPOSIT;

		try {

			QueryResponse response = solrClient.query(coreName, solrQuery);

			SolrDocumentList docs = response.getResults();
			if (CollectionUtils.isNotEmpty(docs)) {
				for (SolrDocument doc : docs) {
					String str = JSON.toJSONString(doc);
					DepositStats stats = JSON.parseObject(str, DepositStats.class);
					statsMap.put(stats.getStatsDate(), stats);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return statsMap;
	}

	@Override
	public YxwResponse getKPIStatsInfo(OrderStatsRequest request) {
		YxwResponse responseVo = new YxwResponse();
		YxwResult yxwResult = new YxwResult();
		Map<String, Integer> resultMap = getKPIStats(request.getHospitalCode(), request.getBeginDate(), request.getEndDate());
		if (!org.springframework.util.CollectionUtils.isEmpty(resultMap)) {
			yxwResult.setSize(1);
			yxwResult.setItems(resultMap);
		}

		responseVo.setResult(yxwResult);
		return responseVo;
	}

	private Map<String, Integer> getKPIStats(String hospitalCode, String beginDate, String endDate) {
		Map<String, Integer> resultMap = new HashMap<>();

		SolrQuery solrQuery = new SolrQuery();

		// 某家医院所有平台所有分院在某段时间内的数据统计
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("hospitalCode").append(SolrConstant.COLON).append(hospitalCode).append(SolrConstant.AND);
		sbQuery.append("platform").append(SolrConstant.COLON).append("\\-1").append(SolrConstant.AND);
		sbQuery.append("branchCode").append(SolrConstant.COLON).append("\\-1");
		solrQuery.setQuery(sbQuery.toString());
		solrQuery.setRows(0);

		// 设置日期区间
		StringBuffer sbFilter = new StringBuffer();
		sbFilter.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(beginDate).append(SolrConstant.TO)
				.append(endDate).append(SolrConstant.INTERVAL_END);
		solrQuery.setFilterQueries(sbFilter.toString());

		solrQuery.setGetFieldStatistics(true);
		// KPI需要
		solrQuery.addGetFieldStatistics("netIncome");
		solrQuery.addGetFieldStatistics("regPaidAmount");
		solrQuery.addGetFieldStatistics("clinicPaidAmount");
		solrQuery.addGetFieldStatistics("depositPaidAmount");
		solrQuery.addGetFieldStatistics("regRefundAmount");
		solrQuery.addGetFieldStatistics("clinicRefundAmount");
		solrQuery.addGetFieldStatistics("depositRefundAmount");

		// 累计KPI需要
		// solrQuery.addGetFieldStatistics("cumulativeQuantity");
		// solrQuery.addGetFieldStatistics("cumulativePaidQuantity");
		// solrQuery.addGetFieldStatistics("cumulativeRefundQuantity");
		// solrQuery.addGetFieldStatistics("cumulativePaidAmount");
		// solrQuery.addGetFieldStatistics("cumulativeRefundAmount");
		// solrQuery.addStatsFieldFacets(null, "statsDate");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_ORDER, solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Object>>>> responseMap = response.getResponse().asMap(100);
			Map<String, Map<String, Object>> statsFields = responseMap.get("stats").get("stats_fields");
			for (Entry<String, Map<String, Object>> entry1 : statsFields.entrySet()) {
				String statsField = entry1.getKey();
				Integer value = ((Double) entry1.getValue().get("sum")).intValue();
				resultMap.put(statsField, value);
			}
		} catch (Exception e) {
			logger.error("获取全平台订单统计数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	@Override
	public YxwResponse getStatsInfos(OrderStatsRequest request) {
		YxwResponse responseVo = new YxwResponse();
		YxwResult yxwResult = new YxwResult();
		String coreName = Cores.CORE_STATS_ORDER;

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

		// 设置查询字段，默认是查询全部
		if (StringUtils.isNotBlank(request.getFields())) {
			solrQuery.setFields(request.getFields());
			solrQuery.setSort("statsDate", ORDER.asc);
			solrQuery.setRows(Integer.MAX_VALUE);
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
			logger.error("获取订单统计信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		responseVo.setResult(yxwResult);
		return responseVo;
	}

	@Override
	public String statsInfosWithoutPlatform(OrderStatsRequest request) {
		Map<String, Object> statsMap = new HashMap<>();

		String statsDate = "";
		Map<String, Map<String, Integer>> dataMap = getAllStats(request.getHospitalCode(), request.getBranchCodeValue(), statsDate, null);

		String startDate = getStartDate(Cores.CORE_STATS_ORDER);
		String tempDate = startDate;
		// 统计到昨天之前，昨天的数据，用轮训做
		String currentDate = DateUtils.getDayForDate(new Date(), -1);

		while (tempDate.compareTo(currentDate) <= 0) {

			Date curDate = new Date();
			OrderStats stats = new OrderStats();
			stats.setHospitalCode(request.getHospitalCode());
			stats.setBranchCode(request.getBranchCode());
			stats.setPlatform(-1);
			stats.setId(PKGenerator.generateId());
			stats.setStatsDate(tempDate);
			stats.setUpdateTime(DateUtils.dateToString(curDate));
			stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));

			// 门诊
			stats.setClinicPaidAmount(0);
			stats.setClinicPaidQuantity(0);
			stats.setClinicRefundAmount(0);
			stats.setClinicRefundQuantity(0);
			// 累计
			stats.setCumulativePaidAmount(0);
			stats.setCumulativePaidQuantity(0);
			stats.setCumulativeQuantity(0);
			stats.setCumulativeRefundAmount(0);
			stats.setCumulativeRefundQuantity(0);
			// 押金
			stats.setDepositPaidAmount(0);
			stats.setDepositPaidQuantity(0);
			stats.setDepositRefundAmount(0);
			stats.setDepositRefundQuantity(0);
			// 总统计
			stats.setNetIncome(0);
			stats.setOrdersQuantity(0);
			stats.setPaidAmount(0);
			stats.setPaidQuantity(0);
			stats.setRefundAmount(0);
			stats.setRefundQuantity(0);
			// 挂号
			stats.setRegPaidAmount(0);
			stats.setRegPaidQuantity(0);
			stats.setRegRefundAmount(0);
			stats.setRegRefundQuantity(0);

			if (dataMap.get(tempDate) != null) {
				// netIncome
				if (dataMap.get(tempDate).get("netIncome") != null) {
					stats.setNetIncome(dataMap.get(tempDate).get("netIncome"));
				}

				// regPaidAmount
				if (dataMap.get(tempDate).get("regPaidAmount") != null) {
					stats.setRegPaidAmount(dataMap.get(tempDate).get("regPaidAmount"));
				}

				// clinicPaidAmount
				if (dataMap.get(tempDate).get("clinicPaidAmount") != null) {
					stats.setClinicPaidAmount(dataMap.get(tempDate).get("clinicPaidAmount"));
				}

				// depositPaidAmount
				if (dataMap.get(tempDate).get("depositPaidAmount") != null) {
					stats.setDepositPaidAmount(dataMap.get(tempDate).get("depositPaidAmount"));
				}

				// regRefundAmount
				if (dataMap.get(tempDate).get("regRefundAmount") != null) {
					stats.setRegRefundAmount(dataMap.get(tempDate).get("regRefundAmount"));
				}

				// refundQuantity
				if (dataMap.get(tempDate).get("clinicRefundAmount") != null) {
					stats.setClinicRefundAmount(dataMap.get(tempDate).get("clinicRefundAmount"));
				}

				// depositRefundAmount
				if (dataMap.get(tempDate).get("depositRefundAmount") != null) {
					stats.setDepositRefundAmount(dataMap.get(tempDate).get("depositRefundAmount"));
				}

				// regPaidQuantity
				if (dataMap.get(tempDate).get("regPaidQuantity") != null) {
					stats.setRegPaidQuantity(dataMap.get(tempDate).get("regPaidQuantity"));
				}

				// clinicPaidQuantity
				if (dataMap.get(tempDate).get("clinicPaidQuantity") != null) {
					stats.setClinicPaidQuantity(dataMap.get(tempDate).get("clinicPaidQuantity"));
				}

				// depositPaidQuantity
				if (dataMap.get(tempDate).get("depositPaidQuantity") != null) {
					stats.setDepositPaidQuantity(dataMap.get(tempDate).get("depositPaidQuantity"));
				}

				// regRefundQuantity
				if (dataMap.get(tempDate).get("regRefundQuantity") != null) {
					stats.setRegRefundQuantity(dataMap.get(tempDate).get("regRefundQuantity"));
				}

				// clinicRefundQuantity
				if (dataMap.get(tempDate).get("clinicRefundQuantity") != null) {
					stats.setClinicRefundQuantity(dataMap.get(tempDate).get("clinicRefundQuantity"));
				}

				// depositRefundQuantity
				if (dataMap.get(tempDate).get("depositRefundQuantity") != null) {
					stats.setDepositRefundQuantity(dataMap.get(tempDate).get("depositRefundQuantity"));
				}

				// cumulativeQuantity
				if (dataMap.get(tempDate).get("cumulativeQuantity") != null) {
					stats.setCumulativeQuantity(dataMap.get(tempDate).get("cumulativeQuantity"));
				}

				// cumulativePaidQuantity
				if (dataMap.get(tempDate).get("cumulativePaidQuantity") != null) {
					stats.setCumulativePaidQuantity(dataMap.get(tempDate).get("cumulativePaidQuantity"));
				}

				// cumulativeRefundQuantity
				if (dataMap.get(tempDate).get("cumulativeRefundQuantity") != null) {
					stats.setCumulativeRefundQuantity(dataMap.get(tempDate).get("cumulativeRefundQuantity"));
				}

				// cumulativePaidAmount
				if (dataMap.get(tempDate).get("cumulativePaidAmount") != null) {
					stats.setCumulativePaidAmount(dataMap.get(tempDate).get("cumulativePaidAmount"));
				}

				// cumulativeRefundAmount
				if (dataMap.get(tempDate).get("cumulativeRefundAmount") != null) {
					stats.setCumulativeRefundAmount(dataMap.get(tempDate).get("cumulativeRefundAmount"));
				}

				// ordersQuantity
				if (dataMap.get(tempDate).get("ordersQuantity") != null) {
					stats.setOrdersQuantity(dataMap.get(tempDate).get("ordersQuantity"));
				}

				// paidQuantity
				if (dataMap.get(tempDate).get("paidQuantity") != null) {
					stats.setPaidQuantity(dataMap.get(tempDate).get("paidQuantity"));
				}

				// refundQuantity
				if (dataMap.get(tempDate).get("refundQuantity") != null) {
					stats.setRefundQuantity(dataMap.get(tempDate).get("refundQuantity"));
				}

				// paidAmount
				if (dataMap.get(tempDate).get("paidAmount") != null) {
					stats.setPaidAmount(dataMap.get(tempDate).get("paidAmount"));
				}

				// refundAmount
				if (dataMap.get(tempDate).get("refundAmount") != null) {
					stats.setRefundAmount(dataMap.get(tempDate).get("refundAmount"));
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
	public String statsInfoForDayWithoutPlatform(OrderStatsRequest request) {
		String statsDate = request.getStatsDate();
		// 统计到昨天之前，昨天的数据，用轮训做
		String currentDate = DateUtils.today();
		String tempDate = statsDate;

		if (tempDate.compareTo(currentDate) < 0) {
			Map<String, Map<String, Integer>> dataMap = getAllStats(request.getHospitalCode(), request.getBranchCodeValue(), statsDate, null);
			Date curDate = new Date();
			OrderStats stats = new OrderStats();
			stats.setHospitalCode(request.getHospitalCode());
			stats.setBranchCode(request.getBranchCode());
			stats.setPlatform(-1);
			stats.setId(PKGenerator.generateId());
			stats.setStatsDate(statsDate);
			stats.setUpdateTime(DateUtils.dateToString(curDate));
			stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));

			// 门诊
			stats.setClinicPaidAmount(0);
			stats.setClinicPaidQuantity(0);
			stats.setClinicRefundAmount(0);
			stats.setClinicRefundQuantity(0);
			// 累计
			stats.setCumulativePaidAmount(0);
			stats.setCumulativePaidQuantity(0);
			stats.setCumulativeQuantity(0);
			stats.setCumulativeRefundAmount(0);
			stats.setCumulativeRefundQuantity(0);
			// 押金
			stats.setDepositPaidAmount(0);
			stats.setDepositPaidQuantity(0);
			stats.setDepositRefundAmount(0);
			stats.setDepositRefundQuantity(0);
			// 总统计
			stats.setNetIncome(0);
			stats.setOrdersQuantity(0);
			stats.setPaidAmount(0);
			stats.setPaidQuantity(0);
			stats.setRefundAmount(0);
			stats.setRefundQuantity(0);
			// 挂号
			stats.setRegPaidAmount(0);
			stats.setRegPaidQuantity(0);
			stats.setRegRefundAmount(0);
			stats.setRegRefundQuantity(0);

			if (dataMap.get(tempDate) != null) {
				// netIncome
				if (dataMap.get(tempDate).get("netIncome") != null) {
					stats.setNetIncome(dataMap.get(tempDate).get("netIncome"));
				}

				// regPaidAmount
				if (dataMap.get(tempDate).get("regPaidAmount") != null) {
					stats.setRegPaidAmount(dataMap.get(tempDate).get("regPaidAmount"));
				}

				// clinicPaidAmount
				if (dataMap.get(tempDate).get("clinicPaidAmount") != null) {
					stats.setClinicPaidAmount(dataMap.get(tempDate).get("clinicPaidAmount"));
				}

				// depositPaidAmount
				if (dataMap.get(tempDate).get("depositPaidAmount") != null) {
					stats.setDepositPaidAmount(dataMap.get(tempDate).get("depositPaidAmount"));
				}

				// regRefundAmount
				if (dataMap.get(tempDate).get("regRefundAmount") != null) {
					stats.setRegRefundAmount(dataMap.get(tempDate).get("regRefundAmount"));
				}

				// refundQuantity
				if (dataMap.get(tempDate).get("clinicRefundAmount") != null) {
					stats.setClinicRefundAmount(dataMap.get(tempDate).get("clinicRefundAmount"));
				}

				// depositRefundAmount
				if (dataMap.get(tempDate).get("depositRefundAmount") != null) {
					stats.setDepositRefundAmount(dataMap.get(tempDate).get("depositRefundAmount"));
				}

				// regPaidQuantity
				if (dataMap.get(tempDate).get("regPaidQuantity") != null) {
					stats.setRegPaidQuantity(dataMap.get(tempDate).get("regPaidQuantity"));
				}

				// clinicPaidQuantity
				if (dataMap.get(tempDate).get("clinicPaidQuantity") != null) {
					stats.setClinicPaidQuantity(dataMap.get(tempDate).get("clinicPaidQuantity"));
				}

				// depositPaidQuantity
				if (dataMap.get(tempDate).get("depositPaidQuantity") != null) {
					stats.setDepositPaidQuantity(dataMap.get(tempDate).get("depositPaidQuantity"));
				}

				// regRefundQuantity
				if (dataMap.get(tempDate).get("regRefundQuantity") != null) {
					stats.setRegRefundQuantity(dataMap.get(tempDate).get("regRefundQuantity"));
				}

				// clinicRefundQuantity
				if (dataMap.get(tempDate).get("clinicRefundQuantity") != null) {
					stats.setClinicRefundQuantity(dataMap.get(tempDate).get("clinicRefundQuantity"));
				}

				// depositRefundQuantity
				if (dataMap.get(tempDate).get("depositRefundQuantity") != null) {
					stats.setDepositRefundQuantity(dataMap.get(tempDate).get("depositRefundQuantity"));
				}

				// cumulativeQuantity
				if (dataMap.get(tempDate).get("cumulativeQuantity") != null) {
					stats.setCumulativeQuantity(dataMap.get(tempDate).get("cumulativeQuantity"));
				}

				// cumulativePaidQuantity
				if (dataMap.get(tempDate).get("cumulativePaidQuantity") != null) {
					stats.setCumulativePaidQuantity(dataMap.get(tempDate).get("cumulativePaidQuantity"));
				}

				// cumulativeRefundQuantity
				if (dataMap.get(tempDate).get("cumulativeRefundQuantity") != null) {
					stats.setCumulativeRefundQuantity(dataMap.get(tempDate).get("cumulativeRefundQuantity"));
				}

				// cumulativePaidAmount
				if (dataMap.get(tempDate).get("cumulativePaidAmount") != null) {
					stats.setCumulativePaidAmount(dataMap.get(tempDate).get("cumulativePaidAmount"));
				}

				// cumulativeRefundAmount
				if (dataMap.get(tempDate).get("cumulativeRefundAmount") != null) {
					stats.setCumulativeRefundAmount(dataMap.get(tempDate).get("cumulativeRefundAmount"));
				}

				// ordersQuantity
				if (dataMap.get(tempDate).get("ordersQuantity") != null) {
					stats.setOrdersQuantity(dataMap.get(tempDate).get("ordersQuantity"));
				}

				// paidQuantity
				if (dataMap.get(tempDate).get("paidQuantity") != null) {
					stats.setPaidQuantity(dataMap.get(tempDate).get("paidQuantity"));
				}

				// refundQuantity
				if (dataMap.get(tempDate).get("refundQuantity") != null) {
					stats.setRefundQuantity(dataMap.get(tempDate).get("refundQuantity"));
				}

				// paidAmount
				if (dataMap.get(tempDate).get("paidAmount") != null) {
					stats.setPaidAmount(dataMap.get(tempDate).get("paidAmount"));
				}

				// refundAmount
				if (dataMap.get(tempDate).get("refundAmount") != null) {
					stats.setRefundAmount(dataMap.get(tempDate).get("refundAmount"));
				}
			}

			// 保存
			List<Object> beans = new ArrayList<>();
			beans.add(stats);
			saveDailyInfos(request, beans);
		}

		return String.format("hospitalCode: [%s], branchCode: [%s], platform: [%s]. stats end...",
				new Object[] { request.getHospitalCode(), request.getBranchCode(), request.getPlatform() });
	}

	private Map<String, Map<String, Integer>> getAllStats(String hospitalCode, String branchCode, String statsDate, String beginDate) {
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

		// 日期区间的过滤
		if (StringUtils.isNotBlank(beginDate)) {
			sbQuery.setLength(0);
			sbQuery.append("statsDate").append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(beginDate).append(SolrConstant.TO)
					.append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
			solrQuery.setFilterQueries(sbQuery.toString());
		}

		solrQuery.setGetFieldStatistics(true);
		solrQuery.addGetFieldStatistics("netIncome");
		solrQuery.addGetFieldStatistics("regPaidAmount");
		solrQuery.addGetFieldStatistics("clinicPaidAmount");
		solrQuery.addGetFieldStatistics("depositPaidAmount");
		solrQuery.addGetFieldStatistics("regRefundAmount");
		solrQuery.addGetFieldStatistics("clinicRefundAmount");
		solrQuery.addGetFieldStatistics("depositRefundAmount");
		solrQuery.addGetFieldStatistics("regPaidQuantity");
		solrQuery.addGetFieldStatistics("clinicPaidQuantity");
		solrQuery.addGetFieldStatistics("depositPaidQuantity");
		solrQuery.addGetFieldStatistics("regRefundQuantity");
		solrQuery.addGetFieldStatistics("clinicRefundQuantity");
		solrQuery.addGetFieldStatistics("depositRefundQuantity");
		solrQuery.addGetFieldStatistics("cumulativeQuantity");
		solrQuery.addGetFieldStatistics("cumulativePaidQuantity");
		solrQuery.addGetFieldStatistics("cumulativeRefundQuantity");
		solrQuery.addGetFieldStatistics("cumulativePaidAmount");
		solrQuery.addGetFieldStatistics("cumulativeRefundAmount");
		solrQuery.addGetFieldStatistics("ordersQuantity");
		solrQuery.addGetFieldStatistics("paidQuantity");
		solrQuery.addGetFieldStatistics("refundQuantity");
		solrQuery.addGetFieldStatistics("paidAmount");
		solrQuery.addGetFieldStatistics("refundAmount");
		solrQuery.addStatsFieldFacets(null, "statsDate");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_ORDER, solrQuery);
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
			logger.error("获取全平台订单统计数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	@Override
	public YxwResponse searchOrders(OrderInfoRequest request) {
		YxwResponse yxwResponse = new YxwResponse();
		YxwResult yxwResult = new YxwResult();
		String message = null;

		if (request.getPlatform() != -1) {
			ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("findRegisterOrders"));
			List<FutureTask<OrderInfoResponse>> tasks = new ArrayList<FutureTask<OrderInfoResponse>>();

			try {
				Set<CoreVo> coreList = getCores(request.getPlatform(), request.getBizType());
				if (CollectionUtils.isNotEmpty(coreList)) {
					// 没指定core则查全部
					for (CoreVo core : coreList) {
						if (StringUtils.isNotBlank(core.getCoreName())) {
							// Callable<Map<String, Object>> callable = new
							// OrdersQueryCallable(core, solrQuery);
							SolrQuery solrQuery = getQueryParams(request, core.getPlatform());
							// 查询所有
							solrQuery.set(CommonParams.START, 0);
							solrQuery.set(CommonParams.ROWS, Integer.MAX_VALUE);

							Callable<OrderInfoResponse> callable = new OrdersQueryCallable(core, solrQuery);

							FutureTask<OrderInfoResponse> task = new FutureTask<OrderInfoResponse>(callable);
							tasks.add(task);
							executorService.submit(task);
						}
					}

					if (tasks.size() > 0) {
						List<OrderInfoResponse> beans = new ArrayList<>();
						int size = 0;
						for (FutureTask<OrderInfoResponse> futureTask : tasks) {
							OrderInfoResponse orderInfoResponse = futureTask.get(Integer.MAX_VALUE, TimeUnit.DAYS);
							if (orderInfoResponse != null) {
								size += orderInfoResponse.getSize();
								beans.add(orderInfoResponse);
							}
						}

						Collections.sort(beans, new OrderInfoResponseComparator());

						yxwResult.setSize(size);
						yxwResult.setItems(beans);

						// results = JSON.toJSONString(solrDocumentList);
					}
				} else {
					message = "there is no cores. params: {}." + JSON.toJSONString(request);
					logger.warn(message);
				}
			} catch (Exception e) {
				logger.error("findOrders error. params: {}. errorMsg: {}. cause: {}.",
						new Object[] { JSON.toJSONString(request), e.getMessage(), e.getCause() });
			} finally {
				executorService.shutdown();
			}
		} else {
			message = ErrorMessage.NEED_ASSIGN_PLATFORM;
		}

		yxwResponse.setResult(yxwResult);
		yxwResponse.setResultMessage(message);
		return yxwResponse;
	}

	@Override
	public Map<String, Object> rebuildWithinPlatform(OrderStatsRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("request", request);

		Map<String, Object> beans = new HashMap<String, Object>();
		// 获取开始时间
		String firstDateOfClinic = getStartDate(Cores.CORE_STATS_CLINIC);
		String firstDateOfRegister = getStartDate(Cores.CORE_STATS_REGISTER);
		String firstDateOfDeposit = getStartDate(Cores.CORE_STATS_DEPOSIT);
		String startDate = getMinString(new String[] { firstDateOfClinic, firstDateOfRegister, firstDateOfDeposit });
		// 统计日期从beginDate开始
		String statsDate = request.getBeginDate();
		String tempDate = statsDate;
		String endDate = DateUtils.getDayForDate(new Date(), -1);

		if (statsDate.compareTo(startDate) >= 0 && statsDate.compareTo(endDate) <= 0) {
			// 获取统计数据
			SolrQuery solrQuery = getSolrQueryParams(request);
			Map<String, ClinicStats> clinicMap = getClinicStatsInfos(solrQuery);
			Map<String, RegStats> regMap = getRegStatsInfos(solrQuery);
			Map<String, DepositStats> depositMap = getDepositStatsInfos(solrQuery);

			while (tempDate.compareTo(endDate) <= 0) {
				OrderStats stats = new OrderStats();
				stats.setId(PKGenerator.generateId());
				stats.setHospitalCode(request.getHospitalCode());
				stats.setBranchCode(request.getBranchCode());
				stats.setPlatform(request.getPlatform());
				stats.setStatsDate(tempDate);
				Date date = new Date();
				stats.setUpdateTime_utc(UTCDateUtils.parseDate(date));
				stats.setUpdateTime(DateUtils.getDateStr(date));

				// Integer netIncome = 0;
				Integer regPaidAmount = 0;
				Integer regRefundAmount = 0;
				Integer clinicPaidAmount = 0;
				Integer clinicRefundAmount = 0;
				Integer depositPaidAmount = 0;
				Integer depositRefundAmount = 0;

				Integer regPaidQuantity = 0;
				Integer clinicPaidQuantity = 0;
				Integer depositPaidQuantity = 0;
				Integer regRefundQuantity = 0;
				Integer clinicRefundQuantity = 0;
				Integer depositRefundQuantity = 0;
				Integer paidQuantity = 0;
				Integer refundQuantity = 0;
				Integer paidAmount = 0;
				Integer refundAmount = 0;

				// 处理挂号
				if (regMap.get(tempDate) != null) {
					RegStats regStats = regMap.get(tempDate);
					regPaidAmount = regStats.getPaidAmount();
					regPaidQuantity = regStats.getPaidQuantity();
					regRefundAmount = regStats.getRefundAmount();
					regRefundQuantity = regStats.getRefundQuantity();

					// netIncome += regPaidAmount - regRefundAmount;
					paidQuantity += regPaidQuantity;
					refundQuantity += regRefundQuantity;
					paidAmount += regPaidAmount;
					refundAmount += regRefundAmount;
				}

				// 处理门诊
				if (clinicMap.get(tempDate) != null) {
					ClinicStats clinicStats = clinicMap.get(tempDate);
					clinicPaidAmount = clinicStats.getPaidAmount();
					clinicPaidQuantity = clinicStats.getPaidQuantity();
					clinicRefundAmount = clinicStats.getRefundAmount();
					clinicRefundQuantity = clinicStats.getRefundQuantity();

					// netIncome += clinicPaidAmount - clinicRefundAmount;
					paidQuantity += clinicPaidQuantity;
					refundQuantity += clinicRefundQuantity;
					paidAmount += clinicPaidAmount;
					refundAmount += clinicRefundAmount;
				}

				// 处理住院
				if (depositMap.get(tempDate) != null) {
					DepositStats depositStats = depositMap.get(tempDate);
					depositPaidAmount = depositStats.getPaidAmount();
					depositPaidQuantity = depositStats.getPaidQuantity();
					depositRefundAmount = depositStats.getRefundAmount();
					depositRefundQuantity = depositStats.getRefundQuantity();

					// netIncome += depositAmount - depositRefundAmount;
					paidQuantity += depositPaidQuantity;
					refundQuantity += depositRefundQuantity;
					paidAmount += depositPaidAmount;
					refundAmount += depositRefundAmount;
				}

				// 各类总额
				stats.setNetIncome(paidAmount - refundAmount);
				stats.setRegPaidAmount(regPaidAmount);
				stats.setRegRefundAmount(regRefundAmount);
				stats.setClinicPaidAmount(clinicPaidAmount);
				stats.setClinicRefundAmount(clinicRefundAmount);
				stats.setDepositPaidAmount(depositPaidAmount);
				stats.setDepositRefundAmount(depositRefundAmount);

				// 各类订单数量
				stats.setRegPaidQuantity(regPaidQuantity);
				stats.setRegRefundQuantity(regRefundQuantity);
				stats.setClinicPaidQuantity(clinicPaidQuantity);
				stats.setClinicRefundQuantity(clinicRefundQuantity);
				stats.setDepositPaidQuantity(depositPaidQuantity);
				stats.setDepositRefundQuantity(depositRefundQuantity);

				// 总额+数量
				stats.setOrdersQuantity(paidQuantity + refundQuantity);
				stats.setPaidQuantity(paidQuantity);
				stats.setRefundQuantity(refundQuantity);
				stats.setPaidAmount(paidAmount);
				stats.setRefundAmount(refundAmount);

				// 累计
				Integer cumulativeQuantity = stats.getOrdersQuantity();
				Integer cumulativePaidQuantity = paidQuantity;
				Integer cumulativeRefundQuantity = refundQuantity;
				Integer cumulativePaidAmount = paidAmount;
				Integer cumulativeRefundAmount = refundAmount;

				// 有没有之前那天的数据，没有就之后都不算了
				String preDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), -1);
				if (beans.get(preDate) != null) {
					OrderStats preStats = (OrderStats) beans.get(preDate);
					cumulativeQuantity += preStats.getCumulativeQuantity();
					cumulativePaidAmount += preStats.getCumulativePaidAmount();
					cumulativePaidQuantity += preStats.getCumulativePaidQuantity();
					cumulativeRefundAmount += preStats.getCumulativeRefundAmount();
					cumulativeRefundQuantity += preStats.getCumulativeRefundQuantity();
				}

				stats.setCumulativePaidAmount(cumulativePaidAmount);
				stats.setCumulativePaidQuantity(cumulativePaidQuantity);
				stats.setCumulativeQuantity(cumulativeQuantity);
				stats.setCumulativeRefundAmount(cumulativeRefundAmount);
				stats.setCumulativeRefundQuantity(cumulativeRefundQuantity);

				beans.put(tempDate, stats);

				tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
			}

			if (CollectionUtils.isNotEmpty(beans.values())) {
				// saveStatsInfos(request, beans.values());
				saveRebuildInfos(request, beans.values());
			}

			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_SUCCESS);
		} else {
			String resultMsg = String.format("统计时间不在区间内！统计时间: [%s]. 实际开始时间: [%s]. 最大结束时间: [%s].", new Object[] { statsDate, startDate, endDate });
			logger.error(resultMsg);
			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_ERROR);
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> rebuildWithoutPlatform(OrderStatsRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("request", request);

		Map<String, Object> statsMap = new HashMap<>();

		// 统计日期从beginDate开始
		String statsDate = request.getBeginDate();
		// 在折执行之前，orderStats的Core里面就已经有了具体分院具体平台的数据了
		String startDate = getStartDate(Cores.CORE_STATS_ORDER);
		String tempDate = statsDate;
		// 统计到昨天之前，昨天的数据，用轮训做
		String endDate = DateUtils.getDayForDate(new Date(), -1);

		if (statsDate.compareTo(startDate) >= 0 && statsDate.compareTo(endDate) <= 0) {
			Map<String, Map<String, Integer>> dataMap = getAllStats(request.getHospitalCode(), request.getBranchCodeValue(), null, statsDate);
			while (tempDate.compareTo(endDate) <= 0) {

				Date curDate = new Date();
				OrderStats stats = new OrderStats();
				stats.setHospitalCode(request.getHospitalCode());
				stats.setBranchCode(request.getBranchCode());
				stats.setPlatform(-1);
				stats.setId(PKGenerator.generateId());
				stats.setStatsDate(tempDate);
				stats.setUpdateTime(DateUtils.dateToString(curDate));
				stats.setUpdateTime_utc(UTCDateUtils.parseDate(curDate));

				// 门诊
				stats.setClinicPaidAmount(0);
				stats.setClinicPaidQuantity(0);
				stats.setClinicRefundAmount(0);
				stats.setClinicRefundQuantity(0);
				// 累计
				stats.setCumulativePaidAmount(0);
				stats.setCumulativePaidQuantity(0);
				stats.setCumulativeQuantity(0);
				stats.setCumulativeRefundAmount(0);
				stats.setCumulativeRefundQuantity(0);
				// 押金
				stats.setDepositPaidAmount(0);
				stats.setDepositPaidQuantity(0);
				stats.setDepositRefundAmount(0);
				stats.setDepositRefundQuantity(0);
				// 总统计
				stats.setNetIncome(0);
				stats.setOrdersQuantity(0);
				stats.setPaidAmount(0);
				stats.setPaidQuantity(0);
				stats.setRefundAmount(0);
				stats.setRefundQuantity(0);
				// 挂号
				stats.setRegPaidAmount(0);
				stats.setRegPaidQuantity(0);
				stats.setRegRefundAmount(0);
				stats.setRegRefundQuantity(0);

				if (dataMap.get(tempDate) != null) {
					// netIncome
					if (dataMap.get(tempDate).get("netIncome") != null) {
						stats.setNetIncome(dataMap.get(tempDate).get("netIncome"));
					}

					// regPaidAmount
					if (dataMap.get(tempDate).get("regPaidAmount") != null) {
						stats.setRegPaidAmount(dataMap.get(tempDate).get("regPaidAmount"));
					}

					// clinicPaidAmount
					if (dataMap.get(tempDate).get("clinicPaidAmount") != null) {
						stats.setClinicPaidAmount(dataMap.get(tempDate).get("clinicPaidAmount"));
					}

					// depositPaidAmount
					if (dataMap.get(tempDate).get("depositPaidAmount") != null) {
						stats.setDepositPaidAmount(dataMap.get(tempDate).get("depositPaidAmount"));
					}

					// regRefundAmount
					if (dataMap.get(tempDate).get("regRefundAmount") != null) {
						stats.setRegRefundAmount(dataMap.get(tempDate).get("regRefundAmount"));
					}

					// refundQuantity
					if (dataMap.get(tempDate).get("clinicRefundAmount") != null) {
						stats.setClinicRefundAmount(dataMap.get(tempDate).get("clinicRefundAmount"));
					}

					// depositRefundAmount
					if (dataMap.get(tempDate).get("depositRefundAmount") != null) {
						stats.setDepositRefundAmount(dataMap.get(tempDate).get("depositRefundAmount"));
					}

					// regPaidQuantity
					if (dataMap.get(tempDate).get("regPaidQuantity") != null) {
						stats.setRegPaidQuantity(dataMap.get(tempDate).get("regPaidQuantity"));
					}

					// clinicPaidQuantity
					if (dataMap.get(tempDate).get("clinicPaidQuantity") != null) {
						stats.setClinicPaidQuantity(dataMap.get(tempDate).get("clinicPaidQuantity"));
					}

					// depositPaidQuantity
					if (dataMap.get(tempDate).get("depositPaidQuantity") != null) {
						stats.setDepositPaidQuantity(dataMap.get(tempDate).get("depositPaidQuantity"));
					}

					// regRefundQuantity
					if (dataMap.get(tempDate).get("regRefundQuantity") != null) {
						stats.setRegRefundQuantity(dataMap.get(tempDate).get("regRefundQuantity"));
					}

					// clinicRefundQuantity
					if (dataMap.get(tempDate).get("clinicRefundQuantity") != null) {
						stats.setClinicRefundQuantity(dataMap.get(tempDate).get("clinicRefundQuantity"));
					}

					// depositRefundQuantity
					if (dataMap.get(tempDate).get("depositRefundQuantity") != null) {
						stats.setDepositRefundQuantity(dataMap.get(tempDate).get("depositRefundQuantity"));
					}

					// cumulativeQuantity
					if (dataMap.get(tempDate).get("cumulativeQuantity") != null) {
						stats.setCumulativeQuantity(dataMap.get(tempDate).get("cumulativeQuantity"));
					}

					// cumulativePaidQuantity
					if (dataMap.get(tempDate).get("cumulativePaidQuantity") != null) {
						stats.setCumulativePaidQuantity(dataMap.get(tempDate).get("cumulativePaidQuantity"));
					}

					// cumulativeRefundQuantity
					if (dataMap.get(tempDate).get("cumulativeRefundQuantity") != null) {
						stats.setCumulativeRefundQuantity(dataMap.get(tempDate).get("cumulativeRefundQuantity"));
					}

					// cumulativePaidAmount
					if (dataMap.get(tempDate).get("cumulativePaidAmount") != null) {
						stats.setCumulativePaidAmount(dataMap.get(tempDate).get("cumulativePaidAmount"));
					}

					// cumulativeRefundAmount
					if (dataMap.get(tempDate).get("cumulativeRefundAmount") != null) {
						stats.setCumulativeRefundAmount(dataMap.get(tempDate).get("cumulativeRefundAmount"));
					}

					// ordersQuantity
					if (dataMap.get(tempDate).get("ordersQuantity") != null) {
						stats.setOrdersQuantity(dataMap.get(tempDate).get("ordersQuantity"));
					}

					// paidQuantity
					if (dataMap.get(tempDate).get("paidQuantity") != null) {
						stats.setPaidQuantity(dataMap.get(tempDate).get("paidQuantity"));
					}

					// refundQuantity
					if (dataMap.get(tempDate).get("refundQuantity") != null) {
						stats.setRefundQuantity(dataMap.get(tempDate).get("refundQuantity"));
					}

					// paidAmount
					if (dataMap.get(tempDate).get("paidAmount") != null) {
						stats.setPaidAmount(dataMap.get(tempDate).get("paidAmount"));
					}

					// refundAmount
					if (dataMap.get(tempDate).get("refundAmount") != null) {
						stats.setRefundAmount(dataMap.get(tempDate).get("refundAmount"));
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

			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_SUCCESS);
		} else {
			String resultMsg = String.format("统计时间不在区间内！统计时间: [%s]. 实际开始时间: [%s]. 最大结束时间: [%s].", new Object[] { statsDate, startDate, endDate });
			logger.error(resultMsg);
			resultMap.put("isSuccess", ResultConstant.RESULT_CODE_ERROR);
		}

		return resultMap;
	}

	@Override
	public YxwResult orderQuery(List<OrderInfoRequest> orderInfoRequests) {
		YxwResult yxwResult = new YxwResult();

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("orderQuery"));
		List<FutureTask<List<OrdersQueryResult>>> tasks = new ArrayList<FutureTask<List<OrdersQueryResult>>>();

		try {
			for (OrderInfoRequest request: orderInfoRequests) {
				Set<CoreVo> coreVos = getCores(request.getPlatform(), request.getBizType());
				Iterator<CoreVo> iterator = coreVos.iterator();
				while (iterator.hasNext()) {
					CoreVo coreVo = iterator.next();
					if (StringUtils.isNoneBlank(coreVo.getCoreName())) {
						// 此处返回的应该是多个查询条件 clinic&&deposit 分支付和全额退费两种查询。
						String coreName = coreVo.getCoreName();
						Integer orderMode = Math.abs(coreVo.getBizType());
						if (coreVo.getBizType() > 0) {						// 支付&&全额退费  -- 原表
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
						} else {											// 部分退费的参数
							SolrQuery refundQuery = getOutsidePartRefundParams(request, coreVo.getPlatform());
							Callable<List<OrdersQueryResult>> refundCallable = new OutsidePartRefundOrderCallable(refundQuery, coreName, orderMode);
							FutureTask<List<OrdersQueryResult>> refundTask = new FutureTask<List<OrdersQueryResult>>(refundCallable);
							tasks.add(refundTask);
							executorService.submit(refundTask);
						}
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
	
	private SolrQuery getOutsidePayParams(OrderInfoRequest request, Integer platform) {
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
		// createTime:[begin TO end]
		sbQuery = new StringBuffer("payTime");
		sbQuery.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(request.getBeginTimestamp()).append(SolrConstant.TO);
		sbQuery.append(request.getEndTimestamp()).append(SolrConstant.INTERVAL_END);
		solrQuery.addFilterQuery(sbQuery.toString());
		// payTime:['' TO *]
//		sbQuery = new StringBuffer("payTime");
//		sbQuery.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append("''").append(SolrConstant.TO);
//		sbQuery.append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
//		solrQuery.addFilterQuery(sbQuery.toString());
		
		solrQuery.set(CommonParams.ROWS, Integer.MAX_VALUE);
		solrQuery.setSort("payTime", ORDER.desc);

		return solrQuery;
	}
	
	private SolrQuery getOutsideRefundParams(OrderInfoRequest request, Integer platform) {
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
		// payTime:['' TO *]
//		sbQuery = new StringBuffer("payTime");
//		sbQuery.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append("''").append(SolrConstant.TO);
//		sbQuery.append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
//		solrQuery.addFilterQuery(sbQuery.toString());
		
		solrQuery.set(CommonParams.ROWS, Integer.MAX_VALUE);
		solrQuery.setSort("refundTime", ORDER.desc);
		
		return solrQuery;
	}
	
	private SolrQuery getOutsidePartRefundParams(OrderInfoRequest request, Integer platform) {
		SolrQuery solrQuery = new SolrQuery();
		
		StringBuffer sbRefundQuery = new StringBuffer();
		sbRefundQuery.append("hospitalCode").append(SolrConstant.COLON).append(request.getHospitalCode());
		sbRefundQuery.append(SolrConstant.AND).append("platform").append(SolrConstant.COLON).append(platform);
		sbRefundQuery.append(SolrConstant.AND).append("bizStatus").append(SolrConstant.COLON).append(31);			// 业务状态31：部分退费成功
		if (!request.getBranchCode().equals("-1")) {
			sbRefundQuery.append(SolrConstant.AND).append("branchCode").append(SolrConstant.COLON).append(request.getBranchCodeValue());			
		}
		solrQuery.setQuery(sbRefundQuery.toString());
		// refundTime:[begin TO end]
		StringBuffer sbRefund = new StringBuffer("refundTime");
		sbRefund.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append(request.getBeginTimestamp()).append(SolrConstant.TO);
		sbRefund.append(request.getEndTimestamp()).append(SolrConstant.INTERVAL_END);
		solrQuery.addFilterQuery(sbRefund.toString());
		
		// payTime:['' TO *]
//		sbRefund = new StringBuffer("payTime");
//		sbRefund.append(SolrConstant.COLON).append(SolrConstant.INTERVAL_START).append("''").append(SolrConstant.TO);
//		sbRefund.append(SolrConstant.ALL_VALUE).append(SolrConstant.INTERVAL_END);
//		solrQuery.addFilterQuery(sbRefund.toString());
		
		solrQuery.set(CommonParams.ROWS, Integer.MAX_VALUE);
		solrQuery.setSort("refundTime", ORDER.desc);
		
		return solrQuery;
	}
}
