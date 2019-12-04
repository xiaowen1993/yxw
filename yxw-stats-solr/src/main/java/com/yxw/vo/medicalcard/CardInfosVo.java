package com.yxw.vo.medicalcard;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class CardInfosVo implements Serializable {

	private static final long serialVersionUID = 966523278968210387L;

	@Field
	private String id;

	@Field
	private String areaCode;

	@Field
	private String areaName;

	@Field
	private String hospitalName;

	@Field
	private String hospitalCode;

	@Field
	private String platform;

	@Field
	private String name;

	@Field
	private Integer sex;

	@Field
	private Integer age;

	@Field
	private String birth;

	@Field
	private String mobile;

	@Field
	private String idType;

	@Field
	private String idNo;

	@Field
	private String openId;

	@Field
	private String ownership;

	@Field
	private String cardType;

	@Field
	private String cardNo;

	@Field
	private String addmissionNo;

	@Field
	private String guardName;

	@Field
	private Integer guardIdType;

	@Field
	private String guardIdNo;

	@Field
	private String guardMobile;

	@Field
	private Integer isMedicare;

	@Field
	private String medicareNo;

	@Field
	private String state;

	@Field
	private String tableName;

	@Field
	private String createTime_utc;

	@Field
	private String updateTime_utc;

	@Field
	private String createDate;

	@Field
	private String updateDate;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the hospitalName
	 */
	public String getHospitalName() {
		return hospitalName;
	}

	/**
	 * @param hospitalName the hospitalName to set
	 */
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	/**
	 * @return the hospitalCode
	 */
	public String getHospitalCode() {
		return hospitalCode;
	}

	/**
	 * @param hospitalCode the hospitalCode to set
	 */
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return the birth
	 */
	public String getBirth() {
		return birth;
	}

	/**
	 * @param birth the birth to set
	 */
	public void setBirth(String birth) {
		this.birth = birth;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * @return the idNo
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param idNo the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * @param openId the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @return the ownership
	 */
	public String getOwnership() {
		return ownership;
	}

	/**
	 * @param ownership the ownership to set
	 */
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the addmissionNo
	 */
	public String getAddmissionNo() {
		return addmissionNo;
	}

	/**
	 * @param addmissionNo the addmissionNo to set
	 */
	public void setAddmissionNo(String addmissionNo) {
		this.addmissionNo = addmissionNo;
	}

	/**
	 * @return the guardName
	 */
	public String getGuardName() {
		return guardName;
	}

	/**
	 * @param guardName the guardName to set
	 */
	public void setGuardName(String guardName) {
		this.guardName = guardName;
	}

	/**
	 * @return the guardIdType
	 */
	public Integer getGuardIdType() {
		return guardIdType;
	}

	/**
	 * @param guardIdType the guardIdType to set
	 */
	public void setGuardIdType(Integer guardIdType) {
		this.guardIdType = guardIdType;
	}

	/**
	 * @return the guardIdNo
	 */
	public String getGuardIdNo() {
		return guardIdNo;
	}

	/**
	 * @param guardIdNo the guardIdNo to set
	 */
	public void setGuardIdNo(String guardIdNo) {
		this.guardIdNo = guardIdNo;
	}

	/**
	 * @return the guardMobile
	 */
	public String getGuardMobile() {
		return guardMobile;
	}

	/**
	 * @param guardMobile the guardMobile to set
	 */
	public void setGuardMobile(String guardMobile) {
		this.guardMobile = guardMobile;
	}

	/**
	 * @return the isMedicare
	 */
	public Integer getIsMedicare() {
		return isMedicare;
	}

	/**
	 * @param isMedicare the isMedicare to set
	 */
	public void setIsMedicare(Integer isMedicare) {
		this.isMedicare = isMedicare;
	}

	/**
	 * @return the medicareNo
	 */
	public String getMedicareNo() {
		return medicareNo;
	}

	/**
	 * @param medicareNo the medicareNo to set
	 */
	public void setMedicareNo(String medicareNo) {
		this.medicareNo = medicareNo;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the createTime_utc
	 */
	public String getCreateTime_utc() {
		return createTime_utc;
	}

	/**
	 * @param createTime_utc the createTime_utc to set
	 */
	public void setCreateTime_utc(String createTime_utc) {
		this.createTime_utc = createTime_utc;
	}

	/**
	 * @return the updateTime_utc
	 */
	public String getUpdateTime_utc() {
		return updateTime_utc;
	}

	/**
	 * @param updateTime_utc the updateTime_utc to set
	 */
	public void setUpdateTime_utc(String updateTime_utc) {
		this.updateTime_utc = updateTime_utc;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
