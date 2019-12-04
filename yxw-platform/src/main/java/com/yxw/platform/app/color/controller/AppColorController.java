package com.yxw.platform.app.color.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.commons.entity.platform.app.color.AppColor;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.platform.app.color.service.AppColorService;

@Controller
@RequestMapping("/sys/app/color")
public class AppColorController {
	private static Logger logger = LoggerFactory.getLogger(AppColorController.class);

	private AppColorService service = SpringContextHolder.getBean(AppColorService.class);

	@RequestMapping("/list")
	public ModelAndView toColorList(ModelMap modelMap, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/sys/app/color/list");

		try {
			List<AppColor> colors = service.findAll();
			modelMap.put("colors", colors);
		} catch (Exception e) {
			logger.error("加载系统配色信息列表错误。errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return view;
	}

	@RequestMapping("/edit")
	public ModelAndView toEdit(HttpServletRequest request, ModelMap modelMap, String id) {
		ModelAndView view = new ModelAndView("/sys/app/color/edit");

		if (StringUtils.isNotBlank(id)) {
			try {
				AppColor color = service.findById(id);
				modelMap.put("entity", color);
			} catch (Exception e) {
				logger.error("加载系统配置信息错误。id: {}. errorMsg: {}, cause: {}.", new Object[] { id, e.getMessage(), e.getCause() });
			}
		}

		return view;
	}

	@ResponseBody
	@RequestMapping("/save")
	public RespBody saveColor(AppColor appColor) {
		try {
			service.save(appColor);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			return new RespBody(Status.ERROR, "添加失败，请校验code");
		}
	}

	@ResponseBody
	@RequestMapping("/delete")
	public RespBody deleteColor(AppColor appColor) {
		if (StringUtils.isNoneBlank(appColor.getId())) {
			service.deleteById(appColor.getId());
		}
		return new RespBody(Status.OK);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(PKGenerator.generateId().toLowerCase());
		}
	}
}
