/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年11月16日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.mobileapp.invoke.dto.inside;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 就诊人管理 对外入参
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年11月16日
 */
@XStreamAlias("medicalCardParams")
public class MedicalCardParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6921481999429997735L;

	/**
	 * Id
	 */
	private String id;
	/**
	 * 所属医院Id
	 */
	private String hospitalId;

	/**
	 * 所属医院代码
	 */
	private String hospitalCode;

	/**
	 * 所属分院Id
	 */
	private String branchId;

	/**
	 * 所属分院代码
	 */
	private String branchCode;

	/**
	 * 平台
	 */
	private String platform;

	/**
	 * 用户名
	 */
	private String name;

	/**
	 * 推送专用，不入库
	 */
	private String appId;

	private String appCode;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 推送专用，不存库
	 */
	private String patientSexLable;

	/**
	 * 年龄
	 */
	private String age;

	/**
	 * 出生日期
	 */
	private String birth;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 证件类型
	 */
	private String idType;

	/**
	 * 证件号码
	 */
	private String idNo;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 用户标志
	 */
	private String openId;

	/**
	 * 与本人关系
	 */
	private String ownership;

	/**
	 * 卡类型
	 */
	private String cardType;

	/**
	 * 卡号码
	 */
	private String cardNo;

	/**
	 * 监护人姓名
	 */
	private String guardName;

	/**
	 * 监护人证件类型
	 */
	private String guardIdType;

	/**
	 * 监护人证件号码
	 */
	private String guardIdNo;

	/**
	 * 监护人手机号码
	 */
	private String guardMobile;

	/**
	 * 是否医保病人（0不是，1是）
	 */
	private String isMedicare;

	/**
	 * 医保卡号
	 */
	private String medicareNo;

	/**
	 * 住院号
	 */
	private String admissionNo;

	/**
	 * 备注
	 */
	private String mark;

	/**
	 * 状态（0解绑，1绑定）
	 */
	private String state;

	/**
	 * 病人ID
	 */
	private String patientId;

	/**
	 * 绑卡方式(0绑卡，1建档)
	 */
	private String bindWay;

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 更新时间
	 */
	private Long updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPatientSexLable() {
		return patientSexLable;
	}

	public void setPatientSexLable(String patientSexLable) {
		this.patientSexLable = patientSexLable;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getGuardName() {
		return guardName;
	}

	public void setGuardName(String guardName) {
		this.guardName = guardName;
	}

	public String getGuardIdType() {
		return guardIdType;
	}

	public void setGuardIdType(String guardIdType) {
		this.guardIdType = guardIdType;
	}

	public String getGuardIdNo() {
		return guardIdNo;
	}

	public void setGuardIdNo(String guardIdNo) {
		this.guardIdNo = guardIdNo;
	}

	public String getGuardMobile() {
		return guardMobile;
	}

	public void setGuardMobile(String guardMobile) {
		this.guardMobile = guardMobile;
	}

	public String getIsMedicare() {
		return isMedicare;
	}

	public void setIsMedicare(String isMedicare) {
		this.isMedicare = isMedicare;
	}

	public String getMedicareNo() {
		return medicareNo;
	}

	public void setMedicareNo(String medicareNo) {
		this.medicareNo = medicareNo;
	}

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getBindWay() {
		return bindWay;
	}

	public void setBindWay(String bindWay) {
		this.bindWay = bindWay;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
