package com.yxw.insurance.chinalife.interfaces.entity.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.yxw.insurance.chinalife.interfaces.entity.Head;

/**
 * 就医登记取消
 * 
 * @author YangXuewen
 *
 */
public class HospitalRegistrationCancel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5459101681954122195L;

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
	//证件类型
	@JSONField(name = "CredentialType")
	private String credentialType = "";
	//证件号码
	@JSONField(name = "CredentialNum")
	private String credentialNum = "";

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

	public String getMedicalGUID() {
		return medicalGUID;
	}

	public void setMedicalGUID(String medicalGUID) {
		this.medicalGUID = medicalGUID;
	}

	@JSONField(serialize = false, deserialize = false)
	public Head getHead() {
		return new Head("S108", "00");
	}

}
