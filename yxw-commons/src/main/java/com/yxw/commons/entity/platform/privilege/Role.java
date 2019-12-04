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
 * @ClassName: Role
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Role extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1235401586289060999L;
	/**角色名*/
	private String name;
	/**角色编码 如admin,normal,yxw等等*/
	private String code;
	/**备注*/
	private String memo;
	/**是否可用(0禁用;1可用)*/
	private Integer available;

	/**
	 * 
	 */
	public Role() {
		super();
	}

	/**
	 * @param name
	 * @param code
	 * @param memo
	 * @param available
	 */
	public Role(String name, String code, String memo, Integer available) {
		super();
		this.name = name;
		this.code = code;
		this.memo = memo;
		this.available = available;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

}
