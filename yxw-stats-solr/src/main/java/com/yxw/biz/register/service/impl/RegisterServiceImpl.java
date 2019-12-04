package com.yxw.biz.register.service.impl;

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
import com.yxw.biz.register.service.RegisterService;
import com.yxw.client.YxwSolrClient;
import com.yxw.client.YxwUpdateClient;
import com.yxw.constants.Cores;
import com.yxw.constants.PlatformConstant;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.utils.DateUtils;
import com.yxw.vo.register.RegStats;
import com.yxw.vo.register.RegStatsRequest;
import com.yxw.vo.register.RegVo;

@Service(value = "registerService")
public class RegisterServiceImpl implements RegisterService {

	private Logger logger = LoggerFactory.getLogger(RegisterService.class);

	private YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);

	@Override
	public List<RegStats> getStatsInfos(RegStatsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String statsInfos(RegStatsRequest request) {
		String beginDate = StatsConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();
		// endDate = "2016-10-19";

		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		Map<String, RegVo> regVos = getRegInfosByHospital(request.getHospitalCode(), "", request.getPlatform());

		// 全部生成则直接new，如果是天天轮训则需要先去solr查一遍
		Map<String, Object> statsMap = new LinkedHashMap<>();

		Long cumulateWxTotalAmount = 0L;
		Long cumulateAliTotalAmount = 0L;
		Integer cumulateWxTotalCount = 0;
		Integer cumulateAliTotalCount = 0;
		Integer cumulateDutyWxPayCount = 0;
		Integer cumulateDutyAliPayCount = 0;
		Integer cumulateAppointmentWxPayCount = 0;
		Integer cumulateAppointmentAliPayCount = 0;

		Integer cumulateDutyNoPayCount = 0;
		Integer cumulateAppointmentNoPayCount = 0;
		Integer cumulateWxDutyNoPayCount = 0;
		Integer cumulateAliDutyNoPayCount = 0;
		Integer cumulateWxAppointmentNoPayCount = 0;
		Integer cumulateAliAppointmentNoPayCount = 0;

		Integer cumulateDutyWxRefundCount = 0;
		Integer cumulateDutyAliRefundCount = 0;
		Integer cumulateAppointmentWxRefundCount = 0;
		Integer cumulateAppointmentAliRefundCount = 0;
		Integer cumulateDutyRefundCount = 0;
		Integer cumulateAppointmentRefundCount = 0;
		Integer cumulateWxRefundCount = 0;
		Integer cumulateAliRefundCount = 0;
		Long cumulateWxRefundAmount = 0L;
		Long cumulateAliRefundAmount = 0L;
		Long cumulateRefundAmount = 0L;
		Integer cumulateRefundCount = 0;

		while (tempDate.compareTo(endDate) < 0) {
			RegVo vo = regVos.get(tempDate);

			if (vo == null) {
				logger.warn("医院[{}]在[{}]没有数据。", new Object[] { request.getHospitalName().trim(), tempDate.trim() });
			}

			// 累计当月
			String tmpDate = tempDate.substring(0, 7);
			RegStats stats = null;
			if (statsMap.containsKey(tmpDate)) {
				stats = (RegStats) statsMap.get(tmpDate);
			} else {
				stats = new RegStats();
				BeanUtils.copyProperties(request, stats);
				stats.setPlatform(request.getPlatform());
				stats.setId(PKGenerator.generateId());
			}

			int monthWxDutyCount = 0;
			int monthAliDutyCount = 0;
			int monthWxAppointmentCount = 0;
			int monthAliAppointmentCount = 0;

			int monthWxDutyNoPayCount = 0;
			int monthAliDutyNoPayCount = 0;
			int monthWxAppointmentNoPayCount = 0;
			int monthAliAppointmentNoPayCount = 0;
			// 退费
			int monthWxDutyRefundCount = 0;
			int monthAliDutyRefundCount = 0;
			int monthWxAppointmentRefundCount = 0;
			int monthAliAppointmentRefundCount = 0;

			long monthWxPayAmount = 0L;
			long monthAliPayAmount = 0L;
			long monthWxRefundAmount = 0L;
			long monthAliRefundAmount = 0L;

			if (vo != null) {
				monthWxDutyCount = vo.getDutyPaymentWechat();
				monthAliDutyCount = vo.getDutyPaymentAlipay();
				monthWxAppointmentCount = vo.getReservationPaymentWechat();
				monthAliAppointmentCount = vo.getReservationPaymentAlipay();

				monthWxDutyNoPayCount = vo.getDutyNoPaymentWechat();
				monthAliDutyNoPayCount = vo.getDutyNoPaymentAlipay();
				monthWxAppointmentNoPayCount = vo.getReservationNoPaymentWechat();
				monthAliAppointmentNoPayCount = vo.getReservationNoPaymentAlipay();

				monthWxDutyRefundCount = vo.getDutyRefundWechat();
				monthAliDutyRefundCount = vo.getDutyRefundAlipay();
				monthWxAppointmentRefundCount = vo.getReservationRefundWechat();
				monthAliAppointmentRefundCount = vo.getReservationRefundAlipay();

				// 下面几个，没法用细项。
				monthWxPayAmount = vo.getRegPayFeeWechat();
				monthAliPayAmount = vo.getRegPayFeeAlipay();
				monthWxRefundAmount = vo.getRegRefundFeeWechat();
				monthAliRefundAmount = vo.getRegRefundFeeAlipay();
			}

			cumulateWxTotalAmount += monthWxPayAmount;
			cumulateAliTotalAmount += monthAliPayAmount;

			cumulateWxTotalCount += monthWxDutyCount + monthWxAppointmentCount;
			cumulateAliTotalCount += monthAliDutyCount + monthAliAppointmentCount;

			cumulateDutyNoPayCount += monthWxDutyNoPayCount + monthAliDutyNoPayCount;
			cumulateAppointmentNoPayCount += monthWxAppointmentNoPayCount + monthAliAppointmentNoPayCount;
			cumulateWxDutyNoPayCount += monthWxDutyNoPayCount;
			cumulateAliDutyNoPayCount += monthAliDutyNoPayCount;
			cumulateWxAppointmentNoPayCount += monthWxAppointmentNoPayCount;
			cumulateAliAppointmentNoPayCount += monthAliAppointmentNoPayCount;

			cumulateDutyWxPayCount += monthWxDutyCount;
			cumulateDutyAliPayCount += monthAliDutyCount;
			cumulateAppointmentWxPayCount += monthWxAppointmentCount;
			cumulateAppointmentAliPayCount += monthAliAppointmentCount;

			cumulateDutyWxRefundCount += monthWxDutyRefundCount;
			cumulateDutyAliRefundCount += monthAliDutyRefundCount;
			cumulateAppointmentWxRefundCount += monthWxAppointmentRefundCount;
			cumulateAppointmentAliRefundCount += monthAliAppointmentRefundCount;
			cumulateDutyRefundCount += monthWxDutyRefundCount + monthAliDutyRefundCount;
			cumulateAppointmentRefundCount += monthWxAppointmentRefundCount + monthAliAppointmentRefundCount;
			cumulateWxRefundCount += monthWxDutyRefundCount + monthWxAppointmentRefundCount;
			cumulateAliRefundCount += monthAliDutyRefundCount + monthAliAppointmentRefundCount;

			cumulateWxRefundAmount += monthWxRefundAmount;
			cumulateAliRefundAmount += monthAliRefundAmount;
			cumulateRefundAmount += monthWxRefundAmount + monthAliRefundAmount;
			cumulateRefundCount += monthWxDutyRefundCount + monthAliDutyRefundCount + monthWxAppointmentRefundCount
					+ monthAliAppointmentRefundCount;

			// 日期
			stats.setThisMonth(tempDate.substring(0, 7));
			stats.setLastMonth(DateUtils.getFirstDayOfMonth(tempDate, -1).substring(0, 7));

			// [当月]支付宝
			stats.setMonthAliTotalAmount(stats.getMonthAliTotalAmount() + monthAliPayAmount);
			stats.setMonthAliTotalCount(stats.getMonthAliTotalCount() + monthAliDutyCount + monthAliAppointmentCount);
			// [当月]微信
			stats.setMonthWxTotalAmount(stats.getMonthWxTotalAmount() + monthWxPayAmount);
			stats.setMonthWxTotalCount(stats.getMonthWxTotalCount() + monthWxDutyCount + monthWxAppointmentCount);
			// [当月]支付宝预约、微信预约、预约、预约未支付笔数
			stats.setMonthAppointmentAliPayCount(stats.getMonthAppointmentAliPayCount() + monthAliAppointmentCount);
			stats.setMonthAppointmentNoPayCount(
					stats.getMonthAppointmentNoPayCount() + monthWxAppointmentNoPayCount + monthAliAppointmentNoPayCount);
			stats.setMonthAppointmentPayCount(stats.getMonthAppointmentPayCount() + monthWxAppointmentCount + monthAliAppointmentCount);
			stats.setMonthAppointmentWxPayCount(stats.getMonthAppointmentWxPayCount() + monthWxAppointmentCount);
			stats.setMonthWxAppointmentNoPayCount(stats.getMonthWxAppointmentNoPayCount() + monthWxAppointmentNoPayCount);
			stats.setMonthAliAppointmentNoPayCount(stats.getMonthAliAppointmentNoPayCount() + monthAliAppointmentNoPayCount);
			// [当月]支付宝当班、微信当班、当班、当班未支付笔数
			stats.setMonthDutyAliPayCount(stats.getMonthDutyAliPayCount() + monthAliDutyCount);
			stats.setMonthDutyNoPayCount(stats.getMonthDutyNoPayCount() + monthWxDutyNoPayCount + monthAliDutyNoPayCount);
			stats.setMonthDutyPayCount(stats.getMonthDutyPayCount() + monthWxDutyCount + monthAliDutyCount);
			stats.setMonthDutyWxPayCount(stats.getMonthDutyWxPayCount() + monthWxDutyCount);
			stats.setMonthWxDutyNoPayCount(stats.getMonthWxDutyNoPayCount() + monthWxDutyNoPayCount);
			stats.setMonthAliDutyNoPayCount(stats.getMonthAliDutyNoPayCount() + monthAliDutyNoPayCount);
			// [当月]未支付笔数、支付总额、支付总数
			stats.setMonthNoPayCount(stats.getMonthDutyNoPayCount() + stats.getMonthAppointmentNoPayCount());
			stats.setMonthTotalAmount(stats.getMonthWxTotalAmount() + stats.getMonthAliTotalAmount());
			stats.setMonthTotalCount(stats.getMonthWxTotalCount() + stats.getMonthAliTotalCount());
			// [当月]退费、微信退费、支付宝退费、微信预约退费、微信当班退费、支付宝预约退费、支付宝当班退费
			stats.setMonthDutyWxRefundCount(stats.getMonthDutyWxRefundCount() + monthWxDutyRefundCount);
			stats.setMonthDutyAliRefundCount(stats.getMonthDutyAliRefundCount() + monthAliDutyRefundCount);
			stats.setMonthAppointmentWxRefundCount(stats.getMonthAppointmentWxRefundCount() + monthWxAppointmentRefundCount);
			stats.setMonthAppointmentAliRefundCount(stats.getMonthAppointmentAliRefundCount() + monthAliAppointmentRefundCount);

			stats.setMonthDutyRefundCount(stats.getMonthDutyWxRefundCount() + stats.getMonthDutyAliRefundCount());
			stats.setMonthAppointmentRefundCount(stats.getMonthAppointmentWxRefundCount() + stats.getMonthAppointmentAliRefundCount());
			stats.setMonthWxRefundCount(stats.getMonthDutyWxRefundCount() + stats.getMonthAppointmentWxRefundCount());
			stats.setMonthAliRefundCount(stats.getMonthDutyWxRefundCount() + stats.getMonthAppointmentAliRefundCount());
			stats.setMonthRefundCount(stats.getMonthWxRefundCount() + stats.getMonthAliRefundCount());

			stats.setMonthWxRefundAmount(stats.getMonthWxRefundAmount() + monthWxRefundAmount);
			stats.setMonthAliRefundAmount(stats.getMonthAliRefundAmount() + monthAliRefundAmount);
			stats.setMonthRefundAmount(stats.getMonthWxRefundAmount() + stats.getMonthAliRefundAmount());
			stats.setMonthWxNoPayCount(stats.getMonthWxDutyNoPayCount() + stats.getMonthWxAppointmentNoPayCount());
			stats.setMonthAliNoPayCount(stats.getMonthAliDutyNoPayCount() + stats.getMonthAliAppointmentNoPayCount());

			// [累计] 支付宝总数、总额
			stats.setCumulateAliTotalAmount(cumulateAliTotalAmount);
			stats.setCumulateAliTotalCount(cumulateAliTotalCount);
			// [累计] 预约支付、预约微信支付、预约支付宝支付、预约未支付
			stats.setCumulateAppointmentAliPayCount(cumulateAppointmentAliPayCount);
			stats.setCumulateAppointmentNoPayCount(cumulateAppointmentNoPayCount);
			stats.setCumulateAppointmentPayCount(cumulateAppointmentAliPayCount + cumulateAppointmentWxPayCount);
			stats.setCumulateAppointmentWxPayCount(cumulateAppointmentWxPayCount);
			stats.setCumulateWxAppointmentNoPayCount(cumulateWxAppointmentNoPayCount);
			stats.setCumulateAliAppointmentNoPayCount(cumulateAliAppointmentNoPayCount);
			// [累计] 当班支付、当班微信支付、当班支付宝支付、当班未支付
			stats.setCumulateDutyAliPayCount(cumulateDutyAliPayCount);
			stats.setCumulateDutyNoPayCount(cumulateDutyNoPayCount);
			stats.setCumulateDutyPayCount(cumulateDutyAliPayCount + cumulateDutyWxPayCount);
			stats.setCumulateDutyWxPayCount(cumulateDutyWxPayCount);
			stats.setCumulateWxDutyNoPayCount(cumulateWxDutyNoPayCount);
			stats.setCumulateAliDutyNoPayCount(cumulateAliDutyNoPayCount);
			// [累计] 微信总数、总额
			stats.setCumulateWxTotalAmount(cumulateWxTotalAmount);
			stats.setCumulateWxTotalCount(cumulateWxTotalCount);
			// [累计] 支付总数、总额、未支付总数
			stats.setCumulateNoPayCount(cumulateDutyNoPayCount + cumulateAppointmentNoPayCount);
			stats.setCumulateTotalCount(cumulateWxTotalCount + cumulateAliTotalCount);
			stats.setCumulateTotalAmount(cumulateWxTotalAmount + cumulateAliTotalAmount);

			stats.setCumulateDutyWxRefundCount(cumulateDutyWxRefundCount);
			stats.setCumulateDutyAliRefundCount(cumulateDutyAliRefundCount);
			stats.setCumulateAppointmentWxRefundCount(cumulateAppointmentWxRefundCount);
			stats.setCumulateAppointmentAliRefundCount(cumulateAppointmentAliRefundCount);

			stats.setCumulateDutyRefundCount(cumulateDutyRefundCount);
			stats.setCumulateAppointmentRefundCount(cumulateAppointmentRefundCount);
			stats.setCumulateWxRefundCount(cumulateWxRefundCount);
			stats.setCumulateAliRefundCount(cumulateAliRefundCount);

			stats.setCumulateWxRefundAmount(cumulateWxRefundAmount);
			stats.setCumulateAliRefundAmount(cumulateAliRefundAmount);
			stats.setCumulateRefundAmount(cumulateRefundAmount);
			stats.setCumulateRefundCount(cumulateRefundCount);
			stats.setCumulateWxNoPayCount(cumulateWxDutyNoPayCount + cumulateWxAppointmentNoPayCount);
			stats.setCumulateAliNoPayCount(cumulateAliDutyNoPayCount + cumulateAliAppointmentNoPayCount);

			statsMap.put(tmpDate, stats);
			tempDate = DateUtils.getDayForDate(DateUtils.StringToDate(tempDate), 1);
		}

		// logger.info(JSON.toJSONString(statsMap.values()));
		saveStatsInfos(request, statsMap.values());

		return String.format("hospitalCode: [%s], all card stats end...", new Object[] { request.getHospitalCode() });
	}

	private void saveStatsInfos(RegStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:" + request.getHospitalCode() + " AND ");
		sb.append("areaCode:" + request.getAreaCode().replace("/", "\\/"));

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getBeginDate())) {
			sb.append(" AND statsDate:[" + request.getBeginDate() + " TO *]");
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_REGISTER, collection, true, sb.toString());
	}

	/**
	 * 获取医院挂号信息
	 * 
	 * @param hospitalCode
	 * @return
	 */
	private Map<String, RegVo> getRegInfosByHospital(String hospitalCode, String sDate, int platformType) {
		Map<String, RegVo> map = new HashMap<>();
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
				coreName = Cores.CORE_STD_REGISTER;
			} else if (platformType == PlatformConstant.PLATFORM_TYPE_HIS) {
				coreName = Cores.CORE_HIS_REGISTER;
			}
			QueryResponse response = solrClient.query(coreName, solrQuery);

			// 考虑到有分院的情况，一个日期下同一医院可能有多条信息，需要分别对项进行添加
			List<RegVo> list = response.getBeans(RegVo.class);
			for (RegVo vo : list) {
				String statsDate = vo.getStatsDate();
				if (!map.containsKey(statsDate)) {
					map.put(statsDate, vo);
				} else {
					RegVo tempVo = map.get(statsDate);
					tempVo.combineEntity(vo);
					map.put(statsDate, tempVo);
				}
			}

			// 如果他妈比是广中药，狗日的跨两个平台，需要狗日对待
			if (hospitalCode.equals("gxykdxdyfsyy") || hospitalCode.equals("gzsdyrmyy")) {
				QueryResponse tempResponse = solrClient.query(Cores.CORE_STD_REGISTER, solrQuery);

				// 考虑到有分院的情况，一个日期下同一医院可能有多条信息，需要分别对项进行添加
				List<RegVo> tempList = tempResponse.getBeans(RegVo.class);
				for (RegVo vo : tempList) {
					String statsDate = vo.getStatsDate();
					if (!map.containsKey(statsDate)) {
						map.put(statsDate, vo);
					} else {
						RegVo tempVo = map.get(statsDate);
						tempVo.combineEntity(vo);
						map.put(statsDate, tempVo);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取所有医院挂号信息异常。 errorMsg: {}. cause by: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return map;
	}

	@Override
	public String statsInfoForMonth(RegStatsRequest request) {
		String statsDate = request.getBeginDate();
		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		// 获取上上月的统计信息
		Map<String, RegStats> dataMap = getStatsInfoByStatsMonth(request);
		// 获取现在需要的信息
		Map<String, RegVo> voMap = getRegInfosByHospital(request.getHospitalCode(), request.getBeginDate(), request.getPlatform());
		String lastMonth = DateUtils.getFirstDayOfMonth(request.getBeginDate(), -2).substring(0, 7);

		// 上月统计信息
		RegStats lastMonthStats = dataMap.get(lastMonth);

		// 本月统计
		RegStats stats = new RegStats();
		BeanUtils.copyProperties(request, stats);
		stats.setPlatform(lastMonthStats.getPlatform());
		stats.setId(PKGenerator.generateId());
		stats.setThisMonth(DateUtils.getFirstDayOfLastMonth(statsDate).substring(0, 7));
		stats.setLastMonth(lastMonth);

		// 累计设置
		Long cumulateWxTotalAmount = 0L;
		Long cumulateAliTotalAmount = 0L;
		Integer cumulateWxTotalCount = 0;
		Integer cumulateAliTotalCount = 0;
		Integer cumulateDutyWxPayCount = 0;
		Integer cumulateDutyAliPayCount = 0;
		Integer cumulateAppointmentWxPayCount = 0;
		Integer cumulateAppointmentAliPayCount = 0;

		Integer cumulateDutyNoPayCount = 0;
		Integer cumulateAppointmentNoPayCount = 0;
		Integer cumulateWxDutyNoPayCount = 0;
		Integer cumulateAliDutyNoPayCount = 0;
		Integer cumulateWxAppointmentNoPayCount = 0;
		Integer cumulateAliAppointmentNoPayCount = 0;

		Integer cumulateDutyWxRefundCount = 0;
		Integer cumulateDutyAliRefundCount = 0;
		Integer cumulateAppointmentWxRefundCount = 0;
		Integer cumulateAppointmentAliRefundCount = 0;
		Integer cumulateDutyRefundCount = 0;
		Integer cumulateAppointmentRefundCount = 0;
		Integer cumulateWxRefundCount = 0;
		Integer cumulateAliRefundCount = 0;
		Long cumulateWxRefundAmount = 0L;
		Long cumulateAliRefundAmount = 0L;
		Long cumulateRefundAmount = 0L;
		Integer cumulateRefundCount = 0;

		String beginDate = DateUtils.getFirstDayOfLastMonth(statsDate);
		String endDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(DateUtils.getFirstDayOfThisMonth(statsDate)), -1);
		String tempDate = beginDate;

		while (tempDate.compareTo(endDate) <= 0) {
			RegVo vo = voMap.get(tempDate);

			if (vo == null) {
				logger.warn("医院[{}]在[{}]没有数据。", new Object[] { request.getHospitalName().trim(), tempDate.trim() });
			}

			int monthWxDutyCount = 0;
			int monthAliDutyCount = 0;
			int monthWxAppointmentCount = 0;
			int monthAliAppointmentCount = 0;

			int monthWxDutyNoPayCount = 0;
			int monthAliDutyNoPayCount = 0;
			int monthWxAppointmentNoPayCount = 0;
			int monthAliAppointmentNoPayCount = 0;
			// 退费
			int monthWxDutyRefundCount = 0;
			int monthAliDutyRefundCount = 0;
			int monthWxAppointmentRefundCount = 0;
			int monthAliAppointmentRefundCount = 0;

			long monthWxPayAmount = 0L;
			long monthAliPayAmount = 0L;
			long monthWxRefundAmount = 0L;
			long monthAliRefundAmount = 0L;

			if (vo != null) {
				monthWxDutyCount = vo.getDutyPaymentWechat();
				monthAliDutyCount = vo.getDutyPaymentAlipay();
				monthWxAppointmentCount = vo.getReservationPaymentWechat();
				monthAliAppointmentCount = vo.getReservationPaymentAlipay();

				monthWxDutyNoPayCount = vo.getDutyNoPaymentWechat();
				monthAliDutyNoPayCount = vo.getDutyNoPaymentAlipay();
				monthWxAppointmentNoPayCount = vo.getReservationNoPaymentWechat();
				monthAliAppointmentNoPayCount = vo.getReservationNoPaymentAlipay();

				monthWxDutyRefundCount = vo.getDutyRefundWechat();
				monthAliDutyRefundCount = vo.getDutyRefundAlipay();
				monthWxAppointmentRefundCount = vo.getReservationRefundWechat();
				monthAliAppointmentRefundCount = vo.getReservationRefundAlipay();

				// 下面几个，没法用细项。
				monthWxPayAmount = vo.getRegPayFeeWechat();
				monthAliPayAmount = vo.getRegPayFeeAlipay();
				monthWxRefundAmount = vo.getRegRefundFeeWechat();
				monthAliRefundAmount = vo.getRegRefundFeeAlipay();
			}

			cumulateWxTotalAmount += monthWxPayAmount;
			cumulateAliTotalAmount += monthAliPayAmount;

			cumulateWxTotalCount += monthWxDutyCount + monthWxAppointmentCount;
			cumulateAliTotalCount += monthAliDutyCount + monthAliAppointmentCount;

			cumulateDutyNoPayCount += monthWxDutyNoPayCount + monthAliDutyNoPayCount;
			cumulateAppointmentNoPayCount += monthWxAppointmentNoPayCount + monthAliAppointmentNoPayCount;
			cumulateWxDutyNoPayCount += monthWxDutyNoPayCount;
			cumulateAliDutyNoPayCount += monthAliDutyNoPayCount;
			cumulateWxAppointmentNoPayCount += monthWxAppointmentNoPayCount;
			cumulateAliAppointmentNoPayCount += monthAliAppointmentNoPayCount;

			cumulateDutyWxPayCount += monthWxDutyCount;
			cumulateDutyAliPayCount += monthAliDutyCount;
			cumulateAppointmentWxPayCount += monthWxAppointmentCount;
			cumulateAppointmentAliPayCount += monthAliAppointmentCount;

			cumulateDutyWxRefundCount += monthWxDutyRefundCount;
			cumulateDutyAliRefundCount += monthAliDutyRefundCount;
			cumulateAppointmentWxRefundCount += monthWxAppointmentRefundCount;
			cumulateAppointmentAliRefundCount += monthAliAppointmentRefundCount;
			cumulateDutyRefundCount += monthWxDutyRefundCount + monthAliDutyRefundCount;
			cumulateAppointmentRefundCount += monthWxAppointmentRefundCount + monthAliAppointmentRefundCount;
			cumulateWxRefundCount += monthWxDutyRefundCount + monthWxAppointmentRefundCount;
			cumulateAliRefundCount += monthAliDutyRefundCount + monthAliAppointmentRefundCount;

			cumulateWxRefundAmount += monthWxRefundAmount;
			cumulateAliRefundAmount += monthAliRefundAmount;
			cumulateRefundAmount += monthWxRefundAmount + monthAliRefundAmount;
			cumulateRefundCount += monthWxDutyRefundCount + monthAliDutyRefundCount + monthWxAppointmentRefundCount
					+ monthAliAppointmentRefundCount;

			// [当月]支付宝
			stats.setMonthAliTotalAmount(stats.getMonthAliTotalAmount() + monthAliPayAmount);
			stats.setMonthAliTotalCount(stats.getMonthAliTotalCount() + monthAliDutyCount + monthAliAppointmentCount);
			// [当月]微信
			stats.setMonthWxTotalAmount(stats.getMonthWxTotalAmount() + monthWxPayAmount);
			stats.setMonthWxTotalCount(stats.getMonthWxTotalCount() + monthWxDutyCount + monthWxAppointmentCount);
			// [当月]支付宝预约、微信预约、预约、预约未支付笔数
			stats.setMonthAppointmentAliPayCount(stats.getMonthAppointmentAliPayCount() + monthAliAppointmentCount);
			stats.setMonthAppointmentNoPayCount(
					stats.getMonthAppointmentNoPayCount() + monthWxAppointmentNoPayCount + monthAliAppointmentNoPayCount);
			stats.setMonthAppointmentPayCount(stats.getMonthAppointmentPayCount() + monthWxAppointmentCount + monthAliAppointmentCount);
			stats.setMonthAppointmentWxPayCount(stats.getMonthAppointmentWxPayCount() + monthWxAppointmentCount);
			stats.setMonthWxAppointmentNoPayCount(stats.getMonthWxAppointmentNoPayCount() + monthWxAppointmentNoPayCount);
			stats.setMonthAliAppointmentNoPayCount(stats.getMonthAliAppointmentNoPayCount() + monthAliAppointmentNoPayCount);
			// [当月]支付宝当班、微信当班、当班、当班未支付笔数
			stats.setMonthDutyAliPayCount(stats.getMonthDutyAliPayCount() + monthAliDutyCount);
			stats.setMonthDutyNoPayCount(stats.getMonthDutyNoPayCount() + monthWxDutyNoPayCount + monthAliDutyNoPayCount);
			stats.setMonthDutyPayCount(stats.getMonthDutyPayCount() + monthWxDutyCount + monthAliDutyCount);
			stats.setMonthDutyWxPayCount(stats.getMonthDutyWxPayCount() + monthWxDutyCount);
			stats.setMonthWxDutyNoPayCount(stats.getMonthWxDutyNoPayCount() + monthWxDutyNoPayCount);
			stats.setMonthAliDutyNoPayCount(stats.getMonthAliDutyNoPayCount() + monthAliDutyNoPayCount);
			// [当月]未支付笔数、支付总额、支付总数
			stats.setMonthNoPayCount(stats.getMonthDutyNoPayCount() + stats.getMonthAppointmentNoPayCount());
			stats.setMonthTotalAmount(stats.getMonthWxTotalAmount() + stats.getMonthAliTotalAmount());
			stats.setMonthTotalCount(stats.getMonthWxTotalCount() + stats.getMonthAliTotalCount());
			// [当月]退费、微信退费、支付宝退费、微信预约退费、微信当班退费、支付宝预约退费、支付宝当班退费
			stats.setMonthDutyWxRefundCount(stats.getMonthDutyWxRefundCount() + monthWxDutyRefundCount);
			stats.setMonthDutyAliRefundCount(stats.getMonthDutyAliRefundCount() + monthAliDutyRefundCount);
			stats.setMonthAppointmentWxRefundCount(stats.getMonthAppointmentWxRefundCount() + monthWxAppointmentRefundCount);
			stats.setMonthAppointmentAliRefundCount(stats.getMonthAppointmentAliRefundCount() + monthAliAppointmentRefundCount);

			stats.setMonthDutyRefundCount(stats.getMonthDutyWxRefundCount() + stats.getMonthDutyAliRefundCount());
			stats.setMonthAppointmentRefundCount(stats.getMonthAppointmentWxRefundCount() + stats.getMonthAppointmentAliRefundCount());
			stats.setMonthWxRefundCount(stats.getMonthDutyWxRefundCount() + stats.getMonthAppointmentWxRefundCount());
			stats.setMonthAliRefundCount(stats.getMonthDutyWxRefundCount() + stats.getMonthAppointmentAliRefundCount());
			stats.setMonthRefundCount(stats.getMonthWxRefundCount() + stats.getMonthAliRefundCount());

			stats.setMonthWxRefundAmount(stats.getMonthWxRefundAmount() + monthWxRefundAmount);
			stats.setMonthAliRefundAmount(stats.getMonthAliRefundAmount() + monthAliRefundAmount);
			stats.setMonthRefundAmount(stats.getMonthWxRefundAmount() + stats.getMonthAliRefundAmount());
			stats.setMonthWxNoPayCount(stats.getMonthWxDutyNoPayCount() + stats.getMonthWxAppointmentNoPayCount());
			stats.setMonthAliNoPayCount(stats.getMonthAliDutyNoPayCount() + stats.getMonthAliAppointmentNoPayCount());

			tempDate = DateUtils.getDayForDate(DateUtils.StringToDateYMD(tempDate), 1);
		}

		// [累计] 支付宝总数、总额
		stats.setCumulateAliTotalAmount(lastMonthStats.getCumulateAliTotalAmount() + cumulateAliTotalAmount);
		stats.setCumulateAliTotalCount(lastMonthStats.getCumulateAliTotalCount() + cumulateAliTotalCount);
		// [累计] 预约支付、预约微信支付、预约支付宝支付、预约未支付
		stats.setCumulateAppointmentAliPayCount(lastMonthStats.getCumulateAppointmentAliPayCount() + cumulateAppointmentAliPayCount);
		stats.setCumulateAppointmentNoPayCount(lastMonthStats.getCumulateAppointmentNoPayCount() + cumulateAppointmentNoPayCount);
		stats.setCumulateAppointmentPayCount(
				lastMonthStats.getCumulateAppointmentPayCount() + cumulateAppointmentAliPayCount + cumulateAppointmentWxPayCount);
		stats.setCumulateAppointmentWxPayCount(lastMonthStats.getCumulateAppointmentWxPayCount() + cumulateAppointmentWxPayCount);
		stats.setCumulateWxAppointmentNoPayCount(lastMonthStats.getCumulateWxAppointmentNoPayCount() + cumulateWxAppointmentNoPayCount);
		stats.setCumulateAliAppointmentNoPayCount(lastMonthStats.getCumulateAliAppointmentNoPayCount() + cumulateAliAppointmentNoPayCount);
		// [累计] 当班支付、当班微信支付、当班支付宝支付、当班未支付
		stats.setCumulateDutyAliPayCount(lastMonthStats.getCumulateDutyAliPayCount() + cumulateDutyAliPayCount);
		stats.setCumulateDutyNoPayCount(lastMonthStats.getCumulateDutyNoPayCount() + cumulateDutyNoPayCount);
		stats.setCumulateDutyPayCount(lastMonthStats.getCumulateDutyPayCount() + cumulateDutyAliPayCount + cumulateDutyWxPayCount);
		stats.setCumulateDutyWxPayCount(lastMonthStats.getCumulateDutyWxPayCount() + cumulateDutyWxPayCount);
		stats.setCumulateWxDutyNoPayCount(lastMonthStats.getCumulateWxDutyNoPayCount() + cumulateWxDutyNoPayCount);
		stats.setCumulateAliDutyNoPayCount(lastMonthStats.getCumulateAliDutyNoPayCount() + cumulateAliDutyNoPayCount);
		// [累计] 微信总数、总额
		stats.setCumulateWxTotalAmount(lastMonthStats.getCumulateWxTotalAmount() + cumulateWxTotalAmount);
		stats.setCumulateWxTotalCount(lastMonthStats.getCumulateWxTotalCount() + cumulateWxTotalCount);
		// [累计] 支付总数、总额、未支付总数
		stats.setCumulateNoPayCount(lastMonthStats.getCumulateNoPayCount() + cumulateDutyNoPayCount + cumulateAppointmentNoPayCount);
		stats.setCumulateTotalCount(lastMonthStats.getCumulateTotalCount() + cumulateWxTotalCount + cumulateAliTotalCount);
		stats.setCumulateTotalAmount(lastMonthStats.getCumulateTotalAmount() + cumulateWxTotalAmount + cumulateAliTotalAmount);

		stats.setCumulateDutyWxRefundCount(lastMonthStats.getCumulateDutyWxRefundCount() + cumulateDutyWxRefundCount);
		stats.setCumulateDutyAliRefundCount(lastMonthStats.getCumulateDutyAliRefundCount() + cumulateDutyAliRefundCount);
		stats.setCumulateAppointmentWxRefundCount(lastMonthStats.getCumulateAppointmentWxRefundCount() + cumulateAppointmentWxRefundCount);
		stats.setCumulateAppointmentAliRefundCount(
				lastMonthStats.getCumulateAppointmentAliRefundCount() + cumulateAppointmentAliRefundCount);

		stats.setCumulateDutyRefundCount(lastMonthStats.getCumulateDutyRefundCount() + cumulateDutyRefundCount);
		stats.setCumulateAppointmentRefundCount(lastMonthStats.getCumulateAppointmentRefundCount() + cumulateAppointmentRefundCount);
		stats.setCumulateWxRefundCount(lastMonthStats.getCumulateWxRefundCount() + cumulateWxRefundCount);
		stats.setCumulateAliRefundCount(lastMonthStats.getCumulateAliRefundCount() + cumulateAliRefundCount);

		stats.setCumulateWxRefundAmount(lastMonthStats.getCumulateWxRefundAmount() + cumulateWxRefundAmount);
		stats.setCumulateAliRefundAmount(lastMonthStats.getCumulateAliRefundAmount() + cumulateAliRefundAmount);
		stats.setCumulateRefundAmount(lastMonthStats.getCumulateRefundAmount() + cumulateRefundAmount);
		stats.setCumulateRefundCount(lastMonthStats.getCumulateRefundCount() + cumulateRefundCount);
		stats.setCumulateWxNoPayCount(
				lastMonthStats.getCumulateWxNoPayCount() + cumulateWxDutyNoPayCount + cumulateWxAppointmentNoPayCount);
		stats.setCumulateAliNoPayCount(
				lastMonthStats.getCumulateAliNoPayCount() + cumulateAliDutyNoPayCount + cumulateAliAppointmentNoPayCount);

		// 保存
		saveMonthlyStatsInfo(request, stats);

		return String.format("hospitalCode: [%s], statsDate: [%s]. cards stats end...",
				new Object[] { request.getHospitalCode(), request.getBeginDate() });
	}

	private void saveMonthlyStatsInfo(RegStatsRequest request, RegStats stats) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:" + request.getHospitalCode() + " AND ");
		sb.append("thisMonth:" + DateUtils.getFirstDayOfLastMonth(request.getBeginDate()).substring(0, 7) + " AND ");
		sb.append("areaCode:" + request.getAreaCode().replace("/", "\\/"));

		YxwUpdateClient.addBean(Cores.CORE_STATS_REGISTER, stats, true, sb.toString());
	}

	private Map<String, RegStats> getStatsInfoByStatsMonth(RegStatsRequest request) {
		Map<String, RegStats> resultMap = new HashMap<>();

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
			QueryResponse response = solrClient.query(Cores.CORE_STATS_REGISTER, solrQuery);
			List<RegStats> resultList = response.getBeans(RegStats.class);
			if (CollectionUtils.isNotEmpty(resultList)) {
				for (RegStats stats : resultList) {
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
	public String statsAreaInfos(RegStatsRequest request) {
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
				RegStats stats = new RegStats();
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
		solrQuery.addGetFieldStatistics("cumulateAliTotalAmount");
		solrQuery.addGetFieldStatistics("cumulateAliTotalCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentAliPayCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentPayCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentWxPayCount");
		solrQuery.addGetFieldStatistics("cumulateDutyAliPayCount");
		solrQuery.addGetFieldStatistics("cumulateDutyPayCount");
		solrQuery.addGetFieldStatistics("cumulateDutyWxPayCount");
		solrQuery.addGetFieldStatistics("cumulateTotalAmount");
		solrQuery.addGetFieldStatistics("cumulateTotalCount");
		solrQuery.addGetFieldStatistics("cumulateWxTotalAmount");
		solrQuery.addGetFieldStatistics("cumulateWxTotalCount");
		solrQuery.addGetFieldStatistics("monthAliRefundAmount");
		solrQuery.addGetFieldStatistics("monthAliRefundCount");
		solrQuery.addGetFieldStatistics("monthAliTotalAmount");
		solrQuery.addGetFieldStatistics("monthAliTotalCount");
		solrQuery.addGetFieldStatistics("monthAppointmentAliPayCount");
		solrQuery.addGetFieldStatistics("monthAppointmentAliRefundCount");
		solrQuery.addGetFieldStatistics("monthAppointmentPayCount");
		solrQuery.addGetFieldStatistics("monthAppointmentRefundCount");
		solrQuery.addGetFieldStatistics("monthAppointmentWxPayCount");
		solrQuery.addGetFieldStatistics("monthAppointmentWxRefundCount");
		solrQuery.addGetFieldStatistics("monthDutyAliPayCount");
		solrQuery.addGetFieldStatistics("monthDutyAliRefundCount");
		solrQuery.addGetFieldStatistics("monthDutyPayCount");
		solrQuery.addGetFieldStatistics("monthDutyRefundCount");
		solrQuery.addGetFieldStatistics("monthDutyWxPayCount");
		solrQuery.addGetFieldStatistics("monthDutyWxRefundCount");
		solrQuery.addGetFieldStatistics("monthRefundAmount");
		solrQuery.addGetFieldStatistics("monthRefundCount");
		solrQuery.addGetFieldStatistics("monthTotalAmount");
		solrQuery.addGetFieldStatistics("monthTotalCount");
		solrQuery.addGetFieldStatistics("monthWxRefundAmount");
		solrQuery.addGetFieldStatistics("monthWxRefundCount");
		solrQuery.addGetFieldStatistics("monthWxTotalAmount");
		solrQuery.addGetFieldStatistics("monthWxTotalCount");

		solrQuery.addGetFieldStatistics("cumulateDutyWxRefundCount");
		solrQuery.addGetFieldStatistics("cumulateDutyAliRefundCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentWxRefundCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentAliRefundCount");
		solrQuery.addGetFieldStatistics("cumulateDutyRefundCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentRefundCount");
		solrQuery.addGetFieldStatistics("cumulateWxRefundCount");
		solrQuery.addGetFieldStatistics("cumulateAliRefundCount");
		solrQuery.addGetFieldStatistics("cumulateWxRefundAmount");
		solrQuery.addGetFieldStatistics("cumulateAliRefundAmount");
		solrQuery.addGetFieldStatistics("cumulateRefundAmount");
		solrQuery.addGetFieldStatistics("cumulateRefundCount");

		solrQuery.addGetFieldStatistics("monthWxDutyNoPayCount");
		solrQuery.addGetFieldStatistics("monthAliDutyNoPayCount");
		solrQuery.addGetFieldStatistics("monthWxAppointmentNoPayCount");
		solrQuery.addGetFieldStatistics("monthAliAppointmentNoPayCount");
		solrQuery.addGetFieldStatistics("monthWxNoPayCount");
		solrQuery.addGetFieldStatistics("monthAliNoPayCount");
		solrQuery.addGetFieldStatistics("monthAppointmentNoPayCount");
		solrQuery.addGetFieldStatistics("monthDutyNoPayCount");
		solrQuery.addGetFieldStatistics("monthNoPayCount");

		solrQuery.addGetFieldStatistics("cumulateNoPayCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentNoPayCount");
		solrQuery.addGetFieldStatistics("cumulateWxDutyNoPayCount");
		solrQuery.addGetFieldStatistics("cumulateAliDutyNoPayCount");
		solrQuery.addGetFieldStatistics("cumulateWxAppointmentNoPayCount");
		solrQuery.addGetFieldStatistics("cumulateAliAppointmentNoPayCount");
		solrQuery.addGetFieldStatistics("cumulateWxNoPayCount");
		solrQuery.addGetFieldStatistics("cumulateAliNoPayCount");
		solrQuery.addGetFieldStatistics("cumulateDutyNoPayCount");

		solrQuery.addStatsFieldFacets(null, "thisMonth");

		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_REGISTER, solrQuery);
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

	private void saveAreaStatsInfos(RegStatsRequest request, Collection<Object> collection) {
		StringBuffer sb = new StringBuffer();
		sb.append("hospitalCode:\\- AND areaCode:" + request.getAreaCode().replace("/", "\\/"));

		// 不能删除全部，只删除需要的部分
		if (StringUtils.isNotBlank(request.getStatsMonth())) {
			sb.append(" AND thisMonth:[" + request.getStatsMonth() + " TO *]");
		}

		YxwUpdateClient.addBeans(Cores.CORE_STATS_REGISTER, collection, true, sb.toString());
	}

	@Override
	public String statsAreaInfoForMonth(RegStatsRequest request) {
		// 百分比格式化。去两位小数点
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);

		String lastMonth = DateUtils.getFirstDayOfMonth(request.getBeginDate(), -1).substring(0, 7);
		Map<String, Map<String, Double>> dataMap = getAreaStatsByAreaCode(request.getAreaCode(), lastMonth);

		RegStats stats = new RegStats();
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

		return String.format("area register stats end...");
	}

}
