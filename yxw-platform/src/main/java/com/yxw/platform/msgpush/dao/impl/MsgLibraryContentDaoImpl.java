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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.platform.msgpush.MsgLibraryContent;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.msgpush.dao.MsgLibraryContentDao;

/**
 * 消息模板内容dao实现类
 * 
 * @Package: com.yxw.platform.msgpush.dao.impl
 * @ClassName: MsgTemplateContentDaoImpl
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 申午武
 * @Create Date: 2015年6月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class MsgLibraryContentDaoImpl extends BaseDaoImpl<MsgLibraryContent, String> implements MsgLibraryContentDao {
	private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	private final static String SQLNAME_DELETE_BY_TEMP_LIB_ID = "deleteByTempLibraryId";

	@Override
	public void deleteByTempLibraryId(String tempLibraryId) {
		try {
			sqlSession.delete(getSqlName(SQLNAME_DELETE_BY_TEMP_LIB_ID), tempLibraryId);
		} catch (Exception e) {
			logger.error(String.format("删除对象出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_TEMP_LIB_ID)), e);
			throw new SystemException(String.format("删除对象出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_TEMP_LIB_ID)), e);
		}
	}

}
