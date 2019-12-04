package com.yxw.stats.task.platform.callable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.entity.platform.ClinicOrderCount;
import com.yxw.stats.service.platform.ClinicOrderCountService;

public class StatisticsClinicOrderCallable implements Callable<List<ClinicOrderCount>> {
	public static Logger logger = LoggerFactory.getLogger(StatisticsClinicOrderCallable.class);
	private ClinicOrderCountService clinicOrderCountService = SpringContextHolder.getBean(ClinicOrderCountService.class);

	private Map<String, Object> params;

	public StatisticsClinicOrderCallable(Map<String, Object> params) {
		super();
		this.params = params;
	}

	@Override
	public List<ClinicOrderCount> call() throws Exception {
		List<ClinicOrderCount> result = clinicOrderCountService.findClinicOrderCountByDate(params);
		return result;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
