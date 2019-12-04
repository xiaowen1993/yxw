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
 * @Statement: <p>智能分诊 症状与疾病关联表</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SymptomDisease extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2924510420623698157L;

	/**
	 * 症状表ID
	 */
	private String symptomId;

	/**
	 * 疾病表ID
	 */
	private String diseaseId;

	public SymptomDisease() {
		super();
	}

	public SymptomDisease(String symptomId, String diseaseId) {
		super();
		this.symptomId = symptomId;
		this.diseaseId = diseaseId;
	}

	public String getSymptomId() {
		return symptomId;
	}

	public void setSymptomId(String symptomId) {
		this.symptomId = symptomId;
	}

	public String getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(String diseaseId) {
		this.diseaseId = diseaseId;
	}

}
