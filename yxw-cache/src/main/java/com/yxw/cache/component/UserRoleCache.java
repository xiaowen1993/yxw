/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年9月10日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.cache.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.privilege.Role;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.cache.component
 * @ClassName: RoleResourceCache
 * @Statement: <p>权限系统-角色对应的资源缓存</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月10日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class UserRoleCache {
	//private static Logger logger = LoggerFactory.getLogger(UserRoleCache.class);

//	private UserService userService = SpringContextHolder.getBean(UserService.class);
//	private UserRoleService userRoleService = SpringContextHolder.getBean(UserRoleService.class);

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

//	private static boolean had_init = false;

	public List<Role> queryRoleByUserId(String userId) {
		List<Role> roleList = null;
		if (StringUtils.isNotBlank(userId)) {
			String cacheKey = getCacheKey();
			String fieldName = getFieldName(userId);
			String val = redisSvc.hget(cacheKey, fieldName);

			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				roleList = JSON.parseArray(val, Role.class);
			}
		}
		return roleList;
	}

	public int updateUserRole(String userId, List<Role> roleList) {
		if (StringUtils.isNotBlank(userId)) {
			String cacheKey = getCacheKey();
			String fieldName = getFieldName(userId);

			//覆盖
			return redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(roleList)).intValue();
		}
		
		return 0;
	}

	public int delUserRole(String... userIds) {
		if (userIds != null && userIds.length > 0) {
			String cacheKey = getCacheKey();
			String[] fieldNameArr = new String[userIds.length];
			for (int i = 0; i < userIds.length; i++) {
				fieldNameArr[i] = getFieldName(userIds[i]);
			}
			return redisSvc.hdel(cacheKey, fieldNameArr).intValue();
		}
		
		return 0;
	}

//	public void initUserRoleCache() {
//		if (!had_init) {
//			CacheConstant.logger.info("init userRole is start.................");
//
//			String cacheKey = getCacheKey();
//			String fieldName = null;
//
//			List<User> userList = userService.findAll();
//			if (CollectionUtils.isNotEmpty(userList)) {
//				for (User user : userList) {
//					String userId = user.getId();
//					List<Role> roleList = userRoleService.findRoleByUserId(userId);
//					if (CollectionUtils.isNotEmpty(roleList)) {
//						fieldName = getFieldName(userId);
//
//						redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(roleList));
//					}
//				}
//			}
//
//			had_init = true;
//			CacheConstant.logger.info("init userRole is end.................");
//		}
//	}
	
	/**
	 * 批量保存用户对应的资源到缓存
	 * @param roleMap
	 */
	public int setRolesToCache(Map<String, List<Role>> roleMap) {
		CacheConstant.logger.info("init userRole is start.................");
		String fieldName = null;
		if (!org.springframework.util.CollectionUtils.isEmpty(roleMap)) {
			String cacheKey = getCacheKey();
			Map<String, String> dataMap = new HashMap<String, String>(roleMap.size());
			for (Entry<String, List<Role>> role : roleMap.entrySet()) {
				if (CollectionUtils.isNotEmpty(role.getValue())) {
					fieldName = getFieldName(role.getKey());
					dataMap.put(fieldName, JSON.toJSONString(role.getValue()));
					//redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(role.getValue()));
				}
			}
			
			redisSvc.hmset(cacheKey, dataMap);
			
			return dataMap.size();
		}

		CacheConstant.logger.info("init userRole is end.................");
		
		return 0;
	}

	private String getCacheKey() {
		return CacheConstant.CACHE_USER_ROLE;
	}

	public String getFieldName(String userId) {
		return userId;
	}
}
