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

import com.yxw.commons.entity.platform.hospital.Menu;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.MenuDao;

/**
 * 后台功能选择管理 Dao 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu, String> implements MenuDao {

	private Logger logger = LoggerFactory.getLogger(MenuDaoImpl.class);

	private static final String SQLNAME_FIND_MENU_BY_HOSPITAL_ID_AND_PLATFORMIDS = "findMenuByHospitalIdAndPlatformIds";
	private static final String SQLNAME_FIND_SZ_MENU_BY_HOSPITAL_ID = "findSZMenuByHospitalId";
	private static final String SQLNAME_FIND_DELETE_MENUID_BY_PLATFORMSETTINGSID = "findDeleteMenuIdByPlatformSettingsId";

	@Override
	public List<Menu> findMenuByHospitalIdAndPlatformIds(String hospitalId, String[] platformIds) {
		try {
			HashMap<Object, Object> param = new HashMap<Object, Object>();
			param.put("hospitalId", hospitalId);
			param.put("platformIds", platformIds);
			return sqlSession.selectList(SQLNAME_FIND_MENU_BY_HOSPITAL_ID_AND_PLATFORMIDS, param);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID及接入平台ID查询！语句：%s", SQLNAME_FIND_MENU_BY_HOSPITAL_ID_AND_PLATFORMIDS), e);
			throw new SystemException(String.format("根据医院ID及接入平台ID查询！语句：%s", SQLNAME_FIND_MENU_BY_HOSPITAL_ID_AND_PLATFORMIDS), e);
		}
	}

	@Override
	public List<Menu> findMenuByHospitalIdAndPlatformCode(String hospitalId) {
		try {
			return sqlSession.selectList(SQLNAME_FIND_SZ_MENU_BY_HOSPITAL_ID, hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID查询！语句：%s", SQLNAME_FIND_SZ_MENU_BY_HOSPITAL_ID), e);
			throw new SystemException(String.format("根据医院ID查询！语句：%s", SQLNAME_FIND_SZ_MENU_BY_HOSPITAL_ID), e);
		}
	}

	@Override
	public List<String> findDeleteMenuIdByPlatformSettingsId(String platformSettingsId) {
		List<String> list = null;
		try {
			list = sqlSession.selectList(SQLNAME_FIND_DELETE_MENUID_BY_PLATFORMSETTINGSID, platformSettingsId);
		} catch (Exception e) {
			logger.error(String.format("根据接入平台配置Id查询菜单Id查询！语句：%s", SQLNAME_FIND_DELETE_MENUID_BY_PLATFORMSETTINGSID), e);
			throw new SystemException(String.format("根据接入平台配置Id查询菜单Id查询！语句：%s", SQLNAME_FIND_DELETE_MENUID_BY_PLATFORMSETTINGSID), e);
		}
		return list;
	}
}
