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
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.hospital.HospitalPlatformSettings;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.HospitalPlatformSettingsDao;

/**
 * 后台接入平台配置信息管理 Dao 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Repository
public class HospitalPlatformSettingsDaoImpl extends BaseDaoImpl<HospitalPlatformSettings, String> implements HospitalPlatformSettingsDao {
	private static Logger logger = LoggerFactory.getLogger(HospitalPlatformSettingsDaoImpl.class);

	private final static String SQLNAME_FIND_BY_HOSPITAL = "findByHospital";

	private final static String SQLNAME_DELETE_BY_HOSPITALID = "deleteByHospitalId";

	/**
	 * 根据医院id查找医院接入平台的配置信息
	 * 
	 * @param hospitalId
	 *            医院id
	 * @return List<HospitalPlatformSettings>
	 */
	@Override
	public List<HospitalPlatformSettings> findByHospital(String hospitalId) {
		try {
			Assert.notNull(hospitalId);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_HOSPITAL), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID查询接入平台配置信息列表出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL)), e);
			throw new SystemException(String.format("根据医院ID查询接入平台配置信息列表出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL)), e);
		}
	}

	/**
	 * 根据医院id查找医院接入平台的配置信息
	 * 
	 * @param hospitalId
	 *            医院id
	 * @return List<HospitalPlatformSettings>
	 */
	@Override
	public <K, V> Map<K, HospitalPlatformSettings> findMapByHospital(String hospitalId, String mapKey) {
		try {
			Assert.notNull(hospitalId);
			return sqlSession.selectMap(getSqlName(SQLNAME_FIND_BY_HOSPITAL), hospitalId, mapKey);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID及接入平台ID查询菜单信息列表出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL)), e);
			throw new SystemException(String.format("根据医院ID及接入平台ID查询菜单信息列表出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.dao.HospitalPlatformSettingsDao#deleteByHospitalIds(java.lang.String)
	 */
	@Override
	public void deleteByHospitalId(String hospitalId) {
		Assert.notNull(hospitalId);
		try {
			sqlSession.delete(getSqlName(SQLNAME_DELETE_BY_HOSPITALID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院Id删除接入平台中间表信息出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_HOSPITALID)), e);
			throw new SystemException(String.format("根据医院Id删除接入平台中间表信息出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_HOSPITALID)), e);
		}
	}
}
