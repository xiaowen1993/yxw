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
 * 诊疗付费-->门诊已缴费记录
 * @Package: com.yxw.interfaces.entity.clinicpay
 * @ClassName: Pay
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PayFee extends Reserve implements Serializable {

	private static final long serialVersionUID = -1785850175905963170L;

	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
	 */
	private String branchCode;
	/**
	 * 缴费交易流水号,要求唯一，能标识单独的一笔门诊缴费订单
	 */
	private String hisOrdNum;
	/**
	 * 缴费项唯一标识,门诊流水号，就诊登记号等，并非处方号,用来唯一标识一笔缴费(包含1..n个处方或检查单)
	 */
	private String mzFeeId;
	/**
	 * 接诊科室
	 */
	private String deptName;
	/**
	 * 接诊医生姓名
	 */
	private String doctorName;
	/**
	 * 缴费金额
	 */
	private String payAmout;
	/**
	 * 缴费时间,格式：YYYY-MM-DD HH:mm:ss
	 */
	private String payTime;
	/**
	 * 支付方式,见PayPlatformType
	 * @see com.yxw.interfaces.constants.PayPlatformType
	 */
	private String payMode;
	/**
	 * 缴费状态,0：未支付,1：已支付,2：已退费
	 */
	private String payStatus;
	/**
	 * 收据号
	 */
	private String receiptNum;
	/**
	 * 条形码
	 */
	private String barCode;
	/**
	 * 医院返回的信息,返回取药、检查或治疗地点的导诊信息
	 */
	private String hisMessage;

	public PayFee() {
		super();
	}

	/**
	 * @param branchCode
	 * @param hisOrdNum
	 * @param mzFeeId
	 * @param deptName
	 * @param doctorName
	 * @param payAmout
	 * @param payTime
	 * @param payMode
	 * @param payStatus
	 * @param receiptNum
	 * @param barCode
	 * @param hisMessage
	 */
	public PayFee(String branchCode, String hisOrdNum, String mzFeeId, String deptName, String doctorName, String payAmout, String payTime,
			String payMode, String payStatus, String receiptNum, String barCode, String hisMessage) {
		super();
		this.branchCode = branchCode;
		this.hisOrdNum = hisOrdNum;
		this.mzFeeId = mzFeeId;
		this.deptName = deptName;
		this.doctorName = doctorName;
		this.payAmout = payAmout;
		this.payTime = payTime;
		this.payMode = payMode;
		this.payStatus = payStatus;
		this.receiptNum = receiptNum;
		this.barCode = barCode;
		this.hisMessage = hisMessage;
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
	 * @return the hisOrdNum
	 */
	public String getHisOrdNum() {
		return hisOrdNum;
	}

	/**
	 * @param hisOrdNum the hisOrdNum to set
	 */
	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
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
	 * @return the payTime
	 */
	public String getPayTime() {
		return payTime;
	}

	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	/**
	 * @return the payMode
	 */
	public String getPayMode() {
		return payMode;
	}

	/**
	 * @param payMode the payMode to set
	 */
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	/**
	 * @return the payStatus
	 */
	public String getPayStatus() {
		return payStatus;
	}

	/**
	 * @param payStatus the payStatus to set
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	/**
	 * @return the receiptNum
	 */
	public String getReceiptNum() {
		return receiptNum;
	}

	/**
	 * @param receiptNum the receiptNum to set
	 */
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	/**
	 * @return the barCode
	 */
	public String getBarCode() {
		return barCode;
	}

	/**
	 * @param barCode the barCode to set
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	/**
	 * @return the hisMessage
	 */
	public String getHisMessage() {
		return hisMessage;
	}

	/**
	 * @param hisMessage the hisMessage to set
	 */
	public void setHisMessage(String hisMessage) {
		this.hisMessage = hisMessage;
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

}
