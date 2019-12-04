/**
 * Copyright© 2014-2015 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2015年6月5日
 * @version 1.0
 */
package com.yxw.easyhealth.biz.register.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.register.HadRegDoctor;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.entity.platform.hospital.PayMode;
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.entity.platform.rule.RulePayment;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.TradeParamsVo;
import com.yxw.easyhealth.biz.register.thread.AutoFocusDoctorRunnable;
import com.yxw.easyhealth.biz.register.thread.HisOrderRegRunnable;
import com.yxw.easyhealth.biz.register.thread.SaveHadRegDoctorRunnable;
import com.yxw.easyhealth.biz.vo.RegisterParamsVo;
import com.yxw.easyhealth.biz.vo.SelectItem;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.ServerNoGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.interceptor.repeatrubmit.RepeatSubmitDefinition;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.vo.register.RegRecord;
import com.yxw.interfaces.vo.register.appointment.OrderReg;
import com.yxw.interfaces.vo.register.appointment.PayReg;
import com.yxw.interfaces.vo.register.onduty.PayCurReg;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.biz.usercenter.service.MedicalCardService;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;
import com.yxw.payrefund.utils.TradeCommonHoder;
import com.yxw.utils.DateUtils;

/**
 * @Package: com.yxw.easyhealth.biz.register.controller
 * @ClassName: RegisterConfirmController
 * @Statement: <p>
 *             确认挂号信息，添加就诊人
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
@RequestMapping("/easyhealth/register/confirm")
public class RegisterConfirmController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(RegisterConfirmController.class);
	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
	private RegisterBizManager registerBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
	private RegisterService registerSvc = SpringContextHolder.getBean(RegisterService.class);
	//private BaseDatasManager baseDateManger = SpringContextHolder.getBean(BaseDatasManager.class);
	private MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);

	//	private FamilyService familyService = SpringContextHolder.getBean(FamilyService.class);
	//	private final static String FORWARD_URL = "/easyhealth/register/confirm/registerInfo";
	//	private final static String FAMILY_LIST_KEY = "families";

	/**
	 * 确认挂号信息
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/registerInfo")
	@RepeatSubmitDefinition(needSaveToken = true)
	public ModelAndView registerInfo(HttpServletRequest request, String viaFlag, RegisterParamsVo vo) {

		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/register/confirmRegisterInfo");

		try {
			if (StringUtils.isEmpty(vo.getOpenId())) {
				vo.setOpenId(getOpenId(request));
			}

			RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(vo.getHospitalCode());

			modelAndView.addObject("regFeeAlias", ruleRegister.getRegFeeAlias());

			/**
			 * 在线支付控制 1-必须支付 2-不用支付 3-暂不支付 暂不支付显示是否支付挂号费选择
			 */
			Integer onlinePaymentControl = null;
			Integer regType = vo.getRegType();
			if (regType == null) {
				regType = RegisterConstant.REG_TYPE_CUR;
			}
			if (regType.intValue() == RegisterConstant.REG_TYPE_CUR) {
				onlinePaymentControl = ruleRegister.getOnlinePaymentControl();
			} else {
				onlinePaymentControl = ruleRegister.getAppointmentPaymentControl();
			}
			// 若后台未配置此项规则 则默认为必须支付
			if (onlinePaymentControl == null) {
				onlinePaymentControl = BizConstant.PAYMENT_TYPE_MUST;
			}

			/**预约是否走医保流程*/
			Integer isBasedOnMedicalInsuranceAppoint = ruleRegister.getIsBasedOnMedicalInsuranceAppoint();
			Integer isBasedOnMedicalInsuranceToday = ruleRegister.getIsBasedOnMedicalInsuranceToday();
			if (isBasedOnMedicalInsuranceAppoint == null) {
				isBasedOnMedicalInsuranceAppoint = BizConstant.BASEDON_MEDICAL_INSURANCE_NO;
			}

			if (isBasedOnMedicalInsuranceToday == null) {
				isBasedOnMedicalInsuranceToday = BizConstant.BASEDON_MEDICAL_INSURANCE_NO;
			}
			vo.setIsBasedOnMedicalInsuranceAppoint(isBasedOnMedicalInsuranceAppoint);
			vo.setIsBasedOnMedicalInsuranceToday(isBasedOnMedicalInsuranceToday);

			/** 暂不支付的提示设置 ***/
			if (onlinePaymentControl.intValue() == BizConstant.PAYMENT_TYPE_SUPPORT_TEMPORARILY_NOT) {
				String pausePayMentTip = ruleRegister.getConfirmRegInfoTip();
				if (StringUtils.isBlank(pausePayMentTip)) {
					pausePayMentTip = RegisterConstant.DEF_TIP_PAUSE_PAYMENT_MSG;
				}

				modelAndView.addObject("pausePayMentTip", pausePayMentTip);
			}

			vo.setOnlinePaymentControl(onlinePaymentControl);

			/** 是否显示病情描述 ***/
			Integer isViewDiseaseDesc = ruleRegister.getIsViewDiseaseDesc();
			if (isViewDiseaseDesc == null) {
				isViewDiseaseDesc = BizConstant.DEFAULT_VIEW_DISEASEDESC;
			}
			vo.setIsViewDiseaseDesc(isViewDiseaseDesc);

			/**是否显示挂号费*/
			Integer isViewRegFee = ruleRegister.getIsViewRegFee();
			if (isViewRegFee != null) {
				vo.setIsViewRegFee(isViewRegFee);
			}

			/*** 是否检查患者类型 ***/
			Integer isQueryPatientType = ruleRegister.getIsQueryPatientType();
			if (isQueryPatientType == null) {
				isQueryPatientType = RegisterConstant.IS_QUERY_PATIENT_TYPE_NO;
			}
			vo.setIsQueryPatientType(isQueryPatientType);

			/** 设置挂号方式 当班挂号/预约挂号 ***/
			String selectRegDate = vo.getSelectRegDate();
			String now = BizConstant.YYYYMMDD.format(new Date());
			if (StringUtils.isNotBlank(selectRegDate) && selectRegDate.startsWith(now)) {
				vo.setRegType(RegisterConstant.REG_TYPE_CUR);
			} else {
				vo.setRegType(RegisterConstant.REG_TYPE_APPOINTMENT);

				/*** 预约挂号是否需要选择挂号人群类型 ****/
				Integer isViewPopulationType = ruleRegister.getIsViewPopulationType();
				if (isViewPopulationType != null && isViewPopulationType.intValue() == RuleConstant.IS_VIEW_POPULATION_TYPE_YES) {
					List<SelectItem> populationTypes = buildSelectItems(ruleRegister.getPopulationType(), RuleConstant.populationTypeMap);
					modelAndView.addObject("populationTypes", populationTypes);
					vo.setIsViewPopulationType(isViewPopulationType);
				}

				Integer isViewAppointmentType = ruleRegister.getIsViewAppointmentType();
				if (isViewAppointmentType != null && isViewAppointmentType.intValue() == RuleConstant.IS_VIEW_APPOINTMENT_TYPE_YES) {
					List<SelectItem> appointmentTypes =
							buildSelectItems(ruleRegister.getAppointmentType(), RuleConstant.appointmentTypeMap);
					modelAndView.addObject("appointmentTypes", appointmentTypes);
					vo.setIsViewAppointmentType(isViewAppointmentType);
				}
			}

			/********* app支付方式设置 ***************/
			RulePayment payRule = ruleConfigManager.getRulePaymentByHospitalCode(vo.getHospitalCode());
			modelAndView.addObject("tradeTypes", payRule.getTradeTypesMap().get(vo.getAppCode()));
			modelAndView.addObject("defaultTradeType", payRule.getDefaultTradeTypesMap().get(vo.getAppCode()));

			modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
			modelAndView.addObject(BizConstant.TRADE_URL_KEY, TradeCommonHoder.getTradeUrl());

			// 支付信息
			BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);
			List<PayMode> payModes = baseDatasManager.getPayModesByPlatformCode(vo.getAppCode());

			System.out.println("payModes++++++++" + JSONObject.toJSONString(payModes, true));

			modelAndView.addObject("payModes", payModes);
			modelAndView.addObject("viaFlag", viaFlag);

			// 挂号前温馨提示    		--- add by dfw 2015-12-30
			String preRegisterWarmTip = ruleRegister.getPreRegisterWarmTip();
			modelAndView.addObject("preRegisterWarmTip", preRegisterWarmTip);
		} catch (Exception e) {
			logger.error("获取挂号信息异常", e);
			throw new SystemException(e.getCause());
		}

		return modelAndView;
	}

	/**
	 * 构造下拉选择
	 * 
	 * @param itemStrs
	 * @param dataMap
	 *            所有下拉选择项的定义map
	 * @return
	 */
	private List<SelectItem> buildSelectItems(String itemStrs, Map<String, String> dataMap) {
		List<SelectItem> items = new ArrayList<SelectItem>();
		SelectItem item = null;
		String text = null;
		for (String value : itemStrs.split(",")) {
			if (StringUtils.isNotBlank(value)) {
				text = dataMap.get(value);
				if (StringUtils.isNotBlank(text)) {
					item = new SelectItem(text, value);
					items.add(item);
				}
			}
		}
		return items;
	}

	/**
	 * 患者类型查询提示语
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年5月24日 
	 * @param request
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPatientType", method = RequestMethod.POST)
	public RespBody queryPatientType(HttpServletRequest request, RegisterParamsVo vo) {

		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(vo.getHospitalCode());
		String patientTypeTip = ruleRegister.getPatientTypeTip();
		Map<String, Object> resMap = new HashMap<String, Object>();
		String hospitalCode = vo.getHospitalCode();
		String branchCode = vo.getBranchHospitalCode();
		String cardNo = vo.getCardNo();
		String deptCode = vo.getDeptCode();
		String cardType = String.valueOf(vo.getCardType());
		if (StringUtils.isBlank(hospitalCode) || StringUtils.isBlank(branchCode) || StringUtils.isBlank(cardNo)
				|| StringUtils.isBlank(cardType) || StringUtils.isBlank(deptCode)) {
			resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, false);
			resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "患者参数缺失,请您重新绑卡");
		} else {
			resMap = registerBizManager.getPatientType(hospitalCode, branchCode, cardNo, cardType, deptCode);
			boolean isException = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
			if (isException) {
				resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, false);
				resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, BizConstant.INTERFACE_RES_FAILURE_MSG);
			}
		}
		if (StringUtils.isNotBlank(patientTypeTip)) {
			resMap.put("patientTypeTip", patientTypeTip);
		}
		return new RespBody(Status.OK, resMap);
	}

	/**
	 * 判断是否符合挂号规则
	 * 
	 * @param vo
	 * @param ruleRegister
	 * @return
	 */
	private Map<String, Object> isValidRegister(RegisterRecord vo, RuleRegister ruleRegister) {

		Map<String, Object> resMap = new HashMap<String, Object>();

		String hospitalId = vo.getHospitalId();
		String branchHospitalId = vo.getBranchHospitalId();
		String openId = vo.getOpenId();
		String cardNo = vo.getCardNo();

		GregorianCalendar now = new GregorianCalendar();
		// 今天0:00的时间戳
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		long dayTime = now.getTimeInMillis();

		// 本周1 0:00的时间戳
		now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		long weekTime = now.getTimeInMillis();

		now = new GregorianCalendar();
		now.add(Calendar.MONTH, -1);
		long monthTime = now.getTimeInMillis();

		List<Integer> regStatuses = new ArrayList<Integer>();
		regStatuses.add(RegisterConstant.STATE_NORMAL_HAD);
		regStatuses.add(RegisterConstant.STATE_NORMAL_HAVING);
		List<RegisterRecord> records =
				registerSvc.findOverTimeRecords(hospitalId, branchHospitalId, openId, cardNo, regStatuses, monthTime);

		Calendar monthNow = Calendar.getInstance();
		monthNow.set(Calendar.HOUR_OF_DAY, 0);
		monthNow.set(Calendar.MINUTE, 0);
		monthNow.set(Calendar.SECOND, 0);
		monthNow.set(Calendar.DAY_OF_MONTH, 1);
		long monthFirstTime = monthNow.getTimeInMillis();

		List<RegisterRecord> recordsPerMonth =
				registerSvc.findOverTimeRecords(hospitalId, branchHospitalId, openId, cardNo, regStatuses, monthFirstTime);

		boolean isValid = true;
		String msgInfo = null;
		// 是否是重复提交
		boolean isReInvoke = false;
		RegisterRecord reInvokeRecord = null;
		if (!CollectionUtils.isEmpty(records)) {
			// 1.是否是返回后重复提交
			RegisterRecord record = records.get(0);
			String deptCode = record.getDeptCode() == null ? "" : record.getDeptCode();
			String scheduleDateStr = BizConstant.YYYYMMDD.format(record.getScheduleDate());

			String beginTimeStr = null;
			if (record.getBeginTime() != null) {
				beginTimeStr = BizConstant.HHMM.format(record.getBeginTime());
			}

			String endTimeStr = null;
			if (record.getEndTime() != null) {
				endTimeStr = BizConstant.HHMM.format(record.getEndTime());
			}

			// 本次挂号的就诊日期
			String voScheduleDateStr = BizConstant.YYYYMMDD.format(vo.getScheduleDate());
			String voBeginTimeStr = null;
			if (vo.getBeginTime() != null) {
				voBeginTimeStr = BizConstant.HHMM.format(vo.getBeginTime());
			}
			String voEndTimeStr = null;
			if (vo.getEndTime() != null) {
				voEndTimeStr = BizConstant.HHMM.format(vo.getEndTime());
			}
			Integer voTimeFlag = vo.getTimeFlag();

			boolean isBeginSame = true;
			if (StringUtils.isNotBlank(beginTimeStr) && !beginTimeStr.equalsIgnoreCase(voBeginTimeStr)) {
				isBeginSame = false;
			}

			boolean isEndSame = true;
			if (StringUtils.isNotBlank(endTimeStr) && !endTimeStr.equalsIgnoreCase(voEndTimeStr)) {
				isEndSame = false;
			}

			boolean isTimeFlagSame = true;
			if (record.getTimeFlag() != null && voTimeFlag != null && record.getTimeFlag().intValue() != voTimeFlag.intValue()) {
				isEndSame = false;
			}

			if (record.getOpenId().equalsIgnoreCase(vo.getOpenId()) && record.getDoctorCode().equalsIgnoreCase(vo.getDoctorCode())
					&& deptCode.equalsIgnoreCase(vo.getDeptCode()) && scheduleDateStr.equalsIgnoreCase(voScheduleDateStr) && isBeginSame
					&& isEndSame && isTimeFlagSame && record.getCardNo().equalsIgnoreCase(vo.getCardNo())) {
				if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAVING
						|| ( record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAD && record.getOnlinePaymentType()
								.intValue() == BizConstant.PAYMENT_TYPE_SUPPORT_TEMPORARILY_NOT )) {
					isReInvoke = true;
					reInvokeRecord = record;
				}
			}

			if (!isReInvoke) {
				String selectedDoctorCode = vo.getDoctorCode();
				// 本日同一医生号次数
				int doctorCount = 0;
				// 本日挂号次数
				int dayCount = 0;
				// 本周挂号次数
				int weekCount = 0;
				// 本月挂号次数
				int monthCount = 0;

				if (!CollectionUtils.isEmpty(recordsPerMonth)) {
					monthCount = recordsPerMonth.size();
				}
				for (RegisterRecord reg : records) {
					// 历史挂号记录的就诊日期
					String scheduleDate = BizConstant.YYYYMMDD.format(reg.getScheduleDate());

					if (StringUtils.isNotBlank(reg.getDoctorCode()) && reg.getDoctorCode().equalsIgnoreCase(selectedDoctorCode)
							&& scheduleDate.equalsIgnoreCase(voScheduleDateStr) && cardNo.equalsIgnoreCase(reg.getCardNo())) {
						doctorCount++;
					}
					if (dayTime < reg.getRegisterTime()) {
						dayCount++;
					}
					if (weekTime < reg.getRegisterTime()) {
						weekCount++;
					}
				}

				// 规则 ：每张就诊卡每天允许挂同一医生号最大次数
				Integer regMaximumSameDoctor = ruleRegister.getRegMaximumSameDoctor();
				if (regMaximumSameDoctor == null) {
					regMaximumSameDoctor = 3;
				}

				// 规则 ：每张就诊卡每天允许最大挂号次数
				Integer regMaximumInDay = ruleRegister.getRegMaximumInDay();
				if (regMaximumInDay == null) {
					regMaximumInDay = 5;
				}

				// 规则 ：每张就诊卡每周允许挂号次数
				Integer regMaximumInWeek = ruleRegister.getRegMaximumInWeek();
				if (regMaximumInWeek == null) {
					regMaximumInWeek = 10;
				}

				// 规则 ：每张就诊卡每月允许挂号次数
				Integer regMaximumInMonth = ruleRegister.getRegMaximumInMonth();
				if (regMaximumInMonth == null) {
					regMaximumInMonth = 10;
				}

				if (regMaximumSameDoctor <= doctorCount) {
					isValid = false;
					msgInfo = ruleRegister.getOverMaximumSameDoctorTip();
				} else if (regMaximumInDay <= dayCount) {
					isValid = false;
					msgInfo = ruleRegister.getOverMaximumInDayTip();
				} else if (regMaximumInWeek <= weekCount) {
					isValid = false;
					msgInfo = ruleRegister.getOverMaximumInWeekTip();
				} else if (regMaximumInMonth <= monthCount) {
					isValid = false;
					msgInfo = ruleRegister.getOverMaximumInMonthTip();
				}

			}
		}

		if (!isReInvoke) {

			// 取消挂号次数超过上限，不允许再次挂号

			List<Integer> regStatus = new ArrayList<Integer>();
			regStatus.add(RegisterConstant.STATE_NORMAL_USER_CANCEL);
			//regStatus.add(RegisterConstant.STATE_NORMAL_PAY_TIMEOUT_CANCEL);

			GregorianCalendar inDay = new GregorianCalendar();
			inDay.set(Calendar.HOUR_OF_DAY, 0);
			inDay.set(Calendar.MINUTE, 0);
			inDay.set(Calendar.SECOND, 0);
			long overTimeInDay = inDay.getTimeInMillis();

			GregorianCalendar inMonth = new GregorianCalendar();
			inMonth.set(Calendar.DAY_OF_MONTH, 1);
			inMonth.set(Calendar.HOUR_OF_DAY, 0);
			inMonth.set(Calendar.MINUTE, 0);
			inMonth.set(Calendar.SECOND, 0);
			long overTimeInMonth = inMonth.getTimeInMillis();

			List<RegisterRecord> recordsInDay =
					registerSvc.findOverTimeRecords(hospitalId, branchHospitalId, openId, cardNo, regStatus, overTimeInDay);
			List<RegisterRecord> recordsInMonth =
					registerSvc.findOverTimeRecords(hospitalId, branchHospitalId, openId, cardNo, regStatus, overTimeInMonth);

			if (!CollectionUtils.isEmpty(recordsInDay)) {
				Integer regCancelMaxnumInDay = ruleRegister.getRegCancelMaxnumInDay();
				if (regCancelMaxnumInDay != null) {
					if (recordsInDay.size() >= regCancelMaxnumInDay.intValue()) {
						isValid = false;
						msgInfo = ruleRegister.getOverCancelMaxnumInDayTip();
						if (StringUtils.isBlank(msgInfo)) {
							msgInfo = RegisterConstant.DEF_TIP_OVER_CANCEL_MAXNUM_IN_DAY;
						}
					}
				}
			}
			if (!CollectionUtils.isEmpty(recordsInMonth)) {
				Integer regCancelMaxnumInMonth = ruleRegister.getRegCancelMaxnumInMonth();
				if (regCancelMaxnumInMonth != null) {
					if (recordsInMonth.size() >= regCancelMaxnumInMonth.intValue()) {
						isValid = false;
						msgInfo = ruleRegister.getOverCancelMaxnumInMonthTip();
						if (StringUtils.isBlank(msgInfo)) {
							msgInfo = RegisterConstant.DEF_TIP_OVER_CANCEL_MAXNUM_IN_MONTH;
						}
					}
				}
			}

		}
		resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, isValid);
		resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, msgInfo);
		resMap.put(BizConstant.METHOD_INVOKE_RE_OPERATION, isReInvoke);
		resMap.put(BizConstant.METHOD_INVOKE_RES_DATA_KEY, reInvokeRecord);
		return resMap;
	}

	/**
	 * 处理重复订单
	 * 
	 * @param record
	 *            新的订单信息
	 * @return
	 */
	private void handleReRecord(RegisterRecord record, Map<String, Object> resMap, HttpServletRequest request, RegisterParamsVo vo) {
		RegisterRecord reInvokeRecord = (RegisterRecord) resMap.get(BizConstant.METHOD_INVOKE_RES_DATA_KEY);
		reInvokeRecord.setAppId(vo.getAppId());
		reInvokeRecord.setAppCode(vo.getAppCode());
		// 判断是否选择了不同的交易方式
		if (reInvokeRecord.getTradeMode().intValue() != record.getTradeMode().intValue()) {
			reInvokeRecord.setTradeMode(record.getTradeMode());
			registerSvc.updateTradeMode(reInvokeRecord);
		}
		reInvokeRecord.setAppUrl(getAppUrl(request));

		logger.info("订单号:{},为重复提交订单,不写入数据.", reInvokeRecord.getOrderNo());
		Object pay = registerSvc.buildPayInfo(reInvokeRecord);
		if (reInvokeRecord.getRegStatus().intValue() != RegisterConstant.STATE_NORMAL_HAD) {
			resMap.put(BizConstant.TRADE_PAY_KEY, pay);
		} else {
			if (reInvokeRecord.getOnlinePaymentType().intValue() == BizConstant.PAYMENT_TYPE_SUPPORT_TEMPORARILY_NOT
					&& vo.getIsPay() != null && record.getIsPay().intValue() == BizConstant.PAYMENT_TYPE_SUPPORT_TEMPORARILY_NOT_IS_PAY_YES) {
				resMap.put(BizConstant.TRADE_PAY_KEY, pay);
				resMap.put("currentIsPay", vo.getIsPay());
			}
			resMap.put("currentRegStatus", reInvokeRecord.getRegStatus());
			resMap.put("currentRegOrderNo", reInvokeRecord.getOrderNo());
			resMap.put("currentOpenId", reInvokeRecord.getOpenId());
		}
		resMap.put(BizConstant.TRADE_MODE, reInvokeRecord.getTradeMode());

		BeanUtils.copyProperties(reInvokeRecord, record);
	}

	/**
	 * 生成挂号记录、订单
	 * 
	 * @param vo
	 * @param diseaseDesc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
	@RepeatSubmitDefinition(needRemoveToken = true)
	public RespBody generateOrder(HttpServletRequest request, RegisterParamsVo vo) {
		Map<String, Object> resMap = new HashMap<String, Object>();

		try {
			if (StringUtils.isEmpty(vo.getOpenId())) {
				vo.setOpenId(getOpenId(request));
			}

			Map<String, Object> errorMap = new HashMap<String, Object>();

			/** 判断是否在就诊卡黑名单  ***/
			/*		Boolean isBlockCard = blackListOfCardCache.isBlack(vo.getHospitalId(), vo.getCardNo());
					if (isBlockCard) {
						logger.info("本就诊卡在黑名单内，无法挂号 ,就诊卡号:{},医院:{}", vo.getCardNo(), vo.getHospitalName());
						errorMap.put("msgInfo", "此就诊卡已禁止挂号！");
						return new RespBody(Status.ERROR, errorMap);
					}*/

			// 记录openId访问情况
			/*		boolean isBlack = OpenIdVisit.updateOpenId(vo.getAppId(), vo.getOpenId(), OpenIdVisit.ORDER_REG);
					logger.info("isBlack:{} ,AppId:{} ,OpenId:{} ,type:{}", isBlack, vo.getAppId(), vo.getOpenId(), OpenIdVisit.ORDER_REG);
					if (isBlack) {
						logger.info("遇到黄牛直接返回error isBlack:{} ,AppId:{} ,OpenId:{} ,type:{}", isBlack, vo.getAppId(), vo.getOpenId(),
								OpenIdVisit.ORDER_REG);
						errorMap.put("msgInfo", "请通过正规渠道挂号！");
						return new RespBody(Status.ERROR, errorMap);
					}
					if (StringUtils.isNotBlank(vo.getTeddyId())) {
						String sign = genSign(vo);
						if (!vo.getTeddyId().equals(sign)) {
							errorMap.put("msgInfo", "usbId和dsbId相等！");
							return new RespBody(Status.ERROR, errorMap);
						}
					} else {
						errorMap.put("msgInfo", "usbId不能为空！");
						return new RespBody(Status.ERROR, errorMap);
					}*/

			//先判断用户是否已经绑卡
			MedicalCard medicalCard =
					medicalCardService.findCardByHospitalIdAndOpenIdAndCardNo(vo.getHospitalId(), vo.getOpenId(), vo.getCardNo());
			if (medicalCard == null) {
				logger.info("疑似黄牛非法下单，openId:{},cardNo:{}", vo.getOpenId(), vo.getCardNo());
				errorMap.put("msgInfo", "疑似黄牛非法下单！");
				return new RespBody(Status.ERROR, errorMap);
			}
			// 订单处理日志
			StringBuffer handleLog = new StringBuffer();

			// 获取该医院的挂号规则
			RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(vo.getHospitalCode());
			RuleEdit ruleEdit = ruleConfigManager.getRuleEditByHospitalCode(vo.getHospitalCode());

			// 判断挂号是否符合挂号规则 以及是否为重复提交
			boolean isOrderSuccess = true;
			RegisterRecord record = vo.convertRegisterRecord(ruleEdit.getIsCompatibleOtherPlatform());
			resMap = isValidRegister(record, ruleRegister);
			Boolean isReInvoke = (Boolean) resMap.get(BizConstant.METHOD_INVOKE_RE_OPERATION);
			if (isReInvoke) {
				handleReRecord(record, resMap, request, vo);
			} else {
				Boolean isValid = (Boolean) resMap.get(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY);

				if (isValid) {
					// 生成标准平台订单号
					String orderNo =
							OrderNoGenerator.genOrderNo(String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode())),
									String.valueOf(record.getTradeMode()), OrderConstant.ORDER_TYPE_PAYMENT, BizConstant.BIZ_TYPE_REGISTER,
									ServerNoGenerator.getServerNoByIp(), record.getOpenId());
					logger.info("生成标准平台订单号,orderNo:{},  vo:{}", orderNo, JSON.toJSONString(vo));
					record.setOrderNo(orderNo);
					record.setOrderNoHashVal(Math.abs(orderNo.hashCode()));
					long registerTime = OrderNoGenerator.getDateTime(orderNo).getTime();
					record.setRegisterTime(registerTime);

					// 加入到曾挂号医生处理
					HadRegDoctor hadRegDoctor = new HadRegDoctor(record);
					Thread hadRegDoctorThread = new Thread(new SaveHadRegDoctorRunnable(hadRegDoctor));
					hadRegDoctorThread.start();

					// 自动关注医生
					/*
					 * Thread autoFocusDoctorThread = new Thread(new
					 * AutoFocusDoctorRunnable(record)); autoFocusDoctorThread.start();
					 */

					logger.info("生成订单后开始调用his接口,orderNo:{},", orderNo);
					Map<String, Object> interfaceResMap = new HashMap<String, Object>();
					if (record.getRegType().intValue() == RegisterConstant.REG_TYPE_CUR) {
						interfaceResMap = registerBizManager.orderCurReg(record);
					} else {
						interfaceResMap = registerBizManager.orderReg(record);
					}
					logger.info("调用his挂号接口,orderNo:{},interfaceResMap:{}", orderNo, JSON.toJSONString(interfaceResMap));
					boolean isException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
					boolean isNeedSave = false;

					// 出现异常后 查询his订单 存在继续执行业务 不存在/查询异常不继续业务流程
					if (isException) {
						isOrderSuccess = false;
						boolean isStartThread = false;
						// 查询his 是否生成订单
						Map<String, Object> queryResMap = registerBizManager.queryRegisterRecords(record);

						Boolean isHappenException = (Boolean) queryResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
						if (isHappenException) {
							isStartThread = true;
						} else {
							Boolean isSuccess = (Boolean) queryResMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
							String msgInfo = (String) queryResMap.get(BizConstant.INTERFACE_MAP_MSG_KEY);
							if (isSuccess) {
								Object obj = queryResMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);

								interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, isHappenException);
								interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, isSuccess);
								interfaceResMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, obj);
								interfaceResMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, msgInfo);

								if (obj != null) {
									List<RegRecord> records = (List<RegRecord>) obj;
									if (!CollectionUtils.isEmpty(records)) {
										RegRecord regRecord = records.get(0);
										// 设置医院返回的order信息设置record
										addHisRecordInfo(regRecord, record);
										setUpRegStatus(record, ruleRegister);
										isNeedSave = true;
									} else {
										isStartThread = true;
									}
								}
							} else {
								isStartThread = true;
							}
						}

						// 子线程处理异常
						if (isStartThread) {
							Thread hisOrderRegThread = new Thread(new HisOrderRegRunnable(record));
							hisOrderRegThread.start();
						}
					} else {
						isOrderSuccess = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
						if (isOrderSuccess) {
							handleLog.append(BizConstant.YYYYMMDDHHMMSS.format(new Date()).concat(":挂号订单(未支付)生成成功;"));
							OrderReg orderReg = (OrderReg) interfaceResMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
							// 设置医院返回的order信息设置record
							addHisOrderInfo(orderReg, record);
							// 根据在线支付控制规则设置regStatus
							setUpRegStatus(record, ruleRegister);
							isNeedSave = true;
						} else {
							Object msgInfo = interfaceResMap.get(BizConstant.INTERFACE_MAP_MSG_KEY);
							if (msgInfo != null) {
								interfaceResMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, msgInfo.toString());
								logger.info("订单号:{},his挂号失败:原因:{},此订单作废，不保存。", record.getOrderNo(), msgInfo.toString());
							} else {
								interfaceResMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, ruleRegister.getRegFailedTip());
							}
						}
					}

					resMap = interfaceResMap;
					if (isNeedSave) {
						record.setAppUrl(getAppUrl(request));
						record.addLogInfo(handleLog.toString());
						Map<String, Object> map = registerSvc.saveRegisterInfo(record);

						/*Integer tradeAmout = (Integer) map.get(BizConstant.URL_PARAM_TRADE_AMOUT);
						if (tradeAmout != 0
								&& ( record.getOnlinePaymentType().intValue() == BizConstant.PAYMENT_TYPE_MUST || ( record
										.getOnlinePaymentType().intValue() == BizConstant.PAYMENT_TYPE_SUPPORT_TEMPORARILY_NOT
										&& record.getIsPay() != null && record.getIsPay().intValue() == BizConstant.PAYMENT_TYPE_MUST ) )) {

							String payParam = getPayParamStr(map);
							if (StringUtils.isNotBlank(payParam)) {
								try {
									RSAEncrypt rsaEncrypt = new RSAEncrypt(null, privateKeySpec);
									String cipher = rsaEncrypt.encryptHexByPrivateKey(payParam.getBytes(), false);
									logger.info("cipher:" + cipher);
									map.put("payParam", cipher);
									map.remove(BizConstant.TRADE_PAY_KEY);
								} catch (Exception e) {
									e.printStackTrace();
									logger.error("RSAEncrypt:" + e.getMessage() + "  " + e.getClass());
									return new RespBody(Status.ERROR, "RSAEncrypt对称加密失败");
								}
							} else {
								return new RespBody(Status.ERROR, "RSAEncrypt对称加密失败");
							}
						}*/

						resMap.putAll(map);
						logger.info("生成订单成功>>his锁号成功.hospitalName:{},openId:{},orderNo:{} ,LockId/hisOrdNo:{} ",
								new Object[] { record.getHospitalName(), record.getOpenId(), record.getOrderNo(), record.getHisOrdNo() });
						resMap.put(BizConstant.TRADE_MODE, record.getTradeMode());

					} else {

					}
				} else {
					isOrderSuccess = false;
				}
			}

			/** 零元支付(去除在线不支付的情况) 处理 **/

			Integer tradeAmout = record.getRealRegFee() + record.getRealTreatFee();

			resMap.put(BizConstant.URL_PARAM_TRADE_AMOUT, tradeAmout);

			Integer paymentControl = null;
			if (record.getRegType().intValue() == RegisterConstant.REG_TYPE_CUR) {
				paymentControl = ruleRegister.getOnlinePaymentControl();
			} else {
				paymentControl = ruleRegister.getAppointmentPaymentControl();
			}
			if (tradeAmout == 0 && paymentControl != BizConstant.PAYMENT_TYPE_NOT_NEED && isOrderSuccess) {
				resMap.putAll(dealWithZeroTradeAmout(record));
			}

			if (StringUtils.isBlank(record.getAppUrl())) {
				record.setAppUrl(getAppUrl(request));
			}

			resMap.put("currentRegStatus", record.getRegStatus()+"");
			resMap.put("currentRegOrderNo", record.getOrderNo()+"");
			resMap.put("currentOpenId", record.getOpenId()+"");

			resMap.put(BizConstant.TRADE_URL_KEY, TradeCommonHoder.getTradeUrl()+"");
			//resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, null);
			resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, "");

		} catch (Exception e) {
			logger.error("生成订单信息失败", e);
			throw new SystemException(e.getCause());
		}

		JSONObject obj = new JSONObject(resMap);
		obj.put("hospitalCode", "gzhszhyy");
		obj.put("openId", "def6caa7177845c2ae595a067bb88302");
		System.out.println(obj.toString());
		return new RespBody(Status.OK, obj);
	}

	private Map<String, Object> dealWithZeroTradeAmout(RegisterRecord record) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		/** 零元支付 消息推送 **/
		RegisterBizManager registerBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
		Map<String, Object> interfaceResMap = null;
		if (record.getRegType() != null && record.getRegType().intValue() == RegisterConstant.REG_TYPE_CUR) {
			interfaceResMap = registerBizManager.registerCurPayReg(record);
		} else {
			interfaceResMap = registerBizManager.registerPayReg(record);
		}
		// 零元支付后与his确认是否成功
		boolean isSuccess = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
		if (!isSuccess) {
			record.setPayStatus(OrderConstant.STATE_NO_PAYMENT);
			record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY_HIS_COMMIT);
			// 更新信息到数据库以及缓存
			registerSvc.updateStatusForPay(record);
			logger.info("订单号:{},零元支付his确认失败.原因:{}", record.getOrderNo(), interfaceResMap.get(BizConstant.INTERFACE_MAP_MSG_KEY));
			resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, interfaceResMap.get(BizConstant.INTERFACE_MAP_MSG_KEY));
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
		} else {
			// 添加 就诊序号 就诊地址 his订单号等
			addRecordInfo(record, interfaceResMap.get(BizConstant.INTERFACE_MAP_DATA_KEY));

			record.addLogInfo("零元支付his确认成功");

			// 更新信息到数据库以及缓存
			registerSvc.updateStatusForPay(record);

			CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
			if (record.getRegType() != null && record.getRegType().intValue() == RegisterConstant.REG_TYPE_CUR) {
				commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_ON_DUTY_PAY_SUCCESS);
			} else {
				commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_APPOINT_PAY_SUCCESS);
			}
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
		}
		return resMap;
	}

	/**
	 * 零元支付 ,添加就诊地址,排队号信息
	 * 
	 * @param record
	 * @param payRegObj
	 */
	private void addRecordInfo(RegisterRecord record, Object payRegObj) {
		if (payRegObj != null) {
			if (record.getRegType() != null && record.getRegType().intValue() == RegisterConstant.REG_TYPE_CUR) {
				PayCurReg payReg = (PayCurReg) payRegObj;
				if (StringUtils.isNotBlank(payReg.getSerialNum())) {
					record.setSerialNum(payReg.getSerialNum());
				}
				if (StringUtils.isNotBlank(payReg.getHisOrdNum())) {
					record.setHisOrdNo(payReg.getHisOrdNum());
				}
				if (StringUtils.isNotBlank(payReg.getVisitLocation())) {
					record.setVisitLocation(payReg.getVisitLocation());
				}

				if (StringUtils.isNotEmpty(payReg.getReceiptNum())) {
					record.setReceiptNum(payReg.getReceiptNum());
				}

				if (StringUtils.isNotEmpty(payReg.getBarCode())) {
					record.setBarcode(payReg.getBarCode());
				}
			} else {
				PayReg payReg = (PayReg) payRegObj;
				if (StringUtils.isNotBlank(payReg.getSerialNum())) {
					record.setSerialNum(payReg.getSerialNum());
				}
				if (StringUtils.isNotBlank(payReg.getHisOrdNum())) {
					record.setHisOrdNo(payReg.getHisOrdNum());
				}
				if (StringUtils.isNotBlank(payReg.getVisitLocation())) {
					record.setVisitLocation(payReg.getVisitLocation());
				}

				if (StringUtils.isNotEmpty(payReg.getReceiptNum())) {
					record.setReceiptNum(payReg.getReceiptNum());
				}

				if (StringUtils.isNotEmpty(payReg.getBarCode())) {
					record.setBarcode(payReg.getBarCode());
				}
			}
		}
	}

	/**
	 * 根据挂号规则的 在线支付控制规则设置regStatus
	 * 
	 * @param record
	 * @param ruleRegister
	 */
	private void setUpRegStatus(RegisterRecord record, RuleRegister ruleRegister) {
		// 是否必须在线支付 是则需要设置付款的截至时间点
		Integer onlinePaymentControl = ruleRegister.getOnlinePaymentControl();
		record.setOnlinePaymentType(onlinePaymentControl == null ? BizConstant.PAYMENT_TYPE_NOT_NEED : onlinePaymentControl);

		if (onlinePaymentControl != null && onlinePaymentControl.intValue() == BizConstant.PAYMENT_TYPE_MUST) {
			Integer paymentTimeOutTime = ruleRegister.getPaymentTimeOutTime();
			if (paymentTimeOutTime == null) {
				paymentTimeOutTime = 30;
			}
			record.setPayTimeoutTime(record.getRegisterTime() + paymentTimeOutTime * 60 * 1000);
			record.setWaitPayTime(paymentTimeOutTime);
			record.setRegStatus(RegisterConstant.STATE_NORMAL_HAVING);
		} else {
			record.setRegStatus(RegisterConstant.STATE_NORMAL_HAD);
			setUpPayOfftTime(record, ruleRegister);
		}

		logger.info("onlinePaymentControl:{},payTimeoutTime:{}", new Object[] { record.getOnlinePaymentType(), record.getPayTimeoutTime() });
	}

	/**
	 * 对暂不支付类型的挂号 设置支付的截至时间
	 * 
	 * @param record
	 */
	private void setUpPayOfftTime(RegisterRecord record, RuleRegister ruleRegister) {
		Integer notPaidpayOffType = ruleRegister.getNotPaidpayOffType();
		if (notPaidpayOffType == null) {
			notPaidpayOffType = RegisterConstant.DEF_NOT_PAID_PAYOFF_TYPE;
		}

		// 就诊时间 YYYYMMDD
		Date scheduleDate = record.getScheduleDate();

		Date startTime = record.getBeginTime();
		Date endTime = record.getEndTime();

		// 就诊开始时间
		Date scheduleBigin = null;
		// 就诊结束时间
		Date scheduleEnd = null;
		try {
			scheduleBigin =
					BizConstant.YYYYMMDDHHMM.parse(BizConstant.YYYYMMDD.format(scheduleDate) + " " + BizConstant.HHMM.format(startTime));
			scheduleEnd =
					BizConstant.YYYYMMDDHHMM.parse(BizConstant.YYYYMMDD.format(scheduleDate) + " " + BizConstant.HHMM.format(endTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			scheduleBigin = scheduleDate;
		}

		/**
		 * 暂不支付模式支付截止时间类型 1、就诊前一天几点 2、就诊当天几点 3、就诊时间段开始前几小时 4、就诊时间段开始前多少分钟
		 * 5、就诊时间段开始的时间 6、就诊时间段结束的时间 7、就诊时间结束前多少分钟 8、不限制
		 */
		GregorianCalendar now = new GregorianCalendar();
		Long payTimeoutTime = null;

		Integer notPaidpayOffTime = ruleRegister.getNotPaidpayOffTime();
		switch (notPaidpayOffType) {
		case 1:
			if (notPaidpayOffTime == null) {
				notPaidpayOffTime = 12;
			}
			now.setTime(scheduleDate);
			now.add(Calendar.DAY_OF_YEAR, -1);
			now.set(Calendar.HOUR_OF_DAY, notPaidpayOffTime);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
			payTimeoutTime = now.getTimeInMillis();
			break;
		case 2:
			if (notPaidpayOffTime == null) {
				notPaidpayOffTime = 12;
			}
			now.setTime(scheduleDate);
			now.set(Calendar.HOUR_OF_DAY, notPaidpayOffTime);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
			payTimeoutTime = now.getTimeInMillis();
			break;
		case 3:
			if (notPaidpayOffTime == null) {
				notPaidpayOffTime = 1;
			}
			now.setTime(scheduleBigin);
			payTimeoutTime = now.getTimeInMillis() - notPaidpayOffTime * 60 * 60 * 1000;
			break;
		case 4:
			if (notPaidpayOffTime == null) {
				notPaidpayOffTime = 30;
			}
			now.setTime(scheduleBigin);
			payTimeoutTime = now.getTimeInMillis() - notPaidpayOffTime * 60 * 1000;
			break;
		case 5:
			now.setTime(scheduleBigin);
			payTimeoutTime = now.getTimeInMillis();
			break;
		case 6:
			now.setTime(scheduleEnd);
			payTimeoutTime = now.getTimeInMillis();
			break;
		case 7:
			if (notPaidpayOffTime == null) {
				notPaidpayOffTime = 30;
			}
			now.setTime(scheduleEnd);
			payTimeoutTime = now.getTimeInMillis() - notPaidpayOffTime * 60 * 1000;
			break;
		case 8:
			payTimeoutTime = null;
			break;
		default:
			break;
		}
		record.setPayTimeoutTime(payTimeoutTime);
	}

	/**
	 * 设置医院返回的order信息设置record
	 * 
	 * @param orderReg
	 * @param record
	 */
	private void addHisOrderInfo(OrderReg orderReg, RegisterRecord record) {
		if (StringUtils.isNotBlank(orderReg.getRegFee())) {
			record.setRealRegFee(Integer.valueOf(orderReg.getRegFee()));
		} else {
			record.setRealRegFee(0);
		}
		if (StringUtils.isNotBlank(orderReg.getTreatFee())) {
			record.setRealTreatFee(Integer.valueOf(orderReg.getTreatFee()));
		} else {
			record.setRealTreatFee(0);
		}

		if (StringUtils.isNotBlank(orderReg.getHisOrdNum())) {
			record.setHisOrdNo(orderReg.getHisOrdNum());
		}
		if (StringUtils.isNotBlank(orderReg.getDesc())) {
			record.setFeeDesc(orderReg.getDesc());
		}
	}

	/**
	 * 设置医院返回的record信息设置record
	 * 
	 * @param orderReg
	 * @param record
	 */
	private void addHisRecordInfo(RegRecord regRecord, RegisterRecord record) {
		if (StringUtils.isNotBlank(regRecord.getRealRegFee())) {
			record.setRealRegFee(Integer.valueOf(regRecord.getRealRegFee()));
		} else {
			record.setRealRegFee(0);
		}
		if (StringUtils.isNotBlank(regRecord.getRealTreatFee())) {
			record.setRealTreatFee(Integer.valueOf(regRecord.getRealTreatFee()));
		} else {
			record.setRealTreatFee(0);
		}

		if (StringUtils.isNotBlank(regRecord.getHisOrdNum())) {
			record.setHisOrdNo(regRecord.getHisOrdNum());
		}
		if (StringUtils.isNotBlank(regRecord.getDesc())) {
			record.setFeeDesc(regRecord.getDesc());
		}
	}

	/**
	 * 挂号页面展示信息-iframe方式实现
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月17日 
	 * @param orderNo
	 * @param openId
	 * @return
	 */
	@RequestMapping(value = "/regOrderInfoByIframe")
	public ModelAndView regOrderInfo(String orderNo) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/register/confirmOrderInfo");
		RegisterRecord record = registerSvc.findRecordByOrderNo(orderNo);
		Boolean isPreferential = false;
		if (isHadPreferential(record)) {
			isPreferential = true;
			RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(record.getHospitalCode());
			String regDiscountTip = ruleRegister.getRegDiscountTip();
			if (StringUtils.isBlank(regDiscountTip)) {
				regDiscountTip = RegisterConstant.DEF_TIP_REG_DIS_COUNT;
			}
			modelAndView.addObject("regDiscountTip", regDiscountTip);
		}
		modelAndView.addObject("isPreferential", isPreferential);

		modelAndView.addObject(BizConstant.RECORD_PARAMS_KEY, record);
		return modelAndView;
	}

	/**
	 * 挂号页面展示信息-jsonp方式实现
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月17日 
	 * @param orderNo
	 * @param openId
	 * @param callback
	 * @return
	 */
	@RequestMapping(value = "/regOrderInfoByJsonp")
	@ResponseBody
	public String regOrderInfo(String orderNo, String openId, String callback) {

		StringBuilder sb = new StringBuilder();

		RegisterRecord record = registerSvc.findRecordByOrderNo(orderNo);
		sb.append("<div id='guoHao'><div class='box-list'><ul class='yx-list'>");
		sb.append("<li><div class='label'>医院名称</div><div class='values color666'>" + record.getHospitalName() + "</div></li>");
		sb.append("<li><div class='label'>业务名称</div><div class='values color666'>挂号费</div></li>");
		sb.append("<li><div class='label'>科室</div><div class='values color666'>" + record.getDeptName() + "</div></li>");
		sb.append("<li><div class='label'>医生</div><div class='values color666'>" + record.getDoctorName() + " "
				+ StringUtils.defaultString(record.getDoctorTitle(), "") + "</div></li>");

		String timeFlag = "";
		if (record.getTimeFlag() != null) {
			if (record.getTimeFlag().intValue() == 1) {
				timeFlag = "上午";
			} else if (record.getTimeFlag().intValue() == 2) {
				timeFlag = "下午";
			} else if (record.getTimeFlag().intValue() == 3) {
				timeFlag = "晚午";
			} else if (record.getTimeFlag().intValue() == 4) {
				timeFlag = "全天";
			}
		}
		sb.append("<li><div class='label'>就诊时间</div><div class='values color666'>"
				+ DateUtils.dateToString(record.getScheduleDate(), "yyyy-MM-dd") + " "
				+ DateUtils.dateToString(record.getBeginTime(), "HH:mm") + " " + DateUtils.dateToString(record.getEndTime(), "HH:mm")
				+ timeFlag + "</div></li>");
		sb.append("<li><div class='label'>患者姓名</div><div class='values color666'>" + record.getEncryptedPatientName() + "</div></li>");
		sb.append("<li><div class='label' style='width: 3em'>卡号</div><div class='values color666'>" + record.getCardNo() + "</div></li>");
		sb.append("<li><div class='label'>付款金额</div><div class='values color666'><span class='price'>"
				+ ( record.getRealRegFee() + record.getRealTreatFee() ) / 100 + "</span> 元</div></li>");
		sb.append("</ul></div></div>");

		if (isHadPreferential(record)) {
			RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(record.getHospitalCode());
			String regDiscountTip = ruleRegister.getRegDiscountTip();
			if (StringUtils.isBlank(regDiscountTip)) {
				regDiscountTip = RegisterConstant.DEF_TIP_REG_DIS_COUNT;
			}
			sb.append("<div class='section'><div class='box-tips'> <i class='icon-warn'></i>温馨提示：" + regDiscountTip + "</div></div>");
		}

		RespBody respBody = new RespBody(Status.OK, sb.toString());

		return callback + "(" + JSON.toJSONString(respBody) + ")";
	}

	/**
	 * 判断是否有优惠
	 * 
	 * @param record
	 * @return
	 */
	private Boolean isHadPreferential(RegisterRecord record) {
		Boolean isPreferential = false;
		Integer originalFree = record.getRegFee() + record.getTreatFee();
		Integer actualFree = record.getRealRegFee() + record.getRealTreatFee();
		if (actualFree < originalFree) {
			isPreferential = true;
		}
		return isPreferential;
	}

	/**
	 * 支付后确认his订单
	 * 
	 * @param request
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hisPayConfirm", method = RequestMethod.POST)
	public RespBody hisPayConfirm(HttpServletRequest request, TradeParamsVo vo) {
		int count = 0;
		RegisterRecord record = null;
		boolean isContinue = true;
		Map<String, Object> resMap = new HashMap<String, Object>();
		int step = 1000;
		do {
			// 睡眠1秒 以保证回调能及时写入缓存
			try {
				if (count > 8) {
					isContinue = false;
					logger.info("获取支付回调结果:未收到支付回调请求.当前查询次数:达到设置的最大次数8次,判定此订单为第3方异常订单.订单号:{}", vo.getOrderNo());
					break;
				} else if (count > 1) {
					step = 2000;
				} else if (count > 3) {
					step = 4000;
				} else if (count > 5) {
					step = 6000;
				}
				count++;
				record = registerSvc.findRecordByOrderNo(vo.getOrderNo());
				if (record.getIsHadCallBack() != null && record.getIsHadCallBack().intValue() == RegisterConstant.IS_HAD_CALLBACK_YES) {
					isContinue = false;
					break;
				}
				Thread.sleep(step);

				logger.info("获取支付回调结果:未收到支付回调请求.订单号:{},当前查询次数:{}次", vo.getOrderNo(), count);

			} catch (Exception e) {
			}
		} while (isContinue);
		logger.info("orderNo:{},hisPayConfirm regStatus : {} , record.getIsHadCallBack:{}",
				new Object[] { record.getOrderNo(), record.getRegStatus(), record.getIsHadCallBack() });
		// 第3方交易平台支付后没有收到回调 转为RegisterConstant.STATE_EXCEPTION_PAY
		if (record.getIsHadCallBack() == null || record.getIsHadCallBack().intValue() == RegisterConstant.IS_HAD_CALLBACK_NO
				|| record.getRegStatus().intValue() == RegisterConstant.STATE_EXCEPTION_PAY) {
			logger.error("订单号:{},支付回调网络异常.", record.getOrderNo());
			if (record.getIsException() == null || record.getIsException().intValue() != BizConstant.IS_HAPPEN_EXCEPTION_YES) {
				record.setRegStatus(RegisterConstant.STATE_EXCEPTION_PAY);
				record.setPayStatus(OrderConstant.STATE_PAYMENTING);
				record.setIsHadCallBack(RegisterConstant.IS_HAD_CALLBACK_YES);
				record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, "网络异常,系统后台自动处理中,请稍后查看挂号记录的处理结果.");
				registerSvc.updateRecordForAgtPayment(record);
				CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
				commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_APPOINT_PAY_EXP);
			}
		} else {
			if (record.getRegStatus().intValue() == RegisterConstant.STATE_NORMAL_HAD) {
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
				resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, "医院出号成功,<span id='timeCount'>3</span> 秒后系统将自动跳转到订单详情页.");
			} else {
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				if (StringUtils.isNotBlank(record.getFailureMsg())) {
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, "医院出号失败,原因" + record.getFailureMsg() + "系统后台自动退费中,请稍后查看挂号记录的处理结果.");
				} else {
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, "系统后台自动处理中,请稍后查看挂号记录的处理结果.");
				}
			}
		}
		return new RespBody(Status.OK, resMap);
	}

	/**
	 * 支付成功后跳转页面
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/payMentSuccess")
	public ModelAndView payMentSuccess(HttpServletRequest request, TradeParamsVo vo) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/register/payMentSuccess");
		if (StringUtils.isBlank(vo.getOrderNo())) {
			vo.setOrderNo(request.getParameter("out_trade_no"));
		}

		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		return modelAndView;
	}

}
