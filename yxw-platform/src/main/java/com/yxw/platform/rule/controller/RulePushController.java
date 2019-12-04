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
import com.yxw.commons.entity.platform.rule.RulePush;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.rule.service.RulePushService;
import com.yxw.utils.StringUtils;

/**
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: RuleEditController
 * @Statement: <p>
 *             推送配置
 *             </p>
 * @Author: luob
 * @Create Date: 2015-7-3
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/rulePush")
public class RulePushController extends BizBaseController<RulePush, String> {

	private RulePushService rulePushService = SpringContextHolder.getBean(RulePushService.class);

	@Override
	protected BaseService<RulePush, String> getService() {
		return this.rulePushService;
	}

	/**
	 * @param request
	 * @param ruleEdit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRulePush", method = RequestMethod.POST)
	public RespBody saveRulePush(HttpServletRequest request, RulePush rulePush) {
		User user = getLoginUser(request);
		String rulePushId = rulePush.getId();
		
		if (rulePush.getModeArray().length > 0) {
			rulePush.setPushModes(StringUtils.ArrayToStr(rulePush.getModeArray()));
		}
		
		if (user != null) {
			if (rulePushId == null) {
				rulePush.setCp(user.getId());
				rulePush.setCpName(user.getUserName());
				rulePush.setCt(new Date());
			} else {
				rulePush.setEp(user.getId());
				rulePush.setEpName(user.getUserName());
				rulePush.setEt(new Date());
			}
		}
		rulePushService.saveRulePush(rulePush);
		rulePushId = rulePush.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entityId", rulePushId);
		return new RespBody(Status.OK, map);
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		DateFormat format = new SimpleDateFormat("HH:mm");
		CustomDateEditor dateEditor = new CustomDateEditor(format, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}
}
