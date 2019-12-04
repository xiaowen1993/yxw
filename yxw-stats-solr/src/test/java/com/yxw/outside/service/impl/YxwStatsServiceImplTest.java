package com.yxw.outside.service.impl;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.outside.service.YxwStatsService;
import com.yxw.outside.service.impl.YxwStatsServiceImpl;

public class YxwStatsServiceImplTest extends Junit4SpringContextHolder {

	@Test 
	public void test() {
		YxwStatsService service = new YxwStatsServiceImpl();
		System.err.println(JSON.toJSONString(service.getAllResume()));
	}
	
	@Test 
	public void testGetHospitalInfos() {
		YxwStatsService service = new YxwStatsServiceImpl();
		System.err.println(JSON.toJSONString(service.getAreaHospitalInfos()));
	}
	
	@Test 
	public void testGetAttribution() {
		YxwStatsService service = new YxwStatsServiceImpl();
		System.err.println(JSON.toJSONString(service.getAttributionDatas()));
	}
	
	@Test 
	public void testGetGender() {
		YxwStatsService service = new YxwStatsServiceImpl();
		String string = service.getGenderDatas("/440000/440100", "gzsdyrmyy", "2016-05-01", "2016-10-31");
		System.err.println(JSON.toJSONString(string));
	}
	
	@Test 
	public void testGetAgeGroup() {
		YxwStatsService service = new YxwStatsServiceImpl();
		String string = service.getAgeGroupDatas("/440000/440100", "gzsdyrmyy", "2016-05-01", "2016-10-31");
		System.err.println(JSON.toJSONString(string));
	}

}
