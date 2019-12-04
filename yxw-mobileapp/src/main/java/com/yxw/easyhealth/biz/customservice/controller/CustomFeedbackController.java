package com.yxw.easyhealth.biz.customservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.app.biz.customservice.service.CustomFeedbackService;
import com.yxw.commons.entity.mobile.biz.customservice.Feedback;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;
import com.yxw.mobileapp.constant.EasyHealthConstant;

/**
 * 客服中心
 * 
 * @author 罗斌
 * @date 2015-10-22
 * */
@Controller
@RequestMapping("/easyhealth/customService")
public class CustomFeedbackController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(CustomFeedbackController.class);

	private CustomFeedbackService customFeedbackService = SpringContextHolder.getBean(CustomFeedbackService.class);

	@RequestMapping(value = "toFeedback")
	public ModelAndView toFeedback(HttpServletRequest request, String type) {

		EasyHealthUser user = getEasyHealthUser(request);

		String openId = user.getOpenid();

		List<Feedback> list = customFeedbackService.findFeedbackByOpenId(openId, type);

		ModelAndView modelAndView = new ModelAndView("easyhealth/biz/user/userService");

		modelAndView.addObject("replyList", list);

		modelAndView.addObject("type", type);

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/saveFeedback", method = RequestMethod.POST)
	public RespBody saveFeedback(String feedback, int type, HttpServletRequest request) {
		try {
			EasyHealthUser user = getEasyHealthUser(request);
			Feedback fb = new Feedback();
			fb.setId(PKGenerator.generateId());
			fb.setPlatformType(EasyHealthConstant.EASY_HEALTH);
			fb.setOpenId(user.getOpenid());
			fb.setFeedback(feedback);
			fb.setType(type);
			customFeedbackService.add(fb);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("保存客服建议出错");
			return new RespBody(Status.ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/testFeedback")
	public RespBody testFeedback() {
		try {
			Feedback feedback = new Feedback();
			feedback.setId(PKGenerator.generateId());
			feedback.setPlatformType(EasyHealthConstant.EASY_HEALTH);
			feedback.setOpenId("20885645512adasdadsd");
			feedback.setFeedback("ASDASDASDASDSADASDSAS");
			customFeedbackService.add(feedback);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("保存客服建议出错");
			return new RespBody(Status.ERROR);
		}
	}
}
