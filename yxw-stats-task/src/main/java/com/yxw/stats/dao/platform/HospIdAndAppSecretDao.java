package com.yxw.stats.dao.platform;

import java.util.List;

import com.yxw.stats.entity.platform.HospIdAndAppSecret;

public interface HospIdAndAppSecretDao {
	/**
	* 获取可用的微信公众号app信息
	* */
	public List<HospIdAndAppSecret> findValidWechatAppInfo();
}
