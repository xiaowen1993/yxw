/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-5-27</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.rule.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.rule.RuleClinic;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.rule.dao.RuleClinicDao;

/**
 * @Package: com.yxw.platform.rule.dao.impl
 * @ClassName: RulePaymentDaoImpl
 * @Statement: <p>门诊缴费规则Dao实现</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class RuleClinicDaoImpl extends BaseDaoImpl<RuleClinic, String> implements RuleClinicDao {
	private static Logger logger = LoggerFactory.getLogger(RuleClinicDaoImpl.class);
	private final static String SQLNAME_FINDBY_HOSPITALID = "findByHospitalId";

	@Override
	public String add(RuleClinic entity) {
		// TODO Auto-generated method stub
		return super.add(entity);
	}

	@Override
	public RuleClinic findByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub

		RuleClinic ruleClinic = null;
		try {
			Assert.notNull(hospitalId);
			ruleClinic = sqlSession.selectOne(getSqlName(SQLNAME_FINDBY_HOSPITALID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院查询缴费规则出错！语句：%s", getSqlName(SQLNAME_FINDBY_HOSPITALID)), e);
			throw new SystemException(String.format("根据医院查询缴费规则出错！语句：%s", getSqlName(SQLNAME_FINDBY_HOSPITALID)), e);
		}
		return ruleClinic;
	}

}
