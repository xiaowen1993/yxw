package com.yxw.easyhealth.common.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yxw.commons.enums.Module;
import com.yxw.easyhealth.common.service.CaptchaService;
import com.yxw.utils.NetworkUtil;
import com.yxw.utils.SCaptcha;

/**
 * @author Mingz on 2017/4/27.
 */
@Component
public class CaptchaServiceImpl implements CaptchaService {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    private static final String CAPTCHA_SUFFIX = "captcha";
    private static final String ATTR_NAME_SPLIT = "-";
    private static final String CODE_ATTR_NAME = "code";
    private static final String IP_ATTR_NAME = "ip";
    private static final String ONSET_TIME_ATTR_NAME = "onsetTime";

    @Override
    public void create(String appCode, String appId, String openId, Module module, HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        SCaptcha instance = new SCaptcha();
        instance.write(response.getOutputStream());
        Map<String, Object> captchaMap = new HashMap<String, Object>(2);
        captchaMap.put(CODE_ATTR_NAME, instance.getCode());
        captchaMap.put(IP_ATTR_NAME, NetworkUtil.getIpAddress(request));
        request.getSession().setAttribute(getAttributeName(appCode, appId, openId, module), captchaMap);
    }

	@Override
    public boolean checkCode(String appCode, String appId, String openId, Module module, String code,
                             HttpServletRequest request) {
        if (StringUtils.isBlank(code)) {
            return false;
        }

        String attrName = getAttributeName(appCode, appId, openId, module);
        @SuppressWarnings("unchecked")
		Map<String, Object> captchaMap = (Map<String, Object>) request.getSession().getAttribute(attrName);
        if (captchaMap == null) {
            return false;
        }

        String codeInSession = (String) captchaMap.get(CODE_ATTR_NAME);
        String ipInSession = (String) captchaMap.get(IP_ATTR_NAME);
        if (StringUtils.isBlank(codeInSession) || StringUtils.isBlank(ipInSession)) {
            return false;
        }

        String ip = NetworkUtil.getIpAddress(request);
        if (!codeInSession.toUpperCase().equals(code.toUpperCase()) || !ipInSession.equals(ip)) {
            return false;
        }

        captchaMap.remove(CODE_ATTR_NAME);
        // 为了RedisSession能识别出[AttributeName]属性的值有变化，需要使用新的map作为[AttributeName]属性的值
        captchaMap = new HashMap<String, Object>(captchaMap);
        captchaMap.put(ONSET_TIME_ATTR_NAME, System.currentTimeMillis());
        request.getSession().setAttribute(attrName, captchaMap);
        return true;
    }

    @Override
    public boolean validate(String appCode, String appId, String openId, Module module, long effectiveTime,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String attrName = getAttributeName(appCode, appId, openId, module);
        @SuppressWarnings("unchecked")
		Map<String, Object> captchaMap = (Map<String, Object>) request.getSession().getAttribute(attrName);
        if (captchaMap == null) {
            request.getRequestDispatcher("/mobileApp/captcha/notExist").forward(request, response);
            return false;
        }

        Long onsetTime = (Long) captchaMap.get(ONSET_TIME_ATTR_NAME);
        String ipInSession = (String) captchaMap.get(IP_ATTR_NAME);
        if (onsetTime == null || StringUtils.isBlank(ipInSession)) {
            request.getRequestDispatcher("/mobileApp/captcha/notExist").forward(request, response);
            return false;
        }

        String  ip = NetworkUtil.getIpAddress(request);
        if (!ipInSession.equals(ip)) {
            request.getRequestDispatcher("/mobileApp/captcha/invalid").forward(request, response);
            return false;
        }

        if(onsetTime + effectiveTime < System.currentTimeMillis()) {
            request.getRequestDispatcher("/mobileApp/captcha/expire").forward(request, response);
            request.getSession().removeAttribute(attrName);
            return false;
        }

        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        logger.info("{} 通过验证码校验,请求来自: {}.{}", attrName, stackTraceElement.getClassName(), stackTraceElement.getMethodName());

        // 为了RedisSession能识别出[AttributeName]属性的值有变化，需要使用新的map作为[AttributeName]属性的值
        captchaMap = new HashMap<String, Object>(captchaMap);
        // 刷新验证码生效时间
        captchaMap.put(ONSET_TIME_ATTR_NAME, System.currentTimeMillis());
        request.getSession().setAttribute(attrName, captchaMap);
        return true;
    }

    private static String getAttributeName(String appCode, String appId, String openId, Module module) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(appCode).append(ATTR_NAME_SPLIT).append(appId).append(ATTR_NAME_SPLIT).append(openId);
            sb.append(ATTR_NAME_SPLIT).append(module).append(ATTR_NAME_SPLIT).append(CAPTCHA_SUFFIX);
            return sb.toString();
        } finally {
            sb.setLength(0);
        }
    }
}
