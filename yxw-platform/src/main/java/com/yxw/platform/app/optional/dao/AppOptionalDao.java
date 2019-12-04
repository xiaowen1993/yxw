package com.yxw.platform.app.optional.dao;

import java.util.List;

import com.yxw.commons.entity.platform.app.optional.AppOptional;
import com.yxw.framework.mvc.dao.BaseDao;

public interface AppOptionalDao extends BaseDao<AppOptional, Integer> {
	
	public List<AppOptional> findByModuleId(String moduleId);
	

}
