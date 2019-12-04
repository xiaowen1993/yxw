package com.yxw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.framework.mvc.controller.RespBody;

@Controller
public class DeptController {
	@RequestMapping(value="/dept")
	public ModelAndView index(Model model, HttpServletRequest request) {
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/dept/getDatas")
	public RespBody getDatas(HttpServletRequest request) {
		return null;
	}
}
