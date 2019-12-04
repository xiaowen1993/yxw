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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.hospital.PlatformOptional;
import com.yxw.commons.vo.PlatformOptionalVo;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.PlatformOptionalDao;

/**
 * 后台医院已选功能管理 Dao 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Repository
public class PlatformOptionalDaoImpl extends BaseDaoImpl<PlatformOptional, String> implements PlatformOptionalDao {
	private static Logger logger = LoggerFactory.getLogger(PlatformOptionalDaoImpl.class);

	private final static String SQLNAME_FIND_BY_HOSPITAL = "findByHospital";
	private final static String SQLNAME_FIND_BY_PLATFORM_SETTINGS_ID = "findByPlatformSettingsId";
	private final static String SQLNAME_DELETE_BY_PLATFORM_SETTINGS_ID = "deleteByPlatformSettingsId";
	private final static String SQL_NAME_FIND_ALL_BY_HOSPITAL_ID = "findAllByHospitalId";

	/**
	 * 根据医院id查找医院已选功能
	 * 
	 * @param hospitalId
	 *            医院id
	 * @return List<HospitalPlatformSettings>
	 */
	@Override
	public List<PlatformOptional> findByHospital(String hospitalId) {
		Assert.notNull(hospitalId);
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_HOSPITAL), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID查询医院已选功能列表出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL)), e);
			throw new SystemException(String.format("根据医院ID查询医院已选功能列表出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL)), e);
		}
	}

	@Override
	public void deleteByPlatformSettingsId(String platformSettingsId) {
		Assert.notNull(platformSettingsId);
		try {
			sqlSession.delete(getSqlName(SQLNAME_DELETE_BY_PLATFORM_SETTINGS_ID), platformSettingsId);
		} catch (Exception e) {
			logger.error(String.format("根据平台删除已选功能出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_PLATFORM_SETTINGS_ID)), e);
			throw new SystemException(String.format("根据平台删除已选功能出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_PLATFORM_SETTINGS_ID)), e);
		}
	}

	@Override
	public PlatformOptional findByPlatformSettingsId(String platformSettingsId) {
		Assert.notNull(platformSettingsId);
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_PLATFORM_SETTINGS_ID), platformSettingsId);
		} catch (Exception e) {
			logger.error(String.format("根据平台ID查询医院已选功能列表出错！语句：%s", getSqlName(SQLNAME_FIND_BY_PLATFORM_SETTINGS_ID)), e);
			throw new SystemException(String.format("根据平台ID查询医院已选功能列表出错！语句：%s", getSqlName(SQLNAME_FIND_BY_PLATFORM_SETTINGS_ID)), e);
		}
	}

	@Override
	public List<PlatformOptionalVo> findAllByHospitalId(String hospitalId) {
		Assert.notNull(hospitalId);
		try {
			return sqlSession.selectList(getSqlName(SQL_NAME_FIND_ALL_BY_HOSPITAL_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID查询医院已选功能列表出错！语句：%s", getSqlName(SQL_NAME_FIND_ALL_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("根据医院ID查询医院已选功能列表出错！语句：%s", getSqlName(SQL_NAME_FIND_ALL_BY_HOSPITAL_ID)), e);
		}
	}

}
