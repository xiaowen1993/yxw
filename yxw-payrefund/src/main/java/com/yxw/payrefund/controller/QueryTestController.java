package com.yxw.payrefund.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQueryResponse;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.payrefund.service.QueryService;
import com.yxw.payrefund.service.impl.QueryServiceImpl;

@Controller
@RequestMapping(value = "/query/test")
public class QueryTestController {

	private static Logger logger = LoggerFactory.getLogger(QueryTestController.class);

	private QueryService queryService = SpringContextHolder.getBean(QueryServiceImpl.class);

	@RequestMapping("/unionpayQueryView")
	public ModelAndView unionpayQueryView(HttpServletRequest request, UnionpayOrderQuery orderQuery) {
		if (StringUtils.isBlank(orderQuery.getCode())) {
			orderQuery.setCode("zsdxdsfsyy");
		}

		return new ModelAndView("/test/unionpay.query.test", "params", orderQuery);
	}

	@ResponseBody
	@RequestMapping("/unionpayQuery")
	public JSONObject unionpayQuery(HttpServletRequest request, UnionpayOrderQuery orderQuery) {
		orderQuery.setTradeMode(String.valueOf(TradeConstant.TRADE_MODE_APP_UNIONPAY_VAL));
		logger.info("UnionpayOrderQuery: {}", JSONObject.toJSONString(orderQuery));

		UnionpayOrderQueryResponse orderQueryResponse = queryService.unionpayOrderQuery(orderQuery);

		JSONObject res = new JSONObject();
		res.put("fmtData", JSONObject.toJSONString(orderQueryResponse, true));
		return res;
	}

	@ResponseBody
	@RequestMapping("/wechatPayQuery")
	public JSONObject wechatPayQuery(HttpServletRequest request, WechatPayOrderQuery orderQuery) {
		orderQuery.setCode("zsdxdsfsyy");
		orderQuery.setTradeMode(String.valueOf(TradeConstant.TRADE_MODE_WECHATMICRO_VAL));
		logger.info("WechatPayOrderQuery: {}", JSONObject.toJSONString(orderQuery));

		WechatPayOrderQueryResponse orderQueryResponse = queryService.wechatPayOrderQuery(orderQuery);

		JSONObject res = new JSONObject();

		res.put("result", orderQueryResponse);

		orderQueryResponse.setAttach(null);
		res.put("fmtData", JSONObject.toJSONString(orderQueryResponse, true));
		return res;
	}

}
