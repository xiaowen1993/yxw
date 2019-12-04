package com.yxw.commons.dto.outside;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 *  商保缴费记录查询结果
 * @author Administrator
 *
 */
public class PaidMZDetailReuslt extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2517440881450184042L;

	/**
	 * 姓名
	 */
	private String patName;

	/**
	 * 性别
	 */
	private String patSex;

	/**
	 * 证件类型
	 */
	private String patIdType;

	/**
	 * 出生日期
	 */
	private String birthDay;

	/**
	 * 证件号码
	 */
	private String patIdNo;

	/**
	 * 缴费项唯一标识
	 */
	private String mzFeeId;

	/**
	 * 就诊时间
	 */
	private String time;

	/**
	 * 手机
	 */
	private String patMobile;

	/**
	 * 地址
	 */
	private String patAddress;

	/**
	 * 就诊科室
	 */
	private String deptName;

	/**
	 * 主治医生
	 */
	private String doctorName;

	/**
	 * 是否已理赔，1：已理赔 0：未理赔
	 */
	private Integer isClaim;

	/**
	 * 主诊断疾病
	 */
	private String mainDiagnosisCode;

	/**
	 * 主诊断疾病名称
	 */
	private String mainDiagnosisName;

	//###########以下属性为公共属性################

	private String appId;

	private String appCode;

	private String openId;

	/**
	 * 医院代码
	 */
	private String hospitalCode;

	/**
	 * 缴费单号
	 */
	private String orderNo;

	/**
	 * 理赔原因
	 */
	private String claimType;

	public String getPatName() {
		return patName;
	}

	public void setPatName(String patName) {
		this.patName = patName;
	}

	public String getPatSex() {
		return patSex;
	}

	public void setPatSex(String patSex) {
		this.patSex = patSex;
	}

	public String getPatIdType() {
		return patIdType;
	}

	public void setPatIdType(String patIdType) {
		this.patIdType = patIdType;
	}

	public String getPatIdNo() {
		return patIdNo;
	}

	public void setPatIdNo(String patIdNo) {
		this.patIdNo = patIdNo;
	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPatMobile() {
		return patMobile;
	}

	public void setPatMobile(String patMobile) {
		this.patMobile = patMobile;
	}

	public String getPatAddress() {
		return patAddress;
	}

	public void setPatAddress(String patAddress) {
		this.patAddress = patAddress;
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

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public Integer getIsClaim() {
		return isClaim;
	}

	public void setIsClaim(Integer isClaim) {
		this.isClaim = isClaim;
	}

	public String getMainDiagnosisCode() {
		return mainDiagnosisCode;
	}

	public void setMainDiagnosisCode(String mainDiagnosisCode) {
		this.mainDiagnosisCode = mainDiagnosisCode;
	}

	public String getMainDiagnosisName() {
		return mainDiagnosisName;
	}

	public void setMainDiagnosisName(String mainDiagnosisName) {
		this.mainDiagnosisName = mainDiagnosisName;
	}

}
