package com.yxw.insurance.chinalife.interfaces.entity.request;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.yxw.insurance.chinalife.interfaces.entity.Head;

/**
 * 就医结算
 * 
 * @author YangXuewen
 *
 */
public class HospitalSettlement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7302435719906598147L;

	//医疗机构代码
	@JSONField(name = "MedicalInstituteCode")
	private String medicalInstituteCode = "";

	//归属社保机构编码
	@JSONField(name = "InstituteCode")
	private String instituteCode = "";

	//归属社保地区代码
	@JSONField(name = "BelongInstituteAreaCode")
	private String belongInstituteAreaCode = ""; //120000 440100

	//个人唯一识别码 （填openId | userId）
	@JSONField(name = "PersonGUID")
	private String personGUID = "";

	//就医唯一识别码 (填支付对应的订单号)
	@JSONField(name = "MedicalGUID")
	private String medicalGUID = "";

	//姓名
	@JSONField(name = "Name")
	private String name = "";

	//社会保障号/农合号
	@JSONField(name = "SocialInsuranceNumber")
	private String socialInsuranceNumber = "";

	//性别代码
	//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
	@JSONField(name = "GenderCode")
	private String genderCode = "";

	//证件类型 I身份证 O其他(个人) H户口簿 P护照 S中国人民解放军军官证 J中国人民武装警察部队警官证 B工商登记证 T税务登记证 Z其他(团体) D驾驶证 N出生证 A港澳通行证
	@JSONField(name = "CredentialType")
	private String credentialType = "";
	//证件号码
	@JSONField(name = "CredentialNum")
	private String credentialNum = "";

	//出生日期
	@JSONField(name = "Birthday")
	private String birthday = "";

	//补偿结算方式 1一站式出院即时结算 2一站式网点同步结算 3非一站式结算
	@JSONField(name = "SettlementWay")
	private String settlementWay = "";

	//医疗补偿类型 N普通 A单病种 I重大疾病 S特种病
	@JSONField(name = "MedicalPaymentType")
	private String medicalPaymentType = "";

	//主要费用结算方式
	@JSONField(name = "FeeSettlementType")
	private String feeSettlementType = "";

	//就医结算唯一编号
	@JSONField(name = "SettlementSerialNumber")
	private String settlementSerialNumber = "";

	//就医类型
	@JSONField(name = "MedicalType")
	private String medicalType = "";

	//就诊时间
	@JSONField(name = "MedicalDate")
	private String medicalDate = "";

	//离院方式 1直接离院 2转院治疗 9其他
	@JSONField(name = "LeaveHospitalStyle")
	private String leaveHospitalStyle = "";

	//离院状态
	@JSONField(name = "LeaveHospitalState")
	private String leaveHospitalState = "";

	//异地就医标识
	@JSONField(name = "OffsiteMedicalSign")
	private String offsiteMedicalSign = "";

	//转诊补偿比例
	@JSONField(name = "ReferralPaymentRatio")
	private String referralPaymentRatio = "";

	//离院诊断疾病
	@JSONField(name = "LeaveHospitalDiagnosisCode")
	private String leaveHospitalDiagnosisCode = "";

	//离院诊断疾病名称
	@JSONField(name = "LeaveHospitalDiagnosisName")
	private String leaveHospitalDiagnosisName = "";

	//离院时间
	@JSONField(name = "LeaveHospitalDate")
	private String leaveHospitalDate = "";

	//死亡时间
	@JSONField(name = "DeathTime")
	private String deathTime = "";

	//并发症
	@JSONField(name = "ComplicationCode")
	private String complicationCode = "";

	//实际住院天数
	@JSONField(name = "MedicalDays")
	private String medicalDays = "";

	//本年度累计住院天数
	@JSONField(name = "AnnualCumulativeDays")
	private String annualCumulativeDays = "";

	//手术代码
	@JSONField(name = "OperationCode")
	private String operationCode = "";

	//手术名称
	@JSONField(name = "OperationName")
	private String operationName = "";

	//手术过程
	@JSONField(name = "OperationCourse")
	private String operationCourse = "";

	//手术时间
	@JSONField(name = "OperationTime")
	private String operationTime = "";

	//主刀医生姓名
	@JSONField(name = "CheifOperationPerfomerName")
	private String cheifOperationPerfomerName = "";

	//主刀医生执业编号
	@JSONField(name = "CheifOperationPerfomerOccupationCode")
	private String cheifOperationPerfomerOccupationCode = "";

	//社保受理时间
	@JSONField(name = "AcceptDate")
	private String acceptDate = "";

	//医院结算时间
	@JSONField(name = "HospitalSettleDate")
	private String hospitalSettleDate = "";

	//社保结算时间
	@JSONField(name = "MedicalInsuranceSettleDate")
	private String medicalInsuranceSettleDate = "";

	//付款对象
	@JSONField(name = "PaymentTarget")
	private String paymentTarget = "";

	//结算机构代码
	@JSONField(name = "SettlementInstituteCode")
	private String settlementInstituteCode = "";

	//结算机构名称
	@JSONField(name = "SettlementInstituteName")
	private String settlementInstituteName = "";

	//结算年度
	@JSONField(name = "SettleYear")
	private String settleYear = "";

	//银行代码
	@JSONField(name = "BankCode")
	private String bankCode = "";

	//银行名称
	@JSONField(name = "BankName")
	private String bankName = "";

	//银行账号
	@JSONField(name = "AccountNumber")
	private String accountNumber = "";

	//开户人姓名
	@JSONField(name = "AccountHolder")
	private String accountHolder = "";

	//医疗总费用
	@JSONField(name = "TotalAmount")
	private String totalAmount = "";

	//按病种结算的费用
	@JSONField(name = "DiagnosisSortAmount")
	private String diagnosisSortAmount = "";

	//药品费用
	@JSONField(name = "DrugFee")
	private String drugFee = "";

	//甲类费用
	@JSONField(name = "MedicalInsuranceFullPaymentAmount")
	private String medicalInsuranceFullPaymentAmount = "";

	//乙类医保费用
	@JSONField(name = "MedicalInsurancePartialPaymentAmount")
	private String medicalInsurancePartialPaymentAmount = "";

	//乙类自付费用
	@JSONField(name = "PartialSelfPayment")
	private String partialSelfPayment = "";

	//丙类全自费费用
	@JSONField(name = "PaymentBySelf")
	private String paymentBySelf = "";

	//中药费用
	@JSONField(name = "ChineseMedicineAmount")
	private String chineseMedicineAmount = "";

	//西药费用
	@JSONField(name = "WesternMedicine")
	private String westernMedicine = "";

	//其他第三方补偿金额
	@JSONField(name = "ThirdPartyCompensation")
	private String thirdPartyCompensation = "";

	//其他第三方累计补偿金额
	@JSONField(name = "ThirdPartyAccumulateCompensation")
	private String thirdPartyAccumulateCompensation = "";

	//业务批次号
	@JSONField(name = "BusinessBatchNumber")
	private String businessBatchNumber = "";

	//批次案件数量
	@JSONField(name = "BatchCaseCount")
	private String batchCaseCount = "";

	//操作员编号
	@JSONField(name = "OperatorNum")
	private String operatorNum = "";

	//操作员姓名
	@JSONField(name = "OperatorName")
	private String operatorName = "";

	/**
	 * 社会保险补偿，可多个标签
	 */
	@JSONField(name = "SocialInsuranceAmount")
	private List<SocialInsuranceAmount> socialInsuranceAmounts;

	/**
	 * 商业保险补偿，根据子交易码可空
	 */
	@JSONField(name = "InsuranceCompensation")
	private InsuranceCompensation insuranceCompensation;

	public String getInstituteCode() {
		return instituteCode;
	}

	public void setInstituteCode(String instituteCode) {
		this.instituteCode = instituteCode;
	}

	public String getBelongInstituteAreaCode() {
		return belongInstituteAreaCode;
	}

	public void setBelongInstituteAreaCode(String belongInstituteAreaCode) {
		this.belongInstituteAreaCode = belongInstituteAreaCode;
	}

	public String getMedicalInstituteCode() {
		return medicalInstituteCode;
	}

	public void setMedicalInstituteCode(String medicalInstituteCode) {
		this.medicalInstituteCode = medicalInstituteCode;
	}

	public String getPersonGUID() {
		return personGUID;
	}

	public void setPersonGUID(String personGUID) {
		this.personGUID = personGUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSocialInsuranceNumber() {
		return socialInsuranceNumber;
	}

	public void setSocialInsuranceNumber(String socialInsuranceNumber) {
		this.socialInsuranceNumber = socialInsuranceNumber;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	public String getCredentialNum() {
		return credentialNum;
	}

	public void setCredentialNum(String credentialNum) {
		this.credentialNum = credentialNum;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMedicalGUID() {
		return medicalGUID;
	}

	public void setMedicalGUID(String medicalGUID) {
		this.medicalGUID = medicalGUID;
	}

	public String getSettlementWay() {
		return settlementWay;
	}

	public void setSettlementWay(String settlementWay) {
		this.settlementWay = settlementWay;
	}

	public String getMedicalPaymentType() {
		return medicalPaymentType;
	}

	public void setMedicalPaymentType(String medicalPaymentType) {
		this.medicalPaymentType = medicalPaymentType;
	}

	public String getFeeSettlementType() {
		return feeSettlementType;
	}

	public void setFeeSettlementType(String feeSettlementType) {
		this.feeSettlementType = feeSettlementType;
	}

	public String getSettlementSerialNumber() {
		return settlementSerialNumber;
	}

	public void setSettlementSerialNumber(String settlementSerialNumber) {
		this.settlementSerialNumber = settlementSerialNumber;
	}

	public String getMedicalType() {
		return medicalType;
	}

	public void setMedicalType(String medicalType) {
		this.medicalType = medicalType;
	}

	public String getMedicalDate() {
		return medicalDate;
	}

	public void setMedicalDate(String medicalDate) {
		this.medicalDate = medicalDate;
	}

	public String getLeaveHospitalStyle() {
		return leaveHospitalStyle;
	}

	public void setLeaveHospitalStyle(String leaveHospitalStyle) {
		this.leaveHospitalStyle = leaveHospitalStyle;
	}

	public String getLeaveHospitalState() {
		return leaveHospitalState;
	}

	public void setLeaveHospitalState(String leaveHospitalState) {
		this.leaveHospitalState = leaveHospitalState;
	}

	public String getOffsiteMedicalSign() {
		return offsiteMedicalSign;
	}

	public void setOffsiteMedicalSign(String offsiteMedicalSign) {
		this.offsiteMedicalSign = offsiteMedicalSign;
	}

	public String getReferralPaymentRatio() {
		return referralPaymentRatio;
	}

	public void setReferralPaymentRatio(String referralPaymentRatio) {
		this.referralPaymentRatio = referralPaymentRatio;
	}

	public String getLeaveHospitalDiagnosisCode() {
		return leaveHospitalDiagnosisCode;
	}

	public void setLeaveHospitalDiagnosisCode(String leaveHospitalDiagnosisCode) {
		this.leaveHospitalDiagnosisCode = leaveHospitalDiagnosisCode;
	}

	public String getLeaveHospitalDiagnosisName() {
		return leaveHospitalDiagnosisName;
	}

	public void setLeaveHospitalDiagnosisName(String leaveHospitalDiagnosisName) {
		this.leaveHospitalDiagnosisName = leaveHospitalDiagnosisName;
	}

	public String getLeaveHospitalDate() {
		return leaveHospitalDate;
	}

	public void setLeaveHospitalDate(String leaveHospitalDate) {
		this.leaveHospitalDate = leaveHospitalDate;
	}

	public String getDeathTime() {
		return deathTime;
	}

	public void setDeathTime(String deathTime) {
		this.deathTime = deathTime;
	}

	public String getComplicationCode() {
		return complicationCode;
	}

	public void setComplicationCode(String complicationCode) {
		this.complicationCode = complicationCode;
	}

	public String getMedicalDays() {
		return medicalDays;
	}

	public void setMedicalDays(String medicalDays) {
		this.medicalDays = medicalDays;
	}

	public String getAnnualCumulativeDays() {
		return annualCumulativeDays;
	}

	public void setAnnualCumulativeDays(String annualCumulativeDays) {
		this.annualCumulativeDays = annualCumulativeDays;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationCourse() {
		return operationCourse;
	}

	public void setOperationCourse(String operationCourse) {
		this.operationCourse = operationCourse;
	}

	public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}

	public String getCheifOperationPerfomerName() {
		return cheifOperationPerfomerName;
	}

	public void setCheifOperationPerfomerName(String cheifOperationPerfomerName) {
		this.cheifOperationPerfomerName = cheifOperationPerfomerName;
	}

	public String getCheifOperationPerfomerOccupationCode() {
		return cheifOperationPerfomerOccupationCode;
	}

	public void setCheifOperationPerfomerOccupationCode(String cheifOperationPerfomerOccupationCode) {
		this.cheifOperationPerfomerOccupationCode = cheifOperationPerfomerOccupationCode;
	}

	public String getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getHospitalSettleDate() {
		return hospitalSettleDate;
	}

	public void setHospitalSettleDate(String hospitalSettleDate) {
		this.hospitalSettleDate = hospitalSettleDate;
	}

	public String getMedicalInsuranceSettleDate() {
		return medicalInsuranceSettleDate;
	}

	public void setMedicalInsuranceSettleDate(String medicalInsuranceSettleDate) {
		this.medicalInsuranceSettleDate = medicalInsuranceSettleDate;
	}

	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	public String getSettlementInstituteCode() {
		return settlementInstituteCode;
	}

	public void setSettlementInstituteCode(String settlementInstituteCode) {
		this.settlementInstituteCode = settlementInstituteCode;
	}

	public String getSettlementInstituteName() {
		return settlementInstituteName;
	}

	public void setSettlementInstituteName(String settlementInstituteName) {
		this.settlementInstituteName = settlementInstituteName;
	}

	public String getSettleYear() {
		return settleYear;
	}

	public void setSettleYear(String settleYear) {
		this.settleYear = settleYear;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDiagnosisSortAmount() {
		return diagnosisSortAmount;
	}

	public void setDiagnosisSortAmount(String diagnosisSortAmount) {
		this.diagnosisSortAmount = diagnosisSortAmount;
	}

	public String getDrugFee() {
		return drugFee;
	}

	public void setDrugFee(String drugFee) {
		this.drugFee = drugFee;
	}

	public String getMedicalInsuranceFullPaymentAmount() {
		return medicalInsuranceFullPaymentAmount;
	}

	public void setMedicalInsuranceFullPaymentAmount(String medicalInsuranceFullPaymentAmount) {
		this.medicalInsuranceFullPaymentAmount = medicalInsuranceFullPaymentAmount;
	}

	public String getMedicalInsurancePartialPaymentAmount() {
		return medicalInsurancePartialPaymentAmount;
	}

	public void setMedicalInsurancePartialPaymentAmount(String medicalInsurancePartialPaymentAmount) {
		this.medicalInsurancePartialPaymentAmount = medicalInsurancePartialPaymentAmount;
	}

	public String getPartialSelfPayment() {
		return partialSelfPayment;
	}

	public void setPartialSelfPayment(String partialSelfPayment) {
		this.partialSelfPayment = partialSelfPayment;
	}

	public String getPaymentBySelf() {
		return paymentBySelf;
	}

	public void setPaymentBySelf(String paymentBySelf) {
		this.paymentBySelf = paymentBySelf;
	}

	public String getChineseMedicineAmount() {
		return chineseMedicineAmount;
	}

	public void setChineseMedicineAmount(String chineseMedicineAmount) {
		this.chineseMedicineAmount = chineseMedicineAmount;
	}

	public String getWesternMedicine() {
		return westernMedicine;
	}

	public void setWesternMedicine(String westernMedicine) {
		this.westernMedicine = westernMedicine;
	}

	public String getThirdPartyCompensation() {
		return thirdPartyCompensation;
	}

	public void setThirdPartyCompensation(String thirdPartyCompensation) {
		this.thirdPartyCompensation = thirdPartyCompensation;
	}

	public String getThirdPartyAccumulateCompensation() {
		return thirdPartyAccumulateCompensation;
	}

	public void setThirdPartyAccumulateCompensation(String thirdPartyAccumulateCompensation) {
		this.thirdPartyAccumulateCompensation = thirdPartyAccumulateCompensation;
	}

	public String getBusinessBatchNumber() {
		return businessBatchNumber;
	}

	public void setBusinessBatchNumber(String businessBatchNumber) {
		this.businessBatchNumber = businessBatchNumber;
	}

	public String getBatchCaseCount() {
		return batchCaseCount;
	}

	public void setBatchCaseCount(String batchCaseCount) {
		this.batchCaseCount = batchCaseCount;
	}

	public String getOperatorNum() {
		return operatorNum;
	}

	public void setOperatorNum(String operatorNum) {
		this.operatorNum = operatorNum;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public List<SocialInsuranceAmount> getSocialInsuranceAmounts() {
		return socialInsuranceAmounts;
	}

	public void setSocialInsuranceAmounts(List<SocialInsuranceAmount> socialInsuranceAmounts) {
		this.socialInsuranceAmounts = socialInsuranceAmounts;
	}

	public InsuranceCompensation getInsuranceCompensation() {
		return insuranceCompensation;
	}

	public void setInsuranceCompensation(InsuranceCompensation insuranceCompensation) {
		this.insuranceCompensation = insuranceCompensation;
	}

	@JSONField(serialize = false, deserialize = false)
	public Head getHead() {
		return new Head("S112", "00");
	}
	
	/**
	 * 获取请求 head
	 * @param transactionSubCode 子交易码
	 * 00，商业保险理赔金由消息接收方理算，以接收方的理算结果为标准。
	 * 01，商业保险理赔金的计算结果由消息发起方给出，以消息发起方的理算结果为标准。
	 * @return
	 */
	@JSONField(serialize = false, deserialize = false)
	public Head getHead(String transactionSubCode) {
		return new Head("S112", transactionSubCode);
	}

}
