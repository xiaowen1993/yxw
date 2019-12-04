package com.yxw.statistics.biz.stats.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.outside.service.YxwCardService;
import com.yxw.solr.vo.medicalcard.CardStatsRequest;
import com.yxw.statistics.biz.stats.service.CardStatsService;
import com.yxw.statistics.biz.stats.service.OrderStatsService;
import com.yxw.statistics.biz.vo.StatsVo;

@Service(value="cardStatsService")
public class CardStatsServiceImpl implements CardStatsService {
	
	private Logger logger = LoggerFactory.getLogger(OrderStatsService.class);
	
	private YxwCardService yxwService = SpringContextHolder.getBean(YxwCardService.class);

	@Override
	public String getStatsInfo(StatsVo statsVo) {
		String result = null;
		CardStatsRequest request = new CardStatsRequest();
		BeanUtils.copyProperties(statsVo, request);
		try {
			result = yxwService.getStatsInfos(request);
		} catch (Exception e) {
			logger.error("getStatsInfo error. errorMsg: {}. cause: {}.", new Object[]{e.getMessage(), e.getCause()});
		}
		
		return result;
	}

}
