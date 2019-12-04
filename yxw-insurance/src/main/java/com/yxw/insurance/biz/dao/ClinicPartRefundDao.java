package com.yxw.insurance.biz.dao;

import com.yxw.commons.entity.mobile.biz.clinic.ClinicPartRefundRecord;
import com.yxw.framework.mvc.dao.BaseDao;

public interface ClinicPartRefundDao extends BaseDao<ClinicPartRefundRecord, String> {

	Integer countTotalRefundFeeByRefOrderNo(String orderNo, String hospitalCode);
}
