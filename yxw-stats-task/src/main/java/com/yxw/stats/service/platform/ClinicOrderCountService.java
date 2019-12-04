package com.yxw.stats.service.platform;

import java.util.List;
import java.util.Map;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.stats.entity.platform.ClinicOrderCount;

public interface ClinicOrderCountService extends BaseService<ClinicOrderCount, String> {

	/**
	 * 当天门诊订单统计
	 * @param map
	 * @return
	 */
	public abstract List<ClinicOrderCount> findClinicOrderCountByDate(Map map);
}
