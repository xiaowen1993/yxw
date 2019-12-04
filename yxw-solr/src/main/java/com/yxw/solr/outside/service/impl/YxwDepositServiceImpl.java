package com.yxw.solr.outside.service.impl;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.deposit.service.DepositService;
import com.yxw.solr.outside.service.YxwDepositService;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.deposit.DepositStatsRequest;

public class YxwDepositServiceImpl implements YxwDepositService {
	
	private DepositService depositService = SpringContextHolder.getBean(DepositService.class);

	@Override
	public String findOrders(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatsInfos(DepositStatsRequest request) {
		YxwResponse responseVo = depositService.getStatsInfos(request);
		return JSON.toJSONString(responseVo);
	}

	@Override
	public String statsDailyInfo(DepositStatsRequest request) {
		// 更换，最好新增一个方法 -- 该方法从指定的日期开始。
		return depositService.statsInfoForDayWithinPlatform(request);
	}

}
