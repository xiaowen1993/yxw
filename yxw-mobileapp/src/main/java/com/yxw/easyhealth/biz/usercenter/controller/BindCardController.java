package com.yxw.easyhealth.biz.usercenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.commons.entity.platform.rule.RuleTiedCard;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.easyhealth.biz.usercenter.service.FamilyService;
import com.yxw.easyhealth.biz.vo.FamilyVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.biz.usercenter.service.MedicalCardService;

@Controller
@RequestMapping("app/usercenter/medicalcard/")
public class BindCardController extends BaseAppController {
	private Logger logger = LoggerFactory.getLogger(BindCardController.class);
	private FamilyService familyService = SpringContextHolder.getBean(FamilyService.class);
	private MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);
	private BaseDatasManager baseManager = SpringContextHolder.getBean(BaseDatasManager.class);
	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
	private final static String BIND_CARD_KEY = "bindCard_";

	@RequestMapping(value = "/index")
	public ModelAndView familyList(FamilyVo vo, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("easyhealth/biz/usercenter/bindCard");

		// 从同步卡地方过来的，会有appId, openId, appCode, areaCode, hospitalCode, hospitalId, familyId
		// 可能没有医院名称，帮他补上
		if (StringUtils.isBlank(vo.getHospitalName())) {
			HospIdAndAppSecretVo hosIdAndAppSecretVo = baseManager.getHospitalEasyHealthAppInfo(vo.getHospitalId(), vo.getAppCode());
			vo.setHospitalName(hosIdAndAppSecretVo.getHospName());
		}

		// 如果没有分院，帮他选一个，就不在界面上让用户选了。
		if (StringUtils.isBlank(vo.getBranchHospitalCode())) {
			List<BranchHospital> branchHospitals = baseManager.queryBranchsByHospitalCode(vo.getHospitalCode());
			if (CollectionUtils.isEmpty(branchHospitals)) {
				logger.error("该医院没有分院信息。hospitalName: {}. hospitalCode: {}.", vo.getHospitalName(), vo.getHospitalCode());
				view = new ModelAndView("easyhealth/500");
				return view;
			} else {
				BranchHospital branchHospital = branchHospitals.get(0);
				vo.setBranchHospitalCode(branchHospital.getCode());
				vo.setBranchHospitalId(branchHospital.getId());
				vo.setBranchHospitalName(branchHospital.getName());
			}
		}

		Family family = familyService.findFamilyInfo(vo.getFamilyId(), vo.getOpenId());
		if (family != null) {
			view.addObject(BizConstant.COMMON_ENTITY_KEY, family);
			String sessionKey = BIND_CARD_KEY + vo.getOpenId() + "_" + vo.getFamilyId();
			request.getSession().setAttribute(sessionKey, family);
		} else {
			logger.error("没有找到这个family...familyId={}", vo.getFamilyId());
			view = new ModelAndView("easyhealth/500");
			return view;
		}

		RuleTiedCard ruleTiedCard = ruleConfigManager.getRuleTiedCardByHospitalCode(vo.getHospitalCode());

		view.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		view.addObject("rule", ruleTiedCard);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "bindCard", method = RequestMethod.POST)
	public Object bindCard(MedicalCard medicalCard, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Family family = familyService.findFamilyInfo(medicalCard.getFamilyId(), medicalCard.getOpenId());
		if (family != null) {
			// 执行绑卡流程
			MedicalCard tempCard = family.convertToMedicalCard();
			tempCard.setCardNo(medicalCard.getCardNo());
			tempCard.setCardType(medicalCard.getCardType());
			tempCard.setHospitalCode(medicalCard.getHospitalCode());
			tempCard.setHospitalId(medicalCard.getHospitalId());
			tempCard.setHospitalName(medicalCard.getHospitalName());
			tempCard.setBranchCode(medicalCard.getBranchCode());
			tempCard.setBranchId(medicalCard.getBranchId());
			tempCard.setBranchName(medicalCard.getBranchName());
			tempCard.setAppCode(medicalCard.getAppCode());
			tempCard.setAppId(medicalCard.getAppId());
			tempCard.setAreaCode(medicalCard.getAreaCode());

			// 替换手机号码 -- 新增需求，考虑到多家医院所留号码可能不一致
			if (StringUtils.isNotBlank(medicalCard.getMobile())) {
				tempCard.setMobile(medicalCard.getMobile());
			}

			// 获取绑卡验证配置信息
			RuleTiedCard ruleTiedCard = ruleConfigManager.getRuleTiedCardByHospitalCode(medicalCard.getHospitalCode());
			String verifyConditionType = ruleTiedCard.getVerifyConditionType();

			map = medicalCardService.bindCard(tempCard, verifyConditionType);
		} else {
			logger.error("session中的family数据丢失.." + medicalCard.getFamilyId());
			map.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
			map.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "session中的family数据丢失.." + medicalCard.getFamilyId());
		}
		// 检测卡是否已经绑上了。
		return new RespBody(Status.OK, map);
	}
}
