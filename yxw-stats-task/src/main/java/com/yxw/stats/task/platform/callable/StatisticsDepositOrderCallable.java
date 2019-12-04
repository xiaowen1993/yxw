package com.yxw.stats.task.platform.callable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.entity.platform.DepositOrderCount;
import com.yxw.stats.service.platform.DepositOrderCountService;

public class StatisticsDepositOrderCallable implements Callable<List<DepositOrderCount>> {
	public static Logger logger = LoggerFactory.getLogger(StatisticsDepositOrderCallable.class);
	private DepositOrderCountService depositOrderCountService = SpringContextHolder.getBean(DepositOrderCountService.class);

	private Map<String, Object> params;

	public StatisticsDepositOrderCallable(Map<String, Object> params) {
		super();
		this.params = params;
	}

	@Override
	public List<DepositOrderCount> call() throws Exception {
		List<DepositOrderCount> result = depositOrderCountService.findDepositOrderCountByDate(params);
		return result;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
