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
 * 诊疗付费-->门诊缴费订单支付请求参数封装
 * @Package: com.yxw.interfaces.entity.clinicpay
 * @ClassName: AckPayOrderRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class AckPayOrderRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -590028151593572789L;
	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
	 */
	private String branchCode;
	/**
	 * 诊疗卡类型,见CardType
	 * @see com.yxw.interfaces.constants.CardType
	 */
	private String patCardType;
	/**
	 * 诊疗卡号
	 */
	private String patCardNo;
	/**
	 * 缴费项列表,可以是1..n个门诊流水号或就诊登记号，中间用英文逗号隔开
	 */
	private String mzFeeIdList;
	/**
	 * 付款金额
	 */
	private String payAmout;
	/**
	 * 总金额
	 */
	private String totalAmout;
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
	 * 付款时间,格式：yyyy-MM-dd HH:mm:ss
	 */
	private String payTime;
	/**
	 * 处方类型
	 */
	private String recipeType;
	/**
	 * 处方ID号
	 */
	private String recipeId;
	/**
	 * 结算方式类型
	 */
	private String payType;
	/**
	 * 最大处方号
	 */
	private String mzBillId;
	/**
	 * 社保结算单据号
	 */
	private String SSBillNumber;
	/**
	 * 医保支付结果串
	 */
	private String SSItems;

	public AckPayOrderRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param patCardType
	 * @param patCardNo
	 * @param mzFeeIdList
	 * @param payAmout
	 * @param totalAmout
	 * @param psOrdNum
	 * @param agtOrdNum
	 * @param agtCode
	 * @param payMode
	 * @param payTime
	 * @param channelType
	 */
	public AckPayOrderRequest(String branchCode, String patCardType, String patCardNo, String mzFeeIdList, String payAmout, String totalAmout,
			String psOrdNum, String agtOrdNum, String agtCode, String payMode, String payTime) {
		super();
		this.branchCode = branchCode;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.mzFeeIdList = mzFeeIdList;
		this.payAmout = payAmout;
		this.totalAmout = totalAmout;
		this.psOrdNum = psOrdNum;
		this.agtOrdNum = agtOrdNum;
		this.agtCode = agtCode;
		this.payMode = payMode;
		this.payTime = payTime;
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
	 * @return the mzFeeIdList
	 */
	public String getMzFeeIdList() {
		return mzFeeIdList;
	}

	/**
	 * @param mzFeeIdList the mzFeeIdList to set
	 */
	public void setMzFeeIdList(String mzFeeIdList) {
		this.mzFeeIdList = mzFeeIdList;
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

	/**
	 * @return the agtOrdNum
	 */
	public String getAgtOrdNum() {
		return agtOrdNum;
	}

	/**
	 * @param agtOrdNum the agtOrdNum to set
	 */
	public void setAgtOrdNum(String agtOrdNum) {
		this.agtOrdNum = agtOrdNum;
	}

	/**
	 * @return the agtCode
	 */
	public String getAgtCode() {
		return agtCode;
	}

	/**
	 * @param agtCode the agtCode to set
	 */
	public void setAgtCode(String agtCode) {
		this.agtCode = agtCode;
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

	public String getRecipeType() {
		return recipeType;
	}

	public void setRecipeType(String recipeType) {
		this.recipeType = recipeType;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getMzBillId() {
		return mzBillId;
	}

	public void setMzBillId(String mzBillId) {
		this.mzBillId = mzBillId;
	}

	public String getSSBillNumber() {
		return SSBillNumber;
	}

	public void setSSBillNumber(String sSBillNumber) {
		SSBillNumber = sSBillNumber;
	}

	public String getSSItems() {
		return SSItems;
	}

	public void setSSItems(String sSItems) {
		SSItems = sSItems;
	}

	
}
