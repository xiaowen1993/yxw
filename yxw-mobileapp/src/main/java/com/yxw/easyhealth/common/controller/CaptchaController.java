package com.yxw.easyhealth.common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxw.commons.vo.CaptchaVo;
import com.yxw.easyhealth.common.service.CaptchaService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;

/**
 * @author Mingz on 2017/4/27.
 */
@Controller
@RequestMapping("/app/captcha")
public class CaptchaController extends BaseAppController {
    private CaptchaService service = SpringContextHolder.getBean(CaptchaService.class);

    @ResponseBody
    @RequestMapping("/notExist")
    public Object notExist() {
        return new RespBody(RespBody.Status.PROMPT, "请输入验证码");
    }

    @ResponseBody
    @RequestMapping("/invalid")
    public Object invalid() {
        return new RespBody(RespBody.Status.PROMPT, "无效验证码，请重新输入");
    }

    @ResponseBody
    @RequestMapping("/expire")
    public Object expire() {
        return new RespBody(RespBody.Status.PROMPT, "验证码已失效，请重新输入");
    }

    @RequestMapping("/get")
    public void get(CaptchaVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (vo.getModule() == null) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "module不存在");
            return;
        }

        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        service.create(vo.getAppCode(), vo.getAppId(), vo.getOpenId(), vo.getModule(), request, response);
    }

    @ResponseBody
    @RequestMapping("/validate")
    public Object validate(CaptchaVo vo, HttpServletRequest request) {
        if (service.checkCode(vo.getAppCode(), vo.getAppId(), vo.getOpenId(), vo.getModule(), vo.getCode(), request)) {
            return new RespBody(RespBody.Status.OK, "验证码校验成功");
        }
        return new RespBody(RespBody.Status.ERROR, "验证码错误，请重新输入");
    }
}
