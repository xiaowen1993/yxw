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

import com.yxw.commons.entity.platform.hospital.Extension;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.ExtensionDao;

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
public class ExtensionDaoImpl extends BaseDaoImpl<Extension, String> implements ExtensionDao {
	private static Logger logger = LoggerFactory.getLogger(ExtensionDaoImpl.class);

	private final static String SQLNAME_FIND_EXTENSION_BY_HOSPITAL_ID_AND_APPCODE = "findExtensionByhospitalIdAndAppCode";
	private final static String SQLNAME_FIND_EXTENSION_BY_HOSPITAL_ID_AND_APPCODE_AND_DISTRICT = "findExtensionByhospitalIdAndAppCodeAndDistrict";

	private final static String SQLNAME_FIND_EXTENSION_BY_APPID_AND_SCENEID = "findExtensionByAppIdAndSceneid";

	@Override
	public List<Extension> findExtensionByhospitalIdAndAppCode(Map<String, Object> params) {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_EXTENSION_BY_HOSPITAL_ID_AND_APPCODE), params);
		} catch (Exception e) {
			logger.error(String.format("根据医院I的及appCode查询推广二维码信息出错！语句：%s", getSqlName(SQLNAME_FIND_EXTENSION_BY_HOSPITAL_ID_AND_APPCODE)), e);
			throw new SystemException(
					String.format("根据医院I的及appCode查询推广二维码信息出错！语句：%s", getSqlName(SQLNAME_FIND_EXTENSION_BY_HOSPITAL_ID_AND_APPCODE)), e);
		}
	}

	@Override
	public Extension findExtensionByhospitalIdAndAppCodeAndDistrict(Map<String, Object> params) {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_EXTENSION_BY_HOSPITAL_ID_AND_APPCODE_AND_DISTRICT), params);
		} catch (Exception e) {
			logger.error(String.format("根据医院Id的及appCode以及标识查询推广二维码信息标识出错！语句：%s",
					getSqlName(SQLNAME_FIND_EXTENSION_BY_HOSPITAL_ID_AND_APPCODE_AND_DISTRICT)), e);
			throw new SystemException(String.format("根据医院Id的及appCode以及标识查询推广二维码信息标识出错！语句：%s",
					getSqlName(SQLNAME_FIND_EXTENSION_BY_HOSPITAL_ID_AND_APPCODE_AND_DISTRICT)), e);
		}
	}

	@Override
	public Extension findExtensionByAppIdAndSceneid(Map<String, Object> params) {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_EXTENSION_BY_APPID_AND_SCENEID), params);
		} catch (Exception e) {
			logger.error(String.format("根据appId和Sceneid查询推广二维码来源出错！语句：%s", getSqlName(SQLNAME_FIND_EXTENSION_BY_APPID_AND_SCENEID)), e);
			throw new SystemException(String.format("根据appId和Sceneid查询推广二维码来源出错！语句：%s", getSqlName(SQLNAME_FIND_EXTENSION_BY_APPID_AND_SCENEID)), e);
		}
	}
}
