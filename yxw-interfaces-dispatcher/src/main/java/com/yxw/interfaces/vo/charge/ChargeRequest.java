package com.yxw.interfaces.vo.charge;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 自助机-->充值
 * @Package: com.yxw.interfaces.entity.charge
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

public class ChargeRequest extends Reserve implements Serializable {
	
	private static final long serialVersionUID = -4232099627922063069L;
	/**
	 * 分院编号
	 */
	private String branchCode;
	/**
	 * 诊疗卡
	 */
	private String patCardNo;
	
	/**
	 * 卡类型
	 */
	private String patCardType;
	
	/**
	 * 支付金额
	 */
	private String payAmout;
	
	/**
	 * 第三方支付订单号
	 */
	private String agtOrdNum;
	
	/**
	 * 支付类型 
	 */
	private String payMode;
	
	public ChargeRequest(){}
	public ChargeRequest(String branchCode, String patCardNo, String patCardType, String payAmout, String agtOrdNum,
			 String payMode) {
		super();
		this.branchCode = branchCode;
		this.patCardNo = patCardNo;
		this.patCardType = patCardType;
		this.payAmout = payAmout;
		this.agtOrdNum = agtOrdNum;
		this.payMode = payMode;
	}
	public String getAgtOrdNum() {
		return agtOrdNum;
	}
	public void setAgtOrdNum(String agtOrdNum) {
		this.agtOrdNum = agtOrdNum;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getPatCardNo() {
		return patCardNo;
	}
	public void setPatCardNo(String patCardNo) {
		this.patCardNo = patCardNo;
	}
	public String getPatCardType() {
		return patCardType;
	}
	public void setPatCardType(String patCardType) {
		this.patCardType = patCardType;
	}
	public String getPayAmout() {
		return payAmout;
	}
	public void setPayAmout(String payAmout) {
		this.payAmout = payAmout;
	}

	
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
}
