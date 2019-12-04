package com.yxw.insurance.chinalife.interfaces.entity.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 商业险种补偿信息
 * 
 * @author YangXuewen
 *
 */
public class InsuranceCompensationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4745232870417645847L;

	//业务名称
	@JSONField(name = "BusinessName")
	private String businessName = "";

	//业务处理状态
	@JSONField(name = "BusinessStatus")
	private String businessStatus = "";

	//业务信息
	@JSONField(name = "BusinessMessage")
	private String businessMessage = "";

	//保险合同号
	@JSONField(name = "ContractNumber")
	private String contractNumber = "";

	//赔案号
	@JSONField(name = "CaseNumber")
	private String caseNumber = "";

	//险种代码
	@JSONField(name = "InsuranceCode")
	private String insuranceCode = "";

	//险种名称
	@JSONField(name = "InsuranceName")
	private String insuranceName = "";

	//生效时间
	@JSONField(name = "EffectiveTime")
	private String effectiveTime = "";

	//失效时间
	@JSONField(name = "InvalidTime")
	private String invalidTime = "";

	//起付线
	@JSONField(name = "StartPayLine")
	private String startPayLine = "";

	//合规费用
	@JSONField(name = "CompliantAmount")
	private String compliantAmount = "";

	//扣减金额
	@JSONField(name = "BusinessInsuranceDeductionAmount")
	private String businessInsuranceDeductionAmount = "";

	//险种补偿金额
	@JSONField(name = "BusinessInsurancePayment")
	private String businessInsurancePayment = "";

	//个人自负金额
	@JSONField(name = "SelfPayment")
	private String selfPayment = "";

	//累计补偿金额
	@JSONField(name = "BusinessInsuranceAccumulatePayment")
	private String businessInsuranceAccumulatePayment = "";

	//商业保险补偿时间
	@JSONField(name = "BusinessInsurancePaymentDate")
	private String businessInsurancePaymentDate = "";

	//商业保险住院累计补偿金额
	@JSONField(name = "InsuranceHospitalAccumulatePayment")
	private String insuranceHospitalAccumulatePayment = "";

	//商业保险门诊累计补偿金额
	@JSONField(name = "InsuranceOutpatient")
	private String insuranceOutpatient = "";

	//补偿结算方式
	@JSONField(name = "SettlementWay")
	private String settlementWay = "";

	//转非即时结算原因
	@JSONField(name = "NonImmediateReason")
	private String nonImmediateReason = "";

	//责任免除标识
	@JSONField(name = "WaiverSign")
	private String waiverSign = "";

	//计算过程
	@JSONField(name = "CalculateInformation")
	private String calculateInformation = "";

	//补偿金额大写
	@JSONField(name = "UpperCaseCompensationUpperCase")
	private String upperCaseCompensationUpperCase = "";

	//结算单下载链接
	@JSONField(name = "SettlementDownloadLink")
	private String settlementDownloadLink = "";

	//结算单格式信息
	@JSONField(name = "SettlementDocumentFormat")
	private String settlementDocumentFormat = "";

	//结算单数据内容
	@JSONField(name = "SettlementDocumentContent")
	private String settlementDocumentContent = "";

	//结算操作员
	@JSONField(name = "Operator")
	private String operator = "";

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getBusinessMessage() {
		return businessMessage;
	}

	public void setBusinessMessage(String businessMessage) {
		this.businessMessage = businessMessage;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

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

	public String getSelfPayment() {
		return selfPayment;
	}

	public void setSelfPayment(String selfPayment) {
		this.selfPayment = selfPayment;
	}

	public String getBusinessInsuranceAccumulatePayment() {
		return businessInsuranceAccumulatePayment;
	}

	public void setBusinessInsuranceAccumulatePayment(String businessInsuranceAccumulatePayment) {
		this.businessInsuranceAccumulatePayment = businessInsuranceAccumulatePayment;
	}

	public String getBusinessInsurancePaymentDate() {
		return businessInsurancePaymentDate;
	}

	public void setBusinessInsurancePaymentDate(String businessInsurancePaymentDate) {
		this.businessInsurancePaymentDate = businessInsurancePaymentDate;
	}

	public String getInsuranceHospitalAccumulatePayment() {
		return insuranceHospitalAccumulatePayment;
	}

	public void setInsuranceHospitalAccumulatePayment(String insuranceHospitalAccumulatePayment) {
		this.insuranceHospitalAccumulatePayment = insuranceHospitalAccumulatePayment;
	}

	public String getInsuranceOutpatient() {
		return insuranceOutpatient;
	}

	public void setInsuranceOutpatient(String insuranceOutpatient) {
		this.insuranceOutpatient = insuranceOutpatient;
	}

	public String getSettlementWay() {
		return settlementWay;
	}

	public void setSettlementWay(String settlementWay) {
		this.settlementWay = settlementWay;
	}

	public String getNonImmediateReason() {
		return nonImmediateReason;
	}

	public void setNonImmediateReason(String nonImmediateReason) {
		this.nonImmediateReason = nonImmediateReason;
	}

	public String getWaiverSign() {
		return waiverSign;
	}

	public void setWaiverSign(String waiverSign) {
		this.waiverSign = waiverSign;
	}

	public String getCalculateInformation() {
		return calculateInformation;
	}

	public void setCalculateInformation(String calculateInformation) {
		this.calculateInformation = calculateInformation;
	}

	public String getUpperCaseCompensationUpperCase() {
		return upperCaseCompensationUpperCase;
	}

	public void setUpperCaseCompensationUpperCase(String upperCaseCompensationUpperCase) {
		this.upperCaseCompensationUpperCase = upperCaseCompensationUpperCase;
	}

	public String getSettlementDownloadLink() {
		return settlementDownloadLink;
	}

	public void setSettlementDownloadLink(String settlementDownloadLink) {
		this.settlementDownloadLink = settlementDownloadLink;
	}

	public String getSettlementDocumentFormat() {
		return settlementDocumentFormat;
	}

	public void setSettlementDocumentFormat(String settlementDocumentFormat) {
		this.settlementDocumentFormat = settlementDocumentFormat;
	}

	public String getSettlementDocumentContent() {
		return settlementDocumentContent;
	}

	public void setSettlementDocumentContent(String settlementDocumentContent) {
		this.settlementDocumentContent = settlementDocumentContent;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
