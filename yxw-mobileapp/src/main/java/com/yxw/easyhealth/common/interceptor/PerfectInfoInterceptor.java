package com.yxw.easyhealth.common.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;
import com.yxw.mobileapp.constant.EasyHealthConstant;

public class PerfectInfoInterceptor implements HandlerInterceptor {

	private final static Logger logger = LoggerFactory.getLogger(PerfectInfoInterceptor.class);

	private final static String PERFECT_INFO_URL = "/easyhealth/user/toPerfectUserInfo";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 判断是否异步，异步则直接放行
		if (isAjaxRequest(request)) {
			return true;
		}

		// 约定：app/xx/xxx?xxx=xxx&xx=xxx. 使用url编码

		// 判断是否已完善个人资料（从session中，此时是已经登录了的）
		if (!checkIsPerfect(request)) {
			// 完善后跳转
			String forward = request.getParameter(BizConstant.URL_PARAM_FORWARD_NAME);
			if (StringUtils.isNotBlank(forward)) {

			} else {
				forward = request.getServletPath();
				// 对于这种没有forward的，都必须是get请求过来的
				// 参数转移
				// Map<String, String[]> requestParams = request.getParameterMap();
				forward = forward.substring(1).concat(BizConstant.URL_PARAM_CHAR_FIRST).concat(request.getQueryString());

				request.removeAttribute(BizConstant.URL_PARAM_FORWARD_NAME);
				request.setAttribute(BizConstant.URL_PARAM_FORWARD_NAME, URLEncoder.encode(forward, "utf-8"));
				// response.sendRedirect(PERFECT_INFO_URL);
			}

			request.getRequestDispatcher(PERFECT_INFO_URL).forward(request, response);
			return false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(request.getRequestURL().toString());
			logger.debug(JSON.toJSONString(request.getParameterMap()));
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub

	}

	// 这里是从session中那sessionUser信息，所以应该放在EasyHealthInterceptor后
	// 是否添加了就诊人
	private boolean checkIsPerfect(HttpServletRequest request) {
		String url = String.valueOf(request.getRequestURL());
		String familyInfo = (String) request.getSession().getAttribute(EasyHealthConstant.SESSION_IS_FAMILY_INFO);
		if (StringUtils.equals(familyInfo, EasyHealthConstant.SESSION_IS_FAMILY_INFO_YES_VAL)) {
			if (StringUtils.contains(url, "app/usercenter/syncCard/index")) {
				Object object = request.getSession().getAttribute(EasyHealthConstant.SESSION_USER_ENTITY);
				if (object != null) {
					EasyHealthUser sessionUser = (EasyHealthUser) object;
					if (sessionUser != null) {
						return StringUtils.isNotBlank(sessionUser.getCardNo());
					}
				}
				return false;
			} else {
				return true;
			}

		}

		return false;
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
	}

}
