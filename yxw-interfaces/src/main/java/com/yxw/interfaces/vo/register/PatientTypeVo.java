/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年11月20日</p>
 *  <p> Created by 范建明</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.register;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * @Package: com.yxw.interfaces.vo.register
 * @ClassName: PatientTypeVo
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 范建明
 * @Create Date: 2015年11月20日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PatientTypeVo extends Reserve implements Serializable {

	private static final long serialVersionUID = -2597588247321809334L;
	/**
	 * 就诊类型
	 */
	private String mediCareType;
	/**
	 * 患者类型ID
	 */
	private String patientTypeId;
	/**
	 * 患者类型名称
	 */
	private String patientTypeName;
	
	public PatientTypeVo() {
		super();
	}

	public PatientTypeVo(String mediCareType, String patientTypeId, 
			String patientTypeName) {
		super();
		this.mediCareType = mediCareType;
		this.patientTypeId = patientTypeId;
		this.patientTypeName = patientTypeName;
	}

	public String getMediCareType() {
		return mediCareType;
	}

	public void setMediCareType(String mediCareType) {
		this.mediCareType = mediCareType;
	}

	public String getPatientTypeId() {
		return patientTypeId;
	}

	public void setPatientTypeId(String patientTypeId) {
		this.patientTypeId = patientTypeId;
	}

	public String getPatientTypeName() {
		return patientTypeName;
	}

	public void setPatientTypeName(String patientTypeName) {
		this.patientTypeName = patientTypeName;
	}

}
