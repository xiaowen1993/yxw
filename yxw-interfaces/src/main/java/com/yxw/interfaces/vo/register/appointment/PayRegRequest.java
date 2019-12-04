/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月15日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo.register.appointment;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->预约挂号-->预约支付请求参数封装
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class PayRegRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -1405463454390551567L;
	/**
	 * 医院代码,医院没有分院则传入空字符串；医院不存在分院时不允许为空
	 */
	private String branchCode;
	/**
	 * 医院预约流水号,要求唯一，能标识单独的一笔预约挂号
	 */
	private String hisOrdNum;
	/**
	 * 公众服务平台订单号,公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔交易的流水号
	 */
	private String psOrdNum;
	/**
	 * 收单机构流水号,对应收单机构（如财付通、支付宝）用于标识一笔支付交易的流水号
	 */
	private String agtOrdNum;
	/**
	 * 收单机构代码,财付通账号、银行卡账号等
	 */
	private String agtCode;
	/**
	 * 支付方式,1：微信公众号,2：支付宝服务窗
	 */
	private String payMode;
	/**
	 * 支付金额,单位：分
	 */
	private String payAmout;
	/**
	 * 支付时间,格式：YYYY-MM-DD HH:mm:ss
	 */
	private String payTime;
	
	/**
	 * 是否医保结算
	 */
	private String isInsurance;
	/**
	 * 个人账户结算金额
	 */
	private String accountAmout;
	/**
	 * 统筹基金结算金额
	 */
	private String medicareAmount;
	/**
	 * 记账合计
	 */
	private String insuranceAmout;
	/**
	 * 参保类型
	 */
	private String insuredType;
	/**
	 * 病人类型
	 */
	private String patientType;

	/**
	 * 医疗费用支付项目集合
	 */
	private String SSItems;
	
	/**
	 * 社保缴费金额(个人账户结算金额)
	 */
	private String SSMoney;
	
	/**
	 * 预约号
	 */
	private String orderNo;
	

	public PayRegRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param hisOrdNum
	 * @param psOrdNum
	 * @param agtOrdNum
	 * @param agtCode
	 * @param payMode
	 * @param payAmout
	 * @param payTime
	 */

	public PayRegRequest(String branchCode, String hisOrdNum, String psOrdNum, String agtOrdNum, String agtCode, String payMode, String payAmout,
			String payTime) {
		super();
		this.branchCode = branchCode;
		this.hisOrdNum = hisOrdNum;
		this.psOrdNum = psOrdNum;
		this.agtOrdNum = agtOrdNum;
		this.agtCode = agtCode;
		this.payMode = payMode;
		this.payAmout = payAmout;
		this.payTime = payTime;
	}

	/**
	 * @return the branchCode
	 */

	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode
	 *            the branchCode to set
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
	 * @param hisOrdNum
	 *            the hisOrdNum to set
	 */

	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}

	/**
	 * @return the psOrdNum
	 */

	public String getPsOrdNum() {
		return psOrdNum;
	}

	/**
	 * @param psOrdNum
	 *            the psOrdNum to set
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
	 * @param agtOrdNum
	 *            the agtOrdNum to set
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
	 * @param agtCode
	 *            the agtCode to set
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
	 * @param payMode
	 *            the payMode to set
	 */

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	/**
	 * @return the payAmout
	 */

	public String getPayAmout() {
		return payAmout;
	}

	/**
	 * @param payAmout
	 *            the payAmout to set
	 */

	public void setPayAmout(String payAmout) {
		this.payAmout = payAmout;
	}

	/**
	 * @return the payTime
	 */

	public String getPayTime() {
		return payTime;
	}

	/**
	 * @param payTime
	 *            the payTime to set
	 */

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getIsInsurance() {
		return isInsurance;
	}

	public void setIsInsurance(String isInsurance) {
		this.isInsurance = isInsurance;
	}

	public String getAccountAmout() {
		return accountAmout;
	}

	public void setAccountAmout(String accountAmout) {
		this.accountAmout = accountAmout;
	}

	public String getMedicareAmount() {
		return medicareAmount;
	}

	public void setMedicareAmount(String medicareAmount) {
		this.medicareAmount = medicareAmount;
	}

	public String getInsuranceAmout() {
		return insuranceAmout;
	}

	public void setInsuranceAmout(String insuranceAmout) {
		this.insuranceAmout = insuranceAmout;
	}

	public String getInsuredType() {
		return insuredType;
	}

	public void setInsuredType(String insuredType) {
		this.insuredType = insuredType;
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public String getSSItems() {
		return SSItems;
	}

	public void setSSItems(String sSItems) {
		SSItems = sSItems;
	}

	public String getSSMoney() {
		return SSMoney;
	}

	public void setSSMoney(String sSMoney) {
		SSMoney = sSMoney;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
