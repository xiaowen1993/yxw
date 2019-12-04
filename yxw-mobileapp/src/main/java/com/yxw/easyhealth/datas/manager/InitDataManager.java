package com.yxw.easyhealth.datas.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.vo.cache.SimpleMedicalCard;
import com.yxw.commons.vo.cache.WhiteListVo;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.commons.vo.platform.register.SimpleRecord;
import com.yxw.easyhealth.common.service.CommonCardService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;
import com.yxw.mobileapp.biz.register.service.RegisterService;

public class InitDataManager {

	private static boolean isHasInit = false;
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * 初始化 挂号订单 信息
	 */
	public void initRegisterRecordCache() {

		CacheConstant.logger.info("init registerRecord Cache start......................");
		if (!isHasInit) {
			//预约中
			RegisterService recordSvc = SpringContextHolder.getBean(RegisterService.class);
			List<SimpleRecord> records = recordSvc.findNotPayRecord();
			if (!CollectionUtils.isEmpty(records)) {
				Map<String, Map<String, String>> allDatas = new HashMap<String, Map<String, String>>();
				Map<String, String> hospitalData = null;
				for (SimpleRecord record : records) {
					if (record.getPayTimeoutTime() == null) {
						continue;
					}

					String cacheKey = null;
					if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAVING) {
						cacheKey = getRegisterRecordCacheKey(CacheConstant.CACHE_REGISTER_HAVING_HASH, record.getHospitalCode());
					} else if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAD) {
						cacheKey = getRegisterRecordCacheKey(CacheConstant.CACHE_REGISTER_NOT_PAY_HAD_HASH, record.getHospitalCode());
					} else {
						continue;
					}
					hospitalData = allDatas.get(cacheKey);
					boolean isNeedPut = false;
					if (hospitalData == null) {
						hospitalData = new HashMap<String, String>();
						isNeedPut = true;
					}

					String fieldName = getHashFieldName(record);
					hospitalData.put(fieldName, JSON.toJSONString(record));
					if (isNeedPut) {
						allDatas.put(cacheKey, hospitalData);
					}
				}

				if (!CollectionUtils.isEmpty(allDatas)) {
					List<Object> parameters = new ArrayList<Object>();
					parameters.add(allDatas);
					serveComm.pipeline(CacheType.COMMON_CACHE, "pipelineDatas", parameters);
				}
			}
		}
		CacheConstant.logger.info("init registerRecord Cache end......................");
	}

	/**
	 * 初始化 挂号的异常订单 信息
	 */
	public void initRegisterExceptionCache() {
		CacheConstant.logger.info("init registerExceptionRecord Cache start......................");
		if (!isHasInit) {
			RegisterService recordSvc = SpringContextHolder.getBean(RegisterService.class);
			List<RegisterRecord> regRecords = recordSvc.findAllNeedHandleExceptionRecord();

			Map<String, Double> exRecordsMap = new HashMap<String, Double>(regRecords.size());
			for (RegisterRecord record : regRecords) {
				ExceptionRecord exRecord = record.convertExceptionObj();
				exRecordsMap.put(JSON.toJSONString(exRecord), (double) exRecord.getNextFireTime());
			}

			String cacheKey = CacheConstant.CACHE_REGISTER_EXCEPTION_SORTEDSET;
			//删除缓存中的异常队列
			if (!exRecordsMap.isEmpty()) {
				List<Object> parameters = new ArrayList<Object>();
				parameters.add(cacheKey);
				serveComm.delete(CacheType.COMMON_CACHE, "delCache", parameters);

				parameters.clear();
				parameters.add(cacheKey);
				parameters.add(exRecordsMap);
				serveComm.set(CacheType.COMMON_CACHE, "zaddCache", parameters);
			}

		}
		CacheConstant.logger.info("init registerExceptionRecord Cache end......................");
	}

	/**
	 * 初始化 门诊的异常订单 信息
	 */
	public void initClinicExceptionCache() {
		if (!isHasInit) {
			CacheConstant.logger.info("init clinicExceptionRecord Cache start......................");

			ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);
			List<ClinicRecord> clinicExceptionRecords = clinicService.findAllNeedHandleExceptionRecords();
			Map<String, Double> exRecordsMap = new HashMap<String, Double>(clinicExceptionRecords.size());
			for (ClinicRecord record : clinicExceptionRecords) {
				exRecordsMap.put(JSON.toJSONString(record), (double) record.getNextFireTime());
			}

			if (!CollectionUtils.isEmpty(exRecordsMap)) {
				List<Object> params = new ArrayList<Object>();
				params.add(exRecordsMap);
				serveComm.set(CacheType.CLINIC_EXCEPTION_CACHE, "setExceptionClinicsToCache", params);
			}

			CacheConstant.logger.info("init clinicExceptionRecord Cache end......................");
		}

	}

	/**
	 * 初始化 医院就诊卡 信息
	 */
	public void initMedicalCardCache() throws SystemException {

		if (!isHasInit) {
			CacheConstant.logger.info("init MedicalCard Cache start......................");
			CommonCardService cardSvc = SpringContextHolder.getBean(CommonCardService.class);
			List<SimpleMedicalCard> cards = cardSvc.findAllForCache();
			if (!CollectionUtils.isEmpty(cards)) {
				Map<String, Map<String, List<SimpleMedicalCard>>> allCardMap = new HashMap<String, Map<String, List<SimpleMedicalCard>>>();
				for (SimpleMedicalCard card : cards) {
					boolean isNeedPutMap = false;
					boolean isNeedPutList = false;
					String cacheKey = getMedicalCardCacheKey(card.getHospitalCode(), card.getBranchCode());
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

				//				redisSvc.pipelineDatas(allCardJsonMap);

				List<Object> parameters = new ArrayList<Object>();
				parameters.add(allCardJsonMap);
				serveComm.pipeline(CacheType.COMMON_CACHE, "pipelineDatas", parameters);

				CacheConstant.logger.info("init MedicalCard Cache end......................");
			}
		}
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

	public String getMedicalCardCacheKey(String hospitalCode, String branchHospitalCode) {
		String cacheKey = CacheConstant.CACHE_MEDICAL_CARD_HASH_PREFIX.concat(hospitalCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR);
		cacheKey = cacheKey.concat(branchHospitalCode);
		return cacheKey;
	}

	public String getOpenIdField(WhiteListVo vo) {
		return vo.getAppId().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(vo.getAppCode()).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
				.concat(vo.getOpenId());
	}

	public String getPhoneField(WhiteListVo vo) {
		return vo.getAppId().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(vo.getAppCode()).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
				.concat(vo.getPhone());
	}

	private String getHashFieldName(SimpleRecord record) {
		String fieldName = record.getPayTimeoutTime().toString().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(record.getOpenId());
		return fieldName;
	}

	/**
	 * 根据RegisterRecord 生成cacheKey   挂号的状态 的标志:医院的code
	 * @param type  CACHE_REGISTER_HAVING_HASH/CACHE_REGISTER_NEED_UNLOCK
	 * @param record
	 * @return
	 */
	public static String getRegisterRecordCacheKey(String type, String hospitalCode) {
		String cacheKey = type;
		if (StringUtils.isNotBlank(hospitalCode)) {
			cacheKey = cacheKey.concat(hospitalCode);
		}
		return cacheKey;
	}

	public void finishedInit() {
		isHasInit = true;
	}
}
