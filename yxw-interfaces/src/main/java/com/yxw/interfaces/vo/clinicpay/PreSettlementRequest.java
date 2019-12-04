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
 * 门诊预结算参数封装
 */
public class PreSettlementRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -867761131886799057L;
	
	/**
	 * 医院代码
	 */
	private String branchCode;
	
	/**
	 * 诊疗卡类型
	 */
	private String patCardType;
	
	/**
	 * 诊疗卡号
	 */
	private String patCardNo;
	
	/**
	 * 缴费项唯一标识
	 */
	private String mzFeeId;
	
	/**
	 * 门诊业务单据号
	 */
	private String mzBillId;
	
	/**
	 * 接诊科室代码
	 */
	private String deptCode;
	
	/**
	 * 接诊医生代码
	 */
	private String doctorCode;

	public PreSettlementRequest() {
		super();
	}

	public PreSettlementRequest(String branchCode, String patCardType,
			String patCardNo, String mzFeeId, String mzBillId, String deptCode,
			String doctorCode) {
		super();
		this.branchCode = branchCode;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.mzFeeId = mzFeeId;
		this.mzBillId = mzBillId;
		this.deptCode = deptCode;
		this.doctorCode = doctorCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getPatCardType() {
		return patCardType;
	}

	public void setPatCardType(String patCardType) {
		this.patCardType = patCardType;
	}

	public String getPatCardNo() {
		return patCardNo;
	}

	public void setPatCardNo(String patCardNo) {
		this.patCardNo = patCardNo;
	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	public String getMzBillId() {
		return mzBillId;
	}

	public void setMzBillId(String mzBillId) {
		this.mzBillId = mzBillId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}


}
