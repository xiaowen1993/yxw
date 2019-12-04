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
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.rule.dao.RuleEditDao;
import com.yxw.platform.rule.service.RuleEditService;

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
@Service(value = "ruleEditService")
public class RuleEditServiceImpl extends BaseServiceImpl<RuleEdit, String> implements RuleEditService {

	private Logger logger = LoggerFactory.getLogger(RuleEditServiceImpl.class);

	@Autowired
	private RuleEditDao ruleEditDao;

	@Autowired
	private HospitalService hospitalService;

	@Override
	protected BaseDao<RuleEdit, String> getDao() {
		// TODO Auto-generated method stub
		return ruleEditDao;
	}

	/**
	 * 保存
	 * 
	 * @param ruleEdit
	 * @return
	 */
	public String saveRuleEdit(RuleEdit entity) {
		// TODO Auto-generated method stub
		// 为避免同时新增 不同的用户先后保存先判断是否已存在该规则
		String ruleEditId = null;
		RuleEdit ruleEdit = findByHospitalId(entity.getHospitalId());
		Hospital hospital = hospitalService.findById(entity.getHospitalId());
		if (ruleEdit != null) {
			entity.setId(ruleEdit.getId());
			update(entity);
			ruleEditId = entity.getId();
			hospital.setLastHandler(entity.getEpName());
			hospital.setLastHandlerId(entity.getEp());
		} else {
			ruleEditId = entity.getId();
			if (StringUtils.isBlank(ruleEditId)) {
				ruleEditId = ruleEditDao.add(entity);
				hospital.setIsPublishRule(HospitalConstant.HOSPITAL_RULE_NOT_PUBLISH);
			} else {
				ruleEditDao.update(entity);
			}
			hospital.setLastHandler(entity.getCpName());
			hospital.setLastHandlerId(entity.getCp());

			if (logger.isDebugEnabled()) {
				logger.debug("save RuleEdit :{}", ruleEditId);
			}
		}
		hospital.setRuleLastEditTime(new Date());
		hospitalService.updateForRuleEditTime(hospital);
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

		RuleEdit newRuleEdit = findByHospitalId(entity.getHospitalId());
		ruleConfigManager.updateRuleEdit(newRuleEdit);
		return ruleEditId;
	}

	@Override
	public void update(RuleEdit entity) {
		// TODO Auto-generated method stub
		super.update(entity);

		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleEdit(entity);
	}

	@Override
	public RuleEdit findByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub
		return ruleEditDao.findByHospitalId(hospitalId);
	}

}
