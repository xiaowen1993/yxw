package com.yxw.statistics.biz.stats.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.outside.service.YxwDepositService;
import com.yxw.solr.vo.deposit.DepositStatsRequest;
import com.yxw.statistics.biz.stats.service.DepositStatsService;
import com.yxw.statistics.biz.stats.service.OrderStatsService;
import com.yxw.statistics.biz.vo.StatsVo;

@Service(value="depositStatsService")
public class DepositStatsServiceImpl implements DepositStatsService {
	
	private Logger logger = LoggerFactory.getLogger(OrderStatsService.class);
	
	private YxwDepositService yxwService = SpringContextHolder.getBean(YxwDepositService.class);

	@Override
	public String getStatsInfo(StatsVo statsVo) {
		String result = null;
		DepositStatsRequest request = new DepositStatsRequest();
		BeanUtils.copyProperties(statsVo, request);
		try {
			result = yxwService.getStatsInfos(request);
		} catch (Exception e) {
			logger.error("getStatsInfo error. errorMsg: {}. cause: {}.", new Object[]{e.getMessage(), e.getCause()});
		}
		
		return result;
	}

}
