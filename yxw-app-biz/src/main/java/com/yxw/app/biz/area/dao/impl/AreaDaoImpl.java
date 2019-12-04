package com.yxw.app.biz.area.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.app.biz.area.dao.AreaDao;
import com.yxw.commons.entity.platform.area.Area;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

@Repository
public class AreaDaoImpl extends BaseDaoImpl<Area, String> implements AreaDao {

	private Logger logger = LoggerFactory.getLogger(AreaDaoImpl.class);

	private final static String SQLNAME_FIND_ONE_LEVEL_AREAS = "findOneLevelAreas";
	private final static String SQLNAME_FIND_TWO_LEVEL_AREAS_BY_PARENTID = "findTwoLevelAreasByParentId";
	private final static String SQLNAME_FIND_THREE_LEVEL_AREAS_BY_PARENTID = "findThreeLevelAreasByParentId";

	/**
	 * 查询所有的一级区域
	 */
	@Override
	public List<Area> findOneLevelAreas() {
		List<Area> areas = null;
		try {
			areas = sqlSession.selectList(getSqlName(SQLNAME_FIND_ONE_LEVEL_AREAS));
		} catch (Exception e) {
			logger.error(String.format("查询所有的一级区域！语句：%s", getSqlName(SQLNAME_FIND_ONE_LEVEL_AREAS)), e);
			throw new SystemException(String.format("查询所有的一级区域！语句：%s", getSqlName(SQLNAME_FIND_ONE_LEVEL_AREAS)), e);
		}
		return areas;
	}

	/**
	 * 根据上级ID查询下级区域
	 * @param parentId 上级ID
	 * @param sqlName 查询的SQLNAME
	 * @return
	 */
	private List<Area> findAreasByParentId(String parentId, String sqlName) {
		List<Area> areas = null;
		try {
			areas = sqlSession.selectList(getSqlName(sqlName), parentId);
		} catch (Exception e) {
			logger.error(String.format("查询所有的一级区域！语句：%s", getSqlName(sqlName)), e);
			throw new SystemException(String.format("查询所有的一级区域！语句：%s", getSqlName(sqlName)), e);
		}
		return areas;
	}

	/**
	 * 根据一级区域ID查询二级区域
	 */
	@Override
	public List<Area> findTwoLevelAreasByParentId(String parentId) {
		return findAreasByParentId(parentId, SQLNAME_FIND_TWO_LEVEL_AREAS_BY_PARENTID);
	}

	/**
	 * 根据二级区域ID查询三级区域
	 */
	@Override
	public List<Area> findThreeLevelAreasByParentId(String parentId) {
		return findAreasByParentId(parentId, SQLNAME_FIND_THREE_LEVEL_AREAS_BY_PARENTID);
	}

}
