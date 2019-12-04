package com.yxw.mobileapp.biz.clinic.dao;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.mobileapp.biz.clinic.vo.ClinicQueryVo;

public interface ClinicRecordDao extends BaseDao<ClinicRecord, String> {
	/**
	 * 更新缴费记录状态
	 * @param record
	 */
	public void updateRecordStatusByOrderNoAndOpenId(ClinicRecord record);

	/**
	 * 根据就订单号，查询订单
	 * @param record
	 * @return
	 */
	public ClinicRecord findByOrderNo(ClinicRecord record);

	/**
	 * 根据订单号，医院代码查找订单
	 * @param orderNo
	 * @param hospitalCode
	 * @param hashTableName
	 * @return
	 */
	public ClinicRecord findByOrderNoAndHospitalCode(String orderNo, String hospitalCode, String hashTableName);

	/**
	 * 根据订单号，医院代码查找订单
	 * @param map
	 * @return
	 */
	public ClinicRecord findByOrderNoAndHospitalCode(Map<String, Object> map);

	/**
	 * 查找所有的支付订单
	 * @param record
	 * @return
	 */
	public List<ClinicRecord> findPaidClinicRecord(ClinicRecord record);

	/**
	 * 根据交易订单号查询订单 
	 * 2015年8月11日 14:58:01 周鉴斌增加，用于窗口退费
	 * @param orderNo 查询条件
	 * @param hashTableName 表名
	 * @return
	 */
	public ClinicRecord findPaidRecordByOrderNo(String orderNo, List<String> hashTableName);

	/**
	 * 根据交易订单号查询门诊订单 
	 * @param hospitalId
	 * @param startTime
	 * @param endTime
	 * @param hashTableName
	 * @return
	 */
	public List<ClinicRecord> findDownPaidRecord(String hospitalId, String branchCode, String tradeMode, String startTime, String endTime,
			String hashTableName);

	/**
	 * 根据交易id查询订单 
	 * 2015年9月14日 11:51:30 周鉴斌增加，用于订单管理查询日志
	 * @return
	 */
	public ClinicRecord findClinicRecordByIdAndHashTableName(Map<String, String> params);

	/**
	 * 查找异常订单
	 * @param hashTableName
	 * @return
	 */
	public List<ClinicRecord> findAllNeedHandleExceptionRecords(String hashTableName);

	/**
	 * 查找部分退费订单记录
	 * @param hospitalId
	 * @param branchCode
	 * @param tradeMode
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ClinicRecord>
			findPartRefundRecord(String hospitalId, String branchCode, String tradeMode, String startTime, String endTime);

	/**
	 * 根据条件获取缴费记录。openId不能为空
	 * @param record
	 * @return
	 */
	public List<ClinicRecord> findPaidRecords(ClinicQueryVo vo);

	/**
	 * 根据OpenId 和IdNo 查找交易订单（查本人的就诊卡）			--------------- 旧版健康管理
	 * @param openId
	 * @param hashTableName
	 * @return
	 */
	public List<ClinicRecord>
			findPaidRecordByOpenIdAndIdNo(String openId, String idNo, String clinicTableName, String medicalcardTableName);

	/**
	 * 根据IdType和IdNo 查找交易订单(查本人的就诊卡)			--------------- 新版健康管理
	 * @param idNo
	 * @param idType
	 * @param clinicTableName
	 * @param medicalcardTableName
	 * @return
	 */
	public List<ClinicRecord>
			findPaidRecordByIdNoAndIdType(String idNo, Integer idType, String clinicTableName, String medicalcardTableName);

	public ClinicRecord findSavedUnpaidClinicRecord(ClinicRecord record);

	public void updateCoverOrder(ClinicRecord record);

	public List<ClinicRecord> findListByProcedure(Map map);
}
