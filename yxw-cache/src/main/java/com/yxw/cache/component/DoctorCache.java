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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.register.Doctor;
import com.yxw.interfaces.vo.register.RegDoctor;

/**
 * @Package: com.yxw.platform.cache
 * @ClassName: DoctorCache
 * @Statement: <p>
 *             医生信息缓存
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-14
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DoctorCache {
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	private HospitalRuleCache hospitalRuleCache = SpringContextHolder.getBean(HospitalRuleCache.class);

	/**
	 * 根据医院 分院 为key 所有医生列表json为value
	 */
	private static final String DATA_TYPE_NAME_HASH = "name_hash";

	/**
	 * 根据医院 分院为key  科室 为field  科室下的医生列表json为value  hash存储结构
	 */
	private static final String DATA_TYPE_DEPT_HASH = "dept_hash";

	public DoctorCache() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 得到医院的指定医生
	 * @param hospitalCode
	 * @param childhospitalCode
	 * @param doctorName
	 * @param doctorCode   为区分重名的医生
	 * @return
	 */
	public List<RegDoctor> getHospitalDoctor(String hospitalCode, String childhospitalCode, String doctorName, String doctorCode) {
		String cahceKey = getDoctorCacheKey();

		Set<String> Fields = redisSvc.hkeys(cahceKey);
		List<String> matchFields = new ArrayList<String>();
		String matchField = hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(childhospitalCode);
		if (!CollectionUtils.isEmpty(Fields)) {
			for (String field : Fields) {
				if (field.startsWith(matchField) && field.endsWith(doctorCode)) {
					matchFields.add(field);
				}
			}
		}

		List<RegDoctor> result = null;
		RegDoctor doctor = null;
		if (!CollectionUtils.isEmpty(matchFields)) {
			String[] matchFieldArray = new String[matchFields.size()];
			List<String> doctorJsons = redisSvc.hmget(cahceKey, matchFields.toArray(matchFieldArray));
			if (!CollectionUtils.isEmpty(doctorJsons)) {
				for (String doctorJson : doctorJsons) {
					if (StringUtils.isNotEmpty(doctorJson) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(doctorJson)
							&& doctorJson.indexOf(doctorName) > -1) {
						doctor = JSON.parseObject(doctorJson, RegDoctor.class);
						result = new ArrayList<RegDoctor>(1);
						result.add(doctor);
					}
				}
			}
		}

		return result == null ? new ArrayList<RegDoctor>(0) : result;

	}

	public int clearDoctors(String hospitalCode, String childhospitalCode) {
		String cahceKey = getDoctorCacheKey();
		return redisSvc.del(cahceKey) ? 1 : -1;
	}

	public List<String> getMatchDoctorFields(String hospitalCode, String childhospitalCode) {
		String cahceKey = getDoctorCacheKey();

		Set<String> Fields = redisSvc.hkeys(cahceKey);
		List<String> matchFields = new ArrayList<String>();
		String matchField = hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(childhospitalCode);
		if (!CollectionUtils.isEmpty(Fields)) {
			for (String field : Fields) {
				if (field.startsWith(matchField)) {
					matchFields.add(field);
				}
			}
		}
		return matchFields;
	}

	/**
	 * 得到医院分院下的所有医生
	 * 
	 * @param hospitalCode
	 * @param childhospitalCode
	 *            分院Code 没有分院 传入参数null eg: 查询医院code:a 分院 code:b getHospitalAllDoctors(a,b)
	 *            查询医院id:a没有分院 getHospitalAllDoctors(a,null)
	 * @return
	 */
	public List<RegDoctor> getHospitalAllDoctors(String hospitalCode, String childhospitalCode, String regType) {
		/*String cahceKey = getDoctorCacheKey();
		List<String> matchFields = getMatchDoctorFields(hospitalCode, childhospitalCode);
		List<RegDoctor> doctors = null;
		if (!CollectionUtils.isEmpty(matchFields)) {
			String[] fields = new String[matchFields.size()];
			List<String> doctorJsons = redisSvc.hmget(cahceKey, matchFields.toArray(fields));
			if (!CollectionUtils.isEmpty(doctorJsons)) {
				doctors = new ArrayList<RegDoctor>();
				for (String jsonVal : doctorJsons) {
					if (StringUtils.isNotEmpty(jsonVal) && !CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonVal)) {
						RegDoctor doctor = JSON.parseObject(jsonVal, RegDoctor.class);
						doctors.add(doctor);
					}
				}
			}
		}

		if (doctors == null) {
			doctors = new ArrayList<RegDoctor>();
		}

		return doctors;*/

		String cacheKey = getCacheKey(hospitalCode, childhospitalCode, regType, DATA_TYPE_NAME_HASH);
		List<String> doctorJsons = redisSvc.hvals(cacheKey);
		List<RegDoctor> doctors = new ArrayList<RegDoctor>();
		for (String jsonstr : doctorJsons) {
			doctors.add(JSON.parseObject(jsonstr, RegDoctor.class));
		}
		return doctors;

	}

	/**
	 * 搜索医院医生,只是匹配所有field
	 * @param searchName
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @return
	 */
	public List<RegDoctor> getMatchHospitalDoctors(String searchName, String hospitalCode, String branchHospitalCode, String regType) {
		List<RegDoctor> doctors = null;

		String cacheKey = getCacheKey(hospitalCode, branchHospitalCode, regType, DATA_TYPE_NAME_HASH);
		Set<String> cacheFields = redisSvc.hkeys(cacheKey);
		List<String> matchFields = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(cacheFields)) {
			for (String cacheField : cacheFields) {
				if (cacheField.indexOf(searchName) > -1) {
					matchFields.add(cacheField);
				}
			}
		}

		if (!CollectionUtils.isEmpty(matchFields)) {
			doctors = new ArrayList<RegDoctor>(matchFields.size());
			doctors = getMatchHospitalDoctorsForField(hospitalCode, branchHospitalCode, regType, matchFields.toArray(new String[matchFields.size()]));
		}

		return doctors;
		/*		List<RegDoctor> doctors = null;

				String cacheKey = getCacheKey(hospitalCode, branchHospitalCode, regType, DATA_TYPE_NAME_HASH);
				Set<String> cacheFieldss = redisSvc.hkeys(cacheKey);
				List<String> cacheFields = redisSvc.hvals(cacheKey);
				//		List<String> matchFields = new ArrayList<String>();
				if (!CollectionUtils.isEmpty(cacheFields)) {
					doctors = new ArrayList<RegDoctor>(cacheFields.size());
					for (String cacheField : cacheFields) {
						if (cacheField.indexOf(searchName) > -1) {
							//					matchFields.add(cacheField);
							doctors.add(JSON.parseObject(cacheField, RegDoctor.class));
						}
					}
				} else {
					doctors = new ArrayList<RegDoctor>(0);
				}

				//		return matchFields;
				return doctors;*/
	}

	/**
	 * 搜索医院医生,匹配具体某个field(用于补全信息的操作)
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @return
	 */
	public List<RegDoctor> getMatchHospitalDoctorsForField(String hospitalCode, String branchHospitalCode, String regType, String... fields) {
		List<RegDoctor> doctors = null;

		String cacheKey = getCacheKey(hospitalCode, branchHospitalCode, regType, DATA_TYPE_NAME_HASH);
		List<String> doctorsString = redisSvc.hmget(cacheKey, fields);
		if (!CollectionUtils.isEmpty(doctorsString)) {
			doctors = new ArrayList<RegDoctor>(doctorsString.size());
			for (String doctorString : doctorsString) {
				doctors.add(JSON.parseObject(doctorString, RegDoctor.class));
			}
		} else {
			doctors = new ArrayList<RegDoctor>(0);
		}

		return doctors;
	}

	/**
	 * 得到医院分院下的所有医生信息
	 * @param hospitalCode
	 * @param childhospitalCode
	 * @return
	 */
	//	public Map<String, RegDoctor> getHospitalAllDoctorsMap(String hospitalCode, String childhospitalCode) {
	//		String cahceKey = getDoctorCacheKey();
	//		List<String> matchFields = getMatchDoctorFields(hospitalCode, childhospitalCode);
	//
	//		Map<String, RegDoctor> doctorMap = new HashMap<String, RegDoctor>();
	//		if (!CollectionUtils.isEmpty(matchFields)) {
	//			String[] fields = new String[matchFields.size()];
	//			List<String> doctorJsons = redisSvc.hmget(cahceKey, matchFields.toArray(fields));
	//			String matchField = hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(childhospitalCode);
	//			String mapkey = null;
	//			for (String jsonVal : doctorJsons) {
	//				if (StringUtils.isNotEmpty(jsonVal) && CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(jsonVal)) {
	//					RegDoctor doctor = JSON.parseObject(jsonVal, RegDoctor.class);
	//					mapkey =
	//							matchField.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(doctor.getDeptCode())
	//									.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(doctor.getDoctorCode());
	//					doctorMap.put(mapkey, doctor);
	//				}
	//			}
	//		}
	//
	//		return doctorMap;
	//	}

	/**
	 * 根据医生code得到医生信息列表
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 *           
	 * @return
	 */
	public List<RegDoctor> getDoctors(String hospitalCode, String branchHospitalCode, String regType, String deptCode, String[] doctorCodes) {
		/*String cacheKey = getDoctorCacheKey();
		String[] fieds = new String[doctorCodes.length];
		for (int i = 0; i < doctorCodes.length; i++) {
			fieds[i] = getCacheFied(hospitalCode, branchHospitalCode, deptCode, doctorCodes[i]);
		}
		List<String> doctorJsons = redisSvc.hmget(cacheKey, fieds);
		List<RegDoctor> doctors = new ArrayList<RegDoctor>();
		RegDoctor doctor = null;
		for (String doctorJson : doctorJsons) {
			if (doctorJson == null) {
				continue;
			}
			doctor = JSON.parseObject(doctorJson, RegDoctor.class);
			doctors.add(doctor);
		}
		return doctors;*/

		String cacheKey = getCacheKey(hospitalCode.trim(), branchHospitalCode.trim(), regType, DATA_TYPE_DEPT_HASH);
		String[] fieds = new String[doctorCodes.length];
		for (int i = 0; i < doctorCodes.length; i++) {
			fieds[i] = getCacheFied(deptCode, doctorCodes[i]);
		}
		List<String> doctorJsons = redisSvc.hmget(cacheKey, fieds);
		List<RegDoctor> doctors = new ArrayList<RegDoctor>();
		RegDoctor doctor = null;
		for (String doctorJson : doctorJsons) {
			if (doctorJson == null) {
				continue;
			}
			doctor = JSON.parseObject(doctorJson, RegDoctor.class);
			doctors.add(doctor);
		}
		return doctors;

	}

	/**
	 * 根据医生code得到医生信息列表
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 *           
	 * @return
	 */
	public List<RegDoctor> getDoctors(String hospitalCode, String branchHospitalCode, String deptCode) {
		String cacheKey = getDoctorCacheKey();
		Set<String> cacheDoctorFields = redisSvc.hkeys(cacheKey);
		String matchField =
				hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(branchHospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
						.concat(deptCode);
		List<String> matchFields = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(cacheDoctorFields)) {
			for (String cacheField : cacheDoctorFields) {
				if (cacheField.startsWith(matchField)) {
					matchFields.add(cacheField);
				}
			}
		}

		List<RegDoctor> doctors = new ArrayList<RegDoctor>();
		if (CollectionUtils.isEmpty(matchFields)) {
			String[] queryFields = new String[matchFields.size()];
			List<String> doctorJsons = redisSvc.hmget(cacheKey, matchFields.toArray(queryFields));
			for (String doctorJson : doctorJsons) {
				if (doctorJson == null || StringUtils.isBlank(doctorJson) || CacheConstant.CACHE_KEY_NOT_EXIST.equalsIgnoreCase(doctorJson)) {
					continue;
				}
				RegDoctor doctor = JSON.parseObject(doctorJson, RegDoctor.class);
				doctors.add(doctor);
			}
		}
		return doctors;
	}

	/**
	 * 得到科室的指定医生code的医生
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 *           
	 * @return
	 */
	public List<RegDoctor> getDoctor(String hospitalCode, String branchHospitalCode, String deptCode, List doctorCodes) {
		/*		String cahceKey = getDoctorCacheKey();
				String fied = getCacheFied(hospitalCode, branchHospitalCode, deptCode, doctorCode);
				String doctorJson = redisSvc.hget(cahceKey, fied);

				List<RegDoctor> result = null;
				RegDoctor doctor = null;
				if (StringUtils.isNotBlank(doctorJson) && !doctorJson.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
					doctor = JSON.parseObject(doctorJson, RegDoctor.class);
					result = new ArrayList<RegDoctor>(1);
					result.add(doctor);
				}

				return result == null ? new ArrayList<RegDoctor>(0) : result;*/

		String[] fieds = new String[doctorCodes.size()];
		for (int i = 0; i < doctorCodes.size(); i++) {
			fieds[i] = getCacheFied(deptCode, (String) doctorCodes.get(i));
		}
		RegDoctor doctor = null;
		RuleHisData ruleHisData = null;
		List<RegDoctor> doctors = new ArrayList<RegDoctor>();

		List<RuleHisData> ruleHisDatas = hospitalRuleCache.queryRuleHisDataByHospitalCode(hospitalCode);

		Integer isSameDoctorData = null;
		if (!CollectionUtils.isEmpty(ruleHisDatas)) {
			ruleHisData = ruleHisDatas.get(0);
			isSameDoctorData = ruleHisData.getIsSameDoctorData();
		}
		if (isSameDoctorData != null && isSameDoctorData.intValue() == RuleConstant.IS_SAME_DOCTOR_DATA_NO) {
			String cacheCurKey =
					getCacheKey(hospitalCode.trim(), branchHospitalCode.trim(), String.valueOf(RegisterConstant.REG_TYPE_CUR), DATA_TYPE_DEPT_HASH);
			String cacheAppointKey =
					getCacheKey(hospitalCode.trim(), branchHospitalCode.trim(), String.valueOf(RegisterConstant.REG_TYPE_APPOINTMENT),
							DATA_TYPE_DEPT_HASH);
			List<String> doctorCurJsons = redisSvc.hmget(cacheCurKey, fieds);
			List<String> doctorAppointJsons = redisSvc.hmget(cacheAppointKey, fieds);
			for (String doctorJson : doctorCurJsons) {
				if (doctorJson == null) {
					continue;
				}
				doctor = JSON.parseObject(doctorJson, RegDoctor.class);
				doctors.add(doctor);
			}
			for (String doctorJson : doctorAppointJsons) {
				if (doctorJson == null) {
					continue;
				}
				doctor = JSON.parseObject(doctorJson, RegDoctor.class);
				doctors.add(doctor);
			}
		} else {
			String cacheKey = getCacheKey(hospitalCode.trim(), branchHospitalCode.trim(), null, DATA_TYPE_DEPT_HASH);
			List<String> doctorJsons = redisSvc.hmget(cacheKey, fieds);
			for (String doctorJson : doctorJsons) {
				if (doctorJson == null) {
					continue;
				}
				doctor = JSON.parseObject(doctorJson, RegDoctor.class);
				doctors.add(doctor);
			}
		}
		return doctors;

	}

	/**
	 * 医生信息存入缓存
	 * 
	 * 使用hash结构而不使用list结构  为避免搜索医生上查询list 需要将所有的医生数据获取转对象进行对比  为引起I/O紧张
	 * @param hospitalCode
	 * @param childhospitalCode
	 * @param doctors
	 */
	public int setDoctors(String hospitalCode, String branchHospitalCode, String regType, List<RegDoctor> doctors) {
		if (CollectionUtils.isEmpty(doctors)) {
			return 0;
		}

		//doctors放入doctorNameMap  key为医生的姓名  用于医生的搜索 (对于医生的搜索) 
		Map<String, String> doctorNameMap = new ConcurrentHashMap<String, String>();

		//医院 分院 科室 医生列表(为了查询速度  冗余医生数据) 
		//hash结构    key:    field-deptCode:doctorCode  value:医生doctorJson
		Map<String, String> doctorJsonMap = new ConcurrentHashMap<String, String>();
		try {
			for (RegDoctor doctor : doctors) {
				if (StringUtils.isEmpty(doctor.getDeptCode())) {
					continue;
				}

				String key = doctor.getDoctorName().trim().concat("(").concat(StringUtils.defaultString(doctor.getDeptName(), "")).concat(")");
				doctorNameMap.put(key, JSON.toJSONString(doctor));

				doctorJsonMap.put(getCacheFied(doctor.getDeptCode(), doctor.getDoctorCode()), JSON.toJSONString(doctor));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String cahceKey = getCacheKey(hospitalCode, branchHospitalCode, regType, DATA_TYPE_NAME_HASH);
		if (!CollectionUtils.isEmpty(doctorJsonMap)) {
			redisSvc.hmset(cahceKey, doctorNameMap);

			cahceKey = getCacheKey(hospitalCode, branchHospitalCode, regType, DATA_TYPE_DEPT_HASH);
			redisSvc.hmset(cahceKey, doctorJsonMap);

			return doctorJsonMap.size();
		}

		return 0;

		/*if (CollectionUtils.isEmpty(doctors)) {
			return 0;
		}

		//医院 分院 科室 医生列表 
		//hash结构    cahceKey:CACHE_DOCTOR_HASH_PREFIX    field:hospitalCode:childHospitalCode:deptCode  value:医生json
		ConcurrentHashMap<String, String> doctorJsonMap = new ConcurrentHashMap<String, String>();

		for (RegDoctor doctor : doctors) {
			if (StringUtils.isEmpty(doctor.getDeptCode())) {
				continue;
			}
			doctorJsonMap.put(getCacheFied(hospitalCode, childhospitalCode, doctor.getDeptCode(), doctor.getDoctorCode()), JSON.toJSONString(doctor));
		}

		String cahceKey = getDoctorCacheKey();

		if (!CollectionUtils.isEmpty(doctorJsonMap)) {
			redisSvc.hmset(cahceKey, doctorJsonMap);
			
			return doctorJsonMap.size();
		}
		
		return 0;*/
	}

	private String getCacheFied(String deptCode, String doctorCode) {
		return deptCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(doctorCode);
	}

	public String getDoctorCacheKey() {
		return CacheConstant.CACHE_DOCTOR_HASH_PREFIX;
	}

	/**
	 * 生成cache key
	 * 
	 * @param hospitalCode
	 * @param childhospitalCode
	 * @param dataType   list/hash
	 * @return
	 */
	private String getCacheKey(String hospitalCode, String branchHospitalCode, String regType, String dataType) {
		if (StringUtils.isEmpty(branchHospitalCode)) {
			branchHospitalCode = CacheConstant.CACHE_NULL_STRING;
		}
		String cahceKey = null;
		if (DATA_TYPE_NAME_HASH.equalsIgnoreCase(dataType)) {
			cahceKey = CacheConstant.CACHE_HOSPITAL_DOCTOR_NAME_HASH_PREFIX.concat(hospitalCode.trim()).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR);
			cahceKey = cahceKey.concat(branchHospitalCode.trim());
			if (StringUtils.isNotEmpty(regType)) {
				cahceKey = cahceKey.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(regType);
			}
		} else if (DATA_TYPE_DEPT_HASH.equalsIgnoreCase(dataType)) {
			cahceKey = CacheConstant.CACHE_DOCTOR_HASH_PREFIX + hospitalCode.trim() + CacheConstant.CACHE_KEY_SPLIT_CHAR + branchHospitalCode.trim();
		}
		return cahceKey;
	}

	public int setRegSourceDoctorInfoToCache(String hospitalCode, String branchHospitalCode, String deptCode, String regType, List<Doctor> doctors) {
		List<RegDoctor> regDoctors = new ArrayList<RegDoctor>();
		if (!CollectionUtils.isEmpty(doctors)) {
			for (Doctor doctor : doctors) {
				regDoctors.add(doctorConvertRegDoctor(doctor, branchHospitalCode));
			}
		}

		if (regDoctors.size() > 0) {
			return setDoctors(hospitalCode, branchHospitalCode, regType, regDoctors);
		}

		return 0;
	}

	/**
	 * 将号源中的医生信息,转化为RegDoctor信息
	 * @param doctor
	 * @return
	 */
	private RegDoctor doctorConvertRegDoctor(Doctor doctor, String branchCode) {
		RegDoctor regDoctor = new RegDoctor();
		regDoctor.setBranchCode(branchCode);
		regDoctor.setDeptCode(doctor.getDeptCode());
		regDoctor.setDeptName(doctor.getDeptName());
		regDoctor.setDoctorCode(doctor.getDoctorCode());
		regDoctor.setDoctorName(doctor.getDoctorName());
		regDoctor.setDoctorIntrodution(doctor.getDoctorIntrodution());
		regDoctor.setDoctorSkill(doctor.getDoctorSkill());
		regDoctor.setDoctorTitle(doctor.getDoctorTitle());
		regDoctor.setPicture(doctor.getPicture());
		return regDoctor;
	}
}
