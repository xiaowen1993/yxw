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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.rule.RuleClinic;
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.entity.platform.rule.RuleFriedExpress;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.entity.platform.rule.RuleInHospital;
import com.yxw.commons.entity.platform.rule.RuleOnlineFiling;
import com.yxw.commons.entity.platform.rule.RulePayment;
import com.yxw.commons.entity.platform.rule.RulePush;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.entity.platform.rule.RuleTiedCard;
import com.yxw.commons.entity.platform.rule.RuleUserCenter;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.cache.component
 * @ClassName: HospitalRuleCache
 * @Statement: <p>
 *             医院配置规则缓存管理
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-3
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HospitalRuleCache {
	//	private RuleEditService editService = SpringContextHolder.getBean(RuleEditService.class);
	//	private RuleOnlineFilingService onlineFilingService = SpringContextHolder.getBean(RuleOnlineFilingService.class);
	//	private RuleTiedCardService tiedCardService = SpringContextHolder.getBean(RuleTiedCardService.class);
	//	private RuleRegisterService registerService = SpringContextHolder.getBean(RuleRegisterService.class);
	//	private RuleQueryService queryService = SpringContextHolder.getBean(RuleQueryService.class);
	//	private RulePaymentService paymentService = SpringContextHolder.getBean(RulePaymentService.class);
	//	private RulePushService pushService = SpringContextHolder.getBean(RulePushService.class);
	//	private RuleUserCenterService userCenterService = SpringContextHolder.getBean(RuleUserCenterService.class);

	private HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	//	private static boolean had_init = false;

	/**
	 * 各医院页面底部Logo信息缓存  因数据不大 故使用static map直接缓存
	 */
	public static ConcurrentHashMap<String, String> hospitalFootLogoCache = new ConcurrentHashMap<String, String>();

	//	public void initRuleCache() {
	//		if (!had_init) {
	//			CacheConstant.logger.info("init hospital's rules is start.................");
	//			// 系统中的所有医院code 与 id对应关系
	//			ConcurrentHashMap<String, CodeAndInterfaceVo> voMap = hospitalCache.getCodeAndInterfaceMap();
	//			Map<String, String> codeAndIdMap = new HashMap<String, String>();
	//			for (CodeAndInterfaceVo vo : voMap.values()) {
	//				codeAndIdMap.put(vo.getHospitalId(), vo.getHospitalCode());
	//			}
	//
	//			String cacheKey = getRuleCacheKey();
	//			String fieldName = null;
	//
	//			Map<String, String> dataMap = new HashMap<String, String>();
	//			// 系统所有的ruleEdit
	//			List<RuleEdit> edits = editService.findAll();
	//
	//			//医院页面底部Logo信息
	//			String hospitalFootLogo = null;
	//			for (RuleEdit edit : edits) {
	//				String hospitalCode = codeAndIdMap.get(edit.getHospitalId());
	//				if (StringUtils.isEmpty(hospitalCode)) {
	//					continue;
	//				}
	//				if (StringUtils.isNotBlank(hospitalCode)) {
	//					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_EDIT);
	//					dataMap.put(fieldName, JSON.toJSONString(edit));
	//				}
	//
	//				hospitalFootLogo = edit.getFootLogoInfo();
	//				if (hospitalFootLogo == null) {
	//					hospitalFootLogo = "";
	//				}
	//				hospitalFootLogoCache.put(hospitalCode, hospitalFootLogo);
	//			}
	//
	//			// 在线建档规则
	//			List<RuleOnlineFiling> onlineFilings = onlineFilingService.findAll();
	//			for (RuleOnlineFiling onlineFiling : onlineFilings) {
	//				String hospitalCode = codeAndIdMap.get(onlineFiling.getHospitalId());
	//				if (StringUtils.isNotBlank(hospitalCode)) {
	//					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_ONLINEFILING);
	//					dataMap.put(fieldName, JSON.toJSONString(onlineFiling));
	//				}
	//			}
	//
	//			// 绑卡规则
	//			List<RuleTiedCard> tiedCards = tiedCardService.findAll();
	//			for (RuleTiedCard tiedCard : tiedCards) {
	//				String hospitalCode = codeAndIdMap.get(tiedCard.getHospitalId());
	//				if (StringUtils.isNotBlank(hospitalCode)) {
	//					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_TIEDCARD);
	//					dataMap.put(fieldName, JSON.toJSONString(tiedCard));
	//				}
	//			}
	//
	//			// 挂号规则
	//			List<RuleRegister> registers = registerService.findAll();
	//			for (RuleRegister register : registers) {
	//				String hospitalCode = codeAndIdMap.get(register.getHospitalId());
	//				if (StringUtils.isNotBlank(hospitalCode)) {
	//					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_REGISTER);
	//					dataMap.put(fieldName, JSON.toJSONString(register));
	//				}
	//			}
	//
	//			// 查询规则
	//			List<RuleQuery> querys = queryService.findAll();
	//			for (RuleQuery query : querys) {
	//				String hospitalCode = codeAndIdMap.get(query.getHospitalId());
	//				if (StringUtils.isNotBlank(hospitalCode)) {
	//					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_QUERY);
	//					dataMap.put(fieldName, JSON.toJSONString(query));
	//				}
	//			}
	//
	//			// 缴费规则
	//			List<RulePayment> payments = paymentService.findAll();
	//			for (RulePayment payment : payments) {
	//				String hospitalCode = codeAndIdMap.get(payment.getHospitalId());
	//				if (StringUtils.isNotBlank(hospitalCode)) {
	//					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_PAYMENT);
	//					dataMap.put(fieldName, JSON.toJSONString(payment));
	//				}
	//			}
	//
	//			//推送规则
	//			List<RulePush> pushs = pushService.findAll();
	//			for (RulePush push : pushs) {
	//				String hospitalCode = codeAndIdMap.get(push.getHospitalId());
	//				if (StringUtils.isNotBlank(hospitalCode)) {
	//					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_PUSH.concat(".").concat(push.getPlatformType()));
	//					dataMap.put(fieldName, JSON.toJSONString(push));
	//				}
	//			}
	//
	//			// 个人中心规则
	//			List<RuleUserCenter> centers = userCenterService.findAll();
	//			for (RuleUserCenter center : centers) {
	//				String hospitalCode = codeAndIdMap.get(center.getHospitalId());
	//				if (StringUtils.isNotBlank(hospitalCode)) {
	//					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_USERCENTER);
	//					dataMap.put(fieldName, JSON.toJSONString(center));
	//				}
	//			}
	//
	//			if (!dataMap.isEmpty()) {
	//				redisSvc.hmset(cacheKey, dataMap);
	//			}
	//
	//			had_init = true;
	//
	//			CacheConstant.logger.info("init hospital's rules is end .................");
	//		}
	//	}

	/**
	 * @param hospitalFootLogoCache the hospitalFootLogoCache to set
	 */
	public int setHospitalFootLogo(String hospitalCode, String hospitalFootLogo) {
		hospitalFootLogoCache.put(hospitalCode, hospitalFootLogo);

		return hospitalFootLogoCache.size();
	}

	/**
	 * 批量保存医院页面底部Logo信息到缓存
	 * @param editMap
	 */
	public int setRuleEditToCache(Map<String, RuleEdit> editMap) {
		CacheConstant.logger.info("init hospital's edit rule is start.................");

		if (org.springframework.util.CollectionUtils.isEmpty(editMap)) {
			String fieldName = null;
			String hospitalFootLogo = null;
			Map<String, String> dataMap = new HashMap<String, String>();
			for (Entry<String, RuleEdit> editEntry : editMap.entrySet()) {
				String hospitalCode = editEntry.getKey();
				if (StringUtils.isEmpty(hospitalCode)) {
					continue;
				}

				fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_EDIT);
				hospitalFootLogo = editEntry.getValue().getFootLogoInfo();
				if (hospitalFootLogo == null) {
					hospitalFootLogo = "";
				}

				dataMap.put(fieldName, JSON.toJSONString(editEntry));
				hospitalFootLogoCache.put(hospitalCode, hospitalFootLogo);
			}
			redisSvc.hmset(getRuleCacheKey(), dataMap);

			return dataMap.size();
		}

		CacheConstant.logger.info("init hospital's edit rule is end .................");

		return 0;
	}

	/**
	 * 批量保存在线建档规则到缓存
	 * @param onlineFilingMap
	 */
	public int setRuleOnlineFilingsToCache(Map<String, RuleOnlineFiling> onlineFilingMap) {
		CacheConstant.logger.info("init hospital's online filing rule is start.................");

		if (!org.springframework.util.CollectionUtils.isEmpty(onlineFilingMap)) {
			String fieldName = null;
			Map<String, String> dataMap = new HashMap<String, String>();
			for (Entry<String, RuleOnlineFiling> onlineFilingEntry : onlineFilingMap.entrySet()) {
				String hospitalCode = onlineFilingEntry.getKey();
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_ONLINEFILING);
					dataMap.put(fieldName, JSON.toJSONString(onlineFilingEntry.getValue()));
				}
			}
			redisSvc.hmset(getRuleCacheKey(), dataMap);

			return dataMap.size();
		}

		CacheConstant.logger.info("init hospital's online filing rule is end.................");

		return 0;
	}

	/**
	 * 批量保存绑卡规则到缓存
	 * @param tiedCardMap
	 */
	public int setRuleTiedCardsToCache(Map<String, RuleTiedCard> tiedCardMap) {
		CacheConstant.logger.info("init hospital's tied filing rule is start.................");

		if (!org.springframework.util.CollectionUtils.isEmpty(tiedCardMap)) {
			String fieldName = null;
			Map<String, String> dataMap = new HashMap<String, String>();
			for (Entry<String, RuleTiedCard> tiedCardEntry : tiedCardMap.entrySet()) {
				String hospitalCode = tiedCardEntry.getKey();
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_TIEDCARD);
					dataMap.put(fieldName, JSON.toJSONString(tiedCardEntry.getValue()));
				}
			}
			redisSvc.hmset(getRuleCacheKey(), dataMap);

			return dataMap.size();
		}

		CacheConstant.logger.info("init hospital's tied filing rule is end.................");

		return 0;
	}

	/**
	 * 批量缓存挂号规则到缓存
	 * @param registerMap
	 */
	public int setRuleRegistersToCache(Map<String, RuleRegister> registerMap) {
		CacheConstant.logger.info("init hospital's register rule is start.................");

		if (!org.springframework.util.CollectionUtils.isEmpty(registerMap)) {
			String fieldName = null;
			Map<String, String> dataMap = new HashMap<String, String>();
			for (Entry<String, RuleRegister> registerEntry : registerMap.entrySet()) {
				String hospitalCode = registerEntry.getKey();
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_REGISTER);
					dataMap.put(fieldName, JSON.toJSONString(registerEntry.getValue()));
				}
			}
			redisSvc.hmset(getRuleCacheKey(), dataMap);

			return dataMap.size();
		}

		CacheConstant.logger.info("init hospital's register rule is end.................");

		return 0;
	}

	/**
	 * 批量保存查询规则到缓存
	 * @param queryMap
	 */
	public int setRuleQuerysToCache(Map<String, RuleQuery> queryMap) {
		CacheConstant.logger.info("init hospital's query rule is start.................");

		if (!org.springframework.util.CollectionUtils.isEmpty(queryMap)) {
			String fieldName = null;
			Map<String, String> dataMap = new HashMap<String, String>();
			for (Entry<String, RuleQuery> queryEntry : queryMap.entrySet()) {
				String hospitalCode = queryEntry.getKey();
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_QUERY);
					dataMap.put(fieldName, JSON.toJSONString(queryEntry.getValue()));
				}
			}
			redisSvc.hmset(getRuleCacheKey(), dataMap);

			return dataMap.size();
		}

		CacheConstant.logger.info("init hospital's query rule is end.................");

		return 0;
	}

	/**
	 * 批量保存缴费规则到缓存
	 * @param paymentMap
	 */
	public int setRulePaymentsToCache(Map<String, RulePayment> paymentMap) {
		CacheConstant.logger.info("init hospital's payment rule is start.................");

		if (!org.springframework.util.CollectionUtils.isEmpty(paymentMap)) {
			String fieldName = null;
			Map<String, String> dataMap = new HashMap<String, String>();
			for (Entry<String, RulePayment> paymentEntry : paymentMap.entrySet()) {
				String hospitalCode = paymentEntry.getKey();
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_PAYMENT);
					dataMap.put(fieldName, JSON.toJSONString(paymentEntry.getValue()));
				}
			}
			redisSvc.hmset(getRuleCacheKey(), dataMap);

			return dataMap.size();
		}

		CacheConstant.logger.info("init hospital's payment rule is end.................");

		return 0;
	}

	/**
	 * 批量保存推送规则到缓存
	 * @param pushMap
	 */
	public int setRulePushsToCache(Map<String, RulePush> pushMap) {
		CacheConstant.logger.info("init hospital's push rule is start.................");

		if (!org.springframework.util.CollectionUtils.isEmpty(pushMap)) {
			String fieldName = null;
			Map<String, String> dataMap = new HashMap<String, String>();
			for (Entry<String, RulePush> pushEntry : pushMap.entrySet()) {
				String hospitalCode = pushEntry.getKey();
				if (StringUtils.isNotBlank(hospitalCode)) {
					RulePush push = pushEntry.getValue();
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_PUSH.concat(".").concat(push.getPlatformType()));
					dataMap.put(fieldName, JSON.toJSONString(push));
				}
			}
			redisSvc.hmset(getRuleCacheKey(), dataMap);

			return dataMap.size();
		}

		CacheConstant.logger.info("init hospital's push rule is end.................");

		return 0;
	}

	/**
	 * 批量保存个人中心规则到缓存
	 * @param userCenterMap
	 */
	public int setRuleUserCentersToCache(Map<String, RuleUserCenter> userCenterMap) {
		CacheConstant.logger.info("init hospital's user center rule is start.................");

		if (!org.springframework.util.CollectionUtils.isEmpty(userCenterMap)) {
			String fieldName = null;
			Map<String, String> dataMap = new HashMap<String, String>();
			for (Entry<String, RuleUserCenter> userCenterEntry : userCenterMap.entrySet()) {
				String hospitalCode = userCenterEntry.getKey();
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_USERCENTER);
					dataMap.put(fieldName, JSON.toJSONString(userCenterEntry.getValue()));
				}
			}
			redisSvc.hmset(getRuleCacheKey(), dataMap);

			return dataMap.size();
		}

		CacheConstant.logger.info("init hospital's user center rule is end.................");

		return 0;
	}

	/**
	 * 根据医院code获取底部Logo信息
	 * @param hospitalCode
	 * @return
	 */
	public List<String> getHospitalFootLogoByHospitalCode(String hospitalCode) {
		List<String> result = new ArrayList<String>(1);
		String logo = hospitalFootLogoCache.get(hospitalCode);
		if (logo == null) {
			logo = "";
		}
		result.add(logo);

		return result;
	}

	/**
	 * 根据医院code获取 编辑规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleEdit> queryRuleEditByHospitalCode(String hospitalCode) {
		List<RuleEdit> result = null;
		RuleEdit ruleEdit = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_EDIT);
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				ruleEdit = JSON.parseObject(val, RuleEdit.class);
				result = new ArrayList<RuleEdit>(1);
				result.add(ruleEdit);
			}
		}

		return result == null ? new ArrayList<RuleEdit>(0) : result;
	}

	/**
	 * 根据医院id获取 编辑规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleEdit> queryRuleEditByHospitalId(String hospitalId) {
		List<RuleEdit> result = null;
		String hospitalCode = null;
		if (StringUtils.isNotBlank(hospitalId)) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(hospitalId);
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
				result = queryRuleEditByHospitalCode(hospitalCode);
			}
		}
		return result == null ? new ArrayList<RuleEdit>(0) : result;
	}

	/**
	 * 根据医院Code获取个人中心规则
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleUserCenter> queryRuleUserCenterByHospitalCode(String hospitalCode) {
		List<RuleUserCenter> result = null;
		RuleUserCenter ruleUserCenter = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_USERCENTER);
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				ruleUserCenter = JSON.parseObject(val, RuleUserCenter.class);
				result = new ArrayList<RuleUserCenter>(1);
				result.add(ruleUserCenter);
			}
		}

		return result == null ? new ArrayList<RuleUserCenter>(0) : result;
	}

	/**
	 * 根据医院Id获取个人中心规则
	 * @param hospitalId
	 * @return
	 */
	public List<RuleUserCenter> queryRuleUserCenterByHospitalId(String hospitalId) {
		List<RuleUserCenter> result = null;
		RuleUserCenter ruleUserCenter = null;
		if (StringUtils.isNotBlank(hospitalId)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalId, RuleConstant.RULE_TYPE_USERCENTER);
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				ruleUserCenter = JSON.parseObject(val, RuleUserCenter.class);
				result = new ArrayList<RuleUserCenter>(1);
				result.add(ruleUserCenter);
			}
		}

		return result == null ? new ArrayList<RuleUserCenter>(0) : result;
	}

	/**
	 * 根据医院code获取 在线建档规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleOnlineFiling> queryRuleOFByHospitalCode(String hospitalCode) {
		List<RuleOnlineFiling> result = null;
		RuleOnlineFiling onlineFiling = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_ONLINEFILING);
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				onlineFiling = JSON.parseObject(val, RuleOnlineFiling.class);
				result = new ArrayList<RuleOnlineFiling>(1);
				result.add(onlineFiling);
			}
		}

		return result == null ? new ArrayList<RuleOnlineFiling>(0) : result;
	}

	/**
	 * 根据医院id获取 在线建档规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleOnlineFiling> queryRuleOFByHospitalId(String hospitalId) {
		List<RuleOnlineFiling> result = null;
		String hospitalCode = null;
		if (StringUtils.isNotBlank(hospitalId)) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(hospitalId);
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
				result = queryRuleOFByHospitalCode(hospitalCode);
			}
		}
		return result;
	}

	/**
	 * 根据医院code获取 绑卡规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleTiedCard> queryRuleTiedCardByHospitalCode(String hospitalCode) {
		List<RuleTiedCard> result = null;
		RuleTiedCard ruleTiedCard = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_TIEDCARD);
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				ruleTiedCard = JSON.parseObject(val, RuleTiedCard.class);
				result = new ArrayList<RuleTiedCard>(1);
				result.add(ruleTiedCard);
			}
		}

		return result == null ? new ArrayList<RuleTiedCard>(0) : result;
	}

	/**
	 * 根据医院id获取 绑卡规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleTiedCard> queryRuleTiedCardByHospitalId(String hospitalId) {
		List<RuleTiedCard> result = null;
		String hospitalCode = null;
		if (StringUtils.isNotBlank(hospitalId)) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(hospitalId);
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
				result = queryRuleTiedCardByHospitalCode(hospitalCode);
			}
		}
		return result;
	}

	/**
	 * 根据医院code获取 挂号规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleRegister> queryRuleRegisterByHospitalCode(String hospitalCode) {
		List<RuleRegister> result = null;
		RuleRegister ruleRegister = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_REGISTER);
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				ruleRegister = JSON.parseObject(val, RuleRegister.class);
				result = new ArrayList<RuleRegister>(1);
				result.add(ruleRegister);
			}
		}

		return result == null ? new ArrayList<RuleRegister>(0) : result;
	}

	/**
	 * 根据医院id获取 挂号规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleRegister> queryRuleRegisterByHospitalId(String hospitalId) {
		List<RuleRegister> result = null;
		String hospitalCode = null;
		if (StringUtils.isNotBlank(hospitalId)) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(hospitalId);
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
				result = queryRuleRegisterByHospitalCode(hospitalCode);
			}
		}
		return result;
	}

	/**
	 * 根据医院code获取 支付方式规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RulePayment> queryRulePaymentByHospitalCode(String hospitalCode) {
		List<RulePayment> result = null;
		RulePayment rulePayment = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_PAYMENT);
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				rulePayment = JSON.parseObject(val, RulePayment.class);
				result = new ArrayList<RulePayment>(1);
				result.add(rulePayment);
			}
		}

		return result == null ? new ArrayList<RulePayment>(0) : result;
	}

	/**
	 * 根据医院id获取 缴费规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RulePayment> queryRulePaymentByHospitalId(String hospitalId) {
		List<RulePayment> result = null;
		String hospitalCode = null;
		if (StringUtils.isNotBlank(hospitalId)) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(hospitalId);
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
				result = queryRulePaymentByHospitalCode(hospitalCode);
			}
		}
		return result;
	}

	/**
	 * 根据医院code获取 查询规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleQuery> queryRuleQueryByHospitalCode(String hospitalCode) {
		List<RuleQuery> result = null;
		RuleQuery ruleQuery = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_QUERY);
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				ruleQuery = JSON.parseObject(val, RuleQuery.class);
				result = new ArrayList<RuleQuery>(1);
				result.add(ruleQuery);
			}
		}

		return result == null ? new ArrayList<RuleQuery>(0) : result;
	}

	/**
	 * 根据医院id获取 查询规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleQuery> queryRuleQueryByHospitalId(String hospitalId) {
		List<RuleQuery> result = null;
		String hospitalCode = null;
		if (StringUtils.isNotBlank(hospitalId)) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(hospitalId);
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
				result = queryRuleQueryByHospitalCode(hospitalCode);
			}
		}
		return result;
	}

	/**
	 * 根据医院code获取 推送规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RulePush> queryRulePushByHospitalCode(String hospitalCode, String platformType) {
		List<RulePush> result = null;
		RulePush rulePush = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_PUSH.concat(".").concat(platformType));
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				rulePush = JSON.parseObject(val, RulePush.class);
				result = new ArrayList<RulePush>(1);
				result.add(rulePush);
			}
		}
		return result == null ? new ArrayList<RulePush>(0) : result;
	}

	/**
	 * 根据医院id获取 推送规则
	 * 
	 * @param hospitalId
	 * @return
	 */
	public List<RulePush> queryRulePushByHospitalId(String hospitalId, String platformType) {
		List<RulePush> result = null;
		String hospitalCode = null;
		if (StringUtils.isNotBlank(hospitalId)) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(hospitalId);
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
				result = queryRulePushByHospitalCode(hospitalCode, platformType);
			}
		}
		return result;
	}

	/**
	 * 更新编辑规则
	 * 
	 * @param edit
	 */
	public int updateRuleEdit(RuleEdit edit) {
		String cacheKey = getRuleCacheKey();
		String hospitalCode = null;
		String fieldName = null;
		String hospitalFootLogo = null;
		if (StringUtils.isNotBlank(edit.getHospitalId())) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(edit.getHospitalId());
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
				hospitalFootLogo = edit.getFootLogoInfo();
				if (StringUtils.isBlank(hospitalFootLogo)) {
					hospitalFootLogo = CacheConstant.DEF_FOOT_LOGO_INFO;
				}
				hospitalFootLogoCache.put(hospitalCode, hospitalFootLogo);
			}
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_EDIT);
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(edit));

			return 1;
		}

		return 0;
	}

	/**
	 * 更新个人中心规则
	 * 
	 * @param onlineFiling
	 */
	public int updateRuleUserCenter(RuleUserCenter ruleUserCenter) {
		String cacheKey = getRuleCacheKey();
		String hospitalCode = null;
		String fieldName = null;
		if (StringUtils.isNotBlank(ruleUserCenter.getHospitalId())) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(ruleUserCenter.getHospitalId());
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_USERCENTER);
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(ruleUserCenter));

			return 1;
		}

		return 0;
	}

	/**
	 * 更新在线建档规则
	 * 
	 * @param onlineFiling
	 */
	public int updateRuleOnlineFiling(RuleOnlineFiling onlineFiling) {
		String cacheKey = getRuleCacheKey();
		String hospitalCode = null;
		String fieldName = null;
		if (StringUtils.isNotBlank(onlineFiling.getHospitalId())) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(onlineFiling.getHospitalId());
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_ONLINEFILING);
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(onlineFiling));

			return 1;
		}

		return 0;
	}

	/**
	 * 更新绑卡规则
	 * 
	 * @param tiedCard
	 */
	public int updateRuleTiedCard(RuleTiedCard tiedCard) {
		String cacheKey = getRuleCacheKey();
		String hospitalCode = null;
		String fieldName = null;
		if (StringUtils.isNotBlank(tiedCard.getHospitalId())) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(tiedCard.getHospitalId());
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_TIEDCARD);
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(tiedCard));

			return 1;
		}

		return 0;
	}

	/**
	 * 更新挂号规则
	 * 
	 * @param register
	 */
	public int updateRuleRegister(RuleRegister register) {
		String cacheKey = getRuleCacheKey();
		String hospitalCode = null;
		String fieldName = null;
		if (StringUtils.isNotBlank(register.getHospitalId())) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(register.getHospitalId());
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_REGISTER);
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(register));

			return 1;
		}

		return 0;
	}

	/**
	 * 更新支付方式配置规则
	 * 
	 * @param payment
	 */
	public int updateRulePayment(RulePayment payment) {
		String cacheKey = getRuleCacheKey();
		String hospitalCode = null;
		String fieldName = null;
		if (StringUtils.isNotBlank(payment.getHospitalId())) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(payment.getHospitalId());
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_PAYMENT);
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(payment));

			return 1;
		}

		return 0;
	}

	/**
	 * 更新门诊缴费规则
	 * 
	 * @param payment
	 */
	public int updateRuleClinic(RuleClinic ruleClinic) {
		String cacheKey = getRuleCacheKey();
		String hospitalCode = null;
		String fieldName = null;
		if (StringUtils.isNotBlank(ruleClinic.getHospitalId())) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(ruleClinic.getHospitalId());
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_CLINIC);
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(ruleClinic));

			return 1;
		}

		return 0;
	}

	/**
	 * 更新查询规则
	 * 
	 * @param query
	 */
	public int updateRuleQuery(RuleQuery query) {
		String cacheKey = getRuleCacheKey();
		String hospitalCode = null;
		String fieldName = null;
		if (StringUtils.isNotBlank(query.getHospitalId())) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(query.getHospitalId());
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_QUERY);
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(query));

			return 1;
		}

		return 0;
	}

	/**
	 * 更新推送规则
	 * 
	 * @param query
	 */
	public int updateRulePush(RulePush query) {
		String cacheKey = getRuleCacheKey();
		String hospitalCode = null;
		String fieldName = null;
		if (StringUtils.isNotBlank(query.getHospitalId())) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(query.getHospitalId());
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_PUSH.concat(".").concat(query.getPlatformType()));
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(query));

			return 1;
		}

		return 0;
	}

	/**
	 * 更新His基础数据规则
	 * @param ruleHisData
	 */
	public int updateRuleHisData(RuleHisData ruleHisData) {
		String cacheKey = getRuleCacheKey();
		String hospitalCode = null;
		String fieldName = null;
		if (StringUtils.isNotBlank(ruleHisData.getHospitalId())) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(ruleHisData.getHospitalId());
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_HIS_DATA);
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(ruleHisData));
			return 1;
		}

		return 0;
	}

	/**
	 * 根据医院code获取 His数据规则
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleHisData> queryRuleHisDataByHospitalId(String hospitalId) {
		String hospitalCode = null;
		List<RuleHisData> result = null;
		if (StringUtils.isNotBlank(hospitalId)) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(hospitalId);
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
				result = queryRuleHisDataByHospitalCode(hospitalCode);
			}
		}
		return result;
	}

	/**
	 * 根据医院code获取 His数据规则
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleHisData> queryRuleHisDataByHospitalCode(String hospitalCode) {
		List<RuleHisData> result = null;
		RuleHisData ruleHisData = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_HIS_DATA);
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				ruleHisData = JSON.parseObject(val, RuleHisData.class);
				result = new ArrayList<RuleHisData>(1);
				result.add(ruleHisData);
			}
		}
		return result == null ? new ArrayList<RuleHisData>(0) : result;
	}

	/**
	 * 更新代煎配送
	 * @param friedExpress
	 */
	public int updateRuleFriedExpress(RuleFriedExpress friedExpress) {
		String cacheKey = getRuleCacheKey();
		String hospitalCode = null;
		String fieldName = null;
		if (StringUtils.isNotBlank(friedExpress.getHospitalId())) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(friedExpress.getHospitalId());
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_FRIEDEXPRESS);
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(friedExpress));
			return 1;
		}
		return 0;
	}

	/**
	 * 更新住院规则
	 * @param RuleInHospital
	 */
	public int updateRuleInHospital(RuleInHospital ruleInHospital) {
		String cacheKey = getRuleCacheKey();
		String hospitalCode = null;
		String fieldName = null;
		if (StringUtils.isNotBlank(ruleInHospital.getHospitalId())) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(ruleInHospital.getHospitalId());
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_IN_HOSPITAL);
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(ruleInHospital));
			return 1;
		}
		return 0;
	}

	/**
	 * 根据医院id获取 代煎配送规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleFriedExpress> queryRuleFriedExpressByHospitalId(String hospitalId) {
		List<RuleFriedExpress> result = null;
		String hospitalCode = null;
		if (StringUtils.isNotBlank(hospitalId)) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(hospitalId);
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
				result = queryRuleFriedExpressByHospitalCode(hospitalCode);
			}
		}
		return result;
	}

	/**
	 * 根据医院code获取 代煎配送规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleFriedExpress> queryRuleFriedExpressByHospitalCode(String hospitalCode) {
		List<RuleFriedExpress> result = null;
		RuleFriedExpress ruleFriedExpress = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_FRIEDEXPRESS);
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				ruleFriedExpress = JSON.parseObject(val, RuleFriedExpress.class);
				result = new ArrayList<RuleFriedExpress>(1);
				result.add(ruleFriedExpress);
			}
		}

		return result == null ? new ArrayList<RuleFriedExpress>(0) : result;
	}

	/**
	 * 根据医院id获取 缴费规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleClinic> queryRuleClinicByHospitalId(String hospitalId) {
		List<RuleClinic> result = null;
		String hospitalCode = null;
		if (StringUtils.isNotBlank(hospitalId)) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(hospitalId);
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
				result = queryRuleClinicByHospitalCode(hospitalCode);
			}
		}
		return result;
	}

	/**
	 * 根据医院code获取 门诊缴费规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleClinic> queryRuleClinicByHospitalCode(String hospitalCode) {
		List<RuleClinic> result = null;
		RuleClinic ruleClinic = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_CLINIC);
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				ruleClinic = JSON.parseObject(val, RuleClinic.class);
				result = new ArrayList<RuleClinic>(1);
				result.add(ruleClinic);
			}
		}

		return result == null ? new ArrayList<RuleClinic>(0) : result;
	}

	/**
	 * 根据医院id获取 住院规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleInHospital> queryRuleInHospitalByHospitalId(String hospitalId) {
		List<RuleInHospital> result = null;
		String hospitalCode = null;
		if (StringUtils.isNotBlank(hospitalId)) {
			List<String> hospitalCodes = hospitalCache.getHospitalCodeById(hospitalId);
			if (CollectionUtils.isNotEmpty(hospitalCodes)) {
				hospitalCode = hospitalCodes.get(0);
				result = queryRuleInHospitalByHospitalCode(hospitalCode);
			}
		}
		return result;
	}

	/**
	 * 根据医院code获取 住院规则
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<RuleInHospital> queryRuleInHospitalByHospitalCode(String hospitalCode) {
		List<RuleInHospital> result = null;
		RuleInHospital ruleInHospital = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getRuleCacheKey();
			String fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_IN_HOSPITAL);
			String val = redisSvc.hget(cacheKey, fieldName);
			if (StringUtils.isNotBlank(val) && !val.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				ruleInHospital = JSON.parseObject(val, RuleInHospital.class);
				result = new ArrayList<RuleInHospital>(1);
				result.add(ruleInHospital);
			}
		}

		return result == null ? new ArrayList<RuleInHospital>(0) : result;
	}

	/**
	 * 得到规则缓存的key
	 * 
	 * @return
	 */
	public String getRuleCacheKey() {
		return CacheConstant.CACHE_HOSPITAL_RULES_KEY_PREFIX;
	}

	/**
	 * 
	 * @param ruleType
	 *            参见RuleConstant
	 * @return
	 */
	public String getRuleFieldName(String hospitalCode, String ruleType) {
		return hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(ruleType);
	}
}
