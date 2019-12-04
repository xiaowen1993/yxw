/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-4</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.cache.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.mobile.biz.register.HadRegDoctor;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.cache.component
 * @ClassName: EverHadRegDoctorCache
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-4
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HadRegDoctorCache {
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);
//	private HadRegDoctorService hadRegDoctorService = SpringContextHolder.getBean(HadRegDoctorService.class);

	/**
	 * 保存增挂号医生信息到缓存
	 * 
	 * 已经拆分为获取和写入两个方法，由调用方自己控制逻辑
	 * 
	 * @category dam it
	 * @param doctor
	 */
//	public Map<String, Object> setDoctorToCache(HadRegDoctor doctor) {
//		Map<String, Object> resMap = new HashMap<String, Object>();
//		String hospitalCode = doctor.getHospitalCode();
//		String branchHospitalCode = doctor.getBranchHospitalCode();
//		String cacheKey = getCacheKey(hospitalCode, branchHospitalCode);
//		String fieldName = doctor.getOpenId();
//		Jedis jedis = redisSvc.getRedisClient();
//		boolean isAdd = true;
//		if (jedis != null) {
//			boolean broken = false;
//			try {
//				String doctorsJson = jedis.hget(cacheKey, fieldName);
//				List<HadRegDoctor> doctors = new ArrayList<HadRegDoctor>();
//				if (StringUtils.isNotBlank(doctorsJson) && !doctorsJson.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
//					doctors.addAll(JSON.parseArray(doctorsJson, HadRegDoctor.class));
//				}
//				for (HadRegDoctor d : doctors) {
//					if (d.getDoctorCode().equals(doctor.getDoctorCode())) {
//						isAdd = false;
//						break;
//					}
//				}
//				resMap.put("isAdd", isAdd);
//				if (isAdd) {
//					doctors.add(doctor);
//				}
//				if (doctors.size() > 3) {
//					HadRegDoctor removeDoctor = doctors.remove(0);
//					resMap.put("removeDoctor", removeDoctor);
//				}
//				jedis.hset(cacheKey, fieldName, JSON.toJSONString(doctors));
//
//			} catch (Exception e) {
//				CacheConstant.logger.error(e.getMessage(), e);
//				broken = true;
//			} finally {
//				redisSvc.returnResource(jedis, broken);
//			}
//		}
//		return resMap;
//	}
	
	/**
	 * 保存增挂号医生信息到缓存
	 * @param doctors 曾挂号医生列表
	 * @return
	 */
	public int setDoctorToCache(List<HadRegDoctor> doctors) {
		HadRegDoctor doctor = doctors.get(0);
		String hospitalCode = doctor.getHospitalCode();
		String branchHospitalCode = doctor.getBranchHospitalCode();
		String cacheKey = getCacheKey(hospitalCode, branchHospitalCode);
		String fieldName = doctor.getOpenId();
		return redisSvc.hset(cacheKey, fieldName, JSON.toJSONString(doctors)).intValue();
	}

	/**
	 * 根据医院信息及openId获取层挂号医生
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param openId
	 * @return
	 */
	public List<HadRegDoctor> getDoctorFromCache(String hospitalCode, String branchHospitalCode, String openId) {
		List<HadRegDoctor> result = null;
		String cacheKey = getCacheKey(hospitalCode, branchHospitalCode);
		String fieldName = openId;
		String doctorsJson = redisSvc.hget(cacheKey, fieldName);
		if (StringUtils.isNotBlank(doctorsJson) && !doctorsJson.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
			result = JSON.parseArray(doctorsJson, HadRegDoctor.class);
		}
		
		return result == null ? new ArrayList<HadRegDoctor>(0) : result;
	}
	
	/**
	 * 获取openId曾挂号的医生
	 * @param hospitalCode
	 * @param BranchHospitalCode
	 * @param openId
	 * @return
	 */
	public List<HadRegDoctor> getHadRegDoctorsByOpenId(String openId) {
		List<HadRegDoctor> doctors = new ArrayList<HadRegDoctor>();

		return doctors;
	}

	/**
	 * 获取openId曾挂号的医生
	 * @param hospitalCode
	 * @param BranchHospitalCode
	 * @param openId
	 * @return
	 */
	public List<HadRegDoctor> getHadRegDoctorsByOpenId(String openId, String hospitalCode, String branchCode) {
		List<HadRegDoctor> doctors = new ArrayList<HadRegDoctor>();
		if (StringUtils.isNotBlank(hospitalCode) && StringUtils.isNotBlank(branchCode) && StringUtils.isNotBlank(openId)) {
			String cacheKey = CacheConstant.CACHE_HAD_REG_DOCTOR.concat(hospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(branchCode);
			String doctorsJson = redisSvc.hget(cacheKey, openId);
			if (StringUtils.isNotBlank(doctorsJson) && !doctorsJson.equalsIgnoreCase(CacheConstant.CACHE_KEY_NOT_EXIST)) {
				doctors.addAll(JSON.parseArray(doctorsJson, HadRegDoctor.class));
			}
		}
		return doctors;
	}

//	public void initCache() {
//		List<HadRegDoctor> doctors = hadRegDoctorService.findAll();
//		if (!CollectionUtils.isEmpty(doctors)) {
//			Map<String, Map<String, List<HadRegDoctor>>> allDoctorMap = new HashMap<String, Map<String, List<HadRegDoctor>>>();
//			for (HadRegDoctor doctor : doctors) {
//				boolean isNeedPutMap = false;
//				boolean isNeedPutList = false;
//				String cacheKey = getCacheKey(doctor);
//				String fieldName = doctor.getOpenId().trim();
//
//				Map<String, List<HadRegDoctor>> openIdDoctorMap = allDoctorMap.get(cacheKey);
//				if (openIdDoctorMap == null) {
//					openIdDoctorMap = new HashMap<String, List<HadRegDoctor>>();
//					isNeedPutMap = true;
//				}
//				if (StringUtils.isBlank(doctor.getOpenId())) {
//					continue;
//				}
//
//				List<HadRegDoctor> doctorList = openIdDoctorMap.get(fieldName);
//				if (doctorList == null) {
//					doctorList = new ArrayList<HadRegDoctor>();
//					isNeedPutList = true;
//				}
//				doctorList.add(doctor);
//				if (isNeedPutList) {
//					openIdDoctorMap.put(fieldName, doctorList);
//				}
//				if (isNeedPutMap) {
//					allDoctorMap.put(cacheKey, openIdDoctorMap);
//				}
//			}
//
//			if (!allDoctorMap.isEmpty()) {
//				Map<String, Map<String, String>> allCardJsonMap = new HashMap<String, Map<String, String>>();
//				Map<String, String> openIdCardJsonMap = new HashMap<String, String>();
//				for (String cacheKey : allDoctorMap.keySet()) {
//					Map<String, List<HadRegDoctor>> openIdCardMap = allDoctorMap.get(cacheKey);
//					openIdCardJsonMap = new HashMap<String, String>();
//					for (String fieldName : openIdCardMap.keySet()) {
//						openIdCardJsonMap.put(fieldName, JSON.toJSONString(openIdCardMap.get(fieldName)));
//					}
//					allCardJsonMap.put(cacheKey, openIdCardJsonMap);
//				}
//
//				redisSvc.pipelineDatas(allCardJsonMap);
//			}
//			CacheConstant.logger.info("init MedicalCard Cache end......................");
//		}
//	}

	/**
	 * 批量保存曾挂好医生到缓存
	 * @param doctors
	 * @return
	 */
	public int setDoctorsToCache(List<HadRegDoctor> doctors) {
		if (!CollectionUtils.isEmpty(doctors)) {
			Map<String, Map<String, List<HadRegDoctor>>> allDoctorMap = new HashMap<String, Map<String, List<HadRegDoctor>>>();
			for (HadRegDoctor doctor : doctors) {
				boolean isNeedPutMap = false;
				boolean isNeedPutList = false;
				String hospitalCode = doctor.getHospitalCode();
				String branchHospitalCode = doctor.getBranchHospitalCode();
				String cacheKey = getCacheKey(hospitalCode, branchHospitalCode);
				String fieldName = doctor.getOpenId().trim();

				Map<String, List<HadRegDoctor>> openIdDoctorMap = allDoctorMap.get(cacheKey);
				if (openIdDoctorMap == null) {
					openIdDoctorMap = new HashMap<String, List<HadRegDoctor>>();
					isNeedPutMap = true;
				}
				if (StringUtils.isBlank(doctor.getOpenId())) {
					continue;
				}

				List<HadRegDoctor> doctorList = openIdDoctorMap.get(fieldName);
				if (doctorList == null) {
					doctorList = new ArrayList<HadRegDoctor>();
					isNeedPutList = true;
				}
				doctorList.add(doctor);
				if (isNeedPutList) {
					openIdDoctorMap.put(fieldName, doctorList);
				}
				if (isNeedPutMap) {
					allDoctorMap.put(cacheKey, openIdDoctorMap);
				}
			}

			if (!allDoctorMap.isEmpty()) {
				Map<String, Map<String, String>> allCardJsonMap = new HashMap<String, Map<String, String>>();
				Map<String, String> openIdCardJsonMap = new HashMap<String, String>();
				for (String cacheKey : allDoctorMap.keySet()) {
					Map<String, List<HadRegDoctor>> openIdCardMap = allDoctorMap.get(cacheKey);
					openIdCardJsonMap = new HashMap<String, String>();
					for (String fieldName : openIdCardMap.keySet()) {
						openIdCardJsonMap.put(fieldName, JSON.toJSONString(openIdCardMap.get(fieldName)));
					}
					allCardJsonMap.put(cacheKey, openIdCardJsonMap);
				}

				redisSvc.pipelineDatas(allCardJsonMap);
				
				return doctors.size();
			}
			CacheConstant.logger.info("init MedicalCard Cache end......................");
		}
		
		return 0;
	}
	
	private String getCacheKey(String hospitalCode, String branchHospitalCode) {
		return CacheConstant.CACHE_HAD_REG_DOCTOR.concat(hospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
				.concat(branchHospitalCode);
	}
}
