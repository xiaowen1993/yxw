/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-22</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.cache;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.utils.biz.MedicalCardUtil;
import com.yxw.commons.utils.biz.ModeTypeUtil;

/**
 * @Package: com.yxw.cache.vo
 * @ClassName: SimpleMedicalCard
 * @Statement: <p>
 *             就诊卡的缓存对象
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-22
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SimpleMedicalCard implements Serializable, Comparable<SimpleMedicalCard> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7754235276783267294L;

	private String id;

	/**
	 * 所属医院代码
	 */
	private String hospitalCode;

	private String hospitalId;

	private String hospitalName;

	private String branchId;

	/**
	 * 所属分院代码
	 */
	private String branchCode;

	/**
	 * 分院名称
	 */
	private String branchName;

	/**
	 * 平台(1微信，2支付宝)
	 */
	private Integer platformMode;

	/**
	 * 用户标志
	 */
	private String openId;

	/**
	 * 用户名
	 */
	private String name;

	/**
	 * 脱敏后的患者姓名
	 */
	protected String encryptedPatientName;

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 证件类型
	 */
	private Integer idType;

	/**
	 * 证件号码
	 */
	private String idNo;

	/**
	 * 出生日期
	 */
	private String birth;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 卡类型
	 */
	private Integer cardType;

	/**
	 * 与本人关系
	 */
	private Integer ownership;

	/**
	 * 卡号码
	 */
	private String cardNo;

	/**
	 * 住院号
	 */
	private String admissionNo;

	/**
	 * 关联的家人Id
	 */
	private String familyId;

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 状态
	 */
	private Integer state;

	/**
	 * appId
	 */
	private String appId;

	/**
	 * appCode
	 */
	private String appCode;

	/**
	 * 区域代码
	 */
	private String areaCode;

	/**
	 * 区域名称
	 */
	private String areaName;

	public SimpleMedicalCard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SimpleMedicalCard(MedicalCard card) {
		// TODO Auto-generated constructor stub
		BeanUtils.copyProperties(card, this);
	}

	public SimpleMedicalCard(String openId, String name, String mobile, Integer cardType, Integer ownership, String cardNo,
			String admissionNo, Long createTime) {
		super();
		this.openId = openId;
		this.name = name;
		this.mobile = mobile;
		this.cardType = cardType;
		this.ownership = ownership;
		this.cardNo = cardNo;
		this.createTime = createTime;
		this.admissionNo = admissionNo;
	}

	public SimpleMedicalCard(String name, String mobile, Integer cardType, Integer ownership, String cardNo, String admissionNo) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.cardType = cardType;
		this.ownership = ownership;
		this.cardNo = cardNo;
		this.admissionNo = admissionNo;
	}

	@Override
	public int compareTo(SimpleMedicalCard o) {
		try {
			if (this.createTime < o.getCreateTime()) {
				return 1;
			} else if (this.createTime > o.getCreateTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Integer getPlatformMode() {
		if (platformMode == null || platformMode.intValue() == 0) {
			if (StringUtils.isNotBlank(appCode)) {
				platformMode = ModeTypeUtil.getPlatformModeType(appCode);
			}
		}
		return platformMode;
	}

	public void setPlatformMode(Integer platformMode) {
		this.platformMode = platformMode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		if (StringUtils.isNotBlank(this.birth)) {
			this.age = MedicalCardUtil.getAgeByBirth(this.birth);
		}
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
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

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Integer getOwnership() {
		return ownership;
	}

	public void setOwnership(Integer ownership) {
		this.ownership = ownership;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getEncryptedPatientName() {
		if (StringUtils.isNotEmpty(name)) {
			encryptedPatientName = name.replaceFirst("[\u4E00-\u9FA5]", BizConstant.VIEW_SENSITIVE_CHAR);
		}
		return encryptedPatientName;
	}

	public void setEncryptedPatientName(String encryptedPatientName) {
		this.encryptedPatientName = encryptedPatientName;
	}

	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId
	 *            the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the hospitalId
	 */
	public String getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId
	 *            the hospitalId to set
	 */
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
