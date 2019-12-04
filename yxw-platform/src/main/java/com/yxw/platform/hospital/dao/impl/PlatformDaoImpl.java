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

import com.yxw.commons.entity.platform.hospital.Platform;
import com.yxw.commons.vo.platform.PlatformMsgModeVo;
import com.yxw.commons.vo.platform.PlatformPaymentVo;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.PlatformDao;

/**
 * 后台接入平台管理 Dao 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Repository
public class PlatformDaoImpl extends BaseDaoImpl<Platform, String> implements PlatformDao {

	private Logger logger = LoggerFactory.getLogger(PlatformDaoImpl.class);

	private final static String SQLNAME_FIND_ALL = "findAll";
	// 支付方式
	private final static String SQLNAME_FIND_BY_HOSPITAL_ID = "findByHospitalId";
	private final static String SQLNAME_FIND_ALL_PLATFORM_PAY_MODES = "findAllPlatformPayModes";
	private final static String SQLNAME_FIND_ALL_PLATFORM_PAY_MODES_BY_HOSPITAL_ID = "findAllPlatformPayModesByHospitalId";
	// 消息推送方式
	private final static String SQLNAME_FIND_MSG_MODES_BY_HOSPITAL_ID = "findMsgModesByHospitalId";
	private final static String SQLNAME_FIND_ALL_PLATFORM_MSG_MODES = "findAllPlatformMsgModes";
	private final static String SQLNAME_FIND_ALL_PLATFORM_MSG_MODES_BY_HOSPITAL_ID = "findAllPlatformMsgModesByHospitalId";

	@Override
	public Map<String, Platform> findMapAll(String mapKey) {
		try {
			return sqlSession.selectMap(getSqlName(SQLNAME_FIND_ALL), mapKey);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID查询接入平台配置信息列表出错！语句：%s", getSqlName(SQLNAME_FIND_ALL)), e);
			throw new SystemException(String.format("根据医院ID查询接入平台配置信息列表出错！语句：%s", getSqlName(SQLNAME_FIND_ALL)), e);
		}
	}

	@Override
	public List<PlatformPaymentVo> findAllPlatformPayModes() {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_PLATFORM_PAY_MODES));
		} catch (Exception e) {
			logger.error(String.format("获取所有平台支付出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_PLATFORM_PAY_MODES)), e);
			throw new SystemException(String.format("获取所有平台支付出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_PLATFORM_PAY_MODES)), e);
		}
	}

	@Override
	public List<PlatformPaymentVo> findAllPlatformPayModesByHospitalId(String hospitalId) {
		Assert.notNull(hospitalId);
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_PLATFORM_PAY_MODES_BY_HOSPITAL_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("获取平台支付出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_PLATFORM_PAY_MODES_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("根据是否启用条件获取平台支付出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_PLATFORM_PAY_MODES_BY_HOSPITAL_ID)), e);
		}
	}

	@Override
	public List<PlatformPaymentVo> findAllByHospitalId(String hospitalId) {
		Assert.notNull(hospitalId);
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID查询接入平台配置信息列表出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("根据医院ID查询接入平台配置信息列表出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID)), e);
		}
	}

	@Override
	public List<PlatformMsgModeVo> findAllPlatformMsgModes() {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_PLATFORM_MSG_MODES));
		} catch (Exception e) {
			logger.error(String.format("获取所有平台消息推送方式出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_PLATFORM_MSG_MODES)), e);
			throw new SystemException(String.format("获取所有平台消息推送方式出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_PLATFORM_MSG_MODES)), e);
		}
	}

	@Override
	public List<PlatformMsgModeVo> findAllPlatformMsgModesByHospitalId(String hospitalId) {
		Assert.notNull(hospitalId);
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_PLATFORM_MSG_MODES_BY_HOSPITAL_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID查询接入平台配置信息列表出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_PLATFORM_MSG_MODES_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("根据医院ID查询接入平台配置信息列表出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_PLATFORM_MSG_MODES_BY_HOSPITAL_ID)), e);
		}
	}

	@Override
	public List<PlatformMsgModeVo> findAllMsgModesByHospitalId(String hospitalId) {
		Assert.notNull(hospitalId);
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_MSG_MODES_BY_HOSPITAL_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID查询接入平台消息推送出错！语句：%s", getSqlName(SQLNAME_FIND_MSG_MODES_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("根据医院ID查询接入平台消息推送出错！语句：%s", getSqlName(SQLNAME_FIND_MSG_MODES_BY_HOSPITAL_ID)), e);
		}
	}

}
