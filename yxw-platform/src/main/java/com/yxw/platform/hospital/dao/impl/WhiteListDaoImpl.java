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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.platform.hospital.WhiteList;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.WhiteListDao;

/**
 * @Package: com.yxw.platform.hospital.dao.impl
 * @ClassName: WhiteListDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class WhiteListDaoImpl extends BaseDaoImpl<WhiteList, String> implements WhiteListDao {
	private static Logger logger = LoggerFactory.getLogger(WhiteListDaoImpl.class);

	public final static String SQLNAME_FIND_BY_APPID_AND_PLATFORMTYPE = "findByAppIdAndPlatformType";

	public final static String SQLNAME_FIND_BY_HOSPITALID_AND_APP_CODE = "findHospitalIdAndAppCode";

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.dao.WhiteListDao#findByHospitalIdAndPlatformType(java.lang.String)
	 */
	@Override
	public WhiteList findByAppIdAndCode(String appId, String platformType) {
		try {
			HashMap<Object, Object> param = new HashMap<Object, Object>();
			param.put("appId", appId);

			int platformModeType = ModeTypeUtil.getPlatformModeType(platformType);

			param.put("platformType", platformModeType);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_APPID_AND_PLATFORMTYPE), param);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID和平台类型查白名单 出错！语句：%s", SQLNAME_FIND_BY_APPID_AND_PLATFORMTYPE), e);
			throw new SystemException(String.format("根据医院ID和平台类型查白名单 出错！语句：%s", SQLNAME_FIND_BY_APPID_AND_PLATFORMTYPE), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.dao.WhiteListDao#findHospitalId(java.lang.String, java.lang.String)
	 */
	@Override
	public WhiteList findHospitalId(String hospitalId, String appCode) {
		try {
			HashMap<Object, Object> param = new HashMap<Object, Object>();
			param.put("hospitalId", hospitalId);
			param.put("appCode", appCode);

			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_HOSPITALID_AND_APP_CODE), param);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID查白名单 出错！语句：%s", SQLNAME_FIND_BY_HOSPITALID_AND_APP_CODE), e);
			throw new SystemException(String.format("根据医院ID查白名单 出错！语句：%s", SQLNAME_FIND_BY_HOSPITALID_AND_APP_CODE), e);
		}

	}

}
