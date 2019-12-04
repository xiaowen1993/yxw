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
import com.yxw.commons.entity.platform.rule.RuleOnlineFiling;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.rule.dao.RuleOnlineFilingDao;
import com.yxw.platform.rule.service.RuleOnlineFilingService;

/**
 * @Package: com.yxw.platform.rule.service.impl
 * @ClassName: RuleEditServiceImpl
 * @Statement: <p>
 *             在线建档实现类
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "ruleOnlineFilingService")
public class RuleOnlineFilingServiceImpl extends BaseServiceImpl<RuleOnlineFiling, String> implements RuleOnlineFilingService {

	private Logger logger = LoggerFactory.getLogger(RuleOnlineFilingServiceImpl.class);

	@Autowired
	private RuleOnlineFilingDao ruleOnlineFilingDao;

	@Autowired
	private HospitalService hospitalService;

	@Override
	protected BaseDao<RuleOnlineFiling, String> getDao() {
		// TODO Auto-generated method stub
		return ruleOnlineFilingDao;
	}

	/**
	 * 保存
	 * 
	 * @param ruleEdit
	 * @return
	 */
	public String saveRuleOnlineFiling(RuleOnlineFiling entity) {
		// TODO Auto-generated method stub
		// 为避免同时新增 不同的用户先后保存先判断是否已存在该规则
		String ruleEditId = null;
		RuleOnlineFiling onlineFiling = findByHospitalId(entity.getHospitalId());
		Hospital hospital = hospitalService.findById(entity.getHospitalId());
		if (onlineFiling != null) {
			entity.setId(onlineFiling.getId());
			update(entity);
			ruleEditId = entity.getId();
			hospital.setLastHandlerId(entity.getEp());
			hospital.setLastHandler(entity.getEpName());
		} else {
			ruleEditId = entity.getId();
			if (StringUtils.isBlank(entity.getId())) {
				ruleEditId = ruleOnlineFilingDao.add(entity);
				hospital.setIsPublishRule(HospitalConstant.HOSPITAL_RULE_NOT_PUBLISH);
			} else {
				ruleOnlineFilingDao.update(entity);
			}
			hospital.setLastHandlerId(entity.getCp());
			hospital.setLastHandler(entity.getCpName());
			// hospital.setRuleLastEditTime(new Date());
			// hospitalService.update(hospital);
			if (logger.isDebugEnabled()) {
				logger.debug("save RuleEdit :{}", ruleEditId);
			}
		}
		hospital.setRuleLastEditTime(new Date());
		hospitalService.updateForRuleEditTime(hospital);
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleOnlineFiling(entity);
		return ruleEditId;
	}

	@Override
	public void update(RuleOnlineFiling entity) {
		// TODO Auto-generated method stub
		super.update(entity);
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleOnlineFiling(entity);
	}

	@Override
	public RuleOnlineFiling findByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub
		return ruleOnlineFilingDao.findByHospitalId(hospitalId);
	}
}
