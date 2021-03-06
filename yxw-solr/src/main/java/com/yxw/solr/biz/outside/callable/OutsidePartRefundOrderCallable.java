package com.yxw.solr.biz.outside.callable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.commons.dto.outside.OrdersQueryResult;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.client.YxwSolrClient;
import com.yxw.utils.DateUtils;

/**
 * 挂号原订单查询（包括支付订单和退费订单）
 * 
 * @author Administrator
 *
 */
public class OutsidePartRefundOrderCallable implements Callable<List<OrdersQueryResult>> {

	private Logger logger = LoggerFactory.getLogger(OutsidePartRefundOrderCallable.class);

	/**
	 * 查询参数
	 */
	private SolrQuery solrQuery;

	/**
	 * 查询的core
	 */
	private String coreName;

	/**
	 * 交易类型 1：支付 2：退费 , 这里不需要tradeType，必须是退费的2.
	 */
	// private Integer tradeType;

	/**
	 * 1：挂号 2：门诊 3：住院押金
	 */
	private Integer orderMode;

	public OutsidePartRefundOrderCallable() {
		super();
	}

	public OutsidePartRefundOrderCallable(SolrQuery solrQuery, String coreName, Integer orderMode) {
		super();
		this.coreName = coreName;
		this.solrQuery = solrQuery;
		this.orderMode = orderMode;
	}

	@Override
	public List<OrdersQueryResult> call() throws Exception {
		YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);
		List<OrdersQueryResult> results = null;

		try {
			QueryResponse response = solrClient.query(coreName, solrQuery);
			SolrDocumentList docs = response.getResults();
			switch (this.orderMode) {
			case 1:
				// 挂号部分退费订单
				// do nothing
				logger.warn("挂号没有部分退费的！！！！");
				break;
			case 2:
				// 门诊部分退费订单
				logger.info("===============>>  获取门诊部分退费订单");
				results = convertClinicRefundObjects(docs);
				break;
			case 3:
				// 押金部分退费订单
				logger.info("===============>>  获取押金部分退费订单");
				results = convertDepositRefundObjects(docs);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("findPartRefundOrder error. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return results;
	}

	private List<OrdersQueryResult> convertClinicRefundObjects(SolrDocumentList docs) {
		List<OrdersQueryResult> results = new ArrayList<>();

		for (SolrDocument doc : docs) {
			OrdersQueryResult result = new OrdersQueryResult();

			// 转换
			result.setAgtPayOrdNum((String) doc.get("agtOrdNum"));
			result.setAgtRefundOrdNum((String) doc.get("agtRefundOrdNum"));
			result.setBranchCode((String) doc.get("branchCode"));
			// result.setBranchName(branchName); // 不存这个了，很多单里面都没有，文档中也没有指出。
			result.setHisPayOrdNum((String) doc.get("hisOrdNo"));
			result.setHisRefundOrdNum((String) doc.get("refundHisOrdNo"));
			result.setOrderMode("2"); // 写死算了，这里是挂号的
			result.setPayTotalFee( ( (Integer) doc.get("totalFee") ) + "");
			result.setPsPayOrdNum((String) doc.get("orderNo"));
			result.setPsRefundOrdNum((String) doc.get("refundOrderNo"));
			result.setRefundTotalFee(result.getPayTotalFee());
			result.setTradeMode( ( (Integer) doc.get("platform") ));
			result.setTradeTime(DateUtils.formatDate(new Date((long) doc.get("refundTime"))));
			result.setTradeType("2");

			results.add(result);
		}

		return results;
	}

	private List<OrdersQueryResult> convertDepositRefundObjects(SolrDocumentList docs) {
		List<OrdersQueryResult> results = new ArrayList<>();

		for (SolrDocument doc : docs) {
			OrdersQueryResult result = new OrdersQueryResult();

			// 转换
			result.setAgtPayOrdNum((String) doc.get("agtOrdNum"));
			result.setAgtRefundOrdNum((String) doc.get("agtRefundOrdNum"));
			result.setBranchCode((String) doc.get("branchCode"));
			// result.setBranchName(branchName); // 不存这个了，很多单里面都没有，文档中也没有指出。
			result.setHisPayOrdNum((String) doc.get("hisOrdNo"));
			result.setHisRefundOrdNum((String) doc.get("refundHisOrdNo"));
			result.setOrderMode("3"); // 写死算了，这里是挂号的
			result.setPayTotalFee( ( (Integer) doc.get("totalFee") ) + "");
			result.setPsPayOrdNum((String) doc.get("orderNo"));
			result.setPsRefundOrdNum((String) doc.get("refundOrderNo"));
			result.setRefundTotalFee(result.getPayTotalFee());
			result.setTradeMode( ( (Integer) doc.get("platform") ));
			result.setTradeTime(DateUtils.formatDate(new Date((long) doc.get("refundTime"))));
			result.setTradeType("2");

			results.add(result);
		}

		return results;
	}

	public SolrQuery getSolrQuery() {
		return solrQuery;
	}

	public void setSolrQuery(SolrQuery solrQuery) {
		this.solrQuery = solrQuery;
	}

	public String getCoreName() {
		return coreName;
	}

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	public Integer getOrderMode() {
		return orderMode;
	}

	public void setOrderMode(Integer orderMode) {
		this.orderMode = orderMode;
	}

}
