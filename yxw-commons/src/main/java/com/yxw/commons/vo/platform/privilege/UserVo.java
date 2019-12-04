/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年9月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.platform.privilege;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.privilege.vo
 * @ClassName: UserVo
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月28日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class UserVo extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8799298465746581856L;

	/**用户名称*/
	private String fullName;
	/**登录账号*/
	private String userName;
	private String password;
	private String salt;
	private String email;
	/**备注*/
	private String memo;
	private Integer available;
	private String organizationId;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;
	private String lastLoginIp;

	private String roleNames;

	/**
	 * 
	 */
	public UserVo() {
		super();
	}

	/**
	 * @param fullName
	 * @param userName
	 * @param password
	 * @param salt
	 * @param email
	 * @param memo
	 * @param available
	 * @param organizationId
	 * @param lastLoginTime
	 * @param lastLoginIp
	 * @param roleNames
	 */
	public UserVo(String fullName, String userName, String password, String salt, String email, String memo, Integer available,
			String organizationId, Date lastLoginTime, String lastLoginIp, String roleNames) {
		super();
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.salt = salt;
		this.email = email;
		this.memo = memo;
		this.available = available;
		this.organizationId = organizationId;
		this.lastLoginTime = lastLoginTime;
		this.lastLoginIp = lastLoginIp;
		this.roleNames = roleNames;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the available
	 */
	public Integer getAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(Integer available) {
		this.available = available;
	}

	/**
	 * @return the organizationId
	 */
	public String getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the lastLoginIp
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param lastLoginIp the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * @return the roleNames
	 */
	public String getRoleNames() {
		return roleNames;
	}

	/**
	 * @param roleNames the roleNames to set
	 */
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

}
