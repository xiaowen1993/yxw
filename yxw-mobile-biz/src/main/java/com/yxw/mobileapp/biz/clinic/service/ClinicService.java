package com.yxw.mobileapp.biz.clinic.service;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.mobileapp.biz.clinic.vo.ClinicQueryVo;

public interface ClinicService {
	/**
	 * 新平台多用户
	 * 获取待缴费列表
	 * @param medicalCards
	 * @return
	 */
	public List<Map<String, Object>> getPayList(List<MedicalCard> medicalCards);

	/**
	 * 查找某人的缴费记录(patCardType, patCardNo, patCardName, HospitalCode, branchHospitalCode, payStatus, clinicStatus, beginTime, endTime)
	 * @param medicalCard
	 * @return
	 */
	public List<ClinicRecord> getPaidList(ClinicRecord record, Integer queryMonths);

	public void updateOrderPayInfo(ClinicRecord record);

	/**
	 * 通过订单号找到缴费订单
	 * @param orderNo
	 * @return
	 */
	public ClinicRecord findRecordByOrderNo(String orderNo);

	/**
	 * 更新异常订单信息
	 * @param record
	 */
	public void updateExceptionRecord(ClinicRecord record);

	/**
	 * 通过订单号的hash值找到挂号订单
	 * @param orderNoHash
	 * @return
	 */
	public ClinicRecord findRecordByOrderNoHash(String orderNoHash);

	/**
	 * 保存订单
	 * @param record
	 */
	public void saveRecord(ClinicRecord record);

	/**
	 * 将订单入库、且存到缓存中
	 * @param record
	 * @return
	 */
	public void saveRecordInfo(ClinicRecord record);

	/**
	 * 查询订单
	 * @param record
	 * @return
	 */
	public ClinicRecord getClinicRecord(ClinicRecord record);

	/**
	 * 构建支付信息
	 * @param record
	 * @return
	 */
	//	public PayParamsVo buildPayParam(ClinicRecord record);

	/**
	 * 构建支付信息
	 * @param record
	 * @return
	 */
	public Object buildPayInfo(ClinicRecord record);

	/**
	 * 构建退费信息
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param record
	 * @param invokeType
	 * @return
	 */
	public Object buildRefundInfo(ClinicRecord record);

	/**
	 * 构建订单查询信息
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月14日 
	 * @param record
	 * @return
	 */
	public Object buildOrderQueryInfo(ClinicRecord record);

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
	 * 查找所有需要处理的异常订单
	 * @return
	 */
	public List<ClinicRecord> findAllNeedHandleExceptionRecords();

	/**
	 * 通过订单号，医院代码找到这笔订单
	 * @param orderNo
	 * @param hospitalCode
	 * @return
	 */
	public ClinicRecord findPaidRecordByOrderNo(String orderNo, String hospitalCode);

	/**
	 * 查找部分退费的订单（订单查询）
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
	 * 通过条件找已缴费记录
	 * 必要： openId -- 确定表
	 * 可选： hospitalCode
	 * 	    hospitalId
	 * 		id -- 如果使用Id则只有一个返回，目前不支持多个id的拼接。
	 * 		idNo + idType
	 * 		mzFeeId(配合hospitalCode)
	 * @param record
	 * @return
	 */
	public List<ClinicRecord> findPaidRecords(ClinicQueryVo vo);

	/**
	 * 通过OpenID查找下面的自己的门诊缴费记录
	 * @param openId
	 * @return
	 */
	public List<ClinicRecord> findPaidRecordByIdNoAndOpenId(String openId, String idNo);

	/**
	 * 通过idNo和idType查询所有的该身份证的缴费记录
	 * @param idNo
	 * @param idType
	 * @return
	 */
	public List<ClinicRecord> findPaidRecordByIdTypeAndIdNo(String idNo, Integer idType);

	public ClinicRecord findSavedUnpaidClinicRecord(ClinicRecord record);

	/**
	 * 覆盖未支付的订单。
	 * 适用于未支付，又重新下单的情况 - 数据库，以及缓存clinic:xxx这种情况，但是，如果订单是异常状态，则重新开一笔订单（在查询的时候加上状态）
	 * @param newOrder			// 新订单
	 * @param originalOrder		// 旧订单
	 */
	public void coverUnpaidRecord(ClinicRecord newOrder, ClinicRecord originalOrder);

	public List<ClinicRecord> findListByProcedure(Map map);

}
