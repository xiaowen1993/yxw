package com.yxw.solr.biz.common.callable;

import java.util.concurrent.Callable;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.client.YxwSolrClient;
import com.yxw.solr.vo.CoreVo;
import com.yxw.solr.vo.order.OrderInfoResponse;

public class OrdersQueryCallable implements Callable<OrderInfoResponse> {

	private Logger logger = LoggerFactory.getLogger(OrdersQueryCallable.class);

	private CoreVo vo;
	
	private SolrQuery solrQuery;
	
	public OrdersQueryCallable(CoreVo vo, SolrQuery solrQuery) {
		this.vo = vo;
		this.solrQuery = solrQuery;
	}

	@Override
	public OrderInfoResponse call() throws Exception {
		YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);
		OrderInfoResponse resultResp = new OrderInfoResponse();

		try {
			QueryResponse response = solrClient.query(vo.getCoreName(), solrQuery);
			SolrDocumentList solrDocumentList = response.getResults();
			resultResp.setSize(((Long) solrDocumentList.getNumFound()).intValue());
			resultResp.setBeans(solrDocumentList);
			resultResp.setPlatform(vo.getPlatform());
			resultResp.setBizType(vo.getBizType());
		} catch (Exception e) {
			logger.error("findOrders error. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return resultResp;
	}

	public SolrQuery getSolrQuery() {
		return solrQuery;
	}

	public void setSolrQuery(SolrQuery solrQuery) {
		this.solrQuery = solrQuery;
	}

	public CoreVo getVo() {
		return vo;
	}

	public void setVo(CoreVo vo) {
		this.vo = vo;
	}

}
