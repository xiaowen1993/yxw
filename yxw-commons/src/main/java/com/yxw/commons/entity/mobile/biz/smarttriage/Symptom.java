/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.entity.mobile.biz.smarttriage;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;


/**
 * 
 * @Package: com.yxw.easyhealth.biz.smarttriage.entity
 * @ClassName: EasyHealthUser
 * @Statement: <p>智能分诊 症状</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Symptom extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2904991319211319301L;

	/**
	 * 症状名称
	 */
	private String name;

	/**
	 * 部位
	 */
	private String part;

	/**
	 * 是否显示 0：隐藏 1：显示
	 */
	private Integer isHide;

	public Symptom() {
		super();
	}

	public Symptom(String name, String part, Integer isHide) {
		super();
		this.name = name;
		this.part = part;
		this.isHide = isHide;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

}
