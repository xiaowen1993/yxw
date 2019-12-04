/**
 * Copyright© 2014-2015 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2015年6月5日
 * @version 1.0
 */
package com.yxw.easyhealth.biz.register.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.entity.platform.payrefund.AlipayRefund;
import com.yxw.commons.entity.platform.payrefund.AlipayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefund;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefundResponse;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.TradeParamsVo;
import com.yxw.commons.vo.cache.CommonParamsVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.easyhealth.biz.register.thread.HisRefundRegRunnable;
import com.yxw.easyhealth.biz.vo.RegisterParamsVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;
import com.yxw.payrefund.service.RefundService;
import com.yxw.payrefund.utils.TradeCommonHoder;

/**
 * @Package: com.yxw.easyhealth.biz.register.controller
 * @ClassName: RegisterInfoController
 * @Statement: <p>
 *             挂号记录查询请求controller
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-12
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/easyhealth/register/infos")
public class RegisterInfoController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(RegisterInfoController.class);
	private RegisterService registerInfoSvc = SpringContextHolder.getBean(RegisterService.class);
	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);
	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
	private RegisterBizManager regBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
	private CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);

	/**
	 * 展示挂号记录的详细信息
	 * 
	 * @param orderNo
	 *            订单号
	 * @return
	 */
	@RequestMapping(value = "/showOrderInfo")
	public ModelAndView showOrderInfo(HttpServletRequest request, CommonParamsVo vo, String orderNo) {
		RegisterRecord record = registerInfoSvc.findRecordByOrderNo(orderNo);

		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/register/regRecordInfo");

		addAppInfo(record, vo);

		if (logger.isDebugEnabled()) {
			logger.debug("showOrderInfo record info:{} ", JSON.toJSONString(record));
		}
		if (record.getPayStatus().intValue() == OrderConstant.STATE_NO_PAYMENT) {
			long nowTime = new Date().getTime();
			Boolean isCanPayMent = true;
			// 必须支付的类型 需判断是否已经过了支付的时间点
			if (record.getPayTimeoutTime() != null && record.getOnlinePaymentType().intValue() == BizConstant.PAYMENT_TYPE_MUST) {
				if (nowTime >= record.getPayTimeoutTime()) {
					isCanPayMent = false;
				}
			}
			if (isCanPayMent) {
				record.setAppUrl(getAppUrl(request));
				Object payParams = registerInfoSvc.buildPayInfo(record);
				modelAndView.addObject("payParams", payParams);
			}
			modelAndView.addObject("isCanPayMent", isCanPayMent);
		}

		RuleRegister ruleReg = ruleConfigManager.getRuleRegisterByHospitalCode(record.getHospitalCode());
		if (logger.isDebugEnabled()) {
			logger.debug(JSON.toJSONString(ruleReg));
		}
		if (ruleReg != null) {
			modelAndView.addObject(BizConstant.RULE_CONFIG_PARAMS_KEY, ruleReg);
		}

		if (StringUtils.isNotBlank(ruleReg.getSerialNumTip()) && !StringUtils.isNotBlank(record.getSerialNum())) {
			record.setSerialNum(ruleReg.getSerialNumTip());
		}

		if (record.getRegStatus() != null && record.getPayStatus() != null && record.getRegStatus().intValue() == 0
				&& record.getPayStatus().intValue() == 1 && record.getPayTimeoutTime() != null) {
			Long leftSecond = record.getPayTimeoutTime() - new Date().getTime();
			if (leftSecond <= 0) {
				modelAndView.addObject("leftSecond", 0);
			} else {
				modelAndView.addObject("leftSecond", leftSecond / 1000);
			}
		}

		// 是否支持当班退费(已支付)
		Integer isSupportOndutyRefund = RegisterConstant.SUPPORT_REFUND_ONDUTY_YES;
		Integer regType = record.getRegType();
		if (regType == null) {
			regType = RegisterConstant.REG_TYPE_CUR;
		}
		if (regType.intValue() == RegisterConstant.REG_TYPE_CUR) {
			Integer payStatus = record.getPayStatus();
			if (payStatus != null && payStatus.intValue() == OrderConstant.STATE_PAYMENT) {
				Integer isSupport = ruleReg.getIsSupportRefundOnDuty();
				if (isSupport != null) {
					isSupportOndutyRefund = isSupport;
				} else {
					isSupportOndutyRefund = RegisterConstant.SUPPORT_REFUND_ONDUTY_YES;
				}
			}
		}

		// 是否支持预约退费(已支付)
		Integer isSupportAppointmentRefund = RegisterConstant.SUPPORT_REFUND_APPOINTMENT_YES;
		if (regType.intValue() == RegisterConstant.REG_TYPE_APPOINTMENT) {
			Integer payStatus = record.getPayStatus();
			if (payStatus != null && payStatus.intValue() == OrderConstant.STATE_PAYMENT) {
				Integer isSupport = ruleReg.getIsSupportRefundAppointment();
				if (isSupport != null) {
					isSupportAppointmentRefund = isSupport;
				} else {
					isSupportAppointmentRefund = RegisterConstant.SUPPORT_REFUND_APPOINTMENT_YES;
				}
			}
		}

		// 是否允许异常订单退费
		Integer isExceptionRefundOrder = RegisterConstant.IS_EXCEPTION_REFUND_ORDER_YES;
		Integer payStatus = record.getPayStatus();

		if (payStatus != null && payStatus.intValue() == OrderConstant.STATE_PAYMENT
				&& payStatus.intValue() == RegisterConstant.STATE_EXCEPTION_PAY_HIS_COMMIT) {
			Integer isExecption = ruleReg.getIsExceptionRefundOrder();
			if (isExecption != null) {
				isExceptionRefundOrder = isExecption;
			} else {
				isExceptionRefundOrder = RegisterConstant.IS_EXCEPTION_REFUND_ORDER_YES;
			}
		}

		if (StringUtils.isNotBlank(vo.getAppId())) {
			record.setAppId(vo.getAppId());
		}

		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalId(record.getHospitalId());
		if (StringUtils.isNotBlank(ruleRegister.getSerialNumTip()) && !StringUtils.isNotBlank(record.getSerialNum())) {
			record.setSerialNum(ruleRegister.getSerialNumTip());
		}

		modelAndView.addObject("regFeeAlias", ruleRegister.getRegFeeAlias());
		modelAndView.addObject("showRegPeriod", ruleRegister.getShowRegPeriod());

		modelAndView.addObject("record", record);

		modelAndView.addObject("isSupportOndutyRefund", isSupportOndutyRefund);
		modelAndView.addObject("isSupportAppointmentRefund", isSupportAppointmentRefund);
		modelAndView.addObject("isExceptionRefundOrder", isExceptionRefundOrder);
		modelAndView.addObject("reqSource", vo.getReqSource());

		return modelAndView;
	}

	/**
	 * 查询医疗卡所有的挂号记录
	 * 
	 * @param vo
	 * @param diseaseDesc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryByMedicalCard", method = RequestMethod.POST)
	public RespBody queryByMedicalCard(RegisterParamsVo vo) {

		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(vo.getHospitalCode());
		if (logger.isDebugEnabled()) {
			logger.debug("load hospital's  ruleRegister infos by cache.", ruleRegister);
		}

		//规则配置了不按分院查询时  去除分院检索条件
		if (ruleRegister.getIsHasBranch() != null && ruleRegister.getIsHasBranch().intValue() != RuleConstant.IS_HAS_BRANCH_YES) {
			vo.setBranchHospitalId(null);
		}
		List<RegisterRecord> records = registerInfoSvc.queryByMedicalCard(vo.convertMedicalCard());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("records", records);
		return new RespBody(Status.OK, map);
	}

	/**
	 * 检查是否能支付取号
	 * 
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkCanPayMent", method = RequestMethod.POST)
	public RespBody checkCanPayMent(RegisterRecord record) {
		record = registerInfoSvc.findRecordByOrderNo(record.getOrderNo());
		/* 是否符合 */
		boolean isValid = true;
		/* 不符合限制的前端提示信息 */
		String inValidTip = null;

		if (record.getPayStatus().intValue() == OrderConstant.STATE_NO_PAYMENT) {
			if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_USER_CANCEL) {
				isValid = false;
				inValidTip = "该订单已取消,不能支付取号";
			} else {
				long now = System.currentTimeMillis();
				long payOutTime = record.getPayTimeoutTime();
				// 支持暂不支付/不支付
				if (record.getOnlinePaymentType() != BizConstant.PAYMENT_TYPE_MUST) {
					if (record.getPayTimeoutTime() != null) {
						if (now >= payOutTime) {
							isValid = false;
							inValidTip = "该笔订单已经超过医院规定的支付最后截至时间,已自动取消,不能支付取号";
						} else {
							isValid = true;
						}
					} else {
						isValid = true;
					}
				} else {
					if (now >= payOutTime) {
						isValid = false;
						inValidTip = "支付时间已过,该订单已过期,不能支付取号";
					} else {
						isValid = true;
					}
				}
			}
		} else {
			isValid = false;
			inValidTip = "该订单已取消或已支付,不能支付取号";
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, isValid);
		resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, inValidTip);

		if (isValid) {
			Object pay = registerInfoSvc.buildPayInfo(record);
			resMap.put(BizConstant.TRADE_PAY_KEY, pay);
			resMap.put("tradeUrl", TradeCommonHoder.getTradeUrl());
		}

		return new RespBody(Status.OK, resMap);
	}

	/**
	 * 检查是否能取消挂号
	 * 
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkCanCancelReg", method = RequestMethod.POST)
	public RespBody checkCanCancelReg(RegisterRecord record) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		Integer regStatus = record.getRegStatus();
		String hospitalCode = record.getHospitalCode();
		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(hospitalCode);
		/* 是否符合取消挂号规则/条件限制 */
		boolean isValid = true;
		/* 不符合限制的前端提示信息 */
		String inValidTip = null;
		// 防止返回操作
		record = registerInfoSvc.findRecordByOrderNo(record.getOrderNo());
		if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_USER_CANCEL) {
			isValid = false;
			inValidTip = "该订单已取消,请勿重新操作";
		}

		// 2.取消时间限制
		if (isValid) {
			if (record.getBeginTime() != null) {
				if (record.getEndTime() == null) {
					record.setEndTime(record.getBeginTime());
				}
				Map<String, Object> checkResMap = new HashMap<String, Object>();
				checkResMap = registerInfoSvc.checkValidCacleDateTime(record, ruleRegister);
				isValid = (Boolean) checkResMap.get(BizConstant.CHECK_IS_VALID_RES_KEY);
				if (!isValid) {
					inValidTip = (String) checkResMap.get(BizConstant.METHOD_INVOKE_RES_MSG_KEY);
				}
			}
		}

		if (isValid) {
			// 异常状态下的挂号记录 不能操作()
			if (regStatus == null || regStatus.intValue() == RegisterConstant.STATE_EXCEPTION_HAVING
					|| regStatus.intValue() == RegisterConstant.STATE_EXCEPTION_PAY
					|| regStatus.intValue() == RegisterConstant.STATE_EXCEPTION_PAY_HIS_COMMIT
					|| regStatus.intValue() == RegisterConstant.STATE_EXCEPTION_CANCEL_EXCEPTION
					|| regStatus.intValue() == RegisterConstant.STATE_EXCEPTION_REFUND_HIS_CONFIRM
					|| regStatus.intValue() == RegisterConstant.STATE_EXCEPTION_REFUND
					|| regStatus.intValue() == RegisterConstant.STATE_EXCEPTION_REFUND_HIS_COMMIT
					|| regStatus.intValue() == RegisterConstant.STATE_EXCEPTION_STOP_CURE_CANCEL
					|| regStatus.intValue() == RegisterConstant.STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT) {
				isValid = false;
				inValidTip = "后台处理中,请勿重复操作";
			} else {
				// 1.必须支付状态 是否已超时
				Long timeOutTime = record.getPayTimeoutTime();
				if (timeOutTime != null) {
					Long nowTime = new Date().getTime();
					if (nowTime >= timeOutTime) {
						isValid = false;
						inValidTip = RegisterConstant.DEF_TIP_CANCEL_OVER_TIME_PAY;
					}
				}

				// 2.取消次数达到上限提示 【查询就诊卡已进行取消挂号操作的次数(取消挂号-未支付和退费挂号-已支付的总数)】
				Map<String, Object> checkResMap = new HashMap<String, Object>();
				checkResMap = registerInfoSvc.checkValidCacleTime(record, ruleRegister);
				boolean isTip = (Boolean) checkResMap.get(BizConstant.CHECK_IS_VALID_RES_KEY);
				String tipMsg = (String) checkResMap.get(BizConstant.METHOD_INVOKE_RES_MSG_KEY);
				resMap.put(BizConstant.CANCEL_REG_IS_TIP, isTip);
				resMap.put(BizConstant.CANCEL_REG_TIP_MSG, tipMsg);
			}
		}
		resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, isValid);
		resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, inValidTip);
		return new RespBody(Status.OK, resMap);

	}

	/**
	 * 取消挂号(未支付)
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/cancelRegister", method = RequestMethod.POST)
	public ModelAndView cancelRegister(RegisterRecord record) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/register/regRecordInfo");
		String psOrdNum = record.getOrderNo();
		Integer payStatus = record.getPayStatus();
		String openId = record.getOpenId();
		if (payStatus == null) {
			payStatus = OrderConstant.STATE_NO_PAYMENT;
		}
		Integer regStatus = record.getRegStatus();
		Integer regType = record.getRegType();
		if (regType == null) {
			regType = RegisterConstant.REG_TYPE_APPOINTMENT;
		}

		if (payStatus != null && regType != null && regStatus != null) {
			boolean ishappenException = false;
			if (payStatus.intValue() == OrderConstant.STATE_NO_PAYMENT) {
				Map<String, Object> resultMap = null;
				if (regType.intValue() == RegisterConstant.REG_TYPE_APPOINTMENT) {
					// 取消预约挂号
					resultMap = regBizManager.cancelRegister(record);
				} else {
					// 取消当班挂号
					resultMap = regBizManager.cancelCurRegister(record);
				}

				ishappenException = Boolean.valueOf(resultMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION).toString());
				if (ishappenException) {
					regStatus = RegisterConstant.STATE_EXCEPTION_CANCEL_EXCEPTION;
				} else {
					regStatus = RegisterConstant.STATE_NORMAL_USER_CANCEL;
				}

				registerInfoSvc.updateRecordStatusByOrderNo(psOrdNum, regStatus, ishappenException);
				if (!ishappenException && regType != null) {
					if (RegisterConstant.REG_TYPE_APPOINTMENT == record.getRegType().intValue()) {
						commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_CANCEL_APPOINTMENT);
					} else {
						commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_CANCEL_ON_DUTY);
					}

				}

			}
			record.setRegStatus(regStatus);
			record.setPayStatus(payStatus);
			modelAndView.addObject(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "取消预约成功!");
		} else {
			modelAndView.addObject(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, false);
			modelAndView.addObject(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "网络异常,请稍后操作.");
		}

		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalId(record.getHospitalId());

		modelAndView.addObject("regFeeAlias", ruleRegister.getRegFeeAlias());
		modelAndView.addObject("showRegPeriod", ruleRegister.getShowRegPeriod());
		modelAndView.addObject("record", record);
		return modelAndView;
	}

	/**
	* 预约退费确认 操作流程:预约退费确认接口-->成功-第3方退费交易接口 失败-写入异常队列 状态值STATE_EXCEPTION_REFUND_HIS_CONFIRM=9
	* 
	* @param record
	* @return
	*/
	@ResponseBody
	@RequestMapping(value = "/refundRegConfirm", method = RequestMethod.POST)
	public RespBody refundRegisterConfirm(HttpServletRequest request, RegisterRecord record) {
		logger.info("订单号:{},refundRegisterConfirm start.", record.getOrderNo());
		Map<String, Object> interfaceResultMap = new HashMap<String, Object>();
		String appId = record.getAppId();
		String appCode = record.getAppCode();
		/** 防止返回操作 **/
		record = registerInfoSvc.findRecordByOrderNo(record.getOrderNo());
		if (StringUtils.isNotBlank(appId)) {
			record.setAppId(appId);
		}
		if (StringUtils.isNotBlank(appCode)) {
			record.setAppCode(appCode);
		}

		/*** 是否符合取消挂号规则/条件限制 *******/
		boolean isValid = true;
		/* 不符合限制的前端提示信息 */
		String inValidTip = null;
		if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_USER_CANCEL) {
			interfaceResultMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			inValidTip = "该订单已取消,请勿重新操作";
			interfaceResultMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, inValidTip);
		} else {
			String hospitalCode = record.getHospitalCode();
			String branchCode = record.getBranchHospitalCode();
			String hisOrdNum = record.getHisOrdNo();
			String psOrdNum = record.getOrderNo();
			String refundMode = String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode()));
			String refundAmout = String.valueOf(record.getRealRegFee() + record.getRealTreatFee());

			Integer regType = record.getRegType();
			if (regType == null) {
				regType = RegisterConstant.REG_TYPE_APPOINTMENT;
			}

			Map<String, Object> checkResMap = new HashMap<String, Object>();
			RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(hospitalCode);

			// 2.取消时间限制
			if (isValid) {
				if (record.getBeginTime() != null) {
					if (record.getEndTime() == null) {
						record.setEndTime(record.getBeginTime());
					}
					checkResMap = registerInfoSvc.checkValidCacleDateTime(record, ruleRegister);
					isValid = (Boolean) checkResMap.get(BizConstant.CHECK_IS_VALID_RES_KEY);
					if (!isValid) {
						inValidTip = (String) checkResMap.get(BizConstant.METHOD_INVOKE_RES_MSG_KEY);
					}
				}
			}

			// 3.医院退费确认
			if (isValid) {
				if (regType.intValue() == RegisterConstant.REG_TYPE_APPOINTMENT) {
					// 预约退费确认
					interfaceResultMap =
							regBizManager.refundRegConfirm(hospitalCode, branchCode, psOrdNum, hisOrdNum, refundMode, refundAmout);
				} else {
					// 当班退费确认
					interfaceResultMap =
							regBizManager.refundCurRegConfirm(hospitalCode, branchCode, psOrdNum, hisOrdNum, refundMode, refundAmout);
				}
				Boolean isSuccess = (Boolean) interfaceResultMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);

				if (isSuccess) {

					// 取消次数达到上限提示 【查询就诊卡已进行取消挂号操作的次数(取消挂号-未支付和退费挂号-已支付的总数)】
					Map<String, Object> resMap = new HashMap<String, Object>();
					resMap = registerInfoSvc.checkValidCacleTime(record, ruleRegister);
					boolean isTip = (Boolean) resMap.get(BizConstant.CHECK_IS_VALID_RES_KEY);
					String tipMsg = (String) resMap.get(BizConstant.METHOD_INVOKE_RES_MSG_KEY);
					interfaceResultMap.put(BizConstant.CANCEL_REG_IS_TIP, isTip);
					interfaceResultMap.put(BizConstant.CANCEL_REG_TIP_MSG, tipMsg);

					// 第3方平台退费
					record.addLogInfo("查询his是否可退费成功,结果可退费");
					Boolean refundIsSuccess = true;
					String failMsg = null;
					logger.info("订单号:{},与his退费确认成功,可退费,进入退费流程", record.getOrderNo());
					record.setAppUrl(getAppUrl(request));

					Integer tradeAmout = record.getRealRegFee() + record.getRealTreatFee();
					interfaceResultMap.put("tradeAmout", tradeAmout);

					if (tradeAmout == 0) {
						// 业务减免后,实际支付为零元
						logger.info("订单号:{},因退费金额为0,不执行第3方退费流程", record.getOrderNo());
						record.setPayStatus(OrderConstant.STATE_REFUND);
						record.setRegStatus(RegisterConstant.STATE_NORMAL_USER_CANCELING);
						record.setUpdateTime(System.currentTimeMillis());
						record.setRefundTime(System.currentTimeMillis());
						record.addLogInfo("因退费金额为0,不执行第3方退费流程");

						record.setAgtRefundOrdNum(record.getOrderNo());
						logger.info("lopin:AgtRefundOrdNum----{}", record.getAgtRefundOrdNum());
						/** 分线程执行his退费接口 */
						Thread refundRegThread = new Thread(new HisRefundRegRunnable(record), "refundRegThread.refundHisCommit");
						refundRegThread.start();

						interfaceResultMap.put("refundIsSuccess", refundIsSuccess);
					} else {
						record.addLogInfo("执行第3方退费流程");

						RefundService refundService = TradeCommonHoder.getInvokeRefundService();
						Object refund = registerInfoSvc.buildRefundInfo(record);

						if (refund instanceof WechatPayRefund) {

							if (logger.isDebugEnabled()) {
								logger.debug("WechatPayRefund : {}", JSON.toJSONString(refund));
							}

							WechatPayRefundResponse wechatPayRefundResponse = refundService.wechatPayRefund((WechatPayRefund) refund);

							Date date = new Date();

							if (StringUtils.equals(wechatPayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
								//退费成功
								logger.info("订单号:{},第3方平台退费成功", record.getOrderNo());
								interfaceResultMap.put("refundIsSuccess", true);
								record.setAgtRefundOrdNum(wechatPayRefundResponse.getAgtRefundOrderNo());
								record.setPayStatus(OrderConstant.STATE_REFUND);
								record.setRegStatus(RegisterConstant.STATE_NORMAL_USER_CANCELING);
								record.setUpdateTime(date.getTime());
								record.setRefundTime(date.getTime());

								/** 分线程执行his退费接口 */

								Thread refundRegThread = new Thread(new HisRefundRegRunnable(record), "refundRegThread.refundHisCommit");
								refundRegThread.start();

							} else {
								//退费失败/异常
								logger.info("订单号:{},第3方平台退费失败或异常", record.getOrderNo());
								interfaceResultMap.put("refundIsSuccess", false);
								record.setPayStatus(OrderConstant.STATE_REFUNDING);
								record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
								record.setUpdateTime(date.getTime());
								record.setFailureMsg(wechatPayRefundResponse.getResultMsg());
								interfaceResultMap.put("failMsg", record.getFailureMsg());
							}

						} else if (refund instanceof AlipayRefund) {
							if (logger.isDebugEnabled()) {
								logger.debug("AlipayRefund : {}", JSON.toJSONString(refund));
							}

							AlipayRefundResponse alipayRefundResponse = refundService.alipayRefund((AlipayRefund) refund);

							Date date = new Date();

							if (StringUtils.equals(alipayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
								//退费成功
								logger.info("订单号:{},第3方平台退费成功", record.getOrderNo());
								interfaceResultMap.put("refundIsSuccess", true);
								record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
								record.setPayStatus(OrderConstant.STATE_REFUND);
								record.setRegStatus(RegisterConstant.STATE_NORMAL_USER_CANCELING);
								record.setUpdateTime(date.getTime());
								record.setRefundTime(date.getTime());

								/** 分线程执行his退费接口 */

								Thread refundRegThread = new Thread(new HisRefundRegRunnable(record), "refundRegThread.refundHisCommit");
								refundRegThread.start();

							} else {
								//退费失败/异常
								logger.info("订单号:{},第3方平台退费失败或异常", record.getOrderNo());
								interfaceResultMap.put("refundIsSuccess", false);
								record.setPayStatus(OrderConstant.STATE_REFUNDING);
								record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
								record.setUpdateTime(date.getTime());
								record.setFailureMsg(alipayRefundResponse.getResultMsg());
								interfaceResultMap.put("failMsg", record.getFailureMsg());
							}
						} else if (refund instanceof UnionpayRefund) {
							// TODO 新增
							if (logger.isDebugEnabled()) {
								logger.debug("UnionpayRefund : {}", JSON.toJSONString(refund));
							}

							UnionpayRefundResponse unionpayRefundResponse = refundService.unionpayRefund((UnionpayRefund) refund);

							Date date = new Date();

							if (StringUtils.equals(unionpayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
								//退费成功
								logger.info("订单号:{},第3方平台退费成功", record.getOrderNo());
								refundIsSuccess = true;
								interfaceResultMap.put("refundIsSuccess", refundIsSuccess);
								record.setAgtRefundOrdNum(unionpayRefundResponse.getAgtRefundOrderNo());
								record.setPayStatus(OrderConstant.STATE_REFUND);
								record.setRegStatus(RegisterConstant.STATE_NORMAL_USER_CANCELING);
								record.setUpdateTime(date.getTime());
								record.setRefundTime(date.getTime());
								record.addLogInfo("第3方退费成功,执行his退费接口");

								/** 分线程执行his退费接口 */

								Thread refundRegThread = new Thread(new HisRefundRegRunnable(record), "refundRegThread.refundHisCommit");
								refundRegThread.start();

							} else {
								//退费失败/异常
								logger.info("订单号:{},第3方平台退费失败或异常", record.getOrderNo());
								refundIsSuccess = false;
								interfaceResultMap.put("refundIsSuccess", false);
								record.setPayStatus(OrderConstant.STATE_REFUNDING);
								record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
								record.setUpdateTime(date.getTime());
								record.setFailureMsg(unionpayRefundResponse.getResultMsg());
								record.addLogInfo("第3方退费失败,原因:".concat(record.getFailureMsg()));
								interfaceResultMap.put("failMsg", record.getFailureMsg());
							}

						}

						interfaceResultMap.put(BizConstant.TRADE_REFUND_KEY, refund);
					}
					registerInfoSvc.updateRecordForHisRefund(record);
					if (refundIsSuccess) {
						logger.info("订单号:{},时间:{},第3方平台退费成功.分线程执行his预约退费接口", record.getOrderNo(),
								BizConstant.YYYYMMDDHHMMSS.format(new Date()));
					} else {
						logger.info("订单号:{},第3方平台退费失败或异常,message:{}", new Object[] { record.getOrderNo(), failMsg });
					}
				}
			} else {
				interfaceResultMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				interfaceResultMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, inValidTip);
			}

		}

		logger.info("resMap:".concat(JSON.toJSONString(interfaceResultMap)));
		logger.info("refundRegisterConfirm end..........................................................");
		return new RespBody(Status.OK, interfaceResultMap);
	}

	/**
	 * 预约退费确认 操作流程:预约退费确认接口-->成功-第3方退费交易接口 失败-写入异常队列
	 * 状态值STATE_EXCEPTION_REFUND_HIS_CONFIRM=9
	 * 
	 * @param record
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = "/refundRegConfirm", method = RequestMethod.POST)
	public RespBody refundRegisterConfirm(HttpServletRequest request, RegisterRecord record) {
		logger.info("refundRegisterConfirm start..........................................................");
		Map<String, Object> interfaceResultMap = new HashMap<String, Object>();
		String appId = record.getAppId();
		String appCode = record.getAppCode();
		*//**  防止返回操作 **/
	/*
	record = registerInfoSvc.findRecordByOrderNo(record.getOrderNo());
	if (StringUtils.isNotBlank(appId)) {
	record.setAppId(appId);
	}
	if (StringUtils.isNotBlank(appCode)) {
	record.setAppCode(appCode);
	}

	*//*** 是否符合取消挂号规则/条件限制 *******/
	/*
	boolean isValid = true;
	不符合限制的前端提示信息 
	String inValidTip = null;
	if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_USER_CANCEL) {
	interfaceResultMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
	inValidTip = "该订单已取消,请勿重新操作";
	interfaceResultMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, inValidTip);
	} else {
	String hospitalCode = record.getHospitalCode();
	String branchCode = record.getBranchHospitalCode();
	String hisOrdNum = record.getHisOrdNo();
	String psOrdNum = record.getOrderNo();
	Integer regType = record.getRegType();
	String refundMode = String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode()));
	String refundAmout = String.valueOf(record.getRealRegFee() + record.getRealTreatFee());
	if (regType == null) {
	regType = RegisterConstant.REG_TYPE_APPOINTMENT;
	}

	// 1.取消次数限制 查询就诊卡已进行取消挂号操作的次数(取消挂号-未支付和退费挂号-已支付的总数)
	Map<String, Object> checkResMap = new HashMap<String, Object>();
	RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(hospitalCode);
	if (isValid) {
	checkResMap = registerInfoSvc.checkValidCacleTime(record, ruleRegister);
	isValid = (Boolean) checkResMap.get(BizConstant.CHECK_IS_VALID_RES_KEY);
	if (!isValid) {
	inValidTip = (String) checkResMap.get(BizConstant.METHOD_INVOKE_RES_MSG_KEY);
	}
	}

	// 2.取消时间限制
	if (isValid) {
	//如果没有就诊分时的开始时间和结束时间则不进行取消时间限制判断
	if (record.getBeginTime() != null || record.getEndTime() != null) {
	checkResMap = registerInfoSvc.checkValidCacleDateTime(record, ruleRegister);
	isValid = (Boolean) checkResMap.get(BizConstant.CHECK_IS_VALID_RES_KEY);
	if (!isValid) {
	inValidTip = (String) checkResMap.get(BizConstant.METHOD_INVOKE_RES_MSG_KEY);
	}
	}
	}

	// 3.医院退费确认
	if (isValid) {
	if (regType.intValue() == RegisterConstant.REG_TYPE_APPOINTMENT) {
	// 预约退费确认
	interfaceResultMap =
	regBizManager.refundRegConfirm(hospitalCode, branchCode, psOrdNum, hisOrdNum, refundMode, refundAmout);
	} else {
	// 当班退费确认
	interfaceResultMap =
	regBizManager.refundCurRegConfirm(hospitalCode, branchCode, psOrdNum, hisOrdNum, refundMode, refundAmout);
	}
	Boolean isSuccess = (Boolean) interfaceResultMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);

	if (isSuccess) {
	record.setAppUrl(getAppUrl(request));

	if (logger.isDebugEnabled()) {
	logger.info(" refundRegisterConfirm  record:{}", JSON.toJSONString(record));
	}

	RefundService refundService = TradeCommonHoder.getInvokeRefundService();
	Object refund = registerInfoSvc.buildRefundInfo(record);

	if (refund instanceof WechatPayRefund) {

	if (logger.isDebugEnabled()) {
	logger.debug("WechatPayRefund : {}", JSON.toJSONString(refund));
	}

	WechatPayRefundResponse wechatPayRefundResponse = refundService.wechatPayRefund((WechatPayRefund) refund);

	Date date = new Date();

	if (StringUtils.equals(wechatPayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
	//退费成功
	logger.info("订单号:{},第3方平台退费成功", record.getOrderNo());
	interfaceResultMap.put("refundIsSuccess", true);
	record.setAgtRefundOrdNum(wechatPayRefundResponse.getAgtRefundOrderNo());
	record.setPayStatus(OrderConstant.STATE_REFUND);
	record.setRegStatus(RegisterConstant.STATE_NORMAL_USER_CANCELING);
	record.setUpdateTime(date.getTime());
	record.setRefundTime(date.getTime());
	} else {
	//退费失败/异常
	logger.info("订单号:{},第3方平台退费失败或异常", record.getOrderNo());
	interfaceResultMap.put("refundIsSuccess", false);
	record.setPayStatus(OrderConstant.STATE_REFUNDING);
	record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
	record.setUpdateTime(date.getTime());
	record.setFailureMsg(wechatPayRefundResponse.getResultMsg());
	interfaceResultMap.put("failMsg", record.getFailureMsg());
	}

	} else if (refund instanceof AlipayRefund) {
	if (logger.isDebugEnabled()) {
	logger.debug("AlipayRefund : {}", JSON.toJSONString(refund));
	}

	AlipayRefundResponse alipayRefundResponse = refundService.alipayRefund((AlipayRefund) refund);

	Date date = new Date();

	if (StringUtils.equals(alipayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
	//退费成功
	logger.info("订单号:{},第3方平台退费成功", record.getOrderNo());
	interfaceResultMap.put("refundIsSuccess", true);
	record.setAgtRefundOrdNum(record.getOrderNo().substring(5));
	record.setPayStatus(OrderConstant.STATE_REFUND);
	record.setRegStatus(RegisterConstant.STATE_NORMAL_USER_CANCELING);
	record.setUpdateTime(date.getTime());
	record.setRefundTime(date.getTime());
	} else {
	//退费失败/异常
	logger.info("订单号:{},第3方平台退费失败或异常", record.getOrderNo());
	interfaceResultMap.put("refundIsSuccess", false);
	record.setPayStatus(OrderConstant.STATE_REFUNDING);
	record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
	record.setUpdateTime(date.getTime());
	record.setFailureMsg(alipayRefundResponse.getResultMsg());
	interfaceResultMap.put("failMsg", record.getFailureMsg());
	}
	} else if (refund instanceof UnionpayRefund) {
	// TODO 新增
	if (logger.isDebugEnabled()) {
	logger.debug("UnionpayRefund : {}", JSON.toJSONString(refund));
	}

	UnionpayRefundResponse unionpayRefundResponse = refundService.unionpayRefund((UnionpayRefund) refund);

	Date date = new Date();

	if (StringUtils.equals(unionpayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
	//退费成功
	logger.info("订单号:{},第3方平台退费成功", record.getOrderNo());
	interfaceResultMap.put("refundIsSuccess", true);
	record.setAgtRefundOrdNum(unionpayRefundResponse.getAgtRefundOrderNo());
	record.setPayStatus(OrderConstant.STATE_REFUND);
	record.setRegStatus(RegisterConstant.STATE_NORMAL_USER_CANCELING);
	record.setUpdateTime(date.getTime());
	record.setRefundTime(date.getTime());
	} else {
	//退费失败/异常
	logger.info("订单号:{},第3方平台退费失败或异常", record.getOrderNo());
	interfaceResultMap.put("refundIsSuccess", false);
	record.setPayStatus(OrderConstant.STATE_REFUNDING);
	record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND);
	record.setUpdateTime(date.getTime());
	record.setFailureMsg(unionpayRefundResponse.getResultMsg());
	interfaceResultMap.put("failMsg", record.getFailureMsg());
	}

	}

	registerInfoSvc.updateRecordForHisRefund(record);
	interfaceResultMap.put(BizConstant.TRADE_REFUND_KEY, refund);
	}
	} else {
	interfaceResultMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
	interfaceResultMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, inValidTip);
	}
	}
	logger.info("resMap:" + JSON.toJSONString(interfaceResultMap));
	logger.info("refundRegisterConfirm end..........................................................");
	return new RespBody(Status.OK, interfaceResultMap);
	}*/

	/**
	 * 第3方退费成功 后续处理 操作流程:his预约/当班退费 -->成功-修改系统挂号记录、订单 / 失败-写入异常队列
	 * 状态值STATE_EXCEPTION_REFUND_HIS_COMMIT=9
	 * 
	 * @param vo
	 * @return
	 */
	/*@RequestMapping(value = "/refundSuccess")
	public ModelAndView refundRegisterSuccess(TradeParamsVo vo) {
		logger.info("refundRegisterSuccess start..........................................................");
		RegisterRecord record = registerInfoSvc.findRecordByOrderNo(vo.getOrderNo());
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/register/regRecordInfo");
		if (record.getRegStatus().intValue() != RegisterConstant.STATE_NORMAL_USER_CANCEL) {
			Integer regType = record.getRegType();
			RefundRegRequest requet = record.covertRefundRegRequestVo();

			// 调用his的预约/当班退费接口
			Map<String, Object> interfaceResMap = null;
			if (regType.intValue() == RegisterConstant.REG_TYPE_CUR) {
				interfaceResMap = regBizManager.refundCurReg(record.getHospitalCode(), record.getBranchHospitalCode(), requet);
			} else {
				interfaceResMap = regBizManager.refundReg(record.getHospitalCode(), record.getBranchHospitalCode(), requet);
			}
			logger.info("refundReg resMap:" + JSON.toJSONString(interfaceResMap));
			boolean isException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);

			if (isException) {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND_HIS_COMMIT);
				Thread refundRegThread = new Thread(new HisRefundRegRunnable(record), "refundRegThread.refundHisCommit");
				refundRegThread.start();
			} else {
				boolean isSuccess = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
				if (isSuccess) {
					record.setRegStatus(RegisterConstant.STATE_NORMAL_USER_CANCEL);
					registerInfoSvc.updateRecordForHisRefund(record);
					if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_USER_CANCEL) {
						commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_REFUND_SUCCESS);
					}
				} else {
					//失败当作异常处理
					record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND_HIS_COMMIT);
					Thread refundRegThread = new Thread(new HisRefundRegRunnable(record), "refundRegThread.refundHisCommit");
					refundRegThread.start();
				}
			}
			modelAndView.addObject("record", record);
		}
		return modelAndView;
	}*/

	/**
	 * 第3方退费异常 后续处理 操作流程:第3方退费查询接口
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/refundException")
	public ModelAndView refundException(TradeParamsVo vo) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/register/regRecordInfo");
		if (vo.getIsException()) {
			modelAndView.addObject(BizConstant.METHOD_INVOKE_RES_MSG_KEY, vo.getFailMsg());
		} else {
			modelAndView.addObject(BizConstant.METHOD_INVOKE_RES_MSG_KEY, vo.getFailMsg());
		}

		RegisterRecord record = registerInfoSvc.findRecordByOrderNo(vo.getOrderNo());

		modelAndView.addObject("record", record);
		return modelAndView;
	}

	/**
	 * 对缺失appId appCode的Record
	 * @param record
	 * @param vo
	 */
	private void addAppInfo(RegisterRecord record, CommonParamsVo vo) {
		/*		if (StringUtils.isEmpty(record.getAppId())) {
					record.setAppId(vo.getAppId());
					record.setAppCode(vo.getAppCode());
				}
		*/
		if (StringUtils.isEmpty(record.getAppId())) {
			/*String appCode = vo.getAppCode();
			if (StringUtils.isBlank(appCode)) {
				Integer regMode = record.getRegMode();
				if (regMode != null) {
					if (regMode.intValue() == BizConstant.MODE_TYPE_ALIPAY_VAL) {
						appCode = BizConstant.MODE_TYPE_ALIPAY;
					} else {
						appCode = BizConstant.MODE_TYPE_WEIXIN;
					}
				} else {
					logger.error("缺少参数appCode , 订单号:{}", record.getOrderNo());
				}
			}*/

			// HospitalCodeAndAppVo appVo = baseDatasManager.getAppInfoByHospitalCode(record.getHospitalCode(), appCode);
			String appCode = vo.getAppCode();
			if (StringUtils.isBlank(appCode)) {
				HospIdAndAppSecretVo hospIdAndAppSecretVo =
						baseDatasManager.getHospitalEasyHealthAppInfo(record.getHospitalId(), vo.getAppCode());
				record.setAppId(hospIdAndAppSecretVo.getAppId());
				record.setAppCode(hospIdAndAppSecretVo.getAppCode());
			} else {
				logger.error("缺少参数appCode , 订单号:{}", record.getOrderNo());
			}
		}
	}
}
