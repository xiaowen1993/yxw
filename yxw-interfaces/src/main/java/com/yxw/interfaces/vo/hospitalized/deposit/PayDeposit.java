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
 * 住院患者服务-->住院押金-->住院押金补缴支付
 * @Package: com.yxw.interfaces.entity.hospitalized.deposit
 * @ClassName: Deposit
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By: 范建明
 * @modify Date: 2015-12-07
 * @Why&What is modify: 新增hisMessage
 * @Version: 1.0
 */
public class PayDeposit extends Reserve implements Serializable {

	private static final long serialVersionUID = -1158089145095149181L;

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
	 * 押金当前余额
	 */
	private String balance;
	/**
	 * 医院返回的信息
	 */
	private String hisMessage;
	
	public PayDeposit() {
		super();
	}

	public PayDeposit(String hisOrdNum, String receiptNum, String barCode,
			String balance, String hisMessage) {
		super();
		this.hisOrdNum = hisOrdNum;
		this.receiptNum = receiptNum;
		this.barCode = barCode;
		this.balance = balance;
		this.hisMessage = hisMessage;
	}

	public String getHisOrdNum() {
		return hisOrdNum;
	}

	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}

	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getHisMessage() {
		return hisMessage;
	}

	public void setHisMessage(String hisMessage) {
		this.hisMessage = hisMessage;
	}
	
}
