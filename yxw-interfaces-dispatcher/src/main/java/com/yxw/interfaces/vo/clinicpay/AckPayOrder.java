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
 * 诊疗付费-->门诊缴费订单支付
 * @Package: com.yxw.interfaces.entity.clinicpay
 * @ClassName: AckPayOrder
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class AckPayOrder extends Reserve implements Serializable {

	private static final long serialVersionUID = 7881013697811243850L;
	/**
	 * 医院交易流水号,用来唯一标识一笔门诊缴费交易
	 */
	private String hisOrdNum;
	/**
	 * 收据号
	 */
	private String receiptNum;
	/**
	 * 条形码,该字段用于扫码作为支付凭证
	 */
	private String barCode;
	/**
	 * 医院返回的信息,返回取药、检查或治疗地点的导诊信息
	 */
	private String hisMessage;

	public AckPayOrder() {
		super();
	}

	/**
	 * @param hisOrdNum
	 * @param receiptNum
	 * @param barCode
	 * @param hisMessage
	 */
	public AckPayOrder(String hisOrdNum, String receiptNum, String barCode, String hisMessage) {
		super();
		this.hisOrdNum = hisOrdNum;
		this.receiptNum = receiptNum;
		this.barCode = barCode;
		this.hisMessage = hisMessage;
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

}
