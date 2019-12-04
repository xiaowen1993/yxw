/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-11-12</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.rule.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.commons.constants.biz.HospitalConstant;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.rule.RuleFriedExpress;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.rule.dao.RuleFriedExpressDao;
import com.yxw.platform.rule.service.RuleFriedExpressService;

/**
 * @Package: com.yxw.platform.rule.service.impl
 * @ClassName: RuleFriedExpressServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015-11-12
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service("ruleFriedExpressService")
public class RuleFriedExpressServiceImpl extends BaseServiceImpl<RuleFriedExpress, String> implements RuleFriedExpressService {
	private Logger logger = LoggerFactory.getLogger(RuleFriedExpressServiceImpl.class);

	@Autowired
	private RuleFriedExpressDao ruleFriedExpressDao;
	@Autowired
	private HospitalService hospitalService;

	/* (non-Javadoc)
	 * @see com.yxw.platform.rule.service.RuleFriedExpressService#saveRuleFriedExpress(com.yxw.platform.rule.entity.RuleFriedExpress)
	 */
	@Override
	public String saveRuleFriedExpress(RuleFriedExpress entity) {
		String ruleFriedExpressId = null;

		RuleFriedExpress ruleFriedExpressOld = findByHospitalId(entity.getHospitalId());
		Hospital hospital = hospitalService.findById(entity.getHospitalId());
		if (ruleFriedExpressOld != null) {
			entity.setId(ruleFriedExpressOld.getId());
			update(entity);
			hospital.setRuleLastEditTime(new Date());
			hospital.setLastHandlerId(entity.getEp());
			hospital.setLastHandler(entity.getEpName());
			hospitalService.update(hospital);
			return entity.getId();
		} else {
			ruleFriedExpressId = entity.getId();

			if (StringUtils.isBlank(ruleFriedExpressId)) {
				ruleFriedExpressId = ruleFriedExpressDao.add(entity);
				hospital.setIsPublishRule(HospitalConstant.HOSPITAL_RULE_NOT_PUBLISH);
			} else {
				ruleFriedExpressDao.update(entity);
			}

			hospital.setLastHandlerId(entity.getCp());
			hospital.setLastHandler(entity.getCpName());
			hospital.setRuleLastEditTime(new Date());
			hospitalService.updateForRuleEditTime(hospital);

			if (logger.isDebugEnabled()) {
				logger.debug("save saveRuleFriedExpress :{}", ruleFriedExpressId);
			}
		}

		updateCache(entity);
		return ruleFriedExpressId;
	}

	@Override
	public void update(RuleFriedExpress entity) {
		// TODO Auto-generated method stub
		super.update(entity);

		updateCache(entity);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.rule.service.RuleFriedExpressService#findByHospitalId(java.lang.String)
	 */
	@Override
	public RuleFriedExpress findByHospitalId(String hospitalId) {
		return ruleFriedExpressDao.findByHospitalId(hospitalId);
	}

	/**
	 * 更新缓存
	 * @param ruleFriedExpress
	 */
	private void updateCache(RuleFriedExpress ruleFriedExpress) {
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleFriedExpress(ruleFriedExpress);
	}

	/* (non-Javadoc)
	 * @see com.yxw.framework.mvc.service.impl.BaseServiceImpl#getDao()
	 */
	@Override
	protected BaseDao<RuleFriedExpress, String> getDao() {
		return ruleFriedExpressDao;
	}

}
