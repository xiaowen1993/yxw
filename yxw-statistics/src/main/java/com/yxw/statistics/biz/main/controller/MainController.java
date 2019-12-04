package com.yxw.statistics.biz.main.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("sys/main/")  
public class MainController {

	@RequestMapping(value = "index")
	public ModelAndView toMain(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/biz/homepage/main");
		
		return view;
	}

	@RequestMapping(value = "head")
	public ModelAndView toHead(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/biz/homepage/head");
		return view;
	}

	@RequestMapping(value = "menu")
	public ModelAndView toMenu(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/biz/homepage/menu");
		return view;
	}

}
