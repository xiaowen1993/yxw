package com.yxw.statistics.biz.stats.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.outside.service.YxwClinicService;
import com.yxw.solr.vo.clinic.ClinicStatsRequest;
import com.yxw.statistics.biz.stats.service.ClinicStatsService;
import com.yxw.statistics.biz.stats.service.OrderStatsService;
import com.yxw.statistics.biz.vo.StatsVo;

@Service(value="clinicStatsService")
public class ClinicStatsServiceImpl implements ClinicStatsService {
	
	private Logger logger = LoggerFactory.getLogger(OrderStatsService.class);
	
	private YxwClinicService yxwService = SpringContextHolder.getBean(YxwClinicService.class);

	@Override
	public String getStatsInfo(StatsVo statsVo) {
		String result = null;
		ClinicStatsRequest request = new ClinicStatsRequest();
		BeanUtils.copyProperties(statsVo, request);
		try {
			result = yxwService.getStatsInfos(request);
		} catch (Exception e) {
			logger.error("getStatsInfo error. errorMsg: {}. cause: {}.", new Object[]{e.getMessage(), e.getCause()});
		}
		
		return result;
	}

}
