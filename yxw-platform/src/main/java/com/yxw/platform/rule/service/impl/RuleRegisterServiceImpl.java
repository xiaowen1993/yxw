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
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.rule.dao.RuleRegisterDao;
import com.yxw.platform.rule.service.RuleRegisterService;

/**
 * @Package: com.yxw.platform.rule.service.impl
 * @ClassName: RuleEditServiceImpl
 * @Statement: <p>
 *             编辑规则 Service 实现类
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "ruleRegisterService")
public class RuleRegisterServiceImpl extends BaseServiceImpl<RuleRegister, String> implements RuleRegisterService {

	private Logger logger = LoggerFactory.getLogger(RuleRegisterServiceImpl.class);

	@Autowired
	private RuleRegisterDao ruleRegisterDao;

	@Autowired
	private HospitalService hospitalService;

	@Override
	protected BaseDao<RuleRegister, String> getDao() {
		// TODO Auto-generated method stub
		return ruleRegisterDao;
	}

	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public String saveRuleRegister(RuleRegister entity) {
		// TODO Auto-generated method stub
		String ruleEditId = null;
		RuleRegister register = findByHospitalId(entity.getHospitalId());
		Hospital hospital = hospitalService.findById(entity.getHospitalId());
		if (register != null) {
			entity.setId(register.getId());
			update(entity);
			ruleEditId = entity.getId();
			hospital.setLastHandlerId(entity.getEp());
			hospital.setLastHandler(entity.getEpName());
		} else {
			if (hospital == null) {
				return null;
			}
			if (StringUtils.isBlank(entity.getId())) {
				ruleEditId = ruleRegisterDao.add(entity);
				hospital.setIsPublishRule(HospitalConstant.HOSPITAL_RULE_NOT_PUBLISH);
			} else {
				ruleRegisterDao.update(entity);
			}
			hospital.setLastHandlerId(entity.getCp());
			hospital.setLastHandler(entity.getCpName());
			if (logger.isDebugEnabled()) {
				logger.debug("save RuleEdit :{}", ruleEditId);
			}
		}
		hospital.setRuleLastEditTime(new Date());
		hospitalService.updateForRuleEditTime(hospital);
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleRegister(entity);
		return ruleEditId;
	}

	@Override
	public void update(RuleRegister entity) {
		// TODO Auto-generated method stub
		super.update(entity);

		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleRegister(entity);
	}

	@Override
	public RuleRegister findByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub
		return ruleRegisterDao.findByHospitalId(hospitalId);
	}

}
