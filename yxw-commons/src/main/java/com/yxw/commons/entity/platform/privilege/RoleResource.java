/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.entity.platform.privilege;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.user.entity
 * @ClassName: RoleResource
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RoleResource extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7039509091176333740L;
	private String roleId;
	private String resourceId;

	/**
	 * 
	 */
	public RoleResource() {
		super();
	}

	/**
	 * @param roleId
	 * @param resourceId
	 */
	public RoleResource(String roleId, String resourceId) {
		super();
		this.roleId = roleId;
		this.resourceId = resourceId;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

}
