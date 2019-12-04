package com.yxw.easyhealth.biz.datastorage.base.entity;

import com.yxw.base.entity.BaseEntity;

/**
 * 
 * @Package: com.yxw.mobileapp.biz.datastorage.entity
 * @ClassName: DataBaseEntity
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-14
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DataBaseEntity extends BaseEntity {

	private static final long serialVersionUID = 4830132990295958195L;

	/**
	 * 医院Id
	 */
	private String hospitalId;

	/**
	 * 医院编码
	 */
	private String hospitalCode;

	/**
	 * 医院名称
	 */
	private String hospitalName;

	/**
	 * 分院ID
	 */
	private String branchHospitalId;

	/**
	 * 分院CODE
	 */
	private String branchHospitalCode;

	/**
	 * 分院名称
	 */
	private String branchHospitalName;

	/**
	 * 入库时间
	 */
	private Long storageTime;

	/**
	 * 查询人(即病人姓名)
	 */
	private String name;

	/**
	 * 查询人的卡类型
	 */
	private Integer cardType;

	/**
	 * 查询人的卡号
	 */
	private String cardNo;

	private String idNo;

	public DataBaseEntity() {

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

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getBranchHospitalId() {
		return branchHospitalId;
	}

	public void setBranchHospitalId(String branchHospitalId) {
		this.branchHospitalId = branchHospitalId;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

	public String getBranchHospitalName() {
		return branchHospitalName;
	}

	public void setBranchHospitalName(String branchHospitalName) {
		this.branchHospitalName = branchHospitalName;
	}

	public Long getStorageTime() {
		return storageTime;
	}

	public void setStorageTime(Long storageTime) {
		this.storageTime = storageTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

}
