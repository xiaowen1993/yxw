package com.yxw.stats.service.platform;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.platform.HospIdAndAppSecretDao;
import com.yxw.stats.entity.platform.HospIdAndAppSecret;

@Service(value = "hospIdAndAppSecretService")
public class HospIdAndAppSecretServiceImpl implements HospIdAndAppSecretService {

	private HospIdAndAppSecretDao hospIdAndAppSecretDao = SpringContextHolder.getBean("hospIdAndAppSecretDao");

	/**
	 * 获取可用的微信公众号app信息
	 * */
	@Override
	public List<HospIdAndAppSecret> findValidWechatAppInfo() {

		return hospIdAndAppSecretDao.findValidWechatAppInfo();
	}

}
