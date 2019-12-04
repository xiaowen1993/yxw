package com.yxw.solr.biz.rebuild.callable;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.register.service.RegDeptService;
import com.yxw.solr.vo.register.RegDeptStatsRequest;

public class RegDeptRebuildCallable extends RebuildCallable {
	
	private RegDeptService service = SpringContextHolder.getBean(RegDeptService.class);
	
	private Logger logger = LoggerFactory.getLogger(RegDeptRebuildCallable.class);

	public RegDeptRebuildCallable(Integer platform, String hospitalCode, String branchCode, String statsDate) {
		super(platform, hospitalCode, branchCode, statsDate);
	}

	@Override
	public String call() throws Exception {
		RegDeptStatsRequest request = new RegDeptStatsRequest();
		request.setHospitalCode(this.getHospitalCode());
		request.setBranchCode(this.getBranchCode());
		request.setPlatform(this.getPlatform());
		request.setBeginDate(this.getStatsDate());
		
		Map<String, Object> result = null;
		if (request.getPlatform() == -1) {
			result = service.rebuildWithoutPlatform(request);
		} else {
			result = service.rebuildWithinPlatform(request);
		}
		
		logger.info(JSON.toJSONString(result));
		
		return null;
	}

}
