/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */
package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.platform.cache
 * @ClassName: HospitalCache
 * @Statement: <p>
 *             医院及功能缓存信息
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-14
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HospitalAndOptionCache {

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	public HospitalAndOptionCache() {
		super();
	}

	/**
	 * 批量保存医院与功能的关系到缓存
	 * @param hospitalOptions
	 * @return
	 */
	public int setHospitalOptionsToCache(List<HospitalInfoByEasyHealthVo> hospitalOptions) {
		CacheConstant.logger.info("init HospitalOption config infos   start.................");

		/* redis缓存 */
		// 缓存医院与功能的关系
		if (!CollectionUtils.isEmpty(hospitalOptions)) {
			String cacheKey = getOptionCacheKey();
			// 功能Map
			Map<String, String> optionData = new HashMap<String, String>();
			for (HospitalInfoByEasyHealthVo vo : hospitalOptions) {
				String key = vo.getBizCode().concat(CacheConstant.SPLIT_CHAR).concat(vo.getAreaCode());
				List<HospitalInfoByEasyHealthVo> vos = new ArrayList<HospitalInfoByEasyHealthVo>();
				if (optionData.containsKey(key)) {
					vos = JSON.parseArray(optionData.get(key), HospitalInfoByEasyHealthVo.class);
				}
				vos.add(vo);
				optionData.put(key, JSON.toJSONString(vos));
			}
			redisSvc.del(cacheKey);
			redisSvc.hmset(cacheKey, optionData);
			CacheConstant.logger.info("optionData:{},", JSON.toJSONString(optionData));

			return optionData.size();
		}

		CacheConstant.logger.info("init HospitalOption config infos end.................");
		return 0;
	}

	/**
	 * 根据功能code获取已经配置的医院
	 * 
	 * @param bizCode
	 * @return json字符
	 */
	public List<String> getHospitalByOptionJson(String appCode, String bizCode, String areaCode) {
		
		String cahceKey = getOptionCacheKey();

		Set<String> fields = redisSvc.hkeys(cahceKey);
		List<String> matchFields = new ArrayList<String>();
		String matchField = appCode.concat(CacheConstant.SPLIT_CHAR).concat(bizCode).concat(CacheConstant.SPLIT_CHAR).concat(areaCode);
		if (!CollectionUtils.isEmpty(fields)) {
			for (String field : fields) {
				if (field.startsWith(matchField)) {
					matchFields.add(field);
				}
			}
		}

		List<String> result = null;
		if (!CollectionUtils.isEmpty(matchFields)) {
			String[] matchFieldArray = new String[matchFields.size()];
			List<String> jsons = redisSvc.hmget(cahceKey, matchFields.toArray(matchFieldArray));
			if (!CollectionUtils.isEmpty(jsons)) {
				result = new ArrayList<String>();
				for (String json : jsons) {
					if (StringUtils.isNotEmpty(json) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(json)) {
						result.addAll(JSON.parseArray(json, String.class));
					}
				}
			}
		}

		return result == null ? new ArrayList<String>(0) : result;
	}

	/**
	 * 根据功能code获取已经配置的医院
	 * 
	 * @param bizCode
	 * @return 医院集合
	 */
	public List<HospitalInfoByEasyHealthVo> getHospitalByOption(String appCode, String bizCode, String areaCode) {
		List<HospitalInfoByEasyHealthVo> result = null;
		List<String> jsons = getHospitalByOptionJson(appCode, bizCode, areaCode);
		if (!CollectionUtils.isEmpty(jsons)) {
			result = new ArrayList<HospitalInfoByEasyHealthVo>();
			for (String json : jsons) {
				HospitalInfoByEasyHealthVo hospitalInfoByEasyHealthVo = JSON.parseObject(json, HospitalInfoByEasyHealthVo.class);
				
				result.add(hospitalInfoByEasyHealthVo);
			}
		}
		return result == null ? new ArrayList<HospitalInfoByEasyHealthVo>(0) : result;
	}

	/**
	 * 根据功能code获取已经配置的医院
	 * 
	 * @param bizCode
	 * @return 医院集合
	 */
	public List<JSONObject> getHospitalJsonByOption(String appCode, String bizCode, String areaCode) {
		List<JSONObject> result = null;
		List<String> jsons = getHospitalByOptionJson(appCode, bizCode, areaCode);
		if (!CollectionUtils.isEmpty(jsons)) {
			result = new ArrayList<JSONObject>();
			for (String json : jsons) {
				result.add(JSON.parseObject(json, JSONObject.class));
			}
		}
		
		return result == null ? new ArrayList<JSONObject>(0) : result;
	}

	/**
	 * 更新缓存
	 * 
	 * @param bizCode
	 * @param hospitalId
	 */
	public int updateHospitalByMap(Map<String, Object> cacheOptionMap, String hospitalCode) {
		String cacheKey = getOptionCacheKey();
		Map<String, String> optionData = redisSvc.hgetAll(cacheKey);
		if (!optionData.isEmpty()) {
			for (String key : optionData.keySet()) {// 遍历所有功能
				List<HospitalInfoByEasyHealthVo> vos = JSON.parseArray(optionData.get(key), HospitalInfoByEasyHealthVo.class);
				if (!CollectionUtils.isEmpty(vos)) {// 先删除当前医院的所有功能
					for (HospitalInfoByEasyHealthVo vo : vos) {
						if (vo.getHospitalCode().equals(hospitalCode)) {
							vos.remove(vo);
							optionData.put(key, JSON.toJSONString(vos));
							break;
						}
					}
				}
			}
		}

		for (String key : cacheOptionMap.keySet()) {
			List<HospitalInfoByEasyHealthVo> vos = JSON.parseArray(optionData.get(key), HospitalInfoByEasyHealthVo.class);
			if (CollectionUtils.isEmpty(vos)) {
				vos = new ArrayList<HospitalInfoByEasyHealthVo>();
			}

			vos.add((HospitalInfoByEasyHealthVo) cacheOptionMap.get(key));
			optionData.put(key, JSON.toJSONString(vos));
		}
		redisSvc.del(cacheKey);
		redisSvc.hmset(cacheKey, optionData);

		return cacheOptionMap.size();
	}

	/**
	 * 获取医院与功能缓存key
	 * 
	 * @return
	 */
	private String getOptionCacheKey() {
		return CacheConstant.CACHE_AREA_OPTION_KEY_PREFIX;
	}
}
