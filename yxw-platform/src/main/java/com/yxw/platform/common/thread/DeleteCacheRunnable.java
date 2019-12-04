/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-27</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.common.thread;

import org.apache.commons.lang.StringUtils;

import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.mobileapp.biz.register.thread
 * @ClassName: DeleteCacheRunnable
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DeleteCacheRunnable implements Runnable {
	private String cacheKey;
	private String cacheField;
	private RedisService redisService = SpringContextHolder.getBean(RedisService.class);

	public DeleteCacheRunnable(String cacheKey) {
		super();
		this.cacheKey = cacheKey;
	}

	public DeleteCacheRunnable(String cacheKey, String cacheField) {
		super();
		this.cacheKey = cacheKey;
		this.cacheField = cacheField;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (StringUtils.isNotBlank(cacheKey)) {
			if (StringUtils.isNotBlank(cacheField)) {
				redisService.hdel(cacheKey, cacheField);
			} else {
				redisService.del(cacheKey);
			}
		}
	}
}
