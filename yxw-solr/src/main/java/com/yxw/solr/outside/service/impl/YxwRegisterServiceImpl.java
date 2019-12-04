package com.yxw.solr.outside.service.impl;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.register.service.RegisterService;
import com.yxw.solr.outside.service.YxwRegisterService;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.register.RegInfoRequest;
import com.yxw.solr.vo.register.RegStatsRequest;

public class YxwRegisterServiceImpl implements YxwRegisterService {
	
	private RegisterService service = SpringContextHolder.getBean(RegisterService.class); 

	@Override
	public String getStatsInfos(RegStatsRequest request) {
		YxwResponse vo = service.getStatsInfos(request);
		return JSON.toJSONString(vo);
	}

	@Override
	public String statsDailyInfo(RegStatsRequest request) {
		return service.statsInfoForDayWithinPlatform(request);
	}

	@Override
	public String findOrders(RegInfoRequest request) {
		YxwResponse vo = service.findOrders(request);
		return JSON.toJSONString(vo);
	}

}
