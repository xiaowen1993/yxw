package com.yxw.stats.dao.platform;

import java.util.List;
import java.util.Map;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.stats.entity.platform.OrderCount;

public interface OrderCountDao extends BaseDao<OrderCount, String> {

	/**
	 * 当天挂号订单统计
	 * @param map
	 * @return
	 */
	public abstract List<OrderCount> findRegOrderCountByDate(Map map);

}
