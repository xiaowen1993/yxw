/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月5日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.easyhealth.common.interceptor;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.app.sdk.unionpay.UnionPaySDK;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;
import com.yxw.mobileapp.constant.EasyHealthConstant;
import com.yxw.utils.RandomUtil;

/**
 * 健康易拦截器
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月5日
 */
public class EasyHealthInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(EasyHealthInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { //如果是ajax请求响应头会有x-requested-with  
			// ajax not doing anything
		} else {
			String appCode = getAppCode(request);
			if (modelAndView != null && StringUtils.defaultString(appCode).contains("UnionPay")) {
				String appId = EasyHealthConstant.UNIONPAY_APPID;
				String nonceStr = RandomUtil.RandomString(16);
				String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

				String url =
						String.valueOf(request.getRequestURL())
								+ ( StringUtils.isNotBlank(request.getQueryString()) ? "?" + request.getQueryString() : "" );

				Map<String, String> unionPayUPSDKParams = new HashMap<String, String>();

				unionPayUPSDKParams.put("fronttoken", UnionPaySDK.getFrontToken(appId, EasyHealthConstant.UNIONPAY_SECRET));
				unionPayUPSDKParams.put("noncestr", nonceStr);
				unionPayUPSDKParams.put("timestamp", timestamp);
				unionPayUPSDKParams.put("url", url);

				logger.info("unionPayUPSDKParams: {}", unionPayUPSDKParams.toString());

				//生成签名
				String signature = UnionPaySDK.getUPSDKSignature(unionPayUPSDKParams);

				unionPayUPSDKParams.put("signature", signature);
				unionPayUPSDKParams.put("appid", appId);

				logger.info("unionPayUPSDKParams.signature: {}", unionPayUPSDKParams.toString());
				modelAndView.getModel().put("unionPayUPSDKParams", unionPayUPSDKParams);
			}
		}

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = String.valueOf(request.getRequestURL());
		String queryString = request.getQueryString();
		String ip = request.getRemoteHost();
		String contextPath = request.getContextPath();
		logger.info("ip:{},queryString:{},url:{},contextPath:{}", new Object[] { ip, queryString, url, contextPath });
		boolean isPass = false;
		HttpSession session = request.getSession();
		Object userObject = session.getAttribute(EasyHealthConstant.SESSION_USER_ENTITY);

		if (userObject != null) {
			//EasyHealthUser user = (EasyHealthUser) userObject;
			// 测试用
			//			if ("13538879117".equals(user.getAccount())) {
			//				user.setAccount("13800138000");
			//				user.setId("a89f3b785148445b8b7f99474a8b378a");
			//				user.setUnionPayId("a89f3b785148445b8b7f99474a8b378a");
			//				session.setAttribute(EasyHealthConstant.SESSION_USER_ENTITY, user);
			//			}

			isPass = true;
		} else {
			// 从哪里来，登录后回哪里去

			String __src = url + ( StringUtils.isNotBlank(queryString) ? "?" + queryString : "" );

			String appCode = getAppCode(request);

			if (url.endsWith("user/toLogin")) {
				//queryString = queryString.replace("appCode=appUnionPay", "appCode=innerUnionPay");
				if (StringUtils.isBlank(queryString)) {
					response.sendRedirect(request.getContextPath() + "/easyhealth/user/toLogin?appCode=".concat(appCode));
				} else {
					response.sendRedirect(request.getContextPath() + "/easyhealth/user/toLogin?".concat(queryString));
				}
			} else {
				response.sendRedirect(request.getContextPath()
						+ "/easyhealth/user/toLogin?".concat("__src=" + URLEncoder.encode(__src, "utf-8")).concat("&appCode=" + appCode));
			}

		}
		return isPass;
	}

	private String getAppCode(HttpServletRequest request) {
		String appCode = request.getParameter("appCode");

		if (StringUtils.isBlank(appCode)) {
			Object obj = request.getSession().getAttribute(EasyHealthConstant.SESSION_APP_CODE);
			if (obj != null) {
				appCode = (String) obj;
			}
		}

		if (StringUtils.isBlank(appCode)) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (logger.isDebugEnabled()) {
						logger.debug("cookie.name: {}" + ", cookie.value: {}", cookie.getName(), cookie.getValue());
					}
					if (EasyHealthConstant.SESSION_APP_CODE.equalsIgnoreCase(cookie.getName())) {
						if (logger.isDebugEnabled()) {
							logger.debug("el.user.cookie.value: {}", cookie.getValue());
						}

						appCode = cookie.getValue();
						break;
					}
				}
			}
		}
		return appCode;
	}

}
