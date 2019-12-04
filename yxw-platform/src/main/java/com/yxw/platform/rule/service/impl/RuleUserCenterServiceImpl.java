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
import com.yxw.commons.entity.platform.rule.RuleUserCenter;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.rule.dao.RuleUserCenterDao;
import com.yxw.platform.rule.service.RuleUserCenterService;

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
@Service(value = "ruleUserCenterService")
public class RuleUserCenterServiceImpl extends BaseServiceImpl<RuleUserCenter, String> implements RuleUserCenterService {

	private Logger logger = LoggerFactory.getLogger(RuleUserCenterServiceImpl.class);

	@Autowired
	private RuleUserCenterDao ruleUserCenterDao;

	@Autowired
	private HospitalService hospitalService;

	@Override
	protected BaseDao<RuleUserCenter, String> getDao() {
		// TODO Auto-generated method stub
		return ruleUserCenterDao;
	}

	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	public String saveRuleUserCenter(RuleUserCenter entity) {
		// TODO Auto-generated method stub
		// 为避免同时新增 不同的用户先后保存先判断是否已存在该规则
		String ruleUserCenterId = null;
		RuleUserCenter ruleUserCenter = findByHospitalId(entity.getHospitalId());
		Hospital hospital = hospitalService.findById(entity.getHospitalId());
		if (ruleUserCenter != null) {
			entity.setId(ruleUserCenter.getId());
			update(entity);
			ruleUserCenterId = entity.getId();
			hospital.setLastHandlerId(entity.getEp());
			hospital.setLastHandler(entity.getEpName());
		} else {
			ruleUserCenterId = entity.getId();
			if (StringUtils.isBlank(entity.getId())) {
				ruleUserCenterId = ruleUserCenterDao.add(entity);
				hospital.setIsPublishRule(HospitalConstant.HOSPITAL_RULE_NOT_PUBLISH);
			} else {
				ruleUserCenterDao.update(entity);
			}
			hospital.setLastHandlerId(entity.getCp());
			hospital.setLastHandler(entity.getCpName());
			/*
			 * hospital.setRuleLastEditTime(new Date());
			 * hospitalService.update(hospital);  
			 */
			if (logger.isDebugEnabled()) {
				logger.debug("save ruleUserCenterId :{}", ruleUserCenterId);
			}
		}
		hospital.setRuleLastEditTime(new Date());
		hospitalService.updateForRuleEditTime(hospital);
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleUserCenter(entity);
		return ruleUserCenterId;
	}

	@Override
	public void update(RuleUserCenter entity) {
		// TODO Auto-generated method stub
		super.update(entity);

		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleUserCenter(entity);
	}

	@Override
	public RuleUserCenter findByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub
		return ruleUserCenterDao.findByHospitalId(hospitalId);
	}
}
