/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-15</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.app.biz.customservice.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.app.biz.customservice.dao.CustomFeedbackDao;
import com.yxw.commons.entity.mobile.biz.customservice.Feedback;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

/**
 * 客服中心
 * 
 * @Author: luob
 * @Create Date: 2015-10-22
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class CustomFeedbackDaoImpl extends BaseDaoImpl<Feedback, String> implements CustomFeedbackDao {
	private Logger logger = LoggerFactory.getLogger(CustomFeedbackDaoImpl.class);

	private final String SQLNAME_FIND_BY_OPENID_TYPE = "findByOpenIdAndType";

	@Override
	public List<Feedback> findFeedbackByOpenId(Map<String, String> params) {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_OPENID_TYPE), params);
		} catch (Exception e) {
			logger.error(String.format("根据OpenId和type查询反馈出错：%s", getSqlName(SQLNAME_FIND_BY_OPENID_TYPE)), e);
			throw new SystemException(String.format("根据OpenId和type查询反馈出错：%s", getSqlName(SQLNAME_FIND_BY_OPENID_TYPE)), e);
		}
	}
}
