package com.yxw.platform.hospital.dao;

import java.util.List;

import com.yxw.commons.entity.platform.hospital.PlatformSettingsMenu;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * 
 * @Package: com.yxw.platform.hospital.dao
 * @ClassName: PlatformSettingsMenuDao.java
 * @Statement: <p>
 *             菜单及平台配置信息管理 Dao
 *             </p>
 * @JDK version used: 1.6
 * @Author: zhoujb
 * @Create Date: 2015年5月28日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface PlatformSettingsMenuDao extends BaseDao<PlatformSettingsMenu, String> {

	/**
	 * 根据接入平台配置ID查询平台配置
	 * @param platformSettingsIds
	 * @return
	 */
	public List<PlatformSettingsMenu> findByPlatformSettingsIds(String[] platformSettingsIds);

	/**
	 * 根据菜单ID集合查询平台配置
	 * @param platformSettingsIds
	 * @return
	 */
	public List<PlatformSettingsMenu> findByMenuIds(String[] menuIds);

	/**
	 * 根据ID集合删除
	 * @param platformSettingsIds
	 * @return
	 */
	public void deleteByMenuIds(String[] menuIds);

	/**
	 * 根据接入平台配置ID删除
	 * @param platformSettingsId
	 */
	public void deleteByPlatformSettingsId(String platformSettingsId);

}