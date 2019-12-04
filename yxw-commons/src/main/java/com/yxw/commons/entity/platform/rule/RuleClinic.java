package com.yxw.commons.entity.platform.rule;

import com.yxw.base.entity.BaseEntity;

/**
 * 门诊缴费规则
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年4月27日
 */
public class RuleClinic extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7580208373882535754L;

	/**
	 * 关联医院ID
	 */
	private String hospitalId;

	/**
	 * 门诊缴费是否走医保结算流程  1：是   0：否
	 */
	private Integer isBasedOnMedicalInsurance;
	/**
	 * 门诊缴费是否支持合并支付  1：支持   0：不支持
	 */
	private Integer isSupportCombinedPayments;

	/**
	 * 门诊缴费是否支持医保结算  1：支持     0：不支持
	 */
	private Integer isSupportHealthPayments;

	/**
	 * 门诊缴费是支持不允许支付订单 1：支持 0：不支持
	 */
	private Integer isSupportForbiddenPayment;

	/**
	 * 门诊缴费支持 不允许支付提示
	 */
	private String supportForbiddenPaymentTips;

	/**
	 * 门诊代缴费提示语
	 */
	private String outpatientServicePayTips;

	/**
	 * 门诊缴费不支持医保结算提示语
	 */
	private String notSupportHealthPaymentsTip;

	/**
	 * 门诊缴费支持医保结算提示语
	 */
	private String supportHealthPaymentsTip;

	/**
	 * 门诊缴费失败提示语
	 */
	private String outpatientPaymentFailedTip;

	/**
	 * 显示门诊待缴费详情方式  0 按明细方式显示, 1 按费别方式显示
	 */
	private Integer showClinicPayDetailStyle;

	/**
	 * 门诊预结算样式 0深圳   1汕头
	 * */
	private Integer presettleStyle;

	/**
	 * 确认缴费弹框提示语
	 * */
	private String confirmTipMedicareWechat;
	private String confirmTipMedicareAlipay;
	private String confirmTipSelfPayWechat;
	private String confirmTipSelfPayAlipay;

	/**
	 * 确认缴费是否弹框 0-否 1-是
	 * */
	private Integer isconfirmTipMedicareWechat;
	private Integer isconfirmTipMedicareAlipay;
	private Integer isconfirmTipSelfPayWechat;
	private Integer isconfirmTipSelfPayAlipay;

	/**
	 * his支付异常后退费的延迟时间（分钟）
	 */
	private Integer refundDelayAfterException;

	/**
	 * 门诊缴费支付前提示语
	 */
	private String clinicPrePayWarmTip;

	public RuleClinic() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RuleClinic(String hospitalId) {
		super();
		this.hospitalId = hospitalId;

	}

	public RuleClinic(String hospitalId, Integer isSupportCombinedPayments, Integer isSupportHealthPayments, String notSupportHealthPaymentsTip,
			String supportHealthPaymentsTip, String outpatientPaymentFailedTip, String outpatientServicePayTips, Integer isBasedOnMedicalInsurance) {
		super();
		this.hospitalId = hospitalId;
		this.isSupportCombinedPayments = isSupportCombinedPayments;
		this.isSupportHealthPayments = isSupportHealthPayments;
		this.notSupportHealthPaymentsTip = notSupportHealthPaymentsTip;
		this.supportHealthPaymentsTip = supportHealthPaymentsTip;
		this.outpatientPaymentFailedTip = outpatientPaymentFailedTip;
		this.outpatientServicePayTips = outpatientServicePayTips;
		this.isBasedOnMedicalInsurance = isBasedOnMedicalInsurance;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId == null ? null : hospitalId.trim();
	}

	/**
	 * 门诊缴费是否支持合并支付  1：支持   0：不支持
	 * @return
	 */
	public Integer getIsSupportCombinedPayments() {
		return isSupportCombinedPayments;
	}

	public void setIsSupportCombinedPayments(Integer isSupportCombinedPayments) {
		this.isSupportCombinedPayments = isSupportCombinedPayments;
	}

	/**
	 * 门诊缴费是否支持医保结算  1：支持     0：不支持
	 * @return
	 */
	public Integer getIsSupportHealthPayments() {
		return isSupportHealthPayments;
	}

	public void setIsSupportHealthPayments(Integer isSupportHealthPayments) {
		this.isSupportHealthPayments = isSupportHealthPayments;
	}

	/**
	 * 门诊缴费不支持医保结算提示语
	 * @return
	 */
	public String getNotSupportHealthPaymentsTip() {
		return notSupportHealthPaymentsTip;
	}

	public void setNotSupportHealthPaymentsTip(String notSupportHealthPaymentsTip) {
		this.notSupportHealthPaymentsTip = notSupportHealthPaymentsTip == null ? null : notSupportHealthPaymentsTip.trim();
	}

	/**
	 * 门诊缴费支持医保结算提示语
	 * @return
	 */
	public String getSupportHealthPaymentsTip() {
		return supportHealthPaymentsTip;
	}

	public void setSupportHealthPaymentsTip(String supportHealthPaymentsTip) {
		this.supportHealthPaymentsTip = supportHealthPaymentsTip == null ? null : supportHealthPaymentsTip.trim();
	}

	/**
	 * 门诊缴费失败提示语
	 * @return
	 */
	public String getOutpatientPaymentFailedTip() {
		return outpatientPaymentFailedTip;
	}

	public void setOutpatientPaymentFailedTip(String outpatientPaymentFailedTip) {
		this.outpatientPaymentFailedTip = outpatientPaymentFailedTip == null ? null : outpatientPaymentFailedTip.trim();
	}


	/**
	 * 得到默认的缴费规则
	 * @param hospitalId
	 * @return
	 */
	public static RuleClinic getDefaultRule(String hospitalId) {
		RuleClinic ruleClinic = new RuleClinic(hospitalId);
		ruleClinic.setIsBasedOnMedicalInsurance(0);
		ruleClinic.setIsSupportCombinedPayments(1);
		ruleClinic.setIsSupportHealthPayments(1);
		ruleClinic.setIsSupportForbiddenPayment(0);
		ruleClinic.setShowClinicPayDetailStyle(0);
		ruleClinic.setPresettleStyle(0);
		return ruleClinic;
	}

	public String getClinicPrePayWarmTip() {
		return clinicPrePayWarmTip;
	}

	public void setClinicPrePayWarmTip(String clinicPrePayWarmTip) {
		this.clinicPrePayWarmTip = clinicPrePayWarmTip == null ? null : clinicPrePayWarmTip.trim();
	}

	/**
	 * 门诊缴费是否走医保结算流程 1：是 0：否
	 * @param hospitalId
	 * @return
	 */
	public Integer getIsBasedOnMedicalInsurance() {
		return isBasedOnMedicalInsurance;
	}

	public void setIsBasedOnMedicalInsurance(Integer isBasedOnMedicalInsurance) {
		this.isBasedOnMedicalInsurance = isBasedOnMedicalInsurance;
	}

	/**
	 * 门诊缴费是支持不允许支付订单 1：支持 0：不支持
	 * @param hospitalId
	 * @return
	 */
	public Integer getIsSupportForbiddenPayment() {
		return isSupportForbiddenPayment;
	}

	public void setIsSupportForbiddenPayment(Integer isSupportForbiddenPayment) {
		this.isSupportForbiddenPayment = isSupportForbiddenPayment;
	}

	/**
	 * 门诊缴费支持 不允许支付提示
	 * @param hospitalId
	 * @return
	 */
	public String getSupportForbiddenPaymentTips() {
		return supportForbiddenPaymentTips;
	}

	public void setSupportForbiddenPaymentTips(String supportForbiddenPaymentTips) {
		this.supportForbiddenPaymentTips = supportForbiddenPaymentTips;
	}

	/**
	 * 门诊代缴费提示语
	 * @param hospitalId
	 * @return
	 */
	public String getOutpatientServicePayTips() {
		return outpatientServicePayTips;
	}

	public void setOutpatientServicePayTips(String outpatientServicePayTips) {
		this.outpatientServicePayTips = outpatientServicePayTips;
	}

	/**
	 * 显示门诊待缴费详情方式 0 按明细方式显示, 1 按费别方式显示
	 * @param hospitalId
	 * @return
	 */
	public Integer getShowClinicPayDetailStyle() {
		return showClinicPayDetailStyle;
	}

	public void setShowClinicPayDetailStyle(Integer showClinicPayDetailStyle) {
		this.showClinicPayDetailStyle = showClinicPayDetailStyle;
	}

	/**
	 * 门诊预结算样式 0深圳 1汕头
	 * @param hospitalId
	 * @return
	 */
	public Integer getPresettleStyle() {
		return presettleStyle;
	}

	public void setPresettleStyle(Integer presettleStyle) {
		this.presettleStyle = presettleStyle;
	}

	/**
	 * 确认缴费弹框提示语
	 * @param hospitalId
	 * @return
	 */
	public String getConfirmTipMedicareWechat() {
		return confirmTipMedicareWechat;
	}

	public void setConfirmTipMedicareWechat(String confirmTipMedicareWechat) {
		this.confirmTipMedicareWechat = confirmTipMedicareWechat;
	}

	/**
	 * 确认缴费弹框提示语
	 * @param hospitalId
	 * @return
	 */
	public String getConfirmTipMedicareAlipay() {
		return confirmTipMedicareAlipay;
	}

	public void setConfirmTipMedicareAlipay(String confirmTipMedicareAlipay) {
		this.confirmTipMedicareAlipay = confirmTipMedicareAlipay;
	}

	/**
	 * 确认缴费弹框提示语
	 * @param hospitalId
	 * @return
	 */
	public String getConfirmTipSelfPayWechat() {
		return confirmTipSelfPayWechat;
	}

	public void setConfirmTipSelfPayWechat(String confirmTipSelfPayWechat) {
		this.confirmTipSelfPayWechat = confirmTipSelfPayWechat;
	}

	/**
	 * 确认缴费弹框提示语
	 * @param hospitalId
	 * @return
	 */
	public String getConfirmTipSelfPayAlipay() {
		return confirmTipSelfPayAlipay;
	}

	public void setConfirmTipSelfPayAlipay(String confirmTipSelfPayAlipay) {
		this.confirmTipSelfPayAlipay = confirmTipSelfPayAlipay;
	}

	/**
	 * 确认缴费是否弹框 0-否 1-是
	 * @param hospitalId
	 * @return
	 */
	public Integer getIsconfirmTipMedicareWechat() {
		return isconfirmTipMedicareWechat;
	}

	public void setIsconfirmTipMedicareWechat(Integer isconfirmTipMedicareWechat) {
		this.isconfirmTipMedicareWechat = isconfirmTipMedicareWechat;
	}

	/**
	 * 确认缴费是否弹框 0-否 1-是
	 * @param hospitalId
	 * @return
	 */
	public Integer getIsconfirmTipMedicareAlipay() {
		return isconfirmTipMedicareAlipay;
	}

	public void setIsconfirmTipMedicareAlipay(Integer isconfirmTipMedicareAlipay) {
		this.isconfirmTipMedicareAlipay = isconfirmTipMedicareAlipay;
	}

	/**
	 * 确认缴费是否弹框 0-否 1-是
	 * @param hospitalId
	 * @return
	 */
	public Integer getIsconfirmTipSelfPayWechat() {
		return isconfirmTipSelfPayWechat;
	}

	public void setIsconfirmTipSelfPayWechat(Integer isconfirmTipSelfPayWechat) {
		this.isconfirmTipSelfPayWechat = isconfirmTipSelfPayWechat;
	}

	/**
	 * 确认缴费是否弹框 0-否 1-是
	 * @param hospitalId
	 * @return
	 */
	public Integer getIsconfirmTipSelfPayAlipay() {
		return isconfirmTipSelfPayAlipay;
	}

	public void setIsconfirmTipSelfPayAlipay(Integer isconfirmTipSelfPayAlipay) {
		this.isconfirmTipSelfPayAlipay = isconfirmTipSelfPayAlipay;
	}

	/**
	 * his支付异常后退费的延迟时间（分钟）
	 * @param hospitalId
	 * @return
	 */
	public Integer getRefundDelayAfterException() {
		return refundDelayAfterException;
	}

	public void setRefundDelayAfterException(Integer refundDelayAfterException) {
		this.refundDelayAfterException = refundDelayAfterException;
	}

}