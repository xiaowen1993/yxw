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

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.rule.RulePush;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.rule.dao.RulePushDao;

/**
 * @Package: com.yxw.platform.rule.dao.impl
 * @ClassName: RuleEditDaoImpl
 * @Statement: <p>
 *             编辑规则Dao实现
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class RulePushDaoImpl extends BaseDaoImpl<RulePush, String> implements RulePushDao {
	private static Logger logger = LoggerFactory.getLogger(RulePushDaoImpl.class);

	private final String SQLNAME_FINDBY_HOSPITALID = "findByHospitalId";

	@Override
	public RulePush findByHospitalId(Map<String, Object> params) {
		RulePush rulePush = null;
		try {
			Assert.notNull(params);
			rulePush = sqlSession.selectOne(getSqlName(SQLNAME_FINDBY_HOSPITALID), params);
		} catch (Exception e) {
			logger.error(String.format("根据医院查询编辑规则出错！语句：%s", getSqlName(SQLNAME_FINDBY_HOSPITALID)), e);
			throw new SystemException(String.format("根据医院查询编辑规则出错！语句：%s", getSqlName(SQLNAME_FINDBY_HOSPITALID)), e);
		}
		return rulePush;
	}

}
