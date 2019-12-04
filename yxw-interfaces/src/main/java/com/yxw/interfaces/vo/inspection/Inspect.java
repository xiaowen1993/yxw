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
package com.yxw.interfaces.vo.inspection;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 检验/检查/体检报告查询-->检验报告列表
 * 
 * @Package: com.yxw.interfaces.entity.inspection
 * @ClassName: Inspect
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 申午武
 * @Create Date: 2015年6月15日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Inspect extends Reserve implements Serializable {

	private static final long serialVersionUID = -2292666707411715920L;
	/**
	 * 分院code
	 * */
	private String branchCode;
	/**
	 * 检验ID
	 */
	private String inspectId;
	/**
	 * 检验名称
	 */
	private String inspectName;
	/**
	 * 送检科室
	 */
	private String deptName;
	/**
	 * 送检医生
	 */
	private String doctorName;
	/**
	 * 送检医生编码
	 */
	private String doctorCode;
	/**
	 * 送检时间,格式：YYYY-MM-DD HH:mm:ss
	 */
	private String inspectTime;
	/**
	 * 审核医生
	 */
	private String verifyDoctor;
	/**
	 * 审核时间,格式：YYYY-MM-DD HH:mm:ss
	 */
	private String verifyTime;
	/**
	 * 检验医生
	 */
	private String inspectDoctor;
	/**
	 * 检验时间,格式：YYYY-MM-DD HH:mm:ss
	 */
	private String provingTime;
	/**
	 * 报告时间,格式：YYYY-MM-DD HH:mm:ss
	 */
	private String reportTime;
	/**
	 * 文件地址,报告图片url地址
	 */
	private String fileAddress;

	public Inspect() {
		super();
	}

	/**
	 * @param inspectId
	 * @param inspectName
	 * @param deptName
	 * @param doctorName
	 * @param doctorCode
	 * @param inspectTime
	 * @param verifyDoctor
	 * @param verifyTime
	 * @param inspectDoctor
	 * @param inspectTIme2
	 * @param reportTime
	 * @param fileAddress
	 */
	public Inspect(String inspectId, String inspectName, String deptName, String doctorName, String doctorCode, String inspectTime,
			String verifyDoctor, String verifyTime, String inspectDoctor, String provingTime, String reportTime, String fileAddress, String branchCode) {
		super();
		this.inspectId = inspectId;
		this.inspectName = inspectName;
		this.deptName = deptName;
		this.doctorName = doctorName;
		this.doctorCode = doctorCode;
		this.inspectTime = inspectTime;
		this.verifyDoctor = verifyDoctor;
		this.verifyTime = verifyTime;
		this.inspectDoctor = inspectDoctor;
		this.provingTime = provingTime;
		this.reportTime = reportTime;
		this.fileAddress = fileAddress;
		this.branchCode = branchCode;
	}

	/**
	 * @return the inspectId
	 */
	public String getInspectId() {
		return inspectId;
	}

	/**
	 * @param inspectId
	 *            the inspectId to set
	 */
	public void setInspectId(String inspectId) {
		this.inspectId = inspectId;
	}

	/**
	 * @return the inspectName
	 */
	public String getInspectName() {
		return inspectName;
	}

	/**
	 * @param inspectName
	 *            the inspectName to set
	 */
	public void setInspectName(String inspectName) {
		this.inspectName = inspectName;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName
	 *            the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the doctorName
	 */
	public String getDoctorName() {
		return doctorName;
	}

	/**
	 * @param doctorName
	 *            the doctorName to set
	 */
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	/**
	 * @return the doctorCode
	 */
	public String getDoctorCode() {
		return doctorCode;
	}

	/**
	 * @param doctorCode
	 *            the doctorCode to set
	 */
	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	/**
	 * @return the inspectTime
	 */
	public String getInspectTime() {
		return inspectTime;
	}

	/**
	 * @param inspectTime
	 *            the inspectTime to set
	 */
	public void setInspectTime(String inspectTime) {
		this.inspectTime = inspectTime;
	}

	/**
	 * @return the verifyDoctor
	 */
	public String getVerifyDoctor() {
		return verifyDoctor;
	}

	/**
	 * @param verifyDoctor
	 *            the verifyDoctor to set
	 */
	public void setVerifyDoctor(String verifyDoctor) {
		this.verifyDoctor = verifyDoctor;
	}

	/**
	 * @return the verifyTime
	 */
	public String getVerifyTime() {
		return verifyTime;
	}

	/**
	 * @param verifyTime
	 *            the verifyTime to set
	 */
	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

	/**
	 * @return the inspectDoctor
	 */
	public String getInspectDoctor() {
		return inspectDoctor;
	}

	/**
	 * @param inspectDoctor
	 *            the inspectDoctor to set
	 */
	public void setInspectDoctor(String inspectDoctor) {
		this.inspectDoctor = inspectDoctor;
	}

	/**
	 * @return the inspectTIme
	 */
	public String getProvingTime() {
		return provingTime;
	}

	/**
	 * @param inspectTIme
	 *            the inspectTIme to set
	 */
	public void setProvingTime(String provingTime) {
		this.provingTime = provingTime;
	}

	/**
	 * @return the reportTime
	 */
	public String getReportTime() {
		return reportTime;
	}

	/**
	 * @param reportTime
	 *            the reportTime to set
	 */
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	/**
	 * @return the fileAddress
	 */
	public String getFileAddress() {
		return fileAddress;
	}

	/**
	 * @param fileAddress
	 *            the fileAddress to set
	 */
	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

}
