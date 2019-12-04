/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.datas.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;
import com.yxw.interfaces.service.YxwCommService;
import com.yxw.interfaces.vo.Request;
import com.yxw.interfaces.vo.Response;
import com.yxw.interfaces.vo.register.DocTime;
import com.yxw.interfaces.vo.register.DocTimeRequest;
import com.yxw.interfaces.vo.register.Doctor;
import com.yxw.interfaces.vo.register.PatientTypeRequest;
import com.yxw.interfaces.vo.register.RegInfo;
import com.yxw.interfaces.vo.register.RegInfoRequest;
import com.yxw.interfaces.vo.register.RegRecordRequest;
import com.yxw.interfaces.vo.register.TakeNoRequest;
import com.yxw.interfaces.vo.register.appointment.AckRefundRegRequest;
import com.yxw.interfaces.vo.register.appointment.CancelRegRequest;
import com.yxw.interfaces.vo.register.appointment.OrderReg;
import com.yxw.interfaces.vo.register.appointment.OrderRegRequest;
import com.yxw.interfaces.vo.register.appointment.PayRegRequest;
import com.yxw.interfaces.vo.register.appointment.RefundRegRequest;
import com.yxw.interfaces.vo.register.onduty.AckRefundCurRegRequest;
import com.yxw.interfaces.vo.register.onduty.CancelCurRegRequest;
import com.yxw.interfaces.vo.register.onduty.OrderCurReg;
import com.yxw.interfaces.vo.register.onduty.OrderCurRegRequest;
import com.yxw.interfaces.vo.register.onduty.PayCurRegRequest;
import com.yxw.interfaces.vo.register.onduty.RefundCurRegRequest;
import com.yxw.interfaces.vo.register.stopreg.StopRegRequest;

/**
 * @Package: com.yxw.platform.cache
 * @ClassName: BizDatasCacheManager
 * @Statement: <p>
 *             挂号业务管理
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-23
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@SuppressWarnings("unchecked")
public class RegisterBizManager {
	private static Logger logger = LoggerFactory.getLogger(RegisterBizManager.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * 挂号方式 所有
	 */
	public static final String REG_MODE_ALL = "0";

	/**
	 * 挂号方式 微信公众号
	 */
	public static final String REG_MODE_WEIXIN = "1";

	/**
	 * 挂号方式 支付宝服务窗
	 */
	public static final String REG_MODE_AILPAY = "2";

	/**
	 * 获取科室号源(微信公众号/支付宝服务窗/建康易)
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param deptCode
	 * @param dateStr
	 * @param appCode
	 * @param regType
	 *            当班/预约
	 * @return
	 */
	@Deprecated
	public List<Doctor> searchDayRegSource(String hospitalId, String hospitalCode, String branchHospitalCode, String deptCode,
			String dateStr, String appCode) {
		List<Doctor> doctors = new ArrayList<Doctor>();
		HospIdAndAppSecretVo vo = null;

		String appId = "";

		PlatformSettings settings = null;

		List<Object> params = new ArrayList<Object>();
		params.add(hospitalId);
		params.add(appCode);

		List<Object> hospitalPlatforms = serveComm.get(CacheType.HOSPITAL_CACHE, "findHospitalPlatform", params);
		if (CollectionUtils.isNotEmpty(hospitalPlatforms)) {
			settings = (PlatformSettings) hospitalPlatforms.get(0);
		}

		if (StringUtils.isNotBlank(settings.getAppId())) {
			appId = settings.getAppId();
		}

		params.clear();
		params.add(hospitalId);
		params.add(appId);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getEasyHealthAppByHospitalId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			vo = (HospIdAndAppSecretVo) results.get(0);
		}
		// HospIdAndAppSecretVo vo = hospitalCache.getEasyHealthAppByHospitalId(hospitalId);

		Integer regType = null;
		if (BizConstant.YYYYMMDD.format(new Date()).equalsIgnoreCase(dateStr)) {
			regType = RegisterConstant.REG_TYPE_CUR;
		} else {
			regType = RegisterConstant.REG_TYPE_APPOINTMENT;
		}

		if (vo != null) {
			// 健康易
			if (RegisterConstant.REG_TYPE_CUR == regType.intValue()) {
				doctors = searchDayRegSource(hospitalCode, branchHospitalCode, deptCode, dateStr);
			} else {
				doctors = getRegInfo(hospitalCode, branchHospitalCode, dateStr, deptCode);
				if (!org.springframework.util.CollectionUtils.isEmpty(doctors)) {
					params.clear();
					params.add(hospitalCode);
					params.add(branchHospitalCode);
					params.add(deptCode);
					params.add(String.valueOf(regType));
					params.add(doctors);

					// test
					logger.debug(JSON.toJSONString(params));

					serveComm.set(CacheType.DOCTOR_CACHE, "setRegSourceDoctorInfoToCache", params);
					// DoctorCache doctorCache = SpringContextHolder.getBean(DoctorCache.class);
					// doctorCache.setRegSourceDoctorInfoToCache(hospitalCode, branchHospitalCode, deptCode, doctors);
				}
			}
		} else {
			// 掌上医院
			doctors = searchDayRegSource(hospitalCode, branchHospitalCode, deptCode, dateStr);
		}
		return doctors;
	}

	/**
	 * 获取指定医生code号源(微信公众号/支付宝服务窗/建康易)
	 * 
	 * @param hospitalId
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param deptCode
	 * @param doctorCode
	 * @param dateStr
	 * @return
	 */
	public Doctor searchDoctorRegSource(String hospitalId, String hospitalCode, String branchHospitalCode, String deptCode,
			String doctorCode, String dateStr, String appCode) {
		Doctor queryDoctor = null;
		List<Doctor> doctors = searchDayRegSource(hospitalId, hospitalCode, branchHospitalCode, deptCode, dateStr, appCode);
		if (!CollectionUtils.isEmpty(doctors)) {
			for (Doctor d : doctors) {
				if (doctorCode.trim().equalsIgnoreCase(d.getDoctorCode())) {
					queryDoctor = d;
					break;
				}
			}
		}

		return queryDoctor;
	}

	/**
	 * 掌上医院获取某科室下某天的号源信息
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param deptCode
	 * @param dateStr
	 * @return
	 */
	public List<Doctor> searchDayRegSource(String hospitalCode, String branchHospitalCode, String deptCode, String dateStr) {
		Integer sourceCacheDays = null;
		RuleConfigManager ruleManager = SpringContextHolder.getBean(RuleConfigManager.class);
		RuleRegister ruleRegister = ruleManager.getRuleRegisterByHospitalCode(hospitalCode);
		/*//日历显示的天数--即号源页面应该显示的各个日期的号源，最多显示到从今往后数的第showDays天的号源
		int showDays = 7;
		int calendarDays = ruleRegister.getCalendarDaysType();
		switch (calendarDays) {
		case RegisterConstant.SEVEN_DAYS:
			showDays = 7;
			break;
		case RegisterConstant.FIFTEEN_DAYS:
			showDays = 15;
			break;
		case RegisterConstant.THIRTY_DAYS:
			showDays = 30;
			break;
		case RegisterConstant.CUSTOM_DAYS:
			showDays = ruleRegister.getCustomCalendarDays();
			break;
		default:
			showDays = 7;
			break;
		}*/

		//号源缓存的天数	
		if (ruleRegister != null) {
			sourceCacheDays = ruleRegister.getSourceCacheDays();
		}
		if (sourceCacheDays == null) {
			//默认七天
			sourceCacheDays = 7;
		}

		GregorianCalendar now = new GregorianCalendar();
		if (sourceCacheDays > 0) {
			now.add(Calendar.DAY_OF_MONTH, sourceCacheDays - 1);
		}
		String maxCacheDayStr = BizConstant.YYYYMMDD.format(now.getTime());

		List<Doctor> doctors = new ArrayList<Doctor>();
		// 超过缓存天数 调用his接口
		if (sourceCacheDays == 0 || dateStr.compareTo(maxCacheDayStr) > 0) {
			doctors = getRegInfo(hospitalCode, branchHospitalCode, dateStr, deptCode);
		} else {
			if (StringUtils.isNotBlank(deptCode) && StringUtils.isNotBlank(dateStr)) {
				// doctors = sourceCache.getRegSourceFormCache(hospitalCode, branchHospitalCode, deptCode.trim(), dateStr);
				List<Object> params = new ArrayList<Object>();
				params.add(hospitalCode);
				params.add(branchHospitalCode);
				params.add(deptCode.trim());
				params.add(dateStr);
				List<Object> results = serveComm.get(CacheType.REGISTER_SOURCE_CACHE, "getRegSourceFormCache", params);
				if (CollectionUtils.isNotEmpty(results)) {
					String source = JSON.toJSONString(results);
					doctors = JSON.parseArray(source, Doctor.class);
				}
			}
		}
		return doctors;
	}

	/**
	 * 挂号-->患者类型查询
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param cardNo
	 * @param cardType
	 * @return
	 */
	public Map<String, Object> getPatientType(String hospitalCode, String branchCode, String cardNo, String cardType, String deptCode) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				PatientTypeRequest patientTypeRequest = new PatientTypeRequest();
				patientTypeRequest.setPatCardNo(cardNo);
				patientTypeRequest.setPatCardType(cardType);
				patientTypeRequest.setReservedFieldOne(deptCode);

				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("getPatientType");
				request.setInnerRequest(patientTypeRequest);

				Response response = yxwCommService.invoke(request);

				logger.info("调用患者类型查询接口，patCardNo:{}, response:{} ", patientTypeRequest.getPatCardNo(), JSON.toJSONString(response));

				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke getPatientType interface is exception. resCode:{},cardNo:{} , cardType:{},msg:{} ",
							response.getResultCode(), cardNo, cardType, response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
						resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}

				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("getStopReg error. hospitalCode:{} , branchCode:{}.case by : {}",
						new Object[] { hospitalCode, branchCode, e.getMessage() });
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		} else {
			logger.error("getPatientType error. hospitalCode:{} , branchCode:{}. reason: interfaceId is null", hospitalCode, branchCode);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param queryDay
	 *            yyyy-MM-dd
	 * @return
	 */
	public List<Doctor> getRegInfo(String hospitalCode, String branchHospitalCode, String queryDay, String deptCode) {
		List<Doctor> doctors = null;

		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchHospitalCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);

				RegInfoRequest regInfoRequest = new RegInfoRequest();
				regInfoRequest.setBranchCode(branchHospitalCode);
				regInfoRequest.setBeginDate(queryDay);
				regInfoRequest.setEndDate(queryDay);
				regInfoRequest.setDeptCode(deptCode);

				//Response response = yxwService.getRegInfo(regInfoRequest);

				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("getRegInfo");
				request.setInnerRequest(regInfoRequest);
				Response response = yxwCommService.invoke(request);

				if (response != null) {
					List<RegInfo> infos = (List<RegInfo>) response.getResult();
					if (!CollectionUtils.isEmpty(infos)) {
						doctors = infos.get(0).getDoctors();
						if (logger.isDebugEnabled()) {
							logger.debug("this hospital code:{} , reg source doctors size:{}",
									new Object[] { hospitalCode, doctors.size() });
						}
					}
				} else {
					logger.error("hospitalCode : {} , {} load regInfos is error. response is null", new Object[] { hospitalCode,
							BizConstant.YYYYMMDD.format(new Date()) });
				}
			} catch (Exception e) {
				logger.error("hospitalCode : {} , {} load regInfos is error. response is null.msg:{}", new Object[] { hospitalCode,
						BizConstant.YYYYMMDD.format(new Date()),
						e.getMessage() });
			}
		}
		if (doctors == null) {
			doctors = new ArrayList<Doctor>();

		}
		return doctors;
	}

	/**
	 * 挂号-->预约挂号-->医生预约分时查询
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param deptCode
	 * @param doctorCode
	 * @param dateStr
	 *            yyyy-MM-dd
	 * @param timeFlag
	 *            0：所有时段 1：上午 2：下午 3：晚上 参见RegisterConstant中的TIME_FLAG定义
	 * @return
	 */
	public List<DocTime> searchDocTime(String hospitalCode, String branchHospitalCode, String deptCode, String doctorCode, String dateStr,
			String timeFlag) {
		List<DocTime> docTimes = new ArrayList<DocTime>();

		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchHospitalCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		if (StringUtils.isNotEmpty(interfaceId)) {
			//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			DocTimeRequest docTimeRequest = new DocTimeRequest();
			docTimeRequest.setBranchCode(branchHospitalCode);
			docTimeRequest.setDeptCode(deptCode);
			docTimeRequest.setDoctorCode(doctorCode);
			docTimeRequest.setScheduleDate(dateStr);
			docTimeRequest.setTimeFlag(timeFlag);
			//Response response = yxwService.getDocTime(docTimeRequest);

			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
			Request request = new Request();
			request.setServiceId(interfaceId);
			request.setMethodName("getDocTime");
			request.setInnerRequest(docTimeRequest);
			Response response = yxwCommService.invoke(request);

			if (response != null) {
				if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
					docTimes = (List<DocTime>) response.getResult();
					if (CollectionUtils.isEmpty(docTimes)) {
						docTimes = new ArrayList<DocTime>();
					}
					for (DocTime dt : docTimes) {
						if (StringUtils.isBlank(dt.getRegFee())) {
							dt.setRegFee("0");
						}
						if (StringUtils.isBlank(dt.getTreatFee())) {
							dt.setTreatFee("0");
						}
					}
				}
			} else {
				logger.error("searchDocTime response is null. hospitalCode:{},deptCode:{},doctorCode:{}", new Object[] { hospitalCode,
						deptCode,
						doctorCode });
			}
		}
		return docTimes;
	}

	/**
	 * 挂号-->预约挂号-->预约挂号
	 * 
	 * @param record
	 * @return
	 */
	public Map<String, Object> orderReg(RegisterRecord record) {
		OrderReg orderReg = null;
		Map<String, Object> resMap = new HashMap<String, Object>();

		// String interfaceId = hospitalCache.getInterfaceId(record.getHospitalCode(), record.getBranchHospitalCode());
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(record.getHospitalCode());
		params.add(record.getBranchHospitalCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);

				OrderRegRequest orderRegRequest = record.convertOrderRequest();
				//				System.out.println(JSONObject.toJSONString(record));
				//				System.out.println(JSONObject.toJSONString(orderRegRequest));

				//Response response = yxwService.orderReg(orderRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("orderReg");
				request.setInnerRequest(orderRegRequest);
				Response response = yxwCommService.invoke(request);

				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke orderReg is exception. resCode:{},msg:{} ", response.getResultCode(), response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						orderReg = (OrderReg) response.getResult();
						resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, orderReg);
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
						logger.warn("订单号:{},orderReg is fail.reason:{}", new Object[] { record.getOrderNo(), response.getResultMessage() });
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("invoke RegisterBizManager.orderReg is error. reason:{}", e.getMessage());
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, BizConstant.INTERFACE_RES_FAILURE_MSG);
				logger.error("订单号:{},orderReg is exception.msg:{}", new Object[] { record.getOrderNo(), e.getMessage() });
			}
		} else {
			logger.error("invoke RegisterBizManager.orderReg is error. reason: interfaceId is null");
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 挂号-->预约挂号-->预约挂号
	 * 
	 * @param orderRegRequest
	 *            orderRegRequest参数说明 见类中的属性说明
	 * @param hospitalCode
	 * @param childHospitalCode
	 * @return
	 */
	public Map<String, Object> orderReg(String hospitalCode, String branchHospitalCode, OrderRegRequest orderRegRequest, String patName) {
		Map<String, Object> resMap = new HashMap<String, Object>();

		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, childHospitalCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		OrderReg orderReg = null;
		//YxwService yxwService = null;
		Response response = null;

		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//				yxwService = SpringContextHolder.getBean(interfaceId);
				//				response = yxwService.orderReg(orderRegRequest);

				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("orderReg");
				request.setInnerRequest(orderRegRequest);
				response = yxwCommService.invoke(request);

				if (logger.isDebugEnabled()) {
					logger.info("orderReg resCode:{},reason:{}", response.getResultCode(), response.getResultMessage());
				}
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke orderReg is exception. resCode:{},msg:{} ", response.getResultCode(), response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						orderReg = (OrderReg) response.getResult();
						resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, orderReg);
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}

					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("invoke RegisterBizManager.orderReg is error. reason:{}", e.getMessage());
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, BizConstant.INTERFACE_RES_FAILURE_MSG);
			}

		} else {
			logger.error("invoke RegisterBizManager.orderReg is error. reason: interfaceId is null");
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 挂号-->当班挂号-->当天挂号
	 * 
	 * @param record
	 * @return
	 */
	public Map<String, Object> orderCurReg(RegisterRecord record) {
		OrderCurReg orderCurReg = null;
		Map<String, Object> resMap = new HashMap<String, Object>();

		// String interfaceId = hospitalCache.getInterfaceId(record.getHospitalCode(), record.getBranchHospitalCode());
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(record.getHospitalCode());
		params.add(record.getBranchHospitalCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				OrderRegRequest orderRegRequest = record.convertOrderRequest();
				OrderCurRegRequest orderCurRegRequest = new OrderCurRegRequest();
				BeanUtils.copyProperties(orderRegRequest, orderCurRegRequest);

				//Response response = yxwService.orderCurReg(orderCurRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("orderCurReg");
				request.setInnerRequest(orderCurRegRequest);
				Response response = yxwCommService.invoke(request);

				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke orderCurReg is exception. resCode:{},msg:{} ", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						orderCurReg = (OrderCurReg) response.getResult();
						OrderReg orderReg = new OrderReg();
						BeanUtils.copyProperties(orderCurReg, orderReg);

						resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, orderReg);
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("invoke RegisterBizManager.orderReg is error. reason:{}", e.getMessage());
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, BizConstant.INTERFACE_RES_FAILURE_MSG);
				logger.error("订单号:{},orderCurReg is exception.msg:{}", new Object[] { record.getOrderNo(), e.getMessage() });
			}
		} else {
			logger.error("invoke RegisterBizManager.orderReg is error. reason: interfaceId is null");
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 挂号-->当班挂号-->当天挂号
	 * 
	 * @param orderRegRequest
	 *            orderRegRequest参数说明 见类中的属性说明
	 * @param hospitalCode
	 * @param childHospitalCode
	 * @return
	 */
	public Map<String, Object> orderCurReg(String hospitalCode, String branchHospitalCode, OrderRegRequest orderRegRequest, String patName) {
		Map<String, Object> resMap = new HashMap<String, Object>();

		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, childHospitalCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		OrderReg orderReg = new OrderReg();
		//YxwService yxwService = null;
		Response response = null;

		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//yxwService = SpringContextHolder.getBean(interfaceId);

				OrderCurRegRequest orderCurRegRequest = new OrderCurRegRequest();
				BeanUtils.copyProperties(orderRegRequest, orderCurRegRequest);
				//response = yxwService.orderCurReg(orderCurRegRequest);

				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("orderCurReg");
				request.setInnerRequest(orderCurRegRequest);
				response = yxwCommService.invoke(request);

				if (logger.isDebugEnabled()) {
					logger.info("orderCurReg resCode:{},reason:{}", response.getResultCode(), response.getResultMessage());
				}
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke orderCurReg is exception. resCode:{},msg:{} ", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						OrderCurReg orderCurReg = (OrderCurReg) response.getResult();
						BeanUtils.copyProperties(orderCurReg, orderReg);
						resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, orderReg);
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("invoke RegisterBizManager.orderCurReg is error. reason:{}", e.getMessage());
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, BizConstant.INTERFACE_RES_FAILURE_MSG);
			}
		} else {
			logger.error("invoke RegisterBizManager.orderCurReg is error. reason: interfaceId is null");
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, BizConstant.INTERFACE_RES_FAILURE_MSG);
		}
		return resMap;
	}

	/**
	 * @param record
	 * @return 查询失败返回null,查询成功无论有无查询到数据都返回非空的结果集
	 * 
	 */
	public Map<String, Object> queryRegisterRecords(RegisterRecord record) {
		Map<String, Object> interfaceResMap = new HashMap<String, Object>();

		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(record.getHospitalCode());
		params.add(record.getBranchHospitalCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				RegRecordRequest regRecordRequest = record.convertRegRecordRequest();
				logger.info("挂号记录接口查询参数：{}", JSON.toJSONString(regRecordRequest));
				//Response response = yxwService.getRegRecords(regRecordRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("getRegRecords");
				request.setInnerRequest(regRecordRequest);
				Response response = yxwCommService.invoke(request);

				logger.info("挂号记录接口查询结果：{}", JSON.toJSONString(response));
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					interfaceResMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
						interfaceResMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
						interfaceResMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					} else if (BizConstant.INTERFACE_RES_SUCCESS_NO_DATA_CODE.equalsIgnoreCase(response.getResultCode())) {
						interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
						interfaceResMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					} else {
						interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					}
					interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				interfaceResMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			}
		}
		return interfaceResMap;
	}

	/**
	 * 挂号-报到取号
	 * @param branchCode 医院没有分院，则值为空字符串
	 * @param patCardType
	 * @param patCardNo
	 * @param hisOrdNum 不为空时，对指定的预约/挂号流水号进行取号操作
	 * @return
	 */
	public Map<String, Object> takeNo(String hospitalCode, String branchCode, String patCardType, String patCardNo, String hisOrdNum) {
		branchCode = org.apache.commons.lang3.StringUtils.stripToEmpty(branchCode);
		hisOrdNum = org.apache.commons.lang3.StringUtils.stripToEmpty(hisOrdNum);

		Map<String, Object> interfaceResMap = new HashMap<String, Object>();

		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				TakeNoRequest req = new TakeNoRequest();
				req.setBranchCode(branchCode);
				req.setPatCardType(patCardType);
				req.setPatCardNo(patCardNo);
				req.setHisOrdNum(hisOrdNum);

				//Response response = yxwService.takeNo(req);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("takeNo");
				request.setInnerRequest(req);
				Response response = yxwCommService.invoke(request);

				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					interfaceResMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
						interfaceResMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
						interfaceResMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					} else {
						interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						interfaceResMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				interfaceResMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			}
		}
		return interfaceResMap;
	}

	/**
	 * @param hospitalCode
	 * @param branchCode
	 * @param orderNo
	 *            公众服务平台订单号,要求唯一，能标识单独的一笔预约挂号订单 该字段不为空时, 查询指定公众服务平台订单号的记录;该字段为空时,查询指定患者,所有的预约记录
	 * @param regMode
	 *            挂号来源 0：所有,1：微信公众号,2：支付宝服务窗
	 * @param patCardType
	 *            就诊卡类型
	 * @param patCardNo
	 *            就诊卡号
	 * @param beginDate
	 *            就诊开始时间
	 * @param endDate
	 *            就诊结束时间
	 * @return 查询失败返回null,查询成功无论有无查询到数据都返回非空的结果集
	 * 
	 */
	public Map<String, Object> queryRegisterRecords(String hospitalCode, String branchCode, String orderNo, String regMode,
			String patCardType, String patCardNo, String beginDate, String endDate) {
		Map<String, Object> interfaceResMap = new HashMap<String, Object>();

		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				RegRecordRequest regRecordRequest = new RegRecordRequest();
				regRecordRequest.setBranchCode(branchCode);
				regRecordRequest.setPsOrdNum(orderNo);
				//				regRecordRequest.setRegMode(regMode);
				regRecordRequest.setRegMode(BizConstant.HIS_ORDER_MODE_VAL);//默认值
				regRecordRequest.setBeginDate(beginDate);
				regRecordRequest.setEndDate(endDate);
				regRecordRequest.setPatCardType(patCardType);
				regRecordRequest.setPatCardNo(patCardNo);
				//Response response = yxwService.getRegRecords(regRecordRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("getRegRecords");
				request.setInnerRequest(regRecordRequest);
				Response response = yxwCommService.invoke(request);

				interfaceResMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());

				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					interfaceResMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
						interfaceResMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
					} else if (BizConstant.INTERFACE_RES_SUCCESS_NO_DATA_CODE.equalsIgnoreCase(response.getResultCode())) {
						interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					} else {
						interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					}
					interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				interfaceResMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			}
		}
		return interfaceResMap;
	}

	/**
	 * 当班挂号 --- 取消预约
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param cancleMode
	 *            1：微信公众号 2：支付宝服务窗
	 * @param hisOrdNum
	 *            医院预约流水号,要求唯一，能标识单独的一笔预约挂号订单
	 * @param psOrdNum
	 *            公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔交易的流水号
	 * @return
	 */
	public Map<String, Object> cancelCurRegister(String hospitalCode, String branchCode, String cancleMode, String hisOrdNum,
			String psOrdNum) throws SystemException {

		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				CancelCurRegRequest cancelCurRegRequest = new CancelCurRegRequest();
				//				cancelCurRegRequest.setCancelMode(cancleMode);
				cancelCurRegRequest.setCancelMode(BizConstant.HIS_ORDER_MODE_VAL);//默认值
				cancelCurRegRequest.setHisOrdNum(hisOrdNum);
				cancelCurRegRequest.setPsOrdNum(psOrdNum);
				cancelCurRegRequest.setBranchCode(branchCode);
				Date d = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = formatter.format(d);
				cancelCurRegRequest.setCancelTime(time);
				//Response response = yxwService.cancelCurReg(cancelCurRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("cancelCurReg");
				request.setInnerRequest(cancelCurRegRequest);
				Response response = yxwCommService.invoke(request);

				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke cancelCurRegister is exception. resCode:{},msg:{}", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("exec RegisterBizManager.cancelCurRegister error. orderNo:{}. case:{}",
						new Object[] { psOrdNum, e.getMessage() });
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			}
		} else {
			logger.error("exec RegisterBizManager.cancelCurRegister error. orderNo:{}.reason: interfaceId is null", psOrdNum);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
		}
		return resMap;
	}

	/**
	 * 当班挂号 --- 取消预约
	 * 
	 * @param record
	 * @return
	 */
	public Map<String, Object> cancelCurRegister(RegisterRecord record) throws SystemException {
		// String interfaceId = hospitalCache.getInterfaceId(record.getHospitalCode(), record.getBranchHospitalCode());
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(record.getHospitalCode());
		params.add(record.getBranchHospitalCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				CancelRegRequest cancelRegRequest = record.convertCancelRegRequest();
				CancelCurRegRequest cancelCurRegRequest = new CancelCurRegRequest();
				BeanUtils.copyProperties(cancelRegRequest, cancelCurRegRequest);
				//Response response = yxwService.cancelCurReg(cancelCurRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("cancelCurReg");
				request.setInnerRequest(cancelCurRegRequest);
				Response response = yxwCommService.invoke(request);
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke cancelCurRegister is exception. resCode:{},msg:{} ", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("cancelRegister error. record's orderNo:{}. case:{}", new Object[] { record.getOrderNo(), e.getMessage() });
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		} else {
			logger.error("cancelRegister error. orderNo:{}.reason: interfaceId is null", record.getOrderNo());
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 预约挂号 --- 取消预约
	 * 
	 * @param record
	 * @return
	 */
	public Map<String, Object> cancelRegister(RegisterRecord record) throws SystemException {
		// String interfaceId = hospitalCache.getInterfaceId(record.getHospitalCode(), record.getBranchHospitalCode());
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(record.getHospitalCode());
		params.add(record.getBranchHospitalCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				CancelRegRequest cancelRegRequest = record.convertCancelRegRequest();
				//Response response = yxwService.cancelReg(cancelRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("cancelReg");
				request.setInnerRequest(cancelRegRequest);
				Response response = yxwCommService.invoke(request);

				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke cancelRegister is exception. resCode:{},msg:{} ", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("cancelRegister error. record's orderNo:{}. case:{}", new Object[] { record.getOrderNo(), e.getMessage() });
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		} else {
			logger.error("cancelRegister error. orderNo:{}.reason: interfaceId is null", record.getOrderNo());
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 预约挂号 --- 取消预约
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param cancleMode
	 *            1：微信公众号 2：支付宝服务窗
	 * @param hisOrdNum
	 *            医院预约流水号,要求唯一，能标识单独的一笔预约挂号订单
	 * @param psOrdNum
	 *            公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔交易的流水号
	 * @return
	 */
	public Map<String, Object> cancelRegister(String hospitalCode, String branchCode, String cancleMode, String hisOrdNum, String psOrdNum)
			throws SystemException {
		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				CancelRegRequest cancelRegRequest = new CancelRegRequest();
				//				cancelRegRequest.setCancelMode(cancleMode);
				cancelRegRequest.setCancelMode(BizConstant.HIS_ORDER_MODE_VAL);//默认值
				cancelRegRequest.setHisOrdNum(hisOrdNum);
				cancelRegRequest.setPsOrdNum(psOrdNum);
				cancelRegRequest.setBranchCode(branchCode);
				Date d = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = formatter.format(d);
				cancelRegRequest.setCancelTime(time);
				//Response response = yxwService.cancelReg(cancelRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("cancelReg");
				request.setInnerRequest(cancelRegRequest);
				Response response = yxwCommService.invoke(request);
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke cancelRegister is exception. resCode:{},msg:{} ", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("cancelRegister error. record's orderNo:{}. case:{}", new Object[] { psOrdNum, e.getMessage() });
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		} else {
			logger.error("cancelRegister error. orderNo:{}.reason: interfaceId is null", psOrdNum);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 挂号-->预约挂号-->预约退费
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param psRefOrdNum
	 *            公众服务平台退款订单号,公众服务平台(微信公众号、支付宝服务窗)用于唯一标识一笔订单的退款流水号
	 * @param refundMode
	 *            退款方式 1：微信公众号 2：支付宝服务窗
	 * @param refundAmout
	 *            退款金额 单位：分
	 * @param agtRefOrdNum
	 *            对应收单机构（如财付通、支付宝、银联等机构）用于标识一笔退款交易的流水号
	 * @return
	 */
	public Boolean registerRefund(String hospitalCode, String branchCode, String psRefOrdNum, String refundMode, String refundAmout,
			String agtRefOrdNum) {
		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		Boolean isSuccess = false;
		// Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {
			//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			RefundRegRequest refundRegRequest = new RefundRegRequest();
			refundRegRequest.setPsRefOrdNum(psRefOrdNum);
			//			refundRegRequest.setRefundMode(refundMode);
			refundRegRequest.setRefundMode(BizConstant.HIS_ORDER_MODE_VAL);//默认值
			refundRegRequest.setRefundAmout(refundAmout);
			refundRegRequest.setAgtRefOrdNum(agtRefOrdNum);
			refundRegRequest.setBranchCode(branchCode);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String refundTime = formatter.format(new Date());
			refundRegRequest.setRefundTime(refundTime);
			//Response response = yxwService.refundReg(refundRegRequest);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
			Request request = new Request();
			request.setServiceId(interfaceId);
			request.setMethodName("refundReg");
			request.setInnerRequest(refundRegRequest);
			Response response = yxwCommService.invoke(request);
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
				isSuccess = true;
			}
		}
		return isSuccess;
	}

	/**
	 * 挂号-->当天挂号-->当天挂号支付
	 * 
	 * @param record
	 * @return
	 */
	public Map<String, Object> registerCurPayReg(RegisterRecord record) {
		// String interfaceId = hospitalCache.getInterfaceId(record.getHospitalCode(), record.getBranchHospitalCode());
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(record.getHospitalCode());
		params.add(record.getBranchHospitalCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {

			try {
				//YxwService yxwService = null;
				//yxwService = SpringContextHolder.getBean(interfaceId);
				PayRegRequest payRegRequest = record.convertPayRegRequest();
				PayCurRegRequest payCurRegRequest = new PayCurRegRequest();
				BeanUtils.copyProperties(payRegRequest, payCurRegRequest);
				//Response response = yxwService.payCurReg(payCurRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("payCurReg");
				request.setInnerRequest(payCurRegRequest);
				Response response = yxwCommService.invoke(request);
				if (logger.isDebugEnabled()) {
					logger.info(
							"registerCurPayReg resCode:{},reason:{},agtOrdNum:{},orderNo:{},hisOrdNum:",
							new Object[] { response.getResultCode(),
									response.getResultCode(),
									payRegRequest.getAgtOrdNum(),
									payRegRequest.getPsOrdNum(),
									payRegRequest.getPsOrdNum() });
				}
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke registerCurPayReg is exception. resCode:{},msg:{} ", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
						resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("registerCurPayReg error. orderNo:{}. reason :{} , Cause:{}",
						new Object[] { record.getOrderNo(), e.getMessage(), e.getCause() });
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		} else {
			logger.error("registerCurPayReg error. orderNo:{}.reason: interfaceId is null", record.getOrderNo());
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 挂号-->预约挂号-->预约挂号支付
	 * 
	 * @param record
	 * @return
	 */
	public Map<String, Object> registerPayReg(RegisterRecord record) {
		// String interfaceId = hospitalCache.getInterfaceId(record.getHospitalCode(), record.getBranchHospitalCode());
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(record.getHospitalCode());
		params.add(record.getBranchHospitalCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {

			try {
				//YxwService yxwService = null;
				//yxwService = SpringContextHolder.getBean(interfaceId);
				PayRegRequest payRegRequest = record.convertPayRegRequest();
				//Response response = yxwService.payReg(payRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("payReg");
				request.setInnerRequest(payRegRequest);
				Response response = yxwCommService.invoke(request);
				if (logger.isDebugEnabled()) {
					logger.info(
							"registerPayReg resCode:{},reason:{},agtOrdNum:{},orderNo:{},hisOrdNum:",
							new Object[] { response.getResultCode(),
									response.getResultCode(),
									payRegRequest.getAgtOrdNum(),
									payRegRequest.getPsOrdNum(),
									payRegRequest.getPsOrdNum() });
				}
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke registerPayReg is exception. resCode:{},msg:{} ", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
						resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("registerPayReg error. orderNo:{}. reason :{} , Cause:{}", new Object[] { record.getOrderNo(),
						e.getMessage(),
						e.getCause() });
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		} else {
			logger.error("registerPayReg error. orderNo:{}.reason: interfaceId is null", record.getOrderNo());
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 挂号-->预约挂号-->预约挂号支付
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param psOrdNum
	 *            公众服务平台订单号,公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔交易的流水号
	 * @param payMode
	 *            1：微信 2：支付宝
	 * @param agtOrdNum
	 *            收单机构流水号,对应收单机构（如财付通、支付宝、银联等机构）用于标识一笔支付交易的流水号
	 * @param payAmout
	 *            金额 单位：分
	 * @return
	 */
	public Map<String, Object> registerPayReg(String hospitalCode, String branchCode, String psOrdNum, String hisOrdNum, String payMode,
			String agtOrdNum, String payAmout) throws SystemException {
		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = null;
				//yxwService = SpringContextHolder.getBean(interfaceId);
				PayRegRequest payRegRequest = new PayRegRequest();
				payRegRequest.setPsOrdNum(psOrdNum);
				//				payRegRequest.setPayMode(payMode);
				payRegRequest.setPayMode(BizConstant.HIS_ORDER_MODE_VAL);

				payRegRequest.setAgtOrdNum(agtOrdNum);
				payRegRequest.setPayAmout(payAmout);
				payRegRequest.setBranchCode(branchCode);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String payTime = formatter.format(new Date());
				payRegRequest.setPayTime(payTime);
				payRegRequest.setHisOrdNum(hisOrdNum);
				//Response response = yxwService.payReg(payRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("payReg");
				request.setInnerRequest(payRegRequest);
				Response response = yxwCommService.invoke(request);
				if (logger.isDebugEnabled()) {
					logger.info("registerPayReg resCode:{},reason:{},agtOrdNum:{},orderNo:{},psOrdNum:{}",
							new Object[] { response.getResultCode(), response.getResultMessage(), agtOrdNum, psOrdNum });
				}
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke registerPayReg is exception. resCode:{},msg:{} ", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
						resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("registerPayReg error. orderNo:{}. reason :",
						new Object[] { psOrdNum, ( e.getMessage() + "   Cause:" + e.getCause() ) });
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		} else {
			logger.error("registerPayReg error. orderNo:{}.reason: interfaceId is null", psOrdNum);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 挂号-->当天挂号-->当天挂号支付
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param psOrdNum
	 *            公众服务平台订单号,公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔交易的流水号
	 * @param payMode
	 *            1：微信 2：支付宝
	 * @param agtOrdNum
	 *            收单机构流水号,对应收单机构（如财付通、支付宝、银联等机构）用于标识一笔支付交易的流水号
	 * @param payAmout
	 *            金额 单位：分
	 * @return
	 */
	public Map<String, Object> registerCurPayReg(String hospitalCode, String branchCode, String psOrdNum, String hisOrdNum, String payMode,
			String agtOrdNum, String payAmout) throws SystemException {
		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				PayCurRegRequest payRegRequest = new PayCurRegRequest();
				payRegRequest.setPsOrdNum(psOrdNum);

				//				payRegRequest.setPayMode(payMode);
				payRegRequest.setPayMode(BizConstant.HIS_ORDER_MODE_VAL);//默认值
				payRegRequest.setAgtOrdNum(agtOrdNum);
				payRegRequest.setPayAmout(payAmout);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String payTime = formatter.format(new Date());
				payRegRequest.setPayTime(payTime);
				payRegRequest.setBranchCode(branchCode);
				payRegRequest.setHisOrdNum(hisOrdNum);
				//Response response = yxwService.payCurReg(payRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("payCurReg");
				request.setInnerRequest(payRegRequest);
				Response response = yxwCommService.invoke(request);
				if (logger.isDebugEnabled()) {
					logger.info("registerCurPayReg resCode:{},reason:{},agtOrdNum:{},orderNo:{},psOrdNum:{}",
							new Object[] { response.getResultCode(), response.getResultMessage(), agtOrdNum, psOrdNum });
				}
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke registerCurPayReg is exception. resCode:{},msg:{} ", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
						resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("registerCurPayReg error. orderNo:{}.", psOrdNum);
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		} else {
			logger.error("registerCurPayReg error. orderNo:{}.reason: interfaceId is null", psOrdNum);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 个人中心-->患者信息查询
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param patCardNo
	 *            卡号
	 * @param patCardType
	 *            卡类型
	 * @param patName
	 *            持卡人姓名
	 * @return
	 */
	//	public Map<String, Object> queryMZPatient(String hospitalCode, String branchCode, String patCardNo, String patCardType, String patName) {
	//		Map<String, Object> dataMap = new HashMap<String, Object>();
	//		MZPatient patient = null;
	//		String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
	//		if (StringUtils.isNotEmpty(interfaceId)) {
	//			YxwService yxwService = SpringContextHolder.getBean(interfaceId);
	//			MZPatientRequest patientRequest = new MZPatientRequest();
	//			patientRequest.setPatCardNo(patCardNo);
	//			patientRequest.setPatCardType(patCardType);
	//			patientRequest.setPatName(patName);
	//			patientRequest.setBranchCode(branchCode);
	//			Response response = yxwService.getMZPatient(patientRequest);
	//			dataMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
	//			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
	//				patient = (MZPatient) response.getResult();
	//				dataMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, patient);
	//			} else {
	//				dataMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
	//			}
	//		}
	//		return dataMap;
	//	}
	//
	//	/**
	//	 * 查询病人信息，多传入了一些额外参数，供接口保存使用
	//	 * 
	//	 * @param hospitalCode
	//	 * @param branchCode
	//	 * @param patCardNo
	//	 * @param patCardType
	//	 * @param patName
	//	 * @return
	//	 */
	//	public Map<String, Object> queryMZPatient(String hospitalCode, String branchCode, MedicalCard medicalCard) {
	//		Map<String, Object> dataMap = new HashMap<String, Object>();
	//		MZPatient patient = null;
	//		String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
	//		if (StringUtils.isNotEmpty(interfaceId)) {
	//			YxwService yxwService = SpringContextHolder.getBean(interfaceId);
	//
	//			MZPatientRequest patientRequest = new MZPatientRequest();
	//			patientRequest.setBranchCode(branchCode);
	//			patientRequest.setGuardAddress("");
	//			patientRequest.setGuardIdNo(medicalCard.getGuardIdNo());
	//			patientRequest.setGuardIdType(medicalCard.getGuardIdType() + "");
	//			patientRequest.setGuardMobile(medicalCard.getGuardMobile());
	//			patientRequest.setGuardName(medicalCard.getGuardName());
	//			patientRequest.setPatAddress(medicalCard.getAddress());
	//			patientRequest.setPatBirth(medicalCard.getBirth());
	//			patientRequest.setPatCardNo(medicalCard.getCardNo());
	//			patientRequest.setPatCardType(medicalCard.getCardType() + "");
	//			patientRequest.setPatIdNo(medicalCard.getIdNo());
	//			patientRequest.setPatIdType(medicalCard.getIdType() + "");
	//			patientRequest.setPatMobile(medicalCard.getMobile());
	//			patientRequest.setPatName(medicalCard.getName());
	//			// 性别
	//			if (medicalCard.getSex() == null || medicalCard.getSex().intValue() == 2) {
	//				patientRequest.setPatSex(GenderType.GENDER_F);
	//			} else {
	//				patientRequest.setPatSex(GenderType.GENDER_M);
	//			}
	//
	//			Response response = yxwService.getMZPatient(patientRequest);
	//			dataMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
	//			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
	//				patient = (MZPatient) response.getResult();
	//				dataMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, patient);
	//			} else {
	//				dataMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
	//			}
	//		}
	//		return dataMap;
	//	}

	/**
	 * 预约退费确认
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param psOrdNum
	 * @param hisOrdNum
	 * @param refundMode
	 * @param refundAmout
	 * @return
	 */
	public Map<String, Object> refundRegConfirm(String hospitalCode, String branchCode, String psOrdNum, String hisOrdNum,
			String refundMode, String refundAmout) {
		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				AckRefundRegRequest ackRefundRegRequest = new AckRefundRegRequest();
				ackRefundRegRequest.setBranchCode(branchCode);
				ackRefundRegRequest.setPsOrdNum(psOrdNum);
				ackRefundRegRequest.setHisOrdNum(hisOrdNum);
				ackRefundRegRequest.setRefundAmout(refundAmout);
				ackRefundRegRequest.setRefundMode(refundMode);
				//Response response = yxwService.ackRefundReg(ackRefundRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("ackRefundReg");
				request.setInnerRequest(ackRefundRegRequest);
				Response response = yxwCommService.invoke(request);
				if (logger.isDebugEnabled()) {
					logger.info("refundRegConfirm resCode :{},orderNo:{},hisOrderNo:{} , reason:{}",
							new Object[] { response.getResultCode(), response.getResultMessage(), psOrdNum, hisOrdNum });
				}
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke refundRegConfirm is exception. resCode:{},msg:{} ", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("refundRegConfirm error. orderNo:{}.case by : ", new Object[] { psOrdNum, e.getMessage() });
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		} else {
			logger.error("refundRegConfirm error. orderNo:{}.reason: interfaceId is null", psOrdNum);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 当班退费确认
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param psOrdNum
	 * @param hisOrdNum
	 * @param refundMode
	 * @param refundAmout
	 * @return
	 */
	public Map<String, Object> refundCurRegConfirm(String hospitalCode, String branchCode, String psOrdNum, String hisOrdNum,
			String refundMode, String refundAmout) {
		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				AckRefundCurRegRequest ackRefundRegRequest = new AckRefundCurRegRequest();
				ackRefundRegRequest.setBranchCode(branchCode);
				ackRefundRegRequest.setPsOrdNum(psOrdNum);
				ackRefundRegRequest.setHisOrdNum(hisOrdNum);
				ackRefundRegRequest.setRefundAmout(refundAmout);
				ackRefundRegRequest.setRefundMode(refundMode);
				//Response response = yxwService.ackRefundCurReg(ackRefundRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("ackRefundCurReg");
				request.setInnerRequest(ackRefundRegRequest);
				Response response = yxwCommService.invoke(request);
				if (logger.isDebugEnabled()) {
					logger.info("refundCurRegConfirm resCode :{},orderNo:{},hisOrderNo:{} , reason:{}",
							new Object[] { response.getResultCode(), response.getResultMessage(), psOrdNum, hisOrdNum });
				}
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke refundCurRegConfirm is exception. resCode:{},msg:{} ", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, BizConstant.INTERFACE_RES_FAILURE_MSG);
				logger.error("refundCurRegConfirm error. orderNo:{}.case by : ", new Object[] { psOrdNum, e.getMessage() });
			}
		} else {
			logger.error("refundCurRegConfirm error. orderNo:{}.reason: interfaceId is null", psOrdNum);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, BizConstant.INTERFACE_RES_FAILURE_MSG);
		}
		return resMap;
	}

	/**
	 * 当班退费
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param refundRegRequest
	 * @return
	 */
	public Map<String, Object> refundCurReg(String hospitalCode, String branchCode, RefundRegRequest refundRegRequest) {
		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				RefundCurRegRequest refundCurRegRequest = new RefundCurRegRequest();
				BeanUtils.copyProperties(refundRegRequest, refundCurRegRequest);
				//Response response = yxwService.refundCurReg(refundCurRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("refundCurReg");
				request.setInnerRequest(refundCurRegRequest);
				Response response = yxwCommService.invoke(request);
				if (logger.isDebugEnabled()) {
					logger.debug("refundCurReg resCode :{},orderNo:{},hisOrderNo:{} , reason:{}", new Object[] { response.getResultCode(),
							refundRegRequest.getPsOrdNum(),
							refundRegRequest.getPsOrdNum(),
							response.getResultMessage() });
				}
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke refundCurReg is exception. resCode:{},msg:{} ", response.getResultCode(),
							response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
						resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
					} else if (BizConstant.INTERFACE_RES_SUCCESS_HIS_INVALID.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResult());
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("refundCurReg error. orderNo:{}.case by : {}",
						new Object[] { refundRegRequest.getPsRefOrdNum(), e.getMessage() });
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			}
		} else {
			logger.error("refundCurReg error. orderNo:{}.reason: interfaceId is null", refundRegRequest.getPsRefOrdNum());
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, BizConstant.INTERFACE_RES_FAILURE_MSG);
		}
		return resMap;
	}

	/**
	 * 预约退费
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param refundRegRequest
	 * @return
	 */
	public Map<String, Object> refundReg(String hospitalCode, String branchCode, RefundRegRequest refundRegRequest) {
		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		logger.info("refundReg interfaceId :" + interfaceId);
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				//Response response = yxwService.refundReg(refundRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("refundReg");
				request.setInnerRequest(refundRegRequest);
				Response response = yxwCommService.invoke(request);
				if (logger.isDebugEnabled()) {
					logger.debug("refundReg resCode :{},orderNo:{},hisOrderNo:{} , reason:{}", new Object[] { response.getResultCode(),
							refundRegRequest.getPsOrdNum(),
							refundRegRequest.getPsOrdNum(),
							response.getResultMessage() });
				}
				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					logger.error("invoke refundReg is exception. resCode:{},msg:{} ", response.getResultCode(), response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
						resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
					} else if (BizConstant.INTERFACE_RES_SUCCESS_HIS_INVALID.equalsIgnoreCase(response.getResultCode())) {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResult());
					} else {
						resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
					}
					resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("refundReg error. orderNo:{}.case by : {}", new Object[] { refundRegRequest.getPsRefOrdNum(), e.getMessage() });
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		} else {
			logger.error("refundReg error. orderNo:{}.reason: interfaceId is null", refundRegRequest.getPsRefOrdNum());
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

	/**
	 * 停诊查询
	 * 
	 * @param hospitalCode
	 * @param stopRegRequest
	 * @return
	 */
	public Map<String, Object> getStopReg(String hospitalCode, StopRegRequest stopRegRequest) {
		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, stopRegRequest.getBranchCode());
		String interfaceId = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(stopRegRequest.getBranchCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			interfaceId = (String) results.get(0);
		}

		logger.info("refundReg interfaceId :" + interfaceId);
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(interfaceId)) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				//Response response = yxwService.getStopReg(stopRegRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("getStopReg");
				request.setInnerRequest(stopRegRequest);
				Response response = yxwCommService.invoke(request);
				if (logger.isDebugEnabled()) {
					logger.debug(
							"getStopReg resCode :{},hospitalCode:{},branchCode:{} , reason:{}",
							new Object[] { response.getResultCode(),
									hospitalCode,
									stopRegRequest.getBranchCode(),
									response.getResultMessage() });
				}
				if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
					resMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
				} else {
					resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				}

				resMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("getStopReg error. hospitalCode:{} , branchCode:{}.case by : {}",
						new Object[] { hospitalCode, stopRegRequest.getBranchCode(), e.getMessage() });
				resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		} else {
			logger.error("getStopReg error. hospitalCode:{} , branchCode:{}. reason: interfaceId is null", hospitalCode,
					stopRegRequest.getBranchCode());
			resMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
		}
		return resMap;
	}

}
