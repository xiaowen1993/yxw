/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年12月30日</p>
 *  <p> Created by 范建明</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.register;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->报到取号
 * 
 * @author 范建明
 * @version 1.0
 * @since 2015年12月30日
 */
public class TakeNo extends Reserve implements Serializable {

	private static final long serialVersionUID = -9003663006895126923L;
	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
	 */
	private String branchCode;
	/**
	 * 医院名称,医院没有分院则返回空字符串
	 */
	private String branchName;
	/**
	 * 预约挂号流水号,要求唯一，能标识单独的一笔预约挂号订单
	 */
	private String hisOrdNum;
	/**
	 * 科室名称
	 */
	private String deptName;
	
	/**
	 * 科室地址
	 */
	private String deptLocation;
	
	/**
	 * 医生姓名
	 */
	private String doctorName;
	
	/**
	 * 就诊顺序号
	 */
	private String serialNum;
	
	/**
	 * 当前看诊序号
	 */
	private String currentNum;
	
	/**
	 * 预计就诊时间
	 */
	private String visitTime;
	
	/**
	 * 当前时刻排在前面的人数
	 */
	private String frontNum;
	
	private String receiptNum;
	
	public TakeNo() {
		super();
	}

	public TakeNo(String branchCode, String branchName, String deptName,
			String deptLocation, String doctorName, String serialNum,
			String currentNum, String visitTime, String frontNum,
			String hisOrdNum,String receiptNum) {
		super();
		this.branchCode = branchCode;
		this.branchName = branchName;
		this.deptName = deptName;
		this.deptLocation = deptLocation;
		this.doctorName = doctorName;
		this.serialNum = serialNum;
		this.currentNum = currentNum;
		this.visitTime = visitTime;
		this.frontNum = frontNum;
		this.hisOrdNum = hisOrdNum;
		this.receiptNum = receiptNum;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getHisOrdNum() {
		return hisOrdNum;
	}

	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptLocation() {
		return deptLocation;
	}

	public void setDeptLocation(String deptLocation) {
		this.deptLocation = deptLocation;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(String currentNum) {
		this.currentNum = currentNum;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getFrontNum() {
		return frontNum;
	}

	public void setFrontNum(String frontNum) {
		this.frontNum = frontNum;
	}

	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

}
