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
package com.yxw.commons.entity.platform.hospital;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.hospital.entity
 * @ClassName: WhiteListDetail
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月12日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Entity(name = "whiteListDetail")
public class WhiteListDetail extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5793085168212347403L;

	/**
	 * 白名单表id
	 */
	private String whiteListId;

	/**
	 * 用户名字
	 */
	private String name;

	private String phone;

	private String openId;

	/**
	 * 
	 */
	public WhiteListDetail() {
		super();
	}

	/**
	 * @param whiteListId
	 * @param name
	 * @param phone
	 * @param openId
	 */
	public WhiteListDetail(String whiteListId, String name, String phone, String openId) {
		super();
		this.whiteListId = whiteListId;
		this.name = name;
		this.phone = phone;
		this.openId = openId;
	}

	/**
	 * @return the whiteListId
	 */
	public String getWhiteListId() {
		return whiteListId;
	}

	/**
	 * @param whiteListId the whiteListId to set
	 */
	public void setWhiteListId(String whiteListId) {
		this.whiteListId = whiteListId;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * @param openId the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WhiteListDetail [whiteListId=" + whiteListId + ", name=" + name + ", phone=" + phone + ", openId=" + openId + "]";
	}

}
