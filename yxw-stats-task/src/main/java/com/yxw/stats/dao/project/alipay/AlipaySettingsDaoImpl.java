package com.yxw.stats.dao.project.alipay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.exception.SystemException;
import com.yxw.stats.dao.project.AlipaySettingsDao;
import com.yxw.stats.data.common.Dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.project.AlipaySettings;

@Repository("alipaySettingsDao")
public class AlipaySettingsDaoImpl extends BaseDaoImpl<AlipaySettings, Long> implements AlipaySettingsDao {

	private Logger logger = LoggerFactory.getLogger(AlipaySettingsDaoImpl.class);

	private final static String SQLNAME_FIND_ALIPAYSETTINGS_BY_APPID = "findAlipaySettingsByAppId";

	public AlipaySettings findAlipaySettingsByAppId(String appId) {

		AlipaySettings alipaySettings = null;
		try {

			alipaySettings = sqlSession.selectOne(getSqlName(SQLNAME_FIND_ALIPAYSETTINGS_BY_APPID), appId);
		} catch (Exception e) {
			logger.error(String.format("查询项目平台支付宝配置出错！语句：%s", getSqlName(SQLNAME_FIND_ALIPAYSETTINGS_BY_APPID)), e);
			throw new SystemException(String.format("查询项目平台支付宝配置出错！语句：%s", getSqlName(SQLNAME_FIND_ALIPAYSETTINGS_BY_APPID)), e);
		}

		return alipaySettings;
	}
}