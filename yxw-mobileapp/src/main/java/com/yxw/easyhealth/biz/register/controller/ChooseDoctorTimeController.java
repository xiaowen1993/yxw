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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.easyhealth.biz.vo.RegisterParamsVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.vo.register.DocTime;
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
@RequestMapping("/easyhealth/register/doctorTime")
public class ChooseDoctorTimeController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(ChooseDoctorTimeController.class);

	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
	private RegisterBizManager registerBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);

	private final static String CHOOSE_DOCTOR_TIME_SESSION_VO_KEY = "EASY_HEALTH_CHOOSE_DOCTOR_TIME_VO";
	private final static String CHOOSE_DOCTOR_TIME_SESSION_VIAFLAT_KEY = "EASY_HEALTH_CHOOSE_DOCTOR_TIME_PARAM";

	/**
	 * 显示选择医院的主页面
	 * 
	 * @param appCode
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(RegisterParamsVo vo, String viaFlag, HttpServletRequest request) {
		// 非正常流程进入的，认定为完善个人信息的返回
		if (StringUtils.isBlank(vo.getDeptCode()) && StringUtils.isBlank(vo.getDoctorCode()) && StringUtils.isBlank(viaFlag)) {
			RegisterParamsVo commonVo = (RegisterParamsVo) request.getSession().getAttribute(CHOOSE_DOCTOR_TIME_SESSION_VO_KEY);
			String tempViaFlag = (String) request.getSession().getAttribute(CHOOSE_DOCTOR_TIME_SESSION_VIAFLAT_KEY);
			logger.info("commonVo=" + JSON.toJSONString(commonVo));
			if (commonVo != null) {
				vo = commonVo;
				viaFlag = tempViaFlag;
			}

			request.getSession().removeAttribute(CHOOSE_DOCTOR_TIME_SESSION_VO_KEY);
			request.getSession().removeAttribute(CHOOSE_DOCTOR_TIME_SESSION_VIAFLAT_KEY);
		}

		// 资料没完善
		if (!easyHealthUserHasPerfectInfo(request)) {
			RegisterParamsVo tempVo = new RegisterParamsVo();
			try {
				BeanUtils.copyProperties(tempVo, vo);
				request.getSession().setAttribute(CHOOSE_DOCTOR_TIME_SESSION_VO_KEY, tempVo);
				request.getSession().setAttribute(CHOOSE_DOCTOR_TIME_SESSION_VIAFLAT_KEY, viaFlag);
			} catch (Exception e) {
				logger.error("复制信息存放如缓存异常...");
			}
		}

		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/register/chooseDoctorTime");
		// 获取缓存中医生的个人信息
		String appCode = vo.getAppCode();
		String hospitalCode = vo.getHospitalCode();
		String branchHospitalCode = vo.getBranchHospitalCode();
		String deptCode = vo.getDeptCode();
		String doctorCode = vo.getDoctorCode();
		String selectRegDate = vo.getSelectRegDate();
		if (StringUtils.isEmpty(selectRegDate)) {
			selectRegDate = BizConstant.YYYYMMDD.format(new Date());
		}
		String hospitalId = vo.getHospitalId();

		RegDoctor regDoctor = queryRegDoctor(appCode, hospitalCode, branchHospitalCode, deptCode, doctorCode);
		if (regDoctor == null) {
			Doctor queryDoctor =
					registerBizManager.searchDoctorRegSource(hospitalId, hospitalCode, branchHospitalCode, deptCode, doctorCode,
							selectRegDate, appCode);

			// 避免查询失败 前端显示不友好的错误信息
			if (queryDoctor == null) {
				queryDoctor = new Doctor();
			} else {
				if (StringUtils.isBlank(vo.getDeptName())) {
					vo.setDeptName(queryDoctor.getDeptName());
				}
				if (StringUtils.isBlank(vo.getDoctorTitle())) {
					vo.setDoctorTitle(queryDoctor.getDoctorTitle());
				}

				vo.setDoctorName(queryDoctor.getDoctorName());
				vo.setDoctorPic(queryDoctor.getPicture());
				vo.setDoctorIntrodution(queryDoctor.getDoctorIntrodution());
			}
		} else {
			if (StringUtils.isBlank(vo.getDeptName())) {
				vo.setDeptName(regDoctor.getDeptName());
			}
			if (StringUtils.isBlank(vo.getDoctorTitle())) {
				vo.setDoctorTitle(regDoctor.getDoctorTitle());
			}

			vo.setDoctorName(regDoctor.getDoctorName());
			vo.setDoctorPic(regDoctor.getPicture());
			vo.setDoctorIntrodution(regDoctor.getDoctorIntrodution());
		}

		// 日历显示的天数 根据配置获取 没有预约挂号只显示当天分时
		// 日历显示的天数 根据配置获取 没有预约挂号只显示当天分时
		int showDays = 7;
		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(vo.getHospitalCode());
		Integer isHasAppointmentReg = ruleRegister.getIsHasAppointmentReg();
		if (isHasAppointmentReg == null) {
			isHasAppointmentReg = 1;
		}
		if (isHasAppointmentReg.intValue() == 1) {
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
		} else {
			showDays = 1;
		}

		// 是否允许当天挂号
		Integer isHasDutyReg = ruleRegister.getIsHasOnDutyReg();
		if (isHasDutyReg == null) {
			isHasDutyReg = 1;
		}

		String[] optionalDates = new String[showDays];
		Calendar now = new java.util.GregorianCalendar();
		if (isHasDutyReg.intValue() != 1) {
			now.add(Calendar.DAY_OF_MONTH, 1);
		}

		optionalDates[0] = BizConstant.YYYYMMDDE.format(now.getTime());
		int selectedIndex = 0;
		String selectedDate = optionalDates[0];
		for (int i = 1; i < showDays; i++) {
			now.add(Calendar.DAY_OF_YEAR, 1);
			optionalDates[i] = BizConstant.YYYYMMDDE.format(now.getTime());
			if (StringUtils.isNotBlank(vo.getSelectRegDate())
					&& vo.getSelectRegDate().equalsIgnoreCase(BizConstant.YYYYMMDD.format(now.getTime()))) {
				selectedIndex = i;
				selectedDate = optionalDates[i];
			}

		}

		modelAndView.addObject("showDays", showDays);
		modelAndView.addObject("selectedDate", selectedDate);
		vo.setSelectRegDate(selectedDate);
		modelAndView.addObject("selectedIndex", selectedIndex);
		modelAndView.addObject(RegisterConstant.OPTIONAL_DATES, com.yxw.utils.StringUtils.ArrayToStr(optionalDates));

		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		modelAndView.addObject("viaFlag", viaFlag);
		return modelAndView;
	}

	/**
	 * 查询医生的分时号源信息
	 * 
	 * @param request
	 * @param RegisterParamsVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadDoctorTime", method = RequestMethod.POST)
	public RespBody loadDoctorTime(HttpServletRequest request, RegisterParamsVo vo) {
		String hospitalCode = vo.getHospitalCode();
		String branchHospitalCode = vo.getBranchHospitalCode();
		String deptCode = vo.getDeptCode();
		String doctorCode = vo.getDoctorCode();
		String selectRegDate = vo.getSelectRegDate();
		if (StringUtils.isBlank(selectRegDate)) {
			selectRegDate = BizConstant.YYYYMMDD.format(new Date());
		} else {
			try {
				selectRegDate = BizConstant.YYYYMMDD.format(BizConstant.YYYYMMDDE.parse(selectRegDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				selectRegDate = BizConstant.YYYYMMDD.format(new Date());
			}
		}

		List<DocTime> doctorTimes = null;
		try {
			doctorTimes =
					registerBizManager.searchDocTime(hospitalCode, branchHospitalCode, deptCode, doctorCode, selectRegDate,
							RegisterConstant.TIME_FLAG_ALL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			doctorTimes = new ArrayList<DocTime>();
		}

		if (doctorTimes == null) {
			doctorTimes = new ArrayList<DocTime>();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("doctorTimes", doctorTimes);
		// 当前时间
		map.put("curDate", BizConstant.YYYYMMDD.format(new Date()));
		map.put("curTime", BizConstant.HHMM.format(new Date()));
		return new RespBody(Status.OK, map);
	}

	/**
	 * 得到当前选择的医生信息
	 * 
	 * @param vo
	 */
	public RegDoctor queryRegDoctor(String appCode, String hospitalCode, String branchHospitalCode, String deptCode, String doctorCode) {
		RegDoctor doctor = baseDatasManager.searchDoctor(hospitalCode, branchHospitalCode, deptCode, doctorCode);
		return doctor;
	}
}
