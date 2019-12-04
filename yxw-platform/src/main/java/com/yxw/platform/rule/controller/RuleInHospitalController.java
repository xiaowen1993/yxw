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
import com.yxw.commons.entity.platform.rule.RuleInHospital;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.rule.service.RuleInHospitalService;

/**
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: RuleEditController
 * @Statement: <p>
 *            住院规则配置
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-05-27
 */
@Controller
@RequestMapping("/sys/ruleInHospital")
public class RuleInHospitalController extends BizBaseController<RuleInHospital, String> {

	private RuleInHospitalService ruleInHospitalService = SpringContextHolder.getBean(RuleInHospitalService.class);

	@Override
	protected BaseService<RuleInHospital, String> getService() {

		return this.ruleInHospitalService;
	}

	/**
	 * @param request
	 * @param ruleRegister
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRuleInHospital", method = RequestMethod.POST)
	public RespBody saveRuleInHospital(HttpServletRequest request, RuleInHospital ruleInHospital) {
		User user = getLoginUser(request);
		String ruleId = ruleInHospital.getId();
		if (user != null) {
			if (ruleId == null) {
				ruleInHospital.setCp(user.getId());
				ruleInHospital.setCpName(user.getUserName());
				ruleInHospital.setCt(new Date());
			} else {
				ruleInHospital.setEp(user.getId());
				ruleInHospital.setEpName(user.getUserName());
				ruleInHospital.setEt(new Date());
			}
		}

		ruleId = ruleInHospitalService.saveRuleInHospital(ruleInHospital);
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
