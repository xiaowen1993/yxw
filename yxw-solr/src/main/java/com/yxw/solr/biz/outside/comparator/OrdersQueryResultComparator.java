package com.yxw.solr.biz.outside.comparator;

import java.util.Comparator;

import com.yxw.commons.dto.outside.OrdersQueryResult;

public class OrdersQueryResultComparator implements Comparator<OrdersQueryResult> {

	@Override
	public int compare(OrdersQueryResult o1, OrdersQueryResult o2) {
		return o1.getTradeTime().compareToIgnoreCase(o2.getTradeTime());
	}

}
