package com.yxw.interfaces.vo.charge;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 自助机-->充值记录查询
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
public class ChargeRecord extends Reserve implements Serializable{

	private static final long serialVersionUID = 3950105565172699772L;
	/**
	 * 第三方订单号
	 */
	private String agtOrdNum;
	/**
	 * 充值金额
	 */
	private String money;
	/**
	 * 余额
	 */
	private String balance;
	/**
	 * 操作员
	 */
	private String operCode;
	
	public ChargeRecord(String agtOrdNum, String money, String balance, String operCode) {
		super();
		this.agtOrdNum = agtOrdNum;
		this.money = money;
		this.balance = balance;
		this.operCode = operCode;
	}
	
	public String getOperCode() {
		return operCode;
	}
	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}
	public ChargeRecord(){}

	
	public String getAgtOrdNum() {
		return agtOrdNum;
	}

	public void setAgtOrdNum(String agtOrdNum) {
		this.agtOrdNum = agtOrdNum;
	}

	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	};
}
