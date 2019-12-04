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
package com.yxw.platform.rule.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.commons.constants.biz.HospitalConstant;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.rule.dao.RuleQueryDao;
import com.yxw.platform.rule.service.RuleQueryService;

/**
 * @Package: com.yxw.platform.rule.service.impl
 * @ClassName: RuleQueryServiceImpl
 * @Statement: <p>
 *             查询规则 Service 实现类
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "ruleQueryService")
public class RuleQueryServiceImpl extends BaseServiceImpl<RuleQuery, String> implements RuleQueryService {

	private Logger logger = LoggerFactory.getLogger(RuleQueryServiceImpl.class);

	@Autowired
	private RuleQueryDao ruleQueryDao;

	@Autowired
	private HospitalService hospitalService;

	@Override
	protected BaseDao<RuleQuery, String> getDao() {
		// TODO Auto-generated method stub
		return ruleQueryDao;
	}

	/**
	 * 保存
	 * 
	 * @param ruleEdit
	 * @return
	 */
	public String saveRuleQuery(RuleQuery entity) {
		// TODO Auto-generated method stub
		String ruleId = null;
		RuleQuery query = findByHospitalId(entity.getHospitalId());
		Hospital hospital = hospitalService.findById(entity.getHospitalId());
		if (query != null) {
			entity.setId(query.getId());
			update(entity);
			ruleId = entity.getId();
			hospital.setLastHandlerId(entity.getEp());
			hospital.setLastHandler(entity.getEpName());
		} else {
			ruleId = entity.getId();
			if (StringUtils.isBlank(ruleId)) {
				ruleId = ruleQueryDao.add(entity);
				hospital.setIsPublishRule(HospitalConstant.HOSPITAL_RULE_NOT_PUBLISH);
			} else {
				ruleQueryDao.update(entity);
			}
			hospital.setLastHandlerId(entity.getCp());
			hospital.setLastHandler(entity.getCpName());
			// hospital.setRuleLastEditTime(new Date());
			// hospital.setLastHandlerId(ruleId);
			// hospitalService.update(hospital);
			if (logger.isDebugEnabled()) {
				logger.debug("save RuleQuery :{}", entity);
			}
		}
		hospital.setRuleLastEditTime(new Date());
		hospitalService.updateForRuleEditTime(hospital);
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleQuery(entity);
		return ruleId;
	}

	@Override
	public void update(RuleQuery entity) {
		// TODO Auto-generated method stub
		super.update(entity);

		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleQuery(entity);
	}

	@Override
	public RuleQuery findByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub
		return ruleQueryDao.findByHospitalId(hospitalId);
	}

}
