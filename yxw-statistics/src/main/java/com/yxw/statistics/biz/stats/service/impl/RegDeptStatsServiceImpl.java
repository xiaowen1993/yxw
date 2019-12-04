package com.yxw.statistics.biz.stats.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.outside.service.YxwRegDeptService;
import com.yxw.solr.vo.register.RegDeptStatsRequest;
import com.yxw.statistics.biz.stats.service.OrderStatsService;
import com.yxw.statistics.biz.stats.service.RegDeptStatsService;
import com.yxw.statistics.biz.vo.StatsVo;

@Service(value="regDeptStatsService")
public class RegDeptStatsServiceImpl implements RegDeptStatsService {
	
	private Logger logger = LoggerFactory.getLogger(OrderStatsService.class);
	
	private YxwRegDeptService yxwService = SpringContextHolder.getBean(YxwRegDeptService.class);

	@Override
	public String getStatsInfo(StatsVo statsVo) {
		String result = null;
		RegDeptStatsRequest request = new RegDeptStatsRequest();
		BeanUtils.copyProperties(statsVo, request);
		try {
			result = yxwService.getStatsInfos(request);
		} catch (Exception e) {
			logger.error("getStatsInfo error. errorMsg: {}. cause: {}.", new Object[]{e.getMessage(), e.getCause()});
		}
		
		return result;
	}

}
