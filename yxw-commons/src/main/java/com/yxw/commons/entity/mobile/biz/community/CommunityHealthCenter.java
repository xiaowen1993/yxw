package com.yxw.commons.entity.mobile.biz.community;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 *  社康中心实现类
 *  
 *  郑灏帆
 */

public class CommunityHealthCenter extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3047036965936249522L;

	/**
	 * uuid 主键
	 */
	private String id;
	/**
	 * 医院id
	 */
	private String hospitalId;
	/**
	 * 医院名称
	 */
	private String hospitalName;
	/**
	 * 机构名称
	 */
	private String organizationName;
	/**
	 * 截取之后的机构名称
	 */
	private String organizationNameSub;

	/**
	 * 登记号
	 */
	private String registrationNumber;
	/**
	 * 机构地址
	 */
	private String organizationAddress;
	/**
	 * 电话号码
	 */
	private String phoneNumber;
	/**
	 * 登记发证机关
	 */
	private String registerAndPut;
	/**
	 * 行政区划
	 */
	private String administrativeRegion;
	/**
	 * 社康分类
	 */
	private String communityFication;
	/**
	 * 举办性质
	 */
	private String holdingNature;
	/**
	 * 医疗科目名称
	 */
	private String treatmentSubjectName;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 预留备用字段
	 */
	private String aux1;
	private String aux2;
	private String aux3;

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

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getOrganizationAddress() {
		return organizationAddress;
	}

	public void setOrganizationAddress(String organizationAddress) {
		this.organizationAddress = organizationAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRegisterAndPut() {
		return registerAndPut;
	}

	public void setRegisterAndPut(String registerAndPut) {
		this.registerAndPut = registerAndPut;
	}

	public String getAdministrativeRegion() {
		return administrativeRegion;
	}

	public void setAdministrativeRegion(String administrativeRegion) {
		this.administrativeRegion = administrativeRegion;
	}

	public String getCommunityFication() {
		return communityFication;
	}

	public void setCommunityFication(String communityFication) {
		this.communityFication = communityFication;
	}

	public String getHoldingNature() {
		return holdingNature;
	}

	public void setHoldingNature(String holdingNature) {
		this.holdingNature = holdingNature;
	}

	public String getTreatmentSubjectName() {
		return treatmentSubjectName;
	}

	public void setTreatmentSubjectName(String treatmentSubjectName) {
		this.treatmentSubjectName = treatmentSubjectName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
