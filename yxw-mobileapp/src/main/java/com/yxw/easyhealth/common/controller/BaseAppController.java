/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-24</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.common.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;
import com.yxw.mobileapp.constant.EasyHealthConstant;

/**
 * @Package: com.yxw.mobileapp.common.controller
 * @ClassName: BaseAppController
 * @Statement: <p>
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-24
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class BaseAppController {
	/**
	 * 重定向跳转关键字
	 */
	public static final String REDIRECT_PREFIX = "redirect:";

	public static final String PERFECT_USER_INFO_URL = "/easyhealth/user/toPerfectUserInfo";

	private Logger logger = LoggerFactory.getLogger(BaseAppController.class);

	/**
	 * 获取微信公众号/支付宝服务窗的 openId
	 * 
	 * @param request
	 * @return
	 */
	public String getOpenId(HttpServletRequest request) {
		String appId = request.getParameter(BizConstant.APPID);
		String openId = null;
		Object attrObj = request.getSession().getAttribute(appId + "-" + BizConstant.OPENID);
		if (attrObj != null) {
			openId = attrObj.toString();
		}
		return openId;
	}

	public String getAlipayOpenId(HttpServletRequest request) {
		String appId = request.getParameter(BizConstant.APPID);
		String openId = null;
		logger.info("getAlipayOpenId:session 中的 OPENIDKEY: " + "alipay-" + appId + "-" + BizConstant.USERID);
		Object attrObj = request.getSession().getAttribute("alipay-" + appId + "-" + BizConstant.USERID);
		if (attrObj != null) {
			openId = attrObj.toString();
		}
		return openId;
	}

	public String getWechatOpenId(HttpServletRequest request) {
		String appId = request.getParameter(BizConstant.APPID);
		String openId = null;
		logger.info("getWechatOpenId:session 中的 OPENIDKEY: " + "alipay-" + appId + "-" + BizConstant.USERID);
		Object attrObj = request.getSession().getAttribute("wechat-" + appId + "-" + BizConstant.USERID);
		if (attrObj != null) {
			openId = attrObj.toString();
		}
		return openId;
	}

	/**
	 * 获取建康易app的虚拟openId
	 * 
	 * @param request
	 * @return
	 */
	public String getAppOpenId(HttpServletRequest request) {
		String openId = null;
		Object userObj = request.getSession().getAttribute(EasyHealthConstant.SESSION_USER_ENTITY);
		if (userObj != null && userObj instanceof EasyHealthUser) {
			openId = ( (EasyHealthUser) userObj ).getOpenid();
		}
		return openId;
	}

	/**
	 * 获取健康易登录的用户
	 * 
	 * @param request
	 * @return
	 */
	public EasyHealthUser getEasyHealthUser(HttpServletRequest request) {
		Object userObj = request.getSession().getAttribute(EasyHealthConstant.SESSION_USER_ENTITY);
		return (EasyHealthUser) userObj;
	}

	/**
	 * 得到应用的访问头地址 http://ip(域名):端口/应用名
	 * 
	 * @param request
	 * @return
	 */
	public String getAppUrl(HttpServletRequest request) {
		String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		return appUrl.concat(request.getContextPath());
	}

	/*	public ModelAndView checkUserInfoPerfect(CommonParamsVo paramsVo, HttpServletRequest request, String forwardUrl) {
			ModelAndView view = null;

			EasyHealthUser sessionUser = getEasyHealthUser(request);
			if (sessionUser != null) {
				if (StringUtils.isBlank(sessionUser.getCardNo())) {
					request.getSession().setAttribute(BizConstant.COMMON_SESSION_PARAMS + "_" + paramsVo.getOpenId(), paramsVo);
					ModelMap modelMap = new ModelMap();
					modelMap.put(BizConstant.URL_PARAM_FORWARD_NAME, forwardUrl);
					view = new ModelAndView(REDIRECT_PREFIX + getAppUrl(request) + PERFECT_USER_INFO_URL, modelMap);
				}
			}

			return view;
		}*/

	public Boolean easyHealthUserHasPerfectInfo(HttpServletRequest request) {
		boolean hasPerfectInfo = true;
		EasyHealthUser sessionUser = getEasyHealthUser(request);
		if (sessionUser != null) {
			hasPerfectInfo = StringUtils.isNotBlank(sessionUser.getCardNo());
		}
		return hasPerfectInfo;
	}

	public String getAreaCode(HttpServletRequest request) {
		String key = EasyHealthConstant.SESSION_AREA_CODE;
		String areaCode = null;
		Object obj = request.getSession().getAttribute(key);
		if (obj != null) {
			areaCode = (String) obj;
		}

		if (StringUtils.isBlank(areaCode)) {
			logger.error("could not find the areaCode in this session.");
		}

		return areaCode;
	}

	public void setAreaCode(HttpServletRequest request, String areaCode) {
		String key = EasyHealthConstant.SESSION_AREA_CODE;
		request.getSession().setAttribute(key, areaCode);
	}

	public String getAppCode(HttpServletRequest request) {
		String key = EasyHealthConstant.SESSION_APP_CODE;
		String appCode = null;
		Object obj = request.getSession().getAttribute(key);
		if (obj != null) {
			appCode = (String) obj;
		}

		if (StringUtils.isBlank(appCode)) {
			logger.error("could not find the appCode in this session.");
		}

		return appCode;
	}

	public String getAppCodeBySession(HttpServletRequest request) {
		String appCode = null;
		Object obj = request.getSession().getAttribute(EasyHealthConstant.SESSION_APP_CODE);
		if (obj != null) {
			appCode = (String) obj;
		}
		return appCode;
	}

	public String getAppCodeByCookie(HttpServletRequest request) {
		String appCode = null;

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
		return appCode;
	}

	public void setAppCode(HttpServletRequest request, String appCode) {
		String key = EasyHealthConstant.SESSION_APP_CODE;
		request.getSession().setAttribute(key, appCode);
	}

}
