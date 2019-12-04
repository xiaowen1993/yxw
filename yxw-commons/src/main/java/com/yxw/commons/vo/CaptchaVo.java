package com.yxw.commons.vo;

import java.io.Serializable;

import com.yxw.commons.enums.Module;

/**
 * @author Mingz on 2017/4/28.
 */
public class CaptchaVo implements Serializable {
    private static final long serialVersionUID = -6115070944941305261L;

    private String appId;
    private String appCode;
    private String openId;
    /**
     * 模块
     *
     * @see com.yxw.commons.enums.Module
     */
    private Module module;
    /**
     * 字符码
     */
    private String code;

    public CaptchaVo() {
    }

    public CaptchaVo(String appId, String appCode, String openId, Module module) {
        this.appId = appId;
        this.appCode = appCode;
        this.openId = openId;
        this.module = module;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
