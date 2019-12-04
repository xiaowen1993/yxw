/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.dto.outside;

import java.io.Serializable;

import com.yxw.commons.dto.inside.PaidMZDetailCommParams;

/**
 * @Package: com.yxw.mobileapp.invoke.dto.outside
 * @ClassName: OrdersQueryResult
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月15日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class OrdersQueryResult extends PaidMZDetailCommParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3296753412751068027L;

	/**
	 * 订单生成时间
	 */
	private String tradeTime;

	/**
	 * 订单类型
	 * 1：挂号  2：门诊
	 */
	private String orderMode;

	/**
	 * 交易方式
	 * 1：微信 2：支付宝
	 */
	private Integer tradeMode;

	/**
	 * 医享网平台支付订单号
	 */
	private String psPayOrdNum;

	/**
	 * 医享网平台退款订单号,如果未退款则没有退款订单号
	 */
	private String psRefundOrdNum = "";

	/**
	 * 医院his系统返回支付订单号
	 */
	private String hisPayOrdNum;

	/**
	 * 医院his系统返回退款订单号,如果未退款则没有退款订单号
	 */
	private String hisRefundOrdNum = "";

	/**
	 * 第三方支付订单号,微信、支付宝或其他第三放支付返回的订单号
	 */
	private String agtPayOrdNum;

	/**
	 * 第三方退款订单号,微信、支付宝或其他第三放退款返回的订单号
	 */
	private String agtRefundOrdNum = "";

	/**
	 * 订单支付费用 单位：分
	 */
	private String payTotalFee;

	/**
	 * 订单退款费用 单位：分
	 */
	private String refundTotalFee;

	/**
	 * 分院名称
	 */
	private String branchName;

	/**
	 * 分院代码
	 */
	private String branchCode;

	/**
	 * 交易类型 1：支付 2：退费
	 */
	private String tradeType;

	public OrdersQueryResult() {
		super();
	}

	public OrdersQueryResult(String tradeTime, String orderMode, Integer tradeMode, String psPayOrdNum, String psRefundOrdNum, String hisPayOrdNum,
			String hisRefundOrdNum, String agtPayOrdNum, String agtRefundOrdNum, String payTotalFee, String refundTotalFee, String branchName,
			String branchCode) {
		super();
		this.tradeTime = tradeTime;
		this.orderMode = orderMode;
		this.tradeMode = tradeMode;
		this.psPayOrdNum = psPayOrdNum;
		this.psRefundOrdNum = psRefundOrdNum;
		this.hisPayOrdNum = hisPayOrdNum;
		this.hisRefundOrdNum = hisRefundOrdNum;
		this.agtPayOrdNum = agtPayOrdNum;
		this.agtRefundOrdNum = agtRefundOrdNum;
		this.payTotalFee = payTotalFee;
		this.refundTotalFee = refundTotalFee;
		this.branchName = branchName;
		this.branchCode = branchCode;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getOrderMode() {
		return orderMode;
	}

	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}

	public Integer getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(Integer tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getPsPayOrdNum() {
		return psPayOrdNum;
	}

	public void setPsPayOrdNum(String psPayOrdNum) {
		this.psPayOrdNum = psPayOrdNum;
	}

	public String getPsRefundOrdNum() {
		return psRefundOrdNum;
	}

	public void setPsRefundOrdNum(String psRefundOrdNum) {
		this.psRefundOrdNum = psRefundOrdNum;
	}

	public String getHisPayOrdNum() {
		return hisPayOrdNum;
	}

	public void setHisPayOrdNum(String hisPayOrdNum) {
		this.hisPayOrdNum = hisPayOrdNum;
	}

	public String getHisRefundOrdNum() {
		return hisRefundOrdNum;
	}

	public void setHisRefundOrdNum(String hisRefundOrdNum) {
		this.hisRefundOrdNum = hisRefundOrdNum;
	}

	public String getAgtPayOrdNum() {
		return agtPayOrdNum;
	}

	public void setAgtPayOrdNum(String agtPayOrdNum) {
		this.agtPayOrdNum = agtPayOrdNum;
	}

	public String getAgtRefundOrdNum() {
		return agtRefundOrdNum;
	}

	public void setAgtRefundOrdNum(String agtRefundOrdNum) {
		this.agtRefundOrdNum = agtRefundOrdNum;
	}

	public String getPayTotalFee() {
		return payTotalFee;
	}

	public void setPayTotalFee(String payTotalFee) {
		this.payTotalFee = payTotalFee;
	}

	public String getRefundTotalFee() {
		return refundTotalFee;
	}

	public void setRefundTotalFee(String refundTotalFee) {
		this.refundTotalFee = refundTotalFee;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

}
