package com.yxw.stats.service.project.alipay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.project.AlipaySettingsDao;
import com.yxw.stats.entity.project.AlipaySettings;
import com.yxw.stats.service.project.AlipaySettingsService;

@Repository("alipaySettingsService")
public class AlipaySettingsServiceImpl implements AlipaySettingsService {

	private Logger logger = LoggerFactory.getLogger(AlipaySettingsServiceImpl.class);

	private AlipaySettingsDao alipaySettingsDao = SpringContextHolder.getBean("alipaySettingsDao");

	public AlipaySettings findAlipaySettingsByAppId(String appId) {

		return alipaySettingsDao.findAlipaySettingsByAppId(appId);
	}
}