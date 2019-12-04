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
package com.yxw.platform.cachemanage.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.biz.UserConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.entity.platform.rule.RuleOnlineFiling;
import com.yxw.commons.entity.platform.rule.RulePayment;
import com.yxw.commons.entity.platform.rule.RulePush;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.entity.platform.rule.RuleTiedCard;
import com.yxw.commons.entity.platform.rule.RuleUserCenter;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.platform.Dept;
import com.yxw.commons.vo.platform.PlatformPaymentVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.interceptor.repeatrubmit.RepeatSubmitDefinition;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.service.YxwCommService;
import com.yxw.interfaces.vo.Request;
import com.yxw.interfaces.vo.Response;
import com.yxw.interfaces.vo.register.RegDept;
import com.yxw.interfaces.vo.register.RegDeptRequest;
import com.yxw.interfaces.vo.register.RegDoctor;
import com.yxw.interfaces.vo.register.RegDoctorRequest;
import com.yxw.interfaces.vo.register.RegInfo;
import com.yxw.interfaces.vo.register.RegInfoRequest;
import com.yxw.platform.cachemanage.CacheParamVo;
import com.yxw.platform.cachemanage.utils.JsonFormatTool;
import com.yxw.platform.hospital.service.BranchHospitalService;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.hospital.service.PlatformService;

/**
 * @Package: com.yxw.platform.cachemanage.controller
 * @ClassName: CacheManageController
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年9月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/cacheManage")
public class CacheManageController {
	private static Logger logger = LoggerFactory.getLogger(CacheManageController.class);
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private PlatformService platformService;

	@Autowired
	private BranchHospitalService branchHospitalService;

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * 医院缓存
	 */
	//private HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);

	/**
	 * 白名单缓存
	 */
	//private WhiteListCache whiteListCache = SpringContextHolder.getBean(WhiteListCache.class);

	/**
	 * 规则配置缓存
	 */
	//private RuleConfigManager ruleConfigCache = SpringContextHolder.getBean(RuleConfigManager.class);
	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	/**
	 * 医院医生缓存
	 */
	//private DoctorCache doctorCache = SpringContextHolder.getBean(DoctorCache.class);

	/**
	 * 医院科室缓存
	 */
	//private DeptCache deptCache = SpringContextHolder.getBean(DeptCache.class);

	/**
	 * 挂号记录 缓存
	 * 
	 * @param cacheVo
	 * @return
	 */
	//private RegisterRecordCache regRecordCache = SpringContextHolder.getBean(RegisterRecordCache.class);

	@RequestMapping(value = "/index", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(CacheParamVo cacheVo) {
		List<BranchHospital> branchs = branchHospitalService.selectBranchHospitalsByHospitalId(cacheVo.getHospitalId());
		String cacheType = cacheVo.getCacheType();
		String branchCode = cacheVo.getBranchCode();
		String hospitalCode = cacheVo.getHospitalCode();
		if (StringUtils.isBlank(cacheVo.getCacheType())) {
			cacheType = CacheConstant.CACHE_APP_CODE_KEY_PREFIX;
		}
		if (!CollectionUtils.isEmpty(branchs)) {
			if (StringUtils.isBlank(branchCode)) {
				branchCode = branchs.get(0).getCode();
			}
		}
		ModelAndView modelAndView = null;

		if (CacheConstant.CACHE_APP_CODE_KEY_PREFIX.equalsIgnoreCase(cacheType)) {
			// 接入平台配置信息Cache
			modelAndView = new ModelAndView("/sys/cacheManage/tradeInfoCache");
			appInfoCache(cacheVo, modelAndView);
		} else if (CacheConstant.CACHE_HOSPITAL_RULES_KEY_PREFIX.endsWith(cacheType)) {
			// 规则配置信息cache
			modelAndView = new ModelAndView("/sys/cacheManage/ruleCache");
			ruleCache(hospitalCode, modelAndView);
		} else if (CacheConstant.CACHE_DOCTOR_HASH_PREFIX.endsWith(cacheType)) {
			// 可挂号医生信息Cache
			modelAndView = new ModelAndView("/sys/cacheManage/doctorInfoCache");
			doctorCache(hospitalCode, branchCode, modelAndView);
		} else if (CacheConstant.SYSTEM_MANAGER_CACHE_DEPT_HASH_PREFIX.endsWith(cacheType)) {
			// 医院科室信息cache
			modelAndView = new ModelAndView("/sys/cacheManage/deptInfoCache");
			deptCache(hospitalCode, branchCode, modelAndView);
		} else if (CacheConstant.CACHE_MEDICAL_CARD_HASH_PREFIX.endsWith(cacheType)) {
			// 绑卡信息

		} else if (CacheConstant.CACHE_REGISTER_HAVING_HASH.endsWith(cacheType)) {
			// 待支付信息(必须支付)

		} else if (CacheConstant.CACHE_REGISTER_NOT_PAY_HAD_HASH.endsWith(cacheType)) {
			// 待支付信息(暂不支付 or 不支付)

		} else if (CacheConstant.CACHE_REGISTER_NEED_UNLOCK.endsWith(cacheType)) {
			// 待解锁队列Cache
			modelAndView = new ModelAndView("/sys/cacheManage/unLockRegInfoCache");
			unLockRegInfoCache(hospitalCode, branchCode, modelAndView);
		} else if (CacheConstant.CACHE_REGISTER_EXCEPTION_SORTEDSET.endsWith(cacheType)) {
			// 挂号异常队列Cache

		} else if (CacheConstant.CACHE_CLINIC_EXCEPTION_SORTEDSET.endsWith(cacheType)) {
			// 门诊异常队列Cache

		} else if (CacheConstant.CACHE_STOP_REGISTER_EXCEPTION.endsWith(cacheType)) {
			// 停诊异常队列Cache

		} else if (CacheConstant.CACHE_REGISTER_REG_SOURCE.endsWith(cacheType)) {
			// 号源信息
			modelAndView = new ModelAndView("/sys/cacheManage/regInfoCache");
			registerSourceCache(hospitalCode, branchCode, modelAndView);
		}

		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, cacheVo);
		modelAndView.addObject("branchHospitals", branchs);
		modelAndView.addObject("cacheTypeMap", CacheConstant.cacheTypeMap);

		return modelAndView;
	}

	/**
	 * 获取医院信息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param search
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("/cacheManageHospitalList")
	public ModelAndView cacheManageHospitalList(@RequestParam(required = false, defaultValue = "1") int pageNum, @RequestParam(
			required = false, defaultValue = "5") int pageSize, @RequestParam(required = false, defaultValue = "") String search,
			ModelMap modelMap, HttpServletRequest request) {
		logger.info("缓存管理医院列表分页查询,pageNum=[{}],pageSize=[{}]", new Object[] { pageNum, pageSize });
		// 获取session中保存的
		@SuppressWarnings("unchecked")
		List<Hospital> hospitalList = (List<Hospital>) request.getSession().getAttribute(UserConstant.HOSPITAL_LIST);
		PageInfo<Hospital> hospitals = null;
		if (hospitalList.size() > 0) {
			String[] hospitalIds = new String[hospitalList.size()];
			for (int i = 0; i < hospitalList.size(); i++) {
				hospitalIds[i] = hospitalList.get(i).getId();
			}

			Map<String, Object> params = new HashMap<String, Object>();
			// 设置搜索条件
			params.put("search", search);
			// 把session中保存的ID做查询条件
			params.put("hospitalIds", hospitalIds);
			hospitals = hospitalService.findListByPage(params, new Page<Hospital>(pageNum, pageSize));
		}
		modelMap.put("search", search);
		modelMap.put("hospitals", hospitals);
		return new ModelAndView("/sys/cacheManage/cacheManageHospitalList");
	}

	/**
	 * 获取分院信息
	 * 
	 * @param hospitalId
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("/cacheManageBranchHospitalList")
	public ModelAndView cacheManageBranchHospitalList(String hospitalId, ModelMap modelMap, HttpServletRequest request) {
		List<BranchHospital> branchHospitals = branchHospitalService.findByHospitalId(hospitalId);
		modelMap.put("branchHospitals", branchHospitals);
		return new ModelAndView("/sys/cacheManage/cacheManageBranchHospitalList");
	}

	/**
	 * 获取医院平台配置缓存
	 * 
	 * @param hospitalCode
	 * @param modelMap
	 * @param request
	 * @return
	 */
	public void appInfoCache(CacheParamVo cacheParamVo, ModelAndView modelAndView) {
		List<PlatformPaymentVo> paymentVos = platformService.findAllPlatformPayModesByHospitalId(cacheParamVo.getHospitalId());
		modelAndView.addObject("platforms", paymentVos);

		List<String> fields = new ArrayList<>();
		for (PlatformPaymentVo vo : paymentVos) {
			fields.add(vo.getPlatformCode().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(vo.getAppId()));
		}

		List<Object> parameters = new ArrayList<>();
		parameters.add(cacheParamVo.getHospitalCode());
		parameters.add(fields);
		List<Object> result = serveComm.get(CacheType.HOSPITAL_CACHE, "queryAppInfoByFields", parameters);
		if (!CollectionUtils.isEmpty(result)) {
			String source = JSON.toJSONString(result);
			List<HospitalCodeAndAppVo> vos = JSON.parseArray(source, HospitalCodeAndAppVo.class);
			Map<String, String> hospAndAppMap = new HashMap<>();
			for (HospitalCodeAndAppVo vo : vos) {
				String key = vo.getAppCode().concat(":").concat(vo.getPayModeCode());

				hospAndAppMap.put(key, JsonFormatTool.formatJson(JSON.toJSONString(vo)));
			}

			modelAndView.addObject("hospAndAppMap", hospAndAppMap);
			logger.info(JSON.toJSONString(hospAndAppMap));
		}
	}

	public void ruleCache(String hospitalCode, ModelAndView modelAndView) {
		// 全局规则
		//RuleEdit ruleEdit = ruleConfigCache.getRuleEditByHospitalCode(hospitalCode);
		RuleEdit ruleEdit = ruleConfigManager.getRuleEditByHospitalCode(hospitalCode);

		modelAndView.addObject(RuleConstant.RULE_TYPE_EDIT, JsonFormatTool.formatJson(JSON.toJSONString(ruleEdit)));

		// 在线建档规则
		//RuleOnlineFiling onlineFiling = ruleConfigCache.getRuleOnlineFilingByHospitalCode(hospitalCode);
		RuleOnlineFiling onlineFiling = ruleConfigManager.getRuleOnlineFilingByHospitalCode(hospitalCode);
		modelAndView.addObject(RuleConstant.RULE_TYPE_ONLINEFILING, JsonFormatTool.formatJson(JSON.toJSONString(onlineFiling)));

		//RuleTiedCard ruleTiedCard = ruleConfigCache.getRuleTiedCardByHospitalCode(hospitalCode);
		RuleTiedCard ruleTiedCard = ruleConfigManager.getRuleTiedCardByHospitalCode(hospitalCode);
		modelAndView.addObject(RuleConstant.RULE_TYPE_TIEDCARD, JsonFormatTool.formatJson(JSON.toJSONString(ruleTiedCard)));

		//RuleRegister ruleRegister = ruleConfigCache.getRuleRegisterByHospitalCode(hospitalCode);
		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(hospitalCode);
		modelAndView.addObject(RuleConstant.RULE_TYPE_REGISTER, JsonFormatTool.formatJson(JSON.toJSONString(ruleRegister)));

		// 查询规则
		//RuleQuery ruleQuery = ruleConfigCache.getRuleQueryByHospitalCode(hospitalCode);
		RuleQuery ruleQuery = ruleConfigManager.getRuleQueryByHospitalCode(hospitalCode);
		modelAndView.addObject(RuleConstant.RULE_TYPE_QUERY, JsonFormatTool.formatJson(JSON.toJSONString(ruleQuery)));

		// 缴费规则
		//RulePayment rulePayment = ruleConfigCache.getRulePaymentByHospitalCode(hospitalCode);
		RulePayment rulePayment = ruleConfigManager.getRulePaymentByHospitalCode(hospitalCode);
		modelAndView.addObject(RuleConstant.RULE_TYPE_EDIT, JsonFormatTool.formatJson(JSON.toJSONString(rulePayment)));

		// 推送规则
		//RulePush rulePushWechat = ruleConfigCache.getRulePushByHospitalCode(hospitalCode, BizConstant.MODE_TYPE_WEIXIN);
		RulePush rulePushWechat = ruleConfigManager.getRulePushByHospitalCode(hospitalCode, BizConstant.MODE_TYPE_WECHAT);
		modelAndView.addObject(RuleConstant.RULE_TYPE_PUSH.concat(":").concat(BizConstant.MODE_TYPE_WECHAT),
				JsonFormatTool.formatJson(JSON.toJSONString(rulePushWechat)));
		//RulePush rulePushAlipay = ruleConfigCache.getRulePushByHospitalCode(hospitalCode, BizConstant.MODE_TYPE_ALIPAY);
		RulePush rulePushAlipay = ruleConfigManager.getRulePushByHospitalCode(hospitalCode, BizConstant.MODE_TYPE_ALIPAY);
		modelAndView.addObject(RuleConstant.RULE_TYPE_PUSH.concat(":").concat(BizConstant.MODE_TYPE_ALIPAY),
				JsonFormatTool.formatJson(JSON.toJSONString(rulePushAlipay)));

		RulePush rulePushApp = ruleConfigManager.getRulePushByHospitalCode(hospitalCode, BizConstant.MODE_TYPE_APP);
		modelAndView.addObject(RuleConstant.RULE_TYPE_PUSH.concat(":").concat(BizConstant.MODE_TYPE_APP),
				JsonFormatTool.formatJson(JSON.toJSONString(rulePushApp)));

		RulePush rulePushInnerUnionPay = ruleConfigManager.getRulePushByHospitalCode(hospitalCode, BizConstant.MODE_TYPE_INNER_UNIONPAY);
		modelAndView.addObject(RuleConstant.RULE_TYPE_PUSH.concat(":").concat(BizConstant.MODE_TYPE_INNER_UNIONPAY),
				JsonFormatTool.formatJson(JSON.toJSONString(rulePushInnerUnionPay)));

		// 用户中心规则
		//RuleUserCenter ruleUserCenter = ruleConfigCache.getRuleUserCenterByHospitalCode(hospitalCode);
		RuleUserCenter ruleUserCenter = ruleConfigManager.getRuleUserCenterByHospitalCode(hospitalCode);
		modelAndView.addObject(RuleConstant.RULE_TYPE_USERCENTER, JsonFormatTool.formatJson(JSON.toJSONString(ruleUserCenter)));
	}

	/**
	 * 分院下所有医生json
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param modelAndView
	 */
	private void doctorCache(String hospitalCode, String branchCode, ModelAndView modelAndView) {
		Map<String, List<String>> doctorCacheTypeMap = new HashMap<String, List<String>>();

		RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(hospitalCode);
		Integer isSameDeptData = null;
		if (ruleHisData != null) {
			isSameDeptData = ruleHisData.getIsSameDeptData();
		}
		if (isSameDeptData != null && isSameDeptData.intValue() == RuleConstant.IS_SAME_DEPT_DATA_NO) {
			List<String> keys = new ArrayList<String>(2);
			keys.add("regDoctorInfos");
			doctorCacheTypeMap.put(String.valueOf(RegisterConstant.REG_TYPE_APPOINTMENT), keys);

			keys = new ArrayList<String>(2);
			keys.add("curDoctorInfos");
			doctorCacheTypeMap.put(String.valueOf(RegisterConstant.REG_TYPE_CUR), keys);
		} else {
			List<String> keys = new ArrayList<String>(2);
			keys.add("doctorInfos");
			doctorCacheTypeMap.put(null, keys);
		}

		List<Object> parameters = new ArrayList<Object>();
		for (Entry<String, List<String>> doctorCacheType : doctorCacheTypeMap.entrySet()) {
			parameters.clear();
			String regType = doctorCacheType.getKey();
			List<String> keys = doctorCacheType.getValue();
			parameters.add(hospitalCode);
			parameters.add(branchCode);
			if (StringUtils.isNotBlank(regType)) {
				parameters.add(regType);
			} else {
				parameters.add("");
			}
			List<Object> doctors = serveComm.get(CacheType.DOCTOR_CACHE, "getHospitalAllDoctors", parameters);
			modelAndView.addObject(keys.get(0), JsonFormatTool.formatJson(JSON.toJSONString(doctors)));
		}

		// 标识是否区分当天跟预约的科室数据
		modelAndView.addObject("isSameData", doctorCacheTypeMap.size() == 1 ? RuleConstant.IS_SAME_DEPT_DATA_YES
				: RuleConstant.IS_SAME_DEPT_DATA_NO);

		/*
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(hospitalCode);
		parameters.add(branchCode);
		parameters.add("");
		List<Object> doctors = serveComm.get(CacheType.DOCTOR_CACHE, "getHospitalAllDoctors", parameters);
		modelAndView.addObject("doctorInfos", JsonFormatTool.formatJson(JSON.toJSONString(doctors)));*/
	}

	/**
	 * 获取分院下所有号源信息
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param modelAndView
	 */
	private void registerSourceCache(String hospitalCode, String branchCode, ModelAndView modelAndView) {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(hospitalCode);
		parameters.add(branchCode);
		List<Object> regSource = serveComm.get(CacheType.REGISTER_SOURCE_CACHE, "getHospitalAllRegSourceFromCache", parameters);
		modelAndView.addObject("regSource", JsonFormatTool.formatJson(JSON.toJSONString(regSource)));
	}

	/**
	 * 医院待解锁挂号队列
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param modelAndView
	 */
	private void unLockRegInfoCache(String hospitalCode, String branchCode, ModelAndView modelAndView) {
		//List<SimpleRecord> records = regRecordCache.getNeedAllUnLockRegFormCache(hospitalCode);
		//modelAndView.addObject("unLockRegInfos", JsonFormatTool.formatJson(JSON.toJSONString(records)));

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(hospitalCode);
		parameters.add(branchCode);
		List<Object> records = serveComm.get(CacheType.REGISTER_RECORD_CACHE, "getNeedAllUnLockRegFormCache", parameters);
		modelAndView.addObject("unLockRegInfos", JsonFormatTool.formatJson(JSON.toJSONString(records)));
	}

	/**
	 * 分院下所有科室信息
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 * @param modelAndView
	 */
	private void deptCache(String hospitalCode, String branchCode, ModelAndView modelAndView) {

		Map<String, List<String>> deptTypeMap = new HashMap<String, List<String>>();

		RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(hospitalCode);
		Integer isSameDeptData = null;
		if (ruleHisData != null) {
			isSameDeptData = ruleHisData.getIsSameDeptData();
		}
		if (isSameDeptData != null && isSameDeptData.intValue() == RuleConstant.IS_SAME_DEPT_DATA_NO) {
			List<String> keys = new ArrayList<String>(2);
			keys.add("regLevelOneDepts");
			keys.add("regLevelTwoDepts");
			deptTypeMap.put(String.valueOf(RegisterConstant.REG_TYPE_APPOINTMENT), keys);

			keys = new ArrayList<String>(2);
			keys.add("curLevelOneDepts");
			keys.add("curLevelTwoDepts");
			deptTypeMap.put(String.valueOf(RegisterConstant.REG_TYPE_CUR), keys);
		} else {
			List<String> keys = new ArrayList<String>(2);
			keys.add("levelOneDepts");
			keys.add("levelTwoDepts");
			deptTypeMap.put(null, keys);
		}

		for (Entry<String, List<String>> deptType : deptTypeMap.entrySet()) {
			String regType = deptType.getKey();
			List<String> keys = deptType.getValue();

			List<Dept> depts = baseDatasManager.getAppLevelOneDepts(ruleHisData, hospitalCode, branchCode, regType);
			modelAndView.addObject(keys.get(0), JsonFormatTool.formatJson(JSON.toJSONString(depts)));

			List<Dept> towDepts = new LinkedList<Dept>();

			List<Dept> deptls = baseDatasManager.getAppLevelTwoDepts(ruleHisData, hospitalCode, branchCode, regType);
			if (!CollectionUtils.isEmpty(deptls)) {
				towDepts.addAll(deptls);
				modelAndView.addObject(keys.get(1), JsonFormatTool.formatJson(JSON.toJSONString(towDepts)));
			}
		}

		// 标识是否区分当天跟预约的科室数据
		modelAndView.addObject("isSameData", deptTypeMap.size() == 1 ? RuleConstant.IS_SAME_DEPT_DATA_YES
				: RuleConstant.IS_SAME_DEPT_DATA_NO);
	}

	/**
	 * 获取医院支付缓存
	 * 
	 * @param hospitalCode
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("/getWhiteCache")
	public ModelAndView getWhiteCache(String hospitalCode, ModelMap modelMap, HttpServletRequest request) {
		//HospitalCodeAndAppVo wechatPayCache = hospitalCache.queryAppInfoByHospitalCode(hospitalCode, BizConstant.MODE_TYPE_WEIXIN);
		//HospitalCodeAndAppVo alipayPayCache = hospitalCache.queryAppInfoByHospitalCode(hospitalCode, BizConstant.MODE_TYPE_ALIPAY);
		//		HospitalCodeAndAppVo wechatPayCache = baseDatasManager.getAppInfoByHospitalCode(hospitalCode, BizConstant.MODE_TYPE_WEIXIN);
		//		HospitalCodeAndAppVo alipayPayCache = baseDatasManager.getAppInfoByHospitalCode(hospitalCode, BizConstant.MODE_TYPE_ALIPAY);
		// 获取白名单所有缓存的键
		//String whiteMapKey = CacheConstant.CACHE_WHITE_LIST;
		// 获取白名单所有缓存
		//Map<String, String> whiteMap = redisSvc.hgetAll(whiteMapKey);

		//		List<Object> results = serveComm.get(CacheType.WHITE_LIST_CACHE, "hgetAllWhiteList", new ArrayList<Object>());
		//		Map<String, String> whiteMap = new HashMap<String, String>();
		//		if (CollectionUtils.isNotEmpty(results)) {
		//			whiteMap = (Map<String, String>) results.get(0);
		//		}

		// 遍历白名单缓存 获取当前医院的白名单人员缓存
		/*if (!whiteMap.isEmpty()) {
			Map<String, String> whiteWechatCache = new HashMap<String, String>();
			Map<String, String> whiteAlipayCache = new HashMap<String, String>();
			for (String key : whiteMap.keySet()) {
				// 截取缓存ID的appId及appCode段用于对比缓存ID
				String checkKey = key.substring(0, key.lastIndexOf(CacheConstant.CACHE_KEY_SPLIT_CHAR));
				if (checkKey.equalsIgnoreCase(wechatPayCache.getAppId().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
						.concat(BizConstant.MODE_TYPE_WEIXIN))) {
					whiteWechatCache.put(key, JsonFormatTool.formatJson(whiteMap.get(key)));
				} else if (checkKey.equalsIgnoreCase(alipayPayCache.getAppId().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
						.concat(BizConstant.MODE_TYPE_ALIPAY))) {
					whiteAlipayCache.put(key, JsonFormatTool.formatJson(whiteMap.get(key)));
				}
			}
			modelMap.put("whiteWechatCache", whiteWechatCache);
			modelMap.put("whiteAlipayCache", whiteAlipayCache);
		}*/
		return new ModelAndView("/sys/cacheManage/cacheWhite");
	}

	/**
	 * 获取分院缓存 包括白名单、绑卡、科室、医生、号源、挂号异常
	 * 
	 * @param branchId
	 * @param branchCode
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBranchCache")
	public ModelAndView getBranchCache(String branchId, String branchCode, ModelMap modelMap, HttpServletRequest request) {
		return new ModelAndView("/sys/cacheManage/getBranchCache");
	}

	/**
	 * 获取最新的缓存数据
	 * 
	 * @param vo
	 * @param diseaseDesc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/reLoadDataToCache", method = RequestMethod.POST)
	@RepeatSubmitDefinition(needRemoveToken = true)
	public RespBody reLoadDataToCache(CacheParamVo cacheVo) {
		String hospitalCode = cacheVo.getHospitalCode();
		String hospitalId = cacheVo.getHospitalId();
		String cacheType = cacheVo.getCacheType();
		String branchCode = cacheVo.getBranchCode();
		String regType = cacheVo.getRegType();
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (CacheConstant.CACHE_APP_CODE_KEY_PREFIX.equalsIgnoreCase(cacheType)) {
			// 接入平台配置信息Cache
			reLoadAppInfoToCache(hospitalId, hospitalCode, branchCode);
		} else if (CacheConstant.CACHE_HOSPITAL_RULES_KEY_PREFIX.endsWith(cacheType)) {
			// 规则配置信息cache
			reLoadRuleInfoToCache(hospitalCode, branchCode);
		} else if (CacheConstant.CACHE_DOCTOR_HASH_PREFIX.endsWith(cacheType)) {
			// 可挂号医生信息Cache
			resMap = reLoadDoctorInfoToCache(hospitalCode, branchCode, regType);
		} else if (CacheConstant.SYSTEM_MANAGER_CACHE_DEPT_HASH_PREFIX.endsWith(cacheType)) {
			// 医院科室信息cache
			resMap = reLoadDeptInfoToCache(cacheVo);
		} else if (CacheConstant.CACHE_MEDICAL_CARD_HASH_PREFIX.endsWith(cacheType)) {
			// 绑卡信息

		} else if (CacheConstant.CACHE_REGISTER_HAVING_HASH.endsWith(cacheType)) {
			// 待支付信息(必须支付)

		} else if (CacheConstant.CACHE_REGISTER_NOT_PAY_HAD_HASH.endsWith(cacheType)) {
			// 待支付信息(暂不支付 or 不支付)

		} else if (CacheConstant.CACHE_REGISTER_NEED_UNLOCK.endsWith(cacheType)) {
			// 待解锁队列Cache

			//		} else if (CacheConstant.CACHE_REGISTER_EXCEPTION.endsWith(cacheType)) {
		} else if (CacheConstant.CACHE_REGISTER_EXCEPTION_SORTEDSET.endsWith(cacheType)) {
			// 挂号异常队列Cache

			//		} else if (CacheConstant.CACHE_CLINIC_EXCEPTION.endsWith(cacheType)) {
		} else if (CacheConstant.CACHE_CLINIC_EXCEPTION_SORTEDSET.endsWith(cacheType)) {
			// 门诊异常队列Cache

		} else if (CacheConstant.CACHE_STOP_REGISTER_EXCEPTION.endsWith(cacheType)) {
			// 停诊异常队列Cache

		} else if (CacheConstant.CACHE_REGISTER_REG_SOURCE.endsWith(cacheType)) {
			// 号源信息
			resMap = reLoadRegisterSourceToCache(hospitalId, hospitalCode, branchCode);
		}

		resMap.put(BizConstant.COMMON_PARAMS_KEY, cacheVo);
		return new RespBody(Status.OK, resMap);
	}

	/**
	 * 重新加载平台配置信息到缓存
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 */
	private void reLoadAppInfoToCache(String hospitalId, String hospitalCode, String branchCode) {
		//List<String> oldAppIdAndAppCodes = hospitalCache.findAppInfoCacheFields(hospitalId);
		//hospitalCache.updateAppCacheByHospitalId(hospitalId, oldAppIdAndAppCodes);

		List<HospIdAndAppSecretVo> vos = hospitalService.findAppSecretByHospitalId(hospitalId);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(vos);
		serveComm.set(CacheType.HOSPITAL_CACHE, "updateAppCacheByHospitalId", parameters);
	}

	/**
	 * 重新加载规则配置到缓存
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 */
	private void reLoadRuleInfoToCache(String hospitalCode, String branchCode) {

	}

	/**
	 * 重新获取his的号源信息到缓存
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 */
	private Map<String, Object> reLoadRegisterSourceToCache(String hospitalId, String hospitalCode, String branchCode) {
		/*Map<String, Object> resMap = new HashMap<String, Object>();
		//String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = baseDatasManager.getInterfaceId(hospitalCode, branchCode);
		
		if (StringUtils.isBlank(interfaceId)) {
		    resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
		    resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,该分院未配置serviceId,请检查分院设置!");
		} else {
			YxwService yxwService = SpringContextHolder.getBean(interfaceId);
		    // 查询号源
		    Response regSourceResp = null;
		    try {
		    	//判断是否是健康易 接入医院  如果是,需传入当班辨识(查询)
				String appId = "";
				PlatformSettingsService platformSettingsService = SpringContextHolder.getBean(PlatformSettingsService.class);
				PlatformSettings platformSettings = platformSettingsService.findByHospitalIdAndAppCode(hospitalId, BizConstant.MODE_TYPE_APP);
				if (platformSettings != null && StringUtils.isNotBlank(platformSettings.getAppId())) {
					appId = platformSettings.getAppId();
				} 
				
				List<Object> params = new ArrayList<Object>();
				params.add(hospitalId);
				params.add(appId);
				List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getEasyHealthAppByHospitalId", params);
				
				boolean isEasyHealth = false;
				if (CollectionUtils.isNotEmpty(results)) {
					HospIdAndAppSecretVo vo = (HospIdAndAppSecretVo) results.get(0);
					if (vo != null) {
						isEasyHealth = true;
					}
			
					RegInfoRequest regInfoRequest = new RegInfoRequest();
		
					if (isEasyHealth) {
						regInfoRequest.setReservedFieldOne(String.valueOf(RegisterConstant.REG_TYPE_CUR));
					}
					regInfoRequest.setBranchCode(branchCode);
					Calendar start = Calendar.getInstance();
					regInfoRequest.setBeginDate(df.format(start.getTime()));
		
					Integer sourceCacheDays = null;
					RuleConfigManager ruleManager = SpringContextHolder.getBean(RuleConfigManager.class);
					RuleRegister ruleRegister = ruleManager.getRuleRegisterByHospitalCode(hospitalCode);
					if (ruleRegister != null) {
						sourceCacheDays = ruleRegister.getSourceCacheDays();
					}
					if (sourceCacheDays == null) {
						sourceCacheDays = 0;
					}
		
					List<RegInfo> infos = null;
					if (sourceCacheDays > 0 || isEasyHealth) {
						if (isEasyHealth) {
							//健康易强制只缓存当班号源
							regInfoRequest.setEndDate(regInfoRequest.getBeginDate());
						} else {
							Calendar end = Calendar.getInstance();
							end.add(Calendar.DAY_OF_MONTH, sourceCacheDays - 1);
							regInfoRequest.setEndDate(df.format(end.getTime()));
						}
					}
		    	
					regSourceResp = yxwService.getRegInfo(regInfoRequest);
				}
		    	
		    	
				List<RegInfo> regSources = (List<RegInfo>) regSourceResp.getResult();
		        if (!CollectionUtils.isEmpty(regSources)) {
		        	//regSourceCache.setRegSourceToCache(hospitalCode, branchCode, regSources);
		        	
		        	Hospital hospital = hospitalService.findById(hospitalId);
		        	
		        	params.clear();
					params.add(hospital.getAreaCode());
					params.add(hospitalId);
					params.add(hospital.getName());
					params.add(hospitalCode);
					params.add(branchCode);
					params.add(regSources);
					params.add(isEasyHealth);
					serveComm.set(CacheType.REGISTER_SOURCE_CACHE, "setRegSourceToCache", params);
		        	
		        	resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
		            resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作成功,医院号源信息已更新!");
		        } else {
		        	if (logger.isDebugEnabled()) {
		        		logger.debug("interface invoke succeed, but no datas returns! interfaceId:{}", interfaceId);
		        	}
		            resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
		            resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,获取his最新号源信息失败,稍后再试!");
		        }
		    } catch (Exception e) {
		    	if (logger.isDebugEnabled()) {
		    		logger.debug("interface invoke is error! interfaceId:{}", interfaceId);
		    	}
		    	resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
		        resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,获取his最新号源信息失败,稍后再试!");
		        
		        return resMap;
		    }
		    
		}
		
		return resMap;*/

		Map<String, Object> resMap = new HashMap<String, Object>();
		String interfaceId = baseDatasManager.getInterfaceId(hospitalCode, branchCode);

		if (StringUtils.isBlank(interfaceId)) {
			resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
			resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,该分院未配置serviceId,请检查分院设置!");
		} else {
			Map<String, Object> buildRegInfoMap = buildRegInfoRequest(hospitalCode, branchCode);
			//			YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
			// 查询号源
			Response regSourceResp = null;
			try {
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("getRegInfo");
				request.setInnerRequest(buildRegInfoMap.get("regInfoRequest"));

				//				regSourceResp = yxwService.getRegInfo(buildRegInfoRequest(hospitalCode, branchCode));
				regSourceResp = yxwCommService.invoke(request);
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug("interface invoke is error! interfaceId:{}", interfaceId);
				}
				resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
				resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,获取his最新号源信息失败,稍后再试!");

				return resMap;
			}

			List<RegInfo> regSources = (List<RegInfo>) regSourceResp.getResult();
			if (!CollectionUtils.isEmpty(regSources)) {
				//            	regSourceCache.setRegSourceToCache(hospitalCode, branchCode, regSources);
				Hospital hospital = hospitalService.findById(hospitalId);

				List<Object> params = new ArrayList<Object>();
				params.add(hospital.getAreaCode());
				params.add(hospitalId);
				params.add(hospital.getName());
				params.add(hospitalCode);
				params.add(branchCode);
				params.add((String) buildRegInfoMap.get("regType"));
				params.add(regSources);
				serveComm.set(CacheType.REGISTER_SOURCE_CACHE, "setRegSourceToCache", params);

				resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
				resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作成功,医院号源信息已更新!");
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("interface invoke succeed, but no datas returns! interfaceId:{}", interfaceId);
				}
				resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
				resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,获取his最新号源信息失败,稍后再试!");
			}
		}

		return resMap;

	}

	public Map<String, Object> buildRegInfoRequest(String hospitalCode, String branchCode) {
		Map<String, Object> buildRegInfoMap = new HashMap<String, Object>();

		RegInfoRequest regInfoRequest = new RegInfoRequest();
		regInfoRequest.setBranchCode(branchCode);
		Calendar start = Calendar.getInstance();
		regInfoRequest.setBeginDate(BizConstant.YYYYMMDD.format(start.getTime()));

		Integer sourceCacheDays = null;
		RuleConfigManager ruleManager = SpringContextHolder.getBean(RuleConfigManager.class);
		RuleRegister ruleRegister = ruleManager.getRuleRegisterByHospitalCode(hospitalCode);
		if (ruleRegister != null) {
			sourceCacheDays = ruleRegister.getSourceCacheDays();
		}
		if (sourceCacheDays == null) {
			sourceCacheDays = 7;
		}

		RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(hospitalCode);

		Integer isHadCurRegSourceInterface = null;
		Integer isHadAppointmentRegSourceInterface = null;
		if (ruleHisData != null) {
			//是否有当班号源缓存接口
			isHadCurRegSourceInterface = ruleHisData.getIsHadCurRegSourceInterface();
			if (isHadCurRegSourceInterface == null) {
				isHadCurRegSourceInterface = RuleConstant.IS_HAD_INTERFACE_YES;
			}
			//是否有预约号源缓存接口
			isHadAppointmentRegSourceInterface = ruleHisData.getIsHadAppointmentRegSourceInterface();
			if (isHadAppointmentRegSourceInterface == null) {
				isHadAppointmentRegSourceInterface = RuleConstant.IS_HAD_INTERFACE_YES;
			}
		} else {
			isHadCurRegSourceInterface = RuleConstant.IS_HAD_INTERFACE_YES;
			isHadAppointmentRegSourceInterface = RuleConstant.IS_HAD_INTERFACE_YES;
		}

		if (sourceCacheDays > 0) {
			if (isHadCurRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_YES
					&& isHadAppointmentRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_YES) {
				//根据缓存天数配置,缓存号源
				Calendar end = Calendar.getInstance();
				end.add(Calendar.DAY_OF_MONTH, sourceCacheDays - 1);
				regInfoRequest.setEndDate(BizConstant.YYYYMMDD.format(end.getTime()));
				buildRegInfoMap.put("regType", String.valueOf(RegisterConstant.REG_TYPE_APPOINTMENT));
			} else if (isHadCurRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_YES
					&& isHadAppointmentRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_NO) {
				//只缓存当班号源
				regInfoRequest.setEndDate(regInfoRequest.getBeginDate());
				buildRegInfoMap.put("regType", String.valueOf(RegisterConstant.REG_TYPE_CUR));
			} else if (isHadCurRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_NO
					&& isHadAppointmentRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_YES) {
				//只缓存预约号源

			} else if (isHadCurRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_NO
					&& isHadAppointmentRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_NO) {
				//不缓存号源
				regInfoRequest.setEndDate(null);
			}
		} else {
			regInfoRequest.setEndDate(null);
		}
		buildRegInfoMap.put("regInfoRequest", regInfoRequest);
		return buildRegInfoMap;
	}

	/**
	 * 重新获取his的科室信息到缓存
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 */
	private Map<String, Object> reLoadDeptInfoToCache(CacheParamVo cacheVo) {
		String hospitalCode = cacheVo.getHospitalCode();
		String branchCode = cacheVo.getBranchCode();
		String regType = cacheVo.getRegType();
		String hospitalId = cacheVo.getHospitalId();
		String hospitalName = cacheVo.getHospitalName();

		Map<String, Object> resMap = new HashMap<String, Object>();
		// String interfaceId = hospitalCache.getInterfaceId(hospitalCode, branchCode);
		String interfaceId = baseDatasManager.getInterfaceId(hospitalCode, branchCode);
		CodeAndInterfaceVo codeAndInterfaceVo = baseDatasManager.getCodeAndInterfaceVo(hospitalCode, branchCode);
		if (StringUtils.isBlank(interfaceId)) {
			resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
			resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,该分院未配置serviceId,请检查分院设置!");
		} else {
			//			YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");

			//			if (yxwService == null) {
			if (yxwCommService == null) {
				resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
				resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,该分院配置的serviceId错误,注册中心未找到该服务,请检查分院设置!");
			} else {
				// 查询可挂号科室
				Response deptResp = null;
				RegDeptRequest deptRequest = new RegDeptRequest();
				deptRequest.setBranchCode(branchCode);
				deptRequest.setParentDeptCode("");

				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("getRegDepts");
				request.setInnerRequest(deptRequest);
				try {
					//					deptResp = yxwService.getRegDepts(deptRequest);
					deptResp = yxwCommService.invoke(request);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
					resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,调用医院获取可挂号科室服务网络异常,稍后再试!");
				}

				List<RegDept> regDepts = (List<RegDept>) deptResp.getResult();
				if (logger.isDebugEnabled()) {
					if (CollectionUtils.isEmpty(regDepts)) {
						logger.debug("interface invoke is error! interfaceId:{}", interfaceId);
					} else {
						logger.debug("interfaceId:{} ,regDepts :{} ", new Object[] { interfaceId, regDepts.size() });
					}

				}
				// 主科室 与 子科室封装 目前只考虑2级
				List<Dept> parentDepts = new ArrayList<Dept>();
				ConcurrentHashMap<String, List<Dept>> subDepts = new ConcurrentHashMap<String, List<Dept>>();
				// 存储2级科室时的key前缀
				String keyPrefix = hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(branchCode);
				if (StringUtils.isNotEmpty(regType)) {
					keyPrefix = keyPrefix.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(regType);
				}

				//医院所有科室名称的缓存,以实现科室搜索功能
				String nameCacheKey = "";
				// app所有医院科室名称的缓存,以实现科室搜索功能 固定常量 + 区域code + 当班/预约.
				List<Object> params = new ArrayList<Object>();
				if (StringUtils.isNotBlank(regType)) {
					params.add(regType);
				} else {
					params.add("");
				}
				//		params.add(BizConstant.MODE_TYPE_APP);//去掉appCode，所有平台都用一份
				params.add(codeAndInterfaceVo.getAreaCode());
				List<Object> results = serveComm.get(CacheType.DEPT_CACHE, "getTwoInterfaceDeptNameCacheKey", params);

				if (!CollectionUtils.isEmpty(results)) {
					nameCacheKey = (String) results.get(0);
				}

				StringBuilder nameFieldPrefixSb = new StringBuilder();
				nameFieldPrefixSb.append("(").append(hospitalName);
				if (String.valueOf(RegisterConstant.REG_TYPE_CUR).equalsIgnoreCase(regType)) {
					nameFieldPrefixSb.append("[" + RegisterConstant.REG_TYPE_CUR_CHINESE + "]");
				} else {
					nameFieldPrefixSb.append("[" + RegisterConstant.REG_TYPE_APPOINTMENT_CHINESE + "]");
				}
				nameFieldPrefixSb.append(")");
				String nameFieldPrefix = nameFieldPrefixSb.toString();

				Map<String, String> deptNameJsonMap = new HashMap<String, String>();

				if (!CollectionUtils.isEmpty(regDepts)) {
					for (RegDept regDept : regDepts) {
						// 去除无科室编码的无用数据
						if (StringUtils.isEmpty(regDept.getDeptCode())) {
							continue;
						}
						Dept dept = new Dept(regDept);
						dept.setBranchHospitalCode(branchCode);
						dept.setHospitalCode(hospitalCode);
						dept.setHospitalId(hospitalId);
						dept.setHospitalName(hospitalName);

						// parentDeptCode 为 null 或者为 0 为一级科室
						boolean isParent = CacheConstant.CACHE_PARENT_DEPT_CODE.equalsIgnoreCase(regDept.getParentDeptCode());
						if (StringUtils.isEmpty(regDept.getParentDeptCode()) || isParent) {
							parentDepts.add(dept);
						} else {
							String subDeptsKey = keyPrefix + CacheConstant.CACHE_KEY_SPLIT_CHAR + regDept.getParentDeptCode().trim();
							List<Dept> depts = subDepts.get(subDeptsKey);
							boolean isNeedPut = false;
							if (CollectionUtils.isEmpty(depts)) {
								depts = new ArrayList<Dept>();
								isNeedPut = true;
							}
							depts.add(dept);
							if (isNeedPut) {
								subDepts.put(subDeptsKey, depts);
							}
						}

						deptNameJsonMap.put(dept.getDeptName().concat(nameFieldPrefix), JSON.toJSONString(dept));
					}
					if (parentDepts.size() > 0) {
						// deptCache.setLevelOneDepts(hospitalCode, branchCode, parentDepts);

						List<Object> parameters = new ArrayList<Object>();
						parameters.add(hospitalCode);
						parameters.add(branchCode);
						if (StringUtils.isNotBlank(regType)) {
							parameters.add(regType);
						} else {
							parameters.add("");
						}
						parameters.add(parentDepts);
						serveComm.set(CacheType.DEPT_CACHE, "setAppLevelOneDepts", parameters);
					}
					if (subDepts.size() > 0) {
						// deptCache.setLevelTwoDepts(hospitalCode, branchCode, subDepts);

						List<Object> parameters = new ArrayList<Object>();
						parameters.add(hospitalCode);
						parameters.add(branchCode);
						parameters.add(subDepts);
						serveComm.set(CacheType.DEPT_CACHE, "setAppLevelTwoDepts", parameters);
					}

					if (!CollectionUtils.isEmpty(deptNameJsonMap)) {
						//				deptCache.setDeptNameCache(deptNameJsonMap);
						List<Object> parameters = new ArrayList<Object>();
						parameters.add(nameCacheKey.replace("*", ""));//去掉通配符
						parameters.add(deptNameJsonMap);
						serveComm.set(CacheType.DEPT_CACHE, "setAppDeptNames", parameters);
					}

					resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
					resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作成功,医院可挂号科室信息已更新!");
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("interface invoke succeed, but no datas returns! interfaceId:{}", interfaceId);
					}
					resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
					resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,调用医院获取可挂号科室无数据返回,稍后再试!");
				}

			}

		}
		return resMap;
	}

	/**
	 * 重新获取his的可挂号医生信息到缓存
	 * 
	 * @param hospitalCode
	 * @param branchCode
	 */
	private Map<String, Object> reLoadDoctorInfoToCache(String hospitalCode, String branchCode, String regType) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		String interfaceId = baseDatasManager.getInterfaceId(hospitalCode, branchCode);

		if (StringUtils.isBlank(interfaceId)) {
			resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
			resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,该分院未配置serviceId,请检查分院设置!");
		} else {
			RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(hospitalCode);
			Integer hadDoctorInterface = null;
			if (String.valueOf(RegisterConstant.REG_TYPE_APPOINTMENT).equals(regType)) {
				hadDoctorInterface = ruleHisData.getIsHadAppointmentDoctorInterface();
			} else if (String.valueOf(RegisterConstant.REG_TYPE_CUR).equals(regType)) {
				hadDoctorInterface = ruleHisData.getIsHadCurDoctorInterface();
			}

			if (hadDoctorInterface == null || hadDoctorInterface != RuleConstant.IS_HAD_INTERFACE_NO) {
				//				YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				// 查询可挂号医生
				Response doctorResp = null;
				try {
					RegDoctorRequest regDoctorRequest = new RegDoctorRequest();
					regDoctorRequest.setBranchCode(branchCode);
					if (StringUtils.isNotBlank(regType)) {
						regDoctorRequest.setReservedFieldOne(regType);
					}
					Request request = new Request();
					request.setServiceId(interfaceId);
					request.setMethodName("getRegDoctors");
					request.setInnerRequest(regDoctorRequest);

					//					doctorResp = yxwService.getRegDoctors(regDoctorRequest);
					doctorResp = yxwCommService.invoke(request);
				} catch (Exception e) {
					if (logger.isDebugEnabled()) {
						logger.debug("interface invoke is error! interfaceId:{}", interfaceId);
					}
					resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
					resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,获取his最新医生信息失败,稍后再试!");

					return resMap;
				}

				List<RegDoctor> regDoctors = (List<RegDoctor>) doctorResp.getResult();
				if (!CollectionUtils.isEmpty(regDoctors)) {
					if (logger.isDebugEnabled()) {
						logger.debug("interfaceId:{} ,regDoctors :{} ", new Object[] { interfaceId, regDoctors.size() });
					}

					// 医院的所有医生
					List<RegDoctor> doctors = new ArrayList<RegDoctor>();
					List<RegDoctor> noDeptNameDoctors = new ArrayList<RegDoctor>();
					List<RegDoctor> noCodeDoctors = new ArrayList<RegDoctor>();
					Map<String, List<RegDoctor>> noDeptNameDoctorMap = new HashMap<String, List<RegDoctor>>();

					for (RegDoctor regDoctor : regDoctors) {
						if (regDoctor == null) {
							continue;
						}
						// 去除无DoctorCode的无用数据
						if (StringUtils.isEmpty(regDoctor.getDoctorCode())) {
							noCodeDoctors.add(regDoctor);
							continue;
						}
						/*if (StringUtils.isBlank(regDoctor.getDeptName())) {
							noDeptNameDoctors.add(regDoctor);
							continue;
						}*/
						if (StringUtils.isBlank(regDoctor.getDeptName())) {
							if (StringUtils.isNotBlank(regDoctor.getDeptCode())) {
								List<RegDoctor> noNameDoctors = noDeptNameDoctorMap.get(regDoctor.getDeptCode());
								boolean isNeedPut = false;
								if (noNameDoctors == null) {
									noNameDoctors = new ArrayList<RegDoctor>();
									isNeedPut = true;
								}
								noNameDoctors.add(regDoctor);
								if (isNeedPut) {
									noDeptNameDoctorMap.put(regDoctor.getDeptCode(), noNameDoctors);
								}
								continue;
							}
						}

						doctors.add(regDoctor);
					}

					// 将无部门名称的医生 在科室缓存中查找科室名称
					if (!noDeptNameDoctorMap.isEmpty()) {
						//deptCache.addDoctorDeptName(hospitalCode, branchCode, noDeptNameDoctorMap);
						RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(hospitalCode);
						if (ruleRegister.getIsHasGradeTwoDept() == RuleConstant.IS_HAS_TWO_DEPT_NO) {
							List<Dept> appLevelOneDepts =
									baseDatasManager.getAppLevelOneDepts(ruleHisData, hospitalCode, branchCode, regType);
							for (Dept dept : appLevelOneDepts) {
								noDeptNameDoctors = noDeptNameDoctorMap.get(dept.getDeptCode());
								if (!CollectionUtils.isEmpty(noDeptNameDoctors)) {
									for (RegDoctor doctor : noDeptNameDoctors) {
										doctor.setDeptName(dept.getDeptName());
									}
								}
							}

							for (List<RegDoctor> noNameDoctors : noDeptNameDoctorMap.values()) {
								doctors.addAll(noNameDoctors);
							}
						} else if (ruleRegister.getIsHasGradeTwoDept() == RuleConstant.IS_HAS_TWO_DEPT_YES) {
							List<Dept> appLevelOneDepts =
									baseDatasManager.getAppLevelTwoDepts(ruleHisData, hospitalCode, branchCode, regType);
							for (Dept dept : appLevelOneDepts) {
								noDeptNameDoctors = noDeptNameDoctorMap.get(dept.getDeptCode());
								if (!CollectionUtils.isEmpty(noDeptNameDoctors)) {
									for (RegDoctor doctor : noDeptNameDoctors) {
										doctor.setDeptName(dept.getDeptName());
									}
								}
							}

							for (List<RegDoctor> noNameDoctors : noDeptNameDoctorMap.values()) {
								doctors.addAll(noNameDoctors);
							}
						}

					}

					if (!CollectionUtils.isEmpty(doctors)) {
						//        				doctorCache.setDoctors(hospitalCode, branchCode, regType, doctors);
						List<Object> parameters = new ArrayList<Object>();
						parameters.add(hospitalCode);
						parameters.add(branchCode);
						parameters.add(regType);
						parameters.add(doctors);
						serveComm.set(CacheType.DOCTOR_CACHE, "setDoctors", parameters);
					}

					resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
					resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作成功,医院可挂号医生信息已更新!");
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("interface invoke succeed, but no datas returns! interfaceId:{}", interfaceId);
					}
					resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
					resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,获取his最新医生信息失败,稍后再试!");
				}
			} else {
				resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
				if (String.valueOf(RegisterConstant.REG_TYPE_APPOINTMENT).equals(regType)) {
					resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,该分院无预约可挂号医生缓存接口!");
				} else if (String.valueOf(RegisterConstant.REG_TYPE_CUR).equals(regType)) {
					resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "操作失败,该分院无当班可挂号医生缓存接口!");
				}
			}
		}

		return resMap;
	}
}
