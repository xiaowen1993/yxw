/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-8-12</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.vo.cache.WhiteListVo;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.cache.component
 * @ClassName: WhiteListCache
 * @Statement: <p>
 *             白名单缓存
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-8-12
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class WhiteListCache {
	private static Logger logger = LoggerFactory.getLogger(WhiteListCache.class);
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	//	private CommonService commonService = SpringContextHolder.getBean(CommonService.class);

	//	public void initCahce() {
	//		List<WhiteListVo> vos = commonService.findAllWhiteDetails();
	//		if (!CollectionUtils.isEmpty(vos)) {
	//			Map<String, String> dataMap = new HashMap<String, String>();
	//			String cacheKey = getCacheKey();
	//			for (WhiteListVo vo : vos) {
	//				String fied = null;
	//				if (StringUtils.isNotBlank(vo.getOpenId())) {
	//					fied = getOpenIdField(vo);
	//					dataMap.put(fied, JSON.toJSONString(vo));
	//				}
	//
	//				if (StringUtils.isNotBlank(vo.getPhone())) {
	//					fied = getPhoneField(vo);
	//					dataMap.put(fied, JSON.toJSONString(vo));
	//				}
	//
	//				String setWhiteFied = vo.getAppId().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(vo.getAppCode());
	//				dataMap.put(setWhiteFied, "true");
	//			}
	//			if (!dataMap.isEmpty()) {
	//				redisSvc.hmset(cacheKey, dataMap);
	//			}
	//		}
	//	}

	public int setWhiteListToCache(List<WhiteListVo> vos) {
		if (!CollectionUtils.isEmpty(vos)) {
			Map<String, String> dataMap = new HashMap<String, String>();
			String cacheKey = getCacheKey();
			for (WhiteListVo vo : vos) {
				String fied = null;
				if (StringUtils.isNotBlank(vo.getOpenId())) {
					fied = getOpenIdField(vo);
					dataMap.put(fied, JSON.toJSONString(vo));
				}

				if (StringUtils.isNotBlank(vo.getPhone())) {
					fied = getPhoneField(vo);
					dataMap.put(fied, JSON.toJSONString(vo));
				}

				String setWhiteFied = vo.getAppId().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(vo.getAppCode());
				dataMap.put(setWhiteFied, "true");
			}
			if (!dataMap.isEmpty()) {
				redisSvc.hmset(cacheKey, dataMap);

				return dataMap.size();
			}
		}

		return 0;
	}

	/**
	 * 添加白名单信息到缓存
	 * 
	 * @param vo
	 */
	public int addWhiteInfo(WhiteListVo vo) {
		String cacheKey = getCacheKey();
		Map<String, String> dataMap = new HashMap<String, String>();
		String fied = null;
		if (StringUtils.isNotBlank(vo.getOpenId())) {
			fied = getOpenIdField(vo);
			dataMap.put(fied, JSON.toJSONString(vo));
		}

		if (StringUtils.isNotBlank(vo.getPhone())) {
			fied = getPhoneField(vo);
			dataMap.put(fied, JSON.toJSONString(vo));
		}

		String setWhiteFied = vo.getAppId().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(vo.getAppCode());
		dataMap.put(setWhiteFied, "true");
		redisSvc.hmset(cacheKey, dataMap);

		return dataMap.size();
	}

	/**
	 * 删除缓存中白名单用户
	 * 
	 * @param vo
	 */
	public int delWhiteDetail(WhiteListVo vo) {
		String cacheKey = getCacheKey();

		List<String> fieds = new ArrayList<String>();
		String fied = null;
		if (StringUtils.isNotBlank(vo.getOpenId())) {
			fied = getOpenIdField(vo);
			fieds.add(fied);
		}

		if (StringUtils.isNotBlank(vo.getPhone())) {
			fied = getPhoneField(vo);
			fieds.add(fied);
		}
		if (!CollectionUtils.isEmpty(fieds)) {
			String[] filedArray = new String[fieds.size()];
			return redisSvc.hdel(cacheKey, fieds.toArray(filedArray)).intValue();
		}

		return 0;
	}

	/**
	 * 
	 * 医院对应的白名单人员缓存 fied-->appId:appCode:openId appId:appCode:phone 医院对应的白名单总设置
	 * fied-->appId:appCode
	 * 
	 * @param appId
	 * @param appCode
	 */
	//	public void updateWhiteInfo(String appId, String appCode, Integer isOpen) {
	//	String cacheKey = getCacheKey();
	//	if (BizConstant.WHITE_LIST_IS_OPEN_YES == isOpen.intValue()) {
	//		List<WhiteListVo> vos = commonService.findWhiteDetailsByApp(appId, appCode);
	//		Map<String, String> dataMap = new HashMap<String, String>();
	//		if (!CollectionUtils.isEmpty(vos)) {
	//			for (WhiteListVo vo : vos) {
	//				if (StringUtils.isNotBlank(vo.getOpenId())) {
	//					String openIdFied = getOpenIdField(vo);
	//					dataMap.put(openIdFied, JSON.toJSONString(vo));
	//				}
	//
	//				if (StringUtils.isNotBlank(vo.getPhone())) {
	//					String phoneFied = getPhoneField(vo);
	//					dataMap.put(phoneFied, JSON.toJSONString(vo));
	//				}
	//			}
	//		}
	//
	//		String setWhiteFied = appId.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(appCode);
	//		dataMap.put(setWhiteFied, "true");
	//		redisSvc.hmset(cacheKey, dataMap);
	//
	//	} else {
	//		Set<String> fieds = redisSvc.hkeys(cacheKey);
	//		if (!CollectionUtils.isEmpty(fieds)) {
	//			Set<String> needRemoveFieds = new HashSet<String>();
	//
	//			String prefix = appId.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(appCode);
	//			for (String fied : fieds) {
	//				if (fied.startsWith(prefix)) {
	//					needRemoveFieds.add(fied);
	//				}
	//			}
	//
	//			if (!CollectionUtils.isEmpty(needRemoveFieds)) {
	//				String[] fiedArray = new String[needRemoveFieds.size()];
	//				needRemoveFieds.toArray(fiedArray);
	//				redisSvc.hdel(cacheKey, fiedArray);
	//			}
	//		}
	//	}
	//}

	/**
	 * 开启白名单的相关缓存操作
	 * @param appId
	 * @param appCode
	 * @param vos
	 */
	public int openWhiteList(String appId, String appCode, List<WhiteListVo> vos) {
		String cacheKey = getCacheKey();
		Map<String, String> dataMap = new HashMap<String, String>();
		if (!CollectionUtils.isEmpty(vos)) {
			for (WhiteListVo vo : vos) {
				if (StringUtils.isNotBlank(vo.getOpenId())) {
					String openIdFied = getOpenIdField(vo);
					dataMap.put(openIdFied, JSON.toJSONString(vo));
				}

				if (StringUtils.isNotBlank(vo.getPhone())) {
					String phoneFied = getPhoneField(vo);
					dataMap.put(phoneFied, JSON.toJSONString(vo));
				}
			}
		}

		String setWhiteFied = appId.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(appCode);
		dataMap.put(setWhiteFied, "true");
		redisSvc.hmset(cacheKey, dataMap);

		return dataMap.size();
	}

	/**
	 * 关闭白名单的相关缓存操作
	 * @param appId
	 * @param appCode
	 */
	public int closeWhiteList(String appId, String appCode) {
		String cacheKey = getCacheKey();
		Set<String> fieds = redisSvc.hkeys(cacheKey);
		if (!CollectionUtils.isEmpty(fieds)) {
			Set<String> needRemoveFieds = new HashSet<String>();

			String prefix = appId.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(appCode);
			for (String fied : fieds) {
				if (fied.startsWith(prefix)) {
					needRemoveFieds.add(fied);
				}
			}

			if (!CollectionUtils.isEmpty(needRemoveFieds)) {
				String[] fiedArray = new String[needRemoveFieds.size()];
				needRemoveFieds.toArray(fiedArray);
				return redisSvc.hdel(cacheKey, fiedArray).intValue();
			}
		}

		return 0;
	}

	/**
	 * 是否开启白名单
	 * 
	 * @category 已调整，返回值类型由boolean改为int
	 * @param appId
	 * @param appCode
	 * @return
	 */
	public int isOpenWhiteList(String appId, String appCode) {
		String cacheKey = getCacheKey();

		Boolean isOpen = true;

		// 判断医院是否启用白名单
		String setWhiteFied = appId.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(appCode);
		String isSetUp = redisSvc.hget(cacheKey, setWhiteFied);
		if (StringUtils.isBlank(isSetUp) || CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(isSetUp)) {
			isOpen = false;
		}

		return isOpen ? 1 : 0;
	}

	/**
	 * 
	 * @category 已调整，返回值类型由boolean改为int
	 * @param appId
	 * @param appCode
	 * @param openIdOrPhone
	 * @return
	 */
	public int isValidWhiteOpenIdOrPhone(String appId, String appCode, String openIdOrPhone) {
		String cacheKey = getCacheKey();

		// 判断医院是否启用白名单
		int isOpen = isOpenWhiteList(appId, appCode);
		if (isOpen != 1) {
			if (logger.isDebugEnabled()) {
				logger.debug("appId:{} , not set up white list.");
			}
			return 1;
		}

		if (StringUtils.isNotEmpty(openIdOrPhone)) {
			openIdOrPhone = openIdOrPhone.replaceAll(" ", "+");
		}
		// 判断openId是否在白名单内
		String field =
				appId.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(appCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(openIdOrPhone);
		logger.debug("isValidWhiteOpenIdOrPhone.field:{}", field);
		String val = redisSvc.hget(cacheKey, field);
		if (StringUtils.isNotBlank(val) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(field)) {
			return 1;
		} else {
			return 0;
		}
	}

	public List<Object> hgetAllWhiteList() {
		List<Object> results = null;
		Map<String, String> whiteListAll = redisSvc.hgetAll(getCacheKey());
		if (!CollectionUtils.isEmpty(whiteListAll)) {
			results = new ArrayList<Object>(0);
			results.add(whiteListAll);
		}

		return results;
	}

	public String getCacheKey() {
		return CacheConstant.CACHE_WHITE_LIST;
	}

	public String getOpenIdField(WhiteListVo vo) {
		return vo.getAppId().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(vo.getAppCode()).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
				.concat(vo.getOpenId());
	}

	public String getPhoneField(WhiteListVo vo) {
		return vo.getAppId().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(vo.getAppCode()).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
				.concat(vo.getPhone());
	}
}
