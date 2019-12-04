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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.platform.hospital.PaySettings;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.PaySettingsDao;

/**
 * 后台支付方式配置信息管理 Dao 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Repository
public class PaySettingsDaoImpl extends BaseDaoImpl<PaySettings, String> implements PaySettingsDao {

	private Logger logger = LoggerFactory.getLogger(PaySettingsDaoImpl.class);

	private static final String SQLNAME_FIND_BY_HOSPITAL_ID_AND_MODEIDS = "findByHospitalIdAndModeIds";

	private static final String SQLNAME_FIND_BY_HOSPITAL_ID_AND_MODE_CODE = "findByHospitalIdAndModeCode";

	private static final String SQLNAME_FIND_BY_PLATFORMSETTINGS_ID_AND_PAYMODEID = "findByPlatformSettingsIdAndPayModeId";

	@Override
	public List<PaySettings> findByHospitalIdAndModeIds(String hospitalId, String[] payModeIds) {
		try {
			HashMap<Object, Object> param = new HashMap<Object, Object>();
			param.put("hospitalId", hospitalId);
			param.put("payModeIds", payModeIds);
			return sqlSession.selectList(SQLNAME_FIND_BY_HOSPITAL_ID_AND_MODEIDS, param);
		} catch (Exception e) {
			logger.error(String.format("根据医院及支付方式ID查询支付配置出错！语句：%s", SQLNAME_FIND_BY_HOSPITAL_ID_AND_MODEIDS), e);
			throw new SystemException(String.format("根据医院及支付方式ID查询支付配置出错！语句：%s", SQLNAME_FIND_BY_HOSPITAL_ID_AND_MODEIDS), e);
		}
	}

	@Override
	public PaySettings findByHospitalIdAndModeCode(String hospitalId, String code) {
		try {
			HashMap<Object, Object> param = new HashMap<Object, Object>();
			param.put("hospitalId", hospitalId);
			param.put("code", code);
			return sqlSession.selectOne(SQLNAME_FIND_BY_HOSPITAL_ID_AND_MODE_CODE, param);
		} catch (Exception e) {
			logger.error(String.format("根据医院及Code查询是否是特约商户出错！语句：%s", SQLNAME_FIND_BY_HOSPITAL_ID_AND_MODE_CODE), e);
			throw new SystemException(String.format("根据医院及Code查询是否是特约商户出错！语句：%s", SQLNAME_FIND_BY_HOSPITAL_ID_AND_MODE_CODE), e);
		}
	}

	public PaySettings findByPlatformSettingsIdAndPayModeId(String platformSettingsId, String payModeId) {
		try {
			HashMap<Object, Object> param = new HashMap<Object, Object>();
			param.put("platformSettingsId", platformSettingsId);
			param.put("payModeId", payModeId);
			return sqlSession.selectOne(SQLNAME_FIND_BY_PLATFORMSETTINGS_ID_AND_PAYMODEID, param);
		} catch (Exception e) {
			logger.error(String.format("根据平台查询是否是特约商户出错！语句：%s", SQLNAME_FIND_BY_PLATFORMSETTINGS_ID_AND_PAYMODEID), e);
			throw new SystemException(String.format("根据平台查询是否是特约商户出错！语句：%s", SQLNAME_FIND_BY_PLATFORMSETTINGS_ID_AND_PAYMODEID), e);
		}
	}

}
