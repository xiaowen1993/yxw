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
package com.yxw.platform.hospital.dao;

import java.util.List;

import com.yxw.commons.entity.platform.hospital.Menu;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * 后台自定义菜单管理 Dao
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface MenuDao extends BaseDao<Menu, String> {

	/**
	 * 根据医院ID及接入平台ID查询
	 * 
	 * @param hospitalId
	 * @param platFormIds
	 * @return
	 */
	public List<Menu> findMenuByHospitalIdAndPlatformIds(String hospitalId, String[] platformIds);

	/**
	 * 根据医院ID及接入平台Code查询
	 * 
	 * @param hospitalId
	 * @param platformCode
	 * @return
	 */
	public List<Menu> findMenuByHospitalIdAndPlatformCode(String hospitalId);

	/**
	 * 根据接入平台配置Id查询菜单Id
	 * @param platformSettingsId
	 * @return
	 */
	public List<String> findDeleteMenuIdByPlatformSettingsId(String platformSettingsId);
}
