/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-11-12</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.rule.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.entity.platform.rule.RuleFriedExpress;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.rule.service.RuleFriedExpressService;

/**
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: RuleFriedExpressController
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015-11-12
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/ruleFriedExpress")
public class RuleFriedExpressController extends BizBaseController<RuleFriedExpress, String> {

	private RuleFriedExpressService ruleFriedExpressService = SpringContextHolder.getBean(RuleFriedExpressService.class);

	@ResponseBody
	@RequestMapping(value = "/saveRuleFriedExpress", method = RequestMethod.POST)
	public RespBody saveRuleFriedExpress(HttpServletRequest request, RuleFriedExpress ruleFriedExpress) {
		User user = getLoginUser(request);
		String ruleFriedExpressId = ruleFriedExpress.getId();
		if (user != null) {
			if (StringUtils.isBlank(ruleFriedExpressId)) {
				ruleFriedExpress.setCp(user.getId());
				ruleFriedExpress.setCpName(user.getUserName());
				ruleFriedExpress.setCt(new Date());
			} else {
				ruleFriedExpress.setEp(user.getId());
				ruleFriedExpress.setEpName(user.getUserName());
				ruleFriedExpress.setEt(new Date());
			}
		}
		ruleFriedExpressService.saveRuleFriedExpress(ruleFriedExpress);
		ruleFriedExpressId = ruleFriedExpress.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entityId", ruleFriedExpressId);
		return new RespBody(Status.OK, map);
	}

	/* (non-Javadoc)
	 * @see com.yxw.framework.mvc.controller.BaseController#getService()
	 */
	@Override
	protected BaseService<RuleFriedExpress, String> getService() {
		return ruleFriedExpressService;
	}

}
