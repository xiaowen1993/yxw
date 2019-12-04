package com.yxw.stats.task.platform.callable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.entity.platform.MedicalCardCount;
import com.yxw.stats.service.platform.MedicalCardCountService;

public class StatisticsMedicalCardCollectCall implements Callable<List<MedicalCardCount>> {
	public static Logger logger = LoggerFactory.getLogger(StatisticsMedicalCardCollectCall.class);
	private MedicalCardCountService medicalCardCountService = SpringContextHolder.getBean(MedicalCardCountService.class);

	private Map<String, Object> params;

	public StatisticsMedicalCardCollectCall(Map<String, Object> params) {
		super();
		this.params = params;
	}

	@Override
	public List<MedicalCardCount> call() throws Exception {
		List<MedicalCardCount> result = medicalCardCountService.findMedicalCardCountByDate(params);
		return result;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
