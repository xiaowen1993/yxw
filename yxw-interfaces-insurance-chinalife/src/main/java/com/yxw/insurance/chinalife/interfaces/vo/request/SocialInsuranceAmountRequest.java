package com.yxw.insurance.chinalife.interfaces.vo.request;

import java.io.Serializable;

/**
 * 就医结算：社会保险补偿
 * 
 * @author YangXuewen
 *
 */
public class SocialInsuranceAmountRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3534192613555478886L;

	//社保险种代码
	private String socialInsuranceCode = "";

	//社保险种名称
	private String socialInsuranceName = "";

	//生效时间
	private String effectiveTime = "";

	//失效时间
	private String invalidTime = "";

	//社保合规费用
	private String medicalInsuranceCompliantAmount = "";

	//社保补偿金额
	private String medicalInsurancePayment = "";

	//社保不报销金额
	private String medicalInsuranceDeduction = "";

	//社保起付线
	private String medicalInsuranceStartPayLine = "";

	//补偿比例(分段比例表示：0_10000:0.5|10000_35000:0.6|35000_70000:0.7|70000:0.8)
	private String paymentRatio = "";

	//社保累计补偿金额
	private String medicalInsuranceCumulativePayment = "";

	//社保累计个人自负金额
	private String medicalInsuranceCumulativeSelfPayment = "";

	public String getSocialInsuranceCode() {
		return socialInsuranceCode;
	}

	public void setSocialInsuranceCode(String socialInsuranceCode) {
		this.socialInsuranceCode = socialInsuranceCode;
	}

	public String getSocialInsuranceName() {
		return socialInsuranceName;
	}

	public void setSocialInsuranceName(String socialInsuranceName) {
		this.socialInsuranceName = socialInsuranceName;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getMedicalInsuranceCompliantAmount() {
		return medicalInsuranceCompliantAmount;
	}

	public void setMedicalInsuranceCompliantAmount(String medicalInsuranceCompliantAmount) {
		this.medicalInsuranceCompliantAmount = medicalInsuranceCompliantAmount;
	}

	public String getMedicalInsurancePayment() {
		return medicalInsurancePayment;
	}

	public void setMedicalInsurancePayment(String medicalInsurancePayment) {
		this.medicalInsurancePayment = medicalInsurancePayment;
	}

	public String getMedicalInsuranceDeduction() {
		return medicalInsuranceDeduction;
	}

	public void setMedicalInsuranceDeduction(String medicalInsuranceDeduction) {
		this.medicalInsuranceDeduction = medicalInsuranceDeduction;
	}

	public String getMedicalInsuranceStartPayLine() {
		return medicalInsuranceStartPayLine;
	}

	public void setMedicalInsuranceStartPayLine(String medicalInsuranceStartPayLine) {
		this.medicalInsuranceStartPayLine = medicalInsuranceStartPayLine;
	}

	public String getPaymentRatio() {
		return paymentRatio;
	}

	public void setPaymentRatio(String paymentRatio) {
		this.paymentRatio = paymentRatio;
	}

	public String getMedicalInsuranceCumulativePayment() {
		return medicalInsuranceCumulativePayment;
	}

	public void setMedicalInsuranceCumulativePayment(String medicalInsuranceCumulativePayment) {
		this.medicalInsuranceCumulativePayment = medicalInsuranceCumulativePayment;
	}

	public String getMedicalInsuranceCumulativeSelfPayment() {
		return medicalInsuranceCumulativeSelfPayment;
	}

	public void setMedicalInsuranceCumulativeSelfPayment(String medicalInsuranceCumulativeSelfPayment) {
		this.medicalInsuranceCumulativeSelfPayment = medicalInsuranceCumulativeSelfPayment;
	}

}
