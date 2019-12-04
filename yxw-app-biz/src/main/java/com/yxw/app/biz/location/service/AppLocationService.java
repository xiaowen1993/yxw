package com.yxw.app.biz.location.service;

import java.util.List;

import com.yxw.commons.entity.platform.app.location.AppLocation;
import com.yxw.framework.mvc.service.BaseService;

public interface AppLocationService extends BaseService<AppLocation, String> {
	
	public List<AppLocation> getCacheAll();
	

}
