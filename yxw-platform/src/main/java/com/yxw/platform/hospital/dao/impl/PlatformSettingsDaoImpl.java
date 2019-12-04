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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.PlatformSettingsDao;

/**
 * 后台接入平台配置信息管理 Dao 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Repository
public class PlatformSettingsDaoImpl extends BaseDaoImpl<PlatformSettings, String> implements PlatformSettingsDao {
	private static Logger logger = LoggerFactory.getLogger(PlatformSettingsDaoImpl.class);

	private static final String SQLNAME_FIND_BY_HOSPITALID_AND_PLATFORMIDS = "findByHospitalIdAndPlatformIds";

	private static final String SQLNAME_FIND_BY_HOSPITALID_AND_PLATFORMID = "findByHospitalIdAndPlatformId";

	private static final String SQLNAME_FIND_BY_HOSPITALID_AND_APPCODE = "findByHospitalIdAndAppCode";

	private static final String SQLNAME_FIND_PLATFORM_SETTINGS_BY_APPID = "findPlatformSettingsByAppId";

	private static final String SQLNAME_FIND_ALL_PLATFORM_RELATIONS = "findAllPlatformRelations";

	private static final String SQLNAME_FIND_BY_PRIVATE_KEY = "findByPrivateKey";

	private static final String SQLNAME_FIND_BY_PUBLIC_KEY = "findByPublicKey";

	@Override
	public List<PlatformSettings> findByHospitalIdAndPlatformIds(String hospitalId, String[] platformIds) {
		/** modify by yuce 增加对platformIds未空的判断 **/
		if (platformIds != null && platformIds.length > 0) {
			try {
				HashMap<Object, Object> param = new HashMap<Object, Object>();
				param.put("hospitalId", hospitalId);
				param.put("platformIds", platformIds);
				return sqlSession.selectList(SQLNAME_FIND_BY_HOSPITALID_AND_PLATFORMIDS, param);
			} catch (Exception e) {
				logger.error(String.format("根据医院查询支付配置信息列表出错！语句：%s", SQLNAME_FIND_BY_HOSPITALID_AND_PLATFORMIDS), e);
				throw new SystemException(String.format("根据医院查询支付配置信息列表出错！语句：%s", SQLNAME_FIND_BY_HOSPITALID_AND_PLATFORMIDS), e);
			}
		} else {
			return new ArrayList<PlatformSettings>();
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.dao.PlatformSettingsDao#findByHospitalIdAndPlatformId(java.lang.String, java.lang.String)
	 */
	@Override
	public PlatformSettings findByHospitalIdAndPlatformId(String hospitalId, String platformId) {
		if (platformId != null && platformId.length() > 0) {
			try {
				HashMap<Object, Object> param = new HashMap<Object, Object>();
				param.put("hospitalId", hospitalId);
				param.put("platformId", platformId);
				return sqlSession.selectOne(SQLNAME_FIND_BY_HOSPITALID_AND_PLATFORMID, param);
			} catch (Exception e) {
				logger.error(String.format("根据医院查询支付配置信息列表出错！语句：%s", SQLNAME_FIND_BY_HOSPITALID_AND_PLATFORMID), e);
				throw new SystemException(String.format("根据医院查询支付配置信息列表出错！语句：%s", SQLNAME_FIND_BY_HOSPITALID_AND_PLATFORMID), e);
			}
		} else {
			return new PlatformSettings();
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.dao.PlatformSettingsDao#findByHospitalIdAndPlatformId(java.lang.String, java.lang.String)
	 */
	@Override
	public PlatformSettings findByHospitalIdAndAppCode(String hospitalId, String appCode) {
		if (appCode != null && appCode.length() > 0) {
			try {
				HashMap<Object, Object> param = new HashMap<Object, Object>();
				param.put("hospitalId", hospitalId);
				param.put("appCode", appCode);
				return sqlSession.selectOne(SQLNAME_FIND_BY_HOSPITALID_AND_APPCODE, param);
			} catch (Exception e) {
				logger.error(String.format("根据医院查询支付配置信息列表出错！语句：%s", SQLNAME_FIND_BY_HOSPITALID_AND_APPCODE), e);
				throw new SystemException(String.format("根据医院查询支付配置信息列表出错！语句：%s", SQLNAME_FIND_BY_HOSPITALID_AND_APPCODE), e);
			}
		} else {
			return new PlatformSettings();
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.dao.PlatformSettingsDao#findByAppId(java.lang.String)
	 */
	@Override
	public PlatformSettings findByAppId(String appId) {
		try {
			Assert.notNull(appId);
			return sqlSession.selectOne(SQLNAME_FIND_PLATFORM_SETTINGS_BY_APPID, appId);
		} catch (Exception e) {
			logger.error(String.format("根据appId配置信息列表出错！语句：%s", SQLNAME_FIND_PLATFORM_SETTINGS_BY_APPID), e);
			throw new SystemException(String.format("根据appId配置信息列表出错！语句：%s", SQLNAME_FIND_PLATFORM_SETTINGS_BY_APPID), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.dao.PlatformSettingsDao#findByPrivateKey(java.lang.String)
	 */
	@Override
	public PlatformSettings findByPrivateKey(String privateKey) {
		try {
			Assert.notNull(privateKey);
			return sqlSession.selectOne(SQLNAME_FIND_BY_PRIVATE_KEY, privateKey);
		} catch (Exception e) {
			logger.error(String.format("根据privateKey配置信息列表出错！语句：%s", SQLNAME_FIND_BY_PRIVATE_KEY), e);
			throw new SystemException(String.format("根据privateKey配置信息列表出错！语句：%s", SQLNAME_FIND_BY_PRIVATE_KEY), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.dao.PlatformSettingsDao#findByPublicKey(java.lang.String)
	 */
	@Override
	public PlatformSettings findByPublicKey(String publicKey) {
		try {
			Assert.notNull(publicKey);
			return sqlSession.selectOne(SQLNAME_FIND_BY_PUBLIC_KEY, publicKey);
		} catch (Exception e) {
			logger.error(String.format("根据publicKey配置信息列表出错！语句：%s", SQLNAME_FIND_BY_PUBLIC_KEY), e);
			throw new SystemException(String.format("根据publicKey配置信息列表出错！语句：%s", SQLNAME_FIND_BY_PUBLIC_KEY), e);
		}
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年3月27日 
	 * @return 
	 */
	@Override
	public List<PlatformSettings> findAllPlatformRelations() {
		try {
			return sqlSession.selectList(SQLNAME_FIND_ALL_PLATFORM_RELATIONS, "");
		} catch (Exception e) {
			logger.error(String.format("查询所有平台医院关系信息出错！语句：%s", SQLNAME_FIND_ALL_PLATFORM_RELATIONS), e);
			throw new SystemException(String.format("查询所有平台医院关系信息出错！语句：%s", SQLNAME_FIND_ALL_PLATFORM_RELATIONS), e);
		}
	}
}
