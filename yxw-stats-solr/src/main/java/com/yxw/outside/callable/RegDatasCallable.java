package com.yxw.outside.callable;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.client.YxwSolrClient;
import com.yxw.constants.Cores;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

public class RegDatasCallable implements Callable<Map<String, Object>> {

	private Logger logger = LoggerFactory.getLogger(RegDatasCallable.class);

	private String areaCode;

	private String hospitalCode;

	private String beginMonth;

	private String endMonth;

	public RegDatasCallable(String areaCode, String hospitalCode, String beginMonth, String endMonth) {
		this.areaCode = areaCode;
		this.hospitalCode = hospitalCode;
		this.beginMonth = beginMonth;
		this.endMonth = endMonth;
	}

	/**
	 * beginMonth-endMonth 都是对应thisMonth areaCode:-1
	 * 按区域进行统计。返回区域、区域的微信绑卡数、支付宝的绑卡数 araeCode:0
	 * 按所有区域进行统计，返回所有区域内的各医院的微信绑卡数、支付宝绑卡数 areaCode:XX 按区域，返回该区域内所有医院信息
	 * hospitalCode:XX 返回特定医院信息
	 */

	@Override
	public Map<String, Object> call() throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);
		SolrQuery solrQuery = new SolrQuery();

		StringBuffer sbQuery = new StringBuffer();
		if (areaCode.equals("-1")) {
			sbQuery.append("-hospitalCode:\\- AND ");
			solrQuery.setSort("areaName", ORDER.asc);
		} else if (areaCode.equals("0")) {
			sbQuery.append("-hospitalCode:\\- AND ");
			solrQuery.setSort("areaName", ORDER.asc);
		} else {
			if (hospitalCode.equals("-1")) {
				sbQuery.append("-hospitalCode:\\- AND areaCode:" + areaCode.replace("/", "\\/") + " AND ");
				solrQuery.setSort("hospitalName", ORDER.asc);
			} else {
				sbQuery.append("hospitalCode:" + hospitalCode + " AND ");
				solrQuery.setSort("hospitalName", ORDER.asc);
			}
		}

		// 日期
		sbQuery.append("thisMonth:[" + beginMonth + " TO " + endMonth + "]");

		solrQuery.setQuery(sbQuery.toString());
		solrQuery.setRows(Integer.MAX_VALUE);
		solrQuery.addSort("thisMonth", ORDER.asc);

		solrQuery.setGetFieldStatistics(true);
		solrQuery.addGetFieldStatistics("cumulateAliRefundAmount");
		solrQuery.addGetFieldStatistics("cumulateAliRefundCount");
		solrQuery.addGetFieldStatistics("cumulateAliTotalAmount");
		solrQuery.addGetFieldStatistics("cumulateAliTotalCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentAliPayCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentAliRefundCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentPayCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentRefundCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentWxPayCount");
		solrQuery.addGetFieldStatistics("cumulateAppointmentWxRefundCount");
		solrQuery.addGetFieldStatistics("cumulateDutyAliPayCount");
		solrQuery.addGetFieldStatistics("cumulateDutyAliRefundCount");
		solrQuery.addGetFieldStatistics("cumulateDutyPayCount");
		solrQuery.addGetFieldStatistics("cumulateDutyRefundCount");
		solrQuery.addGetFieldStatistics("cumulateDutyWxPayCount");
		solrQuery.addGetFieldStatistics("cumulateDutyWxRefundCount");
		solrQuery.addGetFieldStatistics("cumulateRefundAmount");
		solrQuery.addGetFieldStatistics("cumulateRefundCount");
		solrQuery.addGetFieldStatistics("cumulateTotalAmount");
		solrQuery.addGetFieldStatistics("cumulateTotalCount");
		solrQuery.addGetFieldStatistics("cumulateWxRefundAmount");
		solrQuery.addGetFieldStatistics("cumulateWxRefundCount");
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
		
		// noPay
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

		Map<String, Map<String, Object>> statsMap = new HashMap<>();
		try {
			QueryResponse response = solrClient.query(Cores.CORE_STATS_REGISTER, solrQuery);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>>>> responseMap = response
					.getResponse().asMap(100);
			Map<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> statsFields = responseMap.get("stats")
					.get("stats_fields");
			for (Entry<String, Map<String, Map<String, Map<String, Map<String, Object>>>>> entry1 : statsFields.entrySet()) {
				String statsField = entry1.getKey();
				// Map<String, Map<String, Object>> facetField =
				// entry1.getValue().get("facets").get("areaName");
				Map<String, Map<String, Object>> facetField = entry1.getValue().get("facets").get("thisMonth");

				for (Entry<String, Map<String, Object>> entry2 : facetField.entrySet()) {
					Double value = (Double) entry2.getValue().get("sum");
					// statsMap.put(entry2.getKey(), value.intValue());
					Map<String, Object> map = null;
					if (statsMap.containsKey(entry2.getKey())) {
						map = statsMap.get(entry2.getKey());
					} else {
						map = new HashMap<>();
					}

					map.put(statsField, (Long)Math.round(value) + "");
					statsMap.put(entry2.getKey(), map);
				}
			}
			
			resultMap.put(Cores.CORE_STATS_REGISTER, statsMap);
		} catch (Exception e) {
			logger.error("获取挂号统计数据异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultMap;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBeginMonth() {
		return beginMonth;
	}

	public void setBeginMonth(String beginMonth) {
		this.beginMonth = beginMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

}
