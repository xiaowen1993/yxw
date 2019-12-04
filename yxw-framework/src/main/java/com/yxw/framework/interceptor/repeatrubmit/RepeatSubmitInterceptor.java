/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-8-19</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.interceptor.repeatrubmit;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Package: com.yxw.framework.interceptor
 * @ClassName: RepeatSubmitInterceptor
 * @Statement: <p>重复提交拦截器</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-8-19
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RepeatSubmitInterceptor implements HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(RepeatSubmitInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.info("RepeatSubmitInterceptor preHandle start. reqAddress:{} , reqHost:{} , reqPort:{}", new Object[] { request.getRemoteAddr(),
					request.getRemoteHost(),
					request.getRemotePort() });
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		RepeatSubmitDefinition annotation = method.getAnnotation(RepeatSubmitDefinition.class);
		if (annotation != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("annotation is not null..............");
			}
			boolean needSaveSession = annotation.needSaveToken();
			if (needSaveSession) {
				if (logger.isDebugEnabled()) {
					logger.debug("needSaveSession : true");
				}

				TokenHelper.setToken(request);
			}
			boolean needRemoveSession = annotation.needRemoveToken();
			if (needRemoveSession) {
				if (logger.isDebugEnabled()) {
					logger.debug("needRemoveSession : true");
				}

				if (!TokenHelper.validToken(request)) {
					logger.warn("please don't repeat submit,url:{}", request.getServletPath());
					return false;
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.info("RepeatSubmitInterceptor preHandle end ....................");
		}
		return true;
	}

}
