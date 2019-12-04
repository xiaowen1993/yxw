package com.yxw.solr.outside.service.impl;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.clinic.service.ClinicService;
import com.yxw.solr.outside.service.YxwClinicService;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.clinic.ClinicInfoRequest;
import com.yxw.solr.vo.clinic.ClinicStatsRequest;

public class YxwClinicServiceImpl implements YxwClinicService {
	
	private ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);

	@Override
	public String getStatsInfos(ClinicStatsRequest request) {
		YxwResponse responseVo = clinicService.getStatsInfos(request);
		return JSON.toJSONString(responseVo);
	}

	@Override
	public String statsDailyInfo(ClinicStatsRequest request) {
		return clinicService.statsInfoForDayWithinPlatform(request);
	}

	@Override
	public String findOrders(ClinicInfoRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
