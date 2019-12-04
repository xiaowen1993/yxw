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
 * 住院患者服务-->住院记录
 * @Package: com.yxw.interfaces.entity.hospitalized
 * @ClassName: BedRecord
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By: 范建明
 * @modify Date: 2015年12月24日
 * @Why&What is modify: 增加医院代码
 * @Version: 1.0
 */
public class BedRecord extends Reserve implements Serializable {

	private static final long serialVersionUID = 2175712873853133648L;

	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
	 */
	private String branchCode;
	/**
	 * 住院记录ID,用来唯一标识一次住院记录
	 */
	private String patientId;
	/**
	 * 住院号
	 */
	private String admissionNo;
	/**
	 * 住院次数
	 */
	private String inTime;
	/**
	 * 主管医生代码
	 */
	private String doctorCode;
	/**
	 * 主管医生名称
	 */
	private String doctorName;
	/**
	 * 出院科室
	 */
	private String deptName;
	/**
	 * 病床号
	 */
	private String bedNo;
	/**
	 * 住院状态,见HospitalizedStatusType
	 * @see com.yxw.interfaces.constants.HospitalizedStatusType
	 */
	private String status;
	/**
	 * 是否可清账,见SettleType
	 * @see com.yxw.interfaces.constants.SettleType
	 */
	private String canSettle;
	/**
	 * 入院日期,格式：YYYY-MM-DD
	 */
	private String inDate;
	/**
	 * 出院日期,格式：YYYY-MM-DD
	 */
	private String outDate;
	/**
	 * 住院总费用
	 */
	private String totalFee;
	/**
	 * 其他说明
	 */
	private String otherDesc;

	public BedRecord() {
	}

	public BedRecord(String branchCode, String patientId, String admissionNo, String inTime, String doctorCode, String doctorName, String deptName, String bedNo,
			String status, String canSettle, String inDate, String outDate, String totalFee, String otherDesc) {
		super();
		this.branchCode = branchCode;
		this.patientId = patientId;
		this.admissionNo = admissionNo;
		this.inTime = inTime;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.deptName = deptName;
		this.bedNo = bedNo;
		this.status = status;
		this.canSettle = canSettle;
		this.inDate = inDate;
		this.outDate = outDate;
		this.totalFee = totalFee;
		this.otherDesc = otherDesc;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getOtherDesc() {
		return otherDesc;
	}

	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}

	public String getCanSettle() {
		return canSettle;
	}

	public void setCanSettle(String canSettle) {
		this.canSettle = canSettle;
	}
	
}
