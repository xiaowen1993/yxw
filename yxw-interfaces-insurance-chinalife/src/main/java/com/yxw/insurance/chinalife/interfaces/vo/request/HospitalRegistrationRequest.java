package com.yxw.insurance.chinalife.interfaces.vo.request;

import java.io.Serializable;

/**
 * 就医登记
 * 
 * @author YangXuewen
 *
 */
public class HospitalRegistrationRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5938407206627198717L;

	//归属社保机构编码
	private String instituteCode = "";

	//归属社保地区代码
	private String belongInstituteAreaCode = ""; //440100

	//医疗机构代码
	private String medicalInstituteCode = "";

	//医疗机构名称
	private String medicalInstituteName = "";

	//个人唯一识别码 （填openId | userId）
	private String personGUID = "";

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
	//新生儿姓名
	private String babyName = "";
	//人员身份
	private String personalIdentification = "";
	//就医唯一识别码 (填支付对应的订单号)
	private String medicalGUID = "";
	//就医登记号
	private String hospitalNumber = "";
	//结算方式，即时、非即时
	private String settlementWay = "";
	//就医类型
	private String medicalType = "";
	//出险原因
	private String accidentReason = "";
	//出险时间
	private String accidentDate = "";
	//出险经过
	private String accidentProcedure = "";
	//就诊时间
	private String medicalDate = "";
	//社会医疗保险类别
	private String socialMedicareType = "";
	//患者类型
	private String patientType = "";
	//电话
	private String phone = "";
	//手机
	private String mobilephone = "";
	//家庭地址
	private String address = "";
	//工作单位
	private String company = "";
	//民族
	private String nationality = "";
	//婚姻状态
	private String marriageStatus = "";

	private String medicalDepartment = ""; //就诊科室
	private String masterDoctor = ""; //主治医生
	private String providerID = ""; //医生执业编码
	private String bedNumber = ""; //床位号
	private String patientAreaNumber = ""; //病人病区编号
	private String mainDiagnosisCode = ""; //主诊断疾病
	private String mainDiagnosisName = ""; //主诊断疾病名称
	private String secondaryDiagnosisCode = ""; //第二诊断疾病
	private String secondaryDiagnosisName = ""; //第二诊断疾病名称
	private String otherDiagnosisCode = ""; //其它诊断疾病(疾病代码用“|”分隔)
	private String complicationCode = ""; //并发症(疾病代码用“|”分隔)
	private String principleAction = ""; //主诉
	private String nowDiseaseHistory = ""; //现病史
	private String allergyHistory = ""; //过敏史
	private String familyDiseaseHistory = ""; //遗传家族史
	private String tcmReport = ""; //中医四诊等描述
	private String physicalExaminationReport = ""; //体格检查记录
	private String pathologyReport = ""; //辅助检查记录
	private String systolicPressure = ""; //收缩压
	private String diastolicPressure = ""; //舒张压
	private String height = ""; //身高
	private String weight = ""; //体重
	private String bodyMassIndex = ""; //BMI指数
	private String chestCircumference = ""; //胸围
	private String temperature = ""; //体温
	private String pulse = ""; //脉搏
	private String heartRate = ""; //心率
	private String rhythm = ""; //心律
	private String operatorNum = ""; //操作员编号
	private String operatorName = ""; //操作员姓名
	private String memo = ""; //备注

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

	public String getMedicalInstituteName() {
		return medicalInstituteName;
	}

	public void setMedicalInstituteName(String medicalInstituteName) {
		this.medicalInstituteName = medicalInstituteName;
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

	public String getBabyName() {
		return babyName;
	}

	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}

	public String getPersonalIdentification() {
		return personalIdentification;
	}

	public void setPersonalIdentification(String personalIdentification) {
		this.personalIdentification = personalIdentification;
	}

	public String getMedicalGUID() {
		return medicalGUID;
	}

	public void setMedicalGUID(String medicalGUID) {
		this.medicalGUID = medicalGUID;
	}

	public String getHospitalNumber() {
		return hospitalNumber;
	}

	public void setHospitalNumber(String hospitalNumber) {
		this.hospitalNumber = hospitalNumber;
	}

	public String getSettlementWay() {
		return settlementWay;
	}

	public void setSettlementWay(String settlementWay) {
		this.settlementWay = settlementWay;
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

	public String getAccidentProcedure() {
		return accidentProcedure;
	}

	public void setAccidentProcedure(String accidentProcedure) {
		this.accidentProcedure = accidentProcedure;
	}

	public String getMedicalDate() {
		return medicalDate;
	}

	public void setMedicalDate(String medicalDate) {
		this.medicalDate = medicalDate;
	}

	public String getSocialMedicareType() {
		return socialMedicareType;
	}

	public void setSocialMedicareType(String socialMedicareType) {
		this.socialMedicareType = socialMedicareType;
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(String marriageStatus) {
		this.marriageStatus = marriageStatus;
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

	public String getProviderID() {
		return providerID;
	}

	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}

	public String getBedNumber() {
		return bedNumber;
	}

	public void setBedNumber(String bedNumber) {
		this.bedNumber = bedNumber;
	}

	public String getPatientAreaNumber() {
		return patientAreaNumber;
	}

	public void setPatientAreaNumber(String patientAreaNumber) {
		this.patientAreaNumber = patientAreaNumber;
	}

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

	public String getSecondaryDiagnosisCode() {
		return secondaryDiagnosisCode;
	}

	public void setSecondaryDiagnosisCode(String secondaryDiagnosisCode) {
		this.secondaryDiagnosisCode = secondaryDiagnosisCode;
	}

	public String getSecondaryDiagnosisName() {
		return secondaryDiagnosisName;
	}

	public void setSecondaryDiagnosisName(String secondaryDiagnosisName) {
		this.secondaryDiagnosisName = secondaryDiagnosisName;
	}

	public String getOtherDiagnosisCode() {
		return otherDiagnosisCode;
	}

	public void setOtherDiagnosisCode(String otherDiagnosisCode) {
		this.otherDiagnosisCode = otherDiagnosisCode;
	}

	public String getComplicationCode() {
		return complicationCode;
	}

	public void setComplicationCode(String complicationCode) {
		this.complicationCode = complicationCode;
	}

	public String getPrincipleAction() {
		return principleAction;
	}

	public void setPrincipleAction(String principleAction) {
		this.principleAction = principleAction;
	}

	public String getNowDiseaseHistory() {
		return nowDiseaseHistory;
	}

	public void setNowDiseaseHistory(String nowDiseaseHistory) {
		this.nowDiseaseHistory = nowDiseaseHistory;
	}

	public String getAllergyHistory() {
		return allergyHistory;
	}

	public void setAllergyHistory(String allergyHistory) {
		this.allergyHistory = allergyHistory;
	}

	public String getFamilyDiseaseHistory() {
		return familyDiseaseHistory;
	}

	public void setFamilyDiseaseHistory(String familyDiseaseHistory) {
		this.familyDiseaseHistory = familyDiseaseHistory;
	}

	public String getTcmReport() {
		return tcmReport;
	}

	public void setTcmReport(String tcmReport) {
		this.tcmReport = tcmReport;
	}

	public String getPhysicalExaminationReport() {
		return physicalExaminationReport;
	}

	public void setPhysicalExaminationReport(String physicalExaminationReport) {
		this.physicalExaminationReport = physicalExaminationReport;
	}

	public String getPathologyReport() {
		return pathologyReport;
	}

	public void setPathologyReport(String pathologyReport) {
		this.pathologyReport = pathologyReport;
	}

	public String getSystolicPressure() {
		return systolicPressure;
	}

	public void setSystolicPressure(String systolicPressure) {
		this.systolicPressure = systolicPressure;
	}

	public String getDiastolicPressure() {
		return diastolicPressure;
	}

	public void setDiastolicPressure(String diastolicPressure) {
		this.diastolicPressure = diastolicPressure;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getBodyMassIndex() {
		return bodyMassIndex;
	}

	public void setBodyMassIndex(String bodyMassIndex) {
		this.bodyMassIndex = bodyMassIndex;
	}

	public String getChestCircumference() {
		return chestCircumference;
	}

	public void setChestCircumference(String chestCircumference) {
		this.chestCircumference = chestCircumference;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getPulse() {
		return pulse;
	}

	public void setPulse(String pulse) {
		this.pulse = pulse;
	}

	public String getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}

	public String getRhythm() {
		return rhythm;
	}

	public void setRhythm(String rhythm) {
		this.rhythm = rhythm;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
