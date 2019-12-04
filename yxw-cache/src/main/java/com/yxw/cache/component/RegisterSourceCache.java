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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.vo.platform.Dept;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.register.Doctor;
import com.yxw.interfaces.vo.register.RegInfo;

/**
 * @Package: com.yxw.platform.cache
 * @ClassName: RegInfoCache
 * @Statement: <p>
 *             号源缓存
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-14
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterSourceCache {
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);
	private HospitalRuleCache hospitalRuleCache = SpringContextHolder.getBean(HospitalRuleCache.class);
	private DeptCache deptCache = SpringContextHolder.getBean(DeptCache.class);

	/**
	 * 存储号源信息到缓存
	 * @param hospitalCode
	 * @param branchCode
	 * @param regInfos
	 * @return
	 */
	public int setRegSourceToCache(String areaCode, String hospitalId, String hospitalName, String hospitalCode, String branchCode,
			String regType, List<RegInfo> regInfos) throws Exception {
		if (CollectionUtils.isEmpty(regInfos)) {
			return 0;
		}

		String regSourceCacheKey = getRegSourceCacheKey(hospitalCode, branchCode, CacheConstant.REG_SOURCE);
		//用于区域范围内搜索医生
		String areaDoctorNameCacheKey = getAreaDoctorNameCacheKey(areaCode);

		Map<String, String> doctorNameJsonDatas = new HashMap<String, String>();

		List<Dept> levOneDepts = null;
		List<Dept> levTwoDepts = null;

		RuleRegister ruleRegister = null;
		RuleHisData ruleHisData = null;

		//key为deptcode:yyyyMMdd  value为科室下医生的号源信息列表
		Map<String, List<Doctor>> doctorMap = new ConcurrentHashMap<String, List<Doctor>>();
		String dateStr = null;
		if (!CollectionUtils.isEmpty(regInfos)) {

			List<RuleRegister> ruleRegisters = hospitalRuleCache.queryRuleRegisterByHospitalCode(hospitalCode);
			List<RuleHisData> ruleHisDatas = hospitalRuleCache.queryRuleHisDataByHospitalCode(hospitalCode);

			if (!CollectionUtils.isEmpty(ruleRegisters)) {
				ruleRegister = ruleRegisters.get(0);
			}

			Integer isSameDoctorData = null;
			if (!CollectionUtils.isEmpty(ruleHisDatas)) {
				ruleHisData = ruleHisDatas.get(0);
			}

			if (ruleHisData != null) {
				isSameDoctorData = ruleHisData.getIsSameDoctorData();
			}
			if (isSameDoctorData != null && isSameDoctorData.intValue() == RuleConstant.IS_SAME_DOCTOR_DATA_YES) {
				regType = "";
			}

			//查询所有已经缓存过以部门code规则为Field的号源数据
			Set<String> hadCacheRegFields = redisSvc.hkeys(regSourceCacheKey);
			if (hadCacheRegFields == null) {
				hadCacheRegFields = new HashSet<String>();
			}

			String now = BizConstant.YYYYMMDD.format(new Date());

			for (RegInfo regInfo : regInfos) {
				dateStr = regInfo.getScheduleDate();

				//判断是否是当天号源
				boolean isCurReg = false;
				if (dateStr == null) {
					continue;
				}
				if (dateStr.equalsIgnoreCase(now)) {
					isCurReg = true;
				}

				List<Doctor> doctors = regInfo.getDoctors();
				if (!CollectionUtils.isEmpty(doctors)) {
					for (Doctor doctor : doctors) {
						/** redis hash结构中的 fieldname ***/
						String key = getFieldName(doctor.getDeptCode(), dateStr);

						/** 去除本次查询已经查询到的field ***/
						if (hadCacheRegFields.contains(key)) {
							hadCacheRegFields.remove(key);
						}

						/** redis hash结构中的deptCode:dateStr 对应的value 科室下所有号源信息列表  **/
						List<Doctor> deptDoctorRegInfos = doctorMap.get(key);
						boolean isNeedPut = false;
						if (deptDoctorRegInfos == null) {
							deptDoctorRegInfos = new ArrayList<Doctor>();
							isNeedPut = true;
						}
						deptDoctorRegInfos.add(doctor);
						if (isNeedPut) {
							doctorMap.put(key, deptDoctorRegInfos);
						}

						/** 缓存医生名称  用于各种搜索 ***/
						String mapKey = null;
						String doctorName = doctor.getDoctorName();
						String deptName = doctor.getDeptName();
						String doctorCode = doctor.getDoctorCode();
						String deptCode = doctor.getDeptCode();
						if (StringUtils.isBlank(deptName)) {

							if (ruleRegister.getIsHasGradeTwoDept() == RuleConstant.IS_HAS_TWO_DEPT_NO) {
								if (CollectionUtils.isEmpty(levOneDepts)) {
									levOneDepts = deptCache.getAppLevelOneDepts(hospitalCode, branchCode, regType);
								}

								if (!CollectionUtils.isEmpty(levOneDepts)) {
									for (Dept dept : levOneDepts) {
										if (StringUtils.equals(dept.getDeptCode(), deptCode)) {
											deptName = dept.getDeptName();
										}
									}
								}

							} else if (ruleRegister.getIsHasGradeTwoDept() == RuleConstant.IS_HAS_TWO_DEPT_YES) {
								if (CollectionUtils.isEmpty(levTwoDepts)) {
									levTwoDepts = deptCache.getAppLevelTwoDepts(hospitalCode, branchCode, regType);
								}

								if (!CollectionUtils.isEmpty(levTwoDepts)) {
									for (Dept dept : levTwoDepts) {
										if (StringUtils.equals(dept.getDeptCode(), deptCode)) {
											deptName = dept.getDeptName();
										}
									}
								}
							}
						}

						mapKey =
								doctorName + "#" + hospitalName + "-" + deptName + "#" + hospitalId + "#" + hospitalCode + "#" + branchCode
										+ "#" + ( ( isCurReg ) ? RegisterConstant.REG_TYPE_CUR : RegisterConstant.REG_TYPE_APPOINTMENT )
										+ "#" + doctorCode + "#" + deptCode;

						doctorNameJsonDatas.put(mapKey, JSON.toJSONString(doctor));
					}
				}
			}

			//缓存号源
			Map<String, String> jsonDoctorMap = new ConcurrentHashMap<String, String>();
			if (!CollectionUtils.isEmpty(doctorMap)) {
				for (String key : doctorMap.keySet()) {
					List<Doctor> doctors = doctorMap.get(key);
					if (!CollectionUtils.isEmpty(doctors)) {
						jsonDoctorMap.put(key, JSON.toJSONString(doctorMap.get(key)));
					}
				}
				redisSvc.hmset(regSourceCacheKey, jsonDoctorMap);
			}

			//缓存用于搜索的医生缓存
			if (!CollectionUtils.isEmpty(doctorNameJsonDatas)) {
				redisSvc.hmset(areaDoctorNameCacheKey, doctorNameJsonDatas);
			}

			//hadCacheFields中剩余的Field 为无效的号源缓存信息 删除
			if (hadCacheRegFields.size() > 0) {
				redisSvc.hdel(regSourceCacheKey, hadCacheRegFields.toArray(new String[hadCacheRegFields.size()]));
			}

			levOneDepts = null;
			levTwoDepts = null;
			return jsonDoctorMap.size();
		}
		return 0;
	}

	/**
	 * 得到某一天某一科室的所有医生号源信息
	 * @param hospitalCode
	 * @param branchCode
	 * @param deptCode
	 * @param dateStr yyyy-MM-dd
	 * @return
	 */
	public List<Doctor> getRegSourceFormCache(String hospitalCode, String branchCode, String deptCode, String dateStr) {
		String cacheKey = getRegSourceCacheKey(hospitalCode.trim(), branchCode.trim(), CacheConstant.REG_SOURCE);
		String fieldName = getFieldName(deptCode.trim(), dateStr.trim());
		String doctorsJson = redisSvc.hget(cacheKey, fieldName);
		if (StringUtils.isEmpty(doctorsJson) || CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(doctorsJson)) {
			return new ArrayList<Doctor>();
		} else {
			return JSON.parseArray(doctorsJson, Doctor.class);
		}
	}

	/**
	 * 获取分院下所有号源信息
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @return
	 */
	public List<RegInfo> getHospitalAllRegSourceFromCache(String hospitalCode, String branchHospitalCode) {
		String cacheKey = getRegSourceCacheKey(hospitalCode.trim(), branchHospitalCode.trim(), CacheConstant.REG_SOURCE);
		Map<String, String> regSources = redisSvc.hgetAll(cacheKey);
		if (CollectionUtils.isEmpty(regSources)) {
			return (List<RegInfo>) Collections.EMPTY_LIST;
		} else {
			List<RegInfo> regInfos = new ArrayList<RegInfo>();

			// key为yyyymmdd	    value为日期下的号源信息
			Map<String, List<Doctor>> doctorMap = new HashMap<String, List<Doctor>>();
			List<Doctor> doctors = null;
			for (Entry<String, String> regSource : regSources.entrySet()) {
				String key = regSource.getKey();
				String doctorsJson = regSource.getValue();
				List<Doctor> doctorTemps = JSON.parseArray(doctorsJson, Doctor.class);

				String scheduleDate = key.split(CacheConstant.CACHE_KEY_SPLIT_CHAR)[1];
				doctors = doctorMap.get(scheduleDate);
				if (CollectionUtils.isEmpty(doctors)) {
					RegInfo regInfo = new RegInfo();
					regInfo.setScheduleDate(scheduleDate);
					regInfos.add(regInfo);

					doctors = new ArrayList<Doctor>();
					regInfo.setDoctors(doctors);
					doctorMap.put(scheduleDate, doctors);
				}

				doctors.addAll(doctorTemps);
			}

			return regInfos;
		}
	}

	/**
	 * 获取存储号源 hash结构中的cacheKey 前缀:医院code:分院code
	 * @param hospitalCode       医院code
	 * @param branchCode  分院code
	 * @return
	 */
	/*	private String getRegSourceCacheKey(String hospitalCode, String branchCode) {
			String cacheKey = CacheConstant.CACHE_REGISTER_SOURCE.concat(hospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(branchCode);
			return cacheKey;
		}*/
	/**
	 * 获取存储号源 hash结构中的cacheKey 前缀:医院code:分院code
	 * @param hospitalCode       医院code
	 * @param childHospitalCode  分院code
	 * @param dataType           数据类型   号源/分时号源  APPOINTMENT/APPOINTMENT_TIME
	 * @return
	 */
	private String getRegSourceCacheKey(String hospitalCode, String childHospitalCode, String dataType) {
		if (StringUtils.isEmpty(childHospitalCode)) {
			childHospitalCode = CacheConstant.CACHE_NULL_STRING;
		}

		String cacheKey = null;
		if (CacheConstant.REG_SOURCE.equalsIgnoreCase(dataType)) {
			cacheKey = CacheConstant.CACHE_REGISTER_REG_SOURCE;
		} else {
			cacheKey = CacheConstant.CACHE_REGISTER_REG_TIME_SOURCE;
		}
		return cacheKey + hospitalCode + CacheConstant.CACHE_KEY_SPLIT_CHAR + childHospitalCode;
	}

	/**
	 * @param deptCode
	 * @param dateStr     yyyy-MM-dd
	 * @return
	 */
	private String getFieldName(String deptCode, String dateStr) {
		return deptCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(dateStr);
	}

	public String getAreaDoctorNameCacheKey(String areaCode) {
		String areaDoctorNameCacheKey = CacheConstant.CACHE_APP_DOCTOR_NAME_HASH_PREFIX;
		areaDoctorNameCacheKey = areaDoctorNameCacheKey + areaCode;
		return areaDoctorNameCacheKey;
	}

	public String getHospitalDoctorNameCacheKey(String hospitalCode, String childHospitalCode) {
		return CacheConstant.CACHE_HOSPITAL_DOCTOR_NAME_HASH_PREFIX.concat(hospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
				.concat(childHospitalCode);
	}

	/**
	 * 搜索医院医生
	 * @param searchName
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @return
	 */
	/*
	public List<String> getMatchHospitalDoctors(String searchName, String hospitalCode, String branchHospitalCode) {
	String cacheKey = getHospitalDoctorNameCacheKey(hospitalCode, branchHospitalCode);
	Set<String> cacheFields = redisSvc.hkeys(cacheKey);
	List<String> matchFields = new ArrayList<String>();
	if (!CollectionUtils.isEmpty(cacheFields)) {
		for (String cacheField : cacheFields) {
			if (cacheField.indexOf(searchName) > -1) {
				matchFields.add(cacheField);
			}
		}
	}
	return matchFields;
	}
	*/
	/**
	 * 搜索区域医生
	 * @param searchName
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param areaCode
	 * @return
	 */
	public List<String> getMatchAreaDoctors(String searchName, /*String hospitalCode, String branchHospitalCode,*/String areaCode) {
		String cacheKey = getAreaDoctorNameCacheKey(areaCode).concat(CacheConstant.CACHE_KEY_PATTERN_CHAR);
		List<String> matchFields = new ArrayList<String>();
		/*Set<String> cacheFields = redisSvc.hkeys(cacheKey);
		List<String> matchFields = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(cacheFields)) {
			for (String cacheField : cacheFields) {
				if (cacheField.indexOf(searchName) > -1) {
					matchFields.add(cacheField);
				}
			}
		}*/
		Set<String> keys = redisSvc.keys(cacheKey);
		if (!CollectionUtils.isEmpty(keys)) {
			for (String key : keys) {
				Set<String> cacheFields = redisSvc.hkeys(key);
				if (!CollectionUtils.isEmpty(cacheFields)) {
					for (String cacheField : cacheFields) {
						if (cacheField.indexOf(searchName) > -1) {
							matchFields.add(cacheField);
						}
					}
				}
			}
		}
		return matchFields;
	}

	/**
	 * 根据医生缓存的field查找区域内的医生
	 * @param doctorNameKey
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param areaCode
	 * @return
	 */
	public List<Doctor> getAreaDoctorByField(String doctorNameKey, String hospitalCode, String branchHospitalCode, String areaCode) {
		List<Doctor> result = null;

		String cacheKey = getAreaDoctorNameCacheKey(areaCode).concat(CacheConstant.CACHE_KEY_PATTERN_CHAR);
		Set<String> keys = redisSvc.keys(cacheKey);
		if (!CollectionUtils.isEmpty(keys)) {
			for (String key : keys) {
				String jsonVal = redisSvc.hget(key, doctorNameKey);
				Doctor doctor = null;
				if (StringUtils.isNotBlank(jsonVal) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonVal)) {
					doctor = JSON.parseObject(jsonVal, Doctor.class);
					result = new ArrayList<Doctor>(1);
					result.add(doctor);
				}
			}

			return result == null ? new ArrayList<Doctor>(0) : result;
		}

		return result;
	}

	/**
	 * 根据医生名的缓存field,获取医院内的医生信息
	 * @param deptNameStr
	 * @param areaCode
	 * @return
	 */
	public List<Doctor> getHospitalDoctorByField(String doctorNameKey, String hospitalCode, String branchHospitalCode) {
		List<Doctor> result = null;
		String cacheKey = getHospitalDoctorNameCacheKey(hospitalCode, branchHospitalCode);
		String jsonVal = redisSvc.hget(cacheKey, doctorNameKey);
		Doctor doctor = null;
		if (StringUtils.isNotBlank(jsonVal) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonVal)) {
			doctor = JSON.parseObject(jsonVal, Doctor.class);
			result = new ArrayList<Doctor>(1);
			result.add(doctor);
		}
		return result == null ? new ArrayList<Doctor>(0) : result;
	}
}
