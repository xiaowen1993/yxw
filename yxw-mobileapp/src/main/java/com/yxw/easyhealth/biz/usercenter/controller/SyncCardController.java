package com.yxw.easyhealth.biz.usercenter.controller;

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
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.FamilyConstants;
import com.yxw.commons.constants.biz.MedicalCardConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.easyhealth.biz.usercenter.service.FamilyService;
import com.yxw.easyhealth.biz.vo.FamilyVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.biz.usercenter.service.MedicalCardService;

@Controller
@RequestMapping("app/usercenter/syncCard/")
public class SyncCardController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(SyncCardController.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	private FamilyService familyService = SpringContextHolder.getBean(FamilyService.class);

	@RequestMapping(value = "/index")
	public ModelAndView familyList(FamilyVo vo, HttpServletRequest request) {
		ModelAndView view = null;

		view = new ModelAndView("easyhealth/biz/usercenter/syncCard");
		try {
			// 判断同步类型（本人还是家人）
			if (vo.getSyncType() == null) {
				vo.setSyncType(FamilyConstants.FAMILY_OWNERSHIP_SELF);
			}
			view.addObject(BizConstant.COMMON_PARAMS_KEY, vo);

			try {
				Family family = null;
				if (vo.getSyncType() != null && vo.getSyncType().intValue() == FamilyConstants.FAMILY_OWNERSHIP_SELF
						&& StringUtils.isBlank(vo.getFamilyId())) {
					// 本人不知道Id
					family = familyService.findSelfInfo(vo.getOpenId());
					vo.setFamilyId(family.getId());
				} else {
					family = familyService.findFamilyInfo(vo.getFamilyId(), vo.getOpenId());
				}

				view.addObject(BizConstant.COMMON_ENTITY_KEY, family);

				Map<String, Object> hospitalMap = getHospitalMap(vo.getAppCode(), vo.getAreaCode(), vo.getOpenId(), family.getId());

				view.addObject("entityMap", hospitalMap);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("获取家人信息失败, errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
				view = new ModelAndView("easyhealth/common/error");
				view.addObject("msg", "获取家人信息失败，请稍后再试...");
			}
		} catch (Exception e) {
			logger.error("获取医院信息失败, errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			view = new ModelAndView("easyhealth/common/error");
			view.addObject("msg", "获取医院信息失败，请稍后再试...");
		}
		return view;
	}

	private Map<String, Object> getHospitalMap(String appCode, String areaCode, String openId, String familyId) {
		Map<String, Object> resultsMap = new HashMap<>();
		List<HospitalInfoByEasyHealthVo> hospitalInfos = null;

		List<Object> requestGetParameters = new ArrayList<Object>();
		requestGetParameters.add(appCode);
		requestGetParameters.add(BizConstant.OPTION_ONLINE_FILING);
		requestGetParameters.add(areaCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalByOption", requestGetParameters);
		if (!CollectionUtils.isEmpty(results)) {
			String source = JSON.toJSONString(results);
			hospitalInfos = JSON.parseArray(source, HospitalInfoByEasyHealthVo.class);
		}

		List<Object> params = new ArrayList<Object>();
		for (int i = hospitalInfos.size() - 1; i >= 0; i--) {
			HospitalInfoByEasyHealthVo hospitalInfoByEasyHealthVo = hospitalInfos.get(i);
			params.clear();
			params.add(hospitalInfoByEasyHealthVo.getHospitalCode());
			List<Object> result = serveComm.get(CacheType.HOSPITAL_CACHE, "getDefCodeAndInterfaceVo", params);
			if (!CollectionUtils.isEmpty(result)) {
				CodeAndInterfaceVo codeAndInterfaceVo = (CodeAndInterfaceVo) result.get(0);

				if (codeAndInterfaceVo.getStatus() == 0) {
					hospitalInfos.remove(i);
				}
			}
		}

		MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);
		List<MedicalCard> families = medicalCardService.findCardsByOpenIdAndFamilyId(openId, familyId);
		Map<String, MedicalCard> cardsMap = Maps.uniqueIndex(families, new Function<MedicalCard, String>() {

			@Override
			public String apply(MedicalCard input) {
				return input.getHospitalCode();
			}
		});

		if (!CollectionUtils.isEmpty(hospitalInfos)) {
			for (HospitalInfoByEasyHealthVo vo : hospitalInfos) {
				Map<String, Object> map = new HashMap<>();
				map.put("hospital", vo);
				map.put("card", Boolean.toString(cardsMap.containsKey(vo.getHospitalCode())));
				resultsMap.put(vo.getHospitalCode(), map);
			}

		}
		logger.info(JSON.toJSONString(resultsMap.values()));

		return resultsMap;
	}

	@ResponseBody
	@RequestMapping(value = "syncMedicalcard", method = RequestMethod.POST)
	public Object syncMedicalcard(FamilyVo vo, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Family family = null;

			if (vo.getSyncType() != null && vo.getSyncType().intValue() == FamilyConstants.FAMILY_OWNERSHIP_SELF
					&& StringUtils.isBlank(vo.getFamilyId())) {
				// 本人不知道Id
				family = familyService.findSelfInfo(vo.getOpenId());
			} else {
				family = familyService.findFamilyInfo(vo.getFamilyId(), vo.getOpenId());
			}

			if (family != null) {
				// 旧版放session中的方式。
				// request.getSession().setAttribute(sessionKey, family);

				// 检测卡有没有被绑定 hospitalCode, name, idType, idNo, openId
				MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);
				//				List<MedicalCard> medicalCards = medicalCardService.findCardsByOpenIdAndFamilyIdAndHospitalCode(vo.getOpenId(), family.getId(),
				//						vo.getHospitalCode());
				// 只要是有过卡了就不让绑了
				Map<String, Object> params = new HashMap<>();
				params.put("familyId", family.getId());
				params.put("appCode", vo.getAppCode());
				params.put("hospitalCode", vo.getHospitalCode());
				params.put("state", MedicalCardConstant.MEDICALCARD_BOUND);
				List<MedicalCard> medicalCards = medicalCardService.findCardSuperviseParams(params);
				if (!CollectionUtils.isEmpty(medicalCards)) {
					map.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
					map.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "同步失败。(卡已被其他账户绑卡，无法同步。)");
					// map.put(BizConstant.COMMON_ENTITY_KEY, medicalCards.get(0));

					if (medicalCards.size() > 1) {
						logger.error("信息查询异常，同一个家人在同一家医院有多张诊疗卡号。openId={}, familyId={}, hospitalCode={}.", vo.getOpenId(),
								vo.getFamilyId(), vo.getHospitalCode());
					}

					return new RespBody(Status.PROMPT, map);
				} else {
					map = familyService.syncMedicalcard(family, vo.getHospitalId(), vo.getHospitalCode(), vo.getAppCode());//使用当前平台code，不能用添加家人时的code，这样添加的家人就能在所有平台使用，目前数据库记录的appCode只能表示第一次创建是使用什么平台
					// 测试失败
					// map.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
					// map.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "故意让你失败的");
				}

			} else {
				map.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
				map.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "非法的用户数据.familyId=" + vo.getFamilyId());
			}

		} catch (Exception e) {
			logger.error("syncMedicalcard error. errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			map.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
			map.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.FAIL);
		}

		return new RespBody(Status.OK, map);
	}
}
