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
import com.yxw.commons.entity.platform.rule.RuleOnlineFiling;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.rule.service.RuleOnlineFilingService;
import com.yxw.utils.StringUtils;

/**
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: RuleEditController
 * @Statement: <p>在线建档规则</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/ruleOnlineFiling")
public class RuleOnlineFilingController extends BizBaseController<RuleOnlineFiling, String> {

	private RuleOnlineFilingService ruleOnlineFilingService = SpringContextHolder.getBean(RuleOnlineFilingService.class);

	@Override
	protected BaseService<RuleOnlineFiling, String> getService() {
		// TODO Auto-generated method stub
		return ruleOnlineFilingService;
	}

	/**
	 * @param request
	 * @param ruleOnlineFiling
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRuleOnlineFiling", method = RequestMethod.POST)
	public RespBody saveRuleOnlineFiling(HttpServletRequest request, RuleOnlineFiling ruleOnlineFiling) {
		User user = getLoginUser(request);
		String ruleId = ruleOnlineFiling.getId();
		if (ruleOnlineFiling.getCertificatesTypeArray().length > 0) {
			ruleOnlineFiling.setCertificatesType(StringUtils.ArrayToStr(ruleOnlineFiling.getCertificatesTypeArray()));
		}

		if (ruleOnlineFiling.getVisitingPersonTypeArray().length > 0) {
			ruleOnlineFiling.setVisitingPersonType(StringUtils.ArrayToStr(ruleOnlineFiling.getVisitingPersonTypeArray()));
		}

		if (user != null) {
			if (ruleId == null) {
				ruleOnlineFiling.setCp(user.getId());
				ruleOnlineFiling.setCpName(user.getUserName());
				ruleOnlineFiling.setCt(new Date());
			} else {
				ruleOnlineFiling.setEp(user.getId());
				ruleOnlineFiling.setEpName(user.getUserName());
				ruleOnlineFiling.setEt(new Date());
			}
		}
		ruleId = ruleOnlineFilingService.saveRuleOnlineFiling(ruleOnlineFiling);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entityId", ruleId);
		return new RespBody(Status.OK, map);
	}
}
