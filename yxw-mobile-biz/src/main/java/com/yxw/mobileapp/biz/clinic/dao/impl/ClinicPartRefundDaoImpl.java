package com.yxw.mobileapp.biz.clinic.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.mobile.biz.clinic.ClinicPartRefundRecord;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.mobileapp.biz.clinic.dao.ClinicPartRefundDao;

@Repository
public class ClinicPartRefundDaoImpl extends BaseDaoImpl<ClinicPartRefundRecord, String> implements ClinicPartRefundDao {
	private Logger logger = LoggerFactory.getLogger(ClinicPartRefundDaoImpl.class);

	private final static String SQLNAME_COUNT_TOTAL_REFUND_FEE_BY_REF_ORDER_NO = "countTotalRefundFeeByRefOrderNo";

	private final static String SQLNAME_FIND_BY_REFUND_ORDER_NO = "findByRefundOrderNo";

	private final static String SQLNAME_FIND_BY_HOSPITAL_CODE_AND_HIS_ORD_NO = "findByHospitalCodeAndHisOrdNo";

	@Override
	public Integer countTotalRefundFeeByRefOrderNo(String orderNo, String hospitalCode) {
		Integer hadRefundFee = 0;
		try {
			Assert.notNull(orderNo);
			Assert.notNull(hospitalCode);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orderNo", orderNo);
			paramMap.put("hospitalCode", hospitalCode);
			String refundFee = sqlSession.selectOne(getSqlName(SQLNAME_COUNT_TOTAL_REFUND_FEE_BY_REF_ORDER_NO), paramMap);
			if (StringUtils.isNotBlank(refundFee)) {
				hadRefundFee = Integer.valueOf(refundFee);
			}
		} catch (Exception e) {
			logger.error(String.format("通过orderNo和hospitalCode统计部分退费出错！语句：%s", getSqlName(SQLNAME_COUNT_TOTAL_REFUND_FEE_BY_REF_ORDER_NO)), e);
			throw new SystemException(String.format("通过orderNo和hospitalCode统计部分退费出错！语句：%s",
					getSqlName(SQLNAME_COUNT_TOTAL_REFUND_FEE_BY_REF_ORDER_NO)), e);
		}
		return hadRefundFee;
	}

	@Override
	public ClinicPartRefundRecord findByRefundOrderNo(String refundOrderNo) {
		ClinicPartRefundRecord record = null;
		try {
			Assert.notNull(refundOrderNo);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orderNo", refundOrderNo);
			record = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_REFUND_ORDER_NO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("通过refundOrderNo查找退费订单出错！语句：%s", getSqlName(SQLNAME_FIND_BY_REFUND_ORDER_NO)), e);
			throw new SystemException(String.format("通过refundOrderNo查找退费订单出错！语句：%s", getSqlName(SQLNAME_FIND_BY_REFUND_ORDER_NO)), e);
		}

		return record;
	}

	@Override
	public ClinicPartRefundRecord findByHospitalCodeAndHisOrdNo(String hospitalCode, String hisOrdNo, String orderNo) {
		ClinicPartRefundRecord record = null;
		try {
			Assert.notNull(hospitalCode);
			Assert.notNull(hisOrdNo);
			Assert.notNull(orderNo);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("hospitalCode", hospitalCode);
			paramMap.put("hisOrdNo", hisOrdNo);
			paramMap.put("orderNo", orderNo);
			List<ClinicPartRefundRecord> list = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_HOSPITAL_CODE_AND_HIS_ORD_NO), paramMap);
			if (list.size() > 0) {
				record = list.get(0);
			}
		} catch (Exception e) {
			logger.error(String.format("通过hospitalCode和HisOrdNo查找退费订单出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_CODE_AND_HIS_ORD_NO)), e);
			throw new SystemException(
					String.format("通过hospitalCode和HisOrdNo查找退费订单出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_CODE_AND_HIS_ORD_NO)), e);
		}

		return record;
	}
}
