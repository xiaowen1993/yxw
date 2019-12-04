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
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.rule.service.RuleQueryService;
import com.yxw.utils.StringUtils;

/**
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: RuleQueryController
 * @Statement: <p>
 *             查询规则
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
@RequestMapping("/sys/ruleQuery")
public class RuleQueryController extends BizBaseController<RuleQuery, String> {

	private RuleQueryService ruleQueryService = SpringContextHolder.getBean(RuleQueryService.class);

	@Override
	protected BaseService<RuleQuery, String> getService() {
		// TODO Auto-generated method stub
		return this.ruleQueryService;
	}

	/**
	 * @param request
	 * @param ruleQuery
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRuleQuery", method = RequestMethod.POST)
	public RespBody saveRuleQuery(HttpServletRequest request, RuleQuery ruleQuery) {
		User user = getLoginUser(request);
		String ruleQueryId = ruleQuery.getId();

		if (ruleQuery.getBespeakRecordQueryTypeArray().length > 0) {
			ruleQuery.setBespeakRecordQueryType(StringUtils.ArrayToStr(ruleQuery.getBespeakRecordQueryTypeArray()));
		}

		if (ruleQuery.getPaymentRecordQueryTypeArray().length > 0) {
			ruleQuery.setPaymentRecordQueryType(StringUtils.ArrayToStr(ruleQuery.getPaymentRecordQueryTypeArray()));
		}

		if (ruleQuery.getReportCouldQueryTypeArray().length > 0) {
			ruleQuery.setReportCouldQueryType(StringUtils.ArrayToStr(ruleQuery.getReportCouldQueryTypeArray()));
		}

		if (ruleQuery.getQueueTypeArray().length > 0) {
			ruleQuery.setQueueType(StringUtils.ArrayToStr(ruleQuery.getQueueTypeArray()));
		}

		if (user != null) {
			if (ruleQueryId == null) {
				ruleQuery.setCp(user.getId());
				ruleQuery.setCpName(user.getUserName());
				ruleQuery.setCt(new Date());
			} else {
				ruleQuery.setEp(user.getId());
				ruleQuery.setEpName(user.getUserName());
				ruleQuery.setEt(new Date());
			}
		}
		ruleQueryService.saveRuleQuery(ruleQuery);
		ruleQueryId = ruleQuery.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entityId", ruleQueryId);
		return new RespBody(Status.OK, map);
	}
}
