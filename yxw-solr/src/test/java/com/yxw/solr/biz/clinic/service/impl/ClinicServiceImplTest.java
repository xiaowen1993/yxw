package com.yxw.solr.biz.clinic.service.impl;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.solr.biz.clinic.service.ClinicService;
import com.yxw.solr.task.collector.StatsClinicCollector;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.clinic.ClinicStatsRequest;

public class ClinicServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void testStatsInfos() {
		ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);
		ClinicStatsRequest request = new ClinicStatsRequest();
		request.setBranchCode("-1");
		request.setHospitalCode("dgsrmyy");
		request.setPlatform(1);
		clinicService.statsInfosWithinPlatform(request);
	}
	
	@Test
	public void testStatsYesterdayInfos() {
		ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);
		ClinicStatsRequest request = new ClinicStatsRequest();
		request.setHospitalCode("szszyy");
		request.setBranchCode("0001");
		request.setPlatform(3);
		request.setStatsDate("2016-04-01");
//		request.setHospitalCode("szsdsrmyy");
//		request.setBranchCode("45575559X");
//		request.setPlatform(3);
		clinicService.statsInfoForDayWithinPlatform(request);
		
	}
	
	@Test
	public void testGetStatsInfos() {
		ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);
		ClinicStatsRequest request = new ClinicStatsRequest();
		request.setBranchCode("-1");
		request.setHospitalCode("dgsrmyy");
		request.setPlatform(1);
		YxwResponse responseVo = clinicService.getStatsInfos(request);
		System.out.println(JSON.toJSONString(responseVo));
	}
	
	@Test
	public void doStats() {
		StatsClinicCollector collector = new StatsClinicCollector();
		collector.startUp();
	}

}
