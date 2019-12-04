/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.msgpush.MsgCustomer;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateLibrary;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * 消息模板缓存管理
 * @Package: com.yxw.cache.component
 * @ClassName: MsgCache
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月15日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MsgTempCache {
	private static Logger logger = LoggerFactory.getLogger(MsgTempCache.class);

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);
	// private MsgTemplateLibraryDao msgTemplateLibraryDao = SpringContextHolder.getBean(MsgTemplateLibraryDao.class);
	// private MsgTemplateDao msgTemplateDao = SpringContextHolder.getBean(MsgTemplateDao.class);
	// private MsgCustomerDao msgCustomerDao = SpringContextHolder.getBean(MsgCustomerDao.class);
	// private static boolean isHasInit = false;

	public MsgTempCache() {
		super();
	}

	/**
	 * 初始化模板消息和客服消息
	 * @throws SystemException
	 */
	// public void initCache() throws SystemException {
	// if (!isHasInit) {
	// logger.info("init msgTemplateLibrary, msgTemplate and msgCustomer config infos start.................");
	// //msgTemplateLibrary
	// List<MsgTemplateLibrary> msgTemplateLibraries = msgTemplateLibraryDao.findAll();
	// if (!CollectionUtils.isEmpty(msgTemplateLibraries)) {
	// Map<String, String> map = new HashMap<String, String>();
	// String cacheKey = getMsgTemplateLibraryCacheKey();
	// for (MsgTemplateLibrary msgTemplateLibrary : msgTemplateLibraries) {
	// map.put(getMsgTemplateLibraryFieldName(msgTemplateLibrary.getTemplateCode(), String.valueOf(msgTemplateLibrary.getSource())),
	// JSON.toJSONString(msgTemplateLibrary));
	// }
	// redisSvc.del(cacheKey);
	// redisSvc.hmset(cacheKey, map);
	// }
	// logger.info("目前模板库共有{}个模板", msgTemplateLibraries.size());
	// //msgTemplate
	// List<MsgTemplate> msgTemplates = msgTemplateDao.findAll();
	// if (!CollectionUtils.isEmpty(msgTemplates)) {
	// Map<String, String> map = new HashMap<String, String>();
	// String cacheKey = getMsgTemplateCacheKey();
	// for (MsgTemplate msgTemplate : msgTemplates) {
	// map.put(getMsgTemplateFieldName(msgTemplate.getAppId(), msgTemplate.getCode(), String.valueOf(msgTemplate.getSource())),
	// JSON.toJSONString(msgTemplate));
	// }
	// redisSvc.del(cacheKey);
	// redisSvc.hmset(cacheKey, map);
	// }
	// logger.info("目前已定义{}个模板消息", msgTemplates.size());
	//
	// //msgCustomer
	// List<MsgCustomer> msgCustomers = msgCustomerDao.findAll();
	// if (!CollectionUtils.isEmpty(msgCustomers)) {
	// Map<String, String> map = new HashMap<String, String>();
	// String cacheKey = getMsgCustomerCacheKey();
	// for (MsgCustomer msgCustomer : msgCustomers) {
	// map.put(getMsgCustomerFieldName(msgCustomer.getAppId(), msgCustomer.getCode(), String.valueOf(msgCustomer.getSource())),
	// JSON.toJSONString(msgCustomer));
	// }
	// redisSvc.del(cacheKey);
	// redisSvc.hmset(cacheKey, map);
	// }
	// logger.info("目前已定义{}个客服消息", msgCustomers.size());
	//
	// isHasInit = true;
	// logger.info("init msgTemplateLibrary, msgTemplate and msgCustomer config infos end.................");
	// }
	// }

	/**
	 * 批量保存消息模板库到缓存
	 * @param msgTemplateLibraries
	 */
	public int setMsgTemplateLibraries(List<MsgTemplateLibrary> msgTemplateLibraries) {
		logger.info("init msgTemplateLibrary config infos  start.................");
		// msgTemplateLibrary
		if (!CollectionUtils.isEmpty(msgTemplateLibraries)) {
			Map<String, String> map = new HashMap<String, String>();
			String cacheKey = getMsgTemplateLibraryCacheKey();
			for (MsgTemplateLibrary msgTemplateLibrary : msgTemplateLibraries) {
				map.put(getMsgTemplateLibraryFieldName(msgTemplateLibrary.getTemplateCode(), String.valueOf(msgTemplateLibrary.getSource())),
						JSON.toJSONString(msgTemplateLibrary));
			}
			redisSvc.del(cacheKey);
			redisSvc.hmset(cacheKey, map);

			return map.size();
		}
		logger.info("目前模板库共有{}个模板", msgTemplateLibraries.size());
		logger.info("init msgTemplateLibrary config infos  end.................");

		return 0;
	}

	/**
	 * 批量保存模板消息到缓存
	 * @param msgTemplates
	 */
	public int setMsgTemplates(List<MsgTemplate> msgTemplates) {
		logger.info("init msgTemplate config infos  start.................");
		if (!CollectionUtils.isEmpty(msgTemplates)) {
			Map<String, String> map = new HashMap<String, String>();
			String cacheKey = getMsgTemplateCacheKey();
			for (MsgTemplate msgTemplate : msgTemplates) {
				map.put(getMsgTemplateFieldName(msgTemplate.getAppId(), msgTemplate.getCode(), String.valueOf(msgTemplate.getSource()),
						String.valueOf(msgTemplate.getMsgTarget())), JSON.toJSONString(msgTemplate));
			}
			redisSvc.del(cacheKey);
			redisSvc.hmset(cacheKey, map);

			return map.size();
		}
		logger.info("目前已定义{}个模板消息", msgTemplates.size());
		logger.info("init msgTemplate config infos  end.................");

		return 0;
	}

	/**
	 * 批量保存客服消息到缓存
	 * @param msgCustomers
	 */
	public int setMsgCustomers(List<MsgCustomer> msgCustomers) {
		logger.info("init msgCustomer config infos  start.................");
		if (!CollectionUtils.isEmpty(msgCustomers)) {
			Map<String, String> map = new HashMap<String, String>();
			String cacheKey = getMsgCustomerCacheKey();
			for (MsgCustomer msgCustomer : msgCustomers) {
				map.put(getMsgCustomerFieldName(msgCustomer.getAppId(), msgCustomer.getCode(), String.valueOf(msgCustomer.getSource())),
						JSON.toJSONString(msgCustomer));
			}
			redisSvc.del(cacheKey);
			redisSvc.hmset(cacheKey, map);

			return map.size();
		}
		logger.info("目前已定义{}个客服消息", msgCustomers.size());
		logger.info("init msgCustomer config infos  end.................");

		return 0;
	}

	/**
	 * 根据模板code和模板来源获取模板库模板
	 * @param code
	 * @param source
	 * @return
	 */
	public List<MsgTemplateLibrary> getMsgTemplateLibrary(String code, String source) {
		List<MsgTemplateLibrary> result = null;
		MsgTemplateLibrary msgTemplateLibrary = null;
		String cacheKey = getMsgTemplateLibraryCacheKey();
		String jsonStr = redisSvc.hget(cacheKey, getMsgTemplateLibraryFieldName(code, source));
		if (!CacheConstant.CACHE_KEY_NOT_EXIST.equals(jsonStr)) {
			msgTemplateLibrary = JSON.parseObject(jsonStr, MsgTemplateLibrary.class);
			result = new ArrayList<MsgTemplateLibrary>(1);
			result.add(msgTemplateLibrary);
		}
		return result == null ? new ArrayList<MsgTemplateLibrary>(0) : result;
	}

	/**
	 * 获取所有的模板库模板
	 * @return
	 */
	public List<MsgTemplateLibrary> getMsgTemplateLibraryAll() {
		List<MsgTemplateLibrary> msgTemplateLibraries = new ArrayList<MsgTemplateLibrary>();
		String cacheKey = getMsgTemplateLibraryCacheKey();
		List<String> jsonStrings = redisSvc.hvals(cacheKey);
		for (String str : jsonStrings) {
			MsgTemplateLibrary msgTemplateLibrary = JSON.parseObject(str, MsgTemplateLibrary.class);
			msgTemplateLibraries.add(msgTemplateLibrary);
		}
		return msgTemplateLibraries;
	}

	/**
	 * 根据source获取模板库模板
	 * @param source
	 * @return
	 */
	public List<MsgTemplateLibrary> getMsgTemplateLibraryBySource(String source) {
		List<MsgTemplateLibrary> msgTemplateLibraries = new ArrayList<MsgTemplateLibrary>();
		String cacheKey = getMsgTemplateLibraryCacheKey();
		List<String> jsonStrings = redisSvc.hvals(cacheKey);
		for (String str : jsonStrings) {
			MsgTemplateLibrary msgTemplateLibrary = JSON.parseObject(str, MsgTemplateLibrary.class);
			if (source.equals(msgTemplateLibrary.getSource())) {
				msgTemplateLibraries.add(msgTemplateLibrary);
			}
		}
		return msgTemplateLibraries;
	}

	/**
	 * 判断缓存对象是否存在
	 * 
	 * @category 已调整，把返回值类型由布尔类型改为int类型
	 * @param code
	 * @param source
	 * @return
	 */
	public int existsMsgTemplateLibrary(String code, String source) {
		String cacheKey = getMsgTemplateLibraryCacheKey();
		return redisSvc.hexists(cacheKey, getMsgTemplateLibraryFieldName(code, source)) ? 1 : 0;
	}

	/**
	 * 添加/更新模板库模板
	 * @param msgTemplateLibrary
	 */
	public int updateMsgTemplateLibrary(MsgTemplateLibrary msgTemplateLibrary) {
		if (msgTemplateLibrary != null) {
			String cacheKey = getMsgTemplateLibraryCacheKey();
			redisSvc.hset(cacheKey, getMsgTemplateLibraryFieldName(msgTemplateLibrary.getTemplateCode(), String.valueOf(msgTemplateLibrary.getSource())),
					JSON.toJSONString(msgTemplateLibrary));

			return 1;
		}

		return 0;
	}

	/**
	 * 删除模板库模板
	 * @param msgTemplateLibrary
	 */
	public int deleteMsgTemplateLibrary(MsgTemplateLibrary msgTemplateLibrary) {
		if (msgTemplateLibrary != null) {
			String cacheKey = getMsgTemplateLibraryCacheKey();
			redisSvc.hdel(cacheKey, getMsgTemplateLibraryFieldName(msgTemplateLibrary.getTemplateCode(), String.valueOf(msgTemplateLibrary.getSource())));

			return 1;
		}

		return 0;
	}

	/**
	 * 根据appId,模板code和模板来源获取模板消息
	 * @param appId
	 * @param code
	 * @param source
	 * @return
	 */
	public List<MsgTemplate> getMsgTemplate(String appId, String code, String source, String msgTarget) {
		List<MsgTemplate> result = null;
		MsgTemplate msgTemplate = null;
		String cacheKey = getMsgTemplateCacheKey();
		String jsonStr = redisSvc.hget(cacheKey, getMsgTemplateFieldName(appId, code, source, msgTarget));
		if (!CacheConstant.CACHE_KEY_NOT_EXIST.equals(jsonStr)) {
			msgTemplate = JSON.parseObject(jsonStr, MsgTemplate.class);
			result = new ArrayList<MsgTemplate>(1);
			result.add(msgTemplate);
		}
		return result == null ? new ArrayList<MsgTemplate>(0) : result;
	}

	/**
	 * 判断缓存对象是否存在
	 * 
	 * @category 已调整，把返回值类型由布尔类型改为int类型
	 * @param code
	 * @param source
	 * @return
	 */
	public int existsMsgTemplate(String appId, String code, String source, String msgTarget) {
		String cacheKey = getMsgTemplateCacheKey();
		return redisSvc.hexists(cacheKey, getMsgTemplateFieldName(appId, code, source, msgTarget)) ? 1 : 0;
	}

	/**
	 * 获取所有的模板消息
	 * @return
	 */
	public List<MsgTemplate> getMsgTemplateAll() {
		List<MsgTemplate> msgTemplates = new ArrayList<MsgTemplate>();
		String cacheKey = getMsgTemplateCacheKey();
		List<String> jsonStrings = redisSvc.hvals(cacheKey);
		for (String str : jsonStrings) {
			MsgTemplate msgTemplate = JSON.parseObject(str, MsgTemplate.class);
			msgTemplates.add(msgTemplate);
		}
		return msgTemplates;
	}

	/**
	 * 根据source获取模板消息
	 * @param source
	 * @return
	 */
	public List<MsgTemplate> getMsgTemplateBySource(String source) {
		List<MsgTemplate> msgTemplates = new ArrayList<MsgTemplate>();
		String cacheKey = getMsgTemplateCacheKey();
		List<String> jsonStrings = redisSvc.hvals(cacheKey);
		for (String str : jsonStrings) {
			MsgTemplate msgTemplate = JSON.parseObject(str, MsgTemplate.class);
			if (source.equals(msgTemplate.getSource())) {
				msgTemplates.add(msgTemplate);
			}
		}
		return msgTemplates;
	}

	/**
	 * 添加/更新模板库模板
	 * @param msgTemplate
	 */
	public int updateMsgTemplate(MsgTemplate msgTemplate) {
		if (msgTemplate != null) {
			String cacheKey = getMsgTemplateCacheKey();
			redisSvc.hset(cacheKey, getMsgTemplateFieldName(msgTemplate.getAppId(), msgTemplate.getCode(), String.valueOf(msgTemplate.getSource()),
					String.valueOf(msgTemplate.getMsgTarget())), JSON.toJSONString(msgTemplate));

			return 1;
		}

		return 0;
	}

	/**
	 * 删除模板库模板
	 * @param msgTemplate
	 */
	public int deleteMsgTemplate(MsgTemplate msgTemplate) {
		if (msgTemplate != null) {
			String cacheKey = getMsgTemplateCacheKey();
			redisSvc.hdel(cacheKey, getMsgTemplateFieldName(msgTemplate.getAppId(), msgTemplate.getCode(), String.valueOf(msgTemplate.getSource()),
					String.valueOf(msgTemplate.getMsgTarget())));
			return 1;
		}

		return 0;
	}

	/**
	 * 根据appId,模板code和模板来源获取客服消息
	 * @param appId
	 * @param code
	 * @param source
	 * @return
	 */
	public List<MsgCustomer> getMsgCustomer(String appId, String code, String source) {
		List<MsgCustomer> result = null;
		MsgCustomer msgCustomer = null;
		String cacheKey = getMsgCustomerCacheKey();
		String jsonStr = redisSvc.hget(cacheKey, getMsgCustomerFieldName(appId, code, source));
		if (!CacheConstant.CACHE_KEY_NOT_EXIST.equals(jsonStr)) {
			msgCustomer = JSON.parseObject(jsonStr, MsgCustomer.class);
			result = new ArrayList<MsgCustomer>(1);
			result.add(msgCustomer);
		}
		return result == null ? new ArrayList<MsgCustomer>(0) : result;
	}

	/**
	 * 判断缓存对象是否存在
	 * 
	 * @category 已调整，把返回值类型由布尔类型改为int类型
	 * @param code
	 * @param source
	 * @return
	 */
	public int existsMsgCustomer(String appId, String code, String source) {
		String cacheKey = getMsgCustomerCacheKey();
		return redisSvc.hexists(cacheKey, getMsgCustomerFieldName(appId, code, source)) ? 1 : 1;
	}

	/**
	 * 获取所有的客服消息
	 * @return
	 */
	public List<MsgCustomer> getMsgCustomerAll() {
		List<MsgCustomer> msgCustomers = new ArrayList<MsgCustomer>();
		String cacheKey = getMsgCustomerCacheKey();
		List<String> jsonStrings = redisSvc.hvals(cacheKey);
		for (String str : jsonStrings) {
			MsgCustomer msgCustomer = JSON.parseObject(str, MsgCustomer.class);
			msgCustomers.add(msgCustomer);
		}
		return msgCustomers;
	}

	public List<MsgCustomer> getMsgCustomerBySource(String source) {
		List<MsgCustomer> msgCustomers = new ArrayList<MsgCustomer>();
		String cacheKey = getMsgCustomerCacheKey();
		List<String> jsonStrings = redisSvc.hvals(cacheKey);
		for (String str : jsonStrings) {
			MsgCustomer msgCustomer = JSON.parseObject(str, MsgCustomer.class);
			if (source.equals(msgCustomer.getSource())) {
				msgCustomers.add(msgCustomer);
			}
		}
		return msgCustomers;
	}

	/**
	 * 添加/更新模板库模板
	 * @param msgCustomer
	 */
	public int updateMsgCustomer(MsgCustomer msgCustomer) {
		if (msgCustomer != null) {
			String cacheKey = getMsgCustomerCacheKey();
			redisSvc.hset(cacheKey, getMsgCustomerFieldName(msgCustomer.getAppId(), msgCustomer.getCode(), String.valueOf(msgCustomer.getSource())),
					JSON.toJSONString(msgCustomer));

			return 1;
		}

		return 0;
	}

	/**
	 * 删除模板库模板
	 * @param msgCustomer
	 */
	public int deleteMsgCustomer(MsgCustomer msgCustomer) {
		if (msgCustomer != null) {
			String cacheKey = getMsgCustomerCacheKey();
			redisSvc.hdel(cacheKey, getMsgCustomerFieldName(msgCustomer.getAppId(), msgCustomer.getCode(), String.valueOf(msgCustomer.getSource())));

			return 1;
		}

		return 0;
	}

	/**
	 * 根据appId,模板code,模板来源生成redis hash存储结构中的field name
	 * @param appId
	 * @param templateCode
	 * @param source
	 * @return
	 */
	private String getMsgTemplateFieldName(String appId, String templateCode, String source, String msgTarget) {
		return appId.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(templateCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(source)
				.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(msgTarget);
	}

	/**
	 * 根据模板code,模板来源生成redis hash存储结构中的field name
	 * @param templateCode
	 * @param source
	 * @return
	 */
	private String getMsgTemplateLibraryFieldName(String templateCode, String source) {
		return templateCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(source);
	}

	/**
	 * 根据appId,模板code,模板来源生成redis hash存储结构中的field name
	 * @param appId
	 * @param templateCode
	 * @param source
	 * @return
	 */
	private String getMsgCustomerFieldName(String appId, String templateCode, String source) {
		return appId.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(templateCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(source);
	}

	/**
	 * redis hash存储结构中的cache key
	 * @return
	 */
	private String getMsgTemplateCacheKey() {
		return CacheConstant.CACHE_MSG_TEMPLATE;
	}

	private String getMsgCustomerCacheKey() {
		return CacheConstant.CACHE_MSG_CUSTOMER;
	}

	private String getMsgTemplateLibraryCacheKey() {
		return CacheConstant.CACHE_MSG_LIBRARY;
	}
}
