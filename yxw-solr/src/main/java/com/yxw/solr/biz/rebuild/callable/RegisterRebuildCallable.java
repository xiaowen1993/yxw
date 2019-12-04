package com.yxw.solr.biz.rebuild.callable;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.register.service.RegisterService;
import com.yxw.solr.vo.register.RegStatsRequest;

public class RegisterRebuildCallable extends RebuildCallable {
	
	private RegisterService service = SpringContextHolder.getBean(RegisterService.class);
	
	private Logger logger = LoggerFactory.getLogger(RegisterRebuildCallable.class);

	public RegisterRebuildCallable(Integer platform, String hospitalCode, String branchCode, String statsDate) {
		super(platform, hospitalCode, branchCode, statsDate);
	}

	@Override
	public String call() throws Exception {
		RegStatsRequest request = new RegStatsRequest();
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
