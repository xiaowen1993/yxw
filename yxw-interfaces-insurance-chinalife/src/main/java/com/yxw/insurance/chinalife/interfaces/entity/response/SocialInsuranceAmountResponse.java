package com.yxw.insurance.chinalife.interfaces.entity.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 社会保险补偿信息
 * 
 * @author YangXuewen
 *
 */
public class SocialInsuranceAmountResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1988187245535189914L;

	//社保险种代码
	@JSONField(name = "SocialInsuranceCode")
	private String socialInsuranceCode = "";

	//社保险种名称
	@JSONField(name = "SocialInsuranceName")
	private String socialInsuranceName = "";

	//社保合规费用
	@JSONField(name = "MedicalInsuranceCompliantAmount")
	private String medicalInsuranceCompliantAmount = "";

	//社保补偿金额
	@JSONField(name = "MedicalInsurancePayment")
	private String medicalInsurancePayment = "";

	//社保起付线
	@JSONField(name = "MedicalInsuranceStartPayLine")
	private String medicalInsuranceStartPayLine = "";

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

	public String getMedicalInsuranceStartPayLine() {
		return medicalInsuranceStartPayLine;
	}

	public void setMedicalInsuranceStartPayLine(String medicalInsuranceStartPayLine) {
		this.medicalInsuranceStartPayLine = medicalInsuranceStartPayLine;
	}

}
