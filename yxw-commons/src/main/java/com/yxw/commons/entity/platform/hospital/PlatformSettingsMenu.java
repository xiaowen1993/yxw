/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月14日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.commons.entity.platform.hospital;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * 接入平台配置信息实体
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月14日
 */
@Entity(name = "platformSettingsMenu")
public class PlatformSettingsMenu extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4980797208312198076L;

	/**
	 * 平台配置ID
	 */
	private String platformSettingsId;

	/**
	 * 菜单ID
	 */
	private String menuId;

	public PlatformSettingsMenu() {
		super();
	}

	public PlatformSettingsMenu(String platformSettingsId, String menuId) {
		super();
		this.platformSettingsId = platformSettingsId;
		this.menuId = menuId;
	}

	public String getPlatformSettingsId() {
		return platformSettingsId;
	}

	public void setPlatformSettingsId(String platformSettingsId) {
		this.platformSettingsId = platformSettingsId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
