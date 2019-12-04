package com.yxw.insurance.biz.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.mobile.biz.clinic.ClinicPartRefundRecord;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.insurance.biz.dao.ClinicPartRefundDao;

@Repository
public class ClinicPartRefundDaoImpl extends BaseDaoImpl<ClinicPartRefundRecord, String> implements ClinicPartRefundDao {
	private Logger logger = LoggerFactory.getLogger(ClinicPartRefundDaoImpl.class);

	private final static String SQLNAME_COUNT_TOTAL_REFUND_FEE_BY_REF_ORDER_NO = "countTotalRefundFeeByRefOrderNo";

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
			logger.error(String.format("通过orderNo和hospitalCode统计部分退费出错！语句：%s", getSqlName(SQLNAME_COUNT_TOTAL_REFUND_FEE_BY_REF_ORDER_NO)),
					e);
			throw new SystemException(String.format("通过orderNo和hospitalCode统计部分退费出错！语句：%s",
					getSqlName(SQLNAME_COUNT_TOTAL_REFUND_FEE_BY_REF_ORDER_NO)), e);
		}
		return hadRefundFee;
	}

}
