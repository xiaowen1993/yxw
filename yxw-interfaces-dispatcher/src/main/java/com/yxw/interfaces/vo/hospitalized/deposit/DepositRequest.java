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
 * 住院患者服务-->住院押金-->住院押金补缴记录查询请求参数封装
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
public class DepositRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 1780795285556205190L;

	/**
	 * 医院代码,医院没有分院,则值为空字符串;医院有分院,则值不允许为空字符串
	 */
	private String branchCode;
	/**
	 * 诊疗卡类型,见cardType
	 * @see com.yxw.interfaces.constants.CardType
	 */
	private String patCardType;
	/**
	 * 诊疗卡号
	 */
	private String patCardNo;
	/**
	 * 住院号
	 */
	private String admissionNo;
	/**
	 * 支付方式,见PlatformType
	 * @see com.yxw.interfaces.constants.PayPlatformType
	 */
	private String payMode;
	/**
	 * 开始时间,格式：YYYY-MM-DD
	 */
	private String beginDate;
	/**
	 * 结束时间,格式：YYYY-MM-DD
	 */
	private String endDate;
	/**
	 * 公众服务平台订单号,不为空时，查询指定公众服务平台订单号的记录；为空时，查询指定时间的所有预约记录。
	 */
	private String psOrdNum;
	
	public DepositRequest() {
		super();
	}

	public DepositRequest(String branchCode, String patCardType,
			String patCardNo, String admissionNo, String payMode,
			String beginDate, String endDate, String psOrdNum) {
		super();
		this.branchCode = branchCode;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.admissionNo = admissionNo;
		this.payMode = payMode;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.psOrdNum = psOrdNum;
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

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPsOrdNum() {
		return psOrdNum;
	}

	public void setPsOrdNum(String psOrdNum) {
		this.psOrdNum = psOrdNum;
	}
	
}
