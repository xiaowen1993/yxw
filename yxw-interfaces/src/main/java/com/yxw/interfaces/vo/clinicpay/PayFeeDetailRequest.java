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
 * 诊疗付费-->门诊已缴费记录明细查询请求参数封装
 * @Package: com.yxw.interfaces.entity.clinicpay
 * @ClassName: PayFeeDetailRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PayFeeDetailRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -2943864914281173464L;
	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
	 */
	private String branchCode;
	/**
	 * 医院交易流水号,用来唯一标识一笔门诊缴费交易
	 */
	private String hisOrdNum;
	/**
	 * 缴费项唯一标识,门诊流水号，就诊登记号等，并非处方号,用来唯一标识一笔缴费(包含1..n个处方或检查单)
	 */
	private String mzFeeId;
	/**
	 * 收据号
	 */
	private String receiptNum;

	public PayFeeDetailRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param hisOrdNum
	 * @param mzFeeId
	 * @param receiptNum
	 */
	public PayFeeDetailRequest(String branchCode, String hisOrdNum, String mzFeeId, String receiptNum) {
		super();
		this.branchCode = branchCode;
		this.hisOrdNum = hisOrdNum;
		this.mzFeeId = mzFeeId;
		this.receiptNum = receiptNum;
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
