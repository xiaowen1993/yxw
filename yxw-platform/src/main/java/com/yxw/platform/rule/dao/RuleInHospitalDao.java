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
package com.yxw.platform.rule.dao;

import com.yxw.commons.entity.platform.rule.RuleInHospital;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.platform.rule.dao
 * @ClassName: RuleRegisterDao
 * @Statement: <p>挂号规则 Dao</p>
 */
public interface RuleInHospitalDao extends BaseDao<RuleInHospital, String> {
	/**
	 * 根据医院id查找编辑规则
	 * @param hospitalId
	 * @return
	 */
	public RuleInHospital findByHospitalId(String hospitalId);
}
