package com.yxw.solr.outside.service.impl;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.solr.biz.common.service.HospitalInfoService;
import com.yxw.solr.constants.BizConstant;
import com.yxw.solr.outside.service.YxwRebuildService;
import com.yxw.solr.vo.rebuild.RebuildRequest;
import com.yxw.solr.vo.rebuild.RebuildResponse;

public class YxwRebuildServiceImplTest extends Junit4SpringContextHolder {
	
	@Test
	public void testCardRebuild() {
		YxwRebuildService service = SpringContextHolder.getBean(YxwRebuildService.class);
		HospitalInfoService hospitalInfoService = SpringContextHolder.getBean(HospitalInfoService.class);
		Map<String, Map<String, List<String>>> hospitalInfos = hospitalInfoService.getInfos();
		
		RebuildRequest request = new RebuildRequest();
		request.setBizCode(BizConstant.BIZ_TYPE_CARD);
		request.setHospitalCode("dgsrmyy");
		request.setHospitalInfosMap(hospitalInfos.get("dgsrmyy"));
		request.setBeginDate("2016-06-01");
		RebuildResponse response = service.rebuild(request);
		System.out.println(JSON.toJSONString(response));
	}
	
	@Test
	public void testOrderRebuild() {
		YxwRebuildService service = SpringContextHolder.getBean(YxwRebuildService.class);
		HospitalInfoService hospitalInfoService = SpringContextHolder.getBean(HospitalInfoService.class);
		Map<String, Map<String, List<String>>> hospitalInfos = hospitalInfoService.getInfos();
		
		RebuildRequest request = new RebuildRequest();
		request.setBizCode(BizConstant.BIZ_TYPE_ORDER);
		request.setHospitalCode("dgsrmyy");
		request.setHospitalInfosMap(hospitalInfos.get("dgsrmyy"));
		request.setBeginDate("2016-06-01");
		RebuildResponse response = service.rebuild(request);
		System.out.println(JSON.toJSONString(response));
	}
	
	@Test
	public void testRegDeptRebuild() {
		YxwRebuildService service = SpringContextHolder.getBean(YxwRebuildService.class);
		HospitalInfoService hospitalInfoService = SpringContextHolder.getBean(HospitalInfoService.class);
		Map<String, Map<String, List<String>>> hospitalInfos = hospitalInfoService.getInfos();
		
		RebuildRequest request = new RebuildRequest();
		request.setBizCode(BizConstant.BIZ_TYPE_DEPT);
		request.setHospitalCode("dgsrmyy");
		request.setHospitalInfosMap(hospitalInfos.get("dgsrmyy"));
		request.setBeginDate("2016-06-01");
		RebuildResponse response = service.rebuild(request);
		System.out.println(JSON.toJSONString(response));
	}
	
	@Test
	public void testRegisterRebuild() {
		YxwRebuildService service = SpringContextHolder.getBean(YxwRebuildService.class);
		HospitalInfoService hospitalInfoService = SpringContextHolder.getBean(HospitalInfoService.class);
		Map<String, Map<String, List<String>>> hospitalInfos = hospitalInfoService.getInfos();
		
		RebuildRequest request = new RebuildRequest();
		request.setBizCode(BizConstant.BIZ_TYPE_REGISTER);
		request.setHospitalCode("dgsrmyy");
		request.setHospitalInfosMap(hospitalInfos.get("dgsrmyy"));
		request.setBeginDate("2016-06-01");
		RebuildResponse response = service.rebuild(request);
		System.out.println(JSON.toJSONString(response));
	}
	
	@Test
	public void testClinicRebuild() {
		YxwRebuildService service = SpringContextHolder.getBean(YxwRebuildService.class);
		HospitalInfoService hospitalInfoService = SpringContextHolder.getBean(HospitalInfoService.class);
		Map<String, Map<String, List<String>>> hospitalInfos = hospitalInfoService.getInfos();
		
		RebuildRequest request = new RebuildRequest();
		request.setBizCode(BizConstant.BIZ_TYPE_CLINIC);
		request.setHospitalCode("dgsrmyy");
		request.setHospitalInfosMap(hospitalInfos.get("dgsrmyy"));
		request.setBeginDate("2016-06-01");
		RebuildResponse response = service.rebuild(request);
		System.out.println(JSON.toJSONString(response));
	}
	
	@Test
	public void testDepositRebuild() {
		YxwRebuildService service = SpringContextHolder.getBean(YxwRebuildService.class);
		HospitalInfoService hospitalInfoService = SpringContextHolder.getBean(HospitalInfoService.class);
		Map<String, Map<String, List<String>>> hospitalInfos = hospitalInfoService.getInfos();
		
		RebuildRequest request = new RebuildRequest();
		request.setBizCode(BizConstant.BIZ_TYPE_DEPOSIT);
		request.setHospitalCode("dgsrmyy");
		request.setHospitalInfosMap(hospitalInfos.get("dgsrmyy"));
		request.setBeginDate("2016-06-01");
		RebuildResponse response = service.rebuild(request);
		System.out.println(JSON.toJSONString(response));
	}
	
}
