package com.yxw.commons.entity.mobile.biz.medicalcard;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.MsgPushEntity;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.utils.biz.MedicalCardUtil;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.MessagePushParamsVo;

/**
 * 
 * @Package: com.yxw.mobileapp.biz.medicalcard.entity
 * @ClassName: MedicalCard
 * @Statement: <p>
 *             </p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-6-3
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */

@Entity(name = "medicalCard")
public class MedicalCard extends MsgPushEntity implements Comparable<MedicalCard> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4706477634352647748L;

	/**
	 * 从属的用户编号
	 */
	private String userId;

	/**
	 * 所属医院Id
	 */
	private String hospitalId;

	/**
	 * 所属医院代码
	 */
	private String hospitalCode;

	/**
	 * 医院名称
	 */
	private String hospitalName;

	/**
	 * 分院名称
	 */
	private String branchName;

	/**
	 * 所属分院Id
	 */
	private String branchId;

	/**
	 * 所属分院代码
	 */
	private String branchCode;

	/**
	 * 平台类型
	 */
	protected Integer platformMode;

	/**
	 * 用户名
	 */
	private String name;

	/**
	 * 从属的familyId
	 */
	private String familyId;

	/**
	 * 推送专用，不存库
	 */
	@SuppressWarnings("unused")
	private String patientName;

	/**
	 * 脱敏后的患者姓名
	 */
	private String encryptedPatientName;

	/**
	 * 脱敏后的手机号码
	 */
	private String encryptedMobile;

	/**
	 * 脱敏后的证件号码
	 */
	private String encryptedIdNo;

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

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * 推送专用，不存库
	 */
	private String patientSexLable;

	/**
	 * 年龄
	 */
	private Integer age;

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
	private Integer idType;

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
	private Integer ownership;

	/**
	 * 卡类型
	 */
	private Integer cardType;

	/**
	 * 卡号码
	 */
	private String cardNo;

	/**
	 * 监护人姓名
	 */
	private String guardName;

	private String encryptedGuardName;

	private String encryptedGuardMobile;

	private String encryptedGuardIdNo;

	/**
	 * 监护人证件类型
	 */
	private Integer guardIdType;

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
	private Integer state;

	/**
	 * 病人ID
	 */
	private String patientId;

	/**
	 * 绑卡方式(0绑卡，1建档)
	 */
	private Integer bindWay;

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 更新时间
	 */
	private Long updateTime;

	/**
	 * 实体类对应的hash子表 该属性只在数据库操作时定位tableName 不入库
	 */
	protected String hashTableName;

	public MedicalCard(String userId, String hospitalId, String hospitalCode, String branchId, String branchCode, Integer platformMode,
			String name, Integer sex, Integer age, String birth, String mobile, Integer idType, String idNo, String address, String openId,
			Integer ownership, Integer cardType, String cardNo, String guardName, Integer guardIdType, String guardIdNo,
			String guardMobile, String isMedicare, String medicareNo, String mark, Integer state, String patientId, Integer bindWay,
			Long createTime, Long updateTime) {
		super();
		this.userId = userId;
		this.hospitalId = hospitalId;
		this.hospitalCode = hospitalCode;
		this.branchId = branchId;
		this.branchCode = branchCode;
		this.platformMode = platformMode;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.birth = birth;
		this.mobile = mobile;
		this.idType = idType;
		this.idNo = idNo;
		this.address = address;
		this.openId = openId;
		this.ownership = ownership;
		this.cardType = cardType;
		this.cardNo = cardNo;
		this.guardName = guardName;
		this.guardIdType = guardIdType;
		this.guardIdNo = guardIdNo;
		this.guardMobile = guardMobile;
		this.isMedicare = isMedicare;
		this.medicareNo = medicareNo;
		this.mark = mark;
		this.state = state;
		this.patientId = patientId;
		this.bindWay = bindWay;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public MedicalCard() {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Integer getBindWay() {
		return bindWay;
	}

	public void setBindWay(Integer bindWay) {
		this.bindWay = bindWay;
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

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
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

	@Override
	public int compareTo(MedicalCard o) {
		// TODO Auto-generated method stub
		if (this.createTime < o.getCreateTime()) {
			return 1;
		} else if (this.createTime > o.getCreateTime()) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public MessagePushParamsVo convertMessagePushParams() {
		MessagePushParamsVo params = new MessagePushParamsVo();
		params.setHospitalId(this.hospitalId);
		params.setAppId(this.appId);
		params.setOpenId(this.openId.replaceAll(" ", "+"));

		params.setPlatformType(this.appCode);

		@SuppressWarnings("unchecked")
		Map<String, Serializable> dataMap = JSON.parseObject(JSON.toJSONString(this), Map.class);

		// 通知的参数
		String urlParms =
				BizConstant.URL_PARAM_CHAR_FIRST.concat(BizConstant.APPID).concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.appId)
						.concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.URL_PARAM_OPEN_ID)
						.concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.openId).concat(BizConstant.URL_PARAM_CHAR_CONCAT)
						.concat(BizConstant.URL_PARAM_APPCODE).concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(params.getPlatformType())
						// .concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat("id").concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.id);
						.concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat("medicalcardId").concat(BizConstant.URL_PARAM_CHAR_ASSGIN)
						.concat(this.id);
		dataMap.put(BizConstant.MSG_PUSH_URL_PARAMS_KEY, urlParms);
		params.setParamMap(dataMap);
		return params;
	}

	public String getPatientName() {
		return name;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientSexLable() {
		if (sex != null) {
			if (sex.intValue() == BizConstant.SEX_MAN) {
				patientSexLable = "男";
			} else {
				patientSexLable = "女";
			}
		} else {
			patientSexLable = "男";
		}

		return patientSexLable;
	}

	public void setPatientSexLable(String patientSexLable) {
		this.patientSexLable = patientSexLable;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getHashTableName() {
		if (StringUtils.isBlank(hashTableName)) {
			hashTableName = SimpleHashTableNameGenerator.getMedicalCardHashTable(openId, true);
		}
		return hashTableName;
	}

	public void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	public String getEncryptedPatientName() {
		encryptedPatientName = "";
		if (StringUtils.isNotEmpty(name)) {
			encryptedPatientName = name.replaceFirst("[\u4E00-\u9FA5]", BizConstant.VIEW_SENSITIVE_CHAR);
		}
		return encryptedPatientName;
	}

	public void setEncryptedPatientName(String encryptedPatientName) {
		this.encryptedPatientName = encryptedPatientName;
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

	public String getEncryptedIdNo() {
		encryptedIdNo = "";
		if (this.idNo != null && this.idNo.length() > 2) {
			// 旧规则，显示前1后1
			// encryptedIdNo = idNo.substring(0, 1) +
			// StringUtils.repeat(BizConstant.VIEW_SENSITIVE_CHAR, idNo.length()
			// - 2) + idNo.substring(idNo.length() - 1, idNo.length());

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

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	public String getEncryptedGuardName() {
		encryptedGuardName = "";
		if (StringUtils.isNotEmpty(guardName)) {
			encryptedGuardName = guardName.replaceFirst("[\u4E00-\u9FA5]", BizConstant.VIEW_SENSITIVE_CHAR);
		}
		return encryptedGuardName;
	}

	public void setEncryptedGuardName(String encryptedGuardName) {
		this.encryptedGuardName = encryptedGuardName;
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

	public String getEncryptedGuardIdNo() {
		encryptedGuardIdNo = "";
		if (this.guardIdNo != null && this.guardIdNo.length() > 2) {
			encryptedGuardIdNo =
					guardIdNo.substring(0, 1) + StringUtils.repeat(BizConstant.VIEW_SENSITIVE_CHAR, guardIdNo.length() - 2)
							+ guardIdNo.substring(guardIdNo.length() - 1, guardIdNo.length());
		}
		return encryptedGuardIdNo;
	}

	public void setEncryptedGuardIdNo(String encryptedGuardIdNo) {
		this.encryptedGuardIdNo = encryptedGuardIdNo;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
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
