package com.yxw.platform.sdk.alipay.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.platform.sdk.alipay.constant.AlipayServiceEnvConstants;
import com.yxw.platform.sdk.alipay.dispatcher.Dispatcher;
import com.yxw.platform.sdk.alipay.executor.ActionExecutor;
import com.yxw.platform.sdk.alipay.utils.MessageSendUtils;
import com.yxw.platform.sdk.alipay.utils.RequestUtil;

/**
 * 支付宝 service controller
 * 
 * @author luob
 * */
@Controller
@RequestMapping("/sdk/alipay")
public class AlipaySDKController {

	private static Logger logger = LoggerFactory.getLogger(AlipaySDKController.class);

	// 图片路径http://servername:port/
	public static String basePath = "";

	/**
	 * 支付宝网关接入
	 * 
	 * @throws IOException
	 * */
	@RequestMapping(value = "/main",
			method = RequestMethod.POST)
	public void main(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("GBK");
		if (basePath == null || "".equals(basePath)) {
			basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
		}
		// 支付宝响应消息
		String responseMsg = "";
		// 1. 解析请求参数
		Map<String, String> params = RequestUtil.getRequestParams(request);
		if (logger.isDebugEnabled()) {
			logger.debug("支付宝请求串参数：" + params.toString());
		}
		String bizContent = null;
		String appId = null;
		if (params != null) {
			bizContent = params.get("biz_content");
			if (bizContent != null) {
				JSONObject bizContentJson = (JSONObject) new XMLSerializer().read(bizContent);
				appId = bizContentJson.getString("AppId");
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("AlipaySDKController---appId   " + appId);
		}
		try {
			// 2. 验证签名
			this.verifySign(params);
			logger.info("验证签名通过...");

			// 3. 获取业务执行器 根据请求中的 service, msgType, eventType, actionParam 确定执行器
			ActionExecutor executor = Dispatcher.getExecutor(params);

			// 4. 执行业务逻辑
			responseMsg = executor.execute();
		} catch (Exception exception) {
			// 开发者可以根据异常自行进行处理
			exception.printStackTrace();
		} finally {
			// 5. 响应结果加签及返回
			try {
				if (appId != null) {
					HospIdAndAppSecretVo vo = MessageSendUtils.obtainByAppId(appId);
					if (vo != null) {
						// 对响应内容加签，支付宝用来验证发送该消息的商户身份
						responseMsg = AlipaySignature.encryptAndSign(responseMsg, AlipayServiceEnvConstants.ALIPAY_PUBLIC_KEY, vo.getAppSecret(),
								AlipayServiceEnvConstants.CHARSET, false, true);
						// http 内容应答
						if (logger.isDebugEnabled()) {
							logger.debug("responseMsg内容  ---" + responseMsg);
						}
						response.reset();
						response.setContentType("text/xml;charset=GBK");
						PrintWriter printWriter = response.getWriter();
						printWriter.print(responseMsg);
						response.flushBuffer();
					}
				} else {
					logger.error("无法获取医院的私钥  ---");
				}
			} catch (AlipayApiException alipayApiException) {
				// 开发者可以根据异常自行进行处理
				alipayApiException.printStackTrace();
			}
		}
	}

	/**
	 * 验签
	 * 
	 * @param request
	 * @return
	 */
	private void verifySign(Map<String, String> params) throws AlipayApiException {
		if (!AlipaySignature.rsaCheckV2(params, AlipayServiceEnvConstants.ALIPAY_PUBLIC_KEY, AlipayServiceEnvConstants.SIGN_CHARSET)) {
			logger.error("验签失败.......");
			throw new AlipayApiException("verify sign fail.");
		}
	}

}
