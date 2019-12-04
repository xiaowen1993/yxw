package com.yxw.platform.app.optional.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.app.optional.AppOptional;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.app.optional.dao.AppOptionalDao;
import com.yxw.platform.app.optional.service.AppOptionalService;

@Service(value = "appOptionalService")
public class AppOptionalServiceImpl extends BaseServiceImpl<AppOptional, Integer> implements AppOptionalService {

	@Autowired
	private AppOptionalDao appOptionalDao;

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@Override
	protected BaseDao<AppOptional, Integer> getDao() {
		return appOptionalDao;
	}

	@Override
	public List<AppOptional> findByModuleId(String moduleId) {
		return appOptionalDao.findByModuleId(moduleId);
	}

	@Override
	public List<AppOptional> getCacheAppOptionalsByModuleCode(String moduleCode) {
		List<AppOptional> resultList = null;
		List<Object> params = new ArrayList<Object>();
		params.add(moduleCode);
		List<Object> results = serveComm.get(CacheType.APP_OPTIONAL_CACHE, "getAppOptionalsByModuleCode", params);
		if (CollectionUtils.isNotEmpty(results)) {
			resultList = new ArrayList<AppOptional>(results.size());
			String sourceJson = JSON.toJSONString(results);
			resultList.addAll(JSON.parseArray(sourceJson, AppOptional.class));
		}

		return resultList != null ? resultList : new ArrayList<AppOptional>();
	}
	
	@Override
	public void setCacheAppOptionalsByModuleCode(List<AppOptional> appOptionals, String moduleCode) {
		if (CollectionUtils.isNotEmpty(appOptionals)) {
			List<Object> parameters = new ArrayList<Object>();
			parameters.add(appOptionals);
			parameters.add(moduleCode);
			serveComm.set(CacheType.APP_OPTIONAL_CACHE, "setAppOptionalsByModuleCode", parameters);
		}
	}

}
