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

import com.yxw.commons.entity.platform.rule.RuleInHospital;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.rule.dao.RuleInHospitalDao;

/**
 * @Package: com.yxw.platform.rule.dao.impl
 * @ClassName: RuleInHospitalDaoImpl
 * @Statement: <p>住院规则Dao实现</p>
 */
@Repository
public class RuleInHospitalDaoImpl extends BaseDaoImpl<RuleInHospital, String> implements RuleInHospitalDao {
	private static Logger logger = LoggerFactory.getLogger(RuleInHospitalDaoImpl.class);
	private final static String SQLNAME_FINDBY_HOSPITALID = "findByHospitalId";

	@Override
	public String add(RuleInHospital entity) {
		return super.add(entity);
	}

	@Override
	public RuleInHospital findByHospitalId(String hospitalId) {
		RuleInHospital ruleInHospital = null;
		try {
			Assert.notNull(hospitalId);
			ruleInHospital = sqlSession.selectOne(getSqlName(SQLNAME_FINDBY_HOSPITALID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院查询编辑规则出错！语句：%s", getSqlName(SQLNAME_FINDBY_HOSPITALID)), e);
			throw new SystemException(String.format("根据医院查询编辑规则出错！语句：%s", getSqlName(SQLNAME_FINDBY_HOSPITALID)), e);
		}
		return ruleInHospital;
	}

}
