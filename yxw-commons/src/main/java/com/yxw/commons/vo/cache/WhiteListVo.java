/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-8-12</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.cache;

import java.io.Serializable;

import com.yxw.commons.entity.platform.hospital.WhiteListDetail;

/**
 * @Package: com.yxw.platform.datas.cache.vo
 * @ClassName: WhiteListVo
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-8-12
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class WhiteListVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8566814006169912943L;
	private String id;
	private String whiteListId;
	private String hospitalId;
	private String hospitalCode;
	private String appId;
	private String appCode;
	private String openId;
	private String phone;
	private Integer isOpen;
	private String name;

	public WhiteListVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WhiteListVo(String appId, String appCode, String openId, String phone, Integer isOpen) {
		super();
		this.appId = appId;
		this.appCode = appCode;
		this.openId = openId;
		this.phone = phone;
		this.isOpen = isOpen;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getWhiteListId() {
		return whiteListId;
	}

	public void setWhiteListId(String whiteListId) {
		this.whiteListId = whiteListId;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WhiteListDetail convertDetail() {
		WhiteListDetail detail = new WhiteListDetail();
		detail.setName(this.name);
		detail.setPhone(phone);
		detail.setWhiteListId(whiteListId);
		detail.setOpenId(openId);
		return detail;
	}
}
