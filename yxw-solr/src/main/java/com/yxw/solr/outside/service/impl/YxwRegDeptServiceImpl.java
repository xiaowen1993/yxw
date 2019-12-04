package com.yxw.solr.outside.service.impl;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.register.service.RegDeptService;
import com.yxw.solr.outside.service.YxwRegDeptService;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.register.RegDeptStatsRequest;

public class YxwRegDeptServiceImpl implements YxwRegDeptService {
	
	private RegDeptService service = SpringContextHolder.getBean(RegDeptService.class);

	@Override
	public String getStatsInfos(RegDeptStatsRequest request) {
		YxwResponse vo = service.getStatsInfos(request);
		return JSON.toJSONString(vo);
	}

	@Override
	public String statsDailyInfo(RegDeptStatsRequest request) {
		return service.statsInfoForDayWithinPlatform(request);
	}

}
