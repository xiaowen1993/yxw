package com.yxw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.outside.service.YxwStatsService;

@Controller
public class NumberAttrController {
	@RequestMapping(value = "/numberAttr")
	public ModelAndView index(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		return new ModelAndView("/numberAttrData").addObject("userName", userName);
	}
	
	@ResponseBody
	@RequestMapping(value = "/numberAttr/getDatas")
	public RespBody getDatas() {
		YxwStatsService service = SpringContextHolder.getBean(YxwStatsService.class);
		return new RespBody(Status.OK, JSON.parseObject(service.getAttributionDatas()));
	}
}
