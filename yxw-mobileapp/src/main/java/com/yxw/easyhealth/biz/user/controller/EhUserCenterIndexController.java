package com.yxw.easyhealth.biz.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.HadRegDoctor;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.CommonParamsVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.easyhealth.biz.register.service.HadRegDoctorService;
import com.yxw.easyhealth.biz.vo.RegisterParamsVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.utils.DateUtils;

@Controller
@RequestMapping("app/usercenter")
public class EhUserCenterIndexController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(EhUserCenterIndexController.class);

	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);

	private HadRegDoctorService hadRegDoctorService = SpringContextHolder.getBean(HadRegDoctorService.class);

	// private HospitalAndOptionCache hospitalAndOptionCache = SpringContextHolder.getBean(HospitalAndOptionCache.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@RequestMapping(value = "/historyRegDoctorIndex")
	public ModelAndView index(CommonParamsVo vo, HttpServletRequest request) {
		/*
		 * if (StringUtils.isBlank(vo.getAreaCode())) { vo.setAreaCode(getAreaCode(request)); }
		 */
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/static/historyRegDoctor");
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "getHistoryDoctors", method = RequestMethod.POST)
	public Object getHistoryRegDoctor(RegisterParamsVo vo) {
		List<HadRegDoctor> doctors = new ArrayList<HadRegDoctor>();
		try {
			// 从缓存拿
			List<HadRegDoctor> hadRegDoctors = baseDatasManager.getHadRegDoctorsByOpenId(vo.getOpenId());
			if (CollectionUtils.isEmpty(hadRegDoctors)) {
				hadRegDoctors = hadRegDoctorService.findByOpenId(vo.getOpenId());
				if (!CollectionUtils.isEmpty(hadRegDoctors)) {
					// 测试，不想要那么多数据
					/*
					 * if (hadRegDoctors.size() > 10) { hadRegDoctors = hadRegDoctors.subList(0, 10); }
					 */

					// 对数据进行排序 去重
					// 曾挂号医生，在入库的时候已经去重了。
				}

				// 拿到的数据，还需要进行一次过滤。即查出来的医生的医院，必须是在健康易的列表里面才行。
				String appCode = vo.getAppCode();
				String areaCode = vo.getAreaCode();
				if (logger.isDebugEnabled()) {
					logger.debug("查询曾挂号医生. 过滤. appCode={}, areaCode={}.", new Object[] { appCode, areaCode });
				}

				List<HospIdAndAppSecretVo> hospitalInfos = baseDatasManager.getHospitalListByAppCode(appCode, areaCode);
				Set<String> hospitalCodes = new HashSet<String>();
				for (HospIdAndAppSecretVo hospVo : hospitalInfos) {
					hospitalCodes.add(hospVo.getHospCode());
				}

				for (HadRegDoctor doctor : hadRegDoctors) {
					if (hospitalCodes.contains(doctor.getHospitalCode())) {
						doctors.add(doctor);
					}
				}
			}
		} catch (Exception e) {
			logger.error("加载我的医生错误异常。errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}
		return new RespBody(Status.OK, doctors);
	}

	@RequestMapping(value = "/settings")
	public ModelAndView gotoSettings(CommonParamsVo vo) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/settings/settingIndex");
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		return modelAndView;
	}

	@RequestMapping(value = "/aboutUs")
	public ModelAndView aboutUs(CommonParamsVo vo) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/settings/aboutUs");
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		return modelAndView;
	}

	@RequestMapping(value = "/regRecords/list")
	public ModelAndView toRegRecordList(CommonParamsVo vo, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/usercenter/regRecordList");

		// 获取当班医院
		List<HospitalInfoByEasyHealthVo> dutyHospitals = null;
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(vo.getAppCode());
		parameters.add(BizConstant.OPTION_DUTY_REGISTER);
		parameters.add(vo.getAreaCode());
		List<Object> result = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalByOption", parameters);
		if (CollectionUtils.isNotEmpty(result)) {
			String source = JSON.toJSONString(result);
			dutyHospitals = JSON.parseArray(source, HospitalInfoByEasyHealthVo.class);
		}

		List<Object> params = new ArrayList<Object>();
		for (int i = dutyHospitals.size() - 1; i >= 0; i--) {
			HospitalInfoByEasyHealthVo hospitalInfoByEasyHealthVo = dutyHospitals.get(i);
			params.clear();
			params.add(hospitalInfoByEasyHealthVo.getHospitalCode());
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getDefCodeAndInterfaceVo", params);
			if (!CollectionUtils.isEmpty(results)) {
				CodeAndInterfaceVo codeAndInterfaceVo = (CodeAndInterfaceVo) results.get(0);

				if (codeAndInterfaceVo.getStatus() == 0) {
					dutyHospitals.remove(i);
				}
			}
		}

		// 获取预约医院
		List<HospitalInfoByEasyHealthVo> appointHospitals = null;
		parameters.clear();
		parameters.add(vo.getAppCode());
		parameters.add(BizConstant.OPTION_RESERVATION_REGISTER);
		parameters.add(vo.getAreaCode());
		result = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalByOption", parameters);
		if (CollectionUtils.isNotEmpty(result)) {
			String source = JSON.toJSONString(result);
			appointHospitals = JSON.parseArray(source, HospitalInfoByEasyHealthVo.class);
		}

		for (int i = appointHospitals.size() - 1; i >= 0; i--) {
			HospitalInfoByEasyHealthVo hospitalInfoByEasyHealthVo = appointHospitals.get(i);
			params.clear();
			params.add(hospitalInfoByEasyHealthVo.getHospitalCode());
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getDefCodeAndInterfaceVo", params);
			if (!CollectionUtils.isEmpty(results)) {
				CodeAndInterfaceVo codeAndInterfaceVo = (CodeAndInterfaceVo) results.get(0);

				if (codeAndInterfaceVo.getStatus() == 0) {
					appointHospitals.remove(i);
				}
			}
		}

		// 医院并集
		Map<String, HospitalInfoByEasyHealthVo> dutyMap = Maps.uniqueIndex(dutyHospitals, new hospitalFunction());
		Map<String, HospitalInfoByEasyHealthVo> appointMap = Maps.uniqueIndex(appointHospitals, new hospitalFunction());
		Map<String, HospitalInfoByEasyHealthVo> hospitalMap = new HashMap<>();
		hospitalMap.putAll(dutyMap);
		hospitalMap.putAll(appointMap);

		modelAndView.addObject("hospitals", hospitalMap.values());
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "regRecords/getData", method = RequestMethod.POST)
	public Object getData(RegisterParamsVo vo, Integer dateType) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 判断时间段
			// 0: 全部
			// 1: 今天
			// 2: 近3天
			// 3: 近一周
			// 4: 近30天
			long beginTime = 0;
			long endTime = 0;
			Date today = new Date();

			switch (dateType.intValue()) {
			case 1:
				beginTime = DateUtils.getDateBegin(today).getTime();
				endTime = DateUtils.getDateEnd(today).getTime();
				break;
			case 2:
				beginTime = DateUtils.getDateBegin(DateUtils.StringToDateYMD(DateUtils.getDayForDate(today, -2))).getTime();
				endTime = DateUtils.getDateEnd(today).getTime();
				break;
			case 3:
				beginTime = DateUtils.getDateBegin(DateUtils.StringToDateYMD(DateUtils.getDayForDate(today, -6))).getTime();
				endTime = DateUtils.getDateEnd(today).getTime();
				break;
			case 4:
				beginTime = DateUtils.getDateBegin(DateUtils.StringToDateYMD(DateUtils.getDayForDate(today, -29))).getTime();
				endTime = DateUtils.getDateEnd(today).getTime();
				break;
			default:
				break;
			}

			RegisterService registerService = SpringContextHolder.getBean(RegisterService.class);
			List<RegisterRecord> records =
					registerService
							.findRecordsByOpenIdAndAppCode(vo.getOpenId(), vo.getAppCode(), vo.getHospitalCode(), beginTime, endTime);
			resultMap.put(BizConstant.COMMON_ENTITY_LIST_KEY, records);
		} catch (Exception e) {
			logger.error("通过OpenId，APPCode，查询挂号记录异常. errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return new RespBody(Status.OK, resultMap);
	}

	class hospitalFunction implements Function<HospitalInfoByEasyHealthVo, String> {

		@Override
		public String apply(HospitalInfoByEasyHealthVo input) {
			return input.getHospitalId();
		}
	}
}
