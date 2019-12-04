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
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.commons.constants.biz.HospitalConstant;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.rule.RulePush;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.rule.dao.RulePushDao;
import com.yxw.platform.rule.service.RulePushService;

/**
 * @Package: com.yxw.platform.rule.service.impl
 * @ClassName: RuleEditServiceImpl
 * @Statement: <p>
 *             推送规则Service 实现类
 *             </p>
 * @JDK version used: 1.6
 * @Author: luob
 * @Create Date: 2015-7-3
 * @Version: 1.0
 */
@Service(value = "rulePushService")
public class RulePushServiceImpl extends BaseServiceImpl<RulePush, String> implements RulePushService {

	private Logger logger = LoggerFactory.getLogger(RulePushServiceImpl.class);

	@Autowired
	private RulePushDao rulePushDao;

	@Autowired
	private HospitalService hospitalService;

	@Override
	protected BaseDao<RulePush, String> getDao() {
		return rulePushDao;
	}

	@Override
	public String saveRulePush(RulePush entity) {
		String rulePushId = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", entity.getHospitalId());
		params.put("platformType", entity.getPlatformType());
		RulePush rulePush = findByHospitalId(params);
		Hospital hospital = hospitalService.findById(entity.getHospitalId());
		if (rulePush != null) {
			entity.setId(rulePush.getId());
			update(entity);
			rulePushId = entity.getId();
			hospital.setLastHandler(entity.getEpName());
			hospital.setLastHandlerId(entity.getEp());
		} else {
			rulePushId = entity.getId();
			if (StringUtils.isBlank(rulePushId)) {
				rulePushId = rulePushDao.add(entity);
				hospital.setIsPublishRule(HospitalConstant.HOSPITAL_RULE_NOT_PUBLISH);
			} else {
				rulePushDao.update(entity);
			}
			hospital.setLastHandler(entity.getCpName());
			hospital.setLastHandlerId(entity.getCp());
			if (logger.isDebugEnabled()) {
				logger.debug("save rulePush :{}", rulePushId);
			}
		}
		hospital.setRuleLastEditTime(new Date());
		hospitalService.updateRuleModifyTime(hospital);
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRulePush(entity);
		return rulePushId;
	}

	@Override
	public RulePush findByHospitalId(Map<String, Object> params) {
		return rulePushDao.findByHospitalId(params);
	}

	@Override
	public void update(RulePush entity) {
		super.update(entity);
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRulePush(entity);
	}
}
