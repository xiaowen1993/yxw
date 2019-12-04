package com.yxw.insurance.chinalife.interfaces.vo.request;

import java.io.Serializable;
import java.util.List;

/**
 * 就医处方明细上传
 * 
 * @author YangXuewen
 *
 */
public class HospitalFeeDetailInfoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7836915018230230149L;

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

	/**
	 * 就医处方明细
	 */
	private List<HospitalFeeItemRequest> hospitalFeeItems;

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

	public List<HospitalFeeItemRequest> getHospitalFeeItems() {
		return hospitalFeeItems;
	}

	public void setHospitalFeeItems(List<HospitalFeeItemRequest> hospitalFeeItems) {
		this.hospitalFeeItems = hospitalFeeItems;
	}

}
