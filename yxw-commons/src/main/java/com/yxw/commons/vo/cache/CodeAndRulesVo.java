/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-5</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.cache;

import java.io.Serializable;

import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.entity.platform.rule.RuleOnlineFiling;
import com.yxw.commons.entity.platform.rule.RulePayment;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.entity.platform.rule.RuleTiedCard;

/**
 * @Package: com.yxw.cache.vo
 * @ClassName: CodeAndRulesVo
 * @Statement: <p>医院和规则的对应关系</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-5
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class CodeAndRulesVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8056882847606186089L;

	private String hospitalId;
	private String hospitalCode;
	private String hospitalName;

	/**
	 * 编辑规则
	 */
	private RuleEdit ruleEdit;

	/**
	 * 在线建档规则
	 */
	private RuleOnlineFiling onlineFiling;

	/**
	 * 绑卡规则
	 */
	private RuleTiedCard tiedCard;

	/**
	 * 挂号规则
	 */
	private RuleRegister register;

	/**
	 * 查询规则
	 */
	private RuleQuery query;

	/**
	 * 缴费规则
	 */
	private RulePayment payment;

	public CodeAndRulesVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CodeAndRulesVo(String hospitalId, String hospitalCode, String hospitalName, RuleEdit ruleEdit, RuleOnlineFiling onlineFiling,
			RuleTiedCard tiedCard, RuleRegister register, RuleQuery query, RulePayment payment) {
		super();
		this.hospitalId = hospitalId;
		this.hospitalCode = hospitalCode;
		this.hospitalName = hospitalName;
		this.ruleEdit = ruleEdit;
		this.onlineFiling = onlineFiling;
		this.tiedCard = tiedCard;
		this.register = register;
		this.query = query;
		this.payment = payment;
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

	public RuleEdit getRuleEdit() {
		return ruleEdit;
	}

	public void setRuleEdit(RuleEdit ruleEdit) {
		this.ruleEdit = ruleEdit;
	}

	public RuleOnlineFiling getOnlineFiling() {
		return onlineFiling;
	}

	public void setOnlineFiling(RuleOnlineFiling onlineFiling) {
		this.onlineFiling = onlineFiling;
	}

	public RuleTiedCard getTiedCard() {
		return tiedCard;
	}

	public void setTiedCard(RuleTiedCard tiedCard) {
		this.tiedCard = tiedCard;
	}

	public RuleRegister getRegister() {
		return register;
	}

	public void setRegister(RuleRegister register) {
		this.register = register;
	}

	public RuleQuery getQuery() {
		return query;
	}

	public void setQuery(RuleQuery query) {
		this.query = query;
	}

	public RulePayment getPayment() {
		return payment;
	}

	public void setPayment(RulePayment payment) {
		this.payment = payment;
	}
}
