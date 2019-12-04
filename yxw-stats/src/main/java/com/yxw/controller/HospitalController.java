/**
 * Copyright© 2014-2015 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2015年12月31日
 * @version 1.0
 */
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

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2015年12月31日  
 */
@Controller
@RequestMapping(value = "/")
public class HospitalController {

	@RequestMapping(value = "/hospital")
	public ModelAndView index(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		return new ModelAndView("/hospitalData").addObject("userName", userName);
	}

	@ResponseBody
	@RequestMapping(value = "/hospital/getDatas")
	public RespBody getDatas() {
		YxwStatsService service = SpringContextHolder.getBean(YxwStatsService.class);
		return new RespBody(Status.OK, JSON.parseObject(service.getAreaHospitalInfos()));
	}

}
