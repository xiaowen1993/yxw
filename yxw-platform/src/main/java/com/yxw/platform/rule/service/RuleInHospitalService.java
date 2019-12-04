/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月15日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.rule.service;

import com.yxw.commons.entity.platform.rule.RuleInHospital;
import com.yxw.framework.mvc.service.BaseService;

/**
 * @Package: com.yxw.platform.rule.service
 * @ClassName: RuleInHospitalService
 * @Statement: <p>住院规则 Service</p>
 */
public interface RuleInHospitalService extends BaseService<RuleInHospital, String> {
	/**
	 * 保存
	 * @param ruleInHospital
	 * @return
	 */
	public String saveRuleInHospital(RuleInHospital ruleInHospital);

	/**
	 * 根据医院id查找编辑规则
	 * @param hospitalId
	 * @return
	 */
	public RuleInHospital findByHospitalId(String hospitalId);
}
