package com.yxw.platform.privilege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统界面菜单控制器类
 * 
 * */
@Controller
@RequestMapping("/menu")
public class MenuController {

	@RequestMapping("showMenu")
	public String showMenu() {
		return "/sys/common/menu";
	}

	@RequestMapping("head")
	public String head() {
		return "/sys/common/head";
	}

}
