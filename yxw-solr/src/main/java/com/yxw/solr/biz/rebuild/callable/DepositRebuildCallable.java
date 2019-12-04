package com.yxw.solr.biz.rebuild.callable;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.deposit.service.DepositService;
import com.yxw.solr.vo.deposit.DepositStatsRequest;

public class DepositRebuildCallable extends RebuildCallable {
	
	private DepositService service = SpringContextHolder.getBean(DepositService.class);
	
	public DepositRebuildCallable(Integer platform, String hospitalCode, String branchCode, String statsDate) {
		super(platform, hospitalCode, branchCode, statsDate);
	}

	@Override
	public String call() throws Exception {
		DepositStatsRequest request = new DepositStatsRequest();
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
		
		return JSON.toJSONString(result);
	}

}
