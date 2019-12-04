package com.yxw.commons.entity.mobile.biz.usercenter;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.yxw.base.entity.BaseEntity;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.FamilyConstants;
import com.yxw.commons.constants.biz.MedicalCardConstant;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.utils.biz.MedicalCardUtil;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.interfaces.constants.IdType;

/**
 * 家人，通过openId hash
 * 
 * @Package: com.yxw.easyhealth.biz.usercenter.entity
 * @ClassName: Family
 * @Statement: <p>
 *             </p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-12-3
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Family extends BaseEntity {

	private static final long serialVersionUID = -7230402978270328518L;

	/**
	 * appCode: easyHealth
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

	/**
	 * openId
	 */
	private String openId;

	/**
	 * 与本人关系
	 */
	private Integer ownership;

	/**
	 * 与本人关系说明, 不入库
	 */
	private String ownershipLabel;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 脱敏姓名，不入库
	 */
	private String encryptedName;

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * 性别说明，不入库
	 */
	private String sexLabel;

	/**
	 * 出生年月，不入库
	 */
	private String birth;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 脱敏手机号码，不入库
	 */
	private String encryptedMobile;

	/**
	 * 证件类型
	 */
	private Integer idType;

	/**
	 * 证件类型说明，不入库
	 */
	private String idTypeLabel;

	/**
	 * 证件号码
	 */
	private String idNo;

	/**
	 * 脱敏证件号码，不入库
	 */
	private String encryptedIdNo;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 监护人姓名
	 */
	private String guardName;

	/**
	 * 监护人姓名脱敏，不入库
	 */
	private String encryptedGuardName;

	/**
	 * 监护人证件类型
	 */
	private Integer guardIdType;

	/**
	 * 监护人证件类型说明，不入库
	 */
	private String guardIdTypeLabel;

	/**
	 * 监护人证件号码
	 */
	private String guardIdNo;

	/**
	 * 脱敏的监护人证件号码，不入库
	 */
	private String encryptedGuardIdNo;

	/**
	 * 监护人手机号码
	 */
	private String guardMobile;

	/**
	 * 脱敏的监护人手机号码
	 */
	private String encryptedGuardMobile;

	/**
	 * 备注信息
	 */
	private String mark;

	/**
	 * family的状态. 1启用，0禁用
	 */
	private Integer state;

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 创建时间格式化，不入库
	 */
	private String createTimeLabel;

	/**
	 * 更新时间
	 */
	private Long updateTime;

	/**
	 * 更新时间格式化，不入库
	 */
	private String updateTimeLabel;

	/**
	 * 年龄，不入库
	 */
	private int age;

	/**
	 * 验证码类型，不入库，绑定家人验证使用
	 */
	private String codeType;

	/**
	 * 验证码，不入库
	 */
	private String verifyCode;

	/**
	 * 表名，不入库
	 */
	private String hashTableName;

	public People convertToPeople() {
		People people = new People();
		try {
			BeanUtils.copyProperties(this, people);
			if (this.ownership == FamilyConstants.FAMILY_OWNERSHIP_CHILD) {
				people.setUserType(FamilyConstants.USER_TYPE_CHILD);
			} else {
				people.setUserType(FamilyConstants.USER_TYPE_ADULT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return people;
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getOwnership() {
		return ownership;
	}

	public void setOwnership(Integer ownership) {
		this.ownership = ownership;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEncryptedName() {
		if (StringUtils.isNotEmpty(name)) {
			encryptedName = name.replaceFirst("[\u4E00-\u9FA5]", BizConstant.VIEW_SENSITIVE_CHAR);
		}
		return encryptedName;
	}

	public void setEncryptedName(String encryptedName) {
		this.encryptedName = encryptedName;
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

	/**
	 * 转成页面需要查看的
	 * 
	 * @return
	 */
	public String getSexLabel() {
		if (this.sex != null) {
			if (sex.intValue() == BizConstant.SEX_MAN) {
				sexLabel = "男";
			} else {
				sexLabel = "女";
			}
		} else {
			sexLabel = "男";
		}

		return sexLabel;
	}

	public void setSexLabel(String sexLabel) {
		this.sexLabel = sexLabel;
	}

	public String getBirth() {
		if (StringUtils.isBlank(birth)) {
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

	public String getEncryptedMobile() {
		encryptedMobile = "";
		if (this.mobile != null && mobile.length() == 11) {
			encryptedMobile =
					mobile.substring(0, 3) + StringUtils.repeat(BizConstant.VIEW_SENSITIVE_CHAR, mobile.length() - 7)
							+ mobile.substring(mobile.length() - 4, mobile.length());
		}
		return encryptedMobile;
	}

	public void setEncryptedMobile(String encryptedMobile) {
		this.encryptedMobile = encryptedMobile;
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

	public String getEncryptedIdNo() {
		encryptedIdNo = "";
		if (this.idNo != null && this.idNo.length() > 2) {
			// 旧规则，显示前1后1
			// encryptedIdNo = idNo.substring(0, 1) +
			// StringUtils.repeat(BizConstant.VIEW_SENSITIVE_CHAR, idNo.length()
			// - 2)
			// + idNo.substring(idNo.length() - 1, idNo.length());

			// 新规则，显示前1后3
			encryptedIdNo =
					idNo.substring(0, 1) + StringUtils.repeat(BizConstant.VIEW_SENSITIVE_CHAR, idNo.length() - 4)
							+ idNo.substring(idNo.length() - 3, idNo.length());
		}
		return encryptedIdNo;
	}

	public void setEncryptedIdNo(String encryptedIdNo) {
		this.encryptedIdNo = encryptedIdNo;
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

	public String getEncryptedGuardName() {
		if (StringUtils.isNotEmpty(guardName)) {
			encryptedGuardName = guardName.replaceFirst("[\u4E00-\u9FA5]", BizConstant.VIEW_SENSITIVE_CHAR);
		}
		return encryptedGuardName;
	}

	public void setEncryptedGuardName(String encryptedGuardName) {
		this.encryptedGuardName = encryptedGuardName;
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

	public String getEncryptedGuardIdNo() {
		encryptedGuardIdNo = "";
		if (this.guardIdNo != null && this.guardIdNo.length() > 2) {
			// 旧规则，显示前1后1
			// encryptedGuardIdNo = guardIdNo.substring(0, 1) +
			// StringUtils.repeat(BizConstant.VIEW_SENSITIVE_CHAR,
			// guardIdNo.length() - 2)
			// + guardIdNo.substring(guardIdNo.length() - 1,
			// guardIdNo.length());

			// 新规则，显示前1后3
			encryptedGuardIdNo =
					guardIdNo.substring(0, 1) + StringUtils.repeat(BizConstant.VIEW_SENSITIVE_CHAR, guardIdNo.length() - 4)
							+ guardIdNo.substring(guardIdNo.length() - 3, guardIdNo.length());
		}
		return encryptedGuardIdNo;
	}

	public void setEncryptedGuardIdNo(String encryptedGuardIdNo) {
		this.encryptedGuardIdNo = encryptedGuardIdNo;
	}

	public String getGuardMobile() {
		return guardMobile;
	}

	public void setGuardMobile(String guardMobile) {
		this.guardMobile = guardMobile;
	}

	public String getEncryptedGuardMobile() {
		encryptedGuardMobile = "";
		if (this.guardMobile != null && guardMobile.length() == 11) {
			encryptedGuardMobile =
					guardMobile.substring(0, 3) + StringUtils.repeat(BizConstant.VIEW_SENSITIVE_CHAR, guardMobile.length() - 7)
							+ guardMobile.substring(guardMobile.length() - 4, guardMobile.length());
		}
		return encryptedGuardMobile;
	}

	public void setEncryptedGuardMobile(String encryptedGuardMobile) {
		this.encryptedGuardMobile = encryptedGuardMobile;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeLabel() {
		if (createTime != null) {
			createTimeLabel = BizConstant.YYYYMMDDHHMMSS.format(new Date(createTime));
		}
		return createTimeLabel;
	}

	public void setCreateTimeLabel(String createTimeLabel) {
		this.createTimeLabel = createTimeLabel;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTimeLabel() {
		if (updateTime != null) {
			updateTimeLabel = BizConstant.YYYYMMDDHHMMSS.format(new Date(updateTime));
		}
		return updateTimeLabel;
	}

	public void setUpdateTimeLabel(String updateTimeLabel) {
		this.updateTimeLabel = updateTimeLabel;
	}

	public String getHashTableName() {
		if (StringUtils.isBlank(hashTableName)) {
			hashTableName = SimpleHashTableNameGenerator.getFamilyHashTable(openId, true);
		}
		return hashTableName;
	}

	public void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	public String getOwnershipLabel() {
		ownershipLabel = "本人";
		if (this.ownership != null) {
			switch (this.ownership) {
			case FamilyConstants.FAMILY_OWNERSHIP_SELF:
				ownershipLabel = "本人";
				break;
			case FamilyConstants.FAMILY_OWNERSHIP_OTHERS:
				ownershipLabel = "他人";
				break;
			case FamilyConstants.FAMILY_OWNERSHIP_CHILD:
				ownershipLabel = "儿童";
				break;
			case FamilyConstants.FAMILY_OWNERSHIP_PARENT:
				ownershipLabel = "父母";
				break;
			case FamilyConstants.FAMILY_OWNERSHIP_SIBLING:
				ownershipLabel = "兄弟姐妹";
				break;
			case FamilyConstants.FAMILY_OWNERSHIP_PARTNER:
				ownershipLabel = "伴侣";
				break;
			default:
				break;
			}
		}

		return ownershipLabel;
	}

	public void setOwnershipLabel(String ownershipLabel) {
		this.ownershipLabel = ownershipLabel;
	}

	public String getIdTypeLabel() {
		idTypeLabel = "二代身份证";
		if (this.idType != null) {
			switch (this.idType) {
			case 1:
				idTypeLabel = "二代身份证";
				break;
			case 2:
				idTypeLabel = "港澳居民身份证";
				break;
			case 3:
				idTypeLabel = "台湾居民身份证";
				break;
			default:
				break;
			}
		}
		return idTypeLabel;
	}

	public void setIdTypeLabel(String idTypeLabel) {
		this.idTypeLabel = idTypeLabel;
	}

	public String getGuardIdTypeLabel() {
		guardIdTypeLabel = "二代身份证";
		if (this.guardIdType != null) {
			switch (this.guardIdType) {
			case 1:
				guardIdTypeLabel = "二代身份证";
				break;
			case 2:
				guardIdTypeLabel = "港澳居民身份证";
				break;
			case 3:
				guardIdTypeLabel = "台湾居民身份证";
				break;
			default:
				break;
			}
		}
		return guardIdTypeLabel;
	}

	public void setGuardIdTypeLabel(String guardIdTypeLabel) {
		this.guardIdTypeLabel = guardIdTypeLabel;
	}

	public int getAge() {
		if (StringUtils.isNotBlank(this.birth)) {
			this.age = MedicalCardUtil.getAgeByBirth(this.getBirth());
		}
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public MedicalCard convertToMedicalCard() {
		MedicalCard medicalCard = new MedicalCard();

		// 病人信息
		medicalCard.setAppCode(this.appCode);
		medicalCard.setAddress(this.address);
		medicalCard.setBindWay(-1); // -1暂定都是自动建档绑卡的。 健康易专用
		medicalCard.setBirth(this.getBirth());
		medicalCard.setGuardIdNo(this.getGuardIdNo());
		medicalCard.setGuardIdType(this.guardIdType);
		medicalCard.setGuardMobile(this.guardMobile);
		medicalCard.setGuardName(this.guardName);
		medicalCard.setIdNo(this.idNo);
		medicalCard.setIdType(this.idType);
		medicalCard.setMobile(this.mobile);
		medicalCard.setName(this.name);
		medicalCard.setOpenId(this.openId);
		medicalCard.setOwnership(this.ownership);
		medicalCard.setPlatformMode(ModeTypeUtil.getPlatformModeType(this.appCode));
		medicalCard.setSex(this.sex);
		medicalCard.setState(MedicalCardConstant.MEDICALCARD_BOUND);
		medicalCard.setFamilyId(this.id);

		// add 2016-4-21 15:46:43
		medicalCard.setAreaCode(this.areaCode);
		medicalCard.setAreaName(this.areaName);

		return medicalCard;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

}
