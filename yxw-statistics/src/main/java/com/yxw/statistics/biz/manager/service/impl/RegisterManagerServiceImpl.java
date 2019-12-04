package com.yxw.statistics.biz.manager.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.outside.service.YxwRegisterService;
import com.yxw.solr.vo.register.RegInfoRequest;
import com.yxw.statistics.biz.manager.service.RegisterManagerService;
import com.yxw.statistics.biz.vo.RegisterManagerVo;

@Service("registerManagerService")
public class RegisterManagerServiceImpl implements RegisterManagerService {
	
	private YxwRegisterService service = SpringContextHolder.getBean(YxwRegisterService.class);

	@Override
	public String getOrders(RegisterManagerVo vo) {
		RegInfoRequest request = new RegInfoRequest();
		BeanUtils.copyProperties(vo, request);
		// request.setBizStatus(vo.getBizStatus());
		return service.findOrders(request);
	}

}
