package com.yxw.biz.deposit.service.impl;

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
import com.yxw.biz.deposit.service.DepositService;
import com.yxw.client.YxwSolrClient;
import com.yxw.client.YxwUpdateClient;
import com.yxw.constants.Cores;
import com.yxw.constants.PlatformConstant;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.utils.DateUtils;
import com.yxw.vo.deposit.DepositStats;
import com.yxw.vo.deposit.DepositStatsRequest;
import com.yxw.vo.deposit.DepositVo;

@Service(value = "depositService")
public class DepositServiceImpl implements DepositService {

	private Logger logger = LoggerFactory.getLogger(DepositService.class);

	private YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);

	@Override
	public List<DepositStats> getStatsInfos(DepositStatsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String statsInfos(DepositStatsRequest request) {
		String beginDate = StatsConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();
		// endDate = "2016-10-19";

		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		Map<String, DepositVo> depositVos = getDepositInfosByHospital(request.getHospitalCode(), "", request.getPlatform());

		// 全部生成则直接new，如果是天天轮训则需要先去solr查一遍
		Map<String, Object> statsMap = new LinkedHashMap<>();

		Integer cumulateWxRefundCount = 0;
		Integer cumulateAliRefundCount = 0;
		Integer cumulateWxPayCount = 0;
		Integer cumulateAliPayCount = 0;
		Integer cumulateWxPartRefundCount = 0;
		Integer cumulateAliPartRefundCount = 0;
		Integer cumulateWxNoPayCount = 0;
		Integer cumulateAliNoPayCount = 0;
		Long cumulateWxPayAmount = 0L;
		Long cumulateAliPayAmount = 0L;
		Long cumulateWxRefundAmount = 0L;
		Long cumulateAliRefundAmount = 0L;
		Long cumulateWxPartRefundAmount = 0L;
		Long cumulateAliPartRefundAmount = 0L;

		while (tempDate.compareTo(endDate) < 0) {
			DepositVo vo = depositVos.get(tempDate);

			if (vo == null) {
				logger.warn("医院[{}]在[{}]没有数据。", new Object[] { request.getHospitalName().trim(), tempDate.trim() });
			}

			// 累计当月
			String tmpDate = tempDate.substring(0, 7);
			DepositStats stats = null;
			if (statsMap.containsKey(tmpDate)) {
				stats = (DepositStats) statsMap.get(tmpDate);
			} else {
				stats = new DepositStats();
				BeanUtils.copyProperties(request, stats);
				stats.setPlatform(request.getPlatform());
				stats.setId(PKGenerator.generateId());
			}

			int monthWxRefundCount = 0;
			int monthAliRefundCount = 0;
			int monthWxPayCount = 0;
			int monthAliPayCount = 0;
			int monthWxPartRefundCount = 0;
			int monthAliPartRefundCount = 0;
			int monthWxNoPayCount = 0;
			int monthAliNoPayCount = 0;
			long monthWxPayAmount = 0l;
			long monthAliPayAmount = 0l;
			long monthWxRefundAmount = 0l;
			long monthAliRefundAmount = 0l;
			long monthWxPartRefundAmount = 0l;
			long monthAliPartRefundAmount = 0l;

			if (vo != null) {
				monthWxRefundCount = vo.getRefundWechat();
				monthAliRefundCount = vo.getRefundAlipay();
				monthWxPayCount = vo.getPaymentWechat();
				monthAliPayCount = vo.getPaymentAlipay();
				if (vo.getPartRefundWechat() != null) {
					monthWxPartRefundCount = vo.getPartRefundWechat().intValue();
				}
				if (vo.getPartRefundAlipay() != null) {
					monthAliPartRefundCount = vo.getPartRefundAlipay().intValue();
				}
				monthWxNoPayCount = vo.getNoPaymentWechat();
				monthAliNoPayCount = vo.getNoPaymentAlipay();
				monthWxPayAmount = vo.getDepositPayFeeWechat();
				monthAliPayAmount = vo.getDepositPayFeeAlipay();
				monthWxRefundAmount = vo.getDepositRefundFeeWechat();
				monthAliRefundAmount = vo.getDepositRefundFeeAlipay();

				// 这两个金额，需要他那边给定，才能通过get获取~！！！！！！！！！！！！！！！！
				monthWxPartRefundAmount = 0;
				monthAliPartRefundAmount = 0;
			}

			cumulateWxRefundCount += monthWxRefundCount;
			cumulateAliRefundCount += monthAliRefundCount;
			cumulateWxPayCount += monthWxPayCount;
			cumulateAliPayCount += monthAliPayCount;
			cumulateWxPartRefundCount += monthWxPartRefundCount;
			cumulateAliPartRefundCount += monthAliPartRefundCount;

			cumulateWxNoPayCount += monthWxNoPayCount;
			cumulateAliNoPayCount += monthAliNoPayCount;

			cumulateWxPayAmount += monthWxPayAmount;
			cumulateAliPayAmount += monthAliPayAmount;
			cumulateWxRefundAmount += monthWxRefundAmount;
			cumulateAliRefundAmount += monthAliRefundAmount;
			cumulateWxPartRefundAmount += monthWxPartRefundAmount;
			cumulateAliPartRefundAmount += monthAliPartRefundAmount;

			// 日期
			stats.setThisMonth(tempDate.substring(0, 7));
			stats.setLastMonth(DateUtils.getFirstDayOfMonth(tempDate, -1).substring(0, 7));

			// [每月]
			stats.setMonthAliNoPayCount(stats.getMonthAliNoPayCount() + monthAliNoPayCount);
			stats.setMonthAliPartRefundAmount(stats.getMonthAliPartRefundAmount() + monthAliPartRefundAmount);
			stats.setMonthAliPartRefundCount(stats.getMonthAliPartRefundCount() + monthAliPartRefundCount);
			stats.setMonthAliPayAmount(stats.getMonthAliPayAmount() + monthAliPayAmount);
			stats.setMonthAliRefundAmount(stats.getMonthAliRefundAmount() + monthAliRefundAmount);
			stats.setMonthAliRefundCount(stats.getMonthAliRefundCount() + monthAliRefundCount);
			stats.setMonthWxNoPayCount(stats.getMonthWxNoPayCount() + monthWxNoPayCount);
			stats.setMonthWxPartRefundAmount(stats.getMonthWxPartRefundAmount() + monthWxPartRefundAmount);
			stats.setMonthWxPartRefundCount(stats.getMonthWxPartRefundCount() + monthWxPartRefundCount);
			stats.setMonthWxPayAmount(stats.getMonthWxPayAmount() + monthWxPayAmount);
			stats.setMonthWxPayCount(stats.getMonthWxPayCount() + monthWxPayCount);
			stats.setMonthWxRefundAmount(stats.getMonthWxRefundAmount() + monthWxRefundAmount);
			stats.setMonthWxRefundCount(stats.getMonthWxRefundCount() + monthWxRefundCount);
			stats.setMonthAliPayCount(stats.getMonthAliPayCount() + monthAliPayCount);

			stats.setMonthNoPayCount(stats.getMonthNoPayCount() + monthWxNoPayCount + monthAliNoPayCount);
			stats.setMonthPartRefundAmount(stats.getMonthWxPartRefundAmount() + stats.getMonthAliPartRefundAmount());
			stats.setMonthPartRefundCount(stats.getMonthWxPartRefundCount() + stats.getMonthAliPartRefundCount());
			stats.setMonthPayAmount(stats.getMonthWxPayAmount() + stats.getMonthAliPayAmount());
			stats.setMonthPayCount(stats.getMonthWxPayCount() + stats.getMonthAliPayCount());
			stats.setMonthRefundAmount(stats.getMonthWxRefundAmount() + stats.getMonthAliRefundAmount());
			stats.setMonthRefundCount(stats.getMonthWxRefundCount() + stats.getMonthAliRefundCount());

			// [累计]
			stats.setCumulateAliNoPayCount(cumulateAliNoPayCount);
			stats.setCumulateAliPartRefundAmount(cumulateAliPartRefundAmount);
			stats.setCumulateAliPartRefundCount(cumulateAliPartRefundCount);
			stats.setCumulateAliTotalAmount(cumulateAliPayAmount);
			stats.setCumulateAliTotalCount(cumulateAliPayCount);
			stats.setCumulateAliRefundAmount(cumulateAliRefundAmount);
			stats.setCumulateAliRefundCount(cumulateAliRefundCount);
			stats.setCumulateWxNoPayCount(cumulateWxNoPayCount);
			stats.setCumulateWxPartRefundAmount(cumulateWxPartRefundAmount);
			stats.setCumulateWxPartRefundCount(cumulateWxPartRefundCount);
			stats.setCumulateWxTotalAmount(cumulateWxPayAmount);
			stats.setCumulateWxTotalCount(cumulateWxPayCount);
			stats.setCumulateWxRefundAmount(cumulateWxRefundAmount);
			stats.setCumulateWxRefundCount(cumulateWxRefundCount);

			stats.setCumulateNoPayCount(cumulateWxNoPayCount + cumulateAliNoPayCount);
			stats.setCumulatePartRefundAmount(cumulateWxPartRefundAmount + cumulateAliPartRefundAmount);
			stats.setCumulatePartRefundCount(cumulateWxPartRefundCount + cumulateAliPartRefundCount);
			stats.setCumulateTotalAmount(cumulateWxPayAmount + cumulateAliPayAmount);
			stats.setCumulateTotalCount(cumulateAliPayCount + cumulateWxPayCount);
			stats.setCumulateRefundAmount(cumulateWxRefundAmount + cumulateAliRefundAmount);
			stats.setCumulateRefundCount(cumulateWxRefundCount + cumulateAliRefundCount);

			statsMap.put(tmpDate, stats);
			tempDate = DateUtils.getDayForDate(DateUtils.StringToDate(tempDate), 1);
		}

		saveStatsInfos(request, statsMap.values());

		return String.format("hospitalCode: [%s], all card stats end...", new Object[] { request.getHospitalCode() });
	}

	private void saveStatsInfos(DepositStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:" + request.getHospitalCode() + " AND ");
		sb.append("areaCode:" + request.getAreaCode().replace("/", "\\/"));

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getBeginDate())) {
			sb.append(" AND statsDate:[" + request.getBeginDate() + " TO *]");
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_DEPOSIT, collection, true, sb.toString());
	}

	/**
	 * 获取医院挂号信息
	 * 
	 * @param hospitalCode
	 * @return
	 */
	private Map<String, DepositVo> getDepositInfosByHospital(String hospitalCode, String sDate, int platformType) {
		Map<String, DepositVo> map = new HashMap<>();
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
				coreName = Cores.CORE_STD_DEPOSIT;
			} else if (platformType == PlatformConstant.PLATFORM_TYPE_HIS) {
				coreName = Cores.CORE_HIS_DEPOSIT;
			}
			QueryResponse response = solrClient.query(coreName, solrQuery);

			// 考虑到有分院的情况，一个日期下同一医院可能有多条信息，需要分别对项进行添加
			List<DepositVo> list = response.getBeans(DepositVo.class);

			for (DepositVo vo : list) {
				String statsDate = vo.getStatsDate();
				if (!map.containsKey(statsDate)) {
					map.put(statsDate, vo);
				} else {
					DepositVo tempVo = map.get(statsDate);
					tempVo.combineEntity(vo);
					map.put(statsDate, tempVo);
				}
			}

			// 如果他妈比是法棍西医科大附属第一医院，设置的是医院项目，狗日的跨两个平台，需要狗日对待
			if (hospitalCode.equals("gxykdxdyfsyy") || hospitalCode.equals("gzsdyrmyy")) {
				QueryResponse tempResponse = solrClient.query(Cores.CORE_STD_DEPOSIT, solrQuery);

				// 考虑到有分院的情况，一个日期下同一医院可能有多条信息，需要分别对项进行添加
				List<DepositVo> tempList = tempResponse.getBeans(DepositVo.class);
				for (DepositVo vo : tempList) {
					String statsDate = vo.getStatsDate();
					if (!map.containsKey(statsDate)) {
						map.put(statsDate, vo);
					} else {
						DepositVo tempVo = map.get(statsDate);
						tempVo.combineEntity(vo);
						map.put(statsDate, tempVo);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取所有医院门诊信息异常。 errorMsg: {}. cause by: {}.hospitalCode:[{}]",
					new Object[] { e.getMessage(), e.getCause(), hospitalCode });
		}

		return map;
	}

	@Override
	public String statsInfoForMonth(DepositStatsRequest request) {
		String statsDate = request.getBeginDate();
		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		Map<String, DepositStats> dataMap = getStatsInfoByStatsMonth(request);
		// 获取现在需要的信息
		Map<String, DepositVo> voMap = getDepositInfosByHospital(request.getHospitalCode(), request.getBeginDate(), request.getPlatform());
		String lastMonth = DateUtils.getFirstDayOfMonth(request.getBeginDate(), -2).substring(0, 7);

		// 上月统计信息
		DepositStats lastMonthStats = dataMap.get(lastMonth);

		// 本月统计
		DepositStats stats = new DepositStats();
		BeanUtils.copyProperties(request, stats);
		stats.setPlatform(lastMonthStats.getPlatform());
		stats.setId(PKGenerator.generateId());
		stats.setThisMonth(DateUtils.getFirstDayOfLastMonth(statsDate).substring(0, 7));
		stats.setLastMonth(lastMonth);

		Integer cumulateWxRefundCount = 0;
		Integer cumulateAliRefundCount = 0;
		Integer cumulateWxPayCount = 0;
		Integer cumulateAliPayCount = 0;
		Integer cumulateWxPartRefundCount = 0;
		Integer cumulateAliPartRefundCount = 0;
		Integer cumulateWxNoPayCount = 0;
		Integer cumulateAliNoPayCount = 0;

		Long cumulateWxPayAmount = 0L;
		Long cumulateAliPayAmount = 0L;
		Long cumulateWxRefundAmount = 0L;
		Long cumulateAliRefundAmount = 0L;
		Long cumulateWxPartRefundAmount = 0L;
		Long cumulateAliPartRefundAmount = 0L;

		String beginDate = DateUtils.getFirstDayOfLastMonth(statsDate);
		String endDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(DateUtils.getFirstDayOfThisMonth(statsDate)), -1);
		String tempDate = beginDate;

		while (tempDate.compareTo(endDate) <= 0) {
			DepositVo vo = voMap.get(tempDate);

			if (vo == null) {
				logger.warn("医院[{}]在[{}]没有数据。", new Object[] { request.getHospitalName().trim(), tempDate.trim() });
			}

			int monthWxRefundCount = 0;
			int monthAliRefundCount = 0;
			int monthWxPayCount = 0;
			int monthAliPayCount = 0;
			int monthWxPartRefundCount = 0;
			int monthAliPartRefundCount = 0;

			// 退费信息
			int monthWxNoPayCount = 0;
			int monthAliNoPayCount = 0;

			long monthWxPayAmount = 0;
			long monthAliPayAmount = 0;
			long monthWxRefundAmount = 0;
			long monthAliRefundAmount = 0;
			long monthWxPartRefundAmount = 0;
			long monthAliPartRefundAmount = 0;

			if (vo != null) {
				monthWxRefundCount = vo.getRefundWechat();
				monthAliRefundCount = vo.getRefundAlipay();
				monthWxPayCount = vo.getPaymentWechat();
				monthAliPayCount = vo.getPaymentAlipay();
				monthWxPartRefundCount = vo.getPartRefundWechat().intValue();
				monthAliPartRefundCount = vo.getPartRefundAlipay().intValue();
				monthWxNoPayCount = vo.getNoPaymentWechat();
				monthAliNoPayCount = vo.getNoPaymentAlipay();
				monthWxPayAmount = vo.getDepositPayFeeWechat();
				monthAliPayAmount = vo.getDepositPayFeeAlipay();
				monthWxRefundAmount = vo.getDepositRefundFeeWechat();
				monthAliRefundAmount = vo.getDepositRefundFeeAlipay();

				// 这两个金额，需要他那边给定，才能通过get获取~！！！！！！！！！！！！！！！！
				monthWxPartRefundAmount = 0;
				monthAliPartRefundAmount = 0;
			}

			cumulateWxRefundCount += monthWxRefundCount;
			cumulateAliRefundCount += monthAliRefundCount;
			cumulateWxPayCount += monthWxPayCount;
			cumulateAliPayCount += monthAliPayCount;
			cumulateWxPartRefundCount += monthWxPartRefundCount;
			cumulateAliPartRefundCount += monthAliPartRefundCount;

			cumulateWxNoPayCount += monthWxNoPayCount;
			cumulateAliNoPayCount += monthAliNoPayCount;

			cumulateWxPayAmount += monthWxPayAmount;
			cumulateAliPayAmount += monthAliPayAmount;
			cumulateWxRefundAmount += monthWxRefundAmount;
			cumulateAliRefundAmount += monthAliRefundAmount;
			cumulateWxPartRefundAmount += monthWxPartRefundAmount;
			cumulateAliPartRefundAmount += monthAliPartRefundAmount;

			// 日期
			stats.setThisMonth(tempDate.substring(0, 7));
			stats.setLastMonth(DateUtils.getFirstDayOfMonth(tempDate, -1).substring(0, 7));

			// [每月]
			stats.setMonthAliNoPayCount(stats.getMonthAliNoPayCount() + monthAliNoPayCount);
			stats.setMonthAliPartRefundAmount(stats.getMonthAliPartRefundAmount() + monthAliPartRefundAmount);
			stats.setMonthAliPartRefundCount(stats.getMonthAliPartRefundCount() + monthAliPartRefundCount);
			stats.setMonthAliPayAmount(stats.getMonthAliPayAmount() + monthAliPayAmount);
			stats.setMonthAliRefundAmount(stats.getMonthAliRefundAmount() + monthAliRefundAmount);
			stats.setMonthAliRefundCount(stats.getMonthAliRefundCount() + monthAliRefundCount);
			stats.setMonthWxNoPayCount(stats.getMonthWxNoPayCount() + monthWxNoPayCount);
			stats.setMonthWxPartRefundAmount(stats.getMonthWxPartRefundAmount() + monthWxPartRefundAmount);
			stats.setMonthWxPartRefundCount(stats.getMonthWxPartRefundCount() + monthWxPartRefundCount);
			stats.setMonthWxPayAmount(stats.getMonthWxPayAmount() + monthWxPayAmount);
			stats.setMonthWxPayCount(stats.getMonthWxPayCount() + monthWxPayCount);
			stats.setMonthWxRefundAmount(stats.getMonthWxRefundAmount() + monthWxRefundAmount);
			stats.setMonthWxRefundCount(stats.getMonthWxRefundCount() + monthWxRefundCount);
			stats.setMonthAliPayCount(stats.getMonthAliPayCount() + monthAliPayCount);

			stats.setMonthNoPayCount(stats.getMonthNoPayCount() + monthWxNoPayCount + monthAliNoPayCount);
			stats.setMonthPartRefundAmount(stats.getMonthWxPartRefundAmount() + stats.getMonthAliPartRefundAmount());
			stats.setMonthPartRefundCount(stats.getMonthWxPartRefundCount() + stats.getMonthAliPartRefundCount());
			stats.setMonthPayAmount(stats.getMonthWxPayAmount() + stats.getMonthAliPayAmount());
			stats.setMonthPayCount(stats.getMonthWxPayCount() + stats.getMonthAliPayCount());
			stats.setMonthRefundAmount(stats.getMonthWxRefundAmount() + stats.getMonthAliRefundAmount());
			stats.setMonthRefundCount(stats.getMonthWxRefundCount() + stats.getMonthAliRefundCount());

			tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
		}

		// [累计]
		stats.setCumulateAliNoPayCount(lastMonthStats.getCumulateAliNoPayCount() + cumulateAliNoPayCount);
		stats.setCumulateAliPartRefundAmount(lastMonthStats.getCumulateAliPartRefundAmount() + cumulateAliPartRefundAmount);
		stats.setCumulateAliPartRefundCount(lastMonthStats.getCumulateAliPartRefundCount() + cumulateAliPartRefundCount);
		stats.setCumulateAliTotalAmount(lastMonthStats.getCumulateAliTotalAmount() + cumulateAliPayAmount);
		stats.setCumulateAliTotalCount(lastMonthStats.getCumulateAliTotalCount() + cumulateAliPayCount);
		stats.setCumulateAliRefundAmount(lastMonthStats.getCumulateAliRefundAmount() + cumulateAliRefundAmount);
		stats.setCumulateAliRefundCount(lastMonthStats.getCumulateAliRefundCount() + cumulateAliRefundCount);
		stats.setCumulateWxNoPayCount(lastMonthStats.getCumulateWxNoPayCount() + cumulateWxNoPayCount);
		stats.setCumulateWxPartRefundAmount(lastMonthStats.getCumulateWxPartRefundAmount() + cumulateWxPartRefundAmount);
		stats.setCumulateWxPartRefundCount(lastMonthStats.getCumulateWxPartRefundCount() + cumulateWxPartRefundCount);
		stats.setCumulateWxTotalAmount(lastMonthStats.getCumulateWxTotalAmount() + cumulateWxPayAmount);
		stats.setCumulateWxTotalCount(lastMonthStats.getCumulateWxTotalCount() + cumulateWxPayCount);
		stats.setCumulateWxRefundAmount(lastMonthStats.getCumulateWxRefundAmount() + cumulateWxRefundAmount);
		stats.setCumulateWxRefundCount(lastMonthStats.getCumulateWxRefundCount() + cumulateWxRefundCount);

		stats.setCumulateNoPayCount(lastMonthStats.getCumulateNoPayCount() + cumulateWxNoPayCount + cumulateAliNoPayCount);
		stats.setCumulatePartRefundAmount(
				lastMonthStats.getCumulatePartRefundAmount() + cumulateWxPartRefundAmount + cumulateAliPartRefundAmount);
		stats.setCumulatePartRefundCount(
				lastMonthStats.getCumulatePartRefundCount() + cumulateWxPartRefundCount + cumulateAliPartRefundCount);
		stats.setCumulateTotalAmount(lastMonthStats.getCumulateTotalAmount() + cumulateWxPayAmount + cumulateAliPayAmount);
		stats.setCumulateTotalCount(lastMonthStats.getCumulateTotalCount() + cumulateAliPayCount + cumulateWxPayCount);
		stats.setCumulateRefundAmount(lastMonthStats.getCumulateRefundAmount() + cumulateWxRefundAmount + cumulateAliRefundAmount);
		stats.setCumulateRefundCount(lastMonthStats.getCumulateRefundCount() + cumulateWxRefundCount + cumulateAliRefundCount);

		saveMonthlyStatsInfo(request, stats);

		return String.format("hospitalCode: [%s], statsDate: [%s]. cards stats end...",
				new Object[] { request.getHospitalCode(), request.getBeginDate() });
	}
	
	private void saveMonthlyStatsInfo(DepositStatsRequest request, DepositStats stats) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:" + request.getHospitalCode() + " AND ");
		sb.append("thisMonth:" + DateUtils.getFirstDayOfLastMonth(request.getBeginDate()).substring(0, 7) + " AND ");
		sb.append("areaCode:" + request.getAreaCode().replace("/", "\\/"));

		YxwUpdateClient.addBean(Cores.CORE_STATS_DEPOSIT, stats, true, sb.toString());
	}

	private Map<String, DepositStats> getStatsInfoByStatsMonth(DepositStatsRequest request) {
		Map<String, DepositStats> resultMap = new HashMap<>();

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
			QueryResponse response = solrClient.query(Cores.CORE_STATS_DEPOSIT, solrQuery);
			List<DepositStats> resultList = response.getBeans(DepositStats.class);
			if (CollectionUtils.isNotEmpty(resultList)) {
				for (DepositStats stats : resultList) {
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
	public String statsAreaInfos(DepositStatsRequest request) {
		String beginDate = StatsConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();
		// endDate = "2016-10-19";

		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		Map<String, Map<String, Double>> dataMap = getAreaStatsByAreaCode(request.getAreaCode(), "");
		// logger.info(JSON.toJSONString(dataMap));

		Map<String, Object> statsMap = new LinkedHashMap<>();

		while (tempDate.compareTo(endDate) < 0) {
			String tDate = tempDate.substring(0, 7);
			if (dataMap.containsKey(tDate)) {
				DepositStats stats = new DepositStats();
				BeanUtils.copyProperties(request, stats);
				stats.setId(PKGenerator.generateId());
				// 这个platform的问题，得以后考虑。 标准平台1健康益2其他3吧？？
				stats.setPlatform(-1);
				stats.setHospitalCode("-");
				stats.setHospitalName("-");

				stats.setThisMonth(tempDate.substring(0, 7));
				stats.setLastMonth(DateUtils.getFirstDayOfMonth(tempDate, -1).substring(0, 7));

				Map<String, Double> datasMap = dataMap.get(tDate);
				for (Entry<String, Double> entry : datasMap.entrySet()) {
					String key = entry.getKey();
					try {
						org.apache.commons.beanutils.BeanUtils.setProperty(stats, key, entry.getValue().intValue());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				statsMap.put(tempDate, stats);
			}

			// 下月1号
			tempDate = DateUtils.getFirstDayNextMonth(tempDate);
		}

		saveAreaStatsInfos(request, statsMap.values());

		return String.format("AreaName: [%s], all subscribe stats end...", new Object[] { request.getAreaName() });
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
		solrQuery.addGetFieldStatistics("monthPayCount");
		solrQuery.addGetFieldStatistics("monthNoPayCount");
		solrQuery.addGetFieldStatistics("monthRefundCount");
		solrQuery.addGetFieldStatistics("monthPartRefundCount");
		solrQuery.addGetFieldStatistics("monthWxRefundCount");
		solrQuery.addGetFieldStatistics("monthAliRefundCount");
		solrQuery.addGetFieldStatistics("monthWxPayCount");
		solrQuery.addGetFieldStatistics("monthAliPayCount");
		solrQuery.addGetFieldStatistics("monthWxPartRefundCount");
		solrQuery.addGetFieldStatistics("monthAliPartRefundCount");
		solrQuery.addGetFieldStatistics("monthWxNoPayCount");
		solrQuery.addGetFieldStatistics("monthAliNoPayCount");
		solrQuery.addGetFieldStatistics("monthPayAmount");
		solrQuery.addGetFieldStatistics("monthWxPayAmount");
		solrQuery.addGetFieldStatistics("monthAliPayAmount");
		solrQuery.addGetFieldStatistics("monthRefundAmount");
		solrQuery.addGetFieldStatistics("monthWxRefundAmount");
		solrQuery.addGetFieldStatistics("monthAliRefundAmount");
		solrQuery.addGetFieldStatistics("monthPartRefundAmount");
		solrQuery.addGetFieldStatistics("monthWxPartRefundAmount");
		solrQuery.addGetFieldStatistics("monthAliPartRefundAmount");
		solrQuery.addGetFieldStatistics("cumulateTotalCount");
		solrQuery.addGetFieldStatistics("cumulateNoPayCount");
		solrQuery.addGetFieldStatistics("cumulateRefundCount");
		solrQuery.addGetFieldStatistics("cumulatePartRefundCount");
		solrQuery.addGetFieldStatistics("cumulateWxRefundCount");
		solrQuery.addGetFieldStatistics("cumulateAliRefundCount");
		solrQuery.addGetFieldStatistics("cumulateWxTotalCount");
		solrQuery.addGetFieldStatistics("cumulateAliTotalCount");
		solrQuery.addGetFieldStatistics("cumulateWxPartRefundCount");
		solrQuery.addGetFieldStatistics("cumulateAliPartRefundCount");
		solrQuery.addGetFieldStatistics("cumulateWxNoPayCount");
		solrQuery.addGetFieldStatistics("cumulateAliNoPayCount");
		solrQuery.addGetFieldStatistics("cumulateTotalAmount");
		solrQuery.addGetFieldStatistics("cumulateWxTotalAmount");
		solrQuery.addGetFieldStatistics("cumulateAliTotalAmount");
		solrQuery.addGetFieldStatistics("cumulateRefundAmount");
		solrQuery.addGetFieldStatistics("cumulateWxRefundAmount");
		solrQuery.addGetFieldStatistics("cumulateAliRefundAmount");
		solrQuery.addGetFieldStatistics("cumulatePartRefundAmount");
		solrQuery.addGetFieldStatistics("cumulateWxPartRefundAmount");
		solrQuery.addGetFieldStatistics("cumulateAliPartRefundAmount");
		solrQuery.addStatsFieldFacets(null, "thisMonth");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_DEPOSIT, solrQuery);
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

	private void saveAreaStatsInfos(DepositStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:\\- AND areaCode:" + request.getAreaCode().replace("/", "\\/"));

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getStatsMonth())) {
			sb.append(" AND thisMonth:[" + request.getStatsMonth() + " TO *]");
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_DEPOSIT, collection, true, sb.toString());
	}

	@Override
	public String statsAreaInfoForMonth(DepositStatsRequest request) {
		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		String lastMonth = DateUtils.getFirstDayOfMonth(request.getBeginDate(), -1).substring(0, 7);
		Map<String, Map<String, Double>> dataMap = getAreaStatsByAreaCode(request.getAreaCode(), lastMonth);

		DepositStats stats = new DepositStats();
		BeanUtils.copyProperties(request, stats);
		stats.setId(PKGenerator.generateId());
		stats.setPlatform(request.getPlatform());
		stats.setHospitalCode("-");
		stats.setHospitalName("-");
		stats.setThisMonth(lastMonth);
		stats.setLastMonth(DateUtils.getFirstDayOfMonth(request.getBeginDate(), -2).substring(0, 7));

		Map<String, Double> datasMap = dataMap.get(lastMonth);
		for (Entry<String, Double> entry : datasMap.entrySet()) {
			String key = entry.getKey();
			try {
				org.apache.commons.beanutils.BeanUtils.setProperty(stats, key, entry.getValue().intValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Map<String, Object> map = new HashMap<>();
		map.put(lastMonth, stats);
		saveAreaStatsInfos(request, map.values());

		return String.format("area deposit stats end...");
	}

}
