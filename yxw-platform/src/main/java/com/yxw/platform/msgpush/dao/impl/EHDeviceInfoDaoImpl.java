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
package com.yxw.platform.msgpush.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.platform.msgpush.EHDeviceInfo;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.msgpush.dao.EHDeviceInfoDao;

/**
 * @Package: com.yxw.platform.msgpush.dao.impl
 * @ClassName: EHDeviceInfoDaoImpl
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
public class EHDeviceInfoDaoImpl extends BaseDaoImpl<EHDeviceInfo, String> implements EHDeviceInfoDao {
	private static Logger logger = LoggerFactory.getLogger(EHDeviceInfoDaoImpl.class);
	private static final String SQL_FIND_BY_USER_ID = "findByUserId";
	private static final String SQL_FIND_BY_DEVICE_ID = "findByDeviceId";
	private static final String SQL_DELETE_BY_USER_ID = "deleteByUserId";

	@Override
	public List<EHDeviceInfo> findByUserId(Map<String, Object> params) {
		List<EHDeviceInfo> ehDeviceInfos = new ArrayList<EHDeviceInfo>();
		try {
			params.put("hashTableName", SimpleHashTableNameGenerator.getEhDeviceInfoHashTable(String.valueOf(params.get("userId")), true));
			ehDeviceInfos = sqlSession.selectList(getSqlName(SQL_FIND_BY_USER_ID), params);
		} catch (Exception e) {
			logger.error(String.format("根据用户ID查询设备出错!语句：%s", getSqlName(SQL_FIND_BY_USER_ID)), e);
			throw new SystemException(String.format("根据用户ID查询设备出错!语句：%s", getSqlName(SQL_FIND_BY_USER_ID)), e);
		}
		return ehDeviceInfos;
	}

	@Override
	public EHDeviceInfo findByDeviceId(Map<String, Object> params) {
		try {
			return sqlSession.selectOne(getSqlName(SQL_FIND_BY_DEVICE_ID), params);
		} catch (Exception e) {
			logger.error(String.format("根据设备ID查询设备出错!语句：%s", getSqlName(SQL_FIND_BY_DEVICE_ID)), e);
			throw new SystemException(String.format("根据设备ID查询设备出错!语句：%s", getSqlName(SQL_FIND_BY_DEVICE_ID)), e);
		}
	}

	@Override
	public void deleteByUserId(EHDeviceInfo ehDeviceInfo) {
		try {
			sqlSession.delete(getSqlName(SQL_DELETE_BY_USER_ID), ehDeviceInfo);
		} catch (Exception e) {
			logger.error(String.format("根据USERID删除对象出错!语句：%s", getSqlName(SQL_DELETE_BY_USER_ID)), e);
			throw new SystemException(String.format("根据USERID删除对象出错!语句：%s", getSqlName(SQL_DELETE_BY_USER_ID)), e);
		}
	}

}
