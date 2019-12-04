package com.yxw.insurance.biz.thread;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

public class SaveAndUpdateClinicCacheRunnable implements Runnable {

	private Logger logger = LoggerFactory.getLogger(SaveAndUpdateClinicCacheRunnable.class);

	private String opType;

	private ClinicRecord record;

	// private ClinicRecordCache recordCache = SpringContextHolder.getBean(ClinicRecordCache.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	public SaveAndUpdateClinicCacheRunnable() {
		super();
	}

	public SaveAndUpdateClinicCacheRunnable(ClinicRecord record, String opType) {
		super();
		this.record = record;
		this.opType = opType;
	}

	@Override
	public void run() {
		logger.info("SaveAndUpdateClinicCacheRunnable.opType : {}", opType);
		List<Object> params = new ArrayList<Object>();
		params.add(record);
		if (CacheConstant.CACHE_OP_ADD.equals(opType) && record != null) {
			// recordCache.saveRecord(record);
			serveComm.set(CacheType.CLINIC_RECORD_CACHE, "saveRecord", params);
		} else if (CacheConstant.CACHE_OP_UPDATE.equals(opType) && record != null) {
			// recordCache.updateRecord(record);
			serveComm.set(CacheType.CLINIC_RECORD_CACHE, "updateRecord", params);
		} else if (CacheConstant.CACHE_OP_DEL.equals(opType) && record != null) {
			// recordCache.deleteRecord(record);
			serveComm.delete(CacheType.CLINIC_RECORD_CACHE, "deleteRecord", params);
		}
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public ClinicRecord getRecord() {
		return record;
	}

	public void setRecord(ClinicRecord record) {
		this.record = record;
	}

}
