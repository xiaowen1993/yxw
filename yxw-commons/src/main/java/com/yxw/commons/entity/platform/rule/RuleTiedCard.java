package com.yxw.commons.entity.platform.rule;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.rule.entity
 * @ClassName: RuleTiedCard
 * @Statement: <p>医院配置规则-->绑卡规则</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RuleTiedCard extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8463827688087235432L;

	private String hospitalId;

	/**
	 * 就诊人关系    1 本人   2 他人   3 儿童  可多选  ,号隔开
	 */
	private String visitingPersonType;
	private String[] visitingPersonTypeArray;

	/**
	 * 就诊卡类型  1：就诊卡、2：社保卡、3：医保卡、4：住院号 
	 * 多个值用,号隔开
	 */
	private String cardType;
	private String[] cardTypeArray;

	/**
	 * 1：二代身份证    2：港澳通行证     3：台湾居民身份证   4：护照
	 * 可多选  ,号隔开
	 */
	private String certificatesType;
	private String[] certificatesTypeArray;

	/**
	 * 是否显示卡号  0：否  1：是
	 */
	private Integer isShowCardNo;

	/**
	 * 温馨提示内容
	 */
	private String tipWarmContent;

	/**
	 * 是否显示在线建档    0：否    1：是
	 */
	private Integer isShowOnlineFiling;

	/**
	 * 添加就诊人时需要验证内容的类型     1：姓名   2 ：身份证  3：手机号   4：卡号
	 * 多个值用,号隔开
	 */
	private String verifyConditionType;
	private String[] verifyConditionTypeArray;

	/**
	 * 输入不完整提示内容
	 */
	private String inputIncompleteTip;

	/**
	 * 输入不正确提示内容
	 */
	private String inputIncorrectTip;

	/**
	 * 绑卡提示
	 */
	private String tiedCardTip;

	/**
	 * 输入卡号提示
	 */
	private String inputCardNoTip;
	private String[] inputCardNoTipArray;

	/**
	 * 输入对应卡类型的另外称呼
	 */
	private String inputCardTypeRemark;
	private String[] inputCardTypeRemarkArray;

	private Integer isUnbindRestricted;
	private Integer unbindRestrictedDayNum;
	private String unbindRestrictedTip;

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

	public String[] getVisitingPersonTypeArray() {
		return visitingPersonTypeArray;
	}

	public void setVisitingPersonTypeArray(String[] visitingPersonTypeArray) {
		this.visitingPersonTypeArray = visitingPersonTypeArray;
	}

	/**
	 * 就诊卡类型   1：二代身份证  2：港澳通行证  3：台湾居民身份证 4：护照
	 * 多个值用,号隔开
	 * @return
	 */
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String[] getCardTypeArray() {
		return cardTypeArray;
	}

	public void setCardTypeArray(String[] cardTypeArray) {
		this.cardTypeArray = cardTypeArray;
	}

	/**
	 * 是否显示卡号   0：否  1：是
	 * @return
	 */
	public Integer getIsShowCardNo() {
		return isShowCardNo;
	}

	public void setIsShowCardNo(Integer isShowCardNo) {
		this.isShowCardNo = isShowCardNo;
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
	 * 是否显示在线建档    0：否    1：是
	 * @return
	 */
	public Integer getIsShowOnlineFiling() {
		return isShowOnlineFiling;
	}

	public void setIsShowOnlineFiling(Integer isShowOnlineFiling) {
		this.isShowOnlineFiling = isShowOnlineFiling;
	}

	/**
	 * 输入不完整提示内容
	 * @return
	 */
	public String getInputIncompleteTip() {
		return inputIncompleteTip;
	}

	/**
	 *添加就诊人时需要验证内容的类型  0：不验证    1：姓名   2 ：身份证  3：手机号   4：卡号
	 * @return
	 */
	public String getVerifyConditionType() {
		return verifyConditionType;
	}

	public void setVerifyConditionType(String verifyConditionType) {
		this.verifyConditionType = verifyConditionType;
	}

	public String[] getVerifyConditionTypeArray() {
		return verifyConditionTypeArray;
	}

	public void setVerifyConditionTypeArray(String[] verifyConditionTypeArray) {
		this.verifyConditionTypeArray = verifyConditionTypeArray;
	}

	public void setInputIncompleteTip(String inputIncompleteTip) {
		this.inputIncompleteTip = inputIncompleteTip == null ? null : inputIncompleteTip.trim();
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

	public String getCertificatesType() {
		return certificatesType;
	}

	public void setCertificatesType(String certificatesType) {
		this.certificatesType = certificatesType;
	}

	/**
	 * 证件类型  1：二代身份证  2：港澳通行证  3：台湾居民身份证 4：护照
	 * 多个值用,号隔开
	 * @return
	 */
	public String[] getCertificatesTypeArray() {
		return certificatesTypeArray;
	}

	public void setCertificatesTypeArray(String[] certificatesTypeArray) {
		this.certificatesTypeArray = certificatesTypeArray;
	}

	public String getTiedCardTip() {
		return tiedCardTip;
	}

	public void setTiedCardTip(String tiedCardTip) {
		this.tiedCardTip = tiedCardTip;
	}

	public String[] getInputCardNoTipArray() {
		return inputCardNoTipArray;
	}

	public void setInputCardNoTipArray(String[] inputCardNoTipArray) {
		this.inputCardNoTipArray = inputCardNoTipArray;
	}

	public String getInputCardNoTip() {
		return inputCardNoTip;
	}

	public void setInputCardNoTip(String inputCardNoTip) {
		this.inputCardNoTip = inputCardNoTip;
	}

	public String getInputCardTypeRemark() {
		return inputCardTypeRemark;
	}

	public void setInputCardTypeRemark(String inputCardTypeRemark) {
		this.inputCardTypeRemark = inputCardTypeRemark;
	}

	public String[] getInputCardTypeRemarkArray() {
		return inputCardTypeRemarkArray;
	}

	public void setInputCardTypeRemarkArray(String[] inputCardTypeRemarkArray) {
		this.inputCardTypeRemarkArray = inputCardTypeRemarkArray;
	}

	public Integer getIsUnbindRestricted() {
		return isUnbindRestricted;
	}

	public void setIsUnbindRestricted(Integer isUnbindRestricted) {
		this.isUnbindRestricted = isUnbindRestricted;
	}

	public Integer getUnbindRestrictedDayNum() {
		return unbindRestrictedDayNum;
	}

	public void setUnbindRestrictedDayNum(Integer unbindRestrictedDayNum) {
		this.unbindRestrictedDayNum = unbindRestrictedDayNum;
	}

	public String getUnbindRestrictedTip() {
		return unbindRestrictedTip;
	}

	public void setUnbindRestrictedTip(String unbindRestrictedTip) {
		this.unbindRestrictedTip = unbindRestrictedTip;
	}

	/**
	 * 得到默认的绑卡规则
	 * @param hospitalId
	 * @return
	 */
	public static RuleTiedCard getDefaultRule(String hospitalId) {
		RuleTiedCard tiedCard = new RuleTiedCard();
		tiedCard.setHospitalId(hospitalId);
		tiedCard.setVisitingPersonType("1");
		tiedCard.setCertificatesType("1");
		tiedCard.setCertificatesTypeArray(new String[] { "1" });
		tiedCard.setCardType("1");
		tiedCard.setCardTypeArray(new String[] { "1" });
		tiedCard.setIsShowCardNo(1);
		tiedCard.setIsShowOnlineFiling(1);
		tiedCard.setVerifyConditionType("1");
		tiedCard.setVerifyConditionTypeArray(new String[] { "1" });
		tiedCard.setInputCardNoTipArray(new String[1]);
		tiedCard.setInputCardTypeRemarkArray(new String[1]);
		tiedCard.setIsUnbindRestricted(0);
		return tiedCard;
	}

}