package com.yxw.easyhealth.biz.usercenter.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.MedicalCardConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.platform.rule.RuleUserCenter;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.easyhealth.biz.vo.FamilyVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.biz.usercenter.service.MedicalCardService;

@Controller
@RequestMapping("app/usercenter/cardInfo/")
public class CardInfoController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(CardInfoController.class);

	//	private HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	private MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);

	@RequestMapping(value = "toView")
	public ModelAndView toView(FamilyVo vo, HttpServletRequest request) {
		/********************************openId************************************/
		if (StringUtils.isBlank(vo.getOpenId()) || vo.getOpenId().equals("null")) {
			vo.setOpenId(getOpenId(request));
		}

		ModelAndView view = new ModelAndView("easyhealth/common/error");
		try {
			/********************************绑卡信息************************************/
			if (StringUtils.isNotBlank(vo.getMedicalcardId())) {
				// 根据卡ID，查找卡
				MedicalCard medicalCard = medicalCardService.findById(vo.getMedicalcardId());

				if (medicalCard != null) {
					if (medicalCard.getState().intValue() == MedicalCardConstant.MEDICALCARD_BOUND) {
						// 查出医院信息
						List<Object> params = new ArrayList<Object>();
						params.add(medicalCard.getHospitalCode());
						params.add(vo.getAppCode());
						List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "queryAppInfoByHospitalCode", params);
						if (CollectionUtils.isNotEmpty(results)) {
							String source = JSON.toJSONString(results);
							List<HospitalCodeAndAppVo> vos = JSON.parseArray(source, HospitalCodeAndAppVo.class);
							HospitalCodeAndAppVo hospitalCodeAndAppVo = vos.get(0);

							medicalCard.setAppCode(hospitalCodeAndAppVo.getAppCode());
							medicalCard.setAppId(hospitalCodeAndAppVo.getAppId());
						}

						view = new ModelAndView("easyhealth/biz/usercenter/cardInfo");
						view.addObject(BizConstant.COMMON_ENTITY_KEY, medicalCard);
						view.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
						if (logger.isDebugEnabled()) {
							logger.debug("需解绑的就诊卡", JSON.toJSONString(medicalCard));
						}
						/********************************规则信息************************************/
						RuleUserCenter rule = ruleConfigManager.getRuleUserCenterByHospitalCode(medicalCard.getHospitalCode());
						view.addObject("rule", rule);
					} else if (medicalCard.getState().intValue() == MedicalCardConstant.MEDICALCARD_UNBIND) {
						request.setAttribute("msg", "该就诊卡已被解绑，请重新绑卡");
					} else {
						logger.info("该就诊卡绑卡状态异常.[{}], state=[{}]", medicalCard.getCardNo(), medicalCard.getState());
						request.setAttribute("msg", "该就诊卡绑卡状态异常");
					}
				} else {
					logger.info("openId=" + vo.getOpenId() + "[错误的绑卡信息，查不到绑卡数据]");
					request.setAttribute("msg", "非法的用户数据");
				}
			} else {
				logger.info("openId=" + vo.getOpenId() + "[错误的绑卡信息，查不到绑卡数据]");
				request.setAttribute("msg", "非法的用户数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	/**
	 * 异步解绑就诊卡
	 * @param medicalCardId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "unbindCard", method = RequestMethod.POST)
	public Object unbind(MedicalCard medicalCard) {
		try {
			medicalCard = medicalCardService.findById(medicalCard.getId());
			Map<String, Object> resultMap = medicalCardService.unBindCard(medicalCard);
			return new RespBody(Status.OK, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "");
		}
	}
}
