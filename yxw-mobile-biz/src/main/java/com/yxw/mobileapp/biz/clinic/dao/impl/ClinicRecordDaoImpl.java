package com.yxw.mobileapp.biz.clinic.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.constants.biz.ReceiveConstant;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.mobileapp.biz.clinic.dao.ClinicRecordDao;
import com.yxw.mobileapp.biz.clinic.vo.ClinicQueryVo;

@Repository
public class ClinicRecordDaoImpl extends BaseDaoImpl<ClinicRecord, String> implements ClinicRecordDao {

	private Logger logger = LoggerFactory.getLogger(ClinicRecordDaoImpl.class);

	private final static String SQLNAME_UPDATE_RECORD_STATUS = "updateRecordStatusByOrderNoAndOpenId";

	private final static String SQLNAME_FIND_BY_ORDERNO = "findByOrderNo";

	private final static String SQLNAME_FIND_PAID_RECORD = "findPaidRecord";

	private final static String SQLNAME_FIND_PAID_RECORD_BY_ORDER_NO = "findPaidRecordByOrderNo";

	private final static String SQLNAME_FIND_SAVED_UNPAID_CLINICRECORD = "findSavedUnpaidClinicRecord";

	private final static String SQLNAME_FIND_DOWN_PAID_RECORD = "findDownPaidRecord";

	private final static String SQLNAME_FIND_CLINIC_RECORD_BY_ID_AND_HASHTABLENAME = "findClinicRecordByIdAndHashTableName";

	private final static String SQLNAME_FIND_ALL_NEED_HANDLE_EXCEPTION_RECORDS = "findAllNeedHandleExceptionRecords";

	private final static String SQLNAME_FIND_BY_ORDER_NO_AND_HOSPITAL_CODE = "findByOrderNoAndHospitalCode";

	private final static String SQLNAME_FIND_PART_REFUND_RECORD = "findPartRefundRecord";

	private final static String SQLNAME_FIND_PAID_RECORDS = "findPaidRecords";

	private final static String SQLNAME_FIND_PAID_RECORD_BY_OPEN_ID_AND_ID_NO = "findPaidRecordByOpenIdAndIdNo";

	private final static String SQLNAME_FIND_PAID_RECORD_BY__ID_NO_AND_ID_TYPE = "findPaidRecordByIdNoAndIdType";

	private final static String SQLNAME_UPDATE_COVER_ORDER = "updateCoverOrder";

	private final static String SQLNAME_FIND_LIST_BY_PROCEDURE = "findListByProcedure";

	@Override
	public void updateRecordStatusByOrderNoAndOpenId(ClinicRecord record) {
		try {
			Assert.notNull(record.getOrderNo());
			Assert.notNull(record.getOpenId());
			sqlSession.update(getSqlName(SQLNAME_UPDATE_RECORD_STATUS), record);
		} catch (Exception e) {
			logger.error(String.format("通过orderNo和openId更新门诊记录出错！语句：%s", getSqlName(SQLNAME_UPDATE_RECORD_STATUS)), e);
			throw new SystemException(String.format("通过orderNo和openId更新门诊记录出错！语句：%s", getSqlName(SQLNAME_UPDATE_RECORD_STATUS)), e);
		}
	}

	@Override
	public ClinicRecord findByOrderNo(ClinicRecord record) {
		// TODO Auto-generated method stub
		ClinicRecord clinicRecord = null;
		try {
			Assert.notNull(record.getOrderNo());
			Integer orderNoHashVal = Math.abs(record.getOrderNo().hashCode());
			Integer splitKeyVal = Integer.valueOf(OrderNoGenerator.getHashVal(record.getOrderNo()));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orderNo", record.getOrderNo());
			paramMap.put("orderNoHashVal", orderNoHashVal);
			if (logger.isDebugEnabled()) {
				logger.debug("hashVal:" + splitKeyVal + "  hashTableName :"
						+ SimpleHashTableNameGenerator.getRegRecordHashTable(splitKeyVal, false));
			}
			paramMap.put("hashTableName", SimpleHashTableNameGenerator.getClinicRecordHashTable(splitKeyVal, false));
			clinicRecord = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ORDERNO), paramMap);

			return clinicRecord;
		} catch (Exception e) {
			logger.error(String.format("通过orderNo和openId查询门诊记录出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ORDERNO)), e);
			throw new SystemException(String.format("通过orderNo和openId查询门诊记录出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ORDERNO)), e);
		}

		/*ClinicRecord clinicRecord = null;
		try {
			Assert.notNull(record.getOrderNo());
			clinicRecord = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ORDERNO), record);
		} catch (Exception e) {
			logger.error(String.format("通过orderNo和openId查询门诊记录出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ORDERNO)), e);
			throw new SystemException(String.format("通过orderNo和openId查询门诊记录出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ORDERNO)), e);
		}
		return clinicRecord;*/
	}

	@Override
	public List<ClinicRecord> findPaidClinicRecord(ClinicRecord record) {
		List<ClinicRecord> list = null;
		try {
			Assert.notNull(record.getHospitalCode());
			Assert.notNull(record.getBranchHospitalCode());
			Assert.notNull(record.getCardType());
			Assert.notNull(record.getCardNo());
			Assert.notNull(record.getPatientName());
			Assert.notNull(record.getOpenId());
			Assert.notNull(record.getPayStatus());
			Assert.notNull(record.getQueryBeginTime());
			Assert.notNull(record.getQueryEndTime());

			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_PAID_RECORD), record);
		} catch (Exception e) {
			logger.error(String.format("查询门诊已缴费记录出错！语句：%s", getSqlName(SQLNAME_FIND_PAID_RECORD)), e);
			throw new SystemException(String.format("查询门诊已缴费记录出错！语句：%s", getSqlName(SQLNAME_FIND_PAID_RECORD)), e);
		}
		return list;
	}

	@Override
	public ClinicRecord findPaidRecordByOrderNo(String orderNo, List<String> hashTableName) {
		try {
			Assert.notNull(hashTableName);
			Assert.notNull(orderNo);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("hashTableName", hashTableName);
			queryMap.put("orderNo", orderNo);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_PAID_RECORD_BY_ORDER_NO), queryMap);
		} catch (Exception e) {
			logger.error(String.format("根据交易订单号查询订单 出错!语句：%s", getSqlName(SQLNAME_FIND_PAID_RECORD_BY_ORDER_NO)), e);
			throw new SystemException(String.format("根据交易订单号查询订单 出错!语句：%s", getSqlName(SQLNAME_FIND_PAID_RECORD_BY_ORDER_NO)), e);
		}
	}

	@Override
	public List<ClinicRecord> findDownPaidRecord(String hospitalId, String branchCode, String tradeMode, String startTime, String endTime,
			String hashTableName) {
		List<ClinicRecord> records = null;
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
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_DOWN_PAID_RECORD), queryMap);
		} catch (Exception e) {
			logger.error(String.format("门诊订单下载查询接口出错!语句：%s", getSqlName(SQLNAME_FIND_DOWN_PAID_RECORD)), e);
			throw new SystemException(String.format("门诊订单下载 查询接口出错!语句：%s", getSqlName(SQLNAME_FIND_DOWN_PAID_RECORD)), e);
		}
		return records;
	}

	@Override
	public ClinicRecord findClinicRecordByIdAndHashTableName(Map<String, String> params) {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_CLINIC_RECORD_BY_ID_AND_HASHTABLENAME), params);
		} catch (Exception e) {
			logger.error(String.format("根据交易id查询订单 出错!语句：%s", getSqlName(SQLNAME_FIND_CLINIC_RECORD_BY_ID_AND_HASHTABLENAME)), e);
			throw new SystemException(
					String.format("根据交易id查询订单  出错!语句：%s", getSqlName(SQLNAME_FIND_CLINIC_RECORD_BY_ID_AND_HASHTABLENAME)), e);
		}
	}

	@Override
	public List<ClinicRecord> findAllNeedHandleExceptionRecords(String hashTableName) {
		List<ClinicRecord> list = null;
		try {
			Assert.notNull(hashTableName);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("hashTableName", hashTableName);
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_NEED_HANDLE_EXCEPTION_RECORDS), params);
		} catch (Exception e) {
			logger.error(String.format("查询门诊需处理的异常订单记录出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_NEED_HANDLE_EXCEPTION_RECORDS)), e);
			throw new SystemException(String.format("查询门诊需处理的异常订单记录出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_NEED_HANDLE_EXCEPTION_RECORDS)),
					e);
		}
		return list;
	}

	@Override
	public ClinicRecord findByOrderNoAndHospitalCode(String orderNo, String hospitalCode, String hashTableName) {
		try {
			Assert.notNull(orderNo);
			Assert.notNull(hospitalCode);
			Assert.notNull(hashTableName);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("hashTableName", hashTableName);
			queryMap.put("orderNo", orderNo);
			queryMap.put("hospitalCode", hospitalCode);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ORDER_NO_AND_HOSPITAL_CODE), queryMap);
		} catch (Exception e) {
			logger.error(String.format("根据交易订单号和医院代码查询订单 出错!语句：%s", getSqlName(SQLNAME_FIND_BY_ORDER_NO_AND_HOSPITAL_CODE)), e);
			throw new SystemException(String.format("根据交易订单号和医院代码查询订单 出错!语句：%s", getSqlName(SQLNAME_FIND_BY_ORDER_NO_AND_HOSPITAL_CODE)), e);
		}
	}

	@Override
	public List<ClinicRecord>
			findPartRefundRecord(String hospitalId, String branchCode, String tradeMode, String startTime, String endTime) {
		List<ClinicRecord> records = null;
		try {
			Assert.notNull(hospitalId);
			Assert.notNull(startTime);
			Assert.notNull(endTime);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("hospitalId", hospitalId);
			queryMap.put("branchCode", branchCode);
			if (tradeMode.equalsIgnoreCase(ReceiveConstant.TRADEMODE_ALL)) {
				tradeMode = "";
			}
			queryMap.put("tradeMode", tradeMode);
			queryMap.put("startTime", startTime);
			queryMap.put("endTime", endTime);
			records = sqlSession.selectList(getSqlName(SQLNAME_FIND_PART_REFUND_RECORD), queryMap);
		} catch (Exception e) {
			logger.error(String.format("门诊部分退费订单下载查询接口出错!语句：%s", getSqlName(SQLNAME_FIND_PART_REFUND_RECORD)), e);
			throw new SystemException(String.format("门诊部分退费订单下载 查询接口出错!语句：%s", getSqlName(SQLNAME_FIND_PART_REFUND_RECORD)), e);
		}
		return records;
	}

	@Override
	public ClinicRecord findByOrderNoAndHospitalCode(Map<String, Object> map) {
		String orderNo = (String) map.get("orderNo");
		String hospitalCode = (String) map.get("hospitalCode");
		String hashTableName = (String) map.get("hashTableName");
		return findByOrderNoAndHospitalCode(orderNo, hospitalCode, hashTableName);
	}

	@Override
	public List<ClinicRecord> findPaidRecords(ClinicQueryVo vo) {
		List<ClinicRecord> list = null;
		try {
			Assert.notNull(vo.getOpenId(), "openId不能为空！");
			Assert.notNull(vo.getHashTableName(), "hashTableName不能为空！");
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_PAID_RECORDS), vo);
		} catch (Exception e) {
			logger.error(String.format("通过条件查找缴费订单出错!语句：%s", getSqlName(SQLNAME_FIND_PAID_RECORDS)), e);
			throw new SystemException(String.format("通过条件查找缴费订单出错!语句：%s", getSqlName(SQLNAME_FIND_PAID_RECORDS)), e);
		}
		return list;
	}

	@Override
	public List<ClinicRecord>
			findPaidRecordByOpenIdAndIdNo(String openId, String idNo, String clinicTableName, String medicalcardTableName) {
		List<ClinicRecord> list = null;
		try {
			Assert.notNull(openId, "openId不能为空！");
			Assert.notNull(idNo, "IdNo不能为空！");
			Assert.notNull(clinicTableName, "clinicTableName不能为空！");
			Assert.notNull(medicalcardTableName, "medicalcardTableName不能为空！");
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("openId", openId);
			queryMap.put("idNo", idNo);
			queryMap.put("clinicTableName", clinicTableName);
			queryMap.put("medicalcardTableName", medicalcardTableName);
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_PAID_RECORD_BY_OPEN_ID_AND_ID_NO), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过OpenId和IdNo查找缴费订单出错!语句：%s", getSqlName(SQLNAME_FIND_PAID_RECORD_BY_OPEN_ID_AND_ID_NO)), e);
			throw new SystemException(String.format("通过OpenId和IdNo查找缴费订单出错!语句：%s",
					getSqlName(SQLNAME_FIND_PAID_RECORD_BY_OPEN_ID_AND_ID_NO)), e);
		}
		return list;
	}

	@Override
	public List<ClinicRecord>
			findPaidRecordByIdNoAndIdType(String idNo, Integer idType, String clinicTableName, String medicalcardTableName) {
		List<ClinicRecord> list = null;
		try {
			Assert.notNull(idNo, "idNo不能为空！");
			Assert.notNull(idType, "idType不能为空！");
			Assert.notNull(clinicTableName, "clinicTableName不能为空！");
			Assert.notNull(medicalcardTableName, "medicalcardTableName不能为空！");
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("idNo", idNo);
			queryMap.put("idType", idType);
			queryMap.put("clinicTableName", clinicTableName);
			queryMap.put("medicalcardTableName", medicalcardTableName);
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_PAID_RECORD_BY__ID_NO_AND_ID_TYPE), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过idType和IdNo查找缴费订单出错!语句：%s", getSqlName(SQLNAME_FIND_PAID_RECORD_BY__ID_NO_AND_ID_TYPE)), e);
			throw new SystemException(String.format("通过idType和IdNo查找缴费订单出错!语句：%s",
					getSqlName(SQLNAME_FIND_PAID_RECORD_BY__ID_NO_AND_ID_TYPE)), e);
		}
		return list;
	}

	@Override
	public ClinicRecord findSavedUnpaidClinicRecord(ClinicRecord record) {
		ClinicRecord clinicRecord = null;
		try {
			Assert.notNull(record.getMzFeeId());
			Assert.notNull(record.getAppId());
			Assert.notNull(record.getOpenId());
			Assert.notNull(record.getHospitalId());
			Assert.notNull(record.getCardNo());
			clinicRecord = sqlSession.selectOne(getSqlName(SQLNAME_FIND_SAVED_UNPAID_CLINICRECORD), record);
		} catch (Exception e) {
			logger.error(String.format("查询已保存的未缴费门诊记录出错！语句：%s", getSqlName(SQLNAME_FIND_SAVED_UNPAID_CLINICRECORD)), e);
			throw new SystemException(String.format("查询已保存的未缴费门诊记录出错！语句：%s", getSqlName(SQLNAME_FIND_SAVED_UNPAID_CLINICRECORD)), e);
		}
		return clinicRecord;
	}

	@Override
	public void updateCoverOrder(ClinicRecord record) {
		try {
			Assert.notNull(record.getHashTableName());
			sqlSession.update(getSqlName(SQLNAME_UPDATE_COVER_ORDER), record);
		} catch (Exception e) {
			logger.error(String.format("使用新的订单信息覆盖更新门诊记录出错！语句：%s", getSqlName(SQLNAME_UPDATE_COVER_ORDER)), e);
			throw new SystemException(String.format("使用新的订单信息覆盖更新门诊记录出错！语句：%s", getSqlName(SQLNAME_UPDATE_COVER_ORDER)), e);
		}
	}

	@Override
	public List<ClinicRecord> findListByProcedure(Map map) {
		try {
			//logger.info("----map:{}", JSON.toJSONString(map));
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_LIST_BY_PROCEDURE), map);
		} catch (Exception e) {
			logger.error(String.format("根据参数查询订单视图出错！语句：%s", getSqlName(SQLNAME_FIND_LIST_BY_PROCEDURE)), e);
			throw new SystemException(String.format("根据参数查询订单视图出错！语句：%s", getSqlName(SQLNAME_FIND_LIST_BY_PROCEDURE)), e);
		}
	}
}
