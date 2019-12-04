/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.outside.service;

import java.util.List;

import com.yxw.commons.dto.outside.OrdersQueryResult;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.mobileapp.invoke.dto.Response;
import com.yxw.mobileapp.invoke.dto.inside.RgParams;
import com.yxw.mobileapp.invoke.dto.outside.RegOrdersQueryResult;

/**
 * @Package: com.yxw.mobileapp.biz.outside.service
 * @ClassName: InterfaceService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年12月21日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface InterfaceService {
	/**
	 * 停诊取消挂号
	 * @param record
	 */
	public void stopRegisterDealPay(RegisterRecord record);

	/**
	 * 挂号订单查询 （根据支付时间）
	 * @param hospitalId
	 * @param branchCode
	 * @param tradeMode
	 * @param startTime
	 * @param endTime
	 * @param hashTableName
	 * @param orderMode
	 * @return
	 */
	public List<OrdersQueryResult> queryRegisterRecord(String hospitalId, String branchCode, String tradeMode, String startTime,
			String endTime, String hashTableName, String orderMode);

	/**
	 * 门诊订单查询 （根据支付时间）
	 * @param hospitalId
	 * @param branchCode
	 * @param tradeMode
	 * @param startTime
	 * @param endTime
	 * @param hashTableName
	 * @param orderMode
	 * @return
	 */
	public List<OrdersQueryResult> queryClinicRecord(String hospitalId, String branchCode, String tradeMode, String startTime,
			String endTime, String hashTableName, String orderMode);

	/**
	 * 住院押金补缴查询（根据支付时间）
	 * @param hospitalId
	 * @param branchCode
	 * @param tradeMode
	 * @param startTime
	 * @param endTime
	 * @param hashTableName
	 * @param orderMode
	 * @return
	 */
	public List<OrdersQueryResult> queryDepositRecord(String hospitalId, String branchCode, String tradeMode, String startTime,
			String endTime, String hashTableName, String orderMode);

	/**
	 * 查询挂号订单,根据就诊日期查询
	 * 
	 * @param hospitalId
	 * @param startTime
	 * @param endTime
	 * @param hashTableName
	 * @param orderMode
	 * @return
	 */
	public List<RegOrdersQueryResult> queryRegisterRecordByScheduleDate(String hospitalId, String branchCode, String tradeMode,
			String startDate, String endDate, String hashTableName, String regType);

	/**
	 * 挂号原渠道退费业务
	 * @param records
	 * @param rg
	 * @return
	 */
	public Response refundRegisterOriginalChannel(RegisterRecord records, RgParams rg);

	/**
	 * 门诊原渠道退费业务 全部退费
	 * @param records
	 * @param rg
	 * @return
	 */
	public Response refundClinicOriginalChannel(ClinicRecord records, RgParams rg);

	/**
	 * 门诊原渠道退费业务 部分退费
	 * @param records
	 * @param rg
	 * @return
	 */
	public Response refundClinicOriginalChannelPart(ClinicRecord records, RgParams rg);

	/**
	 * 住院原渠道退费业务 全额退费
	 * @param record
	 * @param rgParams
	 * @return
	 */
	//	public Response refundDepositOriginalChannel(DepositRecord record, RgParams rgParams);

	/**
	 * 住院原渠道退费业务 部分退费
	 * @param record
	 * @param rgParams
	 * @return
	 */
	//	public Response refundDepositOriginalChannelPart(DepositRecord record, RgParams rgParams);
}
