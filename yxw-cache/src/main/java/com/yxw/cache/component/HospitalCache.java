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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.hospital.HospitalPlatformSettings;
import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeInterfaceAndAppVo;
import com.yxw.framework.cache.redis.RedisLock;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;

/**
 * @Package: com.yxw.platform.cache
 * @ClassName: HospitalCache
 * @Statement: <p>
 *             医院缓存信息
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-14
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HospitalCache {
	/**
	 * 医院信息相对较少 使用频率很高 故使用ConcurrentHashMap来存储 不使用redis来缓存 private static ConcurrentHashMap<String, Hospital>
	 * hospitalInfoMap = new ConcurrentHashMap<String, Hospital>();
	 */
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);
	private static Logger logger = LoggerFactory.getLogger(HospitalCache.class);

	public HospitalCache() {
		super();

	}

	/**
	 * 批量保存医院与接口id的映射关系到缓存
	 * 
	 * @param vos
	 */
	public int setInterfaceInfosToCache(List<CodeAndInterfaceVo> vos) {
		if (!CollectionUtils.isEmpty(vos)) {
			String cacheKey = getInterfaceCaceKey();
			Map<String, String> hospitalData = new HashMap<String, String>();
			for (CodeAndInterfaceVo vo : vos) {
				String fieldName = genInterfaceFieldNameByCode(vo.getHospitalCode(), vo.getBranchHospitalCode());
				hospitalData.put(fieldName, JSON.toJSONString(vo));
			}

			redisSvc.del(cacheKey);
			redisSvc.hmset(cacheKey, hospitalData);
		}

		return vos.size();
	}

	/**
	 * 批量保存医院与app的映射关系到缓存
	 * 
	 * @param vos
	 */
	public int setAppInfosToCahce(List<HospitalCodeAndAppVo> vos) {
		if (!CollectionUtils.isEmpty(vos)) {
			String cacheKey = getAppCacheKey();
			Map<String, String> appAndcodeVoMap = new HashMap<String, String>();
			for (HospitalCodeAndAppVo appVo : vos) {
				if (StringUtils.isNotEmpty(appVo.getAppCode()) && StringUtils.isNotEmpty(appVo.getAppId())) {
					String fieldName = genAppFieldNameByCode(appVo.getAppCode(), appVo.getAppId());
					appAndcodeVoMap.put(fieldName, JSON.toJSONString(appVo));
				}
			}
			redisSvc.del(cacheKey);
			redisSvc.hmset(cacheKey, appAndcodeVoMap);
		}

		return vos.size();
	}

	/**
	 * 批量保存医院与appSecret的映射关系到缓存
	 * 
	 * @param vos
	 */
	public int setAppSecretsToCache(List<HospIdAndAppSecretVo> vos) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (!CollectionUtils.isEmpty(vos)) {
			for (HospIdAndAppSecretVo vo : vos) {
				if (StringUtils.isNotBlank(vo.getAppId()) && StringUtils.isNotBlank(vo.getAppCode())) {
					jsonMap.put(genAppFieldNameByCode(vo.getAppCode(), vo.getAppId()), JSON.toJSONString(vo));
				}
			}
			if (jsonMap.size() > 0) {
				redisSvc.del(getAppSecretCacheKey());
				redisSvc.hmset(getAppSecretCacheKey(), jsonMap);
			}
		}

		return vos.size();
	}

	/**
	 * 批量保存医院的基本信息到缓存
	 * 
	 * @param vos
	 */
	public int setHospBaseInfosToCache(List<Hospital> vos) {
		if (!CollectionUtils.isEmpty(vos)) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			for (Hospital vo : vos) {
				if (StringUtils.isNotBlank(vo.getId()) && StringUtils.isNotBlank(vo.getCode())) {
					jsonMap.put(genHospBaseInfoName(vo.getId(), vo.getCode()), JSON.toJSONString(vo));
				}
			}
			if (jsonMap.size() > 0) {
				redisSvc.del(getHospBaseInfoKey());
				redisSvc.hmset(getHospBaseInfoKey(), jsonMap);
			}
		}

		return vos.size();
	}

	/**
	 * 根据医院id 查询 医院Code
	 * 
	 * @param hospitalId
	 * @return
	 */
	public List<String> getHospitalCodeById(String hospitalId) {
		List<String> result = null;
		String hospitalCode = null;
		List<CodeAndInterfaceVo> voMap = getCodeAndInterfaceMap();
		for (CodeAndInterfaceVo vo : voMap) {
			if (vo.getHospitalId().equalsIgnoreCase(hospitalId)) {
				hospitalCode = vo.getHospitalCode();
				result = new ArrayList<String>(1);
				result.add(hospitalCode);
				break;
			}
		}
		return result == null ? new ArrayList<String>(0) : result;
	}

	/**
	 * 根据医院id 查询 医院接口信息对象
	 * 
	 * @param hospitalId
	 * @return
	 */
	public List<CodeAndInterfaceVo> getHospitalInfoById(String hospitalId) {
		List<CodeAndInterfaceVo> result = null;
		CodeAndInterfaceVo hospitalVo = null;
		List<CodeAndInterfaceVo> voMap = getCodeAndInterfaceMap();
		for (CodeAndInterfaceVo vo : voMap) {
			if (vo.getHospitalId().equalsIgnoreCase(hospitalId)) {
				hospitalVo = vo;
				result = new ArrayList<CodeAndInterfaceVo>(1);
				result.add(hospitalVo);
				break;
			}
		}
		return result == null ? new ArrayList<CodeAndInterfaceVo>(0) : result;
	}

	/**
	 * 根据医院code 查询 医院id
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<String> getHospitalIdByCode(String hospitalCode) {
		List<String> result = null;
		String hospitalId = null;
		List<CodeAndInterfaceVo> voMap = getCodeAndInterfaceMap();
		for (CodeAndInterfaceVo vo : voMap) {
			if (vo.getHospitalCode().equalsIgnoreCase(hospitalCode)) {
				hospitalId = vo.getHospitalId();
				result = new ArrayList<String>(1);
				result.add(hospitalId);
				break;
			}
		}
		return result == null ? new ArrayList<String>(0) : result;
	}

	public List<CodeAndInterfaceVo> getCodeAndInterfaceVo(String hospitalCode, String branchHospitalCode) {
		List<CodeAndInterfaceVo> result = null;
		CodeAndInterfaceVo vo = null;
		if (StringUtils.isNotBlank(hospitalCode) && StringUtils.isNotBlank(branchHospitalCode)) {
			String cacheKey = getInterfaceCaceKey();
			String filed = genInterfaceFieldNameByCode(hospitalCode, branchHospitalCode);
			String jsonStr = redisSvc.hget(cacheKey, filed);
			if (!CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonStr)) {
				vo = JSON.parseObject(jsonStr, CodeAndInterfaceVo.class);
				result = new ArrayList<CodeAndInterfaceVo>(1);
				result.add(vo);
			}
		}
		return result == null ? new ArrayList<CodeAndInterfaceVo>(0) : result;
	}

	/**
	 * 根据医院Code获取一个分院的CodeAndInterfaceVo对象 适用于对于实际不存在分院 获取后台配置的分院Code
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<CodeAndInterfaceVo> getDefCodeAndInterfaceVo(String hospitalCode) {
		List<CodeAndInterfaceVo> result = null;
		CodeAndInterfaceVo vo = null;
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getInterfaceCaceKey();
			Set<String> cacheFields = redisSvc.hkeys(cacheKey);
			String prefix = hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR);
			String mathchField = null;
			for (String cacheField : cacheFields) {
				if (cacheField.startsWith(prefix)) {
					mathchField = cacheField;
					break;
				}
			}
			if (StringUtils.isNotBlank(mathchField)) {
				String jsonStr = redisSvc.hget(cacheKey, mathchField);
				if (!CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonStr) && StringUtils.isNotBlank(jsonStr)) {
					vo = JSON.parseObject(jsonStr, CodeAndInterfaceVo.class);
					result = new ArrayList<CodeAndInterfaceVo>(1);
					result.add(vo);
				}
			}
		}
		return result == null ? new ArrayList<CodeAndInterfaceVo>(0) : result;
	}

	/**
	 * 查询医院-分院对应的 interfaceId
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @return
	 */
	public List<String> getInterfaceId(String hospitalCode, String branchHospitalCode) throws SystemException {
		List<String> result = null;
		String interfaceId = null;
		if (StringUtils.isNotBlank(hospitalCode) && StringUtils.isNotBlank(branchHospitalCode)) {
			String cacheKey = getInterfaceCaceKey();
			String filed = genInterfaceFieldNameByCode(hospitalCode, branchHospitalCode);
			String jsonStr = redisSvc.hget(cacheKey, filed);
			if (!CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonStr) && StringUtils.isNotBlank(jsonStr)) {
				CodeAndInterfaceVo vo = JSON.parseObject(jsonStr, CodeAndInterfaceVo.class);
				interfaceId = vo.getInterfaceId();
				result = new ArrayList<String>(1);
				result.add(interfaceId);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.info("hospitalCode:{} , branchHospitalCode:{} ,interfaceId:{}", new Object[] { hospitalCode,
					branchHospitalCode,
					interfaceId });
		}
		return result == null ? new ArrayList<String>(0) : result;
	}

	/**
	 * 得到有效的医院Code与医院接口Id对应的Map
	 * 
	 * @category 返回值由Map转为List
	 * @return
	 * @throws SystemException
	 */
	public List<CodeAndInterfaceVo> getValidCodeAndInterfaceMap() throws SystemException {
		String cacheKey = getInterfaceCaceKey();
		Map<String, String> codeAndInterfaceMap = redisSvc.hgetAll(cacheKey);
		List<CodeAndInterfaceVo> result = null;

		if (codeAndInterfaceMap != null && !codeAndInterfaceMap.isEmpty()) {
			result = new ArrayList<CodeAndInterfaceVo>(codeAndInterfaceMap.size());
			for (String fieldName : codeAndInterfaceMap.keySet()) {
				CodeAndInterfaceVo vo = JSON.parseObject(codeAndInterfaceMap.get(fieldName), CodeAndInterfaceVo.class);
				String interfaceId = vo.getInterfaceId();
				if (StringUtils.isNotBlank(interfaceId)) {
					try {
						// YxwService yxwService = SpringContextHolder.getBean(interfaceId);
					} catch (Exception e) {
						if (e instanceof org.springframework.beans.factory.NoSuchBeanDefinitionException) {
							CacheConstant.logger.warn("{} is not defined , do not scan this hospital.hospital name :{}",
									new Object[] { interfaceId, vo.getHospitalName() + "-" + vo.getBranchName() });
							continue;
						}
					}
					result.add(vo);
				}
			}
		}
		return result == null ? new ArrayList<CodeAndInterfaceVo>(0) : result;
	}

	/**
	 * 系统所有医院code与interfaceid的对应关系
	 * 
	 * @category 返回值由Map转为List
	 * @return
	 * @throws SystemException
	 */
	public List<CodeAndInterfaceVo> getCodeAndInterfaceMap() throws SystemException {
		/*** redis **/
		String cacheKey = getInterfaceCaceKey();
		Map<String, String> codeAndInterfaceMap = redisSvc.hgetAll(cacheKey);
		List<CodeAndInterfaceVo> result = null;

		if (codeAndInterfaceMap != null && !codeAndInterfaceMap.isEmpty()) {
			result = new ArrayList<CodeAndInterfaceVo>(codeAndInterfaceMap.size());
			for (String fieldName : codeAndInterfaceMap.keySet()) {
				CodeAndInterfaceVo vo = JSON.parseObject(codeAndInterfaceMap.get(fieldName), CodeAndInterfaceVo.class);
				String interfaceId = vo.getInterfaceId();
				if (StringUtils.isNotBlank(interfaceId)) {
					result.add(vo);
				}
			}
		}
		return result == null ? new ArrayList<CodeAndInterfaceVo>(0) : result;
	}

	/**
	 * 根据app信息获取医院code
	 * 
	 * @param appCode
	 * @param appId
	 *            wechat:wxbaecafd2477f2568
	 * @return HospitalCodeAndAppVo app与医院关系的一个传值对象
	 */
	public List<HospitalCodeAndAppVo> queryHospitalCodeByApp(String appCode, String appId) throws SystemException {
		List<HospitalCodeAndAppVo> result = null;
		String cacheKey = getAppCacheKey();
		String fieldName = genAppFieldNameByCode(appCode, appId);

		String json = redisSvc.hget(cacheKey, fieldName);
		if (StringUtils.isNotBlank(json) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(json)) {
			result = JSON.parseArray(json, HospitalCodeAndAppVo.class);
		}

		return result == null ? new ArrayList<HospitalCodeAndAppVo>(0) : result;
	}

	/**
	 * 
	 * @param appId
	 * @param appCode
	 * @return
	 * modify: 2017-06-27 update payModeCode..
	 * untest
	 */
	public List<HospitalCodeAndAppVo> queryAppInfoByAppIdAndAppCodeForApp(String appId, String appCode, String payModeCode) {
		List<HospitalCodeAndAppVo> vos = new ArrayList<HospitalCodeAndAppVo>(1);

		// 新平台存储方式：固定app:appId
		String cacheKey = getAppCacheKey();
		String fieldName = appCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(appId);
		String values = redisSvc.hget(cacheKey, fieldName);
		if (StringUtils.isNotBlank(values) && !CacheConstant.CACHE_KEY_NOT_EXIST.equals(values)) {
			List<HospitalCodeAndAppVo> hospitalCodeAndAppVos = JSON.parseArray(values, HospitalCodeAndAppVo.class);

			for (HospitalCodeAndAppVo vo : hospitalCodeAndAppVos) {
				if (vo.getPayModeCode().equals(payModeCode)) {
					vos.add(vo);
					break;
				}
			}
		}

		return vos;
	}

	/**
	 * 通过支付宝/微信平台配置的appId,appCode找医院 通过医院找平台的支付信息
	 * 
	 * @param appId
	 * @param appCode
	 * @return
	 */
	/*public List<HospitalCodeAndAppVo> queryAppInfoByAppIdAndAppCodeForAppEx(String payAppId, String payAppCode) {
		List<HospitalCodeAndAppVo> vos = new ArrayList<HospitalCodeAndAppVo>(1);

		String appCode = "";
		if (payAppCode.equals(BizConstant.MODE_TYPE_WEIXIN)) {
			appCode = BizConstant.MODE_TYPE_APP_WECHAT;
		} else if (payAppCode.equals(BizConstant.MODE_TYPE_ALIPAY)) {
			appCode = BizConstant.MODE_TYPE_APP_ALIPAY;
		}

		List<HospIdAndAppSecretVo> hospitals = findAppSecretByAppId(payAppId, appCode);
		HospIdAndAppSecretVo hospIdAndAppSecretVo = hospitals.get(0);

		List<HospitalCodeAndAppVo> tempVos = queryAppInfoByHospitalCode(hospIdAndAppSecretVo.getHospCode(), BizConstant.MODE_TYPE_APP);
		for (HospitalCodeAndAppVo vo : tempVos) {
			if (vo.getPayModeCode().equals(appCode)) {
				vos.add(vo);
			}
		}
		return vos;

	}*/

	/**
	 * @param hospitalCode
	 * @param appCode
	 * @return
	 */
	public List<HospitalCodeAndAppVo> queryAppInfoByHospitalId(String hospitalId, String appCode) {
		String hospitalCode = null;
		List<String> hospitalCodes = getHospitalCodeById(hospitalId);
		if (!CollectionUtils.isEmpty(hospitalCodes)) {
			hospitalCode = hospitalCodes.get(0);
		}
		return queryAppInfoByHospitalCode(hospitalCode, appCode);
	}

	/**
	 * @param hospitalCode
	 * @param appCode
	 * @return
	 */
	public List<HospitalCodeAndAppVo> queryAppInfoByHospitalCode(String hospitalCode, String appCode) {
		List<HospitalCodeAndAppVo> result = null;
		String cacheKey = getAppCacheKey();
		appCode = appCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR);
		Set<String> fieldNames = redisSvc.hkeys(cacheKey);
		Set<String> mathchKey = new HashSet<String>();
		for (String fieldName : fieldNames) {
			if (fieldName.startsWith(appCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR))) {
				mathchKey.add(fieldName);
			}
		}

		if (mathchKey.size() > 0) {
			String[] keys = new String[mathchKey.size()];
			List<String> voJsonList = redisSvc.hmget(cacheKey, mathchKey.toArray(keys));
			if (!CollectionUtils.isEmpty(voJsonList)) {
				for (String json : voJsonList) {
					if (StringUtils.isNotBlank(json)) {
						List<HospitalCodeAndAppVo> tempVos = JSON.parseArray(json, HospitalCodeAndAppVo.class);
						for (HospitalCodeAndAppVo hospitalCodeAndAppVo : tempVos) {
							if (hospitalCode.equalsIgnoreCase(hospitalCodeAndAppVo.getHospitalCode())) {
								if (CollectionUtils.isEmpty(result)) {
									result = new ArrayList<HospitalCodeAndAppVo>();
								}
								result.add(hospitalCodeAndAppVo);
							} else {
								break;
							}
						}
					}
				}
			}
		}
		if (logger.isDebugEnabled()) {
			if (result != null) {
				logger.debug("queryAppInfoByHospitalCode hospitalCode:{},appCode{},vo:{}",
						new Object[] { hospitalCode, appCode, JSON.toJSONString(result) });
			} else {
				logger.debug("queryAppInfoByHospitalCode hospitalCode:{} , appCode{} , vo:{}",
						new Object[] { hospitalCode, appCode, "null" });
			}
		}
		return result == null ? new ArrayList<HospitalCodeAndAppVo>(0) : result;
	}

	/**
	 * 仅用于cacheManager
	 * @param hospitalCode
	 * @param fields
	 * @return
	 */
	public List<HospitalCodeAndAppVo> queryAppInfoByFields(String hospitalCode, List<String> fields) {
		List<HospitalCodeAndAppVo> resutls = new ArrayList<HospitalCodeAndAppVo>();
		if (!CollectionUtils.isEmpty(fields)) {
			String cacheKey = getAppCacheKey();
			List<String> voJsonList = redisSvc.hmget(cacheKey, fields.toArray(new String[fields.size()]));
			if (!CollectionUtils.isEmpty(voJsonList)) {
				for (String json : voJsonList) {
					if (StringUtils.isNotBlank(json)) {
						resutls.addAll(JSON.parseArray(json, HospitalCodeAndAppVo.class));
					}
				}
			}
		}
		return resutls;
	}

	/**
	 * 根据接入的平台信息 查询医院的分院信息
	 * 
	 * @param appCode
	 * @param appId
	 * @return
	 */
	public List<BranchHospital> queryBranchsByHospitalCode(String appCode, String appId) {
		HospitalCodeAndAppVo vo = null;
		List<HospitalCodeAndAppVo> vos = queryHospitalCodeByApp(appCode, appId);
		if (!CollectionUtils.isEmpty(vos)) {
			vo = vos.get(0);
		}

		return queryBranchsByHospitalCode(vo.getHospitalCode());
	}

	/**
	 * 根据医院code 查询所有分院信息
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<BranchHospital> queryBranchsByHospitalCode(String hospitalCode) {
		List<BranchHospital> branchs = new ArrayList<BranchHospital>();
		if (StringUtils.isNotBlank(hospitalCode)) {
			String cacheKey = getInterfaceCaceKey();
			Set<String> keys = redisSvc.hkeys(cacheKey);
			Set<String> branchKeys = new HashSet<String>();
			for (String key : keys) {
				if (key.startsWith(hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR))) {
					branchKeys.add(key);
				}
			}

			if (branchKeys.size() > 0) {
				String[] branchArray = new String[branchKeys.size()];
				branchKeys.toArray(branchArray);
				List<String> vals = redisSvc.hmget(cacheKey, branchArray);
				for (String val : vals) {
					CodeAndInterfaceVo vo = JSON.parseObject(val, CodeAndInterfaceVo.class);
					BranchHospital branch = new BranchHospital();
					branch.setId(vo.getBranchHospitalId());
					branch.setCode(vo.getBranchHospitalCode());
					branch.setName(vo.getBranchName());
					branch.setHospitalCode(vo.getHospitalCode());
					branch.setHospitalId(vo.getHospitalId());
					branch.setAddress(vo.getBranchAddress());
					branch.setLatitude(vo.getLatitude());
					branch.setLongitude(vo.getLongitude());
					branchs.add(branch);
				}
			}
		}
		return branchs;
	}

	/**
	 * 更新/添加医院
	 * 
	 * updateType HospitalConstant.UPDATE_OP_TYPE_ADD HospitalConstant.UPDATE_OP_TYPE_UPDATE
	 * 
	 * @param hospitalCode
	 * @param opType
	 */
	public int updateHospitalCache(Hospital hospital, String updateType) throws SystemException {
		List<BranchHospital> branchs = hospital.getBranchHospitals();

		if (!CollectionUtils.isEmpty(branchs)) {
			Map<String, String> codeAndInterfaceMap = new HashMap<String, String>();
			String cacheKey = getInterfaceCaceKey();
			if (CacheConstant.UPDATE_OP_TYPE_DEL.equals(updateType)) {
				Set<String> keys = redisSvc.hkeys(cacheKey);
				Set<String> delKeys = new HashSet<String>();
				for (String key : keys) {
					if (key.startsWith(hospital.getCode().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR))) {
						delKeys.add(key);
					}
				}
				if (delKeys.size() > 0) {
					String[] delKeyArray = new String[delKeys.size()];
					redisSvc.hdel(cacheKey, delKeys.toArray(delKeyArray));
				}
			}
			if (CacheConstant.UPDATE_OP_TYPE_UPDATE.equals(updateType) || CacheConstant.UPDATE_OP_TYPE_ADD.equals(updateType))
				for (BranchHospital branch : branchs) {
					CodeAndInterfaceVo vo = null;
					String fieldName = null;
					if (StringUtils.isNotBlank(branch.getInterfaceId())) {
						vo = new CodeAndInterfaceVo();
						vo.setHospitalCode(hospital.getCode());
						vo.setBranchHospitalCode(branch.getCode());
						vo.setInterfaceId(branch.getInterfaceId() != null ? branch.getInterfaceId().trim() : null);
						vo.setStatus(hospital.getStatus());

						vo.setBranchHospitalId(branch.getId());
						vo.setHospitalId(hospital.getId());
						fieldName = genInterfaceFieldNameByCode(hospital.getCode(), branch.getCode());
						codeAndInterfaceMap.put(fieldName, JSON.toJSONString(vo));
					}
					redisSvc.hmset(cacheKey, codeAndInterfaceMap);

					return codeAndInterfaceMap.size();
				}
		}

		return 0;
	}

	public int updateHospitalStatus(String code, Integer status) throws SystemException {
		/** redis 缓存 **/
		String cacheKey = getInterfaceCaceKey();

		Set<String> keys = redisSvc.hkeys(cacheKey);
		if (!CollectionUtils.isEmpty(keys) && keys.size() > 0) {
			Set<String> updateKeys = new HashSet<String>();
			for (String key : keys) {
				if (key.startsWith(code)) {
					updateKeys.add(key);
				}
			}

			if (updateKeys.size() > 0) {
				String[] delKeyArray = new String[updateKeys.size()];
				List<String> jsons = redisSvc.hmget(cacheKey, updateKeys.toArray(delKeyArray));
				Map<String, String> updateJsonMap = new HashMap<String, String>();
				CodeAndInterfaceVo vo = null;
				for (String json : jsons) {
					vo = JSON.parseObject(json, CodeAndInterfaceVo.class);
					vo.setStatus(status);
					updateJsonMap.put(genInterfaceFieldNameByCode(vo.getHospitalCode(), vo.getBranchHospitalCode()), JSON.toJSONString(vo));
				}
				if (!updateJsonMap.isEmpty()) {
					redisSvc.hmset(cacheKey, updateJsonMap);

					return updateJsonMap.size();
				}
			}
		}

		return 0;
	}

	/**
	 * 将某一医院的信息 移除
	 * 
	 * @param hospital
	 */
	public int delHospital(Hospital hospital) throws SystemException {
		/** redis **/
		String cacheKey = CacheConstant.CACHE_HOSPITAL_HASH_KEY_PREFIX;
		boolean isLock = false;
		RedisLock redisLock = new RedisLock(redisSvc.getRedisPool());
		try {
			do {
				isLock = redisLock.singleLock(cacheKey);
			} while (!isLock);
			redisSvc.hdel(cacheKey, hospital.getCode());

			return 1;
		} finally {
			redisLock.singleUnlock(cacheKey);
		}
	}

	/**
	 * 清除缓存中所有医院信息
	 */
	public int delAllHospital() throws SystemException {
		/** redis **/
		String cacheKey = CacheConstant.CACHE_HOSPITAL_HASH_KEY_PREFIX;
		boolean isLock = false;
		RedisLock redisLock = new RedisLock(redisSvc.getRedisPool());
		try {
			do {
				isLock = redisLock.singleLock(cacheKey);
			} while (!isLock);
			redisSvc.del(cacheKey);

			return 1;
		} finally {
			redisLock.singleUnlock(cacheKey);
		}
	}

	/**
	 * 更新医院已平台配置缓存
	 * 
	 * @param hospitalId
	 */
	public int updateAppCacheByHospitalId(String hospitalId, List<String> oldAppIdAndAppCodes) {
		int result = 0;

		try {
			// 1更新 hospital.app.interface (平台接入信息和接入平台的支付信息)
			updateAppPayCacheByHospitalId(hospitalId, oldAppIdAndAppCodes);

			// 2更新 hospital.app.secret
			updateAppSecretCacheByHospitalId(hospitalId, oldAppIdAndAppCodes);

			result = 1;
		} catch (Exception e) {
			logger.error("updateAppCacheByHospitalId error. error msg: {}. cause by: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return result;
	}

	/**
	 * 更新医院的平台配置信息
	 * 
	 * @param hospitalId
	 * @param oldAppIdAndAppCodes
	 */
	public int updateAppSecretCacheByHospitalId(String hospitalId, List<String> oldAppIdAndAppCodes) {
		String appSecretKey = getAppSecretCacheKey();
		try {
			if (!CollectionUtils.isEmpty(oldAppIdAndAppCodes)) {
				String[] delFields = new String[oldAppIdAndAppCodes.size()];
				return redisSvc.hdel(appSecretKey, oldAppIdAndAppCodes.toArray(delFields)).intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
		// TODO:外部自行调用该方法，内部不再自动执行
		// updateAppSecretCache(hospitalId);
	}

	/**
	 * 新平台 更新医院的平台支付信息,删除oldAppIdAndAppCodes
	 * 
	 * @param hospitalId
	 * @param oldAppIdAndAppCodes
	 */
	public int updateAppPayCacheByHospitalId(String appCode, String appId, String values) {// 新平台新增方法
		// 删除医院以前的appInfo Cache
		String appIdCacheKey = getAppCacheKey();
		String fieldName = genAppFieldNameByCode(appCode, appId);
		if (!StringUtils.isEmpty(values)) {
			redisSvc.hdel(appIdCacheKey, fieldName).intValue();
			redisSvc.hset(appIdCacheKey, fieldName, values);
		}

		return 0;

		// TODO:外部自行调用该方法，内部不再自动执行
		// updateAppCache(hospitalId);
	}

	/**
	 * 更新医院的平台支付信息,删除oldAppIdAndAppCodes
	 * 
	 * @param hospitalId
	 * @param oldAppIdAndAppCodes
	 */
	public int updateAppPayCacheByHospitalId(String hospitalId, List<String> oldAppIdAndAppCodes) {
		// 删除医院以前的appInfo Cache
		String appIdCacheKey = getAppCacheKey();
		if (!CollectionUtils.isEmpty(oldAppIdAndAppCodes)) {
			String[] delFields = new String[oldAppIdAndAppCodes.size()];
			return redisSvc.hdel(appIdCacheKey, oldAppIdAndAppCodes.toArray(delFields)).intValue();
		}

		return 0;
	}

	public int updateAppCache(List<HospitalCodeAndAppVo> vos) {
		String appIdCacheKey = getAppCacheKey();
		if (!CollectionUtils.isEmpty(vos)) {
			Map<String, String> appAndcodeVoMap = new HashMap<String, String>();
			for (HospitalCodeAndAppVo appVo : vos) {
				if (StringUtils.isNotEmpty(appVo.getAppCode()) && StringUtils.isNotEmpty(appVo.getAppId())) {
					String fieldName = genAppFieldNameByCode(appVo.getAppCode(), appVo.getAppId());
					appAndcodeVoMap.put(fieldName, JSON.toJSONString(appVo));

					if (appAndcodeVoMap.containsKey(fieldName)) {
						List<HospitalCodeAndAppVo> list = JSON.parseArray(appAndcodeVoMap.get(fieldName), HospitalCodeAndAppVo.class);
						list.add(appVo);
						appAndcodeVoMap.put(fieldName, JSON.toJSONString(list));
					} else {
						List<HospitalCodeAndAppVo> list = new ArrayList<HospitalCodeAndAppVo>(1);
						list.add(appVo);
						appAndcodeVoMap.put(fieldName, JSON.toJSONString(list));
					}
				}
			}

			if (!CollectionUtils.isEmpty(appAndcodeVoMap)) {
				redisSvc.hmset(appIdCacheKey, appAndcodeVoMap);
			}

			return appAndcodeVoMap.size();
		}

		return 0;
	}

	public int updateAppCache(HospitalCodeAndAppVo vo) {
		String cacheKey = getInterfaceCaceKey();
		String fieldName = genAppFieldNameByCode(vo.getAppCode(), vo.getAppId());
		return redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(vo)).intValue();
	}

	/**
	 * redis hash存储结构中的cache key
	 * 
	 * @return
	 */
	private String getInterfaceCaceKey() {
		return CacheConstant.CACHE_CODE_INTERFACE_KEY_PREFIX;
	}

	private String getAppCacheKey() {
		return CacheConstant.CACHE_APP_CODE_KEY_PREFIX;
	}

	private String getHospBaseInfoKey() {
		return CacheConstant.CACHE_HOSPITAL_BASEINFO;
	}

	/**
	 * 根据医院code和分院code生成redis hash存储结构中的field name
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @return
	 */
	private String genInterfaceFieldNameByCode(String hospitalCode, String branchHospitalCode) {
		return hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(branchHospitalCode);
	}

	private String genAppFieldNameByCode(String appCode, String appId) {
		return appCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(appId);
	}

	private String genHospBaseInfoName(String hospId, String hospCode) {
		return hospId.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(hospCode);
	}

	public int updateHospitalForBranch(List<CodeAndInterfaceVo> vos) {
		Jedis jedis = redisSvc.getRedisClient();
		boolean broken = false;

		if (jedis != null) {
			try {
				if (!CollectionUtils.isEmpty(vos)) {
					String cacheKey = getInterfaceCaceKey();

					// 删除旧缓存
					String hospitalCode = vos.get(0).getHospitalCode();
					Set<String> keys = jedis.hkeys(cacheKey);
					if (!CollectionUtils.isEmpty(keys)) {
						Set<String> removeFileds = new HashSet<String>();
						for (String key : keys) {
							if (key.startsWith(hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR))) {
								removeFileds.add(key);
							}
						}
						if (!CollectionUtils.isEmpty(removeFileds)) {
							String[] fieldsArray = new String[removeFileds.size()];
							jedis.hdel(cacheKey, removeFileds.toArray(fieldsArray));
						}
					}

					// 设置新缓存
					Map<String, String> hospitalData = new HashMap<String, String>();
					for (CodeAndInterfaceVo vo : vos) {
						String fieldName = genInterfaceFieldNameByCode(vo.getHospitalCode(), vo.getBranchHospitalCode());
						hospitalData.put(fieldName, JSON.toJSONString(vo));
					}
					jedis.hmset(cacheKey, hospitalData);

					return hospitalData.size();

					// TODO:外部自行调用该方法，内部不再自动执行
					// 更新该医院的基础信息缓存
					// updateHospBaseInfo(hospitalId);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				broken = true;
			} finally {
				redisSvc.returnResource(jedis, broken);
			}
		}

		return broken ? -1 : 0;
	}

	public int updateHospitalByBranch(List<CodeAndInterfaceVo> vos) {
		if (!CollectionUtils.isEmpty(vos)) {
			if (!CollectionUtils.isEmpty(vos)) {
				String cacheKey = getInterfaceCaceKey();
				Map<String, String> hospitalData = new HashMap<String, String>();
				for (CodeAndInterfaceVo vo : vos) {
					String fieldName = genInterfaceFieldNameByCode(vo.getHospitalCode(), vo.getBranchHospitalCode());
					hospitalData.put(fieldName, JSON.toJSONString(vo));
				}
				redisSvc.hmset(cacheKey, hospitalData);

				return hospitalData.size();
			}
		}

		return 0;
	}

	public int updateAppSecretCache(List<HospIdAndAppSecretVo> vos) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (!CollectionUtils.isEmpty(vos)) {
			for (HospIdAndAppSecretVo vo : vos) {
				if (StringUtils.isNotBlank(vo.getAppId()) && StringUtils.isNotBlank(vo.getAppCode())) {
					jsonMap.put(genAppFieldNameByCode(vo.getAppCode(), vo.getAppId()), JSON.toJSONString(vo));
				}
			}
			if (jsonMap.size() > 0) {
				redisSvc.hmset(getAppSecretCacheKey(), jsonMap);

				return jsonMap.size();
			}
		}

		return 0;
	}

	/**
	 * 根据医院Id 查找对应的建康易接入信息
	 * 
	 * @param hospitalId
	 * @return
	 */
	public List<HospIdAndAppSecretVo> getEasyHealthAppByHospitalId(String hospitalId, String appId) {
		List<HospIdAndAppSecretVo> result = null;

		List<HospIdAndAppSecretVo> vos = findAppSecretByAppId(appId);

		if (!CollectionUtils.isEmpty(vos)) {
			HospIdAndAppSecretVo vo = vos.get(0);
			result = new ArrayList<HospIdAndAppSecretVo>(1);
			result.add(vo);
		}
		return result == null ? new ArrayList<HospIdAndAppSecretVo>(0) : result;
	}

	/**
	 * 根据appCode 得到同一appCode所有的医院信息(简单信息,只包含医院的名称,id,code)
	 * 
	 * @param appCode
	 * @return
	 */
	public List<HospIdAndAppSecretVo> findByAppSecretByAppCode(String appCode, String areaCode) {
		List<HospIdAndAppSecretVo> vos = null;
		if (StringUtils.isNotBlank(appCode)) {
			String cacheKey = getAppSecretCacheKey();
			Set<String> fileds = redisSvc.hkeys(cacheKey);
			if (!CollectionUtils.isEmpty(fileds)) {
				Set<String> matchFileds = new HashSet<String>();
				for (String key : fileds) {
					// 使用app开头的，都查出来。
					if (key.startsWith(appCode)) {
						matchFileds.add(key);
					}
				}

				if (!CollectionUtils.isEmpty(matchFileds)) {
					String[] fieldsArray = new String[matchFileds.size()];
					List<String> jsonValList = redisSvc.hmget(cacheKey, matchFileds.toArray(fieldsArray));
					if (!CollectionUtils.isEmpty(jsonValList)) {
						vos = new ArrayList<HospIdAndAppSecretVo>();
						for (String jsonVal : jsonValList) {
							if (StringUtils.isNotBlank(jsonVal) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonVal)) {
								HospIdAndAppSecretVo vo = JSON.parseObject(jsonVal, HospIdAndAppSecretVo.class);
								if (StringUtils.isNotEmpty(vo.getAppId())) {
									if (StringUtils.isNotEmpty(areaCode)) {
										//                                        if (areaCode.equalsIgnoreCase(vo.getAreaCode())) {
										if (vo.getAreaCode().contains(areaCode)) {
											vos.add(vo);
										}
									} else {
										vos.add(vo);
									}
								}
							}
						}
					}
				}
			}
		} else {
			vos = new ArrayList<HospIdAndAppSecretVo>();
		}

		if (!CollectionUtils.isEmpty(vos)) {
			Collections.sort(vos);
		}

		return vos;
	}

	public List<HospIdAndAppSecretVo> findAppSecretByAppId(String appId, String appCode) {
		List<HospIdAndAppSecretVo> result = null;
		HospIdAndAppSecretVo vo = null;
		if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(appCode)) {
			String jsonVal = redisSvc.hget(getAppSecretCacheKey(), genAppFieldNameByCode(appCode, appId));
			if (StringUtils.isNotBlank(jsonVal) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonVal)) {
				vo = JSON.parseObject(jsonVal, HospIdAndAppSecretVo.class);
				result = new ArrayList<HospIdAndAppSecretVo>(1);
				result.add(vo);
			}
		}
		return result == null ? new ArrayList<HospIdAndAppSecretVo>(0) : result;
	}

	private String getAppSecretCacheKey() {
		return CacheConstant.CACHE_APP_SECRET_KEY_PREFIX;
	}

	public List<String> findAppInfoCacheFields(List<HospIdAndAppSecretVo> vos) {
		List<String> fields = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(vos)) {
			for (HospIdAndAppSecretVo vo : vos) {
				if (StringUtils.isNotBlank(vo.getAppId()) && StringUtils.isNotBlank(vo.getAppCode())) {
					fields.add(genAppFieldNameByCode(vo.getAppCode(), vo.getAppId()));
				}
			}
		}
		return fields;
	}

	public List<HospIdAndAppSecretVo> findAppSecretByAppId(String appId) {
		List<HospIdAndAppSecretVo> vos = new ArrayList<HospIdAndAppSecretVo>();
		if (StringUtils.isNotBlank(appId)) {
			String cacheKey = getAppSecretCacheKey();
			Set<String> fileds = redisSvc.hkeys(cacheKey);
			Set<String> matchKeys = new HashSet<String>();
			if (!CollectionUtils.isEmpty(fileds)) {
				for (String matchkey : fileds) {
					if (matchkey.endsWith(appId)) {
						matchKeys.add(matchkey);
					}
				}
			}

			if (!CollectionUtils.isEmpty(matchKeys)) {
				String[] matchKeyArray = new String[matchKeys.size()];
				List<String> voJsons = redisSvc.hmget(getAppSecretCacheKey(), matchKeys.toArray(matchKeyArray));
				if (!CollectionUtils.isEmpty(voJsons)) {
					for (String jsonVal : voJsons) {
						if (StringUtils.isNotBlank(jsonVal) && !jsonVal.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
							HospIdAndAppSecretVo vo = JSON.parseObject(jsonVal, HospIdAndAppSecretVo.class);
							vos.add(vo);
						}
					}
				}
			}
		}
		return vos;
	}

	/***
	 * 获取缓存中所有医院基础信息
	 * */
	public List<Hospital> queryAllHospBaseInfo() {
		String cacheKey = getHospBaseInfoKey();
		List<Hospital> vos = null;
		Set<String> fields = redisSvc.hkeys(cacheKey);
		if (!CollectionUtils.isEmpty(fields)) {
			String[] fieldsArray = new String[fields.size()];
			List<String> jsonValList = redisSvc.hmget(cacheKey, fields.toArray(fieldsArray));
			if (!CollectionUtils.isEmpty(jsonValList)) {
				vos = new ArrayList<Hospital>();
				for (String jsonVal : jsonValList) {
					if (StringUtils.isNotBlank(jsonVal) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonVal)) {
						Hospital vo = JSON.parseObject(jsonVal, Hospital.class);
						if (vo != null && vo.getStatus() == 1) {
							vos.add(vo);
						}
					}
				}
			}
		}
		return vos;
	}

	/**
	 * 目前仅供停诊接口使用
	 * 返回了 hospital-branch-interface-platform的关系，与支付无关。
	 * @return
	 */
	public List<HospitalCodeInterfaceAndAppVo> getHospitalCodeInterfaceAndAppVos() {
		List<HospitalCodeInterfaceAndAppVo> vos = new ArrayList<>();

		List<Hospital> hospitals = queryAllHospBaseInfo();
		for (Hospital hospital : hospitals) {
			// 只要已启用的
			if (hospital.getStatus() == 1 && !CollectionUtils.isEmpty(hospital.getPlatformPaySettings())) {
				for (BranchHospital branchHospital : hospital.getBranchHospitals()) {
					// 有接口ID
					if (StringUtils.isNotBlank(branchHospital.getInterfaceId())) {
						for (HospitalPlatformSettings settings : hospital.getHospitalPlatformSettings()) {
							HospitalCodeInterfaceAndAppVo vo = new HospitalCodeInterfaceAndAppVo();
							vo.setAppCode(settings.getPlatformSettings().getCode());
							// vo.setAppId(settings.getPlatformSettings().getAppId());
							vo.setHospitalCode(hospital.getCode());
							vo.setHospitalName(hospital.getName());
							vo.setHospitalId(hospital.getId());
							vo.setBranchHospitalCode(branchHospital.getCode());
							vo.setBranchHospitalName(branchHospital.getName());
							vo.setInterfaceId(branchHospital.getInterfaceId());

							vos.add(vo);
						}
					}
				}
			}
		}

		return vos;
	}

	public int updateHospBaseInfo(Hospital vo) {
		if (vo != null) {
			String cacheKey = getHospBaseInfoKey();
			String fieldName = genHospBaseInfoName(vo.getId(), vo.getCode());
			redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(vo));

			return 1;
		}

		return 0;
	}

	public int updateHospitalPlatform(Hospital vo) {
		if (vo != null) {
			String cacheKey = getHospitalPlatformCacheKey();
			Set<String> keys = redisSvc.hkeys(cacheKey);
			if (!CollectionUtils.isEmpty(keys)) {
				List<String> matchKeys = new ArrayList<>();
				String suffix = CacheConstant.CACHE_KEY_SPLIT_CHAR.concat(vo.getId());
				for (String key : keys) {
					if (key.endsWith(suffix)) {
						matchKeys.add(key);
					}
				}

				if (matchKeys.size() > 0) {
					String[] fields = matchKeys.toArray(new String[matchKeys.size()]);
					List<String> values = redisSvc.hmget(cacheKey, fields);
					if (!CollectionUtils.isEmpty(values)) {
						Map<String, String> setValues = new HashMap<>();
						for (String value : values) {
							PlatformSettings settings = JSON.parseObject(value, PlatformSettings.class);
							settings.setHospital(vo);
							setValues.put(getHospitalPlatformField(settings.getCode(), vo.getId()), JSON.toJSONString(settings));
						}

						redisSvc.hmset(cacheKey, setValues);
					}
				}

				return 1;
			}
		}

		return 0;
	}

	private String getHospitalPlatformCacheKey() {
		return CacheConstant.CACHE_HOSPITAL_PLATFORM_KEY_PREFIX;
	}

	public String getHospitalPlatformField(String appCode, String hospitalId) {
		return appCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(hospitalId);
	}

	/**
	 * 查询某个医院某个平台的配置信息
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月27日 
	 * @param hospitalId
	 * @param appCode
	 * @return
	 */
	public List<PlatformSettings> findHospitalPlatform(String hospitalId, String appCode) {

		List<PlatformSettings> result = null;
		PlatformSettings vo = null;
		if (StringUtils.isNotBlank(hospitalId) && StringUtils.isNotBlank(appCode)) {
			String jsonVal = redisSvc.hget(getHospitalPlatformCacheKey(), getHospitalPlatformField(appCode, hospitalId));
			if (StringUtils.isNotBlank(jsonVal) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonVal)) {
				vo = JSON.parseObject(jsonVal, PlatformSettings.class);
				result = new ArrayList<PlatformSettings>(1);
				result.add(vo);
			}
		}

		return result == null ? new ArrayList<PlatformSettings>(0) : result;
	}

	/**
	 * 查询某个医院下所有平台的配置信息
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月27日 
	 * @param hospitalId
	 * @return
	 */
	public List<PlatformSettings> findHospitalPlatform(String hospitalId) {

		List<PlatformSettings> result = null;
		String cacheKey = getHospitalPlatformCacheKey();
		if (StringUtils.isNotBlank(hospitalId)) {

			Set<String> keys = redisSvc.hkeys(cacheKey);
			List<String> matchFields = new ArrayList<String>();
			String matchField = hospitalId;

			if (!CollectionUtils.isEmpty(keys)) {
				for (String field : keys) {
					if (field.endsWith(matchField)) {
						matchFields.add(field);
					}
				}
			}

			if (!CollectionUtils.isEmpty(matchFields)) {
				String[] matchFieldArray = new String[matchFields.size()];
				List<String> jsons = redisSvc.hmget(cacheKey, matchFields.toArray(matchFieldArray));
				if (!CollectionUtils.isEmpty(jsons)) {
					result = JSON.parseObject(jsons.toString(), new TypeReference<List<PlatformSettings>>() {
					});
				}
			}

		}

		return result == null ? new ArrayList<PlatformSettings>(0) : result;
	}
}
