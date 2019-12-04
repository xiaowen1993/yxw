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
 * 诊疗付费-->门诊待缴费记录
 * @Package: com.yxw.interfaces.entity.clinicpay
 * @ClassName: MZFee
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MZFee extends Reserve implements Serializable {

	private static final long serialVersionUID = -8575882679552163808L;

	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
	 */
	private String branchCode;
	/**
	 * 缴费项唯一标识,门诊流水号，就诊登记号等，并非处方号,用来唯一标识一笔缴费(包含1..n个处方或检查单)
	 */
	private String mzFeeId;
	/**
	 * 生成时间,格式：yyyy-MM-dd HH:mm:ss
	 */
	private String time;
	/**
	 * 接诊科室
	 */
	private String deptName;
	/**
	 * 接诊医生姓名
	 */
	private String doctorName;
	/**
	 * 结算方式类型,自费、医保、公费、农村合作医疗等，必须返回中文名称…以医院返回为准，当为空时显示自费
	 */
	private String payType;
	/**
	 * 自费金额,自费金额 = 总金额 – 统筹金额,若不支持统筹结算，则返回总金额
	 */
	private String payAmout;
	/**
	 * 统筹金额,统筹金额 = 总金额 – 自费金额,结算方式类型为“自费”时，为空
	 */
	private String medicareAmout;
	/**
	 * 总金额,总金额 = 自费金额 + 统筹金额
	 */
	private String totalAmout;

	public MZFee() {
		super();
	}

	/**
	 * @param branchCode
	 * @param mzFeeId
	 * @param time
	 * @param deptName
	 * @param doctorName
	 * @param payType
	 * @param payAmout
	 * @param medicareAmout
	 * @param totalAmout
	 */
	public MZFee(String branchCode, String mzFeeId, String time, String deptName, String doctorName, String payType, String payAmout,
			String medicareAmout, String totalAmout) {
		super();
		this.branchCode = branchCode;
		this.mzFeeId = mzFeeId;
		this.time = time;
		this.deptName = deptName;
		this.doctorName = doctorName;
		this.payType = payType;
		this.payAmout = payAmout;
		this.medicareAmout = medicareAmout;
		this.totalAmout = totalAmout;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the mzFeeId
	 */
	public String getMzFeeId() {
		return mzFeeId;
	}

	/**
	 * @param mzFeeId the mzFeeId to set
	 */
	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
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

	/**
	 * @return the payType
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * @param payType the payType to set
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * @return the payAmout
	 */
	public String getPayAmout() {
		return payAmout;
	}

	/**
	 * @param payAmout the payAmout to set
	 */
	public void setPayAmout(String payAmout) {
		this.payAmout = payAmout;
	}

	/**
	 * @return the medicareAmout
	 */
	public String getMedicareAmout() {
		return medicareAmout;
	}

	/**
	 * @param medicareAmout the medicareAmout to set
	 */
	public void setMedicareAmout(String medicareAmout) {
		this.medicareAmout = medicareAmout;
	}

	/**
	 * @return the totalAmout
	 */
	public String getTotalAmout() {
		return totalAmout;
	}

	/**
	 * @param totalAmout the totalAmout to set
	 */
	public void setTotalAmout(String totalAmout) {
		this.totalAmout = totalAmout;
	}

}
