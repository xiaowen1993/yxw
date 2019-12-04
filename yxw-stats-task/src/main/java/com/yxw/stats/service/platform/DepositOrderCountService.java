package com.yxw.stats.service.platform;

import java.util.List;
import java.util.Map;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.stats.entity.platform.DepositOrderCount;

public interface DepositOrderCountService extends BaseService<DepositOrderCount, String> {

	/**
	 * 当天门诊订单统计
	 * 
	 * @param map
	 * @return
	 */
	public abstract List<DepositOrderCount> findDepositOrderCountByDate(Map map);

}
