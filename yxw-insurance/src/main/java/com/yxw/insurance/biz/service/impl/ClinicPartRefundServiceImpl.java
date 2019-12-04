package com.yxw.insurance.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.insurance.biz.dao.ClinicPartRefundDao;
import com.yxw.insurance.biz.service.ClinicPartRefundService;

@Service
public class ClinicPartRefundServiceImpl implements ClinicPartRefundService {

	private Logger logger = LoggerFactory.getLogger(ClinicPartRefundServiceImpl.class);

	private ClinicPartRefundDao clinicDao = SpringContextHolder.getBean(ClinicPartRefundDao.class);

	@Override
	public Integer getRefundedFee(String orderNo, String hospitalCode) {
		Integer hadRefundFee = clinicDao.countTotalRefundFeeByRefOrderNo(orderNo, hospitalCode);
		logger.info("orderNo[{}] refund fee: {}", new Object[] { orderNo, hadRefundFee });
		return hadRefundFee;
	}

}
