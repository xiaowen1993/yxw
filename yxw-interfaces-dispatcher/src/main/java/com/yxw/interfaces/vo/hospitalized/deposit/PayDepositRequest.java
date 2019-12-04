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
package com.yxw.interfaces.vo.hospitalized.deposit;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 住院患者服务-->住院押金-->住院押金补缴支付请求参数封装
 * @Package: com.yxw.interfaces.entity.hospitalized.deposit
 * @ClassName: DepositRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PayDepositRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 1780795285556205190L;

	/**
	 * 医院代码
	 */
	private String branchCode;
	/**
	 * 住院记录ID
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
	 * 公众服务平台订单号,公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔交易的流水号
	 */
	private String psOrdNum;
	/**
	 * 收单机构流水号,对应收单机构（如财付通、支付宝、银联等机构）用于标识一笔支付交易的流水号
	 */
	private String agtOrdNum;
	/**
	 * 收单机构代码,财付通账号、银行卡账号等
	 */
	private String agtCode;
	/**
	 * 付款方式,见PayPlatformType
	 * @see com.yxw.interfaces.constants.PayPlatformType
	 */
	private String payMode;
	/**
	 * 付款金额
	 */
	private String payAmout;
	/**
	 * 付款时间,格式：yyyy-MM-dd HH:mm:ss
	 */
	private String payTime;
	
	public PayDepositRequest() {
		super();
	}

	public PayDepositRequest(String branchCode, String patientId,
			String admissionNo, String inTime, String psOrdNum,
			String agtOrdNum, String agtCode, String payMode, String payAmout,
			String payTime) {
		super();
		this.branchCode = branchCode;
		this.patientId = patientId;
		this.admissionNo = admissionNo;
		this.inTime = inTime;
		this.psOrdNum = psOrdNum;
		this.agtOrdNum = agtOrdNum;
		this.agtCode = agtCode;
		this.payMode = payMode;
		this.payAmout = payAmout;
		this.payTime = payTime;
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

	public String getPsOrdNum() {
		return psOrdNum;
	}

	public void setPsOrdNum(String psOrdNum) {
		this.psOrdNum = psOrdNum;
	}

	public String getAgtOrdNum() {
		return agtOrdNum;
	}

	public void setAgtOrdNum(String agtOrdNum) {
		this.agtOrdNum = agtOrdNum;
	}

	public String getAgtCode() {
		return agtCode;
	}

	public void setAgtCode(String agtCode) {
		this.agtCode = agtCode;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getPayAmout() {
		return payAmout;
	}

	public void setPayAmout(String payAmout) {
		this.payAmout = payAmout;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
}
