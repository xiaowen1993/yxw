/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年11月20日</p>
 *  <p> Created by 范建明</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.register;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * @Package: com.yxw.interfaces.vo.register
 * @ClassName: UnpaidRegRecord
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 范建明
 * @Create Date: 2015年11月20日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class UnpaidRegRecord extends Reserve implements Serializable {

	private static final long serialVersionUID = 7999006733352429089L;
	/**
	 * 医院代码,医院没有分院则传入空字符串；医院不存在分院时不允许为空
	 */
	private String branchCode;
	/**
	 * 预约挂号流水号,要求唯一，能标识单独的一笔预约挂号订单
	 */
	private String hisOrdNum;
	/**
	 * 科室代码
	 */
	private String deptCode;
	/**
	 * 科室名称
	 */
	private String deptName;
	/**
	 * 医生代码
	 */
	private String doctorCode;
	/**
	 * 医生名称
	 */
	private String doctorName;
	/**
	 * 挂号类型,1:预约,2:当天
	 */
	private String regType;
	/**
	 * 就诊日期 ,格式：YYYY-MM-DD
	 */
	private String bookDate;
	/**
	 * 时段,见TimeType
	 * 
	 * @see com.yxw.interfaces.constants.TimeType
	 */
	private String timeFlag;
	/**
	 * 预约/挂号开始时间,格式：HH24:MI
	 */
	private String beginTime;
	/**
	 * 预约/挂号结束时间,格式：HH24:MI
	 */
	private String endTime;
	/**
	 * 预约机构,中文名称
	 */
	private String regAgency;
	/**
	 * 优惠挂号费,单位：分,优惠费用
	 */
	private String realRegFee;
	/**
	 * 优惠诊疗费,单位：分,优惠费用
	 */
	private String realTreatFee;
	/**
	 * 订单状态
	 */
	private int regStatus;
	/**
	 * 支付状态
	 */
	private int payStatus;

	public UnpaidRegRecord() {
		super();
	}

	public UnpaidRegRecord(String branchCode, String hisOrdNum, String deptCode, String deptName, String doctorCode, String doctorName,
			String regType, String bookDate, String timeFlag, String beginTime, String endTime, String regAgency, String realRegFee,
			String realTreatFee, int regStatus, int payStatus) {
		super();
		this.branchCode = branchCode;
		this.hisOrdNum = hisOrdNum;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.regType = regType;
		this.bookDate = bookDate;
		this.timeFlag = timeFlag;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.regAgency = regAgency;
		this.realRegFee = realRegFee;
		this.realTreatFee = realTreatFee;
		this.regStatus = regStatus;
		this.payStatus = payStatus;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public String getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRegAgency() {
		return regAgency;
	}

	public void setRegAgency(String regAgency) {
		this.regAgency = regAgency;
	}

	public String getHisOrdNum() {
		return hisOrdNum;
	}

	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}

	public String getRealRegFee() {
		return realRegFee;
	}

	public void setRealRegFee(String realRegFee) {
		this.realRegFee = realRegFee;
	}

	public String getRealTreatFee() {
		return realTreatFee;
	}

	public void setRealTreatFee(String realTreatFee) {
		this.realTreatFee = realTreatFee;
	}

	public int getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(int regStatus) {
		this.regStatus = regStatus;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

}
