/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-11-12</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.rule.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.rule.RuleFriedExpress;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.rule.dao.RuleFriedExpressDao;

/**
 * @Package: com.yxw.platform.rule.dao.impl
 * @ClassName: RuleFriedExpressDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015-11-12
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class RuleFriedExpressDaoImpl extends BaseDaoImpl<RuleFriedExpress, String> implements RuleFriedExpressDao {
	private static Logger logger = LoggerFactory.getLogger(RulePaymentDaoImpl.class);
	private final static String SQLNAME_FINDBY_HOSPITALID = "findByHospitalId";

	/* (non-Javadoc)
	 * @see com.yxw.platform.rule.dao.RuleFriedExpressDao#findByHospitalId(java.lang.String)
	 */
	@Override
	public RuleFriedExpress findByHospitalId(String hospitalId) {
		RuleFriedExpress ruleFriedExpress = null;
		try {
			Assert.notNull(hospitalId);
			ruleFriedExpress = sqlSession.selectOne(getSqlName(SQLNAME_FINDBY_HOSPITALID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院查询缴费规则出错！语句：%s", getSqlName(SQLNAME_FINDBY_HOSPITALID)), e);
			throw new SystemException(String.format("根据医院查询缴费规则出错！语句：%s", getSqlName(SQLNAME_FINDBY_HOSPITALID)), e);
		}
		return ruleFriedExpress;
	}

}
