package com.yxw.easyhealth.biz.usercenter.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.vo.cache.CommonParamsVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.easyhealth.biz.vo.RegisterParamsVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.biz.usercenter.service.MedicalCardService;

@Controller
@RequestMapping("app/medicalcard/manage/")
public class MedicalcardManageController extends BaseAppController {

	private static Logger logger = LoggerFactory.getLogger(MedicalcardManageController.class);

	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);

	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	private MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);

	/**
	 * 选择医院列表(建康易)
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/hospitalList")
	public ModelAndView hospitalList(CommonParamsVo vo, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/easyhealth/common/h	ospotalList");
		String appCode = vo.getAppCode();
		String areaCode = vo.getAreaCode();
		List<HospIdAndAppSecretVo> hospitalInfos = baseDatasManager.getHospitalListByAppCode(appCode, areaCode);
		view.addObject(BizConstant.COMMON_ENTITY_LIST_KEY, hospitalInfos);

		String openId = getAppOpenId(request);
		vo.setOpenId(openId);
		view.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		view.addObject("nextUrl", "mobileApp/medicalcard/bind/toView");
		return view;
	}

	@RequestMapping(value = "toView")
	public ModelAndView toView(RegisterParamsVo vo, HttpServletRequest request) throws IOException {
		ModelAndView view = new ModelAndView("/easyhealth/biz/usercenter/medicalcardManage");

		if (StringUtils.isBlank(vo.getOpenId()) || vo.getOpenId().equals("null")) {
			vo.setOpenId(getOpenId(request));
		}

		try {
			// 医院的信息
			String appCode = vo.getAppCode();
			String areaCode = vo.getAreaCode();
			List<HospIdAndAppSecretVo> hospitalInfos = baseDatasManager.getHospitalListByAppCode(appCode, areaCode);
			view.addObject(BizConstant.COMMON_ENTITY_LIST_KEY, hospitalInfos);

			// 每个医院的绑卡规则
			Map<String, Object> hospitalBindCardNumsMap = new HashMap<String, Object>();
			for (HospIdAndAppSecretVo hospIdAndAppSecretVo : hospitalInfos) {
				String hospitalCode = hospIdAndAppSecretVo.getHospCode();
				RuleEdit ruleEdit = ruleConfigManager.getRuleEditByHospitalCode(hospitalCode);
				if (ruleEdit != null) {
					hospitalBindCardNumsMap.put(hospitalCode, ruleEdit.getAddVpNum());
				} else {
					logger.error("医院基础配置异常RuleEdit. hospitalCode: {}.", hospitalCode);
				}
			}
			view.addObject("hospitalBindCardNums", JSON.toJSONString(hospitalBindCardNumsMap));

			// 基础信息
			view.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		} catch (Exception e) {
			logger.error("跳转就诊卡管理失败~~. errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return view;
	}

	@ResponseBody
	@RequestMapping(value = "getAllCards", method = RequestMethod.POST)
	public Object getAllCards(CommonParamsVo vo) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<MedicalCard> cards = medicalCardService.findAllCardsByOpenId(vo.getOpenId());
			map.put(BizConstant.COMMON_ENTITY_LIST_KEY, cards);
		} catch (Exception e) {
			logger.error("getAllCards error. errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return new RespBody(Status.OK, map);
	}

}
