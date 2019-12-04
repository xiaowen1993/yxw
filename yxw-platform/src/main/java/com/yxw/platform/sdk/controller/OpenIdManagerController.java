package com.yxw.platform.sdk.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.platform.sdk.SDKPublicArgs;

/**
 * 管理当前浏览器存在Cookie里面的openId，主要用来在浏览器里面测试页面的时候，可以自己设置一个openId到Cookie里面，
 * 这样可以免去获取openId的步骤，直接从Cookie里面拿openId，因为普通浏览器获取不了openId
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月8日
 */
@Controller
@RequestMapping("/openidmanager")
public class OpenIdManagerController {

	@RequestMapping("/setting")
	public ModelAndView setting(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "appId",
			required = true) String appId, @RequestParam(value = "openId",
			required = false) String openId) {
		// 测试地址：http://hw9.yx129.net/new-yxw-framework/openidmanager/setting?appId=wxfc6703a4f4a2fe36
		System.out.println("/openidmanager/setting.appId: " + appId);
		System.out.println("/openidmanager/setting.openId: " + openId);
		String openIdCookieKey = "yxw_" + appId + "_openId";
		if (StringUtils.isNotBlank(openId)) {
			// openId 存入 Cookie
			// openId 在 Cookie 里面的有效天数，默认为30天
			int openIdCookieMaxAge = Integer.valueOf(StringUtils.isBlank(SDKPublicArgs.OPENID_COOKIE_MAX_AGE) ? "30"
					: SDKPublicArgs.OPENID_COOKIE_MAX_AGE);
			Cookie cookie = new Cookie(openIdCookieKey, openId);
			cookie.setDomain(request.getServerName()); // 请用自己的域
			cookie.setMaxAge(24 * 60 * 60 * openIdCookieMaxAge); // cookie的有效期
			cookie.setPath("/");
			response.addCookie(cookie);
		} else {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (openIdCookieKey.equalsIgnoreCase(cookie.getName())) {
						openId = cookie.getValue();
						break;
					}
				}
			}

		}
		request.setAttribute(appId + "-" + "openId", openId);
		ModelAndView view = new ModelAndView("/sdk/openIdManager", "appId", appId);
		return view;
	}

}
