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
package com.yxw.platform.privilege.entity;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.user.entity
 * @ClassName: Organization
 * @Statement: <p>组织/机构/公司 表</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Organization extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2048354055540113573L;

	/**组织/机构/公司 名称*/
	private String name;
	/**简介*/
	private String introduction;
	/**父ID*/
	private String parentId;
	/**编码(预留)*/
	private String code;
	/**备注*/
	private String memo;

	/**
	 * 
	 */
	public Organization() {
		super();
	}

	/**
	 * @param name
	 * @param introduction
	 * @param parentId
	 * @param code
	 * @param memo
	 */
	public Organization(String name, String introduction, String parentId, String code, String memo) {
		super();
		this.name = name;
		this.introduction = introduction;
		this.parentId = parentId;
		this.code = code;
		this.memo = memo;
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
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
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

}
