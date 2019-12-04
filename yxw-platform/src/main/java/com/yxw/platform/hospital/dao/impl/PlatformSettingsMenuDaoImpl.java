/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月15日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.hospital.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.platform.hospital.PlatformSettingsMenu;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.PlatformSettingsMenuDao;

/**
 * 菜单及平台配置信息管理 Dao 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Repository
public class PlatformSettingsMenuDaoImpl extends BaseDaoImpl<PlatformSettingsMenu, String> implements PlatformSettingsMenuDao {
	private static Logger logger = LoggerFactory.getLogger(PlatformSettingsMenuDaoImpl.class);

	private static final String SQLNAME_FIND_BY_PLATFORM_SETTINGSIDS = "findByPlatformSettingsIds";
	private static final String SQLNAME_FIND_BY_MENUIDS = "findByMenuIds";
	private static final String SQLNAME_DELETE_BY_MENUIDS = "deleteByMenuIds";
	private static final String SQLNAME_DELETE_BY_PLATFORM_SETTINGSID = "deleteByPlatformSettingsId";

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.dao.impl.PlatformSettingsMenuDao#findByHospitalIdAndPlatformIds(java.lang.Long, java.lang.Long[])
	 */
	@Override
	public List<PlatformSettingsMenu> findByPlatformSettingsIds(String[] platformSettingsIds) {
		try {
			HashMap<Object, Object> param = new HashMap<Object, Object>();
			param.put("platformSettingsIds", platformSettingsIds);
			return sqlSession.selectList(SQLNAME_FIND_BY_PLATFORM_SETTINGSIDS, param);
		} catch (Exception e) {
			logger.error(String.format("根据接入平台配置ID查询平台配置出错！语句：%s", SQLNAME_FIND_BY_PLATFORM_SETTINGSIDS), e);
			throw new SystemException(String.format("根据接入平台配置ID查询平台配置出错！语句：%s", SQLNAME_FIND_BY_PLATFORM_SETTINGSIDS), e);
		}
	}

	@Override
	public List<PlatformSettingsMenu> findByMenuIds(String[] menuIds) {
		try {
			HashMap<Object, Object> param = new HashMap<Object, Object>();
			param.put("menuIds", menuIds);
			return sqlSession.selectList(SQLNAME_FIND_BY_MENUIDS, param);
		} catch (Exception e) {
			logger.error(String.format("根据菜单ID集合查询平台配置出错！语句：%s", SQLNAME_FIND_BY_MENUIDS), e);
			throw new SystemException(String.format("根据菜单ID集合查询平台配置出错！语句：%s", SQLNAME_FIND_BY_MENUIDS), e);
		}
	}

	@Override
	public void deleteByMenuIds(String[] menuIds) {
		try {
			HashMap<Object, Object> param = new HashMap<Object, Object>();
			param.put("menuIds", menuIds);
			sqlSession.delete(SQLNAME_DELETE_BY_MENUIDS, param);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合删除出错！语句：%s", SQLNAME_DELETE_BY_MENUIDS), e);
			throw new SystemException(String.format("根据ID集合删除出错！语句：%s", SQLNAME_DELETE_BY_MENUIDS), e);
		}
	}

	@Override
	public void deleteByPlatformSettingsId(String platformSettingsId) {
		try {
			sqlSession.delete(SQLNAME_DELETE_BY_PLATFORM_SETTINGSID, platformSettingsId);
		} catch (Exception e) {
			logger.error(String.format("根据接入平台配置ID删除出错！语句：%s", SQLNAME_DELETE_BY_PLATFORM_SETTINGSID), e);
			throw new SystemException(String.format("根据接入平台配置ID删除出错！语句：%s", SQLNAME_DELETE_BY_PLATFORM_SETTINGSID), e);
		}

	}

}
