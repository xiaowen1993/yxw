/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-5-27</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.rule.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.entity.platform.rule.RuleTiedCard;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.rule.service.RuleTiedCardService;
import com.yxw.utils.StringUtils;

/**
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: RuleEditController
 * @Statement: <p>页面编辑规则</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/ruleTiedCard")
public class RuleTiedCardController extends BizBaseController<RuleTiedCard, String> {
	private RuleTiedCardService ruleTiedCardService = SpringContextHolder.getBean(RuleTiedCardService.class);

	@Override
	protected BaseService<RuleTiedCard, String> getService() {
		// TODO Auto-generated method stub
		return ruleTiedCardService;
	}

	/**
	 * @param ruleEdit
	 * @param isSameRules
	 *            是否所有分院使用同一规则
	 * @param branchHospitalId
	 *            非所有分院使用同一规则时 当前选定的分院id
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRuleTiedCard", method = RequestMethod.POST)
	public RespBody saveRuleTiedCard(HttpServletRequest request, RuleTiedCard ruleTiedCard) {
		User user = getLoginUser(request);
		String ruleId = ruleTiedCard.getId();
		if (ruleTiedCard.getCardTypeArray().length > 0) {
			ruleTiedCard.setCardType(StringUtils.ArrayToStr(ruleTiedCard.getCardTypeArray()));
		}

		if (ruleTiedCard.getVisitingPersonTypeArray().length > 0) {
			ruleTiedCard.setVisitingPersonType(StringUtils.ArrayToStr(ruleTiedCard.getVisitingPersonTypeArray()));
		}

		if (ruleTiedCard.getVerifyConditionTypeArray().length > 0) {
			ruleTiedCard.setVerifyConditionType(StringUtils.ArrayToStr(ruleTiedCard.getVerifyConditionTypeArray()));
		}

		if (ruleTiedCard.getCertificatesTypeArray().length > 0) {
			ruleTiedCard.setCertificatesType(StringUtils.ArrayToStr(ruleTiedCard.getCertificatesTypeArray()));
		}

		if (ruleTiedCard.getInputCardNoTipArray().length > 0) {
			ruleTiedCard.setInputCardNoTip(StringUtils.ArrayToStr(ruleTiedCard.getInputCardNoTipArray(), false));
		}

		if (ruleTiedCard.getInputCardTypeRemarkArray().length > 0) {
			ruleTiedCard.setInputCardTypeRemark(StringUtils.ArrayToStr(ruleTiedCard.getInputCardTypeRemarkArray(), false));
		}

		if (user != null) {
			if (ruleId == null) {
				ruleTiedCard.setCp(user.getId());
				ruleTiedCard.setCpName(user.getUserName());
				ruleTiedCard.setCt(new Date());
			} else {
				ruleTiedCard.setEp(user.getId());
				ruleTiedCard.setEpName(user.getUserName());
				ruleTiedCard.setEt(new Date());
			}
		}
		ruleId = ruleTiedCardService.saveRuleTiedCard(ruleTiedCard);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entityId", ruleId);
		return new RespBody(Status.OK, map);
	}
}
