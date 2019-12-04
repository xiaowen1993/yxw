/**
 * Copyright© 2014-2015 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2015年6月5日
 * @version 1.0
 */
package com.yxw.easyhealth.biz.register.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.vo.platform.Dept;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.easyhealth.biz.search.vo.SearchParamVo;
import com.yxw.easyhealth.biz.vo.DoctorVo;
import com.yxw.easyhealth.biz.vo.RegisterParamsVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.vo.register.Doctor;
import com.yxw.interfaces.vo.register.RegDoctor;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;

/**
 * @Package: com.yxw.easyhealth.biz.register.controller
 * @ClassName: ChooseDoctorController
 * @Statement: <p>
 *             选择医生控制类
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-9
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/easyhealth/register/doctor")
public class ChooseDoctorController {
	private static Logger logger = LoggerFactory.getLogger(ChooseDoctorController.class);

	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
	private RegisterBizManager registerBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);

	/**
	 * 显示选择医生号源的主页面
	 * 
	 * @param appCode
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(RegisterParamsVo vo, String viaFlag, String searchType, String searchStr, HttpServletRequest request) {
		String appCode = vo.getAppCode();
		Integer regType = vo.getRegType();
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/register/chooseDoctor");
		// 日历显示的天数 根据配置获取
		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(vo.getHospitalCode());

		int showDays = 7;
		if (ruleRegister != null) {
			int type = ruleRegister.getCalendarDaysType();
			switch (type) {
			case RegisterConstant.SEVEN_DAYS:
				showDays = 7 + 1;
				break;
			case RegisterConstant.FIFTEEN_DAYS:
				showDays = 15 + 1;
				break;
			case RegisterConstant.THIRTY_DAYS:
				showDays = 30 + 1;
				break;
			case RegisterConstant.CUSTOM_DAYS:
				showDays = ruleRegister.getCustomCalendarDays();
				break;
			default:
				showDays = 7 + 1;
				break;
			}
		}

		Calendar now = new java.util.GregorianCalendar();

		Integer isHasDutyReg = null;
		Integer isHasAppointmentReg = null;
		if (RegisterConstant.REG_TYPE_APPOINTMENT == regType.intValue()) {// 是否允许预约挂号
			isHasAppointmentReg = ruleRegister.getIsHasAppointmentReg();
			if (isHasAppointmentReg == null) {
				isHasAppointmentReg = 1;
			}

			if (isHasAppointmentReg != null && isHasAppointmentReg.intValue() == 1) {
				now.add(Calendar.DAY_OF_MONTH, 1);
			}

		} else if (RegisterConstant.REG_TYPE_CUR == regType.intValue()) {// 是否允许当班挂号
			isHasDutyReg = ruleRegister.getIsHasOnDutyReg();
			if (isHasDutyReg == null) {
				isHasDutyReg = 1;
			}
		}

		modelAndView.addObject("isHasDutyReg", isHasDutyReg);
		modelAndView.addObject("isHasAppointmentReg", isHasAppointmentReg);

		if (isHasDutyReg != null && isHasDutyReg.intValue() == RegisterConstant.DEF_IS_HAS_DUTY_REG) {
			modelAndView.addObject("onDutyRegTipContent", ruleRegister.getOnDutyRegTipContent());
		}

		modelAndView.addObject("nextDay", BizConstant.YYYYMMDD.format(now.getTime()));

		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		modelAndView.addObject(RegisterConstant.CALENDAR_SHOW_DAYS, showDays);
		modelAndView.addObject("viaFlag", viaFlag);
		modelAndView.addObject("searchType", searchType);
		modelAndView.addObject("searchStr", searchStr);
		return modelAndView;
	}

	/**
	 * 查询当前科室所有医生或者某个特定 医生的号源信息
	 * 
	 * @param request
	 * @param RegisterParamsVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDoctorList", method = { RequestMethod.POST, RequestMethod.GET })
	public RespBody queryDoctorList(HttpServletRequest request, RegisterParamsVo vo) {
		String hospitalId = vo.getHospitalId();
		String hospitalCode = vo.getHospitalCode();
		String branchHospitalCode = vo.getBranchHospitalCode();
		String deptCode = vo.getDeptCode();
		String deptName = vo.getDeptName();
		String selectRegDate = vo.getSelectRegDate();
		String isSearchDoctor = vo.getIsSearchDoctor();
		String appCode = vo.getAppCode();
		if (StringUtils.isBlank(selectRegDate)) {
			selectRegDate = BizConstant.YYYYMMDD.format(new Date());
		}
		List<Doctor> doctors = new ArrayList<Doctor>();
		Map<String, Object> map = new HashMap<String, Object>();

		// 判断是当班挂号
		Boolean isTimeValid = true;
		String timeValidTip = null;
		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(vo.getHospitalCode());
		if (BizConstant.YYYYMMDD.format(new Date()).equalsIgnoreCase(selectRegDate)) {
			String now = BizConstant.HHMM.format(new Date());
			Date onDutyRegStartTime = ruleRegister.getOnDutyRegStartTime();
			if (onDutyRegStartTime != null) {
				if (now.compareTo(BizConstant.HHMM.format(onDutyRegStartTime)) < 1) {
					isTimeValid = false;
					timeValidTip = RegisterConstant.DEF_TIP_DUTY_NOT_TO_MSG;
				}
			}

			if (isTimeValid) {
				Date onDutyRegEndTime = ruleRegister.getOnDutyRegEndTime();
				if (onDutyRegEndTime != null) {
					if (now.compareTo(BizConstant.HHMM.format(onDutyRegEndTime)) >= 1) {
						isTimeValid = false;
						timeValidTip = ruleRegister.getOnDutyRegOverTimeTip();
						if (StringUtils.isNotBlank(timeValidTip)) {
							timeValidTip = RegisterConstant.DEF_TIP_DUTY_OVER_TIME_MSG;
						}
					}
				}
			}
		} else {
			// 判断是否已配置预约明天限制
			Date appointmentTomorrowEndTime = ruleRegister.getAppointmentTomorrowEndTime();
			if (appointmentTomorrowEndTime != null) {
				// 判断选择的日期是否是明天
				GregorianCalendar today = new GregorianCalendar();
				today.add(Calendar.DAY_OF_MONTH, 1);
				if (BizConstant.YYYYMMDD.format(today.getTime()).equalsIgnoreCase(selectRegDate)) {
					String toDayTime = BizConstant.HHMM.format(today.getTime());
					if (toDayTime.compareTo(BizConstant.HHMM.format(appointmentTomorrowEndTime)) > 1) {
						timeValidTip = ruleRegister.getBespeakRegOverTimeTip();
						if (StringUtils.isBlank(timeValidTip)) {
							timeValidTip = RegisterConstant.DEF_TIP_APPOINTMENT_OVER_TIME_MSG;
						}
						isTimeValid = false;
					}
				}
			}
		}
		map.put("timeValidTip", timeValidTip);
		map.put("isTimeValid", isTimeValid);

		if (isTimeValid) {
			doctors = registerBizManager.searchDayRegSource(hospitalCode, branchHospitalCode, deptCode, selectRegDate);
			//入口为搜索医生/曾挂号医生 进入医生号源列表时  去掉其它医生号源
			if (StringUtils.isNotBlank(vo.getDoctorCode()) && StringUtils.isNotBlank(isSearchDoctor)) {
				Doctor queryDoctor = null;
				for (Doctor d : doctors) {
					if (vo.getDoctorCode().trim().equalsIgnoreCase(d.getDoctorCode())) {
						queryDoctor = d;
						break;
					}
				}
				doctors = new ArrayList<Doctor>();
				if (queryDoctor != null) {
					doctors.add(queryDoctor);
				}
			}

			// 避免前台处理时为空出现异常
			if (doctors == null) {
				doctors = new ArrayList<Doctor>(0);
			}

			// 补全医生的其它个人信息 (app预约挂号不补全)
			// 
			/*if (doctors.size() > 0) {
				Integer regType = null;
				if (BizConstant.YYYYMMDD.format(new Date()).equalsIgnoreCase(selectRegDate)) {
					regType = RegisterConstant.REG_TYPE_CUR;
				} else {
					regType = RegisterConstant.REG_TYPE_APPOINTMENT;
				}
				HospIdAndAppSecretVo easyHealthAppInfo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, appCode);
				// 不是app挂号 或者是当天的app挂号
				if (easyHealthAppInfo == null || ( easyHealthAppInfo != null && regType.intValue() == RegisterConstant.REG_TYPE_CUR )) {
					completeDoctorInfo(appCode, doctors, hospitalCode, branchHospitalCode, deptCode, deptName, regType);
				}
			}*/

			/*//补全医生的其它个人信息
			if (doctors.size() > 0) {
				String regType = null;
				if (vo.getRegType() != null) {
					regType = vo.getRegType().toString();
				}
				completeDoctorInfo(doctors, hospitalCode, branchHospitalCode, regType, deptCode, deptName);
			}*/

			if (doctors.size() == 0) {
				if (StringUtils.isBlank(ruleRegister.getNoSourceTipContent())) {
					map.put("noSourceTipContent", RegisterConstant.DEF_TIP_NO_REG_SOURCE);
				} else {
					map.put("noSourceTipContent", ruleRegister.getNoSourceTipContent());
				}

			}
		}

		/**是否显示剩余号源数【当班】*/
		if (StringUtils.isNotBlank(selectRegDate) && selectRegDate.startsWith(BizConstant.YYYYMMDD.format(new Date()))) {
			Integer isViewSourceNum = ruleRegister.getIsViewSourceNum();
			if (isViewSourceNum == null) {
				isViewSourceNum = RegisterConstant.IS_VIEW_SOURCE_NUM_YES;
			}
			map.put("isViewSourceNum", isViewSourceNum);
		}
		/**是否显示剩余号源数【预约】*/
		if (StringUtils.isNotBlank(selectRegDate) && !selectRegDate.startsWith(BizConstant.YYYYMMDD.format(new Date()))) {
			Integer isViewSourceNumReg = ruleRegister.getIsViewSourceNumReg();
			if (isViewSourceNumReg == null) {
				isViewSourceNumReg = RegisterConstant.IS_VIEW_SOURCE_NUM_YES;
			}
			map.put("isViewSourceNumReg", isViewSourceNumReg);
		}

		Integer isOrderByDoctorTitle = ruleRegister.getIsOrderByDoctorTitle();
		if (isOrderByDoctorTitle != null && isOrderByDoctorTitle.intValue() == RegisterConstant.ORDERBY_TITLE_YES) {
			Map<String, Integer> orderMap = new HashMap<String, Integer>();
			String doctorTitleOrder = ruleRegister.getDoctorTitleOrder();
			if (StringUtils.isNotBlank(doctorTitleOrder)) {
				String[] orders = doctorTitleOrder.split(",");
				for (int i = 0; i < orders.length; i++) {
					orderMap.put(orders[i], i);
				}
			}
			List<DoctorVo> sortDoctors = new ArrayList<DoctorVo>();
			DoctorVo doctorVo = null;
			try {
				for (Doctor doctor : doctors) {
					doctorVo = new DoctorVo();
					BeanUtils.copyProperties(doctorVo, doctor);
					if (StringUtils.isNotBlank(doctor.getDoctorTitle()) && orderMap.size() > 0
							&& orderMap.get(doctor.getDoctorTitle()) != null) {
						doctorVo.setTitleNo(orderMap.get(doctor.getDoctorTitle()));
					} else {
						doctorVo.setTitleNo(999);
					}

					sortDoctors.add(doctorVo);
				}
				Collections.sort(sortDoctors);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("doctors", sortDoctors);
		} else {
			map.put("doctors", doctors);
		}

		return new RespBody(Status.OK, map);
	}

	/**
	 * 补全医生的其它个人信息
	 * 
	 * @param doctors
	 */
	private void completeDoctorInfo(String appCode, List<Doctor> doctors, String hospitalCode, String branchHospitalCode, String deptCode,
			String deptName, Integer regType) {
		String[] doctorCodes = new String[doctors.size()];
		int index = 0;
		for (Doctor d : doctors) {
			if (StringUtils.isNotBlank(d.getDoctorCode())) {
				doctorCodes[index++] = d.getDoctorCode().trim();
			}
		}

		// 获取医生的其它信息
		List<RegDoctor> deptDoctors = baseDatasManager.searchDoctors(hospitalCode, branchHospitalCode, deptCode, doctorCodes, regType);
		if (deptDoctors == null) {
			deptDoctors = new ArrayList<RegDoctor>();
		} else {
			if (deptDoctors.size() > 0) {
				Map<String, RegDoctor> regDoctorMap = new HashMap<String, RegDoctor>();
				for (RegDoctor rd : deptDoctors) {
					regDoctorMap.put(rd.getDoctorCode(), rd);
				}

				RegDoctor rd = null;
				for (Doctor d : doctors) {
					if (StringUtils.isBlank(d.getCategory()) && !RegisterConstant.IS_NOT_DOCTOR_FLAG.equalsIgnoreCase(d.getDoctorTitle())) {
						d.setCategory(RegisterConstant.PROFICIENT_NUM);
					}
					if (StringUtils.isEmpty(d.getDeptCode())) {
						d.setDeptCode(deptCode);
					}
					if (StringUtils.isEmpty(d.getDeptName())) {
						d.setDeptName(deptName);
					}
					rd = regDoctorMap.get(d.getDoctorCode());
					if (rd != null && StringUtils.isNotEmpty(rd.getPicture())) {
						d.setPicture(rd.getPicture());
					}
				}
			}
		}
	}

	/**
	 * 显示搜素医生的主页面
	 * 
	 * @param RegisterParamsVo
	 * @return
	 */
	@RequestMapping(value = "/searchIndex")
	public ModelAndView searchIndex(RegisterParamsVo vo, String searchType) {
		ModelAndView modelAndView = null;
		if (BizConstant.SEARCH_TYPE_DEPT.equalsIgnoreCase(searchType)) {
			modelAndView = new ModelAndView("/easyhealth/biz/register/searchDept");
		} else if (BizConstant.SEARCH_TYPE_DOCTOR.equalsIgnoreCase(searchType)) {
			modelAndView = new ModelAndView("/easyhealth/biz/register/searchDoctor");
		}
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		return modelAndView;
	}

	/**
	 * 查询 医生的号源信息
	 * 
	 * @param request
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchDoctors", method = RequestMethod.POST)
	public RespBody searchDoctors(HttpServletRequest request, RegisterParamsVo vo, String searchStr) {
		List<Object> searchDoctors = null;

		if (StringUtils.isNotBlank(searchStr)) {
			Integer regType = vo.getRegType();
			if (regType == null) {
				regType = RegisterConstant.REG_TYPE_APPOINTMENT;
			}

			RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(vo.getHospitalCode());
			Integer isSameDeptData = null;
			if (ruleHisData != null) {
				isSameDeptData = ruleHisData.getIsSameDeptData();
			}
			if (isSameDeptData != null && isSameDeptData.intValue() == RuleConstant.IS_SAME_DEPT_DATA_NO) {
				searchDoctors =
						baseDatasManager.getMatchHospitalDoctors(searchStr.trim(), vo.getHospitalCode(), vo.getBranchHospitalCode(),
								String.valueOf(regType));
			} else {
				searchDoctors =
						baseDatasManager.getMatchHospitalDoctors(searchStr.trim(), vo.getHospitalCode(), vo.getBranchHospitalCode(), null);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("invoke ChooseDoctorController.searchDoctors  , searchStr : {}", searchStr);
			}
		}

		if (CollectionUtils.isEmpty(searchDoctors)) {
			searchDoctors = new ArrayList<Object>();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("doctors", searchDoctors);
		return new RespBody(Status.OK, map);
	}

	/**
	 * 查询科室信息
	 * 
	 * @param request
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchDepts", method = RequestMethod.POST)
	public RespBody searchDepts(HttpServletRequest request, RegisterParamsVo vo, String searchStr) {
		List<String> deptKeys = null;
		String areaCode = vo.getAreaCode();
		//		String appCode = vo.getAppCode();
		String hospitalName = vo.getHospitalName();
		Integer regType = vo.getRegType();
		if (regType == null) {
			regType = RegisterConstant.REG_TYPE_APPOINTMENT;
		}

		RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(vo.getHospitalCode());
		Integer isSameDeptData = null;
		if (ruleHisData != null) {
			isSameDeptData = ruleHisData.getIsSameDeptData();
		}
		if (isSameDeptData != null && isSameDeptData.intValue() == RuleConstant.IS_SAME_DEPT_DATA_NO) {
			deptKeys = baseDatasManager.searchDeptNames(searchStr, String.valueOf(regType), /*appCode,*/areaCode);
		} else {
			if (regType == RegisterConstant.REG_TYPE_CUR) {
				deptKeys = baseDatasManager.searchDeptNames(searchStr, String.valueOf(regType), /*appCode,*/areaCode);
			} else {
				deptKeys = baseDatasManager.searchDeptNames(searchStr, null, /*appCode,*/areaCode);
			}
		}

		if (CollectionUtils.isEmpty(deptKeys)) {
			deptKeys = new ArrayList<String>();
		}

		List<String> matchHospitalDepts = new ArrayList<String>();
		for (String deptKey : deptKeys) {
			if (deptKey.indexOf(hospitalName) > -1) {
				matchHospitalDepts.add(deptKey);
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("depts", matchHospitalDepts);
		return new RespBody(Status.OK, map);
	}

	/**
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deptInfo")
	public RespBody deptInfo(SearchParamVo vo, String deptNameKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		String areaCode = vo.getAreaCode();
		String appCode = vo.getAppCode();

		Integer regType = null;
		if (deptNameKey.indexOf(RegisterConstant.REG_TYPE_CUR_CHINESE) > -1) {
			regType = RegisterConstant.REG_TYPE_CUR;
		} else if (deptNameKey.indexOf(RegisterConstant.REG_TYPE_APPOINTMENT_CHINESE) > -1) {
			regType = RegisterConstant.REG_TYPE_APPOINTMENT;
		}

		Dept dept = baseDatasManager.getDeptByField(null, deptNameKey, null, /*appCode,*/areaCode);
		if (dept != null) {
			String hospitalCode = dept.getHospitalCode();
			String branchHospitalCode = dept.getBranchHospitalCode();
			String hospitalId = dept.getHospitalId();
			HospIdAndAppSecretVo hospIdAndAppSecretVo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, appCode);
			/*	String appId = null;
					if (StringUtils.isBlank(appId) && appCode.startsWith(BizConstant.MODE_TYPE_APP)) {
							appId = String.valueOf(Math.abs(hospitalId.hashCode()));
							dept.setRegType(regType);
						} else {
							appId = vo.getAppId();
						}*/

			dept.setAppId(hospIdAndAppSecretVo.getAppId());
			dept.setRegType(regType);

			RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(hospitalCode);
			if (ruleRegister.getIsHasGradeTwoDept() == RuleConstant.IS_HAS_TWO_DEPT_NO) {
				//没有2级科室  直接跳号源列表
				map.put("isGoRegInfo", true);
			} else {
				// parentDeptCode为 null 或者为 0 为一级科室
				boolean isParent = CacheConstant.CACHE_PARENT_DEPT_CODE.equalsIgnoreCase(dept.getParentDeptCode());
				if (StringUtils.isNotEmpty(dept.getParentDeptCode()) && !isParent) {
					map.put("isGoRegInfo", true);
				} else {
					map.put("isGoRegInfo", false);
				}
			}
			map.put("dept", dept);
		}

		return new RespBody(Status.OK, map);
	}

	/*@ResponseBody
	@RequestMapping(value = "/deptInfo")
	public RespBody deptInfo(SearchParamVo vo, String deptNameKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		String areaCode = vo.getAreaCode();
		String appCode = vo.getAppCode();
		Integer regType = null;
		if (deptNameKey.indexOf(RegisterConstant.REG_TYPE_CUR_CHINESE) > -1) {
			regType = RegisterConstant.REG_TYPE_CUR;
		} else if (deptNameKey.indexOf(RegisterConstant.REG_TYPE_APPOINTMENT_CHINESE) > -1) {
			regType = RegisterConstant.REG_TYPE_APPOINTMENT;
		}

		RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(vo.getHospitalCode());
		Dept dept = baseDatasManager.getDeptByField(ruleHisData, deptNameKey, String.valueOf(regType), appCode,areaCode);
		if (dept != null) {
			//			String hospitalCode = dept.getHospitalCode();
			//			String hospitalId = dept.getHospitalId();
			String appId = null;

						if (StringUtils.isBlank(appId) && appCode.startsWith(BizConstant.MODE_TYPE_APP)) {
							appId = String.valueOf(Math.abs(hospitalId.hashCode()));
							dept.setRegType(regType);
						} else {
							appId = vo.getAppId();
						}

			appId = vo.getAppId();
			dept.setAppId(appId);
			dept.setRegType(regType);
			RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(vo.getHospitalCode());
			if (ruleRegister.getIsHasGradeTwoDept() == RuleConstant.IS_HAS_TWO_DEPT_NO) {
				// 没有2级科室 直接跳号源列表
				map.put("isGoRegInfo", true);
			} else {
				// parentDeptCode为 null 或者为 0 为一级科室
				boolean isParent = CacheConstant.CACHE_PARENT_DEPT_CODE.equalsIgnoreCase(dept.getParentDeptCode());
				if (StringUtils.isNotEmpty(dept.getParentDeptCode()) && !isParent) {
					map.put("isGoRegInfo", true);
				} else {
					map.put("isGoRegInfo", false);
				}
			}
			map.put("dept", dept);
		}

		return new RespBody(Status.OK, map);
	}*/

	/**
	 * 查询2级科室
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/querySubDepts")
	public ModelAndView querySubDepts(SearchParamVo vo) {
		String hospitalId = vo.getHospitalId();
		List<Dept> subDepts = null;

		String hospitalCode = vo.getHospitalCode();
		String branchCode = vo.getBranchHospitalCode();
		Integer regType = vo.getRegType();
		String parentDeptCode = vo.getParentDeptCode();
		String appCode = vo.getAppCode();

		RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(hospitalCode);
		HospIdAndAppSecretVo easyHealthAppInfo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, appCode);
		if (easyHealthAppInfo != null) {
			subDepts = baseDatasManager.getAppLevelTwoDepts(ruleHisData, hospitalCode, branchCode, regType.toString(), parentDeptCode);
		} else {
			logger.error("系统配置错误,医院:{}未配置平台", vo.getHospitalName());
		}

		if (subDepts == null) {
			subDepts = new ArrayList<Dept>(0);
		}
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/register/searchSubDepts");
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		modelAndView.addObject(BizConstant.COMMON_ENTITY_LIST_KEY, subDepts);
		return modelAndView;
	}

}
