/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.msgcenter.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.mobile.biz.msgcenter.MsgCenter;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.mobileapp.biz.msgcenter.dao.MsgCenterDao;

/**
 * @Package: com.yxw.platform.msgpush.dao.impl
 * @ClassName: MsgCenterDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class MsgCenterDaoImpl extends BaseDaoImpl<MsgCenter, String> implements MsgCenterDao {
	private static Logger logger = LoggerFactory.getLogger(MsgCenterDaoImpl.class);
	private static final String SQLNAME_FIND_LIST_BY_PAGE = "findListByPage";
	private static final String SQL_FIND_BY_ID = "findById";
	private static final String SQL_UPDATE_IS_READ = "updateIsRead";
	private static final String SQL_FIND_COUNT_BY_IS_READ = "findCountByIsRead";

	@Override
	public MsgCenter findById(Map<String, Serializable> params) {
		try {
			params.put("hashTableName", SimpleHashTableNameGenerator.getSysMsgCenterHashTable(String.valueOf(params.get("userId")), true));
			return sqlSession.selectOne(getSqlName(SQL_FIND_BY_ID), params);
		} catch (Exception e) {
			logger.error(String.format("根据ID查询对象出错！语句：%s", getSqlName(SQL_FIND_BY_ID)), e);
			throw new SystemException(String.format("根据ID查询对象出错！语句：%s", getSqlName(SQL_FIND_BY_ID)), e);
		}
	}

	@Override
	public PageInfo<MsgCenter> findListByPage(Map<String, Object> params, Page<MsgCenter> page) {
		try {
			PageHelper.startPage(page.getPageNum(), page.getPageSize());
			params.put("hashTableName", SimpleHashTableNameGenerator.getSysMsgCenterHashTable(String.valueOf(params.get("userId")), true));
			List<MsgCenter> list = sqlSession.selectList(getSqlName(SQLNAME_FIND_LIST_BY_PAGE), params);
			return new PageInfo<MsgCenter>(list);
		} catch (Exception e) {
			logger.error(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQLNAME_FIND_LIST_BY_PAGE)), e);
			throw new SystemException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQLNAME_FIND_LIST_BY_PAGE)), e);
		}
	}

	@Override
	public void updateIsRead(Map<String, Serializable> params) {
		try {
			params.put("hashTableName", SimpleHashTableNameGenerator.getSysMsgCenterHashTable(String.valueOf(params.get("userId")), true));
			sqlSession.update(getSqlName(SQL_UPDATE_IS_READ), params);
		} catch (Exception e) {
			logger.error(String.format("更新是否已读状态出错！语句:%s", getSqlName(SQL_UPDATE_IS_READ)), e);
			throw new SystemException(String.format("更新是否已读状态出错！语句:%s", getSqlName(SQL_UPDATE_IS_READ)), e);
		}

	}

	@Override
	public Integer findCountByIsRead(Map<String, Serializable> params) {
		try {
			params.put("hashTableName", SimpleHashTableNameGenerator.getSysMsgCenterHashTable(String.valueOf(params.get("userId")), true));
			return sqlSession.selectOne(getSqlName(SQL_FIND_COUNT_BY_IS_READ), params);
		} catch (Exception e) {
			logger.error(String.format("查询对象出错！语句:%s", getSqlName(SQL_UPDATE_IS_READ)), e);
			throw new SystemException(String.format("查询对象出错！语句:%s", getSqlName(SQL_UPDATE_IS_READ)), e);
		}
	}

}
