package com.yxw.stats.service.platform;

import java.util.List;
import java.util.Map;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.stats.entity.platform.OrderCount;

public interface OrderCountService extends BaseService<OrderCount, String> {

	/**
	 * 当天挂号订单统计
	 * @param map
	 * @return
	 */
	public abstract List<OrderCount> findRegOrderCountByDate(Map map);
}
