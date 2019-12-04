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
 * 诊疗付费-->门诊已缴费记录请求参数封装
 * @Package: com.yxw.interfaces.entity.clinicpay
 * @ClassName: PayRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PayFeeRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -7343170069852500890L;
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

	public PayFeeRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param patCardType
	 * @param patCardNo
	 * @param payMode
	 * @param beginDate
	 * @param endDate
	 * @param psOrdNum
	 */
	public PayFeeRequest(String branchCode, String patCardType, String patCardNo, String payMode, String beginDate, String endDate, String psOrdNum) {
		super();
		this.branchCode = branchCode;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.payMode = payMode;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.psOrdNum = psOrdNum;
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
	 * @return the patCardType
	 */
	public String getPatCardType() {
		return patCardType;
	}

	/**
	 * @param patCardType the patCardType to set
	 */
	public void setPatCardType(String patCardType) {
		this.patCardType = patCardType;
	}

	/**
	 * @return the patCardNo
	 */
	public String getPatCardNo() {
		return patCardNo;
	}

	/**
	 * @param patCardNo the patCardNo to set
	 */
	public void setPatCardNo(String patCardNo) {
		this.patCardNo = patCardNo;
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
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the psOrdNum
	 */
	public String getPsOrdNum() {
		return psOrdNum;
	}

	/**
	 * @param psOrdNum the psOrdNum to set
	 */
	public void setPsOrdNum(String psOrdNum) {
		this.psOrdNum = psOrdNum;
	}

}
