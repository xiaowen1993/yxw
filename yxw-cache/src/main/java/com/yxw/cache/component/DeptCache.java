package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.vo.platform.Dept;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.platform.cache
 * @ClassName: DeptCache
 * @Statement: <p>
 *             科室缓存管理
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-13
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DeptCache {
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	public DeptCache() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 得到一级科室列表
	 * 
	 * @param hospitalCode
	 *            医院Id
	 * @param branchHospitalCode
	 *            分院Code 没有分院 传入参数null eg: 查询医院code:a 分院 code:b getDepts(a,b)
	 *            查询医院id:a没有分院 getDepts(a,null)
	 * 
	 * @return
	 */
	public List<Dept> getLevelOneDepts(String hospitalCode, String branchHospitalCode) {
		List<Dept> depts = new ArrayList<Dept>();
		String cahceKey = getLevelOneCacheKey();
		String fieldName = getFieldName(hospitalCode.trim(), branchHospitalCode.trim());
		String jsonList = redisSvc.hget(cahceKey, fieldName);
		if (StringUtils.isNotEmpty(jsonList) && !jsonList.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			depts = JSON.parseArray(jsonList, Dept.class);
		}
		return depts;
	}

	/**
	 * 得到二级科室列表
	 * 
	 * @param hospitalCode
	 *            医院Id
	 * @param branchHospitalCode
	 *            分院Code 
	 * @param deptCode
	 *            一级科室Code 
	 * 
	 * @return
	 */
	public List<Dept> getLevelTwoDepts(String hospitalCode, String branchHospitalCode, String deptCode) {
		List<Dept> depts = new ArrayList<Dept>();
		String cahceKey = getLevelTwoCacheKey();
		String fieldName = getLevelTwoFiedName(hospitalCode.trim(), branchHospitalCode.trim(), deptCode.trim());
		String jsonList = redisSvc.hget(cahceKey, fieldName);
		if (StringUtils.isNotEmpty(jsonList) && !jsonList.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			depts = JSON.parseArray(jsonList, Dept.class);
		}
		return depts;
	}

	//	public Map<String, List<Dept>> getLevelTwoDepts(String hospitalCode, String branchHospitalCode) {
	//		Map<String, List<Dept>> deptsMap = new HashMap<String, List<Dept>>();
	//		String cahceKey = getLevelTwoCacheKey();
	//		String fieldNamePrefix = getFieldName(hospitalCode, branchHospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR);
	//		Set<String> fieldNames = redisSvc.hkeys(cahceKey);
	//
	//		Set<String> levelTwoDeptFields = new HashSet<String>();
	//		for (String fieldName : fieldNames) {
	//			if (fieldName.startsWith(fieldNamePrefix)) {
	//				levelTwoDeptFields.add(fieldName);
	//			}
	//		}
	//		if (levelTwoDeptFields.size() > 0) {
	//			String[] fieldsArray = new String[levelTwoDeptFields.size()];
	//			levelTwoDeptFields.toArray(fieldsArray);
	//			List<String> jsonVals = redisSvc.hmget(cahceKey, fieldsArray);
	//			int index = 0;
	//			for (String jsonVal : jsonVals) {
	//				if (StringUtils.isNotBlank(jsonVal) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonVal)) {
	//					List<Dept> depts = JSON.parseArray(jsonVal, Dept.class);
	//					deptsMap.put(fieldsArray[index], depts);
	//				} else {
	//					deptsMap.put(fieldsArray[index], new ArrayList<Dept>());
	//				}
	//				index++;
	//			}
	//		}
	//
	//		return deptsMap;
	//	}

	/**
	 * 存储 建康易 医院的一级科室列表
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param regType
	 * @param depts
	 */
	public int setAppLevelOneDepts(String hospitalCode, String branchHospitalCode, String regType, List<Dept> depts) {
		if (!CollectionUtils.isEmpty(depts)) {
			String cahceKey = getAppLevelOneCacheKey();
			String jsonString = JSON.toJSONString(depts);
			redisSvc.hdel(cahceKey, getAppLevelOneFieldName(hospitalCode.trim(), branchHospitalCode.trim(), regType));
			return redisSvc.hset(cahceKey, getAppLevelOneFieldName(hospitalCode.trim(), branchHospitalCode.trim(), regType), jsonString).intValue();
		}

		return 0;
	}

	/*	*//**
			* 获取 建康易 医院的一级科室列表
			* 
			* @param hospitalCode
			*            医院Id
			* @param branchHospitalCode
			*            分院Code 没有分院 传入参数null eg: 查询医院code:a 分院 code:b getDepts(a,b)
			*            查询医院id:a没有分院 getDepts(a,null)
			* 
			* @return
			*/
	public List<Dept> getAppLevelOneDepts(String hospitalCode, String branchHospitalCode, String regType) {
		List<Dept> depts = new ArrayList<Dept>();
		String cahceKey = getAppLevelOneCacheKey();
		String fieldName = getAppLevelOneFieldName(hospitalCode.trim(), branchHospitalCode.trim(), regType);
		String jsonList = redisSvc.hget(cahceKey, fieldName);
		if (StringUtils.isNotEmpty(jsonList) && !jsonList.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			depts = JSON.parseArray(jsonList, Dept.class);
		}
		return depts;
	}

	/**
	 * 建康易2级科室写入缓存
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param subDeptMap
	 */
	public int setAppLevelTwoDepts(String hospitalCode, String branchHospitalCode, Map<String, List<Dept>> subDeptMap) {
		if (!CollectionUtils.isEmpty(subDeptMap)) {
			String cacheKey = getAppLevelTwoCacheKey();
			//转换为json格式
			Map<String, String> jsonMap = new ConcurrentHashMap<String, String>();
			for (String subDeptKey : subDeptMap.keySet()) {
				jsonMap.put(subDeptKey, JSON.toJSONString(subDeptMap.get(subDeptKey)));
			}
			if (!jsonMap.isEmpty()) {
				redisSvc.hdel(cacheKey, subDeptMap.keySet().toArray(new String[subDeptMap.keySet().size()]));
				redisSvc.hmset(cacheKey, jsonMap);

				return jsonMap.size();
			}
		}

		return 0;
	}

	/**
	 * 掌上医院2级科室写入缓存
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param subDeptMap
	 */
	public int setLevelTwoDepts(String hospitalCode, String branchHospitalCode, Map<String, List<Dept>> subDeptMap) {
		if (!CollectionUtils.isEmpty(subDeptMap)) {
			String cahceKey = getLevelTwoCacheKey();
			//转换为json格式
			Map<String, String> jsonMap = new ConcurrentHashMap<String, String>();
			for (String subDeptKey : subDeptMap.keySet()) {
				jsonMap.put(subDeptKey, JSON.toJSONString(subDeptMap.get(subDeptKey)));
			}
			if (!jsonMap.isEmpty()) {
				redisSvc.hdel(cahceKey, subDeptMap.keySet().toArray(new String[subDeptMap.keySet().size()]));
				redisSvc.hmset(cahceKey, jsonMap);

				return jsonMap.size();
			}
		}

		return 0;
	}

	/**
	 * app根据一级室得到二级科室列表
	 * 
	 * @param hospitalCode
	 *            医院Id
	 * @param branchHospitalCode
	 *            分院Code 
	 * @param deptCode
	 *            一级科室Code 
	 * 
	 * @return
	 */
	public List<Dept> getAppLevelTwoDepts(String hospitalCode, String branchHospitalCode, String regType, String deptCode) {
		List<Dept> depts = new ArrayList<Dept>();
		String cahceKey = getAppLevelTwoCacheKey();
		String fieldName = getAppLevelTwoFiedName(hospitalCode, branchHospitalCode, regType, deptCode);
		String jsonList = redisSvc.hget(cahceKey, fieldName);
		if (StringUtils.isNotEmpty(jsonList) && !jsonList.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			depts = JSON.parseArray(jsonList, Dept.class);
		}
		return depts;
	}

	/**
	 * app得到二级科室所有列表
	 * 
	 * @param hospitalCode
	 *            医院Id
	 * @param branchHospitalCode
	 *            分院Code 
	 * @param deptCode
	 *            一级科室Code 
	 * 
	 * @return
	 */
	public List<Dept> getAppLevelTwoDepts(String hospitalCode, String branchHospitalCode, String regType) {
		List<Dept> result = new ArrayList<Dept>();

		String cahceKey = getAppLevelTwoCacheKey();
		String fieldNamePrefix = getFieldName(hospitalCode, branchHospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR);
		if (StringUtils.isNotBlank(regType)) {
			fieldNamePrefix = fieldNamePrefix.concat(regType);
		}
		Set<String> fieldNames = redisSvc.hkeys(cahceKey);

		Set<String> levelTwoDeptFields = new HashSet<String>();
		for (String fieldName : fieldNames) {
			if (fieldName.startsWith(fieldNamePrefix)) {
				levelTwoDeptFields.add(fieldName);
			}
		}
		if (levelTwoDeptFields.size() > 0) {
			String[] fieldsArray = new String[levelTwoDeptFields.size()];
			levelTwoDeptFields.toArray(fieldsArray);
			List<String> jsonVals = redisSvc.hmget(cahceKey, fieldsArray);
			for (String jsonVal : jsonVals) {
				if (StringUtils.isNotBlank(jsonVal) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonVal)) {
					List<Dept> depts = JSON.parseArray(jsonVal, Dept.class);
					result.addAll(depts);
				}
			}
		}

		return result;
	}

	/**
	 * 掌上医院一级科室列表写入缓存
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param depts
	 */
	public int setLevelOneDepts(String hospitalCode, String branchHospitalCode, List<Dept> depts) {
		if (!CollectionUtils.isEmpty(depts)) {
			String cahceKey = getLevelOneCacheKey();
			String jsonString = JSON.toJSONString(depts);
			redisSvc.hdel(cahceKey, getFieldName(hospitalCode.trim(), branchHospitalCode.trim()));
			redisSvc.hset(cahceKey, getFieldName(hospitalCode.trim(), branchHospitalCode.trim()), jsonString);

			return depts.size();
		}

		return 0;
	}

	/**
	 * 根据(app)部门的缓存field,获取部门
	 * @param deptNameStr
	 * @param areaCode
	 * @return
	 */
	public List<Dept> getDeptByField(String deptNameStr, String regType,/*String appCode,*/String areaCode) {
		List<Dept> result = null;
		Dept dept = null;
		String cacheKey = null;

		/*if (deptNameStr.indexOf(RegisterConstant.REG_TYPE_CUR_CHINESE) > -1) {
			List<Object> results = getTwoInterfaceDeptNameCacheKey(String.valueOf(RegisterConstant.REG_TYPE_CUR), appCode,areaCode);

			cacheKey = CollectionUtils.isEmpty(results) ? "" : (String) results.get(0);
		}
		if (deptNameStr.indexOf(RegisterConstant.REG_TYPE_APPOINTMENT_CHINESE) > -1) {
			List<Object> results = getTwoInterfaceDeptNameCacheKey(String.valueOf(RegisterConstant.REG_TYPE_APPOINTMENT), appCode,areaCode);

			cacheKey = CollectionUtils.isEmpty(results) ? "" : (String) results.get(0);
		}*/
		List<Object> results = getTwoInterfaceDeptNameCacheKey(regType, areaCode);
		cacheKey = CollectionUtils.isEmpty(results) ? "" : (String) results.get(0);

		if (StringUtils.isNotBlank(cacheKey)) {
			Set<String> keys = redisSvc.keys(cacheKey);
			if (!CollectionUtils.isEmpty(keys)) {
				for (String key : keys) {
					String jsonVal = redisSvc.hget(key, deptNameStr);
					if (StringUtils.isNotBlank(jsonVal) && !jsonVal.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
						dept = JSON.parseObject(jsonVal, Dept.class);
						result = new ArrayList<Dept>(1);
						result.add(dept);
					}
				}
			}

		}
		return result == null ? new ArrayList<Dept>(0) : result;
	}

	/**
	 * 查询预约挂号科室
	 * @param deptNameStr
	 * @param areaCode
	 * @return
	 */
	public List<String> searchDeptNames(String deptNameStr, String regType, /*String appCode,*/String areaCode) {
		List<String> searchMatch = new ArrayList<String>();

		//regType:预约/当班/all
		List<Object> results = getTwoInterfaceDeptNameCacheKey(regType, areaCode);

		String cacheKey = CollectionUtils.isEmpty(results) ? "" : (String) results.get(0);

		Set<String> keys = redisSvc.keys(cacheKey);
		if (!CollectionUtils.isEmpty(keys)) {
			for (String key : keys) {
				Set<String> fields = redisSvc.hkeys(key);
				if (!CollectionUtils.isEmpty(fields)) {
					for (String field : fields) {
						String deptName = field.split("\\(")[0];
						if (deptName.indexOf(deptNameStr) > -1) {
							searchMatch.add(field);
						}
					}
				}
			}
		}

		return searchMatch;
	}

	/**
	 * app按科室名称hash存储
	 * @param cacheKey
	 * @param deptNameMap
	 */
	public int setAppDeptNames(String cacheKey, Map<String, String> deptNameMap) {
		if (!CollectionUtils.isEmpty(deptNameMap)) {
			redisSvc.hmset(cacheKey, deptNameMap);

			return deptNameMap.size();
		}

		return 0;
	}

	private String getFieldName(String hospitalCode, String branchHospitalCode) {
		return hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(branchHospitalCode);
	}

	/**
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param deptCode    一级科室code
	 * @return
	 */
	private String getLevelTwoFiedName(String hospitalCode, String branchHospitalCode, String deptCode) {
		return getFieldName(hospitalCode.trim(), branchHospitalCode.trim()).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat("2")
				.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(deptCode);
	}

	/**
	 * 一级科室cache key
	 * 
	 * @return
	 */
	private String getLevelOneCacheKey() {
		return CacheConstant.CACHE_LEVELONE_DEPT_HASH_PREFIX;
	}

	/**
	 * 二级科室cache key
	 * 
	 * @return
	 */
	private String getLevelTwoCacheKey() {
		return CacheConstant.CACHE_LEVELTWO_DEPT_HASH_PREFIX;
	}

	/**
	 * 健康易一级科室cache key
	 * 
	 * @return
	 */
	private String getAppLevelOneCacheKey() {
		return CacheConstant.CACHE_APP_LEVELONE_DEPT_HASH_PREFIX;
	}

	/*	*//**
			* 健康易二级科室cache key
			* 
			* @return
			*/
	private String getAppLevelTwoCacheKey() {
		return CacheConstant.CACHE_APP_LEVELTWO_DEPT_HASH_PREFIX;
	}

	/**
	 * 健康易  一级科室fieldName的获取
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param regType
	 * @return
	 */
	private String getAppLevelOneFieldName(String hospitalCode, String branchHospitalCode, String regType) {
		String cacheFieldName = hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(branchHospitalCode);
		if (StringUtils.isNotEmpty(regType)) {
			cacheFieldName = cacheFieldName.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(regType);
		}
		return cacheFieldName;
	}

	/**
	 * 健康易  二级科室fieldName的获取
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param deptCode    一级科室code
	 * @return
	 */
	private String getAppLevelTwoFiedName(String hospitalCode, String branchHospitalCode, String regType, String parentDeptCode) {
		String appLevelTwoFiedName = getAppLevelOneFieldName(hospitalCode, branchHospitalCode, regType);
		if (StringUtils.isNotEmpty(parentDeptCode)) {
			appLevelTwoFiedName = appLevelTwoFiedName.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(parentDeptCode);
		}
		return appLevelTwoFiedName;
	}

	/**
	 * (当班与预约为2套不同接口)当班/预约科室名称缓存的keyName
	 * @param regType    当班/预约
	 * @param areaCode   区域代码
	 * @return
	 */
	public List<Object> getTwoInterfaceDeptNameCacheKey(String regType, String areaCode) {
		List<Object> results = new ArrayList<Object>();

		String nameCacheKey = CacheConstant.CACHE_APP_DEPT_NAME_HASH_PREFIX;
		nameCacheKey = nameCacheKey.concat(areaCode).concat(CacheConstant.CACHE_KEY_PATTERN_CHAR);
		if (StringUtils.isNotEmpty(regType)) {
			nameCacheKey = nameCacheKey.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(regType);
		}
		results.add(nameCacheKey);

		return results;
	}

}
