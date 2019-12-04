package com.yxw.easyhealth.biz.unionpay.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.yxw.app.sdk.unionpay.UnionPaySDK;
import com.yxw.mobileapp.constant.EasyHealthConstant;

/**
 * 银联钱包测试Demo
 * 
 * @author YangXuewen
 *
 */
@Controller
@RequestMapping("/easyhealth/unionpay/demo")
public class UnionPayTestController {

	@RequestMapping(value = "test")
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException {
		String url = String.valueOf(request.getRequestURL());
		String queryString = request.getQueryString();
		System.out.println("url: " + url + "?" + queryString);
		String code = request.getParameter("code");
		System.out.println("unionPay OAuth2 code: " + code);
		if (StringUtils.isBlank(code)) {
			String path = request.getContextPath();
			String basePath =
					request.getScheme() + "://" + request.getServerName()
							+ ( 80 == request.getServerPort() ? "" : ":" + request.getServerPort() ) + path + "/";

			String redirectUrl =
					basePath + "easyhealth/unionpay/demo/test?appId=" + EasyHealthConstant.UNIONPAY_APPID + "&appCode="
							+ EasyHealthConstant.APP_CODE_UNIONPAY;
			String oauth2url =
					UnionPaySDK.getOAuth2(EasyHealthConstant.UNIONPAY_APPID, EasyHealthConstant.UNIONPAY_SCOPE_UPAPI_BASE, redirectUrl);
			System.out.println("unionPay OAuth2 url: " + oauth2url);
			response.sendRedirect(oauth2url);
			return null;
		} else {
			String backendToken = UnionPaySDK.getBackendToken(EasyHealthConstant.UNIONPAY_APPID, EasyHealthConstant.UNIONPAY_SECRET);
			System.out.println("backendToken: " + backendToken);
			if (StringUtils.isNotBlank(backendToken)) {
				JSONObject accessTokenAndOpenIdJson =
						UnionPaySDK.getAccessTokenAndOpenIdByUnionPay(EasyHealthConstant.UNIONPAY_APPID, backendToken, code);
				System.out.println("accessTokenAndOpenIdJson: " + accessTokenAndOpenIdJson);

				String accessToken = accessTokenAndOpenIdJson.getString("accessToken");
				String openId = accessTokenAndOpenIdJson.getString("openId");

				if (StringUtils.isNotBlank(accessToken) && StringUtils.isNotBlank(openId)) {
					JSONObject userInfoJson = UnionPaySDK.getUserInfo(EasyHealthConstant.UNIONPAY_APPID, openId, accessToken, backendToken);
					System.out.println("userInfoJson: " + userInfoJson);

					modelMap.put("userInfoJson", JSONObject.toJSONString(userInfoJson, true));
					System.out.println(modelMap.get("userInfoJson").toString());
				}

			}
		}
		return new ModelAndView("easyhealth/biz/unionpay/demo/test" + modelMap);
	}

	@ResponseBody
	@RequestMapping(value = "loadData")
	public Object loadData() {
		Map<String, Object> resMap = new HashMap<String, Object>();

		return resMap;
	}

}
