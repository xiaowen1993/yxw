package com.yxw.easyhealth.common.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yxw.commons.enums.Module;

/**
 * @author Mingz on 2017/4/27.
 */
public interface CaptchaService {
    public void create(String appCode, String appId, String openId, Module module, HttpServletRequest request,
                       HttpServletResponse response) throws IOException;
    public boolean checkCode(String appCode, String appId, String openId, Module module, String code,
                             HttpServletRequest request);
    public boolean validate(String appCode, String appId, String openId, Module module, long effectiveTime,
                            HttpServletRequest request, HttpServletResponse response) throws Exception;
}
