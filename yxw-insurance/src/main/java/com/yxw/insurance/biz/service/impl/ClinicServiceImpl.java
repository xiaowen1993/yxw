package com.yxw.insurance.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.insurance.biz.dao.ClinicRecordDao;
import com.yxw.insurance.biz.service.ClinicService;
import com.yxw.insurance.biz.service.InsuranceBusinessService;
import com.yxw.insurance.biz.thread.SaveAndUpdateClinicCacheRunnable;

@Service
public class ClinicServiceImpl implements ClinicService {

	private Logger logger = LoggerFactory.getLogger(ClinicServiceImpl.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	private ClinicRecordDao clinicRecordDao = SpringContextHolder.getBean(ClinicRecordDao.class);

	private InsuranceBusinessService insuranceBusinessService = SpringContextHolder.getBean(InsuranceBusinessService.class);

	@Override
	public ClinicRecord findRecordByOrderNo(String orderNo) {

		ClinicRecord record = null;

		List<Object> params = new ArrayList<Object>();
		params.add(orderNo);
		List<Object> results = serveComm.get(CacheType.CLINIC_RECORD_CACHE, "getRecordByOrderNo", params);

		try {
			if (CollectionUtils.isNotEmpty(results)) {
				record = (ClinicRecord) results.get(0);

				if (StringUtils.isBlank(record.getAppId())) {
					HospIdAndAppSecretVo hospIdAndAppSecretVo =
							insuranceBusinessService.getHospitalEasyHealthAppInfo(record.getHospitalId(), record.getAppCode());

					if (hospIdAndAppSecretVo != null) {
						record.setAppId(hospIdAndAppSecretVo.getAppId());
					}

				}
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("find record from cache error, make transfer error. orderNo={}, errorMsg={}, " + "cause by: {}.",
						new Object[] { orderNo, e.getMessage(), e.getCause() });
			}
		}

		// 缓存拿不到则从数据库拿
		if (record == null) {
			record = new ClinicRecord();
			record.setOrderNo(orderNo);
			record.setOrderNoHashVal(Math.abs(orderNo.hashCode()));
			record = clinicRecordDao.findByOrderNo(record);

			if (record != null) {
				// 设置appId, appCode
				HospIdAndAppSecretVo hospIdAndAppSecretVo =
						insuranceBusinessService.getHospitalEasyHealthAppInfo(record.getHospitalId(), record.getAppCode());
				if (hospIdAndAppSecretVo != null) {
					record.setAppId(hospIdAndAppSecretVo.getAppId());
				}

				logger.info("start a thread to save the record into cache where the record was not found from the cache first but database did...");
				Thread cacheThread =
						new Thread(new SaveAndUpdateClinicCacheRunnable(record, CacheConstant.CACHE_OP_ADD),
								"CacheRunnable.saveClinicRecord");
				cacheThread.start();
			}
		}

		return record;
	}

}
