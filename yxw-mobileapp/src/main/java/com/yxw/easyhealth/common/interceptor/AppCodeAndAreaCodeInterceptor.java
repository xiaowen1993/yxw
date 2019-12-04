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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.app.color.AppColor;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.constant.EasyHealthConstant;

/**
 * 健康易拦截器
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月5日
 */
public class AppCodeAndAreaCodeInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(AppCodeAndAreaCodeInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		String areaCode = getAreaCode(request);

		if (StringUtils.isBlank(areaCode)) {
			areaCode = "/440000/440100";
			request.getSession().setAttribute(EasyHealthConstant.SESSION_AREA_CODE, areaCode);
		}

		// request.getSession().setAttribute(EasyHealthConstant.SESSION_APP_CODE, "app");

		String appColor = getYxColorFromRequest(request);
		if (StringUtils.isBlank(appColor)) {
			appColor = getYxColor(getAppCode(request));
			request.getSession().setAttribute(EasyHealthConstant.SESSION_APP_COLOR, appColor);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean isPass = false;

		String appCode = getAppCode(request);

		if (StringUtils.isNotBlank(appCode)) {
			isPass = true;
		}

		return isPass;
	}

	private String getAppCode(HttpServletRequest request) {
		String appCode = null;
		Object obj = request.getSession().getAttribute(EasyHealthConstant.SESSION_APP_CODE);
		if (obj != null) {
			appCode = (String) obj;
		} else {
			appCode = request.getParameter(EasyHealthConstant.SESSION_APP_CODE);
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

		if (StringUtils.isBlank(appCode)) {
			logger.error("could not find the appCode in this session.");
		}

		return appCode;
	}

	private String getAreaCode(HttpServletRequest request) {
		String areaCode = null;
		Object obj = request.getSession().getAttribute(EasyHealthConstant.SESSION_AREA_CODE);
		if (obj != null) {
			areaCode = (String) obj;
		} else {
			areaCode = request.getParameter(EasyHealthConstant.SESSION_AREA_CODE);
		}

		if (StringUtils.isBlank(areaCode)) {
			logger.error("could not find the areaCode in this session.");
		}

		return areaCode;
	}

	private String getYxColorFromRequest(HttpServletRequest request) {
		String appColor = null;
		Object obj = request.getSession().getAttribute(EasyHealthConstant.SESSION_APP_COLOR);
		if (obj != null) {
			appColor = (String) obj;
		} else {
			appColor = request.getParameter(EasyHealthConstant.SESSION_APP_COLOR);
		}

		if (StringUtils.isBlank(appColor)) {
			logger.error("could not find the appColor in this session.");
		}

		return appColor;
	}

	private String getYxColor(String appCode) {
		// 设置默认颜色为空。到页面JS-setSkinColor会判断
		String yxColor = "";
		List<Object> params = new ArrayList<>();
		params.add(appCode);
		ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
		List<Object> results = serveComm.get(CacheType.APP_COLOR_CACHE, "findByAppCode", params);
		if (!CollectionUtils.isEmpty(results)) {
			String source = JSON.toJSONString(results.get(0));
			AppColor appColor = JSON.parseObject(source, AppColor.class);
			yxColor = appColor.getColor();
		}
		return yxColor;
	}

}
