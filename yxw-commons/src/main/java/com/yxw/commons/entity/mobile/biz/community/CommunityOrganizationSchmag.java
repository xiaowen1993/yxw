package com.yxw.commons.entity.mobile.biz.community;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 *  社康中心  排班医生 实现类
 *  
 *  郑灏帆
 */

public class CommunityOrganizationSchmag extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3047036965936249523L;

	/**
	 * uuid 主键
	 */
	private String id;
	/**
	 * 关联社区id
	 */
	private String organizationId;

	/**
	 * 关联社区名称
	 */
	private String organizationName;
	/**
	 * 星期
	 */
	private String week;
	/**
	 * 时间段
	 */
	private String timeSlot;

	/**
	 * 专家名称
	 */
	private String doctorName;
	/**
	 * 职称
	 */
	private String position;
	/**
	 * 专科
	 */
	private String specialty;
	/**
	 * 派出医院
	 */
	private String hospitalName;
	/**
	 * 医院对应id 
	 */
	private String hospitalId;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 修改时间
	 */
	private Long updateTime;

	/**
	 * 预留备用字段
	 */
	private String aux1;
	private String aux2;
	private String aux3;

	/**
	 * 截取之后的机构名称
	 */
	private String organizationNameSub;

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAux1() {
		return aux1;
	}

	public void setAux1(String aux1) {
		this.aux1 = aux1;
	}

	public String getAux2() {
		return aux2;
	}

	public void setAux2(String aux2) {
		this.aux2 = aux2;
	}

	public String getAux3() {
		return aux3;
	}

	public void setAux3(String aux3) {
		this.aux3 = aux3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrganizationNameSub() {
		int staSize = this.organizationName.indexOf("医院");
		return this.organizationName.substring(staSize > 0 ? staSize + 2 : 0, this.organizationName.length());
	}

}
