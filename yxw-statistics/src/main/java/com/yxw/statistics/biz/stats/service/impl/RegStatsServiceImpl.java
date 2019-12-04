package com.yxw.statistics.biz.stats.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.outside.service.YxwRegisterService;
import com.yxw.solr.vo.register.RegStatsRequest;
import com.yxw.statistics.biz.stats.service.OrderStatsService;
import com.yxw.statistics.biz.stats.service.RegStatsService;
import com.yxw.statistics.biz.vo.StatsVo;

@Service(value="regStatsService")
public class RegStatsServiceImpl implements RegStatsService {
	
	private Logger logger = LoggerFactory.getLogger(OrderStatsService.class);
	
	private YxwRegisterService yxwService = SpringContextHolder.getBean(YxwRegisterService.class);

	@Override
	public String getStatsInfo(StatsVo statsVo) {
		String result = null;
		RegStatsRequest request = new RegStatsRequest();
		BeanUtils.copyProperties(statsVo, request);
		try {
			result = yxwService.getStatsInfos(request);
		} catch (Exception e) {
			logger.error("getStatsInfo error. errorMsg: {}. cause: {}.", new Object[]{e.getMessage(), e.getCause()});
		}
		
		return result;
	}

}
