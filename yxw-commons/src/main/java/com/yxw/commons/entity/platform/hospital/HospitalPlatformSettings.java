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
 * 医院和接入平台配置信息中间实体
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月14日
 */
@Entity(name = "hospitalPlatformSettings")
public class HospitalPlatformSettings extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8604358031894773909L;

	private PlatformSettings platformSettings;
	// private Long platformSettingsId;
	private String hospitalId;
	private int status;

	/**
	 * 
	 */
	public HospitalPlatformSettings() {
		super();
	}

	/**
	 * @param hospitalId
	 * @param status
	 */
	public HospitalPlatformSettings(String hospitalId, int status) {
		super();
		this.hospitalId = hospitalId;
		this.status = status;
	}

	public HospitalPlatformSettings(PlatformSettings platformSettings, String hospitalId) {
		super();
		this.platformSettings = platformSettings;
		this.hospitalId = hospitalId;
	}

	/**
	 * @return the platformSettings
	 */
	public PlatformSettings getPlatformSettings() {
		return platformSettings;
	}

	/**
	 * @param platformSettings
	 *            the platformSettings to set
	 */
	public void setPlatformSettings(PlatformSettings platformSettings) {
		this.platformSettings = platformSettings;
	}

	// /**
	// * @return the platformSettingsId
	// */
	// public Long getPlatformSettingsId() {
	// return platformSettingsId;
	// }
	//
	// /**
	// * @param platformSettingsId
	// * the platformSettingsId to set
	// */
	// public void setPlatformSettingsId(Long platformSettingsId) {
	// this.platformSettingsId = platformSettingsId;
	// }

	/**
	 * @return the hospitalId
	 */
	public String getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId
	 *            the hospitalId to set
	 */
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
