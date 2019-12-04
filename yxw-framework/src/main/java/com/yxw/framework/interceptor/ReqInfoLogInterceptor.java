/**
 * Copyright© 2014-2015 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2015年6月4日
 * @version 1.0
 */
package com.yxw.framework.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.utils.StringUtils;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2015年6月4日  
 */
public class ReqInfoLogInterceptor implements HandlerInterceptor, Serializable {

	private static final long serialVersionUID = 1485772771215514457L;

	private static Logger logger = LoggerFactory.getLogger(ReqInfoLogInterceptor.class);

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2015年6月4日 
	 * @param paramHttpServletRequest
	 * @param paramHttpServletResponse
	 * @param paramObject
	 * @return
	 * @throws Exception 
	 */
	@Override
	public boolean preHandle(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Object paramObject)
			throws Exception {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			long begin = System.currentTimeMillis();

			String uri = paramHttpServletRequest.getRequestURI();

			if (paramObject instanceof HandlerMethod) {
				Method method = ( (HandlerMethod) paramObject ).getMethod();

				logger.info(StringUtils.buildInfoString("", "开始请求", uri, ".", method.getName(), ""));

				logger.info(StringUtils.map2String(paramHttpServletRequest.getParameterMap(), "" + "请求数据", "",
						"--------------------------------------------------------", true, "="));
			}
			paramHttpServletRequest.setAttribute("begin", begin);
		}

		return true;
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2015年6月4日 
	 * @param paramHttpServletRequest
	 * @param paramHttpServletResponse
	 * @param paramObject
	 * @param paramModelAndView
	 * @throws Exception 
	 */
	@Override
	public void postHandle(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Object paramObject,
			ModelAndView paramModelAndView) throws Exception {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			long begin = (Long) paramHttpServletRequest.getAttribute("begin");
			paramHttpServletRequest.removeAttribute("startTime");
			String uri = paramHttpServletRequest.getRequestURI();
			logger.info(StringUtils.buildInfoString("", "", "结束", uri, ",共花费", System.currentTimeMillis() - begin, "毫秒"));
		}
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2015年6月4日 
	 * @param paramHttpServletRequest
	 * @param paramHttpServletResponse
	 * @param paramObject
	 * @param paramException
	 * @throws Exception 
	 */
	@Override
	public void afterCompletion(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Object paramObject,
			Exception paramException) throws Exception {
		// TODO Auto-generated method stub
	}

}
