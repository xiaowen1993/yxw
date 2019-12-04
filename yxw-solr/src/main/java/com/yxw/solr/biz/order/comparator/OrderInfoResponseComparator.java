package com.yxw.solr.biz.order.comparator;

import java.util.Comparator;

import com.yxw.solr.vo.order.OrderInfoResponse;

public class OrderInfoResponseComparator implements Comparator<OrderInfoResponse> {

	@Override
	public int compare(OrderInfoResponse o1, OrderInfoResponse o2) {
		int result = 0;
		
		if (o1.getPlatform() == o2.getPlatform()) {
			if (o1.getBizType() >= 0 && o2.getBizType() >= 0) {
				result = o1.getBizType() - o2.getBizType();
			} else {
				result = o2.getBizType() - o1.getBizType();
			}
		} else {
			result = o1.getPlatform() - o2.getPlatform();
		}
		return result;
	}

}
