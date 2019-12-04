package com.yxw.platform.rule.service;

import com.yxw.commons.entity.platform.rule.RuleClinic;
import com.yxw.framework.mvc.service.BaseService;

/**
 * 门诊缴费规则
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年4月27日
 */
public interface RuleClinicService extends BaseService<RuleClinic, String> {
	/**
	 * 保存
	 * @param ruleClinic
	 * @return
	 */
	public String saveRuleClinic(RuleClinic ruleClinic);

	/**
	 * 根据医院id查找编辑规则
	 * @param hospitalId
	 * @return
	 */
	public RuleClinic findByHospitalId(String hospitalId);
}
