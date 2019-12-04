/**
 * 
 */
package com.yxw.mobileapp.biz.register.service;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.commons.vo.platform.register.SimpleRecord;

/**
 * @Package: com.yxw.mobileapp.biz.register.service
 * @ClassName: RegisterService
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface RegisterService {
	/**
	 * 订单号查找挂号记录
	 * @param orderNo
	 * @return
	 */
	public List<RegisterRecord> queryByMedicalCard(MedicalCard card);

	/**
	 * 更新挂号记录的支付方式
	 * @param orderNo
	 * @param openId
	 */
	public void updateTradeMode(RegisterRecord record);

	/**
	 * 更新挂号状态
	 * @param orderNo
	 * @param regStatus
	 * @param isException
	 */
	public void updateRecordStatusByOrderNo(String orderNo, Integer regStatus, Boolean isException);

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
	 * 查询预约中的挂号记录
	 * @return
	 */
	public List<SimpleRecord> findHavingRecord();

	/**
	 * 查询已预约但未支付的的挂号记录
	 * @return
	 */
	public List<SimpleRecord> findNotPayHadRecord();

	/**
	 * 查询未支付的的挂号记录
	 * @return
	 */
	public List<SimpleRecord> findNotPayRecord();

	/**
	 * 更新异常挂号记录的信息
	 * @param record
	 * key:
	 */
	public void updateExceptionRecord(ExceptionRecord record);

	/**
	 * 构建微信退费信息
	 * @param record
	 * @param invokeType 调用类型  
	 * 		BizConstant.INVOKE_TYPE_WEB_REQ
	 *      BizConstant.INVOKE_TYPE_SERVICE_REQ
	 * @return
	 */
	//	public WechatPayRefund buildWechatRefundInfo(RegisterRecord record, String invokeType);

	/**
	 * 构建支付宝退费信息 
	 * @param record
	 * @param invokeType 调用类型  
	 * 		BizConstant.INVOKE_TYPE_WEB_REQ
	 *      BizConstant.INVOKE_TYPE_SERVICE_REQ 
	 * @return
	 */
	//	public AlipayRefund buildAliRefundInfo(RegisterRecord record, String invokeType);
	/**
	 * 构建退费信息 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param record
	 * @param invokeType
	 * @return
	 */
	public Object buildRefundInfo(RegisterRecord record);

	/** 
	 * 构建订单查询信息
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月14日 
	 * @param convertRecord
	 * @return 
	 */
	public Object buildOrderQueryInfo(RegisterRecord record);

	/**
	 * 保存挂号记录
	 * @param record
	 * @return  key:isSuccess / msg  / data    
	 */
	public void saveRecordInfo(RegisterRecord record);

	/**
	 * 保存挂号记录和订单信息
	 * @param record
	 * @return  key:isSuccess / msg  / data    
	 * public void saveRecordAndOrder(RegisterRecord record, RegisterOrder order);
	 */

	/**
	 * 第3方支付成功后更新挂号记录和订单信息
	 * @param record
	 * @param order
	 * public void updateStatusForPay(RegisterRecord record, RegisterOrder order);
	 */

	/**
	 * 第3方支付后更新挂号记录
	 * @param record
	 * @param order
	 */
	public void updateStatusForPay(RegisterRecord record);

	/**
	 * 保存挂号记录并生成订单
	 * @param record
	 * @return
	 */
	public Map<String, Object> saveRegisterInfo(RegisterRecord record);

	/**
	 * 根据订单号  删除订单信息和挂号信息
	 * @param orderNo
	 */
	public void delRecordAndOrder(String orderNo, String openId);

	/**
	 * 更新挂号记录的状态信息
	 * @param record
	 */
	public void updateRecordStatus(RegisterRecord record);

	/**
	 * 订单号查找挂号记录
	 * @param orderNo
	 * @return
	 */
	public RegisterRecord findRecordByOrderNo(String orderNo);

	/**
	 * 医院退费后更新记录
	 * @param record
	 */
	public void updateRecordForHisRefund(RegisterRecord record);

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
	 * 构建支付所需的参数
	 * @param record
	 * @return
	 */
	//	public PayParamsVo buildPayParam(RegisterRecord record);

	/**
	 * 根据平台订单号及表名查询订单 
	 * 2015年7月13日 17:14:01 周鉴斌增加，用于停诊
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
	 * 是否符合取消挂号 次数限制
	 * @param record
	 * @param ruleRegister  
	 * @return  map-keys:BizConstant.CHECK_IS_VALID_RES_KEY  BizConstant.METHOD_INVOKE_RES_MSG_KEY
	 */
	public Map<String, Object> checkValidCacleTime(RegisterRecord record, RuleRegister ruleRegister);

	/**
	 * 是否符合取消挂号 时间限制
	 * @param record
	 * @param ruleRegister
	 * @return map-keys:BizConstant.CHECK_IS_VALID_RES_KEY  BizConstant.METHOD_INVOKE_RES_MSG_KEY
	 */
	public Map<String, Object> checkValidCacleDateTime(RegisterRecord record, RuleRegister ruleRegister);

	/**
	 * 构建支付信息
	 * @param record
	 * @return
	 */
	public Object buildPayInfo(RegisterRecord record);

	/**
	 * 订单下载查询接口 挂号
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
	 * 查询所有需要处理的异常记录
	 * @return
	 */
	public List<RegisterRecord> findAllNeedHandleExceptionRecord();

	/**
	 * 通过openId, idNo找到挂号记录				----- 旧版健康管理
	 * @param openId
	 * @param idNo
	 * @return
	 */
	public List<RegisterRecord> findRecordsByOpenIdAndIdNo(String openId, String idNo);

	/**
	 * 通过idType, idNo找挂号记录				----- 新版健康管理
	 * @param idType
	 * @param idNo
	 * @return
	 */
	public List<RegisterRecord> findRecordsByIdTypeAndIdNo(Integer idType, String idNo);

	/**
	 * 通过openId和appCode找到这个账号近三个月的挂号记录
	 * @param openId
	 * @param appCode
	 * @return
	 */
	public List<RegisterRecord> findRecordsByOpenIdAndAppCode(String openId, String appCode, String hospitalCode, long beginTime,
			long endTime);

	/**
	 * 获取所有的当班记录，日期为当天（轮询的当天）
	 * key1 hospitalCode
	 * key2 platformType, value2 挂号记录列表
	 * @return
	 */
	public Map<String, List<RegisterRecord>> findAllTodayRecords();

	/**
	 * 获取所有的当班记录，日期为当天（轮询的第二天）
	 * key hospitalCode
	 * value 挂号记录列表
	 * @return
	 */
	public Map<String, List<RegisterRecord>> findAllTomorrowRecords();

	public RegisterRecord findRecordFromDBByOrderNoOpenId(String orderNo);

	public List<RegisterRecord> findListByProcedure(Map map);

	public List<RegisterRecord> findStopListByProcedure(Map map);
}
