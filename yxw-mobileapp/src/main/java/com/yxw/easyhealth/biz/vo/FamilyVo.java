package com.yxw.easyhealth.biz.vo;

import com.yxw.commons.vo.cache.CommonParamsVo;

public class FamilyVo extends CommonParamsVo {

	private static final long serialVersionUID = -3904119049097601751L;

	/**
	 * 同步类型
	 */
	private Integer syncType;

	/**
	 * family的Id
	 */
	private String familyId;

	/**
	 * 就诊卡Id
	 */
	private String medicalcardId;

	/**
	 * 最大家人个数
	 */
	private Integer maxFamilyNum;

	/**
	 * 跳转URL
	 */
	private String forward;

	public Integer getSyncType() {
		return syncType;
	}

	public void setSyncType(Integer syncType) {
		this.syncType = syncType;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public Integer getMaxFamilyNum() {
		return maxFamilyNum;
	}

	public void setMaxFamilyNum(Integer maxFamilyNum) {
		this.maxFamilyNum = maxFamilyNum;
	}

	public String getMedicalcardId() {
		return medicalcardId;
	}

	public void setMedicalcardId(String medicalcardId) {
		this.medicalcardId = medicalcardId;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}
}
