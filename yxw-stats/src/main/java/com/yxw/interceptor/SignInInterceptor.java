/**
 * Copyright© 2014-2016 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2016年11月13日
 * @version 1.0
 */
package com.yxw.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2016年11月13日  
 */
public class SignInInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SignInInterceptor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(true);
		// 获取application对象
		// WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		// ServletContext servletContext = webApplicationContext.getServletContext();
		String login_status = (String) session.getAttribute("login_states");
		if (!StringUtils.equals("true", login_status)) {

			String params = request.getParameter("s");
			String referer = request.getHeader("Referer");
			if (StringUtils.equals("daye", params) || StringUtils.contains(referer, "daye")) {
				return true;
			} else {
				if (isAjaxRequest(httpRequest)) {
					logger.info("您已经太长时间没有操作,请刷新页面");
					httpResponse.setCharacterEncoding("UTF-8");
					httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "您已经太长时间没有操作,请刷新页面");
				} else {

					httpRequest.getRequestDispatcher("/signin").forward(httpRequest, httpResponse);
				}

				return false;
			}

		} else {
			return true;
		}
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		// 目前/api下的路径都是ajax请求
		if ( ( requestType != null && requestType.equals("XMLHttpRequest") ) || ( request.getRequestURI().startsWith("/api") )) {
			return true;
		} else {
			return false;
		}

	}
}