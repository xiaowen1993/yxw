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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.msgpush.MsgTemplateLibrary;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.msgpush.dao.MsgTemplateLibraryDao;

/**
 * 模板库dao实现类
 * @Package: com.yxw.platform.msgpush.dao.impl
 * @ClassName: MsgTemplateLibraryDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class MsgTemplateLibraryDaoImpl extends BaseDaoImpl<MsgTemplateLibrary, String> implements MsgTemplateLibraryDao {
	private static Logger logger = LoggerFactory.getLogger(MsgTemplateLibraryDaoImpl.class);
	private static final String SQLNAME_FIND_LIST_BY_SOURCE = "findListBySource";

	@Override
	public List<MsgTemplateLibrary> findListBySource(String source) {
		try {
			Assert.notNull(source);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_LIST_BY_SOURCE), source);
		} catch (Exception e) {
			logger.error(String.format("根据source查询对象出错！语句：%s", getSqlName(SQLNAME_FIND_LIST_BY_SOURCE)), e);
			throw new SystemException(String.format("根据source查询对象出错！语句：%s", getSqlName(SQLNAME_FIND_LIST_BY_SOURCE)), e);
		}
	}

}
