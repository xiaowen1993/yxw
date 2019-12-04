package com.yxw.platform.costomservice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.app.biz.customservice.service.CustomFeedbackService;
import com.yxw.commons.entity.mobile.biz.customservice.Feedback;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;

/**
 * 客服中心
 * 
 * @author 罗斌
 * @date 2015-10-22
 * */
@Controller
@RequestMapping("/sys/customService")
public class CustomManageController extends BizBaseController<Feedback, String> {
	private static Logger logger = LoggerFactory.getLogger(CustomManageController.class);

	private CustomFeedbackService customFeedbackService = SpringContextHolder.getBean(CustomFeedbackService.class);

	@RequestMapping(value = "list")
	public ModelAndView
			list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",
					required = false, defaultValue = "10") Integer pageSize, @RequestParam(value = "type", defaultValue = "1") String type) {
		ModelAndView modelAndView = new ModelAndView("sys/customservice/list");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		PageInfo<Feedback> list = customFeedbackService.findListByPage(params, new Page<Feedback>(pageNum, pageSize));
		modelAndView.addObject("feedBackList", list);
		modelAndView.addObject("type", type);
		return modelAndView;
	}

	@RequestMapping(value = "toDialogWords")
	public ModelAndView toDialogWords() {
		ModelAndView modelAndView = new ModelAndView("/sys/customservice/reply");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/updateFeedback", method = RequestMethod.POST)
	public RespBody updateFeedback(String id, String reply, HttpServletRequest request) {
		try {
			Feedback entity = customFeedbackService.findById(id);
			if (entity != null) {
				entity.setReply(reply);
				customFeedbackService.update(entity);
				return new RespBody(Status.OK);
			} else {
				return new RespBody(Status.ERROR, "保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("保存客服建议出错");
			return new RespBody(Status.ERROR, "保存失败");
		}
	}

	@Override
	protected BaseService<Feedback, String> getService() {
		return customFeedbackService;
	}
}
