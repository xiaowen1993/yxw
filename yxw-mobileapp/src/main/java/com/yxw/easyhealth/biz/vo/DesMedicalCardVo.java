package com.yxw.easyhealth.biz.vo;

import java.io.Serializable;

import com.yxw.commons.utils.biz.MedicalCardUtil;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;

/**
 * 接收支付宝返回的本人信息
 * @Package: com.yxw.mobileapp.biz.medicalcard.vo
 * @ClassName: MedicalCardVo
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-6-14
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DesMedicalCardVo implements Serializable {

	private static final long serialVersionUID = -7964765408081268070L;

	/**
	 * base64加密后的名字(base64('刘备'))
	 */
	private String base64Name;

	/**
	 * 脱敏后的姓名(*备)
	 */
	private String desName;

	/**
	 * base64加密后的手机号码
	 */
	private String base64Mobile;

	/**
	 * 脱敏后的手机号码
	 */
	private String desMobile;

	/**
	 * base64加密后的身份证
	 */
	private String base64IdNo;

	/**
	 * 脱敏后的证件号码
	 */
	private String desIdNo;

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * 生日
	 */
	private String birth;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 固定是身份证:1
	 */
	private Integer IdType;

	private String bindCardType;

	public DesMedicalCardVo() {

	}

	public DesMedicalCardVo(EasyHealthUser user) {
		this.setIdType(Integer.valueOf(com.yxw.interfaces.constants.IdType.CHINA_ID_CARD));
		String userIdCardNo = user.getCardNo();
		String userName = user.getName();
		String userMobile = user.getMobile();
		this.setDesName(MedicalCardUtil.desName(user.getName()));
		this.setDesMobile(MedicalCardUtil.desMobile(userMobile));
		this.setDesIdNo(MedicalCardUtil.desIdNo(userIdCardNo));
		this.setBase64Name(MedicalCardUtil.getEncodeValue(userName));
		this.setBase64Mobile(MedicalCardUtil.getEncodeValue(userMobile));
		this.setBase64IdNo(MedicalCardUtil.getEncodeValue(userIdCardNo));
		this.setBirth(MedicalCardUtil.getBirth(userIdCardNo));
		this.setSex(MedicalCardUtil.getSex(userIdCardNo));
		this.setAge(MedicalCardUtil.getAgeByBirth(this.getBirth()));
	}

	public String getBase64Name() {
		return base64Name;
	}

	public void setBase64Name(String base64Name) {
		this.base64Name = base64Name;
	}

	public String getDesName() {
		return desName;
	}

	public void setDesName(String desName) {
		this.desName = desName;
	}

	public String getBase64Mobile() {
		return base64Mobile;
	}

	public void setBase64Mobile(String base64Mobile) {
		this.base64Mobile = base64Mobile;
	}

	public String getDesMobile() {
		return desMobile;
	}

	public void setDesMobile(String desMobile) {
		this.desMobile = desMobile;
	}

	public String getBase64IdNo() {
		return base64IdNo;
	}

	public void setBase64IdNo(String base64IdNo) {
		this.base64IdNo = base64IdNo;
	}

	public String getDesIdNo() {
		return desIdNo;
	}

	public void setDesIdNo(String desIdNo) {
		this.desIdNo = desIdNo;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public Integer getIdType() {
		return IdType;
	}

	public void setIdType(Integer idType) {
		IdType = idType;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "DesMedicalCardVo [base64Name=" + base64Name + ", desName=" + desName + ", base64Mobile=" + base64Mobile + ", desMobile="
				+ desMobile + ", base64IdNo=" + base64IdNo + ", desIdNo=" + desIdNo + ", sex=" + sex + ", birth=" + birth + ", age=" + age
				+ ", IdType=" + IdType + ", bindCardType=" + bindCardType + "]";
	}

	public String getBindCardType() {
		return bindCardType;
	}

	public void setBindCardType(String bindCardType) {
		this.bindCardType = bindCardType;
	}
}
