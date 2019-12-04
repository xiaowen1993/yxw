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

import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

public class ComponentVerifyTicketCache {

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);


	public List<String> getComponentVerifyTicketByAppId(String componentAppId) {
		List<String> result = null;
		if (StringUtils.isNotBlank(componentAppId)) {
			String cacheKey = getComponentVerifyTicketCacheKey();
			String val = redisSvc.hget(cacheKey, componentAppId);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				String componentVerifyTicket = val;
				result = new ArrayList<String>(1);
				result.add(componentVerifyTicket);
			}
		}
		return result == null ? new ArrayList<String>(0) : result;
	}

	/**
	 * 更新token
	 */
	public int updateComponentVerifyTicket(String componentAppId, String componentVerifyTicket) {
		String cacheKey = getComponentVerifyTicketCacheKey();
		if (StringUtils.isNotBlank(componentAppId) && StringUtils.isNotBlank(componentVerifyTicket)) {
			return redisSvc.hset(cacheKey, componentAppId, componentVerifyTicket).intValue();
		}
		
		return 0;
	}

	/**
	 * 得到token缓存的key
	 * 
	 * @return
	 */
	public String getComponentVerifyTicketCacheKey() {
		return CacheConstant.CACHE_WECHAT_COMPONENT_VERIFY_TICKET_KEY_PREFIX;
	}

}
