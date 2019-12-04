package com.yxw.commons.entity.platform.rule;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.rule.entity
 * @ClassName: RuleOnlineFiling
 * @Statement: <p>医院配置规则-->在线建档规则</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RuleOnlineFiling extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6369672501119408627L;

	private String hospitalId;

	/**
	 * 就诊人类型  1 本人   2 他人   3 儿童  可多选  ,号隔开
	 */
	private String visitingPersonType;
	private String[] visitingPersonTypeArray;

	/**
	 * 1：二代身份证    2：港澳通行证     3：台湾居民身份证   4：护照
	 * 可多选  ,号隔开
	 */
	private String certificatesType;

	private String[] certificatesTypeArray;

	/**
	 * 温馨提示内容
	 */
	private String tipWarmContent;

	/**
	 * 输入不正确提示内容
	 */
	private String inputIncorrectTip;

	/**
	 * 在线建档提示
	 */
	private String onlineFilingTip;

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/**
	 * 就诊人类型  1 本人   2 他人   3 儿童  可多选  ,号隔开
	 * @return
	 */
	public String getVisitingPersonType() {
		return visitingPersonType;
	}

	public void setVisitingPersonType(String visitingPersonType) {
		this.visitingPersonType = visitingPersonType;
	}

	/**
	 * 1：二代身份证     2：港澳通行证     3：台湾居民身份证 4：护照
	 * @return
	 */
	public String getCertificatesType() {
		return certificatesType;
	}

	public void setCertificatesType(String certificatesType) {
		this.certificatesType = certificatesType;
	}

	/**
	 * 温馨提示内容
	 * @return
	 */
	public String getTipWarmContent() {
		return tipWarmContent;
	}

	public void setTipWarmContent(String tipWarmContent) {
		this.tipWarmContent = tipWarmContent == null ? null : tipWarmContent.trim();
	}

	/**
	 * 输入不正确提示内容
	 * @return
	 */
	public String getInputIncorrectTip() {
		return inputIncorrectTip;
	}

	public void setInputIncorrectTip(String inputIncorrectTip) {
		this.inputIncorrectTip = inputIncorrectTip == null ? null : inputIncorrectTip.trim();
	}

	public String[] getVisitingPersonTypeArray() {
		return visitingPersonTypeArray;
	}

	public void setVisitingPersonTypeArray(String[] visitingPersonTypeArray) {
		this.visitingPersonTypeArray = visitingPersonTypeArray;
	}

	public String getOnlineFilingTip() {
		return onlineFilingTip;
	}

	public void setOnlineFilingTip(String onlineFilingTip) {
		this.onlineFilingTip = onlineFilingTip;
	}

	public String[] getCertificatesTypeArray() {
		return certificatesTypeArray;
	}

	public void setCertificatesTypeArray(String[] certificatesTypeArray) {
		this.certificatesTypeArray = certificatesTypeArray;
	}

	/**
	 * 得到默认的在线建档规则
	 * @param hospitalId
	 * @return
	 */
	public static RuleOnlineFiling getDefaultRule(String hospitalId) {
		RuleOnlineFiling onlineFiling = new RuleOnlineFiling();
		onlineFiling.setHospitalId(hospitalId);
		onlineFiling.setVisitingPersonType("1");
		onlineFiling.setCertificatesType("1");
		onlineFiling.setCertificatesTypeArray(new String[] { "1" });
		return onlineFiling;
	}

}