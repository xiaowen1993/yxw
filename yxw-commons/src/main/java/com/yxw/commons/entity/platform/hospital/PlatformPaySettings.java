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
@Entity(name = "platFormPaySettings")
public class PlatformPaySettings extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8604358031894773909L;

	/**
	 * 支付属性ID
	 */
	private String paySettingsId;

	private PaySettings paySettings;

	/**
	 * 医院ID
	 */
	//	private String hospitalId;

	/**
	 * 平台ID
	 */
	private String platformSettingsId;

	private HospitalPlatformSettings hospitalPlatformSettings;

	/**
	 * 标识是否删除 1：未删除  0:删除
	 */
	private int status;

	/**
	 * 
	 */
	public PlatformPaySettings() {
		super();
	}

	public PlatformPaySettings(String paySettingsId, String platformSettingsId, int status) {
		super();
		this.paySettingsId = paySettingsId;
		this.platformSettingsId = platformSettingsId;
		this.status = status;
	}

	public String getPaySettingsId() {
		return paySettingsId;
	}

	public void setPaySettingsId(String paySettingsId) {
		this.paySettingsId = paySettingsId;
	}

	public PaySettings getPaySettings() {
		return paySettings;
	}

	public void setPaySettings(PaySettings paySettings) {
		this.paySettings = paySettings;
	}

	public String getPlatformSettingsId() {
		return platformSettingsId;
	}

	public void setPlatformSettingsId(String platformSettingsId) {
		this.platformSettingsId = platformSettingsId;
	}

	public HospitalPlatformSettings getHospitalPlatformSettings() {
		return hospitalPlatformSettings;
	}

	public void setHospitalPlatformSettings(HospitalPlatformSettings hospitalPlatformSettings) {
		this.hospitalPlatformSettings = hospitalPlatformSettings;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
