package com.yxw.app.biz.location.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.app.biz.location.dao.AppLocationDao;
import com.yxw.app.biz.location.service.AppLocationService;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.app.location.AppLocation;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;

@Service(value = "appLocationService")
public class AppLocationServiceImpl extends BaseServiceImpl<AppLocation, String> implements AppLocationService {

	@Autowired
	private AppLocationDao appLocationDao;

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@Override
	protected BaseDao<AppLocation, String> getDao() {
		return appLocationDao;
	}

	@Override
	public List<AppLocation> getCacheAll() {
		List<AppLocation> resultList = null;
		List<Object> params = new ArrayList<Object>();
		List<Object> results = serveComm.get(CacheType.APP_LOCATION_CACHE, "get", params);
		if (CollectionUtils.isNotEmpty(results)) {
			resultList = new ArrayList<AppLocation>(results.size());
			String sourceJson = JSON.toJSONString(results);
			resultList.addAll(JSON.parseArray(sourceJson, AppLocation.class));
		}

		return resultList != null ? resultList : new ArrayList<AppLocation>();
	}

}
