package com.yxw.insurance.biz.dto.inside;

import java.io.Serializable;

/**
 *  商保处方接口请求参数集合
 * @author Administrator
 *
 */
public class ApplyPrescriptionParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2517440881450184042L;

	/**
	 * 缴费项唯一标识
	 */
	private String mzFeeId;

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
	 * 证件号码
	 */
	private String patIdNo;

	/**
	 * 处方明细唯一编号
	 */
	private String itemId;

	/**
	 * 社保医疗目录名称
	 */
	private String itemName;

	/**
	 * 单价
	 */
	private String itemPrice;

	/**
	 * 数量
	 */
	private String itemNumber;

	/**
	 * 项目类别/费别
	 */
	private String itemType;

	/**
	 * 项目价格合计
	 */
	private String itemTotalFee;

	/**
	 * 单位
	 */
	private String itemUnit;

	/**
	 * 规格
	 */
	private String itemSpec;

	/**
	 * 处方医生
	 */
	private String doctorName;

	/**
	 * 医生代码
	 */
	private String doctorCode;

	/**
	 * 项目时间
	 */
	private String itemTime;

	/**
	 * 出生时间
	 */
	private String birthDay;

	private String openId;

	/**
	 * 银行卡号
	 */
	private String bankCardNo;

	/**
	 * 银行名称
	 */
	private String bankName;

	/**
	 * 理赔原因
	 */
	private String claimType;

	/**
	 * 医院代码
	 */
	private String hospitalCode;

	/**
	 * 社保医疗目录编码
	 */
	private String medicalCatalogCode;

	/**
	 * 社保报销类别
	 */
	private String medicalInsurancePaymentType;

	/**
	 * 自费比例
	 */
	private String selfPaymentRatio;

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemTotalFee() {
		return itemTotalFee;
	}

	public void setItemTotalFee(String itemTotalFee) {
		this.itemTotalFee = itemTotalFee;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getItemTime() {
		return itemTime;
	}

	public void setItemTime(String itemTime) {
		this.itemTime = itemTime;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getMedicalCatalogCode() {
		return medicalCatalogCode;
	}

	public void setMedicalCatalogCode(String medicalCatalogCode) {
		this.medicalCatalogCode = medicalCatalogCode;
	}

	public String getMedicalInsurancePaymentType() {
		return medicalInsurancePaymentType;
	}

	public void setMedicalInsurancePaymentType(String medicalInsurancePaymentType) {
		this.medicalInsurancePaymentType = medicalInsurancePaymentType;
	}

	public String getSelfPaymentRatio() {
		return selfPaymentRatio;
	}

	public void setSelfPaymentRatio(String selfPaymentRatio) {
		this.selfPaymentRatio = selfPaymentRatio;
	}

}
