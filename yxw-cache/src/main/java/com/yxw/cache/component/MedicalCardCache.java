/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-20</p>
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
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.vo.cache.SimpleMedicalCard;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.cache.component
 * @ClassName: MedicalCardCache
 * @Statement: <p>
 *             医疗卡缓存 -- 目前规则， medicalcard:hospitalCode:branchCode. 可能存在不同分院下就诊卡号不同。
 *                        以后可能可以改成  medicalcard:hospitalCode ， 即该医院下，只有唯一的一个卡
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-20
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MedicalCardCache {
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	/**
	 * 批量保存医院与功能的关系到缓存
	 * @param medicalCards
	 * @return
	 */
	public int setMedicalCardsToCache(List<SimpleMedicalCard> medicalCards) {
		CacheConstant.logger.info("init MedicalCard Cache start......................");
		if (!CollectionUtils.isEmpty(medicalCards)) {
			Map<String, Map<String, List<SimpleMedicalCard>>> allCardMap = new HashMap<String, Map<String, List<SimpleMedicalCard>>>();
			for (SimpleMedicalCard card : medicalCards) {
				boolean isNeedPutMap = false;
				boolean isNeedPutList = false;
				String cacheKey = getCacheKey(card.getHospitalCode(), card.getBranchCode());
				Map<String, List<SimpleMedicalCard>> openIdCardMap = allCardMap.get(cacheKey);
				if (openIdCardMap == null) {
					openIdCardMap = new HashMap<String, List<SimpleMedicalCard>>();
					isNeedPutMap = true;
				}
				if (StringUtils.isBlank(card.getOpenId())) {
					continue;
				}
				String fieldName = card.getOpenId().trim();
				List<SimpleMedicalCard> cardList = openIdCardMap.get(fieldName);
				if (cardList == null) {
					cardList = new ArrayList<SimpleMedicalCard>();
					isNeedPutList = true;
				}
				cardList.add(card);
				if (isNeedPutList) {
					openIdCardMap.put(fieldName, cardList);
				}
				if (isNeedPutMap) {
					allCardMap.put(cacheKey, openIdCardMap);
				}
			}

			Map<String, Map<String, String>> allCardJsonMap = new HashMap<String, Map<String, String>>();
			Map<String, String> openIdCardJsonMap = new HashMap<String, String>();
			for (String cacheKey : allCardMap.keySet()) {
				Map<String, List<SimpleMedicalCard>> openIdCardMap = allCardMap.get(cacheKey);
				openIdCardJsonMap = new HashMap<String, String>();
				for (String fieldName : openIdCardMap.keySet()) {
					openIdCardJsonMap.put(fieldName, JSON.toJSONString(openIdCardMap.get(fieldName)));
				}
				allCardJsonMap.put(cacheKey, openIdCardJsonMap);
			}

			redisSvc.pipelineDatas(allCardJsonMap);
			CacheConstant.logger.info("init MedicalCard Cache end......................");

			return allCardJsonMap.size();
		}

		return 0;
	}

	/**
	 * 更新医疗卡缓存信息
	 * 
	 * @param medicalCard
	 * @param opType
	 *            操作类型 可选参数如下:<br>
	 *            CacheConstant.UPDATE_OP_TYPE_ADD 新增<br>
	 *            CacheConstant.UPDATE_OP_TYPE_UPDATE 更新<br>
	 *            CacheConstant.UPDATE_OP_TYPE_DEL 删除<br>
	 */
	public int setMedicalCardToCache(MedicalCard medicalCard, String opType) {
		String cacheKey = getCacheKey(medicalCard.getHospitalCode(), medicalCard.getBranchCode());
		String fieldName = medicalCard.getOpenId();
		SimpleMedicalCard simpleMedicalCard = new SimpleMedicalCard(medicalCard);

		boolean broken = false;
		Jedis jedis = redisSvc.getRedisClient();
		if (jedis != null) {
			try {
				String json = jedis.hget(cacheKey, fieldName);
				List<SimpleMedicalCard> medicalCards = new ArrayList<SimpleMedicalCard>();
				if (StringUtils.isNotBlank(json) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(json)) {
					medicalCards = JSON.parseArray(json, SimpleMedicalCard.class);
				}

				if (CacheConstant.UPDATE_OP_TYPE_ADD.equalsIgnoreCase(opType)) {
					medicalCards.add(simpleMedicalCard);
				} else if (CacheConstant.UPDATE_OP_TYPE_UPDATE.equalsIgnoreCase(opType)) {
					SimpleMedicalCard removeCard = null;
					for (SimpleMedicalCard card : medicalCards) {
						if (card.getId().equalsIgnoreCase(medicalCard.getId())) {
							removeCard = card;
							break;
						}
					}
					if (removeCard != null) {
						medicalCards.remove(removeCard);
					}
					medicalCards.add(simpleMedicalCard);
				} else if (CacheConstant.UPDATE_OP_TYPE_DEL.equalsIgnoreCase(opType)) {
					SimpleMedicalCard removeCard = null;
					for (SimpleMedicalCard card : medicalCards) {
						if (card.getId().equalsIgnoreCase(simpleMedicalCard.getId())) {
							removeCard = card;
							break;
						}
					}
					if (removeCard != null) {
						medicalCards.remove(removeCard);
					}
				}
				jedis.hset(cacheKey, fieldName, JSON.toJSONString(medicalCards));
			} catch (Exception e) {
				CacheConstant.logger.error(e.getMessage(), e);
				broken = true;
			} finally {
				redisSvc.returnResource(jedis, broken);
			}
		}

		return broken ? -1 : 1;
	}

	/**
	 * 将就诊卡数据写入缓存，新增或覆盖。
	 * @param medicalCards
	 */
	public int addMedicalcardsToCache(List<MedicalCard> medicalCards) {
		if (medicalCards.size() > 0) {
			MedicalCard firstCard = medicalCards.get(0);
			String cacheKey = getCacheKey(firstCard.getHospitalCode(), firstCard.getBranchCode());
			String fieldName = firstCard.getOpenId();

			boolean broken = false;
			Jedis jedis = redisSvc.getRedisClient();
			if (jedis != null) {
				try {
					List<SimpleMedicalCard> simpleCards = new ArrayList<SimpleMedicalCard>();
					for (MedicalCard card : medicalCards) {
						SimpleMedicalCard simpleCard = new SimpleMedicalCard(card);
						simpleCards.add(simpleCard);
					}
					jedis.hset(cacheKey, fieldName, JSON.toJSONString(simpleCards));
				} catch (Exception e) {
					CacheConstant.logger.error(e.getMessage(), e);
					broken = true;
				} finally {
					redisSvc.returnResource(jedis, broken);
				}
			}

			return broken ? -1 : 1;
		}

		return 0;
	}

	/**
	 * openId对应一家医院所有的绑卡
	 * 
	 * @param openId
	 * @param hospitalCode
	 * @return
	 */
	public List<SimpleMedicalCard> getMedicalCardsByOpenId(String openId, String hospitalCode) {
		Assert.notNull(openId, "getMedicalCardsByOpenId openId is null");
		Assert.notNull(hospitalCode, "getMedicalCardsByOpenId hospitalCode is null");
		String pattern = CacheConstant.CACHE_MEDICAL_CARD_HASH_PREFIX.concat(hospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR);
		Set<String> keysSet = redisSvc.keys(pattern.concat(CacheConstant.CACHE_KEY_PATTERN_CHAR));
		List<SimpleMedicalCard> cards = redisSvc.pipelineGetHDatas(keysSet, openId, SimpleMedicalCard.class);
		return cards;

	}

	/**
	 * 新平台指定区域所有医院所有的绑卡
	 * 区域为空的时候，查询所有
	 * area不能为null.否则找不到该方法
	 * @param openId
	 * @return
	 */
	public List<SimpleMedicalCard> getMedicalCardsByOpenIdAndArea(String openId, String area) {
		HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
		List<HospIdAndAppSecretVo> hospitalInfos = hospitalCache.findByAppSecretByAppCode(BizConstant.MODE_TYPE_APP, area);
		String pattern = null;
		String hospitalCode = null;
		Set<String> keysSet = new HashSet<String>();
		for (HospIdAndAppSecretVo vo : hospitalInfos) {
			hospitalCode = vo.getHospCode();
			pattern = CacheConstant.CACHE_MEDICAL_CARD_HASH_PREFIX.concat(hospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR);
			pattern = pattern.concat(CacheConstant.CACHE_KEY_PATTERN_CHAR);
			// modify by dfw
			// Set<String> cacheKeys = redisSvc.keys(pattern.concat(CacheConstant.CACHE_KEY_PATTERN_CHAR));
			Set<String> cacheKeys = redisSvc.keys(pattern);
			if (!CollectionUtils.isEmpty(cacheKeys)) {
				keysSet.addAll(cacheKeys);
			}
		}
		List<SimpleMedicalCard> cards = redisSvc.pipelineGetHDatas(keysSet, openId, SimpleMedicalCard.class);
		return cards;
	}

	/**
	 * 通过openId和familyId拿到某个医院下的卡
	 * @param openId
	 * @param familyId
	 * @param hospitalCode
	 * @return
	 */
	public List<SimpleMedicalCard> getMedicalCardForCommonQuery(String openId, String familyId, String hospitalCode) {
		List<SimpleMedicalCard> results = null;

		// 获取所有该医院下的该openId下的卡
		String pattern = CacheConstant.CACHE_MEDICAL_CARD_HASH_PREFIX.concat(hospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
				.concat(CacheConstant.CACHE_KEY_PATTERN_CHAR);
		Set<String> cacheKeys = redisSvc.keys(pattern);
		List<SimpleMedicalCard> cards = redisSvc.pipelineGetHDatas(cacheKeys, openId, SimpleMedicalCard.class);

		for (SimpleMedicalCard card : cards) {
			if (card.getFamilyId().equals(familyId)) {
				results = new ArrayList<SimpleMedicalCard>(1);
				results.add(card);
				break;
			}
		}

		return results == null ? new ArrayList<SimpleMedicalCard>(0) : results;
	}

	/**
	 * 查询所有用户所有区域所有医院的所有绑卡数据
	 * @param openId
	 * @return
	 */
	public List<SimpleMedicalCard> getMedicalCardsByOpenId(String openId) {
		return getMedicalCardsByOpenIdAndArea(openId, null);
	}

	/**
	 * 得到openIdappCrash对应医院的所有的绑卡
	 * 
	 * @param openId
	 * @param hospitalCode
	 * @param branchCode -- 就诊卡的分院目前是没用的。
	 * @return
	 */
	public List<SimpleMedicalCard> getMedicalCardsByOpenId(String openId, String hospitalCode, String branchCode) {
		// 获取医院的所有卡，不特指某个用户
		String pattern = CacheConstant.CACHE_MEDICAL_CARD_HASH_PREFIX.concat(hospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
				.concat(CacheConstant.CACHE_KEY_PATTERN_CHAR);
		Set<String> cacheKeys = redisSvc.keys(pattern);
		List<SimpleMedicalCard> cards = redisSvc.pipelineGetHDatas(cacheKeys, openId, SimpleMedicalCard.class);

		List<SimpleMedicalCard> validCards = new ArrayList<SimpleMedicalCard>();
		for (SimpleMedicalCard card : cards) {
			// if (card.getState() == null || card.getState() == 1) {
			if (card.getState() == 1) {
				validCards.add(card);
			}
		}
		return validCards;
	}

	public String getCacheKey(String hospitalCode, String branchHospitalCode) {
		String cacheKey = CacheConstant.CACHE_MEDICAL_CARD_HASH_PREFIX.concat(hospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR);
		cacheKey = cacheKey.concat(branchHospitalCode);
		return cacheKey;
	}
}
