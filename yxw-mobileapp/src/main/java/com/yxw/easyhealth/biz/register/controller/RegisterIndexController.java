/**
 * Copyright© 2014-2015 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2015年6月4日
 * @version 1.0
 */
package com.yxw.easyhealth.biz.register.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.HospitalConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.HadRegDoctor;
import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.commons.vo.platform.Dept;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.easyhealth.biz.vo.RegisterParamsVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;

/**
 * @Package: com.yxw.easyhealth.biz.register.controller
 * @ClassName: RegisterIndexController
 * @Statement: <p>
 *             掌上医院标准平台挂号首页
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
@RequestMapping("/easyhealth/register")
public class RegisterIndexController extends BaseAppController {

	private static Logger logger = LoggerFactory.getLogger(RegisterIndexController.class);

	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);

	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	// private HospitalAndOptionCache hospitalAndOptionCache = SpringContextHolder.getBean(HospitalAndOptionCache.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * app 挂号业务->选择医院列表
	 * 
	 * @param vo
	 *            挂号业务公共参数传值对象
	 * @param request
	 *            app请求信息中,必须包含appCode,regType,areaCode 
	 * @return
	 */
	@RequestMapping(value = "/hospitalList")
	public ModelAndView hospitalList(RegisterParamsVo vo, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/common/hospotalList");

		String areaCode = vo.getAreaCode();

		Collection<HospitalInfoByEasyHealthVo> result = null;

		List<HospitalInfoByEasyHealthVo> hospitalInfos = null;
		if (vo.getRegType().intValue() == RegisterConstant.REG_TYPE_CUR) {
			List<Object> params = new ArrayList<Object>();
			params.add(vo.getAppCode());
			params.add(BizConstant.OPTION_DUTY_REGISTER);
			params.add(areaCode);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalByOption", params);
			if (CollectionUtils.isNotEmpty(results)) {
				String source = JSON.toJSONString(results);
				hospitalInfos = JSON.parseArray(source, HospitalInfoByEasyHealthVo.class);
			}
			// hospitalInfos = hospitalAndOptionCache.getHospitalByOption(BizConstant.OPTION_DUTY_REGISTER, areaCode);
		} else {
			// hospitalInfos = hospitalAndOptionCache.getHospitalByOption(BizConstant.OPTION_RESERVATION_REGISTER, areaCode);
			List<Object> params = new ArrayList<Object>();
			params.add(vo.getAppCode());
			params.add(BizConstant.OPTION_RESERVATION_REGISTER);
			params.add(areaCode);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalByOption", params);
			if (CollectionUtils.isNotEmpty(results)) {
				String source = JSON.toJSONString(results);
				hospitalInfos = JSON.parseArray(source, HospitalInfoByEasyHealthVo.class);
			}
		}

		List<CodeAndInterfaceVo> codeAndInterfaceLs = null;
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getValidCodeAndInterfaceMap", new ArrayList<Object>());

		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			codeAndInterfaceLs = new ArrayList<CodeAndInterfaceVo>(results.size());
			codeAndInterfaceLs.addAll(JSON.parseArray(source, CodeAndInterfaceVo.class));

			final List<String> transformHospitalIds = Lists.transform(codeAndInterfaceLs, new Function<CodeAndInterfaceVo, String>() {
				public String apply(CodeAndInterfaceVo codeAndInterfaceVo) {
					Preconditions.checkNotNull(codeAndInterfaceVo);
					return HospitalConstant.HOSPITAL_VALID_STATUS == codeAndInterfaceVo.getStatus() ? codeAndInterfaceVo.getHospitalId()
							: null;

				}
			});

			if (CollectionUtils.isNotEmpty(hospitalInfos)) {
				result = Collections2.filter(hospitalInfos, new Predicate<HospitalInfoByEasyHealthVo>() {
					@Override
					public boolean apply(HospitalInfoByEasyHealthVo hospitalInfoByEasyHealthVo) {

						return transformHospitalIds.contains(hospitalInfoByEasyHealthVo.getHospitalId());
					}
				});

				Ordering<HospitalInfoByEasyHealthVo> ordering = new Ordering<HospitalInfoByEasyHealthVo>() {
					@Override
					public int compare(HospitalInfoByEasyHealthVo left, HospitalInfoByEasyHealthVo right) {
						return ( left.getSortIndex() != null && right.getSortIndex() != null ) ? ( left.getSortIndex() - right
								.getSortIndex() ) : 1;
					}
				};

				result = ordering.sortedCopy(result);

			}
		}

		modelAndView.addObject(BizConstant.COMMON_ENTITY_LIST_KEY, result);

		String openId = getAppOpenId(request);
		vo.setOpenId(openId);

		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		modelAndView.addObject("nextUrl", "easyhealth/register/index");

		return modelAndView;
	}

	/**
	 * 根据后台配置 跳转到分院选择或者直接科室选择
	 * 
	 * @param vo
	 *            挂号业务公共参数传值对象
	 * @param request
	 *            用户请求信息
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView switchIndexView(RegisterParamsVo vo, HttpServletRequest request) {
		long startExecTime = System.currentTimeMillis();
		ModelAndView modelAndView = null;

		String hospitalCode = null;
		String hospitalId = null;
		String hospitalName = null;

		String appCode = vo.getAppCode();
		String appId = vo.getAppId();

		/*		if (appCode.startsWith(BizConstant.MODE_TYPE_APP)) { // 银联钱包
					hospitalCode = vo.getHospitalCode();
					hospitalId = vo.getHospitalId();
					hospitalName = vo.getHospitalName();
					//只能搜索时,医院名丢失,补全参数
					if (StringUtils.isBlank(hospitalName) && StringUtils.isNotBlank(hospitalId)) {
						HospIdAndAppSecretVo appVo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, appCode);
						if (appVo != null) {
							vo.setHospitalName(appVo.getHospName());
							hospitalName = appVo.getHospName();
						}
					}
				}*/

		hospitalCode = vo.getHospitalCode();
		hospitalId = vo.getHospitalId();
		hospitalName = vo.getHospitalName();
		//只能搜索时,医院名丢失,补全参数
		if (StringUtils.isBlank(hospitalName) && StringUtils.isNotBlank(hospitalId)) {
			HospIdAndAppSecretVo appVo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, appCode);
			if (appVo != null) {
				vo.setHospitalName(appVo.getHospName());
				hospitalName = appVo.getHospName();
			}
		}

		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(hospitalCode);
		if (logger.isDebugEnabled()) {
			logger.debug("load hospital's  ruleRegister infos by cache.", ruleRegister);
		}

		// 每个医院在后台配置中都有分院 实际中没有分院的医院都有一个编码通主院的虚拟分院
		List<BranchHospital> branchs = getBranchHospitals(hospitalCode);
		if (branchs.size() > 0) {
			BranchHospital branchHospital = branchs.get(0);
			// 多个分院 跳分院选择
			if (ruleRegister.getIsHasBranch() != null && ruleRegister.getIsHasBranch() == RuleConstant.IS_HAS_BRANCH_YES) {
				modelAndView = new ModelAndView("/easyhealth/biz/register/chooseBranchHis");
				Collections.sort(branchs);
				modelAndView.addObject("branchHospitals", branchs);
				modelAndView.addObject("nextUrl", "easyhealth/register/branchDeptes");
			} else {
				// 逻辑分院 直接跳科室选择(实际无分院)
				if (branchHospital != null) {
					String branchHospitalCode = branchHospital.getCode();
					String branchHospitalId = branchHospital.getId();
					vo.setBranchHospitalCode(branchHospitalCode);
					vo.setBranchHospitalId(branchHospitalId);
					vo.setBranchHospitalName(branchHospital.getName());
					modelAndView = branchHospitalToDepts(vo, ruleRegister);
					return modelAndView;

				} else {
					logger.error("系统配置错误,医院:{}未配置分院", hospitalName);
				}
			}
		} else {
			logger.error("系统配置错误,医院:{}未配置分院", hospitalName);
		}
		logger.info("********************cast time:{}", System.currentTimeMillis() - startExecTime);
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		return modelAndView;
	}

	/**
	 * 分院到科室
	 * 
	 * @param vo
	 * @param ruleRegister
	 * @return
	 */
	private ModelAndView branchHospitalToDepts(RegisterParamsVo vo, RuleRegister ruleRegister) {
		ModelAndView modelAndView = null;
		String hospitalCode = vo.getHospitalCode();
		String hospitalId = vo.getHospitalId();
		String branchHospitalCode = vo.getBranchHospitalCode();
		String branchHospitalId = vo.getBranchHospitalId();
		List<Dept> depts = null;
		Integer regType = vo.getRegType() == null ? null : vo.getRegType();

		List<Dept> subDepts = null;
		String activeDeptCode = null;
		RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(hospitalCode);
		HospIdAndAppSecretVo easyHealthAppInfo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, vo.getAppCode());
		if (easyHealthAppInfo != null) {
			// app一级科室
			depts = baseDatasManager.getAppLevelOneDepts(ruleHisData, hospitalCode, branchHospitalCode, String.valueOf(regType));
			if (!CollectionUtils.isEmpty(depts)) {
				activeDeptCode = depts.get(0).getDeptCode();
				subDepts =
						baseDatasManager.getAppLevelTwoDepts(ruleHisData, hospitalCode, branchHospitalCode, String.valueOf(regType),
								activeDeptCode);
			}
		} else {
			logger.error("系统配置错误,医院:{}未配置平台", vo.getHospitalName());
		}

		if (!CollectionUtils.isEmpty(depts)) {
			if (ruleRegister.getIsHasGradeTwoDept() == RuleConstant.IS_HAS_TWO_DEPT_NO) {
				// 没有2级科室
				modelAndView = new ModelAndView("/easyhealth/biz/register/chooseNoSubDept");
			} else {
				// 有2级科室
				modelAndView = new ModelAndView("/easyhealth/biz/register/chooseHasSubDept");
			}

			vo.setDeptCode(activeDeptCode);
			vo.setHospitalId(hospitalId);
			vo.setBranchHospitalCode(branchHospitalCode);
			vo.setBranchHospitalId(branchHospitalId);
			vo.setHospitalCode(hospitalCode);
			modelAndView.addObject("depts", depts);
			modelAndView.addObject("subDepts", subDepts);

			// 曾挂号医生信息
			List<HadRegDoctor> hadRegDoctors = baseDatasManager.getHadRegDoctorsByOpenId(vo.getOpenId(), hospitalCode, branchHospitalCode);
			modelAndView.addObject("hadRegDoctors", hadRegDoctors);

			// 是否显示搜索功能
			Integer isHasSearChDoctor = ruleRegister.getIsHasSearChDoctor();
			if (isHasSearChDoctor == null) {
				isHasSearChDoctor = RegisterConstant.DEF_IS_HAS_SEARCH_DOCTOR_YES;
			}
			modelAndView.addObject("isHasSearChDoctor", isHasSearChDoctor);
			modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		} else {
			modelAndView = new ModelAndView("/easyhealth/common/building");
		}
		return modelAndView;
	}

	/**
	 * 刷新科室
	 * 
	 * @param RegisterParamsVo
	 *            vo
	 * @return
	 */
	@RequestMapping(value = "/refreshDepts")
	private ModelAndView refreshDepts(RegisterParamsVo vo) {
		String hospitalCode = vo.getHospitalCode();
		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(hospitalCode);
		ModelAndView modelAndView = null;
		String hospitalId = vo.getHospitalId();
		String branchHospitalCode = vo.getBranchHospitalCode();
		String branchHospitalId = vo.getBranchHospitalId();
		List<Dept> depts = null;
		Integer regType = ( vo.getRegType() == null ? RegisterConstant.REG_TYPE_CUR : vo.getRegType() );
		List<Dept> subDepts = null;
		String activeDeptCode = null;
		RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(hospitalCode);
		HospIdAndAppSecretVo easyHealthAppInfo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, vo.getAppCode());
		if (easyHealthAppInfo != null) {
			// app一级科室
			depts = baseDatasManager.getAppLevelOneDepts(ruleHisData, hospitalCode, branchHospitalCode, String.valueOf(regType));
			if (!CollectionUtils.isEmpty(depts)) {
				activeDeptCode = depts.get(0).getDeptCode();
				subDepts =
						baseDatasManager.getAppLevelTwoDepts(ruleHisData, hospitalCode, branchHospitalCode, String.valueOf(regType),
								activeDeptCode);
			}
		} else {
			logger.error("系统配置错误,医院:{}未配置平台", vo.getHospitalName());
		}

		if (!CollectionUtils.isEmpty(depts)) {
			if (ruleRegister.getIsHasGradeTwoDept() == RuleConstant.IS_HAS_TWO_DEPT_NO) {
				// 没有2级科室
				modelAndView = new ModelAndView("/easyhealth/biz/register/chooseNoSubDept");
			} else {
				// 有2级科室
				modelAndView = new ModelAndView("/easyhealth/biz/register/chooseHasSubDept");
			}

			vo.setDeptCode(activeDeptCode);
			vo.setHospitalId(hospitalId);
			vo.setBranchHospitalCode(branchHospitalCode);
			vo.setBranchHospitalId(branchHospitalId);
			vo.setHospitalCode(hospitalCode);
			modelAndView.addObject("depts", depts);
			modelAndView.addObject("subDepts", subDepts);

			// 曾挂号医生信息
			List<HadRegDoctor> hadRegDoctors = baseDatasManager.getHadRegDoctorsByOpenId(vo.getOpenId(), hospitalCode, branchHospitalCode);
			modelAndView.addObject("hadRegDoctors", hadRegDoctors);

			// 是否显示搜索功能
			Integer isHasSearChDoctor = ruleRegister.getIsHasSearChDoctor();
			if (isHasSearChDoctor == null) {
				isHasSearChDoctor = RegisterConstant.DEF_IS_HAS_SEARCH_DOCTOR_YES;
			}
			modelAndView.addObject("isHasSearChDoctor", isHasSearChDoctor);
			modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		}
		return modelAndView;
	}

	/**
	 * 分院 选科室
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/branchDeptes")
	public ModelAndView branchDeptes(RegisterParamsVo vo) {
		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(vo.getHospitalCode());
		ModelAndView modelAndView = branchHospitalToDepts(vo, ruleRegister);
		return modelAndView;
	}

	/**
	 * 根据一级科室代码 查找2级科室
	 * 
	 * @param request
	 * @param hospitalCode
	 *            医院编码
	 * @param branchHospitalCode
	 *            分院编码
	 * @param deptCode
	 *            一级科室编码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/querySubDepts", method = RequestMethod.POST)
	public RespBody querySubDepts(HttpServletRequest request, RegisterParamsVo vo) {
		List<Dept> subDepts = null;
		String hospitalCode = vo.getHospitalCode();
		String branchCode = vo.getBranchHospitalCode();
		Integer regType = ( vo.getRegType() == null ? RegisterConstant.REG_TYPE_CUR : vo.getRegType() );
		String deptCode = vo.getDeptCode();
		String hospitalId = vo.getHospitalId();
		RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(hospitalCode);
		HospIdAndAppSecretVo hospAppInfo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, vo.getAppCode());
		if (hospAppInfo != null) {
			subDepts = baseDatasManager.getAppLevelTwoDepts(ruleHisData, hospitalCode, branchCode, regType.toString(), deptCode);
		} else {
			logger.error("系统配置错误,医院:{}未配置平台", vo.getHospitalName());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subDepts", subDepts);
		return new RespBody(Status.OK, map);
	}

	/**
	 * 
	 * 描述: TODO 跳转到分院页面
	 * 
	 * @author Caiwq
	 * @date 2015年6月5日
	 * @param appCode
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/chooseBranchHis")
	public ModelAndView chooseBranchHisModelAndView(RegisterParamsVo vo) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/register/chooseBranchHis");
		return modelAndView;
	}

	/**
	 * 根据医院编码得到所有的分院信息
	 * 
	 * @param hospitalCode
	 * @return
	 */
	private List<BranchHospital> getBranchHospitals(String hospitalCode) {
		return baseDatasManager.queryBranchsByHospitalCode(hospitalCode);
	}

	@RequestMapping(value = "/refreshInfos")
	@ResponseBody
	public RespBody refreshInfos(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "success");
		return new RespBody(Status.OK, map);
	}

	@RequestMapping(value = "/searchDoctorInfo")
	public ModelAndView searchDoctorInfo() {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/register/searchDoctorInfo");
		return modelAndView;
	}

}
