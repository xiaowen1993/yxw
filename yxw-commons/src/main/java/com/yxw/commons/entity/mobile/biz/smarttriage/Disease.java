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
 * @Statement: <p>智能分诊 疾病</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Disease extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 808833819797510797L;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 概述
	 */
	private String dummary;

	public Disease() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDummary() {
		return dummary;
	}

	public void setDummary(String dummary) {
		this.dummary = dummary;
	}

}
