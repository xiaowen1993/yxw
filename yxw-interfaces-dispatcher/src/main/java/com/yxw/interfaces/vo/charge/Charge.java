package com.yxw.interfaces.vo.charge;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 自助机-->充值
 * @Package: com.yxw.interfaces.entity.charge
 * @ClassName: ChargeRecordRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Charge extends Reserve implements Serializable{

	private static final long serialVersionUID = 6375740469479457177L;
	
	/**
	 * 押金序号
	 */
	private String depositNum;
	
	/**
	 * 发票号
	 */
	private String receiptNum;
	/**
	 * 卡余额
	 */
	private String balance;
	
	public Charge() {}
	
	public Charge(String depositNum, String receiptNum, String balance) {
		super();
		this.depositNum = depositNum;
		this.receiptNum = receiptNum;
		this.balance = balance;
	}

	public String getDepositNum() {
		return depositNum;
	}

	public void setDepositNum(String depositNum) {
		this.depositNum = depositNum;
	}

	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}
	
	
}
