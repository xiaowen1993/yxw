package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.hospital.MsgMode;
import com.yxw.commons.entity.platform.hospital.PayMode;
import com.yxw.commons.entity.platform.hospital.Platform;
import com.yxw.commons.vo.platform.PlatformMsgModeVo;
import com.yxw.commons.vo.platform.PlatformPaymentVo;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;

public class PlatformCache {

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	public List<Platform> getAllPlatform() throws SystemException {
		List<Platform> platforms = null;
		String cacheKey = getCacheKey();
		Map<String, String> resultMap = redisSvc.hgetAll(cacheKey);
		if (!CollectionUtils.isEmpty(resultMap)) {
			platforms = new ArrayList<>(resultMap.size());
			for (String string : resultMap.values()) {
				platforms.add(JSON.parseObject(string, Platform.class));
			}
		}
		return platforms;
	}

	public List<PayMode> getPayModesByPlatformCode(String platformCode) {
		List<PayMode> payModes = null;
		String cacheKey = CacheConstant.CACHE_PLATFORM_PAY_MODE_KEY_PREFIX;
		String source = redisSvc.hget(cacheKey, platformCode);
		if (StringUtils.isNotBlank(source) && !source.equals(CacheConstant.CACHE_NULL_STRING)) {
			payModes = JSON.parseArray(source, PayMode.class);
		}
		return payModes == null ? new ArrayList<PayMode>(0) : payModes;
	}
	
	public List<MsgMode> getMsgModesByPlatformCode(String platformCode) {
		List<MsgMode> msgModes = null;
		String cacheKey = CacheConstant.CACHE_PLATFORM_MSG_MODE_KEY_PREFIX;
		String source = redisSvc.hget(cacheKey, platformCode);
		if (StringUtils.isNotBlank(source) && !source.equals(CacheConstant.CACHE_NULL_STRING)) {
			msgModes = JSON.parseArray(source, MsgMode.class);
		}
		return msgModes == null ? new ArrayList<MsgMode>(0) : msgModes;
	}


	public int cacheAllPlatform(List<Platform> platforms) throws SystemException {
		if (!CollectionUtils.isEmpty(platforms)) {
			Map<String, String> platformsMap = new HashMap<>();
			for (Platform platform : platforms) {
				platformsMap.put(platform.getCode(), JSON.toJSONString(platform));
			}

			String cacheKey = getCacheKey();
			redisSvc.hmset(cacheKey, platformsMap);

			return platforms.size();
		}

		return 0;
	}

	public int cacheAllPlatformPayModes(List<PlatformPaymentVo> vos) throws SystemException {
		if (!CollectionUtils.isEmpty(vos)) {
			Map<String, String> platformsMap = new HashMap<>();
			for (PlatformPaymentVo vo : vos) {
				if (!CollectionUtils.isEmpty(vo.getPayModes())) {
					platformsMap.put(vo.getPlatformCode(), JSON.toJSONString(vo.getPayModes()));
				}
			}

			String cacheKey = CacheConstant.CACHE_PLATFORM_PAY_MODE_KEY_PREFIX;
			redisSvc.hmset(cacheKey, platformsMap);

			return vos.size();
		}

		return 0;
	}
	
	public int cacheAllPlatformMsgModes(List<PlatformMsgModeVo> vos) throws SystemException {
		if (!CollectionUtils.isEmpty(vos)) {
			Map<String, String> platformsMap = new HashMap<>();
			for (PlatformMsgModeVo vo : vos) {
				if (!CollectionUtils.isEmpty(vo.getMsgModes())) {
					platformsMap.put(vo.getPlatformCode(), JSON.toJSONString(vo.getMsgModes()));
				}
			}

			String cacheKey = CacheConstant.CACHE_PLATFORM_MSG_MODE_KEY_PREFIX;
			redisSvc.hmset(cacheKey, platformsMap);

			return vos.size();
		}

		return 0;
	}

	private String getCacheKey() {
		return CacheConstant.CACHE_PLATFORM_KEY_PREFIX;
	}
}
