package com.yxw.app.biz.area.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.app.biz.area.dao.AreaDao;
import com.yxw.app.biz.area.service.AreaService;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.area.Area;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;

@Service(value = "areaService")
public class AreaServiceImpl extends BaseServiceImpl<Area, String> implements AreaService {

	@Autowired
	private AreaDao areaDao;

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@Override
	protected BaseDao<Area, String> getDao() {
		return areaDao;
	}

	@Override
	public List<Area> findOneLevelAreas() {
		return areaDao.findOneLevelAreas();
	}

	@Override
	public List<Area> findTwoLevelAreasByParentId(String parentId) {
		return areaDao.findTwoLevelAreasByParentId(parentId);
	}

	@Override
	public List<Area> findThreeLevelAreasByParentId(String parentId) {
		return areaDao.findThreeLevelAreasByParentId(parentId);
	}

	@Override
	public List<Area> getCacheOneLevelAreas() {
		return getCacheAreas("1", "100000");
	}

	@Override
	public List<Area> getCacheTwoLevelAreasByParentId(String parentId) {
		return getCacheAreas("2", parentId);
	}

	@Override
	public List<Area> getCacheThreeLevelAreasByParentId(String parentId) {
		return getCacheAreas("3", parentId);
	}

	private List<Area> getCacheAreas(String level, String parentId) {
		List<Area> resultList = null;
		List<Object> params = new ArrayList<Object>();
		params.add(level);
		params.add(parentId);
		List<Object> results = serveComm.get(CacheType.AREA_CACHE, "getAreas", params);
		if (CollectionUtils.isNotEmpty(results)) {
			resultList = new ArrayList<Area>(results.size());
			String sourceJson = JSON.toJSONString(results);
			resultList.addAll(JSON.parseArray(sourceJson, Area.class));
		}

		return resultList != null ? resultList : new ArrayList<Area>();
	}

}
