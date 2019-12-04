/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-12-10</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.entity.platform.rule;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;
import com.yxw.commons.constants.biz.RuleConstant;

/**
 * @Package: com.yxw.platform.rule.entity
 * @ClassName: RuleHisData
 * @Statement: <p>his接口数据规则配置</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-12-10
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RuleHisData extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2977552447615795273L;

	/**
	 * 医院Id
	 */
	private String hospitalId;

	/**
	 * 当班与预约挂号的科室数据是否相同
	 */
	private Integer isSameDeptData;

	/**
	 * 当班与预约挂号的医生数据是否相同
	 */
	private Integer isSameDoctorData;

	/**
	 * 当班与预约挂号的科室数据不相同时-是否有当班可挂号医生缓存接口
	 */
	private Integer isHadCurDoctorInterface;

	/**
	 * 当班与预约挂号的科室数据不相同时-是否有预约可挂号医生缓存接口
	 */
	private Integer isHadAppointmentDoctorInterface;

	/**
	 * 当班与预约挂号的科室数据不相同时-是否有当班号源缓存接口
	 */
	private Integer isHadCurRegSourceInterface;

	/**
	 * 当班与预约挂号的科室数据不相同时-是否有预约号源缓存接口
	 */
	private Integer isHadAppointmentRegSourceInterface;

	public RuleHisData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RuleHisData(String hospitalId) {
		super();
		this.hospitalId = hospitalId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Integer getIsSameDeptData() {
		return isSameDeptData;
	}

	public void setIsSameDeptData(Integer isSameDeptData) {
		this.isSameDeptData = isSameDeptData;
	}

	public Integer getIsSameDoctorData() {
		return isSameDoctorData;
	}

	public void setIsSameDoctorData(Integer isSameDoctorData) {
		this.isSameDoctorData = isSameDoctorData;
	}

	public Integer getIsHadCurDoctorInterface() {
		return isHadCurDoctorInterface;
	}

	public void setIsHadCurDoctorInterface(Integer isHadCurDoctorInterface) {
		this.isHadCurDoctorInterface = isHadCurDoctorInterface;
	}

	public Integer getIsHadAppointmentDoctorInterface() {
		return isHadAppointmentDoctorInterface;
	}

	public void setIsHadAppointmentDoctorInterface(Integer isHadAppointmentDoctorInterface) {
		this.isHadAppointmentDoctorInterface = isHadAppointmentDoctorInterface;
	}

	public Integer getIsHadCurRegSourceInterface() {
		return isHadCurRegSourceInterface;
	}

	public void setIsHadCurRegSourceInterface(Integer isHadCurRegSourceInterface) {
		this.isHadCurRegSourceInterface = isHadCurRegSourceInterface;
	}

	public Integer getIsHadAppointmentRegSourceInterface() {
		return isHadAppointmentRegSourceInterface;
	}

	public void setIsHadAppointmentRegSourceInterface(Integer isHadAppointmentRegSourceInterface) {
		this.isHadAppointmentRegSourceInterface = isHadAppointmentRegSourceInterface;
	}

	public static RuleHisData getDefaultRule(String hospitalId) {
		RuleHisData rule = new RuleHisData(hospitalId);
		rule.setIsSameDeptData(RuleConstant.IS_SAME_DEPT_DATA_YES);

		rule.setIsSameDoctorData(RuleConstant.IS_SAME_DOCTOR_DATA_YES);
		rule.setIsHadCurDoctorInterface(RuleConstant.IS_HAD_INTERFACE_YES);
		rule.setIsHadAppointmentDoctorInterface(RuleConstant.IS_HAD_INTERFACE_YES);

		rule.setIsHadCurRegSourceInterface(RuleConstant.IS_HAD_INTERFACE_YES);
		rule.setIsHadAppointmentRegSourceInterface(RuleConstant.IS_HAD_INTERFACE_YES);
		return rule;
	}
}
