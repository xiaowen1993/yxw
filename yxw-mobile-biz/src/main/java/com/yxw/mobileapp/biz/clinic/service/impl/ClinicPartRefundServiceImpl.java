package com.yxw.mobileapp.biz.clinic.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicPartRefundRecord;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.ServerNoGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;
import com.yxw.mobileapp.biz.clinic.dao.ClinicPartRefundDao;
import com.yxw.mobileapp.biz.clinic.service.ClinicPartRefundService;

@Service(value = "clinicPartRefundService")
public class ClinicPartRefundServiceImpl implements ClinicPartRefundService {

	private Logger logger = LoggerFactory.getLogger(ClinicPartRefundServiceImpl.class);

	private ClinicPartRefundDao clinicDao = SpringContextHolder.getBean(ClinicPartRefundDao.class);

	@Override
	public ClinicPartRefundRecord saveSuccessPartRefundRecord(ClinicRecord record, String refundHisOrdNo, String agtRefundOrdNum,
			Long curTime) {

		ClinicPartRefundRecord refundRecord = new ClinicPartRefundRecord(record);

		refundRecord.setRefundHisOrdNo(refundHisOrdNo);
		refundRecord.setId(PKGenerator.generateId());
		refundRecord.setCreateTime(curTime);
		refundRecord.setUpdateTime(curTime);
		refundRecord.setAgtRefundOrdNum(agtRefundOrdNum);
		refundRecord.setRefundTime(curTime);
		refundRecord.setRefundFee(Integer.valueOf(record.getRefundFee()));
		refundRecord.setRefundStatus(ClinicConstant.STATE_PART_REFUND_SUCCESS);
		refundRecord.setRecordTitle(ClinicConstant.CLNIC_PART_REFUND_TITLE);
		refundRecord.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
		refundRecord.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
		refundRecord.setHandleCount(0);

		logger.info("save part refund record. orderNo: {}", refundRecord.getRefundOrderNo());

		// 将订单信息存到数据库中
		clinicDao.add(refundRecord);

		return refundRecord;
	}

	@Override
	public Integer getRefundedFee(String orderNo, String hospitalCode) {
		Integer hadRefundFee = clinicDao.countTotalRefundFeeByRefOrderNo(orderNo, hospitalCode);
		logger.info("orderNo[{}] refund fee: {}", new Object[] { orderNo, hadRefundFee });
		// return totalFee > hadRefundFee + refundFee;
		return hadRefundFee;
	}

	@Override
	public ClinicPartRefundRecord saveFailPartRefundRecord(ClinicRecord record, String refundHisOrdNo, Long curTime) {
		ClinicPartRefundRecord refundRecord = new ClinicPartRefundRecord(record);

		try {
			String orderNo =
					OrderNoGenerator.genOrderNo(String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode())),
							String.valueOf(record.getTradeMode()), OrderConstant.ORDER_TYPE_REFUND_PART, BizConstant.BIZ_TYPE_CLINIC,
							ServerNoGenerator.getServerNoByIp(), record.getOpenId());

			logger.info("save part refund record. orderNo: {}", orderNo);

			refundRecord.setRefundOrderNo(orderNo);
		} catch (Exception e) {
			throw new SystemException(e.getCause());
		}

		//		String orderNo = OrderNoGenerator.genOrderNo(record.getOrderType(), BizConstant.ORDER_TYPE_REFUND_OFFLINE_PART, BizConstant.BIZ_TYPE_CLINIC);
		//		refundRecord.setRefundOrderNo(orderNo);
		refundRecord.setRefundHisOrdNo(refundHisOrdNo);
		refundRecord.setId(PKGenerator.generateId());
		refundRecord.setCreateTime(curTime);
		refundRecord.setUpdateTime(curTime);
		// refundRecord.setAgtRefundOrdNum(agtRefundOrdNum);
		refundRecord.setRefundFee(Integer.valueOf(record.getRefundFee()));
		refundRecord.setRefundStatus(ClinicConstant.STATE_PART_REFUND_FAIL);
		refundRecord.setRecordTitle(ClinicConstant.CLNIC_PART_REFUND_TITLE);
		refundRecord.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
		refundRecord.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
		refundRecord.setHandleCount(0);
		refundRecord.setHandleLog("部分退费失败。");

		// 将订单信息存到数据库中
		clinicDao.add(refundRecord);

		return refundRecord;
	}

	@Override
	public ClinicPartRefundRecord findByRefundOrderNo(String hospitalCode, String hisOrdNo, String orderNo) {
		return clinicDao.findByHospitalCodeAndHisOrdNo(hospitalCode, hisOrdNo, orderNo);
	}

	@Override
	public void updateRecord(ClinicPartRefundRecord record) {
		clinicDao.update(record);
	}

}
