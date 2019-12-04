package com.yxw.easyhealth.biz.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.easyhealth.common.controller.BaseAppController;

@Controller
@RequestMapping("easyhealth/listindex")
public class EhListIndexController extends BaseAppController {

	@RequestMapping(value = "/registerIndex")
	public ModelAndView toRegisterIndex(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("/easyhealth/biz/listindex/registerIndex");
		return view;
	}

}
