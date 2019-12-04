package com.yxw.insurance.chinalife.interfaces.vo.request;

import java.io.Serializable;
import java.util.List;

/**
 * 就医结算
 * 
 * @author YangXuewen
 *
 */
public class HospitalSettlementRequest implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5155226659487079728L;

	//医疗机构代码
	private String medicalInstituteCode = "";

	//归属社保机构编码
	private String instituteCode = "";

	//归属社保地区代码
	private String belongInstituteAreaCode = ""; //120000 440100

	//个人唯一识别码 （填openId | userId）
	private String personGUID = "";

	//就医唯一识别码 (填支付对应的订单号)
	private String medicalGUID = "";

	//姓名
	private String name = "";

	//社会保障号/农合号
	private String socialInsuranceNumber = "";

	//性别代码
	//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
	private String genderCode = "";

	//证件类型
	private String credentialType = "";
	//证件号码
	private String credentialNum = "";

	//出生日期
	private String birthday = "";

	//补偿结算方式
	private String settlementWay = "";

	//医疗补偿类型
	private String medicalPaymentType = "";

	//主要费用结算方式
	private String feeSettlementType = "";

	//就医结算唯一编号
	private String settlementSerialNumber = "";

	//就医类型
	private String medicalType = "";

	//就诊时间
	private String medicalDate = "";

	//离院方式
	private String leaveHospitalStyle = "";

	//离院状态
	private String leaveHospitalState = "";

	//异地就医标识
	private String offsiteMedicalSign = "";

	//转诊补偿比例
	private String referralPaymentRatio = "";

	//离院诊断疾病
	private String leaveHospitalDiagnosisCode = "";

	//离院诊断疾病名称
	private String leaveHospitalDiagnosisName = "";

	//离院时间
	private String leaveHospitalDate = "";

	//死亡时间
	private String deathTime = "";

	//并发症
	private String complicationCode = "";

	//实际住院天数
	private String medicalDays = "";

	//本年度累计住院天数
	private String annualCumulativeDays = "";

	//手术代码
	private String operationCode = "";

	//手术名称
	private String operationName = "";

	//手术过程
	private String operationCourse = "";

	//手术时间
	private String operationTime = "";

	//主刀医生姓名
	private String cheifOperationPerfomerName = "";

	//主刀医生执业编号
	private String cheifOperationPerfomerOccupationCode = "";

	//社保受理时间
	private String acceptDate = "";

	//医院结算时间
	private String hospitalSettleDate = "";

	//社保结算时间
	private String medicalInsuranceSettleDate = "";

	//付款对象
	private String paymentTarget = "";

	//结算机构代码
	private String settlementInstituteCode = "";

	//结算机构名称
	private String settlementInstituteName = "";

	//结算年度
	private String settleYear = "";

	//银行代码
	private String bankCode = "";

	//银行名称
	private String bankName = "";

	//银行账号
	private String accountNumber = "";

	//开户人姓名
	private String accountHolder = "";

	//医疗总费用
	private String totalAmount = "";

	//按病种结算的费用
	private String diagnosisSortAmount = "";

	//药品费用
	private String drugFee = "";

	//甲类费用
	private String medicalInsuranceFullPaymentAmount = "";

	//乙类医保费用
	private String medicalInsurancePartialPaymentAmount = "";

	//乙类自付费用
	private String partialSelfPayment = "";

	//丙类全自费费用
	private String paymentBySelf = "";

	//中药费用
	private String chineseMedicineAmount = "";

	//西药费用
	private String westernMedicine = "";

	//其他第三方补偿金额
	private String thirdPartyCompensation = "";

	//其他第三方累计补偿金额
	private String thirdPartyAccumulateCompensation = "";

	//业务批次号
	private String businessBatchNumber = "";

	//批次案件数量
	private String batchCaseCount = "";

	//操作员编号
	private String operatorNum = "";

	//操作员姓名
	private String operatorName = "";

	/**
	 * 社会保险补偿，可多个标签
	 */
	private List<SocialInsuranceAmountRequest> socialInsuranceAmounts;
	
	private String mainDiagnosisCode = "";
	private String mainDiagnosisName = "";
	
	
	public String getMainDiagnosisCode() {
		return mainDiagnosisCode;
	}
	
	public void setMainDiagnosisCode(String mainDiagnosisCode) {
		this.mainDiagnosisCode = mainDiagnosisCode;
	}

	public String getMainDiagnosisName() {
		return mainDiagnosisName;
	}

	public void setMainDiagnosisName(String mainDiagnosisName) {
		this.mainDiagnosisName = mainDiagnosisName;
	}

	

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

	public List<SocialInsuranceAmountRequest> getSocialInsuranceAmounts() {
		return socialInsuranceAmounts;
	}

	public void setSocialInsuranceAmounts(List<SocialInsuranceAmountRequest> socialInsuranceAmounts) {
		this.socialInsuranceAmounts = socialInsuranceAmounts;
	}

}
