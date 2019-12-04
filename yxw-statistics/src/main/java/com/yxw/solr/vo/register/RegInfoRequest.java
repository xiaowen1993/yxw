package com.yxw.solr.vo.register;

import com.yxw.solr.vo.BizInfoRequest;

public class RegInfoRequest extends BizInfoRequest {

	private static final long serialVersionUID = -3269257083543929871L;
	
	/**
	 * 挂号类型：1预约2当班
	 */
	private Integer regType = -1;
	
	/**
	 * 业务状态
	 */
	private Integer bizStatus = -1;
	
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

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public Integer getBizStatus() {
		return bizStatus;
	}

	public void setRegStatus(Integer bizStatus) {
		this.bizStatus = bizStatus;
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

	public String getPatientMobile() {
		return patientMobile;
	}

	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}
}
