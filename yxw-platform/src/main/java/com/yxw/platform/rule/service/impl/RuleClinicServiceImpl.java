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
import com.yxw.commons.entity.platform.rule.RuleClinic;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.rule.dao.RuleClinicDao;
import com.yxw.platform.rule.service.RuleClinicService;

/**
 * @Package: com.yxw.platform.rule.service.impl
 * @ClassName: RuleEditServiceImpl
 * @Statement: <p>
 *             门诊缴费规则 Service 实现类
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "ruleClinicService")
public class RuleClinicServiceImpl extends BaseServiceImpl<RuleClinic, String> implements RuleClinicService {

	private Logger logger = LoggerFactory.getLogger(RuleClinicServiceImpl.class);

	@Autowired
	private RuleClinicDao ruleClinicDao;

	@Autowired
	private HospitalService hospitalService;

	@Override
	protected BaseDao<RuleClinic, String> getDao() {
		// TODO Auto-generated method stub
		return ruleClinicDao;
	}

	/**
	 * 保存
	 * 
	 * @param ruleEdit
	 * @return
	 */
	public String saveRuleClinic(RuleClinic entity) {
		// TODO Auto-generated method stub
		// 为避免同时新增 不同的用户先后保存先判断是否已存在该规则
		String ruleId = null;
		RuleClinic ruleClinic = findByHospitalId(entity.getHospitalId());
		Hospital hospital = hospitalService.findById(entity.getHospitalId());
		if (ruleClinic != null) {
			entity.setId(ruleClinic.getId());
			update(entity);
			hospital.setRuleLastEditTime(new Date());
			hospital.setLastHandlerId(entity.getEp());
			hospital.setLastHandler(entity.getEpName());
			hospitalService.update(hospital);
			return entity.getId();
		} else {
			ruleId = entity.getId();
			if (StringUtils.isBlank(ruleId)) {
				ruleId = ruleClinicDao.add(entity);
				hospital.setIsPublishRule(HospitalConstant.HOSPITAL_RULE_NOT_PUBLISH);
			} else {
				ruleClinicDao.update(entity);
			}
			hospital.setLastHandlerId(entity.getCp());
			hospital.setLastHandler(entity.getCpName());
			hospital.setRuleLastEditTime(new Date());
			hospital.setLastHandlerId(ruleId);
			hospitalService.updateForRuleEditTime(hospital);
			if (logger.isDebugEnabled()) {
				logger.debug("save saveRulePayMent :{}", ruleClinicDao);
			}
		}
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleClinic(entity);
		return ruleId;
	}

	@Override
	public void update(RuleClinic entity) {
		// TODO Auto-generated method stub
		super.update(entity);

		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleClinic(entity);
	}

	@Override
	public RuleClinic findByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub
		return ruleClinicDao.findByHospitalId(hospitalId);
	}

}
