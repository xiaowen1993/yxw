package com.yxw.easyhealth.common.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.MedicalCardConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.cache.CommonParamsVo;
import com.yxw.commons.vo.cache.SimpleMedicalCard;
import com.yxw.easyhealth.common.service.CommonCardService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;
import com.yxw.mobileapp.biz.usercenter.service.MedicalCardService;
import com.yxw.mobileapp.constant.EasyHealthConstant;

@Controller
@RequestMapping("mobileApp/common/")
public class CommonCardController extends BaseController<MedicalCard, String> {

	private static Logger logger = LoggerFactory.getLogger(CommonCardController.class);

	private CommonCardService service = SpringContextHolder.getBean(CommonCardService.class);
	private MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);

	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);

	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	@Override
	protected BaseService<MedicalCard, String> getService() {
		return service;
	}

	@ResponseBody
	@RequestMapping(value = "/findAllCards", method = RequestMethod.POST)
	public Object findAllCards(MedicalCard medicalCard, HttpServletRequest request) {
		String appCode = medicalCard.getAppCode();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("异步从缓存中获取就诊卡..........");
				logger.debug(
						"openId:{},hospitalCode:{},brachCode:{},platform:{}",
						new Object[] { medicalCard.getOpenId(),
								medicalCard.getHospitalCode(),
								medicalCard.getBranchCode(),
								medicalCard.getPlatformMode() });
			}
			List<SimpleMedicalCard> simpleList = new ArrayList<SimpleMedicalCard>();
			try {

				/*
				 * if (appCode.startsWith(BizConstant.MODE_TYPE_APP) && StringUtils.isBlank(medicalCard.getHospitalCode())) { //
				 * 健康易获取所有医院的绑卡 // 有Bug，要区分医院了。 simpleList = baseDatasManager.getMedicalCardsByOpenId(medicalCard.getOpenId()); } else {
				 */

				RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(medicalCard.getHospitalCode());
				if (logger.isDebugEnabled()) {
					logger.debug("load hospital's  ruleRegister infos by cache.", ruleRegister);
				}

				if (ruleRegister.getIsHasBranch() != null && ruleRegister.getIsHasBranch() == RuleConstant.IS_HAS_BRANCH_YES) {
					simpleList =
							baseDatasManager.getMedicalCardsByOpenId(medicalCard.getOpenId(), medicalCard.getHospitalCode(),
									medicalCard.getBranchCode());
				} else {
					// 查询所有分院下的就诊卡
					simpleList = baseDatasManager.getMedicalCardsByOpenId(medicalCard.getOpenId(), medicalCard.getHospitalCode());
				}
				if (logger.isDebugEnabled()) {
					logger.debug("异步从缓存中获取到的就诊卡：{}", simpleList);
				}
				// }
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("findAllCards is happend exception.errorMsg:{},case:{}", e.getMessage(), e.getCause());
			}
			if (!CollectionUtils.isEmpty(simpleList)) {
				List<SimpleMedicalCard> list = new ArrayList<SimpleMedicalCard>();

				if (logger.isDebugEnabled()) {
					logger.debug("openId:{} , 异步从缓存中获取到的就诊卡数：{}", medicalCard.getOpenId(), simpleList.size());
				}

				for (SimpleMedicalCard card : simpleList) {
					if ( ( card.getState().intValue() == MedicalCardConstant.MEDICALCARD_BOUND )) {
						list.add(card);
					}
				}

				Collections.sort(list);
				return new RespBody(Status.OK, list);
			} else {
				List<MedicalCard> list = new ArrayList<MedicalCard>();
				// medicalCard.setPlatform(null);
				List<MedicalCard> cardList = service.getAllCards(medicalCard);
				if (!CollectionUtils.isEmpty(cardList)) {
					if (logger.isDebugEnabled()) {
						logger.debug("openId:{} , 异步从数据库中获取到的就诊卡数：{}", medicalCard.getOpenId(), list.size());
					}

					if (!appCode.startsWith(BizConstant.MODE_TYPE_APP)) {
						// 开线程把这些数据放入到缓存中。
						try {
							baseDatasManager.addMedicalcardsToCache(cardList);
						} catch (Exception e) {
							logger.error("就诊卡列表数据写入缓存错误..., message: {}, cause: {}", e.getMessage(), e.getCause());
						}
					}

					// 过滤
					for (MedicalCard card : cardList) {
						if (card.getState().intValue() == MedicalCardConstant.MEDICALCARD_BOUND) {
							list.add(card);
						}
					}

					Collections.sort(list);
				}

				return new RespBody(Status.OK, list);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "");
		}
	}

	/**
	 * 是否能够绑卡
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/whetherCanBindCard", method = RequestMethod.POST)
	public Object whetherCanBindCard(CommonParamsVo vo) {
		try {
			int maxCardCount = ruleConfigManager.getRuleEditByHospitalCode(vo.getHospitalCode()).getAddVpNum();
			MedicalCard medicalCard = new MedicalCard();
			medicalCard.setHospitalCode(vo.getHospitalCode());
			medicalCard.setOpenId(vo.getOpenId());
			String appCode = vo.getAppCode();

			int platformMode = ModeTypeUtil.getPlatformModeType(appCode);

			medicalCard.setPlatformMode(platformMode);
			medicalCard.setState(MedicalCardConstant.MEDICALCARD_BOUND);

			Map<String, Object> respMap = medicalCardService.whetherCanBindCard(medicalCard, maxCardCount);

			return new RespBody(Status.OK, respMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "");
		}
	}

	/**
	 * 是否能够绑卡
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/autoBindCard", method = RequestMethod.POST)
	public Object autoBindCard(CommonParamsVo vo, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 自动绑卡
		if (StringUtils.isNotBlank(vo.getAppCode()) && vo.getAppCode().startsWith(BizConstant.MODE_TYPE_APP)) {
			EasyHealthUser user = (EasyHealthUser) request.getSession().getAttribute(EasyHealthConstant.SESSION_USER_ENTITY);
			if (user != null) {
				String id = medicalCardService.autoBindSelfCardForEasyHealth(vo, user.getName(), user.getCardNo(), user.getMobile());
				if (StringUtils.isNotBlank(id)) {
					map.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, BizConstant.INTERFACE_RES_SUCCESS_CODE);
				} else {
					map.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, BizConstant.INTERFACE_RES_FAILURE_MSG);
				}
			} else {
				logger.error("session中没有User信息，请先登陆..");
				map.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, BizConstant.INTERFACE_RES_FAILURE_MSG);
			}
		}

		return new RespBody(Status.OK, map);
	}

	@ResponseBody
	@RequestMapping(value = "/findCardsForEasyHealth", method = RequestMethod.POST)
	public Object findCardsForEasyHealth(MedicalCard medicalCard, HttpServletRequest request) {
		try {
			List<SimpleMedicalCard> simpleList = new ArrayList<SimpleMedicalCard>();
			try {
				// simpleList = baseDatasManager.getMedicalCardsByOpenIdAndArea(medicalCard.getOpenId(), medicalCard.getAreaCode());
				List<Object> params = new ArrayList<Object>();
				params.add(medicalCard.getOpenId());
				params.add(medicalCard.getAreaCode());
				ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
				List<Object> results = serveComm.get(CacheType.MEDICAL_CARD_CACHE, "getMedicalCardsByOpenIdAndArea", params);
				if (CollectionUtils.isNotEmpty(results)) {
					String source = JSON.toJSONString(results);
					simpleList = JSON.parseArray(source, SimpleMedicalCard.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("findAllCards is happend exception.errorMsg:{},case:{}", e.getMessage(), e.getCause());
			}

			// logger.info("findCardsForEasyHealth: {}.", JSON.toJSONString(simpleList));
			if (!CollectionUtils.isEmpty(simpleList)) {
				List<SimpleMedicalCard> list = new ArrayList<SimpleMedicalCard>();

				for (SimpleMedicalCard card : simpleList) {
					if ( ( card.getState().intValue() == MedicalCardConstant.MEDICALCARD_BOUND )) {
						list.add(card);
					}
				}
				return new RespBody(Status.OK, list);
			} else {
				List<MedicalCard> list = new ArrayList<MedicalCard>();
				// medicalCard.setPlatform(null);
				List<MedicalCard> cardList = service.getAllCards(medicalCard);
				if (!CollectionUtils.isEmpty(cardList)) {
					for (MedicalCard card : cardList) {
						list.add(card);
					}
				}

				// 把查询出来的数据放回缓存中。 留下的坑。
				return new RespBody(Status.OK, list);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "");
		}
	}
}
