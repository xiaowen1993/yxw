package com.yxw.payrefund.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alipay.AlipayUtil;
import com.common.AlipayConstant;
import com.common.CommonConstant;
import com.common.UnionPayConstant;
import com.common.WechatConstant;
import com.wechat.WechatUtil;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.entity.platform.payrefund.Alipay;
import com.yxw.commons.entity.platform.payrefund.Pay;
import com.yxw.commons.entity.platform.payrefund.WechatPay;
import com.yxw.commons.entity.platform.payrefund.WechatPayAsynResponse;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.payrefund.service.impl.PayServiceImpl;
import com.yxw.payrefund.utils.CacheUtil;
import com.yxw.payrefund.utils.ResponseUtil;

@Controller
@RequestMapping(value = "/pay/test")
public class PayTestController {

	private static Logger logger = LoggerFactory.getLogger(PayTestController.class);

	private PayServiceImpl payService = SpringContextHolder.getBean(PayServiceImpl.class);

	@RequestMapping("/wechatPay")
	public ModelAndView wechatPay(HttpServletRequest request, String totalFee) throws HttpException, IOException {
		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache("zsdxdsfsyy", "21");
		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		if (StringUtils.isBlank(totalFee)) {
			totalFee = WechatConstant.WECHAT_TEST_MONEY;
		}

		Map<String, Object> params = new HashMap<>();
		params.put("totalFee", totalFee);

		String url =
				String.valueOf(request.getRequestURL())
						+ ( StringUtils.isNotBlank(request.getQueryString()) ? "?" + request.getQueryString() : "" );

		String jsTicket = WechatUtil.getJSTicket(vo.getAppId(), vo.getAppSecret());
		Map<String, String> wechatJSSDKParams = WechatUtil.getJSSDKParams(vo.getAppId(), url, jsTicket);
		logger.info("wechatJSSDKParams: {}", wechatJSSDKParams);

		params.put("wechatJSSDKParams", wechatJSSDKParams);

		return new ModelAndView("/test/wechatPay.test", "params", params);
	}

	@RequestMapping("/wechatPay.iframe")
	public ModelAndView wechatPayIFRAME(HttpServletRequest request, HttpServletResponse response, String totalFee, String openId,
			String code) throws IOException {
		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache("zsdxdsfsyy", String.valueOf(BizConstant.MODE_TYPE_INNER_WECHAT_VAL));
		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		if (StringUtils.isNotBlank(code) || StringUtils.isNotBlank(openId)) {
			if (StringUtils.isBlank(openId)) {
				logger.info("getOpenId.code: {}", code);

				openId = WechatUtil.getOpenId(vo.getAppId(), vo.getAppSecret(), code);
				logger.info("用code换取到openId: " + openId);
			}

			Map<String, String> params = new HashMap<>();

			String testOrderNo =
					OrderNoGenerator.genOrderNo(String.valueOf(BizConstant.MODE_TYPE_INNER_WECHAT_VAL),
							String.valueOf(TradeConstant.TRADE_MODE_APP_WECHAT_VAL), Integer.valueOf(OrderConstant.ORDER_TYPE_PAYMENT), 9,
							"99", openId);

			params.put("openId", openId);
			params.put("orderNo", testOrderNo);
			params.put("totalFee", totalFee);
			params.put("showTotalFee", AlipayUtil.f2y(totalFee));

			return new ModelAndView("/test/wechatPay.test.iframe", params);
		} else {
			String url = String.valueOf(request.getRequestURL());
			String queryString = request.getQueryString();

			String redirectUrl = url.concat("?").concat(queryString);
			logger.info("redirectUrl: {}", redirectUrl);
			String oauth2Url = WechatUtil.getOAuth2(vo.getAppId(), redirectUrl);
			logger.info("oauth2Url: {}", oauth2Url);
			ResponseUtil.redirect(response, oauth2Url);

			return null;
		}
	}

	@RequestMapping("/wechatPay.iframe.component")
	public ModelAndView wechatPayIFRAMEComponent(HttpServletRequest request, HttpServletResponse response, String totalFee, String openId,
			String code) throws IOException {
		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache("zsdxdsfsyy", String.valueOf(BizConstant.MODE_TYPE_INNER_WECHAT_VAL));
		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		if (StringUtils.isNotBlank(code) || StringUtils.isNotBlank(openId)) {
			if (StringUtils.isBlank(openId)) {
				logger.info("getOpenId.code: {}", code);

				String state = request.getParameter("state");
				if (StringUtils.equals(WechatConstant.WECHAT_COMPONENT_OAUTH2_STATE, state)) {
					String wechatComponentVerifyTicket =
							WechatConstant.wechatComponentVerifyTicketMap.get(WechatConstant.WECHAT_COMPONENT_APPID);
					if (StringUtils.isBlank(wechatComponentVerifyTicket)) {
						wechatComponentVerifyTicket = CacheUtil.getWechatComponentVerifyTicket(WechatConstant.WECHAT_COMPONENT_APPID);
					}

					String componentAccessToken =
							WechatUtil.getComponentAccessToken(WechatConstant.WECHAT_COMPONENT_APPID,
									WechatConstant.WECHAT_COMPONENT_APPSECRET, wechatComponentVerifyTicket);
					logger.info("componentAccessToken: {}", componentAccessToken);

					openId =
							WechatUtil.getOpenIdByComponent(vo.getAppId(), code, WechatConstant.WECHAT_COMPONENT_APPID,
									componentAccessToken);
				} else {
					openId = WechatUtil.getOpenId(vo.getAppId(), vo.getAppSecret(), code);
				}
				logger.info("用code换取到openId: " + openId);
			}

			Map<String, String> params = new HashMap<>();

			String testOrderNo =
					OrderNoGenerator.genOrderNo(String.valueOf(BizConstant.MODE_TYPE_INNER_WECHAT_VAL),
							String.valueOf(TradeConstant.TRADE_MODE_APP_WECHAT_VAL), Integer.valueOf(OrderConstant.ORDER_TYPE_PAYMENT), 9,
							"99", openId);

			params.put("openId", openId);
			params.put("orderNo", testOrderNo);
			params.put("totalFee", totalFee);
			params.put("showTotalFee", AlipayUtil.f2y(totalFee));

			return new ModelAndView("/test/wechatPay.test.iframe", params);
		} else {
			String url = String.valueOf(request.getRequestURL());
			String queryString = request.getQueryString();

			String redirectUrl = url.concat("?").concat(queryString);
			logger.info("redirectUrl: {}", redirectUrl);
			String oauth2Url = WechatUtil.getComponentOAuth2(vo.getAppId(), WechatConstant.WECHAT_COMPONENT_APPID, redirectUrl);
			logger.info("oauth2Url: {}", oauth2Url);
			ResponseUtil.redirect(response, oauth2Url);

			return null;
		}
	}

	@RequestMapping("/alipay")
	public ModelAndView alipay(HttpServletRequest request, String totalFee) {
		if (StringUtils.isBlank(totalFee)) {
			totalFee = AlipayConstant.ALIPAY_TEST_MONEY;
		}

		Map<String, String> params = new HashMap<>();
		params.put("totalFee", totalFee);

		return new ModelAndView("/test/alipay.test", params);
	}

	@RequestMapping("/alipay.iframe")
	public ModelAndView alipayIFRAME(HttpServletRequest request, HttpServletResponse response, String totalFee) throws IOException {
		Map<String, String> params = new HashMap<>();

		String testOrderNo =
				OrderNoGenerator.genOrderNo(String.valueOf(BizConstant.MODE_TYPE_INNER_ALIPAY_VAL),
						String.valueOf(TradeConstant.TRADE_MODE_APP_ALIPAY_VAL), Integer.valueOf(OrderConstant.ORDER_TYPE_PAYMENT), 9,
						"99", "");

		params.put("orderNo", testOrderNo);
		params.put("totalFee", totalFee);
		params.put("showTotalFee", AlipayUtil.f2y(totalFee));

		return new ModelAndView("/test/alipay.test.iframe", params);
	}

	@RequestMapping("/unionpay")
	public ModelAndView unionpay(HttpServletRequest request, String totalFee) {
		if (StringUtils.isBlank(totalFee)) {
			totalFee = UnionPayConstant.UNIONPAY_TEST_MONEY;
		}

		Map<String, String> params = new HashMap<>();
		params.put("totalFee", totalFee);

		return new ModelAndView("/test/unionpay.test", params);
	}

	@RequestMapping("/unionpay.iframe")
	public ModelAndView unionpayIFRAME(HttpServletRequest request, String totalFee) {
		Map<String, String> params = new HashMap<>();

		String testOrderNo =
				OrderNoGenerator.genOrderNo(String.valueOf(BizConstant.MODE_TYPE_INNER_UNIONPAY_VAL),
						String.valueOf(TradeConstant.TRADE_MODE_APP_UNIONPAY_VAL), Integer.valueOf(OrderConstant.ORDER_TYPE_PAYMENT), 9,
						"99", "a89f3b785148445b8b7f99474a8b378a");

		params.put("orderNo", testOrderNo);
		params.put("totalFee", totalFee);
		params.put("showTotalFee", AlipayUtil.f2y(totalFee));

		return new ModelAndView("/test/unionpay.test.iframe", params);
	}

	@RequestMapping("/unionpay.jsonp")
	public ModelAndView unionpayJSONP(HttpServletRequest request, String totalFee) {
		Map<String, String> params = new HashMap<>();

		String testOrderNo =
				OrderNoGenerator.genOrderNo(String.valueOf(BizConstant.MODE_TYPE_INNER_UNIONPAY_VAL),
						String.valueOf(TradeConstant.TRADE_MODE_APP_UNIONPAY_VAL), Integer.valueOf(OrderConstant.ORDER_TYPE_PAYMENT), 9,
						"99", "a89f3b785148445b8b7f99474a8b378a");

		params.put("orderNo", testOrderNo);
		params.put("totalFee", totalFee);
		params.put("showTotalFee", AlipayUtil.f2y(totalFee));

		return new ModelAndView("/test/unionpay.test.jsonp", params);
	}

	@RequestMapping("/info")
	public ModelAndView info(HttpServletRequest request) {
		return new ModelAndView("/test/unionpay.test.info");
	}

	@RequestMapping("/success")
	public ModelAndView success(HttpServletRequest request) {
		return new ModelAndView("/test/unionpay.test.success");
	}

	//?hospitalCode=zsdxdsfsyy&tradeMode=9&totalFee=1&body=test
	@RequestMapping("/qrCodePay")
	public void qrCodePay(HttpServletRequest request, HttpServletResponse response, Pay pay) throws Exception {
		if (String.valueOf(TradeConstant.TRADE_MODE_WECHATNATIVE_VAL).equalsIgnoreCase(pay.getTradeMode())) {
			String testOrderNo =
					OrderNoGenerator.genOrderNo(String.valueOf(TradeConstant.TRADE_MODE_WECHATNATIVE_VAL),
							String.valueOf(TradeConstant.TRADE_MODE_WECHATNATIVE_VAL), Integer.valueOf(OrderConstant.ORDER_TYPE_PAYMENT),
							9, "99", "");

			pay.setOrderNo(testOrderNo);

			wechatNative(request, response, pay);
		} else if (String.valueOf(TradeConstant.TRADE_MODE_ALIPAYNATIVE_VAL).equalsIgnoreCase(pay.getTradeMode())) {
			String testOrderNo =
					OrderNoGenerator.genOrderNo(String.valueOf(TradeConstant.TRADE_MODE_ALIPAYNATIVE_VAL),
							String.valueOf(TradeConstant.TRADE_MODE_ALIPAYNATIVE_VAL), Integer.valueOf(OrderConstant.ORDER_TYPE_PAYMENT),
							9, "99", "");

			pay.setOrderNo(testOrderNo);

			alipayNative(request, response, pay);
		} else if (String.valueOf(TradeConstant.TRADE_MODE_WECHATMICRO_VAL).equalsIgnoreCase(pay.getTradeMode())) {
		} else if (String.valueOf(TradeConstant.TRADE_MODE_ALIPAYMICRO_VAL).equalsIgnoreCase(pay.getTradeMode())) {
		}
	}

	public void alipayNative(HttpServletRequest request, HttpServletResponse response, Pay pay) throws Exception {
		Alipay alipay = new Alipay();
		BeanUtils.copyProperties(pay, alipay);
		logger.info("支付宝支付参数：{}.", JSONObject.toJSONString(pay));

		JSONObject res = payService.alipayNativeService(alipay);

		String codeUrl = res.getString("codeUrl");
		if (org.apache.commons.lang3.StringUtils.isNotBlank(codeUrl)) {
			ResponseUtil.printXml(response, "<xml><code>0</code><msg>OK</msg><codeUrl>" + codeUrl + "</codeUrl></xml>");
		} else {
			ResponseUtil.printXml(response, "<xml><code>1</code><msg>" + res.getString("errorMsg") + "</msg><codeUrl></codeUrl></xml>");
		}

	}

	public void wechatNative(HttpServletRequest request, HttpServletResponse response, Pay pay) throws Exception {
		WechatPay wechatPay = new WechatPay();
		BeanUtils.copyProperties(pay, wechatPay);
		logger.info("微信支付参数：{}.", JSONObject.toJSONString(pay));

		JSONObject res = payService.wechatNativeService(wechatPay);

		String codeUrl = res.getString("codeUrl");
		if (org.apache.commons.lang3.StringUtils.isNotBlank(codeUrl)) {
			ResponseUtil.printXml(response, "<xml><code>0</code><msg>OK</msg><codeUrl>" + codeUrl + "</codeUrl></xml>");
		} else {
			ResponseUtil.printXml(response, "<xml><code>1</code><msg>" + res.getString("errorMsg") + "</msg><codeUrl></codeUrl></xml>");
		}

	}

	@ResponseBody
	@RequestMapping("/wechatMicroPay")
	public Map<String, Object> wechatMicroPay(HttpServletRequest request, String authCode) throws HttpException, IOException,
			ParseException, DocumentException {
		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache("zsdxdsfsyy", String.valueOf(TradeConstant.TRADE_MODE_WECHATMICRO_VAL));
		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		WechatPay pay = new WechatPay();

		String testOrderNo =
				OrderNoGenerator.genOrderNo(String.valueOf(TradeConstant.TRADE_MODE_WECHATMICRO_VAL),
						String.valueOf(TradeConstant.TRADE_MODE_WECHATMICRO_VAL), Integer.valueOf(OrderConstant.ORDER_TYPE_PAYMENT), 9,
						"99", "");

		pay.setOrderNo(testOrderNo);
		pay.setTotalFee(WechatConstant.WECHAT_TEST_MONEY);
		pay.setAuthCode(authCode);
		pay.setBody("test");

		JSONObject attach = new JSONObject();
		attach.put("hospitalCode", vo.getHospitalCode());
		attach.put("tradeMode", vo.getTradeMode());

		Map<String, String> wechatMicroPayParams = WechatUtil.genMicroPayParams(pay, vo, CommonConstant.SERVER_IP, attach);
		String microPayResponse = WechatUtil.microPay(wechatMicroPayParams);
		logger.info("microPayResponse: {}", microPayResponse);

		WechatPayAsynResponse wechatPayAsynResponse = WechatUtil.map2WechatPayAsynResponse(WechatUtil.xml2Map(microPayResponse));
		wechatPayAsynResponse.setOrderNo(testOrderNo);

		Map<String, Object> response = new HashMap<>();
		response.put("result", wechatPayAsynResponse);

		wechatPayAsynResponse.setAttach(null);
		response.put("fmtData", JSONObject.toJSONString(wechatPayAsynResponse, true));

		return response;

	}

	@RequestMapping("/jsonp")
	public ModelAndView jsonp(HttpServletRequest request) {
		return new ModelAndView("/test/jsonp.test");
	}

	@RequestMapping("/jsonpServer")
	public void jsonpServer(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String jsonpCallback = request.getParameter("callback");//客户端请求参数
		String orderNo = request.getParameter("orderNo");//客户端请求参数
		String openId = request.getParameter("openId");//客户端请求参数
		logger.info("jsonpCallback：{}，orderNo：{}，openId：{}", jsonpCallback, orderNo, openId);

		JSONObject jsonData = new JSONObject();
		String html = "";
		html += "<div class=\"box-list\">";
		html += "<ul class=\"yx-list flex\">";
		html += "<li>";
		html += "<div class=\"label\">名称</div>";
		html += "<div class=\"values color666\">JSONP 测试</div>";
		html += "</li>";
		html += "<li>";
		html += "<div class=\"label\">价格</div>";
		html += "<div class=\"values color666\">";
		html += "<span class=\"price\">0.01</span>";
		html += "</div>";
		html += "</li>";
		html += "</ul>";
		html += "</div>";
		jsonData.put("message", html);

		ResponseUtil.printPlain(response, jsonpCallback + "(" + jsonData.toJSONString() + ")");

	}

}
