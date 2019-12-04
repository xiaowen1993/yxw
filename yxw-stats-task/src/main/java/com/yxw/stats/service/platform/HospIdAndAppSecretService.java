package com.yxw.stats.service.platform;

import java.util.List;

import com.yxw.stats.entity.platform.HospIdAndAppSecret;

public interface HospIdAndAppSecretService {
	/**
	* 获取可用的微信公众号app信息
	* */
	public List<HospIdAndAppSecret> findValidWechatAppInfo();
}
