/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.clinicpay;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 门诊预结算 返回实体
 */
public class PreSettlement extends Reserve implements Serializable {

	private static final long serialVersionUID = -1785850175905963170L;

	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
	 */
	private String mzFeeId;
	
	/**
	 * 门诊业务单据号
	 */
	private String mzBillId;

	/**
	 * 医保流水号
	 */
	private String sSFeeNo;

	/**
	 * 医保单据号
	 */
	private String sSBillNo;

	/**
	 * 退款流水号
	 */
	private String cancelSerialNo;

	/**
	 * 退款单据号
	 */
	private String cancelBillNo;

	/**
	 * 生成时间
	 */
	private String time;

	/**
	 * 处方张数
	 */
	private String recipeCount;

	/**
	 * 门诊类别
	 */
	private String mzCategory;

	/**
	 * 具体类别
	 */
	private String specificCategory;

	/**
	 * 诊断
	 */
	private String diagnosis;

	/**
	 * 诊断描述
	 */
	private String diagnosisDetail;

	/**
	 * 接诊科室代码
	 */
	private String deptCode;

	/**
	 * 接诊科室
	 */
	private String deptName;

	/**
	 * 接诊医生代码
	 */
	private String doctorCode;

	/**
	 * 接诊医生姓名
	 */
	private String doctorName;

	/**
	 * 接诊医生电话
	 */
	private String doctorTelephone;

	/**
	 * 结算方式类型
	 */
	private String payType;

	/**
	 * 自费金额
	 */
	private String payAmout;

	/**
	 * 个人账户结算金额
	 */
	private String accountAmout;

	/**
	 * 统筹基金结算金额
	 */
	private String medicareAmount;

	/**
	 * 记账合计
	 */
	private String insuranceAmout;

	/**
	 * 总金额
	 */
	private String totalAmout;


	public PreSettlement() {
		super();
	}


	public PreSettlement(String mzFeeId, String mzBillId, String sSFeeNo,
			String sSBillNo, String cancelSerialNo, String cancelBillNo,
			String time, String recipeCount, String mzCategory,
			String specificCategory, String diagnosis, String diagnosisDetail,
			String deptCode, String deptName, String doctorCode,
			String doctorName, String doctorTelephone, String payType,
			String payAmout, String accountAmout, String medicareAmount,
			String insuranceAmout, String totalAmout) {
		super();
		this.mzFeeId = mzFeeId;
		this.mzBillId = mzBillId;
		this.sSFeeNo = sSFeeNo;
		this.sSBillNo = sSBillNo;
		this.cancelSerialNo = cancelSerialNo;
		this.cancelBillNo = cancelBillNo;
		this.time = time;
		this.recipeCount = recipeCount;
		this.mzCategory = mzCategory;
		this.specificCategory = specificCategory;
		this.diagnosis = diagnosis;
		this.diagnosisDetail = diagnosisDetail;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.doctorTelephone = doctorTelephone;
		this.payType = payType;
		this.payAmout = payAmout;
		this.accountAmout = accountAmout;
		this.medicareAmount = medicareAmount;
		this.insuranceAmout = insuranceAmout;
		this.totalAmout = totalAmout;
	}


	public String getMzFeeId() {
		return mzFeeId;
	}


	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}


	public String getMzBillId() {
		return mzBillId;
	}


	public void setMzBillId(String mzBillId) {
		this.mzBillId = mzBillId;
	}


	public String getsSFeeNo() {
		return sSFeeNo;
	}


	public void setsSFeeNo(String sSFeeNo) {
		this.sSFeeNo = sSFeeNo;
	}


	public String getsSBillNo() {
		return sSBillNo;
	}


	public void setsSBillNo(String sSBillNo) {
		this.sSBillNo = sSBillNo;
	}


	public String getCancelSerialNo() {
		return cancelSerialNo;
	}


	public void setCancelSerialNo(String cancelSerialNo) {
		this.cancelSerialNo = cancelSerialNo;
	}


	public String getCancelBillNo() {
		return cancelBillNo;
	}


	public void setCancelBillNo(String cancelBillNo) {
		this.cancelBillNo = cancelBillNo;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getRecipeCount() {
		return recipeCount;
	}


	public void setRecipeCount(String recipeCount) {
		this.recipeCount = recipeCount;
	}


	public String getMzCategory() {
		return mzCategory;
	}


	public void setMzCategory(String mzCategory) {
		this.mzCategory = mzCategory;
	}


	public String getSpecificCategory() {
		return specificCategory;
	}


	public void setSpecificCategory(String specificCategory) {
		this.specificCategory = specificCategory;
	}


	public String getDiagnosis() {
		return diagnosis;
	}


	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}


	public String getDiagnosisDetail() {
		return diagnosisDetail;
	}


	public void setDiagnosisDetail(String diagnosisDetail) {
		this.diagnosisDetail = diagnosisDetail;
	}


	public String getDeptCode() {
		return deptCode;
	}


	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}


	public String getDeptName() {
		return deptName;
	}


	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getDoctorCode() {
		return doctorCode;
	}


	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}


	public String getDoctorName() {
		return doctorName;
	}


	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}


	public String getDoctorTelephone() {
		return doctorTelephone;
	}


	public void setDoctorTelephone(String doctorTelephone) {
		this.doctorTelephone = doctorTelephone;
	}


	public String getPayType() {
		return payType;
	}


	public void setPayType(String payType) {
		this.payType = payType;
	}


	public String getPayAmout() {
		return payAmout;
	}


	public void setPayAmout(String payAmout) {
		this.payAmout = payAmout;
	}


	public String getAccountAmout() {
		return accountAmout;
	}


	public void setAccountAmout(String accountAmout) {
		this.accountAmout = accountAmout;
	}


	public String getMedicareAmount() {
		return medicareAmount;
	}


	public void setMedicareAmount(String medicareAmount) {
		this.medicareAmount = medicareAmount;
	}


	public String getInsuranceAmout() {
		return insuranceAmout;
	}


	public void setInsuranceAmout(String insuranceAmout) {
		this.insuranceAmout = insuranceAmout;
	}


	public String getTotalAmout() {
		return totalAmout;
	}


	public void setTotalAmout(String totalAmout) {
		this.totalAmout = totalAmout;
	}

}
