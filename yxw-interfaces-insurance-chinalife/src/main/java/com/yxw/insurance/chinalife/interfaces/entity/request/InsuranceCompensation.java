package com.yxw.insurance.chinalife.interfaces.entity.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 就医结算：商业保险补偿，根据子交易码可空
 * 
 * @author YangXuewen
 *
 */
public class InsuranceCompensation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3525459081027371489L;

	//险种代码
	@JSONField(name = "InsuranceCode")
	private String insuranceCode = "";

	//险种名称
	@JSONField(name = "InsuranceName")
	private String insuranceName = "";

	//起付线
	@JSONField(name = "StartPayLine")
	private String startPayLine = "";

	//合规费用
	@JSONField(name = "CompliantAmount")
	private String compliantAmount = "";

	//扣减金额
	@JSONField(name = "BusinessInsuranceDeductionAmount")
	private String businessInsuranceDeductionAmount = "";

	//补偿金额
	@JSONField(name = "BusinessInsurancePayment")
	private String businessInsurancePayment = "";

	//补偿比例(分段比例表示：0_10000:0.5|10000_35000:0.6|35000_70000:0.7|70000:0.8)
	@JSONField(name = "InsurancePaymentRatio")
	private String insurancePaymentRatio = "";

	//累计补偿金额
	@JSONField(name = "BusinessInsuranceAccumulatePayment")
	private String businessInsuranceAccumulatePayment = "";

	public String getInsuranceCode() {
		return insuranceCode;
	}

	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public String getStartPayLine() {
		return startPayLine;
	}

	public void setStartPayLine(String startPayLine) {
		this.startPayLine = startPayLine;
	}

	public String getCompliantAmount() {
		return compliantAmount;
	}

	public void setCompliantAmount(String compliantAmount) {
		this.compliantAmount = compliantAmount;
	}

	public String getBusinessInsuranceDeductionAmount() {
		return businessInsuranceDeductionAmount;
	}

	public void setBusinessInsuranceDeductionAmount(String businessInsuranceDeductionAmount) {
		this.businessInsuranceDeductionAmount = businessInsuranceDeductionAmount;
	}

	public String getBusinessInsurancePayment() {
		return businessInsurancePayment;
	}

	public void setBusinessInsurancePayment(String businessInsurancePayment) {
		this.businessInsurancePayment = businessInsurancePayment;
	}

	public String getInsurancePaymentRatio() {
		return insurancePaymentRatio;
	}

	public void setInsurancePaymentRatio(String insurancePaymentRatio) {
		this.insurancePaymentRatio = insurancePaymentRatio;
	}

	public String getBusinessInsuranceAccumulatePayment() {
		return businessInsuranceAccumulatePayment;
	}

	public void setBusinessInsuranceAccumulatePayment(String businessInsuranceAccumulatePayment) {
		this.businessInsuranceAccumulatePayment = businessInsuranceAccumulatePayment;
	}

}
