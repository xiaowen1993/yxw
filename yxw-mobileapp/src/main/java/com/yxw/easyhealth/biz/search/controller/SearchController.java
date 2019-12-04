package com.yxw.easyhealth.biz.search.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.platform.Dept;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.easyhealth.biz.register.controller.RegisterIndexController;
import com.yxw.easyhealth.biz.search.vo.SearchParamVo;
import com.yxw.easyhealth.biz.vo.RegisterParamsVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.vo.register.Doctor;

/**
 * @Package: com.yxw.mobileapp.biz.search.controller
 * @ClassName: SearchController
 * @Statement: <p>搜索</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-10-19
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/easyhealth/search/")
public class SearchController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(RegisterIndexController.class);

	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);

	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	/**
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(SearchParamVo vo) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/search/index");
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/doSearch", method = RequestMethod.POST)
	public RespBody doSearch(SearchParamVo vo) {
		String searchType = vo.getSearchType();
		Map<String, Object> map = new HashMap<String, Object>();
		if (BizConstant.SEARCH_TYPE_HOSPITAL.equalsIgnoreCase(searchType)) {
			List<HospIdAndAppSecretVo> hospitalInfos = searchHospitals(vo);
			map.put(BizConstant.COMMON_ENTITY_LIST_KEY, hospitalInfos);
		} else if (BizConstant.SEARCH_TYPE_DEPT.equalsIgnoreCase(searchType)) {
			List<String> deptKeys = searchDepts(vo);
			map.put(BizConstant.COMMON_ENTITY_LIST_KEY, deptKeys);
		} else if (BizConstant.SEARCH_TYPE_DOCTOR.equalsIgnoreCase(searchType)) {
			List<String> doctors = searchDoctors(vo);
			map.put(BizConstant.COMMON_ENTITY_LIST_KEY, doctors);
		}
		return new RespBody(Status.OK, map);
	}

	/**
	 * 搜索医院
	 * @param vo
	 * @return
	 */
	private List<HospIdAndAppSecretVo> searchHospitals(SearchParamVo vo) {
		String appCode = vo.getAppCode();
		String areaCode = vo.getAreaCode();
		List<HospIdAndAppSecretVo> hospitalInfos = baseDatasManager.getHospitalListByAppCode(appCode, areaCode);
		List<HospIdAndAppSecretVo> searchHospiatlInfos = new ArrayList<HospIdAndAppSecretVo>();
		if (!CollectionUtils.isEmpty(hospitalInfos)) {
			String searchStr = vo.getSearchStr();
			for (HospIdAndAppSecretVo hospitalInfo : hospitalInfos) {
				if (hospitalInfo.getHospName().indexOf(searchStr) > -1) {
					searchHospiatlInfos.add(hospitalInfo);
				}
			}
		}
		return searchHospiatlInfos;
	}

	/**
	 * 搜索科室
	 * @param vo
	 * @return
	 */
	private List<String> searchDepts(SearchParamVo vo) {
		List<String> deptKeys = null;
		String appCode = vo.getAppCode();
		String areaCode = vo.getAreaCode();
		String searchStr = vo.getSearchStr();

		deptKeys = baseDatasManager.searchDeptNames(searchStr, null, /*appCode,*/areaCode);

		/*		if (appCode.startsWith(BizConstant.MODE_TYPE_APP)) {
					deptKeys = baseDatasManager.searchDeptNames(searchStr, null, appCode,areaCode);
				} else {
					//do something...............
				}*/
		if (CollectionUtils.isEmpty(deptKeys)) {
			deptKeys = new ArrayList<String>();
		}
		return deptKeys;
	}

	/**
	 * 
	 * @param vo
	 * @return
	 */
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

		Dept dept = baseDatasManager.getDeptByField(null, deptNameKey, null, appCode,areaCode);
		if (dept != null) {
			String hospitalCode = dept.getHospitalCode();
			String branchHospitalCode = dept.getBranchHospitalCode();
			String hospitalId = dept.getHospitalId();
			HospIdAndAppSecretVo hospIdAndAppSecretVo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, appCode);
				String appId = null;
					if (StringUtils.isBlank(appId) && appCode.startsWith(BizConstant.MODE_TYPE_APP)) {
							appId = String.valueOf(Math.abs(hospitalId.hashCode()));
							dept.setRegType(regType);
						} else {
							appId = vo.getAppId();
						}

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
	}*/

	/**
	 * 查询2级科室
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

		RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(hospitalCode);
		HospIdAndAppSecretVo hospAppInfo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, vo.getAppCode());
		if (hospAppInfo != null) {
			subDepts = baseDatasManager.getAppLevelTwoDepts(ruleHisData, hospitalCode, branchCode, regType.toString(), parentDeptCode);
		} else {
			logger.error("系统配置错误,医院:{}未配置平台", vo.getHospitalName());
		}

		if (subDepts == null) {
			subDepts = new ArrayList<Dept>();
		}
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/search/searchSubDepts");
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		modelAndView.addObject(BizConstant.COMMON_ENTITY_LIST_KEY, subDepts);
		return modelAndView;
	}

	/**
	 * 搜索医生
	 * @param vo
	 * @return
	 */
	private List<String> searchDoctors(SearchParamVo vo) {
		String searchStr = vo.getSearchStr();
		String areaCode = vo.getAreaCode();
		//		String hospitalCode = vo.getHospitalCode();
		//		String branchHospitalCode = vo.getBranchHospitalCode();
		List<String> searchDoctors = new ArrayList<String>();
		if (StringUtils.isNotBlank(searchStr)) {
			searchDoctors = baseDatasManager.getMatchAreaDoctors(searchStr.trim(), /*hospitalCode, branchHospitalCode,*/areaCode);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(BizConstant.COMMON_ENTITY_LIST_KEY, searchDoctors);
		return searchDoctors;
	}

	/**
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doctorInfo")
	public RespBody doctorInfo(RegisterParamsVo vo, String doctorNameKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		String areaCode = vo.getAreaCode();
		String appCode = vo.getAppCode();

		String hospitalId = vo.getHospitalId();
		HospIdAndAppSecretVo hospIdAndAppSecretVo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, appCode);

		String hospitalCode = vo.getHospitalCode();
		String branchHospitalCode = vo.getBranchHospitalCode();

		Doctor doctor = baseDatasManager.getAreaDoctorByField(doctorNameKey, hospitalCode, branchHospitalCode, areaCode);

		if (doctor != null) {
			String[] array = doctorNameKey.split("#");
			String regType = "";
			if (array.length == 8) {
				hospitalId = array[2];
				hospitalCode = array[3];
				branchHospitalCode = array[4];
				regType = array[5];
				vo.setRegType(Integer.valueOf(regType));
				vo.setHospitalCode(hospitalCode);
				vo.setHospitalId(hospitalId);
			}

			vo.setBranchHospitalCode(branchHospitalCode);
			vo.setDeptCode(doctor.getDeptCode());
			vo.setDeptName(doctor.getDeptName());
			vo.setDoctorCode(doctor.getDoctorCode());
			vo.setDoctorName(doctor.getDoctorName());
			vo.setAppId(hospIdAndAppSecretVo.getAppId());

			if (StringUtils.isNotBlank(hospitalCode) && StringUtils.isNotBlank(branchHospitalCode)) {
				CodeAndInterfaceVo codeInterface = baseDatasManager.getCodeAndInterfaceVo(hospitalCode, branchHospitalCode);
				if (codeInterface != null) {
					vo.setHospitalName(codeInterface.getHospitalName());
					vo.setBranchHospitalId(codeInterface.getBranchHospitalId());
					vo.setBranchHospitalName(codeInterface.getBranchName());
				}
			}

			map.put("doctor", vo);
		}

		return new RespBody(Status.OK, map);
	}
}
