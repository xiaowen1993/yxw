/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-3</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.vo.cache.AccessTokenVo;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.cache.component
 * @ClassName: HospitalRuleCache
 * @Statement: <p>
 *             微信accessToken管理
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-3
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class AccessTokenCache {

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	public void initRuleCache() {

	}

	/**
	 * 根据医院code获取 编辑规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<AccessTokenVo> getTokenByAppId(String appId) {
		List<AccessTokenVo> result = null;
		AccessTokenVo accessTokenVo = null;
		if (StringUtils.isNotBlank(appId)) {
			String cacheKey = getTokenCacheKey();
			String val = redisSvc.hget(cacheKey, appId);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				accessTokenVo = JSON.parseObject(val, AccessTokenVo.class);
				result = new ArrayList<AccessTokenVo>(1);
				result.add(accessTokenVo);
			}
		}
		return result == null ? new ArrayList<AccessTokenVo>(0) : result;
	}

	/**
	 * 更新token
	 * 
	 * @param onlineFiling
	 */
	public int updateToken(String appId, AccessTokenVo accessToken) {
		String cacheKey = getTokenCacheKey();
		if (StringUtils.isNotBlank(appId) && accessToken != null && StringUtils.isNotBlank(accessToken.getAccessToken())
				&& StringUtils.isNotBlank(accessToken.getExpiresTime())) {
			return redisSvc.hset(cacheKey, appId, JSON.toJSONString(accessToken)).intValue();
		}
		
		return 0;
	}

	/**
	 * 得到token缓存的key
	 * 
	 * @return
	 */
	public String getTokenCacheKey() {
		return CacheConstant.CACHE_HOSPITAL_WECHAT_TOKEN_KEY_PREFIX;
	}

}
