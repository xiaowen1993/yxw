package com.yxw.solr.biz.rebuild.callable;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.medicalcard.service.MedicalcardService;
import com.yxw.solr.vo.medicalcard.CardStatsRequest;

public class MedicalcardRebuildCallable extends RebuildCallable {
	
	private MedicalcardService medicalcardService = SpringContextHolder.getBean(MedicalcardService.class);
	
	private Logger logger = LoggerFactory.getLogger(MedicalcardRebuildCallable.class);

	public MedicalcardRebuildCallable(Integer platform, String hospitalCode, String branchCode, String statsDate) {
		super(platform, hospitalCode, branchCode, statsDate);
	}

	@Override
	public String call() throws Exception {
		CardStatsRequest request = new CardStatsRequest();
		request.setHospitalCode(this.getHospitalCode());
		request.setBranchCode(this.getBranchCode());
		request.setPlatform(this.getPlatform());
		request.setBeginDate(this.getStatsDate());
		
		Map<String, Object> result = null;
		if (request.getPlatform() == -1) {
			result = medicalcardService.rebuildWithoutPlatform(request);
		} else {
			result = medicalcardService.rebuildWithinPlatform(request);
		}
		
		logger.info(JSON.toJSONString(result));
		
		return null;
	}

}
