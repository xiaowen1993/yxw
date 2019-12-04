package com.yxw.statistics.biz.vo;

import java.io.Serializable;

public class RegisterManagerVo implements Serializable {

	private static final long serialVersionUID = 3410042095650037434L;

	/**
	 * 医院代码
	 */
	protected String hospitalCode;
	
	/**
	 * 分院代码 -- -1 表示全部		---- 不必作为查询条件
	 */
	protected String branchCode;
	
	/**
	 * 平台 -- -1 表示全部，这个时候需要查询多个core
	 */
	protected Integer platform;
	
	/**
	 * 分页大小
	 */
	protected Integer pageSize;
	
	/**
	 * 分页序号
	 */
	protected Integer pageIndex;
	
	/**
	 * 挂号类型：1预约2当班
	 */
	private Integer regType;
	
	/**
	 * 业务状态
	 */
	private Integer bizStatus;
	
	/**
	 * 科室名称
	 */
	private String deptName;
	
	/**
	 * 医生名称
	 */
	private String doctorName;
	
	/**
	 * 患者姓名
	 */
	private String patientName;
	
	/**
	 * 卡号
	 */
	private String cardNo;
	
	/**
	 * 手机号码
	 */
	private String patientMobile;
	
	/**
	 * 挂号开始时间
	 */
	private String regBeginTime;
	
	/**
	 * 挂号结束时间
	 */
	private String regEndTime;
	
	/**
	 * 就诊开始时间
	 */
	private String visitBeginTime;
	
	/**
	 * 就诊结束时间
	 */
	private String visitEndTime;
	
	public String getHospitalCode() {
		return hospitalCode;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}


	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPatientMobile() {
		return patientMobile;
	}

	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}

	public String getRegBeginTime() {
		return regBeginTime;
	}

	public void setRegBeginTime(String regBeginTime) {
		this.regBeginTime = regBeginTime;
	}

	public String getRegEndTime() {
		return regEndTime;
	}

	public void setRegEndTime(String regEndTime) {
		this.regEndTime = regEndTime;
	}

	public String getVisitBeginTime() {
		return visitBeginTime;
	}

	public void setVisitBeginTime(String visitBeginTime) {
		this.visitBeginTime = visitBeginTime;
	}

	public String getVisitEndTime() {
		return visitEndTime;
	}

	public void setVisitEndTime(String visitEndTime) {
		this.visitEndTime = visitEndTime;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Integer getPlatform() {
		return platform == null ? -1 : platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getBizStatus() {
		return bizStatus;
	}

	public void setBizStatus(Integer bizStatus) {
		this.bizStatus = bizStatus;
	}

}
