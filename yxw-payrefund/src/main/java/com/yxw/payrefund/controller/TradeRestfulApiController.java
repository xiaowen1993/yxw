package com.yxw.payrefund.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.CommonConstant;
import com.common.WechatConstant;
import com.wechat.WechatUtil;
import com.yxw.commons.entity.platform.payrefund.WechatPay;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefundResponse;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.payrefund.service.QueryService;
import com.yxw.payrefund.service.RefundService;
import com.yxw.payrefund.utils.CacheUtil;
import com.yxw.payrefund.utils.OrderUtil;
import com.yxw.payrefund.utils.RequestUtil;
import com.yxw.payrefund.utils.ResponseUtil;

@Controller
@RequestMapping(value = "/trade")
public class TradeRestfulApiController {

	private static Logger logger = LoggerFactory.getLogger(TradeRestfulApiController.class);

	private RefundService refundService = SpringContextHolder.getBean(RefundService.class);
	private QueryService queryService = SpringContextHolder.getBean(QueryService.class);

	@ResponseBody
	@RequestMapping("/payment")
	public RespBody payment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> requestParams = RequestUtil.getRequestParams(request);
		logger.info("payment.params: {}", requestParams);
		String tradeMode = requestParams.get("tradeMode");
		String tradeModeCode = ModeTypeUtil.getTradeModeCode(Integer.valueOf(tradeMode));

		// 剩余时间处理
		Date orderGeneratorTime = OrderNoGenerator.getDateTime(requestParams.get("orderNo"));
		Long passSecond = ( System.currentTimeMillis() - orderGeneratorTime.getTime() ) / 1000;
		String leftSecond = "";
		if (StringUtils.isNotBlank(requestParams.get("timeout"))) {
			leftSecond = String.valueOf(Integer.valueOf(requestParams.get("timeout")) - passSecond);
		}
		requestParams.put("timeout", leftSecond);

		if ("wechat".equalsIgnoreCase(tradeModeCode)) {
			WechatPay pay = JSONObject.parseObject(JSONObject.toJSONString(requestParams), WechatPay.class);

			if (pay.getComponentOauth2()) {
				return wechatPayIframeComponent(request, response, pay);
			} else {
				return wechatPay(request, pay);
			}

		} else if ("alipay".equalsIgnoreCase(tradeModeCode)) {
			return new RespBody(Status.PROMPT, "不支持的支付方式");
		} else if ("unionpay".equalsIgnoreCase(tradeModeCode)) {
			return new RespBody(Status.PROMPT, "不支持的支付方式");
		} else {
			return new RespBody(Status.PROMPT, "不支持的支付方式");
		}
	}

	@ResponseBody
	@RequestMapping("/wechatPay")
	public RespBody wechatPay(HttpServletRequest request, WechatPay pay) throws Exception {
		Map<String, Object> params = new HashMap<>();

		boolean status = false;

		String code = pay.getCode();
		String tradeMode = pay.getTradeMode();
		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(code, tradeMode);

		JSONObject jsonObject = CacheUtil.getPaySettingCache(code);

		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));
		logger.info("PaySettingCache jsonObject：{} ", JSONObject.toJSONString(jsonObject));

		if (vo != null || jsonObject != null) {

			if (jsonObject != null) {
				vo = JSONObject.toJavaObject(jsonObject, HospitalCodeAndAppVo.class);
				vo.setPaykey(jsonObject.getString("payKey"));
				vo.setRefundFilePath(jsonObject.getString("certificatePath"));
			}

			logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

			System.out.println("+++++test++++++" + request.getParameter("test"));
			if (StringUtils.isNotBlank(request.getParameter("test"))) {
				vo =
						JSONObject
								.parseObject(
										"{\"appCode\":\"innerChinaLife\",\"appId\":\"wx7245e798af225271\",\"appName\":\"内嵌国寿app\",\"appSecret\":\"0017026efd68ec789106f1556309e06e\",\"hospitalCode\":\"gzhszhyy\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalName\":\"广州市红十字会医院\",\"isSubPay\":0,\"mchId\":\"1227059102\",\"payModeCode\":\"wechat\",\"paySettingId\":\"045b22f359c84b24bb8a1a090b801343\",\"paykey\":\"abcdefghijklmnopqrstuvwxyz910629\",\"tradeMode\":\"21\"}",
										HospitalCodeAndAppVo.class);
			}

			if (WechatConstant.WECHAT_TEST) {
				pay.setTotalFee(WechatConstant.WECHAT_TEST_MONEY);
			}

			if (StringUtils.equals(vo.getAppId(), "wx7245e798af225271")) {//临时测试
				pay.setTotalFee(WechatConstant.WECHAT_TEST_MONEY);
			}

			if (StringUtils.isNotBlank(CommonConstant.PAY_VIEW_TYPE)) {
				pay.setViewType(CommonConstant.PAY_VIEW_TYPE);
			}

			JSONObject attach = new JSONObject();
			attach.put("code", code);
			attach.put("tradeMode", tradeMode);
			attach.put("isRestful", true);
			attach.put("notifyUrl", pay.getNotifyUrl());

			String ip = request.getRemoteAddr();

			Map<String, String> payParams = WechatUtil.genPayParams(pay, vo, WechatConstant.WECHAT_TRADE_TYPE_JSAPI, ip, attach);

			logger.info("订单号:{},统一下单请求数据：{}", new Object[] { pay.getOrderNo(), payParams });

			String unifiedOrderRes = WechatUtil.unifiedOrder(payParams);
			logger.info("订单号:{},微信统一支付接口返回:{}", new Object[] { pay.getOrderNo(), unifiedOrderRes });

			if (StringUtils.isNotBlank(unifiedOrderRes)) {
				Map<String, String> checkParams = WechatUtil.xml2Map(unifiedOrderRes);
				boolean checkSign = WechatUtil.checkSign(checkParams, vo.getPaykey());

				if (checkSign) {
					String returnCode = checkParams.get("return_code");
					String resultCode = checkParams.get("result_code");
					if ("SUCCESS".equalsIgnoreCase(returnCode)) {
						if ("SUCCESS".equalsIgnoreCase(resultCode)) {
							Map<String, String> wechatPayJSParams = new HashMap<String, String>();
							wechatPayJSParams.put("appId", vo.getAppId());
							wechatPayJSParams.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
							wechatPayJSParams.put("nonceStr", OrderUtil.genGUID("@guid32"));
							wechatPayJSParams.put("package", "prepay_id=" + checkParams.get("prepay_id"));
							wechatPayJSParams.put("signType", "MD5");
							String paySign = WechatUtil.getSign(wechatPayJSParams, vo.getPaykey());
							wechatPayJSParams.put("paySign", paySign);

							params.put("wechatPayJSParams", wechatPayJSParams);

							//========================================================================================
							//JSSDK 认证代码

							String url =
									String.valueOf(request.getRequestURL())
											+ ( StringUtils.isNotBlank(request.getQueryString()) ? "?" + request.getQueryString() : "" );

							String jsTicket = WechatUtil.getJSTicket(vo.getAppId(), vo.getAppSecret());
							Map<String, String> wechatJSSDKParams = WechatUtil.getJSSDKParams(vo.getAppId(), url, jsTicket);
							params.put("wechatJSSDKParams", wechatJSSDKParams);

							params.put("pay", pay);
							status = true;

						} else {
							params.put("resultMsg", checkParams.get("err_code").concat(" : ").concat(checkParams.get("err_code_des")));
							logger.error("订单号:{},接口调用失败，返回结果:{}", pay.getOrderNo(), params.get("resultMsg"));
						}
					} else {
						params.put("resultMsg", checkParams.get("err_code").concat(" : ").concat(checkParams.get("err_code_des")));
						logger.error("订单号:{},接口调用失败，返回结果:{}", pay.getOrderNo(), params.get("resultMsg"));
					}
				} else {
					params.put("resultMsg", "验证签名失败");
					logger.error("订单号:{},验证签名失败:{}", pay.getOrderNo(), checkSign);
				}
			} else {
				params.put("resultMsg", "微信统一支付接口返回null");
			}

		} else {
			params.put("resultMsg", "没有对应的支付配置");
		}

		if (status) {
			return new RespBody(Status.OK, params);
		} else {
			return new RespBody(Status.PROMPT, params);
		}
	}

	@ResponseBody
	@RequestMapping("/wechatPay.iframe.component")
	public RespBody wechatPayIframeComponent(HttpServletRequest request, HttpServletResponse response, WechatPay pay) throws Exception {
		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(pay.getCode(), pay.getTradeMode());

		System.out.println("+++++test++++++" + request.getParameter("test"));
		if (StringUtils.isNotBlank(request.getParameter("test"))) {
			vo =
					JSONObject
							.parseObject(
									"{\"appCode\":\"innerChinaLife\",\"appId\":\"wx7245e798af225271\",\"appName\":\"内嵌国寿app\",\"appSecret\":\"0017026efd68ec789106f1556309e06e\",\"hospitalCode\":\"gzhszhyy\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalName\":\"广州市红十字会医院\",\"isSubPay\":0,\"mchId\":\"1227059102\",\"payModeCode\":\"wechat\",\"paySettingId\":\"045b22f359c84b24bb8a1a090b801343\",\"paykey\":\"abcdefghijklmnopqrstuvwxyz910629\",\"tradeMode\":\"21\"}",
									HospitalCodeAndAppVo.class);
		}

		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		String code = request.getParameter("code");
		String state = request.getParameter("state");

		String openId = "";

		if (StringUtils.isNotBlank(code) || StringUtils.isNotBlank(pay.getOpenId())) {
			if (StringUtils.isBlank(openId)) {
				logger.info("getOpenId.code: {}", code);

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

			pay.setOpenId(openId);

			return wechatPay(request, pay);

		} else {
			Map<String, String> requestParams = RequestUtil.getRequestParams(request);

			String url = String.valueOf(request.getRequestURL());
			String queryString = request.getQueryString();
			if (StringUtils.isBlank(queryString)) {
				queryString = getUrlParamsByMap(requestParams);
			}

			if (StringUtils.isNotBlank(requestParams.get("test"))) {
				queryString = getUrlParamsByMap(requestParams);
			}

			String redirectUrl = url.concat("?").concat(queryString);
			logger.info("redirectUrl: {}", redirectUrl);
			String oauth2Url = WechatUtil.getComponentOAuth2(vo.getAppId(), WechatConstant.WECHAT_COMPONENT_APPID, redirectUrl);
			logger.info("oauth2Url: {}", oauth2Url);
			ResponseUtil.redirect(response, oauth2Url);

			return null;
		}
	}

	public static String getUrlParamsByMap(Map<String, String> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = StringUtils.substringBeforeLast(s, "&");
		}
		return s;
	}

	@ResponseBody
	@RequestMapping("/wechatRefund")
	public RespBody wechatRefund(HttpServletRequest request, WechatPayRefund refund) throws Exception {

		/*WechatPayRefund refund = new WechatPayRefund();
		refund.setCode(hospitalCode);
		refund.setTradeMode(tradeMode);
		refund.setRefundOrderNo(refundOrderNo);
		refund.setAgtOrderNo(agtOrderNo);
		refund.setOrderNo(orderNo);
		refund.setTotalFee(totalFee);
		refund.setRefundFee(refundFee);
		refund.setRefundDesc(refundDesc);*/

		WechatPayRefundResponse refundResponse = refundService.wechatPayRefund(refund);

		return new RespBody(Status.OK, refundResponse);

	}

	@ResponseBody
	@RequestMapping("/wechatOrderQuery")
	private RespBody wechatOrderQuery(HttpServletRequest request, WechatPayOrderQuery orderQuery) {
		/*Response response = new Response();

		WechatPayOrderQuery orderQuery = new WechatPayOrderQuery();
		orderQuery.setCode(hospitalCode);
		orderQuery.setTradeMode(tradeMode);
		orderQuery.setAgtOrderNo(agtOrderNo);
		orderQuery.setOrderNo(orderNo);*/

		WechatPayOrderQueryResponse orderQueryResponse = queryService.wechatPayOrderQuery(orderQuery);

		return new RespBody(Status.OK, orderQueryResponse);
	}
}
