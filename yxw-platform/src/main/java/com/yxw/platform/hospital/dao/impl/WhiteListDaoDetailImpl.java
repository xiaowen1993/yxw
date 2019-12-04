/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.hospital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.hospital.WhiteListDetail;
import com.yxw.commons.vo.cache.WhiteListVo;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.WhiteListDetailDao;

/**
 * @Package: com.yxw.platform.hospital.dao.impl
 * @ClassName: WhiteListDaoDetailImpl
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 黄忠英
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class WhiteListDaoDetailImpl extends BaseDaoImpl<WhiteListDetail, String> implements WhiteListDetailDao {
	private static Logger logger = LoggerFactory.getLogger(WhiteListDaoDetailImpl.class);

	public final static String SQLNAME_FIND_WHITELIST_DETAIL = "findWhiteListDetail";

	public final static String SQLNAME_FIND_WHITELISTID_AND_PHONE = "findByWhiteListIdAndPhone";

	public final static String SQLNAME_FIND_WHITELISTID_AND_OPENID = "findByWhiteListIdAndOpenId";

	public final static String SQLNAME_FIND_ALL_WHITELISTID_INFO = "findAllWhiteInfo";
	public final static String SQLNAME_FIND_WHITELISTID_BY_APP = "findWhiteDetailsByApp";

	public final static String SQLNAME_UPDATE_OPENID_BYPHONE = "updateOpenIdByPhone";

	public final static String SQLNAME_FIND_WHITELIST_ID = "findByWhiteListId";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yxw.platform.hospital.dao.WhiteListDetailDao#findWhiteListDetail(
	 * java.util.Map)
	 */
	@Override
	public List<WhiteListDetail> findWhiteListDetail(Map<String, Object> param) {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_WHITELIST_DETAIL), param);
		} catch (Exception e) {
			logger.error(String.format("查询白名单 出错！语句：%s", SQLNAME_FIND_WHITELIST_DETAIL), e);
			throw new SystemException(String.format("查询白名单 出错！语句：%s", SQLNAME_FIND_WHITELIST_DETAIL), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yxw.platform.hospital.dao.WhiteListDetailDao#findByWhiteListIdAndPhone
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public WhiteListDetail findByWhiteListIdAndPhone(String whiteListId, String phone) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("whiteListId", whiteListId);
			param.put("phone", phone);

			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_WHITELISTID_AND_PHONE), param);
		} catch (Exception e) {
			logger.error(String.format("根据白名单ID和手机号查询白名单 出错！语句：%s", SQLNAME_FIND_WHITELISTID_AND_PHONE), e);
			throw new SystemException(String.format("根据白名单ID和手机号查询白名单 出错！语句：%s", SQLNAME_FIND_WHITELISTID_AND_PHONE), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yxw.platform.hospital.dao.WhiteListDetailDao#findByWhiteListIdAndOpenId
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public WhiteListDetail findByWhiteListIdAndOpenId(String whiteListId, String openId) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("whiteListId", whiteListId);
			param.put("openId", openId);

			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_WHITELISTID_AND_OPENID), param);
		} catch (Exception e) {
			logger.error(String.format("根据白名单和OPID查询白名单 出错！语句：%s", SQLNAME_FIND_WHITELISTID_AND_OPENID), e);
			throw new SystemException(String.format("根据白名单和OPID查询白名单 出错！语句：%s", SQLNAME_FIND_WHITELISTID_AND_OPENID), e);
		}
	}

	/**
	 * 根据手机号码和whiteListId更新openId
	 * 
	 * @param entity
	 * */
	public void updateOpenIdByPhone(Map<String, Object> paraMap) {
		try {
			Assert.notNull(paraMap);
			sqlSession.update(getSqlName(SQLNAME_UPDATE_OPENID_BYPHONE), paraMap);
		} catch (Exception e) {
			logger.error(String.format("更新对象出错！语句：%s", getSqlName(SQLNAME_UPDATE_OPENID_BYPHONE)), e);
			throw new SystemException(String.format("更新对象出错！语句：%s", getSqlName(SQLNAME_UPDATE_OPENID_BYPHONE)), e);
		}
	}

	@Override
	public List<WhiteListVo> findAllWhiteInfo() {
		// TODO Auto-generated method stub
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_WHITELISTID_INFO));
		} catch (Exception e) {
			logger.error(String.format("查询系统所有白名单 出错！语句：%s", SQLNAME_FIND_ALL_WHITELISTID_INFO), e);
			throw new SystemException(String.format("查询系统所有白名单 出错！语句：%s", SQLNAME_FIND_ALL_WHITELISTID_INFO), e);
		}
	}

	@Override
	public List<WhiteListVo> findWhiteDetailsByApp(String appId, String appCode) {
		// TODO Auto-generated method stub
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("appId", appId);
			params.put("appCode", appCode);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_WHITELISTID_BY_APP), params);
		} catch (Exception e) {
			logger.error(String.format("查询医院下的所有已启用的白名单用户 出错！语句：%s", SQLNAME_FIND_WHITELISTID_BY_APP), e);
			throw new SystemException(String.format("查询医院下的所有已启用的白名单用户 出错！语句：%s", SQLNAME_FIND_WHITELISTID_BY_APP), e);
		}
	}

	@Override
	public List<WhiteListDetail> findByWhiteListId(String whiteListId) {
		// TODO Auto-generated method stub
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_WHITELIST_ID), whiteListId);
		} catch (Exception e) {
			logger.error(String.format("查询医院下的所有已启用的白名单用户 出错！语句：%s", SQLNAME_FIND_WHITELIST_ID), e);
			throw new SystemException(String.format("查询医院下的所有已启用的白名单用户 出错！语句：%s", SQLNAME_FIND_WHITELIST_ID), e);
		}
	}

}
