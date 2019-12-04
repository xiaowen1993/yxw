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
import com.yxw.commons.entity.platform.rule.RulePayment;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.rule.service.RulePaymentService;

/**
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: RulePaymentController
 * @Statement: <p>
 *             缴费规则
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
@RequestMapping("/sys/rulePayment")
public class RulePaymentController extends BizBaseController<RulePayment, String> {

	private RulePaymentService rulePaymentService = SpringContextHolder.getBean(RulePaymentService.class);

	@Override
	protected BaseService<RulePayment, String> getService() {
		// TODO Auto-generated method stub
		return this.rulePaymentService;
	}

	/**
	 * @param request
	 * @param rulePayment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRulePayment", method = RequestMethod.POST)
	public RespBody saveRulePayment(HttpServletRequest request, RulePayment rulePayment) {
		User user = getLoginUser(request);
		String rulePaymentId = rulePayment.getId();

		if (user != null) {
			if (rulePaymentId == null) {
				rulePayment.setCp(user.getId());
				rulePayment.setCpName(user.getUserName());
				rulePayment.setCt(new Date());
			} else {
				rulePayment.setEp(user.getId());
				rulePayment.setEpName(user.getUserName());
				rulePayment.setEt(new Date());
			}
		}
		rulePaymentService.saveRulePayment(rulePayment);
		rulePaymentId = rulePayment.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entityId", rulePaymentId);
		return new RespBody(Status.OK, map);
	}
}
