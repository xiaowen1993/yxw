package com.yxw.app.biz.area.dao;

import java.util.List;

import com.yxw.commons.entity.platform.area.Area;
import com.yxw.framework.mvc.dao.BaseDao;

public interface AreaDao extends BaseDao<Area, String> {
	
	/**
	 * 查询所有的一级区域
	 */
	public abstract List<Area> findOneLevelAreas();
	
	/**
	 * 根据一级区域ID查询二级区域
	 */
	public abstract List<Area> findTwoLevelAreasByParentId(String parentId);
	
	/**
	 * 根据二级区域ID查询三级区域
	 */
	public abstract List<Area> findThreeLevelAreasByParentId(String parentId);

}
