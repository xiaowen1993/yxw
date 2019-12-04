package com.yxw.platform.sdk.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yxw.platform.sdk.alipay.constant.AlipayConstant;
import com.yxw.platform.sdk.wechat.constant.WechatConstant;

/**
 * SDK异常控制类
 * 
 * @author luob
 * */
@Controller
@RequestMapping("/sdk/exception")
public class SDKException {

	/**
	 * 获取openID异常 跳转
	 * 
	 * @param platformType
	 *            平台类型
	 * */
	@RequestMapping("/openIdExp")
	public String showException(String platformType, HttpServletRequest request) {
		if (WechatConstant.WECHAT.equals(platformType)) {
			request.setAttribute("msg", "微信服务异常，暂时无法获取用户openID号");
		} else if (AlipayConstant.ALIPAY.equals(platformType)) {
			request.setAttribute("msg", "支付宝服务异常，暂时无法获取用户openID号");
		} else {
			request.setAttribute("msg", "系统服务异常，暂时无法获取用户openID号");
		}
		return "/mobileApp/common/sdkException";
	}

	/**
	 * 请求问医生出现异常
	 * 
	 * @param msg
	 * */
	@RequestMapping("/askdoctorExp")
	public String showException(HttpServletRequest request, String msg) {
		request.setAttribute("msg", msg);
		return "/mobileApp/common/sdkException";
	}

	/**
	 * 白名单不通过异常
	 * 
	 * */
	@RequestMapping("/whiteListException")
	public String whiteListException(HttpServletRequest request) {
		request.setAttribute("msg", "正在建设中...");
		return "/mobileApp/common/sdkException";
	}
}
