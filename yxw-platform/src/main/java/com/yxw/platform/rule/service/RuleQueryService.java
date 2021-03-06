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

import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.framework.mvc.service.BaseService;

/**
 * @Package: com.yxw.platform.rule.service
 * @ClassName: RuleQueryService
 * @Statement: <p>查询规则 Service</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface RuleQueryService extends BaseService<RuleQuery, String> {
	/**
	 * 保存
	 * @param ruleEdit
	 * @return
	 */
	public String saveRuleQuery(RuleQuery ruleQuery);

	/**
	 * 根据医院id查找编辑规则
	 * @param hospitalId
	 * @return
	 */
	public RuleQuery findByHospitalId(String hospitalId);
}
