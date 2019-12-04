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

import com.yxw.commons.entity.platform.rule.RuleClinic;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * 支付方式配置规则 Dao
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年4月27日
 */
public interface RuleClinicDao extends BaseDao<RuleClinic, String> {
	/**
	 * 根据医院id查找编辑规则
	 * @param hospitalId
	 * @return
	 */
	public RuleClinic findByHospitalId(String hospitalId);
}
