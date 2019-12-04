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

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.platform.rule.service.RuleHisDataService;

/**
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: RuleHisDataController
 * @Statement: <p>
 *             his基础数据获取规则配置
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
@RequestMapping("/sys/ruleHisData")
public class RuleHisDataController extends BizBaseController<RuleHisData, String> {

	private RuleHisDataService ruleHisDataService = SpringContextHolder.getBean(RuleHisDataService.class);

	@Override
	protected BaseService<RuleHisData, String> getService() {
		// TODO Auto-generated method stub
		return this.ruleHisDataService;

	}

	/**
	 * @param request
	 * @param ruleRegister
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRuleHisData", method = RequestMethod.POST)
	public RespBody saveRuleHisData(HttpServletRequest request, RuleHisData ruleHisData) {
		User user = getLoginUser(request);
		String ruleId = ruleHisData.getId();
		if (user != null) {
			if (ruleId == null) {
				ruleHisData.setCp(user.getId());
				ruleHisData.setCpName(user.getUserName());
				ruleHisData.setCt(new Date());
			} else {
				ruleHisData.setEp(user.getId());
				ruleHisData.setEpName(user.getUserName());
				ruleHisData.setEt(new Date());
			}
		}

		ruleId = ruleHisDataService.saveRuleHisData(ruleHisData);
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
