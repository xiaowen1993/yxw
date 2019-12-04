package com.yxw.easyhealth.biz.clinic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.commons.entity.platform.rule.RuleClinic;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.commons.vo.mobile.biz.ClinicPayVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.easyhealth.biz.datastorage.clinic.thread.SaveDataMZFeeRunnable;
import com.yxw.easyhealth.biz.usercenter.service.FamilyService;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.vo.clinicpay.MZFee;
import com.yxw.interfaces.vo.mycenter.MZPatient;
import com.yxw.mobileapp.datas.manager.ClinicBizManager;
import com.yxw.mobileapp.datas.manager.MedicalcardBizManager;

/**
 * 门诊未支付信息模块
 * 功能概要：
 * @author Administrator
 * @date 2017年3月24日
 */
@Controller
@RequestMapping("app/clinic/pay")
public class ClinicPayController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(ClinicPayController.class);
	private final static String HOSPITAL_LIST_KEY = "hospitals";
	private final static String FAMILY_LIST_KEY = "families";
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);
	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
	private FamilyService familyService = SpringContextHolder.getBean(FamilyService.class);
	private ClinicBizManager clinicBizManager = SpringContextHolder.getBean(ClinicBizManager.class);
	private final static String FORWARD_URL = "/easyhealth/clinic/payIndex";

	/**
	 * 正常请求流程，进入待缴费列表
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView toPayIndex(ClinicPayVo vo, HttpServletRequest request) {
		if (StringUtils.isBlank(vo.getOpenId()) || vo.getOpenId().equals("null")) {
			vo.setOpenId(getOpenId(request));
		}

		ModelAndView view = new ModelAndView("/easyhealth/biz/clinic/payIndex");

		// 加载家人信息
		List<Family> families = familyService.findAllFamilies(vo.getOpenId());

		if (CollectionUtils.isEmpty(families)) {
			// 加本人
			familyService.saveFamilyInfo(this.getEasyHealthUser(request));
			families = familyService.findAllFamilies(vo.getOpenId());
			view.addObject(FAMILY_LIST_KEY, families);
		} else {
			view.addObject(FAMILY_LIST_KEY, families);
		}

		// 加载医院信息
		List<HospitalInfoByEasyHealthVo> hospitals = null;
		List<Object> params = new ArrayList<Object>();
		params.add(vo.getAppCode());
		params.add(BizConstant.OPTION_CLINIC_PAY);
		params.add(vo.getAreaCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalByOption", params);
		if (!CollectionUtils.isEmpty(results)) {
			String source = JSON.toJSONString(results);
			hospitals = JSON.parseArray(source, HospitalInfoByEasyHealthVo.class);
		}

		for (int i = hospitals.size() - 1; i >= 0; i--) {
			HospitalInfoByEasyHealthVo hospitalInfoByEasyHealthVo = hospitals.get(i);
			params.clear();
			params.add(hospitalInfoByEasyHealthVo.getHospitalCode());
			List<Object> result = serveComm.get(CacheType.HOSPITAL_CACHE, "getDefCodeAndInterfaceVo", params);
			if (!CollectionUtils.isEmpty(result)) {
				CodeAndInterfaceVo codeAndInterfaceVo = (CodeAndInterfaceVo) result.get(0);

				if (codeAndInterfaceVo.getStatus() == 0) {
					hospitals.remove(i);
				}
			}
		}

		view.addObject(HOSPITAL_LIST_KEY, hospitals);

		view.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		view.addObject(BizConstant.URL_PARAM_FORWARD_NAME, FORWARD_URL);
		return view;
	}

	/**
	 * 消息推送流程进入代缴费流程
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/msgIndex")
	public ModelAndView toPayIndexFromMsg(ClinicPayVo vo, HttpServletRequest request) {
		ModelAndView view = null;

		if (StringUtils.isBlank(vo.getOpenId()) || vo.getOpenId().equals("null")) {
			vo.setOpenId(getOpenId(request));
		}

		List<HospIdAndAppSecretVo> list = baseDatasManager.getHospitalListByAppCode(vo.getAppCode(), vo.getAreaCode());
		try {
			for (int i = 0; i < list.size(); i++) {
				HospIdAndAppSecretVo hospVo = list.get(i);
				// 找下这个医院是否支持门诊缴费供能
				logger.info("appId=" + hospVo.getAppId());
				if (hospVo.getAppId().equals(vo.getAppId())) {
					vo.setHospitalCode(hospVo.getHospCode());
					vo.setHospitalId(hospVo.getHospId());
					vo.setHospitalName(hospVo.getHospName());
					vo.setAreaCode(hospVo.getAreaCode());
				}
			}

			// 如果没找到医院，则跳转空页面，提示用户该医院没有门诊缴费供能
			if (StringUtils.isBlank(vo.getHospitalCode())) {
				view = new ModelAndView("easyhealth/common/error");
				view.addObject("msg", "无效的待缴费通知链接。该医院暂不支持门诊缴费。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("加载待缴费记录基础数据错误, errorMsg: {}, cause by: {}", new Object[] { e.getMessage(), e.getCause() });
		}

		view = new ModelAndView("/easyhealth/biz/clinic/payIndex");

		// 加载家人信息
		List<Family> families = familyService.findAllFamilies(vo.getOpenId());
		view.addObject(FAMILY_LIST_KEY, families);

		// 加载医院信息
		List<HospitalInfoByEasyHealthVo> hospitals = null;
		List<Object> params = new ArrayList<Object>();
		params.add(vo.getAppCode());
		params.add(BizConstant.OPTION_CLINIC_PAY);
		params.add(vo.getAreaCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalByOption", params);
		if (!CollectionUtils.isEmpty(results)) {
			String source = JSON.toJSONString(results);
			hospitals = JSON.parseArray(source, HospitalInfoByEasyHealthVo.class);
		}

		for (int i = hospitals.size() - 1; i >= 0; i--) {
			HospitalInfoByEasyHealthVo hospitalInfoByEasyHealthVo = hospitals.get(i);
			params.clear();
			params.add(hospitalInfoByEasyHealthVo.getHospitalCode());
			List<Object> result = serveComm.get(CacheType.HOSPITAL_CACHE, "getDefCodeAndInterfaceVo", params);
			if (!CollectionUtils.isEmpty(result)) {
				CodeAndInterfaceVo codeAndInterfaceVo = (CodeAndInterfaceVo) result.get(0);

				if (codeAndInterfaceVo.getStatus() == 0) {
					hospitals.remove(i);
				}
			}
		}

		view.addObject(HOSPITAL_LIST_KEY, hospitals);

		view.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		view.addObject(BizConstant.URL_PARAM_FORWARD_NAME, FORWARD_URL);
		return view;

	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "getDatas", method = RequestMethod.POST)
	public Object getDatas(ClinicPayVo vo) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, String> params = new HashMap<>();
		params.putAll(JSON.parseObject(JSON.toJSONString(vo), Map.class));
		params.put("patCardType", vo.getCardType());
		params.put("patCardNo", vo.getCardNo());
		List<MZFee> list = clinicBizManager.getMZFeeList(params);
		
		resultMap.put("entities", list);

		// 规则
		RuleClinic rule = ruleConfigManager.getRuleClinicByHospitalCode(vo.getHospitalCode());
		resultMap.put(BizConstant.RULE_CONFIG_PARAMS_KEY, rule.getOutpatientServicePayTips());

		// 正式上线的时候再开启数据存储，这块要重新调整下才行。
		
		 Thread saveDataThread = new Thread(new SaveDataMZFeeRunnable(list, vo));
		 saveDataThread.start();
		 

		return new RespBody(Status.OK, resultMap);
	}

	/**
	 * 新的门诊缴费入口 -- 扫码支付
	 * 为用户提供便利，不进行绑卡操作
	 * -- 拦截器放行？放毛,都有appCode的
	 * -- 只能放，有appCode没毛用, 没areaCode, 要通过appId去获取医院areaCode信息
	 * -- 需要这个医院，支持这个功能才行吧。
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/payIndexInScanCode")
	public ModelAndView toPayViewInScanCode(HttpServletRequest request) {

		ModelAndView modelAndView = null;
		MedicalCard medicalCard = new MedicalCard();

		String branchCode = request.getParameter("branchCode"); //分院代码
		String patCardType = request.getParameter("patCardType"); //诊疗卡类型
		String patCardNo = request.getParameter("patCardNo"); //诊疗卡号
		String patMobile = request.getParameter("patMobile"); //手机号码
		//String mzFeeId = request.getParameter("mzFeeId");			//缴费项唯一标识
		String patName = request.getParameter("patName"); //患者姓名
		String appId = (String) request.getAttribute("appId");
		String appCode = (String) request.getAttribute("appCode");

		// ??
		String openId = (String) request.getSession().getAttribute(appId + BizConstant.OPENID);

		String idCardNo = "";

		/************************************************* 参数校验  ******************************************************/
		medicalCard.setBranchCode(branchCode);

		if (StringUtils.isNotBlank(appId)) {
			medicalCard.setAppId(appId);
		} else {
			return goAndViewwaiting("【系统参数错误】：缺少医院AppId");
		}

		if (StringUtils.isNotBlank(appCode)) {
			medicalCard.setAppCode(appCode);
		} else {
			return goAndViewwaiting("【系统参数错误】：缺少医院Appcode为空");
		}

		if (StringUtils.isNotBlank(openId)) {
			medicalCard.setOpenId(openId);
		} else {
			return goAndViewwaiting("【系统参数错误】：缺少用户唯一标示openId");
		}

		Integer patCardTypeInteger = 1;
		if (StringUtils.isBlank(patCardNo)) {
			return goAndViewwaiting("【系统参数错误】：缺少就诊卡号");
		}

		try {
			if (StringUtils.isBlank(patCardType)) {
				return goAndViewwaiting("【系统参数错误】：缺少卡类型");
			} else {
				patCardTypeInteger = Integer.valueOf(patCardType);
			}
		} catch (Exception e) {
			return goAndViewwaiting("【系统参数错误】：卡类型转化失败,请检查传值是否正确");
		}

		try {

			medicalCard.setCardNo(patCardNo);
			medicalCard.setCardType(patCardTypeInteger);

			/*************************************************  查询患者信息接口  ******************************************************/
			// 通过appId,appCode获取医院信息：public List<HospIdAndAppSecretVo> findAppSecretByAppId(String appId, String appCode)
			HospIdAndAppSecretVo hospIdAndAppSecretVo = null;
			List<Object> params = new ArrayList<>();
			params.add(appId);
			params.add(appCode);
			List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "findAppSecretByAppId", params);
			if (!CollectionUtils.isEmpty(objects)) {
				List<HospIdAndAppSecretVo> vos = JSON.parseArray(JSON.toJSONString(objects), HospIdAndAppSecretVo.class);
				hospIdAndAppSecretVo = vos.get(0);
				objects = null;
			} else {
				return goAndViewwaiting("【系统参数错误】：无效的appId与appCode对，请检测参数有效性！");
			}

			medicalCard.setHospitalCode(hospIdAndAppSecretVo.getHospCode());
			medicalCard.setHospitalId(hospIdAndAppSecretVo.getHospId());
			medicalCard.setHospitalName(hospIdAndAppSecretVo.getHospName());

			if (StringUtils.isBlank(branchCode)) {
				// 通过hospitalCode获取分院接口调用： public List<CodeAndInterfaceVo> getDefCodeAndInterfaceVo(String hospitalCode)
				CodeAndInterfaceVo codeAndInterfaceVo = null;
				params.clear();
				params.add(hospIdAndAppSecretVo.getHospCode());
				objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getDefCodeAndInterfaceVo", params);
				List<CodeAndInterfaceVo> vos = JSON.parseArray(JSON.toJSONString(objects), CodeAndInterfaceVo.class);
				codeAndInterfaceVo = vos.get(0);
				objects = null;

				branchCode = codeAndInterfaceVo.getBranchHospitalCode();
			}

			MedicalcardBizManager medicalcardBizManager = SpringContextHolder.getBean(MedicalcardBizManager.class);
			Map<String, Object> resMap = medicalcardBizManager.queryMZPatient(medicalCard.getHospitalCode(), branchCode, medicalCard);
			String code = (String) resMap.get(BizConstant.INTERFACE_MAP_CODE_KEY);
			if (code.equals(BizConstant.INTERFACE_RES_SUCCESS_CODE)) {
				MZPatient patient = (MZPatient) resMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
				if (patient != null) {
					if (StringUtils.isBlank(patName) && StringUtils.isNotBlank(patient.getPatName())) {
						patName = patient.getPatName();
					}
					if (StringUtils.isBlank(patMobile) && StringUtils.isNotBlank(patient.getPatMobile())) {
						patMobile = patient.getPatMobile();
					}
					if (StringUtils.isNotBlank(patient.getPatIdNo())) {
						idCardNo = patient.getPatIdNo();
						medicalCard.setIdNo(idCardNo);
					}
				} else {
					return goAndViewwaiting("就诊人信息不存在");
				}
			} else {
				return goAndViewwaiting("获取就诊人信息失败，请稍候重试");
			}

			modelAndView = new ModelAndView("/easyhealth/biz/clinic/payIndex");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("加载待缴费记录基础数据错误, errorMsg: {}, cause by: {}", new Object[] { e.getMessage(), e.getCause() });
		}

		modelAndView.addObject("patCardType", patCardType);
		modelAndView.addObject("patCardNo", patCardNo);
		modelAndView.addObject("patName", patName);
		modelAndView.addObject("patMobile", patMobile);
		modelAndView.addObject("isScanCode", ClinicConstant.CLINIC_TYPE_SCANCODE_VAL); //扫码入口标示
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, medicalCard);
		return modelAndView;
	}

	private ModelAndView goAndViewwaiting(String msg) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/common/error");
		modelAndView.addObject("msg", msg);
		return modelAndView;
	}
}
