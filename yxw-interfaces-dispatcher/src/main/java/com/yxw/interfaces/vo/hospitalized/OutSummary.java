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
package com.yxw.interfaces.vo.hospitalized;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 住院患者服务-->出院小结
 * @Package: com.yxw.interfaces.entity.hospitalized
 * @ClassName: OutSummary
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class OutSummary extends Reserve implements Serializable {

	private static final long serialVersionUID = -7654458391857636325L;

	/**
	 * 入院日期,格式：YYYY-MM-DD
	 */
	private String inDate;
	/**
	 * 手术日期,格式：YYYY-MM-DD
	 */
	private String surgeryDate;
	/**
	 * 手术名称
	 */
	private String surgeryName;
	/**
	 * 伤口愈合
	 */
	private String woundHealing;
	/**
	 * 出院日期,格式：YYYY-MM-DD
	 */
	private String outDate;
	/**
	 * 住院科室
	 */
	private String deptName;
	/**
	 * 入院诊断
	 */
	private String admissionDiagnosis;
	/**
	 * 出院诊断
	 */
	private String dischargeDiagnosis;
	/**
	 * 入院时情况
	 */
	private String admissionConditions;
	/**
	 * 入院经过
	 */
	private String onSituation;
	/**
	 * 出院时情况
	 */
	private String dischargeConditions;
	/**
	 * 出院医嘱
	 */
	private String dischargePrescription;
	
	public OutSummary() {
		super();
	}

	public OutSummary(String inDate, String surgeryDate, String surgeryName,
			String woundHealing, String outDate, String deptName,
			String admissionDiagnosis, String dischargeDiagnosis,
			String admissionConditions, String onSituation,
			String dischargeConditions, String dischargePrescription) {
		super();
		this.inDate = inDate;
		this.surgeryDate = surgeryDate;
		this.surgeryName = surgeryName;
		this.woundHealing = woundHealing;
		this.outDate = outDate;
		this.deptName = deptName;
		this.admissionDiagnosis = admissionDiagnosis;
		this.dischargeDiagnosis = dischargeDiagnosis;
		this.admissionConditions = admissionConditions;
		this.onSituation = onSituation;
		this.dischargeConditions = dischargeConditions;
		this.dischargePrescription = dischargePrescription;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public String getSurgeryDate() {
		return surgeryDate;
	}

	public void setSurgeryDate(String surgeryDate) {
		this.surgeryDate = surgeryDate;
	}

	public String getSurgeryName() {
		return surgeryName;
	}

	public void setSurgeryName(String surgeryName) {
		this.surgeryName = surgeryName;
	}

	public String getWoundHealing() {
		return woundHealing;
	}

	public void setWoundHealing(String woundHealing) {
		this.woundHealing = woundHealing;
	}

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAdmissionDiagnosis() {
		return admissionDiagnosis;
	}

	public void setAdmissionDiagnosis(String admissionDiagnosis) {
		this.admissionDiagnosis = admissionDiagnosis;
	}

	public String getDischargeDiagnosis() {
		return dischargeDiagnosis;
	}

	public void setDischargeDiagnosis(String dischargeDiagnosis) {
		this.dischargeDiagnosis = dischargeDiagnosis;
	}

	public String getAdmissionConditions() {
		return admissionConditions;
	}

	public void setAdmissionConditions(String admissionConditions) {
		this.admissionConditions = admissionConditions;
	}

	public String getOnSituation() {
		return onSituation;
	}

	public void setOnSituation(String onSituation) {
		this.onSituation = onSituation;
	}

	public String getDischargeConditions() {
		return dischargeConditions;
	}

	public void setDischargeConditions(String dischargeConditions) {
		this.dischargeConditions = dischargeConditions;
	}

	public String getDischargePrescription() {
		return dischargePrescription;
	}

	public void setDischargePrescription(String dischargePrescription) {
		this.dischargePrescription = dischargePrescription;
	}
	
}
