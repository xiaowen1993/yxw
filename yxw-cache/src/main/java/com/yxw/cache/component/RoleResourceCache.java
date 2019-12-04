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
import com.yxw.commons.entity.platform.privilege.Resource;
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
public class RoleResourceCache {
	//private static Logger logger = LoggerFactory.getLogger(RoleResourceCache.class);

//	private RoleService roleService = SpringContextHolder.getBean(RoleService.class);
//	private RoleResourceService roleResourceService = SpringContextHolder.getBean(RoleResourceService.class);

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

//	private static boolean had_init = false;

	public List<Resource> queryResourcesByRoleId(String roleId) {
		List<Resource> resourceList = null;
		if (StringUtils.isNotBlank(roleId)) {
			String cacheKey = getCacheKey();
			String fieldName = getFieldName(roleId);
			String val = redisSvc.hget(cacheKey, fieldName);

			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				resourceList = JSON.parseArray(val, Resource.class);
			}
		}
		return resourceList;
	}

	public int updateRoleResource(String roleId, List<Resource> resourceList) {
		if (StringUtils.isNotBlank(roleId)) {
			String cacheKey = getCacheKey();
			String fieldName = getFieldName(roleId);

			//覆盖
			return redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(resourceList)).intValue();
		}
		
		return 0;
	}

	public int delRoleResource(String... roleIds) {
		if (roleIds != null && roleIds.length > 0) {
			String cacheKey = getCacheKey();
			String[] fieldNameArr = new String[roleIds.length];
			for (int i = 0; i < roleIds.length; i++) {
				fieldNameArr[i] = getFieldName(roleIds[i]);
			}
			return redisSvc.hdel(cacheKey, fieldNameArr).intValue();
		}
		
		return 0;
	}

//	public void initRoleResourceCache() {
//		if (!had_init) {
//			CacheConstant.logger.info("init roleResource is start.................");
//
//			String cacheKey = getCacheKey();
//			String fieldName = null;
//
//			List<Role> roleList = roleService.findAll();
//			if (CollectionUtils.isNotEmpty(roleList)) {
//				for (Role role : roleList) {
//					String roleId = role.getId();
//					List<Resource> resourceList = roleResourceService.findResourceByRoleId(roleId);
//					if (CollectionUtils.isNotEmpty(resourceList)) {
//						fieldName = getFieldName(roleId);
//
//						redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(resourceList));
//					}
//				}
//			}
//
//			had_init = true;
//			CacheConstant.logger.info("init roleResource is end.................");
//		}
//	}
	
	/**
	 * 批量保存角色对应的资源到缓存
	 * @param resourceMap
	 */
	public int setRoleResourcesToCache(Map<String, List<Resource>> resourceMap) {
		CacheConstant.logger.info("init roleResource is start.................");

		if (!org.springframework.util.CollectionUtils.isEmpty(resourceMap)) {
			String cacheKey = getCacheKey();
			Map<String, String> dataMap = new HashMap<String, String>(resourceMap.size());
			for (Entry<String, List<Resource>> resource : resourceMap.entrySet()) {
				if (CollectionUtils.isNotEmpty(resource.getValue())) {
					 String fieldName = getFieldName(resource.getKey());
					 dataMap.put(fieldName, JSON.toJSONString(resource.getValue()));
					 // redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(resource.getValue()));
				}
			}
			
			redisSvc.hmset(cacheKey, dataMap);
			
			return dataMap.size();
		}

		CacheConstant.logger.info("init roleResource is end.................");
		
		return 0;
	}

	private String getCacheKey() {
		return CacheConstant.CACHE_ROLE_RESOURCE;
	}

	public String getFieldName(String roleId) {
		return roleId;
	}
}
