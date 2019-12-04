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
package com.yxw.mobileapp.biz.outside.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.OutsideConstant;
import com.yxw.commons.constants.biz.ReceiveConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.dto.outside.OrdersQueryResult;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicPartRefundRecord;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.entity.platform.payrefund.AlipayRefund;
import com.yxw.commons.entity.platform.payrefund.AlipayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefund;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefundResponse;
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.framework.common.ServerNoGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.framework.exception.SystemException;
import com.yxw.interfaces.constants.PaymentStatusType;
import com.yxw.interfaces.service.YxwCommService;
import com.yxw.interfaces.vo.Request;
import com.yxw.interfaces.vo.register.RegRecord;
import com.yxw.interfaces.vo.register.RegRecordRequest;
import com.yxw.interfaces.vo.register.appointment.RefundRegRequest;
import com.yxw.mail.service.MailService;
import com.yxw.mobileapp.biz.clinic.service.ClinicPartRefundService;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.outside.service.InterfaceService;
import com.yxw.mobileapp.biz.register.entity.StopRegisterRecord;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.biz.register.service.StopRegisterRecordService;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;
import com.yxw.mobileapp.invoke.dto.Response;
import com.yxw.mobileapp.invoke.dto.inside.RgParams;
import com.yxw.mobileapp.invoke.dto.outside.RegOrdersQueryResult;
import com.yxw.mobileapp.invoke.dto.outside.ReturnGeneralResult;
import com.yxw.mobileapp.invoke.impl.OutsideInvokeServiceImpl;
import com.yxw.mobileapp.invoke.thread.OrdersQueryCallable;
import com.yxw.mobileapp.invoke.thread.RegOrdersQueryCallable;
import com.yxw.payrefund.service.RefundService;
import com.yxw.payrefund.utils.TradeCommonHoder;
import com.yxw.utils.DateUtils;

/**
 * @Package: com.yxw.mobileapp.biz.outside.service.impl
 * @ClassName: InterfaceServiceImpl
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年12月28日
 * @modify By:
 * @modify Date:	
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "interfaceService")
public class InterfaceServiceImpl implements InterfaceService {
	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static Logger logger = LoggerFactory.getLogger(InterfaceServiceImpl.class);
	private RegisterService registerService = SpringContextHolder.getBean(RegisterService.class);
	private ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);
	//	private InpatientService inpatientService = SpringContextHolder.getBean(InpatientService.class);
	private RegisterBizManager registerBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
	//	private AttachService attachService = SpringContextHolder.getBean(AttachService.class);
	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
	private CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);

	private StopRegisterRecordService stopRegisterRecordService = SpringContextHolder.getBean(StopRegisterRecordService.class);

	private MailService mailService = SpringContextHolder.getBean(MailService.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@Override
	public void stopRegisterDealPay(RegisterRecord record) {

		Date scheduleDate = record.getScheduleDate();
		Date beginTimeDate = record.getBeginTime();
		logger.info("停诊：scheduleDate:{}, beginTimeDate:{}, orderno:{}", scheduleDate, beginTimeDate, record.getOrderNo());
		Calendar date = Calendar.getInstance();
		Calendar time = Calendar.getInstance();
		date.setTime(scheduleDate);
		if (beginTimeDate != null) {
			time.setTime(beginTimeDate);
			date.add(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
			date.add(Calendar.MINUTE, time.get(Calendar.MINUTE));
			date.add(Calendar.SECOND, time.get(Calendar.SECOND));
		} else {
			date.add(Calendar.HOUR_OF_DAY, 0);
			date.add(Calendar.MINUTE, 0);
			date.add(Calendar.SECOND, 0);
		}
		logger.info("record{} ,date:{}", record.getOrderNo(), df.format(date.getTime()));
		Date now = new Date();
		String hospitalCode = record.getHospitalCode();
		RuleEdit ruleEdit = ruleConfigManager.getRuleEditByHospitalCode(hospitalCode);

		String errorMsg = null;

		StopRegisterRecord stopRegisterRecord = new StopRegisterRecord();
		stopRegisterRecord.setLaunchTime(System.currentTimeMillis());
		stopRegisterRecord.setOrderNo(record.getOrderNo());
		stopRegisterRecord.setHasTrade(record.getPayStatus() == OrderConstant.STATE_PAYMENT ? (byte) 1 : (byte) 0);
		stopRegisterRecord.setHandleState(1);
		try {
			// 是否需要增加出诊时间是否大于当前时间的限制 0 不需要 1 需要
			int overBeginTimeBanStopReg = ruleEdit.getOverBeginTimeBanStopReg();
			//当出诊时间大于当前时间时,患者还未就诊,可以进行取消号源退费等操作,否则不能随意退费
			if (overBeginTimeBanStopReg == 0 || date.getTime().getTime() > now.getTime()) {
				logger.info("record.getPayStatus():{},orderno:{}", record.getPayStatus(), record.getOrderNo());
				//				commonMsgPushSvc.pushMsg(record, CommonMsgPushService.ACTION_TYPE_STOP_VISIT);

				commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_STOP_VISIT);

				stopRegisterRecord.setPushState(1);

				if (record.getPayStatus() == OrderConstant.STATE_PAYMENT) {// 如果订单已支付，则执行退费操作
					Map<String, Object> resMap = new HashMap<String, Object>();
					String msgLog = "";// 日志消息
					int stopRegNeedInvokeAckRefund = ruleEdit.getStopRegNeedInvokeAckRefund();
					Integer regType = record.getRegType();
					String branchCode = record.getBranchHospitalCode();
					String hisOrdNum = record.getHisOrdNo();
					String psOrdNum = record.getOrderNo();
					String refundMode = String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode()));
					String refundAmout = String.valueOf(record.getRealRegFee() + record.getRealTreatFee());
					Boolean isCanRefundSuccess = true;
					// 判断是否调用退费确认接口 【1-调用】
					if (stopRegNeedInvokeAckRefund == 1) {
						Map<String, Object> interfaceResultMap = new HashMap<String, Object>();
						/** 确认是否能退费 第三方退费开始 **/
						logger.info("停诊前调用医院退费确认接口，订单号:{}", record.getOrderNo());
						if (regType == null) {
							regType = RegisterConstant.REG_TYPE_APPOINTMENT;
						}
						if (regType.intValue() == RegisterConstant.REG_TYPE_APPOINTMENT) {
							// 预约退费确认
							interfaceResultMap =
									registerBizManager.refundRegConfirm(hospitalCode, branchCode, psOrdNum, hisOrdNum, refundMode,
											refundAmout);
						} else {
							// 当班退费确认
							interfaceResultMap =
									registerBizManager.refundCurRegConfirm(hospitalCode, branchCode, psOrdNum, hisOrdNum, refundMode,
											refundAmout);
						}
						isCanRefundSuccess = (Boolean) interfaceResultMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
						if (isCanRefundSuccess) {
							stopRegisterRecord.setAckRefundState(1);
						}
						logger.info("停诊前调用退费确认接口结果:{}，订单号：{}", isCanRefundSuccess, record.getOrderNo());
					}

					logger.info("isCanRefundSuccess:{},orderno:{}", isCanRefundSuccess, record.getOrderNo());
					if (isCanRefundSuccess) {
						/** 调用his接口 已经确认可以退费*/
						// 设置退费订单号
						String refundOrderNo = record.getRefundOrderNo();
						if (StringUtils.isEmpty(refundOrderNo)) {
							try {
								refundOrderNo =
										OrderNoGenerator.genOrderNo(String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode())),
												String.valueOf(record.getTradeMode()), OrderConstant.ORDER_TYPE_REFUND,
												BizConstant.BIZ_TYPE_REGISTER, ServerNoGenerator.getServerNoByIp(), record.getOpenId());
								record.setRefundOrderNo(refundOrderNo);
							} catch (Exception e) {
								throw new SystemException(e.getCause());
							}
						}
						logger.info("refundOrderNo : {} .", refundOrderNo);
						logger.info("开始第三方退费,orderno:{}", record.getOrderNo());
						if (record.getIsMedicarePayMents() != null
								&& record.getIsMedicarePayMents() == BizConstant.BASEDON_MEDICAL_INSURANCE_YES) {
							//							resMap = stopRegisterRefundMedicare(record);
						} else {
							resMap = stopRegisterRefund(record);
						}
						logger.info("订单号:{} 已支付，第三方退费结果：{} ", new Object[] { record.getOrderNo(), resMap });
						boolean isException = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);// 是否第三方退费异常
						boolean isSuccess = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);// 第三方退款是否成功
						if (!isException && isSuccess) {
							stopRegisterRecord.setRefundState(1);
							/** 第三方退费成功 his退费 开始 **/
							String agtRefOrdNum = resMap.get(BizConstant.TRADE_NUM_PARAM_NAME).toString();// 获取第三方返回的退款订单号
							logger.info("订单号:{} 第三方退费订单号: {}", new Object[] { record.getOrderNo(), agtRefOrdNum });
							record.setAgtRefundOrdNum(agtRefOrdNum);
							record.setPayStatus(OrderConstant.STATE_REFUND);// 设置支付状态为退款成功
							record.setRefundTime(new Date().getTime());// 设置退费时间
							record.addLogInfo("调用第三方退费接口,退款成功,下一步调用his退费接口");
							RefundRegRequest refundRegRequest = record.covertRefundRegRequestVo();
							if (record.getRegType().equals(RegisterConstant.REG_TYPE_CUR)) {
								// 当班退费
								resMap = registerBizManager.refundCurReg(hospitalCode, record.getBranchHospitalCode(), refundRegRequest);
							} else if (record.getRegType().equals(RegisterConstant.REG_TYPE_APPOINTMENT)) {
								// 预约退费
								resMap = registerBizManager.refundReg(hospitalCode, record.getBranchHospitalCode(), refundRegRequest);
							}
							logger.info("订单号:{} 已支付，his退费结果：{} ", new Object[] { record.getOrderNo(), resMap });
							isException = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);// 是否his退费异常
							if (isException) {
								// his退款异常
								errorMsg = "调用HIS退款异常";
								msgLog = errorMsg + ",无法停诊,状态变更为[STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT=19]";
								pushException(record, RegisterConstant.STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT, msgLog);
							} else {
								isSuccess = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);// his退款是否成功
								if (isSuccess) {
									stopRegisterRecord.setHisRefundState(1);
									stopRegisterRecord.setFinishState(1);
									/**
									 * his退费成功 1.获取his返回的的退费单号
									 */
									record.setRegStatus(RegisterConstant.STATE_NORMAL_STOP_CURE_CANCEL);
									record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
									record.setPayStatus(OrderConstant.STATE_REFUND);
									record.addLogInfo("调用HIS退费成功,停诊成功,状态变更为[STATE_NORMAL_STOP_CURE_CANCEL=4]");
									record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
									// 处理成功：删除停诊map集合中的当前订单对象
									//									stopRegisterExceptionCache.delStopRegOrdersByOrderNo(record.getOrderNo());
									List<Object> params = new ArrayList<Object>();
									params.add(record.getOrderNo());
									serveComm.delete(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "delStopRegOrdersByOrderNo", params);

									updateStopRegisterRecord(record);
								} else {
									msgLog = "调用HIS退款失败,查询挂号日志无果,无法停诊,状态变更为[STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT=19]";
									RegRecord regRecord = null;
									try {
										regRecord = getRegRecords(record, RegisterConstant.STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT, msgLog);
										if (regRecord != null) {
											if (regRecord.getStatus().equalsIgnoreCase(PaymentStatusType.PAID)) {
												errorMsg = "调用HIS确认退费失败";
												msgLog = errorMsg + ",无法停诊,状态变更为[STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT=19]";
												pushException(record, RegisterConstant.STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT, msgLog);
											} else {
												stopRegisterRecord.setFinishState(1);
												record.setRegStatus(RegisterConstant.STATE_NORMAL_STOP_CURE_CANCEL);
												record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
												record.setPayStatus(OrderConstant.STATE_REFUND);
												record.addLogInfo(errorMsg
														+ "调用HIS退费失败,但查询挂号日志返回非已支付状态,无须停诊,状态变更为[STATE_NORMAL_STOP_CURE_CANCEL=4]");
												record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
												// 处理成功：删除停诊map集合中的当前订单对象
												//												stopRegisterExceptionCache.delStopRegOrdersByOrderNo(record.getOrderNo());
												List<Object> params = new ArrayList<Object>();
												params.add(record.getOrderNo());
												serveComm.delete(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "delStopRegOrdersByOrderNo",
														params);

												updateStopRegisterRecord(record);
											}
										}
									} catch (Exception e) {
										logger.error("订单号:{}, msg:{}", record.getOrderNo(), e.getMessage());
									}

								}
							}
						} else {
							errorMsg = "第三方退费失败";
							logger.info("停诊发生异常，订单号：{}," + errorMsg, record.getOrderNo());
							logger.error("停诊发生异常，订单号：{}," + errorMsg, record.getOrderNo());
							// 停诊-第三方退费失败，加入到异常队列
							ExceptionRecord exceptionRecord = record.convertExceptionObj();
							exceptionRecord.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
							exceptionRecord.setHandleCount(0);
							//							exceptionCache.setAgtRefundExceptionToCache(exceptionRecord);
							List<Object> params = new ArrayList<Object>();
							params.add(exceptionRecord);
							serveComm.set(CacheType.REGISTER_EXCEPTION_CACHE, "setAgtRefundExceptionToCache", params);
						}
					} else {
						errorMsg = "调用退费确认接口异常";
						logger.info(errorMsg + "，无法停诊，订单号:{}", new Object[] { record.getOrderNo() });
						record.addLogInfo(errorMsg + "，无法停诊");
						record.setRegStatus(RegisterConstant.STATE_EXCEPTION_STOP_REFUND_HIS_CONFIRM);
						updateStopRegisterRecord(record);
					}
				} else {// 未支付，更新订单业务状态及处理结果，并且写入日志
					stopRegisterRecord.setFinishState(1);
					record.setRegStatus(RegisterConstant.STATE_NORMAL_STOP_CURE_CANCEL);
					record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
					record.addLogInfo("订单未支付,停诊成功,状态变更为[STATE_NORMAL_STOP_CURE_CANCEL=4]");
					// 处理成功：删除停诊map集合中的当前订单对象
					//					stopRegisterExceptionCache.delStopRegOrdersByOrderNo(record.getOrderNo());
					List<Object> params = new ArrayList<Object>();
					params.add(record.getOrderNo());
					serveComm.delete(CacheType.REGISTER_EXCEPTION_CACHE, "delStopRegOrdersByOrderNo", params);

					updateStopRegisterRecord(record);
				}
			} else {
				String visitTime = BizConstant.YYYYMMDDHHMM.format(date.getTime());
				errorMsg = StringUtils.join("当前时间大于就诊时间(", visitTime, ")");
				logger.info("当前时间大于就诊时间({})，无法停诊退费，此订单已过期，停诊失败。订单号:{}", new Object[] { visitTime, record.getOrderNo() });
				record.addLogInfo(StringUtils.join(errorMsg, "，无法停诊退费，此订单已过期，停诊失败。"));
				updateStopRegisterRecord(record);
			}
		} finally {
			stopRegisterRecordService.update(stopRegisterRecord);
			// 未完成停诊时，需要发邮件通知相关人员
			if (stopRegisterRecord.getFinishState() == 0 && StringUtils.isNotBlank(ruleEdit.getRecipientsForStopRegException())) {
				// 邮件标题
				StringBuilder subject = new StringBuilder();
				subject.append(record.getHospitalName()).append("挂号订单").append(record.getOrderNo()).append("执行停诊异常");

				// 邮件正文
				StringBuilder content = new StringBuilder();
				content.append("<b>异常信息: ").append(errorMsg).append("</b><br><br>");
				content.append("订单号: ").append(record.getOrderNo()).append("<br>");
				content.append("停诊发起时间: ").append(BizConstant.YYYYMMDDHHMMSS.format(stopRegisterRecord.getLaunchTime())).append("<br>");
				content.append("医院: ").append(record.getHospitalName()).append("<br>");
				content.append("就诊日期: ").append(record.getScheduleDateStr()).append("<br>");
				content.append("就诊科室: ").append(record.getDeptName()).append("<br>");
				content.append("就诊医生: ").append(record.getDoctorName()).append("<br>");
				content.append("就诊开始时间: ").append(BizConstant.HHMM.format(record.getBeginTime())).append("<br>");
				content.append("就诊结束时间: ").append(BizConstant.HHMM.format(record.getEndTime())).append("<br>");
				content.append("患者卡号: ").append(record.getCardNo()).append("<br>");
				content.append("患者姓名: ").append(record.getPatientName()).append("<br>");
				content.append("患者手机: ").append(record.getPatientMobile()).append("<br>");

				// 收件人列表
				String[] tos = ruleEdit.getRecipientsForStopRegException().split(",");
				try {
					mailService.sendHtml(subject.toString(), content.toString(), tos);
				} catch (Exception e) {
					logger.error("发送停诊异常邮件时发生异常，原因为：{}", e.getCause());
				}
			}
		}

	}

	private void updateStopRegisterRecord(RegisterRecord record) {
		logger.info("停诊保存结果:{}", JSON.toJSONString(record));
		registerService.updateRecordStatus(record);
		//		recordCache.updateRecordToCache(record);
		List<Object> params = new ArrayList<Object>();
		params.add(record);
		serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordToCache", params);
	}

	@Override
	public List<OrdersQueryResult> queryRegisterRecord(String hospitalId, String branchCode, String tradeMode, String startTime,
			String endTime, String hashTableName, String orderMode) {
		List<OrdersQueryResult> results = new ArrayList<OrdersQueryResult>();
		// 设置线程池的数量
		ExecutorService collectExec =
				Executors.newFixedThreadPool(OutsideInvokeServiceImpl.EXECUTOR_COUNT, new SimpleThreadFactory("ordersQuery:doBiz"));
		List<FutureTask<List<OrdersQueryResult>>> taskList = new ArrayList<FutureTask<List<OrdersQueryResult>>>();
		for (int i = 0; i < ReceiveConstant.TABLE_SIZE; i++) {
			OrdersQueryCallable queryRunnable =
					new OrdersQueryCallable(hospitalId, branchCode, tradeMode, startTime, endTime, hashTableName + "_" + ( i + 1 ),
							orderMode);
			// 创建每条指令的采集任务对象
			FutureTask<List<OrdersQueryResult>> collectTask = new FutureTask<List<OrdersQueryResult>>(queryRunnable);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}
		// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
		try {
			for (FutureTask<List<OrdersQueryResult>> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<OrdersQueryResult> collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (collectData != null && logger.isInfoEnabled()) {
					results.addAll(collectData);
					if (logger.isDebugEnabled()) {
						logger.debug("date:{} queryRegisterRecord query success.",
								new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();
		if (logger.isDebugEnabled()) {
			logger.debug("查询挂号订单:{}.", com.alibaba.fastjson.JSON.toJSONString(results));
		}
		return results;
	}

	@Override
	public List<OrdersQueryResult> queryClinicRecord(String hospitalId, String branchCode, String tradeMode, String startTime,
			String endTime, String hashTableName, String orderMode) {
		List<OrdersQueryResult> results = new ArrayList<OrdersQueryResult>();
		// 设置线程池的数量
		ExecutorService collectExec =
				Executors.newFixedThreadPool(OutsideInvokeServiceImpl.EXECUTOR_COUNT, new SimpleThreadFactory("clinicQuery:doBiz"));
		List<FutureTask<List<OrdersQueryResult>>> taskList = new ArrayList<FutureTask<List<OrdersQueryResult>>>();
		for (int i = 0; i < ReceiveConstant.TABLE_SIZE; i++) {
			OrdersQueryCallable queryRunnable =
					new OrdersQueryCallable(hospitalId, branchCode, tradeMode, startTime, endTime, hashTableName + "_" + ( i + 1 ),
							orderMode);
			// 创建每条指令的采集任务对象
			FutureTask<List<OrdersQueryResult>> collectTask = new FutureTask<List<OrdersQueryResult>>(queryRunnable);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}
		// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
		try {
			for (FutureTask<List<OrdersQueryResult>> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<OrdersQueryResult> collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (collectData != null && logger.isInfoEnabled()) {
					results.addAll(collectData);
					if (logger.isDebugEnabled()) {
						logger.debug("date:{} queryClinicRecord query success.",
								new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		// 门诊部分退费的订单下载
		List<ClinicRecord> list = clinicService.findPartRefundRecord(hospitalId, branchCode, tradeMode, startTime, endTime);
		List<OrdersQueryResult> resultList = new ArrayList<OrdersQueryResult>();
		for (ClinicRecord record : list) {
			resultList.add(record.convertToOrdersQueryResult());
		}
		results.addAll(resultList);

		if (logger.isDebugEnabled()) {
			logger.debug("查询门诊订单:{}.", com.alibaba.fastjson.JSON.toJSONString(results));
		}
		return results;
	}

	@Override
	public List<OrdersQueryResult> queryDepositRecord(String hospitalId, String branchCode, String tradeMode, String startTime,
			String endTime, String hashTableName, String orderMode) {
		List<OrdersQueryResult> results = new ArrayList<OrdersQueryResult>();
		// 设置线程池的数量
		ExecutorService collectExec =
				Executors.newFixedThreadPool(OutsideInvokeServiceImpl.EXECUTOR_COUNT, new SimpleThreadFactory("depositQuery:doBiz"));
		List<FutureTask<List<OrdersQueryResult>>> taskList = new ArrayList<FutureTask<List<OrdersQueryResult>>>();
		for (int i = 0; i < ReceiveConstant.TABLE_SIZE; i++) {
			OrdersQueryCallable queryRunnable =
					new OrdersQueryCallable(hospitalId, branchCode, tradeMode, startTime, endTime, hashTableName + "_" + ( i + 1 ),
							orderMode);
			// 创建每条指令的采集任务对象
			FutureTask<List<OrdersQueryResult>> collectTask = new FutureTask<List<OrdersQueryResult>>(queryRunnable);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}
		// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
		try {
			for (FutureTask<List<OrdersQueryResult>> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<OrdersQueryResult> collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (collectData != null && logger.isInfoEnabled()) {
					results.addAll(collectData);
					if (logger.isDebugEnabled()) {
						logger.debug("date:{} queryClinicRecord query success.",
								new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		if (logger.isDebugEnabled()) {
			logger.debug("查询门诊订单:{}.", com.alibaba.fastjson.JSON.toJSONString(results));
		}
		return results;
	}

	@Override
	public List<RegOrdersQueryResult> queryRegisterRecordByScheduleDate(String hospitalId, String branchCode, String tradeMode,
			String startDate, String endDate, String hashTableName, String regType) {
		List<RegOrdersQueryResult> results = new ArrayList<RegOrdersQueryResult>();
		// 设置线程池的数量
		ExecutorService collectExec =
				Executors.newFixedThreadPool(OutsideInvokeServiceImpl.EXECUTOR_COUNT, new SimpleThreadFactory(
						"queryRegisterRecordByScheduleDate:doBiz"));
		List<FutureTask<List<RegOrdersQueryResult>>> taskList = new ArrayList<FutureTask<List<RegOrdersQueryResult>>>();
		for (int i = 0; i < ReceiveConstant.TABLE_SIZE; i++) {
			RegOrdersQueryCallable queryRunnable =
					new RegOrdersQueryCallable(hospitalId, branchCode, tradeMode, startDate, endDate, hashTableName + "_" + ( i + 1 ),
							regType);
			// 创建每条指令的采集任务对象
			FutureTask<List<RegOrdersQueryResult>> collectTask = new FutureTask<List<RegOrdersQueryResult>>(queryRunnable);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}
		// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
		try {
			for (FutureTask<List<RegOrdersQueryResult>> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<RegOrdersQueryResult> collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (collectData != null && logger.isInfoEnabled()) {
					results.addAll(collectData);
					if (logger.isDebugEnabled()) {
						logger.debug("date:{} queryRegisterRecord query success.",
								new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();
		if (logger.isDebugEnabled()) {
			logger.debug("查询挂号订单:{}.", com.alibaba.fastjson.JSON.toJSONString(results));
		}
		return results;
	}

	@Override
	public Response refundRegisterOriginalChannel(RegisterRecord records, RgParams rg) {
		Response result = new Response();
		if (records.getPayStatus() == OrderConstant.STATE_PAYMENT
				&& ( records.getRegStatus() == RegisterConstant.STATE_NORMAL_HAD || records.getRegStatus() == RegisterConstant.STATE_WINDOW_EXCEPTION_REFUND )) {// 已支付且
			// 设置退费订单号
			String refundOrderNo = records.getRefundOrderNo();
			if (StringUtils.isEmpty(refundOrderNo)) {
				try {
					refundOrderNo =
							OrderNoGenerator.genOrderNo(String.valueOf(ModeTypeUtil.getPlatformModeType(records.getAppCode())),
									String.valueOf(records.getTradeMode()), OrderConstant.ORDER_TYPE_REFUND, BizConstant.BIZ_TYPE_REGISTER,
									ServerNoGenerator.getServerNoByIp(), records.getOpenId());
					records.setRefundOrderNo(refundOrderNo);
				} catch (Exception e) {
					throw new SystemException(e.getCause());
				}
			}
			logger.info("refundOrderNo : {} .", refundOrderNo);

			Map<String, Object> resMap = new HashMap<String, Object>();
			if (records.getIsMedicarePayMents() != null && records.getIsMedicarePayMents() == BizConstant.BASEDON_MEDICAL_INSURANCE_YES) {
				//				resMap = refundRegisterMedicare(records);
			} else {
				resMap = refundRegister(records);
			}
			logger.info("支付订单号：{}, 退费订单号:{}, 第三方退费结果 : {} , .", new Object[] { refundOrderNo,
					records.getOrderNo(),
					com.alibaba.fastjson.JSON.toJSONString(resMap) });
			boolean isException = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);// 是否第三方退费异常
			if (!isException) {
				boolean isSuccess = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);// 是否第三方退费异常
				if (isSuccess) {
					String agtRefOrdNum = resMap.get(BizConstant.TRADE_NUM_PARAM_NAME).toString();// 获取第三方返回的退款订单号
					records.setAgtRefundOrdNum(agtRefOrdNum);
					records.setPayStatus(OrderConstant.STATE_REFUND);// 设置支付状态为退款成功
					records.setRegStatus(RegisterConstant.STATE_WINDOW_SUCCESSFUL_REFUND);
					records.setRefundHisOrdNo(rg.getHisNewOrdNum());// 写入his退费单号
					records.setRefundTime(new Date().getTime());
					boolean isPush =
							StringUtils.isBlank(rg.getPushType())
									|| rg.getPushType().equals(String.valueOf(ReceiveConstant.PUSH_MESSAGE_TYPE));
					//logger.info("是否推送消息: {} .", isPush);
					if (isPush) {
						//logger.info("推送消息开始：{} .", DateUtils.dateToString(new Date()));
						CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
						if (records.getRegType().intValue() == RegisterConstant.REG_TYPE_CUR) {
							commonMsgPushSvc.pushMsg(records, MessageConstant.ACTION_TYPE_REFUND_SUCCESS);
						} else {
							commonMsgPushSvc.pushMsg(records, MessageConstant.ACTION_TYPE_REFUND_SUCCESS_APPOINT);
						}
						//logger.info("推送消息结束：{} .", DateUtils.dateToString(new Date()));
					}
					ReturnGeneralResult returnGeneral = new ReturnGeneralResult(records.getRefundOrderNo(), records.getAgtRefundOrdNum());
					XStream stream = new XStream();
					stream.alias("returnGeneral", ReturnGeneralResult.class);
					result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", stream.toXML(returnGeneral));
					registerService.updateRecordStatus(records);
					//					recordCache.updateRecordToCache(records);

					List<Object> params = new ArrayList<Object>();
					params.add(records);
					serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordToCache", params);

				} else {
					result = new Response(OutsideConstant.OUTSIDE_ERROR, resMap.get(BizConstant.INTERFACE_MAP_MSG_KEY).toString());
				}
			} else {
				result = new Response(OutsideConstant.OUTSIDE_ERROR, "第三方退费失败");
			}
		} else {// 已经退费
			String resultMessage = "order [" + rg.getPsOrdNum() + "] has been a refund";
			ReturnGeneralResult returnGeneral = new ReturnGeneralResult(records.getRefundOrderNo(), records.getAgtRefundOrdNum());
			XStream stream = new XStream();
			stream.alias("returnGeneral", ReturnGeneralResult.class);
			result = new Response(OutsideConstant.OUTSIDE_OPERATED, resultMessage, stream.toXML(returnGeneral));
		}
		return result;
	}

	/**
	 * 门诊原渠道退费  全额  
	 * @param records
	 * @param rg
	 * */
	@Override
	public Response refundClinicOriginalChannel(ClinicRecord records, RgParams rg) {
		Response result = new Response();
		if (records.getPayStatus() == OrderConstant.STATE_PAYMENT
				&& ( records.getClinicStatus() == ClinicConstant.STATE_PAY_SUCCESS || records.getClinicStatus() == ClinicConstant.STATE_PERSON_HANDLE_EXCEPTION )) {// 已支付且
																																									// 已经预约或窗口退费异常，可以退费
			// 设置退费订单号
			String refundOrderNo = records.getRefundOrderNo();
			if (StringUtils.isEmpty(refundOrderNo)) {
				try {
					refundOrderNo =
							OrderNoGenerator.genOrderNo(String.valueOf(ModeTypeUtil.getPlatformModeType(records.getAppCode())),
									String.valueOf(records.getTradeMode()), OrderConstant.ORDER_TYPE_REFUND, BizConstant.BIZ_TYPE_CLINIC,
									ServerNoGenerator.getServerNoByIp(), records.getOpenId());
					records.setRefundOrderNo(refundOrderNo);
				} catch (Exception e) {
					throw new SystemException(e.getCause());
				}
			}
			logger.info("refundOrderNo : {} .", refundOrderNo);
			Map<String, Object> resMap = new HashMap<String, Object>();
			if (records.getIsMedicarePayMents() != null && records.getIsMedicarePayMents() == BizConstant.BASEDON_MEDICAL_INSURANCE_YES) {
				//				resMap = refundClinicMedicare(records);
			} else {
				resMap = refundClinic(records);

			}
			logger.info("第三方退费结果 : {} .", com.alibaba.fastjson.JSON.toJSONString(resMap));
			boolean isException = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);// 是否第三方退费异常
			if (!isException) {
				boolean isSuccess = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);// 是否第三方退费异常
				if (isSuccess) {
					String agtRefOrdNum = resMap.get(BizConstant.TRADE_NUM_PARAM_NAME).toString();// 获取第三方返回的退款订单号
					records.setAgtRefundOrdNum(agtRefOrdNum);
					records.setPayStatus(OrderConstant.STATE_REFUND);// 设置支付状态为退款成功
					records.setClinicStatus(ClinicConstant.STATE_PERSON_HANDLE_SUCCESS);
					records.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
					records.setRefundHisOrdNo(rg.getHisNewOrdNum());// 写入his退费单号
					Long refundTime = new Date().getTime();// 退费时间
					records.setRefundTime(refundTime);
					records.setUpdateTime(refundTime);

					if (logger.isDebugEnabled()) {
						logger.debug("rgParams: {}", com.alibaba.fastjson.JSON.toJSONString(rg));
					}

					boolean isPush =
							StringUtils.isBlank(rg.getPushType())
									|| rg.getPushType().equals(String.valueOf(ReceiveConstant.PUSH_MESSAGE_TYPE));
					//logger.info("是否推送消息: {} .", isPush);
					if (isPush) {
						//logger.info("推送消息开始：{} .", DateUtils.dateToString(new Date()));
						CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
						commonMsgPushSvc.pushMsg(records, MessageConstant.ACTION_TYPE_CLINIC_REFUND_SUCCESS);
						//logger.info("推送消息结束：{} .", DateUtils.dateToString(new Date()));
					}

					ReturnGeneralResult returnGeneral = new ReturnGeneralResult(records.getRefundOrderNo(), records.getAgtRefundOrdNum());
					XStream stream = new XStream();
					stream.alias("returnGeneral", ReturnGeneralResult.class);
					result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", stream.toXML(returnGeneral));
					clinicService.updateOrderPayInfo(records);
				} else {
					result = new Response(OutsideConstant.OUTSIDE_ERROR, resMap.get(BizConstant.INTERFACE_MAP_MSG_KEY).toString());
				}
			} else {
				result = new Response(OutsideConstant.OUTSIDE_ERROR, "第三方退费异常");
			}

		} else {
			String resultMessage = "order [" + rg.getPsOrdNum() + "] has been a refund";
			ReturnGeneralResult returnGeneral = new ReturnGeneralResult(records.getRefundOrderNo(), records.getAgtRefundOrdNum());
			XStream stream = new XStream();
			stream.alias("returnGeneral", ReturnGeneralResult.class);
			result = new Response(OutsideConstant.OUTSIDE_OPERATED, resultMessage, stream.toXML(returnGeneral));
		}
		return result;
	}

	/**
	 * 住院原渠道退费  全额  
	 * @param record
	 * @param rgParams
	 * */
	/*@Override
	public Response refundDepositOriginalChannel(DepositRecord record, RgParams rgParams) {
		Response result = new Response();
		if (record.getPayStatus() == OrderConstant.STATE_PAYMENT
				&& ( record.getDepositStatus() == InpatientConstant.STATE_PAY_SUCCESS || record.getDepositStatus() == InpatientConstant.STATE_PERSON_HANDLE_EXCEPTION )) {
			// 已支付且
			// 已经预约或窗口退费异常，可以退费
			// 设置退费订单号
			String refundOrderNo = record.getRefundOrderNo();
			if (StringUtils.isEmpty(refundOrderNo)) {
				try {
					refundOrderNo =
							OrderNoGenerator.genOrderNo(String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode())),
									String.valueOf(record.getTradeMode()), OrderConstant.ORDER_TYPE_REFUND, BizConstant.BIZ_TYPE_DEPOSIT,
									ServerNoGenerator.getServerNoByIp(), record.getOpenId());
					record.setRefundOrderNo(refundOrderNo);
				} catch (Exception e) {
					throw new SystemException(e.getCause());
				}
			}
			logger.info("refundOrderNo : {} .", refundOrderNo);

			Map<String, Object> resMap = refundDeposit(record);
			logger.info("第三方退费结果 : {} .", com.alibaba.fastjson.JSON.toJSONString(resMap));
			boolean isException = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);// 是否第三方退费异常
			if (!isException) {
				boolean isSuccess = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);// 是否第三方退费异常
				if (isSuccess) {
					String agtRefOrdNum = resMap.get(BizConstant.TRADE_NUM_PARAM_NAME).toString();// 获取第三方返回的退款订单号
					record.setAgtRefundOrdNum(agtRefOrdNum);
					record.setPayStatus(OrderConstant.STATE_REFUND);// 设置支付状态为退款成功
					record.setDepositStatus(InpatientConstant.STATE_PERSON_HANDLE_SUCCESS);
					record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
					record.setRefundHisOrdNo(rgParams.getHisNewOrdNum());// 写入his退费单号
					Long refundTime = new Date().getTime();// 退费时间
					record.setRefundTime(refundTime);
					record.setUpdateTime(refundTime);

					if (logger.isDebugEnabled()) {
						logger.debug("rgParams: {}", com.alibaba.fastjson.JSON.toJSONString(rgParams));
					}

					boolean isPush =
							StringUtils.isBlank(rgParams.getPushType())
									|| rgParams.getPushType().equals(String.valueOf(ReceiveConstant.PUSH_MESSAGE_TYPE));
					//logger.info("是否推送消息: {} .", isPush);
					if (isPush) {
						//logger.info("推送消息开始：{} .", DateUtils.dateToString(new Date()));
						CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
						commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_DEPOSIT_REFUND_SUCCESS);
						//logger.info("推送消息结束：{} .", DateUtils.dateToString(new Date()));
					}

					ReturnGeneralResult returnGeneral = new ReturnGeneralResult(record.getRefundOrderNo(), record.getAgtRefundOrdNum());
					XStream stream = new XStream();
					stream.alias("returnGeneral", ReturnGeneralResult.class);
					result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", stream.toXML(returnGeneral));
					//					inpatientService.updateOrderPayInfo(record);// TODO 住院押金
				} else {
					result = new Response(OutsideConstant.OUTSIDE_ERROR, resMap.get(BizConstant.INTERFACE_MAP_MSG_KEY).toString());
				}
			} else {
				result = new Response(OutsideConstant.OUTSIDE_ERROR, "第三方退费异常");
			}

		} else {
			String resultMessage = "order [" + rgParams.getPsOrdNum() + "] has been a refund";
			ReturnGeneralResult returnGeneral = new ReturnGeneralResult(record.getRefundOrderNo(), record.getAgtRefundOrdNum());
			XStream stream = new XStream();
			stream.alias("returnGeneral", ReturnGeneralResult.class);
			result = new Response(OutsideConstant.OUTSIDE_OPERATED, resultMessage, stream.toXML(returnGeneral));
		}
		return result;
	}*/

	/**
	 * 门诊部分退费
	 * 
	 * @param records
	 * @param rg
	 * */
	@Override
	public Response refundClinicOriginalChannelPart(ClinicRecord records, RgParams rg) {
		/**********************************医保不支持部分退费************************************/
		if (records.getIsMedicarePayMents() != null && records.getIsMedicarePayMents() == BizConstant.BASEDON_MEDICAL_INSURANCE_YES) {
			return new Response(OutsideConstant.OUTSIDE_ERROR, "目前医保不支持部分退费");
		}

		Response result = new Response();
		/***
		 * modify by 三金 增加部分退费支付状态判断 (已支付||已退费||退费中)&&(已缴费||窗口退费异常||部分退费||部分退费状态：成功 )
		 */
		if ( ( records.getPayStatus() == OrderConstant.STATE_PAYMENT || records.getPayStatus() == OrderConstant.STATE_REFUND || records
				.getPayStatus() == OrderConstant.STATE_REFUNDING )

				&&

				( records.getClinicStatus() == ClinicConstant.STATE_PAY_SUCCESS
						|| records.getClinicStatus() == ClinicConstant.STATE_PERSON_HANDLE_EXCEPTION
						|| records.getClinicStatus() == ClinicConstant.STATE_PART_REFUND || records.getClinicStatus() == ClinicConstant.STATE_PART_REFUND_SUCCESS )) {
			// 已支付且 // 已经预约或窗口退费异常，可以退费
			ClinicPartRefundService clinicPartRefundService = SpringContextHolder.getBean(ClinicPartRefundService.class);
			String hisOrdNo = rg.getHisNewOrdNum();// his退费订单号
			ClinicPartRefundRecord partRefundRecord =
					clinicPartRefundService.findByRefundOrderNo(records.getHospitalCode(), hisOrdNo, rg.getPsOrdNum());
			if (partRefundRecord != null) {
				logger.info("订单号:{},部分退费订单已经存在一个医院流水号为{}的退费单", records.getOrderNo(), hisOrdNo);
				String resultMessage = "订单[" + rg.getPsOrdNum() + "]已存在一个医院流水号为[" + rg.getHisNewOrdNum() + "]的退费数据！";
				ReturnGeneralResult returnGeneral =
						new ReturnGeneralResult(partRefundRecord.getRefundOrderNo(), partRefundRecord.getAgtRefundOrdNum());
				XStream stream = new XStream();
				stream.alias("returnGeneral", ReturnGeneralResult.class);
				logger.info("部分退费订单已存在 返回信息:{}", stream.toXML(returnGeneral));
				if (partRefundRecord.getRefundStatus() == ClinicConstant.STATE_PART_REFUND_FAIL) {
					result = new Response(OutsideConstant.OUTSIDE_ERROR, "部分退费失败", "");
				} else {
					result = new Response(OutsideConstant.OUTSIDE_OPERATED, resultMessage, stream.toXML(returnGeneral));
				}
			} else {
				Integer refundFee = Integer.valueOf(rg.getRefundAmout());// 部分退费金额
				Integer hadRefundFee = clinicPartRefundService.getRefundedFee(records.getOrderNo(), records.getHospitalCode());
				if (Integer.valueOf(records.getTotalFee()) >= refundFee + hadRefundFee) {
					logger.info("订单号:{},hisOrdNo:{},本次退费金额：{}", records.getOrderNo(), hisOrdNo, refundFee);
					// 设置退费订单号
					String refundOrderNo = records.getRefundOrderNo();
					if (StringUtils.isEmpty(refundOrderNo)) {
						try {
							refundOrderNo =
									OrderNoGenerator.genOrderNo(String.valueOf(ModeTypeUtil.getPlatformModeType(records.getAppCode())),
											String.valueOf(records.getTradeMode()), OrderConstant.ORDER_TYPE_REFUND_PART,
											BizConstant.BIZ_TYPE_CLINIC, ServerNoGenerator.getServerNoByIp(), records.getOpenId());
							records.setRefundOrderNo(refundOrderNo);
						} catch (Exception e) {
							throw new SystemException(e.getCause());
						}
					}

					logger.info("门诊部分退费 ---> 支付订单号：{},退费订单号:refundOrderNo : {} .", records.getOrderNo(), refundOrderNo);
					records.setRefundFee(String.valueOf(refundFee));
					Map<String, Object> resMap = refundClinic(records);
					logger.info("退费单号:{} ,第三方退费结果 : {} .", records.getOrderNo(), com.alibaba.fastjson.JSON.toJSONString(resMap));
					boolean isException = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);// 是否第三方退费异常
					if (!isException) {
						boolean isSuccess = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);// 是否第三方退费异常
						if (isSuccess) {
							records.setClinicStatus(ClinicConstant.STATE_PART_REFUND);// 门诊部分退费原单改成部分退费状态
							records.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
							if (Integer.valueOf(records.getTotalFee()) == refundFee + hadRefundFee) {// 如果费用完全退完，设置支付状态为退款成功
								records.setPayStatus(OrderConstant.STATE_REFUND);
							}
							/*
							 * 部分退费时：不更新第三方退费订单号、订单支付状态、his退费订单号, 退费时间
							 */
							Long refundTime = new Date().getTime();// 退费时间
							records.setUpdateTime(refundTime);
							records.setRefundOrderNo(""); // 清空原单的退费订单号，否则将无法进行退费操作。
							String agtRefOrdNum = resMap.get(BizConstant.TRADE_NUM_PARAM_NAME).toString();// 获取第三方返回的退款订单号

							ReturnGeneralResult returnGeneral = new ReturnGeneralResult(refundOrderNo, agtRefOrdNum);
							XStream stream = new XStream();
							stream.alias("returnGeneral", ReturnGeneralResult.class);
							result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", stream.toXML(returnGeneral));

							// 更新原单
							clinicService.updateOrderPayInfo(records);
							// 部分退费订单生成
							records.setRefundOrderNo(refundOrderNo);
							ClinicPartRefundRecord refundRecord =
									clinicPartRefundService.saveSuccessPartRefundRecord(records, hisOrdNo, agtRefOrdNum, refundTime);

							// 部分退费消息推送
							if (logger.isDebugEnabled()) {
								logger.debug("rgParams: {}", com.alibaba.fastjson.JSON.toJSONString(rg));
							}

							boolean isPush =
									StringUtils.isBlank(rg.getPushType())
											|| rg.getPushType().equals(String.valueOf(ReceiveConstant.PUSH_MESSAGE_TYPE));
							// logger.info("是否推送消息: {} .", isPush);
							if (isPush) {
								// logger.info("推送消息开始：{} .", DateUtils.dateToString(new Date()));
								CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
								commonMsgPushSvc.pushMsg(refundRecord, MessageConstant.ACTION_TYPE_CLINIC_PART_REFUND_SUCCESS);
								// logger.info("推送消息结束：{} .", DateUtils.dateToString(new Date()));
							}

						} else {
							// 第三方退费失败
							Long curTime = new Date().getTime();// 退费时间
							// 更新原单
							records.setUpdateTime(curTime);
							records.setClinicStatus(ClinicConstant.STATE_PART_REFUND);// 门诊部分退费原单改成部分退费状态
							records.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
							records.setRefundOrderNo(""); // 清空原单退费订单号，否则原单无法退费。
							clinicService.updateOrderPayInfo(records);
							// 部分退费订单生成
							records.setRefundOrderNo(refundOrderNo);
							clinicPartRefundService.saveFailPartRefundRecord(records, hisOrdNo, curTime);
							result = new Response(OutsideConstant.OUTSIDE_ERROR, resMap.get(BizConstant.INTERFACE_MAP_MSG_KEY).toString());
						}
					} else {
						result = new Response(OutsideConstant.OUTSIDE_ERROR, "第三方退费异常");
					}

				} else {
					logger.info("退费金额超过了订单金额，不进行退费。");
					String resultMessage = "部分退费金额应不能超过订单剩余的金额！";
					result = new Response(OutsideConstant.OUTSIDE_ERROR, resultMessage, "");
				}
			}
		} else {
			if (records.getPayStatus() == OrderConstant.STATE_REFUND) {
				String resultMessage = "该订单已经完成退费！order [" + rg.getPsOrdNum() + "]";
				/*
				 * ReturnGeneralResult returnGeneral = new ReturnGeneralResult(); if
				 * (StringUtils.isNotBlank(records.getRefundOrderNo())) { returnGeneral = new
				 * ReturnGeneralResult(records.getRefundOrderNo(), records.getAgtRefundOrdNum()); } else {
				 * ClinicPartRefundService clinicPartRefundService =
				 * SpringContextHolder.getBean(ClinicPartRefundService.class); List<ClinicPartRefundRecord>
				 * partRefundList = clinicPartRefundService .findRecordsByOrderNo(records.getHospitalCode(),
				 * records.getOrderNo()); if (!CollectionUtils.isEmpty(partRefundList)) {
				 * 
				 * } } XStream stream = new XStream(); stream.alias("returnGeneral", ReturnGeneralResult.class);
				 */
				result = new Response(OutsideConstant.OUTSIDE_OPERATED, resultMessage);
			} else {
				logger.info("该订单不满足部分退费的条件！order: {}", rg.getPsOrdNum());
				String resultMessage = "该订单不满足部分退费的条件！order [" + rg.getPsOrdNum() + "]";
				result = new Response(OutsideConstant.OUTSIDE_ERROR, resultMessage);
			}
		}
		return result;
	}

	/**
	 * 住院部分退费
	 * @param record
	 * @param rgParams
	 * @return 
	 * */
	/*@Override
	public Response refundDepositOriginalChannelPart(DepositRecord record, RgParams rgParams) {
		Response result = new Response();
		if (record.getPayStatus() == OrderConstant.STATE_PAYMENT
				&& ( record.getDepositStatus() == InpatientConstant.STATE_PAY_SUCCESS
						|| record.getDepositStatus() == InpatientConstant.STATE_PERSON_HANDLE_EXCEPTION || record.getDepositStatus() == InpatientConstant.STATE_PART_REFUND )) {

			DepositPartRefundService depositPartRefundService = SpringContextHolder.getBean(DepositPartRefundService.class);
			String hisOrdNo = rgParams.getHisNewOrdNum();// his退费订单号

			DepositPartRefundRecord partRefundRecord =
					depositPartRefundService.findByRefundOrderNo(record.getHospitalCode(), hisOrdNo, rgParams.getPsOrdNum());

			if (partRefundRecord != null) {
				String resultMessage = "订单[" + rgParams.getPsOrdNum() + "]已存在一个医院流水号为[" + rgParams.getHisNewOrdNum() + "]的退费数据！";
				result = new Response(OutsideConstant.OUTSIDE_OPERATED, resultMessage, "");
			} else {
				Integer refundFee = Integer.valueOf(rgParams.getRefundAmout());// 部分退费金额
				Integer hadRefundFee = depositPartRefundService.getRefundedFee(record.getOrderNo(), record.getHospitalCode());
				if (Integer.valueOf(record.getTotalFee()) >= refundFee + hadRefundFee) {
					//logger.info("本次退费金额：{}", refundFee);
					// 设置退费订单号
					String refundOrderNo = record.getRefundOrderNo();
					if (StringUtils.isEmpty(refundOrderNo)) {
						int orderType = BizConstant.ORDER_TYPE_REFUND_OFFLINE_ALL;
						if (!StringUtils.isBlank(rgParams.getPartialOrAllrefund())) {
							if (!rgParams.getPartialOrAllrefund().equals(String.valueOf(ReceiveConstant.ALL_REFUND))) {
								orderType = BizConstant.ORDER_TYPE_REFUND_OFFLINE_PART;
							}
						}
						refundOrderNo = OrderNoGenerator.genOrderNo(record.getOrderType(), orderType, BizConstant.BIZ_TYPE_DEPOSIT);
						record.setRefundOrderNo(refundOrderNo);
					}
					//logger.info("refundOrderNo : {} .", refundOrderNo);
					record.setRefundFee(String.valueOf(refundFee));
					Map<String, Object> resMap = refundDeposit(record);
					logger.info("第三方退费结果 : {} .", com.alibaba.fastjson.JSON.toJSONString(resMap));
					boolean isException = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);// 是否第三方退费异常
					if (!isException) {
						boolean isSuccess = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);// 是否第三方退费异常
						if (isSuccess) {
							record.setDepositStatus(InpatientConstant.STATE_PART_REFUND);// 住院部分退费原单改成部分退费状态
							record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
							if (Integer.valueOf(record.getTotalFee()) == refundFee + hadRefundFee) {// 如果费用完全退完，设置支付状态为退款成功
								record.setPayStatus(OrderConstant.STATE_REFUND);
							}
							
							 * 部分退费时：不更新第三方退费订单号、订单支付状态、his退费订单号, 退费时间
							 
							Long refundTime = new Date().getTime();// 退费时间
							record.setUpdateTime(refundTime);
							record.setRefundOrderNo(""); // 清空原单的退费订单号，否则将无法进行退费操作。
							String agtRefOrdNum = resMap.get(BizConstant.TRADE_NUM_PARAM_NAME).toString();// 获取第三方返回的退款订单号

							ReturnGeneralResult returnGeneral = new ReturnGeneralResult(refundOrderNo, agtRefOrdNum);
							XStream stream = new XStream();
							stream.alias("returnGeneral", ReturnGeneralResult.class);
							result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", stream.toXML(returnGeneral));

							// 更新原单
							inpatientService.updateOrderPayInfo(record);
							// 部分退费订单生成
							record.setRefundOrderNo(refundOrderNo);
							DepositPartRefundRecord refundRecord =
									depositPartRefundService.saveSuccessPartRefundRecord(record, hisOrdNo, agtRefOrdNum, refundTime);

							// 部分退费消息推送
							if (logger.isDebugEnabled()) {
								logger.debug("rgParams: {}", com.alibaba.fastjson.JSON.toJSONString(rgParams));
							}

							boolean isPush =
									StringUtils.isBlank(rgParams.getPushType())
											|| rgParams.getPushType().equals(String.valueOf(ReceiveConstant.PUSH_MESSAGE_TYPE));
							//logger.info("是否推送消息: {} .", isPush);
							if (isPush) {
								//logger.info("推送消息开始：{} .", DateUtils.dateToString(new Date()));
								CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
								commonMsgPushSvc.pushMsg(refundRecord, CommonMsgPushService.ACTION_TYPE_DEPOSIT_PART_REFUND_SUCCESS);
								//logger.info("推送消息结束：{} .", DateUtils.dateToString(new Date()));
							}

						} else {
							// 第三方退费失败
							Long curTime = new Date().getTime();// 退费时间
							// 更新原单
							record.setUpdateTime(curTime);
							record.setDepositStatus(InpatientConstant.STATE_PART_REFUND);// 门诊部分退费原单改成部分退费状态
							record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
							record.setRefundOrderNo(""); // 清空原单退费订单号，否则原单无法退费。
							inpatientService.updateOrderPayInfo(record);
							// 部分退费订单生成
							record.setRefundOrderNo(refundOrderNo);
							depositPartRefundService.saveFailPartRefundRecord(record, hisOrdNo, curTime);
							result = new Response(OutsideConstant.OUTSIDE_ERROR, resMap.get(BizConstant.INTERFACE_MAP_MSG_KEY).toString());
						}
					} else {
						result = new Response(OutsideConstant.OUTSIDE_ERROR, "第三方退费异常");
					}

				} else {
					logger.info("退费金额超过了订单金额，不进行退费。");
					String resultMessage = "部分退费金额应不能超过订单剩余的金额！";
					result = new Response(OutsideConstant.OUTSIDE_ERROR, resultMessage, "");
				}
			}
		} else {
			if (record.getPayStatus() == OrderConstant.STATE_REFUND) {
				String resultMessage = "该订单已经完成退费！order [" + rgParams.getPsOrdNum() + "]";
				
				 * ReturnGeneralResult returnGeneral = new
				 * ReturnGeneralResult(); if
				 * (StringUtils.isNotBlank(records.getRefundOrderNo())) {
				 * returnGeneral = new
				 * ReturnGeneralResult(records.getRefundOrderNo(),
				 * records.getAgtRefundOrdNum()); } else {
				 * ClinicPartRefundService clinicPartRefundService =
				 * SpringContextHolder.getBean(ClinicPartRefundService.class);
				 * List<ClinicPartRefundRecord> partRefundList =
				 * clinicPartRefundService
				 * .findRecordsByOrderNo(records.getHospitalCode(),
				 * records.getOrderNo()); if
				 * (!CollectionUtils.isEmpty(partRefundList)) {
				 * 
				 * } } XStream stream = new XStream();
				 * stream.alias("returnGeneral", ReturnGeneralResult.class);
				 
				result = new Response(OutsideConstant.OUTSIDE_OPERATED, resultMessage);
			} else {
				logger.info("该订单不满足部分退费的条件！order: {}", rgParams.getPsOrdNum());
				String resultMessage = "该订单不满足部分退费的条件！order [" + rgParams.getPsOrdNum() + "]";
				result = new Response(OutsideConstant.OUTSIDE_ERROR, resultMessage);
			}
		}
		return result;
	}*/

	/**
	 * 停诊退费 第三方退费【医保】
	 * 
	 * @param record
	 */
	/*private Map<String, Object> stopRegisterRefundMedicare(RegisterRecord record) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			if (record.getRegMode().intValue() == BizConstant.MODE_TYPE_WEIXIN_VAL) {
				RefundWechatPayInsuranceSZ refundWechatPayInsuranceSZ =
						registerService.buildWechatMedicareRefundInfo(record, BizConstant.INVOKE_TYPE_SERVICE_REQ);
				PayInsuranceRefundService payInsuranceRefundService =
						TradeCommonHoder.getInvokeWechatRefundInsuranceService(refundWechatPayInsuranceSZ);
				com.yxw.platform.payinsurance.payrefund.entity.Refund refund =
						payInsuranceRefundService.wechatSZRefundPayInsuranceService(refundWechatPayInsuranceSZ);

				logger.info("第三方退费结果，状态：{},orderno:{}", refund.getRefundFlag(), record.getOrderNo());
				if (refund.getRefundFlag() == BizConstant.MOTHED_INVOKE_SUCCESS) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, refund.getAgtRefundOrdNum());
				} else if (refund.getRefundFlag() == BizConstant.MOTHED_INVOKE_FAILURE) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, refund.getReturnMsg());
					pushException(record, RegisterConstant.STATE_EXCEPTION_REFUND, "第三方退款异常,无法停诊,状态变更为[STATE_EXCEPTION_REFUND=10]");
				} else {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					pushException(record, RegisterConstant.STATE_EXCEPTION_REFUND, "第三方退款异常,无法停诊,状态变更为[STATE_EXCEPTION_REFUND=10]");
				}

			} else {
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			}
		} catch (Exception e) {
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			pushException(record, RegisterConstant.STATE_EXCEPTION_REFUND, "第三方退款异常,无法停诊,状态变更为[STATE_EXCEPTION_REFUND=10]");
			logger.error("regMode : {}, {} stopRegisterRefund is Exception. msg:{} , case {}",
					new Object[] { record.getRegMode(), df.format(new Date()), e.getMessage(), e.getCause() });
		}
		return resMap;
	}*/

	/**
	 * 停诊退费 第三方退费
	 * 
	 * @param record
	 */
	private Map<String, Object> stopRegisterRefund(RegisterRecord record) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			RefundService refundService = null;

			Object refund = registerService.buildRefundInfo(record);
			refundService = TradeCommonHoder.getInvokeRefundService();

			if (refund instanceof WechatPayRefund) {

				if (logger.isDebugEnabled()) {
					logger.debug("WechatPayRefund : {}", JSON.toJSONString(refund));
				}

				WechatPayRefundResponse wechatPayRefundResponse = refundService.wechatPayRefund((WechatPayRefund) refund);

				if (StringUtils.equals(wechatPayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, wechatPayRefundResponse.getAgtRefundOrderNo());
				} else if (StringUtils.equals(wechatPayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_FAILURE)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, wechatPayRefundResponse.getResultMsg());
					pushException(record, RegisterConstant.STATE_EXCEPTION_REFUND, "第三方退款异常,无法停诊,状态变更为[STATE_EXCEPTION_REFUND=10]");
				} else {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					pushException(record, RegisterConstant.STATE_EXCEPTION_REFUND, "第三方退款异常,无法停诊,状态变更为[STATE_EXCEPTION_REFUND=10]");
				}

			} else if (refund instanceof AlipayRefund) {

				if (logger.isDebugEnabled()) {
					logger.debug("AlipayRefund : {}", JSON.toJSONString(refund));
				}

				AlipayRefundResponse alipayRefundResponse = refundService.alipayRefund((AlipayRefund) refund);

				if (StringUtils.equals(alipayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, record.getOrderNo().substring(5));
				} else if (StringUtils.equals(alipayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_FAILURE)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, alipayRefundResponse.getResultMsg());
					pushException(record, RegisterConstant.STATE_EXCEPTION_REFUND, "第三方退款异常,无法停诊,状态变更为[STATE_EXCEPTION_REFUND=10]");
				} else {
					pushException(record, RegisterConstant.STATE_EXCEPTION_REFUND, "第三方退款异常,无法停诊,状态变更为[STATE_EXCEPTION_REFUND=10]");
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				}
			} else if (refund instanceof UnionpayRefund) {
				// TODO 新增
				if (logger.isDebugEnabled()) {
					logger.debug("AlipayRefund : {}", JSON.toJSONString(refund));
				}

				UnionpayRefundResponse unionpayRefundResponse = refundService.unionpayRefund((UnionpayRefund) refund);

				if (StringUtils.equals(unionpayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, record.getOrderNo().substring(5));

				} else if (StringUtils.equals(unionpayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_FAILURE)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, unionpayRefundResponse.getResultMsg());
					pushException(record, RegisterConstant.STATE_EXCEPTION_REFUND, "第三方退款异常,无法停诊,状态变更为[STATE_EXCEPTION_REFUND=10]");
				} else {
					pushException(record, RegisterConstant.STATE_EXCEPTION_REFUND, "第三方退款异常,无法停诊,状态变更为[STATE_EXCEPTION_REFUND=10]");
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				}
			}
		} catch (Exception e) {
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			pushException(record, RegisterConstant.STATE_EXCEPTION_REFUND, "第三方退款异常,无法停诊,状态变更为[STATE_EXCEPTION_REFUND=10]");
			logger.error("regMode : {}, {} stopRegisterRefund is Exception. msg:{} , case {}",
					new Object[] { record.getPlatformMode(), df.format(new Date()), e.getMessage(), e.getCause() });
		}
		return resMap;
	}

	/**
	 * 查询挂号记录
	 * 
	 * @param record
	 * @param regStatus
	 */
	private RegRecord getRegRecords(RegisterRecord record, int regStatus, String msgException) throws Exception {
		RegRecord regRecord = new RegRecord();
		RegRecordRequest regRecordRequest = new RegRecordRequest();
		regRecordRequest.setBeginDate(DateUtils.tomorrowDate());
		regRecordRequest.setBranchCode(record.getBranchHospitalCode());
		regRecordRequest.setEndDate(DateUtils.tomorrowDate());
		regRecordRequest.setPsOrdNum(record.getOrderNo());
		//		regRecordRequest.setRegMode(String.valueOf(record.getPlatformMode()));
		regRecordRequest.setRegMode(BizConstant.HIS_ORDER_MODE_VAL);
		//		String interfaceId = hospitalCache.getInterfaceId(record.getHospitalCode(), record.getBranchHospitalCode());
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(record.getHospitalCode());
		params.add(record.getBranchHospitalCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		//		YxwService yxwService = SpringContextHolder.getBean(interfaceId);
		//		com.yxw.interfaces.vo.Response response = yxwService.getRegRecords(regRecordRequest);

		YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
		Request request = new Request();
		request.setServiceId(interfaceId);
		request.setMethodName("getRegRecords");
		request.setInnerRequest(regRecordRequest);
		com.yxw.interfaces.vo.Response response = yxwCommService.invoke(request);

		if (response != null) {
			List<RegRecord> regRecords = (List<RegRecord>) response.getResult();
			if (regRecords != null && regRecords.size() > 0) {
				for (RegRecord rr : regRecords) {
					if (rr.getHisOrdNum().equalsIgnoreCase(record.getHisOrdNo())) {
						regRecord = rr;
						break;
					}
				}
			}
		} else {
			pushException(record, regStatus, msgException);
		}
		return regRecord;
	}

	/**
	 * 异常push
	 * 
	 * @param record
	 * @param regStatus
	 */
	private void pushException(RegisterRecord record, int regStatus, String msgException) {
		logger.info("push异常。订单:{},状态： {} , msgException: {}.", new Object[] { record.getOrderNo(), regStatus, msgException });
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
		record.setRegStatus(regStatus);
		record.setUpdateTime(System.currentTimeMillis());
		ExceptionRecord exceptionRecord = new ExceptionRecord();
		BeanUtils.copyProperties(record, exceptionRecord);
		exceptionRecord.addLogInfo(msgException);
		exceptionRecord.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
		if (regStatus == RegisterConstant.STATE_EXCEPTION_REFUND) {
			record.setHandleCount(0);
			//			exceptionCache.setAgtRefundExceptionToCache(exceptionRecord);
			List<Object> params = new ArrayList<Object>();
			params.add(exceptionRecord);
			serveComm.set(CacheType.REGISTER_EXCEPTION_CACHE, "setAgtRefundExceptionToCache", params);
		} else {
			//			exceptionCache.setExceptionRegisterToCache(exceptionRecord);
			List<Object> params = new ArrayList<Object>();
			params.add(exceptionRecord);
			serveComm.set(CacheType.REGISTER_EXCEPTION_CACHE, "setExceptionRegisterToCache", params);
		}
		registerService.updateExceptionRecord(exceptionRecord);
		//		recordCache.updateExceptionRecordsStatusToCache(exceptionRecord);
		List<Object> params = new ArrayList<Object>();
		params.add(exceptionRecord);
		serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateExceptionRecordsStatusToCache", params);
	}

	/**
	 * 挂号退费【医保】
	 * 
	 * @param rg
	 * @param records
	 * @return
	 */
	/*private Map<String, Object> refundRegisterMedicare(RegisterRecord records) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			if (records.getRegMode().intValue() == BizConstant.MODE_TYPE_WEIXIN_VAL) {
				com.yxw.platform.payinsurance.payrefund.entity.Refund refund = null;
				RefundWechatPayInsuranceSZ refundWechatPayInsuranceSZ =
						registerService.buildWechatMedicareRefundInfo(records, BizConstant.INVOKE_TYPE_SERVICE_REQ);
				PayInsuranceRefundService payInsuranceRefundService =
						TradeCommonHoder.getInvokeWechatRefundInsuranceService(refundWechatPayInsuranceSZ);
				refund = payInsuranceRefundService.wechatSZRefundPayInsuranceService(refundWechatPayInsuranceSZ);
				if (refund.getRefundFlag() == BizConstant.MOTHED_INVOKE_SUCCESS) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, refund.getAgtRefundOrdNum());
				} else if (refund.getRefundFlag() == BizConstant.MOTHED_INVOKE_FAILURE) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, refund.getReturnMsg());
				} else {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					pushRegisterException(records, RegisterConstant.STATE_WINDOW_EXCEPTION_REFUND,
							"第三方退款异常,窗口退费失败;状态变更为[STATE_WINDOW_EXCEPTION_REFUND=21]");
				}
			} else {
				// 其他退费渠道未开通
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			pushRegisterException(records, RegisterConstant.STATE_WINDOW_EXCEPTION_REFUND,
					"第三方退款异常,窗口退费失败,状态变更为[STATE_WINDOW_EXCEPTION_REFUND=21]");
		}
		return resMap;
	}*/

	/**
	 * 挂号退费
	 * 
	 * @param rg
	 * @param records
	 * @return
	 */
	private Map<String, Object> refundRegister(RegisterRecord records) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		//logger.info("挂号退费：records : {}.", records);
		try {
			Object refund = registerService.buildRefundInfo(records);
			RefundService refundService = TradeCommonHoder.getInvokeRefundService();

			if (logger.isDebugEnabled()) {
				logger.debug("register refund info : {} ", JSON.toJSONString(refund));
			}

			if (refund instanceof WechatPayRefund) {
				WechatPayRefundResponse wechatPayRefundResponse = refundService.wechatPayRefund((WechatPayRefund) refund);

				if (StringUtils.equals(wechatPayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, wechatPayRefundResponse.getAgtRefundOrderNo());
				} else if (StringUtils.equals(wechatPayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_FAILURE)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, wechatPayRefundResponse.getResultMsg());
				} else {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					pushRegisterException(records, RegisterConstant.STATE_WINDOW_EXCEPTION_REFUND,
							"第三方退款异常,窗口退费失败;状态变更为[STATE_WINDOW_EXCEPTION_REFUND=21]");
				}

			} else if (refund instanceof AlipayRefund) {
				AlipayRefundResponse alipayRefundResponse = refundService.alipayRefund((AlipayRefund) refund);

				if (StringUtils.equals(alipayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, records.getOrderNo().substring(5));
				} else if (StringUtils.equals(alipayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_FAILURE)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, alipayRefundResponse.getResultMsg());
				} else {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					pushRegisterException(records, RegisterConstant.STATE_WINDOW_EXCEPTION_REFUND,
							"第三方退款异常,窗口退费失败;状态变更为[STATE_WINDOW_EXCEPTION_REFUND=21]");
				}
			} else if (refund instanceof UnionpayRefund) {
				UnionpayRefundResponse unionpayRefundResponse = refundService.unionpayRefund((UnionpayRefund) refund);

				if (StringUtils.equals(unionpayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, unionpayRefundResponse.getAgtRefundOrderNo());
				} else if (StringUtils.equals(unionpayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_FAILURE)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, unionpayRefundResponse.getResultMsg());
				} else {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					pushRegisterException(records, RegisterConstant.STATE_WINDOW_EXCEPTION_REFUND,
							"第三方退款异常,窗口退费失败;状态变更为[STATE_WINDOW_EXCEPTION_REFUND=21]");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			pushRegisterException(records, RegisterConstant.STATE_WINDOW_EXCEPTION_REFUND,
					"第三方退款异常,窗口退费失败,状态变更为[STATE_WINDOW_EXCEPTION_REFUND=21]");
		}
		return resMap;
	}

	/**
	 * 异常push 挂号
	 * 
	 * @param record
	 * @param regStatus
	 */
	private void pushRegisterException(RegisterRecord record, int regStatus, String msgException) {
		logger.info("push异常: {} ,订单号： {}, msgException: {}.", new Object[] { regStatus, record.getOrderNo(), msgException });
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		record.setRegStatus(regStatus);
		record.addLogInfo(msgException);
		record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
		registerService.updateRecordStatus(record);
		//		recordCache.updateRecordToCache(record);
		List<Object> params = new ArrayList<Object>();
		params.add(record);
		serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordToCache", params);
	}

	/**
	 * 门诊退费【医保】
	 * 
	 * @param rg
	 * @param records
	 * @return
	 */
	/*private Map<String, Object> refundClinicMedicare(ClinicRecord records) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			if (records.getPayMode().intValue() == BizConstant.MODE_TYPE_WEIXIN_VAL) {

				com.yxw.platform.payinsurance.payrefund.entity.Refund refund = null;
				RefundWechatPayInsuranceSZ refundWechatPayInsuranceSZ =
						clinicService.buildRefundWechatMedicareInfo(records, BizConstant.INVOKE_TYPE_SERVICE_REQ);
				PayInsuranceRefundService payInsuranceRefundService =
						TradeCommonHoder.getInvokeWechatRefundInsuranceService(refundWechatPayInsuranceSZ);
				refund = payInsuranceRefundService.wechatSZRefundPayInsuranceService(refundWechatPayInsuranceSZ);

				if (refund.getRefundFlag() == BizConstant.MOTHED_INVOKE_SUCCESS) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, refund.getAgtRefundOrdNum());
				} else if (refund.getRefundFlag() == BizConstant.MOTHED_INVOKE_FAILURE) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, refund.getReturnMsg());
				} else {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					//					clinicService.updateOrderPayInfo(records);
					pushClinicException(records, ClinicConstant.STATE_PERSON_HANDLE_EXCEPTION,
							"第三方退款异常,窗口退费失败;状态变更为[STATE_PERSON_HANDLE_EXCEPTION=21]");
				}
			} else {
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			pushClinicException(records, ClinicConstant.STATE_PERSON_HANDLE_EXCEPTION,
					"第三方退款异常,窗口退费失败,状态变更为[STATE_PERSON_HANDLE_EXCEPTION=21]");
		}
		return resMap;
	}*/

	/**
	 * 门诊退费
	 * 
	 * @param rg
	 * @param records
	 * @return
	 */
	private Map<String, Object> refundClinic(ClinicRecord records) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {

			Object refund = clinicService.buildRefundInfo(records);
			RefundService refundService = TradeCommonHoder.getInvokeRefundService();

			if (logger.isDebugEnabled()) {
				logger.debug("clinic refund info : {} ", JSON.toJSONString(refund));
			}

			if (refund instanceof WechatPayRefund) {
				WechatPayRefundResponse wechatPayRefundResponse = refundService.wechatPayRefund((WechatPayRefund) refund);

				if (StringUtils.equals(wechatPayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, wechatPayRefundResponse.getAgtRefundOrderNo());
				} else if (StringUtils.equals(wechatPayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_FAILURE)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, wechatPayRefundResponse.getResultMsg());
				} else {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					pushClinicException(records, ClinicConstant.STATE_PERSON_HANDLE_EXCEPTION,
							"第三方退款异常,窗口退费失败;状态变更为[STATE_PERSON_HANDLE_EXCEPTION=21]");
				}

			} else if (refund instanceof AlipayRefund) {
				AlipayRefundResponse alipayRefundResponse = refundService.alipayRefund((AlipayRefund) refund);

				if (StringUtils.equals(alipayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, records.getOrderNo().substring(5));
				} else if (StringUtils.equals(alipayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_FAILURE)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, alipayRefundResponse.getResultMsg());
				} else {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					pushClinicException(records, ClinicConstant.STATE_PERSON_HANDLE_EXCEPTION,
							"第三方退款异常,窗口退费失败,状态变更为[STATE_PERSON_HANDLE_EXCEPTION=21]");
				}
			} else if (refund instanceof UnionpayRefund) {
				UnionpayRefundResponse unionpayRefundResponse = refundService.unionpayRefund((UnionpayRefund) refund);

				if (StringUtils.equals(unionpayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, unionpayRefundResponse.getAgtRefundOrderNo());
				} else if (StringUtils.equals(unionpayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_FAILURE)) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, unionpayRefundResponse.getResultMsg());
				} else {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					pushClinicException(records, ClinicConstant.STATE_PERSON_HANDLE_EXCEPTION,
							"第三方退款异常,窗口退费失败,状态变更为[STATE_PERSON_HANDLE_EXCEPTION=21]");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			pushClinicException(records, ClinicConstant.STATE_PERSON_HANDLE_EXCEPTION,
					"第三方退款异常,窗口退费失败,状态变更为[STATE_PERSON_HANDLE_EXCEPTION=21]");
		}
		return resMap;
	}

	/**
	 * 异常push 门诊
	 * 
	 * @param record
	 * @param regStatus
	 */
	private void pushClinicException(ClinicRecord record, int regStatus, String msgException) {
		logger.info("push异常: {} ,订单号： {}, msgException: {}.", new Object[] { regStatus, record.getOrderNo(), msgException });
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		record.setClinicStatus(regStatus);
		record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
		String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
		StringBuilder sbLog = new StringBuilder();
		sbLog.append("HandleCount:" + record.getHandleCount());
		sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
		sbLog.append(",HandleMsg:" + msgException);
		record.setHandleLog(handleLog + sbLog.toString());
		record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
		clinicService.updateOrderPayInfo(record);
	}

	/**
	 * 异常push 住院
	 * 
	 * @param record
	 * @param regStatus
	 */
	/*private void pushDepositException(DepositRecord record, int regStatus, String msgException) {
		logger.info("push异常: {} ,订单号： {}, msgException: {}.", new Object[] { regStatus, record.getOrderNo(), msgException });
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		record.setDepositStatus(regStatus);
		record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
		String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
		StringBuilder sbLog = new StringBuilder();
		sbLog.append("HandleCount:" + record.getHandleCount());
		sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
		sbLog.append(",HandleMsg:" + msgException);
		record.setHandleLog(handleLog + sbLog.toString());
		record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
		inpatientService.updateOrderPayInfo(record);
	}*/

	/**
	 * 住院退费
	 * 
	 * @param record
	 * @return
	 */
	/*private Map<String, Object> refundDeposit(DepositRecord record) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		//logger.info("住院退费：record : {}.", record);
		try {
			RefundService refundService = null;
			if (record.getPayMode().intValue() == BizConstant.MODE_TYPE_WEIXIN_VAL) {
				RefundWechat refund = inpatientService.buildRefundWechatInfo(record, BizConstant.INVOKE_TYPE_SERVICE_REQ);
				refundService = TradeCommonHoder.getInvokeRefundService(refund);
				if (logger.isDebugEnabled()) {
					logger.debug("RefundWechat : {}", JSON.toJSONString(refund));
				}
				refund = (RefundWechat) refundService.wechatRefundService(refund);
				if (refund.getRefundFlag() == BizConstant.MOTHED_INVOKE_SUCCESS) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, refund.getAgtRefundOrdNum());
				} else if (refund.getRefundFlag() == BizConstant.MOTHED_INVOKE_FAILURE) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, refund.getReturnMsg());
				} else {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					inpatientService.updateOrderPayInfo(record);
					pushDepositException(record, InpatientConstant.STATE_PERSON_HANDLE_EXCEPTION,
							"第三方退款异常,窗口退费失败;状态变更为[STATE_PERSON_HANDLE_EXCEPTION=21]");
				}
			} else {
				RefundAli refund = inpatientService.buildRefundAliInfo(record, BizConstant.INVOKE_TYPE_SERVICE_REQ);
				refundService = TradeCommonHoder.getInvokeRefundService(refund);
				if (logger.isDebugEnabled()) {
					logger.debug("RefundAli : {}", JSON.toJSONString(refund));
				}
				refund = (RefundAli) refundService.aliRefundService(refund);
				if (refund.getRefundFlag() == BizConstant.MOTHED_INVOKE_SUCCESS) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					refund.setAgtOrdNum(refund.getBatchNo());
					resMap.put(BizConstant.TRADE_NUM_PARAM_NAME, refund.getAgtOrdNum());
				} else if (refund.getRefundFlag() == BizConstant.MOTHED_INVOKE_FAILURE) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, refund.getReturnMsg());
				} else {
					pushDepositException(record, InpatientConstant.STATE_PERSON_HANDLE_EXCEPTION,
							"第三方退款异常,窗口退费失败,状态变更为[STATE_PERSON_HANDLE_EXCEPTION=21]");
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			pushDepositException(record, InpatientConstant.STATE_PERSON_HANDLE_EXCEPTION,
					"第三方退款异常,窗口退费失败,状态变更为[STATE_PERSON_HANDLE_EXCEPTION=21]");
		}
		return resMap;
	}*/

}
