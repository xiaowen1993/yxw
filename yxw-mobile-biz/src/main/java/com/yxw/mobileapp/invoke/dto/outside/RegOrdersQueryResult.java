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
package com.yxw.mobileapp.invoke.dto.outside;

import java.io.Serializable;

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
public class RegOrdersQueryResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3296753412751068027L;

	/**
	 * 就诊时间
	 */
	private String bookDate;

	/**
	 * 挂号类型
	 */
	private String regType;

	/**
	 * 订单生成时间
	 */
	private String tradeTime;

	/**
	 * 交易方式
	 * 1：微信 2：支付宝 3: 健康易.微信 4: 健康易.支付宝
	 */
	private String tradeMode;

	/**
	 * 医享网平台订单号
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
	 * 第三方支付订单号,微信、支付宝或其他第三方返回的订单号
	 */
	private String agtPayOrdNum;

	/**
	 * 第三方退款订单号,微信、支付宝或其他第三放退款返回的订单号
	 */
	private String agtRefundOrdNum = "";

	/**
	 * 订单支付金额 单位：分
	 */
	private String payTotalFee;

	/**
	 * 订单退费金额 单位：分
	 */
	private String refundTotalFee;

	/**
	 * 交易类型 1：支付 2：退费
	 */
	private String tradeType;

	public RegOrdersQueryResult() {
		super();
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getPsPayOrdNum() {
		return psPayOrdNum;
	}

	public void setPsPayOrdNum(String psPayOrdNum) {
		this.psPayOrdNum = psPayOrdNum;
	}

	public String getHisPayOrdNum() {
		return hisPayOrdNum;
	}

	public void setHisPayOrdNum(String hisPayOrdNum) {
		this.hisPayOrdNum = hisPayOrdNum;
	}

	public String getAgtPayOrdNum() {
		return agtPayOrdNum;
	}

	public void setAgtPayOrdNum(String agtPayOrdNum) {
		this.agtPayOrdNum = agtPayOrdNum;
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

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPsRefundOrdNum() {
		return psRefundOrdNum;
	}

	public void setPsRefundOrdNum(String psRefundOrdNum) {
		this.psRefundOrdNum = psRefundOrdNum;
	}

	public String getHisRefundOrdNum() {
		return hisRefundOrdNum;
	}

	public void setHisRefundOrdNum(String hisRefundOrdNum) {
		this.hisRefundOrdNum = hisRefundOrdNum;
	}

	public String getAgtRefundOrdNum() {
		return agtRefundOrdNum;
	}

	public void setAgtRefundOrdNum(String agtRefundOrdNum) {
		this.agtRefundOrdNum = agtRefundOrdNum;
	}

}
