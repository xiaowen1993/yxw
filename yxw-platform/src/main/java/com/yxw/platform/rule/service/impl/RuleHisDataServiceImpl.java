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
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.rule.dao.RuleHisDataDao;
import com.yxw.platform.rule.service.RuleHisDataService;

/**
 * @Package: com.yxw.platform.rule.service.impl
 * @ClassName: RuleHisDataServiceImpl
 * @Statement: <p>His基础数据(科室、医生、号源)规则 Service 实现类</p>          
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "ruleHisDataService")
public class RuleHisDataServiceImpl extends BaseServiceImpl<RuleHisData, String> implements RuleHisDataService {

	private Logger logger = LoggerFactory.getLogger(RuleHisDataServiceImpl.class);

	@Autowired
	private RuleHisDataDao ruleHisDataDao;

	@Autowired
	private HospitalService hospitalService;

	@Override
	protected BaseDao<RuleHisData, String> getDao() {
		// TODO Auto-generated method stub
		return ruleHisDataDao;
	}

	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public String saveRuleHisData(RuleHisData entity) {
		// TODO Auto-generated method stub
		String ruleHisDataId = null;
		RuleHisData ruleHisData = findByHospitalId(entity.getHospitalId());
		Hospital hospital = hospitalService.findById(entity.getHospitalId());
		if (ruleHisData != null) {
			entity.setId(ruleHisData.getId());
			update(entity);
			ruleHisDataId = entity.getId();
			hospital.setLastHandlerId(entity.getEp());
			hospital.setLastHandler(entity.getEpName());
		} else {
			if (hospital == null) {
				return null;
			}
			if (StringUtils.isBlank(entity.getId())) {
				ruleHisDataId = ruleHisDataDao.add(entity);
				hospital.setIsPublishRule(HospitalConstant.HOSPITAL_RULE_NOT_PUBLISH);
			} else {
				ruleHisDataDao.update(entity);
			}
			hospital.setLastHandlerId(entity.getCp());
			hospital.setLastHandler(entity.getCpName());
			if (logger.isDebugEnabled()) {
				logger.debug("save RuleEdit :{}", ruleHisDataId);
			}
		}
		hospital.setRuleLastEditTime(new Date());
		hospitalService.updateForRuleEditTime(hospital);
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleHisData(entity);
		return ruleHisDataId;
	}

	@Override
	public void update(RuleHisData entity) {
		// TODO Auto-generated method stub
		super.update(entity);

		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRuleHisData(entity);
	}

	@Override
	public RuleHisData findByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub
		return ruleHisDataDao.findByHospitalId(hospitalId);
	}

}
