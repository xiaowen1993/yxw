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

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.platform.hospital.ExtensionDetail;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.ExtensionDetailDao;

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
public class ExtensionDetailDaoImpl extends BaseDaoImpl<ExtensionDetail, String> implements ExtensionDetailDao {
	private static Logger logger = LoggerFactory.getLogger(ExtensionDetailDaoImpl.class);

	private final static String SQLNAME_FIND_EXTENSION_DETAIL_BY_EXTENSIONID = "findExtensionDetailByExtensionId";

	private final static String SQLNAME_FIND_EXTENSION_DETAIL_BY_EXTENSIONID_AND_OPENID = "findExtensionDetailByExtensionIdAndOpenId";

	@Override
	public List<ExtensionDetail> findExtensionDetailByExtensionId(Map<String, Object> params) {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_EXTENSION_DETAIL_BY_EXTENSIONID), params);
		} catch (Exception e) {
			logger.error(String.format("根据推广二维码Id查询其关注信息出错！语句：%s", getSqlName(SQLNAME_FIND_EXTENSION_DETAIL_BY_EXTENSIONID)), e);
			throw new SystemException(String.format("根据推广二维码Id查询其关注信息出错！语句：%s", getSqlName(SQLNAME_FIND_EXTENSION_DETAIL_BY_EXTENSIONID)), e);
		}
	}

	@Override
	public ExtensionDetail findExtensionDetailByExtensionIdAndOpenId(Map<String, Object> params) {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_EXTENSION_DETAIL_BY_EXTENSIONID_AND_OPENID), params);
		} catch (Exception e) {
			logger.error(String.format("根据推广二维码Id及openId查询其关注信息出错！语句：%s", getSqlName(SQLNAME_FIND_EXTENSION_DETAIL_BY_EXTENSIONID_AND_OPENID)), e);
			throw new SystemException(String.format("根据推广二维码Id及openId查询其关注信息出错！语句：%s",
					getSqlName(SQLNAME_FIND_EXTENSION_DETAIL_BY_EXTENSIONID_AND_OPENID)), e);
		}
	}
}
