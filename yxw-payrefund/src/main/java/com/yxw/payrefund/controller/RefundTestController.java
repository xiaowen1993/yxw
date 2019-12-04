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
import com.common.UnionPayConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefund;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefundResponse;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.payrefund.service.RefundService;
import com.yxw.payrefund.service.impl.RefundServiceImpl;

@Controller
@RequestMapping(value = "/refund/test")
public class RefundTestController {

	private static Logger logger = LoggerFactory.getLogger(RefundTestController.class);

	private RefundService refundService = SpringContextHolder.getBean(RefundServiceImpl.class);

	@RequestMapping("/unionpayRefundView")
	public ModelAndView unionpayRefundView(HttpServletRequest request, UnionpayRefund refund) {
		if (StringUtils.isBlank(refund.getCode())) {
			refund.setCode("zsdxdsfsyy");
		}
		if (StringUtils.isBlank(refund.getTotalFee())) {
			refund.setTotalFee(UnionPayConstant.UNIONPAY_TEST_MONEY);
		}
		if (StringUtils.isBlank(refund.getRefundFee())) {
			refund.setRefundFee(UnionPayConstant.UNIONPAY_TEST_MONEY);
		}

		return new ModelAndView("/test/unionpay.refund.test", "params", refund);
	}

	@ResponseBody
	@RequestMapping("/unionpayRefund")
	public JSONObject unionpayRefund(HttpServletRequest request, UnionpayRefund refund) {
		refund.setTradeMode(String.valueOf(TradeConstant.TRADE_MODE_APP_UNIONPAY_VAL));
		refund.setRefundOrderNo(OrderNoGenerator.genOrderNo(String.valueOf(TradeConstant.TRADE_MODE_APP_UNIONPAY_VAL),
				String.valueOf(TradeConstant.TRADE_MODE_APP_UNIONPAY_VAL), Integer.valueOf(OrderConstant.ORDER_TYPE_REFUND), 9, "99", "999"));
		logger.info("UnionpayRefund: {}", JSONObject.toJSONString(refund));

		UnionpayRefundResponse unionpayRefund = refundService.unionpayRefund(refund);

		JSONObject res = new JSONObject();
		res.put("fmtData", JSONObject.toJSONString(unionpayRefund, true));
		return res;
	}

}
