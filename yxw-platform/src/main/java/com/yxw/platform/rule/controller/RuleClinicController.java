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
import com.yxw.commons.entity.platform.rule.RuleClinic;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.rule.service.RuleClinicService;

/**
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: RulePaymentController
 * @Statement: <p>
 *             门诊缴费规则
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/ruleClinic")
public class RuleClinicController extends BizBaseController<RuleClinic, String> {

	private RuleClinicService ruleClinicService = SpringContextHolder.getBean(RuleClinicService.class);

	@Override
	protected BaseService<RuleClinic, String> getService() {
		// TODO Auto-generated method stub
		return this.ruleClinicService;
	}

	/**
	 * @param request
	 * @param rulePayment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRuleClinic", method = RequestMethod.POST)
	public RespBody saveRuleClinic(HttpServletRequest request, RuleClinic ruleClinic) {
		User user = getLoginUser(request);
		String ruleClinicId = ruleClinic.getId();

		if (user != null) {
			if (ruleClinicId == null) {
				ruleClinic.setCp(user.getId());
				ruleClinic.setCpName(user.getUserName());
				ruleClinic.setCt(new Date());
			} else {
				ruleClinic.setEp(user.getId());
				ruleClinic.setEpName(user.getUserName());
				ruleClinic.setEt(new Date());
			}
		}
		ruleClinicService.saveRuleClinic(ruleClinic);
		ruleClinicId = ruleClinic.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entityId", ruleClinicId);
		return new RespBody(Status.OK, map);
	}
}
