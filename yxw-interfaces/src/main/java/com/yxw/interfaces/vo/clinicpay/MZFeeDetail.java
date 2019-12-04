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
 * 诊疗付费-->门诊待缴费记录明细
 * @Package: com.yxw.interfaces.entity.clinicpay
 * @ClassName: MZFeeDetail
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MZFeeDetail extends Reserve implements Serializable {

	private static final long serialVersionUID = 3630181107785529432L;
	/**
	 * 项目日期,格式：yyyy-MM-dd HH:mm:ss
	 */
	private String itemTime;
	/**
	 * 项目编号,项目的标识符，没有则空
	 */
	private String itemId;
	/**
	 * 项目名称
	 */
	private String itemName;
	/**
	 * 项目类别/费别,如：药费、检查费、材料费
	 */
	private String itemType;
	/**
	 * 单位
	 */
	private String itemUnit;
	/**
	 * 单价,单位：分
	 */
	private String itemPrice;
	/**
	 * 规格
	 */
	private String itemSpec;
	/**
	 * 数量
	 */
	private String itemNumber;
	/**
	 * 项目价格合计,单位：分，等于：单价*数量
	 */
	private String itemTotalFee;
	/**
	 * 开出科室
	 */
	private String deptName;
	/**
	 * 开药医生代码
	 */
	private String doctorCode;
	/**
	 * 开药医生姓名
	 */
	private String doctorName;
	
	/**
	 * 社保统一药品或诊疗项目编码
	 */
	private String socialInsuranceItemId;
	
	/**
	 * 医保结算项目代码
	 */
	private String medicareItemId;
	
	
	/**
	 * 医院ID
	 */
	private String unit_id;

	/**
	 * 医院名称
	 */
	private String unit_name;

	/**
	 * 分院ID
	 */
	private String unit_branch_id;
	
	/**
	 * 开单科室编号
	 */
	private String department_no;
	
	/**
	 * 开单科室名称
	 */
	private String department_na;
	
	/**
	 * 处方单号
	 */
	private String prescription_no;
	
	/**
	 * 处方名称
	 */
	private String prescription_na;
	
	/**
	 * 开单时间
	 */
	private String consult_date;
	
	/**
	 * 开单医生姓名
	 */
	private String doctor_name;
	
	/**
	 * 就诊人姓名
	 */
	private String patient_name;
	
	/**
	 * 就诊人性别
	 */
	private String patient_sex;
	
	/**
	 * 就诊人年龄
	 */
	private String patient_age;
	
	public MZFeeDetail() {
		super();
	}

	/**
	 * @param itemTime
	 * @param itemId
	 * @param itemName
	 * @param itemType
	 * @param itemUnit
	 * @param itemPrice
	 * @param itemSpec
	 * @param itemNumber
	 * @param itemTotalFee
	 * @param deptName
	 * @param doctorCode
	 * @param doctorName
	 */
	public MZFeeDetail(String itemTime, String itemId, String itemName, String itemType, String itemUnit, String itemPrice, String itemSpec,
			String itemNumber, String itemTotalFee, String deptName, String doctorCode, String doctorName) {
		super();
		this.itemTime = itemTime;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemType = itemType;
		this.itemUnit = itemUnit;
		this.itemPrice = itemPrice;
		this.itemSpec = itemSpec;
		this.itemNumber = itemNumber;
		this.itemTotalFee = itemTotalFee;
		this.deptName = deptName;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
	}

	/**
	 * @return the itemTime
	 */
	public String getItemTime() {
		return itemTime;
	}

	/**
	 * @param itemTime the itemTime to set
	 */
	public void setItemTime(String itemTime) {
		this.itemTime = itemTime;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return the itemUnit
	 */
	public String getItemUnit() {
		return itemUnit;
	}

	/**
	 * @param itemUnit the itemUnit to set
	 */
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	/**
	 * @return the itemPrice
	 */
	public String getItemPrice() {
		return itemPrice;
	}

	/**
	 * @param itemPrice the itemPrice to set
	 */
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	/**
	 * @return the itemSpec
	 */
	public String getItemSpec() {
		return itemSpec;
	}

	/**
	 * @param itemSpec the itemSpec to set
	 */
	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	/**
	 * @return the itemNumber
	 */
	public String getItemNumber() {
		return itemNumber;
	}

	/**
	 * @param itemNumber the itemNumber to set
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * @return the itemTotalFee
	 */
	public String getItemTotalFee() {
		return itemTotalFee;
	}

	/**
	 * @param itemTotalFee the itemTotalFee to set
	 */
	public void setItemTotalFee(String itemTotalFee) {
		this.itemTotalFee = itemTotalFee;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the doctorCode
	 */
	public String getDoctorCode() {
		return doctorCode;
	}

	/**
	 * @param doctorCode the doctorCode to set
	 */
	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	/**
	 * @return the doctorName
	 */
	public String getDoctorName() {
		return doctorName;
	}

	/**
	 * @param doctorName the doctorName to set
	 */
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getSocialInsuranceItemId() {
		return socialInsuranceItemId;
	}

	public void setSocialInsuranceItemId(String socialInsuranceItemId) {
		this.socialInsuranceItemId = socialInsuranceItemId;
	}

	public String getMedicareItemId() {
		return medicareItemId;
	}

	public void setMedicareItemId(String medicareItemId) {
		this.medicareItemId = medicareItemId;
	}

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getUnit_branch_id() {
		return unit_branch_id;
	}

	public void setUnit_branch_id(String unit_branch_id) {
		this.unit_branch_id = unit_branch_id;
	}

	public String getDepartment_no() {
		return department_no;
	}

	public void setDepartment_no(String department_no) {
		this.department_no = department_no;
	}

	public String getDepartment_na() {
		return department_na;
	}

	public void setDepartment_na(String department_na) {
		this.department_na = department_na;
	}

	public String getPrescription_no() {
		return prescription_no;
	}

	public void setPrescription_no(String prescription_no) {
		this.prescription_no = prescription_no;
	}

	public String getPrescription_na() {
		return prescription_na;
	}

	public void setPrescription_na(String prescription_na) {
		this.prescription_na = prescription_na;
	}

	public String getConsult_date() {
		return consult_date;
	}

	public void setConsult_date(String consult_date) {
		this.consult_date = consult_date;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getPatient_sex() {
		return patient_sex;
	}

	public void setPatient_sex(String patient_sex) {
		this.patient_sex = patient_sex;
	}

	public String getPatient_age() {
		return patient_age;
	}

	public void setPatient_age(String patient_age) {
		this.patient_age = patient_age;
	}

}
