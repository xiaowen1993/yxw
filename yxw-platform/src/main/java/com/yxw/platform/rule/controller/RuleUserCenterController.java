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
import com.yxw.commons.entity.platform.rule.RuleUserCenter;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.rule.service.RuleUserCenterService;
import com.yxw.utils.StringUtils;

/**
 * 个人中心规则
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: RuleUserCenterController
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-10
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/ruleUserCenter")
public class RuleUserCenterController extends BizBaseController<RuleUserCenter, String> {
	private RuleUserCenterService ruleUserCenterService = SpringContextHolder.getBean(RuleUserCenterService.class);

	@Override
	protected BaseService<RuleUserCenter, String> getService() {
		// TODO Auto-generated method stub
		return ruleUserCenterService;
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
	@RequestMapping(value = "/saveRuleUserCenter", method = RequestMethod.POST)
	public RespBody saveRuleUserCenter(HttpServletRequest request, RuleUserCenter ruleUserCenter) {
		User user = getLoginUser(request);
		String ruleId = ruleUserCenter.getId();
		if (ruleUserCenter.getIndexContentArray().length > 0) {
			ruleUserCenter.setIndexContent(StringUtils.ArrayToStr(ruleUserCenter.getIndexContentArray()));
		}

		if (ruleUserCenter.getUserInfoContentArray().length > 0) {
			ruleUserCenter.setUserInfoContent(StringUtils.ArrayToStr(ruleUserCenter.getUserInfoContentArray()));
		}

		if (user != null) {
			if (ruleId == null) {
				ruleUserCenter.setCp(user.getId());
				ruleUserCenter.setCpName(user.getUserName());
				ruleUserCenter.setCt(new Date());
			} else {
				ruleUserCenter.setEp(user.getId());
				ruleUserCenter.setEpName(user.getUserName());
				ruleUserCenter.setEt(new Date());
			}
		}
		ruleId = ruleUserCenterService.saveRuleUserCenter(ruleUserCenter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entityId", ruleId);
		return new RespBody(Status.OK, map);
	}
}
