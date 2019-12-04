package com.yxw.solr.biz.deposit.service.impl;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.solr.biz.deposit.service.DepositService;
import com.yxw.solr.task.collector.StatsDepositCollector;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.deposit.DepositStatsRequest;

public class DepositServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void testStatsInfos() {
		DepositService service = SpringContextHolder.getBean(DepositService.class);
		DepositStatsRequest request = new DepositStatsRequest();
//		request.setHospitalCode("szszyy");
		// request.setBranchCode("0001");
		request.setBranchCode("-1");
		// request.setHospitalCode("szsdsrmyy");
		 request.setHospitalCode("fsszyy");
//		request.setBranchCode("45575559X");
		request.setPlatform(2);
		service.statsInfosWithinPlatform(request);
	}
	
	@Test
	public void testGetStatsInfos() {
		DepositService service = SpringContextHolder.getBean(DepositService.class);
		DepositStatsRequest request = new DepositStatsRequest();
		request.setHospitalCode("dgsrmyy");
		request.setPlatform(2);
		
		YxwResponse vo = service.getStatsInfos(request);
		System.out.println(JSON.toJSON(vo));
	}
	
	@Test
	public void doStats() {
		StatsDepositCollector collector = new StatsDepositCollector();
		collector.startUp();
		
	}

}
