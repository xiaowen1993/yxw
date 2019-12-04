package com.yxw.easyhealth.biz.usercenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.FamilyConstants;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.easyhealth.biz.usercenter.service.FamilyService;
import com.yxw.easyhealth.biz.vo.FamilyVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.biz.usercenter.service.MedicalCardService;

/**
 * 显示人的基本信息，可以解绑人。显示相关医院卡信息，跳转查看明细卡。
 * @Package: com.yxw.easyhealth.biz.usercenter.controller
 * @ClassName: EhCardInfoController
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-12-7
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("app/usercenter/familyInfo/")
public class FamilyInfoController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(FamilyInfoController.class);
	private FamilyService familyService = SpringContextHolder.getBean(FamilyService.class);

	@RequestMapping(value = "/index")
	public ModelAndView familyList(FamilyVo vo, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("easyhealth/biz/usercenter/familyInfo");

		// 设置openId
		if (StringUtils.isBlank(vo.getOpenId())) {
			String openId = getAppOpenId(request);
			vo.setOpenId(openId);
		}

		// 去session中拿信息
		if (vo.getSyncType() == null) {
			vo.setSyncType(FamilyConstants.FAMILY_OWNERSHIP_SELF);
		}

		Family family = null;

		if (vo.getSyncType() != null && vo.getSyncType().intValue() == FamilyConstants.FAMILY_OWNERSHIP_SELF
				&& StringUtils.isBlank(vo.getFamilyId())) {
			family = familyService.findSelfInfo(vo.getOpenId());

			if (family == null) {
				// 加本人
				familyService.saveFamilyInfo(this.getEasyHealthUser(request));
				family = familyService.findSelfInfo(vo.getOpenId());
			}
		} else {
			family = familyService.findFamilyInfo(vo.getFamilyId(), vo.getOpenId());
		}

		if (family == null) {
			view = new ModelAndView("easyhealth/common/error");
			view.addObject("msg", "无效的链接，请重新登录后重试...");
		} else {
			view.addObject(FamilyConstants.FAMILY_KEY, family);
		}

		view.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "getCards", method = RequestMethod.POST)
	public Object getCards(FamilyVo vo, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 优先从缓存拿
			MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);
			List<MedicalCard> families = medicalCardService.findCardsByOpenIdAndFamilyId(vo.getOpenId(), vo.getFamilyId());
			map.put(BizConstant.COMMON_ENTITY_LIST_KEY, families);
		} catch (Exception e) {
			logger.error("unbindFamily error. errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return new RespBody(Status.OK, map);
	}

	@ResponseBody
	@RequestMapping(value = "unbindFamily", method = RequestMethod.POST)
	public Object getFamilies(FamilyVo vo, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Family family = null;
			String sessionKey = FamilyConstants.FAMILY_KEY.concat("_").concat(vo.getOpenId());
			if (vo.getSyncType().intValue() != FamilyConstants.FAMILY_OWNERSHIP_SELF) {
				sessionKey = sessionKey.concat("_").concat(vo.getFamilyId());
			}
			family = (Family) request.getSession().getAttribute(sessionKey);

			if (family == null) {
				if (vo.getSyncType().intValue() == FamilyConstants.FAMILY_OWNERSHIP_SELF) {
					// 本人不知道Id
					family = familyService.findSelfInfo(vo.getOpenId());
				} else {
					family = familyService.findFamilyInfo(vo.getFamilyId(), vo.getOpenId());
				}
			}

			if (family != null) {
				map = familyService.unbindFamily(family);
			} else {
				map.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_EXCEPTION);
				map.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "移除家人异常！不存在该家人。");
			}
		} catch (Exception e) {
			logger.error("unbindFamily error. errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			map.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_EXCEPTION);
			map.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "移除家人异常！");
		}

		return new RespBody(Status.OK, map);
	}

}
