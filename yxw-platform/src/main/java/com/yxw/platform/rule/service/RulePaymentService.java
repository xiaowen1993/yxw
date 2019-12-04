package com.yxw.platform.rule.service;

import com.yxw.commons.entity.platform.rule.RulePayment;
import com.yxw.framework.mvc.service.BaseService;

/**
 * @Package: com.yxw.platform.rule.service
 * @ClassName: RuleEditService
 * @Statement: <p>支付方式配置规则 Service</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface RulePaymentService extends BaseService<RulePayment, String> {
	/**
	 * 保存
	 * @param rulePayment
	 * @return
	 */
	public String saveRulePayment(RulePayment rulePayment);

	/**
	 * 根据医院id查找编辑规则
	 * @param hospitalId
	 * @return
	 */
	public RulePayment findByHospitalId(String hospitalId);
}
