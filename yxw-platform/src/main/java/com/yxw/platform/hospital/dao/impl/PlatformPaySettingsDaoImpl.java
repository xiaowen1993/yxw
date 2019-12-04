/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月15日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.hospital.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.hospital.PlatformPaySettings;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.PlatformPaySettingsDao;

/**
 * 后台接入平台配置信息管理 Dao 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Repository
public class PlatformPaySettingsDaoImpl extends BaseDaoImpl<PlatformPaySettings, String> implements PlatformPaySettingsDao {
	private static Logger logger = LoggerFactory.getLogger(PlatformPaySettingsDaoImpl.class);

	public final static String SQLNAME_FIND_BY_HOSPITAL = "findByHospital";

	public final static String SQLNAME_FIND_BY_PLATFORMSETTINGSID_AND_PAYSETTINGSID = "findByPlatformSettingsIdAndPaySettingsId";

	private final static String SQLNAME_DELETE_PLATFORM_PAYSETTINGS_BY_PLATFORM_ID = "deletePlatformPaySettingsByPlatFormId";

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.dao.HospitalPlatformSettingsDao#deleteByHospitalIds(java.lang.String)
	 */
	@Override
	public void deletePlatformPaySettingsByPlatFormId(String platformSettingsId, String paySettingsId) {
		Assert.notNull(platformSettingsId);
		Assert.notNull(paySettingsId);
		Map<String, String> params = new HashMap<String, String>();
		params.put("platformSettingsId", platformSettingsId);
		params.put("paySettingsId", paySettingsId);
		try {
			sqlSession.delete(getSqlName(SQLNAME_DELETE_PLATFORM_PAYSETTINGS_BY_PLATFORM_ID), params);
		} catch (Exception e) {
			logger.error(String.format("根据平台Id删除支付配置信息出错！语句：%s", getSqlName(SQLNAME_DELETE_PLATFORM_PAYSETTINGS_BY_PLATFORM_ID)), e);
			throw new SystemException(String.format("根据平台Id删除支付配置信息出错！语句：%s", getSqlName(SQLNAME_DELETE_PLATFORM_PAYSETTINGS_BY_PLATFORM_ID)), e);
		}
	}

	@Override
	public PlatformPaySettings findByPlatformSettingsIdAndPaySettingsId(String platformSettingsId, String paySettingsId) {
		Assert.notNull(platformSettingsId);
		Assert.notNull(paySettingsId);
		Map<String, String> params = new HashMap<String, String>();
		params.put("platformSettingsId", platformSettingsId);
		params.put("paySettingsId", paySettingsId);
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_PLATFORMSETTINGSID_AND_PAYSETTINGSID), params);
		} catch (Exception e) {
			logger.error(String.format("根据platformSettingsId和paySettingsId查询支付配置信息出错！语句：%s",
					getSqlName(SQLNAME_FIND_BY_PLATFORMSETTINGSID_AND_PAYSETTINGSID)), e);
			throw new SystemException(String.format("根据platformSettingsId和paySettingsId查询支付配置信息出错！语句：%s",
					getSqlName(SQLNAME_FIND_BY_PLATFORMSETTINGSID_AND_PAYSETTINGSID)), e);
		}
	}

}
