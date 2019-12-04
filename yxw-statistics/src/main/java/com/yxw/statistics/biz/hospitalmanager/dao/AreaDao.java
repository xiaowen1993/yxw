package com.yxw.statistics.biz.hospitalmanager.dao;

import java.util.List;

import com.yxw.statistics.biz.hospitalmanager.entity.Area;
import com.yxw.framework.mvc.dao.BaseDao;

public interface AreaDao extends BaseDao<Area, String> {
	public List<Area> findAllByLevel(Integer level);
	
	public List<Area> findAllHiperLevel(Integer level);
}
