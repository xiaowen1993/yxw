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
import com.yxw.commons.entity.platform.rule.RulePayment;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.rule.dao.RulePaymentDao;
import com.yxw.platform.rule.service.RulePaymentService;

/**
 * @Package: com.yxw.platform.rule.service.impl
 * @ClassName: RuleEditServiceImpl
 * @Statement: <p>
 *             支付方式配置规则 Service 实现类
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "rulePaymentService")
public class RulePaymentServiceImpl extends BaseServiceImpl<RulePayment, String> implements RulePaymentService {

	private Logger logger = LoggerFactory.getLogger(RulePaymentServiceImpl.class);

	@Autowired
	private RulePaymentDao rulePaymentDao;

	@Autowired
	private HospitalService hospitalService;

	@Override
	protected BaseDao<RulePayment, String> getDao() {
		// TODO Auto-generated method stub
		return rulePaymentDao;
	}

	/**
	 * 保存
	 * 
	 * @param ruleEdit
	 * @return
	 */
	public String saveRulePayment(RulePayment entity) {
		// TODO Auto-generated method stub
		// 为避免同时新增 不同的用户先后保存先判断是否已存在该规则
		String ruleId = null;
		RulePayment payment = findByHospitalId(entity.getHospitalId());
		Hospital hospital = hospitalService.findById(entity.getHospitalId());
		if (payment != null) {
			entity.setId(payment.getId());
			update(entity);
			hospital.setRuleLastEditTime(new Date());
			hospital.setLastHandlerId(entity.getEp());
			hospital.setLastHandler(entity.getEpName());
			hospitalService.update(hospital);
			
			// 真TMD大一个坑
			// return entity.getId();
			ruleId = entity.getId();
		} else {
			ruleId = entity.getId();
			if (StringUtils.isBlank(ruleId)) {
				ruleId = rulePaymentDao.add(entity);
				hospital.setIsPublishRule(HospitalConstant.HOSPITAL_RULE_NOT_PUBLISH);
			} else {
				rulePaymentDao.update(entity);
			}
			hospital.setLastHandlerId(entity.getCp());
			hospital.setLastHandler(entity.getCpName());
			hospital.setRuleLastEditTime(new Date());
			hospitalService.updateForRuleEditTime(hospital);
			if (logger.isDebugEnabled()) {
				logger.debug("save saveRulePayMent :{}", rulePaymentDao);
			}
		}
		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRulePayment(entity);
		return ruleId;
	}

	@Override
	public void update(RulePayment entity) {
		// TODO Auto-generated method stub
		super.update(entity);

		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		ruleConfigManager.updateRulePayment(entity);
	}

	@Override
	public RulePayment findByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub
		return rulePaymentDao.findByHospitalId(hospitalId);
	}

}
