/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.datastorage.reports.entity;

import com.yxw.easyhealth.biz.datastorage.base.entity.DataBaseEntity;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.entity
 * @ClassName: DataExamine
 * @Statement: <p>检查入库（包含列表和详细）</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DataExamine extends DataBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1388719567506744240L;

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

	/**
	 * 检查部位
	 */
	private String checkPart;
	/**
	 * 检查方法
	 */
	private String checkMethod;
	/**
	 * 检查所见
	 */
	private String checkSituation;
	/**
	 * 诊断意见
	 */
	private String option;
	/**
	 * 医嘱项
	 */
	private String advice;

	public DataExamine() {
		super();
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getCheckTypeName() {
		return checkTypeName;
	}

	public void setCheckTypeName(String checkTypeName) {
		this.checkTypeName = checkTypeName;
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

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getOrderDoctor() {
		return orderDoctor;
	}

	public void setOrderDoctor(String orderDoctor) {
		this.orderDoctor = orderDoctor;
	}

	public String getOrderDoctorCode() {
		return orderDoctorCode;
	}

	public void setOrderDoctorCode(String orderDoctorCode) {
		this.orderDoctorCode = orderDoctorCode;
	}

	public String getOrderDept() {
		return orderDept;
	}

	public void setOrderDept(String orderDept) {
		this.orderDept = orderDept;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	public String getCheckPart() {
		return checkPart;
	}

	public void setCheckPart(String checkPart) {
		this.checkPart = checkPart;
	}

	public String getCheckMethod() {
		return checkMethod;
	}

	public void setCheckMethod(String checkMethod) {
		this.checkMethod = checkMethod;
	}

	public String getCheckSituation() {
		return checkSituation;
	}

	public void setCheckSituation(String checkSituation) {
		this.checkSituation = checkSituation;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

}
