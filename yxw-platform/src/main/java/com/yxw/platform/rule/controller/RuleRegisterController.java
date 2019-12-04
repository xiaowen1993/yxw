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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.rule.service.RuleRegisterService;

/**
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: RuleEditController
 * @Statement: <p>
 *             挂号规则配置
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
@RequestMapping("/sys/ruleRegister")
public class RuleRegisterController extends BizBaseController<RuleRegister, String> {

	private RuleRegisterService ruleRegisterService = SpringContextHolder.getBean(RuleRegisterService.class);

	@Override
	protected BaseService<RuleRegister, String> getService() {
		// TODO Auto-generated method stub
		return this.ruleRegisterService;

	}

	/**
	 * @param request
	 * @param ruleRegister
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRuleRegister", method = RequestMethod.POST)
	public RespBody saveRuleRegister(HttpServletRequest request, RuleRegister ruleRegister) {
		User user = getLoginUser(request);
		String ruleId = ruleRegister.getId();
		if (user != null) {
			if (ruleId == null) {
				ruleRegister.setCp(user.getId());
				ruleRegister.setCpName(user.getUserName());
				ruleRegister.setCt(new Date());
			} else {
				ruleRegister.setEp(user.getId());
				ruleRegister.setEpName(user.getUserName());
				ruleRegister.setEt(new Date());
			}
		}

		ruleId = ruleRegisterService.saveRuleRegister(ruleRegister);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entityId", ruleId);
		return new RespBody(Status.OK, map);
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		DateFormat format = new SimpleDateFormat("HH:mm");
		CustomDateEditor dateEditor = new CustomDateEditor(format, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}
}
