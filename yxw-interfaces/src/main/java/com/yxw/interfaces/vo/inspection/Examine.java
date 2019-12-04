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
 * 检验/检查/体检报告查询-->检查结果列表
 * 
 * @Package: com.yxw.interfaces.entity.inspection
 * @ClassName: Examine
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
public class Examine extends Reserve implements Serializable {

	private static final long serialVersionUID = 6106617051002864670L;
	/**
	 * 分院code
	 * */
	private String branchCode;
	/**
	 * 检查ID,唯一标识某次检查
	 */
	private String checkId;
	/**
	 * 检查名称
	 */
	private String checkName;
	/**
	 * 报告类型
	 */
	private String checkType;
	/**
	 * 报告类型名称
	 */
	private String checkTypeName;
	/**
	 * 检查科室
	 */
	private String deptName;
	/**
	 * 检查医生
	 */
	private String doctorName;
	/**
	 * 检查时间,格式：YYYY-MM-DD HH:mm:ss
	 */
	private String checkTime;
	/**
	 * 开单医生姓名
	 */
	private String orderDoctor;
	/**
	 * 开单医生编码
	 */
	private String orderDoctorCode;
	/**
	 * 开单科室
	 */
	private String orderDept;
	/**
	 * 报告时间,格式：YYYY-MM-DD HH:mm:ss
	 */
	private String reportTime;
	/**
	 * 文件地址,报告图片url地址
	 */
	private String fileAddress;

	public Examine() {
		super();
	}

	/**
	 * @param checkId
	 * @param checkName
	 * @param checkType
	 * @param checkTypeName
	 * @param deptName
	 * @param doctorName
	 * @param checkTime
	 * @param orderDoctor
	 * @param orderDoctorCode
	 * @param orderDept
	 * @param reportTime
	 * @param fileAddress
	 */
	public Examine(String checkId, String checkName, String checkType, String checkTypeName, String deptName, String doctorName, String checkTime,
			String orderDoctor, String orderDoctorCode, String orderDept, String reportTime, String fileAddress, String branchCode) {
		super();
		this.checkId = checkId;
		this.checkName = checkName;
		this.checkType = checkType;
		this.checkTypeName = checkTypeName;
		this.deptName = deptName;
		this.doctorName = doctorName;
		this.checkTime = checkTime;
		this.orderDoctor = orderDoctor;
		this.orderDoctorCode = orderDoctorCode;
		this.orderDept = orderDept;
		this.reportTime = reportTime;
		this.fileAddress = fileAddress;
		this.branchCode = branchCode;
	}

	/**
	 * @return the checkId
	 */
	public String getCheckId() {
		return checkId;
	}

	/**
	 * @param checkId
	 *            the checkId to set
	 */
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	/**
	 * @return the checkName
	 */
	public String getCheckName() {
		return checkName;
	}

	/**
	 * @param checkName
	 *            the checkName to set
	 */
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	/**
	 * @return the checkType
	 */
	public String getCheckType() {
		return checkType;
	}

	/**
	 * @param checkType
	 *            the checkType to set
	 */
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	/**
	 * @return the checkTypeName
	 */
	public String getCheckTypeName() {
		return checkTypeName;
	}

	/**
	 * @param checkTypeName
	 *            the checkTypeName to set
	 */
	public void setCheckTypeName(String checkTypeName) {
		this.checkTypeName = checkTypeName;
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
	 * @return the checkTime
	 */
	public String getCheckTime() {
		return checkTime;
	}

	/**
	 * @param checkTime
	 *            the checkTime to set
	 */
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	/**
	 * @return the orderDoctor
	 */
	public String getOrderDoctor() {
		return orderDoctor;
	}

	/**
	 * @param orderDoctor
	 *            the orderDoctor to set
	 */
	public void setOrderDoctor(String orderDoctor) {
		this.orderDoctor = orderDoctor;
	}

	/**
	 * @return the orderDoctorCode
	 */
	public String getOrderDoctorCode() {
		return orderDoctorCode;
	}

	/**
	 * @param orderDoctorCode
	 *            the orderDoctorCode to set
	 */
	public void setOrderDoctorCode(String orderDoctorCode) {
		this.orderDoctorCode = orderDoctorCode;
	}

	/**
	 * @return the orderDept
	 */
	public String getOrderDept() {
		return orderDept;
	}

	/**
	 * @param orderDept
	 *            the orderDept to set
	 */
	public void setOrderDept(String orderDept) {
		this.orderDept = orderDept;
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
