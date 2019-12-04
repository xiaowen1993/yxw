/**
 * Copyright© 2014-2016 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2016年11月13日
 * @version 1.0
 */
package com.yxw.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.utils.MD5Utils;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2016年11月13日  
 */
@Controller
public class SignInController {

	@RequestMapping(value = "/signin")
	public ModelAndView index(Model model, HttpServletRequest request) {
		return new ModelAndView("/signin");
	}

	@ResponseBody
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public RespBody confirm(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		String login_states = "false";

		String MD5pwd = "3993598b7010c1a6e3b8525e3f1806e3";

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		try {
			if (StringUtils.equals(MD5pwd, MD5Utils.getMd5String32(userName + password))) {
				login_states = "true";
				request.getSession().setAttribute("login_states", login_states);
				request.getSession().setAttribute("userName", userName);
			}
		} catch (Exception e) {
			return new RespBody(Status.ERROR, map);
		}

		map.put("login_states", login_states);

		return new RespBody(Status.OK, map);
	}

	@ResponseBody
	@RequestMapping(value = "/signout", method = RequestMethod.POST)
	public RespBody logout(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		request.getSession().removeAttribute("login_states");
		request.getSession().removeAttribute("userName");

		return new RespBody(Status.OK, map);
	}
}
