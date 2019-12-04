package com.yxw.commons.entity.platform.rule;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.rule.entity
 * @ClassName: RuleInHospital
 * @Statement: <p>
 *            住院规则实体类
 *             </p>
 * @Author: Luob
 * @Create Date: 2016-5-26
 */
public class RuleInHospital extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2366986579993088391L;

	/**
	 * 关联医院id
	 */
	private String hospitalId;

	/**
	 * 住院信息绑定是否需要住院人身份证  1：有 0：没有
	 */
	private Integer ifBindNeedIDCard;

	/**
	 * 住院信息绑定是否需要住院号  1：有 0：没有
	 */
	private Integer ifBindNeedAdmissionNo;

	/**
	 * 住院是否有分院 0-无   1-有
	 * 注：全局规则的【是否有分院】是指定进入门诊/报告/绑卡/记录/个人中心等页面是否先进入选择分院页面，
	 * 挂号配置的【挂号是否有分院】是指定进入挂号页面前是否进入选择分院页面
	 * 而此处的【住院是否有分院】同样指定是否先进入选择分院页面
	 * */
	private Integer hasBranch;

	/**
	 * 是否有弹框提示 -- 进入时
	 * 
	 * */
	private Integer isTip;

	/**
	 * 弹框提示内容 -- 进入时
	 * */
	private String tipContent;

	/**
	 * 住院押金补缴最低缴费金额
	 */
	private Double hospitalDepositMinMoney;

	/**
	 * 住院押金补缴最高缴费金额
	 */
	private Double hospitalDepositMaxMoney;
	
	/**
	 * 住院押金补缴提示语
	 */
	private String hospitalDepositPaymentsTip;
	
	/**
	 * 押金补缴失败提示语
	 */
	private String depositPaymentFailedTip;

	/**
	 * 清账缴费提示语（支付页面的）
	 */
	// private String clearAccountPaymentsTip;

	/**
	 * 清账缴费失败提示语 
	 */
	// private String clearAccountPaymentFailTip;

	/**
	 * 清账提示语
	 */
	private String clearAccountTip;
	
	/**
	 * 清帐确认下单提示语配置
	 */
	private String clearAccountPreOrderTip;

	public RuleInHospital() {
		super();
	}

	public RuleInHospital(String hospitalId) {
		super();
		this.hospitalId = hospitalId;
	}

	public Integer getIfBindNeedIDCard() {
		return ifBindNeedIDCard;
	}

	public void setIfBindNeedIDCard(Integer ifBindNeedIDCard) {
		this.ifBindNeedIDCard = ifBindNeedIDCard;
	}

	public Integer getIfBindNeedAdmissionNo() {
		return ifBindNeedAdmissionNo;
	}

	public void setIfBindNeedAdmissionNo(Integer ifBindNeedAdmissionNo) {
		this.ifBindNeedAdmissionNo = ifBindNeedAdmissionNo;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Integer getHasBranch() {
		return hasBranch;
	}

	public void setHasBranch(Integer hasBranch) {
		this.hasBranch = hasBranch;
	}

	public Integer getIsTip() {
		return isTip;
	}

	public void setIsTip(Integer isTip) {
		this.isTip = isTip;
	}

	public String getTipContent() {
		return tipContent;
	}

	public void setTipContent(String tipContent) {
		this.tipContent = tipContent;
	}

	/**
	 * 住院押金补缴最低缴费金额
	 * @return
	 */
	public Double getHospitalDepositMinMoney() {
		return hospitalDepositMinMoney;
	}

	public void setHospitalDepositMinMoney(Double hospitalDepositMinMoney) {
		this.hospitalDepositMinMoney = hospitalDepositMinMoney;
	}

	/**
	 * 住院押金补缴最高缴费金额
	 * @param hospitalId
	 * @return
	 */
	public Double getHospitalDepositMaxMoney() {
		return hospitalDepositMaxMoney;
	}

	public void setHospitalDepositMaxMoney(Double hospitalDepositMaxMoney) {
		this.hospitalDepositMaxMoney = hospitalDepositMaxMoney;
	}

	public static RuleInHospital getDefaultRule(String hospitalId) {
		RuleInHospital rule = new RuleInHospital(hospitalId);
		rule.setIfBindNeedAdmissionNo(1);
		rule.setIfBindNeedIDCard(1);
		rule.setHasBranch(1);
		rule.setIsTip(0);
		return rule;
	}

	public String getHospitalDepositPaymentsTip() {
		return hospitalDepositPaymentsTip;
	}

	public void setHospitalDepositPaymentsTip(String hospitalDepositPaymentsTip) {
		this.hospitalDepositPaymentsTip = hospitalDepositPaymentsTip;
	}

	public String getDepositPaymentFailedTip() {
		return depositPaymentFailedTip;
	}

	public void setDepositPaymentFailedTip(String depositPaymentFailedTip) {
		this.depositPaymentFailedTip = depositPaymentFailedTip;
	}

	public String getClearAccountTip() {
		return clearAccountTip;
	}

	public void setClearAccountTip(String clearAccountTip) {
		this.clearAccountTip = clearAccountTip;
	}

	public String getClearAccountPreOrderTip() {
		return clearAccountPreOrderTip;
	}

	public void setClearAccountPreOrderTip(String clearAccountPreOrderTip) {
		this.clearAccountPreOrderTip = clearAccountPreOrderTip;
	}
}