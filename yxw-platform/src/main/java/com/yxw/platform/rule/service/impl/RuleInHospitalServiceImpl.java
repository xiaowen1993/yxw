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
import com.yxw.commons.entity.platform.rule.RuleInHospital;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.rule.dao.RuleInHospitalDao;
import com.yxw.platform.rule.service.RuleInHospitalService;

/**
 * @Package: com.yxw.platform.rule.service.impl
 * @ClassName: RuleEditServiceImpl
 * @Statement: <p>编辑规则 Service 实现类</p>          
 */
@Service(value = "ruleInHospitalService")
public class RuleInHospitalServiceImpl extends BaseServiceImpl<RuleInHospital, String> implements RuleInHospitalService {

	private Logger logger = LoggerFactory.getLogger(RuleInHospitalServiceImpl.class);

	@Autowired
	private RuleInHospitalDao ruleInHospitalDao;

	@Autowired
	private HospitalService hospitalService;

	@Override
	protected BaseDao<RuleInHospital, String> getDao() {
		// TODO Auto-generated method stub
		return ruleInHospitalDao;
	}

	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public String saveRuleInHospital(RuleInHospital entity) {
		String ruleInHospitalId = null;
		RuleInHospital ruleInHospital = findByHospitalId(entity.getHospitalId());
		Hospital hospital = hospitalService.findById(entity.getHospitalId());
		if (ruleInHospital != null) {
			entity.setId(ruleInHospital.getId());
			update(entity);
			hospital.setRuleLastEditTime(new Date());
			hospital.setLastHandlerId(entity.getEp());
			hospital.setLastHandler(entity.getEpName());
			hospitalService.update(hospital);
			return entity.getId();
		} else {
			ruleInHospitalId = entity.getId();

			if (StringUtils.isBlank(ruleInHospitalId)) {
				ruleInHospitalId = ruleInHospitalDao.add(entity);
				hospital.setIsPublishRule(HospitalConstant.HOSPITAL_RULE_NOT_PUBLISH);
			} else {
				ruleInHospitalDao.update(entity);
			}

			hospital.setLastHandlerId(entity.getCp());
			hospital.setLastHandler(entity.getCpName());
			hospital.setRuleLastEditTime(new Date());
			hospitalService.updateForRuleEditTime(hospital);

			if (logger.isDebugEnabled()) {
				logger.debug("save RuleEdit :{}", ruleInHospitalId);
			}
		}

		updateCache(entity);

		return ruleInHospitalId;
	}

	/**
	 * 更新缓存
	 * @param ruleFriedExpress
	 */
	private void updateCache(RuleInHospital entity) {
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleInHospital(entity);
	}

	@Override
	public void update(RuleInHospital entity) {
		super.update(entity);

		updateCache(entity);
	}

	@Override
	public RuleInHospital findByHospitalId(String hospitalId) {
		return ruleInHospitalDao.findByHospitalId(hospitalId);
	}

}
