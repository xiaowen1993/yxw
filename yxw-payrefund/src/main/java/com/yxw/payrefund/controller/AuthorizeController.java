package com.yxw.payrefund.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.common.WechatConstant;
import com.wechat.WechatUtil;
import com.yxw.payrefund.utils.CacheUtil;
import com.yxw.payrefund.utils.RequestUtil;
import com.yxw.payrefund.utils.aes.AesException;
import com.yxw.payrefund.utils.aes.WXBizMsgCrypt;

/**
 * 接受微信推送的 component_verify_ticket
 * 
 * 出于安全考虑，在第三方平台创建审核通过后，微信服务器 每隔10分钟会向第三方的消息接收地址推送一次component_verify_ticket，用于获取第三方平台接口调用凭据
 * 
 * @author YangXuewen
 *
 */
@Controller
@RequestMapping(value = "/authorize")
public class AuthorizeController {

	private static Logger logger = LoggerFactory.getLogger(AuthorizeController.class);

	@ResponseBody
	@RequestMapping("/notify")
	public String notify(HttpServletRequest request) throws HttpException, IOException, DocumentException, AesException {
		//signature=d6576c86ef5549d61a3a9b4584b66559b156db8c&timestamp=1517470258&nonce=1729656945&encrypt_type=aes&msg_signature=60d51850f8f89b0ad568f696ed7a5cd91cea6b8b
		logger.info("authorize.notify... {}", request.getQueryString());
		/**
		<xml>
		  <AppId><![CDATA[wx3142599554018787]]></AppId>
		  <Encrypt><![CDATA[CwjII+9fPt3f7TKJQnoq36WMHKWpuGj3gXDtuZDm8dcj1iUXx5JQAnPh/yofkPqNjH4MA7Xxc6ahKVVrry+n+88GNBYpt+4Pd6CQ2qwHkXaHzFoEofIGIibabd/wTy+o0bRP0jY7RSHd21fpUoIkWWUTk96LNCFtXIm39pHqzW33maXyNG7C6/9rYgYuIu3l7MEBcNCWBJcJzfP4Iu2mj/TwHUhQmHnEVk11kNVLYAMnVrJNGosENufejP0r2PoBH+H6Ak5bdsUoWQHMhaSFrF2GJTSP0tJ7rxfRmyE6Q/siKinzbb6pkT5oKELZjTaojGAlUXsvjPURq+CmHJAFwH5SgIpdcPaAH/yb0OqTmzHhp5TyEJj13GQPTnyR+/mOp2aV8DZ6tRuEWObWx6Q/wW6kGVEnxHZGc3/eyh5vCbsqPaKia3S+bcZVdDufP7fILyGVCO4bHG/0rOMLdTH6VQ==]]></Encrypt>
		</xml>
		 */

		String dataString = RequestUtil.getInputStreamAsString(request);
		logger.info("wechat.authorize.notify.dataString：{}", dataString);

		if (StringUtils.isNotBlank(dataString)) {
			Map<String, String> paramsMap = RequestUtil.getRequestParams(request);
			logger.info("wechat.authorize.notify.paramsMap：{}", paramsMap);

			//String signature = paramsMap.get("signature");
			String timestamp = paramsMap.get("timestamp");
			String nonce = paramsMap.get("nonce");
			//String encryptType = paramsMap.get("encrypt_type");
			String msgSignature = paramsMap.get("msg_signature");

			Document doc = DocumentHelper.parseText(dataString);
			String appId = doc.valueOf("//AppId");
			String encrypt = doc.valueOf("//Encrypt");
			if (StringUtils.isNotBlank(appId)) {
				// 验证安全签名 token, timestamp, nonce, encrypt
				String signature = WechatUtil.getSha1Sign(WechatConstant.WECHAT_COMPONENT_TOKEN, timestamp, nonce, encrypt);
				logger.info("signature: {}", signature);

				// 和URL中的签名比较是否相等
				if (signature.equals(msgSignature)) {
					// 解密

					WXBizMsgCrypt pc =
							new WXBizMsgCrypt(WechatConstant.WECHAT_COMPONENT_TOKEN, WechatConstant.WECHAT_COMPONENT_ENCODING_AES_KEY,
									appId);
					String decryptString = pc.decrypt(encrypt);

					/**
					 <xml>
					   <AppId><![CDATA[wx3142599554018787]]></AppId>
					   <CreateTime>1517477450</CreateTime>
					   <InfoType><![CDATA[component_verify_ticket]]></InfoType>
					   <ComponentVerifyTicket><![CDATA[ticket@@@LJLMd76DQdJWf1qqTcnrwW07Jrzd33BF2yYzqIAq-OvzWV6jbE2VaEgx4tXUFhIllf1yHoPGTCBkvj1zBt4rLw]]></ComponentVerifyTicket>
					</xml>
					 */
					logger.info("decryptString: {}", decryptString);

					Document componentVerifyTicketDoc = DocumentHelper.parseText(decryptString);
					String componentAppId = componentVerifyTicketDoc.valueOf("//AppId");
					String componentVerifyTicket = componentVerifyTicketDoc.valueOf("//ComponentVerifyTicket");

					//此参数获取途径只能接受微信推送，无法主动获取，建议不只是存在内存中
					WechatConstant.wechatComponentVerifyTicketMap.put(componentAppId, componentVerifyTicket);

					//存入redis
					CacheUtil.setWechatComponentVerifyTicket(componentAppId, componentVerifyTicket);

					String componentAccessToken =
							WechatUtil.getComponentAccessToken(componentAppId, WechatConstant.WECHAT_COMPONENT_APPSECRET,
									componentVerifyTicket);
					logger.info("init componentAccessToken: {}", componentAccessToken);

				}

			}
		}

		logger.info("authorize.notify.return.success...");
		return "success";
	}

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException {
		return new ModelAndView("/pay/wechat.login.index");
	}

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException {
		String wechatComponentVerifyTicket = WechatConstant.wechatComponentVerifyTicketMap.get(WechatConstant.WECHAT_COMPONENT_APPID);
		if (StringUtils.isBlank(wechatComponentVerifyTicket)) {
			wechatComponentVerifyTicket = CacheUtil.getWechatComponentVerifyTicket(WechatConstant.WECHAT_COMPONENT_APPID);
		}

		String componentAccessToken =
				WechatUtil.getComponentAccessToken(WechatConstant.WECHAT_COMPONENT_APPID, WechatConstant.WECHAT_COMPONENT_APPSECRET,
						wechatComponentVerifyTicket);
		logger.info("login.componentAccessToken: {}", componentAccessToken);

		String componentPreAuthCode = WechatUtil.getComponentPreAuthCode(WechatConstant.WECHAT_COMPONENT_APPID, componentAccessToken);
		logger.info("login.componentPreAuthCode: {}", componentPreAuthCode);

		String path = request.getContextPath();
		String basePath =
				request.getScheme() + "://" + request.getServerName()
						+ ( 80 == request.getServerPort() ? "" : ":" + request.getServerPort() ) + path + "/";
		String redirectUrl = basePath + "authorize/loginSuccess";
		logger.info("login.redirectUrl: {}", redirectUrl);

		String componentLoginPage =
				WechatUtil.getComponentLoginPage(WechatConstant.WECHAT_COMPONENT_APPID, componentPreAuthCode, redirectUrl);
		logger.info("login.componentLoginPage: {}", componentLoginPage);

		response.sendRedirect(componentLoginPage);

		return null;
	}

	@RequestMapping("/loginSuccess")
	public ModelAndView loginSuccess(HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException {
		Map<String, String> requestParams = RequestUtil.getRequestParams(request);

		//{auth_code=queryauthcode@@@51bYwoqL9tG8kjH5wbIq5xHMw9TrVZ0u_zXHJDLLmPW-h6qq-WqO14OLqcuDhezeb_ud7qIle09Uyc8jlvj4MQ, expires_in=3600}
		logger.info("loginSuccess.requestParams: {}", requestParams);

		return new ModelAndView("/pay/wechat.login.success");
	}

}
