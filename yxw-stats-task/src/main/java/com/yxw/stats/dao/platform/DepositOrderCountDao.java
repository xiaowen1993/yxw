package com.yxw.stats.dao.platform;

import java.util.List;
import java.util.Map;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.stats.entity.platform.DepositOrderCount;

public interface DepositOrderCountDao extends BaseDao<DepositOrderCount, String> {

	/**
	 * 当天门诊订单统计
	 * 
	 * @param map
	 * @return
	 */
	public abstract List<DepositOrderCount> findDepositOrderCountByDate(Map map);

}
