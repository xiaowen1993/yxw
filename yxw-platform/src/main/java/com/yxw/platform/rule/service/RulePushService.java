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

import java.util.Map;

import com.yxw.commons.entity.platform.rule.RulePush;
import com.yxw.framework.mvc.service.BaseService;

/**
 * @Package: com.yxw.platform.rule.service
 * @ClassName: RulePushService
 * @Statement: <p>
 *             编辑规则 Service
 *             </p>
 * @JDK version used: 1.6
 * @Author: luob
 * @Create Date: 2015-7-3
 * @Version: 1.0
 */
public interface RulePushService extends BaseService<RulePush, String> {

	public String saveRulePush(RulePush rushPush);

	public RulePush findByHospitalId(Map<String, Object> params);
}
