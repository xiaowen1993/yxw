package com.yxw.insurance.biz.dao;

import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.framework.mvc.dao.BaseDao;

public interface ClinicRecordDao extends BaseDao<ClinicRecord, String> {

	/**
	 * 根据就订单号，查询订单
	 * @param record
	 * @return
	 */
	public ClinicRecord findByOrderNo(ClinicRecord record);
}
