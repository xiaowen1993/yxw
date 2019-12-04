package com.yxw.insurance.chinalife.interfaces.entity.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 就医结算返回
 * 
 * @author YangXuewen
 *
 */
public class HospitalSettlementResponse extends Resonse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2470784484940370021L;

	//医疗机构代码
	@JSONField(name = "MedicalInstituteCode")
	private String medicalInstituteCode = "";

	//个人唯一识别码
	@JSONField(name = "PersonGUID")
	private String personGUID = "";

	//就医唯一识别码
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

	//证件类型
	@JSONField(name = "CredentialType")
	private String credentialType = "";
	//证件号码
	@JSONField(name = "CredentialNum")
	private String credentialNum = "";

	//医疗总费用
	@JSONField(name = "TotalAmount")
	private String totalAmount = "";

	//就医结算唯一编号
	@JSONField(name = "SettlementSerialNumber")
	private String settlementSerialNumber = "";

	//结算年度
	@JSONField(name = "SettleYear")
	private String settleYear = "";

	//付款对象
	@JSONField(name = "PaymentTarget")
	private String paymentTarget = "";

	//其他第三方补偿金额
	@JSONField(name = "ThirdPartyCompensation")
	private String thirdPartyCompensation = "";

	//其他第三方累计补偿金额
	@JSONField(name = "ThirdPartyAccumulateCompensation")
	private String thirdPartyAccumulateCompensation = "";

	//就医类型
	@JSONField(name = "MedicalType")
	private String medicalType = "";

	//出险原因
	@JSONField(name = "AccidentReason")
	private String accidentReason = "";

	//出险时间
	@JSONField(name = "AccidentDate")
	private String accidentDate = "";

	//就诊时间
	@JSONField(name = "MedicalDate")
	private String medicalDate = "";

	//就诊科室
	@JSONField(name = "MedicalDepartment")
	private String medicalDepartment = "";

	//主治医生
	@JSONField(name = "MasterDoctor")
	private String masterDoctor = "";

	//离院时间
	@JSONField(name = "LeaveHospitalDate")
	private String leaveHospitalDate = "";

	//离院方式
	@JSONField(name = "LeaveHospitalStyle")
	private String leaveHospitalStyle = "";

	//离院状态
	@JSONField(name = "LeaveHospitalState")
	private String leaveHospitalState = "";

	//离院诊断疾病
	@JSONField(name = "LeaveHospitalDiagnosisCode")
	private String leaveHospitalDiagnosisCode = "";

	//离院诊断疾病名称
	@JSONField(name = "LeaveHospitalDiagnosisName")
	private String leaveHospitalDiagnosisName = "";

	/**
	 * 社会保险补偿信息
	 */
	@JSONField(name = "SocialInsuranceAmount")
	private SocialInsuranceAmountResponse socialInsuranceAmount;

	/**
	 * 商业险种补偿信息
	 */
	@JSONField(name = "InsuranceCompensation")
	private InsuranceCompensationResponse insuranceCompensation;

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

	public String getMedicalGUID() {
		return medicalGUID;
	}

	public void setMedicalGUID(String medicalGUID) {
		this.medicalGUID = medicalGUID;
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

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getSettlementSerialNumber() {
		return settlementSerialNumber;
	}

	public void setSettlementSerialNumber(String settlementSerialNumber) {
		this.settlementSerialNumber = settlementSerialNumber;
	}

	public String getSettleYear() {
		return settleYear;
	}

	public void setSettleYear(String settleYear) {
		this.settleYear = settleYear;
	}

	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
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

	public String getMedicalType() {
		return medicalType;
	}

	public void setMedicalType(String medicalType) {
		this.medicalType = medicalType;
	}

	public String getAccidentReason() {
		return accidentReason;
	}

	public void setAccidentReason(String accidentReason) {
		this.accidentReason = accidentReason;
	}

	public String getAccidentDate() {
		return accidentDate;
	}

	public void setAccidentDate(String accidentDate) {
		this.accidentDate = accidentDate;
	}

	public String getMedicalDate() {
		return medicalDate;
	}

	public void setMedicalDate(String medicalDate) {
		this.medicalDate = medicalDate;
	}

	public String getMedicalDepartment() {
		return medicalDepartment;
	}

	public void setMedicalDepartment(String medicalDepartment) {
		this.medicalDepartment = medicalDepartment;
	}

	public String getMasterDoctor() {
		return masterDoctor;
	}

	public void setMasterDoctor(String masterDoctor) {
		this.masterDoctor = masterDoctor;
	}

	public String getLeaveHospitalDate() {
		return leaveHospitalDate;
	}

	public void setLeaveHospitalDate(String leaveHospitalDate) {
		this.leaveHospitalDate = leaveHospitalDate;
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

	public InsuranceCompensationResponse getInsuranceCompensation() {
		return insuranceCompensation;
	}

	public SocialInsuranceAmountResponse getSocialInsuranceAmount() {
		return socialInsuranceAmount;
	}

	public void setSocialInsuranceAmount(SocialInsuranceAmountResponse socialInsuranceAmount) {
		this.socialInsuranceAmount = socialInsuranceAmount;
	}

	public void setInsuranceCompensation(InsuranceCompensationResponse insuranceCompensation) {
		this.insuranceCompensation = insuranceCompensation;
	}

}
