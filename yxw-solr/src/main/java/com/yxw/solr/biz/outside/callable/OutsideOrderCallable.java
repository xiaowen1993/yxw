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
public class OutsideOrderCallable implements Callable<List<OrdersQueryResult>> {

	private Logger logger = LoggerFactory.getLogger(OutsideOrderCallable.class);

	/**
	 * 查询参数
	 */
	private SolrQuery solrQuery;

	/**
	 * 查询的core
	 */
	private String coreName;

	/**
	 * 交易类型 1：支付 2：退费
	 */
	private Integer tradeType;

	/**
	 * 1：挂号 2：门诊 3：住院押金
	 */
	private Integer orderMode;

	public OutsideOrderCallable() {
		super();
	}

	public OutsideOrderCallable(SolrQuery solrQuery, String coreName, Integer tradeType, Integer orderMode) {
		super();
		this.coreName = coreName;
		this.solrQuery = solrQuery;
		this.tradeType = tradeType;
		this.orderMode = orderMode;
	}

	@Override
	public List<OrdersQueryResult> call() throws Exception {
		YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);
		List<OrdersQueryResult> results = null;

		try {
			QueryResponse response = solrClient.query(coreName, solrQuery);
			SolrDocumentList docs = response.getResults();
			if (tradeType.intValue() == 1) { // 支付的
				switch (this.orderMode) {
				case 1:
					// 挂号支付订单
					logger.info("===============>>  获取挂号订单");
					results = convertRegPayObjects(docs);
					break;
				case 2:
					// 门诊支付订单
					logger.info("===============>>  获取门诊订单");
					results = convertClinicPayObjects(docs);
					break;
				case 3:
					// 押金支付订单
					logger.info("===============>>  获取住院订单");
					results = convertDepositPayObjects(docs);
					break;
				default:
					break;
				}
			} else { // 退费的
				switch (this.orderMode) {
				case 1:
					// 挂号退费订单（全额）
					logger.info("===============>>  获取全额挂号退费订单");
					results = convertRegRefundObjects(docs);
					break;
				case 2:
					// 门诊退费订单（全额）
					logger.info("===============>>  获取全额门诊退费订单");
					results = convertClinicRefundObjects(docs);
					break;
				case 3:
					// 押金退费订单（全额）
					logger.info("===============>>  获取全额住院退费订单");
					results = convertDepositRefundObjects(docs);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			logger.error("findOrders error. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return results;
	}

	private List<OrdersQueryResult> convertRegPayObjects(SolrDocumentList docs) {
		List<OrdersQueryResult> results = new ArrayList<>();

		for (SolrDocument doc : docs) {
			OrdersQueryResult result = new OrdersQueryResult();

			// 转换
			result.setAgtPayOrdNum((String) doc.get("agtOrdNum"));
			// result.setAgtRefundOrdNum("");
			result.setBranchCode((String) doc.get("branchCode"));
			// result.setBranchName(branchName); // 不存这个了，很多单里面都没有，文档中也没有指出。
			result.setHisPayOrdNum((String) doc.get("hisOrdNo"));
			// result.setHisRefundOrdNum("");
			result.setOrderMode("1"); // 写死算了，这里是挂号的
			result.setPayTotalFee( ( (Integer) doc.get("totalFee") ) + "");
			result.setPsPayOrdNum((String) doc.get("orderNo"));
			// result.setPsRefundOrdNum("");
			// result.setRefundTotalFee("");
			result.setTradeMode( ( (Integer) doc.get("platform") ));
			result.setTradeTime(DateUtils.formatDate(new Date((long) doc.get("payTime"))));
			result.setTradeType(tradeType + "");

			results.add(result);
		}

		return results;
	}

	private List<OrdersQueryResult> convertClinicPayObjects(SolrDocumentList docs) {
		List<OrdersQueryResult> results = new ArrayList<>();

		for (SolrDocument doc : docs) {
			OrdersQueryResult result = new OrdersQueryResult();

			// 转换
			result.setAgtPayOrdNum((String) doc.get("agtOrdNum"));
			// result.setAgtRefundOrdNum("");
			result.setBranchCode((String) doc.get("branchCode"));
			// result.setBranchName(branchName); // 不存这个了，很多单里面都没有，文档中也没有指出。
			result.setHisPayOrdNum((String) doc.get("hisOrdNo"));
			// result.setHisRefundOrdNum("");
			result.setOrderMode("2");
			result.setPayTotalFee( ( (Long) doc.get("payFee") ) + "");
			result.setPsPayOrdNum((String) doc.get("orderNo"));
			// result.setPsRefundOrdNum("");
			// result.setRefundTotalFee("");
			result.setTradeMode( ( (Integer) doc.get("platform") ));
			result.setTradeTime(DateUtils.formatDate(new Date((long) doc.get("payTime"))));
			result.setTradeType(tradeType + "");

			results.add(result);
		}

		return results;
	}

	private List<OrdersQueryResult> convertDepositPayObjects(SolrDocumentList docs) {
		List<OrdersQueryResult> results = new ArrayList<>();

		for (SolrDocument doc : docs) {
			OrdersQueryResult result = new OrdersQueryResult();

			// 转换
			result.setAgtPayOrdNum((String) doc.get("agtOrdNum"));
			// result.setAgtRefundOrdNum("");
			result.setBranchCode((String) doc.get("branchCode"));
			// result.setBranchName(branchName); // 不存这个了，很多单里面都没有，文档中也没有指出。
			result.setHisPayOrdNum((String) doc.get("hisOrdNo"));
			// result.setHisRefundOrdNum("");
			result.setOrderMode("3");
			result.setPayTotalFee( ( (Long) doc.get("payFee") ) + "");
			result.setPsPayOrdNum((String) doc.get("orderNo"));
			// result.setPsRefundOrdNum("");
			// result.setRefundTotalFee("");
			result.setTradeMode( ( (Integer) doc.get("platform") ));
			result.setTradeTime(DateUtils.formatDate(new Date((long) doc.get("payTime"))));
			result.setTradeType(tradeType + "");

			results.add(result);
		}

		return results;
	}

	private List<OrdersQueryResult> convertRegRefundObjects(SolrDocumentList docs) {
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
			result.setOrderMode("1"); // 写死算了，这里是挂号的
			result.setPayTotalFee( ( (Integer) doc.get("totalFee") ) + "");
			result.setPsPayOrdNum((String) doc.get("orderNo"));
			result.setPsRefundOrdNum((String) doc.get("refundOrderNo"));
			result.setRefundTotalFee(result.getPayTotalFee());
			result.setTradeMode( ( (Integer) doc.get("platform") ));
			result.setTradeTime(DateUtils.formatDate(new Date((long) doc.get("refundTime"))));
			result.setTradeType(tradeType + "");

			results.add(result);
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
			result.setPayTotalFee( ( (Long) doc.get("payFee") ) + "");
			result.setPsPayOrdNum((String) doc.get("orderNo"));
			result.setPsRefundOrdNum((String) doc.get("refundOrderNo"));
			result.setRefundTotalFee(result.getPayTotalFee());
			result.setTradeMode( ( (Integer) doc.get("platform") ));
			result.setTradeTime(DateUtils.formatDate(new Date((long) doc.get("refundTime"))));
			result.setTradeType(tradeType + "");

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
			result.setPayTotalFee( ( (Long) doc.get("payFee") ) + "");
			result.setPsPayOrdNum((String) doc.get("orderNo"));
			result.setPsRefundOrdNum((String) doc.get("refundOrderNo"));
			result.setRefundTotalFee(result.getPayTotalFee());
			result.setTradeMode( ( (Integer) doc.get("platform") ));
			result.setTradeTime(DateUtils.formatDate(new Date((long) doc.get("refundTime"))));
			result.setTradeType(tradeType + "");

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

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getOrderMode() {
		return orderMode;
	}

	public void setOrderMode(Integer orderMode) {
		this.orderMode = orderMode;
	}

}
