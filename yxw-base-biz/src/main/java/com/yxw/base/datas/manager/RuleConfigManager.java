/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-3</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.base.datas.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.rule.RuleClinic;
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.entity.platform.rule.RuleFriedExpress;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.entity.platform.rule.RuleInHospital;
import com.yxw.commons.entity.platform.rule.RuleOnlineFiling;
import com.yxw.commons.entity.platform.rule.RulePayment;
import com.yxw.commons.entity.platform.rule.RulePush;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.entity.platform.rule.RuleTiedCard;
import com.yxw.commons.entity.platform.rule.RuleUserCenter;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.platform.datas.manager
 * @ClassName: RuleConfigManager
 * @Statement:
 * 			<p>
 *             配置规则管理
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-3
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RuleConfigManager {

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	// private HospitalRuleCache ruleCache =
	// SpringContextHolder.getBean(HospitalRuleCache.class);

	public void initRuleData() {
		// ruleCache.initRuleCache();
	}

	/**
	 * 根据医院Code得到编辑规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public RuleEdit getRuleEditByHospitalCode(String hospitalCode) {
		RuleEdit rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleEditByHospitalCode", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleEdit) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院id得到编辑规则
	 * 
	 * @param hospitalId
	 * @return
	 */
	public RuleEdit getRuleEditByHospitalId(String hospitalId) {
		RuleEdit rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleEditByHospitalId", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleEdit) objects.get(0);
		}

		return rule;
	}

	public RuleUserCenter getRuleUserCenterByHospitalCode(String hospitalCode) {
		RuleUserCenter rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleUserCenterByHospitalCode", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleUserCenter) objects.get(0);
		}

		return rule;
	}

	public RuleUserCenter getRuleUserCenterByHospitalId(String hospitalId) {
		RuleUserCenter rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleUserCenterByHospitalId", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleUserCenter) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院Code得到在线建档规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public RuleOnlineFiling getRuleOnlineFilingByHospitalCode(String hospitalCode) {
		RuleOnlineFiling rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleOFByHospitalCode", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleOnlineFiling) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院Id得到在线建档规则
	 * 
	 * @param hospitalId
	 * @return
	 */
	public RuleOnlineFiling getRuleOnlineFilingByHospitalId(String hospitalId) {
		RuleOnlineFiling rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleOFByHospitalId", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleOnlineFiling) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院Code得到绑卡规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public RuleTiedCard getRuleTiedCardByHospitalCode(String hospitalCode) {
		RuleTiedCard rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleTiedCardByHospitalCode", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleTiedCard) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院Id得到绑卡规则
	 * 
	 * @param hospitalId
	 * @return
	 */
	public RuleTiedCard getRuleTiedCardByHospitalId(String hospitalId) {
		RuleTiedCard rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleTiedCardByHospitalId", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleTiedCard) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院Code得到挂号规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public RuleRegister getRuleRegisterByHospitalCode(String hospitalCode) {
		RuleRegister rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleRegisterByHospitalCode", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleRegister) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院Id得到挂号规则
	 * 
	 * @param hospitalId
	 * @return
	 */
	public RuleRegister getRuleRegisterByHospitalId(String hospitalId) {
		RuleRegister rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleRegisterByHospitalId", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleRegister) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院Code得到查询规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public RuleQuery getRuleQueryByHospitalCode(String hospitalCode) {
		RuleQuery rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleQueryByHospitalCode", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleQuery) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院Id得到查询规则
	 * 
	 * @param hospitalId
	 * @return
	 */
	public RuleQuery getRuleQueryByHospitalId(String hospitalId) {
		RuleQuery rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleQueryByHospitalId", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleQuery) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院Code得到支付方式配置规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public RulePayment getRulePaymentByHospitalCode(String hospitalCode) {
		RulePayment rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRulePaymentByHospitalCode", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RulePayment) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院Id得到推送规则
	 * 
	 * @param hospitalId
	 * @param platformType
	 * @return
	 */
	public RulePush getRulePushByHospitalId(String hospitalId, String platformType) {
		RulePush rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		params.add(platformType);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRulePushByHospitalId", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RulePush) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院Code得到推送规则
	 * 
	 * @param hospitalCode
	 * @param platformType
	 * @return
	 */
	public RulePush getRulePushByHospitalCode(String hospitalCode, String platformType) {
		RulePush rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(platformType);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRulePushByHospitalCode", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RulePush) objects.get(0);
		}

		return rule;
	}

	/**
	 * 根据医院Id得到缴费规则
	 * 
	 * @param hospitalId
	 * @return
	 */
	public RulePayment getRulePaymentByHospitalId(String hospitalId) {
		RulePayment rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRulePaymentByHospitalId", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RulePayment) objects.get(0);
		}

		return rule;
	}

	/**
	 * 
	 * 根据医院Code得到缴费规则
	 * @param hospitalCode
	 * @return
	 */
	public RuleClinic getRuleClinicByHospitalCode(String hospitalCode) {
		RuleClinic ruleClinic = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleClinicByHospitalCode", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			ruleClinic = (RuleClinic) objects.get(0);
		}

		return ruleClinic;
	}

	/**
	 * 根据医院Id得到缴费规则
	 * 
	 * @param hospitalId
	 * @return
	 */
	public RuleClinic getRuleClinicByHospitalId(String hospitalId) {
		RuleClinic rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleClinicByHospitalId", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			rule = (RuleClinic) objects.get(0);
		}

		return rule;
	}

	/**
	 * 更新查询规则
	 * 
	 * @param query
	 */
	public void updateRuleQuery(RuleQuery query) {
		List<Object> params = new ArrayList<Object>();
		params.add(query);
		serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "updateRuleQuery", params);
	}

	/**
	 * 更新编辑规则
	 * 
	 * @param query
	 */
	public void updateRuleEdit(RuleEdit edit) {
		List<Object> params = new ArrayList<Object>();
		params.add(edit);
		serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "updateRuleEdit", params);
	}

	/**
	 * 更新个人中心规则
	 * 
	 * @param query
	 */
	public void updateRuleUserCenter(RuleUserCenter ruleUserCenter) {
		List<Object> params = new ArrayList<Object>();
		params.add(ruleUserCenter);
		serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "updateRuleUserCenter", params);
	}

	/**
	 * 更新在线建档规则
	 * 
	 * @param query
	 */
	public void updateRuleOnlineFiling(RuleOnlineFiling onlineFiling) {
		List<Object> params = new ArrayList<Object>();
		params.add(onlineFiling);
		serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "updateRuleOnlineFiling", params);
	}

	/**
	 * 更新绑卡规则
	 * 
	 * @param query
	 */
	public void updateRuleTiedCard(RuleTiedCard tiedCard) {
		List<Object> params = new ArrayList<Object>();
		params.add(tiedCard);
		serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "updateRuleTiedCard", params);
	}

	/**
	 * 更新挂号规则
	 * 
	 * @param query
	 */
	public void updateRuleRegister(RuleRegister register) {
		List<Object> params = new ArrayList<Object>();
		params.add(register);
		serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "updateRuleRegister", params);
	}

	/**
	 * 更新支付方式规则
	 * 
	 * @param query
	 */
	public void updateRulePayment(RulePayment payment) {
		List<Object> params = new ArrayList<Object>();
		params.add(payment);
		serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "updateRulePayment", params);
	}

	/**
	 * 根据医院Id得到代煎规则
	 * 
	 * @param hospitalId
	 * @return
	 */
	public RuleFriedExpress getRuleFriedExpressByHospitalId(String hospitalId) {
		RuleFriedExpress ruleFriedExpress = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleFriedExpressByHospitalId", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			ruleFriedExpress = (RuleFriedExpress) objects.get(0);
		}

		return ruleFriedExpress;
	}

	/**
	 * 根据医院hospitalCode得到代煎规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public RuleFriedExpress getRuleFriedExpressByHospitalCode(String hospitalCode) {
		RuleFriedExpress ruleFriedExpress = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleFriedExpressByHospitalCode", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			ruleFriedExpress = (RuleFriedExpress) objects.get(0);
		}

		return ruleFriedExpress;
	}

	/**
	 * 根据医院Id得到住院规则
	 * 
	 * @param hospitalId
	 * @return
	 */
	public RuleInHospital getRuleInHospitalByHospitalId(String hospitalId) {
		RuleInHospital ruleInHospital = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleInHospitalByHospitalId", params);
		if (CollectionUtils.isNotEmpty(objects)) {
			ruleInHospital = (RuleInHospital) objects.get(0);
		}

		return ruleInHospital;
	}

	/**
	 * 更新门诊缴费规则
	 * 
	 * @param query
	 */
	public void updateRuleClinic(RuleClinic clinic) {
		List<Object> params = new ArrayList<Object>();
		params.add(clinic);
		serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "updateRuleClinic", params);
	}

	/**
	 * 更新推送规则
	 * 
	 * @param query
	 */
	public void updateRulePush(RulePush rulePush) {
		List<Object> params = new ArrayList<Object>();
		params.add(rulePush);
		serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "updateRulePush", params);
	}

	/**
	 * 更新His基础数据规则
	 * @param ruleHisData
	 */
	public void updateRuleHisData(RuleHisData ruleHisData) {
		List<Object> params = new ArrayList<Object>();
		params.add(ruleHisData);
		serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "updateRuleHisData", params);
	}

	/**
	 * 根据医院code得到HIS数据规则
	 * @param hospitalCode
	 * @return
	 */
	public RuleHisData getRuleHisDataByHospitalId(String hospitalId) {
		RuleHisData ruleHisData = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleHisDataByHospitalId", params);

		if (CollectionUtils.isNotEmpty(objects)) {
			ruleHisData = (RuleHisData) objects.get(0);
		}

		return ruleHisData;
	}

	/**
	 * 根据医院code得到HIS数据规则
	 * @param hospitalCode
	 * @return
	 */
	public RuleHisData getRuleHisDataByHospitalCode(String hospitalCode) {
		RuleHisData ruleHisData = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleHisDataByHospitalCode", params);

		if (CollectionUtils.isNotEmpty(objects)) {
			ruleHisData = (RuleHisData) objects.get(0);
		}

		return ruleHisData;
	}

	/**
	 * 更新代煎配送规则
	 * @param friedExpress
	 */
	public void updateRuleFriedExpress(RuleFriedExpress friedExpress) {
		//		ruleCache.updateRuleFriedExpress(friedExpress);
		List<Object> params = new ArrayList<Object>();
		params.add(friedExpress);
		serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "updateRuleFriedExpress", params);
	}

	/**
	 * 更新住院规则
	 * @param ruleInHospital
	 */
	public void updateRuleInHospital(RuleInHospital ruleInHospital) {
		//		ruleCache.updateRuleInHospital(ruleInHospital);
		List<Object> params = new ArrayList<Object>();
		params.add(ruleInHospital);
		serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "updateRuleInHospital", params);
	}
}
