package com.yxw.platform.privilege.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 * */
@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping
	public String execute(HttpServletRequest request) {
		return "redirect:/sys/user/main";
	}

}
