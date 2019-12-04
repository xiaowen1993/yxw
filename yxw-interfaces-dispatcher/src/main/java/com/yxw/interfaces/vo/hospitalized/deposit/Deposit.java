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
 * 住院患者服务-->住院押金-->住院押金补缴记录
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
public class Deposit extends Reserve implements Serializable {

	private static final long serialVersionUID = -1158089145095149181L;

	/**
	 * 缴费交易流水号,要求唯一，能标识单独的一笔门诊缴费订单
	 */
	private String hisOrdNum;
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
	 * 押金总额
	 */
	private String amout;
	/**
	 * 押金当前余额
	 */
	private String balance;
	/**
	 * 押金冲销说明
	 */
	private String sterilisation;
	/**
	 * 收据号
	 */
	private String receiptNum;
	/**
	 * 条形码
	 */
	private String barCode;
	/**
	 * 医院返回的信息
	 */
	private String hisMessage;
	
	public Deposit() {
		super();
	}

	public Deposit(String hisOrdNum, String payTime, String payMode,
			String amout, String balance, String sterilisation,
			String receiptNum, String barCode, String hisMessage) {
		super();
		this.hisOrdNum = hisOrdNum;
		this.payTime = payTime;
		this.payMode = payMode;
		this.amout = amout;
		this.balance = balance;
		this.sterilisation = sterilisation;
		this.receiptNum = receiptNum;
		this.barCode = barCode;
		this.hisMessage = hisMessage;
	}

	public String getHisOrdNum() {
		return hisOrdNum;
	}

	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getAmout() {
		return amout;
	}

	public void setAmout(String amout) {
		this.amout = amout;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getSterilisation() {
		return sterilisation;
	}

	public void setSterilisation(String sterilisation) {
		this.sterilisation = sterilisation;
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

	public String getHisMessage() {
		return hisMessage;
	}

	public void setHisMessage(String hisMessage) {
		this.hisMessage = hisMessage;
	}
	
}
