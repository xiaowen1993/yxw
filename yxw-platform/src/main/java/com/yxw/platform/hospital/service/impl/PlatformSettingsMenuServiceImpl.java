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
package com.yxw.platform.hospital.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.platform.hospital.PlatformSettingsMenu;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.PlatformSettingsMenuDao;
import com.yxw.platform.hospital.service.PlatformSettingsMenuService;

/**
 * 菜单及平台配置信息管理 Service 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Repository
public class PlatformSettingsMenuServiceImpl extends BaseDaoImpl<PlatformSettingsMenu, String> implements PlatformSettingsMenuService {
	//private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private PlatformSettingsMenuDao platformSettingsMenuDao;

	@Override
	public List<PlatformSettingsMenu> findByPlatformSettingsIds(String[] platformSettingsIds) {
		return platformSettingsMenuDao.findByPlatformSettingsIds(platformSettingsIds);
	}

	@Override
	public List<PlatformSettingsMenu> findByMenuIds(String[] menuIds) {
		return platformSettingsMenuDao.findByMenuIds(menuIds);
	}

}
