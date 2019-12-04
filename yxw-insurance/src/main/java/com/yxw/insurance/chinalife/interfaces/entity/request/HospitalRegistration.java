package com.yxw.insurance.chinalife.interfaces.entity.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.yxw.insurance.chinalife.interfaces.entity.Head;

/**
 * 就医登记
 * 
 * @author YangXuewen
 *
 */
public class HospitalRegistration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5429713512356366336L;

	//归属社保机构编码
	@JSONField(name = "InstituteCode")
	private String instituteCode = "";

	//归属社保地区代码
	@JSONField(name = "BelongInstituteAreaCode")
	private String belongInstituteAreaCode = ""; //120000 440100

	//医疗机构代码
	@JSONField(name = "MedicalInstituteCode")
	private String medicalInstituteCode = "";

	//医疗机构名称
	@JSONField(name = "MedicalInstituteName")
	private String medicalInstituteName = "";

	//个人唯一识别码 （填openId | userId）
	@JSONField(name = "PersonGUID")
	private String personGUID = "";

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
	//出生日期
	@JSONField(name = "Birthday")
	private String birthday = "";
	//新生儿姓名
	@JSONField(name = "BabyName")
	private String babyName = "";
	//人员身份
	@JSONField(name = "PersonalIdentification")
	private String personalIdentification = "";
	//就医唯一识别码 (填支付对应的订单号)
	@JSONField(name = "MedicalGUID")
	private String medicalGUID = "";
	//就医登记号
	@JSONField(name = "HospitalNumber")
	private String hospitalNumber = "";
	//结算方式，即时、非即时
	@JSONField(name = "SettlementWay")
	private String settlementWay = "";
	//就医类型 12急症 14门诊 ...
	@JSONField(name = "MedicalType")
	private String medicalType = "";
	//出险原因 1疾病 2意外 3自杀
	@JSONField(name = "AccidentReason")
	private String accidentReason = "";
	//出险时间
	@JSONField(name = "AccidentDate")
	private String accidentDate = "";
	//出险经过
	@JSONField(name = "AccidentProcedure")
	private String accidentProcedure = "";
	//就诊时间
	@JSONField(name = "MedicalDate")
	private String medicalDate = "";
	//社会医疗保险类别 JA:城镇居民基本医疗保险 JB:新型农村合作医疗保险 JC:城乡居民基本医疗保险 JD:城镇职工基本医疗保险 JE:学生基本医疗保险 JF:离休人员医疗保障 BA:公务员医疗补助 BB:城乡救助 BC:大额医疗费用补助 BD:1-6级残疾军人医疗补助 BE:老红军医疗保障 BF:企业补充医疗保险 GA:工伤保险 LA:养老保险 YA:失业保险 SA生育保险 NA无社会医疗保险
	@JSONField(name = "SocialMedicareType")
	private String socialMedicareType = "";
	//患者类型 N本地人 O外地人 F境外人员
	@JSONField(name = "PatientType")
	private String patientType = "";
	//电话
	@JSONField(name = "Phone")
	private String phone = "";
	//手机
	@JSONField(name = "Mobilephone")
	private String mobilephone = "";
	//家庭地址
	@JSONField(name = "Address")
	private String address = "";
	//工作单位
	@JSONField(name = "Company")
	private String company = "";
	//民族
	@JSONField(name = "Nationality")
	private String nationality = "";
	//婚姻状态
	@JSONField(name = "MarriageStatus")
	private String marriageStatus = "";

	@JSONField(name = "MedicalDepartment")
	private String medicalDepartment = ""; //就诊科室
	@JSONField(name = "MasterDoctor")
	private String masterDoctor = ""; //主治医生
	@JSONField(name = "ProviderID")
	private String providerID = ""; //医生执业编码
	@JSONField(name = "BedNumber")
	private String bedNumber = ""; //床位号
	@JSONField(name = "PatientAreaNumber")
	private String patientAreaNumber = ""; //病人病区编号
	@JSONField(name = "MainDiagnosisCode")
	private String mainDiagnosisCode = ""; //主诊断疾病
	@JSONField(name = "MainDiagnosisName")
	private String mainDiagnosisName = ""; //主诊断疾病名称
	@JSONField(name = "SecondaryDiagnosisCode")
	private String secondaryDiagnosisCode = ""; //第二诊断疾病
	@JSONField(name = "SecondaryDiagnosisName")
	private String secondaryDiagnosisName = ""; //第二诊断疾病名称
	@JSONField(name = "OtherDiagnosisCode")
	private String otherDiagnosisCode = ""; //其它诊断疾病(疾病代码用“|”分隔)
	@JSONField(name = "ComplicationCode")
	private String complicationCode = ""; //并发症(疾病代码用“|”分隔)
	@JSONField(name = "PrincipleAction")
	private String principleAction = ""; //主诉
	@JSONField(name = "NowDiseaseHistory")
	private String nowDiseaseHistory = ""; //现病史
	@JSONField(name = "AllergyHistory")
	private String allergyHistory = ""; //过敏史
	@JSONField(name = "FamilyDiseaseHistory")
	private String familyDiseaseHistory = ""; //遗传家族史
	@JSONField(name = "TCMReport")
	private String tcmReport = ""; //中医四诊等描述
	@JSONField(name = "PhysicalExaminationReport")
	private String physicalExaminationReport = ""; //体格检查记录
	@JSONField(name = "PathologyReport")
	private String pathologyReport = ""; //辅助检查记录
	@JSONField(name = "SystolicPressure")
	private String systolicPressure = ""; //收缩压
	@JSONField(name = "DiastolicPressure")
	private String diastolicPressure = ""; //舒张压
	@JSONField(name = "Height")
	private String height = ""; //身高
	@JSONField(name = "Weight")
	private String weight = ""; //体重
	@JSONField(name = "BodyMassIndex")
	private String bodyMassIndex = ""; //BMI指数
	@JSONField(name = "ChestCircumference")
	private String chestCircumference = ""; //胸围
	@JSONField(name = "Temperature")
	private String temperature = ""; //体温
	@JSONField(name = "Pulse")
	private String pulse = ""; //脉搏
	@JSONField(name = "HeartRate")
	private String heartRate = ""; //心率
	@JSONField(name = "Rhythm")
	private String rhythm = ""; //心律
	@JSONField(name = "OperatorNum")
	private String operatorNum = ""; //操作员编号
	@JSONField(name = "OperatorName")
	private String operatorName = ""; //操作员姓名
	@JSONField(name = "Memo")
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

	@JSONField(serialize = false, deserialize = false)
	public Head getHead() {
		return new Head("S107", "00");
	}
	
	
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
	

}
