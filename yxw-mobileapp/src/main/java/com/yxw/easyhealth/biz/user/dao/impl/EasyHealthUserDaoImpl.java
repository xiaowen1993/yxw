/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.easyhealth.biz.user.dao.EasyHealthUserDao;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;

/**
 * @Package: com.yxw.easyhealth.biz.user.dao.impl
 * @ClassName: EasyHealthUserDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月6日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class EasyHealthUserDaoImpl extends BaseDaoImpl<EasyHealthUser, String> implements EasyHealthUserDao {
	private Logger logger = LoggerFactory.getLogger(EasyHealthUserDaoImpl.class);

	private final static String SQLNAME_FIND_EASY_HEALTH_FOR_CARD_NO = "findEasyHealthForCardNo";

	private final static String SQLNAME_FIND_EASY_HEALTH_FOR_CARD_NO_AND_PASSWORD = "findEasyHealthForCardNoAndPassWord";

	private final static String SQLNAME_FIND_EASY_HEALTH_USER_BY_MOBILE = "findEasyHealthUserByMobile";
	private final static String SQLNAME_FIND_EASY_HEALTH_USER_BY_CARDNO_AND_MOBILE = "findEasyHealthUserByCardNoAndMobile";
	private final static String SQLNAME_FIND_EASY_HEALTH_USER_BY_ACCOUNT = "findEasyHealthUserByAccount";

	@Override
	public EasyHealthUser findEasyHealthForCardNo(HashMap<String, Object> params) {
		EasyHealthUser ehUser = null;
		try {
			ehUser = sqlSession.selectOne(getSqlName(SQLNAME_FIND_EASY_HEALTH_FOR_CARD_NO), params);
		} catch (Exception e) {
			logger.error(String.format("检查证件号是否已经注册出错！语句：%s", getSqlName(SQLNAME_FIND_EASY_HEALTH_FOR_CARD_NO)), e);
			throw new SystemException(String.format("检查证件号是否已经注册出错！语句：%s", getSqlName(SQLNAME_FIND_EASY_HEALTH_FOR_CARD_NO)), e);
		}
		return ehUser;
	}

	@Override
	public EasyHealthUser findEasyHealthForCardNoAndPassWord(HashMap<String, Object> params) {
		EasyHealthUser ehUser = null;
		try {
			ehUser = sqlSession.selectOne(getSqlName(SQLNAME_FIND_EASY_HEALTH_FOR_CARD_NO_AND_PASSWORD), params);
		} catch (Exception e) {
			logger.error(String.format("登录检查出错！语句：%s", getSqlName(SQLNAME_FIND_EASY_HEALTH_FOR_CARD_NO_AND_PASSWORD)), e);
			throw new SystemException(String.format("登录检查出错！语句：%s", getSqlName(SQLNAME_FIND_EASY_HEALTH_FOR_CARD_NO_AND_PASSWORD)), e);
		}
		return ehUser;
	}

	@Override
	public List<EasyHealthUser> findEasyHealthUserByMobile(Map<String, Object> params) {
		List<EasyHealthUser> ehUsers = null;
		try {
			ehUsers = sqlSession.selectList(getSqlName(SQLNAME_FIND_EASY_HEALTH_USER_BY_MOBILE), params);
		} catch (Exception e) {
			logger.error(String.format("根据手机号查找用户出错！语句：%s", getSqlName(SQLNAME_FIND_EASY_HEALTH_USER_BY_MOBILE)), e);
			throw new SystemException(String.format("根据手机号查找用户出错！语句：%s", getSqlName(SQLNAME_FIND_EASY_HEALTH_USER_BY_MOBILE)), e);
		}
		return ehUsers;
	}

	@Override
	public List<EasyHealthUser> findEasyHealthUserByCardNoAndMobile(Map<String, Object> params) {
		List<EasyHealthUser> ehUsers = null;
		try {
			ehUsers = sqlSession.selectList(getSqlName(SQLNAME_FIND_EASY_HEALTH_USER_BY_CARDNO_AND_MOBILE), params);
		} catch (Exception e) {
			logger.error(String.format("根据手机号查找用户出错！语句：%s", getSqlName(SQLNAME_FIND_EASY_HEALTH_USER_BY_CARDNO_AND_MOBILE)), e);
			throw new SystemException(String.format("根据手机号查找用户出错！语句：%s", getSqlName(SQLNAME_FIND_EASY_HEALTH_USER_BY_CARDNO_AND_MOBILE)), e);
		}
		return ehUsers;
	}

	@Override
	public EasyHealthUser findEasyHealthUserByAccount(Map<String, Object> params) {
		EasyHealthUser ehUser = null;
		try {
			ehUser = sqlSession.selectOne(getSqlName(SQLNAME_FIND_EASY_HEALTH_USER_BY_ACCOUNT), params);
		} catch (Exception e) {
			logger.error(String.format("登录检查出错！语句：%s", getSqlName(SQLNAME_FIND_EASY_HEALTH_USER_BY_ACCOUNT)), e);
			throw new SystemException(String.format("登录检查出错！语句：%s", getSqlName(SQLNAME_FIND_EASY_HEALTH_USER_BY_ACCOUNT)), e);
		}
		return ehUser;
	}
}
