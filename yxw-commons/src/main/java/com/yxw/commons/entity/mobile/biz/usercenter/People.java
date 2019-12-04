package com.yxw.commons.entity.mobile.biz.usercenter;

import org.apache.commons.lang.StringUtils;

import com.yxw.base.entity.BaseEntity;
import com.yxw.commons.constants.biz.FamilyConstants;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.utils.biz.MedicalCardUtil;
import com.yxw.interfaces.constants.IdType;

/**
 * 人的实体 通过idNo 或者 guardIdNo hash
 * @Package: com.yxw.easyhealth.biz.usercenter.entity
 * @ClassName: People
 * @Statement: <p>拿来确保这个人不重复、大人：身份证+姓名。小孩：姓名、监护人姓名、监护人证件号码、监护人证件类型</p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-12-3
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class People extends BaseEntity {

	private static final long serialVersionUID = 6932488781977497933L;

	private Integer userType;

	private String name;

	private Integer sex;

	private String birth;

	private String mobile;

	private Integer idType;

	private String idNo;

	private String address;

	private String guardName;

	private Integer guardIdType;

	private String guardIdNo;

	private String guardMobile;

	private Long createTime;

	/**
	 * 不入库 记录存放在哪个表
	 */
	private String hashTableName;

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		if (sex == null) {
			if (idType == Integer.valueOf(IdType.CHINA_ID_CARD) && StringUtils.isNotBlank(idNo)) {
				sex = MedicalCardUtil.getSex(idNo);
			}
		}
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirth() {
		if (birth == null) {
			if (idType == Integer.valueOf(IdType.CHINA_ID_CARD) && StringUtils.isNotBlank(idNo)) {
				birth = MedicalCardUtil.getBirth(idNo);
			}
		}
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGuardName() {
		return guardName;
	}

	public void setGuardName(String guardName) {
		this.guardName = guardName;
	}

	public Integer getGuardIdType() {
		return guardIdType;
	}

	public void setGuardIdType(Integer guardIdType) {
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

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getHashTableName() {
		if (this.userType == FamilyConstants.USER_TYPE_ADULT) {
			// 大人拿自己的身份证hash
			hashTableName = SimpleHashTableNameGenerator.getPeopleHashTable(idNo, true);
		} else {
			// 小孩拿大人的身份证hash
			hashTableName = SimpleHashTableNameGenerator.getPeopleHashTable(guardIdNo, true);
		}
		return hashTableName;
	}

	public void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

}
