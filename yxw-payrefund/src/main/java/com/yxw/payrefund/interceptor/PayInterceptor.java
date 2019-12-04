package com.yxw.payrefund.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class PayInterceptor implements HandlerInterceptor {
	private static Logger logger = LoggerFactory.getLogger(PayInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception e) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		//String url = String.valueOf(request.getRequestURL());
		
		String hospitalCode = request.getParameter("hospitalCode");
		if (StringUtils.isBlank(hospitalCode)) {
			logger.error("hospitalCode is null");
			return false;
		}
		
		String tradeMode = request.getParameter("tradeMode");
		if (StringUtils.isBlank(tradeMode)) {
			logger.error("tradeMode is null");
			return false;
		}
		
		String totalFee = request.getParameter("totalFee");
		if (StringUtils.isBlank(totalFee)) {
			logger.error("totalFee is null");
			return false;
		}
		
		return true;
		
	}
}
