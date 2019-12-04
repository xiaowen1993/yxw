package com.yxw.stats.entity.platform;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

public class UserSubscribe extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8421853913957505626L;

	/**
	 * 日期
	 */
	private String refDate;

	/**
	 * appid
	 */
	private String appId;

	/**
	 * 医院id
	 */
	private String hospitalId;

	/**
	 * 新增关注数
	 */
	private Integer newUser;

	/**
	 * 取消关注数
	 */
	private Integer cancelUser;

	/**
	 * 累计关注数
	 */
	private Integer cumulateUser;

	/**
	 * 平台类型 wechat-微信 alipay-支付宝
	 */
	private String platformMode;

	/**
	 * @return the date
	 */

	/**
	 * @return the appId
	 */

	public String getAppId() {
		return appId;
	}

	/**
	 * @return the refDate
	 */

	public String getRefDate() {
		return refDate;
	}

	/**
	 * @param refDate
	 *            the refDate to set
	 */

	public void setRefDate(String refDate) {
		this.refDate = refDate;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */

	public void setAppId(String appId) {
		this.appId = appId;
	}

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
	 * @return the newUser
	 */

	public Integer getNewUser() {
		return newUser;
	}

	/**
	 * @param newUser
	 *            the newUser to set
	 */

	public void setNewUser(Integer newUser) {
		this.newUser = newUser;
	}

	/**
	 * @return the cancelUser
	 */

	public Integer getCancelUser() {
		return cancelUser;
	}

	/**
	 * @param cancelUser
	 *            the cancelUser to set
	 */

	public void setCancelUser(Integer cancelUser) {
		this.cancelUser = cancelUser;
	}

	/**
	 * @return the cumulateUser
	 */

	public Integer getCumulateUser() {
		return cumulateUser;
	}

	/**
	 * @param cumulateUser
	 *            the cumulateUser to set
	 */

	public void setCumulateUser(Integer cumulateUser) {
		this.cumulateUser = cumulateUser;
	}

	/**
	 * @return the platformMode
	 */

	public String getPlatformMode() {
		return platformMode;
	}

	/**
	 * @param platformMode
	 *            the platformMode to set
	 */

	public void setPlatformMode(String platformMode) {
		this.platformMode = platformMode;
	}
}
