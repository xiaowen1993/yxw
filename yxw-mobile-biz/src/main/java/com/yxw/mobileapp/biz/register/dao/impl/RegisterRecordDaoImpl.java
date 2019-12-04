/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-15</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.register.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.commons.vo.platform.register.SimpleRecord;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.mobileapp.biz.register.dao.RegisterRecordDao;

/**
 * @Package: com.yxw.mobileapp.biz.register.dao.impl
 * @ClassName: RegisterDaoImpl
 * @Statement: <p>前端业务操作挂号记录的Dao实现类</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository(value = "registerRecordDao")
public class RegisterRecordDaoImpl extends BaseDaoImpl<RegisterRecord, String> implements RegisterRecordDao {
	private Logger logger = LoggerFactory.getLogger(RegisterRecordDaoImpl.class);
	private final static String SQLNAME_DEL_BY_ORDERNO = "deleteByOrderNo";
	private final static String SQLNAME_FIND_BY_MEDICALCARD = "findByMedicalCard";
	private final static String SQLNAME_FIND_BY_ORDERNO = "findByOrderNo";
	private final static String SQLNAME_UPDATE_RECORD_STATUS = "updateRecordStatus";
	private static final String SQLNAME_FIND_OVERTIME_RECORDS = "findOverTimeRecords";
	private static final String SQLNAME_UPDATE_RECORD_SATUTS = "updateRegisterRecordSatuts";
	private static final String SQLNAME_UPDATE_RECORD_PAYTIMEOUT_STATUS = "updateRecordPayTimeOutStatus";
	private static final String SQLNAME_FIND_REGISTER_RECORD = "findRegisterRecord";
	private static final String SQLNAME_FIND_PAY_STATUS_RECORD = "findPayStatusRecord";
	private static final String SQLNAME_FIND_NOT_PAYMENT_BY_IDS = "findNotPayMentByIds";
	private static final String SQLNAME_UPDATE_EXCEPTION_RECORD = "updateExceptionRecord";
	private final static String SQLNAME_UPDATE_REFUND_ORDER_NO = "updateRefundOrderNo";
	private final static String SQLNAME_UPDATE_RECORD_FOR_AGT_REFUND = "updateRecordForAgtRefund";
	private final static String SQLNAME_UPDATE_RECORD_FOR_AGT_PAYMENT = "updateRecordForAgtPayment";
	private final static String SQLNAME_FIND_STOP_REGISTER_RECORD = "findStopRegisterRecord";
	private final static String SQLNAME_FIND_STOP_REGISTER_RECORD_POLL = "findStopRegisterRecordPoll";
	private final static String SQLNAME_DELETE_BY_ID = "deleteById";
	private final static String SQLNAME_FIND_STOP_REGISTER_RECORD_BY_ORDER_NO = "findStopRegisterRecordByOrderNo";
	private final static String SQLNAME_FIND_DOWN_REGISTER_RECORD = "findDownRegisterRecord";
	private final static String SQLNAME_FIND_DOWN_REGISTER_RECORD_BY_SCHEDULE_DATE = "findDownRegisterRecordByScheduleDate";
	private final static String SQLNAME_FIND_ALL_NEED_HANDLE_EXCEPTION_RECORD = "findAllNeedHandleExceptionRecord";
	private final static String SQLNAME_FIND_REGISTER_RECORD_BY_ID_AND_HASHTABLENAME = "findRegisterRecordByIdAndHashTableName";
	private final static String SQLNAME_FIND_ALL_BY_ORDERNO = "findAllByOrderNo";
	private final static String SQLNAME_UPDATE_TRADE_MODE = "updateTradeMode";
	private final static String SQLNAME_FIND_RECORDS_BY_OPEN_ID_AND_ID_NO = "findRecordsByOpenIdAndIdNo";
	private final static String SQLNAME_FIND_RECORDS_FOR_VISIT_WARN = "findRecordsForVisitWarn";
	private final static String SQLNAME_FIND_RECORDS_BY_OPEN_ID_AND_APP_CODE = "findRecordsByOpenIdAndAppCode";
	private final static String SQLNAME_FIND_RECORDS_BY_ID_TYPE_AND_ID_NO = "findRecordsByIdTypeAndIdNo";
	private final static String SQLNAME_FIND_STOP_LIST_BY_PROCEDURE = "findStopListByProcedure";
	private final static String SQLNAME_FIND_LIST_BY_PROCEDURE = "findListByProcedure";

	@Override
	public void updateRecordStatus(RegisterRecord record) {
		try {
			Assert.notNull(record.getOrderNo());
			Integer orderNoHashVal = null;
			if (record.getOrderNoHashVal() == null || record.getOrderNoHashVal() == 0) {
				orderNoHashVal = Math.abs(record.getOrderNo().hashCode());
				record.setOrderNoHashVal(orderNoHashVal);
			}
			long updateTime = new Date().getTime();
			record.setUpdateTime(updateTime);
			sqlSession.update(getSqlName(SQLNAME_UPDATE_RECORD_STATUS), record);
		} catch (Exception e) {
			logger.error(String.format("更新挂号记录出错!语句：%s", getSqlName(SQLNAME_UPDATE_RECORD_STATUS)), e);
			throw new SystemException(String.format("更新挂号记录出错!语句：%s", getSqlName(SQLNAME_UPDATE_RECORD_STATUS)), e);
		}
	}

	@Override
	public RegisterRecord findByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		try {
			Assert.notNull(orderNo);
			Integer orderNoHashVal = Math.abs(orderNo.hashCode());
			Integer splitKeyVal = Integer.valueOf(OrderNoGenerator.getHashVal(orderNo));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orderNo", orderNo);
			paramMap.put("orderNoHashVal", orderNoHashVal);
			if (logger.isDebugEnabled()) {
				logger.debug("hashVal:" + splitKeyVal + "  hashTableName :"
						+ SimpleHashTableNameGenerator.getRegRecordHashTable(splitKeyVal, false));
			}
			paramMap.put("hashTableName", SimpleHashTableNameGenerator.getRegRecordHashTable(splitKeyVal, false));
			RegisterRecord record = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ORDERNO), paramMap);
			return record;
		} catch (Exception e) {
			logger.error(String.format("根据订单编号查询订单信息出错!语句：%s", getSqlName(SQLNAME_FIND_BY_ORDERNO)), e);
			throw new SystemException(String.format("根据订单编号查询订单信息出错!语句：%s", getSqlName(SQLNAME_FIND_BY_ORDERNO)), e);
		}
	}

	@Override
	public RegisterRecord findAllByOrderNo(Map<String, Object> params) {
		try {
			Assert.notNull(params);
			RegisterRecord record = sqlSession.selectOne(getSqlName(SQLNAME_FIND_ALL_BY_ORDERNO), params);
			return record;
		} catch (Exception e) {
			logger.error(String.format("根据订单编号查询订单信息出错!语句：%s", getSqlName(SQLNAME_FIND_ALL_BY_ORDERNO)), e);
			throw new SystemException(String.format("根据订单编号查询订单信息出错!语句：%s", getSqlName(SQLNAME_FIND_ALL_BY_ORDERNO)), e);
		}
	}

	@Override
	public void deleteByOrderNo(String orderNo, String openId) {
		// TODO Auto-generated method stub
		try {
			Assert.notNull(orderNo);
			Integer orderNoHashVal = Math.abs(orderNo.hashCode());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orderNo", orderNo);
			paramMap.put("orderNoHashVal", orderNoHashVal);
			paramMap.put("hashTableName", SimpleHashTableNameGenerator.getRegRecordHashTable(openId, true));
			sqlSession.delete(getSqlName(SQLNAME_DEL_BY_ORDERNO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("根据订单编号删除订单信息出错!语句：%s", getSqlName(SQLNAME_DEL_BY_ORDERNO)), e);
			throw new SystemException(String.format("根据订单编号删除订单信息出错!语句：%s", getSqlName(SQLNAME_DEL_BY_ORDERNO)), e);
		}
	}

	@Override
	public List<RegisterRecord> findByMedicalCard(MedicalCard card) {
		// TODO Auto-generated method stub
		List<RegisterRecord> records = null;
		try {
			Assert.notNull(card.getCardNo());
			Assert.notNull(card.getOpenId());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("cardNo", card.getCardNo());
			paramMap.put("openId", card.getOpenId());
			paramMap.put("hospitalId", card.getHospitalId());
			paramMap.put("branchHospitalId", card.getBranchId());
			paramMap.put("hashTableName", SimpleHashTableNameGenerator.getRegRecordHashTable(card.getOpenId(), true));
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_MEDICALCARD), paramMap);
		} catch (Exception e) {
			logger.error(String.format("根据医疗卡查询挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_MEDICALCARD)), e);
			throw new SystemException(String.format("根据医疗卡查询挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_MEDICALCARD)), e);
		}
		return records;
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.register.dao.RegisterDao#findRecordForThreeMonth()
	 */
	@Override
	public List<RegisterRecord> findOverTimeRecords(String hospitalId, String branchHospitalId, String openId, String cardNo,
			List<Integer> regStatuses, Long overTime) {
		// TODO Auto-generated method stub
		List<RegisterRecord> records = null;
		try {
			Assert.notNull(hospitalId);
			Assert.notNull(branchHospitalId);
			Assert.notNull(openId);
			Assert.notNull(overTime);
			Assert.notNull(cardNo);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("hospitalId", hospitalId);
			queryMap.put("branchHospitalId", branchHospitalId);
			queryMap.put("openId", openId);
			queryMap.put("overTime", overTime);
			queryMap.put("cardNo", cardNo);
			queryMap.put("regStatuses", regStatuses);
			queryMap.put("hashTableName", SimpleHashTableNameGenerator.getRegRecordHashTable(openId, true));
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_OVERTIME_RECORDS), queryMap);
		} catch (Exception e) {
			logger.error(String.format("根据时间查询挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_OVERTIME_RECORDS)), e);
			throw new SystemException(String.format("根据时间查询挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_OVERTIME_RECORDS)), e);
		}
		return records;
	}

	@Override
	public void updateRegisterRecordSatuts(Integer regStatus, List<String> recordIds, String hashTableName) {
		// TODO Auto-generated method stub
		try {
			Assert.notNull(regStatus);
			Assert.notEmpty(recordIds);
			Map<String, Object> paramDatas = new HashMap<String, Object>();
			paramDatas.put("regStatus", regStatus);
			paramDatas.put("updateTime", new Date().getTime());
			paramDatas.put("recordIds", recordIds);
			paramDatas.put("hashTableName", hashTableName);
			sqlSession.update(getSqlName(SQLNAME_UPDATE_RECORD_SATUTS), paramDatas);
		} catch (Exception e) {
			logger.error(String.format("更新挂号记录状态出错!语句：%s", getSqlName(SQLNAME_UPDATE_RECORD_SATUTS)), e);
			throw new SystemException(String.format("更新挂号记录状态!语句：%s", getSqlName(SQLNAME_UPDATE_RECORD_SATUTS)), e);
		}
	}

	public void updateRecordPayTimeOutStatus(List<String> recordIds, String hashTableName) {
		try {
			Assert.notEmpty(recordIds);
			Map<String, Object> paramDatas = new HashMap<String, Object>();

			paramDatas.put("updateTime", new Date().getTime());
			paramDatas.put("recordIds", recordIds);
			paramDatas.put("hashTableName", hashTableName);
			paramDatas.put("regStatus", RegisterConstant.STATE_NORMAL_PAY_TIMEOUT_CANCEL);
			sqlSession.update(getSqlName(SQLNAME_UPDATE_RECORD_PAYTIMEOUT_STATUS), paramDatas);
		} catch (Exception e) {
			logger.error(String.format("更新挂号记录状态出错!语句：%s", getSqlName(SQLNAME_UPDATE_RECORD_PAYTIMEOUT_STATUS)), e);
			throw new SystemException(String.format("更新挂号记录状态!语句：%s", getSqlName(SQLNAME_UPDATE_RECORD_PAYTIMEOUT_STATUS)), e);
		}
	}

	@Override
	public List<String> queryNotPayMentByIds(List<String> recordIds, String hashTableName) {
		// TODO Auto-generated method stub
		List<String> resIds = new ArrayList<String>();
		try {
			Assert.notEmpty(recordIds);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("recordIds", recordIds);
			param.put("hashTableName", hashTableName);
			resIds = sqlSession.selectList(getSqlName(SQLNAME_FIND_NOT_PAYMENT_BY_IDS), param);
		} catch (Exception e) {
			logger.error(String.format("查询状态为预约中的挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_NOT_PAYMENT_BY_IDS)), e);
			throw new SystemException(String.format("查询状态为预约中的挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_NOT_PAYMENT_BY_IDS)), e);
		}
		return resIds;
	}

	@Override
	public List<SimpleRecord> findRegisterRecord(String hashTableName, Integer regStatus, Integer payStatus) {
		// TODO Auto-generated method stub
		List<SimpleRecord> records = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("hashTableName", hashTableName);
			paramMap.put("regStatus", regStatus);
			paramMap.put("payStatus", payStatus);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_REGISTER_RECORD), paramMap);
		} catch (Exception e) {
			logger.error(String.format("查询挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_REGISTER_RECORD)), e);
			throw new SystemException(String.format("查询挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_REGISTER_RECORD)), e);
		}
		return records;
	}

	public List<SimpleRecord> findPayStatusRecord(String hashTableName, Integer payStatus) {
		List<SimpleRecord> records = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("hashTableName", hashTableName);
			paramMap.put("payStatus", payStatus);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_PAY_STATUS_RECORD), paramMap);
		} catch (Exception e) {
			logger.error(String.format("查询挂号记录findPayStatusRecord出错!语句：%s", getSqlName(SQLNAME_FIND_PAY_STATUS_RECORD)), e);
			throw new SystemException(String.format("查询挂号记录findPayStatusRecord出错!语句：%s", getSqlName(SQLNAME_FIND_PAY_STATUS_RECORD)), e);
		}
		return records;
	}

	@Override
	public void updateExceptionRecord(ExceptionRecord record) {
		// TODO Auto-generated method stub
		try {
			Assert.notNull(record);
			Assert.notNull(record.getOrderNo());
			long updateTime = System.currentTimeMillis();
			record.setUpdateTime(updateTime);

			if (record.getOrderNoHashVal() == null) {
				record.setOrderNoHashVal(Math.abs(record.getOrderNo().hashCode()));
			}

			sqlSession.update(getSqlName(SQLNAME_UPDATE_EXCEPTION_RECORD), record);
		} catch (Exception e) {
			logger.error(String.format("更新异常挂号记录状态出错!语句：%s", getSqlName(SQLNAME_UPDATE_EXCEPTION_RECORD)), e);
			throw new SystemException(String.format("更新异常挂号记录状态!语句：%s", getSqlName(SQLNAME_UPDATE_EXCEPTION_RECORD)), e);
		}
	}

	@Override
	public void updateRefundOrderNo(String orderNo, String refundOrderNo, String openId) {
		// TODO Auto-generated method stub
		try {
			Assert.notNull(orderNo, "updateRefundOrderNo orderNo is null!");
			Assert.notNull(refundOrderNo, "updateRefundOrderNo refundOrderNo is null!");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Integer orderNoHashVal = Math.abs(orderNo.hashCode());
			paramMap.put("orderNo", orderNo);
			paramMap.put("orderNoHashVal", orderNoHashVal);
			paramMap.put("refundOrderNo", refundOrderNo);
			paramMap.put("hashTableName", SimpleHashTableNameGenerator.getRegRecordHashTable(openId, true));
			sqlSession.update(getSqlName(SQLNAME_UPDATE_REFUND_ORDER_NO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("保存订单的退费号出错!语句：%s", getSqlName(SQLNAME_UPDATE_REFUND_ORDER_NO)), e);
			throw new SystemException(String.format("保存订单的退费号出错!语句：%s", getSqlName(SQLNAME_UPDATE_REFUND_ORDER_NO)), e);
		}
	}

	/**
	 * 第3方交易平台退费后更新记录
	 * @param record
	 */
	public void updateRecordForAgtRefund(RegisterRecord record) {
		try {
			Assert.notNull(record.getOrderNo(), "updateRecordForAgtRefund orderNo is null!");
			if (record.getOrderNoHashVal() == null) {
				Integer orderNoHashVal = Math.abs(record.getOrderNo().hashCode());
				record.setOrderNoHashVal(orderNoHashVal);
			}
			sqlSession.update(getSqlName(SQLNAME_UPDATE_RECORD_FOR_AGT_REFUND), record);
		} catch (Exception e) {
			logger.error(String.format("更新第三方交易平台退费状态出错!语句：%s", getSqlName(SQLNAME_UPDATE_RECORD_FOR_AGT_REFUND)), e);
			throw new SystemException(String.format("更新第三方交易平台退费状态出错!语句：%s", getSqlName(SQLNAME_UPDATE_RECORD_FOR_AGT_REFUND)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.mobileapp.biz.register.dao.RegisterRecordDao#updateRecordForAgtPay(com.yxw.mobileapp.biz.register.entity.RegisterRecord)
	 */
	@Override
	public void updateRecordForAgtPayment(RegisterRecord record) {
		// TODO Auto-generated method stub
		try {
			Assert.notNull(record.getOrderNo(), "updateRecordForAgtPayment orderNo is null!");
			if (record.getOrderNoHashVal() == null) {
				Integer orderNoHashVal = Math.abs(record.getOrderNo().hashCode());
				record.setOrderNoHashVal(orderNoHashVal);
			}
			sqlSession.update(getSqlName(SQLNAME_UPDATE_RECORD_FOR_AGT_PAYMENT), record);
		} catch (Exception e) {
			logger.error(String.format("更新第三方交易平台支付状态出错!语句：%s", getSqlName(SQLNAME_UPDATE_RECORD_FOR_AGT_PAYMENT)), e);
			throw new SystemException(String.format("更新第三方交易平台支付状态出错!语句：%s", getSqlName(SQLNAME_UPDATE_RECORD_FOR_AGT_PAYMENT)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.mobileapp.biz.register.dao.RegisterRecordDao#findStopRegisterRecord(java.lang.String, java.lang.String)
	 */
	@Override
	public List<RegisterRecord> findStopRegisterRecord(String condition, String hashTableName) {
		List<RegisterRecord> records = null;
		try {
			Assert.notNull(hashTableName);
			Assert.notNull(condition);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("hashTableName", hashTableName);
			queryMap.put("condition", condition);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_STOP_REGISTER_RECORD), queryMap);
		} catch (Exception e) {
			logger.error(String.format("根据平台订单号及挂号表查询订单  出错!语句：%s", getSqlName(SQLNAME_FIND_STOP_REGISTER_RECORD)), e);
			throw new SystemException(String.format("根据平台订单号及挂号表查询订单  出错!语句：%s", getSqlName(SQLNAME_FIND_STOP_REGISTER_RECORD)), e);
		}
		return records;
	}

	@Override
	public List<RegisterRecord> findStopRegisterRecordForPoll(String condition, String hashTableName) {
		List<RegisterRecord> records = null;
		try {
			Assert.notNull(hashTableName);
			Assert.notNull(condition);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("hashTableName", hashTableName);
			queryMap.put("condition", condition);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_STOP_REGISTER_RECORD_POLL), queryMap);
		} catch (Exception e) {
			logger.error(String.format("根据his订单号及挂号表查询订单 出错!语句：%s", getSqlName(SQLNAME_FIND_STOP_REGISTER_RECORD_POLL)), e);
			throw new SystemException(String.format("根据his订单号及挂号表查询订单 出错!语句：%s", getSqlName(SQLNAME_FIND_STOP_REGISTER_RECORD_POLL)), e);
		}
		return records;
	}

	public void deleteById(String recordId, String openId) {
		try {
			Assert.notNull(recordId);
			Assert.notNull(openId);
			Map<String, Object> deleteMap = new HashMap<String, Object>();
			deleteMap.put("recordId", recordId);
			deleteMap.put("hashTableName", SimpleHashTableNameGenerator.getRegRecordHashTable(openId, true));
			sqlSession.delete(getSqlName(SQLNAME_DELETE_BY_ID), deleteMap);
		} catch (Exception e) {
			logger.error(String.format("根据his订单号及分院Code查询订单 出错!语句：%s", getSqlName(SQLNAME_DELETE_BY_ID)), e);
			throw new SystemException(String.format("根据his订单号及分院Code查询订单 出错!语句：%s", getSqlName(SQLNAME_DELETE_BY_ID)), e);
		}
	}

	@Override
	public RegisterRecord findStopRegisterRecordByOrderNo(String orderNo, List<String> hashTableName) {
		try {
			Assert.notNull(hashTableName);
			Assert.notNull(orderNo);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("hashTableName", hashTableName);
			queryMap.put("orderNo", orderNo);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_STOP_REGISTER_RECORD_BY_ORDER_NO), queryMap);
		} catch (Exception e) {
			logger.error(String.format("根据交易订单号查询订单 出错!语句：%s", getSqlName(SQLNAME_FIND_STOP_REGISTER_RECORD_BY_ORDER_NO)), e);
			throw new SystemException(String.format("根据交易订单号查询订单 出错!语句：%s", getSqlName(SQLNAME_FIND_STOP_REGISTER_RECORD_BY_ORDER_NO)), e);
		}
	}

	@Override
	public List<RegisterRecord> findDownRegisterRecord(String hospitalId, String branchCode, String tradeMode, String startTime,
			String endTime, String hashTableName) {
		List<RegisterRecord> records = null;
		try {
			Assert.notNull(hospitalId);
			Assert.notNull(startTime);
			Assert.notNull(endTime);
			Assert.notNull(hashTableName);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("hospitalId", hospitalId);
			queryMap.put("branchCode", branchCode);
			queryMap.put("tradeMode", tradeMode);
			queryMap.put("hashTableName", hashTableName);
			queryMap.put("startTime", startTime);
			queryMap.put("endTime", endTime);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_DOWN_REGISTER_RECORD), queryMap);
		} catch (Exception e) {
			logger.error(String.format("挂号订单下载 查询接口 出错!语句：%s", getSqlName(SQLNAME_FIND_DOWN_REGISTER_RECORD)), e);
			throw new SystemException(String.format("挂号订单下载 查询接口 出错!语句：%s", getSqlName(SQLNAME_FIND_DOWN_REGISTER_RECORD)), e);
		}
		return records;
	}

	@Override
	public List<RegisterRecord> findDownRegisterRecordByScheduleDate(String hospitalId, String branchCode, String tradeMode,
			String regType, String startDate, String endDate, String hashTableName) {
		List<RegisterRecord> records = null;
		try {
			Assert.notNull(hospitalId);
			Assert.notNull(startDate);
			Assert.notNull(endDate);
			Assert.notNull(hashTableName);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("hospitalId", hospitalId);
			queryMap.put("branchCode", branchCode);
			queryMap.put("tradeMode", tradeMode);
			queryMap.put("regType", regType);
			queryMap.put("hashTableName", hashTableName);
			queryMap.put("startDate", startDate);
			queryMap.put("endDate", endDate);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_DOWN_REGISTER_RECORD_BY_SCHEDULE_DATE), queryMap);
		} catch (Exception e) {
			logger.error(String.format("挂号订单下载(根据就诊时间) 查询接口 出错!语句：%s", getSqlName(SQLNAME_FIND_DOWN_REGISTER_RECORD_BY_SCHEDULE_DATE)), e);
			throw new SystemException(String.format("挂号订单下载(根据就诊时间) 查询接口 出错!语句：%s",
					getSqlName(SQLNAME_FIND_DOWN_REGISTER_RECORD_BY_SCHEDULE_DATE)), e);
		}
		return records;
	}

	@Override
	public List<ExceptionRecord> findAllNeedHandleExceptionRecord(String hashTableName) {
		// TODO Auto-generated method stub
		List<ExceptionRecord> records = null;
		try {
			Assert.notNull(hashTableName);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("hashTableName", hashTableName);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_NEED_HANDLE_EXCEPTION_RECORD), paramMap);
		} catch (Exception e) {
			logger.error(String.format("挂号订单下载 查询接口 出错!语句：%s", getSqlName(SQLNAME_FIND_ALL_NEED_HANDLE_EXCEPTION_RECORD)), e);
			throw new SystemException(String.format("挂号订单下载 查询接口 出错!语句：%s", getSqlName(SQLNAME_FIND_ALL_NEED_HANDLE_EXCEPTION_RECORD)), e);
		}
		return records;
	}

	@Override
	public RegisterRecord findRegisterRecordByIdAndHashTableName(Map<String, String> params) {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_REGISTER_RECORD_BY_ID_AND_HASHTABLENAME), params);
		} catch (Exception e) {
			logger.error(String.format("根据交易id查询订单 出错!语句：%s", getSqlName(SQLNAME_FIND_REGISTER_RECORD_BY_ID_AND_HASHTABLENAME)), e);
			throw new SystemException(String.format("根据交易id查询订单  出错!语句：%s",
					getSqlName(SQLNAME_FIND_REGISTER_RECORD_BY_ID_AND_HASHTABLENAME)), e);
		}
	}

	@Override
	public void updateTradeMode(RegisterRecord record) {
		// TODO Auto-generated method stub
		try {
			sqlSession.update(getSqlName(SQLNAME_UPDATE_TRADE_MODE), record);
		} catch (Exception e) {
			logger.error(String.format("更新订单的交易方式 出错!语句：%s", getSqlName(SQLNAME_UPDATE_TRADE_MODE)), e);
			throw new SystemException(String.format("更新订单的交易方式 出错!语句：%s", getSqlName(SQLNAME_UPDATE_TRADE_MODE)), e);
		}
	}

	@Override
	public List<RegisterRecord>
			findRecordsByOpenIdAndIdNo(String openId, String idNo, String registerTableName, String medicalcardTableName) {
		List<RegisterRecord> records = null;
		try {
			Assert.notNull(openId, "openId不能为空");
			Assert.notNull(idNo, "idNo不能为空");
			Assert.notNull(registerTableName, "registerTableName不能为空");
			Assert.notNull(medicalcardTableName, "medicalcardTableName不能为空");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("openId", openId);
			paramMap.put("idNo", idNo);
			paramMap.put("hashTableName", registerTableName);
			paramMap.put("medicalcardTableName", medicalcardTableName);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_RECORDS_BY_OPEN_ID_AND_ID_NO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("通过IdNo和OpenId查找挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_RECORDS_BY_OPEN_ID_AND_ID_NO)), e);
			throw new SystemException(String.format("通过IdNo和OpenId查找挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_RECORDS_BY_OPEN_ID_AND_ID_NO)),
					e);
		}
		return records;
	}

	@Override
	public List<RegisterRecord> findRecordsForVisitWarn(String scheduleDate, String hashTableName) {
		List<RegisterRecord> records = null;
		try {
			Assert.notNull(scheduleDate, "scheduleDay不能为空");
			Assert.notNull(hashTableName, "hashTableName不能为空");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("scheduleDate", scheduleDate);
			paramMap.put("hashTableName", hashTableName);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_RECORDS_FOR_VISIT_WARN), paramMap);
		} catch (Exception e) {
			logger.error(String.format("获取就诊轮询挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_RECORDS_FOR_VISIT_WARN)), e);
			throw new SystemException(String.format("获取就诊轮询挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_RECORDS_FOR_VISIT_WARN)), e);
		}
		return records;
	}

	@Override
	public List<RegisterRecord> findRecordsByOpenIdAndAppCode(String openId, String appCode, String hospitalCode, long beginTime,
			long endTime, String hashTableName) {
		List<RegisterRecord> records = null;
		try {
			Assert.notNull(openId, "openId不能为空");
			// Assert.notNull(appCode, "appCode不能为空"); appCode可以为空
			Assert.notNull(hashTableName, "hashTableName不能为空");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("openId", openId);
			paramMap.put("appCode", appCode);
			paramMap.put("hospitalCode", hospitalCode);
			paramMap.put("beginTime", beginTime);
			paramMap.put("endTime", endTime);
			paramMap.put("hashTableName", hashTableName);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_RECORDS_BY_OPEN_ID_AND_APP_CODE), paramMap);
		} catch (Exception e) {
			logger.error(String.format("按时间、医院查找挂号订单出错!语句：%s", getSqlName(SQLNAME_FIND_RECORDS_BY_OPEN_ID_AND_APP_CODE)), e);
			throw new SystemException(String.format("按时间、医院查找挂号订单出错!语句：%s", getSqlName(SQLNAME_FIND_RECORDS_BY_OPEN_ID_AND_APP_CODE)), e);
		}
		return records;
	}

	@Override
	public List<RegisterRecord> findRecordsByIdTypeAndIdNo(Integer idType, String idNo, String registerTableName,
			String medicalcardTableName) {
		List<RegisterRecord> records = null;
		try {
			Assert.notNull(idType, "idType不能为空");
			Assert.notNull(idNo, "idNo不能为空");
			Assert.notNull(registerTableName, "registerTableName不能为空");
			Assert.notNull(medicalcardTableName, "medicalcardTableName不能为空");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("idType", idType);
			paramMap.put("idNo", idNo);
			paramMap.put("hashTableName", registerTableName);
			paramMap.put("medicalcardTableName", medicalcardTableName);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_RECORDS_BY_ID_TYPE_AND_ID_NO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("通过IdNo和idType查找挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_RECORDS_BY_ID_TYPE_AND_ID_NO)), e);
			throw new SystemException(String.format("通过IdNo和idType查找挂号记录出错!语句：%s", getSqlName(SQLNAME_FIND_RECORDS_BY_ID_TYPE_AND_ID_NO)),
					e);
		}
		return records;
	}

	@Override
	public List<RegisterRecord> findListByProcedure(Map map) {
		try {
			//logger.info("----map:{}", JSON.toJSONString(map));
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_LIST_BY_PROCEDURE), map);
		} catch (Exception e) {
			logger.error(String.format("根据参数查询订单视图出错！语句：%s", getSqlName(SQLNAME_FIND_LIST_BY_PROCEDURE)), e);
			throw new SystemException(String.format("根据参数查询订单视图出错！语句：%s", getSqlName(SQLNAME_FIND_LIST_BY_PROCEDURE)), e);
		}
	}

	@Override
	public List<RegisterRecord> findStopListByProcedure(Map map) {
		try {
			//logger.info("----map:{}", JSON.toJSONString(map));
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_STOP_LIST_BY_PROCEDURE), map);
		} catch (Exception e) {
			logger.error(String.format("根据参数查询订单视图出错！语句：%s", getSqlName(SQLNAME_FIND_STOP_LIST_BY_PROCEDURE)), e);
			throw new SystemException(String.format("根据参数查询订单视图出错！语句：%s", getSqlName(SQLNAME_FIND_STOP_LIST_BY_PROCEDURE)), e);
		}
	}
}
