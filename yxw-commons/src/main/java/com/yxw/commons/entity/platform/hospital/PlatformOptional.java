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
 * 医院和可选功能信息中间实体
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月14日
 */
@Entity(name = "hospitalOptional")
public class PlatformOptional extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3746944697384440345L;

	private Optional optional;
	// private int optionalId;
	private String platformSettingsId;

	/**
	 * 
	 */
	public PlatformOptional() {
		super();
	}


	/**
	 * @param optional
	 * @param hospitalId
	 */
	public PlatformOptional(Optional optional, String platformSettingsId) {
		super();
		this.optional = optional;
		this.setPlatformSettingsId(platformSettingsId);
	}

	/**
	 * @return the optional
	 */
	public Optional getOptional() {
		return optional;
	}

	// /**
	// * @return the optionalId
	// */
	// public int getOptionalId() {
	// return optionalId;
	// }
	//
	// /**
	// * @param optionalId
	// * the optionalId to set
	// */
	// public void setOptionalId(int optionalId) {
	// this.optionalId = optionalId;
	// }

	/**
	 * @param optional
	 *            the optional to set
	 */
	public void setOptional(Optional optional) {
		this.optional = optional;
	}

	public String getPlatformSettingsId() {
		return platformSettingsId;
	}

	public void setPlatformSettingsId(String platformSettingsId) {
		this.platformSettingsId = platformSettingsId;
	}

}
