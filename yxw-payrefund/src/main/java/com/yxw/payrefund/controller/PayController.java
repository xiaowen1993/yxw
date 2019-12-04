package com.yxw.payrefund.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alipay.AlipayUtil;
import com.alipay.api.internal.util.AlipaySignature;
import com.common.AlipayConstant;
import com.common.CommonConstant;
import com.common.UnionPayConstant;
import com.common.WechatConstant;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.UnionPayUtil;
import com.wechat.WechatUtil;
import com.yxw.commons.entity.platform.payrefund.Alipay;
import com.yxw.commons.entity.platform.payrefund.AlipayAsynResponse;
import com.yxw.commons.entity.platform.payrefund.Unionpay;
import com.yxw.commons.entity.platform.payrefund.UnionpayAsynResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPay;
import com.yxw.commons.entity.platform.payrefund.WechatPayAsynResponse;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.payrefund.thread.PayAsynResponseRunnable;
import com.yxw.payrefund.utils.CacheUtil;
import com.yxw.payrefund.utils.OrderUtil;
import com.yxw.payrefund.utils.RequestUtil;
import com.yxw.payrefund.utils.ResponseUtil;

@Controller
@RequestMapping(value = "/pay")
public class PayController {

	private static Logger logger = LoggerFactory.getLogger(PayController.class);

	@RequestMapping("/error")
	public ModelAndView error(String errorMsg) {
		Map<String, String> params = new HashMap<>();
		params.put("resultMsg", errorMsg);
		return new ModelAndView("/pay/error", "params", params);
	}

	@RequestMapping("/payment")
	public ModelAndView payment(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
			Alipay pay = JSONObject.parseObject(JSONObject.toJSONString(requestParams), Alipay.class);
			return alipay(request, pay);
		} else if ("unionpay".equalsIgnoreCase(tradeModeCode)) {
			Unionpay pay = JSONObject.parseObject(JSONObject.toJSONString(requestParams), Unionpay.class);
			return unionpay(request, pay);
		} else {
			return error("不支持的支付方式");
		}
	}

	@RequestMapping("/wechatPay")
	public ModelAndView wechatPay(HttpServletRequest request, WechatPay pay) throws Exception {
		Map<String, Object> params = new HashMap<>();

		String hospitalCode = pay.getCode();
		String tradeMode = pay.getTradeMode();
		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(hospitalCode, tradeMode);

		System.out.println("+++++test++++++" + request.getParameter("test"));
		if (StringUtils.isNotBlank(request.getParameter("test"))) {
			vo =
					JSONObject
							.parseObject(
									"{\"appCode\":\"innerChinaLife\",\"appId\":\"wx7245e798af225271\",\"appName\":\"内嵌国寿app\",\"appSecret\":\"0017026efd68ec789106f1556309e06e\",\"hospitalCode\":\"gzhszhyy\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalName\":\"广州市红十字会医院\",\"isSubPay\":0,\"mchId\":\"1227059102\",\"payModeCode\":\"wechat\",\"paySettingId\":\"045b22f359c84b24bb8a1a090b801343\",\"paykey\":\"abcdefghijklmnopqrstuvwxyz910629\",\"tradeMode\":\"21\"}",
									HospitalCodeAndAppVo.class);
		}

		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));
		if (vo != null) {

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
			attach.put("code", hospitalCode);
			attach.put("tradeMode", tradeMode);

			String ip = request.getRemoteAddr();
			//			Map<String, String> payParams = WechatUtil.genPayParams(pay, vo.getAppId(), vo.getMchId(), vo.getSubMchId(), 
			//					vo.getPaykey(), WechatConstant.WECHAT_TRADE_TYPE_JSAPI, ip, attach);

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

							ModelAndView modelAndView = new ModelAndView("/pay/wechatPay");

							if ("iframe".equalsIgnoreCase(pay.getViewType())) {
								modelAndView = new ModelAndView("/pay/wechatPay.iframe");
							} else if ("jsonp".equalsIgnoreCase(pay.getViewType())) {
								modelAndView = new ModelAndView("/pay/wechatPay.jsonp");
							}

							params.put("pay", pay);

							modelAndView.getModel().put("params", params);

							return modelAndView;

						} else {
							params.put("resultMsg", checkParams.get("err_code").concat(" : ").concat(checkParams.get("err_code_des")));
							logger.error("订单号:{},接口调用失败，返回结果:{}", pay.getOrderNo(), params.get("resultMsg"));
						}
					} else {

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

		return error(String.valueOf(params.get("resultMsg")));
	}

	@RequestMapping("/wechatPay.iframe.component")
	public ModelAndView wechatPayIframeComponent(HttpServletRequest request, HttpServletResponse response, WechatPay pay) throws Exception {
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

	@RequestMapping("/alipay")
	public ModelAndView alipay(HttpServletRequest request, Alipay pay) throws Exception {
		Map<String, Object> params = new HashMap<>();

		String hospitalCode = pay.getCode();
		String tradeMode = pay.getTradeMode();
		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(hospitalCode, tradeMode);

		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));
		if (vo != null) {

			if (AlipayConstant.ALIPAY_TEST) {
				pay.setTotalFee(AlipayConstant.ALIPAY_TEST_MONEY);
			}

			if (StringUtils.isNotBlank(CommonConstant.PAY_VIEW_TYPE)) {
				pay.setViewType(CommonConstant.PAY_VIEW_TYPE);
			}

			//String alipayFormHtml = AlipayUtil.getPayFormHtml(vo, pay);
			Map<String, String> alipayFormParams = AlipayUtil.getMPayFormParams(vo, pay);
			Element alipayFormElement = DocumentHelper.createElement("form");
			alipayFormElement.addAttribute("id", "alipaysubmit").addAttribute("name", "alipaysubmit")
					.addAttribute("action", AlipayConstant.ALIPAY_MAPI_GATEWAY).addAttribute("method", "get");

			for (Map.Entry<String, String> entry : alipayFormParams.entrySet()) {
				Element inputElement = alipayFormElement.addElement("input");
				inputElement.addAttribute("type", "hidden").addAttribute("name", entry.getKey()).addAttribute("value", entry.getValue());
			}
			String alipayFormHtml = alipayFormElement.asXML();

			logger.info("alipayFormHtml: {}", alipayFormHtml);
			if (StringUtils.isNotBlank(alipayFormHtml)) {
				params.put("alipayFormHtml", alipayFormHtml);

				ModelAndView modelAndView = new ModelAndView("/pay/alipay");

				if ("iframe".equalsIgnoreCase(pay.getViewType())) {
					modelAndView = new ModelAndView("/pay/alipay.iframe");
				} else if ("jsonp".equalsIgnoreCase(pay.getViewType())) {
					modelAndView = new ModelAndView("/pay/alipay.jsonp");
				}

				params.put("pay", pay);

				modelAndView.getModel().put("params", params);

				return modelAndView;
			} else {
				params.put("resultMsg", "生成支付宝支付参数失败");
			}

		} else {
			params.put("resultMsg", "没有对应的支付配置");
		}

		return error(String.valueOf(params.get("resultMsg")));
	}

	@RequestMapping("/alipay2d0")
	public ModelAndView alipay2d0(HttpServletRequest request, Alipay pay) throws Exception {
		Map<String, Object> params = new HashMap<>();

		String hospitalCode = pay.getCode();
		String tradeMode = pay.getTradeMode();
		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(hospitalCode, tradeMode);

		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));
		if (vo != null) {

			if (AlipayConstant.ALIPAY_TEST) {
				pay.setTotalFee(AlipayConstant.ALIPAY_TEST_MONEY);
			}

			if (StringUtils.isNotBlank(CommonConstant.PAY_VIEW_TYPE)) {
				pay.setViewType(CommonConstant.PAY_VIEW_TYPE);
			}

			String alipayFormHtml = AlipayUtil.getPayFormHtml(vo, pay);

			logger.info("alipayFormHtml: {}", alipayFormHtml);
			if (StringUtils.isNotBlank(alipayFormHtml)) {
				params.put("alipayFormHtml", alipayFormHtml);

				ModelAndView modelAndView = new ModelAndView("/pay/alipay");

				if ("iframe".equalsIgnoreCase(pay.getViewType())) {
					modelAndView = new ModelAndView("/pay/alipay.iframe");
				} else if ("jsonp".equalsIgnoreCase(pay.getViewType())) {
					modelAndView = new ModelAndView("/pay/alipay.jsonp");
				}

				params.put("pay", pay);

				modelAndView.getModel().put("params", params);

				return modelAndView;
			} else {
				params.put("resultMsg", "生成支付宝支付参数失败");
			}

		} else {
			params.put("resultMsg", "没有对应的支付配置");
		}

		return error(String.valueOf(params.get("resultMsg")));
	}

	@RequestMapping("/unionpay")
	public ModelAndView unionpay(HttpServletRequest request, Unionpay pay) {
		Map<String, Object> params = new HashMap<>();

		String hospitalCode = pay.getCode();
		String tradeMode = pay.getTradeMode();
		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(hospitalCode, tradeMode);

		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		if (vo != null) {
			if (UnionPayConstant.UNIONPAY_TEST) {
				pay.setTotalFee(UnionPayConstant.UNIONPAY_TEST_MONEY);
			}

			if (StringUtils.isNotBlank(CommonConstant.PAY_VIEW_TYPE)) {
				pay.setViewType(CommonConstant.PAY_VIEW_TYPE);
			}

			JSONObject attach = new JSONObject();
			attach.put("code", hospitalCode);
			attach.put("tradeMode", tradeMode);
			//因为银联支付回调获取不到 openId
			//attach.put("openId", pay.getOpenId());

			Map<String, String> rspData;
			if (UnionPayConstant.UNIONPAY_TEST) {
				rspData = UnionPayUtil.pay(pay, UnionPayConstant.UNIONPAY_TEST_MERID, attach, null, null);
			} else {
				// /opt/new_platform/certs/acp303440180110054.pfx
				String certPath =
						CommonConstant.CERTS_PATH.concat(String.valueOf(java.io.File.separatorChar)).concat("acp").concat(vo.getMchId())
								.concat(".pfx");
				rspData = UnionPayUtil.pay(pay, vo.getMchId(), attach, certPath, vo.getCertificatePwd());
			}

			if (!rspData.isEmpty()) {

				if (AcpService.validate(rspData, UnionPayConstant.encoding)) {
					LogUtil.writeLog("验证签名成功");
					String respCode = rspData.get("respCode");

					if ( ( "00" ).equals(respCode)) {

						int resultCode = AcpService.updateEncryptCert(rspData, "UTF-8");
						if (resultCode == 1) {
							LogUtil.writeLog("加密公钥更新成功");
						} else if (resultCode == 0) {
							LogUtil.writeLog("加密公钥无更新");
						} else {
							LogUtil.writeLog("加密公钥更新失败: " + resultCode);
						}

						LogUtil.writeLog("tn: " + rspData.get("tn"));
						params.put("tn", rspData.get("tn"));

						//========================================================================================
						//JSSDK 认证代码

						String url =
								String.valueOf(request.getRequestURL())
										+ ( StringUtils.isNotBlank(request.getQueryString()) ? "?" + request.getQueryString() : "" );

						Map<String, String> unionpayJSSDKParams = UnionPayUtil.genJSSDKParams(url);
						logger.info("unionpayJSSDKParams.signature: {}", unionpayJSSDKParams.toString());

						ModelAndView modelAndView = new ModelAndView("/pay/unionpay");

						if ("iframe".equalsIgnoreCase(pay.getViewType())) {
							modelAndView = new ModelAndView("/pay/unionpay.iframe");
						} else if ("jsonp".equalsIgnoreCase(pay.getViewType())) {
							modelAndView = new ModelAndView("/pay/unionpay.jsonp");
						}

						params.put("pay", pay);
						params.put("unionpayJSSDKParams", unionpayJSSDKParams);

						modelAndView.getModel().put("params", params);

						return modelAndView;
					} else {
						LogUtil.writeErrorLog("respCode: " + respCode);
						LogUtil.writeErrorLog("respMsg: " + rspData.get("respMsg"));
						//其他应答码为失败请排查原因
						params.put("resultMsg", respCode.concat(" : ").concat(rspData.get("respMsg")));
					}
				} else {
					LogUtil.writeErrorLog("验证签名失败");
					LogUtil.writeErrorLog("respCode: " + rspData.get("respCode"));
					LogUtil.writeErrorLog("respMsg: " + rspData.get("respMsg"));
					//TODO 检查验证签名失败的原因
					params.put("resultMsg", "验证签名失败: ( " + rspData.get("respMsg") + " )");
				}

			} else {
				//未返回正确的http状态
				LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");

				params.put("resultMsg", "获取TN失败");
			}
		} else {
			params.put("resultMsg", "没有对应的支付配置");
		}

		return error(String.valueOf(params.get("resultMsg")));
	}

	/**
	 * 支付回调(微信)
	 *  支付完成后，微信会把相关支付结果和用户信息发送给商户，商户需要接收处理，并返回应答。
		对后台通知交互时，如果微信收到商户的应答不是成功或超时，微信认为通知失败，微信会通过一定的策略定期重新发起通知，尽可能提高通知的成功率，但微信不保证通知最终能成功。 
		（通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒）
		注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
		推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
		在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
		特别提醒：商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失。
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/wechatNotify")
	public void wechatNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> data = RequestUtil.getInputStreamAsMap(request);
		logger.info("wechatNotify.data：{}", data);
		String outTradeNo = data.get("out_trade_no"); // 商户订单号
		String resultCode = data.get("result_code"); // 支付结果

		Map<String, String> attachMap = new HashMap<String, String>();
		attachMap = JSON.parseObject(data.get("attach"), new TypeReference<Map<String, String>>() {
		});
		logger.info("attachMap:{},", attachMap);

		String code = attachMap.get("code");
		String tradeMode = attachMap.get("tradeMode");

		boolean checkSign = false;

		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(code, tradeMode);
		JSONObject jsonObject = CacheUtil.getPaySettingCache(code);

		if (StringUtils.equals(data.get("mch_id"), "1227059102")) {//临时
			vo =
					JSONObject
							.parseObject(
									"{\"appCode\":\"innerChinaLife\",\"appId\":\"wx7245e798af225271\",\"appName\":\"内嵌国寿app\",\"appSecret\":\"0017026efd68ec789106f1556309e06e\",\"hospitalCode\":\"gzhszhyy\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalName\":\"广州市红十字会医院\",\"isSubPay\":0,\"mchId\":\"1227059102\",\"payModeCode\":\"wechat\",\"paySettingId\":\"045b22f359c84b24bb8a1a090b801343\",\"paykey\":\"abcdefghijklmnopqrstuvwxyz910629\",\"tradeMode\":\"21\"}",
									HospitalCodeAndAppVo.class);
		}

		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));
		if (vo != null || jsonObject != null) {

			if (jsonObject != null) {
				vo = JSONObject.toJavaObject(jsonObject, HospitalCodeAndAppVo.class);
				vo.setPaykey(jsonObject.getString("payKey"));
				vo.setRefundFilePath(jsonObject.getString("certificatePath"));
			}

			checkSign = WechatUtil.checkSign(data, vo.getPaykey());
			logger.info("支付验签. 订单号：{} , checkData:{} ", outTradeNo, checkSign);
			if (checkSign) {

				WechatPayAsynResponse wechatPayAsynResponse = WechatUtil.map2WechatPayAsynResponse(data);

				Thread thread = new Thread(new PayAsynResponseRunnable(wechatPayAsynResponse));
				thread.start();

				if ("SUCCESS".equalsIgnoreCase(resultCode)) {
					logger.info("支付成功. 订单号：{} , ", new Object[] { outTradeNo });
					ResponseUtil.printXml(response,
							"<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
				} else {
					logger.info("支付失败. 订单号：{} , ", new Object[] { outTradeNo });
					ResponseUtil.printXml(response,
							"<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[FAIL]]></return_msg></xml>");
				}
			}
		} else {
			logger.error("could not find the pay info. code: {}, tradeMode: {}.", code, tradeMode);
		}

	}

	/**
	 * 支付回调(支付宝)
	 *  必须保证服务器异步通知页面（notify_url）上无任何字符，如空格、HTML标签、开发系统自带抛出的异常提示信息等；
		支付宝是用POST方式发送通知信息，因此该页面中获取参数的方式，如：request.Form(“out_trade_no”)、$_POST[‘out_trade_no’]；
		支付宝主动发起通知，该方式才会被启用；
		只有在支付宝的交易管理中存在该笔交易，且发生了交易状态的改变，支付宝才会通过该方式发起服务器通知（即时到账交易状态为“等待买家付款”的状态默认是不会发送通知的）；
		服务器间的交互，不像页面跳转同步通知可以在页面上显示出来，这种交互方式是不可见的；
		第一次交易状态改变（即时到账中此时交易状态是交易完成）时，不仅会返回同步处理结果，而且服务器异步通知页面也会收到支付宝发来的处理结果通知；
		程序执行完后必须打印输出“success”（不包含引号）。如果商户反馈给支付宝的字符不是success这7个字符，支付宝服务器会不断重发通知，直到超过24小时22分钟。
		一般情况下，25小时以内完成8次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h）；
		程序执行完成后，该页面不能执行页面跳转。如果执行页面跳转，支付宝会收不到success字符，会被支付宝服务器判定为该页面程序运行出现异常，而重发处理结果通知；
		cookies、session等在此页面会失效，即无法获取这些数据；
		该方式的调试与运行必须在服务器上，即互联网上能访问；
		该方式的作用主要防止订单丢失，即页面跳转同步通知没有处理订单更新，它则去处理；
		当商户收到服务器异步通知并打印出success时，服务器异步通知参数notify_id才会失效。也就是说在支付宝发送同一条异步通知时（包含商户并未成功打印出success导致支付宝重发数次通知），服务器异步通知参数notify_id是不变的。
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/alipayNotify")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取支付宝POST过来反馈信息
		Map<String, String> data = RequestUtil.getRequestParams(request);
		logger.info("alipayNotify.data：{}", data);
		try {

			Map<String, String> attachMap = new HashMap<String, String>();
			attachMap =
					JSON.parseObject(OrderUtil.ifBlank(data.get("passback_params"), data.get("body"), "{}"),
							new TypeReference<Map<String, String>>() {
							});
			logger.info("attachMap:{}", attachMap);

			String hospitalCode = attachMap.get("hospitalCode");
			String tradeMode = attachMap.get("tradeMode");

			HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(hospitalCode, tradeMode);

			logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

			//如果回调地址后面加了自有参数，必须删掉自己的参数后再验签
			//params.remove("paramName");

			boolean verifyNotify = false;
			String signType = data.get("sign_type");

			if (AlipayConstant.SIGN_TYPE_RSA2.equalsIgnoreCase(signType)) {
				verifyNotify =
						AlipaySignature.rsaCheckV1(data, AlipayConstant.ALIPAY_OPEN_PLATFORM_PUBLIC_KEY_RSA2, AlipayConstant.CHARSET,
								AlipayConstant.SIGN_TYPE_RSA2);
			} else {
				//verifyNotify = AlipaySignature.rsaCheckV1(data, AlipayConstant.ALIPAY_OPEN_PLATFORM_PUBLIC_KEY, AlipayConstant.CHARSET, AlipayConstant.SIGN_TYPE_RSA);
				verifyNotify = AlipaySignature.rsaCheckV1(data, vo.getPaykey(), AlipayConstant.CHARSET, AlipayConstant.SIGN_TYPE_RSA);
			}

			logger.info("alipay.verifyNotify:" + verifyNotify);

			//老版本的手机网站支付回调没有返回app_id
			if (!data.containsKey("app_id")) {
				data.put("app_id", vo.getAppId());
			}

			String result = "";
			if (verifyNotify) {
				result = "success";

				AlipayAsynResponse alipayAsynResponse = AlipayUtil.map2AlipayAsynResponse(data);

				Thread thread = new Thread(new PayAsynResponseRunnable(alipayAsynResponse));
				thread.start();

			} else {
				result = "fail";
			}
			logger.info("回复支付宝结果:{}.", result);
			ResponseUtil.print(response, result);
		} catch (Exception e) {
			logger.error("处理支付宝回调异常：{}", e.getMessage());
			ResponseUtil.print(response, "fail");
		}
	}

	/**
	 * 支付回调(银联钱包)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/unionpayNotify")
	public void unionpayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LogUtil.writeLog("unionpayNotify 接收后台通知开始");

		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> data = RequestUtil.getRequestParamsNotBlank(request);

		logger.info("unionpayNotify.data：{}", data);

		String encoding = data.get(SDKConstants.param_encoding);
		Map<String, String> valideData = new HashMap<>();
		if (null != data && !data.isEmpty()) {
			for (Map.Entry<String, String> entry : data.entrySet()) {
				String value = new String(entry.getValue().getBytes(encoding), encoding);
				valideData.put(entry.getKey(), value);
			}
		}

		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(valideData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			//验签失败，需解决验签问题

		} else {
			LogUtil.writeLog("验证签名结果[成功].");
			//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态

			UnionpayAsynResponse unionpayAsynResponse = UnionPayUtil.map2UnionpayAsynResponse(valideData);

			Thread thread = new Thread(new PayAsynResponseRunnable(unionpayAsynResponse));
			thread.start();

			ResponseUtil.print(response, "ok");
		}
		LogUtil.writeLog("unionPayNotify 接收后台通知结束");
	}

}
