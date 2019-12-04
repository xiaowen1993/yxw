package com.yxw.commons.entity.mobile.biz.register;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.yxw.base.entity.BaseEntity;

public class HadRegDoctor extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3047036965936249521L;

	/**
	 * 登录账号
	 */
	private String openId;

	/**
	 * 
	 */
	private String appCode;

	private String appId;

	/**
	 * 医院id
	 */
	private String hospitalId;

	/**
	 * 医院code
	 */
	private String hospitalCode;

	/**
	 * 医院名称
	 */
	private String hospitalName;

	/**
	 * 分院id
	 */
	private String branchHospitalId;

	/**
	 * 分院code
	 */
	private String branchHospitalCode;

	/**
	 * 科室code
	 */
	private String deptCode;

	/**
	 * 科室名称
	 */
	private String deptName;

	/**
	 * 医生code
	 */
	private String doctorCode;

	/**
	 * 医生名称
	 */
	private String doctorName;

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 挂号类型（add by dfw. 健康易，我的医生需要区分当班、预约）
	 */
	private Integer regType;

	public HadRegDoctor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HadRegDoctor(String id, String openId, String appCode, String appId, String hospitalId, String hospitalCode, String hospitalName,
			String branchHospitalId, String branchHospitalCode, String deptCode, String deptName, String doctorCode, String doctorName) {
		super();
		this.id = id;
		this.openId = openId;
		this.appCode = appCode;
		this.appId = appId;
		this.hospitalId = hospitalId;
		this.hospitalCode = hospitalCode;
		this.hospitalName = hospitalName;
		this.branchHospitalId = branchHospitalId;
		this.branchHospitalCode = branchHospitalCode;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
	}

	public HadRegDoctor(RegisterRecord record) {
		BeanUtils.copyProperties(record, this);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId == null ? null : openId.trim();
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode == null ? null : appCode.trim();
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId == null ? null : appId.trim();
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId == null ? null : hospitalId.trim();
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode == null ? null : hospitalCode.trim();
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName == null ? null : hospitalName.trim();
	}

	public String getBranchHospitalId() {
		return branchHospitalId;
	}

	public void setBranchHospitalId(String branchHospitalId) {
		this.branchHospitalId = branchHospitalId == null ? null : branchHospitalId.trim();
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode == null ? null : branchHospitalCode.trim();
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode == null ? null : deptCode.trim();
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName == null ? null : deptName.trim();
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode == null ? null : doctorCode.trim();
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName == null ? null : doctorName.trim();
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}
}