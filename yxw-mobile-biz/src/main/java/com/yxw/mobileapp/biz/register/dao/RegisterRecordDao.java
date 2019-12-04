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
package com.yxw.mobileapp.biz.register.dao;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.commons.vo.platform.register.SimpleRecord;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.mobileapp.biz.register.dao
 * @ClassName: RegisterDao
 * @Statement: <p>前端业务操作挂号记录的Dao接口声明</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface RegisterRecordDao extends BaseDao<RegisterRecord, String> {
	/**
	 * 更新挂号记录的状态
	 * @param record
	 */
	public void updateRecordStatus(RegisterRecord record);

	/**
	 * 根据订单编号删除订单
	 * @param orderNo
	 * @return
	 */
	public void deleteByOrderNo(String orderNo, String openId);

	/**
	 * 订单编号查询挂号记录信息
	 * @param orderNo
	 * @param openId
	 * @return
	 */
	public RegisterRecord findByOrderNo(String orderNo);

	/**
	 * 根据医疗卡得到对应的挂号记录
	 * @param card
	 * @return
	 */
	public List<RegisterRecord> findByMedicalCard(MedicalCard card);

	/**
	 * 某张就诊卡大于overTime时间点的 某一或多个状态的挂号记录 
	 * @param hospitalId
	 * @param branchHospitalId
	 * @param openId
	 * @param cardNo   就诊人卡号
	 * @param regStatuses  一个或多个挂号记录状态值  参见RegisterConstant.SATE_XXXXX
	 * @param overTime
	 * @return
	 */
	public List<RegisterRecord> findOverTimeRecords(String hospitalId, String branchHospitalId, String openId, String cardNo,
			List<Integer> regStatuses, Long overTime);

	/**
	 * 更新挂号记录的regSatus
	 *  注：更新挂号记录为支付超时取消状态时  请使用updateRecordPayTimeOutStatus
	 * @param regStatus
	 * @param recordIds
	 */
	public void updateRegisterRecordSatuts(Integer regStatus, List<String> recordIds, String hashTableName);

	/**
	 * 更新挂号记录为支付超时取消  此时需要判断是否支付  对于支付的不更新  避免出现在超时轮询处理中支付完成的情况
	 * @param recordIds
	 * @param hashTableName
	 */
	public void updateRecordPayTimeOutStatus(List<String> recordIds, String hashTableName);

	/**
	 * 查询指定recordIds中未支付的挂号记录Id
	 * @param recordIds
	 * @param hashTableName  hash子表名
	 * @return
	 */
	public List<String> queryNotPayMentByIds(List<String> recordIds, String hashTableName);

	/**
	 * 查询指定hash子表中的挂号记录
	 * @param hashTableName
	 * @param regStatus   挂号记录状态
	 * @param payStatus   支付状态
	 * @return
	 */
	public List<SimpleRecord> findRegisterRecord(String hashTableName, Integer regSatus, Integer payStatus);

	/**
	 * 查询必须支付模式下的(payStatus)预约中的记录 + 暂不支付模式/在线不支付模式下(payStatus)已预约
	 * @param hashTableName
	 * @param payStatus
	 * @return
	 */
	public List<SimpleRecord> findPayStatusRecord(String hashTableName, Integer payStatus);

	/**
	 * 更新异常挂号记录的信息
	 * @param record
	 * key:
	 */
	public void updateExceptionRecord(ExceptionRecord record);

	/**
	 * 写入订单的退费号
	 * @param orderNo
	 */
	public void updateRefundOrderNo(String orderNo, String refundOrderNo, String openId);

	/**
	 * 第3方交易平台退费后更新记录
	 * @param record
	 */
	public void updateRecordForAgtRefund(RegisterRecord record);

	/**
	 * 第3方交易平台支付后更新
	 * @param record
	 */
	public void updateRecordForAgtPayment(RegisterRecord record);

	/**
	 * 根据平台订单号及挂号表查询订单 
	 * 2015年7月13日 17:14:01 周鉴斌增加，用于停诊对外接口
	 * @param condition 查询条件
	 * @param hashTableName 表名
	 * @return
	 */
	public List<RegisterRecord> findStopRegisterRecord(String condition, String hashTableName);

	/**
	 * 根据his订单号及挂号表查询订单 
	 * 2015年7月13日 17:14:01 周鉴斌增加，用于停诊轮询
	 * @param condition 查询条件
	 * @param hashTableName 表名
	 * @return
	 */
	public List<RegisterRecord> findStopRegisterRecordForPoll(String condition, String hashTableName);

	/**
	 * 根据交易订单号查询订单 
	 * 2015年8月11日 14:58:01 周鉴斌增加，用于窗口退费
	 * @param condition 查询条件
	 * @param hashTableName 表名
	 * @return
	 */
	public RegisterRecord findStopRegisterRecordByOrderNo(String orderNo, List<String> hashTableName);

	/**
	 * 订单下载  查询接口 挂号
	 * @param hospitalId
	 * @param startTime
	 * @param endTime
	 * @param hashTableName
	 * @return
	 */
	public List<RegisterRecord> findDownRegisterRecord(String hospitalId, String branchCode, String tradeMode, String startTime,
			String endTime, String hashTableName);

	/**
	 * 订单下载查询接口 根据就诊日期查询
	 * @param hospitalId
	 * @param branchCode
	 * @param regType
	 * @param startDate
	 * @param endDate
	 * @param hashTableName
	 * @return
	 */
	public List<RegisterRecord> findDownRegisterRecordByScheduleDate(String hospitalId, String branchCode, String tradeMode,
			String regType, String startDate, String endDate, String hashTableName);

	/**
	 * @param tableName
	 * @return
	 */
	public List<ExceptionRecord> findAllNeedHandleExceptionRecord(String tableName);

	/**
	 * 根据交易id查询订单 
	 * 2015年9月14日 11:51:30 周鉴斌增加，用于订单管理查询日志
	 * @return
	 */
	public RegisterRecord findRegisterRecordByIdAndHashTableName(Map<String, String> params);

	/**
	 * 根据订单号全表查询订单
	 * @param orderNo
	 * @return
	 */
	public RegisterRecord findAllByOrderNo(Map<String, Object> params);

	/**
	 * 更新挂号记录的支付方式
	 * @param orderNo
	 * @param openId
	 */
	public void updateTradeMode(RegisterRecord record);

	/**
	 * 通过openid，idNo 找到挂号记录 			----- 旧健康管理
	 * @param openId
	 * @param idNo
	 * @param registerTableName
	 * @param medicalcardTableName
	 * @return
	 */
	public List<RegisterRecord>
			findRecordsByOpenIdAndIdNo(String openId, String idNo, String registerTableName, String medicalcardTableName);

	/**
	 * 通过idType, idNo 找挂号记录			----- 新健康管理
	 * @param idType
	 * @param idNo
	 * @param registerTableName
	 * @param medicalcardTableName
	 * @return
	 */
	public List<RegisterRecord> findRecordsByIdTypeAndIdNo(Integer idType, String idNo, String registerTableName,
			String medicalcardTableName);

	/**
	 * 查询预约某天的记录
	 * @param scheduleDay
	 * @param hashTableName
	 * @return
	 */
	public List<RegisterRecord> findRecordsForVisitWarn(String scheduleDate, String hashTableName);

	/**
	 * 通过OpenId和AppCode查找挂号记录
	 * @param openId
	 * @param appCode
	 * @param hashTableName
	 * @return
	 */
	public List<RegisterRecord> findRecordsByOpenIdAndAppCode(String openId, String appCode, String hospitalCode, long beginTime,
			long endTime, String hashTableName);

	public List<RegisterRecord> findListByProcedure(Map map);

	public List<RegisterRecord> findStopListByProcedure(Map map);
}
