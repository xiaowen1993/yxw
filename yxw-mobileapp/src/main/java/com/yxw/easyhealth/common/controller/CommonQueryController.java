package com.yxw.easyhealth.common.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.FamilyConstants;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.commons.vo.cache.SimpleMedicalCard;
import com.yxw.easyhealth.biz.usercenter.service.FamilyService;
import com.yxw.easyhealth.biz.vo.FamilyVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;

@Controller
@RequestMapping("app/common/")
public class CommonQueryController extends BaseAppController {
	private Logger logger = LoggerFactory.getLogger(CommonQueryController.class);
	private FamilyService familyService = SpringContextHolder.getBean(FamilyService.class);

	/**
	 * 扫码报到 - 通用的家人列表（目前就只有扫码报道用到）
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/familyList")
	public ModelAndView toPayView(FamilyVo vo, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("easyhealth/common/commonFamilyList");

		try {
			// 没有openId
			if (StringUtils.isBlank(vo.getOpenId())) {
				EasyHealthUser user = getEasyHealthUser(request);
				vo.setOpenId(user.getId());
			}

			// 没有appCode或者areaCode
			if (StringUtils.isBlank(vo.getAppCode()) || StringUtils.isBlank(vo.getAreaCode())) {
				vo.setAppCode(BizConstant.MODE_TYPE_APP);
				// 预测，预留的坑
				vo.setAreaCode("ShenZheng");
			}

			List<Family> families = familyService.findAllFamilies(vo.getOpenId());
			modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
			modelAndView.addObject(BizConstant.COMMON_ENTITY_LIST_KEY, families);
			// modelAndView.addObject("title", "扫码报到");
		} catch (Exception e) {
			logger.error("无效的参数，请重新登录后重试... errorMsg: {}. cause: {}.", e.getMessage(), e.getCause());
			modelAndView = new ModelAndView("easyhealth/common/error");
			modelAndView.addObject("msg", "无效的参数，请重新登录后重试...");
		}

		return modelAndView;
	}

	/**
	 * 通用业务查询 - app.commonBizQuery.js 选定了family才能执行该方法
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFamilyCard")
	public RespBody getFamilyCard(FamilyVo vo, HttpServletRequest request) {
		if (StringUtils.isAnyBlank(vo.getOpenId(), vo.getAreaCode(), vo.getFamilyId(), vo.getHospitalCode())) {
			return new RespBody(Status.ERROR, "非法请求，openId, areaCode, familyId, hospitalCode都不能为空！");
		} else {
			MedicalCard card = null;
			List<Object> params = new ArrayList<Object>();
			params.add(vo.getOpenId());
			params.add(vo.getFamilyId());
			params.add(vo.getHospitalCode());
			ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
			List<Object> results = serveComm.get(CacheType.MEDICAL_CARD_CACHE, "getMedicalCardForCommonQuery", params);
			if (!CollectionUtils.isEmpty(results)) {
				String source = JSON.toJSONString(results.get(0));
				card = JSON.parseObject(source, MedicalCard.class);
			}

			return new RespBody(Status.OK, card);
		}
	}

	/*
	 * @RequestMapping(value = "/test") public ModelAndView toTest(FamilyVo vo,
	 * HttpServletRequest request) { ModelAndView modelAndView = new
	 * ModelAndView("easyhealth/biz/register/test"); return modelAndView; }
	 */

	/**
	 * 卡信息查询 - app.regCards.js 找出该openId的所有家人并且如果家人在该医院有卡号的话，也一并返回 Map<familyId,
	 * Map<String, object>> 里层的map为 family:family对象, card:card对象（如果有的话，若没有则为空字符串）
	 * Map<maxXXX, maxXXX> 最大绑卡数
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRegCards")
	public RespBody getRegCards(FamilyVo vo) {
		// 一个familyId， 在一家医院下，对应一个OpenId只有一张卡（也可以和OpenId无关了，就是卡唯一）
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> entities = new HashMap<>();
		resultMap.put("entities", entities);

		try {

			if (StringUtils.isAnyBlank(vo.getOpenId(), vo.getAreaCode(), vo.getHospitalCode(),
					vo.getBranchHospitalCode())) {
				return new RespBody(Status.ERROR, "非法请求，openId, areaCode, hospitalCode, branchHospitalCode都不能为空！");
			} else {
				// 拿添加家人规则 jky:easyhealth_family_numbers, 新app目前也一样写在配置文件中写死，后续可在缓存中添加
				int appFamilyLimit = SystemConfig.getInteger(FamilyConstants.MAX_FAMILY_NUM_KEY, 5);
				resultMap.put("appFamilyLimit", appFamilyLimit);

				// 拿所有family
				List<Family> families = familyService.findAllFamilies(vo.getOpenId());

				// 拿所有卡 -- 指定某家医院的卡。
				ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
				List<Object> params = new ArrayList<>();
				params.add(vo.getOpenId());
				params.add(vo.getHospitalCode());
				params.add(vo.getBranchHospitalCode());

				List<Object> results = serveComm.get(CacheType.MEDICAL_CARD_CACHE, "getMedicalCardsByOpenId", params);

				List<SimpleMedicalCard> cards = null;
				if (!CollectionUtils.isEmpty(results)) {
					String source = JSON.toJSONString(results);
					cards = JSON.parseArray(source, SimpleMedicalCard.class);
				}

				Map<String, SimpleMedicalCard> cardsMap = null;

				if (!CollectionUtils.isEmpty(cards)) {
					cardsMap = new HashMap<String, SimpleMedicalCard>();
					cardsMap = Maps.uniqueIndex(cards, new Function<SimpleMedicalCard, String>() {

						@Override
						public String apply(SimpleMedicalCard input) {
							return input.getFamilyId();
						}
					});
				}

				for (Family family : families) {
					Map<String, Object> familyMap = new HashMap<>();
					familyMap.put("family", family);
					SimpleMedicalCard card = CollectionUtils.isEmpty(cardsMap) ? null : cardsMap.get(family.getId());
					familyMap.put("card", card == null ? "" : card);
					entities.put(family.getId(), familyMap);
				}

				return new RespBody(Status.OK, resultMap);
			}
		} catch (Exception e) {
			logger.error("query card is error.", e);
			return new RespBody(Status.ERROR, "根据openId查询查记录失败。");
		}
	}
}
